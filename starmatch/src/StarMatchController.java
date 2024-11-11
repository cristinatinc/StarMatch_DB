import model.Element;
import model.NatalChart;
import model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        System.out.println("Admin added successfully!");
    }

    public void removeAdmin(Integer adminID){
        starMatchService.removeAdmin(adminID);
        System.out.println("Removed admin with ID " + adminID);
    }

    public void updateAdmin(Integer adminID, String name, String email, String password){
        starMatchService.updateAdmin(adminID, name, email, password);
        System.out.println("Admin updated successfully!");
    }

    public void viewAdmins(){
        StringBuilder output = new StringBuilder("Admins of the app:\n");
        starMatchService.getAdmins().forEach(admin -> output.append(admin.toString()).append("\n"));
        System.out.println(output);
    }

    public void viewQuotes(){
        StringBuilder output = new StringBuilder("Quotes:\n");
        starMatchService.getQuotes().forEach(quote -> output.append(quote.toString()).append("\n"));
        System.out.println(output);
    }

    public void addNewQuote(String newQuote, String element){
        starMatchService.createQuote(newQuote, element);
    }

    public void removeQuote(Integer quoteID){
        starMatchService.removeQuote(quoteID);
        System.out.println("Quote removed successfully!");
    }

    public void updateQuote(Integer quoteID, String quoteText){
        starMatchService.updateQuote(quoteID, quoteText);
        System.out.println("Quote updated successfully!");
    }

    public void viewTraits(){
        StringBuilder output = new StringBuilder("Traits:\n");
        starMatchService.getTraits().forEach(trait -> output.append(trait.toString()).append("\n"));
        System.out.println(output);
    }

    public void addTrait(String traitName, Element element){
        starMatchService.createTrait(traitName,element);
    }

    public void removeTrait(Integer traitID){
        starMatchService.removeTrait(traitID);
    }

    public void updateTrait(Integer traitID, String traitName, Element element){
        starMatchService.updateTrait(traitID,traitName,element);
    }

    public void viewUsers(){
        StringBuilder output = new StringBuilder("User profile:\n");
        starMatchService.getUsers().forEach(user -> output.append(user.toString()).append("\n"));
        System.out.println(output);
    }

    public void removeUser(Integer userID){
        starMatchService.removeUser(userID);
        System.out.println("Removed user with ID " + userID);
    }

    public NatalChart viewNatalChart(String userEmail){
        User user = starMatchService.getUserByEmail(userEmail);
        if (user != null) {
            return starMatchService.getNatalChart(user);
        } else {
            System.out.println("User not found."); //o sa fie o exceptie aici dar nu o pun acum
            return null;
        }
    }

    public User viewUserProfile(String userEmail){
        return starMatchService.getUserByEmail(userEmail);
    }

    public List<String> viewPersonalityTraits(String userEmail){
        User user = starMatchService.getUserByEmail(userEmail);
        return starMatchService.getPersonalityTraits(user);
    }

    public String getPersonalizedQuote(String userEmail){
        User user = starMatchService.getUserByEmail(userEmail);
        return starMatchService.getPersonalizedQuote(user);
    }

    public void updateUser(User user, String name, String email, String password, LocalDate birthDate, LocalTime birthTime, String birthPlace){
        User user1=starMatchService.getUserByEmail(user.getEmail());
        starMatchService.updateUser(user1,name,email,password,birthDate,birthTime,birthPlace);
    }

    public List<User> getAllUsersExcept(String userEmail){
        User currentUser = starMatchService.getUserByEmail(userEmail);
        return starMatchService.getAllUsersExcept(currentUser);
    }

    public void addFriend(String userEmail, String friendEmail){
        User user = starMatchService.getUserByEmail(userEmail);
        starMatchService.addFriend(user,friendEmail);
    }

    public List<User> viewFriends(String userEmail){
        User user = starMatchService.getUserByEmail(userEmail);
        return starMatchService.getFriends(user);
    }

    public void removeFriend(String userEmail, String friendEmail){
        User user = starMatchService.getUserByEmail(userEmail);
        starMatchService.removeFriend(user,friendEmail);
    }

}
