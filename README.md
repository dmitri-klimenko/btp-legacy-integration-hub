# 🚀 BTP Legacy Integration Hub

End-to-end **SAP Business Technology Platform (BTP)** scenario connecting a simulated **legacy ECC system** with **SAP HANA Cloud**, **Integration Suite**, **CAP**, and **XSUAA**.

**Goal:** Demonstrate a clean-core modernization journey — integrating, extending, and securing enterprise data without modifying ECC — and then extending it further through **ABAP RAP** and **SAP Fiori (UI5)**.

---

## 📖 Table of Contents

1. [Project Overview](#-project-overview)
2. [Architecture](#️-architecture)
3. [Technologies Used](#-technologies-used)
4. [Business Scenario](#-business-scenario)
5. [Repository Structure](#-repository-structure)
6. [Setup Instructions](#-setup-instructions)
7. [Security & Roles](#-security--roles)
8. [Learning Roadmap](#-learning-roadmap)
9. [Clean Core Principles](#-clean-core-principles)
10. [Next Steps](#-next-steps)
11. [Author](#-author)

---

## 🧩 Project Overview

This project simulates a **migration from SAP ECC to SAP HANA Cloud**, using SAP BTP as the integration and extension platform.

**Key capabilities:**
- Extract and transform data from a simulated legacy ECC system (CAP app)
- Persist and enrich data in SAP HANA Cloud via Integration Suite
- Extend business logic via RAP and CAP-based applications
- Secure APIs with XSUAA and OAuth2
- Build modern Fiori UI5 application using Business Application Studio

---

## 🏗️ Architecture

The solution demonstrates a **clean-core modernization pipeline**:

```
Legacy ECC Simulation (CAP Node.js + XSUAA)
          ↓
Integration Suite iFlow (Timer → REST → JDBC)
          ↓
SAP HANA Cloud (HDI Container)
          ↓
ABAP RAP Application (extension layer)
          ↓
SAP Fiori UI5 App (Business Application Studio)
```

### 🔁 Implementation Status

| Layer | Status | Description |
|-------|--------|-------------|
| **Legacy System (CAP)** | ✅ Done | CAP app exposes `/Orders` and `/Customers` as OData services, secured via XSUAA |
| **Integration Layer** | ✅ Done | iFlow runs every minute, transforms + enriches data, and stores it in HANA Cloud |
| **Database Layer (HANA Cloud)** | ✅ Done | Schema `POLLING_DEMO` created, connected via JDBC adapter |
| **Extension Layer (RAP)** | 🔜 Next | RAP app will extend schema with additional fields and OData V4 services |
| **UI Layer (Fiori)** | 🔜 Next | Fiori UI5 app built in BAS consuming RAP OData service |

---

## 🧰 Technologies Used

| Layer | Service/Tool | Purpose |
|-------|---------------|----------|
| **Integration** | SAP Integration Suite | Polling, transformation, persistence via JDBC adapter |
| **Data** | SAP HANA Cloud | Central data store for synchronized business data |
| **Backend Extension** | SAP CAP (Node.js) | Simulated ECC system and initial API provider |
| **Security** | SAP XSUAA | OAuth2 security and role management |
| **Extension** | SAP ABAP RAP | Modern business logic layer on top of HANA schema |
| **Frontend** | SAP Business Application Studio | Fiori UI5 application development |
| **Workflow** | SAP Build Process Automation | Optional: approval workflow for high-value orders |
| **Runtime** | Cloud Foundry | Deployment platform for CAP and RAP apps |
| **Dev Environment** | Business Application Studio | Development workspace for CAP, RAP & iFlows |

---

## 💼 Business Scenario

A manufacturing company is transitioning from **SAP ECC** to **SAP S/4HANA Cloud**.  
During migration, order and customer data remain in ECC. SAP BTP acts as the **integration and extension bridge**, ensuring data consistency and providing cloud APIs without modifying the legacy core.

**This project demonstrates:**
- End-to-end cloud integration between systems  
- Secure data persistence in HANA Cloud  
- Modern OData services via CAP and RAP  
- UI consumption through Fiori UI5 applications  

---

## 📁 Repository Structure

```
btp-legacy-integration-hub/
├── cap-app/                    # Simulated legacy ECC system (CAP)
│   ├── db/
│   ├── srv/
│   ├── package.json
│   ├── xs-security.json
│   └── mta.yaml
├── integration-suite/          # iFlow + API proxies
│   ├── iflow/
│   └── api-proxy/
├── hana-cloud/                 # SQL scripts (schema, tables, inserts)
│   └── schema/
├── rap-app/                    # ABAP RAP application (extension)
│   └── src/
├── fiori-app/                  # SAP Fiori UI5 application (BAS)
│   ├── webapp/
│   ├── ui5.yaml
│   └── manifest.json
├── workflow/                   # Optional: Build Process Automation files
│   └── approval-process.bpa
├── docs/
│   ├── architecture-diagram.png
│   └── demo-screenshots/
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

5. **Import and deploy the iFlow**
   - Use Timer start event (1 min interval)
   - REST call to CAP API `/Orders`
   - Add dynamic fields via Groovy
   - Store data in HANA Cloud using JDBC adapter

6. **Test OData endpoints**
   - Obtain OAuth2 token from XSUAA
   - Test APIs via Postman

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

Bind XSUAA service to CAP app:

```bash
cf bind-service cap-app xsuaa-service
```

---

## 🎓 Learning Roadmap

| Phase | Goal | Key Focus Areas |
|-------|------|-----------------|
| 1️⃣ Integration Foundation | ✅ Complete data sync pipeline | CAP (Node.js), Integration Suite, Groovy scripting, JDBC adapter |
| 2️⃣ ABAP RAP Extension | 🔜 Build RAP service on existing schema | RAP model, CDS views, Behavior Definitions, Service Binding |
| 3️⃣ SAP Fiori UI5 Application | 🔜 Create Fiori app consuming RAP OData | BAS, Fiori Elements, SAPUI5, OData V4 |
| 4️⃣ Eventing & Analytics | ⏳ Add event-driven updates & dashboards | SAP Event Mesh, SAP Analytics Cloud |
| 5️⃣ Security & Deployment Hardening | ⏳ Prepare enterprise-grade setup | PAYG subaccount, IAS integration, CI/CD pipelines |

---

## 🧱 Clean Core Principles

- No modification of legacy ECC system
- Reuse of standard SAP APIs and services
- Side-by-side extensions on BTP (CAP + RAP)
- Clear separation of concerns (Integration, Data, Logic, UI)
- Secure, isolated architecture with XSUAA and OAuth2
- Cloud-native, scalable design

---

## 🪴 Next Steps

1. **Design RAP data model**
   - Reuse `POLLING_DEMO.ORDERS` and `CUSTOMERS`
   - Add 1–2 extension fields (`SyncedAt`, `ExternalStatus`)
   - Define CDS and Behavior Definitions

2. **Expose RAP OData V4 service**
   - Deploy to ABAP environment
   - Test via `/sap/opu/odata4/` endpoint

3. **Create SAP Fiori UI5 application in BAS**
   - Connect to RAP OData V4 service
   - Build Fiori Elements app (List Report, Object Page)
   - Use SAPUI5 framework for custom UI logic

4. **(Optional) Integrate SAP Build Process Automation**
   - Approve high-value orders based on `Amount > 1000`

---

## 👤 Author

**Dmytro Klymenko**  
SAP BTP Architect & Integrator  
Passionate about clean-core modernization, integration, and side-by-side extensions.