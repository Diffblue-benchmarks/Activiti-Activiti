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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessPayloadBuilderDiffblueTest {
  /**
   * Test {@link ProcessPayloadBuilder#start()}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#start()}
   */
  @Test
  @DisplayName("Test start()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StartProcessPayloadBuilder ProcessPayloadBuilder.start()"})
  void testStart() {
    // Arrange, Act and Assert
    StartProcessPayload startProcessPayload = ProcessPayloadBuilder.start().build();
    assertNull(startProcessPayload.getBusinessKey());
    assertNull(startProcessPayload.getName());
    assertNull(startProcessPayload.getProcessDefinitionId());
    assertNull(startProcessPayload.getProcessDefinitionKey());
    assertTrue(startProcessPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#start(StartProcessPayload)} with {@code StartProcessPayload}.
   *
   * <ul>
   *   <li>Then return build BusinessKey is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#start(StartProcessPayload)}
   */
  @Test
  @DisplayName(
      "Test start(StartProcessPayload) with 'StartProcessPayload'; then return build BusinessKey is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"StartProcessPayloadBuilder ProcessPayloadBuilder.start(StartProcessPayload)"})
  void testStartWithStartProcessPayload_thenReturnBuildBusinessKeyIsNull() {
    // Arrange and Act
    StartProcessPayloadBuilder actualStartResult =
        ProcessPayloadBuilder.start(new StartProcessPayload());

    // Assert
    StartProcessPayload startProcessPayload = actualStartResult.build();
    assertNull(startProcessPayload.getBusinessKey());
    assertNull(startProcessPayload.getName());
    assertNull(startProcessPayload.getProcessDefinitionId());
    assertNull(startProcessPayload.getProcessDefinitionKey());
    assertTrue(startProcessPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#create()}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#create()}
   */
  @Test
  @DisplayName("Test create()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CreateProcessPayloadBuilder ProcessPayloadBuilder.create()"})
  void testCreate() {
    // Arrange, Act and Assert
    CreateProcessInstancePayload createProcessInstancePayload =
        ProcessPayloadBuilder.create().build();
    assertNull(createProcessInstancePayload.getBusinessKey());
    assertNull(createProcessInstancePayload.getName());
    assertNull(createProcessInstancePayload.getProcessDefinitionId());
    assertNull(createProcessInstancePayload.getProcessDefinitionKey());
  }

  /**
   * Test {@link ProcessPayloadBuilder#create(CreateProcessInstancePayload)} with {@code
   * CreateProcessInstancePayload}.
   *
   * <ul>
   *   <li>Then return build BusinessKey is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#create(CreateProcessInstancePayload)}
   */
  @Test
  @DisplayName(
      "Test create(CreateProcessInstancePayload) with 'CreateProcessInstancePayload'; then return build BusinessKey is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateProcessPayloadBuilder ProcessPayloadBuilder.create(CreateProcessInstancePayload)"
  })
  void testCreateWithCreateProcessInstancePayload_thenReturnBuildBusinessKeyIsNull() {
    // Arrange and Act
    CreateProcessPayloadBuilder actualCreateResult =
        ProcessPayloadBuilder.create(new CreateProcessInstancePayload());

    // Assert
    CreateProcessInstancePayload createProcessInstancePayload = actualCreateResult.build();
    assertNull(createProcessInstancePayload.getBusinessKey());
    assertNull(createProcessInstancePayload.getName());
    assertNull(createProcessInstancePayload.getProcessDefinitionId());
    assertNull(createProcessInstancePayload.getProcessDefinitionKey());
  }

  /**
   * Test {@link ProcessPayloadBuilder#delete()}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#delete()}
   */
  @Test
  @DisplayName("Test delete()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.DeleteProcessPayloadBuilder ProcessPayloadBuilder.delete()"
  })
  void testDelete() {
    // Arrange, Act and Assert
    DeleteProcessPayload deleteProcessPayload = ProcessPayloadBuilder.delete().build();
    assertNull(deleteProcessPayload.getProcessInstanceId());
    assertNull(deleteProcessPayload.getReason());
  }

  /**
   * Test {@link ProcessPayloadBuilder#delete(ProcessInstance)} with {@code processInstance}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#delete(ProcessInstance)}
   */
  @Test
  @DisplayName("Test delete(ProcessInstance) with 'processInstance'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"DeleteProcessPayload ProcessPayloadBuilder.delete(ProcessInstance)"})
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
   * Test {@link ProcessPayloadBuilder#delete(String)} with {@code processInstanceId}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#delete(String)}
   */
  @Test
  @DisplayName("Test delete(String) with 'processInstanceId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"DeleteProcessPayload ProcessPayloadBuilder.delete(String)"})
  void testDeleteWithProcessInstanceId() {
    // Arrange and Act
    DeleteProcessPayload actualDeleteResult = ProcessPayloadBuilder.delete("42");

    // Assert
    assertEquals("42", actualDeleteResult.getProcessInstanceId());
    assertNull(actualDeleteResult.getReason());
  }

  /**
   * Test {@link ProcessPayloadBuilder#suspend()}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#suspend()}
   */
  @Test
  @DisplayName("Test suspend()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.SuspendProcessPayloadBuilder ProcessPayloadBuilder.suspend()"
  })
  void testSuspend() {
    // Arrange, Act and Assert
    SuspendProcessPayload suspendProcessPayload = ProcessPayloadBuilder.suspend().build();
    assertNull(suspendProcessPayload.getId());
    assertNull(suspendProcessPayload.getProcessInstanceId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#suspend(ProcessInstance)} with {@code processInstance}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#suspend(ProcessInstance)}
   */
  @Test
  @DisplayName("Test suspend(ProcessInstance) with 'processInstance'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"SuspendProcessPayload ProcessPayloadBuilder.suspend(ProcessInstance)"})
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
   * Test {@link ProcessPayloadBuilder#suspend(String)} with {@code processInstanceId}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#suspend(String)}
   */
  @Test
  @DisplayName("Test suspend(String) with 'processInstanceId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"SuspendProcessPayload ProcessPayloadBuilder.suspend(String)"})
  void testSuspendWithProcessInstanceId() {
    // Arrange and Act
    SuspendProcessPayload actualSuspendResult = ProcessPayloadBuilder.suspend("42");

    // Assert
    assertEquals("42", actualSuspendResult.getProcessInstanceId());
    assertNull(actualSuspendResult.getId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#resume()}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#resume()}
   */
  @Test
  @DisplayName("Test resume()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.ResumeProcessPayloadBuilder ProcessPayloadBuilder.resume()"
  })
  void testResume() {
    // Arrange, Act and Assert
    ResumeProcessPayload resumeProcessPayload = ProcessPayloadBuilder.resume().build();
    assertNull(resumeProcessPayload.getId());
    assertNull(resumeProcessPayload.getProcessInstanceId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#resume(ProcessInstance)} with {@code processInstance}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#resume(ProcessInstance)}
   */
  @Test
  @DisplayName("Test resume(ProcessInstance) with 'processInstance'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResumeProcessPayload ProcessPayloadBuilder.resume(ProcessInstance)"})
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
   * Test {@link ProcessPayloadBuilder#resume(String)} with {@code processInstanceId}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#resume(String)}
   */
  @Test
  @DisplayName("Test resume(String) with 'processInstanceId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResumeProcessPayload ProcessPayloadBuilder.resume(String)"})
  void testResumeWithProcessInstanceId() {
    // Arrange and Act
    ResumeProcessPayload actualResumeResult = ProcessPayloadBuilder.resume("42");

    // Assert
    assertEquals("42", actualResumeResult.getProcessInstanceId());
    assertNull(actualResumeResult.getId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#update()}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#update()}
   */
  @Test
  @DisplayName("Test update()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.UpdateProcessPayloadBuilder ProcessPayloadBuilder.update()"
  })
  void testUpdate() {
    // Arrange, Act and Assert
    UpdateProcessPayload updateProcessPayload = ProcessPayloadBuilder.update().build();
    assertNull(updateProcessPayload.getBusinessKey());
    assertNull(updateProcessPayload.getDescription());
    assertNull(updateProcessPayload.getName());
    assertNull(updateProcessPayload.getProcessInstanceId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#variables()}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#variables()}
   */
  @Test
  @DisplayName("Test variables()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.GetVariablesPayloadBuilder ProcessPayloadBuilder.variables()"
  })
  void testVariables() {
    // Arrange, Act and Assert
    assertNull(ProcessPayloadBuilder.variables().build().getProcessInstanceId());
  }

  /**
   * Test {@link ProcessPayloadBuilder#setVariables()}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#setVariables()}
   */
  @Test
  @DisplayName("Test setVariables()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"SetVariablesPayloadBuilder ProcessPayloadBuilder.setVariables()"})
  void testSetVariables() {
    // Arrange, Act and Assert
    SetProcessVariablesPayload setProcessVariablesPayload =
        ProcessPayloadBuilder.setVariables().build();
    assertNull(setProcessVariablesPayload.getProcessInstanceId());
    assertTrue(setProcessVariablesPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#setVariables(ProcessInstance)} with {@code processInstance}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#setVariables(ProcessInstance)}
   */
  @Test
  @DisplayName("Test setVariables(ProcessInstance) with 'processInstance'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SetVariablesPayloadBuilder ProcessPayloadBuilder.setVariables(ProcessInstance)"
  })
  void testSetVariablesWithProcessInstance() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");

    // Act
    SetVariablesPayloadBuilder actualSetVariablesResult =
        ProcessPayloadBuilder.setVariables(processInstance);

    // Assert
    verify(processInstance).getId();
    SetProcessVariablesPayload setProcessVariablesPayload = actualSetVariablesResult.build();
    assertEquals("42", setProcessVariablesPayload.getProcessInstanceId());
    assertTrue(setProcessVariablesPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#setVariables(String)} with {@code processInstanceId}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#setVariables(String)}
   */
  @Test
  @DisplayName("Test setVariables(String) with 'processInstanceId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"SetVariablesPayloadBuilder ProcessPayloadBuilder.setVariables(String)"})
  void testSetVariablesWithProcessInstanceId() {
    // Arrange, Act and Assert
    SetProcessVariablesPayload setProcessVariablesPayload =
        ProcessPayloadBuilder.setVariables("42").build();
    assertEquals("42", setProcessVariablesPayload.getProcessInstanceId());
    assertTrue(setProcessVariablesPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#removeVariables()}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#removeVariables()}
   */
  @Test
  @DisplayName("Test removeVariables()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.RemoveVariablesPayloadBuilder ProcessPayloadBuilder.removeVariables()"
  })
  void testRemoveVariables() {
    // Arrange, Act and Assert
    RemoveProcessVariablesPayload removeProcessVariablesPayload =
        ProcessPayloadBuilder.removeVariables().build();
    assertNull(removeProcessVariablesPayload.getProcessInstanceId());
    assertTrue(removeProcessVariablesPayload.getVariableNames().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#signal()}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#signal()}
   */
  @Test
  @DisplayName("Test signal()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.SignalPayloadBuilder ProcessPayloadBuilder.signal()"
  })
  void testSignal() {
    // Arrange, Act and Assert
    SignalPayload signalPayload = ProcessPayloadBuilder.signal().build();
    assertNull(signalPayload.getName());
    assertNull(signalPayload.getVariables());
  }

  /**
   * Test {@link ProcessPayloadBuilder#processDefinitions()}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#processDefinitions()}
   */
  @Test
  @DisplayName("Test processDefinitions()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.GetProcessDefinitionsPayloadBuilder ProcessPayloadBuilder.processDefinitions()"
  })
  void testProcessDefinitions() {
    // Arrange, Act and Assert
    GetProcessDefinitionsPayload getProcessDefinitionsPayload =
        ProcessPayloadBuilder.processDefinitions().build();
    assertNull(getProcessDefinitionsPayload.getProcessDefinitionId());
    assertFalse(getProcessDefinitionsPayload.hasDefinitionKeys());
    assertTrue(getProcessDefinitionsPayload.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#processInstances()}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#processInstances()}
   */
  @Test
  @DisplayName("Test processInstances()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.process.model.builders.GetProcessInstancesPayloadBuilder ProcessPayloadBuilder.processInstances()"
  })
  void testProcessInstances() {
    // Arrange, Act and Assert
    GetProcessInstancesPayload getProcessInstancesPayload =
        ProcessPayloadBuilder.processInstances().build();
    assertNull(getProcessInstancesPayload.getBusinessKey());
    assertNull(getProcessInstancesPayload.getParentProcessInstanceId());
    assertFalse(getProcessInstancesPayload.isActiveOnly());
    assertFalse(getProcessInstancesPayload.isSuspendedOnly());
    assertTrue(getProcessInstancesPayload.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#subprocesses(ProcessInstance)} with {@code
   * parentProcessInstance}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#subprocesses(ProcessInstance)}
   */
  @Test
  @DisplayName("Test subprocesses(ProcessInstance) with 'parentProcessInstance'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "GetProcessInstancesPayload ProcessPayloadBuilder.subprocesses(ProcessInstance)"
  })
  void testSubprocessesWithParentProcessInstance() {
    // Arrange
    ProcessInstance parentProcessInstance = mock(ProcessInstance.class);
    when(parentProcessInstance.getId()).thenReturn("42");

    // Act
    GetProcessInstancesPayload actualSubprocessesResult =
        ProcessPayloadBuilder.subprocesses(parentProcessInstance);

    // Assert
    verify(parentProcessInstance).getId();
    assertEquals("42", actualSubprocessesResult.getParentProcessInstanceId());
    assertNull(actualSubprocessesResult.getBusinessKey());
    assertFalse(actualSubprocessesResult.isActiveOnly());
    assertFalse(actualSubprocessesResult.isSuspendedOnly());
    assertTrue(actualSubprocessesResult.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test {@link ProcessPayloadBuilder#subprocesses(String)} with {@code parentProcessInstanceId}.
   *
   * <p>Method under test: {@link ProcessPayloadBuilder#subprocesses(String)}
   */
  @Test
  @DisplayName("Test subprocesses(String) with 'parentProcessInstanceId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"GetProcessInstancesPayload ProcessPayloadBuilder.subprocesses(String)"})
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
