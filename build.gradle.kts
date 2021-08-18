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
    implementation("org.clojure:clojure:1.10.0")
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
