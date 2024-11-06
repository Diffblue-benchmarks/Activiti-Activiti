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
package org.activiti.core.el.juel.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ELException;
import jakarta.el.FunctionMapper;
import jakarta.el.VariableMapper;
import java.util.ArrayList;
import java.util.List;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.impl.ast.AstFunction;
import org.activiti.core.el.juel.tree.impl.ast.AstNull;
import org.activiti.core.el.juel.tree.impl.ast.AstParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TreeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Tree#Tree(ExpressionNode, List, List, boolean)}
   *   <li>{@link Tree#toString()}
   *   <li>{@link Tree#getFunctionNodes()}
   *   <li>{@link Tree#getIdentifierNodes()}
   *   <li>{@link Tree#getRoot()}
   *   <li>{@link Tree#isDeferred()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    ArrayList<IdentifierNode> identifiers = new ArrayList<>();

    // Act
    Tree actualTree = new Tree(root, functions, identifiers, true);
    String actualToStringResult = actualTree.toString();
    Iterable<FunctionNode> actualFunctionNodes = actualTree.getFunctionNodes();
    Iterable<IdentifierNode> actualIdentifierNodes = actualTree.getIdentifierNodes();
    ExpressionNode actualRoot = actualTree.getRoot();

    // Assert
    assertTrue(actualFunctionNodes instanceof List);
    assertTrue(actualIdentifierNodes instanceof List);
    assertEquals("null", actualToStringResult);
    assertTrue(actualTree.isDeferred());
    assertSame(functions, actualFunctionNodes);
    assertSame(identifiers, actualIdentifierNodes);
    assertSame(root, actualRoot);
  }

  /**
   * Test {@link Tree#bind(FunctionMapper, VariableMapper, TypeConverter)} with
   * {@code fnMapper}, {@code varMapper}, {@code converter}.
   * <p>
   * Method under test:
   * {@link Tree#bind(FunctionMapper, VariableMapper, TypeConverter)}
   */
  @Test
  @DisplayName("Test bind(FunctionMapper, VariableMapper, TypeConverter) with 'fnMapper', 'varMapper', 'converter'")
  void testBindWithFnMapperVarMapperConverter() {
    // Arrange
    ArrayList<FunctionNode> functions = new ArrayList<>();
    functions.add(new AstFunction("Name", 1, new AstParameters(new ArrayList<>())));
    AstNull root = new AstNull();
    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    FunctionMapper fnMapper = mock(FunctionMapper.class);
    when(fnMapper.resolveFunction(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> tree.bind(fnMapper, mock(VariableMapper.class), mock(TypeConverter.class)));
    verify(fnMapper).resolveFunction(eq(""), eq("Name"));
  }

  /**
   * Test {@link Tree#bind(FunctionMapper, VariableMapper, TypeConverter)} with
   * {@code fnMapper}, {@code varMapper}, {@code converter}.
   * <ul>
   *   <li>Then return not FunctionBound is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Tree#bind(FunctionMapper, VariableMapper, TypeConverter)}
   */
  @Test
  @DisplayName("Test bind(FunctionMapper, VariableMapper, TypeConverter) with 'fnMapper', 'varMapper', 'converter'; then return not FunctionBound is one")
  void testBindWithFnMapperVarMapperConverter_thenReturnNotFunctionBoundIsOne() {
    // Arrange
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    // Act
    Bindings actualBindResult = (new Tree(root, functions, new ArrayList<>(), true)).bind(mock(FunctionMapper.class),
        mock(VariableMapper.class), mock(TypeConverter.class));

    // Assert
    assertFalse(actualBindResult.isFunctionBound(1));
    assertFalse(actualBindResult.isVariableBound(1));
  }

  /**
   * Test {@link Tree#bind(FunctionMapper, VariableMapper, TypeConverter)} with
   * {@code fnMapper}, {@code varMapper}, {@code converter}.
   * <ul>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Tree#bind(FunctionMapper, VariableMapper, TypeConverter)}
   */
  @Test
  @DisplayName("Test bind(FunctionMapper, VariableMapper, TypeConverter) with 'fnMapper', 'varMapper', 'converter'; then throw ELException")
  void testBindWithFnMapperVarMapperConverter_thenThrowELException() {
    // Arrange
    ArrayList<FunctionNode> functions = new ArrayList<>();
    functions.add(new AstFunction("Name", 1, new AstParameters(new ArrayList<>())));
    AstNull root = new AstNull();
    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    FunctionMapper fnMapper = mock(FunctionMapper.class);
    when(fnMapper.resolveFunction(Mockito.<String>any(), Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(ELException.class, () -> tree.bind(fnMapper, mock(VariableMapper.class), mock(TypeConverter.class)));
    verify(fnMapper).resolveFunction(eq(""), eq("Name"));
  }

  /**
   * Test {@link Tree#bind(FunctionMapper, VariableMapper)} with {@code fnMapper},
   * {@code varMapper}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tree#bind(FunctionMapper, VariableMapper)}
   */
  @Test
  @DisplayName("Test bind(FunctionMapper, VariableMapper) with 'fnMapper', 'varMapper'; given ELException(String) with pMessage is 'An error occurred'")
  void testBindWithFnMapperVarMapper_givenELExceptionWithPMessageIsAnErrorOccurred() {
    // Arrange
    ArrayList<FunctionNode> functions = new ArrayList<>();
    functions.add(new AstFunction("Name", 1, new AstParameters(new ArrayList<>())));
    AstNull root = new AstNull();
    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    FunctionMapper fnMapper = mock(FunctionMapper.class);
    when(fnMapper.resolveFunction(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> tree.bind(fnMapper, mock(VariableMapper.class)));
    verify(fnMapper).resolveFunction(eq(""), eq("Name"));
  }

  /**
   * Test {@link Tree#bind(FunctionMapper, VariableMapper)} with {@code fnMapper},
   * {@code varMapper}.
   * <ul>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tree#bind(FunctionMapper, VariableMapper)}
   */
  @Test
  @DisplayName("Test bind(FunctionMapper, VariableMapper) with 'fnMapper', 'varMapper'; then throw ELException")
  void testBindWithFnMapperVarMapper_thenThrowELException() {
    // Arrange
    ArrayList<FunctionNode> functions = new ArrayList<>();
    functions.add(new AstFunction("Name", 1, new AstParameters(new ArrayList<>())));
    AstNull root = new AstNull();
    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    FunctionMapper fnMapper = mock(FunctionMapper.class);
    when(fnMapper.resolveFunction(Mockito.<String>any(), Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(ELException.class, () -> tree.bind(fnMapper, mock(VariableMapper.class)));
    verify(fnMapper).resolveFunction(eq(""), eq("Name"));
  }

  /**
   * Test {@link Tree#bind(FunctionMapper, VariableMapper)} with {@code fnMapper},
   * {@code varMapper}.
   * <ul>
   *   <li>When {@link FunctionMapper}.</li>
   *   <li>Then return not FunctionBound is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Tree#bind(FunctionMapper, VariableMapper)}
   */
  @Test
  @DisplayName("Test bind(FunctionMapper, VariableMapper) with 'fnMapper', 'varMapper'; when FunctionMapper; then return not FunctionBound is one")
  void testBindWithFnMapperVarMapper_whenFunctionMapper_thenReturnNotFunctionBoundIsOne() {
    // Arrange
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    // Act
    Bindings actualBindResult = (new Tree(root, functions, new ArrayList<>(), true)).bind(mock(FunctionMapper.class),
        mock(VariableMapper.class));

    // Assert
    assertFalse(actualBindResult.isFunctionBound(1));
    assertFalse(actualBindResult.isVariableBound(1));
  }
}
