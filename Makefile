run:
	mvn clean install

test:
	mvn clean test

sonar:
	mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar

check-format:
	find . -type f -name "*.java" -exec google-java-format -a -n {} +

stest: test sonar
