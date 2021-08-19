import com.stehno.gradle.natives.ext.Platform
import dev.clojurephant.plugin.clojure.tasks.ClojureSourceSet

plugins {
    application
    id("dev.clojurephant.clojure") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("com.stehno.natives") version "0.3.1"
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
    implementation("metosin:compojure-api:0.16.0")
    implementation("metosin:ring-http-response:0.5.0")
    implementation("metosin:ring-swagger-ui:2.0.17")
    implementation("metosin:ring-swagger:0.13.0")
    implementation("org.clojure:clojure-contrib:1.2.0")
    implementation("org.clojure:clojure:1.10.3")
    implementation("org.clojure:data.json:0.2.2")
    implementation("org.clojure:tools.logging:0.2.3")
    implementation("org.clojure:tools.logging:0.2.3")
    implementation("org.flatland:ordered:1.5.7")
    implementation("org.lwjgl.lwjgl:lwjgl:2.9.1")
    testRuntimeOnly("org.ajoberstar:jovial:0.3.0")
}

natives {
    configurations = listOf("runtimeClasspath")
    platforms = listOf(Platform.current())
}

// Compilation
val hyperspaceMainClass = "hyperspace.main"
version = "1.0.0-SNAPSHOT"

@Suppress("DEPRECATION") // clojurephant still depends on conventions
val SourceSet.clojure: SourceDirectorySet
    get() = (this as org.gradle.api.internal.HasConvention).convention.getPlugin<ClojureSourceSet>().clojure

sourceSets {
    main {
        clojure.srcDir("src")
    }
}

clojure.builds.named("main") {
    aotAll()
}

// Testing
tasks.withType<Test> {
    useJUnitPlatform()
}

// Running
application {
    mainClass.set(hyperspaceMainClass)
}

tasks.named<JavaExec>("run") {
    dependsOn("includeNatives")
    systemProperties = mapOf("java.library.path" to "build/natives")
}
