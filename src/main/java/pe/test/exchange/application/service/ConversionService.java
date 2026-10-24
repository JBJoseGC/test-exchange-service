package pe.test.exchange.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.test.exchange.application.dto.ConversionRequest;
import pe.test.exchange.application.dto.ConversionResponse;
import pe.test.exchange.application.mapper.ConversionMapper;
import pe.test.exchange.domain.model.Conversion;
import pe.test.exchange.domain.model.Moneda;
import pe.test.exchange.domain.model.Monto;
import pe.test.exchange.domain.repository.ConversionRepository;
import pe.test.exchange.domain.service.TipoCambioProvider;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ConversionService {

    private final TipoCambioProvider tipoCambioProvider;
    private final ConversionRepository conversionRepository;

    public ConversionResponse convertir(ConversionRequest request) {
        // Validar antes de llamar al servicio externo
        Monto monto = new Monto(request.monto());
        Moneda origen = new Moneda(request.monedaOrigen());
        Moneda destino = new Moneda(request.monedaDestino());

        BigDecimal tipoCambio = tipoCambioProvider.obtenerTipoCambio(origen, destino);
        
        Conversion conversion = conversionRepository.guardar(Conversion.crear(monto, origen, destino, tipoCambio));

        return ConversionMapper.toResponse(conversion);
    }
}
