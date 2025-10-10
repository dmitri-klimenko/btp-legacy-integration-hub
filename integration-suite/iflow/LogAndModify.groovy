import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

def Message processData(Message message) {
    
    def messageLog = messageLogFactory.getMessageLog(message)
    
    def body = message.getBody(String.class)
    def jsonSlurper = new JsonSlurper()
    def data = jsonSlurper.parseText(body)
    
    messageLog.addAttachmentAsString("Original Data", body, "application/json")
    
    // Extend each order
    data.value.each { order ->
        // Calculate tax
        order.TotalWithTax = order.Amount * 1.2
        
        // Set priority
        order.Priority = order.Amount > 1000 ? "High" : "Normal"
    }
    
    // Convert back to JSON
    def json = new JsonBuilder(data).toPrettyString()
    message.setBody(json)
    
    messageLog.addAttachmentAsString("Modified Data", json, "application/json")
    
    return message
}