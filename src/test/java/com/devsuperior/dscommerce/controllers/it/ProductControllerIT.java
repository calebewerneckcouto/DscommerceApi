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

		Category category = new Category(2L, "Eletro");
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
	public void findAllShouldReturnPageWhenNameParamIsNotEmpty() throws Exception {
	    // Realiza uma requisição GET na rota "/products" com o parâmetro 'name' e verifica se o status é 200 (OK).
	    ResultActions result = mockMvc
	        .perform(get("/products?name={productName}", productName).accept(MediaType.APPLICATION_JSON));

	    // Verifica se o status da resposta é 200 (OK).
	    result.andExpect(status().isOk());

	    // Verifica se o primeiro item no conteúdo retornado tem o ID igual a 3.
	    result.andExpect(jsonPath("$.content[0].id").value(3L));

	    // Verifica se o nome do primeiro item no conteúdo é "Macbook Pro".
	    result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));

	    // Verifica se o preço do primeiro item é 1250.0.
	    result.andExpect(jsonPath("$.content[0].price").value(1250.0));

	    // Verifica se a URL da imagem do primeiro item corresponde ao esperado.
	    result.andExpect(jsonPath("$.content[0].imgUrl").value(
	        "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));
	}

	@Test
	public void insertShouldReturnProductDTOCreatedWhenAdminLogged() throws Exception {
	    // Cria o JSON do produto a partir de um objeto DTO.
	    String jsonBody = objectMapper.writeValueAsString(productDTO);

	    // Realiza uma requisição POST para inserir o produto com autenticação de um administrador.
	    ResultActions result = mockMvc
	        .perform(post("/products").header("Authorization", "Bearer " + adminToken)
	            .content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
	        .andDo(MockMvcResultHandlers.print());

	    // Verifica se o status da resposta é 201 (Created).
	    result.andExpect(status().isCreated());

	    // Verifica os atributos do produto retornado.
	    result.andExpect(jsonPath("$.id").value(26L)); // ID esperado do produto.
	    result.andExpect(jsonPath("$.name").value("Console Playstation 5")); // Nome esperado.
	    result.andExpect(jsonPath("$.description").value("Videogame irado.....")); // Descrição esperada.
	    result.andExpect(jsonPath("$.price").value(3999.90)); // Preço esperado.
	    result.andExpect(jsonPath("$.imgUrl").value("https://cwc3d.net")); // URL da imagem.
	    result.andExpect(jsonPath("$.categories[0].id").value(2L)); // ID da primeira categoria.
	}

	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndInvalidName() throws Exception {
	    // Configura um nome inválido para o produto.
	    product.setName("ab");
	    productDTO = new ProductDTO(product);
	    String jsonBody = objectMapper.writeValueAsString(productDTO);

	    // Realiza uma requisição POST com um nome inválido.
	    ResultActions result = mockMvc
	        .perform(post("/products").header("Authorization", "Bearer " + adminToken)
	            .content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	    
	    // Verifica se o status da resposta é 422 (Unprocessable Entity).
	    result.andExpect(status().isUnprocessableEntity());
	}

	// Os próximos métodos seguem a mesma lógica, ajustando o campo inválido para o teste:

	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndInvalidDescription() throws Exception {
	    // Configura uma descrição inválida.
	    product.setDescription("ab");
	    productDTO = new ProductDTO(product);
	    String jsonBody = objectMapper.writeValueAsString(productDTO);

	    // Realiza a requisição POST.
	    ResultActions result = mockMvc
	        .perform(post("/products").header("Authorization", "Bearer " + adminToken)
	            .content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	    
	    result.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndPriceIsNegative() throws Exception {
	    // Configura um preço negativo.
	    product.setPrice(-5.0);
	    productDTO = new ProductDTO(product);
	    String jsonBody = objectMapper.writeValueAsString(productDTO);

	    ResultActions result = mockMvc
	        .perform(post("/products").header("Authorization", "Bearer " + adminToken)
	            .content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	    
	    result.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndPriceIsZero() throws Exception {
	    // Configura o preço como zero.
	    product.setPrice(0.0);
	    productDTO = new ProductDTO(product);
	    String jsonBody = objectMapper.writeValueAsString(productDTO);

	    ResultActions result = mockMvc
	        .perform(post("/products").header("Authorization", "Bearer " + adminToken)
	            .content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	    
	    result.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndProductHasNotCategory() throws Exception {
	    // Remove todas as categorias do produto.
	    product.getCategories().clear();
	    productDTO = new ProductDTO(product);
	    String jsonBody = objectMapper.writeValueAsString(productDTO);

	    ResultActions result = mockMvc
	        .perform(post("/products").header("Authorization", "Bearer " + adminToken)
	            .content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	    
	    result.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
	    // Tenta inserir um produto com autenticação de cliente (não administrador).
	    String jsonBody = objectMapper.writeValueAsString(productDTO);

	    ResultActions result = mockMvc
	        .perform(post("/products").header("Authorization", "Bearer " + clientToken)
	            .content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	    
	    // Verifica se o status da resposta é 403 (Forbidden).
	    result.andExpect(status().isForbidden());
	}

	@Test
	public void insertShouldReturnUnauthorizenWhenInvalidToken() throws Exception {
	    // Tenta inserir um produto com um token inválido.
	    String jsonBody = objectMapper.writeValueAsString(productDTO);

	    ResultActions result = mockMvc
	        .perform(post("/products").header("Authorization", "Bearer " + invalidToken)
	            .content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
	    
	    // Verifica se o status da resposta é 401 (Unauthorized).
	    result.andExpect(status().isUnauthorized());
	}
}