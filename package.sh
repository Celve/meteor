#!/usr/bin/env bash
if ! grep -m 1 "package frontend.parser;" src/main/java/frontend/parser/Stardust.java; then
  ex -sc '1i|package frontend.parser;' -cx 'src/main/java/frontend/parser/Stardust.java'
  ex -sc '1i|package frontend.parser;' -cx 'src/main/java/frontend/parser/MeteorBaseListener.java'
  ex -sc '1i|package frontend.parser;' -cx 'src/main/java/frontend/parser/MeteorBaseVisitor.java'
  ex -sc '1i|package frontend.parser;' -cx 'src/main/java/frontend/parser/MeteorLexer.java'
  ex -sc '1i|package frontend.parser;' -cx 'src/main/java/frontend/parser/MeteorListener.java'
  ex -sc '1i|package frontend.parser;' -cx 'src/main/java/frontend/parser/MeteorParser.java'
  ex -sc '1i|package frontend.parser;' -cx 'src/main/java/frontend/parser/MeteorVisitor.java'
fi

rm -rf build/generated-src/antlr/main/
