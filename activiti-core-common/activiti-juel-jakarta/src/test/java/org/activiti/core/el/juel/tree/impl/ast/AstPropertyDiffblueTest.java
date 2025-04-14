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
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.activiti.core.el.juel.tree.impl.ast.AstBinary.Operator;
import org.activiti.core.el.juel.util.RootPropertyResolver;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstPropertyDiffblueTest {
  /**
   * Test {@link AstProperty#getPrefix()}.
   * <p>
   * Method under test: {@link AstProperty#getPrefix()}
   */
  @Test
  @DisplayName("Test getPrefix()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"AstNode AstProperty.getPrefix()"})
  void testGetPrefix() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);

    // Act and Assert
    assertSame(astDot.prefix, astDot.getPrefix());
  }

  /**
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference() {
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
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference2() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference3() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference4() {
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
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference5() {
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
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference6() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference7() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference8() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} and {@code Property} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstChoice(AstNode, AstNode, AstNode) and 'Property' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference_givenAstDotWithBaseIsAstChoiceAndPropertyAndLvalueIsTrue() {
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
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull} (default constructor) and {@code Property} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
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
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   * <ul>
   *   <li>Then return Base is {@code Eval}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); then return Base is 'Eval'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference_thenReturnBaseIsEval() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval() {
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
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with question is {@link AstNull} (default constructor) and yes is {@link AstNull} (default constructor) and no is {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstNull (default constructor) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval_givenAstChoiceWithQuestionIsAstNullAndYesIsAstNullAndNoIsAstNull() {
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
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull} (default constructor) and {@code Property} and lvalue is {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue_thenReturnNull() {
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
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Then throw {@link PropertyNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then throw PropertyNotFoundException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval_thenThrowPropertyNotFoundException() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#isLiteralText()}.
   * <p>
   * Method under test: {@link AstProperty#isLiteralText()}
   */
  @Test
  @DisplayName("Test isLiteralText()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isLiteralText()"})
  void testIsLiteralText() {
    // Arrange, Act and Assert
    assertFalse((new AstDot(new AstNull(), "Property", true)).isLiteralText());
  }

  /**
   * Test {@link AstProperty#isLeftValue()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isLeftValue()"})
  void testIsLeftValue_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AstDot(new AstNull(), "Property", false)).isLeftValue());
  }

  /**
   * Test {@link AstProperty#isLeftValue()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isLeftValue()"})
  void testIsLeftValue_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new AstDot(new AstNull(), "Property", true)).isLeftValue());
  }

  /**
   * Test {@link AstProperty#isMethodInvocation()}.
   * <p>
   * Method under test: {@link AstProperty#isMethodInvocation()}
   */
  @Test
  @DisplayName("Test isMethodInvocation()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isMethodInvocation()"})
  void testIsMethodInvocation() {
    // Arrange, Act and Assert
    assertFalse((new AstDot(new AstNull(), "Property", true)).isMethodInvocation());
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType() {
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
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType2() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType3() {
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
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType4() {
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
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType5() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType6() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType7() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} and {@code Property} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstChoice(AstNode, AstNode, AstNode) and 'Property' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType_givenAstDotWithBaseIsAstChoiceAndPropertyAndLvalueIsTrue() {
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
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull} (default constructor) and {@code Property} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
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
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType_thenReturnNull() {
    // Arrange
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), mock(Operator.class)), "Property", false);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astDot.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link RootPropertyResolver#RootPropertyResolver()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); when SimpleContext(ELResolver) with resolver is RootPropertyResolver()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType_whenSimpleContextWithResolverIsRootPropertyResolver() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly() throws ELException {
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
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly2() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly3() throws ELException {
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
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly4() throws ELException {
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
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly5() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly6() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly7() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} and {@code Property} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstChoice(AstNode, AstNode, AstNode) and 'Property' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_givenAstDotWithBaseIsAstChoiceAndPropertyAndLvalueIsTrue() throws ELException {
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
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull} (default constructor) and {@code Property} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() throws ELException {
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
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_thenReturnTrue() throws ELException {
    // Arrange
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), mock(Operator.class)), "Property", false);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertTrue(astDot.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link RootPropertyResolver#RootPropertyResolver()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); when SimpleContext(ELResolver) with resolver is RootPropertyResolver()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_whenSimpleContextWithResolverIsRootPropertyResolver() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue() throws ELException {
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
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue2() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue3() throws ELException {
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
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue4() throws ELException {
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
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue5() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue6() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue7() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} and {@code Property} and lvalue is {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and 'Property' and lvalue is 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstDotWithBaseIsAstBinaryAndPropertyAndLvalueIsFalse() throws ELException {
    // Arrange
    AstNull left = new AstNull();
    AstDot astDot = new AstDot(new AstBinary(left, new AstNull(), mock(Operator.class)), "Property", false);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(ELException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstBracket#AstBracket(AstNode, AstNode, boolean, boolean)} and {@code Property} and lvalue is {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); given AstDot(AstNode, String, boolean) with base is AstBracket(AstNode, AstNode, boolean, boolean) and 'Property' and lvalue is 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstDotWithBaseIsAstBracketAndPropertyAndLvalueIsFalse() throws ELException {
    // Arrange
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), mock(Operator.class));

    AstDot astDot = new AstDot(new AstBracket(base, new AstNull(), true, true), "Property", false);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertThrows(ELException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} and {@code Property} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); given AstDot(AstNode, String, boolean) with base is AstChoice(AstNode, AstNode, AstNode) and 'Property' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstDotWithBaseIsAstChoiceAndPropertyAndLvalueIsTrue() throws ELException {
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
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull} (default constructor) and {@code Property} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() throws ELException {
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
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link RootPropertyResolver#RootPropertyResolver()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); when SimpleContext(ELResolver) with resolver is RootPropertyResolver()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue_whenSimpleContextWithResolverIsRootPropertyResolver() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#findMethod(String, Class, Class, Class[])}.
   * <ul>
   *   <li>When array of {@link Class} with {@link Object}.</li>
   *   <li>Then throw {@link MethodNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#findMethod(String, Class, Class, Class[])}
   */
  @Test
  @DisplayName("Test findMethod(String, Class, Class, Class[]); when array of Class with Object; then throw MethodNotFoundException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Method AstProperty.findMethod(String, Class, Class, Class[])"})
  void testFindMethod_whenArrayOfClassWithObject_thenThrowMethodNotFoundException() {
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
   * Test {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[]); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"jakarta.el.MethodInfo AstProperty.getMethodInfo(Bindings, ELContext, Class, Class[])"})
  void testGetMethodInfo_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull} (default constructor) and {@code Property} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[]); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"jakarta.el.MethodInfo AstProperty.getMethodInfo(Bindings, ELContext, Class, Class[])"})
  void testGetMethodInfo_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
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
   * Test {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Then throw {@link MethodNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[]); then throw MethodNotFoundException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"jakarta.el.MethodInfo AstProperty.getMethodInfo(Bindings, ELContext, Class, Class[])"})
  void testGetMethodInfo_thenThrowMethodNotFoundException() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object AstProperty.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull} (default constructor) and {@code Property} and lvalue is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object AstProperty.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
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
   * Test {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>Then throw {@link MethodNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); then throw MethodNotFoundException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object AstProperty.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_thenThrowMethodNotFoundException() {
    // Arrange
    Operator operator = mock(Operator.class);
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
   * Test {@link AstProperty#getChild(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when one; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"AstNode AstProperty.getChild(int)"})
  void testGetChild_whenOne_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new AstDot(new AstNull(), "Property", true)).getChild(1));
  }

  /**
   * Test {@link AstProperty#getChild(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstProperty#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when zero; then return AstNull (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"AstNode AstProperty.getChild(int)"})
  void testGetChild_whenZero_thenReturnAstNull() {
    // Arrange
    AstNull base = new AstNull();

    // Act and Assert
    assertSame(base, (new AstDot(base, "Property", true)).getChild(0));
  }
}
