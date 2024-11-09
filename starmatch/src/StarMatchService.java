import model.Admin;
import model.User;
import repository.Repository;

public class StarMatchService {
    private Repository<User> userRepository;

    private Repository<Admin> adminRepository;

    public StarMatchService(Repository<User> userRepository, Repository<Admin> adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }


}
