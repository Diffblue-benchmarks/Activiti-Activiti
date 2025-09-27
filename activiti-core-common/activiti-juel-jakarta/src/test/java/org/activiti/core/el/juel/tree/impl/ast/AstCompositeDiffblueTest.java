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

class AstCompositeDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstComposite#AstComposite(List)}
   *   <li>{@link AstComposite#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstComposite.<init>(List)", "java.lang.String AstComposite.toString()"})
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("composite", new AstComposite(new ArrayList<>()).toString());
  }

  /**
   * Test {@link AstComposite#eval(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstComposite.eval(Bindings, ELContext)"})
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
    AstComposite astComposite = new AstComposite(nodes);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertEquals("", astComposite.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstComposite#eval(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstComposite.eval(Bindings, ELContext)"})
  void testEval2() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, new AstComposite(new ArrayList<>()));
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
    AstComposite astComposite = new AstComposite(nodes);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertEquals("", astComposite.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstComposite#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstComposite#AstComposite(List)} with
   *       nodes is {@link ArrayList#ArrayList()}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given ArrayList() add AstComposite(List) with nodes is ArrayList(); then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstComposite.eval(Bindings, ELContext)"})
  void testEval_givenArrayListAddAstCompositeWithNodesIsArrayList_thenReturnEmptyString() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstComposite(new ArrayList<>()));
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
    AstComposite astComposite = new AstComposite(nodes);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertEquals("", astComposite.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstComposite#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default constructor).
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given ArrayList() add AstNull (default constructor); then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstComposite.eval(Bindings, ELContext)"})
  void testEval_givenArrayListAddAstNull_thenReturnEmptyString() {
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
    AstComposite astComposite = new AstComposite(nodes);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertEquals("", astComposite.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstComposite#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with question is {@link
   *       AstNull} (default constructor) and yes is {@link AstNull} (default constructor) and no is
   *       {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstNull (default constructor) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstComposite.eval(Bindings, ELContext)"})
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
    AstComposite astComposite = new AstComposite(nodes);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertEquals("", astComposite.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstComposite#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstComposite.eval(Bindings, ELContext)"})
  void testEval_thenReturnEmptyString() {
    // Arrange
    AstComposite astComposite = new AstComposite(new ArrayList<>());
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertEquals("", astComposite.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstComposite#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstComposite#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstComposite.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure() {
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
    AstComposite astComposite = new AstComposite(nodes);
    StringBuilder b = new StringBuilder("foo");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astComposite.appendStructure(b, bindings);

    // Assert
    assertEquals(
        "foonullnullnullnullnullnullnullnullnullnullnullnullnullnullnullnullnullnull",
        b.toString());
  }

  /**
   * Test {@link AstComposite#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstComposite#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstComposite.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure2() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstComposite(new ArrayList<>()));
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
    AstComposite astComposite = new AstComposite(nodes);
    StringBuilder b = new StringBuilder("foo");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astComposite.appendStructure(b, bindings);

    // Assert
    assertEquals(
        "foonullnullnullnullnullnullnullnullnullnullnullnullnullnullnullnullnull", b.toString());
  }

  /**
   * Test {@link AstComposite#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foo}.
   * </ul>
   *
   * <p>Method under test: {@link AstComposite#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstComposite.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoo() {
    // Arrange
    AstComposite astComposite = new AstComposite(new ArrayList<>());
    StringBuilder b = new StringBuilder("foo");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astComposite.appendStructure(b, bindings);

    // Assert that nothing has changed
    assertEquals("foo", b.toString());
  }

  /**
   * Test {@link AstComposite#getCardinality()}.
   *
   * <p>Method under test: {@link AstComposite#getCardinality()}
   */
  @Test
  @DisplayName("Test getCardinality()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int AstComposite.getCardinality()"})
  void testGetCardinality() {
    // Arrange, Act and Assert
    assertEquals(0, new AstComposite(new ArrayList<>()).getCardinality());
  }

  /**
   * Test {@link AstComposite#getChild(int)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default constructor).
   *   <li>Then return {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstComposite#getChild(int)}
   */
  @Test
  @DisplayName(
      "Test getChild(int); given ArrayList() add AstNull (default constructor); then return AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstComposite.getChild(int)"})
  void testGetChild_givenArrayListAddAstNull_thenReturnAstNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstNull astNull = new AstNull();
    nodes.add(astNull);

    // Act and Assert
    assertSame(astNull, new AstComposite(nodes).getChild(1));
  }
}
