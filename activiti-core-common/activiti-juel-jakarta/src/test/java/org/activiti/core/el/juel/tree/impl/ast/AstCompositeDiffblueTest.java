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
import org.junit.jupiter.api.Test;

class AstCompositeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AstComposite#AstComposite(List)}
   *   <li>{@link AstComposite#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("composite", (new AstComposite(new ArrayList<>())).toString());
  }

  /**
   * Test {@link AstComposite#eval(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  void testEval() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    nodes.add(new AstChoice(question, yes, new AstNull()));
    AstComposite astComposite = new AstComposite(nodes);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertEquals("", astComposite.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstComposite#eval(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  void testEval2() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    nodes.add(new AstChoice(question, yes, new AstComposite(new ArrayList<>())));
    AstComposite astComposite = new AstComposite(nodes);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertEquals("", astComposite.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstComposite#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link AstComposite#AstComposite(List)} with nodes is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given ArrayList() add AstComposite(List) with nodes is ArrayList(); then return empty string")
  void testEval_givenArrayListAddAstCompositeWithNodesIsArrayList_thenReturnEmptyString() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstComposite(new ArrayList<>()));
    AstComposite astComposite = new AstComposite(nodes);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertEquals("", astComposite.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstComposite#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default
   * constructor).</li>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given ArrayList() add AstNull (default constructor); when 'java.lang.Object'; then return empty string")
  void testEval_givenArrayListAddAstNull_whenJavaLangObject_thenReturnEmptyString() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstComposite astComposite = new AstComposite(nodes);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertEquals("", astComposite.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstComposite#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with
   * question is {@link AstNull} (default constructor) and yes is {@link AstNull}
   * (default constructor) and no is {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstNull (default constructor) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  void testEval_givenAstChoiceWithQuestionIsAstNullAndYesIsAstNullAndNoIsAstNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice question2 = new AstChoice(question, yes, new AstNull());

    AstNull yes2 = new AstNull();
    nodes.add(new AstChoice(question2, yes2, new AstNull()));
    AstComposite astComposite = new AstComposite(nodes);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertEquals("", astComposite.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstComposite#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); when 'java.lang.Object'; then return empty string")
  void testEval_whenJavaLangObject_thenReturnEmptyString() {
    // Arrange
    AstComposite astComposite = new AstComposite(new ArrayList<>());
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertEquals("", astComposite.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstComposite#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link AstComposite#AstComposite(List)} with nodes is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstComposite#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); given ArrayList() add AstComposite(List) with nodes is ArrayList()")
  void testAppendStructure_givenArrayListAddAstCompositeWithNodesIsArrayList() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstComposite(new ArrayList<>()));
    AstComposite astComposite = new AstComposite(nodes);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astComposite.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert that nothing has changed
    assertEquals("foo", b.toString());
  }

  /**
   * Test {@link AstComposite#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstComposite#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoo() {
    // Arrange
    AstComposite astComposite = new AstComposite(new ArrayList<>());
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astComposite.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert that nothing has changed
    assertEquals("foo", b.toString());
  }

  /**
   * Test {@link AstComposite#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstComposite#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstComposite astComposite = new AstComposite(nodes);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astComposite.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull", b.toString());
  }

  /**
   * Test {@link AstComposite#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foonull()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstComposite#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull()'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonull2() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstFunction("null", 1, new AstParameters(new ArrayList<>())));
    AstComposite astComposite = new AstComposite(nodes);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astComposite.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull()", b.toString());
  }

  /**
   * Test {@link AstComposite#getCardinality()}.
   * <ul>
   *   <li>Given {@link AstComposite#AstComposite(List)} with nodes is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstComposite#getCardinality()}
   */
  @Test
  @DisplayName("Test getCardinality(); given AstComposite(List) with nodes is ArrayList()")
  void testGetCardinality_givenAstCompositeWithNodesIsArrayList() {
    // Arrange, Act and Assert
    assertEquals(0, (new AstComposite(new ArrayList<>())).getCardinality());
  }

  /**
   * Test {@link AstComposite#getCardinality()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstComposite#getCardinality()}
   */
  @Test
  @DisplayName("Test getCardinality(); given 'java.lang.Object'")
  void testGetCardinality_givenJavaLangObject() {
    // Arrange
    AstComposite astComposite = new AstComposite(new ArrayList<>());
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    astComposite.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Act and Assert
    assertEquals(0, astComposite.getCardinality());
  }

  /**
   * Test {@link AstComposite#getChild(int)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default
   * constructor).</li>
   *   <li>Then return {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstComposite#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); given ArrayList() add AstNull (default constructor); then return AstNull (default constructor)")
  void testGetChild_givenArrayListAddAstNull_thenReturnAstNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstNull astNull = new AstNull();
    nodes.add(astNull);

    // Act and Assert
    assertSame(astNull, (new AstComposite(nodes)).getChild(1));
  }
}
