/*==============================================================================================
Nombre del archivo: CurrencyConversionController.java
Descripción: Se encarga de transformar la divisa.
--------------------------------------------------------------------------------------------
Autor: Santiago Bellaizan Chaparro
Fecha: 2025-01-16
===============================================================================================*/

package conversion_divisas_prueba.conversion_divisas_prueba.service;


import conversion_divisas_prueba.conversion_divisas_prueba.model.ConversionRequest;
import conversion_divisas_prueba.conversion_divisas_prueba.model.ConversionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class CurrencyConversionService {
    private final RestTemplate restTemplate;
    private static final String API_URL = "http://api.exchangeratesapi.io/v1/latest?access_key=10124780aa73c83cd1e5b667cf8af774";

    public CurrencyConversionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> convertCurrency(ConversionRequest request) {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(API_URL, Map.class);
            Map<String, Object> responseBody = response.getBody();
            if (responseBody == null || !responseBody.containsKey("rates")) {
                return new ResponseEntity<>("Failed to fetch exchange rates", HttpStatus.SERVICE_UNAVAILABLE);
            }

            Map<String, Object> rates = (Map<String, Object>) responseBody.get("rates");

            if (!rates.containsKey(request.getSourceCurrency()) || !rates.containsKey(request.getTargetCurrency())) {
                return new ResponseEntity<>("Invalid currency code", HttpStatus.BAD_REQUEST);
            }

            double sourceRate = convertToDouble(rates.get(request.getSourceCurrency()));
            double targetRate = convertToDouble(rates.get(request.getTargetCurrency()));
            double conversionRate = targetRate / sourceRate;
            double result = request.getAmount() * conversionRate;

            ConversionResponse conversionResponse = new ConversionResponse(
                    (String) responseBody.get("date"),
                    conversionRate,
                    request.getAmount(),
                    result
            );

            return ResponseEntity.ok(conversionResponse);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred during currency conversion: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private double convertToDouble(Object value) {
        if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof String) {
            return Double.parseDouble((String) value);
        }
        throw new IllegalArgumentException("Cannot convert value to Double: " + value);
    }
}