package conversion_divisas_prueba.conversion_divisas_prueba.controller;

import conversion_divisas_prueba.conversion_divisas_prueba.model.ConversionRequest;
import conversion_divisas_prueba.conversion_divisas_prueba.model.ConversionResponse;
import conversion_divisas_prueba.conversion_divisas_prueba.service.CurrencyConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/conversion")
public class CurrencyConversionController {
    private final CurrencyConversionService conversionService;

    public CurrencyConversionController(CurrencyConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping
    public ResponseEntity<?> convertCurrency(@Valid @RequestBody ConversionRequest request) {
        return conversionService.convertCurrency(request);
    }
}