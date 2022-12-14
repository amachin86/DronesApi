
## Drones Service REST API


---

:scroll: **START**


### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

---

### Task description

We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones (i.e. **dispatch controller**). The specific communicaiton with the drone is outside the scope of this task. 

The service should allow:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone; 
- checking available drones for loading;
- check drone battery level for a given drone;

> Feel free to make assumptions for the design approach. 

---

### Requirements

While implementing your solution **please take care of the following requirements**: 

#### Functional requirements

- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event log for this.

---

#### Non-functional requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- JUnit tests are optional but advisable (if you have time);
- Advice: Show us how you work through your commit history.

---
### How to build

#### Requirements

- Java 11
- Java IDE (IntelliJ IDEA)
- MYSQL databse (Optional you can use in-memory databse)
- Postman(For testing ) 
 
### Steps by step for building and runing the project locally

- Clone the from the link git clone https://github.com/amachin86/DronesApi

- Open the cloned project in IntelliJ

- Go to maven the update Project to update all the maven dependencies

- Maven Build the project and run

- Before running you can run the JUnit test cases to assert that everything is working correctly (I have included some of the JUnit tests)


---

### Testing the API
- Some of the assumption made for the purpose of this API design are:-

- Once the Medication is loaded to a specific drone it cannot be loaded to another drone at the same time.

Open Postman
For testing purpose the API is secured and you will have to specify the Authorization in the headers as Basic Auth

Username **admin**

Password **admin**

Note: the ContentType is application/json

[![D:\Musala Imagenes\register1.png](https://github.com/amachin86/DronesApi/blob/master/src/main/resources/assert/register1.png)]

**Registering a drone** localhost:8090/apidrone/register
The payload should be in json format like this

[![D:\Musala Imagenes\register.png)](https://github.com/amachin86/DronesApi/blob/master/src/main/resources/assert/register.png)]

The response should be 

[![D:\Musala Imagenes\repuesta.png](https://github.com/amachin86/DronesApi/blob/master/src/main/resources/assert/repuesta.png)

---
- **Checking available drones for loading;**


Before loading a drone with Medication you can first check the available drones to confirm that the drone is not in use

**localhost:8090/apidrone/available**

[![available.png](https://github.com/amachin86/DronesApi/blob/master/src/main/resources/assert/available.png)]

---
- **Loading a drone with medication items;** 
 
**localhost:8090/apidrone/load**

The payload will have the following fields

- serialNumber is the unique serial for the drone being loaded
- code id the unique code for the medication load being loaded to the drone
- source is the loading point
- destination is where the load is being taken

the Medication items to be loaded for testing are code : **ME2902344, ME2892345, ME9864347, ME2323900**

the destination and the source are any places

 - The serialNumber is the unique serialNumber a drone that you register

[![D:\Musala Imagenes\load.png](https://github.com/amachin86/DronesApi/blob/master/src/main/resources/assert/load.png)

--- 
- **Checking loaded medication items for a given drone;**

**localhost:8090/apidrone/details/QR345K89789**

- Check which medication item is loaded to a specific drone.

[![D:\Musala Imagenes\details.png](https://github.com/amachin86/DronesApi/blob/master/src/main/resources/assert/details.png)

---

- **Check drone battery level for a given drone;**

**localhost:8090/apidrone/battery**

[![D:\Musala Imagenes\battery.png](https://github.com/amachin86/DronesApi/blob/master/src/main/resources/assert/battery.png)

---
- **Delivery of medication item**

**localhost:8090/apidrone/deliver**

When the drone delivers the item it call this end-point and its status is change drop loaded to delivering then delivered

[![D:\Musala Imagenes\deliver.png](https://github.com/amachin86/DronesApi/blob/master/src/main/resources/assert/deliver.png)

---

:scroll: **END** 


