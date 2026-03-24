import java.util.List;
import java.util.Scanner;

public class Main {
    private static HardwareRepository repository = new HardwareRepository();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextId = 1;

    public static void main(String[] args) {
        System.out.println("=== Hardware Management System ===\n");
        
        // Add some sample data
        initializeSampleData();
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addHardware();
                    break;
                case 2:
                    viewAllHardware();
                    break;
                case 3:
                    searchHardware();
                    break;
                case 4:
                    updateHardware();
                    break;
                case 5:
                    deleteHardware();
                    break;
                case 6:
                    filterByType();
                    break;
                case 7:
                    filterByBrand();
                    break;
                case 8:
                    System.out.println("Total items: " + repository.getCount());
                    break;
                case 9:
                    running = false;
                    System.out.println("Thank you for using Hardware Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Add Hardware");
        System.out.println("2. View All Hardware");
        System.out.println("3. Search by Model");
        System.out.println("4. Update Hardware");
        System.out.println("5. Delete Hardware");
        System.out.println("6. Filter by Type");
        System.out.println("7. Filter by Brand");
        System.out.println("8. View Total Count");
        System.out.println("9. Exit");
    }

    private static void initializeSampleData() {
        System.out.println("Adding sample hardware items...");
        repository.addHardware(new Phone(nextId++, "Apple", "iPhone 15", 48));
        repository.addHardware(new Phone(nextId++, "Samsung", "Galaxy S24", 50));
        repository.addHardware(new Laptop(nextId++, "Dell", "XPS 15", 16));
        repository.addHardware(new Laptop(nextId++, "Apple", "MacBook Pro", 32));
        repository.addHardware(new Phone(nextId++, "Google", "Pixel 9", 50));
        System.out.println("Sample data added successfully!\n");
    }

    private static void addHardware() {
        System.out.println("\n--- Add New Hardware ---");
        System.out.println("Select type:");
        System.out.println("1. Phone");
        System.out.println("2. Laptop");
        int typeChoice = getIntInput("Enter choice (1-2): ");
        
        String brand = getStringInput("Enter brand: ");
        String model = getStringInput("Enter model: ");
        int spec = getIntInput("Enter spec value: ");
        
        Hardware hardware;
        if (typeChoice == 1) {
            hardware = new Phone(nextId++, brand, model, spec);
            System.out.println("Phone added successfully!");
        } else if (typeChoice == 2) {
            hardware = new Laptop(nextId++, brand, model, spec);
            System.out.println("Laptop added successfully!");
        } else {
            System.out.println("Invalid type. Hardware not added.");
            return;
        }
        
        repository.addHardware(hardware);
        System.out.println("ID: " + hardware.getId());
    }

    private static void viewAllHardware() {
        System.out.println("\n--- All Hardware Items ---");
        repository.displayAllHardware();
        System.out.println("Total: " + repository.getCount());
    }

    private static void searchHardware() {
        System.out.println("\n--- Search by Model ---");
        String model = getStringInput("Enter model name to search: ");
        List<Hardware> results = repository.searchByModel(model);
        
        if (results.isEmpty()) {
            System.out.println("No hardware found with model containing: " + model);
        } else {
            System.out.println("Found " + results.size() + " item(s):");
            for (Hardware h : results) {
                System.out.println("  ID: " + h.getId() + 
                                 ", Type: " + h.getType() +
                                 ", Brand: " + h.getBrand() + 
                                 ", Model: " + h.getModel() + 
                                 ", Spec: " + h.interpretSpec());
            }
        }
    }

    private static void updateHardware() {
        System.out.println("\n--- Update Hardware ---");
        viewAllHardware();
        
        if (repository.getCount() == 0) {
            System.out.println("No items to update.");
            return;
        }
        
        int id = getIntInput("Enter ID of hardware to update: ");
        Hardware existing = repository.getHardwareById(id);
        
        if (existing == null) {
            System.out.println("Hardware with ID " + id + " not found.");
            return;
        }
        
        System.out.println("Updating: " + existing.getType() + " - " + existing.getBrand() + " " + existing.getModel());
        System.out.println("Leave blank to keep current value.");
        
        String brand = getStringInput("Enter new brand (" + existing.getBrand() + "): ");
        if (brand.isEmpty()) brand = existing.getBrand();
        
        String model = getStringInput("Enter new model (" + existing.getModel() + "): ");
        if (model.isEmpty()) model = existing.getModel();
        
        String specInput = getStringInput("Enter new spec value (" + existing.getSpec() + "): ");
        int spec = existing.getSpec();
        if (!specInput.isEmpty()) {
            spec = Integer.parseInt(specInput);
        }
        
        Hardware updated;
        if (existing.getType().equals("Phone")) {
            updated = new Phone(id, brand, model, spec);
        } else {
            updated = new Laptop(id, brand, model, spec);
        }
        
        if (repository.updateHardware(id, updated)) {
            System.out.println("Hardware updated successfully!");
        } else {
            System.out.println("Update failed.");
        }
    }

    private static void deleteHardware() {
        System.out.println("\n--- Delete Hardware ---");
        viewAllHardware();
        
        if (repository.getCount() == 0) {
            System.out.println("No items to delete.");
            return;
        }
        
        int id = getIntInput("Enter ID of hardware to delete: ");
        Hardware existing = repository.getHardwareById(id);
        
        if (existing == null) {
            System.out.println("Hardware with ID " + id + " not found.");
            return;
        }
        
        System.out.println("Deleting: " + existing.getType() + " - " + existing.getBrand() + " " + existing.getModel());
        String confirm = getStringInput("Are you sure? (y/n): ");
        
        if (confirm.equalsIgnoreCase("y")) {
            if (repository.removeHardware(id)) {
                System.out.println("Hardware deleted successfully!");
            } else {
                System.out.println("Deletion failed.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void filterByType() {
        System.out.println("\n--- Filter by Type ---");
        System.out.println("1. Phone");
        System.out.println("2. Laptop");
        int typeChoice = getIntInput("Select type (1-2): ");
        
        String type = (typeChoice == 1) ? "Phone" : "Laptop";
        List<Hardware> results = repository.getHardwareByType(type);
        
        if (results.isEmpty()) {
            System.out.println("No " + type + " items found.");
        } else {
            System.out.println("Found " + results.size() + " " + type + "(s):");
            for (Hardware h : results) {
                System.out.println("  ID: " + h.getId() + 
                                 ", Brand: " + h.getBrand() + 
                                 ", Model: " + h.getModel() + 
                                 ", Spec: " + h.interpretSpec());
            }
        }
    }

    private static void filterByBrand() {
        System.out.println("\n--- Filter by Brand ---");
        String brand = getStringInput("Enter brand name: ");
        List<Hardware> results = repository.getHardwareByBrand(brand);
        
        if (results.isEmpty()) {
            System.out.println("No hardware found for brand: " + brand);
        } else {
            System.out.println("Found " + results.size() + " item(s) from " + brand + ":");
            for (Hardware h : results) {
                System.out.println("  ID: " + h.getId() + 
                                 ", Type: " + h.getType() +
                                 ", Model: " + h.getModel() + 
                                 ", Spec: " + h.interpretSpec());
            }
        }
    }

    // Helper methods for input handling
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}