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
package org.activiti.core.el.juel.tree.impl.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary.Operator;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstNestedDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AstNested#AstNested(AstNode)}
   *   <li>{@link AstNested#toString()}
   *   <li>{@link AstNested#getCardinality()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    AstNested actualAstNested = new AstNested(new AstNull());
    String actualToStringResult = actualAstNested.toString();

    // Assert
    assertEquals("(...)", actualToStringResult);
    assertEquals(1, actualAstNested.getCardinality());
  }

  /**
   * Test {@link AstNested#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with
   * question is {@link AstNull} (default constructor) and yes is {@link AstNull}
   * (default constructor) and no is {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstNested#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstNull (default constructor) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  void testEval_givenAstChoiceWithQuestionIsAstNullAndYesIsAstNullAndNoIsAstNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstNested astNested = new AstNested(new AstChoice(question, yes, new AstNull()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astNested.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstNested#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name}
   * and index is zero.</li>
   *   <li>Then return {@code Convert}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstNested#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstIdentifier(String, int) with 'Name' and index is zero; then return 'Convert'")
  void testEval_givenAstIdentifierWithNameAndIndexIsZero_thenReturnConvert() throws ELException {
    // Arrange
    AstNested astNested = new AstNested(new AstIdentifier("Name", 0));
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Convert");
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act
    Object actualEvalResult = astNested.eval(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertEquals("Convert", actualEvalResult);
  }

  /**
   * Test {@link AstNested#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstNested#AstNested(AstNode)} with child is {@link AstNull}
   * (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstNested#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstNested(AstNode) with child is AstNull (default constructor); then return 'null'")
  void testEval_givenAstNestedWithChildIsAstNull_thenReturnNull() {
    // Arrange
    AstNested astNested = new AstNested(new AstNull());
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astNested.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstNested#appendStructure(StringBuilder, Bindings)}.
   * <p>
   * Method under test: {@link AstNested#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  void testAppendStructure() {
    // Arrange
    AstNested astNested = new AstNested(new AstFunction("(", 1, new AstParameters(new ArrayList<>())));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astNested.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo((())", b.toString());
  }

  /**
   * Test {@link AstNested#appendStructure(StringBuilder, Bindings)}.
   * <p>
   * Method under test: {@link AstNested#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  void testAppendStructure2() {
    // Arrange
    AstNested astNested = new AstNested(new AstFunction("(", -1, new AstParameters(new ArrayList<>())));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astNested.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo((())", b.toString());
  }

  /**
   * Test {@link AstNested#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foo(<fn>())}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstNested#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo(<fn>())'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooFn() {
    // Arrange
    AstNested astNested = new AstNested(new AstFunction("(", 0, new AstParameters(new ArrayList<>())));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astNested.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo(<fn>())", b.toString());
  }

  /**
   * Test {@link AstNested#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foo(null)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstNested#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo(null)'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooNull() {
    // Arrange
    AstNested astNested = new AstNested(new AstNull());
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astNested.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo(null)", b.toString());
  }

  /**
   * Test {@link AstNested#getChild(int)}.
   * <ul>
   *   <li>Given {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left
   * is {@link AstNull} (default constructor) and right is {@link AstNull}
   * (default constructor) and {@link Operator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstNested#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); given AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  void testGetChild_givenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertNull((new AstNested(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)))).getChild(1));
  }

  /**
   * Test {@link AstNested#getChild(int)}.
   * <ul>
   *   <li>Given {@link AstNested#AstNested(AstNode)} with child is {@link AstNull}
   * (default constructor).</li>
   *   <li>When one.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstNested#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); given AstNested(AstNode) with child is AstNull (default constructor); when one; then return 'null'")
  void testGetChild_givenAstNestedWithChildIsAstNull_whenOne_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new AstNested(new AstNull())).getChild(1));
  }

  /**
   * Test {@link AstNested#getChild(int)}.
   * <ul>
   *   <li>Given {@link AstNested#AstNested(AstNode)} with child is {@link AstNull}
   * (default constructor).</li>
   *   <li>When zero.</li>
   *   <li>Then return {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstNested#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); given AstNested(AstNode) with child is AstNull (default constructor); when zero; then return AstNull (default constructor)")
  void testGetChild_givenAstNestedWithChildIsAstNull_whenZero_thenReturnAstNull() {
    // Arrange
    AstNull child = new AstNull();

    // Act and Assert
    assertSame(child, (new AstNested(child)).getChild(0));
  }
}
