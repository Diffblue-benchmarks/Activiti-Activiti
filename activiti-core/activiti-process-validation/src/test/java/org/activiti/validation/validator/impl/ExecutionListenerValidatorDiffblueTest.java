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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.Test;

class ExecutionListenerValidatorDiffblueTest {
  /**
   * Method under test:
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}
   */
  @Test
  void testValidateListeners() {
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
    assertTrue(listeners.isEmpty());
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}
   */
  @Test
  void testValidateListeners2() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    ActivitiListener activitiListener = new ActivitiListener();
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
    assertEquals(1, listeners.size());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(activitiListener, listeners.get(0));
  }

  /**
   * Method under test:
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}
   */
  @Test
  void testValidateListeners3() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    ActivitiListener activitiListener = new ActivitiListener();
    listeners.add(activitiListener);
    ActivitiListener activitiListener2 = new ActivitiListener();
    listeners.add(activitiListener2);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.validateListeners(process, baseElement, listeners, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    ValidationError getResult2 = errors.get(1);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult2.getDefaultDescription());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult2.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult2.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult2.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult2.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(2, listeners.size());
    assertFalse(getResult.isWarning());
    assertFalse(getResult2.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertSame(activitiListener, listeners.get(0));
    assertSame(activitiListener2, listeners.get(1));
  }

  /**
   * Method under test:
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}
   */
  @Test
  void testValidateListeners4() {
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
    assertEquals(1, listeners.size());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(activitiListener, listeners.get(0));
  }

  /**
   * Method under test:
   * {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}
   */
  @Test
  void testValidateListeners5() {
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
    assertEquals(1, listeners.size());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(activitiListener, listeners.get(0));
  }
}
