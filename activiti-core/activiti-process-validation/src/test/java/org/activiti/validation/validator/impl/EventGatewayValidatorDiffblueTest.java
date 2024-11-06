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

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EventGateway;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EventGatewayValidatorDiffblueTest {
  /**
   * Test
   * {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EventGateway} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList() add EventGateway (default constructor)")
  void testExecuteValidation_givenArrayListAddEventGateway() {
    // Arrange
    EventGatewayValidator eventGatewayValidator = new EventGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventGateway> eventGatewayList = new ArrayList<>();
    eventGatewayList.add(new EventGateway());
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<EventGateway>>any())).thenReturn(eventGatewayList);

    // Act
    eventGatewayValidator.executeValidation(bpmnModel, process, new ArrayList<>());

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test
   * {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link Process#findFlowElementsOfType(Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList(); then calls findFlowElementsOfType(Class)")
  void testExecuteValidation_givenArrayList_thenCallsFindFlowElementsOfType() {
    // Arrange
    EventGatewayValidator eventGatewayValidator = new EventGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<EventGateway>>any())).thenReturn(new ArrayList<>());

    // Act
    eventGatewayValidator.executeValidation(bpmnModel, process, new ArrayList<>());

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test
   * {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ValidationError (default constructor) ActivityId is '42'")
  void testExecuteValidation_givenValidationErrorActivityIdIs42() {
    // Arrange
    EventGatewayValidator eventGatewayValidator = new EventGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<EventGateway>>any())).thenReturn(new ArrayList<>());

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

    // Act
    eventGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test
   * {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is
   * {@code Activity Id}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ValidationError (default constructor) ActivityId is 'Activity Id'")
  void testExecuteValidation_givenValidationErrorActivityIdIsActivityId() {
    // Arrange
    EventGatewayValidator eventGatewayValidator = new EventGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<EventGateway>>any())).thenReturn(new ArrayList<>());

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

    // Act
    eventGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
  }
}
