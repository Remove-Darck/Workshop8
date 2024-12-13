
import  java .io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileReader;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.time.LocalDate;
        import java.util.HashMap;
        import java.util.Map;
        import java.util.Scanner;

public class Notebook {
    private static Map<String, String> notes = new HashMap<>();
    private static Map<String, LocalDate> noteDates = new HashMap<>();
    private  static final String NOTES_FILE = "notes.txt";

    public static void main(String[] args) {
        // Load notes when the application starts
        loadNotesFromFile();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addNote(scanner);
                    break;
                case 2:
                    removeNote(scanner);
                    break;

                case 3:
                    exportNote(scanner);
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting the application...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("Welcome to the Personal Notebook-taking App!");
        System.out.println("Please select an option:");
        System.out.println("1. Add Note");
        System.out.println("2. Remove Note");
        System.out.println("3. Export Note");
        System.out.println("4. Exit");
    }

    private static void addNote(Scanner scanner) {
        System.out.print("Enter note titleName : ");
        String title = scanner.nextLine();

        if (notes.containsKey(title)) {
            System.out.println("Note with the same title already exists. Please choose a different title.");
            return;
        }

        System.out.print("Enter note content: ");
        String content = scanner.nextLine();

        notes.put(title, content);
        noteDates.put(title, LocalDate.now());
        saveNotesToFile(); // Save notes to the file after adding
        System.out.println("Note added successfully.");
    }


    private static void removeNote(Scanner scanner) {
        System.out.print("Enter note titleName to remove: ");
        String title = scanner.nextLine();

        if (notes.containsKey(title)) {
            notes.remove(title);
            noteDates.remove(title);
            saveNotesToFile(); // Save notes to the file after removal
            System.out.println("Note removed successfully.");
        } else {
            System.out.println("Note not found.");
        }
    }

    private static void exportNote(Scanner scanner) {
        System.out.print("Enter note title to export: ");


        if (notes.isEmpty()) {
            System.out.println("No notes found.");
        } else {
            System.out.println("Saved notes:");
            for (Map.Entry<String, String> entry : notes.entrySet()) {
                String title = entry.getKey();
                LocalDate date = noteDates.get(title);
                System.out.println("** " + title + " - " + date);
            }
        }
        String title = scanner.nextLine();


        if (notes.containsKey(title)) {
            String content = notes.get(title);
            LocalDate date = noteDates.get(title);
            try {
                File exportDir = new File("export");
                exportDir.mkdir(); // Create the export directory if it doesn't exist
                File exportFile = new File(exportDir, title + ".txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(exportFile));
                writer.write(content);
                writer.close();
                System.out.println("Note exported successfully to " + exportFile.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error exporting note: " + e.getMessage());
            }
        } else {
            System.out.println("Note not found.");
        }
    }
    private static void saveNotesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOTES_FILE))) {
            for (String title : notes.keySet()) {
                String content = notes.get(title);
                LocalDate date = noteDates.get(title);
                writer.write(title + "|" + content + "|" + date);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving notes: " + e.getMessage());
        }
    }

    private static void loadNotesFromFile() {
        File file = new File(NOTES_FILE);
        if (!file.exists()) {
            return; // If the file doesn't exist, there are no notes to load
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String title = parts[0];
                    String content = parts[1];
                    LocalDate date = LocalDate.parse(parts[2]);
                    notes.put(title, content);
                    noteDates.put(title, date);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading notes: " + e.getMessage());
        }
    }



































}




