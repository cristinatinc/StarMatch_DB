import model.*;
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
                case "1" -> adminManageUserMenu(scanner);
                case "2" -> System.out.println("Managing traits...");
                case "3" -> adminManageQuotesMenu(scanner);
                case "4" -> adminManageAdminMenu(scanner);
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void adminManageUserMenu(Scanner scanner) {
        boolean adminLoop = true;
        while (adminLoop) {
            System.out.print("""
                    -- Manage other Users --
                    1. View all users
                    2. Delete users
                    
                    0. Go back to admin menu
                    """);

            String adminOption = scanner.nextLine();

            switch (adminOption) {
                case "0" -> adminLoop = false;
                case "1" -> starMatchController.viewUsers();
                case "2" -> removeUsers(scanner);
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void removeUsers(Scanner scanner) {
        System.out.println("-- Remove Users --");
        starMatchController.viewUsers();
        System.out.print("User ID: ");
        String userID = scanner.nextLine();

        starMatchController.removeUser(Integer.valueOf(userID));
    }

    private void adminManageQuotesMenu(Scanner scanner) {
        boolean adminLoop = true;
        while (adminLoop) {
            System.out.print("""
                    -- Manage Quotes used by the app --
                    1. View all quotes
                    2. Add new quote
                    3. Delete quote
                    4. Update quote text
                    
                    0. Go back to admin menu
                    """);

            String adminOption = scanner.nextLine();

            switch (adminOption) {
                case "0" -> adminLoop = false;
                case "1" -> starMatchController.viewQuotes();
                case "2" -> addNewQuote(scanner);
                case "3" -> removeQuote(scanner);
                case "4" -> updateQuote(scanner);
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addNewQuote(Scanner scanner) {
        System.out.println("-- Add New Quote --");
        System.out.print("Enter the quote: ");
        String newQuote = scanner.nextLine();
        System.out.print("Enter the element represented by the quote: ");
        String element = scanner.nextLine();

        starMatchController.addNewQuote(newQuote, element);
    }

    private void removeQuote(Scanner scanner) {
        System.out.println("-- Delete quote --");
        starMatchController.viewQuotes();
        System.out.print("Quote ID: ");
        String quoteID = scanner.nextLine();

        starMatchController.removeQuote(Integer.valueOf(quoteID));
    }

    private void updateQuote(Scanner scanner) {
        System.out.println("-- Update Quote --");
        starMatchController.viewQuotes();
        System.out.print("Enter the quote ID to update: ");
        String quoteID = scanner.nextLine();
        System.out.print("New quote text: ");
        String quoteText = scanner.nextLine();

        starMatchController.updateQuote(Integer.valueOf(quoteID), quoteText);
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
                    
                    0. Go back to admin menu
                    """);

            String adminOption = scanner.nextLine();

            switch (adminOption) {
                case "0" -> adminLoop = false;
                case "1" -> starMatchController.viewAdmins();
                case "2" -> addNewAdmin(scanner);
                case "3" -> removeAdmin(scanner);
                case "4" -> updateAdmin(scanner);
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

    private void updateAdmin(Scanner scanner) {
        System.out.println("-- Update Existing Admin --");
        starMatchController.viewAdmins();

        System.out.print("Admin ID to update: ");
        Integer adminId = Integer.valueOf(scanner.nextLine());

        System.out.print("New Name (leave blank to keep current): ");
        String name = scanner.nextLine();
        System.out.print("New Email (leave blank to keep current): ");
        String email = scanner.nextLine();
        System.out.print("New Password (leave blank to keep current): ");
        String password = scanner.nextLine();

        starMatchController.updateAdmin(adminId, name, email, password);
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
        Repository<Quote> quoteRepository = createInMemoryQuoteRepository();

        StarMatchService starMatchService = new StarMatchService(userRepository, adminRepository,signRepository, quoteRepository);
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

    private static Repository<Quote> createInMemoryQuoteRepository() {
        Repository<Quote> quoteRepository = new InMemoryRepository<>();
        Element fire = Element.Fire;
        Element water = Element.Water;
        Element air = Element.Air;
        Element earth = Element.Earth;

        quoteRepository.create(new Quote(1, fire ,"The only trip you will regret is the one you don’t take." ));
        quoteRepository.create(new Quote(2, fire ,"Adventure is worthwhile in itself." ));
        quoteRepository.create(new Quote(3, fire ,"Life begins at the end of your comfort zone." ));
        quoteRepository.create(new Quote(4, fire ,"Free spirits don't ask for permission." ));
        quoteRepository.create(new Quote(5, water ,"Normal is nothing more than a cycle on a washing machine." ));
        quoteRepository.create(new Quote(6, water ,"The great gift of human beings is that we have the power of empathy." ));
        quoteRepository.create(new Quote(7, water ,"To be rude to someone is not my nature." ));
        quoteRepository.create(new Quote(8, water ,"Learn as much from joy as you do from pain." ));
        quoteRepository.create(new Quote(9, air ,"That was her gift. She filled you with words you didn’t know were there." ));
        quoteRepository.create(new Quote(10, air ,"I feel like I'm too busy writing history to read it." ));
        quoteRepository.create(new Quote(11, air ,"Identify with everything. Align with nothing." ));
        quoteRepository.create(new Quote(12, air ,"Everything in the universe is within you. Ask all from yourself." ));
        quoteRepository.create(new Quote(13, earth ,"Empty yourself and let the universe fill you." ));
        quoteRepository.create(new Quote(14, earth ,"Fall seven times, stand up eight." ));
        quoteRepository.create(new Quote(15, earth ,"I have standards I don’t plan on lowering for anybody, including myself." ));
        quoteRepository.create(new Quote(16, earth ,"Be easily awed, not easily impressed." ));
        return quoteRepository;
    }

}
