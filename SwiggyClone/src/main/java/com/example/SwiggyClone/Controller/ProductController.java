package com.example.SwiggyClone.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SwiggyClone.Dto.ProductDTO;
import com.example.SwiggyClone.Model.Product;
import com.example.SwiggyClone.Service.ProductService;



@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService service;

    // Existing
    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(service.addProduct(product));
    }

    // Existing
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestParam String name) {
        return ResponseEntity.ok(service.getProductsByName(name));
    }

    // GET /products/{id}/price — ComparisonService calls this for live price comparison
    @GetMapping("/{id}/price")
    public ResponseEntity<ProductDTO> getProductPrice(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getProductById(id));
    }
	

}
