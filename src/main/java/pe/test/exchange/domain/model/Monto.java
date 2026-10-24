package pe.test.exchange.domain.model;

import pe.test.exchange.domain.exception.MontoInvalidoException;

import java.math.BigDecimal;

// Value Object: monto siempre mayor a cero
public record Monto(BigDecimal valor) {

    public Monto {
        if (valor == null || valor.signum() <= 0) {
            throw new MontoInvalidoException("El monto debe ser mayor a cero");
        }
    }
}
