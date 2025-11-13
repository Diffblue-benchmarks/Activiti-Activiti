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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Interface;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.Operation;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OperationValidatorDiffblueTest {
  /**
   * Test {@link OperationValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Interface} (default constructor) Operations is {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then calls {@link BpmnModel#getInterfaces()}.
   * </ul>
   *
   * <p>Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Interface (default constructor) Operations is ArrayList(); then calls getInterfaces()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OperationValidator.validate(BpmnModel, List)"})
  void testValidate_givenInterfaceOperationsIsArrayList_thenCallsGetInterfaces() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();

    Interface resultInterface = new Interface();
    resultInterface.setOperations(new ArrayList<>());

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(resultInterface);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);

    // Act
    operationValidator.validate(bpmnModel, new ArrayList<>());

    // Assert
    verify(bpmnModel, atLeast(1)).getInterfaces();
  }

  /**
   * Test {@link OperationValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Interface} (default constructor) Operations is {@code null}.
   *   <li>Then calls {@link BpmnModel#getInterfaces()}.
   * </ul>
   *
   * <p>Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Interface (default constructor) Operations is 'null'; then calls getInterfaces()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OperationValidator.validate(BpmnModel, List)"})
  void testValidate_givenInterfaceOperationsIsNull_thenCallsGetInterfaces() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();

    Interface resultInterface = new Interface();
    resultInterface.setOperations(null);

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(resultInterface);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);

    // Act
    operationValidator.validate(bpmnModel, new ArrayList<>());

    // Assert
    verify(bpmnModel, atLeast(1)).getInterfaces();
  }

  /**
   * Test {@link OperationValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Operation} (default constructor) InMessageRef is {@code :}.
   *   <li>Then calls {@link BpmnModel#getMessage(String)}.
   * </ul>
   *
   * <p>Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Operation (default constructor) InMessageRef is ':'; then calls getMessage(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OperationValidator.validate(BpmnModel, List)"})
  void testValidate_givenOperationInMessageRefIsColon_thenCallsGetMessage() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();

    Operation operation = new Operation();
    operation.setInMessageRef(":");

    ArrayList<Operation> operations = new ArrayList<>();
    operations.add(operation);

    Interface resultInterface = new Interface();
    resultInterface.setOperations(operations);

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(resultInterface);

    BpmnModel bpmnModel = mock(BpmnModel.class);

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    when(bpmnModel.getMessage(Mockito.<String>any()))
        .thenReturn(
            attributesResult
                .extensionElements(new HashMap<>())
                .id("42")
                .itemRef("Item Ref")
                .name("Name")
                .xmlColumnNumber(10)
                .xmlRowNumber(10)
                .build());
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);

    // Act
    operationValidator.validate(bpmnModel, new ArrayList<>());

    // Assert
    verify(bpmnModel, atLeast(1)).getInterfaces();
    verify(bpmnModel).getMessage(":");
  }

  /**
   * Test {@link OperationValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link ValidationError} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given ValidationError (default constructor) ActivityId is '42'; when ArrayList() add ValidationError (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OperationValidator.validate(BpmnModel, List)"})
  void testValidate_givenValidationErrorActivityIdIs42_whenArrayListAddValidationError() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();

    Operation operation = new Operation();
    operation.setInMessageRef(":");

    ArrayList<Operation> operations = new ArrayList<>();
    operations.add(operation);

    Interface resultInterface = new Interface();
    resultInterface.setOperations(operations);

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(resultInterface);

    BpmnModel bpmnModel = mock(BpmnModel.class);

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    when(bpmnModel.getMessage(Mockito.<String>any()))
        .thenReturn(
            attributesResult
                .extensionElements(new HashMap<>())
                .id("42")
                .itemRef("Item Ref")
                .name("Name")
                .xmlColumnNumber(10)
                .xmlRowNumber(10)
                .build());
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);

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

    // Assert
    verify(bpmnModel, atLeast(1)).getInterfaces();
    verify(bpmnModel).getMessage(":");
  }
}
