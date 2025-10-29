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
package org.activiti.editor.language.json.converter.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;

class CollectionUtilsDiffblueTest {
  /**
   * Method under test: {@link CollectionUtils#isEmpty(Collection)}
   */
  @Test
  void testIsEmpty() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isEmpty(new ArrayList<>()));
    assertTrue(CollectionUtils.isEmpty(null));
  }

  /**
   * Method under test: {@link CollectionUtils#isEmpty(Collection)}
   */
  @Test
  void testIsEmpty2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add("42");

    // Act and Assert
    assertFalse(CollectionUtils.isEmpty(collection));
  }

  /**
   * Method under test: {@link CollectionUtils#isEmpty(Collection)}
   */
  @Test
  void testIsEmpty3() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add("42");
    collection.add("42");

    // Act and Assert
    assertFalse(CollectionUtils.isEmpty(collection));
  }

  /**
   * Method under test: {@link CollectionUtils#isNotEmpty(Collection)}
   */
  @Test
  void testIsNotEmpty() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isNotEmpty(new ArrayList<>()));
    assertFalse(CollectionUtils.isNotEmpty(null));
  }

  /**
   * Method under test: {@link CollectionUtils#isNotEmpty(Collection)}
   */
  @Test
  void testIsNotEmpty2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add("42");

    // Act and Assert
    assertTrue(CollectionUtils.isNotEmpty(collection));
  }

  /**
   * Method under test: {@link CollectionUtils#isNotEmpty(Collection)}
   */
  @Test
  void testIsNotEmpty3() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add("42");
    collection.add("42");

    // Act and Assert
    assertTrue(CollectionUtils.isNotEmpty(collection));
  }
}
