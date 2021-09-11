# Force Of Sales

## Table of Contents

1. [Introduction](#Introduction)
2. [Functionality](#Functionality)
    1. [SalesReps](#SalesReps)
    2. [Lead](#Lead)
    3. [Opportunity](#Opportunity)
    4. [Contact](#Contact)
    5. [Account](#Account)
    6. [Report](#Report)
3. [User Tutorial](#User-Tutorial)
4. [Class Diagram](#Class-Diagram)
5. [Use Case Diagram](#Use-Case-Diagram)
### Introduction

Force of Sales is a compact, modern **CRM** (Customer Relationship Management) system for tracking prospective and existing customers through the sales process.

Our current client is the LBL Trucking Company which sells fleets of Trucks to large companies all over the world.

### Functionality
#### 1. SalesReps
- SalesReps can be added by typing the command **New SalesRep**
- SalesReps will be associated with Leads during the creation process
- When converting a Lead to an Opportunity, the same SalesRep will be associated to the Opportunity
- A list of all SalesReps can be displayed by typing **Show SalesReps**
#### 2. Lead
- Leads can be added to the CRM by typing the command **New Lead**
- A Lead contains all the contact information of the prospective buyer:
  name, phone number, email, and company name
- An individual Lead’s details can be displayed by typing **Lookup Lead {id}** (example: lookup lead 1)
- A list of all Leads’ ids and names can be displayed by typing **Show Leads**
- Export a list of Leads by typing **Export Leads**
#### 3. Opportunity
- A Lead can be converted to an Opportunity by typing **Convert {id}** (example: convert 3)
- Opportunity is composed of the Contact data from Lead and additional information concerning the type (*BOX*, *HYBRID*, *FLATBED*)
  and quantity of the product the buyer is interested in
- Contact is set as the decisionMaker for the new Opportunity and its status set to *OPEN*
- The CRM can display the Opportunity's information by typing **Lookup Opportunity {id}** (example: lookup opportunity 1) 
- The CRM can display the Opportunity's set its status by typing **Closed-lost {id}** or **Closed-win {id}** (example: closed-lost 7)
- A list of all Opportunities can be displayed by typing **Show Opportunities**
#### 4. Contact
- A contact is created when converting a Lead
- An individual Contact’s details can be displayed by typing **Lookup Contact {id}** (example: lookup contact 1)
- A list of all Contacts can be displayed by typing **Show Contacts**
#### 5. Account
- Opportunities are associated with Accounts
- Opportunities form a part of the Account which stores all the buyer's data, including details about their
  company: industry type (*PRODUCE*, *ECOMMERCE*, *MANUFACTURING*, *MEDICAL*, *OTHER*), number of employees,
  city and country
- An individual Account’s details can be displayed by typing **Lookup Account {id}** (example: lookup account 1)
- A list of all Accounts can be displayed by typing **Show Accounts**
- Export a list of Accounts by typing **Export Accounts**
#### 6. Report
- Get a count of Leads by SalesRep, Country, City or Industry by typing **Report Lead by {SalesRep / Country / City / Industry}** (example: Report Lead by SalesRep)
- Get a count of all Opportunities by SalesRep, Product, Country, City or Industry by typing **Report Opportunity by {SalesRep / Product / Country / City / Industry}** (example: Report Opportunity by Product)
- Get a count of CLOSED_WON Opportunities by SalesRep, Product, Country, City or Industry by typing **Report CLOSED-WON by {SalesRep / Product / Country / City / Industry}** (example: Report CLOSED-WON by City)
- Get a count of CLOSED_LOST Opportunities by SalesRep, Product, Country, City or Industry by typing **Report CLOSED-LOST by {SalesRep / Product / Country / City / Industry}** (example: Report CLOSED-LOST by Country)
- Get a count of OPEN Opportunities by SalesRep, Product, Country, City or Industry by typing **Report OPEN by {SalesRep / Product / Country / City / Industry}** (example: Report OPEN by SalesRep)
- Get the Mean, Median, Max, Min values for EmployeeCount or Quantity by typing **{Mean / Median / Max / Min} {EmployeeCount / Quantity}** (example: Mean EmployeeCount or Min Quantity)
- Get the Mean, Median, Max, Min values for Opportunity by typing **{Mean / Median / Max / Min} Opps per Account** (example: Max Opps per Account)
### User Tutorial
- Start the program by running the Main class
- All the commands are case-insensitive
- The menu offers the **help** option which lists all the possible commands as well as the ability to quit
  the program

### Class Diagram
![Class diagram](src/main/java/com/ironhack/FabFour_ForceOfSalesHomework3/assets/Class diagram_Homework3.png?raw=true "Class diagram")

### Use Case Diagram
![Use case diagram](src/main/java/com/ironhack/FabFour_ForceOfSalesHomework3/assets/Use Case diagram_Homework3.png?raw=true "Use case diagram")





