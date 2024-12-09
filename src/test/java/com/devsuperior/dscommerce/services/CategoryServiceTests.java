package com.devsuperior.dscommerce.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.repositories.CategoryRepository;
import com.devsuperior.dscommerce.tests.CategoryFactory;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {
	
	
	@InjectMocks
	private CategoryService service;

	@Mock
	private CategoryRepository repository;

	private Category category;

	private List<Category> list;
	
	@BeforeEach
	void setup() throws Exception{
		
		category = CategoryFactory.createCategory();
		
		list = new ArrayList<Category>();
		
		list.add(category);
		
		Mockito.when(repository.findAll()).thenReturn(list);
		
		
	}
	
	
	
	@Test//  teste para returnar lista de categoria DTO
	public void findAllShouldReturnListCategoryDTO() {
		
		
		
		List<CategoryDTO> result = service.findAll();
		
		Assertions.assertEquals(result.size(), 1);//ve se o tamanho da lista e igual a 1
		Assertions.assertEquals(result.get(0).getId(),category.getId());//ve se e o mesmo numero id
		Assertions.assertEquals(result.get(0).getName(),category.getName());//ve se e o mesmo nome cadastrado


		
		
	}
	

}
