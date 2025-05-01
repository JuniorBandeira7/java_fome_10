package comida.loja.fome10.service;

import java.util.List;

import org.springframework.stereotype.Service;

import comida.loja.fome10.model.Product;
import comida.loja.fome10.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {return productRepository.findAll();}

    public Product save(Product product) {return productRepository.save(product);}

    public void delete(Integer id) {productRepository.deleteById(id);}
}
