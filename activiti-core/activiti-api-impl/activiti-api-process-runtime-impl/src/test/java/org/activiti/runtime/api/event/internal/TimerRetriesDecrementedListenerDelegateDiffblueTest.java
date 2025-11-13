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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.api.process.model.events.BPMNTimerRetriesDecrementedEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.runtime.api.event.impl.BPMNTimerConverter;
import org.activiti.runtime.api.event.impl.ToTimerRetriesDecrementedConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TimerRetriesDecrementedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       TimerRetriesDecrementedListenerDelegate#TimerRetriesDecrementedListenerDelegate(List,
   *       ToTimerRetriesDecrementedConverter)}
   *   <li>{@link TimerRetriesDecrementedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TimerRetriesDecrementedListenerDelegate.<init>(List, ToTimerRetriesDecrementedConverter)",
    "boolean TimerRetriesDecrementedListenerDelegate.isFailOnException()"
  })
  void testGettersAndSetters() {
    // Arrange
    ArrayList<BPMNElementEventListener<BPMNTimerRetriesDecrementedEvent>>
        processRuntimeEventListeners = new ArrayList<>();

    // Act
    TimerRetriesDecrementedListenerDelegate actualTimerRetriesDecrementedListenerDelegate =
        new TimerRetriesDecrementedListenerDelegate(
            processRuntimeEventListeners,
            new ToTimerRetriesDecrementedConverter(new BPMNTimerConverter()));

    // Assert
    assertFalse(actualTimerRetriesDecrementedListenerDelegate.isFailOnException());
  }

  /**
   * Test {@link TimerRetriesDecrementedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link BPMNElementEventListener} {@link
   *       BPMNElementEventListener#onEvent(RuntimeEvent)} does nothing.
   *   <li>Then calls {@link BPMNElementEventListener#onEvent(RuntimeEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link TimerRetriesDecrementedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(ActivitiEvent); given BPMNElementEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TimerRetriesDecrementedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenBPMNElementEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    BPMNElementEventListener<BPMNTimerRetriesDecrementedEvent> bpmnElementEventListener =
        mock(BPMNElementEventListener.class);
    doNothing()
        .when(bpmnElementEventListener)
        .onEvent(Mockito.<BPMNTimerRetriesDecrementedEvent>any());

    ArrayList<BPMNElementEventListener<BPMNTimerRetriesDecrementedEvent>>
        processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(bpmnElementEventListener);
    TimerRetriesDecrementedListenerDelegate timerRetriesDecrementedListenerDelegate =
        new TimerRetriesDecrementedListenerDelegate(
            processRuntimeEventListeners,
            new ToTimerRetriesDecrementedConverter(new BPMNTimerConverter()));

    DeadLetterJobEntityImpl deadLetterJobEntityImpl = new DeadLetterJobEntityImpl();
    deadLetterJobEntityImpl.setDeleted(true);
    deadLetterJobEntityImpl.setDuedate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl.setEndDate(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    deadLetterJobEntityImpl.setExceptionMessage("An error occurred");
    deadLetterJobEntityImpl.setExclusive(true);
    deadLetterJobEntityImpl.setExecutionId("42");
    deadLetterJobEntityImpl.setId("42");
    deadLetterJobEntityImpl.setInserted(true);
    deadLetterJobEntityImpl.setJobHandlerConfiguration("Job Handler Configuration");
    deadLetterJobEntityImpl.setJobHandlerType("Job Handler Type");
    deadLetterJobEntityImpl.setMaxIterations(3);
    deadLetterJobEntityImpl.setProcessDefinitionId("42");
    deadLetterJobEntityImpl.setProcessInstanceId("42");
    deadLetterJobEntityImpl.setRepeat("Repeat");
    deadLetterJobEntityImpl.setRetries(1);
    deadLetterJobEntityImpl.setRevision(1);
    deadLetterJobEntityImpl.setTenantId("42");
    deadLetterJobEntityImpl.setUpdated(true);
    deadLetterJobEntityImpl.setJobType("timer");

    // Act
    timerRetriesDecrementedListenerDelegate.onEvent(
        new ActivitiEntityEventImpl(deadLetterJobEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(bpmnElementEventListener).onEvent(isA(BPMNTimerRetriesDecrementedEvent.class));
  }
}
