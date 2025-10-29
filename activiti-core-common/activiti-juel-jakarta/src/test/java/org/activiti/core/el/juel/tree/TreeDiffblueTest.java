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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TreeDiffblueTest {
  /**
   * Method under test: {@link Tree#bind(FunctionMapper, VariableMapper)}
   */
  @Test
  void testBind() {
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

  /**
   * Method under test: {@link Tree#bind(FunctionMapper, VariableMapper)}
   */
  @Test
  void testBind2() {
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
   * Method under test: {@link Tree#bind(FunctionMapper, VariableMapper)}
   */
  @Test
  void testBind3() {
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
   * Method under test:
   * {@link Tree#bind(FunctionMapper, VariableMapper, TypeConverter)}
   */
  @Test
  void testBind4() {
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
   * Method under test:
   * {@link Tree#bind(FunctionMapper, VariableMapper, TypeConverter)}
   */
  @Test
  void testBind5() {
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
   * Method under test:
   * {@link Tree#bind(FunctionMapper, VariableMapper, TypeConverter)}
   */
  @Test
  void testBind6() {
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
}
