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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ArrayELResolver;
import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.FunctionMapper;
import jakarta.el.MethodNotFoundException;
import jakarta.el.PropertyNotFoundException;
import jakarta.el.StandardELContext;
import jakarta.el.ValueExpression;
import jakarta.el.ValueReference;
import jakarta.el.VariableMapper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.TreeValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.TreeBuilder;
import org.activiti.core.el.juel.tree.TreeStore;
import org.activiti.core.el.juel.tree.impl.Cache;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary.Operator;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstPropertyDiffblueTest {
  /**
   * Test {@link AstProperty#getPrefix()}.
   *
   * <p>Method under test: {@link AstProperty#getPrefix()}
   */
  @Test
  @DisplayName("Test getPrefix()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstProperty.getPrefix()"})
  void testGetPrefix() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);

    // Act
    AstNode actualPrefix = astDot.getPrefix();

    // Assert
    assertSame(astDot.prefix, actualPrefix);
  }

  /**
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference2() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, null, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getValueReference(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference3() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getValueReference(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference4() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstBracket base3 = new AstBracket(base2, new AstNull(), true, true);
    AstDot astDot = new AstDot(base3, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference5() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base = new AstChoice(question, yes, new AstNull());
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference6() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstNull base2 = new AstNull();
    AstBracket property = new AstBracket(base2, new AstNull(), true, true);

    AstBracket base3 = new AstBracket(base, property, true, true);
    AstDot astDot = new AstDot(base3, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getValueReference(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference7() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice property = new AstChoice(question, yes, new AstNull());

    AstBracket base2 = new AstBracket(base, property, true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getValueReference(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference8() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstBracket base2 = new AstBracket(base, new AstNull(), true, false);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getValueReference(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstChoice#AstChoice(AstNode, AstNode, AstNode)} and {@code Property} and lvalue is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getValueReference(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstChoice(AstNode, AstNode, AstNode) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference_givenAstDotWithBaseIsAstChoiceAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base = new AstChoice(question, yes, new AstNull());
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull}
   *       (default constructor) and {@code Property} and lvalue is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getValueReference(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#getValueReference(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then return Base is {@code Eval}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); then return Base is 'Eval'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstProperty.getValueReference(Bindings, ELContext)"})
  void testGetValueReference_thenReturnBaseIsEval() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    ValueReference actualValueReference = astDot.getValueReference(bindings, new SimpleContext());

    // Assert
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
    assertEquals("Eval", actualValueReference.getBase());
    assertEquals("Property", actualValueReference.getProperty());
  }

  /**
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astDot.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with question is {@link
   *       AstNull} (default constructor) and yes is {@link AstNull} (default constructor) and no is
   *       {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstNull (default constructor) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval_givenAstChoiceWithQuestionIsAstNullAndYesIsAstNullAndNoIsAstNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base = new AstChoice(question, yes, new AstNull());
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astDot.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue
   *       is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, null, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    Object actualEvalResult = astDot.eval(bindings, new SimpleContext());

    // Assert
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
    assertNull(actualEvalResult);
  }

  /**
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull}
   *       (default constructor) and {@code Property} and lvalue is {@code true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue_thenReturnNull() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astDot.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then calls build(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval_thenCallsBuild() throws ELException {
    // Arrange
    AstDot astDot = new AstDot(new AstIdentifier("Name", 0), null, true);

    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn("Convert");
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);
    ValueExpression[] variables2 = new ValueExpression[] {treeValueExpression};
    Method[] functions3 = new Method[] {null};

    Bindings bindings = new Bindings(functions3, variables2);

    // Act
    Object actualEvalResult = astDot.eval(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build("Expr");
    assertNull(actualEvalResult);
  }

  /**
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval_thenCallsConvert() throws ELException {
    // Arrange
    AstDot astDot = new AstDot(new AstIdentifier("Name", 0), null, true);

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn("Convert");
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};
    Method[] functions = new Method[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    Object actualEvalResult = astDot.eval(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertNull(actualEvalResult);
  }

  /**
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>When {@link ArrayELResolver#ArrayELResolver(boolean)} with isReadOnly is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); when ArrayELResolver(boolean) with isReadOnly is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval_whenArrayELResolverWithIsReadOnlyIsTrue() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.eval(bindings, new SimpleContext(new ArrayELResolver(true))));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       CompositeELResolver} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); when SimpleContext(ELResolver) with resolver is CompositeELResolver (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstProperty.eval(Bindings, ELContext)"})
  void testEval_whenSimpleContextWithResolverIsCompositeELResolver() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.eval(bindings, new SimpleContext(new CompositeELResolver())));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#isLiteralText()}.
   *
   * <p>Method under test: {@link AstProperty#isLiteralText()}
   */
  @Test
  @DisplayName("Test isLiteralText()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isLiteralText()"})
  void testIsLiteralText() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);

    // Act and Assert
    assertFalse(astDot.isLiteralText());
  }

  /**
   * Test {@link AstProperty#isLeftValue()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isLeftValue()"})
  void testIsLeftValue_thenReturnFalse() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", false);

    // Act and Assert
    assertFalse(astDot.isLeftValue());
  }

  /**
   * Test {@link AstProperty#isLeftValue()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isLeftValue()"})
  void testIsLeftValue_thenReturnTrue() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);

    // Act and Assert
    assertTrue(astDot.isLeftValue());
  }

  /**
   * Test {@link AstProperty#isMethodInvocation()}.
   *
   * <p>Method under test: {@link AstProperty#isMethodInvocation()}
   */
  @Test
  @DisplayName("Test isMethodInvocation()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isMethodInvocation()"})
  void testIsMethodInvocation() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);

    // Act and Assert
    assertFalse(astDot.isMethodInvocation());
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType2() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType3() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstBracket base3 = new AstBracket(base2, new AstNull(), true, true);
    AstDot astDot = new AstDot(base3, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType4() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base = new AstChoice(question, yes, new AstNull());
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType5() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstNull base2 = new AstNull();
    AstBracket property = new AstBracket(base2, new AstNull(), true, true);

    AstBracket base3 = new AstBracket(base, property, true, true);
    AstDot astDot = new AstDot(base3, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType6() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice property = new AstChoice(question, yes, new AstNull());

    AstBracket base2 = new AstBracket(base, property, true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType7() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstBracket base2 = new AstBracket(base, new AstNull(), true, false);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue
   *       is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getType(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, null, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstChoice#AstChoice(AstNode, AstNode, AstNode)} and {@code Property} and lvalue is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getType(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstChoice(AstNode, AstNode, AstNode) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType_givenAstDotWithBaseIsAstChoiceAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base = new AstChoice(question, yes, new AstNull());
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull}
   *       (default constructor) and {@code Property} and lvalue is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getType(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType_thenReturnNull() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), false, false);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);

    // Act and Assert
    assertNull(astBracket.getType(bindings, context));
  }

  /**
   * Test {@link AstProperty#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       CompositeELResolver} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getType(Bindings, ELContext); when SimpleContext(ELResolver) with resolver is CompositeELResolver (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstProperty.getType(Bindings, ELContext)"})
  void testGetType_whenSimpleContextWithResolverIsCompositeELResolver() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getType(bindings, new SimpleContext(new CompositeELResolver())));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly2() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly3() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstBracket base3 = new AstBracket(base2, new AstNull(), true, true);
    AstDot astDot = new AstDot(base3, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly4() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base = new AstChoice(question, yes, new AstNull());
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly5() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstNull base2 = new AstNull();
    AstBracket property = new AstBracket(base2, new AstNull(), true, true);

    AstBracket base3 = new AstBracket(base, property, true, true);
    AstDot astDot = new AstDot(base3, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly6() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice property = new AstChoice(question, yes, new AstNull());

    AstBracket base2 = new AstBracket(base, property, true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly7() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstBracket base2 = new AstBracket(base, new AstNull(), true, false);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue
   *       is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue()
      throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, null, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstChoice#AstChoice(AstNode, AstNode, AstNode)} and {@code Property} and lvalue is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstChoice(AstNode, AstNode, AstNode) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_givenAstDotWithBaseIsAstChoiceAndPropertyAndLvalueIsTrue()
      throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base = new AstChoice(question, yes, new AstNull());
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull}
   *       (default constructor) and {@code Property} and lvalue is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(Bindings, ELContext); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() throws ELException {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class, () -> astDot.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_thenReturnTrue() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), false, false);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);

    // Act and Assert
    assertTrue(astBracket.isReadOnly(bindings, context));
  }

  /**
   * Test {@link AstProperty#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       CompositeELResolver} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(Bindings, ELContext); when SimpleContext(ELResolver) with resolver is CompositeELResolver (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstProperty.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_whenSimpleContextWithResolverIsCompositeELResolver() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.isReadOnly(bindings, new SimpleContext(new CompositeELResolver())));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), false, false);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);

    // Act and Assert
    assertThrows(ELException.class, () -> astBracket.setValue(bindings, context, null));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue2() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue3() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue4() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstBracket base3 = new AstBracket(base2, new AstNull(), true, true);
    AstDot astDot = new AstDot(base3, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue5() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base = new AstChoice(question, yes, new AstNull());
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue6() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstNull base2 = new AstNull();
    AstBracket property = new AstBracket(base2, new AstNull(), true, true);

    AstBracket base3 = new AstBracket(base, property, true, true);
    AstDot astDot = new AstDot(base3, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue7() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice property = new AstChoice(question, yes, new AstNull());

    AstBracket base2 = new AstBracket(base, property, true, true);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue8() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstBracket base2 = new AstBracket(base, new AstNull(), true, false);
    AstDot astDot = new AstDot(base2, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue
   *       is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue()
      throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, null, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstBracket#AstBracket(AstNode, AstNode, boolean, boolean)} and {@code Property} and
   *       lvalue is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); given AstDot(AstNode, String, boolean) with base is AstBracket(AstNode, AstNode, boolean, boolean) and 'Property' and lvalue is 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstDotWithBaseIsAstBracketAndPropertyAndLvalueIsFalse()
      throws ELException {
    // Arrange
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), mock(Operator.class));
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot astDot = new AstDot(base2, "Property", false);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(ELException.class, () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstChoice#AstChoice(AstNode, AstNode, AstNode)} and {@code Property} and lvalue is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); given AstDot(AstNode, String, boolean) with base is AstChoice(AstNode, AstNode, AstNode) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstDotWithBaseIsAstChoiceAndPropertyAndLvalueIsTrue() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base = new AstChoice(question, yes, new AstNull());
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull}
   *       (default constructor) and {@code Property} and lvalue is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() throws ELException {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstProperty#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       CompositeELResolver} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); when SimpleContext(ELResolver) with resolver is CompositeELResolver (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstProperty.setValue(Bindings, ELContext, Object)"})
  void testSetValue_whenSimpleContextWithResolverIsCompositeELResolver() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.setValue(bindings, new SimpleContext(new CompositeELResolver()), "Value"));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#findMethod(String, Class, Class, Class[])}.
   *
   * <ul>
   *   <li>When array of {@link Class} with {@link Object}.
   *   <li>Then throw {@link MethodNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#findMethod(String, Class, Class, Class[])}
   */
  @Test
  @DisplayName(
      "Test findMethod(String, Class, Class, Class[]); when array of Class with Object; then throw MethodNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Method AstProperty.findMethod(String, Class, Class, Class[])"})
  void testFindMethod_whenArrayOfClassWithObject_thenThrowMethodNotFoundException() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    Class<Object> clazz = Object.class;
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () -> astDot.findMethod("Name", clazz, returnType, new Class[] {forNameResult}));
  }

  /**
   * Test {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue
   *       is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName(
      "Test getMethodInfo(Bindings, ELContext, Class, Class[]); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodInfo AstProperty.getMethodInfo(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodInfo_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, null, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getMethodInfo(bindings, context, returnType, new Class[] {forNameResult}));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull}
   *       (default constructor) and {@code Property} and lvalue is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName(
      "Test getMethodInfo(Bindings, ELContext, Class, Class[]); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodInfo AstProperty.getMethodInfo(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodInfo_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astDot.getMethodInfo(bindings, context, returnType, new Class[] {forNameResult}));
  }

  /**
   * Test {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   *
   * <ul>
   *   <li>Given {@link Operator} {@link Operator#eval(Bindings, ELContext, AstNode, AstNode)}
   *       return {@code Eval}.
   *   <li>Then throw {@link MethodNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName(
      "Test getMethodInfo(Bindings, ELContext, Class, Class[]); given Operator eval(Bindings, ELContext, AstNode, AstNode) return 'Eval'; then throw MethodNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodInfo AstProperty.getMethodInfo(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodInfo_givenOperatorEvalReturnEval_thenThrowMethodNotFoundException() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () -> astDot.getMethodInfo(bindings, context, returnType, new Class[] {forNameResult}));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link
   *       AstBinary#AstBinary(AstNode, AstNode, Operator)} and property is {@code null} and lvalue
   *       is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); given AstDot(AstNode, String, boolean) with base is AstBinary(AstNode, AstNode, Operator) and property is 'null' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstProperty.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenAstDotWithBaseIsAstBinaryAndPropertyIsNullAndLvalueIsTrue() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, null, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () ->
            astDot.invoke(
                bindings,
                context,
                returnType,
                new Class[] {forNameResult},
                new Object[] {"Param Values"}));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@link AstDot#AstDot(AstNode, String, boolean)} with base is {@link AstNull}
   *       (default constructor) and {@code Property} and lvalue is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); given AstDot(AstNode, String, boolean) with base is AstNull (default constructor) and 'Property' and lvalue is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstProperty.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenAstDotWithBaseIsAstNullAndPropertyAndLvalueIsTrue() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () ->
            astDot.invoke(
                bindings,
                context,
                returnType,
                new Class[] {forNameResult},
                new Object[] {"Param Values"}));
  }

  /**
   * Test {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@link Operator} {@link Operator#eval(Bindings, ELContext, AstNode, AstNode)}
   *       return {@code Eval}.
   *   <li>Then throw {@link MethodNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); given Operator eval(Bindings, ELContext, AstNode, AstNode) return 'Eval'; then throw MethodNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstProperty.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenOperatorEvalReturnEval_thenThrowMethodNotFoundException() {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstDot astDot = new AstDot(base, "Property", true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () ->
            astDot.invoke(
                bindings,
                context,
                returnType,
                new Class[] {forNameResult},
                new Object[] {"Param Values"}));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstProperty#getChild(int)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when one; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstProperty.getChild(int)"})
  void testGetChild_whenOne_thenReturnNull() {
    // Arrange
    AstDot astDot = new AstDot(new AstNull(), "Property", true);

    // Act and Assert
    assertNull(astDot.getChild(1));
  }

  /**
   * Test {@link AstProperty#getChild(int)}.
   *
   * <ul>
   *   <li>When zero.
   *   <li>Then return {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstProperty#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when zero; then return AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstProperty.getChild(int)"})
  void testGetChild_whenZero_thenReturnAstNull() {
    // Arrange
    AstNull base = new AstNull();
    AstDot astDot = new AstDot(base, "Property", true);

    // Act and Assert
    assertSame(base, astDot.getChild(0));
  }
}
