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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.runtime.events.ProcessCancelledEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.runtime.api.event.impl.ToProcessCancelledConverter;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessCancelledListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProcessCancelledListenerDelegate#ProcessCancelledListenerDelegate(List,
   *       ToProcessCancelledConverter)}
   *   <li>{@link ProcessCancelledListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessCancelledListenerDelegate.<init>(List, ToProcessCancelledConverter)",
    "boolean ProcessCancelledListenerDelegate.isFailOnException()"
  })
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> listeners = new ArrayList<>();

    // Act
    ProcessCancelledListenerDelegate actualProcessCancelledListenerDelegate =
        new ProcessCancelledListenerDelegate(
            listeners, new ToProcessCancelledConverter(new APIProcessInstanceConverter()));

    // Assert
    assertFalse(actualProcessCancelledListenerDelegate.isFailOnException());
  }

  /**
   * Test {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link ProcessRuntimeEventListener} {@link
   *       ProcessRuntimeEventListener#onEvent(RuntimeEvent)} does nothing.
   *   <li>Then calls {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(ActivitiEvent); given ProcessRuntimeEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessCancelledListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenProcessRuntimeEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCancelledEvent> processRuntimeEventListener =
        mock(ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCancelledEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);
    ProcessCancelledListenerDelegate processCancelledListenerDelegate =
        new ProcessCancelledListenerDelegate(
            listeners, new ToProcessCancelledConverter(new APIProcessInstanceConverter()));

    ExecutionEntityImpl processInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    processInstance.setEnded(false);
    processInstance.setStartTime(null);

    ActivitiProcessCancelledEventImpl event =
        new ActivitiProcessCancelledEventImpl(processInstance);
    event.setCause(null);

    // Act
    processCancelledListenerDelegate.onEvent(event);

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCancelledEvent.class));
  }

  /**
   * Test {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When createWithEmptyRelationshipCollections Ended is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessCancelledListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(ActivitiEvent); given 'true'; when createWithEmptyRelationshipCollections Ended is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessCancelledListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenTrue_whenCreateWithEmptyRelationshipCollectionsEndedIsTrue() {
    // Arrange
    ProcessRuntimeEventListener<ProcessCancelledEvent> processRuntimeEventListener =
        mock(ProcessRuntimeEventListener.class);
    doNothing().when(processRuntimeEventListener).onEvent(Mockito.<ProcessCancelledEvent>any());

    ArrayList<ProcessRuntimeEventListener<ProcessCancelledEvent>> listeners = new ArrayList<>();
    listeners.add(processRuntimeEventListener);
    ProcessCancelledListenerDelegate processCancelledListenerDelegate =
        new ProcessCancelledListenerDelegate(
            listeners, new ToProcessCancelledConverter(new APIProcessInstanceConverter()));

    ExecutionEntityImpl processInstance =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    processInstance.setEnded(true);
    processInstance.setStartTime(null);

    ActivitiProcessCancelledEventImpl event =
        new ActivitiProcessCancelledEventImpl(processInstance);
    event.setCause(null);

    // Act
    processCancelledListenerDelegate.onEvent(event);

    // Assert
    verify(processRuntimeEventListener).onEvent(isA(ProcessCancelledEvent.class));
  }
}
