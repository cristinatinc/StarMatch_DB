import model.Admin;
import model.NatalChart;
import model.StarSign;
import model.User;
import repository.InMemoryRepository;
import repository.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class ConsoleApp {
    private final StarMatchController starMatchController;

    public ConsoleApp(StarMatchController starMatchController) {
        this.starMatchController = starMatchController;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("""
                    -- StarMatch --
                    Welcome! Please log in:
                    1. Log in as User
                    2. Log in as Admin
                    3. Sign up as User
                    
                    0. Exit Application
                    """);

            String option = scanner.nextLine();

            switch (option) {
                case "0" -> {
                    System.out.println("Exiting application. Goodbye!");
                    continueLoop = false;
                }
                case "1" -> userLoginMenu(scanner);
                case "2" -> adminLoginMenu(scanner);
                case "3" -> userSignUpMenu(scanner);
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private void userLoginMenu(Scanner scanner) {
        System.out.println("-- User Log In --");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (starMatchController.handleUserLogin(email, password)) {
            System.out.println("Login successful! Welcome, " + email);
            userMenu(scanner, email);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void adminLoginMenu(Scanner scanner) {
        System.out.println("-- Admin Log In --");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (starMatchController.handleAdminLogin(email, password)) {
            System.out.println("Login successful! Welcome, " + email);
            adminMenu(scanner);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void userSignUpMenu(Scanner scanner) {
        System.out.println("-- User Sign Up --");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Birthdate (yyyy-MM-dd): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Time of birth (HH:mm): ");
        String timeOfBirth = scanner.nextLine();
        System.out.print("Place of birth: ");
        String placeOfBirth = scanner.nextLine();

        starMatchController.signUpNewUser(name, LocalDate.parse(dateOfBirth), LocalTime.parse(timeOfBirth), placeOfBirth, email, password);

        System.out.println("Sign-up successful! Welcome, " + name);
        userMenu(scanner, email);
    }

    private void userMenu(Scanner scanner, String userEmail) {
        boolean userLoop = true;
        while (userLoop) {
            System.out.print("""
                    -- User Dashboard --
                    1. View Profile
                    2. View Natal Chart
                    3. View Personality Traits
                    4. View Daily Quote
                    5. Manage Friends
                    6. Check Compatibility
                    
                    0. Log Out
                    """);

            String userOption = scanner.nextLine();

            switch (userOption) {
                case "0" -> {
                    System.out.println("Logging out...");
                    userLoop = false;
                }
                case "1" -> viewUserProfile(userEmail);
                case "2" -> viewNatalChart(userEmail);
                case "3" -> System.out.println("Displaying personality traits...");
                case "4" -> System.out.println("Displaying daily quote...");
                case "5" -> System.out.println("Managing friends...");
                case "6" -> System.out.println("Checking compatibility...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void adminMenu(Scanner scanner) {
        boolean adminLoop = true;
        while (adminLoop) {
            System.out.print("""
                    -- Admin Dashboard --
                    1. Manage Users
                    2. Manage Traits
                    3. Manage Quotes
                    4. Manage Admins
                    
                    0. Log Out
                    """);

            String adminOption = scanner.nextLine();

            switch (adminOption) {
                case "0" -> {
                    System.out.println("Logging out...");
                    adminLoop = false;
                }
                case "1" -> System.out.println("Managing users...");
                case "2" -> System.out.println("Managing traits...");
                case "3" -> System.out.println("Managing quotes...");
                case "4" -> adminManageAdminMenu(scanner);
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void adminManageAdminMenu(Scanner scanner) {
        boolean adminLoop = true;
        while (adminLoop) {
            System.out.print("""
                    -- Manage other Admins --
                    1. View all admins
                    2. Add new admin
                    3. Delete admin
                    4. Update existing admin
                    
                    0. Go back to main menu
                    """);

            String adminOption = scanner.nextLine();

            switch (adminOption) {
                case "0" -> adminLoop = false;
                case "1" -> starMatchController.viewAdmins();
                case "2" -> addNewAdmin(scanner);
                case "3" -> removeAdmin(scanner);
                case "4" -> System.out.println("update existing admin...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addNewAdmin(Scanner scanner) {
        System.out.println("-- Add New Admin --");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        starMatchController.addNewAdmin(name, email, password);
    }

    private void removeAdmin(Scanner scanner) {
        System.out.println("-- Remove Admin --");
        starMatchController.viewAdmins();
        System.out.print("Admin ID: ");
        String adminID = scanner.nextLine();

        starMatchController.removeAdmin(Integer.valueOf(adminID));
    }

    private void viewNatalChart(String userEmail) {
        NatalChart natalChart = starMatchController.viewNatalChart(userEmail);
        if (natalChart != null) {
            System.out.println("Natal Chart:");
            natalChart.getPlanets().forEach(planet ->
                    System.out.println(planet.getPlanetName() + ": " + planet.getSign().getStarName())
            );
        } else {
            System.out.println("No natal chart available for this user.");
        }
    }

    private void viewUserProfile(String userEmail) {
        User user=starMatchController.viewUserProfile(userEmail);
        System.out.println("""
                User Profile:
                Name: """ + user.getName() + """
                
                Birthdate: """ + user.getBirthDate() +
                """
                
                Birthtime: """ + user.getBirthTime() +
                """
                
                Birthplace: """ + user.getBirthPlace() +
                """
                
                Email: """ + user.getEmail() +
                """
                
                Password: """ + user.getPassword());
    }

    public static void main(String[] args) {
        Repository<User> userRepository = createInMemoryUserRepository();
        Repository<Admin> adminRepository = createInMemoryAdminRepository();
        Repository<StarSign> signRepository = createInMemoryStarSignRepository();

        StarMatchService starMatchService = new StarMatchService(userRepository, adminRepository,signRepository);
        StarMatchController starMatchController = new StarMatchController(starMatchService);

        ConsoleApp consoleApp = new ConsoleApp(starMatchController);
        consoleApp.start();
    }

    private static Repository<User> createInMemoryUserRepository() {
        Repository<User> userRepository = new InMemoryRepository<>();
        userRepository.create(new User(1, "Amna", LocalDate.of(2000, 3, 12), LocalTime.of(9,0), "Romania", "amna@gmail.com", "parola"));
        userRepository.create(new User(2, "Florian", LocalDate.of(2007, 7, 24), LocalTime.of(10,0), "Romania", "florinel@gmail.com", "0987"));
        userRepository.create(new User(3, "Briana Gheorghe", LocalDate.of(2004, 1, 3), LocalTime.of(22,12), "USA", "brianaagheorghe@yahoo.com", "bribri"));
        userRepository.create(new User(4, "sore marian", LocalDate.of(1990, 9, 10), LocalTime.of(6,23), "Germania", "soremarian@gmail.com", "sore1"));
        return userRepository;
    }

    private static Repository<Admin> createInMemoryAdminRepository() {
        Repository<Admin> adminRepository = new InMemoryRepository<>();
        adminRepository.create(new Admin(1, "Bogdan Popa", "bogdan.popa@yahoo.com", "1234"));
        adminRepository.create(new Admin(2, "Ioana Popa", "ioana.popa@yahoo.com" , "1234"));
        return adminRepository;
    }

    private static Repository<StarSign> createInMemoryStarSignRepository() {
        Repository<StarSign> signRepository = new InMemoryRepository<>();
        //signRepository.create(new StarSign());
        return signRepository;
    }


}
