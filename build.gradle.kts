plugins {
    `java-library`
    id("info.solidsoft.pitest") version "1.15.0"
}

group = "io.sandboxdev.id.generator"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot BOM
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.5.9"))

    // Core Dependencies
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // Test Dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("net.jqwik:jqwik:1.9.1")

    testImplementation("com.tngtech.archunit:archunit-junit5:1.3.0")

    testImplementation(platform("org.testcontainers:testcontainers-bom:1.20.1"))
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")

    testImplementation("org.assertj:assertj-core:3.26.3")
}

tasks.test {
    useJUnitPlatform {
        includeEngines("junit-jupiter", "jqwik")
    }
}

pitest {
    junit5PluginVersion.set("1.2.1")
    targetClasses.set(listOf("com.mongodb.id.generator.*"))
    threads.set(Runtime.getRuntime().availableProcessors())
    outputFormats.set(listOf("XML", "HTML"))
}
