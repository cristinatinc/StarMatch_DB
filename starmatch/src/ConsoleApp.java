import model.*;
import repository.InFileRepository;
import repository.InMemoryRepository;
import repository.Repository;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleApp {
    private final StarMatchController starMatchController;

    /**
     * Constructor for initializing ConsoleApp with the StarMatchController
     * @param starMatchController to interact with the controller
     */
    public ConsoleApp(StarMatchController starMatchController) {
        this.starMatchController = starMatchController;
    }

    /**
     * Starts the console application and displays the main menu, allowing users to log in or sign up and admins to log in.
     */
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

    /**
     * Handles the login process for a user.
     * @param scanner The scanner instance to get user input.
     */
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

    /**
     * Handles the login process for an admin.
     * @param scanner The scanner instance to get admin input.
     */
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

    /**
     * Allows a new user to sign up with required details.
     * @param scanner The scanner instance to get user input.
     */
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

        try{
        starMatchController.signUpNewUser(name, LocalDate.parse(dateOfBirth), LocalTime.parse(timeOfBirth), placeOfBirth, email, password);
        System.out.println("Sign-up successful! Welcome, " + name);}
        catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        userMenu(scanner, email);
    }

    /**
     * Displays the user dashboard with options for using the application.
     * @param scanner The scanner instance to get user input.
     * @param userEmail The email of the currently logged-in user.
     */
    private void userMenu(Scanner scanner, String userEmail) {
        boolean userLoop = true;
        while (userLoop) {
            System.out.print("""
                    -- User Dashboard --
                    1. View Profile
                    2. Update Profile
                    3. View Natal Chart
                    4. View Personality Traits
                    5. View Personalized Quote
                    6. Manage Friends
                    7. Check Compatibility
                    
                    0. Log Out
                    """);

            String userOption = scanner.nextLine();

            switch (userOption) {
                case "0" -> {
                    System.out.println("Logging out...");
                    userLoop = false;
                }
                case "1" -> viewUserProfile(userEmail);
                case "2" -> updateUserProfile(scanner, userEmail);
                case "3" -> viewNatalChart(userEmail);
                case "4" -> viewPersonalityTraits(userEmail);
                case "5" -> viewPersonalizedQuote(userEmail);
                case "6" -> manageFriendsMenu(scanner,userEmail);
                case "7" -> viewCompatibility(scanner,userEmail);
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays the admin dashboard with options for managing users, traits, quotes, and admins.
     * @param scanner The scanner instance to get admin input.
     */
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
                case "2" -> adminManageTraitsMenu(scanner);
                case "3" -> adminManageQuotesMenu(scanner);
                case "4" -> adminManageAdminMenu(scanner);
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Provides the admin with options for managing user accounts, viewing and deleting.
     * @param scanner The scanner instance to get admin input.
     */
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

    /**
     * Removes a user from the system.
     * @param scanner The scanner used for reading input from the admin.
     */
    private void removeUsers(Scanner scanner) {
        System.out.println("-- Remove Users --");
        starMatchController.viewUsers();
        System.out.print("User ID: ");
        String userID = scanner.nextLine();

        starMatchController.removeUser(Integer.valueOf(userID));
    }

    /**
     * Provides the admin with options for managing quotes.
     * @param scanner The scanner instance to get admin input.
     */
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

    /**
     * Adds a new quote based on a certain element
     * @param scanner The scanner instance to get admin input.
     */
    private void addNewQuote(Scanner scanner) {
        System.out.println("-- Add New Quote --");
        System.out.print("Enter the quote: ");
        String newQuote = scanner.nextLine();
        System.out.print("Enter the element represented by the quote: ");
        String element = scanner.nextLine();

        starMatchController.addNewQuote(newQuote, element);
    }

    /**
     * Removes a quote based on the ID
     * @param scanner The scanner instance to get admin input.
     */
    private void removeQuote(Scanner scanner) {
        System.out.println("-- Delete quote --");
        starMatchController.viewQuotes();
        System.out.print("Quote ID: ");
        String quoteID = scanner.nextLine();

        starMatchController.removeQuote(Integer.valueOf(quoteID));
    }

    /**
     * Updates a quotes based on the ID
     * @param scanner The scanner instance to get admin input.
     */
    private void updateQuote(Scanner scanner) {
        System.out.println("-- Update Quote --");
        starMatchController.viewQuotes();
        System.out.print("Enter the quote ID to update: ");
        String quoteID = scanner.nextLine();
        System.out.print("New quote text: ");
        String quoteText = scanner.nextLine();

        starMatchController.updateQuote(Integer.valueOf(quoteID), quoteText);
    }

    /**
     * Provides the admin with options for managing other admins.
     * @param scanner The scanner instance to get admin input.
     */
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

    /**
     * Adds a new admin to the system.
     * @param scanner The scanner instance to get admin input.
     */
    private void addNewAdmin(Scanner scanner) {
        System.out.println("-- Add New Admin --");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        try{
            starMatchController.addNewAdmin(name, email, password);
            System.out.println("Admin added.");}
        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes an admin from the system.
     * @param scanner The scanner instance to get admin input.
     */
    private void removeAdmin(Scanner scanner) {
        System.out.println("-- Remove Admin --");
        starMatchController.viewAdmins();
        System.out.print("Admin ID: ");
        String adminID = scanner.nextLine();

        starMatchController.removeAdmin(Integer.valueOf(adminID));
    }

    /**
     * Updates an admins information.
     * @param scanner The scanner instance to get admin input.
     */
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

        try {
            starMatchController.updateAdmin(adminId, name, email, password);
            System.out.println("Admin updated.");
        }
        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Provides the admin with options for managing the traits of the signs.
     * @param scanner The scanner instance to get admin input.
     */
    private void adminManageTraitsMenu(Scanner scanner) {
        boolean adminLoop = true;
        while (adminLoop) {
            System.out.print("""
                    -- Manage Traits used by the app --
                    1. View all traits
                    2. Add new trait
                    3. Delete trait
                    4. Update trait
                    
                    0. Go back to admin menu
                    """);

            String adminOption = scanner.nextLine();

            switch (adminOption) {
                case "0" -> adminLoop = false;
                case "1" -> starMatchController.viewTraits();
                case "2" -> addNewTrait(scanner);
                case "3" -> removeTrait(scanner);
                case "4" -> updateTrait(scanner);
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Adds a new trait based on a certain element.
     * @param scanner The scanner instance to get admin input.
     */
    private void addNewTrait(Scanner scanner) {
        System.out.println("-- Add New Trait --");
        System.out.print("Enter the name of the trait: ");
        String traitName = scanner.nextLine();
        System.out.print("Enter the element (Fire, Earth, Air, Water) that represents it: ");
        String traitElement = scanner.nextLine();
        Element element = null;
        try {
            element = Element.valueOf(traitElement);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid element specified. Trait not created.");
            return;
        }
        starMatchController.addTrait(traitName,element);
    }

    /**
     * Removes a trait from the system.
     * @param scanner The scanner instance to get admin input.
     */
    private void removeTrait(Scanner scanner){
        System.out.println("-- Remove Trait --");
        starMatchController.viewTraits();
        System.out.print("Trait ID to remove: ");
        String traitID = scanner.nextLine();
        starMatchController.removeTrait(Integer.valueOf(traitID));
    }

    /**
     * Updates a trait.
     * @param scanner The scanner instance to get admin input.
     */
    private void updateTrait(Scanner scanner){
        System.out.println("-- Update Existing Trait --");
        starMatchController.viewTraits();
        System.out.print("Trait ID to update: ");
        String traitID = scanner.nextLine();
        System.out.print("New Name (leave blank to keep current): ");
        String traitName = scanner.nextLine();
        System.out.print("New Element (leave blank to keep current): ");
        String traitElement = scanner.nextLine();
        Element element = null;
        if (!traitElement.isBlank()) {
            try {
                element = Element.valueOf(traitElement);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid element specified. Trait not updated.");
                return;
            }
        }
        starMatchController.updateTrait(Integer.valueOf(traitID),traitName,element);
    }

    /**
     * A user views its profile information.
     * @param userEmail The email of the currently logged-in user.
     */
    private void viewUserProfile(String userEmail) {
        User user=starMatchController.viewUserProfile(userEmail);
        System.out.println("""
                User Profile:
                Name:""" + user.getName() + """
                
                Birthdate:""" + user.getBirthDate() +
                """
                
                Birthtime:""" + user.getBirthTime() +
                """
                
                Birthplace:""" + user.getBirthPlace() +
                """
                
                Email:""" + user.getEmail() +
                """
                
                Password:""" + user.getPassword());
    }

    /**
     * A user updates its information.
     * @param scanner The scanner instance to get user input.
     * @param userEmail The email of the currently logged-in user.
     */
    private void updateUserProfile(Scanner scanner, String userEmail){
        System.out.println("-- Update User Profile --");
        User user=starMatchController.viewUserProfile(userEmail);

        System.out.print("New Name (leave blank to keep current): ");
        String name = scanner.nextLine();

        System.out.print("New Email (leave blank to keep current): ");
        String email = scanner.nextLine();

        System.out.print("New Password (leave blank to keep current): ");
        String password = scanner.nextLine();

        System.out.print("New Birth Date (YYYY-MM-DD) (leave blank to keep current): ");
        String birthDateInput = scanner.nextLine();
        LocalDate birthDate = birthDateInput.isBlank() ? null : LocalDate.parse(birthDateInput);

        System.out.print("New Birth Time (HH:MM) (leave blank to keep current): ");
        String birthTimeInput = scanner.nextLine();
        LocalTime birthTime = birthTimeInput.isBlank() ? null : LocalTime.parse(birthTimeInput);

        System.out.print("New Birth Place (leave blank to keep current): ");
        String birthPlace = scanner.nextLine();

        try{
            starMatchController.updateUser(user,name,email,password,birthDate,birthTime,birthPlace);
            System.out.println("User updated");
        }
        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * A user views its Natalchart.
     * @param userEmail The email of the currently logged-in user.
     */
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

    /**
     * A user views a list of the personality traits based on its Natalchart.
     * @param userEmail The email of the currently logged-in user.
     */
    private void viewPersonalityTraits(String userEmail){
        List<String> traits=starMatchController.viewPersonalityTraits(userEmail);
        System.out.println("Your personality traits:");
        traits.forEach(System.out::println);
    }

    /**
     * A user views a personalized quote based on its Natalchart.
     * @param userEmail The email of the currently logged-in user.
     */
    private void viewPersonalizedQuote(String userEmail) {
        System.out.println("Your personalized quote:");
        System.out.println(starMatchController.getPersonalizedQuote(userEmail));
    }

    /**
     * Provides the user with options for managing friends.
     * @param scanner The scanner instance to get user input.
     * @param userEmail The email of the currently logged-in user.
     */
    private void manageFriendsMenu(Scanner scanner, String userEmail) {
        boolean friendLoop=true;
        while (friendLoop) {
            System.out.println("-- Manage Friends Menu --");
            System.out.println("""
                    1. View all users
                    2. Add friend
                    3. View all friends
                    4. Remove friend
                    
                    0.Go back to user dashboard
                    """);
            String friendOption = scanner.nextLine();

            switch (friendOption) {
                case "0" -> friendLoop = false;
                case "1" -> viewAllUsers(userEmail);
                case "2" -> addFriend(scanner,userEmail);
                case "3" -> viewFriends(userEmail);
                case "4" -> removeFriend(scanner,userEmail);
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * A user views all users except for itself.
     * @param userEmail The email of the currently logged-in user.
     */
    private void viewAllUsers(String userEmail) {
        System.out.println("-- All Users --");
        List<User> allUsers = starMatchController.getAllUsersExcept(userEmail);
        allUsers.forEach(user -> System.out.println(user.getName() + " (" + user.getEmail() + ")"));
    }

    /**
     * A user adds a friend to its friend list.
     * @param userEmail The email of the currently logged-in user.
     * @param scanner The scanner instance to get user input.
     */
    public void addFriend(Scanner scanner, String userEmail) {
        viewAllUsers(userEmail);
        System.out.println("Enter the email of the user you want to add as friend:");
        String friendEmail = scanner.nextLine();
        try{
        starMatchController.addFriend(userEmail,friendEmail);
        System.out.println("Friend added!");}
        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * A user views its friend list.
     * @param userEmail The email of the currently logged-in user.
     */
    public void viewFriends(String userEmail) {
        System.out.println("Your friends:");
        List<User> friends = starMatchController.viewFriends(userEmail);
        if(friends.isEmpty())
            System.out.println("No friends found.");
        else
            friends.forEach(friend -> System.out.println(friend.getName() + " (" + friend.getEmail() + ")"));
    }

    /**
     * A user removes another user from its friend list.
     * @param userEmail The email of the currently logged-in user.
     * @param scanner The scanner instance to get user input.
     */
    public void removeFriend(Scanner scanner, String userEmail) {
        viewFriends(userEmail);
        System.out.println("Enter the email of the user you want to remove as friend:");
        String friendEmail = scanner.nextLine();
        try{
        starMatchController.removeFriend(userEmail,friendEmail);
        System.out.println("Friend removed!");}
        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * A user views its compatibility with a certain friend.
     * @param userEmail The email of the currently logged-in user.
     * @param scanner The scanner instance to get user input.
     */
    private void viewCompatibility(Scanner scanner, String userEmail) {
        viewFriends(userEmail);
        System.out.println("Enter the email of the friend that you want to calculate your astrological compatibility with:");
        String friendEmail = scanner.nextLine();
        System.out.println("Your compatibility is:");
        try{
        Compatibility compatibility = starMatchController.getCompatibility(userEmail,friendEmail);
        System.out.println(compatibility.getCompatibilityScore() + "% compatible");}
        catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Main function where the inMemoryRepositories, InFileRepositories are initialized and the application starts.
     */
    public static void main(String[] args) {
        Repository<User> userRepository = createInMemoryUserRepository();
        Repository<Admin> adminRepository = createInMemoryAdminRepository();
        Repository<StarSign> signRepository = createInMemoryStarSignRepository();
        Repository<Quote> quoteRepository = createInMemoryQuoteRepository();
        Repository<Trait> traitRepository = createInMemoryTraitRepository();

        StarMatchService starMatchService = new StarMatchService(userRepository, adminRepository,signRepository, quoteRepository, traitRepository);
        StarMatchController starMatchController = new StarMatchController(starMatchService);
        ConsoleApp consoleApp = new ConsoleApp(starMatchController);
//        consoleApp.start();

        Repository<User> userFileRepo = new InFileRepository<User>("C:\\Users\\Cristina\\IdeaProjects\\StarMatch\\starmatch\\src\\files\\users.txt", User.class);
        Repository<Admin> adminFileRepo = new InFileRepository<Admin>("starmatch/src/files/admins.txt", Admin.class);
        Repository<StarSign> starSignFileRepo = new InFileRepository<StarSign>("starmatch/src/files/starsigns.txt", StarSign.class);
        Repository<Quote> quoteFileRepo = new InFileRepository<Quote>("starmatch/src/files/quotes.txt", Quote.class);
        Repository<Trait> traitFileRepo = new InFileRepository<Trait>("starmatch/src/files/traits.txt", Trait.class);

        StarMatchService starMatchServiceFile = new StarMatchService(userFileRepo, adminFileRepo, starSignFileRepo, quoteFileRepo, traitFileRepo);
        StarMatchController starMatchControllerFile = new StarMatchController(starMatchServiceFile);
        ConsoleApp consoleAppFile = new ConsoleApp(starMatchControllerFile);
        consoleAppFile.start();
    }

    /**
     * User InMemoryRepository
     */
    private static Repository<User> createInMemoryUserRepository() {
        Repository<User> userRepository = new InMemoryRepository<>();
        userRepository.create(new User(1, "Amna", LocalDate.of(2000, 3, 12), LocalTime.of(9,0), "Romania", "amna@gmail.com", "parola"));
        userRepository.create(new User(2, "Florian", LocalDate.of(2007, 7, 24), LocalTime.of(10,0), "Romania", "florinel@gmail.com", "0987"));
        userRepository.create(new User(3, "Briana Gheorghe", LocalDate.of(2004, 1, 3), LocalTime.of(22,12), "USA", "brianaagheorghe@yahoo.com", "bribri"));
        userRepository.create(new User(4, "sore marian", LocalDate.of(1990, 9, 10), LocalTime.of(6,23), "Germania", "soremarian@gmail.com", "sore1"));
        return userRepository;
    }

    /**
     * Admin InMemoryRepository
     */
    private static Repository<Admin> createInMemoryAdminRepository() {
        Repository<Admin> adminRepository = new InMemoryRepository<>();
        adminRepository.create(new Admin(1, "Bogdan Popa", "bogdan.popa@yahoo.com", "1234"));
        adminRepository.create(new Admin(2, "Ioana Popa", "ioana.popa@yahoo.com" , "1234"));
        return adminRepository;
    }

    /**
     * StarSign InMemoryRepository
     */
    private static Repository<StarSign> createInMemoryStarSignRepository() {
        Repository<StarSign> signRepository = new InMemoryRepository<>();
        Repository<Trait> traitRepository = createInMemoryTraitRepository();

        Element fire = Element.Fire;
        Element water = Element.Water;
        Element air = Element.Air;
        Element earth = Element.Earth;
        List<Trait> fireTraits=traitRepository.getAll().stream().filter(trait -> trait.getElement()==Element.Fire).toList();
        List<Trait> waterTraits=traitRepository.getAll().stream().filter(trait -> trait.getElement()==Element.Water).toList();
        List<Trait> airTraits=traitRepository.getAll().stream().filter(trait -> trait.getElement()==Element.Air).toList();
        List<Trait> earthTraits=traitRepository.getAll().stream().filter(trait -> trait.getElement()==Element.Earth).toList();

        signRepository.create(new StarSign("Aries",fire,fireTraits,1));
        signRepository.create(new StarSign("Taurus",earth,earthTraits,2));
        signRepository.create(new StarSign("Gemini",air,airTraits,3));
        signRepository.create(new StarSign("Cancer",water,waterTraits,4));
        signRepository.create(new StarSign("Leo",fire,fireTraits,5));
        signRepository.create(new StarSign("Virgo",earth,earthTraits,6));
        signRepository.create(new StarSign("Libra",air,airTraits,7));
        signRepository.create(new StarSign("Scorpio",water,waterTraits,8));
        signRepository.create(new StarSign("Sagittarius",fire,fireTraits,9));
        signRepository.create(new StarSign("Capricorn",earth,earthTraits,10));
        signRepository.create(new StarSign("Aquarius",air,airTraits,11));
        signRepository.create(new StarSign("Pisces",water,waterTraits,12));
        return signRepository;
    }

    /**
     * Trait InMemoryRepository
     */
    private static Repository<Trait> createInMemoryTraitRepository(){
        Repository<Trait> traitRepository = new InMemoryRepository<>();
        Element fire = Element.Fire;
        Element water = Element.Water;
        Element air = Element.Air;
        Element earth = Element.Earth;

        traitRepository.create(new Trait(fire,"passionate",1));
        traitRepository.create(new Trait(fire,"playful",2));
        traitRepository.create(new Trait(fire,"energized",3));
        traitRepository.create(new Trait(water,"emotional",4));
        traitRepository.create(new Trait(water,"intuitive",5));
        traitRepository.create(new Trait(water,"nurturing",6));
        traitRepository.create(new Trait(air,"adventurous",7));
        traitRepository.create(new Trait(air,"curious",8));
        traitRepository.create(new Trait(air,"sociable",9));
        traitRepository.create(new Trait(earth,"stable",10));
        traitRepository.create(new Trait(earth,"pragmatic",11));
        traitRepository.create(new Trait(earth,"analytic",12));
        return traitRepository;
    }

    /**
     * Quote InMemoryRepository
     */
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
