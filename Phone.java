class Phone extends Hardware {

    public Phone(int id, String brand, String model, int spec) {
        super(id, brand, model, spec, "Phone");
    }

    @Override
    public String interpretSpec() {
        return getSpec() + " Megapixels";
    }
}