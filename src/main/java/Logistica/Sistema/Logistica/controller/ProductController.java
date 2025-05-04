package Logistica.Sistema.Logistica.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import Logistica.Sistema.Logistica.model.Product;
import Logistica.Sistema.Logistica.service.ProductService;



@RestController
@RequestMapping("/produto")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll() {return productService.getAll();}

    @PostMapping
    public Product create(@RequestBody Product product) {return productService.save(product);}

    @PostMapping("/{id}")
    public Product update(@RequestBody Product product, @PathVariable Integer id) {return productService.update(product, id);}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {productService.delete(id);}
}
