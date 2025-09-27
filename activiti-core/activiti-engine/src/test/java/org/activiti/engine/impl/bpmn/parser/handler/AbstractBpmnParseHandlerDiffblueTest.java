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
package org.activiti.engine.impl.bpmn.parser.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class AbstractBpmnParseHandlerDiffblueTest {
  /**
   * Test {@link AbstractBpmnParseHandler#getHandledTypes()}.
   *
   * <p>Method under test: {@link AbstractBpmnParseHandler#getHandledTypes()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Set AbstractBpmnParseHandler.getHandledTypes()"})
  public void testGetHandledTypes() {
    // Arrange, Act and Assert
    assertEquals(1, new AdhocSubProcessParseHandler().getHandledTypes().size());
  }

  /**
   * Test {@link AbstractBpmnParseHandler#createExecutionListener(BpmnParse, ActivitiListener)}.
   *
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnParseHandler#createExecutionListener(BpmnParse,
   * ActivitiListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.delegate.ExecutionListener AbstractBpmnParseHandler.createExecutionListener(BpmnParse, ActivitiListener)"
  })
  public void testCreateExecutionListener_whenActivitiListener_thenReturnNull() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act and Assert
    assertNull(
        adhocSubProcessParseHandler.createExecutionListener(bpmnParse, new ActivitiListener()));
  }

  /**
   * Test {@link AbstractBpmnParseHandler#getPrecedingEventBasedGateway(BpmnParse,
   * IntermediateCatchEvent)}.
   *
   * <p>Method under test: {@link AbstractBpmnParseHandler#getPrecedingEventBasedGateway(BpmnParse,
   * IntermediateCatchEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String AbstractBpmnParseHandler.getPrecedingEventBasedGateway(BpmnParse, IntermediateCatchEvent)"
  })
  public void testGetPrecedingEventBasedGateway() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(TestProcessUtil.createOneTaskBpmnModel());

    ArrayList<SequenceFlow> incomingFlows = new ArrayList<>();
    incomingFlows.add(new SequenceFlow("start", "start"));
    incomingFlows.add(new SequenceFlow("not empty", "Target Ref"));

    IntermediateCatchEvent event = new IntermediateCatchEvent();
    event.setIncomingFlows(incomingFlows);

    // Act and Assert
    assertNull(adhocSubProcessParseHandler.getPrecedingEventBasedGateway(bpmnParse, event));
  }

  /**
   * Test {@link AbstractBpmnParseHandler#getPrecedingEventBasedGateway(BpmnParse,
   * IntermediateCatchEvent)}.
   *
   * <ul>
   *   <li>Given createOneTaskBpmnModel.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnParseHandler#getPrecedingEventBasedGateway(BpmnParse,
   * IntermediateCatchEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String AbstractBpmnParseHandler.getPrecedingEventBasedGateway(BpmnParse, IntermediateCatchEvent)"
  })
  public void testGetPrecedingEventBasedGateway_givenCreateOneTaskBpmnModel_thenReturnNull() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(TestProcessUtil.createOneTaskBpmnModel());

    ArrayList<SequenceFlow> incomingFlows = new ArrayList<>();
    incomingFlows.add(new SequenceFlow("not empty", "Target Ref"));

    IntermediateCatchEvent event = new IntermediateCatchEvent();
    event.setIncomingFlows(incomingFlows);

    // Act and Assert
    assertNull(adhocSubProcessParseHandler.getPrecedingEventBasedGateway(bpmnParse, event));
  }

  /**
   * Test {@link AbstractBpmnParseHandler#getPrecedingEventBasedGateway(BpmnParse,
   * IntermediateCatchEvent)}.
   *
   * <ul>
   *   <li>When {@link IntermediateCatchEvent} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnParseHandler#getPrecedingEventBasedGateway(BpmnParse,
   * IntermediateCatchEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String AbstractBpmnParseHandler.getPrecedingEventBasedGateway(BpmnParse, IntermediateCatchEvent)"
  })
  public void testGetPrecedingEventBasedGateway_whenIntermediateCatchEvent_thenReturnNull() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act and Assert
    assertNull(
        adhocSubProcessParseHandler.getPrecedingEventBasedGateway(
            bpmnParse, new IntermediateCatchEvent()));
  }

  /**
   * Test {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} {@link AdhocSubProcess#getArtifact(String)} return {@link
   *       Association} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractBpmnParseHandler.processArtifacts(BpmnParse, Collection)"})
  public void testProcessArtifacts_givenAdhocSubProcessGetArtifactReturnAssociation() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();

    SubProcess element = new SubProcess();
    element.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getArtifact(Mockito.<String>any())).thenReturn(new Association());

    ArrayList<SubProcess> subProcessList = new ArrayList<>();
    subProcessList.add(adhocSubProcess);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any()))
        .thenReturn(subProcessList);
    when(process.getArtifact(Mockito.<String>any())).thenReturn(null);
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addArtifact(new Association());
    process.addFlowElement(element);

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addProcess(process);

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(bpmnModel);

    LinkedHashSet<Artifact> artifacts = new LinkedHashSet<>();
    artifacts.add(new Association());

    // Act
    adhocSubProcessParseHandler.processArtifacts(bpmnParse, artifacts);

    // Assert
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getArtifact(null);
    verify(adhocSubProcess).getArtifact(null);
  }

  /**
   * Test {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link SubProcess} (default constructor).
   *   <li>Then calls {@link Process#getArtifact(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractBpmnParseHandler.processArtifacts(BpmnParse, Collection)"})
  public void testProcessArtifacts_givenArrayListAddSubProcess_thenCallsGetArtifact() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();

    SubProcess element = new SubProcess();
    element.addFlowElement(new AdhocSubProcess());

    ArrayList<SubProcess> subProcessList = new ArrayList<>();
    subProcessList.add(new SubProcess());

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any()))
        .thenReturn(subProcessList);
    when(process.getArtifact(Mockito.<String>any())).thenReturn(null);
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addArtifact(new Association());
    process.addFlowElement(element);

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addProcess(process);

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(bpmnModel);

    LinkedHashSet<Artifact> artifacts = new LinkedHashSet<>();
    artifacts.add(new Association());

    // Act
    adhocSubProcessParseHandler.processArtifacts(bpmnParse, artifacts);

    // Assert
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getArtifact(null);
  }

  /**
   * Test {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}.
   *
   * <ul>
   *   <li>Given {@link Process} {@link Process#getArtifact(String)} return {@code null}.
   *   <li>Then calls {@link Process#getArtifact(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractBpmnParseHandler.processArtifacts(BpmnParse, Collection)"})
  public void testProcessArtifacts_givenProcessGetArtifactReturnNull_thenCallsGetArtifact() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();

    SubProcess element = new SubProcess();
    element.addFlowElement(new AdhocSubProcess());

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any()))
        .thenReturn(new ArrayList<>());
    when(process.getArtifact(Mockito.<String>any())).thenReturn(null);
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addArtifact(new Association());
    process.addFlowElement(element);

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addProcess(process);

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(bpmnModel);

    LinkedHashSet<Artifact> artifacts = new LinkedHashSet<>();
    artifacts.add(new Association());

    // Act
    adhocSubProcessParseHandler.processArtifacts(bpmnParse, artifacts);

    // Assert
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getArtifact(null);
  }

  /**
   * Test {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}.
   *
   * <ul>
   *   <li>Then calls {@link AdhocSubProcess#getFlowElements()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractBpmnParseHandler.processArtifacts(BpmnParse, Collection)"})
  public void testProcessArtifacts_thenCallsGetFlowElements() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();

    SubProcess element = new SubProcess();
    element.addFlowElement(new AdhocSubProcess());

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getFlowElements()).thenReturn(new ArrayList<>());
    when(adhocSubProcess.getArtifact(Mockito.<String>any())).thenReturn(null);

    ArrayList<SubProcess> subProcessList = new ArrayList<>();
    subProcessList.add(adhocSubProcess);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SubProcess>>any()))
        .thenReturn(subProcessList);
    when(process.getArtifact(Mockito.<String>any())).thenReturn(null);
    doNothing().when(process).addArtifact(Mockito.<Artifact>any());
    doNothing().when(process).addFlowElement(Mockito.<FlowElement>any());
    process.addArtifact(new Association());
    process.addFlowElement(element);

    BpmnModel bpmnModel = TestProcessUtil.createOneTaskBpmnModel();
    bpmnModel.addProcess(process);

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setBpmnModel(bpmnModel);

    LinkedHashSet<Artifact> artifacts = new LinkedHashSet<>();
    artifacts.add(new Association());

    // Act
    adhocSubProcessParseHandler.processArtifacts(bpmnParse, artifacts);

    // Assert
    verify(process).addArtifact(isA(Artifact.class));
    verify(process).addFlowElement(isA(FlowElement.class));
    verify(process, atLeast(1)).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getArtifact(null);
    verify(adhocSubProcess, atLeast(1)).getArtifact(null);
    verify(adhocSubProcess, atLeast(1)).getFlowElements();
  }

  /**
   * Test {@link AbstractBpmnParseHandler#createAssociation(BpmnParse, Association)}.
   *
   * <ul>
   *   <li>Given {@link BpmnModel} {@link BpmnModel#getArtifact(String)} return {@link Association}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnParseHandler#createAssociation(BpmnParse,
   * Association)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractBpmnParseHandler.createAssociation(BpmnParse, Association)"})
  public void testCreateAssociation_givenBpmnModelGetArtifactReturnAssociation() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getArtifact(Mockito.<String>any())).thenReturn(new Association());

    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getBpmnModel()).thenReturn(bpmnModel);

    // Act
    adhocSubProcessParseHandler.createAssociation(bpmnParse, new Association());

    // Assert
    verify(bpmnModel).getArtifact(null);
    verify(bpmnParse).getBpmnModel();
  }

  /**
   * Test {@link AbstractBpmnParseHandler#createAssociation(BpmnParse, Association)}.
   *
   * <ul>
   *   <li>Given {@link BpmnModel} {@link BpmnModel#getArtifact(String)} return {@code null}.
   *   <li>Then calls {@link BpmnModel#getArtifact(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnParseHandler#createAssociation(BpmnParse,
   * Association)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractBpmnParseHandler.createAssociation(BpmnParse, Association)"})
  public void testCreateAssociation_givenBpmnModelGetArtifactReturnNull_thenCallsGetArtifact() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getArtifact(Mockito.<String>any())).thenReturn(null);

    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getBpmnModel()).thenReturn(bpmnModel);

    // Act
    adhocSubProcessParseHandler.createAssociation(bpmnParse, new Association());

    // Assert
    verify(bpmnModel, atLeast(1)).getArtifact(null);
    verify(bpmnParse).getBpmnModel();
  }

  /**
   * Test {@link AbstractBpmnParseHandler#createAssociation(BpmnParse, Association)}.
   *
   * <ul>
   *   <li>Given createOneTaskBpmnModel.
   * </ul>
   *
   * <p>Method under test: {@link AbstractBpmnParseHandler#createAssociation(BpmnParse,
   * Association)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractBpmnParseHandler.createAssociation(BpmnParse, Association)"})
  public void testCreateAssociation_givenCreateOneTaskBpmnModel() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();

    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getBpmnModel()).thenReturn(TestProcessUtil.createOneTaskBpmnModel());

    // Act
    adhocSubProcessParseHandler.createAssociation(bpmnParse, new Association());

    // Assert
    verify(bpmnParse).getBpmnModel();
  }
}
