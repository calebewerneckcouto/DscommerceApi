package com.devsuperior.dscommerce.controllers.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc // carregar o contexto da aplicação para teste de integração
@Transactional // declara que todos os testes sejam maneira transactional apos executar teste
				// dar rollback ( resetar)
public class ProductControllerIT {
	@Autowired
	private MockMvc mockMvc;
	
	private String productName;

	@BeforeEach
	void setup() throws Exception {
		
		productName = "Macbook";

	}
	
	@Test
	public void findAllShouldReturnPageWhenNameParamIsEmpty() throws Exception {
		
		ResultActions result = mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());//verifica se retonrar 200 status
		result.andExpect(jsonPath("$.content[0].id").value(1L));//verficar de o id corresponde a 1
		result.andExpect(jsonPath("$.content[0].name").value("The Lord of the Rings"));//verficar de o id corresponde ao nome
		result.andExpect(jsonPath("$.content[0].price").value(90.5));//verficar se o preco corresponde a 90.5
		result.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"));//verficar se a imgUrl corresponde 
	}
	
	
	@Test
	public void findAllShouldReturnPageWhenNameParamIsNotEmpty() throws Exception {
		
		ResultActions result = mockMvc.perform(get("/products?name={productName}",productName).accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());//verifica se retonrar 200 status
		result.andExpect(jsonPath("$.content[0].id").value(3L));//verficar de o id corresponde a 3
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));//verficar de o id corresponde ao nome
		result.andExpect(jsonPath("$.content[0].price").value(1250.0));//verficar se o preco corresponde a 1250
		result.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));//verficar se a imgUrl corresponde 
	}
}
