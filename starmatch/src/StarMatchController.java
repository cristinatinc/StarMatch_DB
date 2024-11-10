import model.NatalChart;
import model.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class StarMatchController {

    private final StarMatchService starMatchService;

    public StarMatchController(StarMatchService starMatchService) {this.starMatchService=starMatchService;}

    public boolean handleUserLogin(String email, String password) {
        return starMatchService.validateUserLogin(email, password);
    }

    public boolean handleAdminLogin(String email, String password) {
        return starMatchService.validateAdminLogin(email, password);
    }

    public void signUpNewUser(String name, LocalDate birthDate, LocalTime birthTime, String birthPlace, String email, String password) {
        starMatchService.createUser(name, birthDate, birthTime, birthPlace, email, password);
    }

    public void addNewAdmin(String name, String email, String password){
        starMatchService.createAdmin(name, email, password);
    }

    public void viewAdmins(){
        StringBuilder output = new StringBuilder("Admins of the app:\n");
        starMatchService.getAdmins().forEach(course -> output.append(course.toString()).append("\n"));
        System.out.println(output);
    }

    public void viewUsers(){
        StringBuilder output = new StringBuilder("User profile:\n");
        starMatchService.getUsers().forEach(course -> output.append(course.toString()).append("\n"));
        System.out.println(output);
    }

    public NatalChart viewNatalChart(String userEmail){
        User user = starMatchService.getUserByEmail(userEmail); // Implement getUserByEmail in service
        if (user != null) {
            return starMatchService.getNatalChart(user);
        } else {
            System.out.println("User not found.");
            return null;
        }
    }

    public User viewUserProfile(String userEmail){
        User user = starMatchService.getUserByEmail(userEmail);
        return user;
    }

}
