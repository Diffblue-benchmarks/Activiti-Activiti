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
package org.activiti.core.el.juel.tree.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary.Operator;
import org.activiti.core.el.juel.tree.impl.ast.AstNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CacheDiffblueTest {
  /**
   * Test {@link Cache#Cache(int)}.
   * <p>
   * Method under test: {@link Cache#Cache(int)}
   */
  @Test
  @DisplayName("Test new Cache(int)")
  void testNewCache() {
    // Arrange, Act and Assert
    assertEquals(0, (new Cache(3)).size());
  }

  /**
   * Test {@link Cache#Cache(int, int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return size is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#Cache(int, int)}
   */
  @Test
  @DisplayName("Test new Cache(int, int); when one; then return size is zero")
  void testNewCache_whenOne_thenReturnSizeIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new Cache(3, 1)).size());
  }

  /**
   * Test {@link Cache#size()}.
   * <ul>
   *   <li>Given {@link Cache#Cache(int)} with capacity is three.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#size()}
   */
  @Test
  @DisplayName("Test size(); given Cache(int) with capacity is three; then return zero")
  void testSize_givenCacheWithCapacityIsThree_thenReturnZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new Cache(3)).size());
  }

  /**
   * Test {@link Cache#size()}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#size()}
   */
  @Test
  @DisplayName("Test size(); then return one")
  void testSize_thenReturnOne() {
    // Arrange
    Cache cache = new Cache(3);
    AstNull left = new AstNull();
    AstBinary root = new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class));

    ArrayList<FunctionNode> functions = new ArrayList<>();
    cache.put("Expression", new Tree(root, functions, new ArrayList<>(), true));

    // Act and Assert
    assertEquals(1, cache.size());
  }

  /**
   * Test {@link Cache#get(String)}.
   * <p>
   * Method under test: {@link Cache#get(String)}
   */
  @Test
  @DisplayName("Test get(String)")
  void testGet() {
    // Arrange
    Cache cache = new Cache(3);
    AstNull left = new AstNull();
    AstBinary root = new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class));

    ArrayList<FunctionNode> functions = new ArrayList<>();
    Tree tree = new Tree(root, functions, new ArrayList<>(), true);

    cache.put("Expression", tree);

    // Act and Assert
    assertSame(tree, cache.get("Expression"));
  }

  /**
   * Test {@link Cache#get(String)}.
   * <ul>
   *   <li>Given {@link Cache#Cache(int)} with capacity is three.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#get(String)}
   */
  @Test
  @DisplayName("Test get(String); given Cache(int) with capacity is three; then return 'null'")
  void testGet_givenCacheWithCapacityIsThree_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new Cache(3)).get("Expression"));
  }

  /**
   * Test {@link Cache#put(String, Tree)}.
   * <p>
   * Method under test: {@link Cache#put(String, Tree)}
   */
  @Test
  @DisplayName("Test put(String, Tree)")
  void testPut() {
    // Arrange
    Cache cache = new Cache(3);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    cache.put("Expression", new Tree(root, functions, new ArrayList<>(), true));
    AstNull root2 = new AstNull();
    ArrayList<FunctionNode> functions2 = new ArrayList<>();

    // Act
    cache.put("Expression", new Tree(root2, functions2, new ArrayList<>(), true));

    // Assert
    assertEquals(1, cache.size());
  }

  /**
   * Test {@link Cache#put(String, Tree)}.
   * <ul>
   *   <li>Given {@link Cache#Cache(int)} with capacity is three.</li>
   *   <li>Then {@link Cache#Cache(int)} with capacity is three size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#put(String, Tree)}
   */
  @Test
  @DisplayName("Test put(String, Tree); given Cache(int) with capacity is three; then Cache(int) with capacity is three size is one")
  void testPut_givenCacheWithCapacityIsThree_thenCacheWithCapacityIsThreeSizeIsOne() {
    // Arrange
    Cache cache = new Cache(3);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    // Act
    cache.put("Expression", new Tree(root, functions, new ArrayList<>(), true));

    // Assert
    assertEquals(1, cache.size());
  }

  /**
   * Test {@link Cache#put(String, Tree)}.
   * <ul>
   *   <li>Given {@link Cache#Cache(int)} with capacity is zero.</li>
   *   <li>Then {@link Cache#Cache(int)} with capacity is zero size is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#put(String, Tree)}
   */
  @Test
  @DisplayName("Test put(String, Tree); given Cache(int) with capacity is zero; then Cache(int) with capacity is zero size is zero")
  void testPut_givenCacheWithCapacityIsZero_thenCacheWithCapacityIsZeroSizeIsZero() {
    // Arrange
    Cache cache = new Cache(0);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    // Act
    cache.put("Expression", new Tree(root, functions, new ArrayList<>(), true));

    // Assert
    assertEquals(0, cache.size());
  }

  /**
   * Test {@link Cache#put(String, Tree)}.
   * <ul>
   *   <li>When {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left is
   * {@link AstNull} (default constructor) and right is {@link AstNull} (default
   * constructor) and {@link Operator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Cache#put(String, Tree)}
   */
  @Test
  @DisplayName("Test put(String, Tree); when AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  void testPut_whenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator() {
    // Arrange
    Cache cache = new Cache(3);
    AstNull left = new AstNull();
    AstBinary root = new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class));

    ArrayList<FunctionNode> functions = new ArrayList<>();

    // Act
    cache.put("Expression", new Tree(root, functions, new ArrayList<>(), true));

    // Assert
    assertEquals(1, cache.size());
  }
}
