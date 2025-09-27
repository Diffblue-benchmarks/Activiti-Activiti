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
   * <p>Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OperationValidator.validate(BpmnModel, List)"})
  void testValidate() {
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
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    operationValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getInterfaces();
    verify(bpmnModel).getMessage(":");
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link OperationValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Interface} (default constructor) Operations is {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Interface (default constructor) Operations is ArrayList(); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OperationValidator.validate(BpmnModel, List)"})
  void testValidate_givenInterfaceOperationsIsArrayList_thenArrayListEmpty() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();

    Interface resultInterface = new Interface();
    resultInterface.setOperations(new ArrayList<>());

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(resultInterface);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    operationValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getInterfaces();
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link OperationValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Interface} (default constructor) Operations is {@code null}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Interface (default constructor) Operations is 'null'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OperationValidator.validate(BpmnModel, List)"})
  void testValidate_givenInterfaceOperationsIsNull_thenArrayListEmpty() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();

    Interface resultInterface = new Interface();
    resultInterface.setOperations(null);

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(resultInterface);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    operationValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getInterfaces();
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link OperationValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link BpmnModel} {@link BpmnModel#getInterfaces()} return {@code null}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given 'null'; when BpmnModel getInterfaces() return 'null'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OperationValidator.validate(BpmnModel, List)"})
  void testValidate_givenNull_whenBpmnModelGetInterfacesReturnNull_thenArrayListEmpty() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getInterfaces()).thenReturn(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    operationValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    verify(bpmnModel).getInterfaces();
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link OperationValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#getMessage(String)} return {@code null}.
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); when BpmnModel getMessage(String) return 'null'; then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OperationValidator.validate(BpmnModel, List)"})
  void testValidate_whenBpmnModelGetMessageReturnNull_thenArrayListSizeIsOne() {
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
    when(bpmnModel.getMessage(Mockito.<String>any())).thenReturn(null);
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    operationValidator.validate(bpmnModel, errors);

    // Assert
    verify(bpmnModel, atLeast(1)).getInterfaces();
    verify(bpmnModel).getMessage(":");
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("OPERATION_INVALID_IN_MESSAGE_REFERENCE", getResult.getDefaultDescription());
    assertEquals("OPERATION_INVALID_IN_MESSAGE_REFERENCE", getResult.getKey());
    assertEquals("OPERATION_INVALID_IN_MESSAGE_REFERENCE", getResult.getProblem());
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
   * Test {@link OperationValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#getMessage(String)} return {@code null}.
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); when BpmnModel getMessage(String) return 'null'; then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OperationValidator.validate(BpmnModel, List)"})
  void testValidate_whenBpmnModelGetMessageReturnNull_thenArrayListSizeIsTwo() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();

    Operation operation = new Operation();
    operation.setInMessageRef(":");

    ArrayList<Operation> operations = new ArrayList<>();
    operations.add(new Operation());
    operations.add(operation);

    Interface resultInterface = new Interface();
    resultInterface.setOperations(operations);

    ArrayList<Interface> resultInterfaceList = new ArrayList<>();
    resultInterfaceList.add(resultInterface);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getMessage(Mockito.<String>any())).thenReturn(null);
    when(bpmnModel.getInterfaces()).thenReturn(resultInterfaceList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    operationValidator.validate(bpmnModel, errors);

    // Assert
    verify(bpmnModel, atLeast(1)).getInterfaces();
    verify(bpmnModel, atLeast(1)).getMessage(Mockito.<String>any());
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("OPERATION_INVALID_IN_MESSAGE_REFERENCE", getResult.getDefaultDescription());
    assertEquals("OPERATION_INVALID_IN_MESSAGE_REFERENCE", getResult.getKey());
    assertEquals("OPERATION_INVALID_IN_MESSAGE_REFERENCE", getResult.getProblem());
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
   * Test {@link OperationValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link OperationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); when BpmnModel (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void OperationValidator.validate(BpmnModel, List)"})
  void testValidate_whenBpmnModel_thenArrayListEmpty() {
    // Arrange
    OperationValidator operationValidator = new OperationValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    operationValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }
}
