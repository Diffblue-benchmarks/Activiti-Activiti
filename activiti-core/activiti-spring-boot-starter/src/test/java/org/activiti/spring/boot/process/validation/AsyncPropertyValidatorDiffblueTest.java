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
package org.activiti.spring.boot.process.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AsyncPropertyValidator.class})
@ExtendWith(SpringExtension.class)
class AsyncPropertyValidatorDiffblueTest {
  @Autowired
  private AsyncPropertyValidator asyncPropertyValidator;

  /**
   * Method under test:
   * {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  void testValidateFlowElementsInContainer() {
    // Arrange
    AdhocSubProcess container = new AdhocSubProcess();
    ArrayList<ValidationError> errors = new ArrayList<>();
    Process process = new Process();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, process);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Method under test:
   * {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  void testValidateFlowElementsInContainer2() {
    // Arrange
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();
    Process process = new Process();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, process);

    // Assert that nothing has changed
    verify(container).getFlowElements();
    assertTrue(errors.isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Method under test:
   * {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  void testValidateFlowElementsInContainer3() {
    // Arrange
    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(new AdhocSubProcess());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getFlowElements()).thenReturn(flowElementList);
    ArrayList<ValidationError> errors = new ArrayList<>();
    Process process = new Process();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, process);

    // Assert that nothing has changed
    verify(container).getFlowElements();
    assertTrue(errors.isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Method under test:
   * {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  void testValidateFlowElementsInContainer4() {
    // Arrange
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);
    Process process = new Process();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, process);

    // Assert that nothing has changed
    verify(container).getFlowElements();
    assertEquals(1, errors.size());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertSame(validationError, errors.get(0));
  }

  /**
   * Method under test:
   * {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  void testValidateFlowElementsInContainer5() {
    // Arrange
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ValidationError validationError2 = new ValidationError();
    validationError2.setActivityId("Activity Id");
    validationError2.setActivityName("42");
    validationError2.setDefaultDescription("42");
    validationError2.setKey("42");
    validationError2.setParams(new HashMap<>());
    validationError2.setProblem("42");
    validationError2.setProcessDefinitionId("Process Definition Id");
    validationError2.setProcessDefinitionName("42");
    validationError2.setValidatorSetName("42");
    validationError2.setWarning(false);
    validationError2.setXmlColumnNumber(1);
    validationError2.setXmlLineNumber(10);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError2);
    errors.add(validationError);
    Process process = new Process();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, process);

    // Assert that nothing has changed
    verify(container).getFlowElements();
    assertEquals(2, errors.size());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertSame(validationError2, errors.get(0));
  }

  /**
   * Method under test:
   * {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  void testValidateFlowElementsInContainer6() {
    // Arrange
    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(new BoundaryEvent());
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getFlowElements()).thenReturn(flowElementList);
    ArrayList<ValidationError> errors = new ArrayList<>();
    Process process = new Process();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, process);

    // Assert that nothing has changed
    verify(container).getFlowElements();
    assertTrue(errors.isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Method under test:
   * {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  void testValidateFlowElementsInContainer7() {
    // Arrange
    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(null);
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getFlowElements()).thenReturn(flowElementList);
    ArrayList<ValidationError> errors = new ArrayList<>();
    Process process = new Process();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, process);

    // Assert that nothing has changed
    verify(container).getFlowElements();
    assertTrue(errors.isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
  }

  /**
   * Method under test:
   * {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  void testValidateFlowElementsInContainer8() {
    // Arrange
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getXmlColumnNumber()).thenReturn(10);
    when(adhocSubProcess.getXmlRowNumber()).thenReturn(10);
    when(adhocSubProcess.getId()).thenReturn("42");
    when(adhocSubProcess.getName()).thenReturn("Name");
    when(adhocSubProcess.isAsynchronous()).thenReturn(true);
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(adhocSubProcess);
    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getFlowElements()).thenReturn(flowElementList);
    ArrayList<ValidationError> errors = new ArrayList<>();
    Process process = new Process();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, process);

    // Assert
    verify(adhocSubProcess).getId();
    verify(adhocSubProcess).getXmlColumnNumber();
    verify(adhocSubProcess).getXmlRowNumber();
    verify(adhocSubProcess).getName();
    verify(adhocSubProcess).isAsynchronous();
    verify(container).getFlowElements();
    verify(adhocSubProcess).getFlowElements();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getDefaultDescription());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getKey());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }
}
