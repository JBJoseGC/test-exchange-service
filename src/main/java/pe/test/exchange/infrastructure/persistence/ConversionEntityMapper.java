package pe.test.exchange.infrastructure.persistence;

import pe.test.exchange.domain.model.Conversion;

public final class ConversionEntityMapper {

    private ConversionEntityMapper() {
    }

    public static ConversionEntity toEntity(Conversion conversion) {
        ConversionEntity entity = new ConversionEntity();
        entity.setId(conversion.getId());
        entity.setMonto(conversion.getMonto());
        entity.setMonedaOrigen(conversion.getMonedaOrigen());
        entity.setMonedaDestino(conversion.getMonedaDestino());
        entity.setTipoCambio(conversion.getTipoCambio());
        entity.setMontoConvertido(conversion.getMontoConvertido());
        entity.setFechaOperacion(conversion.getFechaOperacion());
        return entity;
    }

    public static Conversion toDomain(ConversionEntity entity) {
        return Conversion.reconstruir(
                entity.getId(),
                entity.getMonto(),
                entity.getMonedaOrigen(),
                entity.getMonedaDestino(),
                entity.getTipoCambio(),
                entity.getMontoConvertido(),
                entity.getFechaOperacion()
        );
    }
}
