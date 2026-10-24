package pe.test.exchange.application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pe.test.exchange.application.dto.ConversionRequest;
import pe.test.exchange.application.dto.ConversionResponse;
import pe.test.exchange.application.service.ConversionService;
import pe.test.exchange.domain.exception.MonedaInvalidaException;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConversionController.class)
class ConversionControllerTest {

    private static final String BODY = """
            {"monto": 100, "monedaOrigen": "USD", "monedaDestino": "PEN"}
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConversionService conversionService;

    @Test
    void convertirRetorna200ConElResultado() throws Exception {
        when(conversionService.convertir(any(ConversionRequest.class))).thenReturn(new ConversionResponse(
                new BigDecimal("100"), new BigDecimal("370.00"), "USD", "PEN", new BigDecimal("3.70")));

        mockMvc.perform(post("/api/conversion").contentType(MediaType.APPLICATION_JSON).content(BODY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.montoConvertido").value(370.00))
                .andExpect(jsonPath("$.tipoCambio").value(3.70));
    }

    @Test
    void monedaInvalidaRetorna400() throws Exception {
        when(conversionService.convertir(any(ConversionRequest.class)))
                .thenThrow(new MonedaInvalidaException("Moneda no soportada: XYZ"));

        mockMvc.perform(post("/api/conversion").contentType(MediaType.APPLICATION_JSON).content(BODY))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value("Moneda no soportada: XYZ"));
    }
}
