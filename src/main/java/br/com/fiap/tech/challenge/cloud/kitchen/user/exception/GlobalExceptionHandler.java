package br.com.fiap.tech.challenge.cloud.kitchen.user.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(
            AuthenticationException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                exception.getMessage()
        );

        problemDetail.setTitle("Falha de autenticação");
        problemDetail.setType(URI.create("https://api.cloud-kitchen.com/errors/authentication"));
        addDefaultProperties(problemDetail, request);

        return problemDetail;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(
            ResourceNotFoundException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );

        problemDetail.setTitle("Recurso não encontrado");
        problemDetail.setType(URI.create("https://api.cloud-kitchen.com/errors/resource-not-found"));
        addDefaultProperties(problemDetail, request);

        return problemDetail;
    }

    @ExceptionHandler(ConflictException.class)
    public ProblemDetail handleConflictException(
            ConflictException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                exception.getMessage()
        );

        problemDetail.setTitle("Conflito na requisição");
        problemDetail.setType(URI.create("https://api.cloud-kitchen.com/errors/conflict"));
        addDefaultProperties(problemDetail, request);

        return problemDetail;
    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessException(
            BusinessException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        problemDetail.setTitle("Erro de regra de negócio");
        problemDetail.setType(URI.create("https://api.cloud-kitchen.com/errors/business-rule"));
        addDefaultProperties(problemDetail, request);

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        List<FieldValidationError> fieldErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toFieldValidationError)
                .toList();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Um ou mais campos enviados são inválidos."
        );

        problemDetail.setTitle("Erro de validação");
        problemDetail.setType(URI.create("https://api.cloud-kitchen.com/errors/validation"));
        problemDetail.setProperty("fields", fieldErrors);
        addDefaultProperties(problemDetail, request);

        return problemDetail;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "O parâmetro obrigatório '%s' não foi informado.".formatted(exception.getParameterName())
        );

        problemDetail.setTitle("Parâmetro obrigatório ausente");
        problemDetail.setType(URI.create("https://api.cloud-kitchen.com/errors/missing-parameter"));
        addDefaultProperties(problemDetail, request);

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "O parâmetro '%s' possui um valor inválido.".formatted(exception.getName())
        );

        problemDetail.setTitle("Tipo de parâmetro inválido");
        problemDetail.setType(URI.create("https://api.cloud-kitchen.com/errors/invalid-parameter-type"));
        addDefaultProperties(problemDetail, request);

        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "O corpo da requisição está inválido ou mal formatado."
        );

        problemDetail.setTitle("Corpo da requisição inválido");
        problemDetail.setType(URI.create("https://api.cloud-kitchen.com/errors/malformed-json"));
        addDefaultProperties(problemDetail, request);

        return problemDetail;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.METHOD_NOT_ALLOWED,
                "O método HTTP '%s' não é suportado para este endpoint.".formatted(exception.getMethod())
        );

        problemDetail.setTitle("Método HTTP não permitido");
        problemDetail.setType(URI.create("https://api.cloud-kitchen.com/errors/method-not-allowed"));
        addDefaultProperties(problemDetail, request);

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(
            Exception exception,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno inesperado."
        );

        problemDetail.setTitle("Erro interno do servidor");
        problemDetail.setType(URI.create("https://api.cloud-kitchen.com/errors/internal-server-error"));
        addDefaultProperties(problemDetail, request);

        return problemDetail;
    }

    private FieldValidationError toFieldValidationError(FieldError fieldError) {
        return new FieldValidationError(
                fieldError.getField(),
                fieldError.getDefaultMessage()
        );
    }

    private void addDefaultProperties(ProblemDetail problemDetail, HttpServletRequest request) {
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        problemDetail.setProperty("path", request.getRequestURI());
    }

    private record FieldValidationError(
            String field,
            String message
    ) {
    }
}
