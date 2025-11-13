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
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.runtime.api.event.impl.ToProcessCompletedConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessCompletedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProcessCompletedListenerDelegate#ProcessCompletedListenerDelegate(List,
   *       ToProcessCompletedConverter)}
   *   <li>{@link ProcessCompletedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessCompletedListenerDelegate.<init>(List, ToProcessCompletedConverter)",
    "boolean ProcessCompletedListenerDelegate.isFailOnException()"
  })
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners =
        new ArrayList<>();

    // Act
    ProcessCompletedListenerDelegate actualProcessCompletedListenerDelegate =
        new ProcessCompletedListenerDelegate(
            processRuntimeEventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()));

    // Assert
    assertFalse(actualProcessCompletedListenerDelegate.isFailOnException());
  }

  /**
   * Test {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener} {@link
   *       ProcessRuntimeEventListener#onEvent(RuntimeEvent)} does nothing.
   *   <li>Then calls {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(ActivitiEvent); given ProcessRuntimeEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessCompletedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenProcessRuntimeEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCompletedEvent> processRuntimeEventListener =
        mock(ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCompletedEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners =
        new ArrayList<>();
    processRuntimeEventListeners.add(processRuntimeEventListener);
    ProcessCompletedListenerDelegate processCompletedListenerDelegate =
        new ProcessCompletedListenerDelegate(
            processRuntimeEventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()));

    ExecutionEntityImpl processInstance = mock(ExecutionEntityImpl.class);
    when(processInstance.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    processCompletedListenerDelegate.onEvent(
        new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCompletedEvent.class));
    verify(processInstance).getProcessInstance();
  }

  /**
   * Test {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessInstance()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessCompletedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); then calls getProcessInstance()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessCompletedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_thenCallsGetProcessInstance() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCompletedEvent>> processRuntimeEventListeners =
        new ArrayList<>();
    ProcessCompletedListenerDelegate processCompletedListenerDelegate =
        new ProcessCompletedListenerDelegate(
            processRuntimeEventListeners,
            new ToProcessCompletedConverter(new APIProcessInstanceConverter()));

    ExecutionEntityImpl processInstance = mock(ExecutionEntityImpl.class);
    when(processInstance.getProcessInstance())
        .thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    processCompletedListenerDelegate.onEvent(
        new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    verify(processInstance).getProcessInstance();
  }
}
