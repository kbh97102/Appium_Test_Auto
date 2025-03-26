plugins {
    kotlin("jvm") version "2.0.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    implementation("io.appium:java-client:9.4.0")
    implementation("org.seleniumhq.selenium:selenium-java:4.28.0")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}