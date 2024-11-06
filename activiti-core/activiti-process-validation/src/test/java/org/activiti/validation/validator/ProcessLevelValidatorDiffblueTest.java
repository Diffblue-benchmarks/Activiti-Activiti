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
package org.activiti.validation.validator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.activiti.validation.validator.impl.ActivitiEventListenerValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessLevelValidatorDiffblueTest {
  /**
   * Test {@link ProcessLevelValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Process} (default
   * constructor).</li>
   *   <li>Then calls {@link BpmnModel#getProcesses()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessLevelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given ArrayList() add Process (default constructor); then calls getProcesses()")
  void testValidate_givenArrayListAddProcess_thenCallsGetProcesses() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(new Process());
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getProcesses()).thenReturn(processList);

    // Act
    activitiEventListenerValidator.validate(bpmnModel, new ArrayList<>());

    // Assert that nothing has changed
    verify(bpmnModel).getProcesses();
  }

  /**
   * Test {@link ProcessLevelValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link BpmnModel#getProcesses()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessLevelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given ArrayList(); then calls getProcesses()")
  void testValidate_givenArrayList_thenCallsGetProcesses() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getProcesses()).thenReturn(new ArrayList<>());

    // Act
    activitiEventListenerValidator.validate(bpmnModel, new ArrayList<>());

    // Assert that nothing has changed
    verify(bpmnModel).getProcesses();
  }

  /**
   * Test {@link ProcessLevelValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is
   * {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link ValidationError} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessLevelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given ValidationError (default constructor) ActivityId is '42'; when ArrayList() add ValidationError (default constructor)")
  void testValidate_givenValidationErrorActivityIdIs42_whenArrayListAddValidationError() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getProcesses()).thenReturn(new ArrayList<>());

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
    activitiEventListenerValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    verify(bpmnModel).getProcesses();
  }

  /**
   * Test {@link ProcessLevelValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is
   * {@code Activity Id}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessLevelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given ValidationError (default constructor) ActivityId is 'Activity Id'")
  void testValidate_givenValidationErrorActivityIdIsActivityId() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getProcesses()).thenReturn(new ArrayList<>());

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
    activitiEventListenerValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    verify(bpmnModel).getProcesses();
  }
}
