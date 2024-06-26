plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    // You can run your app via task "run": ./gradlew run
    application

    // JavaFX plugin allows to run the app using "./gradlew run" command
    // by correctly import javafx JavaFX modules at runtime
    id("org.openjfx.javafxplugin") version "0.1.0"

    /*
     * Adds tasks to export a runnable jar.
     * In order to create it, launch the "shadowJar" task.
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.github.johnrengelman.shadow") version "8.1.1"

    id("org.danilopianini.gradle-java-qa") version "1.39.0"
}

repositories {
    mavenCentral()
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.controls", "javafx.fxml", "javafx.swing", "javafx.graphics")
}

dependencies {
    // Suppressions for SpotBugs
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.3")

    // Reactive Streams
    implementation("io.reactivex.rxjava3:rxjava:3.1.8")

    // Tablesaw
    implementation("tech.tablesaw:tablesaw-core:0.43.1")

    val jUnitVersion = "5.10.2"
    // JUnit API and testing engine
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")

    // Logging library
    implementation("org.slf4j:slf4j-api:2.0.13")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.6")
}

tasks.test {
    // Enables JUnit 5 Jupiter module
    useJUnitPlatform()
}

application {
    // Define the main class for the application
    mainClass.set("roofsense.Launcher")
}
