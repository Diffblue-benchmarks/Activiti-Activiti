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
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstMethodDiffblueTest {
  /**
   * Method under test: {@link AstMethod#getType(Bindings, ELContext)}
   */
  @Test
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
   * Method under test: {@link AstMethod#isReadOnly(Bindings, ELContext)}
   */
  @Test
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
   * Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue() {
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
   * Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue2() {
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
   * Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue3() {
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
   * Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue4() {
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
   * Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue5() {
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
   * Method under test:
   * {@link AstMethod#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
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
    Class<?> resultClass = paramTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test: {@link AstMethod#getValueReference(Bindings, ELContext)}
   */
  @Test
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
   * Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure() {
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
   * Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure2() {
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
   * Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure3() {
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
   * Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure4() {
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
   * Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure5() {
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
   * Method under test: {@link AstMethod#eval(Bindings, ELContext)}
   */
  @Test
  void testEval() {
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
   * Method under test: {@link AstMethod#eval(Bindings, ELContext)}
   */
  @Test
  void testEval2() {
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
   * Method under test: {@link AstMethod#eval(Bindings, ELContext, boolean)}
   */
  @Test
  void testEval3() {
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
   * Method under test: {@link AstMethod#eval(Bindings, ELContext, boolean)}
   */
  @Test
  void testEval4() {
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
   * Method under test:
   * {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke() {
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
   * Method under test:
   * {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke2() {
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
   * Method under test: {@link AstMethod#getChild(int)}
   */
  @Test
  void testGetChild() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstParameters params = new AstParameters(new ArrayList<>());

    // Act and Assert
    assertSame(params, (new AstMethod(property, params)).getChild(1));
  }

  /**
   * Method under test: {@link AstMethod#getChild(int)}
   */
  @Test
  void testGetChild2() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    // Act and Assert
    assertSame(property, (new AstMethod(property, new AstParameters(new ArrayList<>()))).getChild(0));
  }

  /**
   * Method under test: {@link AstMethod#getChild(int)}
   */
  @Test
  void testGetChild3() {
    // Arrange
    AstNull left = new AstNull();
    AstDot property = new AstDot(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), "Property", true);

    AstParameters params = new AstParameters(new ArrayList<>());

    // Act and Assert
    assertSame(params, (new AstMethod(property, params)).getChild(1));
  }

  /**
   * Method under test: {@link AstMethod#getChild(int)}
   */
  @Test
  void testGetChild4() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);

    // Act and Assert
    assertNull((new AstMethod(property, new AstParameters(new ArrayList<>()))).getChild(-1));
  }

  /**
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
}
