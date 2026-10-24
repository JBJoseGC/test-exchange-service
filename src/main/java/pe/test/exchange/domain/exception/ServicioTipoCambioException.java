package pe.test.exchange.domain.exception;

public class ServicioTipoCambioException extends RuntimeException {

    public ServicioTipoCambioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
