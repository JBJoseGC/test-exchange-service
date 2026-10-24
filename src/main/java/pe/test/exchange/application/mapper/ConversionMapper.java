package pe.test.exchange.application.mapper;

import pe.test.exchange.application.dto.ConversionResponse;
import pe.test.exchange.domain.model.Conversion;

public final class ConversionMapper {

    private ConversionMapper() {
    }

    public static ConversionResponse toResponse(Conversion conversion) {
        return new ConversionResponse(
                conversion.getMonto(),
                conversion.getMontoConvertido(),
                conversion.getMonedaOrigen(),
                conversion.getMonedaDestino(),
                conversion.getTipoCambio()
        );
    }
}
