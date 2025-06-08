package MikaDevelops.ticketingSystem.dataRepository;

import MikaDevelops.ticketingSystem.incident.Incident;
import MikaDevelops.ticketingSystem.incident.ModificationInfo;

import java.sql.Connection;
import java.util.List;

public interface DataBaseService {

    void initializeDataBase();

    /**
     * Meant for testing purposes or for special cases.
     * <p>
     * Remember to close connection after use. Remember also that
     * most likely your case ain't that special.
     *
     * @return Connection to database
     */
    Connection getConnection();

    Incident getIncidentById(long id);
    List<Incident> getAllIncidents();
    ModificationInfo getLatestModificationTime(long id);

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
