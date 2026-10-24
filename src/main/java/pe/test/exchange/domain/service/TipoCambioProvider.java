package pe.test.exchange.domain.service;

import pe.test.exchange.domain.model.Moneda;

import java.math.BigDecimal;

public interface TipoCambioProvider {

    BigDecimal obtenerTipoCambio(Moneda origen, Moneda destino);
}
