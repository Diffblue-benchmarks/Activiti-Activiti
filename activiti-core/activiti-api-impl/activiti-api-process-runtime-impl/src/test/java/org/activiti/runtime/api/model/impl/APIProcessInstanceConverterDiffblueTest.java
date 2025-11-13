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
package org.activiti.runtime.api.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {APIProcessInstanceConverter.class})
@ExtendWith(SpringExtension.class)
class APIProcessInstanceConverterDiffblueTest {
  @Autowired private APIProcessInstanceConverter aPIProcessInstanceConverter;

  /**
   * Test {@link APIProcessInstanceConverter#from(ProcessInstance)} with {@code ProcessInstance}.
   *
   * <ul>
   *   <li>Then return StartDate is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * APIProcessInstanceConverter#from(org.activiti.engine.runtime.ProcessInstance)}
   */
  @Test
  @DisplayName("Test from(ProcessInstance) with 'ProcessInstance'; then return StartDate is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance APIProcessInstanceConverter.from(org.activiti.engine.runtime.ProcessInstance)"
  })
  void testFromWithProcessInstance_thenReturnStartDateIsNull() {
    // Arrange and Act
    ProcessInstance actualFromResult =
        aPIProcessInstanceConverter.from(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualFromResult instanceof ProcessInstanceImpl);
    assertNull(actualFromResult.getProcessDefinitionVersion());
    assertNull(actualFromResult.getAppVersion());
    assertNull(actualFromResult.getBusinessKey());
    assertNull(actualFromResult.getId());
    assertNull(actualFromResult.getInitiator());
    assertNull(actualFromResult.getName());
    assertNull(actualFromResult.getParentId());
    assertNull(actualFromResult.getProcessDefinitionId());
    assertNull(actualFromResult.getProcessDefinitionKey());
    assertNull(actualFromResult.getProcessDefinitionName());
    assertNull(actualFromResult.getStartDate());
    assertEquals(ProcessInstanceStatus.CREATED, actualFromResult.getStatus());
  }

  /**
   * Test {@link APIProcessInstanceConverter#from(ProcessInstance)} with {@code ProcessInstance}.
   *
   * <ul>
   *   <li>Then return Status is {@code COMPLETED}.
   * </ul>
   *
   * <p>Method under test: {@link
   * APIProcessInstanceConverter#from(org.activiti.engine.runtime.ProcessInstance)}
   */
  @Test
  @DisplayName(
      "Test from(ProcessInstance) with 'ProcessInstance'; then return Status is 'COMPLETED'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance APIProcessInstanceConverter.from(org.activiti.engine.runtime.ProcessInstance)"
  })
  void testFromWithProcessInstance_thenReturnStatusIsCompleted() {
    // Arrange
    org.activiti.engine.runtime.ProcessInstance internalProcessInstance =
        mock(org.activiti.engine.runtime.ProcessInstance.class);
    when(internalProcessInstance.isSuspended()).thenReturn(false);
    when(internalProcessInstance.isEnded()).thenReturn(true);
    when(internalProcessInstance.getAppVersion()).thenReturn(1);
    when(internalProcessInstance.getProcessDefinitionVersion()).thenReturn(1);
    when(internalProcessInstance.getId()).thenReturn("42");
    when(internalProcessInstance.getParentProcessInstanceId()).thenReturn("42");
    when(internalProcessInstance.getBusinessKey()).thenReturn("Business Key");
    when(internalProcessInstance.getName()).thenReturn("Name");
    when(internalProcessInstance.getProcessDefinitionId()).thenReturn("42");
    when(internalProcessInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(internalProcessInstance.getProcessDefinitionName()).thenReturn("Process Definition Name");
    when(internalProcessInstance.getStartUserId()).thenReturn("42");
    when(internalProcessInstance.getStartTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    ProcessInstance actualFromResult = aPIProcessInstanceConverter.from(internalProcessInstance);

    // Assert
    verify(internalProcessInstance).getId();
    verify(internalProcessInstance).getParentProcessInstanceId();
    verify(internalProcessInstance).isEnded();
    verify(internalProcessInstance).getAppVersion();
    verify(internalProcessInstance).getBusinessKey();
    verify(internalProcessInstance).getName();
    verify(internalProcessInstance).getProcessDefinitionId();
    verify(internalProcessInstance, atLeast(1)).getProcessDefinitionKey();
    verify(internalProcessInstance).getProcessDefinitionName();
    verify(internalProcessInstance, atLeast(1)).getProcessDefinitionVersion();
    verify(internalProcessInstance).getStartTime();
    verify(internalProcessInstance).getStartUserId();
    verify(internalProcessInstance).isSuspended();
    assertTrue(actualFromResult instanceof ProcessInstanceImpl);
    assertEquals("1", actualFromResult.getAppVersion());
    assertEquals("42", actualFromResult.getId());
    assertEquals("42", actualFromResult.getInitiator());
    assertEquals("42", actualFromResult.getParentId());
    assertEquals("42", actualFromResult.getProcessDefinitionId());
    assertEquals("Business Key", actualFromResult.getBusinessKey());
    assertEquals("Name", actualFromResult.getName());
    assertEquals("Process Definition Key", actualFromResult.getProcessDefinitionKey());
    assertEquals("Process Definition Name", actualFromResult.getProcessDefinitionName());
    assertEquals(1, actualFromResult.getProcessDefinitionVersion().intValue());
    assertEquals(ProcessInstanceStatus.COMPLETED, actualFromResult.getStatus());
  }

  /**
   * Test {@link APIProcessInstanceConverter#from(ProcessInstance)} with {@code ProcessInstance}.
   *
   * <ul>
   *   <li>Then return Status is {@code RUNNING}.
   * </ul>
   *
   * <p>Method under test: {@link
   * APIProcessInstanceConverter#from(org.activiti.engine.runtime.ProcessInstance)}
   */
  @Test
  @DisplayName("Test from(ProcessInstance) with 'ProcessInstance'; then return Status is 'RUNNING'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance APIProcessInstanceConverter.from(org.activiti.engine.runtime.ProcessInstance)"
  })
  void testFromWithProcessInstance_thenReturnStatusIsRunning() {
    // Arrange
    ExecutionEntityImpl internalProcessInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    internalProcessInstance.setEnded(false);
    internalProcessInstance.setStartTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    ProcessInstance actualFromResult = aPIProcessInstanceConverter.from(internalProcessInstance);

    // Assert
    assertTrue(actualFromResult instanceof ProcessInstanceImpl);
    assertNull(actualFromResult.getProcessDefinitionVersion());
    assertNull(actualFromResult.getAppVersion());
    assertNull(actualFromResult.getBusinessKey());
    assertNull(actualFromResult.getId());
    assertNull(actualFromResult.getInitiator());
    assertNull(actualFromResult.getName());
    assertNull(actualFromResult.getParentId());
    assertNull(actualFromResult.getProcessDefinitionId());
    assertNull(actualFromResult.getProcessDefinitionKey());
    assertNull(actualFromResult.getProcessDefinitionName());
    assertEquals(ProcessInstanceStatus.RUNNING, actualFromResult.getStatus());
  }

  /**
   * Test {@link APIProcessInstanceConverter#from(ProcessInstance)} with {@code ProcessInstance}.
   *
   * <ul>
   *   <li>Then return Status is {@code SUSPENDED}.
   * </ul>
   *
   * <p>Method under test: {@link
   * APIProcessInstanceConverter#from(org.activiti.engine.runtime.ProcessInstance)}
   */
  @Test
  @DisplayName(
      "Test from(ProcessInstance) with 'ProcessInstance'; then return Status is 'SUSPENDED'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessInstance APIProcessInstanceConverter.from(org.activiti.engine.runtime.ProcessInstance)"
  })
  void testFromWithProcessInstance_thenReturnStatusIsSuspended() {
    // Arrange
    org.activiti.engine.runtime.ProcessInstance internalProcessInstance =
        mock(org.activiti.engine.runtime.ProcessInstance.class);
    when(internalProcessInstance.isSuspended()).thenReturn(true);
    when(internalProcessInstance.getAppVersion()).thenReturn(1);
    when(internalProcessInstance.getProcessDefinitionVersion()).thenReturn(1);
    when(internalProcessInstance.getId()).thenReturn("42");
    when(internalProcessInstance.getParentProcessInstanceId()).thenReturn("42");
    when(internalProcessInstance.getBusinessKey()).thenReturn("Business Key");
    when(internalProcessInstance.getName()).thenReturn("Name");
    when(internalProcessInstance.getProcessDefinitionId()).thenReturn("42");
    when(internalProcessInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(internalProcessInstance.getProcessDefinitionName()).thenReturn("Process Definition Name");
    when(internalProcessInstance.getStartUserId()).thenReturn("42");
    when(internalProcessInstance.getStartTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    ProcessInstance actualFromResult = aPIProcessInstanceConverter.from(internalProcessInstance);

    // Assert
    verify(internalProcessInstance).getId();
    verify(internalProcessInstance).getParentProcessInstanceId();
    verify(internalProcessInstance).getAppVersion();
    verify(internalProcessInstance).getBusinessKey();
    verify(internalProcessInstance).getName();
    verify(internalProcessInstance).getProcessDefinitionId();
    verify(internalProcessInstance, atLeast(1)).getProcessDefinitionKey();
    verify(internalProcessInstance).getProcessDefinitionName();
    verify(internalProcessInstance, atLeast(1)).getProcessDefinitionVersion();
    verify(internalProcessInstance).getStartTime();
    verify(internalProcessInstance).getStartUserId();
    verify(internalProcessInstance).isSuspended();
    assertTrue(actualFromResult instanceof ProcessInstanceImpl);
    assertEquals("1", actualFromResult.getAppVersion());
    assertEquals("42", actualFromResult.getId());
    assertEquals("42", actualFromResult.getInitiator());
    assertEquals("42", actualFromResult.getParentId());
    assertEquals("42", actualFromResult.getProcessDefinitionId());
    assertEquals("Business Key", actualFromResult.getBusinessKey());
    assertEquals("Name", actualFromResult.getName());
    assertEquals("Process Definition Key", actualFromResult.getProcessDefinitionKey());
    assertEquals("Process Definition Name", actualFromResult.getProcessDefinitionName());
    assertEquals(1, actualFromResult.getProcessDefinitionVersion().intValue());
    assertEquals(ProcessInstanceStatus.SUSPENDED, actualFromResult.getStatus());
  }
}
