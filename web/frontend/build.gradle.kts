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
