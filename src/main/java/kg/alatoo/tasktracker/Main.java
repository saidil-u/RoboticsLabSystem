package kg.alatoo.tasktracker;

import kg.alatoo.tasktracker.config.DatabaseConfig;
import kg.alatoo.tasktracker.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Robotics Lab Management System...");
        DatabaseConfig.initDatabase();
        new ConsoleUI().start();
    }
}
