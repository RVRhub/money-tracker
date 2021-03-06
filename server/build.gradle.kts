import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.1.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.3.50"
}

group = "com.rvr.moneytracker"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    runtimeOnly(project(":frontend"))
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.boot:spring-boot-starter-mail:2.1.3.RELEASE")
//    implementation("org.springframework.boot:spring-boot-starter-security:2.1.3.RELEASE")
//    implementation("org.postgresql:postgresql:42.2.5")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("commons-io:commons-io:2.4")
    implementation("io.jsonwebtoken:jjwt:0.9.0")
    implementation("io.jsonwebtoken:jjwt-api:0.10.6")
    implementation("com.mashape.unirest:unirest-java:1.4.9")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    runtimeOnly("org.springframework.boot:spring-boot-devtools:2.1.3.RELEASE")
    runtimeOnly("com.h2database:h2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-noarg:1.3.50")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
