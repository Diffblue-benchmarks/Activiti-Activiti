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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParseHandlers;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultListenerFactory;
import org.activiti.engine.impl.cfg.BpmnParseFactory;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractBpmnParseHandlerDiffblueTest {
  /**
   * Test {@link AbstractBpmnParseHandler#getHandledTypes()}.
   * <p>
   * Method under test: {@link AbstractBpmnParseHandler#getHandledTypes()}
   */
  @Test
  public void testGetHandledTypes() {
    // Arrange, Act and Assert
    assertEquals(1, (new AdhocSubProcessParseHandler()).getHandledTypes().size());
  }

  /**
   * Test {@link AbstractBpmnParseHandler#parse(BpmnParse, BaseElement)}.
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).</li>
   *   <li>Then calls {@link BpmnParser#getActivityBehaviorFactory()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBpmnParseHandler#parse(BpmnParse, BaseElement)}
   */
  @Test
  public void testParse_whenBoundaryEvent_thenCallsGetActivityBehaviorFactory() {
    // Arrange
    BoundaryEventParseHandler boundaryEventParseHandler = new BoundaryEventParseHandler();
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    // Act
    boundaryEventParseHandler.parse(bpmnParse, new BoundaryEvent());

    // Assert that nothing has changed
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test
   * {@link AbstractBpmnParseHandler#getPrecedingEventBasedGateway(BpmnParse, IntermediateCatchEvent)}.
   * <ul>
   *   <li>Given {@link BpmnParseFactory}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBpmnParseHandler#getPrecedingEventBasedGateway(BpmnParse, IntermediateCatchEvent)}
   */
  @Test
  public void testGetPrecedingEventBasedGateway_givenBpmnParseFactory() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setBpmnParseFactory(mock(BpmnParseFactory.class));
    BpmnParse bpmnParse = new BpmnParse(parser);

    // Act and Assert
    assertNull(adhocSubProcessParseHandler.getPrecedingEventBasedGateway(bpmnParse, new IntermediateCatchEvent()));
  }

  /**
   * Test
   * {@link AbstractBpmnParseHandler#getPrecedingEventBasedGateway(BpmnParse, IntermediateCatchEvent)}.
   * <ul>
   *   <li>When {@link IntermediateCatchEvent} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBpmnParseHandler#getPrecedingEventBasedGateway(BpmnParse, IntermediateCatchEvent)}
   */
  @Test
  public void testGetPrecedingEventBasedGateway_whenIntermediateCatchEvent_thenReturnNull() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act and Assert
    assertNull(adhocSubProcessParseHandler.getPrecedingEventBasedGateway(bpmnParse, new IntermediateCatchEvent()));
  }

  /**
   * Test
   * {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor) addProcess {@link Process}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}
   */
  @Test
  public void testProcessArtifacts_givenBpmnModelAddProcessProcess() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getBpmnModel()).thenReturn(bpmnModel);

    ArrayList<Artifact> artifacts = new ArrayList<>();
    artifacts.add(new Association());

    // Act
    adhocSubProcessParseHandler.processArtifacts(bpmnParse, artifacts);

    // Assert that nothing has changed
    verify(bpmnParse).getBpmnModel();
  }

  /**
   * Test
   * {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}.
   * <ul>
   *   <li>Given {@link BpmnModel} {@link BpmnModel#getArtifact(String)} return
   * {@code null}.</li>
   *   <li>Then calls {@link BpmnModel#getArtifact(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}
   */
  @Test
  public void testProcessArtifacts_givenBpmnModelGetArtifactReturnNull_thenCallsGetArtifact() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getArtifact(Mockito.<String>any())).thenReturn(null);
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getBpmnModel()).thenReturn(bpmnModel);

    ArrayList<Artifact> artifacts = new ArrayList<>();
    artifacts.add(new Association());

    // Act
    adhocSubProcessParseHandler.processArtifacts(bpmnParse, artifacts);

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getArtifact(isNull());
    verify(bpmnParse).getBpmnModel();
  }

  /**
   * Test
   * {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>When {@link BpmnParse} {@link BpmnParse#getBpmnModel()} return
   * {@link BpmnModel} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}
   */
  @Test
  public void testProcessArtifacts_givenBpmnModel_whenBpmnParseGetBpmnModelReturnBpmnModel() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getBpmnModel()).thenReturn(new BpmnModel());

    ArrayList<Artifact> artifacts = new ArrayList<>();
    artifacts.add(new Association());

    // Act
    adhocSubProcessParseHandler.processArtifacts(bpmnParse, artifacts);

    // Assert that nothing has changed
    verify(bpmnParse).getBpmnModel();
  }

  /**
   * Test
   * {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}.
   * <ul>
   *   <li>Then calls {@link BpmnModel#getArtifact(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}
   */
  @Test
  public void testProcessArtifacts_thenCallsGetArtifact() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getBpmnModel()).thenReturn(bpmnModel);

    ArrayList<Artifact> artifacts = new ArrayList<>();
    artifacts.add(new Association());

    // Act
    adhocSubProcessParseHandler.processArtifacts(bpmnParse, artifacts);

    // Assert that nothing has changed
    verify(bpmnModel).getArtifact(isNull());
    verify(bpmnParse).getBpmnModel();
  }

  /**
   * Test
   * {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link BpmnParser#getActivityBehaviorFactory()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBpmnParseHandler#processArtifacts(BpmnParse, Collection)}
   */
  @Test
  public void testProcessArtifacts_whenArrayList_thenCallsGetActivityBehaviorFactory() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    // Act
    adhocSubProcessParseHandler.processArtifacts(bpmnParse, new ArrayList<>());

    // Assert that nothing has changed
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test
   * {@link AbstractBpmnParseHandler#createAssociation(BpmnParse, Association)}.
   * <ul>
   *   <li>Given {@link BpmnModel} {@link BpmnModel#getArtifact(String)} return
   * {@code null}.</li>
   *   <li>Then calls {@link BpmnModel#getArtifact(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBpmnParseHandler#createAssociation(BpmnParse, Association)}
   */
  @Test
  public void testCreateAssociation_givenBpmnModelGetArtifactReturnNull_thenCallsGetArtifact() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getArtifact(Mockito.<String>any())).thenReturn(null);
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getBpmnModel()).thenReturn(bpmnModel);

    // Act
    adhocSubProcessParseHandler.createAssociation(bpmnParse, new Association());

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getArtifact(isNull());
    verify(bpmnParse).getBpmnModel();
  }

  /**
   * Test
   * {@link AbstractBpmnParseHandler#createAssociation(BpmnParse, Association)}.
   * <ul>
   *   <li>Given {@link BpmnModel} (default constructor).</li>
   *   <li>When {@link BpmnParse} {@link BpmnParse#getBpmnModel()} return
   * {@link BpmnModel} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBpmnParseHandler#createAssociation(BpmnParse, Association)}
   */
  @Test
  public void testCreateAssociation_givenBpmnModel_whenBpmnParseGetBpmnModelReturnBpmnModel() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getBpmnModel()).thenReturn(new BpmnModel());

    // Act
    adhocSubProcessParseHandler.createAssociation(bpmnParse, new Association());

    // Assert that nothing has changed
    verify(bpmnParse).getBpmnModel();
  }

  /**
   * Test
   * {@link AbstractBpmnParseHandler#createAssociation(BpmnParse, Association)}.
   * <ul>
   *   <li>Then calls {@link BpmnModel#getArtifact(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractBpmnParseHandler#createAssociation(BpmnParse, Association)}
   */
  @Test
  public void testCreateAssociation_thenCallsGetArtifact() {
    // Arrange
    AdhocSubProcessParseHandler adhocSubProcessParseHandler = new AdhocSubProcessParseHandler();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getBpmnModel()).thenReturn(bpmnModel);

    // Act
    adhocSubProcessParseHandler.createAssociation(bpmnParse, new Association());

    // Assert that nothing has changed
    verify(bpmnModel).getArtifact(isNull());
    verify(bpmnParse).getBpmnModel();
  }
}
