# Allegro Sandbox API – Rest Assured Test Framework

## Why WireMock is used

Allegro Sandbox API introduces constraints that make fully live, repeatable API
test automation impractical without additional external tooling.

Key limitations include:
- OAuth authorization code requires manual browser login  
  (possible to automate with external tools, but outside the scope of this repository),
- short-lived access tokens and one-time refresh tokens,
- business rules dependent on mutable external state and asynchronous processing.

To ensure deterministic, repeatable and CI-friendly tests, this project introduces
a mock execution mode based on WireMock.

### Scope of mocking

WireMock in this repository is not a generic mock API.

Mocked endpoints and scenarios are implemented only for the test cases covered by
this project. Each stub exists to support a concrete test scenario and demonstrate
API test design, not to simulate a full backend.

The goal of this repository is test automation demonstration, not building a
reusable API simulator.

Live mode is preserved for integration and contract verification, while mock mode
is the recommended default for automation.

---

## Overview

This repository contains an API test automation framework built with Java and
Rest Assured, targeting the Allegro Sandbox API.

The project demonstrates:
- handling OAuth token lifecycle internally,
- stable API testing using mocked and live environments,
- clean separation of responsibilities in test code.

---

## Tech Stack

- Java, Maven  
- Rest Assured, JUnit 5  
- WireMock, Awaitility  
- Jackson, Lombok  
- Allure (REST integration)

---

## Test Coverage

- Product proposals  
  - valid and invalid ISBN scenarios  
  - duplicate product detection
- Product search
- User information (/me)  
  - contract validation  
  - authorization error handling (401)

---

## Running the tests

### Mock mode (recommended)

mvn test -Denv=mock

#### Example commands

mvn test -Denv=mock -Dtest=CreateProductProposalTest  
mvn test -Denv=mock -Dtags=Regression

WireMock is started automatically in mock mode.

### Live mode (Allegro Sandbox)

mvn test

Requires valid credentials provided via environment variables or a local .env file
(not committed to the repository).

---
```
## Project structure (simplified)
src
├── main/java
│   ├── api        # API clients
│   ├── helpers    # Authentication helpers
│   ├── utils      # Configuration & token handling
│   └── factory    # Test data builders
│
└── test
    ├── java/tests # API tests
    └── resources  # WireMock stubs & schemas
```
---

## Design notes

- Mock mode ensures stable and repeatable test execution
- No Thread.sleep() – Awaitility is used instead
- Factories and argument providers prevent fragile test data
- Focus on API and integration behavior rather than low-value unit tests

---

## Status

- CI-ready  
- Portfolio-ready  
- Production-style API test framework  

---

## Author

Łukasz Jankowski
