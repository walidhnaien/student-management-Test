# Tests BDD – Création d’un étudiant

Projet d’automatisation UI basé sur :

- Java 17
- Maven
- Cucumber / Gherkin
- Selenium WebDriver
- JUnit Platform
- Page Object Model

## Préconditions

1. PostgreSQL est démarré.
2. Le backend Spring Boot répond sur `http://localhost:8080`.
3. Le frontend Angular répond sur `http://localhost:4200`.
4. Google Chrome est installé.

Selenium Manager gère automatiquement le pilote Chrome.

## Structure

```text
src/test/java
├── hooks/Hooks.java
├── pages/StudentPage.java
├── runners/RunCucumberTest.java
├── steps/StudentCreationSteps.java
└── support/TestContext.java

src/test/resources
├── features/student_creation.feature
└── junit-platform.properties
```

## Exécuter le scénario

```bash
mvn clean test
```

Mode sans interface graphique :

```bash
mvn clean test -Dheadless=true
```

Avec une autre URL :

```bash
mvn clean test -DbaseUrl=http://localhost:4200
```

## Rapport

Après l’exécution :

```text
target/cucumber-report.html
```

## Sélecteurs attendus dans Angular

Le formulaire doit contenir :

```html
<input id="firstName">
<input id="lastName">
<input id="email">
<input id="classroom">
<button type="submit">Créer l’étudiant</button>
```

La liste doit être affichée dans un tableau HTML contenant l’adresse email créée.

## Scénario couvert

- ouverture de l’application ;
- saisie du prénom, du nom, d’un email unique et de la classe ;
- soumission du formulaire ;
- contrôle du message de succès ;
- contrôle de la présence de l’étudiant dans la liste.
