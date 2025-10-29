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
import org.junit.jupiter.api.Test;

class AstCompositeDiffblueTest {
  /**
   * Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  void testEval() {
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
   * Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  void testEval2() {
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
   * Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  void testEval3() {
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
   * Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  void testEval4() {
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
   * Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  void testEval5() {
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
   * Method under test: {@link AstComposite#eval(Bindings, ELContext)}
   */
  @Test
  void testEval6() {
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
   * Method under test:
   * {@link AstComposite#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure() {
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
   * Method under test:
   * {@link AstComposite#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure2() {
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
   * Method under test:
   * {@link AstComposite#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure3() {
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
   * Method under test:
   * {@link AstComposite#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure4() {
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
   * Method under test: {@link AstComposite#getCardinality()}
   */
  @Test
  void testGetCardinality() {
    // Arrange, Act and Assert
    assertEquals(0, (new AstComposite(new ArrayList<>())).getCardinality());
  }

  /**
   * Method under test: {@link AstComposite#getCardinality()}
   */
  @Test
  void testGetCardinality2() {
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
   * Method under test: {@link AstComposite#getChild(int)}
   */
  @Test
  void testGetChild() {
    // Arrange
    ArrayList<AstNode> nodes = new ArrayList<>();
    nodes.add(new AstNull());
    AstNull astNull = new AstNull();
    nodes.add(astNull);

    // Act and Assert
    assertSame(astNull, (new AstComposite(nodes)).getChild(1));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AstComposite#AstComposite(List)}
   *   <li>{@link AstComposite#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("composite", (new AstComposite(new ArrayList<>())).toString());
  }
}
