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

       Incident expectedIncident = expectedIncidents[2];
       ArrayList<String> expectedServicePersons = (ArrayList<String>) expectedIncident.getIncidentServicePersons();

       Incident resultIncident = (Incident) dbService.getIncidentById(expectedIncident.getIncidentId());

       assertArrayEquals(expectedServicePersons.toArray(), resultIncident.getIncidentServicePersons().toArray());
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

    @Test
    void shouldHaveEmptyCategoryNames(){
        testName = "shouldHaveEmptyCategoryNames";

        Incident[] expectedIncidents = this.expectedIncidents();
        Incident expectedIncident = expectedIncidents[2];

        Incident resultIncident = dbService.getIncidentById(3L);
        ArrayList<String> resultCategories = (ArrayList<String>) resultIncident.getCategoryNames();

        assertEquals(0, resultCategories.size());
        assertArrayEquals(expectedIncident.getCategoryNames().toArray(), resultIncident.getCategoryNames().toArray());
    }

    @Test
    void shouldReturnAllIncidents(){
        testName = "shouldReturnAllIncidents";

        List<Incident> results = dbService.getAllIncidents();

        assertInstanceOf(List.class, results);
        assertEquals(expectedIncidents.length, results.size());
        for (int i = 0; i < expectedIncidents.length; i++){
            assertEquals(expectedIncidents[i].getIncidentId(), results.get(i).getIncidentId(), "Incident Id on case: "+i);
            assertEquals(expectedIncidents[i].getRelatedIncidentsId(), results.get(i).getRelatedIncidentsId(), "Related incidents Id on case: "+i);
            assertEquals(expectedIncidents[i].getPriorityDescription(), results.get(i).getPriorityDescription(), "Priority desc Id on case: "+i);
            assertEquals(expectedIncidents[i].getCustomerMiddleName(), results.get(i).getCustomerMiddleName(), "Customer middle name Id on case: "+i);
            assertEquals(expectedIncidents[i].getCustomerFirstName(), results.get(i).getCustomerFirstName(), "Customer first name on case: "+i);
            assertEquals(expectedIncidents[i].getCustomerLastName(), results.get(i).getCustomerLastName(), "Customer last name on case: "+i);
            assertEquals(expectedIncidents[i].getSolutionId(), results.get(i).getSolutionId(), "Solution id on case: "+i);
            assertEquals(expectedIncidents[i].getSolutionDescription(), results.get(i).getSolutionDescription(), "Solution desc on case: "+i);
            assertEquals(expectedIncidents[i].getNotes(), results.get(i).getNotes(), "Notes on case: "+i);
            assertEquals(expectedIncidents[i].getStatusName(), results.get(i).getStatusName(), "Status Name on case: "+i);
            assertEquals(expectedIncidents[i].getCustomerId(), results.get(i).getCustomerId(), "Customer id on case: "+i);
            assertArrayEquals(expectedIncidents[i].getIncidentServicePersons().toArray(), results.get(i).getIncidentServicePersons().toArray(), "Service persons on case: "+i);
            assertLinesMatch(expectedIncidents[i].getCategoryNames(), results.get(i).getCategoryNames(), "Category names on case"+i);
            assertEquals(expectedIncidents[i].getCreatedDatetime(), results.get(i).getCreatedDatetime(), "Created datetime Id on case: "+i);
            assertEquals(expectedIncidents[i].getSubject(), results.get(i).getSubject(), "Subject on case: "+i);
            assertEquals(expectedIncidents[i].getPriorityId(), results.get(i).getPriorityId(), "Priority Id on case: "+i);
            assertEquals(expectedIncidents[i].getStatusId(), results.get(i).getStatusId(), "Status Id on case: "+i);
            assertEquals(expectedIncidents[i].getDescription(), results.get(i).getDescription(), "Description on case: "+i);
        }
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
                "INSERT INTO incident_service_person(person_id, incident_id) VALUES (1,1), (2,2), (1,2), (3,4);",
                "INSERT INTO incident_category(category_id, incident_id) VALUES (1,1), (2,2), (3,2), (4,4);"
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

        String[] testCase2_servicePersonNames = {"Mr. Krabs", "Patrick Star"};
        ArrayList<String> testCase2_servicePersonsArray = new ArrayList<>(Arrays.asList(testCase2_servicePersonNames));

        String[] testCase3_categoryNames = new String[]{};
        ArrayList<String> testCase3_categoryArray = new ArrayList<>(Arrays.asList(testCase3_categoryNames));

        String[] testCase3_servicePersonNames = new String[]{};
        ArrayList<String> testCase3_servicePersonsArray = new ArrayList<>(Arrays.asList(testCase3_servicePersonNames));

        String[] testCase4_categoryNames = new String[]{"Plankton"};
        ArrayList<String> testCase4_categoryArray = new ArrayList<>(Arrays.asList(testCase4_categoryNames));

        String[] testCase4_servicePersonNames = new String[]{"Squidward Tentacles"};
        ArrayList<String> testCase4_servicePersonsArray = new ArrayList<>(Arrays.asList(testCase4_servicePersonNames));

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
                    "2,3",
                    2L, 2L, 2L, 2L,
                    "under work",
                    "Powercord plugged to wall outlet",
                    testCase2_categoryArray,
                    testCase2_servicePersonsArray,
                    "Michelle","Eleanore","Pfiifferi",
                    "high"
            ),

            new Incident(
                    3L,
                    15122,
                    "test subject443",
                    "test description443",
                    "test not33es3",
                    "3",
                    3L, 2L, 3L, 3L,
                    "waiting",
                    "Year old fries removed.",
                    testCase3_categoryArray,
                    testCase3_servicePersonsArray,
                    "Michelle","Eleanore","Pfiifferi",
                    "low"
            ),

            new Incident(
                    4L,
                    15123,
                    "test subject444",
                    "test description444",
                    "test not33es4",
                    "1",
                    2L, 1L, 3L, 4L,
                    "under work",
                    "Used magic.",
                    testCase4_categoryArray,
                    testCase4_servicePersonsArray,
                    "John","Milton","Holmes",
                    "low"
            )
        };
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