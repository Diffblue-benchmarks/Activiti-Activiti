/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.core.el.juel.tree.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.tree.ExpressionNode;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.IdentifierNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary;
import org.activiti.core.el.juel.tree.impl.ast.AstBracket;
import org.activiti.core.el.juel.tree.impl.ast.AstChoice;
import org.activiti.core.el.juel.tree.impl.ast.AstComposite;
import org.activiti.core.el.juel.tree.impl.ast.AstDot;
import org.activiti.core.el.juel.tree.impl.ast.AstFunction;
import org.activiti.core.el.juel.tree.impl.ast.AstIdentifier;
import org.activiti.core.el.juel.tree.impl.ast.AstMethod;
import org.activiti.core.el.juel.tree.impl.ast.AstNode;
import org.activiti.core.el.juel.tree.impl.ast.AstNull;
import org.activiti.core.el.juel.tree.impl.ast.AstParameters;
import org.activiti.core.el.juel.tree.impl.ast.AstProperty;
import org.activiti.core.el.juel.tree.impl.ast.AstText;
import org.activiti.core.el.juel.tree.impl.ast.AstUnary;
import org.junit.jupiter.api.Test;

class ParserDiffblueTest {
  /**
   * Method under test: {@link Parser#createScanner(String)}
   */
  @Test
  void testCreateScanner() {
    // Arrange and Act
    Scanner actualCreateScannerResult = (new Parser(new Builder(), "Input")).createScanner("Expression");

    // Assert
    assertEquals("", actualCreateScannerResult.builder.toString());
    assertEquals("Expression", actualCreateScannerResult.getInput());
    assertNull(actualCreateScannerResult.getToken());
    assertEquals(0, actualCreateScannerResult.getPosition());
    assertFalse(actualCreateScannerResult.isEval());
  }

  /**
   * Method under test: {@link Parser#createScanner(String)}
   */
  @Test
  void testCreateScanner2() {
    // Arrange
    AstParameters params = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    params.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    Parser parser = new Parser(new Builder(), "Input");
    parser.function("Name", params);

    // Act
    Scanner actualCreateScannerResult = parser.createScanner("Expression");

    // Assert
    assertEquals("", actualCreateScannerResult.builder.toString());
    assertEquals("Expression", actualCreateScannerResult.getInput());
    assertNull(actualCreateScannerResult.getToken());
    assertEquals(0, actualCreateScannerResult.getPosition());
    assertFalse(actualCreateScannerResult.isEval());
  }

  /**
   * Method under test:
   * {@link Parser.ParseException#ParseException(int, String, String)}
   */
  @Test
  void testParseExceptionNewParseException() {
    // Arrange and Act
    Parser.ParseException actualParseException = new Parser.ParseException(1, "3", "Expected");

    // Assert
    assertEquals("3", actualParseException.encountered);
    assertEquals("Expected", actualParseException.expected);
    assertEquals("syntax error at position 1, encountered 3, expected Expected",
        actualParseException.getLocalizedMessage());
    assertEquals("syntax error at position 1, encountered 3, expected Expected", actualParseException.getMessage());
    assertNull(actualParseException.getCause());
    assertEquals(0, actualParseException.getSuppressed().length);
    assertEquals(1, actualParseException.position);
  }

  /**
   * Method under test:
   * {@link Parser#putExtensionHandler(Scanner.ExtensionToken, Parser.ExtensionHandler)}
   */
  @Test
  void testPutExtensionHandler() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    parser.putExtensionHandler(new Scanner.ExtensionToken("Image"), mock(Parser.ExtensionHandler.class));

    // Assert
    assertEquals(1, parser.extensions.size());
  }

  /**
   * Method under test: {@link Parser#getExtensionHandler(Scanner.Token)}
   */
  @Test
  void testGetExtensionHandler() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act and Assert
    assertNull(parser.getExtensionHandler(new Scanner.Token(Scanner.Symbol.EOF, "Image")));
  }

  /**
   * Method under test: {@link Parser#getExtensionHandler(Scanner.Token)}
   */
  @Test
  void testGetExtensionHandler2() {
    // Arrange
    AstParameters params = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    params.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    Parser parser = new Parser(new Builder(), "Input");
    parser.function("Name", params);

    // Act and Assert
    assertNull(parser.getExtensionHandler(new Scanner.Token(Scanner.Symbol.EOF, "Image")));
  }

  /**
   * Method under test: {@link Parser#parseFloat(String)}
   */
  @Test
  void testParseFloat() throws Parser.ParseException {
    // Arrange, Act and Assert
    assertEquals(42.0d, (new Parser(new Builder(), "Input")).parseFloat("42").doubleValue());
  }

  /**
   * Method under test:
   * {@link Parser#createAstBinary(AstNode, AstNode, AstBinary.Operator)}
   */
  @Test
  void testCreateAstBinary() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    AstNull left = new AstNull();
    AstBinary.Operator operator = mock(AstBinary.Operator.class);

    // Act
    AstBinary actualCreateAstBinaryResult = parser.createAstBinary(left, new AstNull(), operator);

    // Assert
    assertEquals(2, actualCreateAstBinaryResult.getCardinality());
    assertFalse(actualCreateAstBinaryResult.isLeftValue());
    assertFalse(actualCreateAstBinaryResult.isLiteralText());
    assertFalse(actualCreateAstBinaryResult.isMethodInvocation());
    assertSame(operator, actualCreateAstBinaryResult.getOperator());
  }

  /**
   * Method under test:
   * {@link Parser#createAstBracket(AstNode, AstNode, boolean, boolean)}
   */
  @Test
  void testCreateAstBracket() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    AstNull base = new AstNull();

    // Act
    AstBracket actualCreateAstBracketResult = parser.createAstBracket(base, new AstNull(), true, true);

    // Assert
    assertEquals(2, actualCreateAstBracketResult.getCardinality());
    assertFalse(actualCreateAstBracketResult.isLiteralText());
    assertFalse(actualCreateAstBracketResult.isMethodInvocation());
    assertTrue(actualCreateAstBracketResult.isLeftValue());
  }

  /**
   * Method under test:
   * {@link Parser#createAstBracket(AstNode, AstNode, boolean, boolean)}
   */
  @Test
  void testCreateAstBracket2() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class));

    // Act
    AstBracket actualCreateAstBracketResult = parser.createAstBracket(base, new AstNull(), true, true);

    // Assert
    assertEquals(2, actualCreateAstBracketResult.getCardinality());
    assertFalse(actualCreateAstBracketResult.isLiteralText());
    assertFalse(actualCreateAstBracketResult.isMethodInvocation());
    assertTrue(actualCreateAstBracketResult.isLeftValue());
  }

  /**
   * Method under test: {@link Parser#createAstChoice(AstNode, AstNode, AstNode)}
   */
  @Test
  void testCreateAstChoice() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    // Act
    AstChoice actualCreateAstChoiceResult = parser.createAstChoice(question, yes, new AstNull());

    // Assert
    assertEquals(3, actualCreateAstChoiceResult.getCardinality());
    assertFalse(actualCreateAstChoiceResult.isLeftValue());
    assertFalse(actualCreateAstChoiceResult.isLiteralText());
    assertFalse(actualCreateAstChoiceResult.isMethodInvocation());
  }

  /**
   * Method under test: {@link Parser#createAstChoice(AstNode, AstNode, AstNode)}
   */
  @Test
  void testCreateAstChoice2() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    AstNull left = new AstNull();
    AstBinary question = new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class));

    AstNull yes = new AstNull();

    // Act
    AstChoice actualCreateAstChoiceResult = parser.createAstChoice(question, yes, new AstNull());

    // Assert
    assertEquals(3, actualCreateAstChoiceResult.getCardinality());
    assertFalse(actualCreateAstChoiceResult.isLeftValue());
    assertFalse(actualCreateAstChoiceResult.isLiteralText());
    assertFalse(actualCreateAstChoiceResult.isMethodInvocation());
  }

  /**
   * Method under test: {@link Parser#createAstComposite(List)}
   */
  @Test
  void testCreateAstComposite() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    AstComposite actualCreateAstCompositeResult = parser.createAstComposite(new ArrayList<>());

    // Assert
    assertEquals(0, actualCreateAstCompositeResult.getCardinality());
    assertFalse(actualCreateAstCompositeResult.isLeftValue());
    assertFalse(actualCreateAstCompositeResult.isLiteralText());
    assertFalse(actualCreateAstCompositeResult.isMethodInvocation());
  }

  /**
   * Method under test: {@link Parser#createAstComposite(List)}
   */
  @Test
  void testCreateAstComposite2() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());

    // Act
    AstComposite actualCreateAstCompositeResult = parser.createAstComposite(nodes);

    // Assert
    assertEquals(1, actualCreateAstCompositeResult.getCardinality());
    assertFalse(actualCreateAstCompositeResult.isLeftValue());
    assertFalse(actualCreateAstCompositeResult.isLiteralText());
    assertFalse(actualCreateAstCompositeResult.isMethodInvocation());
  }

  /**
   * Method under test: {@link Parser#createAstComposite(List)}
   */
  @Test
  void testCreateAstComposite3() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    nodes.add(new AstNull());

    // Act
    AstComposite actualCreateAstCompositeResult = parser.createAstComposite(nodes);

    // Assert
    assertEquals(2, actualCreateAstCompositeResult.getCardinality());
    assertFalse(actualCreateAstCompositeResult.isLeftValue());
    assertFalse(actualCreateAstCompositeResult.isLiteralText());
    assertFalse(actualCreateAstCompositeResult.isMethodInvocation());
  }

  /**
   * Method under test: {@link Parser#createAstComposite(List)}
   */
  @Test
  void testCreateAstComposite4() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    ArrayList<AstNode> nodes = new ArrayList<>();
    AstNull left = new AstNull();
    nodes.add(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)));

    // Act
    AstComposite actualCreateAstCompositeResult = parser.createAstComposite(nodes);

    // Assert
    assertEquals(1, actualCreateAstCompositeResult.getCardinality());
    assertFalse(actualCreateAstCompositeResult.isLeftValue());
    assertFalse(actualCreateAstCompositeResult.isLiteralText());
    assertFalse(actualCreateAstCompositeResult.isMethodInvocation());
  }

  /**
   * Method under test: {@link Parser#createAstDot(AstNode, String, boolean)}
   */
  @Test
  void testCreateAstDot() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    AstDot actualCreateAstDotResult = parser.createAstDot(new AstNull(), "Property", true);

    // Assert
    assertEquals(1, actualCreateAstDotResult.getCardinality());
    assertFalse(actualCreateAstDotResult.isLiteralText());
    assertFalse(actualCreateAstDotResult.isMethodInvocation());
    assertTrue(actualCreateAstDotResult.isLeftValue());
  }

  /**
   * Method under test: {@link Parser#createAstDot(AstNode, String, boolean)}
   */
  @Test
  void testCreateAstDot2() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    AstNull left = new AstNull();

    // Act
    AstDot actualCreateAstDotResult = parser
        .createAstDot(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), "Property", true);

    // Assert
    assertEquals(1, actualCreateAstDotResult.getCardinality());
    assertFalse(actualCreateAstDotResult.isLiteralText());
    assertFalse(actualCreateAstDotResult.isMethodInvocation());
    assertTrue(actualCreateAstDotResult.isLeftValue());
  }

  /**
   * Method under test: {@link Parser#createAstDot(AstNode, String, boolean)}
   */
  @Test
  void testCreateAstDot3() {
    // Arrange
    Parser parser = new Parser(new Builder(Builder.Feature.IGNORE_RETURN_TYPE), "Input");

    // Act
    AstDot actualCreateAstDotResult = parser.createAstDot(new AstNull(), "Property", true);

    // Assert
    assertEquals(1, actualCreateAstDotResult.getCardinality());
    assertFalse(actualCreateAstDotResult.isLiteralText());
    assertFalse(actualCreateAstDotResult.isMethodInvocation());
    assertTrue(actualCreateAstDotResult.isLeftValue());
  }

  /**
   * Method under test:
   * {@link Parser#createAstFunction(String, int, AstParameters)}
   */
  @Test
  void testCreateAstFunction() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    AstFunction actualCreateAstFunctionResult = parser.createAstFunction("Name", 1,
        new AstParameters(new ArrayList<>()));

    // Assert
    assertEquals("Name", actualCreateAstFunctionResult.getName());
    assertEquals("Name", actualCreateAstFunctionResult.toString());
    assertEquals(0, actualCreateAstFunctionResult.getParamCount());
    assertEquals(1, actualCreateAstFunctionResult.getCardinality());
    assertEquals(1, actualCreateAstFunctionResult.getIndex());
    assertFalse(actualCreateAstFunctionResult.isVarArgs());
    assertFalse(actualCreateAstFunctionResult.isLeftValue());
    assertFalse(actualCreateAstFunctionResult.isLiteralText());
    assertFalse(actualCreateAstFunctionResult.isMethodInvocation());
  }

  /**
   * Method under test:
   * {@link Parser#createAstFunction(String, int, AstParameters)}
   */
  @Test
  void testCreateAstFunction2() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    AstParameters params = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    params.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Act
    AstFunction actualCreateAstFunctionResult = parser.createAstFunction("Name", 1, params);

    // Assert
    assertEquals("Name", actualCreateAstFunctionResult.getName());
    assertEquals("Name", actualCreateAstFunctionResult.toString());
    assertEquals(0, actualCreateAstFunctionResult.getParamCount());
    assertEquals(1, actualCreateAstFunctionResult.getCardinality());
    assertEquals(1, actualCreateAstFunctionResult.getIndex());
    assertFalse(actualCreateAstFunctionResult.isVarArgs());
    assertFalse(actualCreateAstFunctionResult.isLeftValue());
    assertFalse(actualCreateAstFunctionResult.isLiteralText());
    assertFalse(actualCreateAstFunctionResult.isMethodInvocation());
  }

  /**
   * Method under test: {@link Parser#createAstIdentifier(String, int)}
   */
  @Test
  void testCreateAstIdentifier() {
    // Arrange and Act
    AstIdentifier actualCreateAstIdentifierResult = (new Parser(new Builder(), "Input")).createAstIdentifier("Name", 1);

    // Assert
    assertEquals("Name", actualCreateAstIdentifierResult.getName());
    assertEquals("Name", actualCreateAstIdentifierResult.toString());
    assertEquals(0, actualCreateAstIdentifierResult.getCardinality());
    assertEquals(1, actualCreateAstIdentifierResult.getIndex());
    assertFalse(actualCreateAstIdentifierResult.isLiteralText());
    assertFalse(actualCreateAstIdentifierResult.isMethodInvocation());
    assertTrue(actualCreateAstIdentifierResult.isLeftValue());
  }

  /**
   * Method under test: {@link Parser#createAstIdentifier(String, int)}
   */
  @Test
  void testCreateAstIdentifier2() {
    // Arrange and Act
    AstIdentifier actualCreateAstIdentifierResult = (new Parser(new Builder(Builder.Feature.IGNORE_RETURN_TYPE),
        "Input")).createAstIdentifier("Name", 1);

    // Assert
    assertEquals("Name", actualCreateAstIdentifierResult.getName());
    assertEquals("Name", actualCreateAstIdentifierResult.toString());
    assertEquals(0, actualCreateAstIdentifierResult.getCardinality());
    assertEquals(1, actualCreateAstIdentifierResult.getIndex());
    assertFalse(actualCreateAstIdentifierResult.isLiteralText());
    assertFalse(actualCreateAstIdentifierResult.isMethodInvocation());
    assertTrue(actualCreateAstIdentifierResult.isLeftValue());
  }

  /**
   * Method under test: {@link Parser#createAstIdentifier(String, int)}
   */
  @Test
  void testCreateAstIdentifier3() {
    // Arrange
    AstParameters params = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    params.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    Parser parser = new Parser(new Builder(), "Input");
    parser.function("Name", params);

    // Act
    AstIdentifier actualCreateAstIdentifierResult = parser.createAstIdentifier("Name", 1);

    // Assert
    assertEquals("Name", actualCreateAstIdentifierResult.getName());
    assertEquals("Name", actualCreateAstIdentifierResult.toString());
    assertEquals(0, actualCreateAstIdentifierResult.getCardinality());
    assertEquals(1, actualCreateAstIdentifierResult.getIndex());
    assertFalse(actualCreateAstIdentifierResult.isLiteralText());
    assertFalse(actualCreateAstIdentifierResult.isMethodInvocation());
    assertTrue(actualCreateAstIdentifierResult.isLeftValue());
  }

  /**
   * Method under test: {@link Parser#createAstMethod(AstProperty, AstParameters)}
   */
  @Test
  void testCreateAstMethod() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    AstDot property = new AstDot(new AstNull(), "Property", true);

    // Act
    AstMethod actualCreateAstMethodResult = parser.createAstMethod(property, new AstParameters(new ArrayList<>()));

    // Assert
    assertEquals(2, actualCreateAstMethodResult.getCardinality());
    assertFalse(actualCreateAstMethodResult.isLeftValue());
    assertFalse(actualCreateAstMethodResult.isLiteralText());
    assertTrue(actualCreateAstMethodResult.isMethodInvocation());
  }

  /**
   * Method under test: {@link Parser#createAstMethod(AstProperty, AstParameters)}
   */
  @Test
  void testCreateAstMethod2() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    AstNull left = new AstNull();
    AstDot property = new AstDot(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), "Property", true);

    // Act
    AstMethod actualCreateAstMethodResult = parser.createAstMethod(property, new AstParameters(new ArrayList<>()));

    // Assert
    assertEquals(2, actualCreateAstMethodResult.getCardinality());
    assertFalse(actualCreateAstMethodResult.isLeftValue());
    assertFalse(actualCreateAstMethodResult.isLiteralText());
    assertTrue(actualCreateAstMethodResult.isMethodInvocation());
  }

  /**
   * Method under test: {@link Parser#createAstUnary(AstNode, AstUnary.Operator)}
   */
  @Test
  void testCreateAstUnary() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    AstUnary.Operator operator = mock(AstUnary.Operator.class);

    // Act
    AstUnary actualCreateAstUnaryResult = parser.createAstUnary(new AstNull(), operator);

    // Assert
    assertEquals(1, actualCreateAstUnaryResult.getCardinality());
    assertFalse(actualCreateAstUnaryResult.isLeftValue());
    assertFalse(actualCreateAstUnaryResult.isLiteralText());
    assertFalse(actualCreateAstUnaryResult.isMethodInvocation());
    assertSame(operator, actualCreateAstUnaryResult.getOperator());
  }

  /**
   * Method under test: {@link Parser#lookahead(int)}
   */
  @Test
  void testLookahead() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    Scanner.Token actualLookaheadResult = parser.lookahead(1);

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("Input", scanner.builder.toString());
    assertNull(actualLookaheadResult.getImage());
    assertEquals(0, actualLookaheadResult.getSize());
    assertEquals(5, scanner.getPosition());
    assertEquals(Scanner.Symbol.EOF, actualLookaheadResult.getSymbol());
    assertTrue(scanner.isEval());
  }

  /**
   * Method under test: {@link Parser#lookahead(int)}
   */
  @Test
  void testLookahead2() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "");

    // Act
    Scanner.Token actualLookaheadResult = parser.lookahead(1);

    // Assert
    assertNull(actualLookaheadResult.getImage());
    assertEquals(0, actualLookaheadResult.getSize());
    assertEquals(Scanner.Symbol.EOF, actualLookaheadResult.getSymbol());
    assertTrue(parser.scanner.isEval());
  }

  /**
   * Method under test: {@link Parser#lookahead(int)}
   */
  @Test
  void testLookahead3() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "org.activiti.core.el.juel.tree.impl.Parser$LookaheadToken");

    // Act
    Scanner.Token actualLookaheadResult = parser.lookahead(1);

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("org.activiti.core.el.juel.tree.impl.Parser$LookaheadToken", scanner.builder.toString());
    assertNull(actualLookaheadResult.getImage());
    assertEquals(0, actualLookaheadResult.getSize());
    assertEquals(57, scanner.getPosition());
    assertEquals(Scanner.Symbol.EOF, actualLookaheadResult.getSymbol());
    assertTrue(scanner.isEval());
  }

  /**
   * Method under test: {@link Parser#consumeToken()}
   */
  @Test
  void testConsumeToken() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    Scanner.Token actualConsumeTokenResult = parser.consumeToken();

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("Input", scanner.builder.toString());
    Scanner.Token token = parser.getToken();
    assertEquals("Input", token.getImage());
    assertNull(actualConsumeTokenResult);
    assertEquals(5, token.getSize());
    assertEquals(Scanner.Symbol.TEXT, token.getSymbol());
    assertSame(token, scanner.getToken());
  }

  /**
   * Method under test: {@link Parser#consumeToken()}
   */
  @Test
  void testConsumeToken2() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "");

    // Act
    Scanner.Token actualConsumeTokenResult = parser.consumeToken();

    // Assert
    Scanner.Token token = parser.getToken();
    assertNull(token.getImage());
    assertNull(actualConsumeTokenResult);
    assertEquals(0, token.getSize());
    assertEquals(Scanner.Symbol.EOF, token.getSymbol());
    Scanner scanner = parser.scanner;
    assertTrue(scanner.isEval());
    assertSame(token, scanner.getToken());
  }

  /**
   * Method under test: {@link Parser#consumeToken()}
   */
  @Test
  void testConsumeToken3() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "org.activiti.core.el.juel.tree.impl.Parser$LookaheadToken");

    // Act
    Scanner.Token actualConsumeTokenResult = parser.consumeToken();

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("org.activiti.core.el.juel.tree.impl.Parser$LookaheadToken", scanner.builder.toString());
    Scanner.Token token = parser.getToken();
    assertEquals("org.activiti.core.el.juel.tree.impl.Parser$LookaheadToken", token.getImage());
    assertNull(actualConsumeTokenResult);
    assertEquals(57, token.getSize());
    assertEquals(Scanner.Symbol.TEXT, token.getSymbol());
    assertSame(token, scanner.getToken());
  }

  /**
   * Method under test: {@link Parser#consumeToken()}
   */
  @Test
  void testConsumeToken4() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    parser.lookahead(1);

    // Act
    Scanner.Token actualConsumeTokenResult = parser.consumeToken();

    // Assert
    assertEquals("Input", parser.scanner.builder.toString());
    Scanner.Token token = parser.getToken();
    assertEquals("Input", token.getImage());
    assertNull(actualConsumeTokenResult);
    assertEquals(5, token.getSize());
    assertEquals(Scanner.Symbol.TEXT, token.getSymbol());
  }

  /**
   * Method under test: {@link Parser#consumeToken()}
   */
  @Test
  void testConsumeToken5() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    AstParameters params = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    params.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    Parser parser = new Parser(new Builder(), "Input");
    parser.function("Name", params);

    // Act
    Scanner.Token actualConsumeTokenResult = parser.consumeToken();

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("Input", scanner.builder.toString());
    Scanner.Token token = parser.getToken();
    assertEquals("Input", token.getImage());
    assertNull(actualConsumeTokenResult);
    assertEquals(5, token.getSize());
    assertEquals(Scanner.Symbol.TEXT, token.getSymbol());
    assertSame(token, scanner.getToken());
  }

  /**
   * Method under test: {@link Parser#tree()}
   */
  @Test
  void testTree() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    Tree actualTreeResult = parser.tree();

    // Assert
    Iterable<FunctionNode> functionNodes = actualTreeResult.getFunctionNodes();
    assertTrue(functionNodes instanceof List);
    ExpressionNode root = actualTreeResult.getRoot();
    assertTrue(root instanceof AstText);
    Scanner scanner = parser.scanner;
    assertEquals("Input", scanner.builder.toString());
    Scanner.Token token = parser.getToken();
    assertNull(token.getImage());
    assertEquals(0, root.getCardinality());
    assertEquals(0, token.getSize());
    assertEquals(5, scanner.getPosition());
    assertEquals(Scanner.Symbol.EOF, token.getSymbol());
    assertFalse(root.isLeftValue());
    assertFalse(root.isMethodInvocation());
    assertFalse(actualTreeResult.isDeferred());
    assertTrue(((List<FunctionNode>) functionNodes).isEmpty());
    assertTrue(root.isLiteralText());
    assertTrue(scanner.isEval());
    assertSame(token, scanner.getToken());
    assertSame(functionNodes, actualTreeResult.getIdentifierNodes());
  }

  /**
   * Method under test: {@link Parser#tree()}
   */
  @Test
  void testTree2() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "");

    // Act
    Tree actualTreeResult = parser.tree();

    // Assert
    Iterable<FunctionNode> functionNodes = actualTreeResult.getFunctionNodes();
    assertTrue(functionNodes instanceof List);
    ExpressionNode root = actualTreeResult.getRoot();
    assertTrue(root instanceof AstText);
    Scanner.Token token = parser.getToken();
    assertNull(token.getImage());
    assertEquals(0, root.getCardinality());
    assertEquals(0, token.getSize());
    assertEquals(Scanner.Symbol.EOF, token.getSymbol());
    assertFalse(root.isLeftValue());
    assertFalse(root.isMethodInvocation());
    assertFalse(actualTreeResult.isDeferred());
    assertTrue(((List<FunctionNode>) functionNodes).isEmpty());
    assertTrue(root.isLiteralText());
    Scanner scanner = parser.scanner;
    assertTrue(scanner.isEval());
    assertSame(token, scanner.getToken());
    assertSame(functionNodes, actualTreeResult.getIdentifierNodes());
  }

  /**
   * Method under test: {@link Parser#tree()}
   */
  @Test
  void testTree3() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "org.activiti.core.el.juel.tree.impl.Parser$LookaheadToken");

    // Act
    Tree actualTreeResult = parser.tree();

    // Assert
    Iterable<FunctionNode> functionNodes = actualTreeResult.getFunctionNodes();
    assertTrue(functionNodes instanceof List);
    ExpressionNode root = actualTreeResult.getRoot();
    assertTrue(root instanceof AstText);
    Scanner scanner = parser.scanner;
    assertEquals("org.activiti.core.el.juel.tree.impl.Parser$LookaheadToken", scanner.builder.toString());
    Scanner.Token token = parser.getToken();
    assertNull(token.getImage());
    assertEquals(0, root.getCardinality());
    assertEquals(0, token.getSize());
    assertEquals(57, scanner.getPosition());
    assertEquals(Scanner.Symbol.EOF, token.getSymbol());
    assertFalse(root.isLeftValue());
    assertFalse(root.isMethodInvocation());
    assertFalse(actualTreeResult.isDeferred());
    assertTrue(((List<FunctionNode>) functionNodes).isEmpty());
    assertTrue(root.isLiteralText());
    assertTrue(scanner.isEval());
    assertSame(token, scanner.getToken());
    assertSame(functionNodes, actualTreeResult.getIdentifierNodes());
  }

  /**
   * Method under test: {@link Parser#tree()}
   */
  @Test
  void testTree4() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    parser.lookahead(1);

    // Act
    Tree actualTreeResult = parser.tree();

    // Assert
    Iterable<FunctionNode> functionNodes = actualTreeResult.getFunctionNodes();
    assertTrue(functionNodes instanceof List);
    ExpressionNode root = actualTreeResult.getRoot();
    assertTrue(root instanceof AstText);
    Scanner scanner = parser.scanner;
    assertEquals("Input", scanner.builder.toString());
    Scanner.Token token = parser.getToken();
    assertNull(token.getImage());
    assertEquals(0, root.getCardinality());
    assertEquals(0, token.getSize());
    assertEquals(5, scanner.getPosition());
    assertEquals(Scanner.Symbol.EOF, token.getSymbol());
    assertFalse(root.isLeftValue());
    assertFalse(root.isMethodInvocation());
    assertFalse(actualTreeResult.isDeferred());
    assertTrue(((List<FunctionNode>) functionNodes).isEmpty());
    assertTrue(root.isLiteralText());
    assertTrue(scanner.isEval());
    assertSame(token, scanner.getToken());
    assertSame(functionNodes, actualTreeResult.getIdentifierNodes());
  }

  /**
   * Method under test: {@link Parser#tree()}
   */
  @Test
  void testTree5() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    AstParameters params = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    params.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    Parser parser = new Parser(new Builder(), "Input");
    parser.function("Name", params);

    // Act
    Tree actualTreeResult = parser.tree();

    // Assert
    Iterable<FunctionNode> functionNodes = actualTreeResult.getFunctionNodes();
    assertTrue(functionNodes instanceof List);
    Iterable<IdentifierNode> identifierNodes = actualTreeResult.getIdentifierNodes();
    assertTrue(identifierNodes instanceof List);
    assertEquals(1, ((List<FunctionNode>) functionNodes).size());
    FunctionNode getResult = ((List<FunctionNode>) functionNodes).get(0);
    assertTrue(getResult instanceof AstFunction);
    ExpressionNode root = actualTreeResult.getRoot();
    assertTrue(root instanceof AstText);
    Scanner scanner = parser.scanner;
    assertEquals("Input", scanner.builder.toString());
    assertEquals("Name", getResult.toString());
    assertEquals("Name", getResult.getName());
    Scanner.Token token = parser.getToken();
    assertNull(token.getImage());
    assertEquals(0, getResult.getIndex());
    assertEquals(0, getResult.getParamCount());
    assertEquals(0, root.getCardinality());
    assertEquals(0, token.getSize());
    assertEquals(1, getResult.getCardinality());
    assertEquals(5, scanner.getPosition());
    assertEquals(Scanner.Symbol.EOF, token.getSymbol());
    assertFalse(root.isLeftValue());
    assertFalse(root.isMethodInvocation());
    assertFalse(getResult.isVarArgs());
    assertFalse(actualTreeResult.isDeferred());
    assertFalse(((AstFunction) getResult).isLeftValue());
    assertFalse(((AstFunction) getResult).isLiteralText());
    assertFalse(((AstFunction) getResult).isMethodInvocation());
    assertTrue(((List<IdentifierNode>) identifierNodes).isEmpty());
    assertTrue(root.isLiteralText());
    assertTrue(scanner.isEval());
    assertSame(token, scanner.getToken());
  }

  /**
   * Method under test: {@link Parser#function(String, AstParameters)}
   */
  @Test
  void testFunction() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    AstFunction actualFunctionResult = parser.function("Name", new AstParameters(new ArrayList<>()));

    // Assert
    assertEquals("Name", actualFunctionResult.getName());
    assertEquals("Name", actualFunctionResult.toString());
    assertEquals(0, actualFunctionResult.getIndex());
    assertEquals(0, actualFunctionResult.getParamCount());
    assertEquals(1, parser.getFunctions().size());
    assertEquals(1, actualFunctionResult.getCardinality());
    assertFalse(actualFunctionResult.isVarArgs());
    assertFalse(actualFunctionResult.isLeftValue());
    assertFalse(actualFunctionResult.isLiteralText());
    assertFalse(actualFunctionResult.isMethodInvocation());
    assertTrue(parser.getIdentifiers().isEmpty());
  }

  /**
   * Method under test: {@link Parser#function(String, AstParameters)}
   */
  @Test
  void testFunction2() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    AstParameters params = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    params.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Act
    AstFunction actualFunctionResult = parser.function("Name", params);

    // Assert
    assertEquals("Name", actualFunctionResult.getName());
    assertEquals("Name", actualFunctionResult.toString());
    assertEquals(0, actualFunctionResult.getIndex());
    assertEquals(0, actualFunctionResult.getParamCount());
    assertEquals(1, parser.getFunctions().size());
    assertEquals(1, actualFunctionResult.getCardinality());
    assertFalse(actualFunctionResult.isVarArgs());
    assertFalse(actualFunctionResult.isLeftValue());
    assertFalse(actualFunctionResult.isLiteralText());
    assertFalse(actualFunctionResult.isMethodInvocation());
    assertTrue(parser.getIdentifiers().isEmpty());
  }

  /**
   * Method under test: {@link Parser#identifier(String)}
   */
  @Test
  void testIdentifier() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    AstIdentifier actualIdentifierResult = parser.identifier("Name");

    // Assert
    assertEquals("Name", actualIdentifierResult.getName());
    assertEquals("Name", actualIdentifierResult.toString());
    assertEquals(0, actualIdentifierResult.getCardinality());
    assertEquals(0, actualIdentifierResult.getIndex());
    assertEquals(1, parser.getIdentifiers().size());
    assertFalse(actualIdentifierResult.isLiteralText());
    assertFalse(actualIdentifierResult.isMethodInvocation());
    assertTrue(actualIdentifierResult.isLeftValue());
  }

  /**
   * Method under test: {@link Parser#identifier(String)}
   */
  @Test
  void testIdentifier2() {
    // Arrange
    Parser parser = new Parser(new Builder(Builder.Feature.IGNORE_RETURN_TYPE), "Input");

    // Act
    AstIdentifier actualIdentifierResult = parser.identifier("Name");

    // Assert
    assertEquals("Name", actualIdentifierResult.getName());
    assertEquals("Name", actualIdentifierResult.toString());
    assertEquals(0, actualIdentifierResult.getCardinality());
    assertEquals(0, actualIdentifierResult.getIndex());
    assertEquals(1, parser.getIdentifiers().size());
    assertFalse(actualIdentifierResult.isLiteralText());
    assertFalse(actualIdentifierResult.isMethodInvocation());
    assertTrue(actualIdentifierResult.isLeftValue());
  }

  /**
   * Method under test: {@link Parser#identifier(String)}
   */
  @Test
  void testIdentifier3() {
    // Arrange
    AstParameters params = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    params.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    Parser parser = new Parser(new Builder(), "Input");
    parser.function("Name", params);

    // Act
    AstIdentifier actualIdentifierResult = parser.identifier("Name");

    // Assert
    assertEquals("Name", actualIdentifierResult.getName());
    assertEquals("Name", actualIdentifierResult.toString());
    assertEquals(0, actualIdentifierResult.getCardinality());
    assertEquals(0, actualIdentifierResult.getIndex());
    assertEquals(1, parser.getIdentifiers().size());
    assertFalse(actualIdentifierResult.isLiteralText());
    assertFalse(actualIdentifierResult.isMethodInvocation());
    assertTrue(actualIdentifierResult.isLeftValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Parser#getFunctions()}
   *   <li>{@link Parser#getIdentifiers()}
   *   <li>{@link Parser#getToken()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    List<FunctionNode> actualFunctions = parser.getFunctions();
    List<IdentifierNode> actualIdentifiers = parser.getIdentifiers();

    // Assert
    assertNull(parser.getToken());
    assertTrue(actualFunctions.isEmpty());
    assertSame(actualFunctions, actualIdentifiers);
  }

  /**
   * Method under test: {@link Parser#Parser(Builder, String)}
   */
  @Test
  void testNewParser() {
    // Arrange
    Builder context = new Builder();

    // Act
    Parser actualParser = new Parser(context, "Input");

    // Assert
    Scanner scanner = actualParser.scanner;
    assertEquals("", scanner.builder.toString());
    assertEquals("Input", scanner.getInput());
    assertNull(actualParser.getToken());
    assertNull(scanner.getToken());
    assertEquals(0, scanner.getPosition());
    assertFalse(scanner.isEval());
    EnumSet<Builder.Feature> featureSet = actualParser.context.features;
    assertTrue(featureSet.isEmpty());
    List<FunctionNode> functions = actualParser.getFunctions();
    assertTrue(functions.isEmpty());
    assertTrue(actualParser.extensions.isEmpty());
    assertSame(functions, actualParser.getIdentifiers());
    assertSame(context.features, featureSet);
  }
}
