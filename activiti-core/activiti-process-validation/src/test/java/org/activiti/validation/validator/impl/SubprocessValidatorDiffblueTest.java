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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EventSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.SubProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SubprocessValidatorDiffblueTest {
  /**
   * Test {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EventSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add EventSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddEventSubProcess() {
    // Arrange
    SubprocessValidator subprocessValidator = new SubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SubProcess> subProcessList = new ArrayList<>();
    subProcessList.add(new EventSubProcess());

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any()))
        .thenReturn(subProcessList);

    // Act
    subprocessValidator.executeValidation(bpmnModel, process, new ArrayList<>());

    // Assert
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then calls {@link Process#findFlowElementsInSubProcessOfType(SubProcess, Class,
   *       boolean)}.
   * </ul>
   *
   * <p>Method under test: {@link SubprocessValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); then calls findFlowElementsInSubProcessOfType(SubProcess, Class, boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenCallsFindFlowElementsInSubProcessOfType() {
    // Arrange
    SubprocessValidator subprocessValidator = new SubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SubProcess> subProcessList = new ArrayList<>();
    subProcessList.add(new SubProcess());

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(new StartEvent());

    Process process = mock(Process.class);
    when(process.findFlowElementsInSubProcessOfType(
            Mockito.<SubProcess>any(), Mockito.<Class<FlowElement>>any(), anyBoolean()))
        .thenReturn(flowElementList);
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any()))
        .thenReturn(subProcessList);

    // Act
    subprocessValidator.executeValidation(bpmnModel, process, new ArrayList<>());

    // Assert
    verify(process)
        .findFlowElementsInSubProcessOfType(isA(SubProcess.class), isA(Class.class), eq(false));
    verify(process).findFlowElementsOfType(isA(Class.class));
  }
}
