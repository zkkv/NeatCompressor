plugins {
    id("java")
    id("checkstyle")
    id("application")
}

group = "org.github.zkkv"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.github.zkkv.Main")
}

checkstyle {
    toolVersion = "9.2.1"
    configFile = file("$rootDir/sun_checks.xml")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}