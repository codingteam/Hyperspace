plugins {
    application
    id("dev.clojurephant.clojure") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

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
    implementation("com.h2database:h2:1.3.173")
    implementation("crypto-password:crypto-password:0.1.3")
    implementation("crypto-random:crypto-random:1.2.0")
    implementation("log4j:log4j:1.2.17")
    implementation("metosin:compojure-api:0.16.0")
    implementation("metosin:ring-http-response:0.5.0")
    implementation("metosin:ring-swagger-ui:2.0.17")
    implementation("metosin:ring-swagger:0.13.0")
    implementation("org.clojure:clojure-contrib:1.2.0")
    implementation("org.clojure:clojure:1.10.3")
    implementation("org.clojure:data.json:0.2.2")
    implementation("org.clojure:tools.logging:0.2.3")
    implementation("org.clojure:tools.logging:0.2.3")
    implementation("org.lwjgl.lwjgl:lwjgl:2.9.1")
    implementation("ru.org.codingteam:jinput-platform-natives:2.0.6")
    implementation("ru.org.codingteam:lwjgl-platform-natives:2.9.1")
    testRuntimeOnly("org.ajoberstar:jovial:0.3.0")
}

val hyperspaceMainClass = "hyperspace.main"
version = "1.0.0-SNAPSHOT"

@Suppress("DEPRECATION") // clojurephant still depends on conventions
val SourceSet.clojure
    get() = (this as org.gradle.api.internal.HasConvention).convention.getPlugin<dev.clojurephant.plugin.clojure.tasks.ClojureSourceSet>().clojure

sourceSets {
    main {
        clojure.srcDir("src")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set(hyperspaceMainClass)
}

clojure.builds.named("main") {
    aotAll()
}
