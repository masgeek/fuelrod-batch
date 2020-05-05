import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.3.71"
	val springVersion = "2.2.6.RELEASE"

	id("org.springframework.boot") version springVersion
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion
}

group = "com.tsobu"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}


repositories {
	mavenCentral()
	maven {
		url = uri("https://jitpack.io")
	}
	maven {
		url = uri("http://dl.bintray.com/africastalking/java")
	}
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-batch")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.security:spring-security-crypto")

	implementation("com.infobip:infobip-api-java-client:2.1.0")
	implementation("com.africastalking:core:3.4.2")

	implementation("com.googlecode.libphonenumber:libphonenumber:8.10.13")

	implementation("org.quartz-scheduler:quartz:2.3.1")
	implementation("com.github.rozidan:modelmapper-spring-boot-starter:1.0.0")

	compileOnly("org.projectlombok:lombok")

	runtimeOnly("mysql:mysql-connector-java")
	runtimeOnly("com.h2database:h2")

	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.springframework.batch:spring-batch-test")
}