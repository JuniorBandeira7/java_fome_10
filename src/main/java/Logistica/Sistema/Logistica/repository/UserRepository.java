package Logistica.Sistema.Logistica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Logistica.Sistema.Logistica.model.User;

public interface UserRepository  extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
