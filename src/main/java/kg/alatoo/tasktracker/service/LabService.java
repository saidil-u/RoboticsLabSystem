package kg.alatoo.tasktracker.service;

import kg.alatoo.tasktracker.dao.ExperimentDao;
import kg.alatoo.tasktracker.dao.ResearcherDao;
import kg.alatoo.tasktracker.model.Experiment;
import kg.alatoo.tasktracker.model.Researcher;

import java.sql.Date;
import java.util.List;

public class LabService {
    private final ResearcherDao researcherDao = new ResearcherDao();
    private final ExperimentDao experimentDao = new ExperimentDao();

    public List<Researcher> getAllResearchers() { return researcherDao.findAll(); }
    public void addResearcher(String name, String email, String spec) { researcherDao.create(name, email, spec); }
    public void updateResearcherSpec(int id, String spec) { researcherDao.updateSpecialization(id, spec); }
    public void deleteResearcher(int id) { researcherDao.delete(id); }

    public List<Experiment> getAllExperiments() { return experimentDao.findAll(); }
    public void addExperiment(String title, String status, int priority, Date date, int projectId) {
        experimentDao.create(title, status, priority, date, projectId);
    }
    public void updateExperimentStatus(int id, String status) { experimentDao.updateStatus(id, status); }
    public void deleteExperiment(int id) { experimentDao.delete(id); }
    public void searchExperiments(String keyword) { experimentDao.searchByKeyword(keyword); }
    public void showAnalyticsReport() { experimentDao.printAnalyticsReport(); }
}
