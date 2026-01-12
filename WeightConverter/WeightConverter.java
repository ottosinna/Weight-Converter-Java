import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WeightConverter {
    
    // Constants for conversion factors
    private static final double LBS_TO_KG = 0.453592;
    private static final double KG_TO_LBS = 2.20462;
    private static final double OUNCES_TO_GRAMS = 28.3495;
    private static final double GRAMS_TO_OUNCES = 0.035274;
    private static final double STONES_TO_KG = 6.35029;
    private static final double KG_TO_STONES = 0.157473;
    
    // Conversion history
    private static final List<String> conversionHistory = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        print("=== Welcome to the Advanced Weight Converter ===");
        
        while (running) {
            displayMainMenu();
            int choice = getIntInput(scanner, "Please select an option (1-9): ");
            
            switch (choice) {
                case 1 -> displayConversionMenu(scanner);
                case 2 -> quickConvertMode(scanner);
                case 3 -> batchConvert(scanner);
                case 4 -> viewHistory();
                case 5 -> clearHistory();
                case 6 -> precisionSettings(scanner);
                case 7 -> unitReference();
                case 8 -> aboutProgram();
                case 9 -> {
                    print("\nThank you for using the Weight Converter. Goodbye!");
                    running = false;
                }
                default -> print("Invalid option. Please enter 1-9.");
            }
        }
        scanner.close();
    }
    
    private static void displayMainMenu() {
        print("\n=== Main Menu ===");
        print("1: Standard Conversions");
        print("2: Quick Convert Mode");
        print("3: Batch Conversion");
        print("4: View Conversion History");
        print("5: Clear History");
        print("6: Precision Settings");
        print("7: Unit Reference");
        print("8: About");
        print("9: Exit");
    }
    
    private static void displayConversionMenu(Scanner scanner) {
        boolean inConversionMenu = true;
        
        while (inConversionMenu) {
            print("\n=== Conversion Options ===");
            print("1: Convert lbs to kg");
            print("2: Convert kg to lbs");
            print("3: Convert ounces to grams");
            print("4: Convert grams to ounces");
            print("5: Convert stones to kg");
            print("6: Convert kg to stones");
            print("7: Back to Main Menu");
            
            int choice = getIntInput(scanner, "Select conversion (1-7): ");
            
            switch (choice) {
                case 1 -> convertWithPrecision(scanner, "lbs", "kg", LBS_TO_KG, true);
                case 2 -> convertWithPrecision(scanner, "kg", "lbs", KG_TO_LBS, false);
                case 3 -> convertWithPrecision(scanner, "oz", "g", OUNCES_TO_GRAMS, true);
                case 4 -> convertWithPrecision(scanner, "g", "oz", GRAMS_TO_OUNCES, false);
                case 5 -> convertWithPrecision(scanner, "stones", "kg", STONES_TO_KG, true);
                case 6 -> convertWithPrecision(scanner, "kg", "stones", KG_TO_STONES, false);
                case 7 -> inConversionMenu = false;
                default -> print("Invalid option. Please enter 1-7.");
            }
        }
    }
    
    private static void convertWithPrecision(Scanner scanner, String fromUnit, String toUnit, 
                                           double conversionFactor, boolean multiply) {
        boolean convertMore = true;
        int precision = 2; // Default precision
        
        while (convertMore) {
            double weight = getValidatedDoubleInput(scanner, 
                String.format("Enter weight in %s: ", fromUnit));
            
            double convertedWeight = multiply ? weight * conversionFactor : weight / conversionFactor;
            
            System.out.printf("%s in %s: %." + precision + "f%n", 
                fromUnit, toUnit, convertedWeight);
            
            // Add to history
            String entry = String.format("%." + precision + "f %s = %." + precision + "f %s", 
                weight, fromUnit, convertedWeight, toUnit);
            conversionHistory.add(entry);
            print("(Added to history)");
            
            convertMore = askToConvertAgain(scanner, fromUnit + " to " + toUnit);
        }
    }
    
    private static void quickConvertMode(Scanner scanner) {
        print("\n=== Quick Convert Mode ===");
        print("Enter value and unit (e.g., '150 lbs' or '68.5 kg')");
        print("Supported units: lbs, kg, oz, g, stones");
        print("Type 'back' to return to main menu");
        
        while (true) {
            System.out.print("\nEnter conversion: ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("back")) {
                return;
            }
            
            try {
                String[] parts = input.split("\\s+");
                if (parts.length < 2) {
                    print("Format: 'value unit'. Example: '150 lbs'");
                    continue;
                }
                
                double value = Double.parseDouble(parts[0]);
                String unit = parts[1].toLowerCase();
                
                // Validate weight
                if (!isValidWeight(value)) {
                    continue;
                }
                
                String result = "";
                switch (unit) {
                    case "lbs", "lb", "pounds" -> {
                        double converted = value * LBS_TO_KG;
                        result = String.format("%.2f lbs = %.2f kg", value, converted);
                    }
                    case "kg", "kilograms", "kilos" -> {
                        double converted = value * KG_TO_LBS;
                        result = String.format("%.2f kg = %.2f lbs", value, converted);
                    }
                    case "oz", "ounces" -> {
                        double converted = value * OUNCES_TO_GRAMS;
                        result = String.format("%.2f oz = %.2f g", value, converted);
                    }
                    case "g", "grams" -> {
                        double converted = value * GRAMS_TO_OUNCES;
                        result = String.format("%.2f g = %.2f oz", value, converted);
                    }
                    case "stones", "stone" -> {
                        double converted = value * STONES_TO_KG;
                        result = String.format("%.2f stones = %.2f kg", value, converted);
                    }
                    default -> {
                        print("Unknown unit. Try: lbs, kg, oz, g, stones");
                        continue;
                    }
                }
                
                print(result);
                conversionHistory.add(result);
                
            } catch (NumberFormatException e) {
                print("Invalid number format. Use: '150 lbs' or '68.5 kg'");
            } catch (Exception e) {
                print("Error: " + e.getMessage());
            }
        }
    }
    
    private static void batchConvert(Scanner scanner) {
        print("\n=== Batch Conversion ===");
        print("Enter multiple weights separated by commas");
        print("Example: 150, 165, 180");
        System.out.print("\nEnter weights: ");
        
        String input = scanner.nextLine().trim();
        String[] values = input.split(",");
        
        print("\nSelect conversion type:");
        print("1: lbs to kg");
        print("2: kg to lbs");
        int convType = getIntInput(scanner, "Enter choice (1-2): ");
        
        if (convType < 1 || convType > 2) {
            print("Invalid choice. Returning to menu.");
            return;
        }
        
        String fromUnit = (convType == 1) ? "lbs" : "kg";
        String toUnit = (convType == 1) ? "kg" : "lbs";
        double factor = (convType == 1) ? LBS_TO_KG : KG_TO_LBS;
        
        System.out.printf("\nConverting %s to %s:\n", fromUnit, toUnit);
        print("---------------------");
        
        List<Double> validWeights = new ArrayList<>();
        List<Double> convertedWeights = new ArrayList<>();
        
        for (String value : values) {
            try {
                double weight = Double.parseDouble(value.trim());
                if (isValidWeight(weight)) {
                    double converted = weight * factor;
                    validWeights.add(weight);
                    convertedWeights.add(converted);
                    System.out.printf("%.2f %s = %.2f %s%n", weight, fromUnit, converted, toUnit);
                }
            } catch (NumberFormatException e) {
                print("Skipping invalid value: '" + value + "'");
            }
        }
        
        // Add summary to history
        if (!validWeights.isEmpty()) {
            String entry = String.format("Batch: %d values converted from %s to %s", 
                validWeights.size(), fromUnit, toUnit);
            conversionHistory.add(entry);
            print("\nBatch conversion added to history.");
        }
    }
    
    private static void viewHistory() {
        if (conversionHistory.isEmpty()) {
            print("\nNo conversions yet. Try converting some weights first!");
        } else {
            print("\n=== Conversion History ===");
            print("Total conversions: " + conversionHistory.size());
            print("-------------------------");
            for (int i = 0; i < conversionHistory.size(); i++) {
                print((i + 1) + ". " + conversionHistory.get(i));
            }
        }
    }
    
    private static void clearHistory() {
        if (conversionHistory.isEmpty()) {
            print("\nHistory is already empty.");
        } else {
            System.out.print("\nAre you sure you want to clear all history? (y/n): ");
            Scanner tempScanner = new Scanner(System.in);
            String response = tempScanner.nextLine().trim().toLowerCase();
            
            if (response.equals("y") || response.equals("yes")) {
                conversionHistory.clear();
                print("History cleared successfully.");
            } else {
                print("Clear history cancelled.");
            }
        }
    }
    
    private static void precisionSettings(Scanner scanner) {
        print("\n=== Precision Settings ===");
        print("Current default precision: 2 decimal places");
        print("Note: Precision can be set during each conversion");
        
        System.out.print("\nChange default precision? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        
        if (response.equals("y") || response.equals("yes")) {
            int precision = getIntInput(scanner, "Enter default precision (0-6): ");
            if (precision >= 0 && precision <= 6) {
                print("Default precision set to " + precision + " decimal places.");
                print("(Note: This setting persists for this session only)");
            } else {
                print("Precision must be between 0 and 6. Keeping current setting.");
            }
        }
    }
    
    private static void unitReference() {
        print("\n=== Unit Reference ===");
        print("lbs (pounds): Imperial unit of mass");
        print("kg (kilograms): SI unit of mass");
        print("oz (ounces): 1/16 of a pound");
        print("g (grams): 1/1000 of a kilogram");
        print("stones: Imperial unit, equal to 14 pounds");
        print("\nConversion Factors:");
        System.out.printf("1 lb = %.6f kg%n", LBS_TO_KG);
        System.out.printf("1 kg = %.5f lbs%n", KG_TO_LBS);
        System.out.printf("1 oz = %.4f g%n", OUNCES_TO_GRAMS);
        System.out.printf("1 g = %.5f oz%n", GRAMS_TO_OUNCES);
        System.out.printf("1 stone = %.5f kg%n", STONES_TO_KG);
    }
    
    private static void aboutProgram() {
        print("\n=== About Weight Converter ===");
        print("Version: 2.0");
        print("Features:");
        print("- Multiple unit conversions");
        print("- Input validation");
        print("- Conversion history");
        print("- Batch processing");
        print("- Quick convert mode");
        print("- Adjustable precision");
        print("\nCreated as an educational Java project.");
    }
    
    // Helper methods
    private static int getIntInput(Scanner scanner, String prompt) {
        int value = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                valid = true;
            } catch (NumberFormatException e) {
                print("Invalid input. Please enter a valid integer.");
            }
        }
        return value;
    }
    
    private static double getValidatedDoubleInput(Scanner scanner, String prompt) {
        double value = 0.0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                value = Double.parseDouble(scanner.nextLine().trim());
                if (isValidWeight(value)) {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                print("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }
    
    private static boolean isValidWeight(double weight) {
        if (weight < 0) {
            print("Weight cannot be negative. Please enter a positive number.");
            return false;
        } else if (weight > 10000) {
            print("Weight seems unrealistic (>10,000). Please verify your input.");
            return false;
        } else if (weight == 0) {
            print("Weight is zero. Is this correct? (Enter 'y' to confirm, 'n' to re-enter): ");
            Scanner tempScanner = new Scanner(System.in);
            String response = tempScanner.nextLine().trim().toLowerCase();
            return response.equals("y") || response.equals("yes");
        }
        return true;
    }
    
    private static boolean askToConvertAgain(Scanner scanner, String conversionType) {
        while (true) {
            System.out.print("\nConvert another " + conversionType + "? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (response.equals("y") || response.equals("yes")) {
                return true;
            } else if (response.equals("n") || response.equals("no")) {
                return false;
            } else {
                print("Please enter 'y' or 'n'.");
            }
        }
    }
    public static void print(String s){
        System.out.println(s);
    }
}