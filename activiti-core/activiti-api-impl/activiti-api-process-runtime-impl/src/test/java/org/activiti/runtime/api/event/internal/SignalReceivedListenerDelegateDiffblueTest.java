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
import java.util.HashMap;
import java.util.List;
import org.activiti.api.process.model.events.BPMNSignalReceivedEvent;
import org.activiti.api.process.runtime.events.listener.BPMNElementEventListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiSignalEventImpl;
import org.activiti.runtime.api.event.impl.ToSignalReceivedConverter;
import org.activiti.runtime.api.model.impl.ToSignalConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SignalReceivedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link SignalReceivedListenerDelegate#SignalReceivedListenerDelegate(List,
   *       ToSignalReceivedConverter)}
   *   <li>{@link SignalReceivedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalReceivedListenerDelegate.<init>(List, ToSignalReceivedConverter)",
    "boolean SignalReceivedListenerDelegate.isFailOnException()"
  })
  void testGettersAndSetters() {
    // Arrange
    ArrayList<BPMNElementEventListener<BPMNSignalReceivedEvent>> processRuntimeEventListeners =
        new ArrayList<>();

    // Act
    SignalReceivedListenerDelegate actualSignalReceivedListenerDelegate =
        new SignalReceivedListenerDelegate(
            processRuntimeEventListeners, new ToSignalReceivedConverter(new ToSignalConverter()));

    // Assert
    assertFalse(actualSignalReceivedListenerDelegate.isFailOnException());
  }

  /**
   * Test {@link SignalReceivedListenerDelegate#onEvent(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link BPMNElementEventListener} {@link
   *       BPMNElementEventListener#onEvent(RuntimeEvent)} does nothing.
   *   <li>Then calls {@link BPMNElementEventListener#onEvent(RuntimeEvent)}.
   * </ul>
   *
   * <p>Method under test: {@link SignalReceivedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName(
      "Test onEvent(ActivitiEvent); given BPMNElementEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SignalReceivedListenerDelegate.onEvent(ActivitiEvent)"})
  void testOnEvent_givenBPMNElementEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    BPMNElementEventListener<BPMNSignalReceivedEvent> bpmnElementEventListener =
        mock(BPMNElementEventListener.class);
    doNothing().when(bpmnElementEventListener).onEvent(Mockito.<BPMNSignalReceivedEvent>any());

    ArrayList<BPMNElementEventListener<BPMNSignalReceivedEvent>> processRuntimeEventListeners =
        new ArrayList<>();
    processRuntimeEventListeners.add(bpmnElementEventListener);
    SignalReceivedListenerDelegate signalReceivedListenerDelegate =
        new SignalReceivedListenerDelegate(
            processRuntimeEventListeners, new ToSignalReceivedConverter(new ToSignalConverter()));

    ActivitiSignalEventImpl event = new ActivitiSignalEventImpl(ActivitiEventType.ENTITY_CREATED);
    event.setSignalData(new HashMap<>());

    // Act
    signalReceivedListenerDelegate.onEvent(event);

    // Assert
    verify(bpmnElementEventListener).onEvent(isA(BPMNSignalReceivedEvent.class));
  }
}
