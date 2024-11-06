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
package org.activiti.runtime.api.event.internal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.api.process.runtime.events.ProcessCancelledEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.runtime.api.event.impl.ToProcessCancelledConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessCancelledListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessCancelledListenerDelegate#ProcessCancelledListenerDelegate(List, ToProcessCancelledConverter)}
   *   <li>{@link ProcessCancelledListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> listeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new ProcessCancelledListenerDelegate(listeners,
        new ToProcessCancelledConverter(new APIProcessInstanceConverter()))).isFailOnException());
  }

  /**
   * Test {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}.
   * <p>
   * Method under test:
   * {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent)")
  void testOnEvent() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCancelledEvent> processRuntimeEventListener = mock(
        ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCancelledEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);
    ProcessCancelledListenerDelegate processCancelledListenerDelegate = new ProcessCancelledListenerDelegate(listeners,
        new ToProcessCancelledConverter(new APIProcessInstanceConverter()));

    // Act
    processCancelledListenerDelegate
        .onEvent(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCancelledEvent.class));
  }

  /**
   * Test {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@code Cause}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given 'Cause'")
  void testOnEvent_givenCause() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCancelledEvent> processRuntimeEventListener = mock(
        ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCancelledEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);
    ProcessCancelledListenerDelegate processCancelledListenerDelegate = new ProcessCancelledListenerDelegate(listeners,
        new ToProcessCancelledConverter(new APIProcessInstanceConverter()));
    ProcessInstance processInstance = mock(ProcessInstance.class);
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
    when(processInstance.getStartTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    ActivitiProcessCancelledEventImpl event = new ActivitiProcessCancelledEventImpl(processInstance);
    event.setCause("Cause");

    // Act
    processCancelledListenerDelegate.onEvent(event);

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCancelledEvent.class));
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
  }

  /**
   * Test {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>When {@link ProcessInstance} {@link Execution#isEnded()} return
   * {@code false}.</li>
   *   <li>Then calls {@link Execution#isEnded()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given 'false'; when ProcessInstance isEnded() return 'false'; then calls isEnded()")
  void testOnEvent_givenFalse_whenProcessInstanceIsEndedReturnFalse_thenCallsIsEnded() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCancelledEvent> processRuntimeEventListener = mock(
        ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCancelledEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);
    ProcessCancelledListenerDelegate processCancelledListenerDelegate = new ProcessCancelledListenerDelegate(listeners,
        new ToProcessCancelledConverter(new APIProcessInstanceConverter()));
    ProcessInstance processInstance = mock(ProcessInstance.class);
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
    when(processInstance.getStartTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    processCancelledListenerDelegate.onEvent(new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCancelledEvent.class));
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
  }

  /**
   * Test {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>When {@link ProcessInstance} {@link Execution#isEnded()} return
   * {@code true}.</li>
   *   <li>Then calls {@link Execution#isEnded()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given 'false'; when ProcessInstance isEnded() return 'true'; then calls isEnded()")
  void testOnEvent_givenFalse_whenProcessInstanceIsEndedReturnTrue_thenCallsIsEnded() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCancelledEvent> processRuntimeEventListener = mock(
        ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCancelledEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);
    ProcessCancelledListenerDelegate processCancelledListenerDelegate = new ProcessCancelledListenerDelegate(listeners,
        new ToProcessCancelledConverter(new APIProcessInstanceConverter()));
    ProcessInstance processInstance = mock(ProcessInstance.class);
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
    when(processInstance.getStartTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    processCancelledListenerDelegate.onEvent(new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCancelledEvent.class));
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
  }

  /**
   * Test {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link ProcessInstance} {@link ProcessInstance#isSuspended()} return
   * {@code true}.</li>
   *   <li>Then calls {@link Execution#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given 'true'; when ProcessInstance isSuspended() return 'true'; then calls getId()")
  void testOnEvent_givenTrue_whenProcessInstanceIsSuspendedReturnTrue_thenCallsGetId() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCancelledEvent> processRuntimeEventListener = mock(
        ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCancelledEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);
    ProcessCancelledListenerDelegate processCancelledListenerDelegate = new ProcessCancelledListenerDelegate(listeners,
        new ToProcessCancelledConverter(new APIProcessInstanceConverter()));
    ProcessInstance processInstance = mock(ProcessInstance.class);
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
    when(processInstance.getStartTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    processCancelledListenerDelegate.onEvent(new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCancelledEvent.class));
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
  }
}
