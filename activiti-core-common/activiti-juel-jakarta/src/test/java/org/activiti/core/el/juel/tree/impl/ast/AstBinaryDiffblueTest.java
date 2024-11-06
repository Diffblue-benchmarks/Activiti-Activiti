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
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstBinaryDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AstBinary#AstBinary(AstNode, AstNode, AstBinary.Operator)}
   *   <li>{@link AstBinary#toString()}
   *   <li>{@link AstBinary#getCardinality()}
   *   <li>{@link AstBinary#getOperator()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    AstNull left = new AstNull();
    AstBinary.Operator operator = mock(AstBinary.Operator.class);

    // Act
    AstBinary actualAstBinary = new AstBinary(left, new AstNull(), operator);
    actualAstBinary.toString();
    int actualCardinality = actualAstBinary.getCardinality();

    // Assert
    assertEquals(2, actualCardinality);
    assertSame(operator, actualAstBinary.getOperator());
  }

  /**
   * Test {@link AstBinary#eval(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstBinary#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  void testEval() {
    // Arrange
    AstBinary.Operator operator = mock(AstBinary.Operator.class);
    when(operator.eval(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<AstNode>any(),
        Mockito.<AstNode>any())).thenReturn("Eval");
    AstNull left = new AstNull();
    AstBinary astBinary = new AstBinary(left, new AstNull(), operator);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act
    Object actualEvalResult = astBinary.eval(bindings, new SimpleContext());

    // Assert
    verify(operator).eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
    assertEquals("Eval", actualEvalResult);
  }

  /**
   * Test {@link AstBinary#getChild(int)}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstBinary#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when minus one; then return 'null'")
  void testGetChild_whenMinusOne_thenReturnNull() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertNull((new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class))).getChild(-1));
  }

  /**
   * Test {@link AstBinary#getChild(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstBinary#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when one; then return AstNull (default constructor)")
  void testGetChild_whenOne_thenReturnAstNull() {
    // Arrange
    AstNull left = new AstNull();
    AstNull right = new AstNull();

    // Act and Assert
    assertSame(right, (new AstBinary(left, right, mock(AstBinary.Operator.class))).getChild(1));
  }

  /**
   * Test {@link AstBinary#getChild(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@link AstNull} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AstBinary#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when zero; then return AstNull (default constructor)")
  void testGetChild_whenZero_thenReturnAstNull() {
    // Arrange
    AstNull left = new AstNull();

    // Act and Assert
    assertSame(left, (new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class))).getChild(0));
  }
}
