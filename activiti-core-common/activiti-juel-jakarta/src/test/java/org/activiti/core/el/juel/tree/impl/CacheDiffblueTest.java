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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.impl.ast.AstNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CacheDiffblueTest {
  /**
   * Test {@link Cache#Cache(int)}.
   * <p>
   * Method under test: {@link Cache#Cache(int)}
   */
  @Test
  @DisplayName("Test new Cache(int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Cache.<init>(int)"})
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Cache.<init>(int, int)"})
  void testNewCache_whenOne_thenReturnSizeIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new Cache(3, 1)).size());
  }

  /**
   * Test {@link Cache#size()}.
   * <p>
   * Method under test: {@link Cache#size()}
   */
  @Test
  @DisplayName("Test size()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"int Cache.size()"})
  void testSize() {
    // Arrange, Act and Assert
    assertEquals(0, (new Cache(3)).size());
  }

  /**
   * Test {@link Cache#get(String)}.
   * <p>
   * Method under test: {@link Cache#get(String)}
   */
  @Test
  @DisplayName("Test get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Tree Cache.get(String)"})
  void testGet() {
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Cache.put(String, Tree)"})
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

    // Assert that nothing has changed
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Cache.put(String, Tree)"})
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Cache.put(String, Tree)"})
  void testPut_givenCacheWithCapacityIsZero_thenCacheWithCapacityIsZeroSizeIsZero() {
    // Arrange
    Cache cache = new Cache(0);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    // Act
    cache.put("Expression", new Tree(root, functions, new ArrayList<>(), true));

    // Assert that nothing has changed
    assertEquals(0, cache.size());
  }
}
