# 🚀 BTP Legacy Integration Hub

**A complete SAP BTP integration solution** demonstrating clean-core data synchronization between legacy ECC systems and SAP HANA Cloud.

This project showcases a **production-ready integration pattern** using SAP Integration Suite, CAP services, and XSUAA security to extract, transform, and persist legacy data without system modifications.

---

## 🎯 What This Solution Does

✅ **Legacy System Simulation** - CAP-based mock ECC system with realistic customer and order data  
✅ **Secure API Access** - OAuth2/XSUAA authentication protecting all endpoints  
✅ **Automated Data Sync** - Timer-based integration flow polling and transforming data  
✅ **Cloud Persistence** - Real-time data synchronization to HANA Cloud database  
✅ **Production-Ready** - Deployable to Cloud Foundry with proper security configuration

## 🏗️ Architecture

```
🏢 Legacy ECC (CAP + XSUAA)  →  🔄 Integration Suite  →  ☁️ HANA Cloud
   OAuth2-secured APIs           Timer + Transform         Synchronized Data
```

**Data Flow:**
1. **CAP Service** exposes `/Customers` and `/Orders` as secured OData endpoints
2. **Integration Flow** polls APIs every minute, transforms JSON to SQL format
3. **HANA Cloud** stores synchronized data in `POLLING_DEMO` schema

## 🧰 Technology Stack

| Component | Technology | Implementation |
|-----------|------------|----------------|
| **Legacy Simulation** | SAP CAP (Node.js) | OData services with sample business data |
| **API Security** | SAP XSUAA | OAuth2 authentication and role-based access |
| **Integration** | SAP Integration Suite | Timer-triggered iFlow with Groovy transformations |
| **Data Storage** | SAP HANA Cloud | Synchronized tables with timestamp tracking |
| **Deployment** | Cloud Foundry | MTA-based deployment with service bindings |

## 📊 Key Features

### **🔐 Enterprise Security**
- OAuth2 client credentials flow
- Role-based access control (Viewer/Admin roles)
- Secure service-to-service communication

### **🔄 Intelligent Integration**
- Automated polling with configurable intervals
- Data transformation and enrichment via Groovy scripts

### **📈 Real-time Synchronization**
- Timestamp-based change detection
- Incremental data updates
- JDBC-based persistence to HANA Cloud

## 📁 Solution Components
```
btp-legacy-integration-hub/
├── cap-app/                           # Legacy ECC Simulation
│   ├── db/schema.cds                  # Customer & Order data models
│   ├── srv/legacy-service.cds         # OData service definitions
│   ├── xs-security.json               # OAuth2 security configuration
│   └── mta.yaml                       # Cloud Foundry deployment descriptor
├── integration-suite/iflow/           # Data Synchronization Logic
│   ├── HasNewData.groovy              # Validates if Legacy system has new Orders
│   ├── LogAndModify.groovy            # Enriches Orders with TotalWithTax and Priority, logs original and     modified data
│   ├── SetTimestampFromMessage.groovy # Retrieves last sync timestamp from data store, if no data, sets default value, sets exchange property
│   ├── SetTimestampToMessage.groovy   # Generates current UTC timestamp for tracking and sets the message's body with the value
│   └── TransformOrdersToSQL.groovy    # Converts fetched JSON data to SQL INSERT statements
├── hana-cloud/schema/                 # Database Schema
│   └── createSchemaAndTable.sql       # POLLING_DEMO schema and table creation script
└── docs/demo-screenshots/             # Visual documentation
```

## 🎯 Business Value

**Clean-Core Integration Pattern:**
- ✅ **Zero Legacy Modification** - No changes to existing ECC systems
- ✅ **API-First Approach** - Modern REST/OData integration
- ✅ **Cloud-Native Security** - Enterprise-grade OAuth2 implementation
- ✅ **Real-Time Insights** - Up-to-date business data in cloud analytics

**Use Cases:**
- Legacy system modernization during S/4HANA migration
- Real-time reporting and analytics on legacy data
- Side-by-side extension development
- Proof-of-concept for clean-core architecture

## 🚀 Live Demo

This solution is **fully deployed and operational** on SAP BTP, demonstrating:

- **Active data synchronization** between mock ECC and HANA Cloud
- **Secure API endpoints** protected by XSUAA authentication
- **Real-time monitoring** via Integration Suite dashboards
- **Scalable cloud deployment** on Cloud Foundry platform

---


## 💼 Business Scenario

A manufacturing company needs to **synchronize data from their legacy SAP ECC system** to **SAP HANA Cloud** for reporting and analytics purposes.

The legacy system cannot be modified, so SAP BTP acts as the **integration bridge**, ensuring data consistency and providing secure access to synchronized data.

**This project demonstrates:**
- Non-invasive integration with legacy systems
- Secure data synchronization to cloud database
- OAuth2-secured APIs and data access
- Real-time data transformation and enrichment  

---

## 📁 Repository Structure

```
btp-legacy-integration-hub/
├── cap-app/                           # Simulated legacy ECC system (CAP)
│   ├── db/
│   │   ├── data/                      # Sample data files
│   │   └── schema.cds                 # Database schema definition
│   ├── srv/
│   │   └── legacy-service.cds         # Service definitions
│   ├── package.json
│   ├── xs-security.json               # XSUAA security configuration
│   ├── mta.yaml                       # Multi-target application descriptor
│   └── eslint.config.mjs              # ESLint configuration
├── integration-suite/
│   └── iflow/                         # Integration flow Groovy scripts
│       ├── HasNewData.groovy          # Validates if OData response contains records
│       ├── LogAndModify.groovy        # Data enrichment (tax calc, priority assignment)
│       ├── SetTimestampFromMessage.groovy  # Retrieves last synchronization timestamp
│       ├── SetTimestampToMessage.groovy    # Generates current UTC timestamp
│       └── TransformOrdersToSQL.groovy     # JSON to SQL batch INSERT transformation
├── hana-cloud/
│   └── schema/
│       └── createSchemaAndTable.sql   # HANA database schema creation
├── docs/
│   └── demo-screenshots/              # Project documentation and screenshots
├── .gitignore
└── README.md
```

