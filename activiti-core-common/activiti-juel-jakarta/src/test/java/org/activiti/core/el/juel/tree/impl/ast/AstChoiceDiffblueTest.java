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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
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

class AstChoiceDiffblueTest {
  /**
   * Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  void testEval() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astChoice.eval(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  void testEval2() throws ELException {
    // Arrange
    AstBoolean question = new AstBoolean(true);
    AstNull yes = new AstNull();
    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astChoice.eval(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  void testEval3() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice question2 = new AstChoice(question, yes, new AstNull());

    AstNull yes2 = new AstNull();
    AstChoice astChoice = new AstChoice(question2, yes2, new AstNull());
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Boolean>>any())).thenReturn(true);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter2, "Object", type)}, converter);

    // Act
    Object actualEvalResult = astChoice.eval(bindings, new SimpleContext());

    // Assert
    verify(converter, atLeast(1)).convert(isNull(), isA(Class.class));
    assertNull(actualEvalResult);
  }

  /**
   * Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  void testEval4() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull question2 = new AstNull();
    AstNull yes = new AstNull();
    AstChoice yes2 = new AstChoice(question2, yes, new AstNull());

    AstChoice astChoice = new AstChoice(question, yes2, new AstNull());
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Boolean>>any())).thenReturn(true);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter2, "Object", type)}, converter);

    // Act
    Object actualEvalResult = astChoice.eval(bindings, new SimpleContext());

    // Assert
    verify(converter, atLeast(1)).convert(isNull(), isA(Class.class));
    assertNull(actualEvalResult);
  }

  /**
   * Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astChoice.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull ? null : null", b.toString());
  }

  /**
   * Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure2() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice question2 = new AstChoice(question, yes, new AstNull());

    AstNull yes2 = new AstNull();
    AstChoice astChoice = new AstChoice(question2, yes2, new AstNull());
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astChoice.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull ? null : null ? null : null", b.toString());
  }

  /**
   * Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure3() {
    // Arrange
    AstNull question = new AstNull();
    AstNull question2 = new AstNull();
    AstNull yes = new AstNull();
    AstChoice yes2 = new AstChoice(question2, yes, new AstNull());

    AstChoice astChoice = new AstChoice(question, yes2, new AstNull());
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astChoice.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull ? null ? null : null : null", b.toString());
  }

  /**
   * Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure4() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstNull question2 = new AstNull();
    AstNull yes2 = new AstNull();
    AstChoice astChoice = new AstChoice(question, yes, new AstChoice(question2, yes2, new AstNull()));
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astChoice.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull ? null : null ? null : null", b.toString());
  }

  /**
   * Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure5() {
    // Arrange
    AstFunction question = new AstFunction("null", 1, new AstParameters(new ArrayList<>()));

    AstNull yes = new AstNull();
    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astChoice.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foonull() ? null : null", b.toString());
  }

  /**
   * Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  void testGetChild() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    // Act and Assert
    assertSame(yes, (new AstChoice(question, yes, new AstNull())).getChild(1));
  }

  /**
   * Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  void testGetChild2() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    // Act and Assert
    assertSame(question, (new AstChoice(question, yes, new AstNull())).getChild(0));
  }

  /**
   * Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  void testGetChild3() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstNull no = new AstNull();

    // Act and Assert
    assertSame(no, (new AstChoice(question, yes, no)).getChild(2));
  }

  /**
   * Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  void testGetChild4() {
    // Arrange
    AstNull left = new AstNull();
    AstBinary question = new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class));

    AstNull yes = new AstNull();

    // Act and Assert
    assertSame(yes, (new AstChoice(question, yes, new AstNull())).getChild(1));
  }

  /**
   * Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  void testGetChild5() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    // Act and Assert
    assertNull((new AstChoice(question, yes, new AstNull())).getChild(-1));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AstChoice#AstChoice(AstNode, AstNode, AstNode)}
   *   <li>{@link AstChoice#toString()}
   *   <li>{@link AstChoice#getCardinality()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    // Act
    AstChoice actualAstChoice = new AstChoice(question, yes, new AstNull());
    String actualToStringResult = actualAstChoice.toString();

    // Assert
    assertEquals("?", actualToStringResult);
    assertEquals(3, actualAstChoice.getCardinality());
  }
}
