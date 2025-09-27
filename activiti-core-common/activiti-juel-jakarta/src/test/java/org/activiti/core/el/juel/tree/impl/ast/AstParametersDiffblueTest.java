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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AstParametersDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstParameters#AstParameters(List)}
   *   <li>{@link AstParameters#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstParameters.<init>(List)",
    "java.lang.String AstParameters.toString()"
  })
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("(...)", new AstParameters(new ArrayList<>()).toString());
  }

  /**
   * Test {@link AstParameters#eval(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstParameters#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object[] AstParameters.eval(Bindings, ELContext)"})
  void testEval() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    nodes.add(astChoice);
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
    AstParameters astParameters = new AstParameters(nodes);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    Object[] actualEvalResult = astParameters.eval(bindings, new SimpleContext());

    // Assert
    assertNull(actualEvalResult[0]);
    assertNull(actualEvalResult[1]);
    assertNull(actualEvalResult[10]);
    assertNull(actualEvalResult[11]);
    assertNull(actualEvalResult[12]);
    assertNull(actualEvalResult[13]);
    assertNull(actualEvalResult[14]);
    assertNull(actualEvalResult[15]);
    assertNull(actualEvalResult[17]);
    assertNull(actualEvalResult[2]);
    assertNull(actualEvalResult[3]);
    assertNull(actualEvalResult[4]);
    assertNull(actualEvalResult[5]);
    assertNull(actualEvalResult[6]);
    assertNull(actualEvalResult[7]);
    assertNull(actualEvalResult[8]);
    assertNull(actualEvalResult[9]);
    assertNull(actualEvalResult[Short.SIZE]);
    assertEquals(18, actualEvalResult.length);
  }

  /**
   * Test {@link AstParameters#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default constructor).
   *   <li>Then return first element is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstParameters#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given ArrayList() add AstNull (default constructor); then return first element is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object[] AstParameters.eval(Bindings, ELContext)"})
  void testEval_givenArrayListAddAstNull_thenReturnFirstElementIsNull() {
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
    AstParameters astParameters = new AstParameters(nodes);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    Object[] actualEvalResult = astParameters.eval(bindings, new SimpleContext());

    // Assert
    assertNull(actualEvalResult[0]);
    assertNull(actualEvalResult[1]);
    assertNull(actualEvalResult[10]);
    assertNull(actualEvalResult[11]);
    assertNull(actualEvalResult[12]);
    assertNull(actualEvalResult[13]);
    assertNull(actualEvalResult[14]);
    assertNull(actualEvalResult[15]);
    assertNull(actualEvalResult[17]);
    assertNull(actualEvalResult[2]);
    assertNull(actualEvalResult[3]);
    assertNull(actualEvalResult[4]);
    assertNull(actualEvalResult[5]);
    assertNull(actualEvalResult[6]);
    assertNull(actualEvalResult[7]);
    assertNull(actualEvalResult[8]);
    assertNull(actualEvalResult[9]);
    assertNull(actualEvalResult[Short.SIZE]);
    assertEquals(18, actualEvalResult.length);
  }

  /**
   * Test {@link AstParameters#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with question is {@link
   *       AstNull} (default constructor) and yes is {@link AstNull} (default constructor) and no is
   *       {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstParameters#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstNull (default constructor) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object[] AstParameters.eval(Bindings, ELContext)"})
  void testEval_givenAstChoiceWithQuestionIsAstNullAndYesIsAstNullAndNoIsAstNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice question2 = new AstChoice(question, yes, new AstNull());
    AstNull yes2 = new AstNull();

    AstChoice astChoice = new AstChoice(question2, yes2, new AstNull());
    nodes.add(astChoice);
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
    AstParameters astParameters = new AstParameters(nodes);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    Object[] actualEvalResult = astParameters.eval(bindings, new SimpleContext());

    // Assert
    assertNull(actualEvalResult[0]);
    assertNull(actualEvalResult[1]);
    assertNull(actualEvalResult[10]);
    assertNull(actualEvalResult[11]);
    assertNull(actualEvalResult[12]);
    assertNull(actualEvalResult[13]);
    assertNull(actualEvalResult[14]);
    assertNull(actualEvalResult[15]);
    assertNull(actualEvalResult[17]);
    assertNull(actualEvalResult[2]);
    assertNull(actualEvalResult[3]);
    assertNull(actualEvalResult[4]);
    assertNull(actualEvalResult[5]);
    assertNull(actualEvalResult[6]);
    assertNull(actualEvalResult[7]);
    assertNull(actualEvalResult[8]);
    assertNull(actualEvalResult[9]);
    assertNull(actualEvalResult[Short.SIZE]);
    assertEquals(18, actualEvalResult.length);
  }

  /**
   * Test {@link AstParameters#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then return array length is zero.
   * </ul>
   *
   * <p>Method under test: {@link AstParameters#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then return array length is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object[] AstParameters.eval(Bindings, ELContext)"})
  void testEval_thenReturnArrayLengthIsZero() {
    // Arrange
    AstParameters astParameters = new AstParameters(new ArrayList<>());
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertEquals(0, astParameters.eval(bindings, new SimpleContext()).length);
  }

  /**
   * Test {@link AstParameters#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is a string.
   * </ul>
   *
   * <p>Method under test: {@link AstParameters#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is a string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstParameters.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsAString() {
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
    AstParameters astParameters = new AstParameters(nodes);
    StringBuilder builder = new StringBuilder("foo");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astParameters.appendStructure(builder, bindings);

    // Assert
    assertEquals(
        "foo(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,"
            + " null, null)",
        builder.toString());
  }

  /**
   * Test {@link AstParameters#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foo()}.
   * </ul>
   *
   * <p>Method under test: {@link AstParameters#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo()'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstParameters.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoo() {
    // Arrange
    AstParameters astParameters = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astParameters.appendStructure(builder, bindings);

    // Assert
    assertEquals("foo()", builder.toString());
  }

  /**
   * Test {@link AstParameters#getCardinality()}.
   *
   * <p>Method under test: {@link AstParameters#getCardinality()}
   */
  @Test
  @DisplayName("Test getCardinality()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int AstParameters.getCardinality()"})
  void testGetCardinality() {
    // Arrange, Act and Assert
    assertEquals(0, new AstParameters(new ArrayList<>()).getCardinality());
  }

  /**
   * Test {@link AstParameters#getChild(int)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default constructor).
   *   <li>Then return {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstParameters#getChild(int)}
   */
  @Test
  @DisplayName(
      "Test getChild(int); given ArrayList() add AstNull (default constructor); then return AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstParameters.getChild(int)"})
  void testGetChild_givenArrayListAddAstNull_thenReturnAstNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstNull astNull = new AstNull();
    nodes.add(astNull);

    // Act and Assert
    assertSame(astNull, new AstParameters(nodes).getChild(1));
  }
}
