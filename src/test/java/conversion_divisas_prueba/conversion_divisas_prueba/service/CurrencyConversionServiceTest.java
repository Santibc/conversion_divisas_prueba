/*==============================================================================================
Nombre del archivo: CurrencyConversionServiceTest.java
Descripción: Test de conversión de divisas.
--------------------------------------------------------------------------------------------
Autor: Santiago Bellaizan Chaparro
Fecha: 2025-01-16
===============================================================================================*/

package conversion_divisas_prueba.conversion_divisas_prueba.service;

import conversion_divisas_prueba.conversion_divisas_prueba.model.ConversionRequest;
import conversion_divisas_prueba.conversion_divisas_prueba.model.ConversionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class CurrencyConversionServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private CurrencyConversionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CurrencyConversionService(restTemplate);
    }

    @Test
    void testSuccessfulConversion() {
        // Preparar request
        ConversionRequest request = new ConversionRequest();
        request.setAmount(100.0);
        request.setSourceCurrency("EUR");
        request.setTargetCurrency("USD");

        // Preparar respuesta mock de la API
        Map<String, Object> rates = new HashMap<>();
        rates.put("EUR", 1.0);
        rates.put("USD", 1.1);

        Map<String, Object> apiResponse = new HashMap<>();
        apiResponse.put("date", "2024-01-16");
        apiResponse.put("rates", rates);

        when(restTemplate.getForEntity(anyString(), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));

        // Ejecutar conversión
        ResponseEntity<?> response = service.convertCurrency(request);

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ConversionResponse conversionResponse = (ConversionResponse) response.getBody();
        assertNotNull(conversionResponse);
        assertEquals(100.0, conversionResponse.getOriginalAmount());
        assertEquals(110.0, conversionResponse.getConvertedAmount(), 0.01);
        assertEquals(1.1, conversionResponse.getConversionRate(), 0.01);
    }

    @Test
    void testInvalidCurrencyCode() {
        ConversionRequest request = new ConversionRequest();
        request.setAmount(100.0);
        request.setSourceCurrency("INVALID");
        request.setTargetCurrency("USD");

        Map<String, Object> rates = new HashMap<>();
        rates.put("EUR", 1.0);
        rates.put("USD", 1.1);

        Map<String, Object> apiResponse = new HashMap<>();
        apiResponse.put("rates", rates);

        when(restTemplate.getForEntity(anyString(), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));

        ResponseEntity<?> response = service.convertCurrency(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid currency code", response.getBody());
    }

    @Test
    void testApiError() {
        ConversionRequest request = new ConversionRequest();
        request.setAmount(100.0);
        request.setSourceCurrency("EUR");
        request.setTargetCurrency("USD");

        when(restTemplate.getForEntity(anyString(), eq(Map.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE));

        ResponseEntity<?> response = service.convertCurrency(request);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals("Failed to fetch exchange rates", response.getBody());
    }
}