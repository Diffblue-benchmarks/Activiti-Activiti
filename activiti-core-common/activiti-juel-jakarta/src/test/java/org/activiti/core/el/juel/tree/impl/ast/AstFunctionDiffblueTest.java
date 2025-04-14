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
   * <ul>
   *   <li>When {@link AstParameters#AstParameters(List)} with nodes is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return not VarArgs.</li>
   * </ul>
   * <p>
   * Methods under test:
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
  @DisplayName("Test getters and setters; when AstParameters(List) with nodes is ArrayList(); then return not VarArgs")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstFunction.<init>(String, int, AstParameters)",
      "void AstFunction.<init>(String, int, AstParameters, boolean)", "int AstFunction.getCardinality()",
      "int AstFunction.getIndex()", "String AstFunction.getName()", "boolean AstFunction.isVarArgs()",
      "String AstFunction.toString()"})
  void testGettersAndSetters_whenAstParametersWithNodesIsArrayList_thenReturnNotVarArgs() {
    // Arrange and Act
    AstFunction actualAstFunction = new AstFunction("Name", 1, new AstParameters(new ArrayList<>()));
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
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then return VarArgs.</li>
   * </ul>
   * <p>
   * Methods under test:
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstFunction.<init>(String, int, AstParameters)",
      "void AstFunction.<init>(String, int, AstParameters, boolean)", "int AstFunction.getCardinality()",
      "int AstFunction.getIndex()", "String AstFunction.getName()", "boolean AstFunction.isVarArgs()",
      "String AstFunction.toString()"})
  void testGettersAndSetters_whenTrue_thenReturnVarArgs() {
    // Arrange and Act
    AstFunction actualAstFunction = new AstFunction("Name", 1, new AstParameters(new ArrayList<>()), true);
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
   * <p>
   * Method under test: {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstFunction.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure() {
    // Arrange
    AstFunction astFunction = new AstFunction("Name", -1, new AstParameters(new ArrayList<>()));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astFunction.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("fooName()", b.toString());
  }

  /**
   * Test {@link AstFunction#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code foo<fn>()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo<fn>()'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstFunction.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooFn() {
    // Arrange
    AstFunction astFunction = new AstFunction("Name", 0, new AstParameters(new ArrayList<>()));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astFunction.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo<fn>()", b.toString());
  }

  /**
   * Test {@link AstFunction#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code fooName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'fooName()'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstFunction.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooName() {
    // Arrange
    AstFunction astFunction = new AstFunction("Name", 1, new AstParameters(new ArrayList<>()));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astFunction.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("fooName()", b.toString());
  }

  /**
   * Test {@link AstFunction#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code fooName(null)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'fooName(null)'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstFunction.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooNameNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstFunction astFunction = new AstFunction("Name", 1, new AstParameters(nodes));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astFunction.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("fooName(null)", b.toString());
  }

  /**
   * Test {@link AstFunction#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code fooName(null, null)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'fooName(null, null)'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstFunction.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooNameNullNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    AstFunction astFunction = new AstFunction("Name", 1, new AstParameters(nodes));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astFunction.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("fooName(null, null)", b.toString());
  }

  /**
   * Test {@link AstFunction#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code fooName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); when 'null'; then StringBuilder(String) with 'foo' toString is 'fooName()'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstFunction.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_whenNull_thenStringBuilderWithFooToStringIsFooName() {
    // Arrange
    AstFunction astFunction = new AstFunction("Name", 1, new AstParameters(new ArrayList<>()));
    StringBuilder b = new StringBuilder("foo");

    // Act
    astFunction.appendStructure(b, null);

    // Assert
    assertEquals("fooName()", b.toString());
  }

  /**
   * Test {@link AstFunction#getParamCount()}.
   * <ul>
   *   <li>Given {@link AstParameters#AstParameters(List)} with nodes is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstFunction#getParamCount()}
   */
  @Test
  @DisplayName("Test getParamCount(); given AstParameters(List) with nodes is ArrayList(); then return zero")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"int AstFunction.getParamCount()"})
  void testGetParamCount_givenAstParametersWithNodesIsArrayList_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new AstFunction("Name", 1, new AstParameters(new ArrayList<>()))).getParamCount());
  }

  /**
   * Test {@link AstFunction#getParam(int)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default constructor).</li>
   *   <li>Then return {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstFunction#getParam(int)}
   */
  @Test
  @DisplayName("Test getParam(int); given ArrayList() add AstNull (default constructor); then return AstNull (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"AstNode AstFunction.getParam(int)"})
  void testGetParam_givenArrayListAddAstNull_thenReturnAstNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstNull astNull = new AstNull();
    nodes.add(astNull);

    // Act and Assert
    assertSame(astNull, (new AstFunction("Name", 1, new AstParameters(nodes))).getParam(1));
  }

  /**
   * Test {@link AstFunction#getChild(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstFunction#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when one; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"AstNode AstFunction.getChild(int)"})
  void testGetChild_whenOne_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new AstFunction("Name", 1, new AstParameters(new ArrayList<>()))).getChild(1));
  }

  /**
   * Test {@link AstFunction#getChild(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@link AstParameters#AstParameters(List)} with nodes is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstFunction#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when zero; then return AstParameters(List) with nodes is ArrayList()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"AstNode AstFunction.getChild(int)"})
  void testGetChild_whenZero_thenReturnAstParametersWithNodesIsArrayList() {
    // Arrange
    AstParameters params = new AstParameters(new ArrayList<>());

    // Act and Assert
    assertSame(params, (new AstFunction("Name", 1, params)).getChild(0));
  }
}
