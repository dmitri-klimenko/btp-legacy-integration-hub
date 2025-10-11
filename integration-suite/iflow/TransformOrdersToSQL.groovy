import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.*
import java.util.Arrays

def Message processData(Message message) {
    
    // Read the CAP OData JSON response
    def body = message.getBody(String)
    def json = new JsonSlurper().parseText(body)
    
    // Collect parameters for batch insert
    def paramList = new ArrayList()
    
    json.value.each { order ->
        def id = order.ID
        def custId = order.CustomerID_ID
        def amount = order.Amount ?: 0
        def currency = order.Currency ?: 'NULL'
        def status = order.Status ?: 'NULL'
        
        // Convert timestamp: Remove Z and T
        def createdAt = order.CreatedAt ?: '1970-01-01T00:00:00.000'
        createdAt = createdAt.replace('T', ' ').replace('Z', '')
        
        def total = order.TotalWithTax ?: 0
        def priority = order.Priority ?: 'NULL'
        
        // Add parameters as array
        paramList.add(Arrays.asList(id, custId, amount, currency, status, createdAt, total, priority))
    }
    
    // Set JDBC batch parameters in header
    message.setHeader("CamelSqlParameters", paramList)
    
    // Set SQL with placeholders
    message.setBody("INSERT INTO POLLING_DEMO.ORDERS (ID, CustomerID, Amount, Currency, Status, CreatedAt, TotalWithTax, Priority) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
    
    try {
        def log = messageLogFactory?.getMessageLog(message)
        if (log) {
            log.addAttachmentAsString("SQL Statement", message.getBody(String), "text/plain")
        }
    } catch (Exception e) {
        println("Logging failed: ${e.message}")
    }
    
    return message
}