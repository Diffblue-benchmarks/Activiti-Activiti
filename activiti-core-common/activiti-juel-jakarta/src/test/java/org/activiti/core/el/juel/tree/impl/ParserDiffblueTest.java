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
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.IdentifierNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.impl.Parser.ParseException;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary.Operator;
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
import org.activiti.core.el.juel.tree.impl.ast.AstUnary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParserDiffblueTest {
  /**
   * Test {@link Parser#Parser(Builder, String)}.
   * <p>
   * Method under test: {@link Parser#Parser(Builder, String)}
   */
  @Test
  @DisplayName("Test new Parser(Builder, String)")
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

  /**
   * Test {@link Parser#createScanner(String)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createScanner(String)}
   */
  @Test
  @DisplayName("Test createScanner(String); given 'java.lang.Object'")
  void testCreateScanner_givenJavaLangObject() {
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
   * Test {@link Parser#createScanner(String)}.
   * <ul>
   *   <li>Given {@link Parser#Parser(Builder, String)} with context is
   * {@link Builder#Builder()} and {@code Input}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createScanner(String)}
   */
  @Test
  @DisplayName("Test createScanner(String); given Parser(Builder, String) with context is Builder() and 'Input'")
  void testCreateScanner_givenParserWithContextIsBuilderAndInput() {
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
   * Test ParseException
   * {@link ParseException#ParseException(int, String, String)}.
   * <p>
   * Method under test:
   * {@link Parser.ParseException#ParseException(int, String, String)}
   */
  @Test
  @DisplayName("Test ParseException new ParseException(int, String, String)")
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
   * Test {@link Parser#putExtensionHandler(ExtensionToken, ExtensionHandler)}.
   * <p>
   * Method under test:
   * {@link Parser#putExtensionHandler(Scanner.ExtensionToken, Parser.ExtensionHandler)}
   */
  @Test
  @DisplayName("Test putExtensionHandler(ExtensionToken, ExtensionHandler)")
  void testPutExtensionHandler() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    parser.putExtensionHandler(new Scanner.ExtensionToken("Image"), mock(Parser.ExtensionHandler.class));

    // Assert
    assertEquals(1, parser.extensions.size());
  }

  /**
   * Test {@link Parser#getExtensionHandler(Token)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#getExtensionHandler(Scanner.Token)}
   */
  @Test
  @DisplayName("Test getExtensionHandler(Token); given 'java.lang.Object'")
  void testGetExtensionHandler_givenJavaLangObject() {
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
   * Test {@link Parser#getExtensionHandler(Token)}.
   * <ul>
   *   <li>Given {@link Parser#Parser(Builder, String)} with context is
   * {@link Builder#Builder()} and {@code Input}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#getExtensionHandler(Scanner.Token)}
   */
  @Test
  @DisplayName("Test getExtensionHandler(Token); given Parser(Builder, String) with context is Builder() and 'Input'")
  void testGetExtensionHandler_givenParserWithContextIsBuilderAndInput() {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act and Assert
    assertNull(parser.getExtensionHandler(new Scanner.Token(Scanner.Symbol.EOF, "Image")));
  }

  /**
   * Test {@link Parser#parseFloat(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return doubleValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#parseFloat(String)}
   */
  @Test
  @DisplayName("Test parseFloat(String); when '42'; then return doubleValue is forty-two")
  void testParseFloat_when42_thenReturnDoubleValueIsFortyTwo() throws Parser.ParseException {
    // Arrange, Act and Assert
    assertEquals(42.0d, (new Parser(new Builder(), "Input")).parseFloat("42").doubleValue());
  }

  /**
   * Test {@link Parser#createAstBinary(AstNode, AstNode, Operator)}.
   * <p>
   * Method under test:
   * {@link Parser#createAstBinary(AstNode, AstNode, AstBinary.Operator)}
   */
  @Test
  @DisplayName("Test createAstBinary(AstNode, AstNode, Operator)")
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
   * Test {@link Parser#createAstBracket(AstNode, AstNode, boolean, boolean)}.
   * <ul>
   *   <li>Then return Cardinality is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Parser#createAstBracket(AstNode, AstNode, boolean, boolean)}
   */
  @Test
  @DisplayName("Test createAstBracket(AstNode, AstNode, boolean, boolean); then return Cardinality is two")
  void testCreateAstBracket_thenReturnCardinalityIsTwo() {
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
   * Test {@link Parser#createAstBracket(AstNode, AstNode, boolean, boolean)}.
   * <ul>
   *   <li>When {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left is
   * {@link AstNull} (default constructor) and right is {@link AstNull} (default
   * constructor) and {@link Operator}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Parser#createAstBracket(AstNode, AstNode, boolean, boolean)}
   */
  @Test
  @DisplayName("Test createAstBracket(AstNode, AstNode, boolean, boolean); when AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  void testCreateAstBracket_whenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator() {
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
   * Test {@link Parser#createAstChoice(AstNode, AstNode, AstNode)}.
   * <ul>
   *   <li>When {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left is
   * {@link AstNull} (default constructor) and right is {@link AstNull} (default
   * constructor) and {@link Operator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstChoice(AstNode, AstNode, AstNode)}
   */
  @Test
  @DisplayName("Test createAstChoice(AstNode, AstNode, AstNode); when AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  void testCreateAstChoice_whenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator() {
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
   * Test {@link Parser#createAstChoice(AstNode, AstNode, AstNode)}.
   * <ul>
   *   <li>When {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstChoice(AstNode, AstNode, AstNode)}
   */
  @Test
  @DisplayName("Test createAstChoice(AstNode, AstNode, AstNode); when AstNull (default constructor)")
  void testCreateAstChoice_whenAstNull() {
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
   * Test {@link Parser#createAstComposite(List)}.
   * <p>
   * Method under test: {@link Parser#createAstComposite(List)}
   */
  @Test
  @DisplayName("Test createAstComposite(List)")
  void testCreateAstComposite() {
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
   * Test {@link Parser#createAstComposite(List)}.
   * <ul>
   *   <li>Given {@link AstNull} (default constructor).</li>
   *   <li>Then return Cardinality is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstComposite(List)}
   */
  @Test
  @DisplayName("Test createAstComposite(List); given AstNull (default constructor); then return Cardinality is one")
  void testCreateAstComposite_givenAstNull_thenReturnCardinalityIsOne() {
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
   * Test {@link Parser#createAstComposite(List)}.
   * <ul>
   *   <li>Given {@link AstNull} (default constructor).</li>
   *   <li>Then return Cardinality is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstComposite(List)}
   */
  @Test
  @DisplayName("Test createAstComposite(List); given AstNull (default constructor); then return Cardinality is two")
  void testCreateAstComposite_givenAstNull_thenReturnCardinalityIsTwo() {
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
   * Test {@link Parser#createAstComposite(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Cardinality is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstComposite(List)}
   */
  @Test
  @DisplayName("Test createAstComposite(List); when ArrayList(); then return Cardinality is zero")
  void testCreateAstComposite_whenArrayList_thenReturnCardinalityIsZero() {
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
   * Test {@link Parser#createAstDot(AstNode, String, boolean)}.
   * <ul>
   *   <li>Given {@link Builder#Builder(Feature[])} with features is
   * {@code IGNORE_RETURN_TYPE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstDot(AstNode, String, boolean)}
   */
  @Test
  @DisplayName("Test createAstDot(AstNode, String, boolean); given Builder(Feature[]) with features is 'IGNORE_RETURN_TYPE'")
  void testCreateAstDot_givenBuilderWithFeaturesIsIgnoreReturnType() {
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
   * Test {@link Parser#createAstDot(AstNode, String, boolean)}.
   * <ul>
   *   <li>Given {@link Parser#Parser(Builder, String)} with context is
   * {@link Builder#Builder()} and {@code Input}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstDot(AstNode, String, boolean)}
   */
  @Test
  @DisplayName("Test createAstDot(AstNode, String, boolean); given Parser(Builder, String) with context is Builder() and 'Input'")
  void testCreateAstDot_givenParserWithContextIsBuilderAndInput() {
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
   * Test {@link Parser#createAstDot(AstNode, String, boolean)}.
   * <ul>
   *   <li>When {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left is
   * {@link AstNull} (default constructor) and right is {@link AstNull} (default
   * constructor) and {@link Operator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstDot(AstNode, String, boolean)}
   */
  @Test
  @DisplayName("Test createAstDot(AstNode, String, boolean); when AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  void testCreateAstDot_whenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator() {
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
   * Test {@link Parser#createAstFunction(String, int, AstParameters)}.
   * <ul>
   *   <li>Given {@link Parser#Parser(Builder, String)} with context is
   * {@link Builder#Builder()} and {@code Input}.</li>
   *   <li>Then return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Parser#createAstFunction(String, int, AstParameters)}
   */
  @Test
  @DisplayName("Test createAstFunction(String, int, AstParameters); given Parser(Builder, String) with context is Builder() and 'Input'; then return 'Name'")
  void testCreateAstFunction_givenParserWithContextIsBuilderAndInput_thenReturnName() {
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
   * Test {@link Parser#createAstFunction(String, int, AstParameters)}.
   * <ul>
   *   <li>Given {@link StringBuilder#StringBuilder(String)} with {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Parser#createAstFunction(String, int, AstParameters)}
   */
  @Test
  @DisplayName("Test createAstFunction(String, int, AstParameters); given StringBuilder(String) with 'foo'")
  void testCreateAstFunction_givenStringBuilderWithFoo() {
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
   * Test {@link Parser#createAstIdentifier(String, int)}.
   * <ul>
   *   <li>Given {@link Builder#Builder(Feature[])} with features is
   * {@code IGNORE_RETURN_TYPE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstIdentifier(String, int)}
   */
  @Test
  @DisplayName("Test createAstIdentifier(String, int); given Builder(Feature[]) with features is 'IGNORE_RETURN_TYPE'")
  void testCreateAstIdentifier_givenBuilderWithFeaturesIsIgnoreReturnType() {
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
   * Test {@link Parser#createAstIdentifier(String, int)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstIdentifier(String, int)}
   */
  @Test
  @DisplayName("Test createAstIdentifier(String, int); given 'java.lang.Object'; then return 'Name'")
  void testCreateAstIdentifier_givenJavaLangObject_thenReturnName() {
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
   * Test {@link Parser#createAstIdentifier(String, int)}.
   * <ul>
   *   <li>Given {@link Parser#Parser(Builder, String)} with context is
   * {@link Builder#Builder()} and {@code Input}.</li>
   *   <li>Then return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstIdentifier(String, int)}
   */
  @Test
  @DisplayName("Test createAstIdentifier(String, int); given Parser(Builder, String) with context is Builder() and 'Input'; then return 'Name'")
  void testCreateAstIdentifier_givenParserWithContextIsBuilderAndInput_thenReturnName() {
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
   * Test {@link Parser#createAstMethod(AstProperty, AstParameters)}.
   * <ul>
   *   <li>When {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left is
   * {@link AstNull} (default constructor) and right is {@link AstNull} (default
   * constructor) and {@link Operator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstMethod(AstProperty, AstParameters)}
   */
  @Test
  @DisplayName("Test createAstMethod(AstProperty, AstParameters); when AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  void testCreateAstMethod_whenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator() {
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
   * Test {@link Parser#createAstMethod(AstProperty, AstParameters)}.
   * <ul>
   *   <li>When {@link AstDot#AstDot(AstNode, String, boolean)} with base is
   * {@link AstNull} (default constructor) and {@code Property} and lvalue is
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#createAstMethod(AstProperty, AstParameters)}
   */
  @Test
  @DisplayName("Test createAstMethod(AstProperty, AstParameters); when AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  void testCreateAstMethod_whenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
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
   * Test {@link Parser#createAstUnary(AstNode, Operator)}.
   * <p>
   * Method under test: {@link Parser#createAstUnary(AstNode, AstUnary.Operator)}
   */
  @Test
  @DisplayName("Test createAstUnary(AstNode, Operator)")
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
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Parser#getFunctions()}
   *   <li>{@link Parser#getIdentifiers()}
   *   <li>{@link Parser#getToken()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
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
   * Test {@link Parser#lookahead(int)}.
   * <p>
   * Method under test: {@link Parser#lookahead(int)}
   */
  @Test
  @DisplayName("Test lookahead(int)")
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
   * Test {@link Parser#lookahead(int)}.
   * <p>
   * Method under test: {@link Parser#lookahead(int)}
   */
  @Test
  @DisplayName("Test lookahead(int)")
  void testLookahead2() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "");

    // Act
    Scanner.Token actualLookaheadResult = parser.lookahead(1);

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("", scanner.builder.toString());
    assertNull(actualLookaheadResult.getImage());
    assertEquals(0, scanner.getPosition());
    assertEquals(0, actualLookaheadResult.getSize());
    assertEquals(Scanner.Symbol.EOF, actualLookaheadResult.getSymbol());
    assertTrue(scanner.isEval());
  }

  /**
   * Test {@link Parser#lookahead(int)}.
   * <p>
   * Method under test: {@link Parser#lookahead(int)}
   */
  @Test
  @DisplayName("Test lookahead(int)")
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
   * Test {@link Parser#consumeToken()}.
   * <p>
   * Method under test: {@link Parser#consumeToken()}
   */
  @Test
  @DisplayName("Test consumeToken()")
  void testConsumeToken() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "");

    // Act
    parser.consumeToken();

    // Assert
    Scanner.Token token = parser.getToken();
    assertNull(token.getImage());
    assertEquals(0, token.getSize());
    assertEquals(Scanner.Symbol.EOF, token.getSymbol());
    Scanner scanner = parser.scanner;
    assertTrue(scanner.isEval());
    assertSame(token, scanner.getToken());
  }

  /**
   * Test {@link Parser#consumeToken()}.
   * <p>
   * Method under test: {@link Parser#consumeToken()}
   */
  @Test
  @DisplayName("Test consumeToken()")
  void testConsumeToken2() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "org.activiti.core.el.juel.tree.impl.Parser$LookaheadToken");

    // Act
    parser.consumeToken();

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("org.activiti.core.el.juel.tree.impl.Parser$LookaheadToken", scanner.builder.toString());
    Scanner.Token token = parser.getToken();
    assertEquals("org.activiti.core.el.juel.tree.impl.Parser$LookaheadToken", token.getImage());
    assertEquals(57, token.getSize());
    assertSame(token, scanner.getToken());
  }

  /**
   * Test {@link Parser#consumeToken()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#consumeToken()}
   */
  @Test
  @DisplayName("Test consumeToken(); given 'java.lang.Object'")
  void testConsumeToken_givenJavaLangObject() throws Parser.ParseException, Scanner.ScanException {
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
    parser.consumeToken();

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("Input", scanner.builder.toString());
    Scanner.Token token = parser.getToken();
    assertEquals("Input", token.getImage());
    assertEquals(5, token.getSize());
    assertSame(token, scanner.getToken());
  }

  /**
   * Test {@link Parser#consumeToken()}.
   * <ul>
   *   <li>Given {@link Parser#Parser(Builder, String)} with context is
   * {@link Builder#Builder()} and {@code Input}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#consumeToken()}
   */
  @Test
  @DisplayName("Test consumeToken(); given Parser(Builder, String) with context is Builder() and 'Input'")
  void testConsumeToken_givenParserWithContextIsBuilderAndInput() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    parser.consumeToken();

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("Input", scanner.builder.toString());
    Scanner.Token token = parser.getToken();
    assertEquals("Input", token.getImage());
    assertEquals(5, token.getSize());
    assertSame(token, scanner.getToken());
  }

  /**
   * Test {@link Parser#consumeToken()}.
   * <ul>
   *   <li>Then {@link Parser#Parser(Builder, String)} with context is
   * {@link Builder#Builder()} and {@code Input} Token Symbol is
   * {@code TEXT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#consumeToken()}
   */
  @Test
  @DisplayName("Test consumeToken(); then Parser(Builder, String) with context is Builder() and 'Input' Token Symbol is 'TEXT'")
  void testConsumeToken_thenParserWithContextIsBuilderAndInputTokenSymbolIsText()
      throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    parser.lookahead(1);

    // Act
    parser.consumeToken();

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("Input", scanner.builder.toString());
    Scanner.Token token = parser.getToken();
    assertEquals("Input", token.getImage());
    assertEquals(5, token.getSize());
    assertEquals(Scanner.Symbol.TEXT, token.getSymbol());
    assertTrue(scanner.isEval());
  }

  /**
   * Test {@link Parser#tree()}.
   * <p>
   * Method under test: {@link Parser#tree()}
   */
  @Test
  @DisplayName("Test tree()")
  void testTree() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "");

    // Act
    parser.tree();

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("", scanner.builder.toString());
    assertEquals(0, scanner.getPosition());
  }

  /**
   * Test {@link Parser#tree()}.
   * <p>
   * Method under test: {@link Parser#tree()}
   */
  @Test
  @DisplayName("Test tree()")
  void testTree2() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "org.activiti.core.el.juel.tree.impl.Parser$LookaheadToken");

    // Act
    parser.tree();

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("org.activiti.core.el.juel.tree.impl.Parser$LookaheadToken", scanner.builder.toString());
    assertEquals(57, scanner.getPosition());
  }

  /**
   * Test {@link Parser#tree()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then FunctionNodes return {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#tree()}
   */
  @Test
  @DisplayName("Test tree(); given 'java.lang.Object'; then FunctionNodes return List")
  void testTree_givenJavaLangObject_thenFunctionNodesReturnList() throws Parser.ParseException, Scanner.ScanException {
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
    assertEquals("Name", getResult.toString());
    assertEquals("Name", getResult.getName());
    assertEquals(0, getResult.getIndex());
    assertEquals(0, getResult.getParamCount());
    assertEquals(1, getResult.getCardinality());
    assertFalse(getResult.isVarArgs());
    assertFalse(((AstFunction) getResult).isLeftValue());
    assertFalse(((AstFunction) getResult).isLiteralText());
    assertFalse(((AstFunction) getResult).isMethodInvocation());
    assertTrue(((List<IdentifierNode>) identifierNodes).isEmpty());
  }

  /**
   * Test {@link Parser#tree()}.
   * <ul>
   *   <li>Given {@link Parser#Parser(Builder, String)} with context is
   * {@link Builder#Builder()} and {@code Input}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#tree()}
   */
  @Test
  @DisplayName("Test tree(); given Parser(Builder, String) with context is Builder() and 'Input'")
  void testTree_givenParserWithContextIsBuilderAndInput() throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");

    // Act
    parser.tree();

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("Input", scanner.builder.toString());
    assertEquals(5, scanner.getPosition());
  }

  /**
   * Test {@link Parser#tree()}.
   * <ul>
   *   <li>Then {@link Parser#Parser(Builder, String)} with context is
   * {@link Builder#Builder()} and {@code Input} {@link Parser#scanner} Eval.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#tree()}
   */
  @Test
  @DisplayName("Test tree(); then Parser(Builder, String) with context is Builder() and 'Input' scanner Eval")
  void testTree_thenParserWithContextIsBuilderAndInputScannerEval()
      throws Parser.ParseException, Scanner.ScanException {
    // Arrange
    Parser parser = new Parser(new Builder(), "Input");
    parser.lookahead(1);

    // Act
    parser.tree();

    // Assert
    Scanner scanner = parser.scanner;
    assertEquals("Input", scanner.builder.toString());
    assertEquals(5, scanner.getPosition());
    assertTrue(scanner.isEval());
  }

  /**
   * Test {@link Parser#function(String, AstParameters)}.
   * <ul>
   *   <li>Given {@link Parser#Parser(Builder, String)} with context is
   * {@link Builder#Builder()} and {@code Input}.</li>
   *   <li>Then return {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#function(String, AstParameters)}
   */
  @Test
  @DisplayName("Test function(String, AstParameters); given Parser(Builder, String) with context is Builder() and 'Input'; then return 'Name'")
  void testFunction_givenParserWithContextIsBuilderAndInput_thenReturnName() {
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
   * Test {@link Parser#function(String, AstParameters)}.
   * <ul>
   *   <li>Given {@link StringBuilder#StringBuilder(String)} with {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#function(String, AstParameters)}
   */
  @Test
  @DisplayName("Test function(String, AstParameters); given StringBuilder(String) with 'foo'")
  void testFunction_givenStringBuilderWithFoo() {
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
   * Test {@link Parser#identifier(String)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#identifier(String)}
   */
  @Test
  @DisplayName("Test identifier(String); given 'java.lang.Object'")
  void testIdentifier_givenJavaLangObject() {
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
   * Test {@link Parser#identifier(String)}.
   * <ul>
   *   <li>Given {@link Parser#Parser(Builder, String)} with context is
   * {@link Builder#Builder()} and {@code Input}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#identifier(String)}
   */
  @Test
  @DisplayName("Test identifier(String); given Parser(Builder, String) with context is Builder() and 'Input'")
  void testIdentifier_givenParserWithContextIsBuilderAndInput() {
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
   * Test {@link Parser#identifier(String)}.
   * <ul>
   *   <li>Then {@link Parser#Parser(Builder, String)} with context is
   * {@link Builder#Builder(Feature[])} and {@code Input} Identifiers size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Parser#identifier(String)}
   */
  @Test
  @DisplayName("Test identifier(String); then Parser(Builder, String) with context is Builder(Feature[]) and 'Input' Identifiers size is one")
  void testIdentifier_thenParserWithContextIsBuilderAndInputIdentifiersSizeIsOne() {
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
}
