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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.ScriptTask;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ScriptTaskValidatorDiffblueTest {
  /**
   * Test {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ScriptTask} (default
   * constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList() add ScriptTask (default constructor); then ArrayList() size is two")
  void testExecuteValidation_givenArrayListAddScriptTask_thenArrayListSizeIsTwo() {
    // Arrange
    ScriptTaskValidator scriptTaskValidator = new ScriptTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<ScriptTask> scriptTaskList = new ArrayList<>();
    scriptTaskList.add(new ScriptTask());
    scriptTaskList.add(new ScriptTask());
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ScriptTask>>any())).thenReturn(scriptTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    scriptTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("SCRIPT_TASK_MISSING_SCRIPT", getResult.getDefaultDescription());
    assertEquals("SCRIPT_TASK_MISSING_SCRIPT", getResult.getKey());
    assertEquals("SCRIPT_TASK_MISSING_SCRIPT", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList(); then BpmnModel (default constructor) Resources List")
  void testExecuteValidation_givenArrayList_thenBpmnModelResourcesList() {
    // Arrange
    ScriptTaskValidator scriptTaskValidator = new ScriptTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ScriptTask>>any())).thenReturn(new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    scriptTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ScriptTask} {@link ScriptTask#getScript()} return
   * {@code Script}.</li>
   *   <li>Then calls {@link ScriptTask#getScript()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ScriptTask getScript() return 'Script'; then calls getScript()")
  void testExecuteValidation_givenScriptTaskGetScriptReturnScript_thenCallsGetScript() {
    // Arrange
    ScriptTaskValidator scriptTaskValidator = new ScriptTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ScriptTask scriptTask = mock(ScriptTask.class);
    when(scriptTask.getScript()).thenReturn("Script");

    ArrayList<ScriptTask> scriptTaskList = new ArrayList<>();
    scriptTaskList.add(scriptTask);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ScriptTask>>any())).thenReturn(scriptTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    scriptTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(scriptTask).getScript();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is '42'")
  void testExecuteValidation_thenArrayListFirstActivityIdIs42() {
    // Arrange
    ScriptTaskValidator scriptTaskValidator = new ScriptTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ScriptTask scriptTask = mock(ScriptTask.class);
    when(scriptTask.getXmlColumnNumber()).thenReturn(10);
    when(scriptTask.getXmlRowNumber()).thenReturn(10);
    when(scriptTask.getId()).thenReturn("42");
    when(scriptTask.getName()).thenReturn("Name");
    when(scriptTask.getScript()).thenReturn("");

    ArrayList<ScriptTask> scriptTaskList = new ArrayList<>();
    scriptTaskList.add(scriptTask);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ScriptTask>>any())).thenReturn(scriptTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    scriptTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(scriptTask).getId();
    verify(scriptTask).getXmlColumnNumber();
    verify(scriptTask).getXmlRowNumber();
    verify(scriptTask).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    verify(scriptTask).getScript();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is 'null'")
  void testExecuteValidation_thenArrayListFirstActivityIdIsNull() {
    // Arrange
    ScriptTaskValidator scriptTaskValidator = new ScriptTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<ScriptTask> scriptTaskList = new ArrayList<>();
    scriptTaskList.add(new ScriptTask());
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ScriptTask>>any())).thenReturn(scriptTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    scriptTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link BpmnModel}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when BpmnModel; then ArrayList() Empty")
  void testExecuteValidation_whenBpmnModel_thenArrayListEmpty() {
    // Arrange
    ScriptTaskValidator scriptTaskValidator = new ScriptTaskValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    scriptTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ScriptTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then BpmnModel (default constructor) Resources List")
  void testExecuteValidation_whenProcess_thenBpmnModelResourcesList() {
    // Arrange
    ScriptTaskValidator scriptTaskValidator = new ScriptTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    scriptTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }
}
