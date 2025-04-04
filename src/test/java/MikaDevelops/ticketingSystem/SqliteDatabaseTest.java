package MikaDevelops.ticketingSystem;

import MikaDevelops.ticketingSystem.dataRepository.DataBaseService;
import MikaDevelops.ticketingSystem.dataRepository.SqliteDatabase;
import MikaDevelops.ticketingSystem.incident.Incident;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SqliteDatabaseTest {

    private static DataBaseService dbService;

    @BeforeAll
    static void setUp() {
        dbService = new SqliteDatabase("testing.db");
    }

    @BeforeEach
    void testSetup() {

    }

    @AfterEach
    void testTearDown(){

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
        List<String> servicePersons = new ArrayList<String>();
        servicePersons.add("Patrick Star");
        servicePersons.add("Mr. Krabs");

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
        assertInstanceOf(List.class, incident.getIncidentServicePersons());
    }

   @Test
   void shouldReturnServicePersonInformation(){
        String expectedName = "Patrick Star";
        long incidentId = 1L;
        ArrayList<String> receivedNames = (ArrayList<String>) dbService.getServicePersons(incidentId);
        assertEquals(expectedName, receivedNames.get(0));

        String expectedName1 = "Mr. Krabs";
        long incidentId1 = 2L;
        ArrayList<String> receivedNames1 = (ArrayList<String>) dbService.getServicePersons(incidentId1);
        assertEquals(expectedName1, receivedNames1.get(0));

        int patricIndex = receivedNames1.indexOf("Patrick Star");
        int krabsIndex = receivedNames1.indexOf("Mr. Krabs");
        boolean result1 = patricIndex > -1;
        boolean result2 = krabsIndex > -1;
        assertTrue(result1);
        assertTrue(result2);
   }

   @Test
   void shouldReturnEmptyServicePersonListWhenNoneAssigned(){

   }

    @Test
    void shouldRerturnAllIncidents(){

    }
}