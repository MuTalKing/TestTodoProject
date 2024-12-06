import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.10"
    id("io.qameta.allure") version "2.12.0"
    id("org.springframework.boot") version "3.4.0"
    id("org.jetbrains.kotlin.plugin.spring") version "2.0.10"
    id("io.gatling.gradle") version "3.13.1"
}

group = "ru"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


val junitVersion: String by project
val restAssuredVersion: String by project
val jacksonVersion: String by project
val allureVersion: String by project
val springVersion: String by project

dependencies {
    //////////////////////////////////////////////////
    //                  JUnit5                      //
    //////////////////////////////////////////////////
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    //////////////////////////////////////////////////
    //                  RestAssured                 //
    //////////////////////////////////////////////////
    implementation("io.rest-assured:rest-assured:$restAssuredVersion")
    implementation("io.rest-assured:json-path:$restAssuredVersion")
    testImplementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")

    //////////////////////////////////////////////////
    //                  Jackson                     //
    //////////////////////////////////////////////////
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    //////////////////////////////////////////////////
    //                  Spring                      //
    //////////////////////////////////////////////////
    implementation("org.springframework.boot:spring-boot-starter:$springVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")

    //////////////////////////////////////////////////
    //                  Allure                      //
    //////////////////////////////////////////////////
    implementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    implementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    implementation("io.qameta.allure:allure-assertj:$allureVersion")
    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")

    //////////////////////////////////////////////////
    //                  External Libs               //
    //////////////////////////////////////////////////
    runtimeOnly("org.aspectj:aspectjweaver:1.9.22.1")

    implementation("org.assertj:assertj-core:3.26.3")

    implementation("org.java-websocket:Java-WebSocket:1.5.7")
}

allure {
    version.set(allureVersion)
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}