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

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)//Nao carrega o contexto da aplicação
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	private long existingId,nonExistingId;
	
	private String productName;
	
	private Product product;
	
	private ProductDTO dto;
	
	private PageImpl<Product>page;
	
	@BeforeEach // antes de rodar qualquer coisa, execute isso....
	void setup()throws Exception {
		existingId =1L;
		nonExistingId = 2L;
		
		productName = "Playstation 5";
		
		product = ProductFactory.createProduct(productName);
		dto = new ProductDTO(product);
		
		page = new PageImpl<Product>(List.of(product));
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));//retorna optional (exceção caso nao ache id)
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());//retorna vazio pq nao tem nada na lista
		Mockito.when(repository.searchByName(any(), (Pageable)any())).thenReturn(page);//retonar pagina...
		Mockito.when(repository.save(any())).thenReturn(product); // passa qualquer tipo produto salvar e retorna produto salvo
        Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);//caso onde o produto existe....
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);//caso nao exista retorna exceção entity not found exception
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
		
		Assertions.assertNotNull(result); //verfica se nao e nulo
		Assertions.assertEquals(result.getSize(), 1);// verifica se o tamanho da lista... no caso apenas um metodo
		Assertions.assertEquals(result.iterator().next().getName(), productName); //pega nome do produto e compara...
		
	}
	
	@Test// insert deve retornar Product DTO ao salvar...
	public void insertShouldReturnProductDTO() {
		
		ProductDTO result = service.insert(dto);
		
		Assertions.assertNotNull(result);//verifica se nao e nulo
		Assertions.assertEquals(result.getId(), product.getId());//verifica se e o mesmo id...
		
	}
	
	
	@Test// atualize deve retornar Product Dto quando o id existir...
	public void updateShouldReturnProductDTOWhenIdExists() {
		ProductDTO result = service.update(existingId, dto);
		Assertions.assertNotNull(result);//verifica se nao e nulo
		Assertions.assertEquals(result.getId(),existingId);//verifica se o id e igual ao id existente
		Assertions.assertEquals(result.getName(),dto.getName());//verifica se o nome e igual ao nome existente
	}
	
	
	@Test// atualize deve retornar ResourceNotFoundException quando o id nao  existir...
	public void updateShouldReturnResourceNotFoundExceptionWhenDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class,()->{
			service.update(nonExistingId, dto); // passando id que nao existe para dar a exceção
		});
	}
	
	
}
