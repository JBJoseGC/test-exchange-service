package pe.test.exchange.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.test.exchange.application.dto.ConversionRequest;
import pe.test.exchange.application.dto.ConversionResponse;
import pe.test.exchange.domain.exception.MonedaInvalidaException;
import pe.test.exchange.domain.model.Conversion;
import pe.test.exchange.domain.model.Moneda;
import pe.test.exchange.domain.repository.ConversionRepository;
import pe.test.exchange.domain.service.TipoCambioProvider;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversionServiceTest {

    @Mock
    private TipoCambioProvider tipoCambioProvider;

    @Mock
    private ConversionRepository conversionRepository;

    @InjectMocks
    private ConversionService conversionService;

    @Test
    void convertirCalculaYGuardaLaConversion() {
        when(tipoCambioProvider.obtenerTipoCambio(new Moneda("USD"), new Moneda("PEN")))
                .thenReturn(new BigDecimal("3.70"));
        when(conversionRepository.guardar(any(Conversion.class))).thenAnswer(inv -> inv.getArgument(0));

        ConversionResponse response = conversionService.convertir(
                new ConversionRequest(new BigDecimal("100"), "USD", "PEN"));

        assertEquals(new BigDecimal("370.00"), response.montoConvertido());
        assertEquals(new BigDecimal("3.70"), response.tipoCambio());
        verify(conversionRepository).guardar(any(Conversion.class));
    }

    @Test
    void monedaNoSoportadaNoGuardaLaConversion() {
        when(tipoCambioProvider.obtenerTipoCambio(any(), any()))
                .thenThrow(new MonedaInvalidaException("Moneda no soportada: XYZ"));
        ConversionRequest request = new ConversionRequest(BigDecimal.TEN, "USD", "XYZ");

        assertThrows(MonedaInvalidaException.class, () -> conversionService.convertir(request));
        verify(conversionRepository, never()).guardar(any());
    }
}
