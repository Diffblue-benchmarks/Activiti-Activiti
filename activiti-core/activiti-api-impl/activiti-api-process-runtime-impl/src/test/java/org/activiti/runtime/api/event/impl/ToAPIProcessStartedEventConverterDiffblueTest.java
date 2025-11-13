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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus;
import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.api.process.model.events.ProcessRuntimeEvent.ProcessEvents;
import org.activiti.api.process.runtime.events.ProcessStartedEvent;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiProcessStartedEvent;
import org.activiti.engine.delegate.event.impl.ActivitiProcessStartedEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ToAPIProcessStartedEventConverter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ToAPIProcessStartedEventConverterDiffblueTest {
  @MockBean private APIProcessInstanceConverter aPIProcessInstanceConverter;

  @Autowired private ToAPIProcessStartedEventConverter toAPIProcessStartedEventConverter;

  /**
   * Test {@link ToAPIProcessStartedEventConverter#from(ActivitiProcessStartedEvent)} with {@code
   * ActivitiProcessStartedEvent}.
   *
   * <p>Method under test: {@link
   * ToAPIProcessStartedEventConverter#from(ActivitiProcessStartedEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiProcessStartedEvent) with 'ActivitiProcessStartedEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional ToAPIProcessStartedEventConverter.from(ActivitiProcessStartedEvent)"
  })
  void testFromWithActivitiProcessStartedEvent() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(
            Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ActivitiProcessStartedEventImpl internalEvent =
        new ActivitiProcessStartedEventImpl(
            createWithEmptyRelationshipCollectionsResult, new HashMap<>(), true);

    // Act
    Optional<ProcessStartedEvent> actualFromResult =
        toAPIProcessStartedEventConverter.from(internalEvent);

    // Assert
    verify(aPIProcessInstanceConverter)
        .from((org.activiti.engine.runtime.ProcessInstance) isNull());
    ProcessStartedEvent getResult = actualFromResult.get();
    ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessStartedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(getResult.getNestedProcessDefinitionId());
    assertNull(getResult.getNestedProcessInstanceId());
    assertEquals(ProcessEvents.PROCESS_STARTED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(processInstanceImpl, entity);
  }

  /**
   * Test {@link ToAPIProcessStartedEventConverter#from(ActivitiProcessStartedEvent)} with {@code
   * ActivitiProcessStartedEvent}.
   *
   * <p>Method under test: {@link
   * ToAPIProcessStartedEventConverter#from(ActivitiProcessStartedEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiProcessStartedEvent) with 'ActivitiProcessStartedEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional ToAPIProcessStartedEventConverter.from(ActivitiProcessStartedEvent)"
  })
  void testFromWithActivitiProcessStartedEvent2() {
    // Arrange
    ToAPIProcessStartedEventConverter toAPIProcessStartedEventConverter =
        new ToAPIProcessStartedEventConverter(new APIProcessInstanceConverter());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl.isProcessInstanceType()).thenReturn(true);
    ActivitiProcessStartedEventImpl internalEvent =
        new ActivitiProcessStartedEventImpl(executionEntityImpl, new HashMap<>(), true);

    // Act
    Optional<ProcessStartedEvent> actualFromResult =
        toAPIProcessStartedEventConverter.from(internalEvent);

    // Assert
    verify(executionEntityImpl).getProcessInstance();
    verify(executionEntityImpl).getSuperExecution();
    verify(executionEntityImpl).isProcessInstanceType();
    ProcessStartedEvent getResult = actualFromResult.get();
    ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessStartedEventImpl);
    assertNull(entity.getProcessDefinitionVersion());
    assertNull(entity.getAppVersion());
    assertNull(entity.getBusinessKey());
    assertNull(entity.getId());
    assertNull(entity.getInitiator());
    assertNull(entity.getName());
    assertNull(entity.getParentId());
    assertNull(entity.getProcessDefinitionId());
    assertNull(entity.getProcessDefinitionKey());
    assertNull(entity.getProcessDefinitionName());
    assertNull(entity.getStartDate());
    assertEquals(ProcessInstanceStatus.CREATED, entity.getStatus());
  }

  /**
   * Test {@link ToAPIProcessStartedEventConverter#from(ActivitiProcessStartedEvent)} with {@code
   * ActivitiProcessStartedEvent}.
   *
   * <ul>
   *   <li>Then return {@link Optional#get()} Entity AppVersion is {@code 1}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ToAPIProcessStartedEventConverter#from(ActivitiProcessStartedEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiProcessStartedEvent) with 'ActivitiProcessStartedEvent'; then return get() Entity AppVersion is '1'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Optional ToAPIProcessStartedEventConverter.from(ActivitiProcessStartedEvent)"
  })
  void testFromWithActivitiProcessStartedEvent_thenReturnGetEntityAppVersionIs1() {
    // Arrange
    ToAPIProcessStartedEventConverter toAPIProcessStartedEventConverter =
        new ToAPIProcessStartedEventConverter(new APIProcessInstanceConverter());

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.isSuspended()).thenReturn(true);
    when(executionEntityImpl.getAppVersion()).thenReturn(1);
    when(executionEntityImpl.getProcessDefinitionVersion()).thenReturn(1);
    when(executionEntityImpl.getId()).thenReturn("42");
    when(executionEntityImpl.getBusinessKey()).thenReturn("Business Key");
    when(executionEntityImpl.getName()).thenReturn("Name");
    when(executionEntityImpl.getParentProcessInstanceId()).thenReturn("42");
    when(executionEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(executionEntityImpl.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(executionEntityImpl.getProcessDefinitionName()).thenReturn("Process Definition Name");
    when(executionEntityImpl.getStartUserId()).thenReturn("42");
    when(executionEntityImpl.getStartTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    ExecutionEntityImpl executionEntityImpl2 = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl2.getProcessInstance()).thenReturn(executionEntityImpl);
    when(executionEntityImpl2.getSuperExecution())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(executionEntityImpl2.isProcessInstanceType()).thenReturn(true);
    ActivitiProcessStartedEventImpl internalEvent =
        new ActivitiProcessStartedEventImpl(executionEntityImpl2, new HashMap<>(), true);

    // Act
    Optional<ProcessStartedEvent> actualFromResult =
        toAPIProcessStartedEventConverter.from(internalEvent);

    // Assert
    verify(executionEntityImpl).getId();
    verify(executionEntityImpl).getAppVersion();
    verify(executionEntityImpl).getBusinessKey();
    verify(executionEntityImpl).getName();
    verify(executionEntityImpl).getParentProcessInstanceId();
    verify(executionEntityImpl).getProcessDefinitionId();
    verify(executionEntityImpl, atLeast(1)).getProcessDefinitionKey();
    verify(executionEntityImpl).getProcessDefinitionName();
    verify(executionEntityImpl, atLeast(1)).getProcessDefinitionVersion();
    verify(executionEntityImpl2).getProcessInstance();
    verify(executionEntityImpl).getStartTime();
    verify(executionEntityImpl).getStartUserId();
    verify(executionEntityImpl2).getSuperExecution();
    verify(executionEntityImpl2).isProcessInstanceType();
    verify(executionEntityImpl).isSuspended();
    ProcessStartedEvent getResult = actualFromResult.get();
    ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessStartedEventImpl);
    assertEquals("1", entity.getAppVersion());
    assertEquals("42", entity.getId());
    assertEquals("42", entity.getInitiator());
    assertEquals("42", entity.getParentId());
    assertEquals("42", entity.getProcessDefinitionId());
    assertEquals("Business Key", entity.getBusinessKey());
    assertEquals("Name", entity.getName());
    assertEquals("Process Definition Key", entity.getProcessDefinitionKey());
    assertEquals("Process Definition Name", entity.getProcessDefinitionName());
    assertEquals(1, entity.getProcessDefinitionVersion().intValue());
    assertEquals(ProcessInstanceStatus.SUSPENDED, entity.getStatus());
  }
}
