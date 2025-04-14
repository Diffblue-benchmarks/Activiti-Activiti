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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.api.process.model.events.BPMNTimerScheduledEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.runtime.api.event.impl.BPMNTimerConverter;
import org.activiti.runtime.api.event.impl.ToTimerScheduledConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TimerScheduledListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TimerScheduledListenerDelegate#TimerScheduledListenerDelegate(List, ToTimerScheduledConverter)}
   *   <li>{@link TimerScheduledListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TimerScheduledListenerDelegate.<init>(List, ToTimerScheduledConverter)",
      "boolean TimerScheduledListenerDelegate.isFailOnException()"})
  void testGettersAndSetters() {
    // Arrange
    ArrayList<BPMNElementEventListener<BPMNTimerScheduledEvent>> processRuntimeEventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse((new TimerScheduledListenerDelegate(processRuntimeEventListeners,
        new ToTimerScheduledConverter(new BPMNTimerConverter()))).isFailOnException());
  }

  /**
   * Test {@link TimerScheduledListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link BPMNElementEventListener} {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)} does nothing.</li>
   *   <li>Then calls {@link ProcessRuntimeEventListener#onEvent(RuntimeEvent)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerScheduledListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given BPMNElementEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TimerScheduledListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenBPMNElementEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    BPMNElementEventListener<BPMNTimerScheduledEvent> bpmnElementEventListener = mock(BPMNElementEventListener.class);
    doNothing().when(bpmnElementEventListener).onEvent(Mockito.<BPMNTimerScheduledEvent>any());

    ArrayList<BPMNElementEventListener<BPMNTimerScheduledEvent>> processRuntimeEventListeners = new ArrayList<>();
    processRuntimeEventListeners.add(bpmnElementEventListener);
    TimerScheduledListenerDelegate timerScheduledListenerDelegate = new TimerScheduledListenerDelegate(
        processRuntimeEventListeners, new ToTimerScheduledConverter(new BPMNTimerConverter()));

    JobEntityImpl jobEntityImpl = new JobEntityImpl();
    jobEntityImpl.setDeleted(true);
    jobEntityImpl.setDuedate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntityImpl.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntityImpl.setExceptionMessage("An error occurred");
    jobEntityImpl.setExclusive(true);
    jobEntityImpl.setExecutionId("42");
    jobEntityImpl.setId("42");
    jobEntityImpl.setInserted(true);
    jobEntityImpl.setJobHandlerConfiguration("timer");
    jobEntityImpl.setJobHandlerType("timer");
    jobEntityImpl.setJobType("timer");
    jobEntityImpl
        .setLockExpirationTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    jobEntityImpl.setLockOwner("timer");
    jobEntityImpl.setMaxIterations(3);
    jobEntityImpl.setProcessDefinitionId("42");
    jobEntityImpl.setProcessInstanceId("42");
    jobEntityImpl.setRepeat("timer");
    jobEntityImpl.setRetries(1);
    jobEntityImpl.setRevision(1);
    jobEntityImpl.setTenantId("42");
    jobEntityImpl.setUpdated(true);
    jobEntityImpl.setJobType("timer");

    // Act
    timerScheduledListenerDelegate
        .onEvent(new ActivitiEntityEventImpl(jobEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(bpmnElementEventListener).onEvent(isA(BPMNTimerScheduledEvent.class));
  }
}
