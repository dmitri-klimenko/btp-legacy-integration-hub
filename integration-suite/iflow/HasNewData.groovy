import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.json.JsonSlurper;

def Message processData(Message message) {
    def body = message.getBody(String.class);
    def json = new JsonSlurper().parseText(body);
    
    // Check if value array exists and has elements
    if (json.value && json.value instanceof List && json.value.size() > 0) {
        message.setProperty("hasNewData", "true");
    } else {
        message.setProperty("hasNewData", "false");
    }
    
    return message;
}