run:
	gradle bootRun

full-check: lint test run

lint: lint-main lint-test

test:
	gradle test

lint-main:
	./gradlew checkstyleMain

lint-test:
	./gradlew checkstyleTest



