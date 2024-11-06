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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.PropertyNotFoundException;
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

class AstMethodDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AstMethod#AstMethod(AstProperty, AstParameters)}
   *   <li>{@link AstMethod#toString()}
   *   <li>{@link AstMethod#getCardinality()}
   *   <li>{@link AstMethod#isLeftValue()}
   *   <li>{@link AstMethod#isLiteralText()}
   *   <li>{@link AstMethod#isMethodInvocation()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    // Act
    AstMethod actualAstMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    String actualToStringResult = actualAstMethod.toString();
    int actualCardinality = actualAstMethod.getCardinality();
    boolean actualIsLeftValueResult = actualAstMethod.isLeftValue();
    boolean actualIsLiteralTextResult = actualAstMethod.isLiteralText();

    // Assert
    assertEquals("<method>", actualToStringResult);
    assertEquals(2, actualCardinality);
    assertFalse(actualIsLeftValueResult);
    assertFalse(actualIsLiteralTextResult);
    assertTrue(actualAstMethod.isMethodInvocation());
  }

  /**
   * Test {@link AstMethod#getType(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstMethod#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  void testGetType() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astMethod.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstMethod#isReadOnly(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstMethod#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  void testIsReadOnly() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertTrue(astMethod.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstMethod#setValue(Bindings, ELContext, Object)}.
   * <p>
   * Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  void testSetValue() {
    // Arrange
    AstNull base = new AstNull();
    AstDot property = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(ELException.class, () -> astMethod.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstMethod#setValue(Bindings, ELContext, Object)}.
   * <p>
   * Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  void testSetValue2() {
    // Arrange
    AstDot property = new AstDot(new AstFunction("error.value.set.rvalue", 1, new AstParameters(new ArrayList<>())),
        "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(ELException.class, () -> astMethod.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstMethod#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default
   * constructor).</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); given ArrayList() add AstNull (default constructor); then throw ELException")
  void testSetValue_givenArrayListAddAstNull_thenThrowELException() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstParameters params = new AstParameters(nodes);
    AstMethod astMethod = new AstMethod(new AstDot(new AstNull(), "Property", true), params);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(ELException.class, () -> astMethod.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstMethod#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default
   * constructor).</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); given ArrayList() add AstNull (default constructor); then throw ELException")
  void testSetValue_givenArrayListAddAstNull_thenThrowELException2() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    AstParameters params = new AstParameters(nodes);
    AstMethod astMethod = new AstMethod(new AstDot(new AstNull(), "Property", true), params);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(ELException.class, () -> astMethod.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstMethod#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is
   * {@link AstNull} (default constructor) and {@code Property} and lvalue is
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  void testSetValue_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(ELException.class, () -> astMethod.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstMethod#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <p>
   * Method under test:
   * {@link AstMethod#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[])")
  void testGetMethodInfo() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[]{forNameResult};

    // Act and Assert
    assertNull(astMethod.getMethodInfo(bindings, context, returnType, paramTypes));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, paramTypes[0]);
  }

  /**
   * Test {@link AstMethod#getValueReference(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstMethod#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  void testGetValueReference() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astMethod.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstMethod#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull[null].Property()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull[null].Property()'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullProperty() {
    // Arrange
    AstNull base = new AstNull();
    AstDot property = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astMethod.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull[null].Property()", builder.toString());
  }

  /**
   * Test {@link AstMethod#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull.Property()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull.Property()'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullProperty() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astMethod.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull.Property()", builder.toString());
  }

  /**
   * Test {@link AstMethod#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull().Property()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull().Property()'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullProperty2() {
    // Arrange
    AstDot property = new AstDot(new AstFunction("null", 1, new AstParameters(new ArrayList<>())), "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astMethod.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull().Property()", builder.toString());
  }

  /**
   * Test {@link AstMethod#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull.Property(null)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull.Property(null)'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullPropertyNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstParameters params = new AstParameters(nodes);
    AstMethod astMethod = new AstMethod(new AstDot(new AstNull(), "Property", true), params);
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astMethod.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull.Property(null)", builder.toString());
  }

  /**
   * Test {@link AstMethod#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull.Property(null, null)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull.Property(null, null)'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullPropertyNullNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    AstParameters params = new AstParameters(nodes);
    AstMethod astMethod = new AstMethod(new AstDot(new AstNull(), "Property", true), params);
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astMethod.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull.Property(null, null)", builder.toString());
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext)} with {@code bindings},
   * {@code context}.
   * <p>
   * Method under test: {@link AstMethod#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext) with 'bindings', 'context'")
  void testEvalWithBindingsContext() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astMethod.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext, boolean)} with
   * {@code bindings}, {@code context}, {@code answerNullIfBaseIsNull}.
   * <p>
   * Method under test: {@link AstMethod#eval(Bindings, ELContext, boolean)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext, boolean) with 'bindings', 'context', 'answerNullIfBaseIsNull'")
  void testEvalWithBindingsContextAnswerNullIfBaseIsNull() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astMethod.eval(bindings, new SimpleContext(), true));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext, boolean)} with
   * {@code bindings}, {@code context}, {@code answerNullIfBaseIsNull}.
   * <ul>
   *   <li>Then calls
   * {@link Operator#eval(Bindings, ELContext, AstNode, AstNode)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#eval(Bindings, ELContext, boolean)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext, boolean) with 'bindings', 'context', 'answerNullIfBaseIsNull'; then calls eval(Bindings, ELContext, AstNode, AstNode)")
  void testEvalWithBindingsContextAnswerNullIfBaseIsNull_thenCallsEval() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot property = new AstDot(new AstBinary(left, new AstNull(), operator), null, true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astMethod.eval(bindings, new SimpleContext(), true));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext)} with {@code bindings},
   * {@code context}.
   * <ul>
   *   <li>Then throw {@link PropertyNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext) with 'bindings', 'context'; then throw PropertyNotFoundException")
  void testEvalWithBindingsContext_thenThrowPropertyNotFoundException() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot property = new AstDot(new AstBinary(left, new AstNull(), operator), null, true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astMethod.eval(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is
   * {@link AstNull} (default constructor) and {@code Property} and lvalue is
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  void testInvoke_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astMethod.invoke(bindings, context, returnType,
        new Class[]{forNameResult}, new Object[]{"Param Values"}));
  }

  /**
   * Test {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>Then calls
   * {@link Operator#eval(Bindings, ELContext, AstNode, AstNode)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); then calls eval(Bindings, ELContext, AstNode, AstNode)")
  void testInvoke_thenCallsEval() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot property = new AstDot(new AstBinary(left, new AstNull(), operator), null, true);

    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astMethod.invoke(bindings, context, returnType,
        new Class[]{forNameResult}, new Object[]{"Param Values"}));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstMethod#getChild(int)}.
   * <ul>
   *   <li>Given {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left
   * is {@link AstNull} (default constructor) and right is {@link AstNull}
   * (default constructor) and {@link Operator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); given AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  void testGetChild_givenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator() {
    // Arrange
    AstNull left = new AstNull();
    AstDot property = new AstDot(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), "Property", true);

    AstParameters params = new AstParameters(new ArrayList<>());

    // Act and Assert
    assertSame(params, (new AstMethod(property, params)).getChild(1));
  }

  /**
   * Test {@link AstMethod#getChild(int)}.
   * <ul>
   *   <li>Then return {@link AstDot#AstDot(AstNode, String, boolean)} with base is
   * {@link AstNull} (default constructor) and {@code Property} and lvalue is
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); then return AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  void testGetChild_thenReturnAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    // Act and Assert
    assertSame(property, (new AstMethod(property, new AstParameters(new ArrayList<>()))).getChild(0));
  }

  /**
   * Test {@link AstMethod#getChild(int)}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when minus one; then return 'null'")
  void testGetChild_whenMinusOne_thenReturnNull() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    // Act and Assert
    assertNull((new AstMethod(property, new AstParameters(new ArrayList<>()))).getChild(-1));
  }

  /**
   * Test {@link AstMethod#getChild(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@link AstParameters#AstParameters(List)} with nodes is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstMethod#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when one; then return AstParameters(List) with nodes is ArrayList()")
  void testGetChild_whenOne_thenReturnAstParametersWithNodesIsArrayList() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstParameters params = new AstParameters(new ArrayList<>());

    // Act and Assert
    assertSame(params, (new AstMethod(property, params)).getChild(1));
  }
}
