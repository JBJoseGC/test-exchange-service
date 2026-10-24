package pe.test.exchange.domain.exception;

public class MonedaInvalidaException extends RuntimeException {

    public MonedaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
