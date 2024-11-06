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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AstRightValueDiffblueTest {
  /**
   * Test {@link AstRightValue#isLiteralText()}.
   * <ul>
   *   <li>Given {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstRightValue#isLiteralText()}
   */
  @Test
  @DisplayName("Test isLiteralText(); given AstNull (default constructor)")
  void testIsLiteralText_givenAstNull() {
    // Arrange, Act and Assert
    assertFalse((new AstNull()).isLiteralText());
  }

  /**
   * Test {@link AstRightValue#isLiteralText()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstRightValue#isLiteralText()}
   */
  @Test
  @DisplayName("Test isLiteralText(); given 'java.lang.Object'")
  void testIsLiteralText_givenJavaLangObject() {
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
   * Test {@link AstRightValue#getType(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstRightValue#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
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
   * Test {@link AstRightValue#isReadOnly(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstRightValue#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
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
   * Test {@link AstRightValue#setValue(Bindings, ELContext, Object)}.
   * <p>
   * Method under test:
   * {@link AstRightValue#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
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
   * Test
   * {@link AstRightValue#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <p>
   * Method under test:
   * {@link AstRightValue#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[])")
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
    assertEquals(expectedResultClass, paramTypes[0]);
  }

  /**
   * Test
   * {@link AstRightValue#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <p>
   * Method under test:
   * {@link AstRightValue#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[])")
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
   * Test {@link AstRightValue#isLeftValue()}.
   * <ul>
   *   <li>Given {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstRightValue#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); given AstNull (default constructor)")
  void testIsLeftValue_givenAstNull() {
    // Arrange, Act and Assert
    assertFalse((new AstNull()).isLeftValue());
  }

  /**
   * Test {@link AstRightValue#isLeftValue()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstRightValue#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); given 'java.lang.Object'")
  void testIsLeftValue_givenJavaLangObject() {
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
   * Test {@link AstRightValue#isMethodInvocation()}.
   * <ul>
   *   <li>Given {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstRightValue#isMethodInvocation()}
   */
  @Test
  @DisplayName("Test isMethodInvocation(); given AstNull (default constructor)")
  void testIsMethodInvocation_givenAstNull() {
    // Arrange, Act and Assert
    assertFalse((new AstNull()).isMethodInvocation());
  }

  /**
   * Test {@link AstRightValue#isMethodInvocation()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstRightValue#isMethodInvocation()}
   */
  @Test
  @DisplayName("Test isMethodInvocation(); given 'java.lang.Object'")
  void testIsMethodInvocation_givenJavaLangObject() {
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
   * Test {@link AstRightValue#getValueReference(Bindings, ELContext)}.
   * <p>
   * Method under test:
   * {@link AstRightValue#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
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
