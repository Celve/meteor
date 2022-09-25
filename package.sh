#!/usr/bin/env bash
if ! grep -m 1 "package parser;" src/main/java/parser/Stardust.java; then
  ex -sc '1i|package parser;' -cx 'src/main/java/parser/Stardust.java'
  ex -sc '1i|package parser;' -cx 'src/main/java/parser/MeteorBaseListener.java'
  ex -sc '1i|package parser;' -cx 'src/main/java/parser/MeteorBaseVisitor.java'
  ex -sc '1i|package parser;' -cx 'src/main/java/parser/MeteorLexer.java'
  ex -sc '1i|package parser;' -cx 'src/main/java/parser/MeteorListener.java'
  ex -sc '1i|package parser;' -cx 'src/main/java/parser/MeteorParser.java'
  ex -sc '1i|package parser;' -cx 'src/main/java/parser/MeteorVisitor.java'
fi
