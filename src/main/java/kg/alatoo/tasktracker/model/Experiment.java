package kg.alatoo.tasktracker.model;

import java.sql.Date;

public class Experiment {
    private final int id;
    private final String title;
    private final String status;
    private final int priority;
    private final Date experimentDate;
    private final int projectId;

    public Experiment(int id, String title, String status, int priority, Date experimentDate, int projectId) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.experimentDate = experimentDate;
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Title: %-35s | Status: %-10s | Priority: %d | Date: %s | ProjectID: %d",
                id, title, status, priority, experimentDate, projectId);
    }
}
