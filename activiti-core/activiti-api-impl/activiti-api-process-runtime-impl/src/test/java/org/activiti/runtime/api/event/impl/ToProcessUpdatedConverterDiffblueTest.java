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
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import java.util.Optional;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus;
import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.api.process.model.events.ProcessRuntimeEvent.ProcessEvents;
import org.activiti.api.process.runtime.events.ProcessUpdatedEvent;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
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

@ContextConfiguration(classes = {ToProcessUpdatedConverter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ToProcessUpdatedConverterDiffblueTest {
  @MockBean private APIProcessInstanceConverter aPIProcessInstanceConverter;

  @Autowired private ToProcessUpdatedConverter toProcessUpdatedConverter;

  /**
   * Test {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <p>Method under test: {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToProcessUpdatedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent() {
    // Arrange, Act and Assert
    assertFalse(
        toProcessUpdatedConverter
            .from(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED))
            .isPresent());
  }

  /**
   * Test {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <p>Method under test: {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToProcessUpdatedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent2() {
    // Arrange
    ToProcessUpdatedConverter toProcessUpdatedConverter =
        new ToProcessUpdatedConverter(new APIProcessInstanceConverter());

    ExecutionEntityImpl processInstance = mock(ExecutionEntityImpl.class);
    when(processInstance.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(processInstance.isProcessInstanceType()).thenReturn(true);

    ActivitiProcessCancelledEventImpl internalEvent =
        new ActivitiProcessCancelledEventImpl(processInstance);
    internalEvent.setCause("Cause");

    // Act
    Optional<ProcessUpdatedEvent> actualFromResult = toProcessUpdatedConverter.from(internalEvent);

    // Assert
    verify(processInstance).getProcessInstance();
    verify(processInstance).isProcessInstanceType();
    ProcessUpdatedEvent getResult = actualFromResult.get();
    ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessUpdatedEventImpl);
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
   * Test {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <p>Method under test: {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToProcessUpdatedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent3() {
    // Arrange
    ToProcessUpdatedConverter toProcessUpdatedConverter =
        new ToProcessUpdatedConverter(mock(APIProcessInstanceConverter.class));

    ExecutionEntityImpl processInstance = mock(ExecutionEntityImpl.class);
    when(processInstance.isProcessInstanceType()).thenReturn(false);

    ActivitiProcessCancelledEventImpl internalEvent =
        new ActivitiProcessCancelledEventImpl(processInstance);
    internalEvent.setCause("Cause");

    // Act
    Optional<ProcessUpdatedEvent> actualFromResult = toProcessUpdatedConverter.from(internalEvent);

    // Assert
    verify(processInstance).isProcessInstanceType();
    assertFalse(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then calls {@link ActivitiEntityEvent#getEntity()}.
   * </ul>
   *
   * <p>Method under test: {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'null'; then calls getEntity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToProcessUpdatedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenNull_thenCallsGetEntity() {
    // Arrange
    ActivitiEntityEvent internalEvent = mock(ActivitiEntityEvent.class);
    when(internalEvent.getEntity()).thenReturn(null);

    // Act
    Optional<ProcessUpdatedEvent> actualFromResult = toProcessUpdatedConverter.from(internalEvent);

    // Assert
    verify(internalEvent).getEntity();
    assertFalse(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <ul>
   *   <li>Then return {@link Optional#get()} Entity AppVersion is {@code 1}.
   * </ul>
   *
   * <p>Method under test: {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; then return get() Entity AppVersion is '1'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToProcessUpdatedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_thenReturnGetEntityAppVersionIs1() {
    // Arrange
    ToProcessUpdatedConverter toProcessUpdatedConverter =
        new ToProcessUpdatedConverter(new APIProcessInstanceConverter());

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

    ExecutionEntityImpl processInstance = mock(ExecutionEntityImpl.class);
    when(processInstance.getProcessInstance()).thenReturn(executionEntityImpl);
    when(processInstance.isProcessInstanceType()).thenReturn(true);

    ActivitiProcessCancelledEventImpl internalEvent =
        new ActivitiProcessCancelledEventImpl(processInstance);
    internalEvent.setCause("Cause");

    // Act
    Optional<ProcessUpdatedEvent> actualFromResult = toProcessUpdatedConverter.from(internalEvent);

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
    verify(processInstance).getProcessInstance();
    verify(executionEntityImpl).getStartTime();
    verify(executionEntityImpl).getStartUserId();
    verify(processInstance).isProcessInstanceType();
    verify(executionEntityImpl).isSuspended();
    ProcessUpdatedEvent getResult = actualFromResult.get();
    ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessUpdatedEventImpl);
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

  /**
   * Test {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <ul>
   *   <li>Then return {@link Optional#get()} ProcessDefinitionVersion is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; then return get() ProcessDefinitionVersion is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToProcessUpdatedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_thenReturnGetProcessDefinitionVersionIsNull() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(
            Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    Optional<ProcessUpdatedEvent> actualFromResult =
        toProcessUpdatedConverter.from(
            new ActivitiProcessCancelledEventImpl(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    verify(aPIProcessInstanceConverter)
        .from((org.activiti.engine.runtime.ProcessInstance) isNull());
    ProcessUpdatedEvent getResult = actualFromResult.get();
    ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessUpdatedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ProcessEvents.PROCESS_UPDATED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(processInstanceImpl, entity);
  }
}
