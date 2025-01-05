# DSCommerce API Documentation

Welcome to the DSCommerce API documentation. Below you will find detailed information about the available endpoints in the Category management system.

## Endpoints

### Get List of Categories

- **Description**: Retrieves a list of all categories available in the system.
- **URL**: `/categories`
- **Method**: `GET`
- **Success Response**:
  - **Code**: 200 OK
  - **Content**: An array of category objects
    ```json
    [
      {
        "id": 1,
        "name": "Electronics"
      },
      {
        "id": 2,
        "name": "Books"
      }
    ]
    ```
- **Error Response**:
  - **Code**: 500 INTERNAL SERVER ERROR
  - **Content**: `{ "error": "Service unavailable." }`

## How to Use

To use these API endpoints, interact through HTTP GET requests either using tools like cURL, Postman, or through your custom client applications. Ensure your requests adhere to the mentioned specifications.

## Additional Notes

This is a basic overview of the CategoryController in the DSCommerce application. For full functionality interaction, including updates and adding new categories, corresponding endpoints and methods should be integrated following the practices outlined here.

For further assistance, please contact the development team or refer to the full API documentation.

# DSCommerce API

DSCommerce API is a simple e-commerce API allowing users to interact with categories and orders. Below are the detailed API endpoints that facilitate these interactions.

## Endpoints

### Categories

- **Get All Categories**
  - **URL:** `/categories`
  - **Method:** `GET`
  - **Description:** Retrieves a list of all available categories in the system.
  - **Response:** Array of Category objects.
  
### Orders

This section assumes you have endpoints for Orders, but you have not detailed any in your provided information. Thus, I will create a sample based on standard RESTful practices:

- **Create an Order**
  - **URL:** `/orders`
  - **Method:** `POST`
  - **Description:** Submit a new order.
  - **Request Body:** Should include order details such as items, quantities, customer information, etc.
  - **Response:** URI of the newly created order with a success status code, typically 201.

- **Get All Orders**
  - **URL:** `/orders`
  - **Method:** `GET`
  - **Description:** Retrieves a list of all orders.
  - **Response:** Array of Order objects.

- **Get Order by ID**
  - **URL:** `/orders/{id}`
  - **Method:** `GET`
  - **Description:** Retrieves detailed information about a specific order based on Order ID.
  - **Response:** Order object.

## Use

You can use tools like curl, Postman, or any suitable HTTP client to interact with the API. Here is an example using curl to fetch all categories:

```bash
curl -X GET http://localhost:8080/categories
```

Replace `localhost:8080` with the actual host and port where your application is running.

## Development

This API is developed using Spring Boot. Ensure that all dependencies are properly managed via Maven or Gradle, and the application is configured with the necessary database and server settings in the `application.properties` or `application.yml` file.

## Security

(This should detail any authentication/authorization mechanisms in place, like OAuth2, JWT, etc. Since no such details are provided, this section would need to be filled out later.)

Please ensure to replace placeholders with actual data based on your project specifics and add more details wherever applicable for real-world application.
# API Reference Documentação

Esta documentação detalha os endpoints disponíveis em nossa API RESTful. Os exemplos a seguir ilustram como acessar os recursos utilizando métodos HTTP comuns como GET e POST.

## Requisitos

- Java JDK 11 ou superior
- Spring Boot 2.5 ou superior
- Spring Security

## Endpoints da API

### 1. GET Recurso

Este endpoint recupera informações sobre um recurso específico.

- **URL**: `/api/recurso/{id}`
- **Método**: `GET`
- **Permissões Requeridas**: Nenhuma específica
- **Path Variable**: `id` - ID do recurso a ser recuperado.

**Exemplo de Request**:

```
GET /api/recurso/123
```

**Resposta de Sucesso**:

Código: 200 OK

Conteúdo:

```json
{
  "id": 123,
  "nome": "Exemplo",
  "descricao": "Descrição do recurso"
}
```

**Resposta de Erro**:

Código: 404 Not Found

Conteúdo:

```json
{
  "erro": "Recurso não encontrado"
}
```

### 2. POST Criação de Recurso

Este endpoint é utilizado para criar um novo recurso.

- **URL**: `/api/recurso`
- **Método**: `POST`
- **Permissões Requeridas**: `@PreAuthorize("hasAuthority('ADMIN')")`
- **Body do Request** (`application/json`):

```json
{
  "nome": "Novo Recurso",
  "descricao": "Descrição do novo recurso"
}
```

**Exemplo de Request**:

```
POST /api/recurso
Content-Type: application/json

{
  "nome": "Novo Recurso",
  "descricao": "Descrição do novo recurso"
}
```

**Resposta de Sucesso**:

Código: 201 Created

Conteúdo:

```json
{
  "id": 124,
  "nome": "Novo Recurso",
  "descricao": "Descrição do novo recurso",
  "status": "Criado com sucesso"
}
```

**Resposta de Erro**:

Código: 400 Bad Request

Conteúdo:

```json
{
  "erro": "Informação faltando ou inválida no corpo da requisição"
}
```

### Considerações Gerais

- Certifique-se de que todas as requisições ao servidor estão usando HTTPS para segurança.
- Todos os dados devem ser enviados como JSON, a menos que especificado de outra forma.
- Utilize tokens de autenticação conforme necessário, especialmente para endpoints que requerem autorização.

## Conclusão

Este documento visa orientá-lo sobre como interagir com nossa API de maneira eficaz. Para questões não abordadas aqui, entre em contato com nosso suporte técnico.
Based on the provided code snippet for an `OrderController` in a Spring Boot application, below is a simple README document outlining the API endpoints available for managing orders. This README assumes the existence of an `OrderService` with a method `findById` and potential other methods for complete CRUD operations, which are common in an order management system.

---

# Order Management API

## Overview

This API is designed to facilitate the management of orders within our application. It supports various operations that allow users with appropriate roles to view and manipulate order data.

## API Endpoints

Below is a list of available endpoints in the Order Management API along with their HTTP methods, URI paths, required roles, and a brief description.

### Get Order by ID

- **HTTP Method:** GET
- **URI Path:** `/orders/{id}`
- **Required Role:** Admin or Client
- **Description:** Retrieves detailed information about an order based on its unique ID. Accessible by users with 'ROLE_ADMIN' or 'ROLE_CLIENT'.
- **Sample Request:**
  ```bash
  curl -X GET "http://<host>:<port>/orders/1" -H "Authorization: Bearer <your_access_token>"
  ```

### [Include additional endpoints as needed based on the services defined, such as - Create Order, Update Order, Delete Order, List All Orders, etc.]

---

## Security

This API applies strict role-based access control (RBAC) and requires users to be authenticated. Ensure your requests include a valid token acquired through our authentication process.

## Error Handling

The API provides meaningful error messages and status codes to help diagnose issues. For example:

- **404 Not Found:** If an order with the specified ID does not exist.
- **401 Unauthorized:** If the user's credentials are missing or invalid.
- **403 Forbidden:** If the user does not have the required role for the action.

Please ensure to handle these scenarios in your client application.

---

## Getting Started

To begin using this API, ensure you have the appropriate credentials and base URL configured. Use tools like curl, Postman, or any HTTP client of your choice to interact with the API. 

Make sure to replace placeholders such as `<host>`, `<port>`, and `<your_access_token>` with actual values when making requests.

Happy ordering!

---

This simple README document serves to inform developers or external clients about the Order Management API functionalities, how to access them, and how to handle common errors. Adapt and expand based on additional methods in your `OrderService` for a more detailed API guide.
# README - DSCommerce API

Este documento descreve os endpoints básicos disponibilizados pela API DSCommerce, que é responsável pelo gerenciamento de pedidos e produtos.

## Endpoints

### Pedido

- **POST /orders**  
  Cria um pedido novo no sistema. O pedido deve ser fornecido no formato JSON.

  **Requisito de Autorização**: O usuário deve ter a role `ROLE_CLIENT`.
  
  **Exemplo de request**:
  ```json
  {
    "id": 1,
    "date": "2023-03-15T14:30:00Z",
    "status": "PROCESSING",
    "items": [
      {
        "productId": 101,
        "quantity": 2
      }
    ]
  }
  ```
  **Exemplo de response**:
  ```json
  {
    "id": 1,
    "date": "2023-03-15T14:30:00Z",
    "status": "PROCESSING",
    "items": [
      {
        "productId": 101,
        "quantity": 2,
        "price": 10.99
      }
    ],
    "total": 21.98
  }
  ```

### Produto

Atualmente, o conteúdo exato dos endpoints relacionados a produtos não está descrito, mas pode incluir operações típicas como listar todos os produtos, buscar por ID, adicionar um novo produto, entre outros.

## Tecnologias utilizadas

- Spring Boot
- Java
- Hibernate ORM com Spring Data JPA
- Banco de dados relacional (ex. PostgreSQL)

## Instalação e Execução

1. **Clone o repositório**  
   Clone o código da API do seu repositório git.
   ```bash
   git clone URL_DO_REPOSITORIO
   ```

2. **Navegue até a pasta do projeto**
   ```bash
   cd dscommerce
   ```

3. **Execute o projeto**  
   Utilize o Maven para executar o projeto.
   ```bash
   mvn spring-boot:run
   ```

## Mais informações

Para mais detalhes sobre cada endpoint, incluindo opções de parâmetros, formatação de requests e responses, e mensagens de erro, consulte a documentação oficial da API ou entre em contato com o suporte técnico.
# API Documentation

## Overview

This API provides a set of endpoints for managing resources in a generic application. It supports operations to retrieve, delete, and manage data securely with restricted access based on user roles.

## Endpoints

### **Get Data**

- **Description**: Retrieve paginated data from the server.
- **URL**: `/data`
- **Method**: `GET`
- **Auth Required**: Yes (specific roles may be required depending on implementation)
- **Permissions**: `ROLE_USER`, `ROLE_ADMIN`
- **Query Parameters**:
  - **page**: int (optional) - Specify the page number, defaults to first page.
  - **size**: int (optional) - Specify the number of items per page, defaults to 10.
- **Success Response**:
  - **Code**: 200 OK
  - **Content**: JSON containing the paginated data.
- **Error Response**:
  - **Code**: 401 UNAUTHORIZED
  - **Content**: `{ error : "You are not authorized to view this data." }`

### **Delete Data**

- **Description**: Delete a specific data entry by ID.
- **URL**: `/data/{id}`
- **Method**: `DELETE`
- **Auth Required**: Yes
- **Permissions**: `ROLE_ADMIN`
- **Path Parameters**:
  - **id**: int - The ID of the data entry to be deleted.
- **Success Response**:
  - **Code**: 204 NO CONTENT
  - **Content**: None
- **Error Response**:
  - **Code**: 401 UNAUTHORIZED
  - **Content**: `{ error : "You are not authorized to delete this data." }`
  - **Code**: 404 NOT FOUND
  - **Content**: `{ error : "Data not found." }`

## Security

This API uses Spring Security to manage authentication and authorization. Access to different endpoints requires corresponding user roles (`ROLE_USER`, `ROLE_ADMIN`). Credentials must be provided as part of the authorization header.

## Examples

### cURL Requests

**Get Data**:
```bash
curl -X GET 'http://example.com/api/data?page=2&size=5' -H 'Authorization: Bearer YourAccessToken'
```

**Delete Data**:
```bash
curl -X DELETE http://example.com/api/data/123 -H 'Authorization: Bearer YourAccessToken'
```

## Notes

Replace `http://example.com/api/data` with the actual API URL and adjust headers and parameters according to the API specification details. Ensure correct API versioning and endpoint paths in your requests.
# DSCommerce API Endpoints Documentation

Welcome to the DSCommerce API documentation. This documentation provides a brief overview of the available HTTP endpoints for interacting with our product-related operations in the DSCommerce system. Below, you will find a basic guide on how to use each endpoint with descriptions for creating, retrieving, updating, and deleting product information.

## Base URL
The base URL for all endpoints is:
```
https://api.dscommerce.com
```

## Endpoints

### 1. Get a List of Products
- **Endpoint:** `/products`
- **Method:** GET
- **Description:** Retrieves a list of all products available in the system.
- **Query Parameters:** 
  - `page` (optional): The page number for pagination (default is 1).
  - `size` (optional): Number of items per page (default is 10).
- **Example Request:**
  ```
  GET /products?page=2&size=5
  ```

### 2. Get a Specific Product
- **Endpoint:** `/products/{id}`
- **Method:** GET
- **Description:** Retrieves detailed information about a specific product by its ID.
- **URI Parameters:** 
  - `id`: The unique identifier of the product.
- **Example Request:**
  ```
  GET /products/123
  ```

### 3. Create a New Product
- **Endpoint:** `/products`
- **Method:** POST
- **Description:** Creates a new product in the system with the data provided.
- **Body:** JSON object containing the product details.
  - Example:
    ```json
    {
      "name": "New Product",
      "price": 99.99,
      "description": "A description of the new product"
    }
    ```
- **Example Request:**
  ```
  POST /products
  ```

### 4. Update an Existing Product
- **Endpoint:** `/products/{id}`
- **Method:** PUT
- **Description:** Updates the details of an existing product by its ID.
- **URI Parameters:** 
  - `id`: The unique identifier of the product to update.
- **Body:** JSON object containing the updated product details.
  - Example:
    ```json
    {
      "name": "Updated Product",
      "price": 89.99,
      "description": "An updated description of the product"
    }
    ```
- **Example Request:**
  ```
  PUT /products/123
  ```

### 5. Delete a Product
- **Endpoint:** `/products/{id}`
- **Method:** DELETE
- **Description:** Deletes a specific product by its ID from the database.
- **URI Parameters:** 
  - `id`: The unique identifier of the product to delete.
- **Example Request:**
  ```
  DELETE /products/123
  ```

## Usage Notes
- All API responses are in JSON format.
- Ensure that requests to POST and PUT endpoints are accompanied by appropriate headers, e.g., `Content-Type: application/json`.
- Use valid authorization tokens as required for accessing secured endpoints.

This simple documentation should assist developers in integrating with the DSCommerce API, facilitating the management of product listings through automated systems or applications. For detailed coding examples and further integration techniques, refer to our comprehensive developer guide and API reference.
# Product API Documentation

This documentation provides information on the available endpoints in the Product API, allowing users to retrieve information about products stored in our data system. Below is a brief description of each available endpoint along with the appropriate methods and paths to access them.

## API Endpoints

### 1. Get Product by ID

**Description**: Retrieve detailed information about a product by specifying its ID.

- **Method**: `GET`
- **URL**: `/products/{id}`
- **Path Variable**: `id` - The unique identifier of the product.
- **Response**: Returns a JSON object representing the detailed data of a product (`ProductDTO`).
- **Example**: `/products/123`

### 2. List All Products

**Description**: List all products with minimal information.

- **Method**: `GET`
- **URL**: `/products`
- **Query Parameters**:
  - `page` (optional): Integer specifying the page number in the paginated query.
  - `size` (optional): Integer specifying the number of records per page.
  - `sort` (optional): String specifying the sorting criteria in the format: property,(asc|desc).
- **Response**: Returns a paginated list of products, each represented by a compact data structure (`ProductMinDTO`).
- **Example**: `/products?page=0&size=10&sort=name,asc`

## Usage

To utilize this API, make HTTP requests to the provided endpoints according to the documentation above. Each request must adhere to the specified methods and provide necessary parameters if required.

## Additional Information

For further assistance or to report issues related to the API, please contact the API support team or refer to the official API documentation website.

# Produto API

Este documento descreve os endpoints disponíveis na API de Produtos, permitindo operações de CRUD (Criar, Ler, Atualizar, Deletar) sobre produtos.

## Endpoints

### GET /products
- **Descrição:** Lista todos os produtos disponíveis. Você pode pesquisar produtos por nome.
- **Parâmetros:**
  - `name` (opcional): Nome do produto para filtrar na busca.
  - `page`, `size`, `sort` (opcionais): Parâmetros de paginação e ordenação.
- **Segurança:** Sem restrição de acesso.
- **Exemplo de request:**
  ```
  GET /products?name=caneta&page=0&size=10
  ```

### POST /products
- **Descrição:** Insere um novo produto no sistema.
- **Body:**
  - Produto no formato JSON conforme definido pela classe `ProductDTO`.
- **Segurança:** Apenas usuários com a role `ROLE_ADMIN` têm permissão para este endpoint.
- **Exemplo de request:**
  ```json
  POST /products
  Content-Type: application/json

  {
    "name": "Caneta Bic Azul",
    "price": 1.25
  }
  ```

## Segurança
Nossos endpoints utilizam várias técnicas de segurança para garantir que os dados estejam protegidos e que apenas os usuários autorizados possam realizar operações sensíveis. As roles e permissões dos usuários são verificadas antes da execução das operações.

## Formato de Resposta
A menos que indicado de outra forma, todos os dados retornados estão em formato JSON.

### Exemplo de resposta com sucesso:
```json
{
  "id": 10,
  "name": "Caneta Bic Azul",
  "price": 1.25
}
```

### Exemplo de resposta com erro:
```json
{
  "timestamp": "2021-04-23T10:12:31Z",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Detalhe do erro"
}
```

## Mais Informações
Para mais informações sobre os modelos de dados (`ProductDTO`, `ProductMinDTO`) e detalhes sobre outros endpoints ou configurações, favor consultar a documentação completa da API ou entrar em contato com o suporte técnico.

---
Este README é uma descrição simplificada dos endpoints principais da Produto API com detalhes sobre segurança e formato dos dados. Certifique-se de revisar e testar cada chamada com suas próprias configurações de ambiente e dados.
# API Documentation

Below is the basic documentation for the endpoints available in the API. This guide will help frontend developers understand how to interact with the backend effectively.

## Endpoints Overview

### Create Product

- **Method**: POST
- **Endpoint**: `/products`
- **Authorization**: Required (Admin role)
- **Description**: Creates a new product.
- **Request Body**: ProductDTO object.
- **Success Response**: `201 Created`, returns the newly created ProductDTO object with the URI to the created resource in the `Location` header.

### Update Product

- **Method**: PUT
- **Endpoint**: `/products/{id}`
- **Authorization**: Required (Admin role)
- **Description**: Updates an existing product by its ID.
- **Path Variable**: `id` - ID of the product to update.
- **Request Body**: Updated ProductDTO object.
- **Success Response**: `200 OK`, returns the updated ProductDTO object.

### Delete Product

- **Method**: DELETE
- **Endpoint**: `/products/{id}`
- **Authorization**: Required (Admin role)
- **Description**: Deletes a product by its ID.
- **Path Variable**: `id` - ID of the product to delete.
- **Success Response**: `204 No Content`, returns no content indicating the product was successfully deleted.

## Usage Example

### Creating a Product

**Request:**

```http
POST /products
Authorization: Bearer <Your Access Token>
Content-Type: application/json

{
    "name": "Sample Product",
    "price": 19.99,
    "description": "Description of the sample product."
}
```

**Response:**

```http
HTTP/1.1 201 Created
Location: /products/123
Content-Type: application/json

{
    "id": 123,
    "name": "Sample Product",
    "price": 19.99,
    "description": "Description of the sample product."
}
```

### Updating a Product

**Request:**

```http
PUT /products/123
Authorization: Bearer <Your Access Token>
Content-Type: application/json

{
    "name": "Updated Product",
    "price": 29.99,
    "description": "Updated description of the product."
}
```

**Response:**

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 123,
    "name": "Updated Product",
    "price": 29.99,
    "description": "Updated description of the product."
}
```

### Deleting a Product

**Request:**

```http
DELETE /products/123
Authorization: Bearer <Your Access Token>
```

**Response:**

```http
HTTP/1.1 204 No Content
```

## Additional Notes

- All endpoints require authentication. Make sure to obtain a valid token with the necessary permissions to interact with the endpoints.
- Replace `<Your Access Token>` with the actual token received during the authentication process.
- For testing purposes, ensure you have proper roles assigned to access specific endpoints.
- Handle responses and potential errors (like `404 Not Found` or `403 Forbidden`) appropriately in your application.
# API de Comércio Eletrônico (dsCommerce)

Este documento README fornece uma visão geral básica dos endpoints disponíveis no API `UserController` do projeto dsCommerce. O `UserController` é responsável por gerenciar operações relacionadas aos usuários dentro do sistema.

## Endpoints

### 1. Obter Usuário (`GET /users`)

**Descrição:** Este endpoint é usado para recuperar detalhes de todos os usuários cadastrados.

**Permissões:** Apenas acessível por usuários com permissões especiais.

**Endpoint:** `/users`

**Método HTTP:** GET

**Resposta de sucesso:** Código HTTP 204 (No Content)

## Configurações e Dependências

O projeto está configurado com Spring Boot e outras dependências necessárias para operação de segurança e gerenciamento de rotas. A segurança é garantida usando anotações do Spring Security como `@PreAuthorize`.

## Exemplo de Uso

Para utilizar este endpoint após ter o sistema configurado e executando, você pode enviar uma requisição GET para o servidor onde o `dsCommerce` está hospedado, seguido pelo caminho `/users`. A autenticação será necessária para acessar este endpoint.

```bash
curl -X GET "http://localhost:8080/users" -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

**Nota:** Substitua `YOUR_ACCESS_TOKEN` pelo token de acesso do usuário autorizado.

## Conclusão

Este README oferece uma explicação simplificada dos endpoints disponíveis no controle `UserController` do sistema dsCommerce. Para mais detalhes e usos avançados, consulte a documentação detalhada do código ou entre em contato com o administrador do sistema.
### README para o Diretório: handlers

Neste diretório, vamos documentar brevemente os endpoints disponíveis no `UserController` relacionados à gestão de usuários em nossa aplicação. O `UserController` é utilizado para lidar com operações relativas a dados de usuários.

#### Endpoints Disponíveis:

1. **Obter Usuário Atual (`/me`)**:
   - **Method**: GET
   - **URL**: `/users/me`
   - **Descrição**: Este endpoint é utilizado para obter os dados do usuário que está atualmente autenticado. O acesso a este endpoint é restrito a usuários com as roles 'ROLE_ADMIN' ou 'ROLE_CLIENT'.
   - **Permissões Necessárias**: Usuário deve estar autenticado com qualquer uma das roles 'ROLE_ADMIN' ou 'ROLE_CLIENT'.
   - **Resposta Esperada**: Retorna um objeto `UserDTO` contendo os detalhes do usuário autenticado.
   
   Exemplo de resposta:
   ```json
   {
     "id": 1,
     "username": "user1",
     "email": "user1@example.com"
   }
   ```

#### Como Utilizar:

Para acessar estes endpoints, o usuário deve estar autenticado na aplicação. O token de autenticação deve ser incluído no cabeçalho das requisições ao endpoint. 

#### Segurança:

Todos os endpoints neste controlador estão protegidos e exigem autenticação. Certas ações estão limitadas a roles específicas para garantir que usuários tenham apenas acesso a funcionalidades apropriadas.
