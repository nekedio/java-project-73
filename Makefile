run: clean
	./gradlew bootRun --args='--spring.profiles.active=dev'

full-check: lint test run

lint: lint-main lint-test

test:
	./gradlew test

lint-main:
	./gradlew checkstyleMain

lint-test:
	./gradlew checkstyleTest

clean:
	rm *.db || true
	./gradlew clean

dependencies:
	gradle dependencies --configuration runtimeClasspath


