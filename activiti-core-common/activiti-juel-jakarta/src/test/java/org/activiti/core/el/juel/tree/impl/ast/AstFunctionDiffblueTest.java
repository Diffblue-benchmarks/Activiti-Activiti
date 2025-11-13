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
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AstFunctionDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@link AstParameters#AstParameters(List)} with nodes is {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then return not VarArgs.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstFunction#AstFunction(String, int, AstParameters)}
   *   <li>{@link AstFunction#getCardinality()}
   *   <li>{@link AstFunction#getIndex()}
   *   <li>{@link AstFunction#getName()}
   *   <li>{@link AstFunction#isVarArgs()}
   *   <li>{@link AstFunction#toString()}
   * </ul>
   */
  @Test
  @DisplayName(
      "Test getters and setters; when AstParameters(List) with nodes is ArrayList(); then return not VarArgs")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstFunction.<init>(String, int, AstParameters)",
    "void AstFunction.<init>(String, int, AstParameters, boolean)",
    "int AstFunction.getCardinality()",
    "int AstFunction.getIndex()",
    "String AstFunction.getName()",
    "boolean AstFunction.isVarArgs()",
    "String AstFunction.toString()"
  })
  void testGettersAndSetters_whenAstParametersWithNodesIsArrayList_thenReturnNotVarArgs() {
    // Arrange and Act
    AstFunction actualAstFunction =
        new AstFunction("Name", 1, new AstParameters(new ArrayList<>()));
    int actualCardinality = actualAstFunction.getCardinality();
    int actualIndex = actualAstFunction.getIndex();
    String actualName = actualAstFunction.getName();
    boolean actualIsVarArgsResult = actualAstFunction.isVarArgs();

    // Assert
    assertEquals("Name", actualName);
    assertEquals("Name", actualAstFunction.toString());
    assertEquals(1, actualCardinality);
    assertEquals(1, actualIndex);
    assertFalse(actualIsVarArgsResult);
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return VarArgs.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstFunction#AstFunction(String, int, AstParameters, boolean)}
   *   <li>{@link AstFunction#getCardinality()}
   *   <li>{@link AstFunction#getIndex()}
   *   <li>{@link AstFunction#getName()}
   *   <li>{@link AstFunction#isVarArgs()}
   *   <li>{@link AstFunction#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'true'; then return VarArgs")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstFunction.<init>(String, int, AstParameters)",
    "void AstFunction.<init>(String, int, AstParameters, boolean)",
    "int AstFunction.getCardinality()",
    "int AstFunction.getIndex()",
    "String AstFunction.getName()",
    "boolean AstFunction.isVarArgs()",
    "String AstFunction.toString()"
  })
  void testGettersAndSetters_whenTrue_thenReturnVarArgs() {
    // Arrange and Act
    AstFunction actualAstFunction =
        new AstFunction("Name", 1, new AstParameters(new ArrayList<>()), true);
    int actualCardinality = actualAstFunction.getCardinality();
    int actualIndex = actualAstFunction.getIndex();
    String actualName = actualAstFunction.getName();
    boolean actualIsVarArgsResult = actualAstFunction.isVarArgs();

    // Assert
    assertEquals("Name", actualName);
    assertEquals("Name", actualAstFunction.toString());
    assertEquals(1, actualCardinality);
    assertEquals(1, actualIndex);
    assertTrue(actualIsVarArgsResult);
  }

  /**
   * Test {@link AstFunction#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstFunction.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure() {
    // Arrange
    AstFunction astFunction = new AstFunction("Name", 0, new AstParameters(new ArrayList<>()));
    StringBuilder b = new StringBuilder("Str");

    // Act
    astFunction.appendStructure(b, null);

    // Assert
    assertEquals("StrName()", b.toString());
  }

  /**
   * Test {@link AstFunction#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstFunction.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure2() {
    // Arrange
    AstFunction astFunction = new AstFunction("Name", -1, new AstParameters(new ArrayList<>()));
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astFunction.appendStructure(b, bindings);

    // Assert
    assertEquals("StrName()", b.toString());
  }

  /**
   * Test {@link AstFunction#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Str<fn>()}.
   * </ul>
   *
   * <p>Method under test: {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Str<fn>()'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstFunction.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrFn() {
    // Arrange
    AstFunction astFunction = new AstFunction("Name", 0, new AstParameters(new ArrayList<>()));
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astFunction.appendStructure(b, bindings);

    // Assert
    assertEquals("Str<fn>()", b.toString());
  }

  /**
   * Test {@link AstFunction#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       StrName()}.
   * </ul>
   *
   * <p>Method under test: {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'StrName()'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstFunction.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrName() {
    // Arrange
    AstFunction astFunction = new AstFunction("Name", 1, new AstParameters(new ArrayList<>()));
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astFunction.appendStructure(b, bindings);

    // Assert
    assertEquals("StrName()", b.toString());
  }

  /**
   * Test {@link AstFunction#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       StrName(null)}.
   * </ul>
   *
   * <p>Method under test: {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'StrName(null)'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstFunction.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrNameNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstFunction astFunction = new AstFunction("Name", 1, new AstParameters(nodes));
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astFunction.appendStructure(b, bindings);

    // Assert
    assertEquals("StrName(null)", b.toString());
  }

  /**
   * Test {@link AstFunction#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       StrName(null, null)}.
   * </ul>
   *
   * <p>Method under test: {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'StrName(null, null)'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstFunction.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrNameNullNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    AstFunction astFunction = new AstFunction("Name", 1, new AstParameters(nodes));
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astFunction.appendStructure(b, bindings);

    // Assert
    assertEquals("StrName(null, null)", b.toString());
  }

  /**
   * Test {@link AstFunction#getParamCount()}.
   *
   * <ul>
   *   <li>Given {@link AstParameters#AstParameters(List)} with nodes is {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link AstFunction#getParamCount()}
   */
  @Test
  @DisplayName(
      "Test getParamCount(); given AstParameters(List) with nodes is ArrayList(); then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int AstFunction.getParamCount()"})
  void testGetParamCount_givenAstParametersWithNodesIsArrayList_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(
        0, new AstFunction("Name", 1, new AstParameters(new ArrayList<>())).getParamCount());
  }

  /**
   * Test {@link AstFunction#getParam(int)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default constructor).
   *   <li>Then return {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstFunction#getParam(int)}
   */
  @Test
  @DisplayName(
      "Test getParam(int); given ArrayList() add AstNull (default constructor); then return AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstFunction.getParam(int)"})
  void testGetParam_givenArrayListAddAstNull_thenReturnAstNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstNull astNull = new AstNull();
    nodes.add(astNull);

    // Act and Assert
    assertSame(astNull, new AstFunction("Name", 1, new AstParameters(nodes)).getParam(1));
  }

  /**
   * Test {@link AstFunction#getChild(int)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstFunction#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when one; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstFunction.getChild(int)"})
  void testGetChild_whenOne_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new AstFunction("Name", 1, new AstParameters(new ArrayList<>())).getChild(1));
  }

  /**
   * Test {@link AstFunction#getChild(int)}.
   *
   * <ul>
   *   <li>When zero.
   *   <li>Then return {@link AstParameters#AstParameters(List)} with nodes is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link AstFunction#getChild(int)}
   */
  @Test
  @DisplayName(
      "Test getChild(int); when zero; then return AstParameters(List) with nodes is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstFunction.getChild(int)"})
  void testGetChild_whenZero_thenReturnAstParametersWithNodesIsArrayList() {
    // Arrange
    AstParameters params = new AstParameters(new ArrayList<>());

    // Act and Assert
    assertSame(params, new AstFunction("Name", 1, params).getChild(0));
  }
}
