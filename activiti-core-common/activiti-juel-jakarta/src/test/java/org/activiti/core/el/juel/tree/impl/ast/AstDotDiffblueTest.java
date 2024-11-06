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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AstDotDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AstDot#AstDot(AstNode, String, boolean)}
   *   <li>{@link AstDot#toString()}
   *   <li>{@link AstDot#getCardinality()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    AstNull base = new AstNull();

    // Act
    AstDot actualAstDot = new AstDot(base, "Property", true);
    String actualToStringResult = actualAstDot.toString();

    // Assert
    assertEquals(". Property", actualToStringResult);
    assertEquals(1, actualAstDot.getCardinality());
    assertTrue(actualAstDot.isLeftValue());
    assertSame(base, actualAstDot.getPrefix());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AstDot#AstDot(AstNode, String, boolean, boolean)}
   *   <li>{@link AstDot#toString()}
   *   <li>{@link AstDot#getCardinality()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters2() {
    // Arrange
    AstNull base = new AstNull();

    // Act
    AstDot actualAstDot = new AstDot(base, "Property", true, true);
    String actualToStringResult = actualAstDot.toString();

    // Assert
    assertEquals(". Property", actualToStringResult);
    assertEquals(1, actualAstDot.getCardinality());
    assertTrue(actualAstDot.isLeftValue());
    assertSame(base, actualAstDot.getPrefix());
  }

  /**
   * Test {@link AstDot#getProperty(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstDot#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  void testGetProperty() throws ELException {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertEquals("Property", astDot.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstDot#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull.null.Property}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstDot#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull.null.Property'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullProperty() {
    // Arrange
    AstDot astDot = new AstDot(new AstDot(new AstNull(), "null", true), "Property", true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astDot.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull.null.Property", b.toString());
  }

  /**
   * Test {@link AstDot#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull.Property}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstDot#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull.Property'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullProperty() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astDot.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull.Property", b.toString());
  }

  /**
   * Test {@link AstDot#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull().Property}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstDot#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull().Property'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullProperty2() {
    // Arrange
    AstDot astDot = new AstDot(new AstFunction("null", 1, new AstParameters(new ArrayList<>())), "Property", true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astDot.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull().Property", b.toString());
  }
}
