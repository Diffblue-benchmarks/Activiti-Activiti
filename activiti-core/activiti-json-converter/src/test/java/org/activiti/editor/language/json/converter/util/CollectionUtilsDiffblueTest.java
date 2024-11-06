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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CollectionUtilsDiffblueTest {
  /**
   * Test {@link CollectionUtils#isEmpty(Collection)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(Collection)}
   */
  @Test
  @DisplayName("Test isEmpty(Collection); given '42'; when ArrayList() add '42'; then return 'false'")
  void testIsEmpty_given42_whenArrayListAdd42_thenReturnFalse() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add("42");

    // Act and Assert
    assertFalse(CollectionUtils.isEmpty(collection));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(Collection)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(Collection)}
   */
  @Test
  @DisplayName("Test isEmpty(Collection); given '42'; when ArrayList() add '42'; then return 'false'")
  void testIsEmpty_given42_whenArrayListAdd42_thenReturnFalse2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add("42");
    collection.add("42");

    // Act and Assert
    assertFalse(CollectionUtils.isEmpty(collection));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(Collection)}
   */
  @Test
  @DisplayName("Test isEmpty(Collection); when ArrayList(); then return 'true'")
  void testIsEmpty_whenArrayList_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isEmpty(new ArrayList<>()));
  }

  /**
   * Test {@link CollectionUtils#isEmpty(Collection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isEmpty(Collection)}
   */
  @Test
  @DisplayName("Test isEmpty(Collection); when 'null'; then return 'true'")
  void testIsEmpty_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtils.isEmpty(null));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Collection)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Collection)}
   */
  @Test
  @DisplayName("Test isNotEmpty(Collection); given '42'; when ArrayList() add '42'; then return 'true'")
  void testIsNotEmpty_given42_whenArrayListAdd42_thenReturnTrue() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add("42");

    // Act and Assert
    assertTrue(CollectionUtils.isNotEmpty(collection));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Collection)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Collection)}
   */
  @Test
  @DisplayName("Test isNotEmpty(Collection); given '42'; when ArrayList() add '42'; then return 'true'")
  void testIsNotEmpty_given42_whenArrayListAdd42_thenReturnTrue2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add("42");
    collection.add("42");

    // Act and Assert
    assertTrue(CollectionUtils.isNotEmpty(collection));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Collection)}
   */
  @Test
  @DisplayName("Test isNotEmpty(Collection); when ArrayList(); then return 'false'")
  void testIsNotEmpty_whenArrayList_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isNotEmpty(new ArrayList<>()));
  }

  /**
   * Test {@link CollectionUtils#isNotEmpty(Collection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtils#isNotEmpty(Collection)}
   */
  @Test
  @DisplayName("Test isNotEmpty(Collection); when 'null'; then return 'false'")
  void testIsNotEmpty_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtils.isNotEmpty(null));
  }
}
