package comida.loja.fome10.service;

import comida.loja.fome10.model.User;
import comida.loja.fome10.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {return userRepository.findAll();}

    public User save(User user) {return userRepository.save(user);}

    public void delete(Integer id) {userRepository.deleteById(id);}
}
