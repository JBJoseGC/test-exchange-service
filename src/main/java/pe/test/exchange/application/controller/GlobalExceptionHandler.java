package pe.test.exchange.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.test.exchange.application.dto.ErrorResponse;
import pe.test.exchange.domain.exception.MonedaInvalidaException;
import pe.test.exchange.domain.exception.MontoInvalidoException;
import pe.test.exchange.domain.exception.ServicioTipoCambioException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ MonedaInvalidaException.class, MontoInvalidoException.class })
    public ResponseEntity<ErrorResponse> handleDatosInvalidos(RuntimeException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleCuerpoInvalido(HttpMessageNotReadableException ex) {
        return build(HttpStatus.BAD_REQUEST, "Cuerpo de la solicitud inválido");
    }

    @ExceptionHandler(ServicioTipoCambioException.class)
    public ResponseEntity<ErrorResponse> handleServicioExterno(ServicioTipoCambioException ex) {
        return build(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        log.error("Error no controlado", ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecursoNoEncontrado(NoResourceFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "Recurso no encontrado");
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String mensaje) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), mensaje));
    }
}
