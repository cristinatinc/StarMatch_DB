import java.util.Scanner;

public class ConsoleApp {

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.print("""
                    -- StarMatch --
                    Welcome! Please log in:
                    1. Log in as User
                    2. Log in as Admin
                    
                    0. Exit Application
                    """);

            String option = scanner.nextLine();

            switch (option) {
                case "0" -> {
                    System.out.println("Exiting application. Goodbye!");
                    continueLoop = false;
                }
                case "1" -> {
                    userLoginMenu(scanner);
                }
                case "2" -> {
                    adminLoginMenu(scanner);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private void userLoginMenu(Scanner scanner) {
        System.out.println("""
                -- User Log In --
                Please enter your email and password.
                Email:
                Password:
                """);

        // Simulate login success and display user menu
        System.out.println("Login successful! Welcome, User.");
        userMenu(scanner);
    }

    private void adminLoginMenu(Scanner scanner) {
        System.out.println("""
                -- Admin Log In --
                Please enter your email and password.
                Email:
                Password:
                """);

        // Simulate login success and display admin menu
        System.out.println("Login successful! Welcome, Admin.");
        adminMenu(scanner);
    }

    private void userMenu(Scanner scanner) {
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
                case "1" -> System.out.println("Displaying user profile...");
                case "2" -> System.out.println("Displaying natal chart...");
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
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        ConsoleApp consoleApp = new ConsoleApp();
        consoleApp.start();
    }
}
