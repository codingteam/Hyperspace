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

clojure.builds.named("main") {
    aotAll()
}

// Testing
tasks.withType<Test> {
    useJUnitPlatform()
}
