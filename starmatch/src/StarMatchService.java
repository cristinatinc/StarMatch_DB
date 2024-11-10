import model.*;
import repository.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class StarMatchService {
    private final Repository<User> userRepository;
    private final Repository<Admin> adminRepository;
    private final Repository<StarSign> signRepository;
    private final Repository<Quote> quoteRepository;

    public StarMatchService(Repository<User> userRepository, Repository<Admin> adminRepository, Repository<StarSign> signRepository, Repository<Quote> quoteRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.signRepository = signRepository;
        this.quoteRepository = quoteRepository;
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

    public void removeUser(Integer userId) {
        userRepository.delete(userId);
    }

    public void createAdmin(String name, String email, String password) {
        Admin newAdmin = new Admin(getMaxUserId() + 1, name, email, password);
        adminRepository.create(newAdmin);
    }

    public void removeAdmin(Integer adminId){
        adminRepository.delete(adminId);
    }

    public void updateAdmin(Integer adminId, String name, String email, String password) {
        Admin admin = adminRepository.get(adminId);

        if (admin != null) {
            if (!name.isBlank()) admin.setName(name);
            if (!email.isBlank()) admin.setEmail(email);
            if (!password.isBlank()) admin.setPassword(password);
            adminRepository.update(admin);
            System.out.println("Admin updated successfully.");
        } else {
            System.out.println("Admin not found.");
        }
    }


    public int getMaxUserId() {
        return userRepository.getAll().stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0);
    }

    public List<Admin> getAdmins() { return adminRepository.getAll();}

    public List<User> getUsers() { return userRepository.getAll();}

    public User getUserByEmail(String email) {
        return userRepository.getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public NatalChart getNatalChart(User user) {
        List<Planet> planets = new ArrayList<>();
        LocalDate birthDate=user.getBirthDate();
        LocalTime birthTime=user.getBirthTime();
        String sunSign=calculateSunSign(birthDate);
        String moonSign=calculateMoonSign(birthDate);
        String risingSign=calculateRisingSign(birthTime);
        planets.add(new Planet("Sun", new StarSign(null,null,sunSign,1),1));
        planets.add(new Planet("Moon", new StarSign(null,null,moonSign,2),2));
        planets.add(new Planet("Rising", new StarSign(null,null,risingSign,3),3));
        return new NatalChart(planets);
    }

    private String calculateSunSign(LocalDate birthDate) {
        int day= birthDate.getDayOfMonth();
        switch (birthDate.getMonth()) {
            case MARCH -> {
                return (day<21) ? "Pisces" : "Aries";
            }
            case APRIL -> {
                return (day<20) ? "Aries" : "Taurus";
            }
            case MAY -> {
                return (day<21) ? "Taurus" : "Gemini";
            }
            case JUNE -> {
                return (day<21) ? "Gemini" : "Cancer";
            }
            case JULY -> {
                return (day<23) ? "Cancer" : "Leo";
            }
            case AUGUST -> {
                return (day<23) ? "Leo" : "Virgo";
            }
            case SEPTEMBER -> {
                return (day<23) ? "Virgo" : "Libra";
            }
            case OCTOBER -> {
                return (day<23) ? "Libra" : "Scorpio";
            }
            case NOVEMBER -> {
                return (day<22) ? "Scorpio" : "Sagittarius";
            }
            case DECEMBER -> {
                return (day<22) ? "Sagittarius" : "Capricorn";
            }
            case JANUARY -> {
                return (day<20) ? "Capricorn" : "Aquarius";
            }
            case FEBRUARY -> {
                return (day<19) ? "Aquarius" : "Pisces";
            }
            default -> {
                return "Unknown";
            }
        }
    }

    private String calculateMoonSign(LocalDate birthDate){
        long daysSinceFixedDate = java.time.temporal.ChronoUnit.DAYS.between(
                LocalDate.of(2000, 1, 1), birthDate);
        int moonIndex = (int) ((daysSinceFixedDate / 2.5) % 12);
        return getZodiacSignFromIndex(moonIndex);
    }

    private String calculateRisingSign(LocalTime birthTime) {
        int hour = birthTime.getHour();
        int risingIndex = (hour / 2) % 12;
        return getZodiacSignFromIndex(risingIndex);
    }

    private String getZodiacSignFromIndex(int index) {
        String[] zodiacSigns = {
                "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
                "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"
        };
        return zodiacSigns[index];
    }


}
