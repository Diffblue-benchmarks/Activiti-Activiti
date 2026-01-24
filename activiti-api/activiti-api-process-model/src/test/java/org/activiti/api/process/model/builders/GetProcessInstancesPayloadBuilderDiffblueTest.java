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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashSet;
import java.util.Set;
import org.activiti.api.process.model.payloads.GetProcessInstancesPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetProcessInstancesPayloadBuilderDiffblueTest {
  /**
   * Test {@link GetProcessInstancesPayloadBuilder#withProcessDefinitionKey(String)}.
   *
   * <ul>
   *   <li>Given processInstances withProcessDefinitionKeys {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * GetProcessInstancesPayloadBuilder#withProcessDefinitionKey(String)}
   */
  @Test
  @DisplayName(
      "Test withProcessDefinitionKey(String); given processInstances withProcessDefinitionKeys 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "GetProcessInstancesPayloadBuilder GetProcessInstancesPayloadBuilder.withProcessDefinitionKey(String)"
  })
  void testWithProcessDefinitionKey_givenProcessInstancesWithProcessDefinitionKeysNull() {
    // Arrange
    GetProcessInstancesPayloadBuilder processInstancesResult =
        ProcessPayloadBuilder.processInstances();
    processInstancesResult.withProcessDefinitionKeys(null);

    // Act
    GetProcessInstancesPayloadBuilder actualWithProcessDefinitionKeyResult =
        processInstancesResult.withProcessDefinitionKey("Process Definition Key");

    // Assert
    assertSame(processInstancesResult, actualWithProcessDefinitionKeyResult);
  }

  /**
   * Test {@link GetProcessInstancesPayloadBuilder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link GetProcessInstancesPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link GetProcessInstancesPayloadBuilder}
   *   <li>{@link GetProcessInstancesPayloadBuilder#withBusinessKey(String)}
   *   <li>{@link GetProcessInstancesPayloadBuilder#withParentProcessInstanceId(String)}
   *   <li>{@link GetProcessInstancesPayloadBuilder#withProcessDefinitionKeys(Set)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void GetProcessInstancesPayloadBuilder.<init>()",
    "GetProcessInstancesPayloadBuilder GetProcessInstancesPayloadBuilder.active()",
    "GetProcessInstancesPayload GetProcessInstancesPayloadBuilder.build()",
    "GetProcessInstancesPayloadBuilder GetProcessInstancesPayloadBuilder.suspended()",
    "GetProcessInstancesPayloadBuilder GetProcessInstancesPayloadBuilder.withBusinessKey(String)",
    "GetProcessInstancesPayloadBuilder GetProcessInstancesPayloadBuilder.withParentProcessInstanceId(String)",
    "GetProcessInstancesPayloadBuilder GetProcessInstancesPayloadBuilder.withProcessDefinitionKeys(Set)"
  })
  void testBuild() {
    // Arrange and Act
    GetProcessInstancesPayloadBuilder actualWithProcessDefinitionKeyResult =
        new GetProcessInstancesPayloadBuilder()
            .withBusinessKey("Business Key")
            .withParentProcessInstanceId("42")
            .withProcessDefinitionKey("Process Definition Key");
    HashSet<String> processDefinitionKeys = new HashSet<>();
    GetProcessInstancesPayload actualGetProcessInstancesPayload =
        actualWithProcessDefinitionKeyResult
            .withProcessDefinitionKeys(processDefinitionKeys)
            .build();

    // Assert
    assertEquals("42", actualGetProcessInstancesPayload.getParentProcessInstanceId());
    assertEquals("Business Key", actualGetProcessInstancesPayload.getBusinessKey());
    assertFalse(actualGetProcessInstancesPayload.isActiveOnly());
    assertFalse(actualGetProcessInstancesPayload.isSuspendedOnly());
    Set<String> processDefinitionKeys2 =
        actualGetProcessInstancesPayload.getProcessDefinitionKeys();
    assertTrue(processDefinitionKeys2.isEmpty());
    assertSame(processDefinitionKeys, processDefinitionKeys2);
  }
}
