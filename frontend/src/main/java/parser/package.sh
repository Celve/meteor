#!/usr/bin/env bash

ex -sc '1i|package parser;' -cx 'MeteorLexer.java'
ex -sc '1i|package parser;' -cx 'MeteorParserBaseListener.java'
ex -sc '1i|package parser;' -cx 'MeteorParserBaseVisitor.java'
ex -sc '1i|package parser;' -cx 'MeteorParserLexer.java'
ex -sc '1i|package parser;' -cx 'MeteorParserListener.java'
ex -sc '1i|package parser;' -cx 'MeteorParserParser.java'
ex -sc '1i|package parser;' -cx 'MeteorParserVisitor.java'
