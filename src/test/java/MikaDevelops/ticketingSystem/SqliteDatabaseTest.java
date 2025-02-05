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
        dbService = new SqliteDatabase("test.db");
    }

    @Test
    void getIncidentById() {

        long incidentId      = 1L;
        long createdDate     = 15115L;
        String subject       = "test subject";
        String description   = "test description";
        String notes         = "test notes";
        String relIncidents = "1,2,3,4";
        long statusId      = 1L;
        long customerId      = 1L;
        long priorityId      = 1L;
        long solutionId      = 1L;

        Incident incident = dbService.getIncidentById(incidentId);

        assertEquals(incidentId, incident.getIncidentId());
        assertEquals(createdDate, incident.getCreated_datetime());
        assertEquals(subject, incident.getSubject());
        assertEquals(description, incident.getDescription());
        assertEquals(notes, incident.getNotes());
        assertEquals(relIncidents, incident.getRelatedIncidentsId());
        assertEquals(statusId, incident.getStatusId());
        assertEquals(customerId, incident.getCustomerId());
        assertEquals(priorityId, incident.getPriorityId());
        assertEquals(solutionId, incident.getSolutionId());
    }
}