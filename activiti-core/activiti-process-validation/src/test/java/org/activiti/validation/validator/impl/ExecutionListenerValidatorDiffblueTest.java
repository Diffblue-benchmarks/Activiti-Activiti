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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExecutionListenerValidatorDiffblueTest {
  /**
   * Test
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}.
   * <p>
   * Method under test:
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}
   */
  @Test
  @DisplayName("Test validateListeners(Process, BaseElement, List, List)")
  void testValidateListeners() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setImplementation("EXECUTION_LISTENER_IMPLEMENTATION_MISSING");

    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    listeners.add(activitiListener);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.validateListeners(process, baseElement, listeners, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}.
   * <p>
   * Method under test:
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}
   */
  @Test
  @DisplayName("Test validateListeners(Process, BaseElement, List, List)")
  void testValidateListeners2() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setOnTransaction("EXECUTION_LISTENER_IMPLEMENTATION_MISSING");

    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    listeners.add(activitiListener);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.validateListeners(process, baseElement, listeners, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}
   */
  @Test
  @DisplayName("Test validateListeners(Process, BaseElement, List, List); given ActivitiListener (default constructor); then ArrayList() size is one")
  void testValidateListeners_givenActivitiListener_thenArrayListSizeIsOne() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    listeners.add(new ActivitiListener());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.validateListeners(process, baseElement, listeners, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}
   */
  @Test
  @DisplayName("Test validateListeners(Process, BaseElement, List, List); given ActivitiListener (default constructor); then ArrayList() size is two")
  void testValidateListeners_givenActivitiListener_thenArrayListSizeIsTwo() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    listeners.add(new ActivitiListener());
    listeners.add(new ActivitiListener());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.validateListeners(process, baseElement, listeners, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
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
   * Test
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}.
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}
   */
  @Test
  @DisplayName("Test validateListeners(Process, BaseElement, List, List); when ActivitiListener (default constructor); then ArrayList() Empty")
  void testValidateListeners_whenActivitiListener_thenArrayListEmpty() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();
    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.validateListeners(process, baseElement, listeners, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
  }
}
