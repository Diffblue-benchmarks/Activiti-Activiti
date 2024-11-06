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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.function.BiFunction;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.payloads.CreateProcessInstancePayload;
import org.activiti.api.process.model.payloads.DeleteProcessPayload;
import org.activiti.api.process.model.payloads.GetProcessDefinitionsPayload;
import org.activiti.api.process.model.payloads.GetProcessInstancesPayload;
import org.activiti.api.process.model.payloads.RemoveProcessVariablesPayload;
import org.activiti.api.process.model.payloads.ResumeProcessPayload;
import org.activiti.api.process.model.payloads.SetProcessVariablesPayload;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.model.payloads.SuspendProcessPayload;
import org.activiti.api.process.model.payloads.UpdateProcessPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessPayloadBuilderDiffblueTest {
  /**
   * Test {@link ProcessPayloadBuilder#start()}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#start()}
   */
  @Test
  @DisplayName("Test start()")
  void testStart() {
    // Arrange, Act and Assert
    StartProcessPayload buildResult = ProcessPayloadBuilder.start().build();
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getName());
    assertNull(buildResult.getProcessDefinitionId());
    assertNull(buildResult.getProcessDefinitionKey());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#start(StartProcessPayload)} with
   * {@code StartProcessPayload}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return build ProcessDefinitionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#start(StartProcessPayload)}
   */
  @Test
  @DisplayName("Test start(StartProcessPayload) with 'StartProcessPayload'; given 'foo'; then return build ProcessDefinitionId is '42'")
  void testStartWithStartProcessPayload_givenFoo_thenReturnBuildProcessDefinitionIdIs42() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    StartProcessPayload buildResult = ProcessPayloadBuilder
        .start(new StartProcessPayload("42", "Process Definition Key", "Name", "Business Key", variables))
        .build();
    assertEquals("42", buildResult.getProcessDefinitionId());
    assertEquals("Business Key", buildResult.getBusinessKey());
    assertEquals("Name", buildResult.getName());
    assertEquals("Process Definition Key", buildResult.getProcessDefinitionKey());
  }

  /**
   * Test {@link ProcessPayloadBuilder#start(StartProcessPayload)} with
   * {@code StartProcessPayload}.
   * <ul>
   *   <li>Then return build BusinessKey is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#start(StartProcessPayload)}
   */
  @Test
  @DisplayName("Test start(StartProcessPayload) with 'StartProcessPayload'; then return build BusinessKey is 'null'")
  void testStartWithStartProcessPayload_thenReturnBuildBusinessKeyIsNull() {
    // Arrange, Act and Assert
    StartProcessPayload buildResult = ProcessPayloadBuilder.start(new StartProcessPayload()).build();
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getName());
    assertNull(buildResult.getProcessDefinitionId());
    assertNull(buildResult.getProcessDefinitionKey());
  }

  /**
   * Test {@link ProcessPayloadBuilder#create()}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#create()}
   */
  @Test
  @DisplayName("Test create()")
  void testCreate() {
    // Arrange, Act and Assert
    CreateProcessInstancePayload buildResult = ProcessPayloadBuilder.create().build();
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getName());
    assertNull(buildResult.getProcessDefinitionId());
    assertNull(buildResult.getProcessDefinitionKey());
  }

  /**
   * Test {@link ProcessPayloadBuilder#create(CreateProcessInstancePayload)} with
   * {@code CreateProcessInstancePayload}.
   * <ul>
   *   <li>Then return build BusinessKey is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessPayloadBuilder#create(CreateProcessInstancePayload)}
   */
  @Test
  @DisplayName("Test create(CreateProcessInstancePayload) with 'CreateProcessInstancePayload'; then return build BusinessKey is 'null'")
  void testCreateWithCreateProcessInstancePayload_thenReturnBuildBusinessKeyIsNull() {
    // Arrange, Act and Assert
    CreateProcessInstancePayload buildResult = ProcessPayloadBuilder.create(new CreateProcessInstancePayload()).build();
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getName());
    assertNull(buildResult.getProcessDefinitionId());
    assertNull(buildResult.getProcessDefinitionKey());
  }

  /**
   * Test {@link ProcessPayloadBuilder#delete()}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#delete()}
   */
  @Test
  @DisplayName("Test delete()")
  void testDelete() {
    // Arrange, Act and Assert
    DeleteProcessPayload buildResult = ProcessPayloadBuilder.delete().build();
    assertNull(buildResult.getProcessInstanceId());
    assertNull(buildResult.getReason());
  }

  /**
   * Test {@link ProcessPayloadBuilder#delete(ProcessInstance)} with
   * {@code processInstance}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#delete(ProcessInstance)}
   */
  @Test
  @DisplayName("Test delete(ProcessInstance) with 'processInstance'")
  void testDeleteWithProcessInstance() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");

    // Act
    DeleteProcessPayload actualDeleteResult = ProcessPayloadBuilder.delete(processInstance);

    // Assert
    verify(processInstance).getId();
    assertEquals("42", actualDeleteResult.getProcessInstanceId());
    assertNull(actualDeleteResult.getReason());
  }

  /**
   * Test {@link ProcessPayloadBuilder#delete(String)} with
   * {@code processInstanceId}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#delete(String)}
   */
  @Test
  @DisplayName("Test delete(String) with 'processInstanceId'")
  void testDeleteWithProcessInstanceId() {
    // Arrange and Act
    DeleteProcessPayload actualDeleteResult = ProcessPayloadBuilder.delete("42");

    // Assert
    assertEquals("42", actualDeleteResult.getProcessInstanceId());
    assertNull(actualDeleteResult.getReason());
  }

  /**
   * Test {@link ProcessPayloadBuilder#suspend()}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#suspend()}
   */
  @Test
  @DisplayName("Test suspend()")
  void testSuspend() {
    // Arrange, Act and Assert
    SuspendProcessPayload buildResult = ProcessPayloadBuilder.suspend().build();
    assertNull(buildResult.getId());
    assertNull(buildResult.getProcessInstanceId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#suspend(ProcessInstance)} with
   * {@code processInstance}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#suspend(ProcessInstance)}
   */
  @Test
  @DisplayName("Test suspend(ProcessInstance) with 'processInstance'")
  void testSuspendWithProcessInstance() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");

    // Act
    SuspendProcessPayload actualSuspendResult = ProcessPayloadBuilder.suspend(processInstance);

    // Assert
    verify(processInstance).getId();
    assertEquals("42", actualSuspendResult.getProcessInstanceId());
    assertNull(actualSuspendResult.getId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#suspend(String)} with
   * {@code processInstanceId}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#suspend(String)}
   */
  @Test
  @DisplayName("Test suspend(String) with 'processInstanceId'")
  void testSuspendWithProcessInstanceId() {
    // Arrange and Act
    SuspendProcessPayload actualSuspendResult = ProcessPayloadBuilder.suspend("42");

    // Assert
    assertEquals("42", actualSuspendResult.getProcessInstanceId());
    assertNull(actualSuspendResult.getId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#resume()}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#resume()}
   */
  @Test
  @DisplayName("Test resume()")
  void testResume() {
    // Arrange, Act and Assert
    ResumeProcessPayload buildResult = ProcessPayloadBuilder.resume().build();
    assertNull(buildResult.getId());
    assertNull(buildResult.getProcessInstanceId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#resume(ProcessInstance)} with
   * {@code processInstance}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#resume(ProcessInstance)}
   */
  @Test
  @DisplayName("Test resume(ProcessInstance) with 'processInstance'")
  void testResumeWithProcessInstance() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");

    // Act
    ResumeProcessPayload actualResumeResult = ProcessPayloadBuilder.resume(processInstance);

    // Assert
    verify(processInstance).getId();
    assertEquals("42", actualResumeResult.getProcessInstanceId());
    assertNull(actualResumeResult.getId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#resume(String)} with
   * {@code processInstanceId}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#resume(String)}
   */
  @Test
  @DisplayName("Test resume(String) with 'processInstanceId'")
  void testResumeWithProcessInstanceId() {
    // Arrange and Act
    ResumeProcessPayload actualResumeResult = ProcessPayloadBuilder.resume("42");

    // Assert
    assertEquals("42", actualResumeResult.getProcessInstanceId());
    assertNull(actualResumeResult.getId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#update()}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#update()}
   */
  @Test
  @DisplayName("Test update()")
  void testUpdate() {
    // Arrange, Act and Assert
    UpdateProcessPayload buildResult = ProcessPayloadBuilder.update().build();
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getDescription());
    assertNull(buildResult.getName());
    assertNull(buildResult.getProcessInstanceId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#variables()}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#variables()}
   */
  @Test
  @DisplayName("Test variables()")
  void testVariables() {
    // Arrange, Act and Assert
    assertNull(ProcessPayloadBuilder.variables().build().getProcessInstanceId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#setVariables()}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#setVariables()}
   */
  @Test
  @DisplayName("Test setVariables()")
  void testSetVariables() {
    // Arrange, Act and Assert
    SetProcessVariablesPayload buildResult = ProcessPayloadBuilder.setVariables().build();
    assertNull(buildResult.getProcessInstanceId());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#setVariables(ProcessInstance)} with
   * {@code processInstance}.
   * <p>
   * Method under test:
   * {@link ProcessPayloadBuilder#setVariables(ProcessInstance)}
   */
  @Test
  @DisplayName("Test setVariables(ProcessInstance) with 'processInstance'")
  void testSetVariablesWithProcessInstance() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");

    // Act
    SetVariablesPayloadBuilder actualSetVariablesResult = ProcessPayloadBuilder.setVariables(processInstance);

    // Assert
    verify(processInstance).getId();
    SetProcessVariablesPayload buildResult = actualSetVariablesResult.build();
    assertEquals("42", buildResult.getProcessInstanceId());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#setVariables(String)} with
   * {@code processInstanceId}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#setVariables(String)}
   */
  @Test
  @DisplayName("Test setVariables(String) with 'processInstanceId'")
  void testSetVariablesWithProcessInstanceId() {
    // Arrange, Act and Assert
    SetProcessVariablesPayload buildResult = ProcessPayloadBuilder.setVariables("42").build();
    assertEquals("42", buildResult.getProcessInstanceId());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#removeVariables()}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#removeVariables()}
   */
  @Test
  @DisplayName("Test removeVariables()")
  void testRemoveVariables() {
    // Arrange, Act and Assert
    RemoveProcessVariablesPayload buildResult = ProcessPayloadBuilder.removeVariables().build();
    assertNull(buildResult.getProcessInstanceId());
    assertTrue(buildResult.getVariableNames().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#signal()}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#signal()}
   */
  @Test
  @DisplayName("Test signal()")
  void testSignal() {
    // Arrange, Act and Assert
    SignalPayload buildResult = ProcessPayloadBuilder.signal().build();
    assertNull(buildResult.getName());
    assertNull(buildResult.getVariables());
  }

  /**
   * Test {@link ProcessPayloadBuilder#processDefinitions()}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#processDefinitions()}
   */
  @Test
  @DisplayName("Test processDefinitions()")
  void testProcessDefinitions() {
    // Arrange, Act and Assert
    GetProcessDefinitionsPayload buildResult = ProcessPayloadBuilder.processDefinitions().build();
    assertNull(buildResult.getProcessDefinitionId());
    assertFalse(buildResult.hasDefinitionKeys());
    assertTrue(buildResult.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#processInstances()}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#processInstances()}
   */
  @Test
  @DisplayName("Test processInstances()")
  void testProcessInstances() {
    // Arrange, Act and Assert
    GetProcessInstancesPayload buildResult = ProcessPayloadBuilder.processInstances().build();
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getParentProcessInstanceId());
    assertFalse(buildResult.isActiveOnly());
    assertFalse(buildResult.isSuspendedOnly());
    assertTrue(buildResult.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#subprocesses(ProcessInstance)} with
   * {@code parentProcessInstance}.
   * <p>
   * Method under test:
   * {@link ProcessPayloadBuilder#subprocesses(ProcessInstance)}
   */
  @Test
  @DisplayName("Test subprocesses(ProcessInstance) with 'parentProcessInstance'")
  void testSubprocessesWithParentProcessInstance() {
    // Arrange
    ProcessInstance parentProcessInstance = mock(ProcessInstance.class);
    when(parentProcessInstance.getId()).thenReturn("42");

    // Act
    GetProcessInstancesPayload actualSubprocessesResult = ProcessPayloadBuilder.subprocesses(parentProcessInstance);

    // Assert
    verify(parentProcessInstance).getId();
    assertEquals("42", actualSubprocessesResult.getParentProcessInstanceId());
    assertNull(actualSubprocessesResult.getBusinessKey());
    assertFalse(actualSubprocessesResult.isActiveOnly());
    assertFalse(actualSubprocessesResult.isSuspendedOnly());
    assertTrue(actualSubprocessesResult.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#subprocesses(String)} with
   * {@code parentProcessInstanceId}.
   * <p>
   * Method under test: {@link ProcessPayloadBuilder#subprocesses(String)}
   */
  @Test
  @DisplayName("Test subprocesses(String) with 'parentProcessInstanceId'")
  void testSubprocessesWithParentProcessInstanceId() {
    // Arrange and Act
    GetProcessInstancesPayload actualSubprocessesResult = ProcessPayloadBuilder.subprocesses("42");

    // Assert
    assertEquals("42", actualSubprocessesResult.getParentProcessInstanceId());
    assertNull(actualSubprocessesResult.getBusinessKey());
    assertFalse(actualSubprocessesResult.isActiveOnly());
    assertFalse(actualSubprocessesResult.isSuspendedOnly());
    assertTrue(actualSubprocessesResult.getProcessDefinitionKeys().isEmpty());
  }
}
