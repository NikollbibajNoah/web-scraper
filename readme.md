> [!NOTE]
> This project is work in progress and is not yet complete. The README will be updated as the project progresses.

# Web Scraper
This project is a web scraper built with Python 3.14 using message broker to connect systems.
It is a learning project to practice web scraping, software-architecture and security. Data is
getting scraped with python and sent with message broker to spring boot backend which handles data
correctly and saves it to database.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)

## Features
- Data scraping from job listing website
- Data handling and monitoring with message broker
- Store scraped data from listener into database with jpa

## Technologies
Following technologies are used in this project:

- Python 3.14
- Docker
- RabbitMQ (rabbitmq 3-management)
- Spring Boot (Springboot 4.05, Java 26)
- PostgreSQL (Latest)

## Installation

### Scraper
To run this project, make sure **Docker** is installed and working and (recommended) **Python 3.14** installed.
If ready follow the steps below:
1. Clone repository
2. Rename .env.example to .env and set yourself a **username, password** and **queue name** for rabbitmq
3. Inside **/scraper** run ```pip install```
4. Inside project root run ```docker compose up --build -d```
5. Inside **/scraper** run ```python main.py``` (or in project root ```python scraper/main.py```)
6. Open rabbitmq dashboard at: http://localhost:15672/#/ to see scraped data