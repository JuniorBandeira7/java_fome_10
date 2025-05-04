package Logistica.Sistema.Logistica.service;

import java.util.List;

import org.springframework.stereotype.Service;

import Logistica.Sistema.Logistica.model.Product;
import Logistica.Sistema.Logistica.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {return productRepository.findAll();}

    public Product save(Product product) {return productRepository.save(product);}

    public Product update(Product product, Integer id) {
        Product productT = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        productT.setName(product.getName());
        productT.setPrice(product.getPrice());
        productT.setQtd(product.getQtd());
        
        return productRepository.save(productT);
    }

    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
