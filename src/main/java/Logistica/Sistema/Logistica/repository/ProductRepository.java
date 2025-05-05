package Logistica.Sistema.Logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Logistica.Sistema.Logistica.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
