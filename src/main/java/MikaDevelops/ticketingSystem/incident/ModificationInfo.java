package MikaDevelops.ticketingSystem.incident;

public class ModificationInfo {

    private Long modificationId;
    private Long unixTimestamp;
    private Long incidentId;
    private Long servicePersonId;

    public ModificationInfo(){};
    public ModificationInfo(Long modificationId, Long unixTimestamp, Long incidentId, Long servicePersonId) {
        this.modificationId = modificationId;
        this.unixTimestamp = unixTimestamp;
        this.incidentId = incidentId;
        this.servicePersonId = servicePersonId;
    }

    public Long getModificationId() {
        return modificationId;
    }

    public void setModificationId(Long modificationId) {
        this.modificationId = modificationId;
    }

    public Long getUnixTimestamp() {
        return unixTimestamp;
    }

    public void setUnixTimestamp(Long unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
    }

    public Long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(Long incidentId) {
        this.incidentId = incidentId;
    }

    public Long getServicePersonId() {
        return servicePersonId;
    }

    public void setServicePersonId(Long servicePersonId) {
        this.servicePersonId = servicePersonId;
    }
}
