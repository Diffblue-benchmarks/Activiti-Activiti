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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.ValuedDataObject;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DataObjectValidatorDiffblueTest {
  /**
   * Test {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AdhocSubProcess} (default
   * constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList() add AdhocSubProcess (default constructor); then ArrayList() Empty")
  void testExecuteValidation_givenArrayListAddAdhocSubProcess_thenArrayListEmpty() {
    // Arrange
    DataObjectValidator dataObjectValidator = new DataObjectValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(new AdhocSubProcess());
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any(), anyBoolean())).thenReturn(flowElementList);
    when(process.getDataObjects()).thenReturn(new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    dataObjectValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).findFlowElementsOfType(isA(Class.class), eq(true));
    verify(process).getDataObjects();
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link BooleanDataObject}
   * (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList() add BooleanDataObject (default constructor); then ArrayList() size is two")
  void testExecuteValidation_givenArrayListAddBooleanDataObject_thenArrayListSizeIsTwo() {
    // Arrange
    DataObjectValidator dataObjectValidator = new DataObjectValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<ValuedDataObject> valuedDataObjectList = new ArrayList<>();
    valuedDataObjectList.add(new BooleanDataObject());
    valuedDataObjectList.add(new BooleanDataObject());
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any(), anyBoolean())).thenReturn(new ArrayList<>());
    when(process.getDataObjects()).thenReturn(valuedDataObjectList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    dataObjectValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class), eq(true));
    verify(process).getDataObjects();
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("DATA_OBJECT_MISSING_NAME", getResult.getDefaultDescription());
    assertEquals("DATA_OBJECT_MISSING_NAME", getResult.getKey());
    assertEquals("DATA_OBJECT_MISSING_NAME", getResult.getProblem());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList(); then ArrayList() Empty")
  void testExecuteValidation_givenArrayList_thenArrayListEmpty() {
    // Arrange
    DataObjectValidator dataObjectValidator = new DataObjectValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any(), anyBoolean())).thenReturn(new ArrayList<>());
    when(process.getDataObjects()).thenReturn(new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    dataObjectValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).findFlowElementsOfType(isA(Class.class), eq(true));
    verify(process).getDataObjects();
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link BooleanDataObject} {@link FlowElement#getName()} return
   * {@code Name}.</li>
   *   <li>Then calls {@link FlowElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given BooleanDataObject getName() return 'Name'; then calls getName()")
  void testExecuteValidation_givenBooleanDataObjectGetNameReturnName_thenCallsGetName() {
    // Arrange
    DataObjectValidator dataObjectValidator = new DataObjectValidator();
    BpmnModel bpmnModel = new BpmnModel();
    BooleanDataObject booleanDataObject = mock(BooleanDataObject.class);
    when(booleanDataObject.getName()).thenReturn("Name");

    ArrayList<ValuedDataObject> valuedDataObjectList = new ArrayList<>();
    valuedDataObjectList.add(booleanDataObject);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any(), anyBoolean())).thenReturn(new ArrayList<>());
    when(process.getDataObjects()).thenReturn(valuedDataObjectList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    dataObjectValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(booleanDataObject).getName();
    verify(process).findFlowElementsOfType(isA(Class.class), eq(true));
    verify(process).getDataObjects();
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is 'null'")
  void testExecuteValidation_thenArrayListFirstActivityIdIsNull() {
    // Arrange
    DataObjectValidator dataObjectValidator = new DataObjectValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<ValuedDataObject> valuedDataObjectList = new ArrayList<>();
    valuedDataObjectList.add(new BooleanDataObject());
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any(), anyBoolean())).thenReturn(new ArrayList<>());
    when(process.getDataObjects()).thenReturn(valuedDataObjectList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    dataObjectValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(process).findFlowElementsOfType(isA(Class.class), eq(true));
    verify(process).getDataObjects();
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityName is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityName is empty string")
  void testExecuteValidation_thenArrayListFirstActivityNameIsEmptyString() {
    // Arrange
    DataObjectValidator dataObjectValidator = new DataObjectValidator();
    BpmnModel bpmnModel = new BpmnModel();
    BooleanDataObject booleanDataObject = mock(BooleanDataObject.class);
    when(booleanDataObject.getXmlColumnNumber()).thenReturn(10);
    when(booleanDataObject.getXmlRowNumber()).thenReturn(10);
    when(booleanDataObject.getId()).thenReturn("42");
    when(booleanDataObject.getName()).thenReturn("");

    ArrayList<ValuedDataObject> valuedDataObjectList = new ArrayList<>();
    valuedDataObjectList.add(booleanDataObject);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any(), anyBoolean())).thenReturn(new ArrayList<>());
    when(process.getDataObjects()).thenReturn(valuedDataObjectList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    dataObjectValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(booleanDataObject).getId();
    verify(process).getId();
    verify(booleanDataObject).getXmlColumnNumber();
    verify(booleanDataObject).getXmlRowNumber();
    verify(booleanDataObject, atLeast(1)).getName();
    verify(process).findFlowElementsOfType(isA(Class.class), eq(true));
    verify(process).getDataObjects();
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("", getResult.getActivityName());
    assertEquals("42", getResult.getActivityId());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link BpmnModel}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when BpmnModel; then ArrayList() Empty")
  void testExecuteValidation_whenBpmnModel_thenArrayListEmpty() {
    // Arrange
    DataObjectValidator dataObjectValidator = new DataObjectValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    dataObjectValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataObjectValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    DataObjectValidator dataObjectValidator = new DataObjectValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    dataObjectValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertTrue(errors.isEmpty());
  }
}
