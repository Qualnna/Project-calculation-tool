# dasCalculator

dasCalculator er et værktøj til projektstyring og tidsestimering, der hjælper teams med at holde styr på projekter, underprojekter, opgaver og medarbejderallokering.

## Softwaremæssige forudsætninger

For at køre applikationen lokalt skal du have følgende installeret:

- **Java 21**: Projektet er bygget med Java 21.
- **Maven**: Bruges til dependency management og build.
- **MySQL**: Applikationen bruger en MySQL-database til dataopbevaring.
- **Miljøvariabler**: Følgende miljøvariabler skal være sat for at forbinde til databasen i `dev` profilen:
    - `dev_db_host`: URL til din MySQL-database (f.eks. `jdbc:mysql://localhost:3306/project_calculation`).
    - `dev_db_username`: Dit database-brugernavn.
    - `dev_db_password`: Din database-adgangskode.

## Installation og kørsel

1. Klon repositoriet.
2. Sørg for at din MySQL-server kører og opret databasen `project_calculation` (se `src/main/resources/sql/schema.sql`).
3. Sæt de nødvendige miljøvariabler.
4. Kør applikationen med Maven:
   ```bash
   mvn spring-boot:run
   ```
5. Applikationen vil være tilgængelig på `http://localhost:8080`.

## Link til kørende applikation

Applikationen er deployet og kan findes her:
[Kørende Applikation](https://your-deployment-link.com/)  
*(Bemærk: Erstat linket med den faktiske URL til jeres deployede applikation)*

## Funktionalitet

- **Projektstyring**: Opret og administrer overordnede projekter.
- **Underprojekter og Opgaver**: Opdel projekter i mindre dele for bedre overblik.
- **Medarbejderstyring**: Opret medarbejdere med specifikke kompetencer.
- **Allokering**: Tildel medarbejdere til specifikke opgaver og følg tidsforbruget.
- **Dashboard**: Få et hurtigt overblik over projektets fremgang, tidsforbrug og deadlines.

## Teknologier

- **Backend**: Spring Boot 3.x (Java 21)
- **Database**: MySQL (med H2 til test)
- **Frontend**: Thymeleaf & CSS
- **Build Tool**: Maven
