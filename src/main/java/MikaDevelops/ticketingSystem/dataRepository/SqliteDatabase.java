package MikaDevelops.ticketingSystem.dataRepository;

import MikaDevelops.ticketingSystem.incident.Incident;

import java.sql.*;

public class SqliteDatabase implements DataBaseService{

    private final String databaseAddress;

    public SqliteDatabase(String databaseAddress) {
        this.databaseAddress = databaseAddress;
        this.initializeDataBase();
    }

    @Override
    public void initializeDataBase() {
        Connection connection = this.getConnection();
        try (
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS incident ("+
                    "incident_id INTEGER PRIMARY KEY,"+
                    "created_datetime INTEGER NOT NULL,"+
                    "subject TEXT NOT NULL,"+
                    "description TEXT NOT NULL,"+
                    "notes TEXT,"+
                    "related_incidents TEXT,"+
                    "status_id INTEGER,"+
                    "customer_id INTEGER,"+
                    "priority_id INTEGER,"+
                    "solution_id INTEGER"+
                    ")");
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
                PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM incident WHERE incident_id = ?"
                );
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
                incident.setDescription(result.getString("description"));
                incident.setNotes(result.getString("notes"));
                incident.setRelatedIncidentsId(result.getString("related_incidents"));
                incident.setStatusId(result.getLong("status_id"));
                incident.setCustomerId(result.getLong("customer_id"));
                incident.setPriorityId(result.getLong("priority_id"));
                incident.setSolutionId(result.getLong("solution_id"));
            }
            return incident;
        }
        catch (SQLException e){e.printStackTrace();}

        return null;
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
