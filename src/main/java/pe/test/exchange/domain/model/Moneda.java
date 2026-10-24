package pe.test.exchange.domain.model;

import pe.test.exchange.domain.exception.MonedaInvalidaException;

// Value Object: código ISO 4217 de 3 letras
public record Moneda(String codigo) {

    public Moneda {
        if (codigo == null || !codigo.trim().matches("[A-Za-z]{3}")) {
            throw new MonedaInvalidaException("Código de moneda inválido: " + codigo);
        }
        codigo = codigo.trim().toUpperCase();
    }
}
