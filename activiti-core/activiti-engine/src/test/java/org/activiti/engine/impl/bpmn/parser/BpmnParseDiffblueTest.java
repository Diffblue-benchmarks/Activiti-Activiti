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
package org.activiti.engine.impl.bpmn.parser;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.bpmn.behavior.AdhocSubProcessActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultListenerFactory;
import org.activiti.engine.impl.bpmn.parser.factory.ListenerFactory;
import org.activiti.engine.impl.bpmn.parser.handler.AdhocSubProcessParseHandler;
import org.activiti.engine.impl.cfg.BpmnParseFactory;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.impl.util.io.StreamSource;
import org.activiti.engine.impl.util.io.StringStreamSource;
import org.activiti.engine.impl.util.io.UrlStreamSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BpmnParseDiffblueTest {
  @InjectMocks
  private BpmnParse bpmnParse;

  @Mock
  private BpmnParser bpmnParser;

  /**
   * Method under test: {@link BpmnParse#sourceInputStream(InputStream)}
   */
  @Test
  public void testSourceInputStream() throws IOException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    BpmnParse actualSourceInputStreamResult = bpmnParse
        .sourceInputStream(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    StreamSource streamSource = bpmnParse.streamSource;
    assertTrue(streamSource instanceof InputStreamSource);
    assertEquals("inputStream", bpmnParse.name);
    byte[] byteArray = new byte[8];
    assertEquals(8, streamSource.getInputStream().read(byteArray));
    assertSame(bpmnParse, actualSourceInputStreamResult);
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test: {@link BpmnParse#sourceInputStream(InputStream)}
   */
  @Test
  public void testSourceInputStream2() throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name(null);
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceInputStream(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
  }

  /**
   * Method under test: {@link BpmnParse#sourceInputStream(InputStream)}
   */
  @Test
  public void testSourceInputStream3() throws IOException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(null);

    // Act
    BpmnParse actualSourceInputStreamResult = bpmnParse
        .sourceInputStream(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    StreamSource streamSource = bpmnParse.streamSource;
    assertTrue(streamSource instanceof InputStreamSource);
    assertEquals("foo", bpmnParse.name);
    byte[] byteArray = new byte[8];
    assertEquals(8, streamSource.getInputStream().read(byteArray));
    assertSame(bpmnParse, actualSourceInputStreamResult);
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test: {@link BpmnParse#sourceInputStream(InputStream)}
   */
  @Test
  public void testSourceInputStream4() throws UnsupportedEncodingException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceInputStream(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
  }

  /**
   * Method under test: {@link BpmnParse#sourceUrl(URL)}
   */
  @Test
  public void testSourceUrl() throws MalformedURLException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    BpmnParse actualSourceUrlResult = bpmnParse
        .sourceUrl(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    // Assert
    assertTrue(bpmnParse.streamSource instanceof UrlStreamSource);
    assertEquals(String.join("", "file:", Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toString()),
        bpmnParse.name);
    assertSame(bpmnParse, actualSourceUrlResult);
  }

  /**
   * Method under test: {@link BpmnParse#sourceUrl(URL)}
   */
  @Test
  public void testSourceUrl2() throws MalformedURLException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name(null);
    bpmnParse.setStreamSource(mock(StreamSource.class));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceUrl(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL()));
  }

  /**
   * Method under test: {@link BpmnParse#sourceUrl(URL)}
   */
  @Test
  public void testSourceUrl3() throws UnsupportedEncodingException, MalformedURLException {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.name("foo");
    bpmnParse.setStreamSource(new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> bpmnParse.sourceUrl(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL()));
  }

  /**
   * Method under test: {@link BpmnParse#sourceUrl(String)}
   */
  @Test
  public void testSourceUrl4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> bpmnParse.sourceUrl("Url"));
  }

  /**
   * Method under test: {@link BpmnParse#sourceString(String)}
   */
  @Test
  public void testSourceString() throws IOException {
    // Arrange and Act
    BpmnParse actualSourceStringResult = bpmnParse.sourceString("String");

    // Assert
    StreamSource streamSource = bpmnParse.streamSource;
    assertTrue(streamSource instanceof StringStreamSource);
    assertEquals("string", bpmnParse.name);
    byte[] byteArray = new byte[6];
    assertEquals(6, streamSource.getInputStream().read(byteArray));
    assertSame(bpmnParse, actualSourceStringResult);
    assertArrayEquals("String".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test: {@link BpmnParse#applyParseHandlers()}
   */
  @Test
  public void testApplyParseHandlers() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    BpmnModel bpmnModel = new BpmnModel();
    bpmnParse.setBpmnModel(bpmnModel);

    // Act
    bpmnParse.applyParseHandlers();

    // Assert
    assertNull(bpmnParse.getCurrentProcess());
    assertTrue(bpmnParse.getSequenceFlows().isEmpty());
    assertSame(bpmnModel, bpmnParse.getBpmnModel());
  }

  /**
   * Method under test: {@link BpmnParse#applyParseHandlers()}
   */
  @Test
  public void testApplyParseHandlers2() {
    // Arrange
    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(new BpmnParseHandlers());

    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    bpmnModel.addProcess(process);

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setBpmnModel(bpmnModel);

    // Act
    bpmnParse.applyParseHandlers();

    // Assert
    assertTrue(bpmnParse.getSequenceFlows().isEmpty());
    assertSame(bpmnModel, bpmnParse.getBpmnModel());
    assertSame(process, bpmnParse.getCurrentProcess());
  }

  /**
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  public void testProcessFlowElements() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    ArrayList<FlowElement> flowElements = new ArrayList<>();

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert that nothing has changed
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  public void testProcessFlowElements2() {
    // Arrange
    BpmnParser parser = new BpmnParser();
    parser.setBpmnParseFactory(mock(BpmnParseFactory.class));
    BpmnParse bpmnParse = new BpmnParse(parser);
    ArrayList<FlowElement> flowElements = new ArrayList<>();

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert that nothing has changed
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  public void testProcessFlowElements3() {
    // Arrange
    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(new BpmnParseHandlers());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<FlowElement> flowElements = new ArrayList<>();
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    flowElements.add(adhocSubProcess);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertEquals(1, flowElements.size());
    FlowElement getResult = flowElements.get(0);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertNull(((AdhocSubProcess) getResult).getBehavior());
    assertSame(adhocSubProcess, getResult);
    assertSame(adhocSubProcess, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  public void testProcessFlowElements4() {
    // Arrange
    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(new BpmnParseHandlers());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<FlowElement> flowElements = new ArrayList<>();
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    flowElements.add(boundaryEvent);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertEquals(1, flowElements.size());
    FlowElement getResult = flowElements.get(0);
    assertTrue(getResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) getResult).getBehavior());
    assertSame(boundaryEvent, getResult);
    assertSame(boundaryEvent, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  public void testProcessFlowElements5() {
    // Arrange
    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(new BpmnParseHandlers());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<FlowElement> flowElements = new ArrayList<>();
    BooleanDataObject booleanDataObject = new BooleanDataObject();
    flowElements.add(booleanDataObject);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert that nothing has changed
    assertEquals(1, flowElements.size());
    FlowElement getResult = flowElements.get(0);
    assertTrue(getResult instanceof BooleanDataObject);
    assertSame(booleanDataObject, getResult);
  }

  /**
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  public void testProcessFlowElements6() {
    // Arrange
    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParserHandlers.addHandler(new AdhocSubProcessParseHandler());

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    parser.setBpmnParserHandlers(bpmnParserHandlers);
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<FlowElement> flowElements = new ArrayList<>();
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    flowElements.add(adhocSubProcess);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertEquals(1, flowElements.size());
    FlowElement getResult = flowElements.get(0);
    assertTrue(getResult instanceof AdhocSubProcess);
    Object behavior = ((AdhocSubProcess) getResult).getBehavior();
    assertTrue(behavior instanceof AdhocSubProcessActivityBehavior);
    assertNull(((AdhocSubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertSame(adhocSubProcess, getResult);
    assertSame(adhocSubProcess, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  public void testProcessFlowElements7() {
    // Arrange
    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParserHandlers.addHandler(new AdhocSubProcessParseHandler());

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    parser.setBpmnParserHandlers(bpmnParserHandlers);

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setBpmnModel(new BpmnModel());

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.addArtifact(new Association());

    ArrayList<FlowElement> flowElements = new ArrayList<>();
    flowElements.add(adhocSubProcess);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertEquals(1, flowElements.size());
    FlowElement getResult = flowElements.get(0);
    assertTrue(getResult instanceof AdhocSubProcess);
    Object behavior = ((AdhocSubProcess) getResult).getBehavior();
    assertTrue(behavior instanceof AdhocSubProcessActivityBehavior);
    assertNull(((AdhocSubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertSame(adhocSubProcess, getResult);
    assertSame(adhocSubProcess, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Method under test: {@link BpmnParse#processFlowElements(Collection)}
   */
  @Test
  public void testProcessFlowElements8() {
    // Arrange
    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParserHandlers.addHandler(new AdhocSubProcessParseHandler());

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    parser.setBpmnParserHandlers(bpmnParserHandlers);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setBpmnModel(bpmnModel);

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.addArtifact(new Association());

    ArrayList<FlowElement> flowElements = new ArrayList<>();
    flowElements.add(adhocSubProcess);

    // Act
    bpmnParse.processFlowElements(flowElements);

    // Assert
    assertEquals(1, flowElements.size());
    FlowElement getResult = flowElements.get(0);
    assertTrue(getResult instanceof AdhocSubProcess);
    Object behavior = ((AdhocSubProcess) getResult).getBehavior();
    assertTrue(behavior instanceof AdhocSubProcessActivityBehavior);
    assertNull(((AdhocSubProcessActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertSame(adhocSubProcess, getResult);
    assertSame(adhocSubProcess, bpmnParse.getCurrentFlowElement());
  }

  /**
   * Method under test: {@link BpmnParse#processDI()}
   */
  @Test
  public void testProcessDI() {
    // Arrange
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());

    // Act
    (new BpmnParse(parser)).processDI();

    // Assert that nothing has changed
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Method under test: {@link BpmnParse#createBPMNEdge(String, List)}
   */
  @Test
  public void testCreateBPMNEdge() {
    // Arrange
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setBpmnModel(new BpmnModel());

    // Act
    bpmnParse.createBPMNEdge("Key", new ArrayList<>());

    // Assert that nothing has changed
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Method under test: {@link BpmnParse#createBPMNEdge(String, List)}
   */
  @Test
  public void testCreateBPMNEdge2() {
    // Arrange
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setBpmnModel(bpmnModel);

    // Act
    bpmnParse.createBPMNEdge("Key", new ArrayList<>());

    // Assert that nothing has changed
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Method under test: {@link BpmnParse#createBPMNEdge(String, List)}
   */
  @Test
  public void testCreateBPMNEdge3() {
    // Arrange
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setBpmnModel(bpmnModel);

    // Act
    bpmnParse.createBPMNEdge("Key", new ArrayList<>());

    // Assert that nothing has changed
    verify(bpmnModel).getArtifact(eq("Key"));
    verify(bpmnModel).getFlowElement(eq("Key"));
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Method under test: {@link BpmnParse#getProcessDefinition(String)}
   */
  @Test
  public void testGetProcessDefinition() {
    // Arrange, Act and Assert
    assertNull(bpmnParse.getProcessDefinition("Process Definition Key"));
  }

  /**
   * Method under test: {@link BpmnParse#setCurrentSubProcess(SubProcess)}
   */
  @Test
  public void testSetCurrentSubProcess() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    SubProcess subProcess = new SubProcess();

    // Act
    bpmnParse.setCurrentSubProcess(subProcess);

    // Assert
    Collection<FlowElement> flowElements = subProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    LinkedList<SubProcess> subProcessList = bpmnParse.currentSubprocessStack;
    assertEquals(1, subProcessList.size());
    assertTrue(flowElements.isEmpty());
    assertTrue(subProcess.getBoundaryEvents().isEmpty());
    assertTrue(subProcess.getDataInputAssociations().isEmpty());
    assertTrue(subProcess.getDataOutputAssociations().isEmpty());
    assertTrue(subProcess.getMapExceptions().isEmpty());
    assertTrue(subProcess.getExecutionListeners().isEmpty());
    assertTrue(subProcess.getIncomingFlows().isEmpty());
    assertTrue(subProcess.getOutgoingFlows().isEmpty());
    assertTrue(subProcess.getDataObjects().isEmpty());
    assertTrue(bpmnParse.getProcessDefinitions().isEmpty());
    assertSame(subProcess, subProcessList.get(0));
    assertSame(subProcess, bpmnParse.getCurrentSubProcess());
  }

  /**
   * Method under test: {@link BpmnParse#setCurrentSubProcess(SubProcess)}
   */
  @Test
  public void testSetCurrentSubProcess2() {
    // Arrange
    BpmnParser parser = new BpmnParser();
    parser.setBpmnParseFactory(mock(BpmnParseFactory.class));
    BpmnParse bpmnParse = new BpmnParse(parser);
    SubProcess subProcess = new SubProcess();

    // Act
    bpmnParse.setCurrentSubProcess(subProcess);

    // Assert
    Collection<FlowElement> flowElements = subProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    LinkedList<SubProcess> subProcessList = bpmnParse.currentSubprocessStack;
    assertEquals(1, subProcessList.size());
    assertTrue(flowElements.isEmpty());
    assertTrue(subProcess.getBoundaryEvents().isEmpty());
    assertTrue(subProcess.getDataInputAssociations().isEmpty());
    assertTrue(subProcess.getDataOutputAssociations().isEmpty());
    assertTrue(subProcess.getMapExceptions().isEmpty());
    assertTrue(subProcess.getExecutionListeners().isEmpty());
    assertTrue(subProcess.getIncomingFlows().isEmpty());
    assertTrue(subProcess.getOutgoingFlows().isEmpty());
    assertTrue(subProcess.getDataObjects().isEmpty());
    assertTrue(bpmnParse.getProcessDefinitions().isEmpty());
    assertSame(subProcess, subProcessList.get(0));
    assertSame(subProcess, bpmnParse.getCurrentSubProcess());
  }

  /**
   * Method under test: {@link BpmnParse#getCurrentSubProcess()}
   */
  @Test
  public void testGetCurrentSubProcess() {
    // Arrange, Act and Assert
    assertNull((new BpmnParse(new BpmnParser())).getCurrentSubProcess());
  }

  /**
   * Method under test: {@link BpmnParse#getCurrentSubProcess()}
   */
  @Test
  public void testGetCurrentSubProcess2() {
    // Arrange
    BpmnParser parser = new BpmnParser();
    parser.setBpmnParseFactory(mock(BpmnParseFactory.class));

    // Act and Assert
    assertNull((new BpmnParse(parser)).getCurrentSubProcess());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link BpmnParse#deployment(DeploymentEntity)}
   *   <li>{@link BpmnParse#name(String)}
   *   <li>{@link BpmnParse#setActivityBehaviorFactory(ActivityBehaviorFactory)}
   *   <li>{@link BpmnParse#setBpmnModel(BpmnModel)}
   *   <li>{@link BpmnParse#setBpmnParserHandlers(BpmnParseHandlers)}
   *   <li>{@link BpmnParse#setCurrentFlowElement(FlowElement)}
   *   <li>{@link BpmnParse#setCurrentProcess(Process)}
   *   <li>{@link BpmnParse#setCurrentProcessDefinition(ProcessDefinitionEntity)}
   *   <li>{@link BpmnParse#setDeployment(DeploymentEntity)}
   *   <li>{@link BpmnParse#setListenerFactory(ListenerFactory)}
   *   <li>{@link BpmnParse#setSourceSystemId(String)}
   *   <li>{@link BpmnParse#setValidateProcess(boolean)}
   *   <li>{@link BpmnParse#setValidateSchema(boolean)}
   *   <li>{@link BpmnParse#getActivityBehaviorFactory()}
   *   <li>{@link BpmnParse#getBpmnModel()}
   *   <li>{@link BpmnParse#getBpmnParserHandlers()}
   *   <li>{@link BpmnParse#getCurrentFlowElement()}
   *   <li>{@link BpmnParse#getCurrentProcess()}
   *   <li>{@link BpmnParse#getCurrentProcessDefinition()}
   *   <li>{@link BpmnParse#getDeployment()}
   *   <li>{@link BpmnParse#getListenerFactory()}
   *   <li>{@link BpmnParse#getProcessDefinitions()}
   *   <li>{@link BpmnParse#getSequenceFlows()}
   *   <li>{@link BpmnParse#getTargetNamespace()}
   *   <li>{@link BpmnParse#isValidateProcess()}
   *   <li>{@link BpmnParse#isValidateSchema()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    BpmnParse actualDeploymentResult = bpmnParse.deployment(new DeploymentEntityImpl());
    BpmnParse actualNameResult = bpmnParse.name("Name");
    DefaultActivityBehaviorFactory activityBehaviorFactory = new DefaultActivityBehaviorFactory();
    bpmnParse.setActivityBehaviorFactory(activityBehaviorFactory);
    BpmnModel bpmnModel = new BpmnModel();
    bpmnParse.setBpmnModel(bpmnModel);
    BpmnParseHandlers bpmnParserHandlers = new BpmnParseHandlers();
    bpmnParse.setBpmnParserHandlers(bpmnParserHandlers);
    AdhocSubProcess currentFlowElement = new AdhocSubProcess();
    bpmnParse.setCurrentFlowElement(currentFlowElement);
    Process currentProcess = new Process();
    bpmnParse.setCurrentProcess(currentProcess);
    ProcessDefinitionEntityImpl currentProcessDefinition = new ProcessDefinitionEntityImpl();
    bpmnParse.setCurrentProcessDefinition(currentProcessDefinition);
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    bpmnParse.setDeployment(deployment);
    DefaultListenerFactory listenerFactory = new DefaultListenerFactory();
    bpmnParse.setListenerFactory(listenerFactory);
    BpmnParse actualSetSourceSystemIdResult = bpmnParse.setSourceSystemId("42");
    bpmnParse.setValidateProcess(true);
    bpmnParse.setValidateSchema(true);
    ActivityBehaviorFactory actualActivityBehaviorFactory = bpmnParse.getActivityBehaviorFactory();
    BpmnModel actualBpmnModel = bpmnParse.getBpmnModel();
    BpmnParseHandlers actualBpmnParserHandlers = bpmnParse.getBpmnParserHandlers();
    FlowElement actualCurrentFlowElement = bpmnParse.getCurrentFlowElement();
    Process actualCurrentProcess = bpmnParse.getCurrentProcess();
    ProcessDefinitionEntity actualCurrentProcessDefinition = bpmnParse.getCurrentProcessDefinition();
    DeploymentEntity actualDeployment = bpmnParse.getDeployment();
    ListenerFactory actualListenerFactory = bpmnParse.getListenerFactory();
    List<ProcessDefinitionEntity> actualProcessDefinitions = bpmnParse.getProcessDefinitions();
    bpmnParse.getSequenceFlows();
    bpmnParse.getTargetNamespace();
    boolean actualIsValidateProcessResult = bpmnParse.isValidateProcess();
    boolean actualIsValidateSchemaResult = bpmnParse.isValidateSchema();

    // Assert that nothing has changed
    assertTrue(actualProcessDefinitions.isEmpty());
    assertTrue(actualIsValidateProcessResult);
    assertTrue(actualIsValidateSchemaResult);
    assertSame(currentFlowElement, actualCurrentFlowElement);
    assertSame(bpmnModel, actualBpmnModel);
    assertSame(currentProcess, actualCurrentProcess);
    assertSame(bpmnParse, actualDeploymentResult);
    assertSame(bpmnParse, actualNameResult);
    assertSame(bpmnParse, actualSetSourceSystemIdResult);
    assertSame(bpmnParserHandlers, actualBpmnParserHandlers);
    assertSame(activityBehaviorFactory, actualActivityBehaviorFactory);
    assertSame(listenerFactory, actualListenerFactory);
    assertSame(deployment, actualDeployment);
    assertSame(currentProcessDefinition, actualCurrentProcessDefinition);
  }

  /**
   * Method under test: {@link BpmnParse#BpmnParse(BpmnParser)}
   */
  @Test
  public void testNewBpmnParse() {
    // Arrange and Act
    BpmnParse actualBpmnParse = new BpmnParse(new BpmnParser());

    // Assert
    assertNull(actualBpmnParse.getTargetNamespace());
    assertNull(actualBpmnParse.name);
    assertNull(actualBpmnParse.sourceSystemId);
    assertNull(actualBpmnParse.getSequenceFlows());
    assertNull(actualBpmnParse.getBpmnModel());
    assertNull(actualBpmnParse.getCurrentFlowElement());
    assertNull(actualBpmnParse.getCurrentProcess());
    assertNull(actualBpmnParse.getCurrentSubProcess());
    assertNull(actualBpmnParse.getBpmnParserHandlers());
    assertNull(actualBpmnParse.getActivityBehaviorFactory());
    assertNull(actualBpmnParse.getListenerFactory());
    assertNull(actualBpmnParse.getDeployment());
    assertNull(actualBpmnParse.getCurrentProcessDefinition());
    assertNull(actualBpmnParse.streamSource);
    assertTrue(actualBpmnParse.currentSubprocessStack.isEmpty());
    assertTrue(actualBpmnParse.getProcessDefinitions().isEmpty());
    assertTrue(actualBpmnParse.prefixs.isEmpty());
    assertTrue(actualBpmnParse.isValidateProcess());
    assertTrue(actualBpmnParse.isValidateSchema());
  }

  /**
   * Method under test: {@link BpmnParse#BpmnParse(BpmnParser)}
   */
  @Test
  public void testNewBpmnParse2() {
    // Arrange
    BpmnParser parser = new BpmnParser();
    parser.setBpmnParseFactory(mock(BpmnParseFactory.class));

    // Act
    BpmnParse actualBpmnParse = new BpmnParse(parser);

    // Assert
    assertNull(actualBpmnParse.getTargetNamespace());
    assertNull(actualBpmnParse.name);
    assertNull(actualBpmnParse.sourceSystemId);
    assertNull(actualBpmnParse.getSequenceFlows());
    assertNull(actualBpmnParse.getBpmnModel());
    assertNull(actualBpmnParse.getCurrentFlowElement());
    assertNull(actualBpmnParse.getCurrentProcess());
    assertNull(actualBpmnParse.getCurrentSubProcess());
    assertNull(actualBpmnParse.getBpmnParserHandlers());
    assertNull(actualBpmnParse.getActivityBehaviorFactory());
    assertNull(actualBpmnParse.getListenerFactory());
    assertNull(actualBpmnParse.getDeployment());
    assertNull(actualBpmnParse.getCurrentProcessDefinition());
    assertNull(actualBpmnParse.streamSource);
    assertTrue(actualBpmnParse.currentSubprocessStack.isEmpty());
    assertTrue(actualBpmnParse.getProcessDefinitions().isEmpty());
    assertTrue(actualBpmnParse.prefixs.isEmpty());
    assertTrue(actualBpmnParse.isValidateProcess());
    assertTrue(actualBpmnParse.isValidateSchema());
  }
}
