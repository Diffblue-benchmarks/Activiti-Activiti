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
import org.junit.jupiter.api.Test;

class AstParametersDiffblueTest {
  /**
   * Method under test: {@link AstParameters#eval(Bindings, ELContext)}
   */
  @Test
  void testEval() {
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
   * Method under test: {@link AstParameters#eval(Bindings, ELContext)}
   */
  @Test
  void testEval2() {
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
   * Method under test: {@link AstParameters#eval(Bindings, ELContext)}
   */
  @Test
  void testEval3() {
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
   * Method under test: {@link AstParameters#eval(Bindings, ELContext)}
   */
  @Test
  void testEval4() {
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
   * Method under test:
   * {@link AstParameters#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure() {
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
   * Method under test:
   * {@link AstParameters#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure2() {
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
   * Method under test:
   * {@link AstParameters#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure3() {
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
   * Method under test: {@link AstParameters#getCardinality()}
   */
  @Test
  void testGetCardinality() {
    // Arrange, Act and Assert
    assertEquals(0, (new AstParameters(new ArrayList<>())).getCardinality());
  }

  /**
   * Method under test: {@link AstParameters#getCardinality()}
   */
  @Test
  void testGetCardinality2() {
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
   * Method under test: {@link AstParameters#getChild(int)}
   */
  @Test
  void testGetChild() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstNull astNull = new AstNull();
    nodes.add(astNull);

    // Act and Assert
    assertSame(astNull, (new AstParameters(nodes)).getChild(1));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AstParameters#AstParameters(List)}
   *   <li>{@link AstParameters#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("(...)", (new AstParameters(new ArrayList<>())).toString());
  }
}
