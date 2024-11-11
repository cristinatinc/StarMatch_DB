import model.*;
import repository.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class StarMatchService {
    private final Repository<User> userRepository;
    private final Repository<Admin> adminRepository;
    private final Repository<StarSign> signRepository;
    private final Repository<Quote> quoteRepository;
    private final Repository<Trait> traitRepository;

    public StarMatchService(Repository<User> userRepository, Repository<Admin> adminRepository, Repository<StarSign> signRepository, Repository<Quote> quoteRepository, Repository<Trait> traitRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.signRepository = signRepository;
        this.quoteRepository = quoteRepository;
        this.traitRepository = traitRepository;
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
        User newUser = new User(getMaxId(userRepository) + 1, name, birthDate, birthTime, birthPlace, email, password);
        userRepository.create(newUser);
    }

    public void removeUser(Integer userId) {
        userRepository.delete(userId);
    }

    public void createAdmin(String name, String email, String password) {
        Admin newAdmin = new Admin(getMaxId(adminRepository) + 1, name, email, password);
        adminRepository.create(newAdmin);
    }

    public void removeAdmin(Integer adminId){
        adminRepository.delete(adminId);
    }

    public void updateAdmin(Integer adminId, String name, String email, String password) {
        Admin admin = adminRepository.get(adminId);
        if (!name.isBlank()) admin.setName(name);
        if (!email.isBlank()) admin.setEmail(email);
        if (!password.isBlank()) admin.setPassword(password);
        adminRepository.update(admin);
    }

    public void createQuote(String newQuoteText, String element) {
        Element quoteElement = null;
        for (Element e : Element.values()) {
            if (e.name().equalsIgnoreCase(element)) {
                quoteElement = e;
                break;
            }
        }
        if (quoteElement != null) {
            Quote newQuote = new Quote(getMaxId(quoteRepository) + 1, quoteElement, newQuoteText);
            quoteRepository.create(newQuote);
            System.out.println("Quote added successfully!");
        } else {
            System.out.println("Invalid element specified. Quote not created.");
        }
    }

    public void removeQuote(Integer quoteId) {
        quoteRepository.delete(quoteId);
    }

    public void updateQuote(Integer quoteId, String newQuoteText) {
        Quote quote = quoteRepository.get(quoteId);
        quote.setQuoteText(newQuoteText);
        quoteRepository.update(quote);
    }

    public <T extends HasId> int getMaxId(Repository<T> repository) {
        return repository.getAll().stream()
                .mapToInt(T::getId)
                .max()
                .orElse(0);
    }

    public List<Admin> getAdmins() { return adminRepository.getAll();}

    public List<User> getUsers() { return userRepository.getAll();}

    public List<Quote> getQuotes() { return quoteRepository.getAll();}

    public User getUserByEmail(String email) {
        return userRepository.getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public NatalChart getNatalChart(User user) {

        List<Planet> planets = new ArrayList<>();
        LocalDate birthDate = user.getBirthDate();
        LocalTime birthTime = user.getBirthTime();

        StarSign sunSign = calculateSunSign(birthDate);
        StarSign moonSign = calculateMoonSign(birthDate);
        StarSign risingSign = calculateRisingSign(birthTime);

        planets.add(new Planet("Sun", sunSign, 1));
        planets.add(new Planet("Moon", moonSign, 2));
        planets.add(new Planet("Rising", risingSign, 3));

        return new NatalChart(planets);
    }

    private StarSign calculateSunSign(LocalDate birthDate) {
        int day = birthDate.getDayOfMonth();
        String sunSignName;

        switch (birthDate.getMonth()) {
            case MARCH -> sunSignName = (day < 21) ? "Pisces" : "Aries";
            case APRIL -> sunSignName = (day < 20) ? "Aries" : "Taurus";
            case MAY -> sunSignName = (day < 21) ? "Taurus" : "Gemini";
            case JUNE -> sunSignName = (day < 21) ? "Gemini" : "Cancer";
            case JULY -> sunSignName = (day < 23) ? "Cancer" : "Leo";
            case AUGUST -> sunSignName = (day < 23) ? "Leo" : "Virgo";
            case SEPTEMBER -> sunSignName = (day < 23) ? "Virgo" : "Libra";
            case OCTOBER -> sunSignName = (day < 23) ? "Libra" : "Scorpio";
            case NOVEMBER -> sunSignName = (day < 22) ? "Scorpio" : "Sagittarius";
            case DECEMBER -> sunSignName = (day < 22) ? "Sagittarius" : "Capricorn";
            case JANUARY -> sunSignName = (day < 20) ? "Capricorn" : "Aquarius";
            case FEBRUARY -> sunSignName = (day < 19) ? "Aquarius" : "Pisces";
            default -> sunSignName = "Unknown";
        }

        return signRepository.getAll().stream().filter(starSign -> starSign.getStarName().equals(sunSignName)).findFirst().orElse(null);
    }

    private StarSign calculateMoonSign(LocalDate birthDate){
        long daysSinceFixedDate = java.time.temporal.ChronoUnit.DAYS.between(
                LocalDate.of(2000, 1, 1), birthDate);
        int moonIndex = (int) ((daysSinceFixedDate / 2.5) % 12);

        String moonSignName = getZodiacSignFromIndex(moonIndex);
        return signRepository.getAll().stream().filter(starSign -> starSign.getStarName().equals(moonSignName)).findFirst().orElse(null);
    }

    private StarSign calculateRisingSign(LocalTime birthTime) {
        int hour = birthTime.getHour();
        int risingIndex = (hour / 2) % 12;
        String risingSign= getZodiacSignFromIndex(risingIndex);
        return signRepository.getAll().stream().filter(starSign -> starSign.getStarName().equals(risingSign)).findFirst().orElse(null);
    }

    private String getZodiacSignFromIndex(int index) {
        String[] zodiacSigns = {
                "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
                "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"
        };
        return zodiacSigns[index];
    }

    public List<String> getPersonalityTraits(User user){
        NatalChart chart=getNatalChart(user);
        StarSign sunSign=chart.getPlanets().getFirst().getSign();
        return sunSign.getTraits().stream()
                .map(Trait::getTraitName)
                .collect(Collectors.toList());
    }

    public String getPersonalizedQuote(User user){
        NatalChart chart=getNatalChart(user);
        Element element=chart.getPlanets().getFirst().getSign().getElement();
        List<String> quotes=quoteRepository.getAll().stream().filter(quote -> quote.getElement().equals(element)).map(Quote::getQuoteText).toList();
        Random random=new Random();
        return quotes.get(random.nextInt(quotes.size()));
    }

    public void updateUser(User user,String name, String email, String password, LocalDate birthDate, LocalTime birthTime, String birthPlace){
        User user1 = userRepository.get(user.getId());
        if (!name.isBlank()) user1.setName(name);
        if (!email.isBlank()) user1.setEmail(email);
        if (!password.isBlank()) user1.setPassword(password);
        if (birthDate!=null) user1.setBirthDate(birthDate);
        if (birthTime!=null) user1.setBirthTime(birthTime);
        if (!birthPlace.isBlank()) user1.setBirthPlace(birthPlace);
        userRepository.update(user1);
    }

    public List<User> getAllUsersExcept(User currentUser){
        return userRepository.getAll().stream().filter(user -> !user.equals(currentUser)).collect(Collectors.toList());
    }

    public void addFriend(User user, String friendEmail){
        User friend=userRepository.getAll().stream().filter(user1 -> user1.getEmail().equals(friendEmail)).findFirst().orElseThrow(() -> new NoSuchElementException("User with that email does not exist"));
        if(!user.getFriends().contains(friend))
            user.getFriends().add(friend);
    }

    public List<User> getFriends(User user){
        return user.getFriends();
    }

    public void removeFriend(User user, String friendEmail){
        User friend=userRepository.getAll().stream().filter(user1 -> user1.getEmail().equals(friendEmail)).findFirst().orElseThrow(() -> new NoSuchElementException("User with that email does not exist"));
        if(user.getFriends().contains(friend))
            user.getFriends().remove(friend);
    }

}
