package comida.loja.fome10.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import comida.loja.fome10.dto.UserPatchDto;
import comida.loja.fome10.dto.UserTokenDto;
import comida.loja.fome10.model.User;
import comida.loja.fome10.repository.UserRepository;

@Service
public class UserService {

    private final AuthenticationService authenticationService;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    public List<User> getAll() {return userRepository.findAll();}

    public UserTokenDto save(User user) {
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole("USER");
        userRepository.save(user);
    
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getEmail(), rawPassword // usa a senha em texto plano
            )
        );
    
        String jwt = authenticationService.authenticate(authentication);
        return new UserTokenDto(jwt, user);
    }

    public void delete(Integer id) {userRepository.deleteById(id);}

    public User update(Integer id, UserPatchDto userPatchDto){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.setName(userPatchDto.getName());
        user.setEmail(userPatchDto.getEmail());
        String rawPassword = userPatchDto.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));

        return userRepository.save(user);
    }
}
