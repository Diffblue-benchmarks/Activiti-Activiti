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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.activiti.core.el.juel.tree.impl.Cache;
import org.activiti.core.el.juel.tree.impl.ast.AstNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TreeStoreDiffblueTest {
  /**
   * Method under test: {@link TreeStore#get(String)}
   */
  @Test
  void testGet() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    Tree tree = new Tree(root, functions, new ArrayList<>(), true);

    when(builder.build(Mockito.<String>any())).thenReturn(tree);

    // Act
    Tree actualGetResult = (new TreeStore(builder, new Cache(3))).get("Expression");

    // Assert
    verify(builder).build(eq("Expression"));
    assertSame(tree, actualGetResult);
  }

  /**
   * Method under test: {@link TreeStore#get(String)}
   */
  @Test
  void testGet2() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    Tree tree = new Tree(root, functions, new ArrayList<>(), true);

    when(builder.build(Mockito.<String>any())).thenReturn(tree);

    // Act
    Tree actualGetResult = (new TreeStore(builder, null)).get("Expression");

    // Assert
    verify(builder).build(eq("Expression"));
    assertSame(tree, actualGetResult);
  }

  /**
   * Method under test: {@link TreeStore#get(String)}
   */
  @Test
  void testGet3() throws TreeBuilderException {
    // Arrange
    Cache cache = new Cache(3);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    Tree tree = new Tree(root, functions, new ArrayList<>(), true);

    cache.put("Expression", tree);

    // Act and Assert
    assertSame(tree, (new TreeStore(mock(TreeBuilder.class), cache)).get("Expression"));
  }

  /**
   * Method under test: {@link TreeStore#get(String)}
   */
  @Test
  void testGet4() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    when(builder.build(Mockito.<String>any()))
        .thenThrow(new TreeBuilderException("Expression", 1, "3", "Expected", "An error occurred"));

    // Act and Assert
    assertThrows(TreeBuilderException.class, () -> (new TreeStore(builder, new Cache(3))).get("Expression"));
    verify(builder).build(eq("Expression"));
  }

  /**
   * Method under test: {@link TreeStore#get(String)}
   */
  @Test
  void testGet5() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    when(builder.build(Mockito.<String>any()))
        .thenThrow(new TreeBuilderException("Expression", 1, "3", "Expected", "An error occurred"));

    // Act and Assert
    assertThrows(TreeBuilderException.class, () -> (new TreeStore(builder, null)).get("Expression"));
    verify(builder).build(eq("Expression"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TreeStore#TreeStore(TreeBuilder, TreeCache)}
   *   <li>{@link TreeStore#getBuilder()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);

    // Act and Assert
    assertSame(builder, (new TreeStore(builder, new Cache(3))).getBuilder());
  }
}
