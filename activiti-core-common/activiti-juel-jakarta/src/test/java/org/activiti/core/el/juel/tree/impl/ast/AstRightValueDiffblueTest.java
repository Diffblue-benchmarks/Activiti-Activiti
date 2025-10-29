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

class AstRightValueDiffblueTest {
  /**
   * Method under test: {@link AstRightValue#isLiteralText()}
   */
  @Test
  void testIsLiteralText() {
    // Arrange, Act and Assert
    assertFalse((new AstNull()).isLiteralText());
  }

  /**
   * Method under test: {@link AstRightValue#isLiteralText()}
   */
  @Test
  void testIsLiteralText2() {
    // Arrange
    AstNull astNull = new AstNull();
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    astNull.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Act and Assert
    assertFalse(astNull.isLiteralText());
  }

  /**
   * Method under test: {@link AstRightValue#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType() {
    // Arrange
    AstNull astNull = new AstNull();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astNull.getType(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstRightValue#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly() {
    // Arrange
    AstNull astNull = new AstNull();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertTrue(astNull.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Method under test:
   * {@link AstRightValue#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue() {
    // Arrange
    AstNull astNull = new AstNull();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(ELException.class, () -> astNull.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Method under test:
   * {@link AstRightValue#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo() {
    // Arrange
    AstNull astNull = new AstNull();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[]{forNameResult};

    // Act and Assert
    assertNull(astNull.getMethodInfo(bindings, context, returnType, paramTypes));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    Class<?> resultClass = paramTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test:
   * {@link AstRightValue#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke() {
    // Arrange
    AstNull astNull = new AstNull();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class,
        () -> astNull.invoke(bindings, context, returnType, new Class[]{forNameResult}, new Object[]{"Param Values"}));
  }

  /**
   * Method under test: {@link AstRightValue#isLeftValue()}
   */
  @Test
  void testIsLeftValue() {
    // Arrange, Act and Assert
    assertFalse((new AstNull()).isLeftValue());
  }

  /**
   * Method under test: {@link AstRightValue#isLeftValue()}
   */
  @Test
  void testIsLeftValue2() {
    // Arrange
    AstNull astNull = new AstNull();
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    astNull.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Act and Assert
    assertFalse(astNull.isLeftValue());
  }

  /**
   * Method under test: {@link AstRightValue#isMethodInvocation()}
   */
  @Test
  void testIsMethodInvocation() {
    // Arrange, Act and Assert
    assertFalse((new AstNull()).isMethodInvocation());
  }

  /**
   * Method under test: {@link AstRightValue#isMethodInvocation()}
   */
  @Test
  void testIsMethodInvocation2() {
    // Arrange
    AstNull astNull = new AstNull();
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    astNull.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Act and Assert
    assertFalse(astNull.isMethodInvocation());
  }

  /**
   * Method under test:
   * {@link AstRightValue#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference() {
    // Arrange
    AstNull astNull = new AstNull();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astNull.getValueReference(bindings, new SimpleContext()));
  }
}
