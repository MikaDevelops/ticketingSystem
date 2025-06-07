package MikaDevelops.ticketingSystem.incident;

import java.util.List;

public class Incident {

    public Incident() {}
    public Incident(
            long incidentId,
            long createdDatetime,
            String subject,
            String description,
            String notes,
            String relatedIncidentsId,
            long statusId,
            long customerId,
            long priorityId,
            long solutionId,
            String statusName,
            String solutionDescription,
            List<String> categoryNames,
            List<String> incidentServicePersons,
            String customerFirstName,
            String customerMiddleName,
            String customerLastName,
            String priorityDescription,
            String modifiedBy,
            long modifiedDateTime
    )
    {
        this.incidentId = incidentId;
        this.createdDatetime = createdDatetime;
        this.subject = subject;
        this.description = description;
        this.notes = notes;
        this.relatedIncidentsId = relatedIncidentsId;
        this.statusId = statusId;
        this.customerId = customerId;
        this.priorityId = priorityId;
        this.solutionId = solutionId;
        this.statusName = statusName;
        this.solutionDescription = solutionDescription;
        this.categoryNames = categoryNames;
        this.incidentServicePersons = incidentServicePersons;
        this.customerFirstName = customerFirstName;
        this.customerMiddleName = customerMiddleName;
        this.customerLastName = customerLastName;
        this.priorityDescription = priorityDescription;
        this.modifiedBy = modifiedBy;
        this.modifiedDateTime = modifiedDateTime;
    }

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
    private String statusName;
    private String solutionDescription;
    private List<String> categoryNames;
    private List<String> incidentServicePersons;
    private String customerFirstName;
    private String customerMiddleName;
    private String customerLastName;
    private String priorityDescription;
    private String modifiedBy;
    private long modifiedDateTime;

    public List<String> getCategoryNames() { return this.categoryNames; }
    public void setCategoryNames(List<String> categoryNames) { this.categoryNames = categoryNames; }

    public List<String> getIncidentServicePersons() { return this.incidentServicePersons; }
    public void setIncidentServicePersons(List<String> incidentServicePersons) {
        this.incidentServicePersons = incidentServicePersons;
    }

    public long getIncidentId() {
        return incidentId;
    }
    public void setIncidentId(long incidentId) {
        this.incidentId = incidentId;
    }

    public long getCreatedDatetime() {
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

    public String getStatusName() { return this.statusName; }
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getSolutionDescription() {
        return this.solutionDescription;
    }
    public void setSolutionDescription(String description) {
        this.solutionDescription = description;
    }

    public String getCustomerFirstName() { return this.customerFirstName; }
    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerMiddleName() {
        return this.customerMiddleName;
    }
    public void setCustomerMiddleName(String customerMiddleName) {
        this.customerMiddleName = customerMiddleName;
    }

    public String getCustomerLastName() {
        return this.customerLastName;
    }
    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getPriorityDescription() {
        return this.priorityDescription;
    }
    public void setPriorityDescription(String priorityDescription) {
        this.priorityDescription = priorityDescription;
    }

    public String getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    public long getModifiedDateTime() { return modifiedDateTime; }
    public void setModifiedDateTime(long modifiedDateTime) { this.modifiedDateTime = modifiedDateTime; }
}
