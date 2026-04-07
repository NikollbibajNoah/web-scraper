> [!NOTE]
> This project is work in progress and is not yet complete. The README will be updated as the project progresses.

# Web Scraper
This project is a web scraper built with Python 3.14 using message broker to connect systems.
It is a learning project to practice web scraping, software-architecture, microservices and security. Data is
getting scraped with python and sent with message broker to spring boot backend which handles data
correctly and saves it to database.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [API](#api)
- [Configuration](#configuration)
- [Installation](#installation)

## Features
- Data scraping from job listing website
- Duplicate detection - only new jobs stored
- Data handling and monitoring with message broker
- Store scraped data from listener into database with JPA
- REST API to query and search jobs with pagination

## Technologies
Following technologies are used in this project:

- Python with libraries:
  - Playwright 1.58
  - Pika (RabbitMQ client) 1.3.2
  - Python-dotenv 1.1.0
- Docker
- RabbitMQ 3-management
- Spring Boot 4.0.5 (Java 21)
- PostgreSQL (Latest)

## Project Structure
````
webscraper/
├── scraper/
│   ├── config.py
│   ├── rabbitmq.py
│   ├── scraper.py
│   ├── main.py
│   ├── requirements.txt
│   └── Dockerfile
├── backend/
│   ├── src/
│   │   └── main/java/scraper/backend/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── listener/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   ├── pom.xml
│   └── Dockerfile
├── .env.example
└── docker-compose.yml
````

## Architecture
````
Scraper (Python)
│
│ publishes jobs
▼
RabbitMQ (Message Broker)
│
│ consumes messages
▼
Backend (Spring Boot)
│ filters duplicates
│ saves new jobs
▼
PostgreSQL (Database)
│
│ REST API
▼
Frontend
````

## API
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/jobs` | Get all jobs (paginated) |
| GET | `/api/jobs?page=0&size=20` | Get jobs with pagination |
| GET | `/api/jobs/search?query=java` | Search jobs by title, company or location |
| GET | `/api/jobs/search?query=java&page=0&size=20` | Search with pagination |

## Configuration
In root of the project, there is a `.env.example` file which contains all the environment variables needed for the project.

```env
# Postgres DB
POSTGRES_HOST=db
POSTGRES_PORT=5432
POSTGRES_USER=user
POSTGRES_PASSWORD=password
POSTGRES_DB=jobs

# Rabbitmq
RABBITMQ_HOST=rabbitmq
RABBITMQ_PORT=5672
RABBITMQ_DEFAULT_USER=user
RABBITMQ_DEFAULT_PASS=password

RABBITMQ_QUEUE=jobs
RABBITMQ_VHOST=/

# Backend
SERVER_PORT=8080
ALLOWED_ORIGIN_URL=http://localhost:3000
```

## Installation
To run this project, make sure **Docker** is installed and working.
If ready, follow the steps below:

1. Clone repository
2. Rename `.env.example` to `.env` and set a custom **username, password** and **queue name** for RabbitMQ and the database
3. Build images with `docker compose build`
4. Run containers with `docker compose up -d`
5. Containers are running and the backend is available at http://localhost:8080 and RabbitMQ management interface at http://localhost:15672

To check containers, run:
```powershell
docker compose ps
docker compose logs -f backend
```

> [!TIP]
> To trigger the scraper manually run: `docker compose run --rm scraper`

## Troubleshooting
### Backend: `Connection to localhost:5432 refused`
**Cause:** `POSTGRES_HOST` is wrong. In Docker you must use service names, not `localhost`.
**Fix:** set in `.env`:
```env
POSTGRES_HOST=db
```

```powershell
docker compose down
docker compose up -d
docker compose logs -f backend
```

### Scraper: `pika.exceptions.AMQPConnectionError`
**Cause:** RabbitMQ not ready / wrong host / wrong credentials.
**Fix:** set in `.env`:
```env
RABBITMQ_HOST=rabbitmq
RABBITMQ_DEFAULT_USER=user
RABBITMQ_DEFAULT_PASS=password
```

Check:
```powershell
docker compose ps
docker compose logs -f rabbitmq
```

### Postgres: `relation "jobs" does not exist`
**Cause:** fresh/empty DB volume or backend did not initialize schema yet.
**Fix:** restart the stack:
```powershell
docker compose down
docker compose up -d
docker compose logs -f backend
```
