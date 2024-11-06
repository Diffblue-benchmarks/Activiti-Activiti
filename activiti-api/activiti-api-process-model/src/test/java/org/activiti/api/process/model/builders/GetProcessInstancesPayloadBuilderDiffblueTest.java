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
import org.activiti.api.process.model.payloads.GetProcessInstancesPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GetProcessInstancesPayloadBuilderDiffblueTest {
  /**
   * Test
   * {@link GetProcessInstancesPayloadBuilder#withProcessDefinitionKey(String)}.
   * <ul>
   *   <li>Given processInstances withProcessDefinitionKeys {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetProcessInstancesPayloadBuilder#withProcessDefinitionKey(String)}
   */
  @Test
  @DisplayName("Test withProcessDefinitionKey(String); given processInstances withProcessDefinitionKeys 'null'")
  void testWithProcessDefinitionKey_givenProcessInstancesWithProcessDefinitionKeysNull() {
    // Arrange
    GetProcessInstancesPayloadBuilder processInstancesResult = ProcessPayloadBuilder.processInstances();
    processInstancesResult.withProcessDefinitionKeys(null);

    // Act and Assert
    assertSame(processInstancesResult, processInstancesResult.withProcessDefinitionKey("Process Definition Key"));
  }

  /**
   * Test
   * {@link GetProcessInstancesPayloadBuilder#withProcessDefinitionKey(String)}.
   * <ul>
   *   <li>Given processInstances.</li>
   *   <li>Then return processInstances.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetProcessInstancesPayloadBuilder#withProcessDefinitionKey(String)}
   */
  @Test
  @DisplayName("Test withProcessDefinitionKey(String); given processInstances; then return processInstances")
  void testWithProcessDefinitionKey_givenProcessInstances_thenReturnProcessInstances() {
    // Arrange
    GetProcessInstancesPayloadBuilder processInstancesResult = ProcessPayloadBuilder.processInstances();

    // Act and Assert
    assertSame(processInstancesResult, processInstancesResult.withProcessDefinitionKey("Process Definition Key"));
  }

  /**
   * Test {@link GetProcessInstancesPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetProcessInstancesPayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link GetProcessInstancesPayloadBuilder}
   *   <li>{@link GetProcessInstancesPayloadBuilder#withBusinessKey(String)}
   *   <li>
   * {@link GetProcessInstancesPayloadBuilder#withParentProcessInstanceId(String)}
   *   <li>{@link GetProcessInstancesPayloadBuilder#withProcessDefinitionKeys(Set)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange
    GetProcessInstancesPayloadBuilder withProcessDefinitionKeyResult = (new GetProcessInstancesPayloadBuilder())
        .withBusinessKey("Business Key")
        .withParentProcessInstanceId("42")
        .withProcessDefinitionKey("Process Definition Key");
    HashSet<String> processDefinitionKeys = new HashSet<>();

    // Act
    GetProcessInstancesPayload actualBuildResult = withProcessDefinitionKeyResult
        .withProcessDefinitionKeys(processDefinitionKeys)
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getParentProcessInstanceId());
    assertEquals("Business Key", actualBuildResult.getBusinessKey());
    assertFalse(actualBuildResult.isActiveOnly());
    assertFalse(actualBuildResult.isSuspendedOnly());
    Set<String> processDefinitionKeys2 = actualBuildResult.getProcessDefinitionKeys();
    assertTrue(processDefinitionKeys2.isEmpty());
    assertSame(processDefinitionKeys, processDefinitionKeys2);
  }
}
