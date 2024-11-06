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
package org.activiti.api.process.model.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashSet;
import java.util.Set;
import org.activiti.api.process.model.payloads.GetProcessDefinitionsPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GetProcessDefinitionsPayloadBuilderDiffblueTest {
  /**
   * Test
   * {@link GetProcessDefinitionsPayloadBuilder#withProcessDefinitionKey(String)}.
   * <p>
   * Method under test:
   * {@link GetProcessDefinitionsPayloadBuilder#withProcessDefinitionKey(String)}
   */
  @Test
  @DisplayName("Test withProcessDefinitionKey(String)")
  void testWithProcessDefinitionKey() {
    // Arrange
    GetProcessDefinitionsPayloadBuilder processDefinitionsResult = ProcessPayloadBuilder.processDefinitions();
    processDefinitionsResult.withProcessDefinitionKeys(null);

    // Act
    GetProcessDefinitionsPayloadBuilder actualWithProcessDefinitionKeyResult = processDefinitionsResult
        .withProcessDefinitionKey("Process Definition Key");

    // Assert
    assertTrue(processDefinitionsResult.build().hasDefinitionKeys());
    assertSame(processDefinitionsResult, actualWithProcessDefinitionKeyResult);
  }

  /**
   * Test
   * {@link GetProcessDefinitionsPayloadBuilder#withProcessDefinitionKey(String)}.
   * <ul>
   *   <li>Given processDefinitions.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetProcessDefinitionsPayloadBuilder#withProcessDefinitionKey(String)}
   */
  @Test
  @DisplayName("Test withProcessDefinitionKey(String); given processDefinitions")
  void testWithProcessDefinitionKey_givenProcessDefinitions() {
    // Arrange
    GetProcessDefinitionsPayloadBuilder processDefinitionsResult = ProcessPayloadBuilder.processDefinitions();

    // Act
    GetProcessDefinitionsPayloadBuilder actualWithProcessDefinitionKeyResult = processDefinitionsResult
        .withProcessDefinitionKey("Process Definition Key");

    // Assert
    assertTrue(processDefinitionsResult.build().hasDefinitionKeys());
    assertSame(processDefinitionsResult, actualWithProcessDefinitionKeyResult);
  }

  /**
   * Test {@link GetProcessDefinitionsPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetProcessDefinitionsPayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link GetProcessDefinitionsPayloadBuilder}
   *   <li>
   * {@link GetProcessDefinitionsPayloadBuilder#withProcessDefinitionId(String)}
   *   <li>
   * {@link GetProcessDefinitionsPayloadBuilder#withProcessDefinitionKeys(Set)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange
    GetProcessDefinitionsPayloadBuilder withProcessDefinitionKeyResult = (new GetProcessDefinitionsPayloadBuilder())
        .withProcessDefinitionId("42")
        .withProcessDefinitionKey("Process Definition Key");
    HashSet<String> processDefinitionKeys = new HashSet<>();

    // Act
    GetProcessDefinitionsPayload actualBuildResult = withProcessDefinitionKeyResult
        .withProcessDefinitionKeys(processDefinitionKeys)
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getProcessDefinitionId());
    assertFalse(actualBuildResult.hasDefinitionKeys());
    Set<String> processDefinitionKeys2 = actualBuildResult.getProcessDefinitionKeys();
    assertTrue(processDefinitionKeys2.isEmpty());
    assertSame(processDefinitionKeys, processDefinitionKeys2);
  }
}
