package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.ProductsRepository;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductsRepository productsRepository;
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){

        Product product = productsRepository.findById(id).get();
       return new ProductDTO(product);


    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll(){

       List<Product> result = productsRepository.findAll();
        return result.stream().map(x->new ProductDTO(x)).toList();



    }
}
