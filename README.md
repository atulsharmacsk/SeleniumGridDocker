
---

# 📖 Selenium DriverManager with Docker

This project demonstrates how to manage **local and remote WebDriver instances** in Selenium using a custom `DriverManager` class. It supports running tests both on a local machine and inside a **Dockerized Selenium Grid**.

---

## 🚀 Getting Started

### Prerequisites
- Java 17+  
- Gradle or Maven (build tool)  
- Docker installed and running  
- TestNG framework  

---

## ⚙️ Project Structure

- **DriverManager**: Handles WebDriver initialization.  
  - **Local Driver** → Uses WebDriverManager to set up ChromeDriver.  
  - **Remote Driver** → Connects to a Selenium Grid running in Docker (default hub at `http://localhost:4444`).  
- **Tests**: Example test classes (e.g., `LoginTest`) that use `DriverManager` to run against the OrangeHRM demo site.  

---

## 🧪 Test Flow

1. **Setup**  
   - Choose driver type via system property (`EXEC=local` or `EXEC=remote`).  
   - For local execution, ChromeDriver is managed automatically.  
   - For remote execution, tests connect to a Selenium Grid running in Docker.  

2. **Execution**  
   - Tests interact with the OrangeHRM demo login page.  
   - Assertions validate login behavior (e.g., invalid credentials message).  

3. **Teardown**  
   - Browser is closed after test execution.  

---

## 🐳 Running with Docker

1. Pull Selenium Grid images:  
   ```bash
   docker pull selenium/hub
   docker pull selenium/node-chrome
   docker pull selenium/node-firefox
   ```

2. Start Selenium Grid:  
   ```bash
   docker network create grid
   docker run -d -p 4444:4444 --net grid --name selenium-hub selenium/hub
   docker run -d --net grid --name chrome-node -e HUB_HOST=selenium-hub selenium/node-chrome
   docker run -d --net grid --name firefox-node -e HUB_HOST=selenium-hub selenium/node-firefox
   ```

3. Run tests with remote driver:  
   ```bash
   ./gradlew test -DEXEC=remote
   ```

4. Run tests locally:  
   ```bash
   ./gradlew test -DEXEC=local
   ```

---

## 📊 Why This Setup?

- ✅ Flexible execution (local or remote)  
- ✅ Easy scaling with Dockerized Selenium Grid  
- ✅ Cleaner test code with centralized driver management  
- ✅ Supports cross‑browser testing (Chrome, Firefox)  

---

## 📌 Next Steps

- Add support for more browsers (Edge, Safari).  
- Integrate with CI/CD pipelines (GitHub Actions, Jenkins).  
- Parameterize grid URL and browser options.  
- Extend reporting with Allure or Extent Reports.  

---

👉 Do you want me to also add a **diagram (text-based)** showing the flow: *Test → DriverManager → Local Driver OR Remote Driver → Selenium Grid (Docker)*? That would make the README even more visual and beginner-friendly.
