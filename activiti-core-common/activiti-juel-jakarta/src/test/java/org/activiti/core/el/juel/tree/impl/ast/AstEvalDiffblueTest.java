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
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstEvalDiffblueTest {
  /**
   * Method under test: {@link AstEval#isLeftValue()}
   */
  @Test
  void testIsLeftValue() {
    // Arrange, Act and Assert
    assertFalse((new AstEval(new AstNull(), true)).isLeftValue());
    assertFalse((new AstEval(new AstEval(new AstNull(), true), true)).isLeftValue());
  }

  /**
   * Method under test: {@link AstEval#isLeftValue()}
   */
  @Test
  void testIsLeftValue2() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertFalse((new AstEval(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), true)).isLeftValue());
  }

  /**
   * Method under test: {@link AstEval#isLeftValue()}
   */
  @Test
  void testIsLeftValue3() {
    // Arrange
    AstNull base = new AstNull();

    // Act and Assert
    assertTrue((new AstEval(new AstBracket(base, new AstNull(), true, true), true)).isLeftValue());
  }

  /**
   * Method under test: {@link AstEval#isMethodInvocation()}
   */
  @Test
  void testIsMethodInvocation() {
    // Arrange, Act and Assert
    assertFalse((new AstEval(new AstNull(), true)).isMethodInvocation());
    assertFalse((new AstEval(new AstEval(new AstNull(), true), true)).isMethodInvocation());
  }

  /**
   * Method under test: {@link AstEval#isMethodInvocation()}
   */
  @Test
  void testIsMethodInvocation2() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertFalse(
        (new AstEval(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), true)).isMethodInvocation());
  }

  /**
   * Method under test: {@link AstEval#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference() {
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
   * Method under test: {@link AstEval#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference2() {
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
   * Method under test: {@link AstEval#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference3() {
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
   * Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  void testEval() {
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
   * Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  void testEval2() {
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
   * Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  void testEval3() {
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
   * Method under test: {@link AstEval#toString()}
   */
  @Test
  void testToString() {
    // Arrange, Act and Assert
    assertEquals("#{...}", (new AstEval(new AstNull(), true)).toString());
    assertEquals("${...}", (new AstEval(new AstNull(), false)).toString());
  }

  /**
   * Method under test: {@link AstEval#toString()}
   */
  @Test
  void testToString2() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertEquals("#{...}",
        (new AstEval(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), true)).toString());
  }

  /**
   * Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure() {
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
   * Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure2() {
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
   * Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure3() {
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
   * Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure4() {
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
   * Method under test:
   * {@link AstEval#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo() {
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
    Class<?> resultClass = paramTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test:
   * {@link AstEval#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo2() {
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
    Class<?> resultClass = paramTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test:
   * {@link AstEval#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke() {
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
    Class<?> resultClass = paramTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test: {@link AstEval#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType() {
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
   * Method under test: {@link AstEval#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType2() {
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
   * Method under test: {@link AstEval#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType3() {
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
   * Method under test: {@link AstEval#isLiteralText()}
   */
  @Test
  void testIsLiteralText() {
    // Arrange, Act and Assert
    assertFalse((new AstEval(new AstNull(), true)).isLiteralText());
    assertFalse((new AstEval(new AstEval(new AstNull(), true), true)).isLiteralText());
  }

  /**
   * Method under test: {@link AstEval#isLiteralText()}
   */
  @Test
  void testIsLiteralText2() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertFalse(
        (new AstEval(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), true)).isLiteralText());
  }

  /**
   * Method under test: {@link AstEval#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly() {
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
   * Method under test: {@link AstEval#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly2() {
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
   * Method under test: {@link AstEval#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly3() {
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
   * Method under test: {@link AstEval#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly4() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext();

    // Act and Assert
    assertFalse(astEval.isReadOnly(bindings, context));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Method under test: {@link AstEval#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue() {
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
   * Method under test: {@link AstEval#getChild(int)}
   */
  @Test
  void testGetChild() {
    // Arrange, Act and Assert
    assertNull((new AstEval(new AstNull(), true)).getChild(1));
  }

  /**
   * Method under test: {@link AstEval#getChild(int)}
   */
  @Test
  void testGetChild2() {
    // Arrange
    AstNull child = new AstNull();

    // Act and Assert
    assertSame(child, (new AstEval(child, true)).getChild(0));
  }

  /**
   * Method under test: {@link AstEval#getChild(int)}
   */
  @Test
  void testGetChild3() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertNull((new AstEval(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), true)).getChild(1));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AstEval#AstEval(AstNode, boolean)}
   *   <li>{@link AstEval#getCardinality()}
   *   <li>{@link AstEval#isDeferred()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    AstEval actualAstEval = new AstEval(new AstNull(), true);
    int actualCardinality = actualAstEval.getCardinality();

    // Assert
    assertEquals(1, actualCardinality);
    assertTrue(actualAstEval.isDeferred());
  }
}
