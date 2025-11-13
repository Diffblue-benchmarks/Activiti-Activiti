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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ValidateBpmnModelCmdDiffblueTest {
  /**
   * Test {@link ValidateBpmnModelCmd#ValidateBpmnModelCmd(BpmnModel)}.
   *
   * <p>Method under test: {@link ValidateBpmnModelCmd#ValidateBpmnModelCmd(BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ValidateBpmnModelCmd.<init>(BpmnModel)"})
  public void testNewValidateBpmnModelCmd() {
    // Arrange, Act and Assert
    BpmnModel bpmnModel =
        new ValidateBpmnModelCmd(TestProcessUtil.createOneTaskBpmnModel()).bpmnModel;
    assertTrue(bpmnModel.getResources() instanceof List);
    assertTrue(bpmnModel.getSignals() instanceof List);
    Process mainProcess = bpmnModel.getMainProcess();
    assertTrue(mainProcess.getArtifacts() instanceof List);
    assertTrue(mainProcess.getFlowElements() instanceof List);
    Map<String, FlowElement> flowElementMap = mainProcess.getFlowElementMap();
    assertEquals(3, flowElementMap.size());
    assertTrue(flowElementMap.get("theEnd") instanceof EndEvent);
    assertTrue(flowElementMap.get("start") instanceof StartEvent);
    assertTrue(flowElementMap.get("theTask") instanceof UserTask);
    assertEquals("The one task process", mainProcess.getName());
    assertNull(bpmnModel.getEventSupport());
    assertNull(bpmnModel.getSourceSystemId());
    assertNull(bpmnModel.getTargetNamespace());
    assertNull(mainProcess.getDocumentation());
    assertNull(bpmnModel.getStartEventFormTypes());
    assertNull(bpmnModel.getUserTaskFormTypes());
    assertNull(mainProcess.getInitialFlowElement());
    assertNull(mainProcess.getIoSpecification());
    assertEquals(0, mainProcess.getXmlColumnNumber());
    assertEquals(0, mainProcess.getXmlRowNumber());
    assertEquals(1, bpmnModel.getProcesses().size());
    assertFalse(bpmnModel.hasDiagramInterchangeInfo());
    assertFalse(mainProcess.isCandidateStarterGroupsDefined());
    assertFalse(mainProcess.isCandidateStarterUsersDefined());
    assertTrue(bpmnModel.getMessages().isEmpty());
    assertTrue(bpmnModel.getGlobalArtifacts().isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(mainProcess.getCandidateStarterGroups().isEmpty());
    assertTrue(mainProcess.getCandidateStarterUsers().isEmpty());
    assertTrue(mainProcess.getDataObjects().isEmpty());
    assertTrue(mainProcess.getEventListeners().isEmpty());
    assertTrue(mainProcess.getExecutionListeners().isEmpty());
    assertTrue(mainProcess.getLanes().isEmpty());
    assertTrue(mainProcess.getAttributes().isEmpty());
    assertTrue(mainProcess.getExtensionElements().isEmpty());
    assertTrue(bpmnModel.getDataStores().isEmpty());
    assertTrue(bpmnModel.getDefinitionsAttributes().isEmpty());
    assertTrue(bpmnModel.getErrors().isEmpty());
    assertTrue(bpmnModel.getFlowLocationMap().isEmpty());
    assertTrue(bpmnModel.getItemDefinitions().isEmpty());
    assertTrue(bpmnModel.getLabelLocationMap().isEmpty());
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertTrue(bpmnModel.getMessageFlows().isEmpty());
    assertTrue(bpmnModel.getNamespaces().isEmpty());
    assertTrue(mainProcess.isExecutable());
  }
}
