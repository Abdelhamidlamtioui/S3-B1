# E-Sport Tournament Management Application

## Project Context

An e-sport organization is developing an application to organize and manage video game tournaments. This application will support player, team, and tournament management, providing essential features for tracking tournament details and progression.

## Key Features

1. **Player Management**: Register, update, delete, and display players.
2. **Team Management**: Create, modify, add/remove players, and display teams.
3. **Tournament Management**: Create, update, add/remove teams, and display tournaments.

## Main Class Structure

- **Player** (`Joueur`): Includes attributes like pseudo, age, and team.
- **Team** (`Equipe`): Consists of a name, players, tournaments, and ranking.
- **Tournament** (`Tournoi`): Contains details like title, game, start and end dates, number of spectators, teams, estimated duration, break time between matches, ceremony time, and status (e.g., Planned, In Progress, Completed, Canceled).
- **Game** (`Jeu`): Includes game name, difficulty, and average match duration.

## Interfaces

- **TournoiDao**: Method - `calculerdureeEstimeeTournoi(Long tournoiId)` to calculate the estimated duration of the tournament.
- **TournoiMetier**: Method - `obtenirdureeEstimeeTournoi(Long tournoiId)` to obtain the estimated duration of the tournament.

## Implementation Classes

- **TournoiDaoImpl**: Implements `TournoiDao` for basic duration calculation.
- **TournoiDaoExtension**: Extends `TournoiDao` for advanced duration calculation.
- **TournoiMetierImpl**: Implements `TournoiMetier`.

## Estimated Duration Calculation Rules

1. **Basic Calculation** (in `TournoiDaoImpl`):  
   Estimated Duration = (Number of Teams × Average Match Duration) + Break Time Between Matches.

2. **Advanced Calculation** (in `TournoiDaoExtension`):  
   Estimated Duration = (Number of Teams × Average Match Duration × Game Difficulty) + Break Time + Ceremony Time.

## Project Structure

- **Configuration Files**:
  - `applicationContext.xml`: Spring configuration.
  - `pom.xml`: Maven configuration.
  - `persistence.xml`: JPA and H2 configuration.
  - Additional configuration files as needed.

- **Layers**:
  - **Model Layer**: JPA entities.
  - **Repository Layer**: JPA/Hibernate-based repositories.
  - **Service Layer**: Business logic.
  - **Utility Layer**: Support functions.
  - **Console Interface**: A console-based menu (main method) to start the application, loading Spring configuration via `ApplicationContext`.

- **Testing**: Unit tests using JUnit and Mockito, and an integration test covering DAO and service layers.

## Technical Requirements

### New Requirements
- **Database**: Use H2 as the database.
- **Spring Core**: Use XML-based configuration for IoC and DI.
- **Spring Container**: Define and manage beans in `applicationContext.xml`.
- **Open/Closed Principle**: Implement `TournoiDaoExtension`.
- **Testing**: Use JaCoCo for code coverage measurement.

### Previous Requirements to Reuse
- **Java 8**: Use Java 8 features such as Stream API, Lambda expressions, Java Time API, Collection API, HashMaps, and Optional.
- **Maven**: For dependency management.
- **JUnit & Mockito**: For unit testing.
- **Design Patterns**: Implement Repository and Singleton patterns.
- **Logging**: Use SLF4J or native Java logging.
- **Data Validation**: Hibernate Validator annotations (e.g., `@NotNull`, `@Size`) and additional logic validations.
- **Console Menu**: Provide a console interface for user interaction.
- **JAR Generation**: Compile project into an executable JAR.

### Tools

- **Version Control**: Git with branching strategy.
- **IDE**: Choose any IDE.
- **Project Management**: JIRA for Scrum-based project tracking, linked with Git.
- **JIRA Link**: [JIRA Board for Tournament Management Project](https://oneno9847.atlassian.net/jira/software/projects/TOUR/boards/2/backlog?atlOrigin=eyJpIjoiZDBjMGQwNzQ5NmJhNDdkMGE4ZjIzNDM1NDk2MmFiZDgiLCJwIjoiaiJ9).

## Prohibitions and Restrictions

- **Display Output**: Do not use `System.out.println()` for displaying information.
- **Logging**: Do not use Log4j; use SLF4J or native Java logging.
- **Spring Boot**: This is a standard Java project, not a Spring Boot project.
- **Restricted Annotations**: Avoid using the following Spring annotations:
  - `@Service`, `@Repository`, `@Component`, `@SpringBootApplication`, `@Autowired`, `@ComponentScan`
- **XML Configuration Only**: Use XML exclusively for all configurations as per requirements.

