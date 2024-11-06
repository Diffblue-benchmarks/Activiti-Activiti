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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ELContext;
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

class AstEvalDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AstEval#AstEval(AstNode, boolean)}
   *   <li>{@link AstEval#getCardinality()}
   *   <li>{@link AstEval#isDeferred()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    AstEval actualAstEval = new AstEval(new AstNull(), true);
    int actualCardinality = actualAstEval.getCardinality();

    // Assert
    assertEquals(1, actualCardinality);
    assertTrue(actualAstEval.isDeferred());
  }

  /**
   * Test {@link AstEval#isLeftValue()}.
   * <ul>
   *   <li>Given {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left
   * is {@link AstNull} (default constructor) and right is {@link AstNull}
   * (default constructor) and {@link Operator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); given AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  void testIsLeftValue_givenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertFalse((new AstEval(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), true)).isLeftValue());
  }

  /**
   * Test {@link AstEval#isLeftValue()}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'; then return 'false'")
  void testIsLeftValue_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AstEval(new AstEval(new AstNull(), true), true)).isLeftValue());
  }

  /**
   * Test {@link AstEval#isLeftValue()}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstNull} (default constructor) and deferred is {@code true}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'; then return 'false'")
  void testIsLeftValue_givenAstEvalWithChildIsAstNullAndDeferredIsTrue_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AstEval(new AstNull(), true)).isLeftValue());
  }

  /**
   * Test {@link AstEval#isLeftValue()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); then return 'true'")
  void testIsLeftValue_thenReturnTrue() {
    // Arrange
    AstNull base = new AstNull();

    // Act and Assert
    assertTrue((new AstEval(new AstBracket(base, new AstNull(), true, true), true)).isLeftValue());
  }

  /**
   * Test {@link AstEval#isMethodInvocation()}.
   * <p>
   * Method under test: {@link AstEval#isMethodInvocation()}
   */
  @Test
  @DisplayName("Test isMethodInvocation()")
  void testIsMethodInvocation() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertFalse(
        (new AstEval(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), true)).isMethodInvocation());
  }

  /**
   * Test {@link AstEval#isMethodInvocation()}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isMethodInvocation()}
   */
  @Test
  @DisplayName("Test isMethodInvocation(); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'")
  void testIsMethodInvocation_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue() {
    // Arrange, Act and Assert
    assertFalse((new AstEval(new AstEval(new AstNull(), true), true)).isMethodInvocation());
  }

  /**
   * Test {@link AstEval#isMethodInvocation()}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstNull} (default constructor) and deferred is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isMethodInvocation()}
   */
  @Test
  @DisplayName("Test isMethodInvocation(); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'")
  void testIsMethodInvocation_givenAstEvalWithChildIsAstNullAndDeferredIsTrue() {
    // Arrange, Act and Assert
    assertFalse((new AstEval(new AstNull(), true)).isMethodInvocation());
  }

  /**
   * Test {@link AstEval#getValueReference(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'")
  void testGetValueReference_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astEval.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#getValueReference(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstNull} (default constructor) and deferred is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'")
  void testGetValueReference_givenAstEvalWithChildIsAstNullAndDeferredIsTrue() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astEval.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#getValueReference(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name}
   * and index is zero.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); given AstIdentifier(String, int) with 'Name' and index is zero; then return 'null'")
  void testGetValueReference_givenAstIdentifierWithNameAndIndexIsZero_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astEval.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with
   * question is {@link AstNull} (default constructor) and yes is {@link AstNull}
   * (default constructor) and no is {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstNull (default constructor) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  void testEval_givenAstChoiceWithQuestionIsAstNullAndYesIsAstNullAndNoIsAstNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstEval astEval = new AstEval(new AstChoice(question, yes, new AstNull()), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astEval.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'; then return 'null'")
  void testEval_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astEval.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstNull} (default constructor) and deferred is {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'; then return 'null'")
  void testEval_givenAstEvalWithChildIsAstNullAndDeferredIsTrue_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astEval.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#toString()}.
   * <ul>
   *   <li>Given {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left
   * is {@link AstNull} (default constructor) and right is {@link AstNull}
   * (default constructor) and {@link Operator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#toString()}
   */
  @Test
  @DisplayName("Test toString(); given AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  void testToString_givenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertEquals("#{...}",
        (new AstEval(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), true)).toString());
  }

  /**
   * Test {@link AstEval#toString()}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstNull} (default constructor) and deferred is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#toString()}
   */
  @Test
  @DisplayName("Test toString(); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'")
  void testToString_givenAstEvalWithChildIsAstNullAndDeferredIsTrue() {
    // Arrange, Act and Assert
    assertEquals("#{...}", (new AstEval(new AstNull(), true)).toString());
  }

  /**
   * Test {@link AstEval#toString()}.
   * <ul>
   *   <li>Then return {@code ${...}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#toString()}
   */
  @Test
  @DisplayName("Test toString(); then return '${...}'")
  void testToString_thenReturnDollarSignLeftCurlyBracketDotDotDotRightCurlyBracket() {
    // Arrange, Act and Assert
    assertEquals("${...}", (new AstEval(new AstNull(), false)).toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foo#{#{()}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo#{#{()}'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoo() {
    // Arrange
    AstEval astEval = new AstEval(new AstFunction("#{", 1, new AstParameters(new ArrayList<>())), true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astEval.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo#{#{()}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foo#{null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo#{null}'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astEval.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo#{null}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foo#{#{null}}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo#{#{null}}'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooNull2() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astEval.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo#{#{null}}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foo${null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo${null}'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooNull3() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), false);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astEval.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo${null}", b.toString());
  }

  /**
   * Test {@link AstEval#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstEval#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[]); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'; then return 'null'")
  void testGetMethodInfo_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[]{forNameResult};

    // Act and Assert
    assertNull(astEval.getMethodInfo(bindings, context, returnType, paramTypes));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, paramTypes[0]);
  }

  /**
   * Test {@link AstEval#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstNull} (default constructor) and deferred is {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstEval#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[]); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'; then return 'null'")
  void testGetMethodInfo_givenAstEvalWithChildIsAstNullAndDeferredIsTrue_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[]{forNameResult};

    // Act and Assert
    assertNull(astEval.getMethodInfo(bindings, context, returnType, paramTypes));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, paramTypes[0]);
  }

  /**
   * Test {@link AstEval#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>Given {@link AstBinary}
   * {@link AstRightValue#invoke(Bindings, ELContext, Class, Class[], Object[])}
   * return {@code Invoke}.</li>
   *   <li>Then return {@code Invoke}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstEval#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); given AstBinary invoke(Bindings, ELContext, Class, Class[], Object[]) return 'Invoke'; then return 'Invoke'")
  void testInvoke_givenAstBinaryInvokeReturnInvoke_thenReturnInvoke() {
    // Arrange
    AstBinary child = mock(AstBinary.class);
    when(child.invoke(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<Class<Object>>any(),
        Mockito.<Class<Object>[]>any(), Mockito.<Object[]>any())).thenReturn("Invoke");
    AstEval astEval = new AstEval(child, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[]{forNameResult};

    // Act
    Object actualInvokeResult = astEval.invoke(bindings, context, returnType, paramTypes, new Object[]{"Param Values"});

    // Assert
    verify(child).invoke(isA(Bindings.class), isA(ELContext.class), isA(Class.class), isA(Class[].class),
        isA(Object[].class));
    assertEquals("Invoke", actualInvokeResult);
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, paramTypes[0]);
  }

  /**
   * Test {@link AstEval#getType(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'; then return 'null'")
  void testGetType_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astEval.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#getType(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstNull} (default constructor) and deferred is {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'; then return 'null'")
  void testGetType_givenAstEvalWithChildIsAstNullAndDeferredIsTrue_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astEval.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#getType(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name}
   * and index is zero.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); given AstIdentifier(String, int) with 'Name' and index is zero; then return 'null'")
  void testGetType_givenAstIdentifierWithNameAndIndexIsZero_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astEval.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#isLiteralText()}.
   * <ul>
   *   <li>Given {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left
   * is {@link AstNull} (default constructor) and right is {@link AstNull}
   * (default constructor) and {@link Operator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isLiteralText()}
   */
  @Test
  @DisplayName("Test isLiteralText(); given AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  void testIsLiteralText_givenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertFalse(
        (new AstEval(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), true)).isLiteralText());
  }

  /**
   * Test {@link AstEval#isLiteralText()}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isLiteralText()}
   */
  @Test
  @DisplayName("Test isLiteralText(); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'")
  void testIsLiteralText_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue() {
    // Arrange, Act and Assert
    assertFalse((new AstEval(new AstEval(new AstNull(), true), true)).isLiteralText());
  }

  /**
   * Test {@link AstEval#isLiteralText()}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstNull} (default constructor) and deferred is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isLiteralText()}
   */
  @Test
  @DisplayName("Test isLiteralText(); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'")
  void testIsLiteralText_givenAstEvalWithChildIsAstNullAndDeferredIsTrue() {
    // Arrange, Act and Assert
    assertFalse((new AstEval(new AstNull(), true)).isLiteralText());
  }

  /**
   * Test {@link AstEval#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'")
  void testIsReadOnly_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = astEval.isReadOnly(bindings, context);

    // Assert
    assertFalse(context.isPropertyResolved());
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link AstEval#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstNull} (default constructor) and deferred is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'")
  void testIsReadOnly_givenAstEvalWithChildIsAstNullAndDeferredIsTrue() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = astEval.isReadOnly(bindings, context);

    // Assert
    assertFalse(context.isPropertyResolved());
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link AstEval#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name}
   * and index is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); given AstIdentifier(String, int) with 'Name' and index is zero")
  void testIsReadOnly_givenAstIdentifierWithNameAndIndexIsZero() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = astEval.isReadOnly(bindings, context);

    // Assert
    assertFalse(context.isPropertyResolved());
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link AstEval#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); then return 'false'")
  void testIsReadOnly_thenReturnFalse() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext();

    // Act and Assert
    assertFalse(astEval.isReadOnly(bindings, context));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link AstEval#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>Given {@link AstBinary}
   * {@link AstRightValue#setValue(Bindings, ELContext, Object)} does
   * nothing.</li>
   *   <li>Then calls
   * {@link AstRightValue#setValue(Bindings, ELContext, Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); given AstBinary setValue(Bindings, ELContext, Object) does nothing; then calls setValue(Bindings, ELContext, Object)")
  void testSetValue_givenAstBinarySetValueDoesNothing_thenCallsSetValue() {
    // Arrange
    AstBinary child = mock(AstBinary.class);
    doNothing().when(child).setValue(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<Object>any());
    AstEval astEval = new AstEval(child, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act
    astEval.setValue(bindings, new SimpleContext(), "Value");

    // Assert that nothing has changed
    verify(child).setValue(isA(Bindings.class), isA(ELContext.class), isA(Object.class));
  }

  /**
   * Test {@link AstEval#getChild(int)}.
   * <ul>
   *   <li>Given {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left
   * is {@link AstNull} (default constructor) and right is {@link AstNull}
   * (default constructor) and {@link Operator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); given AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  void testGetChild_givenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertNull((new AstEval(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), true)).getChild(1));
  }

  /**
   * Test {@link AstEval#getChild(int)}.
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is
   * {@link AstNull} (default constructor) and deferred is {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'; then return 'null'")
  void testGetChild_givenAstEvalWithChildIsAstNullAndDeferredIsTrue_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new AstEval(new AstNull(), true)).getChild(1));
  }

  /**
   * Test {@link AstEval#getChild(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstEval#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when zero; then return AstNull (default constructor)")
  void testGetChild_whenZero_thenReturnAstNull() {
    // Arrange
    AstNull child = new AstNull();

    // Act and Assert
    assertSame(child, (new AstEval(child, true)).getChild(0));
  }
}
