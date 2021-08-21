import com.stehno.gradle.natives.ext.Platform

plugins {
    application
    id("dev.clojurephant.clojure")
    id("com.stehno.natives")
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
    implementation("clj-tuple:clj-tuple:0.2.2")
    implementation("log4j:log4j:1.2.17")
    implementation("org.clojure:clojure:1.10.3")
    implementation("org.clojure:data.json:0.2.2")
    implementation("org.flatland:ordered:1.5.7")
    implementation("org.lwjgl.lwjgl:lwjgl:2.9.1")
    implementation(project(":core"))
    testImplementation("org.clojure:clojure-contrib:1.2.0")
    testRuntimeOnly("org.ajoberstar:jovial:0.3.0")
}

natives {
    configurations = listOf("runtimeClasspath")
    platforms = listOf(Platform.current())
}

// Compilation
val clientMainClass = "hyperspace.client.main"
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
    mainClass.set(clientMainClass)
}

tasks.named<JavaExec>("run") {
    dependsOn("includeNatives")
    systemProperties = mapOf("java.library.path" to "build/natives")
}
