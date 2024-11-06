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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessVariablesMapDiffblueTest {
  /**
   * Test {@link ProcessVariablesMap#clone()}.
   * <ul>
   *   <li>Given {@link ProcessVariablesMap} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesMap#clone()}
   */
  @Test
  @DisplayName("Test clone(); given ProcessVariablesMap (default constructor)")
  void testClone_givenProcessVariablesMap() {
    // Arrange
    ProcessVariablesMap<Object, Object> objectObjectMap = new ProcessVariablesMap<>();

    // Act and Assert
    assertEquals(objectObjectMap, objectObjectMap.clone());
  }

  /**
   * Test {@link ProcessVariablesMap#clone()}.
   * <ul>
   *   <li>Given {@link ProcessVariablesMap} (default constructor) computeIfPresent
   * {@code 42} and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesMap#clone()}
   */
  @Test
  @DisplayName("Test clone(); given ProcessVariablesMap (default constructor) computeIfPresent '42' and BiFunction")
  void testClone_givenProcessVariablesMapComputeIfPresent42AndBiFunction() {
    // Arrange
    ProcessVariablesMap<Object, Object> objectObjectMap = new ProcessVariablesMap<>();
    objectObjectMap.computeIfPresent("42", mock(BiFunction.class));

    // Act and Assert
    assertEquals(objectObjectMap, objectObjectMap.clone());
  }

  /**
   * Test new {@link ProcessVariablesMap} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ProcessVariablesMap}
   */
  @Test
  @DisplayName("Test new ProcessVariablesMap (default constructor)")
  void testNewProcessVariablesMap() {
    // Arrange and Act
    ProcessVariablesMap<Object, Object> actualObjectObjectMap = new ProcessVariablesMap<>();

    // Assert
    assertTrue(actualObjectObjectMap.isEmpty());
  }
}
