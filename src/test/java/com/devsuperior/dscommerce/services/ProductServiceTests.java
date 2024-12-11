package com.devsuperior.dscommerce.services;

import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
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
	
	private PageImpl<Product>page;
	
	@BeforeEach // antes de rodar qualquer coisa, execute isso....
	void setup()throws Exception {
		existingId =1L;
		nonExistingId = 2L;
		
		productName = "Playstation 5";
		
		product = ProductFactory.createProduct(productName);
		
		page = new PageImpl<Product>(List.of(product));
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));//retorna optional (exceção caso nao ache id)
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());//retorna vazio pq nao tem nada na lista
		Mockito.when(repository.searchByName(any(), (Pageable)any())).thenReturn(page);//retonar pagina...

	}
	
	
	@Test// retornar id quando o id existir....
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
		Assertions.assertEquals(result.getName(),product.getName());
		
		
	}
	
	
	
	
	@Test// retornar excecao ResourceNotFound quando o id nao existir....
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		
		Assertions.assertThrows(ResourceNotFoundException.class,()->{
			service.findById(nonExistingId);
		});
		
		
		
	}
	
	
	@Test//Deve retornar pagina produto min Dto caso nao seja nulo, seja tamanho 1 (lista) e o nome seja igual o setado
	public void findAllShouldReturnPagedProductMinDTO() {
		
		Pageable pageable = PageRequest.of(0, 12);
		Page<ProductMinDTO> result = service.findAll(productName, pageable);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getSize(), 1);
		Assertions.assertEquals(result.iterator().next().getName(), productName);
		
	}
	
	
}
