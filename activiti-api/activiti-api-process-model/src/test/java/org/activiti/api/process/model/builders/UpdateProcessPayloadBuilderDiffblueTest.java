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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.payloads.UpdateProcessPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UpdateProcessPayloadBuilderDiffblueTest {
  /**
   * Test
   * {@link UpdateProcessPayloadBuilder#withProcessInstance(ProcessInstance)}.
   * <p>
   * Method under test:
   * {@link UpdateProcessPayloadBuilder#withProcessInstance(ProcessInstance)}
   */
  @Test
  @DisplayName("Test withProcessInstance(ProcessInstance)")
  void testWithProcessInstance() {
    // Arrange
    UpdateProcessPayloadBuilder updateResult = ProcessPayloadBuilder.update();
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getBusinessKey()).thenReturn("Business Key");
    when(processInstance.getId()).thenReturn("42");
    when(processInstance.getName()).thenReturn("Name");

    // Act
    UpdateProcessPayloadBuilder actualWithProcessInstanceResult = updateResult.withProcessInstance(processInstance);

    // Assert
    verify(processInstance).getBusinessKey();
    verify(processInstance).getId();
    verify(processInstance).getName();
    UpdateProcessPayload buildResult = updateResult.build();
    assertEquals("42", buildResult.getProcessInstanceId());
    assertEquals("Business Key", buildResult.getBusinessKey());
    assertEquals("Name", buildResult.getName());
    assertSame(updateResult, actualWithProcessInstanceResult);
  }

  /**
   * Test {@link UpdateProcessPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateProcessPayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link UpdateProcessPayloadBuilder}
   *   <li>{@link UpdateProcessPayloadBuilder#withBusinessKey(String)}
   *   <li>{@link UpdateProcessPayloadBuilder#withDescription(String)}
   *   <li>{@link UpdateProcessPayloadBuilder#withName(String)}
   *   <li>{@link UpdateProcessPayloadBuilder#withProcessInstanceId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange and Act
    UpdateProcessPayload actualBuildResult = (new UpdateProcessPayloadBuilder()).withBusinessKey("Business Key")
        .withDescription("The characteristics of someone or something")
        .withName("Name")
        .withProcessInstanceId("42")
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getProcessInstanceId());
    assertEquals("Business Key", actualBuildResult.getBusinessKey());
    assertEquals("Name", actualBuildResult.getName());
    assertEquals("The characteristics of someone or something", actualBuildResult.getDescription());
  }
}
