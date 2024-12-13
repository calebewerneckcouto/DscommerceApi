package com.devsuperior.dscommerce.controllers.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc // carregar o contexto da aplicação para teste de integração
@Transactional // declara que todos os testes sejam maneira transactional apos executar teste
				// dar rollback ( resetar)
public class ProductControllerIT {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private TokenUtil tokenUtil;
	@Autowired
	private ObjectMapper objectMapper;

	private String clientUsername, clientPassword, adminUsername, adminPassword;

	private String productName, adminToken, clientToken, invalidToken;

	private ProductDTO productDTO;
	private Product product;

	@BeforeEach
	void setup() throws Exception {

		clientUsername = "maria@gmail.com";
		clientPassword = "123456";
		adminUsername = "alex@gmail.com";
		adminPassword = "123456";

		productName = "Macbook";

		adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
		invalidToken = adminToken + "xpto";// Simulates wrong password
		
		product = new Product(null, "Console Playstation 5", "Videogame irado.....", 3999.90, "https://cwc3d.net");

		Category category = new Category(2L,"Eletro");
		product.getCategories().add(category);
		productDTO = new ProductDTO(product);

	}

	@Test
	public void findAllShouldReturnPageWhenNameParamIsEmpty() throws Exception {

		ResultActions result = mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());// verifica se retonrar 200 status
		result.andExpect(jsonPath("$.content[0].id").value(1L));// verficar de o id corresponde a 1
		result.andExpect(jsonPath("$.content[0].name").value("The Lord of the Rings"));// verficar de o id corresponde
																						// ao nome
		result.andExpect(jsonPath("$.content[0].price").value(90.5));// verficar se o preco corresponde a 90.5
		result.andExpect(jsonPath("$.content[0].imgUrl").value(
				"https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"));// verficar
																													// se
																													// a
																													// imgUrl
																													// corresponde
	}

	@Test
	public void findAllShouldReturnPageWhenNameParamIsNotEmpty() throws Exception {

		ResultActions result = mockMvc
				.perform(get("/products?name={productName}", productName).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());// verifica se retonrar 200 status
		result.andExpect(jsonPath("$.content[0].id").value(3L));// verficar de o id corresponde a 3
		result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));// verficar de o id corresponde ao nome
		result.andExpect(jsonPath("$.content[0].price").value(1250.0));// verficar se o preco corresponde a 1250
		result.andExpect(jsonPath("$.content[0].imgUrl").value(
				"https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));// verficar
																													// se
																													// a
																													// imgUrl
																													// corresponde
	}

	@Test
	public void insertShouldReturnProductDTOCreatedWhenAdminLogged() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(productDTO);

		ResultActions result = mockMvc
				.perform(post("/products")
				.header("Authorization", "Bearer " + adminToken)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print());
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").value(26L));
		result.andExpect(jsonPath("$.name").value("Console Playstation 5"));
		result.andExpect(jsonPath("$.description").value("Videogame irado....."));
		result.andExpect(jsonPath("$.price").value(3999.90));
		result.andExpect(jsonPath("$.imgUrl").value("https://cwc3d.net"));
		result.andExpect(jsonPath("$.categories[0].id").value(2L));
		
	}
}
