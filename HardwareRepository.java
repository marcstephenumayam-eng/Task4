import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class HardwareRepository {
    private List<Hardware> hardwareList;

    public HardwareRepository() {
        this.hardwareList = new ArrayList<>();
    }

    public void addHardware(Hardware hardware) {
        if (hardware != null) {
            hardwareList.add(hardware);
        }
    }

    public boolean removeHardware(int id) {
        return hardwareList.removeIf(hardware -> hardware != null && hardware.getId() == id);
    }

    public Hardware getHardwareById(int id) {
        return hardwareList.stream()
                .filter(hardware -> hardware.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Hardware> getAllHardware() {
        return new ArrayList<>(hardwareList);
    }

    public List<Hardware> getHardwareByType(String type) {
        return hardwareList.stream()
                .filter(hardware -> hardware != null && hardware.getClass().getSimpleName().equals(type))
                .collect(Collectors.toList());
    }

    public List<Hardware> getHardwareByBrand(String brand) {
        return hardwareList.stream()
                .filter(hardware -> hardware != null && hardware.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    public boolean updateHardware(int id, Hardware updatedHardware) {
        for (int i = 0; i < hardwareList.size(); i++) {
            Hardware current = hardwareList.get(i);
            if (current.getId() == id) {
                hardwareList.set(i, updatedHardware);
                return true;
            }
        }
        return false;
    }

    public int getCount() {
        return hardwareList.size();
    }

    public void clearAll() {
        hardwareList.clear();
    }

    public List<Hardware> searchByModel(String model) {
        return hardwareList.stream()
                .filter(hardware -> hardware != null && 
                        hardware.getModel().toLowerCase().contains(model.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void displayAllHardware() {
        if (hardwareList.isEmpty()) {
            System.out.println("No hardware items found.");
            return;
        }
        
        for (Hardware hardware : hardwareList) {
            System.out.println("ID: " + hardware.getId() + 
                             ", Type: " + hardware.getClass().getSimpleName() +
                             ", Brand: " + hardware.getBrand() + 
                             ", Model: " + hardware.getModel() + 
                             ", Spec: " + hardware.interpretSpec());
        }
    }
}