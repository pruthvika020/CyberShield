package cybershield.model;

public class SecurityEvent {
    private final String eventId;
    private final String timestamp;
    private final String sourceIp;
    private final String destinationIp;
    private final String eventType;
    private final String signature;
    private final String severity;

    public SecurityEvent(String eventId, String timestamp, String sourceIp,
                         String destinationIp, String eventType,
                         String signature, String severity) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.eventType = eventType;
        this.signature = signature;
        this.severity = severity;
    }

    public String getEventId() { return eventId; }
    public String getTimestamp() { return timestamp; }
    public String getSourceIp() { return sourceIp; }
    public String getDestinationIp() { return destinationIp; }
    public String getEventType() { return eventType; }
    public String getSignature() { return signature; }
    public String getSeverity() { return severity; }

    @Override
    public String toString() {
        return "Event ID : " + eventId + "\n"
             + "Timestamp: " + timestamp + "\n"
             + "Source IP: " + sourceIp + "\n"
             + "Dest IP  : " + destinationIp + "\n"
             + "Type     : " + eventType + "\n"
             + "Signature: " + signature + "\n"
             + "Severity : " + severity;
    }
}
