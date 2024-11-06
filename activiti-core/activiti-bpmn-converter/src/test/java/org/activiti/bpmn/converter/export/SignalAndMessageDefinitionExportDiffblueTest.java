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
package org.activiti.bpmn.converter.export;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SignalAndMessageDefinitionExportDiffblueTest {
  /**
   * Test
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter)")
  void testWriteSignalsAndMessages() throws Exception {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(new CancelEventDefinition());

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(processList);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getMessages();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter)")
  void testWriteSignalsAndMessages2() throws Exception {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(new SignalEventDefinition());

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(processList);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getMessages();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter)")
  void testWriteSignalsAndMessages3() throws Exception {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(new MessageEventDefinition());

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(processList);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getMessages();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter)")
  void testWriteSignalsAndMessages4() throws Exception {
    // Arrange
    SignalEventDefinition eventDefinition = mock(SignalEventDefinition.class);
    when(eventDefinition.getSignalRef()).thenReturn("");

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(eventDefinition);

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(processList);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getMessages();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(eventDefinition).getSignalRef();
  }

  /**
   * Test
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link BoundaryEvent} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given ArrayList() add BoundaryEvent (default constructor)")
  void testWriteSignalsAndMessages_givenArrayListAddBoundaryEvent() throws Exception {
    // Arrange
    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(new BoundaryEvent());
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(processList);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getMessages();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Process} (default
   * constructor).</li>
   *   <li>Then calls {@link BpmnModel#getMessages()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given ArrayList() add Process (default constructor); then calls getMessages()")
  void testWriteSignalsAndMessages_givenArrayListAddProcess_thenCallsGetMessages() throws Exception {
    // Arrange
    ArrayList<Process> processList = new ArrayList<>();
    processList.add(new Process());
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(processList);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getMessages();
    verify(model).getProcesses();
    verify(model).getSignals();
  }

  /**
   * Test
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link BpmnModel#getMessages()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given ArrayList(); then calls getMessages()")
  void testWriteSignalsAndMessages_givenArrayList_thenCallsGetMessages() throws Exception {
    // Arrange
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getMessages();
    verify(model).getProcesses();
    verify(model).getSignals();
  }

  /**
   * Test
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>Then calls {@link BpmnModel#addSignal(Signal)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given 'false'; then calls addSignal(Signal)")
  void testWriteSignalsAndMessages_givenFalse_thenCallsAddSignal() throws Exception {
    // Arrange
    SignalEventDefinition eventDefinition = mock(SignalEventDefinition.class);
    when(eventDefinition.getSignalRef()).thenReturn("Signal Ref");

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(eventDefinition);

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);
    BpmnModel model = mock(BpmnModel.class);
    when(model.containsSignalId(Mockito.<String>any())).thenReturn(false);
    doNothing().when(model).addSignal(Mockito.<Signal>any());
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(processList);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(model, new IndentingXMLStreamWriter(null));

    // Assert
    verify(model).addSignal(isA(Signal.class));
    verify(model).containsSignalId(eq("Signal Ref"));
    verify(model).getMessages();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(eventDefinition, atLeast(1)).getSignalRef();
  }

  /**
   * Test
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link BpmnModel} {@link BpmnModel#containsSignalId(String)} return
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given 'true'; when BpmnModel containsSignalId(String) return 'true'")
  void testWriteSignalsAndMessages_givenTrue_whenBpmnModelContainsSignalIdReturnTrue() throws Exception {
    // Arrange
    SignalEventDefinition eventDefinition = mock(SignalEventDefinition.class);
    when(eventDefinition.getSignalRef()).thenReturn("Signal Ref");

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(eventDefinition);

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);
    BpmnModel model = mock(BpmnModel.class);
    when(model.containsSignalId(Mockito.<String>any())).thenReturn(true);
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(processList);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).containsSignalId(eq("Signal Ref"));
    verify(model).getMessages();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(eventDefinition, atLeast(1)).getSignalRef();
  }

  /**
   * Test
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls {@link Process#findFlowElementsOfType(Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); then calls findFlowElementsOfType(Class)")
  void testWriteSignalsAndMessages_thenCallsFindFlowElementsOfType() throws Exception {
    // Arrange
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(processList);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getMessages();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(process).findFlowElementsOfType(isA(Class.class));
  }
}
