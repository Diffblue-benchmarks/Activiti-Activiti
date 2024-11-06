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
import static org.junit.jupiter.api.Assertions.assertTrue;
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

class AstBracketDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AstBracket#AstBracket(AstNode, AstNode, boolean, boolean)}
   *   <li>{@link AstBracket#toString()}
   *   <li>{@link AstBracket#getCardinality()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    AstNull base = new AstNull();

    // Act
    AstBracket actualAstBracket = new AstBracket(base, new AstNull(), true, true);
    String actualToStringResult = actualAstBracket.toString();

    // Assert
    assertEquals("[...]", actualToStringResult);
    assertEquals(2, actualAstBracket.getCardinality());
    assertTrue(actualAstBracket.isLeftValue());
    assertSame(base, actualAstBracket.getPrefix());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link AstBracket#AstBracket(AstNode, AstNode, boolean, boolean, boolean)}
   *   <li>{@link AstBracket#toString()}
   *   <li>{@link AstBracket#getCardinality()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters2() {
    // Arrange
    AstNull base = new AstNull();

    // Act
    AstBracket actualAstBracket = new AstBracket(base, new AstNull(), true, true, true);
    String actualToStringResult = actualAstBracket.toString();

    // Assert
    assertEquals("[...]", actualToStringResult);
    assertEquals(2, actualAstBracket.getCardinality());
    assertTrue(actualAstBracket.isLeftValue());
    assertSame(base, actualAstBracket.getPrefix());
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  void testGetProperty() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  void testGetProperty2() throws ELException {
    // Arrange
    new ELException("An error occurred");
    AstNull base = new AstNull();
    AstNull base2 = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstBracket(base2, new AstNull(), true, true), true, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  void testGetProperty3() throws ELException {
    // Arrange
    new ELException("An error occurred");
    AstNull base = new AstNull();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstChoice(question, yes, new AstNull()), true, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  void testGetProperty4() throws ELException {
    // Arrange
    new ELException("An error occurred");
    AstNull base = new AstNull();
    AstNull base2 = new AstNull();
    AstBracket base3 = new AstBracket(base2, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(base, new AstBracket(base3, new AstNull(), true, true), true, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  void testGetProperty5() throws ELException {
    // Arrange
    new ELException("An error occurred");
    AstNull base = new AstNull();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice base2 = new AstChoice(question, yes, new AstNull());

    AstBracket astBracket = new AstBracket(base, new AstBracket(base2, new AstNull(), true, true), true, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   * <ul>
   *   <li>Then calls
   * {@link Operator#eval(Bindings, ELContext, AstNode, AstNode)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext); then calls eval(Bindings, ELContext, AstNode, AstNode)")
  void testGetProperty_thenCallsEval() throws ELException {
    // Arrange
    new ELException("An error occurred");
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstBracket property = new AstBracket(base, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(new AstNull(), property, true, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act
    Object actualProperty = astBracket.getProperty(bindings, new SimpleContext());

    // Assert
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
    assertNull(actualProperty);
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull[null]}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull[null]'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNull() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astBracket.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull()[null]}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull()[null]'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNull2() {
    // Arrange
    AstFunction base = new AstFunction("null", 1, new AstParameters(new ArrayList<>()));

    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astBracket.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull()[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull[null][null]}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull[null][null]'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNull() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(base2, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astBracket.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull[null][null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull.null[null]}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull.null[null]'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNull2() {
    // Arrange
    AstDot base = new AstDot(new AstNull(), "null", true);

    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astBracket.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull.null[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull[null[null]]}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull[null[null]]'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNull3() {
    // Arrange
    AstNull base = new AstNull();
    AstNull base2 = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstBracket(base2, new AstNull(), true, true), true, true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astBracket.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull[null[null]]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull[null.null]}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull[null.null]'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNull4() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstDot(new AstNull(), "null", true), true, true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astBracket.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull[null.null]", b.toString());
  }

  /**
   * Test {@link AstBracket#getChild(int)}.
   * <p>
   * Method under test: {@link AstBracket#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int)")
  void testGetChild() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);

    // Act and Assert
    assertSame(astBracket.property, astBracket.getChild(1));
  }

  /**
   * Test {@link AstBracket#getChild(int)}.
   * <p>
   * Method under test: {@link AstBracket#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int)")
  void testGetChild2() {
    // Arrange
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class));

    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);

    // Act and Assert
    assertSame(astBracket.property, astBracket.getChild(1));
  }

  /**
   * Test {@link AstBracket#getChild(int)}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstBracket#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when minus one; then return 'null'")
  void testGetChild_whenMinusOne_thenReturnNull() {
    // Arrange
    AstNull base = new AstNull();

    // Act and Assert
    assertNull((new AstBracket(base, new AstNull(), true, true)).getChild(-1));
  }

  /**
   * Test {@link AstBracket#getChild(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstBracket#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when zero; then return AstNull (default constructor)")
  void testGetChild_whenZero_thenReturnAstNull() {
    // Arrange
    AstNull base = new AstNull();

    // Act and Assert
    assertSame(base, (new AstBracket(base, new AstNull(), true, true)).getChild(0));
  }
}
