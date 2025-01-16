/*==============================================================================================
Nombre del archivo: ConversionRequest.java
Descripción: Modelo que representa la solicitud de la conversión.
--------------------------------------------------------------------------------------------
Autor: Santiago Bellaizan Chaparro
Fecha: 2025-01-16
===============================================================================================*/

package conversion_divisas_prueba.conversion_divisas_prueba.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ConversionRequest {
    @Min(value = 0, message = "El monto debe ser mayor o igual a 0")
    private double amount;

    @NotBlank(message = "Se requiere la divisa de origen")
    private String sourceCurrency;

    @NotBlank(message = "Se requiere la divisa de destino")
    private String targetCurrency;

    // Getters and Setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}