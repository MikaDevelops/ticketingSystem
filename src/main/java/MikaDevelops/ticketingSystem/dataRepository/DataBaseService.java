package MikaDevelops.ticketingSystem.dataRepository;

import MikaDevelops.ticketingSystem.incident.Incident;

import java.util.List;

public interface DataBaseService {

    public void initializeDataBase();
    public Incident getIncidentById(long id);
    public List<Incident> getAllIncidents();

    public void saveIncident(Incident incident);
    public void saveServicePerson();
    public void saveCategory();
    public void saveStatus();
    public void saveCustomer();
    public void savePriority();
    public void saveSolution();

    public void setIncidentStatus(long incidentId, long statusId);
    public void setServicePersonToIncident(long incidentId, long personId);
    public void setIncidentCategory(long incidentId, long categoryId);
    public void setIncidentCustomer(long incidentId, long customerId);
    public void setIncidentPriority(long incidentId, long priorityId);
}
