package com.devsuperior.dscommerce.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.tests.ProductFactory;

@ExtendWith(SpringExtension.class)//Nao carrega o contexto da aplicação
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	private long existingId,nonExistingId;
	
	private String productName;
	
	private Product product;
	
	@BeforeEach // antes de rodar qualquer coisa, execute isso....
	void setup()throws Exception {
		existingId =1L;
		nonExistingId = 2L;
		
		productName = "Playstation 5";
		
		product = ProductFactory.createProduct(productName);
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));

	}
	
	
	@Test// retornar id quando o id existir....
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
		Assertions.assertEquals(result.getName(),product.getName());
		
		
	}
	
}
