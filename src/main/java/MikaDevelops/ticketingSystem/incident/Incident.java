package MikaDevelops.ticketingSystem.incident;

public class Incident {

    private long incidentId;
    private long createdDatetime;
    private String subject;
    private String description;
    private String notes;
    private String relatedIncidentsId;
    private long statusId;
    private long customerId;
    private long priorityId;
    private long solutionId;

    public long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(long incidentId) {
        this.incidentId = incidentId;
    }

    public long getCreated_datetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(long created_datetime) {
        this.createdDatetime = created_datetime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRelatedIncidentsId() {
        return relatedIncidentsId;
    }

    public void setRelatedIncidentsId(String relatedIncidentsId) {
        this.relatedIncidentsId = relatedIncidentsId;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(long priorityId) {
        this.priorityId = priorityId;
    }

    public long getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(long solutionId) {
        this.solutionId = solutionId;
    }
}
