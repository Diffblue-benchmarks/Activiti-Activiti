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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.spring.process.model.ProcessVariablesMapping.MappingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessVariablesMappingDiffblueTest {
  /**
   * Test {@link ProcessVariablesMapping#getInputMapping(String)}.
   * <p>
   * Method under test: {@link ProcessVariablesMapping#getInputMapping(String)}
   */
  @Test
  @DisplayName("Test getInputMapping(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Mapping ProcessVariablesMapping.getInputMapping(String)"})
  void testGetInputMapping() {
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
   *   <li>{@link ProcessVariablesMapping#setMappingType(MappingType)}
   *   <li>{@link ProcessVariablesMapping#setOutputs(Map)}
   *   <li>{@link ProcessVariablesMapping#getInputs()}
   *   <li>{@link ProcessVariablesMapping#getMappingType()}
   *   <li>{@link ProcessVariablesMapping#getOutputs()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessVariablesMapping.<init>()", "Map ProcessVariablesMapping.getInputs()",
      "MappingType ProcessVariablesMapping.getMappingType()", "Map ProcessVariablesMapping.getOutputs()",
      "void ProcessVariablesMapping.setInputs(Map)", "void ProcessVariablesMapping.setMappingType(MappingType)",
      "void ProcessVariablesMapping.setOutputs(Map)"})
  void testGettersAndSetters() {
    // Arrange and Act
    ProcessVariablesMapping actualProcessVariablesMapping = new ProcessVariablesMapping();
    HashMap<String, Mapping> inputs = new HashMap<>();
    actualProcessVariablesMapping.setInputs(inputs);
    actualProcessVariablesMapping.setMappingType(MappingType.MAP_ALL);
    HashMap<String, Mapping> outputs = new HashMap<>();
    actualProcessVariablesMapping.setOutputs(outputs);
    Map<String, Mapping> actualInputs = actualProcessVariablesMapping.getInputs();
    MappingType actualMappingType = actualProcessVariablesMapping.getMappingType();
    Map<String, Mapping> actualOutputs = actualProcessVariablesMapping.getOutputs();

    // Assert
    assertEquals(MappingType.MAP_ALL, actualMappingType);
    assertTrue(actualInputs.isEmpty());
    assertTrue(actualOutputs.isEmpty());
    assertSame(inputs, actualInputs);
    assertSame(outputs, actualOutputs);
  }
}
