package MikaDevelops.ticketingSystem.dataRepository;

import MikaDevelops.ticketingSystem.incident.Incident;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SqliteDatabase implements DataBaseService{

    private final String databaseAddress;

    public SqliteDatabase(@Qualifier("dataBaseAddress") String databaseAddress) {
        this.databaseAddress = databaseAddress;
        this.initializeDataBase();
    }

    @Override
    public void initializeDataBase() {
        Connection connection = this.getConnection();
        try (
                Statement statement = connection.createStatement()
        ) {

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS customer
                    (
                      customer_id INTEGER PRIMARY KEY,
                      first_name TEXT NOT NULL,
                      middle_name TEXT,
                      last_name TEXT NOT NULL
                    );
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS status
                    (
                      status_id INTEGER PRIMARY KEY,
                      name TEXT NOT NULL
                    );
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS category
                    (
                      category_id INTEGER PRIMARY KEY,
                      name TEXT NOT NULL
                    );
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS priority
                    (
                      priority_id INTEGER PRIMARY KEY,
                      description TEXT NOT NULL
                    );
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS solution
                    (
                      solution_id INTEGER PRIMARY KEY,
                      description TEXT NOT NULL
                    );
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS service_person
                    (
                      person_id INTEGER PRIMARY KEY,
                      name TEXT NOT NULL
                    );
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS incident
                    (
                      incident_id INTEGER PRIMARY KEY,
                      created_datetime INT NOT NULL,
                      subject TEXT NOT NULL,
                      description TEXT NOT NULL,
                      notes TEXT,
                      related_incidents TEXT,
                      status_id INT NOT NULL,
                      customer_id INT NOT NULL,
                      priority_id INT NOT NULL,
                      solution_id INT,
                      FOREIGN KEY (status_id) REFERENCES status(status_id),
                      FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
                      FOREIGN KEY (priority_id) REFERENCES priority(priority_id),
                      FOREIGN KEY (solution_id) REFERENCES solution(solution_id)
                    );
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS incident_category
                    (
                      category_id INT NOT NULL,
                      incident_id INT NOT NULL,
                      FOREIGN KEY (category_id) REFERENCES category(category_id),
                      FOREIGN KEY (incident_id) REFERENCES incident(incident_id)
                    );
                    """);

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS incident_service_person
                    (
                      person_id INT NOT NULL,
                      incident_id INT NOT NULL,
                      FOREIGN KEY (person_id) REFERENCES service_person(person_id),
                      FOREIGN KEY (incident_id) REFERENCES incident(incident_id)
                    );
                    """);

            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Incident getIncidentById(long id) {
        Connection connection = this.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement("""
                SELECT
                incident.incident_id, incident.created_datetime, incident.subject, incident.description AS incident_desc,
                incident.notes, incident.related_incidents, incident.status_id, incident.customer_id,
                incident.priority_id, incident.solution_id, status.name AS status_name,
                customer.first_name AS customer_first_name, customer.middle_name AS customer_middle_name,
                customer.last_name AS customer_last_name,
                priority.description AS priority_desc , solution.description AS solution_desc
                FROM incident
                LEFT JOIN status ON incident.status_id = status.status_id
                LEFT JOIN solution ON incident.solution_id = solution.solution_id
                LEFT JOIN customer ON incident.customer_id = customer.customer_id
                LEFT JOIN priority ON incident.priority_id = priority.priority_id
                WHERE incident_id = ?;
                """);
            )
        {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            connection.commit();
            Incident incident = new Incident();
            if (result.next()){
                incident.setIncidentId(result.getLong("incident_id"));
                incident.setCreatedDatetime(result.getLong("created_datetime"));
                incident.setSubject(result.getString("subject"));
                incident.setDescription(result.getString("incident_desc"));
                incident.setNotes(result.getString("notes"));
                incident.setRelatedIncidentsId(result.getString("related_incidents"));
                incident.setStatusId(result.getLong("status_id"));
                incident.setCustomerId(result.getLong("customer_id"));
                incident.setPriorityId(result.getLong("priority_id"));
                incident.setSolutionId(result.getLong("solution_id"));
                incident.setStatusName(result.getString("status_name"));
                incident.setSolutionDescription(result.getString("solution_desc"));
                incident.setCustomerFirstName(result.getString("customer_first_name"));
                incident.setCustomerMiddleName(result.getString("customer_middle_name"));
                incident.setCustomerLastName(result.getString("customer_last_name"));
                incident.setPriorityDescription(result.getString("priority_desc"));
            }

            List<String> incidentServicePersons = this.getServicePersons(id);
            incident.setIncidentServicePersons(incidentServicePersons);

            // get categories

            // make array of categories

            return incident;
        }
        catch (SQLException e){e.printStackTrace();}

        return null;
    }

    @Override
    public List<String> getServicePersons(long incidentId){
        List<String> servicePersons =  new ArrayList<>();
        try( Connection connection = this.getConnection(); ){

            PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT service_person.name FROM service_person
                    LEFT JOIN incident_service_person ON incident_service_person.person_id = service_person.person_id
                    WHERE incident_service_person.incident_id = ?;
                    """);

            preparedStatement.setLong(1, incidentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            while( resultSet.next() ){
                servicePersons.add(resultSet.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicePersons;
    }

    @Override
    public List<Incident> getAllIncidents(){
        try( Connection connection = this.getConnection(); ){
            //TODO: should return all incidents
            String sqlString = "SELECT created_datetime";
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void saveIncident(Incident incident) {

    }

    @Override
    public void saveServicePerson() {

    }

    @Override
    public void saveCategory() {

    }

    @Override
    public void saveStatus() {

    }

    @Override
    public void saveCustomer() {

    }

    @Override
    public void savePriority() {

    }

    @Override
    public void saveSolution() {

    }

    @Override
    public void setIncidentStatus(long incidentId, long statusId) {

    }

    @Override
    public void setServicePersonToIncident(long incidentId, long personId) {

    }

    @Override
    public void setIncidentCategory(long incidentId, long categoryId) {

    }

    @Override
    public void setIncidentCustomer(long incidentId, long customerId) {

    }

    @Override
    public void setIncidentPriority(long incidentId, long priorityId) {

    }

    private Connection getConnection(){
        Connection connection;
        String baseAddress = this.getBaseAddress();
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:" + baseAddress + this.databaseAddress);
            connection.createStatement().execute("PRAGMA foreign_keys = ON");
            connection.setAutoCommit(false);
            return connection;
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        connection = null;
        return connection;
    }

    private String getBaseAddress(){
        if (this.databaseAddress.equals(":memory:")) return "";
        else return "data/";
    }
}
