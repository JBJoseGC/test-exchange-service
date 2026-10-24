package pe.test.exchange.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;


public class Conversion {

    private final Long id;
    private final BigDecimal monto;
    private final String monedaOrigen;
    private final String monedaDestino;
    private final BigDecimal tipoCambio;
    private final BigDecimal montoConvertido;
    private final LocalDateTime fechaOperacion;

    private Conversion(Long id, BigDecimal monto, String monedaOrigen, String monedaDestino,
                       BigDecimal tipoCambio, BigDecimal montoConvertido, LocalDateTime fechaOperacion) {
        this.id = id;
        this.monto = monto;
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.tipoCambio = tipoCambio;
        this.montoConvertido = montoConvertido;
        this.fechaOperacion = fechaOperacion;
    }

    public static Conversion crear(Monto monto, Moneda origen, Moneda destino, BigDecimal tipoCambio) {
        BigDecimal montoConvertido = monto.valor().multiply(tipoCambio).setScale(2, RoundingMode.HALF_UP);
        return new Conversion(null, monto.valor(), origen.codigo(), destino.codigo(),
                tipoCambio, montoConvertido, LocalDateTime.now());
    }

    public static Conversion reconstruir(Long id, BigDecimal monto, String monedaOrigen, String monedaDestino,
                                         BigDecimal tipoCambio, BigDecimal montoConvertido,
                                         LocalDateTime fechaOperacion) {
        return new Conversion(id, monto, monedaOrigen, monedaDestino, tipoCambio, montoConvertido, fechaOperacion);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public BigDecimal getMontoConvertido() {
        return montoConvertido;
    }

    public LocalDateTime getFechaOperacion() {
        return fechaOperacion;
    }
}
