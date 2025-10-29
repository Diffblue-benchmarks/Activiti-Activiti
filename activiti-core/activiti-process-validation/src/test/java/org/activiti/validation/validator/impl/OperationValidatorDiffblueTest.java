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

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Interface;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.Test;

class OperationValidatorDiffblueTest {
  /**
   * Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(new ArrayList<>());

    // Act
    operationValidator.validate(bpmnModel, new ArrayList<>());

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getInterfaces();
  }

  /**
   * Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate2() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(new Interface());
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);

    // Act
    operationValidator.validate(bpmnModel, new ArrayList<>());

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getInterfaces();
  }

  /**
   * Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate3() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(new ArrayList<>());

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
    operationValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getInterfaces();
  }

  /**
   * Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate4() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(new ArrayList<>());

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
    operationValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getInterfaces();
  }
}
