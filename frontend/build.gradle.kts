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
    into("src/main/java/parser")
  }

//   insert package into the first line, only applicable in macOS
  exec {
    workingDir = File("src/main/java/parser")

//  on macOS
    commandLine("./package.sh")
//    commandLine("ex -sc '1i|package parser' -cx 'MeteorParserBaseListener.java'")
//    commandLine("ex -sc '1i|package parser' -cx 'MeteorParserBaseVisitor.java'")
//    commandLine("ex -sc '1i|package parser' -cx 'MeteorParserLexer.java'")
//    commandLine("ex -sc '1i|package parser' -cx 'MeteorParserListener.java'")
//    commandLine("ex -sc '1i|package parser' -cx 'MeteorParserParser.java'")
//    commandLine("ex -sc '1i|package parser' -cx 'main/java/parser/MeteorParserVisitor.java'")
  }
}