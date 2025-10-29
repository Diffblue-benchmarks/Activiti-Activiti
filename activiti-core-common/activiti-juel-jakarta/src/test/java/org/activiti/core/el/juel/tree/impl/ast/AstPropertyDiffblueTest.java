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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.MethodNotFoundException;
import jakarta.el.PropertyNotFoundException;
import jakarta.el.ValueExpression;
import jakarta.el.ValueReference;
import java.lang.reflect.Method;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.util.RootPropertyResolver;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstPropertyDiffblueTest {
  /**
   * Method under test: {@link AstProperty#getPrefix()}
   */
  @Test
  void testGetPrefix() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);

    // Act and Assert
    assertSame(astDot.prefix, astDot.getPrefix());
  }

  /**
   * Method under test: {@link AstProperty#getPrefix()}
   */
  @Test
  void testGetPrefix2() {
    // Arrange
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), "Property", true);

    // Act and Assert
    assertSame(astDot.prefix, astDot.getPrefix());
  }

  /**
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference2() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act
    ValueReference actualValueReference = astDot.getValueReference(bindings, new SimpleContext());

    // Assert
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
    assertEquals("Eval", actualValueReference.getBase());
    assertEquals("Property", actualValueReference.getProperty());
  }

  /**
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference3() {
    // Arrange
    AstNull base = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference4() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstDot astDot = new AstDot(new AstChoice(question, yes, new AstNull()), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference5() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), null, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getValueReference(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference6() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getValueReference(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference7() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);

    AstDot astDot = new AstDot(new AstBracket(base2, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference8() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice base = new AstChoice(question, yes, new AstNull());

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference9() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstNull base2 = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstBracket(base2, new AstNull(), true, true), true, true),
        "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getValueReference(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference10() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstChoice(question, yes, new AstNull()), true, true),
        "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getValueReference(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference11() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, false), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getValueReference(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  void testEval() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astDot.eval(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  void testEval2() {
    // Arrange
    AstNull base = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astDot.eval(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  void testEval3() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstDot astDot = new AstDot(new AstChoice(question, yes, new AstNull()), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astDot.eval(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  void testEval4() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), null, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act
    Object actualEvalResult = astDot.eval(bindings, new SimpleContext());

    // Assert
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
    assertNull(actualEvalResult);
  }

  /**
   * Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  void testEval5() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astDot.eval(bindings, new SimpleContext(new RootPropertyResolver())));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#isLiteralText()}
   */
  @Test
  void testIsLiteralText() {
    // Arrange, Act and Assert
    assertFalse((new AstDot(new AstNull(), "Property", true)).isLiteralText());
  }

  /**
   * Method under test: {@link AstProperty#isLiteralText()}
   */
  @Test
  void testIsLiteralText2() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertFalse((new AstDot(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), "Property", true))
        .isLiteralText());
  }

  /**
   * Method under test: {@link AstProperty#isLeftValue()}
   */
  @Test
  void testIsLeftValue() {
    // Arrange, Act and Assert
    assertTrue((new AstDot(new AstNull(), "Property", true)).isLeftValue());
    assertFalse((new AstDot(new AstNull(), "Property", false)).isLeftValue());
  }

  /**
   * Method under test: {@link AstProperty#isLeftValue()}
   */
  @Test
  void testIsLeftValue2() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertTrue((new AstDot(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), "Property", true))
        .isLeftValue());
  }

  /**
   * Method under test: {@link AstProperty#isMethodInvocation()}
   */
  @Test
  void testIsMethodInvocation() {
    // Arrange, Act and Assert
    assertFalse((new AstDot(new AstNull(), "Property", true)).isMethodInvocation());
  }

  /**
   * Method under test: {@link AstProperty#isMethodInvocation()}
   */
  @Test
  void testIsMethodInvocation2() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertFalse((new AstDot(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), "Property", true))
        .isMethodInvocation());
  }

  /**
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType2() {
    // Arrange
    AstNull base = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType3() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstDot astDot = new AstDot(new AstChoice(question, yes, new AstNull()), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType4() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), null, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType5() {
    // Arrange
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), "Property", false);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astDot.getType(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType6() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astDot.getType(bindings, new SimpleContext(new RootPropertyResolver())));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType7() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType8() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);

    AstDot astDot = new AstDot(new AstBracket(base2, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType9() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice base = new AstChoice(question, yes, new AstNull());

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType10() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstNull base2 = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstBracket(base2, new AstNull(), true, true), true, true),
        "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType11() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstChoice(question, yes, new AstNull()), true, true),
        "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType12() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, false), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly() throws ELException {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly2() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly3() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstDot astDot = new AstDot(new AstChoice(question, yes, new AstNull()), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly4() throws ELException {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), null, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly5() throws ELException {
    // Arrange
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), "Property", false);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertTrue(astDot.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly6() throws ELException {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astDot.isReadOnly(bindings, new SimpleContext(new RootPropertyResolver())));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly7() throws ELException {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly8() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);

    AstDot astDot = new AstDot(new AstBracket(base2, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly9() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice base = new AstChoice(question, yes, new AstNull());

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly10() throws ELException {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstNull base2 = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstBracket(base2, new AstNull(), true, true), true, true),
        "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly11() throws ELException {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstChoice(question, yes, new AstNull()), true, true),
        "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly12() throws ELException {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, false), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue() throws ELException {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue2() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue3() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstDot astDot = new AstDot(new AstChoice(question, yes, new AstNull()), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue4() throws ELException {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), null, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue5() throws ELException {
    // Arrange
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), "Property", false);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(ELException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue6() throws ELException {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astDot.setValue(bindings, new SimpleContext(new RootPropertyResolver()), "Value"));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue7() throws ELException {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue8() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);

    AstDot astDot = new AstDot(new AstBracket(base2, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue9() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstChoice base = new AstChoice(question, yes, new AstNull());

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue10() throws ELException {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstNull base2 = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstBracket(base2, new AstNull(), true, true), true, true),
        "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue11() throws ELException {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstDot astDot = new AstDot(new AstBracket(base, new AstChoice(question, yes, new AstNull()), true, true),
        "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue12() throws ELException {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), operator);

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, false), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue13() throws ELException {
    // Arrange
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class));

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", false);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(ELException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Method under test:
   * {@link AstProperty#findMethod(String, Class, Class, Class[])}
   */
  @Test
  void testFindMethod() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    Class<Object> clazz = Object.class;
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(MethodNotFoundException.class,
        () -> astDot.findMethod("Name", clazz, returnType, new Class[]{forNameResult}));
  }

  /**
   * Method under test:
   * {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astDot.getMethodInfo(bindings, context, returnType, new Class[]{forNameResult}));
  }

  /**
   * Method under test:
   * {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo2() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(MethodNotFoundException.class,
        () -> astDot.getMethodInfo(bindings, context, returnType, new Class[]{forNameResult}));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test:
   * {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo3() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), null, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astDot.getMethodInfo(bindings, context, returnType, new Class[]{forNameResult}));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test:
   * {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astDot.invoke(bindings, context, returnType, new Class[]{forNameResult}, new Object[]{"Param Values"}));
  }

  /**
   * Method under test:
   * {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke2() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), "Property", true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(MethodNotFoundException.class,
        () -> astDot.invoke(bindings, context, returnType, new Class[]{forNameResult}, new Object[]{"Param Values"}));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test:
   * {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke3() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), operator), null, true);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astDot.invoke(bindings, context, returnType, new Class[]{forNameResult}, new Object[]{"Param Values"}));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Method under test: {@link AstProperty#getChild(int)}
   */
  @Test
  void testGetChild() {
    // Arrange, Act and Assert
    assertNull((new AstDot(new AstNull(), "Property", true)).getChild(1));
  }

  /**
   * Method under test: {@link AstProperty#getChild(int)}
   */
  @Test
  void testGetChild2() {
    // Arrange
    AstNull base = new AstNull();

    // Act and Assert
    assertSame(base, (new AstDot(base, "Property", true)).getChild(0));
  }

  /**
   * Method under test: {@link AstProperty#getChild(int)}
   */
  @Test
  void testGetChild3() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertNull(
        (new AstDot(new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class)), "Property", true)).getChild(1));
  }
}
