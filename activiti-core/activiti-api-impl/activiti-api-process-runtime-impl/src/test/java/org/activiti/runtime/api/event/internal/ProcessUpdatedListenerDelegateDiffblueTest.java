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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.activiti.api.process.runtime.events.ProcessUpdatedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.runtime.api.event.impl.ProcessUpdatedEventImpl;
import org.activiti.runtime.api.event.impl.ToProcessUpdatedConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessUpdatedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProcessUpdatedListenerDelegate#ProcessUpdatedListenerDelegate(List,
   *       ToProcessUpdatedConverter)}
   *   <li>{@link ProcessUpdatedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessUpdatedListenerDelegate.<init>(List, ToProcessUpdatedConverter)",
    "boolean ProcessUpdatedListenerDelegate.isFailOnException()"
  })
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessUpdatedEvent>> listeners = new ArrayList<>();

    // Act
    ProcessUpdatedListenerDelegate actualProcessUpdatedListenerDelegate =
        new ProcessUpdatedListenerDelegate(
            listeners, new ToProcessUpdatedConverter(new APIProcessInstanceConverter()));

    // Assert
    assertFalse(actualProcessUpdatedListenerDelegate.isFailOnException());
  }

  /**
   * Test {@link ProcessUpdatedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener} {@link
   *       ProcessRuntimeEventListener#onEvent(RuntimeEvent)} does nothing.
   *   <li>Then calls {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessUpdatedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(ActivitiEvent); given ProcessRuntimeEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessUpdatedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenProcessRuntimeEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    ProcessRuntimeEventListener<ProcessUpdatedEvent> processRuntimeEventListener =
        mock(ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessUpdatedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessUpdatedEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);

    ToProcessUpdatedConverter processUpdatedConverter = mock(ToProcessUpdatedConverter.class);
    Optional<ProcessUpdatedEvent> ofResult =
        Optional.of(new ProcessUpdatedEventImpl(new ProcessInstanceImpl()));
    when(processUpdatedConverter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);

    ProcessUpdatedListenerDelegate processUpdatedListenerDelegate =
        new ProcessUpdatedListenerDelegate(listeners, processUpdatedConverter);

    // Act
    processUpdatedListenerDelegate.onEvent(
        new ActivitiProcessCancelledEventImpl(mock(ProcessInstance.class)));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessUpdatedEvent.class));
    verify(processUpdatedConverter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Test {@link ProcessUpdatedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessUpdatedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls from(ActivitiEntityEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessUpdatedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_thenCallsFrom() {
    // Arrange
    ToProcessUpdatedConverter processUpdatedConverter = mock(ToProcessUpdatedConverter.class);
    Optional<ProcessUpdatedEvent> ofResult =
        Optional.of(new ProcessUpdatedEventImpl(new ProcessInstanceImpl()));
    when(processUpdatedConverter.from(Mockito.<ActivitiEntityEvent>any())).thenReturn(ofResult);
    ProcessUpdatedListenerDelegate processUpdatedListenerDelegate =
        new ProcessUpdatedListenerDelegate(new ArrayList<>(), processUpdatedConverter);

    // Act
    processUpdatedListenerDelegate.onEvent(
        new ActivitiProcessCancelledEventImpl(mock(ProcessInstance.class)));

    // Assert
    verify(processUpdatedConverter).from(isA(ActivitiEntityEvent.class));
  }

  /**
   * Test {@link ProcessUpdatedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessInstance()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessUpdatedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls getProcessInstance()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessUpdatedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_thenCallsGetProcessInstance() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessUpdatedEvent>> listeners = new ArrayList<>();
    ProcessUpdatedListenerDelegate processUpdatedListenerDelegate =
        new ProcessUpdatedListenerDelegate(
            listeners, new ToProcessUpdatedConverter(new APIProcessInstanceConverter()));

    ExecutionEntityImpl processInstance = mock(ExecutionEntityImpl.class);
    when(processInstance.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    when(processInstance.isProcessInstanceType()).thenReturn(true);

    // Act
    processUpdatedListenerDelegate.onEvent(new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    verify(processInstance).getProcessInstance();
    verify(processInstance).isProcessInstanceType();
  }
}
