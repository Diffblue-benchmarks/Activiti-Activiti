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

class ProcessVariablesMappingDiffblueTest {
  /**
   * Test {@link ProcessVariablesMapping#getInputMapping(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesMapping#getInputMapping(String)}
   */
  @Test
  @DisplayName("Test getInputMapping(String); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testGetInputMapping_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Mapping> inputs = new HashMap<>();
    inputs.computeIfPresent("foo", mock(BiFunction.class));

    ProcessVariablesMapping processVariablesMapping = new ProcessVariablesMapping();
    processVariablesMapping.setInputs(inputs);

    // Act and Assert
    assertNull(processVariablesMapping.getInputMapping("Input Name"));
  }

  /**
   * Test {@link ProcessVariablesMapping#getInputMapping(String)}.
   * <ul>
   *   <li>Given {@link ProcessVariablesMapping} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariablesMapping#getInputMapping(String)}
   */
  @Test
  @DisplayName("Test getInputMapping(String); given ProcessVariablesMapping (default constructor)")
  void testGetInputMapping_givenProcessVariablesMapping() {
    // Arrange, Act and Assert
    assertNull((new ProcessVariablesMapping()).getInputMapping("Input Name"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ProcessVariablesMapping}
   *   <li>{@link ProcessVariablesMapping#setInputs(Map)}
   *   <li>
   * {@link ProcessVariablesMapping#setMappingType(ProcessVariablesMapping.MappingType)}
   *   <li>{@link ProcessVariablesMapping#setOutputs(Map)}
   *   <li>{@link ProcessVariablesMapping#getInputs()}
   *   <li>{@link ProcessVariablesMapping#getMappingType()}
   *   <li>{@link ProcessVariablesMapping#getOutputs()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ProcessVariablesMapping actualProcessVariablesMapping = new ProcessVariablesMapping();
    HashMap<String, Mapping> inputs = new HashMap<>();
    actualProcessVariablesMapping.setInputs(inputs);
    actualProcessVariablesMapping.setMappingType(ProcessVariablesMapping.MappingType.MAP_ALL);
    HashMap<String, Mapping> outputs = new HashMap<>();
    actualProcessVariablesMapping.setOutputs(outputs);
    Map<String, Mapping> actualInputs = actualProcessVariablesMapping.getInputs();
    ProcessVariablesMapping.MappingType actualMappingType = actualProcessVariablesMapping.getMappingType();
    Map<String, Mapping> actualOutputs = actualProcessVariablesMapping.getOutputs();

    // Assert that nothing has changed
    assertEquals(ProcessVariablesMapping.MappingType.MAP_ALL, actualMappingType);
    assertTrue(actualInputs.isEmpty());
    assertTrue(actualOutputs.isEmpty());
    assertSame(inputs, actualInputs);
    assertSame(outputs, actualOutputs);
  }
}
