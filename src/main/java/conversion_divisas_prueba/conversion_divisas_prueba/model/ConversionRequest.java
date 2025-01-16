package conversion_divisas_prueba.conversion_divisas_prueba.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ConversionRequest {
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private double amount;

    @NotBlank(message = "Source currency code is required")
    private String sourceCurrency;

    @NotBlank(message = "Target currency code is required")
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