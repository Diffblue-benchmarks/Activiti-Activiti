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
package org.activiti.validation.validator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EventListener;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ActivitiEventListenerValidatorDiffblueTest {
  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType(null);

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    Process process = new Process();
    process.setEventListeners(eventListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EVENT_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EVENT_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EVENT_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
  }

  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation2() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("Process");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    Process process = new Process();
    process.setEventListeners(eventListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EVENT_LISTENER_INVALID_IMPLEMENTATION", getResult.getDefaultDescription());
    assertEquals("EVENT_LISTENER_INVALID_IMPLEMENTATION", getResult.getKey());
    assertEquals("EVENT_LISTENER_INVALID_IMPLEMENTATION", getResult.getProblem());
  }

  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation3() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("invalidThrowEvent");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    Process process = new Process();
    process.setEventListeners(eventListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EVENT_LISTENER_INVALID_THROW_EVENT_TYPE", getResult.getDefaultDescription());
    assertEquals("EVENT_LISTENER_INVALID_THROW_EVENT_TYPE", getResult.getKey());
    assertEquals("EVENT_LISTENER_INVALID_THROW_EVENT_TYPE", getResult.getProblem());
  }

  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation4() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("throwGlobalSignalEvent");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    Process process = new Process();
    process.setEventListeners(eventListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link EventListener} (default constructor) ImplementationType is {@code class}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given EventListener (default constructor) ImplementationType is 'class'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation_givenEventListenerImplementationTypeIsClass() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("class");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    Process process = new Process();
    process.setEventListeners(eventListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link EventListener} (default constructor) ImplementationType is {@code
   *       delegateExpression}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given EventListener (default constructor) ImplementationType is 'delegateExpression'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation_givenEventListenerImplementationTypeIsDelegateExpression() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("delegateExpression");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    Process process = new Process();
    process.setEventListeners(eventListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link EventListener} (default constructor) ImplementationType is empty string.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given EventListener (default constructor) ImplementationType is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation_givenEventListenerImplementationTypeIsEmptyString() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    Process process = new Process();
    process.setEventListeners(eventListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EVENT_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EVENT_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EVENT_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
  }

  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link EventListener} (default constructor) ImplementationType is {@code
   *       throwErrorEvent}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given EventListener (default constructor) ImplementationType is 'throwErrorEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation_givenEventListenerImplementationTypeIsThrowErrorEvent() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("throwErrorEvent");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    Process process = new Process();
    process.setEventListeners(eventListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link EventListener} (default constructor) ImplementationType is {@code
   *       throwMessageEvent}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given EventListener (default constructor) ImplementationType is 'throwMessageEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation_givenEventListenerImplementationTypeIsThrowMessageEvent() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("throwMessageEvent");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    Process process = new Process();
    process.setEventListeners(eventListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link EventListener} (default constructor) ImplementationType is {@code
   *       throwSignalEvent}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given EventListener (default constructor) ImplementationType is 'throwSignalEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation_givenEventListenerImplementationTypeIsThrowSignalEvent() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType("throwSignalEvent");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    Process process = new Process();
    process.setEventListeners(eventListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link Process} (default constructor) EventListeners is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given 'null'; when Process (default constructor) EventListeners is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation_givenNull_whenProcessEventListenersIsNull() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    process.setEventListeners(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation_thenArrayListSizeIsTwo() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    EventListener eventListener = new EventListener();
    eventListener.setImplementationType(null);

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());
    eventListeners.add(eventListener);

    Process process = new Process();
    process.setEventListeners(eventListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("EVENT_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EVENT_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EVENT_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ActivitiEventListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventListenerValidator#executeValidation(BpmnModel,
   * Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivitiEventListenerValidator.executeValidation(BpmnModel, Process, List)"
  })
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator =
        new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }
}
