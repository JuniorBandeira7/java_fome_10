package comida.loja.fome10.repository;

import comida.loja.fome10.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Integer> {
}
