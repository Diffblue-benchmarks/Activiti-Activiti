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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ArrayELResolver;
import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.MethodNotFoundException;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstMethodDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstMethod.<init>(AstProperty, AstParameters)",
    "int AstMethod.getCardinality()",
    "boolean AstMethod.isLeftValue()",
    "boolean AstMethod.isLiteralText()",
    "boolean AstMethod.isMethodInvocation()",
    "String AstMethod.toString()"
  })
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
   *
   * <p>Method under test: {@link AstMethod#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstMethod.getType(Bindings, ELContext)"})
  void testGetType() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astMethod.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstMethod#isReadOnly(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstMethod#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstMethod.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertTrue(astMethod.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstMethod#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.setValue(Bindings, ELContext, Object)"})
  void testSetValue() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot property = new AstDot(base2, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        ELException.class, () -> astMethod.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstMethod#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.setValue(Bindings, ELContext, Object)"})
  void testSetValue2() {
    // Arrange
    AstDot property =
        new AstDot(
            new AstFunction("error.value.set.rvalue", 1, new AstParameters(new ArrayList<>())),
            "Property",
            true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        ELException.class, () -> astMethod.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstMethod#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.setValue(Bindings, ELContext, Object)"})
  void testSetValue3() {
    // Arrange
    AstDot property =
        new AstDot(
            new AstFunction("error.value.set.rvalue", 0, new AstParameters(new ArrayList<>())),
            "Property",
            true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        ELException.class, () -> astMethod.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstMethod#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.setValue(Bindings, ELContext, Object)"})
  void testSetValue4() {
    // Arrange
    AstDot property =
        new AstDot(
            new AstFunction("error.value.set.rvalue", -1, new AstParameters(new ArrayList<>())),
            "Property",
            true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        ELException.class, () -> astMethod.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstMethod#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default constructor).
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); given ArrayList() add AstNull (default constructor); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenArrayListAddAstNull_thenThrowELException() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstParameters params = new AstParameters(nodes);
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstMethod astMethod = new AstMethod(property, params);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        ELException.class, () -> astMethod.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstMethod#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default constructor).
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); given ArrayList() add AstNull (default constructor); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenArrayListAddAstNull_thenThrowELException2() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    AstParameters params = new AstParameters(nodes);
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstMethod astMethod = new AstMethod(property, params);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        ELException.class, () -> astMethod.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstMethod#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull}
   *       (default constructor) and {@code Property} and lvalue is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        ELException.class, () -> astMethod.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstMethod#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   *
   * <p>Method under test: {@link AstMethod#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodInfo AstMethod.getMethodInfo(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodInfo() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(astMethod.getMethodInfo(bindings, context, returnType, new Class[] {forNameResult}));
  }

  /**
   * Test {@link AstMethod#getValueReference(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstMethod#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"jakarta.el.ValueReference AstMethod.getValueReference(Bindings, ELContext)"})
  void testGetValueReference() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astMethod.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstMethod#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure() {
    // Arrange
    AstDot property =
        new AstDot(
            new AstFunction("null", 1, new AstParameters(new ArrayList<>())), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    StringBuilder builder = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astMethod.appendStructure(builder, bindings);

    // Assert
    assertEquals("Strnull().Property()", builder.toString());
  }

  /**
   * Test {@link AstMethod#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure2() {
    // Arrange
    AstDot property =
        new AstDot(
            new AstFunction("null", -1, new AstParameters(new ArrayList<>())), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    StringBuilder builder = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astMethod.appendStructure(builder, bindings);

    // Assert
    assertEquals("Strnull().Property()", builder.toString());
  }

  /**
   * Test {@link AstMethod#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is a string.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is a string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsAString() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    AstParameters params = new AstParameters(nodes);
    AstDot property = new AstDot(new AstNull(), "Property", true);

    AstMethod astMethod = new AstMethod(property, params);
    StringBuilder builder = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astMethod.appendStructure(builder, bindings);

    // Assert
    assertEquals(
        "Strnull.Property(null, null, null, null, null, null, null, null, null, null, null, null, null, null,"
            + " null, null, null, null)",
        builder.toString());
  }

  /**
   * Test {@link AstMethod#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Str<fn>().Property()}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Str<fn>().Property()'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrFnProperty() {
    // Arrange
    AstDot property =
        new AstDot(
            new AstFunction("null", 0, new AstParameters(new ArrayList<>())), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    StringBuilder builder = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astMethod.appendStructure(builder, bindings);

    // Assert
    assertEquals("Str<fn>().Property()", builder.toString());
  }

  /**
   * Test {@link AstMethod#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull[null].Property()}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull[null].Property()'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullProperty() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot property = new AstDot(base2, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    StringBuilder builder = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astMethod.appendStructure(builder, bindings);

    // Assert
    assertEquals("Strnull[null].Property()", builder.toString());
  }

  /**
   * Test {@link AstMethod#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull.Property()}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull.Property()'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstMethod.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullProperty() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    StringBuilder builder = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astMethod.appendStructure(builder, bindings);

    // Assert
    assertEquals("Strnull.Property()", builder.toString());
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext)} with {@code bindings}, {@code context}.
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext) with 'bindings', 'context'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext)"})
  void testEvalWithBindingsContext() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astMethod.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext)} with {@code bindings}, {@code context}.
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext) with 'bindings', 'context'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext)"})
  void testEvalWithBindingsContext2() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot property = new AstDot(base2, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, mock(TypeConverter.class));

    // Act and Assert
    assertNull(astMethod.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext)} with {@code bindings}, {@code context}.
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext) with 'bindings', 'context'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext)"})
  void testEvalWithBindingsContext3() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base = new AstChoice(question, yes, new AstNull());
    AstDot property = new AstDot(base, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Boolean.class))).thenReturn(true);
    when(converter.convert(Mockito.<Object>any(), eq(String.class))).thenReturn("Convert");
    Method[] functions = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter2, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, converter);

    // Act
    Object actualEvalResult = astMethod.eval(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isNull(), isA(Class.class));
    assertNull(actualEvalResult);
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext, boolean)} with {@code bindings}, {@code
   * context}, {@code answerNullIfBaseIsNull}.
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext, boolean)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext, boolean) with 'bindings', 'context', 'answerNullIfBaseIsNull'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext, boolean)"})
  void testEvalWithBindingsContextAnswerNullIfBaseIsNull() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astMethod.eval(bindings, new SimpleContext(), true));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext, boolean)} with {@code bindings}, {@code
   * context}, {@code answerNullIfBaseIsNull}.
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext, boolean)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext, boolean) with 'bindings', 'context', 'answerNullIfBaseIsNull'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext, boolean)"})
  void testEvalWithBindingsContextAnswerNullIfBaseIsNull2() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot property = new AstDot(base, null, true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astMethod.eval(bindings, new SimpleContext(), true));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext, boolean)} with {@code bindings}, {@code
   * context}, {@code answerNullIfBaseIsNull}.
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext, boolean)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext, boolean) with 'bindings', 'context', 'answerNullIfBaseIsNull'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext, boolean)"})
  void testEvalWithBindingsContextAnswerNullIfBaseIsNull3() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot property = new AstDot(base2, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, mock(TypeConverter.class));

    // Act and Assert
    assertNull(astMethod.eval(bindings, new SimpleContext(), true));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext, boolean)} with {@code bindings}, {@code
   * context}, {@code answerNullIfBaseIsNull}.
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext, boolean)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext, boolean) with 'bindings', 'context', 'answerNullIfBaseIsNull'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext, boolean)"})
  void testEvalWithBindingsContextAnswerNullIfBaseIsNull4() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base = new AstChoice(question, yes, new AstNull());
    AstDot property = new AstDot(base, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Boolean.class))).thenReturn(true);
    when(converter.convert(Mockito.<Object>any(), eq(String.class))).thenReturn("Convert");
    Method[] functions = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter2, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, converter);

    // Act
    Object actualEvalResult = astMethod.eval(bindings, new SimpleContext(), true);

    // Assert
    verify(converter).convert(isNull(), isA(Class.class));
    assertNull(actualEvalResult);
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext, boolean)} with {@code bindings}, {@code
   * context}, {@code answerNullIfBaseIsNull}.
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext, boolean)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext, boolean) with 'bindings', 'context', 'answerNullIfBaseIsNull'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext, boolean)"})
  void testEvalWithBindingsContextAnswerNullIfBaseIsNull5() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot property = new AstDot(base, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Boolean.class))).thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(String.class))).thenReturn("Convert");
    Method[] functions = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter2, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, converter);

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () -> astMethod.eval(bindings, new SimpleContext(new CompositeELResolver()), true));
    verify(converter).convert(isA(Object.class), isA(Class.class));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext, boolean)} with {@code bindings}, {@code
   * context}, {@code answerNullIfBaseIsNull}.
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext, boolean)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext, boolean) with 'bindings', 'context', 'answerNullIfBaseIsNull'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext, boolean)"})
  void testEvalWithBindingsContextAnswerNullIfBaseIsNull6() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot property = new AstDot(base, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Boolean.class))).thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(String.class))).thenReturn("Convert");
    Method[] functions = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter2, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, converter);

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () -> astMethod.eval(bindings, new SimpleContext(new ArrayELResolver()), true));
    verify(converter).convert(isA(Object.class), isA(Class.class));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext, boolean)} with {@code bindings}, {@code
   * context}, {@code answerNullIfBaseIsNull}.
   *
   * <ul>
   *   <li>Given {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext, boolean)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext, boolean) with 'bindings', 'context', 'answerNullIfBaseIsNull'; given 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext, boolean)"})
  void testEvalWithBindingsContextAnswerNullIfBaseIsNull_givenFalse() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    SimpleContext context = new SimpleContext(new CompositeELResolver());
    context.setPropertyResolved(false);

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astMethod.eval(bindings, context, false));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext)} with {@code bindings}, {@code context}.
   *
   * <ul>
   *   <li>Then throw {@link PropertyNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext) with 'bindings', 'context'; then throw PropertyNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext)"})
  void testEvalWithBindingsContext_thenThrowPropertyNotFoundException() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot property = new AstDot(base, null, true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astMethod.eval(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext)} with {@code bindings}, {@code context}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       ArrayELResolver#ArrayELResolver()}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext) with 'bindings', 'context'; when SimpleContext(ELResolver) with resolver is ArrayELResolver()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext)"})
  void testEvalWithBindingsContext_whenSimpleContextWithResolverIsArrayELResolver()
      throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot property = new AstDot(base, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Boolean.class))).thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(String.class))).thenReturn("Convert");
    Method[] functions = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter2, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, converter);

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () -> astMethod.eval(bindings, new SimpleContext(new ArrayELResolver())));
    verify(converter).convert(isA(Object.class), isA(Class.class));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstMethod#eval(Bindings, ELContext)} with {@code bindings}, {@code context}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       CompositeELResolver} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext) with 'bindings', 'context'; when SimpleContext(ELResolver) with resolver is CompositeELResolver (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.eval(Bindings, ELContext)"})
  void testEvalWithBindingsContext_whenSimpleContextWithResolverIsCompositeELResolver()
      throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot property = new AstDot(base, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Boolean.class))).thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(String.class))).thenReturn("Convert");
    Method[] functions = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter2, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, converter);

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () -> astMethod.eval(bindings, new SimpleContext(new CompositeELResolver())));
    verify(converter).convert(isA(Object.class), isA(Class.class));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with question is {@link
   *       AstNull} (default constructor) and yes is {@link AstNull} (default constructor) and no is
   *       {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); given AstChoice(AstNode, AstNode, AstNode) with question is AstNull (default constructor) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenAstChoiceWithQuestionIsAstNullAndYesIsAstNullAndNoIsAstNull()
      throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base = new AstChoice(question, yes, new AstNull());
    AstDot property = new AstDot(base, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Boolean.class))).thenReturn(true);
    when(converter.convert(Mockito.<Object>any(), eq(String.class))).thenReturn("Convert");
    Method[] functions = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter2, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, converter);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () ->
            astMethod.invoke(
                bindings,
                context,
                returnType,
                new Class[] {forNameResult},
                new Object[] {"Param Values"}));
    verify(converter).convert(isNull(), isA(Class.class));
  }

  /**
   * Test {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue
   *       is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot property = new AstDot(base, null, true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () ->
            astMethod.invoke(
                bindings,
                context,
                returnType,
                new Class[] {forNameResult},
                new Object[] {"Param Values"}));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstBracket#AstBracket(AstNode, AstNode, boolean, boolean)} and {@code Property} and
   *       lvalue is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); given AstDot(AstNode, String, boolean) with base is AstBracket(AstNode, AstNode, boolean, boolean) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenAstDotWithBaseIsAstBracketAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot property = new AstDot(base2, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, mock(TypeConverter.class));
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () ->
            astMethod.invoke(
                bindings,
                context,
                returnType,
                new Class[] {forNameResult},
                new Object[] {"Param Values"}));
  }

  /**
   * Test {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull}
   *       (default constructor) and {@code Property} and lvalue is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () ->
            astMethod.invoke(
                bindings,
                context,
                returnType,
                new Class[] {forNameResult},
                new Object[] {"Param Values"}));
  }

  /**
   * Test {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Then throw {@link MethodNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); then throw MethodNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstMethod.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_thenThrowMethodNotFoundException() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot property = new AstDot(base, "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Boolean.class))).thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(String.class))).thenReturn("Convert");
    Method[] functions = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter2, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, converter);
    SimpleContext context = new SimpleContext(new CompositeELResolver());
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () ->
            astMethod.invoke(
                bindings,
                context,
                returnType,
                new Class[] {forNameResult},
                new Object[] {"Param Values"}));
    verify(converter).convert(isA(Object.class), isA(Class.class));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstMethod#getChild(int)}.
   *
   * <ul>
   *   <li>Then return {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull}
   *       (default constructor) and {@code Property} and lvalue is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#getChild(int)}
   */
  @Test
  @DisplayName(
      "Test getChild(int); then return AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.core.el.juel.tree.Node AstMethod.getChild(int)"})
  void testGetChild_thenReturnAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));

    // Act and Assert
    assertSame(property, astMethod.getChild(0));
  }

  /**
   * Test {@link AstMethod#getChild(int)}.
   *
   * <ul>
   *   <li>When minus one.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when minus one; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.core.el.juel.tree.Node AstMethod.getChild(int)"})
  void testGetChild_whenMinusOne_thenReturnNull() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstMethod astMethod = new AstMethod(property, new AstParameters(new ArrayList<>()));

    // Act and Assert
    assertNull(astMethod.getChild(-1));
  }

  /**
   * Test {@link AstMethod#getChild(int)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return {@link AstParameters#AstParameters(List)} with nodes is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link AstMethod#getChild(int)}
   */
  @Test
  @DisplayName(
      "Test getChild(int); when one; then return AstParameters(List) with nodes is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.core.el.juel.tree.Node AstMethod.getChild(int)"})
  void testGetChild_whenOne_thenReturnAstParametersWithNodesIsArrayList() {
    // Arrange
    AstDot property = new AstDot(new AstNull(), "Property", true);
    AstParameters params = new AstParameters(new ArrayList<>());

    AstMethod astMethod = new AstMethod(property, params);

    // Act and Assert
    assertSame(params, astMethod.getChild(1));
  }
}
