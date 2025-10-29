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
import static org.junit.jupiter.api.Assertions.assertTrue;
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

class AstBracketDiffblueTest {
  /**
   * Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  void testGetProperty() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  void testGetProperty2() throws ELException {
    // Arrange
    new ELException("An error occurred");
    AstNull base = new AstNull();
    AstNull base2 = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstBracket(base2, new AstNull(), true, true), true, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  void testGetProperty3() throws ELException {
    // Arrange
    new ELException("An error occurred");
    AstNull base = new AstNull();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstChoice(question, yes, new AstNull()), true, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  void testGetProperty4() throws ELException {
    // Arrange
    new ELException("An error occurred");
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstBracket property = new AstBracket(base, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(new AstNull(), property, true, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act
    Object actualProperty = astBracket.getProperty(bindings, new SimpleContext());

    // Assert
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
    assertNull(actualProperty);
  }

  /**
   * Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  void testGetProperty5() throws ELException {
    // Arrange
    new ELException("An error occurred");
    AstNull base = new AstNull();
    AstNull base2 = new AstNull();
    AstBracket base3 = new AstBracket(base2, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(base, new AstBracket(base3, new AstNull(), true, true), true, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  void testGetProperty6() throws ELException {
    // Arrange
    new ELException("An error occurred");
    AstNull base = new AstNull();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice base2 = new AstChoice(question, yes, new AstNull());

    AstBracket astBracket = new AstBracket(base, new AstBracket(base2, new AstNull(), true, true), true, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Method under test:
   * {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astBracket.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull[null]", b.toString());
  }

  /**
   * Method under test:
   * {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure2() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(base2, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astBracket.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull[null][null]", b.toString());
  }

  /**
   * Method under test:
   * {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure3() {
    // Arrange
    AstDot base = new AstDot(new AstNull(), "null", true);

    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astBracket.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull.null[null]", b.toString());
  }

  /**
   * Method under test:
   * {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure4() {
    // Arrange
    AstNull base = new AstNull();
    AstNull base2 = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstBracket(base2, new AstNull(), true, true), true, true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astBracket.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull[null[null]]", b.toString());
  }

  /**
   * Method under test:
   * {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure5() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstDot(new AstNull(), "null", true), true, true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astBracket.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull[null.null]", b.toString());
  }

  /**
   * Method under test:
   * {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure6() {
    // Arrange
    AstFunction base = new AstFunction("null", 1, new AstParameters(new ArrayList<>()));

    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astBracket.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull()[null]", b.toString());
  }

  /**
   * Method under test: {@link AstBracket#getChild(int)}
   */
  @Test
  void testGetChild() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);

    // Act and Assert
    assertSame(astBracket.property, astBracket.getChild(1));
  }

  /**
   * Method under test: {@link AstBracket#getChild(int)}
   */
  @Test
  void testGetChild2() {
    // Arrange
    AstNull base = new AstNull();

    // Act and Assert
    assertSame(base, (new AstBracket(base, new AstNull(), true, true)).getChild(0));
  }

  /**
   * Method under test: {@link AstBracket#getChild(int)}
   */
  @Test
  void testGetChild3() {
    // Arrange
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class));

    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);

    // Act and Assert
    assertSame(astBracket.property, astBracket.getChild(1));
  }

  /**
   * Method under test: {@link AstBracket#getChild(int)}
   */
  @Test
  void testGetChild4() {
    // Arrange
    AstNull base = new AstNull();

    // Act and Assert
    assertNull((new AstBracket(base, new AstNull(), true, true)).getChild(-1));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AstBracket#AstBracket(AstNode, AstNode, boolean, boolean)}
   *   <li>{@link AstBracket#toString()}
   *   <li>{@link AstBracket#getCardinality()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    AstNull base = new AstNull();

    // Act
    AstBracket actualAstBracket = new AstBracket(base, new AstNull(), true, true);
    String actualToStringResult = actualAstBracket.toString();

    // Assert
    assertEquals("[...]", actualToStringResult);
    assertEquals(2, actualAstBracket.getCardinality());
    assertTrue(actualAstBracket.isLeftValue());
    assertSame(base, actualAstBracket.getPrefix());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link AstBracket#AstBracket(AstNode, AstNode, boolean, boolean, boolean)}
   *   <li>{@link AstBracket#toString()}
   *   <li>{@link AstBracket#getCardinality()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange
    AstNull base = new AstNull();

    // Act
    AstBracket actualAstBracket = new AstBracket(base, new AstNull(), true, true, true);
    String actualToStringResult = actualAstBracket.toString();

    // Assert
    assertEquals("[...]", actualToStringResult);
    assertEquals(2, actualAstBracket.getCardinality());
    assertTrue(actualAstBracket.isLeftValue());
    assertSame(base, actualAstBracket.getPrefix());
  }
}
