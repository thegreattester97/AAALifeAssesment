
# SDET Technical Assignment
This repository contains a small technical showcase covering:
- A focused **API test suite** exercising the Restful Booker public API
- A short **UI smoke test** flow targeting the SauceDemo ecommerce website

The goal of this project is to demonstrate production-ready test automation practices using a clear compact scope.

---

## 🧩 Technologies Used
| Area | Tool |
|---|---|
| Language | Java 11+ |
| Build tool | Maven |
| API | RestAssured |
| UI | Selenium WebDriver |
| Test Runner | TestNG |
| JSON Schema Validation | json-schema-validator |
| Browser Drivers | WebDriverManager |
| Documentation | Gsuite |

---

## ✔️ Requirements Implemented
### API Test Suite
- Tested 3+ endpoints (`auth`, `booking`, `booking/{id}`)
- Positive tests
- Negative & unhappy paths
- Boundary validations
- JSON Schema contract checks
- Data-driven tests from external JSON/CSV
- Logging of requests/responses on failure

### UI Smoke Flow
- Page Object Model
- Login to saucedemo.com
- invalid login failure test 
- Validate inventory amounts
- Add item to cart
- Assert cart count
- Explicit waits (no illadvised sleeps)
- Screenshot + HTML dumps upon failure ( Purpose Automation Test Failure on Test UI-001 Previously Logged)

---

## 🔧 Running Tests
### Run everything
```bash
mvn clean test
```

### Run UI only
```bash
mvn -Dtest=LoginTests test ....ETC
```

### Select browser
```bash
mvn clean test -Dbrowser=chrome (declared at the base test level)
```

### Run API only
```bash
mvn -Dtest=BookingApiTests test
mvn -Dtest=AuthApiTests test
```


---

## ⚙️ Configuration
`src/test/resources/config.properties`

```
base.api.url=https://restful-booker.herokuapp.com
saucedemo.url=https://www.saucedemo.com
browser=chrome
headless=false
```

---

## 📁 Project layout
```
src/test/java
  /api
  /test (UI)
  /listeners
src/test/resources
  /schemas
  /testdata
pom.xml
README.md
testng.xml
```

---

## 📝 Assumptions
- Public target endpoints remain available
- Standard SauceDemo credentials (standard_user / secret_sauce)
- Response models follow public documentation
- The purpose of this implementation is to asses to abiity to implement a framework not to identify prodcution Bugs
- Reduction of dependcies to reduce deployment/execution time not neccesary
- Parralel / prioritizaon  of text execution not important 
- Test suite not being ran in a headless enviorment 
- Test Suite not being ran outside of developers machine 

---

## 🔜 Next Steps (If more time)
- Retry logic for flakiness
- Parallelization & CI integration
- Enter logging and console communications for error handling and bug identification
- Test Coverage analysis - Expanding Coverage
- Test Risk assesment and mitigatio planning 
- Complete Testng Congfiguration so the Suite is more system agnostic 



---

## 👤 Author
Name: *Daniel B. (Candidate)*  
Role: QA Automation / SDET


