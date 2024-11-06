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
import org.activiti.api.model.shared.event.VariableCreatedEvent;
import org.activiti.api.runtime.shared.events.VariableEventListener;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiVariableUpdatedEventImpl;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.activiti.runtime.api.event.impl.ToVariableCreatedConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VariableCreatedListenerDelegateDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link VariableCreatedListenerDelegate#VariableCreatedListenerDelegate(List, ToVariableCreatedConverter, VariableEventFilter)}
   *   <li>{@link VariableCreatedListenerDelegate#isFailOnException()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ArrayList<VariableEventListener<VariableCreatedEvent>> listeners = new ArrayList<>();
    ToVariableCreatedConverter converter = new ToVariableCreatedConverter();

    // Act and Assert
    assertFalse(
        (new VariableCreatedListenerDelegate(listeners, converter, new VariableEventFilter())).isFailOnException());
  }

  /**
   * Test {@link VariableCreatedListenerDelegate#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Given {@link VariableEventListener}
   * {@link VariableEventListener#onEvent(RuntimeEvent)} does nothing.</li>
   *   <li>Then calls {@link VariableEventListener#onEvent(RuntimeEvent)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableCreatedListenerDelegate#onEvent(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test onEvent(ActivitiEvent); given VariableEventListener onEvent(RuntimeEvent) does nothing; then calls onEvent(RuntimeEvent)")
  void testOnEvent_givenVariableEventListenerOnEventDoesNothing_thenCallsOnEvent() {
    // Arrange
    VariableEventListener<VariableCreatedEvent> variableEventListener = mock(VariableEventListener.class);
    doNothing().when(variableEventListener).onEvent(Mockito.<VariableCreatedEvent>any());

    ArrayList<VariableEventListener<VariableCreatedEvent>> listeners = new ArrayList<>();
    listeners.add(variableEventListener);
    ToVariableCreatedConverter converter = new ToVariableCreatedConverter();
    VariableCreatedListenerDelegate variableCreatedListenerDelegate = new VariableCreatedListenerDelegate(listeners,
        converter, new VariableEventFilter());

    ActivitiVariableUpdatedEventImpl event = new ActivitiVariableUpdatedEventImpl();
    event.setVariableType(new BigDecimalType());
    event.setTaskId("42");

    // Act
    variableCreatedListenerDelegate.onEvent(event);

    // Assert
    verify(variableEventListener).onEvent(isA(VariableCreatedEvent.class));
  }
}
