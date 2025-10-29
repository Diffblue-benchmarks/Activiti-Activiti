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
package org.activiti.runtime.api.event.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.api.process.runtime.events.ProcessCancelledEvent;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiProcessCancelledEvent;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.junit.jupiter.api.Test;

class ToProcessCancelledConverterDiffblueTest {
  /**
   * Method under test:
   * {@link ToProcessCancelledConverter#from(ActivitiProcessCancelledEvent)}
   */
  @Test
  void testFrom() {
    // Arrange
    ToProcessCancelledConverter toProcessCancelledConverter = new ToProcessCancelledConverter(
        new APIProcessInstanceConverter());

    // Act
    Optional<ProcessCancelledEvent> actualFromResult = toProcessCancelledConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    ProcessCancelledEvent getResult = actualFromResult.get();
    ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessCancelledImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(entity.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(entity.getAppVersion());
    assertNull(entity.getBusinessKey());
    assertNull(entity.getId());
    assertNull(entity.getInitiator());
    assertNull(entity.getName());
    assertNull(entity.getParentId());
    assertNull(entity.getProcessDefinitionId());
    assertNull(entity.getProcessDefinitionKey());
    assertNull(entity.getProcessDefinitionName());
    assertNull(getResult.getCause());
    assertNull(entity.getCompletedDate());
    assertNull(entity.getStartDate());
    assertEquals(ProcessInstance.ProcessInstanceStatus.CREATED, entity.getStatus());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_CANCELLED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }

  /**
   * Method under test:
   * {@link ToProcessCancelledConverter#from(ActivitiProcessCancelledEvent)}
   */
  @Test
  void testFrom2() {
    // Arrange
    ToProcessCancelledConverter toProcessCancelledConverter = new ToProcessCancelledConverter(
        new APIProcessInstanceConverter());
    org.activiti.engine.runtime.ProcessInstance processInstance = mock(
        org.activiti.engine.runtime.ProcessInstance.class);
    when(processInstance.isSuspended()).thenReturn(true);
    when(processInstance.getAppVersion()).thenReturn(1);
    when(processInstance.getProcessDefinitionVersion()).thenReturn(1);
    when(processInstance.getId()).thenReturn("42");
    when(processInstance.getParentProcessInstanceId()).thenReturn("42");
    when(processInstance.getBusinessKey()).thenReturn("Business Key");
    when(processInstance.getName()).thenReturn("Name");
    when(processInstance.getProcessDefinitionId()).thenReturn("42");
    when(processInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(processInstance.getProcessDefinitionName()).thenReturn("Process Definition Name");
    when(processInstance.getStartUserId()).thenReturn("42");
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(processInstance.getStartTime()).thenReturn(fromResult);

    // Act
    Optional<ProcessCancelledEvent> actualFromResult = toProcessCancelledConverter
        .from(new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    verify(processInstance).getId();
    verify(processInstance).getParentProcessInstanceId();
    verify(processInstance).getAppVersion();
    verify(processInstance).getBusinessKey();
    verify(processInstance).getName();
    verify(processInstance).getProcessDefinitionId();
    verify(processInstance, atLeast(1)).getProcessDefinitionKey();
    verify(processInstance).getProcessDefinitionName();
    verify(processInstance, atLeast(1)).getProcessDefinitionVersion();
    verify(processInstance).getStartTime();
    verify(processInstance).getStartUserId();
    verify(processInstance).isSuspended();
    ProcessCancelledEvent getResult = actualFromResult.get();
    org.activiti.api.process.model.ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessCancelledImpl);
    assertEquals("1", entity.getAppVersion());
    assertEquals("42", entity.getId());
    assertEquals("42", entity.getInitiator());
    assertEquals("42", entity.getParentId());
    assertEquals("42", entity.getProcessDefinitionId());
    assertEquals("Business Key", entity.getBusinessKey());
    assertEquals("Name", entity.getName());
    assertEquals("Process Definition Key", entity.getProcessDefinitionKey());
    assertEquals("Process Definition Name", entity.getProcessDefinitionName());
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(getResult.getCause());
    assertNull(entity.getCompletedDate());
    assertEquals(1, entity.getProcessDefinitionVersion().intValue());
    assertEquals(org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus.SUSPENDED, entity.getStatus());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_CANCELLED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(fromResult, entity.getStartDate());
  }

  /**
   * Method under test:
   * {@link ToProcessCancelledConverter#from(ActivitiProcessCancelledEvent)}
   */
  @Test
  void testFrom3() {
    // Arrange
    ToProcessCancelledConverter toProcessCancelledConverter = new ToProcessCancelledConverter(
        new APIProcessInstanceConverter());
    org.activiti.engine.runtime.ProcessInstance processInstance = mock(
        org.activiti.engine.runtime.ProcessInstance.class);
    when(processInstance.isEnded()).thenReturn(true);
    when(processInstance.isSuspended()).thenReturn(false);
    when(processInstance.getAppVersion()).thenReturn(1);
    when(processInstance.getProcessDefinitionVersion()).thenReturn(1);
    when(processInstance.getId()).thenReturn("42");
    when(processInstance.getParentProcessInstanceId()).thenReturn("42");
    when(processInstance.getBusinessKey()).thenReturn("Business Key");
    when(processInstance.getName()).thenReturn("Name");
    when(processInstance.getProcessDefinitionId()).thenReturn("42");
    when(processInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(processInstance.getProcessDefinitionName()).thenReturn("Process Definition Name");
    when(processInstance.getStartUserId()).thenReturn("42");
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(processInstance.getStartTime()).thenReturn(fromResult);

    // Act
    Optional<ProcessCancelledEvent> actualFromResult = toProcessCancelledConverter
        .from(new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    verify(processInstance).getId();
    verify(processInstance).getParentProcessInstanceId();
    verify(processInstance).isEnded();
    verify(processInstance).getAppVersion();
    verify(processInstance).getBusinessKey();
    verify(processInstance).getName();
    verify(processInstance).getProcessDefinitionId();
    verify(processInstance, atLeast(1)).getProcessDefinitionKey();
    verify(processInstance).getProcessDefinitionName();
    verify(processInstance, atLeast(1)).getProcessDefinitionVersion();
    verify(processInstance).getStartTime();
    verify(processInstance).getStartUserId();
    verify(processInstance).isSuspended();
    ProcessCancelledEvent getResult = actualFromResult.get();
    org.activiti.api.process.model.ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessCancelledImpl);
    assertEquals("1", entity.getAppVersion());
    assertEquals("42", entity.getId());
    assertEquals("42", entity.getInitiator());
    assertEquals("42", entity.getParentId());
    assertEquals("42", entity.getProcessDefinitionId());
    assertEquals("Business Key", entity.getBusinessKey());
    assertEquals("Name", entity.getName());
    assertEquals("Process Definition Key", entity.getProcessDefinitionKey());
    assertEquals("Process Definition Name", entity.getProcessDefinitionName());
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(getResult.getCause());
    assertNull(entity.getCompletedDate());
    assertEquals(1, entity.getProcessDefinitionVersion().intValue());
    assertEquals(org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus.COMPLETED, entity.getStatus());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_CANCELLED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(fromResult, entity.getStartDate());
  }

  /**
   * Method under test:
   * {@link ToProcessCancelledConverter#from(ActivitiProcessCancelledEvent)}
   */
  @Test
  void testFrom4() {
    // Arrange
    ToProcessCancelledConverter toProcessCancelledConverter = new ToProcessCancelledConverter(
        new APIProcessInstanceConverter());
    org.activiti.engine.runtime.ProcessInstance processInstance = mock(
        org.activiti.engine.runtime.ProcessInstance.class);
    when(processInstance.isSuspended()).thenReturn(true);
    when(processInstance.getAppVersion()).thenReturn(1);
    when(processInstance.getProcessDefinitionVersion()).thenReturn(1);
    when(processInstance.getId()).thenReturn("42");
    when(processInstance.getParentProcessInstanceId()).thenReturn("42");
    when(processInstance.getBusinessKey()).thenReturn("Business Key");
    when(processInstance.getName()).thenReturn("Name");
    when(processInstance.getProcessDefinitionId()).thenReturn("42");
    when(processInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(processInstance.getProcessDefinitionName()).thenReturn("Process Definition Name");
    when(processInstance.getStartUserId()).thenReturn("42");
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(processInstance.getStartTime()).thenReturn(fromResult);

    ActivitiProcessCancelledEventImpl internalEvent = new ActivitiProcessCancelledEventImpl(processInstance);
    internalEvent.setCause("Cause");

    // Act
    Optional<ProcessCancelledEvent> actualFromResult = toProcessCancelledConverter.from(internalEvent);

    // Assert
    verify(processInstance).getId();
    verify(processInstance).getParentProcessInstanceId();
    verify(processInstance).getAppVersion();
    verify(processInstance).getBusinessKey();
    verify(processInstance).getName();
    verify(processInstance).getProcessDefinitionId();
    verify(processInstance, atLeast(1)).getProcessDefinitionKey();
    verify(processInstance).getProcessDefinitionName();
    verify(processInstance, atLeast(1)).getProcessDefinitionVersion();
    verify(processInstance).getStartTime();
    verify(processInstance).getStartUserId();
    verify(processInstance).isSuspended();
    ProcessCancelledEvent getResult = actualFromResult.get();
    org.activiti.api.process.model.ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessCancelledImpl);
    assertEquals("1", entity.getAppVersion());
    assertEquals("42", entity.getId());
    assertEquals("42", entity.getInitiator());
    assertEquals("42", entity.getParentId());
    assertEquals("42", entity.getProcessDefinitionId());
    assertEquals("Business Key", entity.getBusinessKey());
    assertEquals("Cause", getResult.getCause());
    assertEquals("Name", entity.getName());
    assertEquals("Process Definition Key", entity.getProcessDefinitionKey());
    assertEquals("Process Definition Name", entity.getProcessDefinitionName());
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(entity.getCompletedDate());
    assertEquals(1, entity.getProcessDefinitionVersion().intValue());
    assertEquals(org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus.SUSPENDED, entity.getStatus());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_CANCELLED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(fromResult, entity.getStartDate());
  }

  /**
   * Method under test:
   * {@link ToProcessCancelledConverter#from(ActivitiProcessCancelledEvent)}
   */
  @Test
  void testFrom5() {
    // Arrange
    ToProcessCancelledConverter toProcessCancelledConverter = new ToProcessCancelledConverter(
        new APIProcessInstanceConverter());
    org.activiti.engine.runtime.ProcessInstance processInstance = mock(
        org.activiti.engine.runtime.ProcessInstance.class);
    when(processInstance.isEnded()).thenReturn(false);
    when(processInstance.isSuspended()).thenReturn(false);
    when(processInstance.getAppVersion()).thenReturn(1);
    when(processInstance.getProcessDefinitionVersion()).thenReturn(1);
    when(processInstance.getId()).thenReturn("42");
    when(processInstance.getParentProcessInstanceId()).thenReturn("42");
    when(processInstance.getBusinessKey()).thenReturn("Business Key");
    when(processInstance.getName()).thenReturn("Name");
    when(processInstance.getProcessDefinitionId()).thenReturn("42");
    when(processInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(processInstance.getProcessDefinitionName()).thenReturn("Process Definition Name");
    when(processInstance.getStartUserId()).thenReturn("42");
    Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    when(processInstance.getStartTime()).thenReturn(fromResult);

    // Act
    Optional<ProcessCancelledEvent> actualFromResult = toProcessCancelledConverter
        .from(new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    verify(processInstance).getId();
    verify(processInstance).getParentProcessInstanceId();
    verify(processInstance).isEnded();
    verify(processInstance).getAppVersion();
    verify(processInstance).getBusinessKey();
    verify(processInstance).getName();
    verify(processInstance).getProcessDefinitionId();
    verify(processInstance, atLeast(1)).getProcessDefinitionKey();
    verify(processInstance).getProcessDefinitionName();
    verify(processInstance, atLeast(1)).getProcessDefinitionVersion();
    verify(processInstance, atLeast(1)).getStartTime();
    verify(processInstance).getStartUserId();
    verify(processInstance).isSuspended();
    ProcessCancelledEvent getResult = actualFromResult.get();
    org.activiti.api.process.model.ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessCancelledImpl);
    assertEquals("1", entity.getAppVersion());
    assertEquals("42", entity.getId());
    assertEquals("42", entity.getInitiator());
    assertEquals("42", entity.getParentId());
    assertEquals("42", entity.getProcessDefinitionId());
    assertEquals("Business Key", entity.getBusinessKey());
    assertEquals("Name", entity.getName());
    assertEquals("Process Definition Key", entity.getProcessDefinitionKey());
    assertEquals("Process Definition Name", entity.getProcessDefinitionName());
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(getResult.getCause());
    assertNull(entity.getCompletedDate());
    assertEquals(1, entity.getProcessDefinitionVersion().intValue());
    assertEquals(org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus.RUNNING, entity.getStatus());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_CANCELLED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(fromResult, entity.getStartDate());
  }
}
