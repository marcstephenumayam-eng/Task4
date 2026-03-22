class Hardware {
    private int id;
    private String brand;
    private String model;
    private int spec;
    private String type;

    public Hardware(int id, String brand, String model, int spec, String type) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.spec = spec;
        this.type = type;
    }

    public int getSpec() {
        return spec;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String interpretSpec() {
        return spec + ""; // default (will be overridden)
    }
}