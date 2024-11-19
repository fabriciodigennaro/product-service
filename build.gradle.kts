plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	jacoco
}

group = "com.challenge"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	val ARCH_UNIT = "1.3.0"
	val ASSERT_J = "3.26.3"
	val H2 = "2.3.232"
	val LOMBOK = "1.18.36"
	val MONETA = "1.4.4"
	val OPEN_API = "2.6.0"
	val REST_ASSURED = "5.5.0"

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$OPEN_API")
	implementation("org.javamoney:moneta:$MONETA")
	implementation("com.h2database:h2:$H2")

	compileOnly("org.projectlombok:lombok:$LOMBOK")

	annotationProcessor("org.projectlombok:lombok:$LOMBOK")

	// Test dependencies
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.assertj:assertj-core:$ASSERT_J")
	testImplementation("com.tngtech.archunit:archunit:$ARCH_UNIT")
	testImplementation("io.rest-assured:rest-assured:$REST_ASSURED")
	testImplementation("io.rest-assured:json-path:$REST_ASSURED")
	testImplementation("io.rest-assured:xml-path:$REST_ASSURED")
	testImplementation("io.rest-assured:spring-mock-mvc:$REST_ASSURED")
	testImplementation("io.rest-assured:spring-commons:$REST_ASSURED")

	testCompileOnly("org.projectlombok:lombok:$LOMBOK")

	testAnnotationProcessor("org.projectlombok:lombok:$LOMBOK")
}

jacoco {
	toolVersion = "0.8.12"
}

tasks.apply {
	test {
		useJUnitPlatform()
		finalizedBy(jacocoTestReport)
	}

	jacocoTestReport {
		val jacocoDir = layout.buildDirectory.dir("jacoco")
		executionData(
			fileTree(jacocoDir).include("/test.exec")
		)
		reports {
			csv.required.set(false)
			html.required.set(true)
			xml.required.set(true)
			html.outputLocation.set(layout.buildDirectory.dir("jacoco/html"))
			xml.outputLocation.set(layout.buildDirectory.file("jacoco/report.xml"))
		}
		dependsOn(test)
	}

	jacocoTestCoverageVerification {
		val jacocoDir = layout.buildDirectory.dir("jacoco")
		executionData(
			fileTree(jacocoDir).include("test.exec")
		)
		violationRules {
			rule {
				limit {
					minimum = "0.90".toBigDecimal()
				}
			}
		}
		dependsOn(test)
	}
}
