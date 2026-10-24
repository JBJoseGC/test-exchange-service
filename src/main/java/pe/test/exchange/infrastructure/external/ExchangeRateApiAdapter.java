package pe.test.exchange.infrastructure.external;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pe.test.exchange.domain.exception.MonedaInvalidaException;
import pe.test.exchange.domain.exception.ServicioTipoCambioException;
import pe.test.exchange.domain.model.Moneda;
import pe.test.exchange.domain.service.TipoCambioProvider;
import pe.test.exchange.infrastructure.external.dto.ExchangeRateApiResponse;

import java.math.BigDecimal;

@Slf4j
@Component
public class ExchangeRateApiAdapter implements TipoCambioProvider {

    private static final String RESULTADO_EXITOSO = "success";

    private final ExchangeRateApiClient client;
    private final String apiKey;

    public ExchangeRateApiAdapter(ExchangeRateApiClient client,
                                  @Value("${exchange-rate.api.key}") String apiKey) {
        this.client = client;
        this.apiKey = apiKey;
    }

    @Override
    public BigDecimal obtenerTipoCambio(Moneda origen, Moneda destino) {
        ExchangeRateApiResponse response;
        try {
            response = client.getLatestRates(apiKey, origen.codigo());
        } catch (FeignException.NotFound | FeignException.BadRequest ex) {
            // La API responde 4xx cuando la moneda base no existe
            throw new MonedaInvalidaException("Moneda no soportada: " + origen.codigo());
        } catch (FeignException ex) {
            log.error("Error al consultar el servicio de tipo de cambio", ex);
            throw new ServicioTipoCambioException("Servicio de tipo de cambio no disponible", ex);
        }

        if (response == null || !RESULTADO_EXITOSO.equals(response.result()) || response.conversionRates() == null) {
            throw new ServicioTipoCambioException("Respuesta inválida del servicio de tipo de cambio", null);
        }

        BigDecimal tipoCambio = response.conversionRates().get(destino.codigo());
        if (tipoCambio == null) {
            throw new MonedaInvalidaException("Moneda no soportada: " + destino.codigo());
        }
        return tipoCambio;
    }
}
