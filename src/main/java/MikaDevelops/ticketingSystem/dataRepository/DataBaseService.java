package MikaDevelops.ticketingSystem.dataRepository;

import MikaDevelops.ticketingSystem.incident.Incident;

import java.util.ArrayList;
import java.util.List;

public interface DataBaseService {

    void initializeDataBase();
    Incident getIncidentById(long id);
    List<Incident> getAllIncidents();

    void saveIncident(Incident incident);
    void saveServicePerson();
    void saveCategory();
    void saveStatus();
    void saveCustomer();
    void savePriority();
    void saveSolution();

    void setIncidentStatus(long incidentId, long statusId);
    void setServicePersonToIncident(long incidentId, long personId);
    void setIncidentCategory(long incidentId, long categoryId);
    void setIncidentCustomer(long incidentId, long customerId);
    void setIncidentPriority(long incidentId, long priorityId);

    List<String> getServicePersons(long incidentId);
}
