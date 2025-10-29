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
import static org.mockito.Mockito.mock;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.Test;

class AstTextDiffblueTest {
  /**
   * Method under test: {@link AstText#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType() {
    // Arrange
    AstText astText = new AstText("42");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astText.getType(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstText#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly() {
    // Arrange
    AstText astText = new AstText("42");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertTrue(astText.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstText#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue() {
    // Arrange
    AstText astText = new AstText("42");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(ELException.class, () -> astText.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Method under test: {@link AstText#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue2() {
    // Arrange
    AstText astText = new AstText("");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(ELException.class, () -> astText.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Method under test: {@link AstText#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference() {
    // Arrange
    AstText astText = new AstText("42");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astText.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstText#eval(Bindings, ELContext)}
   */
  @Test
  void testEval() {
    // Arrange
    AstText astText = new AstText("42");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertEquals("42", astText.eval(bindings, new SimpleContext()));
  }

  /**
   * Method under test:
   * {@link AstText#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo() {
    // Arrange
    AstText astText = new AstText("42");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[]{forNameResult};

    // Act and Assert
    assertNull(astText.getMethodInfo(bindings, context, returnType, paramTypes));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    Class<?> resultClass = paramTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test:
   * {@link AstText#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke() {
    // Arrange
    AstText astText = new AstText("42");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[]{forNameResult};

    // Act and Assert
    assertEquals("42", astText.invoke(bindings, context, returnType, paramTypes, new Object[]{"Param Values"}));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    Class<?> resultClass = paramTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test:
   * {@link AstText#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke2() {
    // Arrange
    AstText astText = new AstText("42");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[]{forNameResult};

    // Act and Assert
    assertEquals("42", astText.invoke(bindings, context, null, paramTypes, new Object[]{"Param Values"}));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    Class<?> resultClass = paramTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test: {@link AstText#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure() {
    // Arrange
    AstText astText = new AstText("42");
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astText.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo42", b.toString());
  }

  /**
   * Method under test: {@link AstText#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure2() {
    // Arrange
    AstText astText = new AstText("");
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astText.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert that nothing has changed
    assertEquals("foo", b.toString());
  }

  /**
   * Method under test: {@link AstText#getChild(int)}
   */
  @Test
  void testGetChild() {
    // Arrange, Act and Assert
    assertNull((new AstText("42")).getChild(1));
  }

  /**
   * Method under test: {@link AstText#getChild(int)}
   */
  @Test
  void testGetChild2() {
    // Arrange
    AstText astText = new AstText("42");
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    astText.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Act and Assert
    assertNull(astText.getChild(1));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AstText#AstText(String)}
   *   <li>{@link AstText#toString()}
   *   <li>{@link AstText#getCardinality()}
   *   <li>{@link AstText#isLeftValue()}
   *   <li>{@link AstText#isLiteralText()}
   *   <li>{@link AstText#isMethodInvocation()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    AstText actualAstText = new AstText("42");
    String actualToStringResult = actualAstText.toString();
    int actualCardinality = actualAstText.getCardinality();
    boolean actualIsLeftValueResult = actualAstText.isLeftValue();
    boolean actualIsLiteralTextResult = actualAstText.isLiteralText();

    // Assert
    assertEquals("\"42\"", actualToStringResult);
    assertEquals(0, actualCardinality);
    assertFalse(actualIsLeftValueResult);
    assertFalse(actualAstText.isMethodInvocation());
    assertTrue(actualIsLiteralTextResult);
  }
}
