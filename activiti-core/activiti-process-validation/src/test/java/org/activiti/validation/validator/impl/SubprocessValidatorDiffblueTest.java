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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EventSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SubprocessValidatorDiffblueTest {
  /**
   * Test {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EventSubProcess} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList() add EventSubProcess (default constructor)")
  void testExecuteValidation_givenArrayListAddEventSubProcess() {
    // Arrange
    SubprocessValidator subprocessValidator = new SubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SubProcess> subProcessList = new ArrayList<>();
    subProcessList.add(new EventSubProcess());
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any())).thenReturn(subProcessList);

    // Act
    subprocessValidator.executeValidation(bpmnModel, process, new ArrayList<>());

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link Process#findFlowElementsOfType(Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList(); then calls findFlowElementsOfType(Class)")
  void testExecuteValidation_givenArrayList_thenCallsFindFlowElementsOfType() {
    // Arrange
    SubprocessValidator subprocessValidator = new SubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any())).thenReturn(new ArrayList<>());

    // Act
    subprocessValidator.executeValidation(bpmnModel, process, new ArrayList<>());

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ValidationError (default constructor) ActivityId is '42'")
  void testExecuteValidation_givenValidationErrorActivityIdIs42() {
    // Arrange
    SubprocessValidator subprocessValidator = new SubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SubProcess> subProcessList = new ArrayList<>();
    subProcessList.add(new SubProcess());
    Process process = mock(Process.class);
    when(process.findFlowElementsInSubProcessOfType(Mockito.<SubProcess>any(), Mockito.<Class<FlowElement>>any(),
        anyBoolean())).thenReturn(new ArrayList<>());
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any())).thenReturn(subProcessList);

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
    subprocessValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsInSubProcessOfType(isA(SubProcess.class), isA(Class.class), eq(false));
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is
   * {@code Activity Id}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ValidationError (default constructor) ActivityId is 'Activity Id'")
  void testExecuteValidation_givenValidationErrorActivityIdIsActivityId() {
    // Arrange
    SubprocessValidator subprocessValidator = new SubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SubProcess> subProcessList = new ArrayList<>();
    subProcessList.add(new SubProcess());
    Process process = mock(Process.class);
    when(process.findFlowElementsInSubProcessOfType(Mockito.<SubProcess>any(), Mockito.<Class<FlowElement>>any(),
        anyBoolean())).thenReturn(new ArrayList<>());
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any())).thenReturn(subProcessList);

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
    subprocessValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsInSubProcessOfType(isA(SubProcess.class), isA(Class.class), eq(false));
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then calls
   * {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then calls findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)")
  void testExecuteValidation_thenCallsFindFlowElementsInSubProcessOfType() {
    // Arrange
    SubprocessValidator subprocessValidator = new SubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SubProcess> subProcessList = new ArrayList<>();
    subProcessList.add(new SubProcess());
    Process process = mock(Process.class);
    when(process.findFlowElementsInSubProcessOfType(Mockito.<SubProcess>any(), Mockito.<Class<FlowElement>>any(),
        anyBoolean())).thenReturn(new ArrayList<>());
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any())).thenReturn(subProcessList);

    // Act
    subprocessValidator.executeValidation(bpmnModel, process, new ArrayList<>());

    // Assert that nothing has changed
    verify(process).findFlowElementsInSubProcessOfType(isA(SubProcess.class), isA(Class.class), eq(false));
    verify(process).findFlowElementsOfType(isA(Class.class));
  }
}
