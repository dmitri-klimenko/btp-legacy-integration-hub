# 🚀 BTP Legacy Integration Hub

End-to-end **SAP Business Technology Platform (BTP)** scenario connecting a simulated **legacy ECC system** with **SAP HANA Cloud** using **Integration Suite**, **CAP**, and **XSUAA**.

**Goal:** Demonstrate a clean-core integration approach — extracting, transforming, and persisting legacy data in HANA Cloud without modifying ECC systems.

---

## 📖 Table of Contents

1. [Project Overview](#-project-overview)
2. [Architecture](#️-architecture)
3. [Technologies Used](#-technologies-used)
4. [Business Scenario](#-business-scenario)
5. [Repository Structure](#-repository-structure)
6. [Setup Instructions](#-setup-instructions)
7. [Security & Roles](#-security--roles)
8. [Clean Core Principles](#-clean-core-principles)
9. [Author](#-author)

---

## 🧩 Project Overview

This project demonstrates an **end-to-end integration from a simulated legacy ECC system to SAP HANA Cloud** using SAP BTP as the integration platform.

**Key capabilities:**
- Simulate legacy ECC system using CAP application with OAuth2 security
- Extract and transform data via Integration Suite iFlow
- Persist synchronized data in SAP HANA Cloud database
- Secure APIs with XSUAA authentication

---

## 🏗️ Architecture

The solution demonstrates a **clean-core integration pipeline**:

```
Legacy ECC Simulation (CAP Node.js + XSUAA)
          ↓
Integration Suite iFlow (Timer → REST → Transform → JDBC)
          ↓
SAP HANA Cloud (Synchronized Data Persistence)
```


## 🧰 Technologies Used

| Layer | Service/Tool | Purpose |
|-------|---------------|----------|
| **Integration** | SAP Integration Suite | Data polling, transformation, and persistence via JDBC adapter |
| **Data** | SAP HANA Cloud | Central data store for synchronized business data |
| **Backend** | SAP CAP (Node.js) | Simulated legacy ECC system with OData APIs |
| **Security** | SAP XSUAA | OAuth2 authentication and role-based access control |
| **Runtime** | Cloud Foundry | Deployment platform for CAP applications |
| **Dev Environment** | Business Application Studio | Development workspace for CAP & Integration Suite |

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
│       ├── HasNewData.groovy          # Check for new data logic
│       ├── LogAndModify.groovy        # Logging and data modification
│       ├── SetTimestampFromMessage.groovy  # Extract timestamp from message
│       ├── SetTimestampToMessage.groovy    # Set timestamp to message
│       └── TransformOrdersToSQL.groovy     # Transform orders to SQL format
├── hana-cloud/
│   └── schema/
│       └── createSchemaAndTable.sql   # HANA database schema creation
├── docs/
│   └── demo-screenshots/              # Project documentation and screenshots
├── .gitignore
└── README.md
```

---

## 🧠 Setup Instructions

### Prerequisites

- SAP BTP Trial or PAYG account (region: eu10 or us10)
- Cloud Foundry enabled
- Business Application Studio or local dev setup (Node.js 18+, cf CLI, cds)

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/btp-legacy-integration-hub.git
   cd btp-legacy-integration-hub
   ```

2. **Deploy HANA Cloud instance**
   - Create schema `POLLING_DEMO`
   - Execute the SQL scripts from `hana-cloud/schema/`

3. **Run the mock ECC API**
   ```bash
   cd cap-app
   cds watch
   ```

4. **Deploy CAP app to Cloud Foundry**
   ```bash
   mbt build
   cf deploy mta_archives/<file>.mtar
   ```

5. **Import and deploy the Integration Flow**
   - Timer start event (configurable interval)
   - REST call to CAP API endpoints (`/Orders`, `/Customers`)
   - Data transformation and enrichment
   - JDBC adapter to persist data in HANA Cloud

6. **Verify data synchronization**
   - Check HANA Cloud database for synchronized data
   - Test OAuth2-secured endpoints

---

## 🔐 Security & Roles

`xs-security.json` configuration:

```json
{
  "xsappname": "btp-legacy-integration-hub",
  "tenant-mode": "shared",
  "scopes": [
    { "name": "$XSAPPNAME.Viewer", "description": "Read access" },
    { "name": "$XSAPPNAME.Admin", "description": "Full access" }
  ],
  "role-templates": [
    { "name": "Viewer", "description": "Can view data", "scope-references": ["$XSAPPNAME.Viewer"] },
    { "name": "Admin", "description": "Can manage data", "scope-references": ["$XSAPPNAME.Admin"] }
  ]
}
```

---

## 🧱 Clean Core Principles

- **No modification** of legacy ECC system
- **API-first approach** for data extraction
- **Secure integration** with OAuth2 and XSUAA
- **Cloud-native data persistence** in HANA Cloud
- **Separation of concerns** between legacy, integration, and data layers
- **Scalable, maintainable architecture** on SAP BTP