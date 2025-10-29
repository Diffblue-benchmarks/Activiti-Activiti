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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
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
import org.junit.jupiter.api.Test;

class ProcessPayloadBuilderDiffblueTest {
  /**
   * Method under test: {@link ProcessPayloadBuilder#start()}
   */
  @Test
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
   * Method under test: {@link ProcessPayloadBuilder#start(StartProcessPayload)}
   */
  @Test
  void testStart2() {
    // Arrange, Act and Assert
    StartProcessPayload buildResult = ProcessPayloadBuilder.start(new StartProcessPayload()).build();
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getName());
    assertNull(buildResult.getProcessDefinitionId());
    assertNull(buildResult.getProcessDefinitionKey());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#start(StartProcessPayload)}
   */
  @Test
  void testStart3() {
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
    Map<String, Object> variables2 = buildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#create()}
   */
  @Test
  void testCreate() {
    // Arrange, Act and Assert
    CreateProcessInstancePayload buildResult = ProcessPayloadBuilder.create().build();
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getName());
    assertNull(buildResult.getProcessDefinitionId());
    assertNull(buildResult.getProcessDefinitionKey());
  }

  /**
   * Method under test:
   * {@link ProcessPayloadBuilder#create(CreateProcessInstancePayload)}
   */
  @Test
  void testCreate2() {
    // Arrange, Act and Assert
    CreateProcessInstancePayload buildResult = ProcessPayloadBuilder.create(new CreateProcessInstancePayload()).build();
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getName());
    assertNull(buildResult.getProcessDefinitionId());
    assertNull(buildResult.getProcessDefinitionKey());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#delete()}
   */
  @Test
  void testDelete() {
    // Arrange, Act and Assert
    DeleteProcessPayload buildResult = ProcessPayloadBuilder.delete().build();
    assertNull(buildResult.getProcessInstanceId());
    assertNull(buildResult.getReason());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#delete(String)}
   */
  @Test
  void testDelete2() {
    // Arrange and Act
    DeleteProcessPayload actualDeleteResult = ProcessPayloadBuilder.delete("42");

    // Assert
    assertEquals("42", actualDeleteResult.getProcessInstanceId());
    assertNull(actualDeleteResult.getReason());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#delete(ProcessInstance)}
   */
  @Test
  void testDelete3() {
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
   * Method under test: {@link ProcessPayloadBuilder#suspend()}
   */
  @Test
  void testSuspend() {
    // Arrange, Act and Assert
    SuspendProcessPayload buildResult = ProcessPayloadBuilder.suspend().build();
    assertNull(buildResult.getId());
    assertNull(buildResult.getProcessInstanceId());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#suspend(String)}
   */
  @Test
  void testSuspend2() {
    // Arrange and Act
    SuspendProcessPayload actualSuspendResult = ProcessPayloadBuilder.suspend("42");

    // Assert
    assertEquals("42", actualSuspendResult.getProcessInstanceId());
    assertNull(actualSuspendResult.getId());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#suspend(ProcessInstance)}
   */
  @Test
  void testSuspend3() {
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
   * Method under test: {@link ProcessPayloadBuilder#resume()}
   */
  @Test
  void testResume() {
    // Arrange, Act and Assert
    ResumeProcessPayload buildResult = ProcessPayloadBuilder.resume().build();
    assertNull(buildResult.getId());
    assertNull(buildResult.getProcessInstanceId());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#resume(String)}
   */
  @Test
  void testResume2() {
    // Arrange and Act
    ResumeProcessPayload actualResumeResult = ProcessPayloadBuilder.resume("42");

    // Assert
    assertEquals("42", actualResumeResult.getProcessInstanceId());
    assertNull(actualResumeResult.getId());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#resume(ProcessInstance)}
   */
  @Test
  void testResume3() {
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
   * Method under test: {@link ProcessPayloadBuilder#update()}
   */
  @Test
  void testUpdate() {
    // Arrange, Act and Assert
    UpdateProcessPayload buildResult = ProcessPayloadBuilder.update().build();
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getDescription());
    assertNull(buildResult.getName());
    assertNull(buildResult.getProcessInstanceId());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#variables()}
   */
  @Test
  void testVariables() {
    // Arrange, Act and Assert
    assertNull(ProcessPayloadBuilder.variables().build().getProcessInstanceId());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#setVariables()}
   */
  @Test
  void testSetVariables() {
    // Arrange, Act and Assert
    SetProcessVariablesPayload buildResult = ProcessPayloadBuilder.setVariables().build();
    assertNull(buildResult.getProcessInstanceId());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#setVariables(String)}
   */
  @Test
  void testSetVariables2() {
    // Arrange, Act and Assert
    SetProcessVariablesPayload buildResult = ProcessPayloadBuilder.setVariables("42").build();
    assertEquals("42", buildResult.getProcessInstanceId());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link ProcessPayloadBuilder#setVariables(ProcessInstance)}
   */
  @Test
  void testSetVariables3() {
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
   * Method under test: {@link ProcessPayloadBuilder#removeVariables()}
   */
  @Test
  void testRemoveVariables() {
    // Arrange, Act and Assert
    RemoveProcessVariablesPayload buildResult = ProcessPayloadBuilder.removeVariables().build();
    assertNull(buildResult.getProcessInstanceId());
    assertTrue(buildResult.getVariableNames().isEmpty());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#signal()}
   */
  @Test
  void testSignal() {
    // Arrange, Act and Assert
    SignalPayload buildResult = ProcessPayloadBuilder.signal().build();
    assertNull(buildResult.getName());
    assertNull(buildResult.getVariables());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#processDefinitions()}
   */
  @Test
  void testProcessDefinitions() {
    // Arrange, Act and Assert
    GetProcessDefinitionsPayload buildResult = ProcessPayloadBuilder.processDefinitions().build();
    assertNull(buildResult.getProcessDefinitionId());
    assertFalse(buildResult.hasDefinitionKeys());
    assertTrue(buildResult.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Method under test: {@link ProcessPayloadBuilder#processInstances()}
   */
  @Test
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
   * Method under test: {@link ProcessPayloadBuilder#subprocesses(String)}
   */
  @Test
  void testSubprocesses() {
    // Arrange and Act
    GetProcessInstancesPayload actualSubprocessesResult = ProcessPayloadBuilder.subprocesses("42");

    // Assert
    assertEquals("42", actualSubprocessesResult.getParentProcessInstanceId());
    assertNull(actualSubprocessesResult.getBusinessKey());
    assertFalse(actualSubprocessesResult.isActiveOnly());
    assertFalse(actualSubprocessesResult.isSuspendedOnly());
    assertTrue(actualSubprocessesResult.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Method under test:
   * {@link ProcessPayloadBuilder#subprocesses(ProcessInstance)}
   */
  @Test
  void testSubprocesses2() {
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
}
