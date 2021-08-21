import dev.clojurephant.plugin.clojurescript.tasks.ClojureScriptSourceSet
import org.gradle.api.internal.HasConvention // clojurephant still depends on conventions

plugins {
    application
    id("dev.clojurephant.clojurescript")
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
    implementation("org.clojure:clojurescript:1.10.773")
}

// Compilation
@Suppress("DEPRECATION") // clojurephant still depends on conventions
val SourceSet.clojurescript: SourceDirectorySet
    get() = (this as HasConvention).convention.getPlugin<ClojureScriptSourceSet>().clojureScript

tasks.register<Copy>("copyHtml") {
    from("src/main/html")
    into("$buildDir/www")
}

tasks.build {
    dependsOn("copyHtml")
}

clojurescript {
    builds {
        all {
            this as dev.clojurephant.plugin.clojurescript.ClojureScriptBuild
            compiler {
                outputTo.set(file("$buildDir/www/js/main.js"))
                outputDir.set(file("$buildDir/www/js/out"))
                main = "hyperspace.core"
                assetPath = "js/out"
            }
        }
    }
}
