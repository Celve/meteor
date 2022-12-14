plugins {
  id("meteor.kotlin-application-conventions")
  id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
  mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "17" }

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
  antlr("org.antlr:antlr4:4.11.1")
}

tasks.generateGrammarSource {
  maxHeapSize = "64m"
  arguments = arguments + listOf("-visitor", "-long-messages")

  // copy generated files to source
  copy {
    from("${buildDir}/generated-src/antlr/main")
    into("src/main/java/frontend/parser")
  }

  // insert package into the first line, only applicable in macOS
  // its default dir is project dir
  exec {
    commandLine("./package.sh")
  }
}

tasks.named("run", JavaExec::class) {
  // redirect the input from console
  standardInput = System.`in`
  enableAssertions = true
}

application {
  // Define the main class for the application.
  mainClass.set("MainKt")
}