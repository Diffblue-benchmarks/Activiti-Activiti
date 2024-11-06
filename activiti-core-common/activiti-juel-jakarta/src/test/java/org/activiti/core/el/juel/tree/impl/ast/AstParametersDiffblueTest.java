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

class AstParametersDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AstParameters#AstParameters(List)}
   *   <li>{@link AstParameters#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("(...)", (new AstParameters(new ArrayList<>())).toString());
  }

  /**
   * Test {@link AstParameters#eval(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstParameters#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  void testEval() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    nodes.add(new AstChoice(question, yes, new AstNull()));
    AstParameters astParameters = new AstParameters(nodes);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act
    Object[] actualEvalResult = astParameters.eval(bindings, new SimpleContext());

    // Assert
    assertNull(actualEvalResult[0]);
    assertEquals(1, actualEvalResult.length);
  }

  /**
   * Test {@link AstParameters#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default
   * constructor).</li>
   *   <li>Then return first element is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstParameters#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given ArrayList() add AstNull (default constructor); then return first element is 'null'")
  void testEval_givenArrayListAddAstNull_thenReturnFirstElementIsNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstParameters astParameters = new AstParameters(nodes);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act
    Object[] actualEvalResult = astParameters.eval(bindings, new SimpleContext());

    // Assert
    assertNull(actualEvalResult[0]);
    assertEquals(1, actualEvalResult.length);
  }

  /**
   * Test {@link AstParameters#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with
   * question is {@link AstNull} (default constructor) and yes is {@link AstNull}
   * (default constructor) and no is {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstParameters#eval(Bindings, ELContext)}
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
    AstParameters astParameters = new AstParameters(nodes);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act
    Object[] actualEvalResult = astParameters.eval(bindings, new SimpleContext());

    // Assert
    assertNull(actualEvalResult[0]);
    assertEquals(1, actualEvalResult.length);
  }

  /**
   * Test {@link AstParameters#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Then return array length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstParameters#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then return array length is zero")
  void testEval_thenReturnArrayLengthIsZero() {
    // Arrange
    AstParameters astParameters = new AstParameters(new ArrayList<>());
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertEquals(0, astParameters.eval(bindings, new SimpleContext()).length);
  }

  /**
   * Test {@link AstParameters#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foo()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstParameters#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo()'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoo() {
    // Arrange
    AstParameters astParameters = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astParameters.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo()", builder.toString());
  }

  /**
   * Test {@link AstParameters#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foo(null)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstParameters#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo(null)'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstParameters astParameters = new AstParameters(nodes);
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astParameters.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo(null)", builder.toString());
  }

  /**
   * Test {@link AstParameters#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foo(null, null)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstParameters#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo(null, null)'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooNullNull() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    nodes.add(new AstNull());
    AstParameters astParameters = new AstParameters(nodes);
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astParameters.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo(null, null)", builder.toString());
  }

  /**
   * Test {@link AstParameters#getCardinality()}.
   * <ul>
   *   <li>Given {@link AstParameters#AstParameters(List)} with nodes is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstParameters#getCardinality()}
   */
  @Test
  @DisplayName("Test getCardinality(); given AstParameters(List) with nodes is ArrayList()")
  void testGetCardinality_givenAstParametersWithNodesIsArrayList() {
    // Arrange, Act and Assert
    assertEquals(0, (new AstParameters(new ArrayList<>())).getCardinality());
  }

  /**
   * Test {@link AstParameters#getCardinality()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstParameters#getCardinality()}
   */
  @Test
  @DisplayName("Test getCardinality(); given 'java.lang.Object'")
  void testGetCardinality_givenJavaLangObject() {
    // Arrange
    AstParameters astParameters = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    astParameters.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Act and Assert
    assertEquals(0, astParameters.getCardinality());
  }

  /**
   * Test {@link AstParameters#getChild(int)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AstNull} (default
   * constructor).</li>
   *   <li>Then return {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstParameters#getChild(int)}
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
    assertSame(astNull, (new AstParameters(nodes)).getChild(1));
  }
}
