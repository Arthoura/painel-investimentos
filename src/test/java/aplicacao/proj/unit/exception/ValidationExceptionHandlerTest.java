package aplicacao.proj.unit.exception;

import aplicacao.proj.exception.DatabaseOperationException;
import aplicacao.proj.exception.ValidationExceptionHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidationExceptionHandlerTest {

    private final ValidationExceptionHandler handler = new ValidationExceptionHandler();

    @Test
    void deveTratarMethodArgumentNotValidException() {
        // Arrange
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "obj");
        bindingResult.addError(new FieldError("obj", "campoTeste", "Mensagem de erro"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deveTratarConstraintViolationException() {
        // Arrange
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);

        Path path = mock(Path.class);
        when(path.toString()).thenReturn("campoTeste");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("Mensagem de erro");

        ConstraintViolationException ex = new ConstraintViolationException(Set.of(violation));

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleConstraintViolationException(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensagem de erro", response.getBody().get("campoTeste"));
    }


    @Test
    void deveTratarMethodArgumentTypeMismatchException() {
        // Arrange
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("data");

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleTypeMismatchException(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("O parâmetro 'data' tem um valor inválido. Formato de data esperado: AAAA-MM-DD.",
                response.getBody().get("data"));
    }

    @Test
    void deveTratarDatabaseOperationException() {

        DatabaseOperationException ex = new DatabaseOperationException("Erro no banco");


        ResponseEntity<Map<String, String>> response = handler.handleDatabaseOperationException(ex);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ocorreu um erro interno durante a operação de banco de dados.",
                response.getBody().get("message"));
    }
}
