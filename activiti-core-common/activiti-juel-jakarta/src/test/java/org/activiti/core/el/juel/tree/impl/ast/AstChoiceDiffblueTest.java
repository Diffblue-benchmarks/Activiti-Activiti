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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
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

class AstChoiceDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AstChoice#AstChoice(AstNode, AstNode, AstNode)}
   *   <li>{@link AstChoice#toString()}
   *   <li>{@link AstChoice#getCardinality()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    // Act
    AstChoice actualAstChoice = new AstChoice(question, yes, new AstNull());
    String actualToStringResult = actualAstChoice.toString();

    // Assert
    assertEquals("?", actualToStringResult);
    assertEquals(3, actualAstChoice.getCardinality());
  }

  /**
   * Test {@link AstChoice#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with
   * question is {@link AstBoolean#AstBoolean(boolean)} and yes is {@link AstNull}
   * (default constructor) and no is {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstBoolean(boolean) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  void testEval_givenAstChoiceWithQuestionIsAstBooleanAndYesIsAstNullAndNoIsAstNull() throws ELException {
    // Arrange
    AstBoolean question = new AstBoolean(true);
    AstNull yes = new AstNull();
    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astChoice.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstChoice#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with
   * question is {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} and yes is
   * {@link AstNull} (default constructor) and no is {@link AstNull} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstChoice(AstNode, AstNode, AstNode) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  void testEval_givenAstChoiceWithQuestionIsAstChoiceAndYesIsAstNullAndNoIsAstNull() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice question2 = new AstChoice(question, yes, new AstNull());

    AstNull yes2 = new AstNull();
    AstChoice astChoice = new AstChoice(question2, yes2, new AstNull());
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Boolean>>any())).thenReturn(true);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter2, "Object", type)}, converter);

    // Act
    Object actualEvalResult = astChoice.eval(bindings, new SimpleContext());

    // Assert
    verify(converter, atLeast(1)).convert(isNull(), isA(Class.class));
    assertNull(actualEvalResult);
  }

  /**
   * Test {@link AstChoice#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with
   * question is {@link AstNull} (default constructor) and yes is
   * {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} and no is
   * {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstNull (default constructor) and yes is AstChoice(AstNode, AstNode, AstNode) and no is AstNull (default constructor)")
  void testEval_givenAstChoiceWithQuestionIsAstNullAndYesIsAstChoiceAndNoIsAstNull() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull question2 = new AstNull();
    AstNull yes = new AstNull();
    AstChoice yes2 = new AstChoice(question2, yes, new AstNull());

    AstChoice astChoice = new AstChoice(question, yes2, new AstNull());
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Boolean>>any())).thenReturn(true);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter2, "Object", type)}, converter);

    // Act
    Object actualEvalResult = astChoice.eval(bindings, new SimpleContext());

    // Assert
    verify(converter, atLeast(1)).convert(isNull(), isA(Class.class));
    assertNull(actualEvalResult);
  }

  /**
   * Test {@link AstChoice#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with
   * question is {@link AstNull} (default constructor) and yes is {@link AstNull}
   * (default constructor) and no is {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstNull (default constructor) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  void testEval_givenAstChoiceWithQuestionIsAstNullAndYesIsAstNullAndNoIsAstNull() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astChoice.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   * <p>
   * Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  void testAppendStructure() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice question2 = new AstChoice(question, yes, new AstNull());

    AstNull yes2 = new AstNull();
    AstChoice astChoice = new AstChoice(question2, yes2, new AstNull());
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astChoice.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull ? null : null ? null : null", b.toString());
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   * <p>
   * Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  void testAppendStructure2() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstNull question2 = new AstNull();
    AstNull yes2 = new AstNull();
    AstChoice astChoice = new AstChoice(question, yes, new AstChoice(question2, yes2, new AstNull()));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astChoice.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull ? null : null ? null : null", b.toString());
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull ? null : null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull ? null : null'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astChoice.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull ? null : null", b.toString());
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull() ? null : null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull() ? null : null'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNull2() {
    // Arrange
    AstFunction question = new AstFunction("null", 1, new AstParameters(new ArrayList<>()));

    AstNull yes = new AstNull();
    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astChoice.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull() ? null : null", b.toString());
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull ? null ? null : null : null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull ? null ? null : null : null'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNullNullNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull question2 = new AstNull();
    AstNull yes = new AstNull();
    AstChoice yes2 = new AstChoice(question2, yes, new AstNull());

    AstChoice astChoice = new AstChoice(question, yes2, new AstNull());
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astChoice.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull ? null ? null : null : null", b.toString());
  }

  /**
   * Test {@link AstChoice#getChild(int)}.
   * <ul>
   *   <li>Given {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left
   * is {@link AstNull} (default constructor) and right is {@link AstNull}
   * (default constructor) and {@link Operator}.</li>
   *   <li>When one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); given AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator; when one")
  void testGetChild_givenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator_whenOne() {
    // Arrange
    AstNull left = new AstNull();
    AstBinary question = new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class));

    AstNull yes = new AstNull();

    // Act and Assert
    assertSame(yes, (new AstChoice(question, yes, new AstNull())).getChild(1));
  }

  /**
   * Test {@link AstChoice#getChild(int)}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when minus one; then return 'null'")
  void testGetChild_whenMinusOne_thenReturnNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    // Act and Assert
    assertNull((new AstChoice(question, yes, new AstNull())).getChild(-1));
  }

  /**
   * Test {@link AstChoice#getChild(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when one; then return AstNull (default constructor)")
  void testGetChild_whenOne_thenReturnAstNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    // Act and Assert
    assertSame(yes, (new AstChoice(question, yes, new AstNull())).getChild(1));
  }

  /**
   * Test {@link AstChoice#getChild(int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when two; then return AstNull (default constructor)")
  void testGetChild_whenTwo_thenReturnAstNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstNull no = new AstNull();

    // Act and Assert
    assertSame(no, (new AstChoice(question, yes, no)).getChild(2));
  }

  /**
   * Test {@link AstChoice#getChild(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when zero; then return AstNull (default constructor)")
  void testGetChild_whenZero_thenReturnAstNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    // Act and Assert
    assertSame(question, (new AstChoice(question, yes, new AstNull())).getChild(0));
  }
}
