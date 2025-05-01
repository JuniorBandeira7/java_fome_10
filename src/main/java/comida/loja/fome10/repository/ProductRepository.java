package comida.loja.fome10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import comida.loja.fome10.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
