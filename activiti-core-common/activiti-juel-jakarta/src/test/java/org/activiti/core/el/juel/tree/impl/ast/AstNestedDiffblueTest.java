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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstNestedDiffblueTest {
  /**
   * Method under test: {@link AstNested#eval(Bindings, ELContext)}
   */
  @Test
  void testEval() {
    // Arrange
    AstNested astNested = new AstNested(new AstNull());
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astNested.eval(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstNested#eval(Bindings, ELContext)}
   */
  @Test
  void testEval2() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstNested astNested = new AstNested(new AstChoice(question, yes, new AstNull()));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astNested.eval(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstNested#eval(Bindings, ELContext)}
   */
  @Test
  void testEval3() throws ELException {
    // Arrange
    AstNested astNested = new AstNested(new AstIdentifier("Name", 0));
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Convert");
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act
    Object actualEvalResult = astNested.eval(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertEquals("Convert", actualEvalResult);
  }

  /**
   * Method under test: {@link AstNested#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure() {
    // Arrange
    AstNested astNested = new AstNested(new AstNull());
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astNested.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo(null)", b.toString());
  }

  /**
   * Method under test: {@link AstNested#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure2() {
    // Arrange
    AstNested astNested = new AstNested(new AstFunction("(", 1, new AstParameters(new ArrayList<>())));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astNested.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo((())", b.toString());
  }

  /**
   * Method under test: {@link AstNested#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure3() {
    // Arrange
    AstNested astNested = new AstNested(new AstFunction("(", 0, new AstParameters(new ArrayList<>())));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astNested.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo(<fn>())", b.toString());
  }

  /**
   * Method under test: {@link AstNested#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure4() {
    // Arrange
    AstNested astNested = new AstNested(new AstFunction("(", -1, new AstParameters(new ArrayList<>())));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astNested.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo((())", b.toString());
  }

  /**
   * Method under test: {@link AstNested#getChild(int)}
   */
  @Test
  void testGetChild() {
    // Arrange, Act and Assert
    assertNull((new AstNested(new AstNull())).getChild(1));
  }

  /**
   * Method under test: {@link AstNested#getChild(int)}
   */
  @Test
  void testGetChild2() {
    // Arrange
    AstNull child = new AstNull();

    // Act and Assert
    assertSame(child, (new AstNested(child)).getChild(0));
  }

  /**
   * Method under test: {@link AstNested#getChild(int)}
   */
  @Test
  void testGetChild3() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertNull((new AstNested(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)))).getChild(1));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AstNested#AstNested(AstNode)}
   *   <li>{@link AstNested#toString()}
   *   <li>{@link AstNested#getCardinality()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    AstNested actualAstNested = new AstNested(new AstNull());
    String actualToStringResult = actualAstNested.toString();

    // Assert
    assertEquals("(...)", actualToStringResult);
    assertEquals(1, actualAstNested.getCardinality());
  }
}
