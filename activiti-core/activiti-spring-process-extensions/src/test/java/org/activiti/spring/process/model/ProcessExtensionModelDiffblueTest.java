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
package org.activiti.spring.process.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessExtensionModelDiffblueTest {
  /**
   * Test {@link ProcessExtensionModel#getExtensions(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExtensionModel#getExtensions(String)}
   */
  @Test
  @DisplayName("Test getExtensions(String); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testGetExtensions_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Extension> extensions = new HashMap<>();
    extensions.computeIfPresent("foo", mock(BiFunction.class));

    ProcessExtensionModel processExtensionModel = new ProcessExtensionModel();
    processExtensionModel.setExtensions(extensions);

    // Act and Assert
    assertNull(processExtensionModel.getExtensions("Process Definition Key"));
  }

  /**
   * Test {@link ProcessExtensionModel#getExtensions(String)}.
   * <ul>
   *   <li>Given {@link ProcessExtensionModel} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExtensionModel#getExtensions(String)}
   */
  @Test
  @DisplayName("Test getExtensions(String); given ProcessExtensionModel (default constructor)")
  void testGetExtensions_givenProcessExtensionModel() {
    // Arrange, Act and Assert
    assertNull((new ProcessExtensionModel()).getExtensions("Process Definition Key"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ProcessExtensionModel}
   *   <li>{@link ProcessExtensionModel#setExtensions(Map)}
   *   <li>{@link ProcessExtensionModel#setId(String)}
   *   <li>{@link ProcessExtensionModel#getAllExtensions()}
   *   <li>{@link ProcessExtensionModel#getId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ProcessExtensionModel actualProcessExtensionModel = new ProcessExtensionModel();
    HashMap<String, Extension> extensions = new HashMap<>();
    actualProcessExtensionModel.setExtensions(extensions);
    actualProcessExtensionModel.setId("42");
    Map<String, Extension> actualAllExtensions = actualProcessExtensionModel.getAllExtensions();

    // Assert that nothing has changed
    assertEquals("42", actualProcessExtensionModel.getId());
    assertTrue(actualAllExtensions.isEmpty());
    assertSame(extensions, actualAllExtensions);
  }
}
