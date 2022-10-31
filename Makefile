path = $(shell pwd)

build:
	./gradlew clean build

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

generate-openapi:
	./gradlew clean generateOpenApiDocs

setup:
	gradle wrapper --gradle-version 7.3

start-prod:
	./gradlew bootRun --args='--spring.profiles.active=prod'

install:
	./gradlew installDist

start-dist:
	./build/install/app/bin/app

generate-migrations:
	gradle diffChangeLog

db-migrate:
	./gradlew update

report: test
	./gradlew jacocoTestReport
	@echo "\n\nOpen the following file in any browser:"
	@echo "\033[34mfile://${path}/build/jacocoHtml/index.html\033[0m"
	@echo "---------------------------------------------------------------------------------------------------"
	@w3m -dump file://${path}/build/jacocoHtml/index.html
	@echo "---------------------------------------------------------------------------------------------------"
