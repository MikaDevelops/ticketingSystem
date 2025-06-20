package MikaDevelops.ticketingSystem;

import MikaDevelops.ticketingSystem.dataRepository.DataBaseService;
import MikaDevelops.ticketingSystem.dataRepository.SqliteDatabase;
import MikaDevelops.ticketingSystem.incident.Incident;
import MikaDevelops.ticketingSystem.incident.ModificationInfo;
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
    private final Incident[] expectedIncidents = this.expectedIncidents();
    private final ModificationInfo[] expectedModificationInfo = this.expectedLatestModificationInfo();

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
        assertEquals(this.expectedIncidents[0].getModifiedBy(), incident.getModifiedBy());
        assertEquals(this.expectedIncidents[0].getModifiedDateTime(), incident.getModifiedDateTime());
    }

    @Test
    void notModifiedIncidentShouldHaveEmptyModifierPerson (){
        testName = "notModifiedIncidentShouldHaveEmptyModifierPerson";

        long incidentId = this.expectedIncidents[1].getIncidentId();

        Incident incident = dbService.getIncidentById(incidentId);

        assertEquals(this.expectedIncidents[1].getModifiedBy(), incident.getModifiedBy(), "Person who modified record if any.");
    }

    @Test
    void notModifiedIncidentShouldHaveZeroInDateTime (){
        testName = "notModifiedIncidentShouldHaveZeroInDateTime";

        long incidentId = this.expectedIncidents[1].getIncidentId();

        Incident incident = dbService.getIncidentById(incidentId);

        assertEquals(this.expectedIncidents[1].getModifiedDateTime(), incident.getModifiedDateTime(), "Timestamp of modification if any.");
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
            assertEquals(expectedIncidents[i].getModifiedBy(), results.get(i).getModifiedBy(), "Modified by on case: "+i);
            assertEquals(expectedIncidents[i].getModifiedDateTime(), results.get(i).getModifiedDateTime(), "Modified datetime on case: "+i);
        }
    }

    @Test
    void categoryTableShouldNotHaveDeletedIncidents(){
        testName = "categoryTableShouldNotHaveDeletedIncidents";
        try (Connection conn = dbService.getConnection()) {

            Statement statement = conn.createStatement();

            // remove first test case from incident table
            String sqlDeleteString = "DELETE FROM incident WHERE incident_id = 1;";
            statement.executeUpdate(sqlDeleteString);
            conn.commit();

            String sqlGetCategoriesString = "SELECT * FROM incident_category WHERE incident_id = 1;";
            ResultSet results = statement.executeQuery(sqlGetCategoriesString);
            conn.commit();
            assertFalse(results.next(), "Removed incident shouldn't be in incident_category table.");

            // Category should still be there
            String sqlGetCategoryString = "SELECT * FROM category WHERE category_id = 1";
            ResultSet categoryResults = statement.executeQuery(sqlGetCategoryString);
            conn.commit();

            String expectedCategory = "Workstation";
            String receivedCategory = categoryResults.getString("name");
            assertEquals(expectedCategory, receivedCategory, "Category should exist after incident removed.");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void categoryTableShouldNotHaveDeletedCategories(){
        testName = "categoryTableShouldNotHaveDeletedCategories";

        try (Connection conn = dbService.getConnection()) {

            Statement statement = conn.createStatement();

            // Remove a category, there should be no incident to that category in incident_category
            String sqlRemoveCategory = "DELETE FROM category WHERE category_id = 2";
            statement.executeUpdate(sqlRemoveCategory);
            conn.commit();

            String sqlGetIncidentCategories = "SELECT * FROM incident_category WHERE category_id = 2";
            ResultSet incidentCategoryResult = statement.executeQuery(sqlGetIncidentCategories);
            conn.commit();
            assertFalse(incidentCategoryResult.next(), "Removed category should not be in incident_category");


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void latestModificationInfo(){
        testName = "latestModificationInfo";

        ModificationInfo resultHasValues = dbService.getLatestModificationInfo(1L);
        ModificationInfo resultNoModification = dbService.getLatestModificationInfo(2L);

        ModificationInfo expectedWithValues = this.expectedModificationInfo[0];
        ModificationInfo expectedNoModification = this.expectedModificationInfo[1];

        assertEquals(expectedWithValues.getModifiedBy(), resultHasValues.getModifiedBy(), "Should have a person who modified.");
        assertEquals(expectedWithValues.getUnixTimestamp(), resultHasValues.getUnixTimestamp(), "Should have a timestamp.");
        assertEquals(expectedWithValues.getModificationId(), resultHasValues.getModificationId(),"Modification Id");
        assertEquals(expectedWithValues.getIncidentId(), resultHasValues.getIncidentId(), "Incident id");
        assertEquals(expectedWithValues.getServicePersonId(), resultHasValues.getServicePersonId(),"Service person id");

        assertEquals(expectedNoModification.getModifiedBy(), resultNoModification.getModifiedBy(),"Should have no modifier.");
        assertEquals(expectedNoModification.getUnixTimestamp(), resultNoModification.getUnixTimestamp(),"Should not have a timestamp");
        assertEquals(expectedNoModification.getModificationId(), resultNoModification.getModificationId(),"Modification Id");
        assertEquals(expectedNoModification.getIncidentId(), resultNoModification.getIncidentId(), "Incident id");
        assertEquals(expectedNoModification.getServicePersonId(), resultNoModification.getServicePersonId(),"Service person id");
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
                "INSERT INTO incident_category(category_id, incident_id) VALUES (1,1), (2,2), (3,2), (4,4);",
                "INSERT INTO modification(timestamp_unix, incident_id, person_id) VALUES (1748535492, 1, 1);"
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
                    "normal",
                    "Patrick Star",
                    1748535492L
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
                    "high",
                    "",
                    0L
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
                    "low",
                    "",
                    0L
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
                    "low",
                    "",
                    0L
            )
        };
    }

    ModificationInfo[] expectedLatestModificationInfo(){

        ModificationInfo expectedWithModification = new ModificationInfo(
                1L,
                1748535492L,
                1L,
                1L
        );
        expectedWithModification.setModifiedBy("Patrick Star");

        ModificationInfo expectedWithNoModification = new ModificationInfo(
                0L,
                0L,
                2L,
                0L
        );
        expectedWithNoModification.setModifiedBy("");

        return new ModificationInfo[]{
                expectedWithModification,
                expectedWithNoModification
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