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
import org.activiti.core.el.juel.tree.impl.ast.AstNull;
import org.junit.jupiter.api.Test;

class CacheDiffblueTest {
  /**
   * Method under test: {@link Cache#size()}
   */
  @Test
  void testSize() {
    // Arrange, Act and Assert
    assertEquals(0, (new Cache(3)).size());
  }

  /**
   * Method under test: {@link Cache#size()}
   */
  @Test
  void testSize2() {
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
   * Method under test: {@link Cache#get(String)}
   */
  @Test
  void testGet() {
    // Arrange, Act and Assert
    assertNull((new Cache(3)).get("Expression"));
  }

  /**
   * Method under test: {@link Cache#get(String)}
   */
  @Test
  void testGet2() {
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
   * Method under test: {@link Cache#put(String, Tree)}
   */
  @Test
  void testPut() {
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
   * Method under test: {@link Cache#put(String, Tree)}
   */
  @Test
  void testPut2() {
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
   * Method under test: {@link Cache#put(String, Tree)}
   */
  @Test
  void testPut3() {
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

  /**
   * Method under test: {@link Cache#Cache(int)}
   */
  @Test
  void testNewCache() {
    // Arrange, Act and Assert
    assertEquals(0, (new Cache(3)).size());
    assertEquals(0, (new Cache(3, 1)).size());
  }
}
