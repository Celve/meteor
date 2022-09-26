plugins {
  id("meteor.kotlin-application-conventions")
}

repositories {
  mavenCentral()
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
  exec {
    // its default dir is project dir
    commandLine("./package.sh")
  }
}

tasks.named("run", JavaExec::class) {
  // redirect the input from console
  standardInput = System.`in`

  // disable the generation task
}

application {
  // Define the main class for the application.
  mainClass.set("MainKt")
}
