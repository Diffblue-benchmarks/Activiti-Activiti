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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.engine.impl.bpmn.behavior.SubProcessActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParseHandlers;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;

public class SubProcessParseHandlerDiffblueTest {
  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with
   * {@code BpmnParse}, {@code SubProcess}.
   * <p>
   * Method under test:
   * {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSubProcess() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(new BpmnParseHandlers());
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    SubProcess subProcess = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    assertSame(element, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with
   * {@code BpmnParse}, {@code SubProcess}.
   * <p>
   * Method under test:
   * {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSubProcess2() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParserHandlers.addHandler(new AdhocSubProcessParseHandler());

    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(bpmnParserHandlers);
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    SubProcess subProcess = new SubProcess();
    AdhocSubProcess element = new AdhocSubProcess();
    subProcess.addFlowElement(element);

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    assertSame(element, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with
   * {@code BpmnParse}, {@code SubProcess}.
   * <p>
   * Method under test:
   * {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSubProcess3() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(new BpmnParseHandlers());
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    SubProcess subProcess = new SubProcess();
    BoundaryEvent element = new BoundaryEvent();
    subProcess.addFlowElement(element);

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    assertSame(element, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with
   * {@code BpmnParse}, {@code SubProcess}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSubProcess_givenBpmnModel() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setBpmnModel(new BpmnModel());

    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(new Association());

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    Collection<Artifact> artifacts = subProcess.getArtifacts();
    assertEquals(1, artifacts.size());
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = subProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    Object behavior = subProcess.getBehavior();
    assertTrue(behavior instanceof SubProcessActivityBehavior);
    assertNull(bpmnParse.getCurrentFlowElement());
    assertNull(((SubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertTrue(flowElements.isEmpty());
    assertTrue(subProcess.getBoundaryEvents().isEmpty());
    assertTrue(subProcess.getDataInputAssociations().isEmpty());
    assertTrue(subProcess.getDataOutputAssociations().isEmpty());
    assertTrue(subProcess.getMapExceptions().isEmpty());
    assertTrue(subProcess.getExecutionListeners().isEmpty());
    assertTrue(subProcess.getIncomingFlows().isEmpty());
    assertTrue(subProcess.getOutgoingFlows().isEmpty());
    assertTrue(subProcess.getDataObjects().isEmpty());
    assertTrue(subProcess.getAttributes().isEmpty());
    assertTrue(subProcess.getExtensionElements().isEmpty());
    assertTrue(subProcess.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with
   * {@code BpmnParse}, {@code SubProcess}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addProcess {@link Process}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSubProcess_givenBpmnModelAddProcessProcess() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setBpmnModel(bpmnModel);

    SubProcess subProcess = new SubProcess();
    subProcess.addArtifact(new Association());

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    Collection<Artifact> artifacts = subProcess.getArtifacts();
    assertEquals(1, artifacts.size());
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = subProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    Object behavior = subProcess.getBehavior();
    assertTrue(behavior instanceof SubProcessActivityBehavior);
    assertNull(bpmnParse.getCurrentFlowElement());
    assertNull(((SubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertTrue(flowElements.isEmpty());
    assertTrue(subProcess.getBoundaryEvents().isEmpty());
    assertTrue(subProcess.getDataInputAssociations().isEmpty());
    assertTrue(subProcess.getDataOutputAssociations().isEmpty());
    assertTrue(subProcess.getMapExceptions().isEmpty());
    assertTrue(subProcess.getExecutionListeners().isEmpty());
    assertTrue(subProcess.getIncomingFlows().isEmpty());
    assertTrue(subProcess.getOutgoingFlows().isEmpty());
    assertTrue(subProcess.getDataObjects().isEmpty());
    assertTrue(subProcess.getAttributes().isEmpty());
    assertTrue(subProcess.getExtensionElements().isEmpty());
    assertTrue(subProcess.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with
   * {@code BpmnParse}, {@code SubProcess}.
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) Artifacts Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSubProcess_thenSubProcessArtifactsEmpty() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    SubProcess subProcess = new SubProcess();

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    Collection<Artifact> artifacts = subProcess.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = subProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    Object behavior = subProcess.getBehavior();
    assertTrue(behavior instanceof SubProcessActivityBehavior);
    assertNull(bpmnParse.getCurrentFlowElement());
    assertNull(((SubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(subProcess.getBoundaryEvents().isEmpty());
    assertTrue(subProcess.getDataInputAssociations().isEmpty());
    assertTrue(subProcess.getDataOutputAssociations().isEmpty());
    assertTrue(subProcess.getMapExceptions().isEmpty());
    assertTrue(subProcess.getExecutionListeners().isEmpty());
    assertTrue(subProcess.getIncomingFlows().isEmpty());
    assertTrue(subProcess.getOutgoingFlows().isEmpty());
    assertTrue(subProcess.getDataObjects().isEmpty());
    assertTrue(subProcess.getAttributes().isEmpty());
    assertTrue(subProcess.getExtensionElements().isEmpty());
    assertTrue(subProcess.getFlowElementMap().isEmpty());
  }

  /**
   * Test {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)} with
   * {@code BpmnParse}, {@code SubProcess}.
   * <ul>
   *   <li>Then {@link SubProcess} (default constructor) FlowElements size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SubProcessParseHandler#executeParse(BpmnParse, SubProcess)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSubProcess_thenSubProcessFlowElementsSizeIsOne() {
    // Arrange
    SubProcessParseHandler subProcessParseHandler = new SubProcessParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(new BpmnParseHandlers());
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    SubProcess subProcess = new SubProcess();
    subProcess.addFlowElement(new BooleanDataObject());

    // Act
    subProcessParseHandler.executeParse(bpmnParse, subProcess);

    // Assert
    Collection<Artifact> artifacts = subProcess.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = subProcess.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    Object behavior = subProcess.getBehavior();
    assertTrue(behavior instanceof SubProcessActivityBehavior);
    assertNull(bpmnParse.getCurrentFlowElement());
    assertNull(((SubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertTrue(artifacts.isEmpty());
    assertTrue(subProcess.getBoundaryEvents().isEmpty());
    assertTrue(subProcess.getDataInputAssociations().isEmpty());
    assertTrue(subProcess.getDataOutputAssociations().isEmpty());
    assertTrue(subProcess.getMapExceptions().isEmpty());
    assertTrue(subProcess.getExecutionListeners().isEmpty());
    assertTrue(subProcess.getIncomingFlows().isEmpty());
    assertTrue(subProcess.getOutgoingFlows().isEmpty());
    assertTrue(subProcess.getDataObjects().isEmpty());
    assertTrue(subProcess.getAttributes().isEmpty());
    assertTrue(subProcess.getExtensionElements().isEmpty());
    assertTrue(subProcess.getFlowElementMap().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SubProcessParseHandler}
   *   <li>{@link SubProcessParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new SubProcessParseHandler()).getHandledType();

    // Assert
    Class<SubProcess> expectedHandledType = SubProcess.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
