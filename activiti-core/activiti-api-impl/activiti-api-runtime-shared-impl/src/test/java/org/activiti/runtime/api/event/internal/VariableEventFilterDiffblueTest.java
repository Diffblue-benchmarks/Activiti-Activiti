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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiVariableEvent;
import org.activiti.engine.delegate.event.impl.ActivitiVariableEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiVariableUpdatedEventImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableEventFilter.class})
@ExtendWith(SpringExtension.class)
class VariableEventFilterDiffblueTest {
  @Autowired private VariableEventFilter variableEventFilter;

  /**
   * Test {@link VariableEventFilter#shouldEmmitEvent(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given {@code Event}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventFilter#shouldEmmitEvent(ActivitiVariableEvent)}
   */
  @Test
  @DisplayName("Test shouldEmmitEvent(ActivitiVariableEvent); given 'Event'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableEventFilter.shouldEmmitEvent(ActivitiVariableEvent)"})
  void testShouldEmmitEvent_givenEvent() {
    // Arrange
    ActivitiVariableEventImpl event =
        new ActivitiVariableEventImpl(ActivitiEventType.ENTITY_CREATED);
    event.setTaskId("Event");

    // Act and Assert
    assertTrue(variableEventFilter.shouldEmmitEvent(event));
  }

  /**
   * Test {@link VariableEventFilter#shouldEmmitEvent(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then calls {@link ActivitiVariableEvent#getProcessInstanceId()}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventFilter#shouldEmmitEvent(ActivitiVariableEvent)}
   */
  @Test
  @DisplayName(
      "Test shouldEmmitEvent(ActivitiVariableEvent); given 'null'; then calls getProcessInstanceId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableEventFilter.shouldEmmitEvent(ActivitiVariableEvent)"})
  void testShouldEmmitEvent_givenNull_thenCallsGetProcessInstanceId() {
    // Arrange
    ActivitiVariableEvent event = mock(ActivitiVariableEvent.class);
    when(event.getTaskId()).thenReturn(null);
    when(event.getProcessInstanceId()).thenReturn("42");
    when(event.getExecutionId()).thenReturn("42");

    // Act
    boolean actualShouldEmmitEventResult = variableEventFilter.shouldEmmitEvent(event);

    // Assert
    verify(event).getProcessInstanceId();
    verify(event).getExecutionId();
    verify(event).getTaskId();
    assertTrue(actualShouldEmmitEventResult);
  }

  /**
   * Test {@link VariableEventFilter#shouldEmmitEvent(ActivitiVariableEvent)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link VariableEventFilter#shouldEmmitEvent(ActivitiVariableEvent)}
   */
  @Test
  @DisplayName("Test shouldEmmitEvent(ActivitiVariableEvent); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean VariableEventFilter.shouldEmmitEvent(ActivitiVariableEvent)"})
  void testShouldEmmitEvent_thenReturnFalse() {
    // Arrange
    ActivitiVariableUpdatedEventImpl event = new ActivitiVariableUpdatedEventImpl();
    event.setExecutionId("42");

    // Act and Assert
    assertFalse(variableEventFilter.shouldEmmitEvent(event));
  }
}
