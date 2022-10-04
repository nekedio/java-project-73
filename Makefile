run:
	gradle bootRun


lint: lint-main lint-test

test:
	gradle test

lint-main:
	./gradlew checkstyleMain

lint-test:
	./gradlew checkstyleTest



