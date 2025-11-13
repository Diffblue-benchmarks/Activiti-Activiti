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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AstDotDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstDot#AstDot(AstNode, String, boolean)}
   *   <li>{@link AstDot#toString()}
   *   <li>{@link AstDot#getCardinality()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstDot.<init>(AstNode, String, boolean)",
    "void AstDot.<init>(AstNode, String, boolean, boolean)",
    "int AstDot.getCardinality()",
    "String AstDot.toString()"
  })
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
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstDot#AstDot(AstNode, String, boolean, boolean)}
   *   <li>{@link AstDot#toString()}
   *   <li>{@link AstDot#getCardinality()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstDot.<init>(AstNode, String, boolean)",
    "void AstDot.<init>(AstNode, String, boolean, boolean)",
    "int AstDot.getCardinality()",
    "String AstDot.toString()"
  })
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
   *
   * <p>Method under test: {@link AstDot#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String AstDot.getProperty(Bindings, ELContext)"})
  void testGetProperty() throws ELException {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertEquals("Property", astDot.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstDot#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstDot#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstDot.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure() {
    // Arrange
    AstDot astDot =
        new AstDot(
            new AstFunction("null", 1, new AstParameters(new ArrayList<>())), "Property", true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astDot.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull().Property", b.toString());
  }

  /**
   * Test {@link AstDot#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstDot#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstDot.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure2() {
    // Arrange
    AstDot astDot =
        new AstDot(
            new AstFunction("null", -1, new AstParameters(new ArrayList<>())), "Property", true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astDot.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull().Property", b.toString());
  }

  /**
   * Test {@link AstDot#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Str<fn>().Property}.
   * </ul>
   *
   * <p>Method under test: {@link AstDot#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Str<fn>().Property'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstDot.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrFnProperty() {
    // Arrange
    AstDot astDot =
        new AstDot(
            new AstFunction("null", 0, new AstParameters(new ArrayList<>())), "Property", true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astDot.appendStructure(b, bindings);

    // Assert
    assertEquals("Str<fn>().Property", b.toString());
  }

  /**
   * Test {@link AstDot#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull.null.Property}.
   * </ul>
   *
   * <p>Method under test: {@link AstDot#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull.null.Property'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstDot.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullProperty() {
    // Arrange
    AstDot base = new AstDot(new AstNull(), "null", true);
    AstDot astDot = new AstDot(base, "Property", true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astDot.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull.null.Property", b.toString());
  }

  /**
   * Test {@link AstDot#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull.Property}.
   * </ul>
   *
   * <p>Method under test: {@link AstDot#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull.Property'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstDot.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullProperty() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astDot.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull.Property", b.toString());
  }
}
