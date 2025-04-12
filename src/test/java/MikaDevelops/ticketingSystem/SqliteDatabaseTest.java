package MikaDevelops.ticketingSystem;

import MikaDevelops.ticketingSystem.dataRepository.DataBaseService;
import MikaDevelops.ticketingSystem.dataRepository.SqliteDatabase;
import MikaDevelops.ticketingSystem.incident.Incident;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SqliteDatabaseTest {

    private static DataBaseService dbService;
    private static String testName;
    private Incident[] expectedIncidents = this.expectedIncidents();

    @BeforeEach
    void testSetup() {
        dbService = new SqliteDatabase("testing.db");
        Connection connection = dbService.getConnection();
        this.insertTestCases(connection);
    }

    @AfterEach
    void testTearDown(){

        String timestamp = this.timestamp();

        File file = new File("data/testing.db");
        
        if(file.renameTo(new File("data/"+ testName + timestamp + ".db"))){
            System.out.println("db file renamed");
        }else{
            System.out.println("renaming db file not succeeded");
        }

        dbService = null;
    }

    @Test
    void getIncidentById() {
        testName = "getIncidentById";

        long incidentId = this.expectedIncidents[0].getIncidentId();

        Incident incident = dbService.getIncidentById(incidentId);

        assertEquals(this.expectedIncidents[0].getIncidentId(), incident.getIncidentId());
        assertEquals(this.expectedIncidents[0].getCreatedDatetime(), incident.getCreatedDatetime());
        assertEquals(this.expectedIncidents[0].getSubject(), incident.getSubject());
        assertEquals(this.expectedIncidents[0].getDescription(), incident.getDescription());
        assertEquals(this.expectedIncidents[0].getNotes(), incident.getNotes());
        assertEquals(this.expectedIncidents[0].getRelatedIncidentsId(), incident.getRelatedIncidentsId());
        assertEquals(this.expectedIncidents[0].getStatusId(), incident.getStatusId());
        assertEquals(this.expectedIncidents[0].getCustomerId(), incident.getCustomerId());
        assertEquals(this.expectedIncidents[0].getPriorityId(), incident.getPriorityId());
        assertEquals(this.expectedIncidents[0].getSolutionId(), incident.getSolutionId());
        assertEquals(this.expectedIncidents[0].getStatusName(), incident.getStatusName());
        assertEquals(this.expectedIncidents[0].getSolutionDescription(), incident.getSolutionDescription());
        assertEquals(this.expectedIncidents[0].getCustomerFirstName(), incident.getCustomerFirstName());
        assertEquals(this.expectedIncidents[0].getCustomerMiddleName(), incident.getCustomerMiddleName());
        assertEquals(this.expectedIncidents[0].getCustomerLastName(), incident.getCustomerLastName());
        assertEquals(this.expectedIncidents[0].getPriorityDescription(), incident.getPriorityDescription());
        assertInstanceOf(List.class, incident.getIncidentServicePersons());
        assertArrayEquals(this.expectedIncidents[0].getIncidentServicePersons().toArray(), incident.getIncidentServicePersons().toArray());
    }

   @Test
   void shouldReturnServicePersonInformation(){
       testName = "shouldReturnServicePersonInformation";

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
       testName = "shouldReturnEmptyServicePersonListWhenNoneAssigned";
   }

    @Test
    void shouldHaveSingleCategoryName(){
        testName = "shouldHaveSingleCategoryName";

        Incident[] expectedIncidents = this.expectedIncidents();
        Incident expectedIncident = expectedIncidents[0];

        Incident resultIncident = dbService.getIncidentById(1L);

        assertArrayEquals(expectedIncident.getCategoryNames().toArray(), resultIncident.getCategoryNames().toArray());
    }

    @Test
    void shouldHaveTwoCategoryNames(){
        testName = "shouldHaveTwoCategoryNames";

        Incident[] expectedIncidents = this.expectedIncidents();
        Incident expectedIncident = expectedIncidents[1];

        Incident resultIncident = dbService.getIncidentById(2L);
        ArrayList<String> resultCategories = (ArrayList<String>) resultIncident.getCategoryNames();

        assertEquals(2, resultCategories.size());
        assertArrayEquals(expectedIncident.getCategoryNames().toArray(), resultIncident.getCategoryNames().toArray());
    }

    @Disabled
    @Test
    void shouldReturnAllIncidents(){
        testName = "shouldReturnAllIncidents";
    }

    void insertTestCases(Connection connection){
        String[] inserts = {
                "INSERT INTO status(name) values ('new'),('under work'),('waiting'),('closed');",
                "INSERT INTO customer(first_name, middle_name, last_name) values ('John', 'Milton','Holmes'),('Michelle','Eleanore','Pfiifferi'),('Spongebob','','Squarepants');",
                "INSERT INTO priority(description) values ('normal'),('high'),('low');",
                "INSERT INTO service_person(name) values ('Patrick Star'),('Mr. Krabs'),('Squidward Tentacles');",
                "INSERT INTO category(name) values ('Workstation'),('Server'),('Deep fryer'),('Plankton');",
                "INSERT INTO solution(description) values ('Coffee on keyboard dryed using hairdryer.'),('Powercord plugged to wall outlet'),('Year old fries removed.'),('Used magic.');",
                "INSERT INTO incident(created_datetime, subject, description, notes, related_incidents, status_id, customer_id, priority_id, solution_id) values"
                    +"(15120, 'test subject', 'test description', 'test notes', '1,2,3,4',1,1,1,1),"
                    +"(15121, 'test subject442', 'test description442', 'test note33s2', '2,3',2,2,2,2),"
                    +"(15122, 'test subject443', 'test description443', 'test not33es3', '3',3,2,3,3),"
                    +"(15123, 'test subject444', 'test description444', 'test not33es4', '1',2,1,3,4);",
                "INSERT INTO incident_service_person(person_id, incident_id) VALUES (1,1), (2,2), (1,2);",
                "INSERT INTO incident_category(category_id, incident_id) VALUES (1,1), (2,2), (3,2);"
        };

        try (Statement statement = connection.createStatement()) {
            for (int i = 0; i < inserts.length; i++){
                statement.execute(inserts[i]);
                connection.commit();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Incident[] expectedIncidents(){

        String[] testCase1_categoryNames = {"Workstation"};
        ArrayList<String> testCase1_categoryArray = new ArrayList<>(Arrays.asList(testCase1_categoryNames));

        String[] testCase1_servicePersonNames = {"Patrick Star"};
        ArrayList<String> testCase1_servicePersonsArray = new ArrayList<>(Arrays.asList(testCase1_servicePersonNames));

        String[] testCase2_categoryNames = {"Server", "Deep fryer"};
        ArrayList<String> testCase2_categoryArray = new ArrayList<>(Arrays.asList(testCase2_categoryNames));

        String[] testCase2_servicePersonNames = {"Patrick Star", "Mr. Krabs"};
        ArrayList<String> testCase2_servicePersonsArray = new ArrayList<>(Arrays.asList(testCase2_servicePersonNames));

        return new Incident[]{

            new Incident(
                    1L,
                    15120,
                    "test subject",
                    "test description",
                    "test notes",
                    "1,2,3,4",
                    1L, 1L, 1L, 1L,
                    "new",
                    "Coffee on keyboard dryed using hairdryer.",
                    testCase1_categoryArray,
                    testCase1_servicePersonsArray,
                    "John","Milton","Holmes",
                    "normal"
            ),

            new Incident(
                    2L,
                    15121,
                    "test subject442",
                    "test description442",
                    "test note33s2",
                    "2, 3",
                    2L, 2L, 2L, 2L,
                    "under work",
                    "Powercord plugged to wall outlet",
                    testCase2_categoryArray,
                    testCase2_servicePersonsArray,
                    "Michelle","Eleanore","Pfiifferi",
                    "high"
            )
        }; //
    }

    String timestamp(){
        LocalDateTime dateTime = LocalDateTime.now();
        String timestamp = dateTime.getYear() +"-"
                + dateTime.getMonthValue() +"-"
                + dateTime.getDayOfMonth() +"-"
                + dateTime.getHour()
                + dateTime.getMinute()
                + dateTime.getSecond()
                + dateTime.getNano();
        return timestamp;
    }
}