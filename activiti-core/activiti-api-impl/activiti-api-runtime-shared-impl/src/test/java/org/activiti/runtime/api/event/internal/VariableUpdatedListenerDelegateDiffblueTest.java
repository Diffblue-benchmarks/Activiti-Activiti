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
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.model.shared.event.VariableUpdatedEvent;
import org.activiti.api.runtime.shared.events.VariableEventListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiVariableUpdatedEventImpl;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.runtime.api.event.impl.ToVariableUpdatedConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VariableUpdatedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link VariableUpdatedListenerDelegate#VariableUpdatedListenerDelegate(List, ToVariableUpdatedConverter, VariableEventFilter)}
   *   <li>{@link VariableUpdatedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ArrayList<VariableEventListener<VariableUpdatedEvent>> listeners = new ArrayList<>();
    ToVariableUpdatedConverter converter = new ToVariableUpdatedConverter();

    // Act and Assert
    assertFalse(
        (new VariableUpdatedListenerDelegate(listeners, converter, new VariableEventFilter())).isFailOnException());
  }

  /**
   * Test {@link VariableUpdatedListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link VariableEventListener}
   * {@link VariableEventListener#onEvent(RuntimeEvent)} does nothing.</li>
   *   <li>Then calls {@link VariableEventListener#onEvent(RuntimeEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableUpdatedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given VariableEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  void testOnEvent_givenVariableEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    VariableEventListener<VariableUpdatedEvent> variableEventListener = mock(VariableEventListener.class);
    doNothing().when(variableEventListener).onEvent(Mockito.<VariableUpdatedEvent>any());

    ArrayList<VariableEventListener<VariableUpdatedEvent>> listeners = new ArrayList<>();
    listeners.add(variableEventListener);
    ToVariableUpdatedConverter converter = new ToVariableUpdatedConverter();
    VariableUpdatedListenerDelegate variableUpdatedListenerDelegate = new VariableUpdatedListenerDelegate(listeners,
        converter, new VariableEventFilter());

    ActivitiVariableUpdatedEventImpl event = new ActivitiVariableUpdatedEventImpl();
    event.setVariableType(new BigDecimalType());
    event.setTaskId("42");

    // Act
    variableUpdatedListenerDelegate.onEvent(event);

    // Assert
    verify(variableEventListener).onEvent(isA(VariableUpdatedEvent.class));
  }
}
