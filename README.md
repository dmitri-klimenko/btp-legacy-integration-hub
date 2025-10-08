# 🚀 BTP Legacy Integration Hub

End-to-end **SAP Business Technology Platform (BTP)** scenario connecting a simulated **legacy ECC system** with **SAP HANA Cloud** using **Integration Suite**, **CAP**, and **XSUAA**.

**Goal:** Demonstrate a clean-core modernization approach — integrating, extending, and securing enterprise data without modifying ECC.

---

## 📖 Table of Contents

1. [Project Overview](#project-overview)
2. [Architecture](#architecture)
3. [Technologies Used](#technologies-used)
4. [Business Scenario](#business-scenario)
5. [Repository Structure](#repository-structure)
6. [Setup Instructions](#setup-instructions)
7. [Security & Roles](#security--roles)
8. [Clean Core Principles](#clean-core-principles)
9. [Next Steps](#next-steps)

---

## 🧩 Project Overview

This project simulates a **migration from SAP ECC to SAP HANA Cloud** using SAP BTP as an integration and extension layer.

**Key capabilities:**
- Extract and transform legacy data (orders/customers)
- Load and expose data in SAP HANA Cloud
- Extend business logic via CAP-based side-by-side application
- Secure APIs with XSUAA and OAuth2
- Optional workflow automation with SAP Build Process Automation

---

## 🏗️ Architecture

The project follows a **clean-core integration pattern**:

```
Legacy ECC (Simulated CAP API)
    ↓
Integration Suite iFlow (REST adapter, field mapping)
    ↓
SAP HANA Cloud (HDI Container)
    ↓
CAP App (Node.js OData/REST APIs)
    ↓
XSUAA (OAuth2 Security)
    ↓
External Consumers
```

**Architecture diagram:** `docs/architecture-diagram.png`

---

## 🧰 Technologies Used

| Layer | Service/Tool | Purpose |
|-------|-------------|---------|
| **Integration** | SAP Integration Suite | Orchestrate and transform data from ECC to HANA |
| **Data** | SAP HANA Cloud | Store and query integrated business data |
| **Extension** | SAP CAP (Node.js) | Side-by-side API and business logic layer |
| **Security** | SAP XSUAA | OAuth2 authentication and role-based access |
| **Workflow** | SAP Build Process Automation | Approve high-value orders (optional) |
| **Runtime** | Cloud Foundry | Deploy CAP and integration artifacts |
| **Dev Environment** | Business Application Studio | Development workspace for CAP & iFlows |

---

## 💼 Business Scenario

A manufacturing company is transitioning from SAP ECC to SAP S/4HANA Cloud. During migration, customer and order data remains in ECC. SAP BTP acts as the integration bridge, ensuring data consistency and providing modern APIs without modifying ECC.

**This project demonstrates:**
- API-based data exchange
- Secure, cloud-based persistence
- Extensible, clean-core architecture

---

## 📁 Repository Structure

```
btp-legacy-integration-hub/
├── cap-app/
│   ├── db/
│   ├── srv/
│   ├── package.json
│   ├── xs-security.json
│   └── mta.yaml
├── integration-suite/
│   ├── iflow/
│   └── api-proxy/
├── hana-cloud/
│   └── schema/
├── workflow/
│   └── approval-process.bpa
├── docs/
│   ├── architecture-diagram.png
│   └── demo-screenshots/
└── README.md
```

---

## 🧠 Setup Instructions

### Prerequisites

- SAP BTP Trial or PAYG account (eu10 or us10)
- Cloud Foundry enabled
- Business Application Studio or local setup (cf CLI, cds, Node.js 18+)

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/btp-legacy-integration-hub.git
   cd btp-legacy-integration-hub
   ```

2. **Deploy HANA Cloud instance** (2GB trial)

3. **Run the mock ECC API**
   ```bash
   cd cap-app
   cds watch
   ```

4. **Import and deploy the iFlow** in Integration Suite

5. **Deploy CAP app to Cloud Foundry**
   ```bash
   mbt build
   cf deploy mta_archives/<file>.mtar
   ```

6. **Test OData endpoints** via Postman (OAuth2 token from XSUAA)

---

## 🔐 Security & Roles

**xs-security.json configuration:**

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

**Bind the XSUAA service:**
```bash
cf bind-service cap-app xsuaa-service
```

---

## 🧱 Clean Core Principles

- No modification of ECC
- Reuse of standard APIs and BTP services
- Side-by-side extensions on BTP
- Secure, isolated integrations
- Cloud-native deployment and scalability

---

## 🪴 Next Steps

- Integrate SAP Event Mesh for async events
- Add SAP Build Apps front-end dashboard
- Connect SAP Analytics Cloud for reporting
- Migrate project to PAYG subaccount for persistence

---

## 👤 Author

**Dmytro Klymenko**  
SAP BTP Architect & Integrator