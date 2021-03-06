# The Teamscale Custom-Check Sample Repository

This repository is meant to serve as a starting point for developing custom checks for [Teamscale](https://www.teamscale.com/).
You can build your custom checks using either [Gradle](https://gradle.org/) or [Maven](https://maven.apache.org/).

## Build with Gradle

- Clone this repository.
- Run `./gradlew jar` (Linux/macOS) or `gradlew.bat jar` (Windows).
- Copy the JAR from `build/libs/teamscale-custom-check-sample.jar` to the `custom-checks/` folder in your Teamscale installation root.
- Restart Teamscale.
- You should now be able to see the sample check when creating a new _Java_ analysis profile.
    It can be found inside the _Bad Practice_ group.

### Developing Custom Checks with IntelliJ IDEA

You can import the project into IntelliJ IDEA using _File_ > _Open_.
Then, when asked, select _Open as:_ _Gradle Project_.
When the import is finished, you will find the sample custom check (`SampleCheck`) in the `src/main/java` directory.

### Developing Custom Checks with Eclipse

Use `./gradlew eclipse` (Linux/macOS) or `gradlew.bat eclipse` (Windows) to create Eclipse project files.
You can then import the project into Eclipse using _File_ > _Import_ > _General_ > _Existing Projects into Workspace_.
When the import is finished, you will find the sample custom check (`SampleCheck`) in the `src/main/java` directory.

## Build with Maven

- Clone this repository.
- Run `./mvnw clean verify`.
- Copy the JAR from `target/teamscale-custom-check-sample.jar` to the `custom-checks/` folder in your Teamscale installation root.
- Restart Teamscale.
- You should now be able to see the sample check when creating a new _Java_ analysis profile.
   It can be found inside the _Bad Practice_ group.

### Developing Custom Checks with IntelliJ IDEA

You can import the project into IntelliJ IDEA using _File_ > _Open_.
Then, when asked, select _Open as:_ _Maven Project_.
When the import is finished, you will find the sample custom check (`SampleCheck`) in the `src/main/java` directory.

### Developing Custom Checks with Eclipse

You can import the project into Eclipse using _File_ > _Import_ > _Maven_ > _Existing Maven projects_.
When the import is finished, you will find the sample custom check (`SampleCheck`) in the `src/main/java` directory.

## Documentation

Javadoc: http://cqse.github.io/teamscale-custom-check-sample

The Javadoc documentation also contains more sample checks which you can use as examples/base for your own checks.

## API evolution / version updates of Teamscale

The custom-check API and thus any custom-check binaries are compatible between patch releases (e.g., 5.6.0 and 5.6.1).
However, when updating to a new feature release (e.g., 5.6.x to 5.7.x), a rebuild of your custom checks against the latest custom-check API is recommended.

## Get Help

Need help?
Contact us at support@teamscale.com.
