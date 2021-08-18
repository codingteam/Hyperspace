plugins {
    application
    id("dev.clojurephant.clojure") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "clojars"
        url = uri("https://repo.clojars.org")
    }
}

dependencies {
    implementation("org.clojure:clojure:1.8.0")
    testRuntimeOnly("org.ajoberstar:jovial:0.3.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val hyperspaceMainClass = "hyperspace.main"

application {
    mainClass.set(hyperspaceMainClass)
}

clojure.builds.named("main") {
    aotAll()
}
