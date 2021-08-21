import dev.clojurephant.plugin.clojure.tasks.ClojureSourceSet
import org.gradle.api.internal.HasConvention // clojurephant still depends on conventions

plugins {
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
    implementation("org.clojure:clojure:1.10.3")
    implementation("org.clojure:tools.logging:0.2.3")
    testRuntimeOnly("org.ajoberstar:jovial:0.3.0")
}

// Compilation
@Suppress("DEPRECATION") // clojurephant still depends on conventions
val SourceSet.clojure: SourceDirectorySet
    get() = (this as HasConvention).convention.getPlugin<ClojureSourceSet>().clojure

clojure.builds.named("main") {
    aotAll()
}

// Testing
tasks.withType<Test> {
    useJUnitPlatform()
}
