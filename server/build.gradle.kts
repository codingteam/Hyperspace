plugins {
    application
    id("dev.clojurephant.clojure")
}

// Dependencies
repositories {
    mavenCentral()
    maven {
        name = "clojars"
        url = uri("https://repo.clojars.org")
    }
}

dependencies {
    implementation("azql:azql:0.2.0")
    implementation("clj-liquibase:clj-liquibase:0.5.2")
    implementation("clj-tuple:clj-tuple:0.2.2")
    implementation("com.h2database:h2:1.3.173")
    implementation("crypto-password:crypto-password:0.1.3")
    implementation("crypto-random:crypto-random:1.2.0")
    implementation("log4j:log4j:1.2.17")
    implementation("org.clojure:clojure-contrib:1.2.0")
    implementation("org.clojure:clojure:1.10.3")
    implementation("org.clojure:data.json:0.2.2")
    implementation("org.clojure:tools.logging:0.2.3")
    implementation("org.flatland:ordered:1.5.7")
    implementation(project(":core"))
    testRuntimeOnly("org.ajoberstar:jovial:0.3.0")
}

// Compilation
val serverMainClass = "hyperspace.server.main"
version = "1.0.0-SNAPSHOT"

clojure.builds.named("main") {
    aotAll()
}

// Testing
tasks.withType<Test> {
    useJUnitPlatform()
}

// Running
application {
    mainClass.set(serverMainClass)
}
