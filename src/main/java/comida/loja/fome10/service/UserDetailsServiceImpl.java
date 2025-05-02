package comida.loja.fome10.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import comida.loja.fome10.repository.UserRepository;
import comida.loja.fome10.security.UserAuthenticated;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // Email faz o papael de userName
        return userRepository.findByEmail(email)
        .map(UserAuthenticated::new)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
