# Bidrag til dasCalculator

Velkommen til dasCalculatore!! Her er hvad du skal vide for at komme i gang med at bidrage til koden.

## Kom i gang

1. **Opsætning af udviklingsmiljø**:
    - Installer Java 21 og Maven.
    - Opsæt en lokal MySQL-database.
    - Kør SQL-scriptsne i `src/main/resources/sql/` for at initialisere din database.
    - Sørg for at sætte de nødvendige miljøvariabler (`dev_db_host`, `dev_db_username`, `dev_db_password`).

2. **Branching Strategi**:
    - Vi bruger en standard workflow baseret på Pull Requests.
    - Opret en ny branch for hver feature eller bugfix: `feature/navn-på-feature` eller `fix/navn-på-bug`.
    - Sørg for at din branch er opdateret med `main` før du opretter en PR.

3. **Kodestil**:
    - Følg standard Java-konventioner.
    - Sørg for at koden er læselig og velkommenteret, hvor det giver mening.
    - Brug meningsfulde variabel- og metodenavne på engelsk.

4. **Test**:
    - Vi lægger vægt på testbar kode.
    - Kør alle eksisterende tests med `mvn test` før du committer.
    - Skriv nye unit tests for den funktionalitet, du tilføjer.

5. **Pull Requests**:
    - Beskriv dine ændringer i din PR.
    - Sørg for at GitHub Actions (CI) kører uden fejl.
    - Din kode skal gennemgås af mindst ét andet teammedlem før den kan merges.

## Projektstruktur

- `src/main/java/qualnna/dascalculator/controller`: Håndterer web-anmodninger og routing.
- `src/main/java/qualnna/dascalculator/service`: Indeholder forretningslogik og orchestration.
- `src/main/java/qualnna/dascalculator/repository`: Håndterer database-kommunikation via JDBC og RowMappers/Extractors.
- `src/main/java/qualnna/dascalculator/model`: Domænemodeller og data-klasser.
- `src/main/resources/templates`: Thymeleaf HTML-skabeloner.
- `src/main/resources/static`: Statiske assets som CSS og billeder.
- `src/main/resources/sql`: Database schema og testdata.

## Kontakt

Hvis du har spørgsmål eller støder på problemer, så tøv ikke med at række ud til resten af  teamet: 
@Bishobos / @Nelsoon-ds / @Namirah0310
