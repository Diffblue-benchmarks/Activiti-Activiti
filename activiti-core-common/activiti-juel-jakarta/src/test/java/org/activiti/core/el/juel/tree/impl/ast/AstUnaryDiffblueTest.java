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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.tree.impl.ast.AstUnary.Operator;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstUnaryDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstUnary#AstUnary(AstNode, Operator)}
   *   <li>{@link AstUnary#toString()}
   *   <li>{@link AstUnary#getCardinality()}
   *   <li>{@link AstUnary#getOperator()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstUnary.<init>(AstNode, Operator)",
    "int AstUnary.getCardinality()",
    "Operator AstUnary.getOperator()",
    "java.lang.String AstUnary.toString()"
  })
  void testGettersAndSetters() {
    // Arrange
    Operator operator = mock(Operator.class);

    // Act
    AstUnary actualAstUnary = new AstUnary(new AstNull(), operator);
    actualAstUnary.toString();
    int actualCardinality = actualAstUnary.getCardinality();

    // Assert
    assertEquals(1, actualCardinality);
    assertSame(operator, actualAstUnary.getOperator());
  }

  /**
   * Test {@link AstUnary#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link Operator} {@link Operator#eval(Bindings, ELContext, AstNode)} return {@code
   *       Eval}.
   *   <li>Then return {@code Eval}.
   * </ul>
   *
   * <p>Method under test: {@link AstUnary#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given Operator eval(Bindings, ELContext, AstNode) return 'Eval'; then return 'Eval'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstUnary.eval(Bindings, ELContext)"})
  void testEval_givenOperatorEvalReturnEval_thenReturnEval() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstUnary astUnary = new AstUnary(new AstNull(), operator);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    Object actualEvalResult = astUnary.eval(bindings, new SimpleContext());

    // Assert
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class));
    assertEquals("Eval", actualEvalResult);
  }

  /**
   * Test {@link AstUnary#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link Operator} {@link Operator#eval(Bindings, ELContext, AstNode)} throw {@link
   *       ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link AstUnary#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given Operator eval(Bindings, ELContext, AstNode) throw ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstUnary.eval(Bindings, ELContext)"})
  void testEval_givenOperatorEvalThrowELException_thenThrowELException() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any()))
        .thenThrow(new ELException());
    AstUnary astUnary = new AstUnary(new AstNull(), operator);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(ELException.class, () -> astUnary.eval(bindings, new SimpleContext()));
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstUnary#getChild(int)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstUnary#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when one; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstUnary.getChild(int)"})
  void testGetChild_whenOne_thenReturnNull() {
    // Arrange
    AstUnary astUnary = new AstUnary(new AstNull(), mock(Operator.class));

    // Act and Assert
    assertNull(astUnary.getChild(1));
  }

  /**
   * Test {@link AstUnary#getChild(int)}.
   *
   * <ul>
   *   <li>When zero.
   *   <li>Then return {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstUnary#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when zero; then return AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstUnary.getChild(int)"})
  void testGetChild_whenZero_thenReturnAstNull() {
    // Arrange
    AstNull child = new AstNull();
    AstUnary astUnary = new AstUnary(child, mock(Operator.class));

    // Act and Assert
    assertSame(child, astUnary.getChild(0));
  }
}
