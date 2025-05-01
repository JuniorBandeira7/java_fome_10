package comida.loja.fome10.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comida.loja.fome10.model.Product;
import comida.loja.fome10.service.ProductService;



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
}
