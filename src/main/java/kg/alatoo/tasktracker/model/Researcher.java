package kg.alatoo.tasktracker.model;

public class Researcher {
    private final int id;
    private final String fullName;
    private final String email;
    private final String specialization;

    public Researcher(int id, String fullName, String email, String specialization) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %-22s | Email: %-25s | Spec: %s",
                id, fullName, email, specialization);
    }
}
