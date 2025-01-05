Este arquivo contém o código de um controlador (Controller) em Java para a entidade de Categoria (Category) em um projeto de e-commerce. O controlador possui a anotação @RestController, indicando que é responsável por lidar com requisições web e retornar respostas HTTP. O controlador possui os seguintes métodos:

1. Método GET para obter uma lista de todas as categorias:
   - Endpoint: /categories
   - Descrição: Retorna uma lista de todas as categorias disponíveis no sistema.
   - Retorna: ResponseEntity<List<CategoryDTO>>

2. Outros métodos e endpoints podem ser adicionados para manipular as categorias, como criar, atualizar ou excluir uma categoria.

Para rodar o projeto e acessar os endpoints, é necessário iniciar um servidor web (como o Tomcat) e acessar o endereço base do projeto, seguido pelo endpoint desejado. Por exemplo, se o projeto estiver rodando em localhost na porta 8080, o endpoint /categories pode ser acessado em http://localhost:8080/categories.

Lembre-se de adicionar documentação detalhada sobre os endpoints, seus parâmetros e respostas esperadas para facilitar o uso por outros desenvolvedores e requisições.
ontroller;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.PostMapping;import org.springframework.web.bind.annotation.RequestBody;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;
import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.services.OrderService;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    
    @Autowired
    private OrderService service;
    
    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        List<OrderDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }
    
    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO dto) {
        dto = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    
}
# API README

This API provides endpoints to perform various operations.
- `/api/users/{id}` - GET request to retrieve user by id.
- `/api/users` - POST request to create a new user.
- `/api/products` - GET request to retrieve all products.

## Authentication
This API requires authentication. You need to include a valid token in the header of your requests.

## Endpoints

### GET User by ID
```
GET /api/users/{id}
```
Retrieve user by id.

### POST Create User
```
POST /api/users
```
Create a new user.

### GET All Products
```
GET /api/products
```
Retrieve all products.

*Note: Make sure to include the necessary parameters in the request body when creating a new user.*

## Authorization
Some endpoints require specific roles to access them. Make sure you have the appropriate permissions before making requests to restricted endpoints. 

## Response Format
All responses will be in JSON format.
## Endpoints

### GET /orders/{id}

- Description: Retrieves an order by its ID.
- Required Role: ROLE_ADMIN or ROLE_CLIENT
- Parameters:
  - id: Long (path variable)
- Returns:
  - OrderDTO object with information about the order
- Example:
  ```json
  {
    "id": 1,
    "total": 50.00,
    "status": "DELIVERED",
    "items": [
      {
        "id": 1,
        "name": "Product A",
        "price": 20.00,
        "quantity": 2
      },
      {
        "id": 2,
        "name": "Product B",
        "price": 10.00,
        "quantity": 1
      }
    ]
  }
  ``` 

Feel free to reach out if you need any further clarifications.
```java
package com.devsuperior.dscommerce.controller;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
```
```plaintext
Este controlador possui dois endpoint: 
1. GET /products/{id} - Retorna as informações de um produto específico com base no ID fornecido.
2. POST /products - Cria um novo produto com base nos dados fornecidos no corpo da requisição. Este endpoint requer a autenticação de um usuário com o papel de 'ROLE_ADMIN'.

Certifique-se de ajustar o código conforme necessário para que ele seja compatível com a sua aplicação e os requisitos específicos do seu projeto.
```
# API de Exemplo

Esta API disponibiliza endpoints para gerenciar uma lista de produtos.

### Endpoints Disponíveis

#### Listar Produtos
```
GET /produtos
```
Este endpoint lista todos os produtos cadastrados.

#### Detalhes do Produto
```
GET /produtos/{id}
```
Este endpoint retorna detalhes de um produto específico, com base no id fornecido.

#### Adicionar Produto
```
POST /produtos
```
Este endpoint permite adicionar um novo produto. Deve ser enviado um JSON com os dados do produto a ser adicionado.

#### Atualizar Produto
```
PUT /produtos/{id}
```
Este endpoint permite atualizar os dados de um produto existente. Deve ser enviado um JSON com os novos dados do produto.

#### Remover Produto
```
DELETE /produtos/{id}
```
Este endpoint permite remover um produto da lista, com base no id fornecido.

### Autenticação
Para acessar os endpoints, é necessário autenticação. Alguns endpoints requerem autorização específica.

### Exemplo de Uso

```
curl -X GET http://localhost:8080/produtos
```

### Documentação da API
A documentação completa da API pode ser encontrada em [https://localhost:8080/swagger-ui.html](https://localhost:8080/swagger-ui.html)
# Endpoint README

## Endpoints

### Product Endpoints
#### GET /products
- List all products

#### GET /products/{id}
- Get product by id

#### POST /products
- Create a new product
- Request Body: ProductDTO

#### PUT /products/{id}
- Update product by id
- Request Body: ProductDTO

#### DELETE /products/{id}
- Delete product by id

### Additional Product Endpoints
#### GET /products/search
- Search products by name or category
- Request Param: keyword

#### GET /products/page
- List paginated products
- Request Param: page, size

#### GET /products/sales
- List products on sale

### Order Endpoints
#### GET /orders
- List all orders

#### GET /orders/{id}
- Get order by id

#### POST /orders
- Create a new order
- Request Body: OrderDTO

#### PUT /orders/{id}
- Update order by id
- Request Body: OrderDTO

#### DELETE /orders/{id}
- Delete order by id

## API Documentation
- Base URL: http://localhost:8080/api
- Swagger UI: http://localhost:8080/api/swagger-ui.html

Feel free to explore and test the endpoints using the provided API documentation.
## Endpoints

### GET /products/{id}
- Descrição: Busca um produto pelo seu ID
- Parâmetros: 
  - `id` (Long) - ID do produto a ser buscado
- Retorna: 
  - Produto encontrado no formato JSON

### GET /products
- Descrição: Lista todos os produtos cadastrados
- Retorna: 
  - Lista de produtos no formato JSON, contendo apenas informações mínimas

#### Obs: Certifique-se de que os endpoints estão corretamente configurados no seu projeto e que a classe `ProductService` está implementada corretamente.
# Endpoints

## GET /products
### Description
Get a list of all products with optional filtering by name and pagination.

### Parameters
- name (String)
- page (Integer)
- size (Integer)

### Example
```
GET /products?name=example&page=1&size=10
```

## POST /products
### Description
Insert a new product. Requires admin role.

### Body
- name (String)
- price (Integer)
- description (String)

### Example
```
POST /products
{
  "name": "Example Product",
  "price": 50,
  "description": "This is an example product."
}
```
# Endpoints

## Criar um novo produto
- Método: POST
- Rota: /api/products
- Permissões necessárias: ROLE_USER
- Descrição: Cria um novo produto com base nos dados fornecidos no corpo da requisição.

## Atualizar um produto existente
- Método: PUT
- Rota: /api/products/{id}
- Permissões necessárias: ROLE_ADMIN
- Descrição: Atualiza um produto existente com base no ID fornecido e nos dados fornecidos no corpo da requisição.

## Deletar um produto
- Método: DELETE
- Rota: /api/products/{id}
- Permissões necessárias: ROLE_ADMIN
- Descrição: Deleta um produto existente com base no ID fornecido.

**Observação:** Para acessar os endpoints que requerem permissões de ROLE_ADMIN, é necessário autenticar-se como um usuário com esse perfil.
# Endpoints

## UserController

- **GET** /users
  - Lista todos os usuários
  - Apenas usuários autenticados podem acessar
  - Necessário ter permissão de administrador
  - Retorna status 204 No Content se não houver usuários cadastrados

- **POST** /users
  - Cria um novo usuário
  - Apenas usuários autenticados podem acessar
  - Necessário ter permissão de administrador

- **PUT** /users/{id}
  - Atualiza um usuário existente
  - Apenas usuários autenticados podem acessar
  - Necessário ter permissão de administrador

- **DELETE** /users/{id}
  - Deleta um usuário existente
  - Apenas usuários autenticados podem acessar
  - Necessário ter permissão de administrador

Lembrando que para acessar os endpoints protegidos, é necessário autenticar e possuir as permissões adequadas.
# Endpoints

## GET /users/me
- Descrição: Retorna os dados do usuário logado
- Permissão: Somente usuários autenticados com papel de admin ou cliente podem acessar
- Retorno: Retorna um objeto UserDTO com os dados do usuário

Exemplo de requisição:
```
GET /users/me
```

Exemplo de retorno:
```
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "role": "CLIENT"
}
```
