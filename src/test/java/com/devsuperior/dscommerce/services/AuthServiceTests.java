package com.devsuperior.dscommerce.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import com.devsuperior.dscommerce.tests.UserFactory;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(SpringExtension.class)
public class AuthServiceTests {

    @InjectMocks
    private AuthService service; // Injeta a instância real de AuthService no teste
    
    @Mock
    private UserService userService; // Simula o comportamento de UserService para os testes

    private User admin, selfClient, otherClient;

    @BeforeEach // Método executado antes de cada teste para inicializar os objetos necessários
    void setUp() throws Exception {
        admin = UserFactory.createAdminUser();  // Cria um usuário admin
        selfClient = UserFactory.createCustomClientUser(1L, "Bob"); // Cria um cliente "Bob"
        otherClient = UserFactory.createCustomClientUser(2L, "Ana"); // Cria outro cliente "Ana"
    }

    @Test // Testa se a validação não lança exceção quando o admin está logado
    public void validateSelfOrAdminShouldDoNothingWhenAdminLogged() {
        Mockito.when(userService.authenticated()).thenReturn(admin); // Simula a autenticação como admin
        
        Long userId = admin.getId(); // Captura o ID do admin
        
        // Verifica que não deve lançar exceção quando o admin valida a si mesmo
        Assertions.assertDoesNotThrow(() -> {
            service.validateSelfOrAdmin(userId); 
        });
    }

    @Test // Testa se a validação não lança exceção quando o cliente está validando a si mesmo
    public void validateSelfOrAdminShouldDoNothingWhenSelfLogged() {
        Mockito.when(userService.authenticated()).thenReturn(selfClient); // Simula a autenticação como o cliente "Bob"
        
        Long userId = selfClient.getId(); // Captura o ID do cliente "Bob"
        
        // Verifica que não deve lançar exceção quando o cliente valida a si mesmo
        Assertions.assertDoesNotThrow(() -> {
            service.validateSelfOrAdmin(userId);
        });
    }

    @Test // Testa se a validação lança ForbiddenException quando um cliente tenta validar outro cliente
    public void validateSelfOrAdminThrowsForbiddenExceptionWhenClientOtherLogged() {
        Mockito.when(userService.authenticated()).thenReturn(selfClient); // Simula a autenticação como o cliente "Bob"
        
        Long userId = otherClient.getId(); // Captura o ID do outro cliente "Ana"
        
        // Verifica que deve lançar ForbiddenException quando "Bob" tenta validar "Ana"
        Assertions.assertThrows(ForbiddenException.class, () -> {
            service.validateSelfOrAdmin(userId);
        });
    }
}
