import com.sap.gateway.ip.core.customdev.util.Message
import java.text.SimpleDateFormat

def Message processData(Message message) {
    
    // Generate current timestamp
    def now = new Date()
    def dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
    def currentTimestamp = dateFormat.format(now)
    
    // Set as body for Data Store Write
    message.setBody(currentTimestamp)
    
    return message
}