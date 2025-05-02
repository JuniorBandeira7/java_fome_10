package comida.loja.fome10.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import comida.loja.fome10.model.User;

public interface UserRepository  extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
