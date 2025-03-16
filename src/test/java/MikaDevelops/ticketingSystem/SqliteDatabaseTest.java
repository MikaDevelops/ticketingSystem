package MikaDevelops.ticketingSystem;

import MikaDevelops.ticketingSystem.dataRepository.DataBaseService;
import MikaDevelops.ticketingSystem.dataRepository.SqliteDatabase;
import MikaDevelops.ticketingSystem.incident.Incident;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqliteDatabaseTest {

    private static DataBaseService dbService;

    @BeforeAll
    static void setUp() {
        dbService = new SqliteDatabase("testing.db");
    }

    @Test
    void getIncidentById() {

        // Expected values
        long incidentId         = 1L;
        long createdDate        = 15115L;
        String subject          = "test subject";
        String description      = "test description";
        String notes            = "test notes";
        String relIncidents     = "1,2,3,4";
        long statusId           = 1L;
        long customerId         = 1L;
        long priorityId         = 1L;
        long solutionId         = 1L;
        String statusName       = "new";
        String solutionDesc     = "Coffee on keyboard dryed using hairdryer.";
        String customerFirstName = "John";
        String customerMiddleName= "Milton";
        String customerLastName  = "Holmes";
        String priorityDesc     = "normal";
        long incidentServicePid = 1L;
        long categoryId         = 1L;
        String incidentServicePersonName = "Patrick Star";
        String categoryName     = "Workstation";

        Incident incident = dbService.getIncidentById(incidentId);

        assertEquals(incidentId, incident.getIncidentId());
        assertEquals(createdDate, incident.getCreatedDatetime());
        assertEquals(subject, incident.getSubject());
        assertEquals(description, incident.getDescription());
        assertEquals(notes, incident.getNotes());
        assertEquals(relIncidents, incident.getRelatedIncidentsId());
        assertEquals(statusId, incident.getStatusId());
        assertEquals(customerId, incident.getCustomerId());
        assertEquals(priorityId, incident.getPriorityId());
        assertEquals(solutionId, incident.getSolutionId());
        assertEquals(statusName, incident.getStatusName());
        assertEquals(solutionDesc, incident.getSolutionDescription());
        assertEquals(customerFirstName, incident.getCustomerFirstName());
        assertEquals(customerMiddleName, incident.getCustomerMiddleName());
        assertEquals(customerLastName, incident.getCustomerLastName());
        assertEquals(priorityDesc, incident.getPriorityDescription());

        assertLinesMatch(servicePersons, incident.getServicePersons());
    }

    @Test
    void shouldRerturnAllIncidents(){

    }
}