plugins {
    application
    kotlin("jvm") version "1.5.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:1.6.2")
    implementation("io.ktor:ktor-websockets:1.6.2")
    implementation("io.ktor:ktor-server-netty:1.6.2")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    testImplementation("io.ktor:ktor-server-tests:1.6.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.21")
}

version = "1.0.0"
application {
    mainClass.set("ru.org.codingteam.hyperspace.web.ApplicationKt")
}
