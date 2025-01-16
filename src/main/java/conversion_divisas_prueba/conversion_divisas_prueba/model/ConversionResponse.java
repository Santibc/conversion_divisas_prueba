package conversion_divisas_prueba.conversion_divisas_prueba.model;

public class ConversionResponse {
    private String date;
    private double conversionRate;
    private double originalAmount;
    private double convertedAmount;

    public ConversionResponse(String date, double conversionRate, double originalAmount, double convertedAmount) {
        this.date = date;
        this.conversionRate = conversionRate;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
    }

    // Getters
    public String getDate() {
        return date;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }
}