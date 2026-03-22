class Laptop extends Hardware {

    public Laptop(int id, String brand, String model, int spec) {
        super(id, brand, model, spec, "Laptop");
    }

    @Override
    public String interpretSpec() {
        return getSpec() + "GB RAM";
    }
}