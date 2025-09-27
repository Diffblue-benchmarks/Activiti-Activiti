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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.core.el.juel.tree.impl.Cache;
import org.activiti.core.el.juel.tree.impl.ast.AstNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TreeStoreDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TreeStore#TreeStore(TreeBuilder, TreeCache)}
   *   <li>{@link TreeStore#getBuilder()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TreeStore.<init>(TreeBuilder, TreeCache)",
    "TreeBuilder TreeStore.getBuilder()"
  })
  void testGettersAndSetters() {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);

    // Act
    TreeStore actualTreeStore = new TreeStore(builder, new Cache(3));

    // Assert
    assertSame(builder, actualTreeStore.getBuilder());
  }

  /**
   * Test {@link TreeStore#get(String)}.
   *
   * <p>Method under test: {@link TreeStore#get(String)}
   */
  @Test
  @DisplayName("Test get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tree TreeStore.get(String)"})
  void testGet() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore treeStore = new TreeStore(builder, new Cache(3));

    // Act
    Tree actualGetResult = treeStore.get("Expression");

    // Assert
    verify(builder).build("Expression");
    assertSame(tree, actualGetResult);
  }

  /**
   * Test {@link TreeStore#get(String)}.
   *
   * <p>Method under test: {@link TreeStore#get(String)}
   */
  @Test
  @DisplayName("Test get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tree TreeStore.get(String)"})
  void testGet2() throws TreeBuilderException {
    // Arrange
    Cache cache = new Cache(3);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    cache.put("Expression", tree);
    TreeStore treeStore = new TreeStore(mock(TreeBuilder.class), cache);

    // Act and Assert
    assertSame(tree, treeStore.get("Expression"));
  }

  /**
   * Test {@link TreeStore#get(String)}.
   *
   * <ul>
   *   <li>Given {@link TreeStore#TreeStore(TreeBuilder, TreeCache)} with builder is {@link
   *       TreeBuilder} and cache is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TreeStore#get(String)}
   */
  @Test
  @DisplayName(
      "Test get(String); given TreeStore(TreeBuilder, TreeCache) with builder is TreeBuilder and cache is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tree TreeStore.get(String)"})
  void testGet_givenTreeStoreWithBuilderIsTreeBuilderAndCacheIsNull() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore treeStore = new TreeStore(builder, null);

    // Act
    Tree actualGetResult = treeStore.get("Expression");

    // Assert
    verify(builder).build("Expression");
    assertSame(tree, actualGetResult);
  }

  /**
   * Test {@link TreeStore#get(String)}.
   *
   * <ul>
   *   <li>Then throw {@link TreeBuilderException}.
   * </ul>
   *
   * <p>Method under test: {@link TreeStore#get(String)}
   */
  @Test
  @DisplayName("Test get(String); then throw TreeBuilderException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tree TreeStore.get(String)"})
  void testGet_thenThrowTreeBuilderException() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    TreeBuilderException treeBuilderException =
        new TreeBuilderException("Expression", 1, "3", "Expected", "An error occurred");
    when(builder.build(Mockito.<String>any())).thenThrow(treeBuilderException);
    TreeStore treeStore = new TreeStore(builder, new Cache(3));

    // Act and Assert
    assertThrows(TreeBuilderException.class, () -> treeStore.get("Expression"));
    verify(builder).build("Expression");
  }

  /**
   * Test {@link TreeStore#get(String)}.
   *
   * <ul>
   *   <li>Then throw {@link TreeBuilderException}.
   * </ul>
   *
   * <p>Method under test: {@link TreeStore#get(String)}
   */
  @Test
  @DisplayName("Test get(String); then throw TreeBuilderException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Tree TreeStore.get(String)"})
  void testGet_thenThrowTreeBuilderException2() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    TreeBuilderException treeBuilderException =
        new TreeBuilderException("Expression", 1, "3", "Expected", "An error occurred");
    when(builder.build(Mockito.<String>any())).thenThrow(treeBuilderException);
    TreeStore treeStore = new TreeStore(builder, null);

    // Act and Assert
    assertThrows(TreeBuilderException.class, () -> treeStore.get("Expression"));
    verify(builder).build("Expression");
  }
}
