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

        long incidentId = 1L;
        Incident incident = dbService.getIncidentById(incidentId);

        assertEquals(incidentId, incident.getIncidentId());

    }
}