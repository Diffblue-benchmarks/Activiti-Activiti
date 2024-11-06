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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.delegate.event.ActivitiVariableEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiVariableEventImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableEventFilter.class})
@ExtendWith(SpringExtension.class)
class VariableEventFilterDiffblueTest {
  @Autowired
  private VariableEventFilter variableEventFilter;

  /**
   * Test {@link VariableEventFilter#shouldEmmitEvent(ActivitiVariableEvent)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventFilter#shouldEmmitEvent(ActivitiVariableEvent)}
   */
  @Test
  @DisplayName("Test shouldEmmitEvent(ActivitiVariableEvent); given 'foo'; then return 'false'")
  void testShouldEmmitEvent_givenFoo_thenReturnFalse() {
    // Arrange
    ActivitiVariableEventImpl event = mock(ActivitiVariableEventImpl.class);
    when(event.getExecutionId()).thenReturn("foo");
    when(event.getProcessInstanceId()).thenReturn("42");
    when(event.getTaskId()).thenReturn(null);

    // Act
    boolean actualShouldEmmitEventResult = variableEventFilter.shouldEmmitEvent(event);

    // Assert
    verify(event).getExecutionId();
    verify(event).getProcessInstanceId();
    verify(event).getTaskId();
    assertFalse(actualShouldEmmitEventResult);
  }

  /**
   * Test {@link VariableEventFilter#shouldEmmitEvent(ActivitiVariableEvent)}.
   * <ul>
   *   <li>When {@link ActivitiVariableEventImpl}
   * {@link ActivitiEventImpl#getExecutionId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventFilter#shouldEmmitEvent(ActivitiVariableEvent)}
   */
  @Test
  @DisplayName("Test shouldEmmitEvent(ActivitiVariableEvent); when ActivitiVariableEventImpl getExecutionId() return '42'")
  void testShouldEmmitEvent_whenActivitiVariableEventImplGetExecutionIdReturn42() {
    // Arrange
    ActivitiVariableEventImpl event = mock(ActivitiVariableEventImpl.class);
    when(event.getExecutionId()).thenReturn("42");
    when(event.getProcessInstanceId()).thenReturn("42");
    when(event.getTaskId()).thenReturn(null);

    // Act
    boolean actualShouldEmmitEventResult = variableEventFilter.shouldEmmitEvent(event);

    // Assert
    verify(event).getExecutionId();
    verify(event).getProcessInstanceId();
    verify(event).getTaskId();
    assertTrue(actualShouldEmmitEventResult);
  }

  /**
   * Test {@link VariableEventFilter#shouldEmmitEvent(ActivitiVariableEvent)}.
   * <ul>
   *   <li>When {@link ActivitiVariableEventImpl}
   * {@link ActivitiVariableEventImpl#getTaskId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableEventFilter#shouldEmmitEvent(ActivitiVariableEvent)}
   */
  @Test
  @DisplayName("Test shouldEmmitEvent(ActivitiVariableEvent); when ActivitiVariableEventImpl getTaskId() return '42'")
  void testShouldEmmitEvent_whenActivitiVariableEventImplGetTaskIdReturn42() {
    // Arrange
    ActivitiVariableEventImpl event = mock(ActivitiVariableEventImpl.class);
    when(event.getTaskId()).thenReturn("42");

    // Act
    boolean actualShouldEmmitEventResult = variableEventFilter.shouldEmmitEvent(event);

    // Assert
    verify(event).getTaskId();
    assertTrue(actualShouldEmmitEventResult);
  }
}
