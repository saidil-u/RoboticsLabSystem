package kg.alatoo.tasktracker.ui;

import kg.alatoo.tasktracker.service.LabService;

import java.sql.Date;
import java.util.Scanner;

public class ConsoleUI {
    private final LabService service = new LabService();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n=== ROBOTICS LAB MANAGEMENT SYSTEM ===");
            System.out.println("1. Show All Researchers (Read)");
            System.out.println("2. Add Researcher (Create)");
            System.out.println("3. Update Researcher Specialization (Update)");
            System.out.println("4. Delete Researcher (Delete)");
            System.out.println("5. Show All Experiments (Read)");
            System.out.println("6. Add Experiment (Create)");
            System.out.println("7. Update Experiment Status (Update)");
            System.out.println("8. Delete Experiment (Delete)");
            System.out.println("9. Search Experiments by Project/Title (JOIN Search)");
            System.out.println("10. Lab Project Analytics Report (JOIN + Aggregates)");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            String input = scanner.nextLine().trim();
            try {
                switch (input) {
                    case "1" -> service.getAllResearchers().forEach(System.out::println);
                    case "2" -> {
                        System.out.print("Full Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Specialization: ");
                        String spec = scanner.nextLine();
                        service.addResearcher(name, email, spec);
                    }
                    case "3" -> {
                        System.out.print("Researcher ID: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("New Specialization: ");
                        String spec = scanner.nextLine();
                        service.updateResearcherSpec(id, spec);
                    }
                    case "4" -> {
                        System.out.print("Researcher ID to delete: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        service.deleteResearcher(id);
                    }
                    case "5" -> service.getAllExperiments().forEach(System.out::println);
                    case "6" -> {
                        System.out.print("Experiment Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Status (PLANNED, RUNNING, COMPLETED, FAILED): ");
                        String status = scanner.nextLine().trim().toUpperCase();
                        System.out.print("Priority (1-5): ");
                        int priority = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Date (YYYY-MM-DD): ");
                        Date date = Date.valueOf(scanner.nextLine().trim());
                        System.out.print("Project ID (1 or 2): ");
                        int projectId = Integer.parseInt(scanner.nextLine().trim());
                        service.addExperiment(title, status, priority, date, projectId);
                    }
                    case "7" -> {
                        System.out.print("Experiment ID: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("New Status (PLANNED, RUNNING, COMPLETED, FAILED): ");
                        String status = scanner.nextLine().trim().toUpperCase();
                        service.updateExperimentStatus(id, status);
                    }
                    case "8" -> {
                        System.out.print("Experiment ID to delete: ");
                        int id = Integer.parseInt(scanner.nextLine().trim());
                        service.deleteExperiment(id);
                    }
                    case "9" -> {
                        System.out.print("Enter search keyword: ");
                        String keyword = scanner.nextLine();
                        service.searchExperiments(keyword);
                    }
                    case "10" -> service.showAnalyticsReport();
                    case "0" -> {
                        System.out.println("Exiting Robotics Lab System...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please enter a number from 0 to 10.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input format! Please check numbers or date format (YYYY-MM-DD).");
            }
        }
    }
}
