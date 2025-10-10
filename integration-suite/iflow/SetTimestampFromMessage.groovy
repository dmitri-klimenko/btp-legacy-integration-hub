import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def lastTimestamp = message.getBody(String.class)
        
    if (lastTimestamp == null || lastTimestamp.isEmpty()) {
        lastTimestamp = "2024-01-01T00:00:00Z"
    }
    
    message.setProperty("lastRunTimestamp", lastTimestamp)
    
    return message
}