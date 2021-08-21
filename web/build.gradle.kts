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
    implementation("clj-liquibase:clj-liquibase:0.5.2")
    implementation("clj-tuple:clj-tuple:0.2.2")
    implementation("com.h2database:h2:1.3.173")
    implementation("crypto-password:crypto-password:0.1.3")
    implementation("crypto-random:crypto-random:1.2.0")
    implementation("log4j:log4j:1.2.17")
    implementation("metosin:compojure-api:0.16.0")
    implementation("metosin:ring-http-response:0.5.0")
    implementation("metosin:ring-swagger-ui:2.0.17")
    implementation("metosin:ring-swagger:0.13.0")
    implementation("org.clojure:clojure:1.10.3")
    implementation("org.flatland:ordered:1.5.7")
    implementation(project(":server"))
}

// Compilation
val hyperspaceMainClass = "hyperspace.web.main"
version = "1.0.0-SNAPSHOT"

clojure.builds.named("main") {
    aotAll()
}

// Running
application {
    mainClass.set(hyperspaceMainClass)
}
