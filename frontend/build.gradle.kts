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
  sync {
    from("${buildDir}/generated-src/antlr/main")
    into("src/main/java/parser")
  }
  delete("${buildDir}/generated-src/antlr/main/*")
}