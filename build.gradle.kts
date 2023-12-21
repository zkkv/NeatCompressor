plugins {
    id("java")
    id("checkstyle")
    id("application")
    jacoco
}

group = "org.github.zkkv"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("org.github.zkkv.Main")
}

checkstyle {
    toolVersion = "9.2.1"
    configFile = file("$rootDir/sun_checks.xml")
}

jacoco {
    toolVersion = "0.8.9"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(false)
        csv.required.set(false)
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("org.jacoco:org.jacoco.agent:0.8.9")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}