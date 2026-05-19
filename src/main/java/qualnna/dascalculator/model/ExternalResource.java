package qualnna.dascalculator.model;

public class ExternalResource {
    private String paymentType;
    private float price;
    private String resourceName;
    private String description;
    private String source;

    public ExternalResource() {
    }

    public ExternalResource(String paymentType, float price, String resourceName, String description, String source) {
        this.paymentType = paymentType;
        this.price = price;
        this.resourceName = resourceName;
        this.description = description;
        this.source = source;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
