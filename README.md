# The Teamscale Custom-Check Sample Repository

This repository is meant to serve as a starting point for developing custom checks for [Teamscale](https://www.teamscale.com).
You can build your custom checks using either Gradle or Maven.

## Build with Gradle

- Clone this repository.
- Run `gradlew jar` (Linux/macOS) or `gradlew.bat jar` (Windows).
- Copy the JAR from `build/libs/custom-checks.jar` to the `custom-checks/` folder in your Teamscale installation root.
- Restart Teamscale.
- You should now be able to see the sample check when creating a new _Java_ analysis profile. It can be found inside the _Bad Practice_ group.

### Developing Custom Checks with IntelliJ IDEA

Use `./gradlew openIdea` (Linux/macOS) or `gradlew.bat openIdea` (Windows) to create the IntelliJ module definition files and open the project in IntelliJ IDEA.
You will then find the sample custom check (`SampleCheck`) in the `src/` directory.

### Developing Custom Checks with Eclipse

Use `./gradlew eclipse` (Linux/macOS) or `gradlew.bat eclipse` (Windows) to create Eclipse project files, afterwards you can easily import the project into Eclipse.
You will then find the sample custom check (`SampleCheck`) in the `src/` directory.

## Build with Maven

- Clone this repository.
- Run `mvn clean verify`.
- Copy the JAR from `target/custom-checks.jar` to the `custom-checks/` folder in your Teamscale installation root.
- Restart Teamscale.
- You should now be able to see the sample check when creating a new _Java_ analysis profile. It can be found inside the _Bad Practice_ group.

### Developing Custom Checks with IntelliJ IDEA

Open the project in IntelliJ IDEA.
You will then find the sample custom check (`SampleCheck`) in the `src/` directory.

### Developing Custom Checks with Eclipse

You can import the project into Eclipse using _File_ > _Import_ > _Maven_ > _Existing Maven projects_.
You will then find the sample custom check (`SampleCheck`) in the `src/` directory of the `custom-checks` project.

## Documentation

Javadoc: http://cqse.github.io/teamscale-custom-check-sample

The Javadoc documentation also contains more sample checks which you can use as examples/base for your own checks.

## API evolution / version updates of Teamscale

The custom-check API and thus any custom-check binaries are compatible between patch releases (e.g. 5.6.0 and 5.6.1).
However, when updating to a new feature release (e.g. 5.6.x to 5.7.x), a rebuild of your custom checks against the latest custom-check API is recommended.

## Get Help

Need help?
Contact us at support@teamscale.com.
