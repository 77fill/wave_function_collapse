# Wave Function Collapse
## Description

This application implements the [wave function collapse alogrithm](https://en.wikipedia.org/wiki/Model_synthesis).
You may choose images - some examples are included - and generate a new image based on wfc.

## Technology

- [Java 21](https://docs.oracle.com/en/java/javase/21/)
- [Spring Framework](https://spring.io/projects/spring-framework) for dependency injection
- [Vavr library](https://vavr.io/) for functional programming
- [Maven](https://maven.apache.org/) as a build tool
- [Lombok](https://projectlombok.org/) as preprocessor for brevity
- [Processing library](https://processing.org/) for graphics
- [Git](https://git-scm.com/) for version control
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

## How to read/understand the code

- Make sure you get acquainted with the above technologies
- Read the `package-info.java` files
- Abstract Mothers might have explanation comments!
- Read the unit tests for examples (TODO)

## Principles

- Points are relative to the upper left corner of the element where they are used (see [RelativeElement](src/main/java/dev/pschmalz/wave_function_collapse/infrastructure/view/RelativeElement.java))

## FAQ

Q: The entrypoint doesn't seem to start the View / PApplet. Where does it happen?<br>
A: Look at the package [config/lifecycle](src/main/java/dev/pschmalz/wave_function_collapse/config/lifecycle/package-info.java)