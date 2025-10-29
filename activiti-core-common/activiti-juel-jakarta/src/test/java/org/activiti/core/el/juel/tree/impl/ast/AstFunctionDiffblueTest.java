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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.junit.jupiter.api.Test;

class AstFunctionDiffblueTest {
  /**
   * Method under test:
   * {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure() {
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
   * Method under test:
   * {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure2() {
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
   * Method under test:
   * {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure3() {
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
   * Method under test:
   * {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure4() {
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
   * Method under test:
   * {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure5() {
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
   * Method under test:
   * {@link AstFunction#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure6() {
    // Arrange
    AstFunction astFunction = new AstFunction("Name", 1, new AstParameters(new ArrayList<>()));
    StringBuilder b = new StringBuilder("foo");

    // Act
    astFunction.appendStructure(b, null);

    // Assert
    assertEquals("fooName()", b.toString());
  }

  /**
   * Method under test: {@link AstFunction#getParamCount()}
   */
  @Test
  void testGetParamCount() {
    // Arrange, Act and Assert
    assertEquals(0, (new AstFunction("Name", 1, new AstParameters(new ArrayList<>()))).getParamCount());
  }

  /**
   * Method under test: {@link AstFunction#getParamCount()}
   */
  @Test
  void testGetParamCount2() {
    // Arrange
    AstParameters params = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    params.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Act and Assert
    assertEquals(0, (new AstFunction("Name", 1, params)).getParamCount());
  }

  /**
   * Method under test: {@link AstFunction#getParam(int)}
   */
  @Test
  void testGetParam() {
    // Arrange
    AstParameters params = mock(AstParameters.class);
    AstNull astNull = new AstNull();
    when(params.getChild(anyInt())).thenReturn(astNull);

    // Act
    AstNode actualParam = (new AstFunction("Name", 1, params)).getParam(1);

    // Assert
    verify(params).getChild(eq(1));
    assertSame(astNull, actualParam);
  }

  /**
   * Method under test: {@link AstFunction#getChild(int)}
   */
  @Test
  void testGetChild() {
    // Arrange, Act and Assert
    assertNull((new AstFunction("Name", 1, new AstParameters(new ArrayList<>()))).getChild(1));
  }

  /**
   * Method under test: {@link AstFunction#getChild(int)}
   */
  @Test
  void testGetChild2() {
    // Arrange
    AstParameters params = new AstParameters(new ArrayList<>());

    // Act and Assert
    assertSame(params, (new AstFunction("Name", 1, params)).getChild(0));
  }

  /**
   * Method under test: {@link AstFunction#getChild(int)}
   */
  @Test
  void testGetChild3() {
    // Arrange
    AstParameters params = new AstParameters(new ArrayList<>());
    StringBuilder builder = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    params.appendStructure(builder,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Act and Assert
    assertNull((new AstFunction("Name", 1, params)).getChild(1));
  }

  /**
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
  void testGettersAndSetters() {
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
  void testGettersAndSetters2() {
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
}
