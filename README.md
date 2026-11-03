A mock draft app. Set up a draft with your number of teams, rounds and starting budget, then draft players from a roster you import.

this app contains the two following artifacts 

- **Frontend:** React + Vite, in `frontend/`. This is the page you see in the browser.

- **Backend:** Java + Javalin, in `backend/`. This holds the draft logic and answers the frontend's requests.

## Requirements

must installs:

- [Java 17 or newer](https://adoptium.net/)
- [Maven](https://maven.apache.org/install.html) (on Mac: `brew install maven`)
- [Node.js 22 or newer](https://nodejs.org/), which includes npm


version checks

```
java -version
mvn -version
node -v
```
## setup

```
git clone https://github.com/noelb67750/Ball-Knowledge.git
cd Ball-Knowledge/frontend
npm install
```

needs two terminals open simultaniously 

Term 1: 

```
cd Ball-Knowledge/backend
mvn compile exec:java
```

Term 2:

```
cd Ball-Knowledge/frontend
npm run dev
```

then run in your browser
**http://localhost:5173**
