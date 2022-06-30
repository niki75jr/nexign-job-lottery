# Nexign career upgrade 24h: Lottery

##  Getting Started

### Requirements (for Ubuntu)

##### Docker

    mvn clean package
    docker-compose up --build

## Specification

| URL                                  | Request | Data                          | HTTP Codes             |
| ------------------------------------ | ------- | ----------------------------- | ---------------------- |
| /lottery/start                       | GET     | URL                           | 200 - OK               |
|                                      |         |                               | 500 - Internal Error   |
|                                      |         |                               |                        |
| /lottery/winners                     | GET     | URL                           | 200 - OK               |
|                                      |         |                               | 500 - Internal Error   |
| /lottery/participant/generate/{size} | GET     | URL                           | 200 - OK               |
|                                      |         | size: Integer                 | 400 - Invalid Data     |
|                                      |         | (1 <= size <= 100)            | 500 - Internal Error   |
|                                      |         |                               |                        |
| /lottery/participant                 | GET     | URL                           | 200 - OK               |
|                                      |         |                               | 500 - Internal Error   |
|                                      |         |                               |                        |
| /lottery/participant                 | POST    | JSON                          | 200 - OK               |
|                                      |         | firstName: String (not blank) | 400 - Invalid Data     |
|                                      |         | lastName: String (not blank)  | 500 - Internal Error   |
|                                      |         | age: Integer                  |                        |
|                                      |         | (18 <= age <= 150)            |                        |
|                                      |         | city: String (not blank)      |                        |
|                                      |         |                               |                        |
| /lottery/participant/batch           | POST    | JSON                          | 200 - OK               |
|                                      |         | List<Participant\>            | 400 - Invalid Data     |
|                                      |         |                               | 500 - Internal Error   |


## Database Scheme

Database: PostgreSQL 12

Number of tables: 2

Table 1: participants

| Column         | Type        | Atributes                |
| -------------- | ----------- | ------------------------ |
| id             | BIGSERIAL   | PK                       |
| first_name     | VARCHAR(50) | NOT NULL                 |
| last_name      | VARCHAR(50) | NOT NULL                 |
| age            | INTEGER     | NOT NULL                 |
| city           | VARCHAR(50) | NOT NULL                 |

Table 2: winner_records

| Column         | Type        | Atributes                |
| -------------- | ----------- | ------------------------ |
| id             | BIGSERIAL   | PK                       |
| participant_id | BIGINT      | FK, NOT NULL             |
| sum            | INTEGER     | NOT NULL                 |
| created_at     | TIMESTAMPTZ | NOT NULL                 |
