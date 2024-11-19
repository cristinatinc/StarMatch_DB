package repository;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import model.HasId;

public class InFileRepository<T extends HasId> implements Repository<T> {
    private final String filePath;
    private final Class<T> entityClass;

    /**
     * Constructs a new FileRepository with the specified file path.
     *
     * @param filePath The path to the file where data will be stored.
     * @param entityClass The class type of T, used for reflection.
     */
    public InFileRepository(String filePath, Class<T> entityClass) {
        this.filePath = filePath;
        this.entityClass = entityClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(T obj) {
        doInFile(data -> data.putIfAbsent(obj.getId(), obj));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(Integer id) {
        return readDataFromFile().get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(T obj) {
        doInFile(data -> data.replace(obj.getId(), obj));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        doInFile(data -> data.remove(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll() {
        return new ArrayList<>(readDataFromFile().values());
    }

    /**
     * Performs an operation on the data stored in the file.
     *
     * @param function The function to apply to the data.
     */
    private void doInFile(Consumer<Map<Integer, T>> function) {
        Map<Integer, T> data = readDataFromFile();
        function.accept(data);
        writeDataToFile(data);
    }

    /**
     * Reads the data from the file.
     *
     * @return The data stored in the file, or an empty map if the file is empty or does not exist.
     */
    private Map<Integer, T> readDataFromFile() {
        Map<Integer, T> data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                Integer id = Integer.valueOf(fields[0]);
                T obj = createObjectFromFields(fields);
                if (obj != null) {
                    data.put(id, obj);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Writes the data to the file.
     *
     * @param data The data to write to the file.
     */
    private void writeDataToFile(Map<Integer, T> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (T obj : data.values()) {
                String line = convertObjectToLine(obj);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts an object to a CSV line using the object's own method.
     *
     * @param obj The object to convert.
     * @return The CSV line representation of the object.
     */
    private String convertObjectToLine(T obj) {
        return obj.convertObjectToLine();
    }

    /**
     * Creates an object from the CSV fields by invoking the `createObjectFromFields` method.
     * This assumes that the class T implements `HasId` and provides this static method.
     *
     * @param fields The fields from the CSV line.
     * @return A new instance of T created from the fields, or null if creation fails.
     */
    private T createObjectFromFields(String[] fields) {
        try {
            // Assuming the class implements a static createObjectFromFields method
            Method method = entityClass.getMethod("createObjectFromFields", String[].class);
            return (T) method.invoke(null, (Object) fields); // Invoke the method using reflection
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
