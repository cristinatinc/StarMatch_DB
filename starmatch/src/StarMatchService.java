import model.Admin;
import model.NatalChart;
import model.User;
import repository.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class StarMatchService {
    private final Repository<User> userRepository;
    private final Repository<Admin> adminRepository;

    public StarMatchService(Repository<User> userRepository, Repository<Admin> adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    public boolean validateUserLogin(String email, String password) {
        return userRepository.getAll().stream()
                .anyMatch(user -> user.getEmail().equals(email) && user.getPassword().equals(password));
    }

    public boolean validateAdminLogin(String email, String password) {
        return adminRepository.getAll().stream()
                .anyMatch(admin -> admin.getEmail().equals(email) && admin.getPassword().equals(password));
    }

    public void createUser(String name, LocalDate birthDate, LocalTime birthTime, String birthPlace, String email, String password) {
        User newUser = new User(getMaxUserId() + 1, name, birthDate, birthTime, birthPlace, email, password);
        userRepository.create(newUser);
    }

    public void createAdmin(String name, String email, String password) {
        Admin newAdmin = new Admin(getMaxUserId() + 1, name, email, password);
        adminRepository.create(newAdmin);
    }

    public int getMaxUserId() {
        return userRepository.getAll().stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0);
    }

    public List<Admin> getAdmins() { return adminRepository.getAll();}

    public List<User> getUsers() { return userRepository.getAll();}

    //public NatalChart getNatalChart(LocalDate birthDate, LocalTime birthTime, String birthPlace) {}
}
