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

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Signal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SignalAndMessageDefinitionExportDiffblueTest {
  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages() throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef(null)
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Signal signal = mock(Signal.class);
    when(signal.getId()).thenReturn("42");
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(signal);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("http://activiti.org/bpmn", "scope", "Scope");
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(signal).getId();
    verify(model).getMessages();
    verify(model).getNamespace("bpmn2");
    verify(model).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages2() throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Signal signal = mock(Signal.class);
    when(signal.getId()).thenReturn("42");
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(signal);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("http://activiti.org/bpmn", "scope", "Scope");
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(signal).getId();
    verify(model).getMessages();
    verify(model).getNamespace("bpmn2");
    verify(model).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages3() throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name(null)
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Signal signal = mock(Signal.class);
    when(signal.getId()).thenReturn("42");
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(signal);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("http://activiti.org/bpmn", "scope", "Scope");
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(signal).getId();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespace("bpmn2");
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages4() throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Signal signal = mock(Signal.class);
    when(signal.getId()).thenReturn("42");
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(signal);

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(new CancelEventDefinition());

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(processList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("http://activiti.org/bpmn", "scope", "Scope");
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(signal).getId();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespace("bpmn2");
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link BpmnModel} {@link BpmnModel#getNamespace(String)} return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given '42'; when BpmnModel getNamespace(String) return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages_given42_whenBpmnModelGetNamespaceReturn42() throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Signal signal = mock(Signal.class);
    when(signal.getId()).thenReturn("42");
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(signal);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("42");
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("http://activiti.org/bpmn", "scope", "Scope");
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(signal).getId();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespace("bpmn2");
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link BpmnModel} {@link BpmnModel#getTargetNamespace()} return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given '42'; when BpmnModel getTargetNamespace() return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages_given42_whenBpmnModelGetTargetNamespaceReturn42()
      throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Signal signal = mock(Signal.class);
    when(signal.getId()).thenReturn("42");
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(signal);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getTargetNamespace()).thenReturn("42");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("http://activiti.org/bpmn", "scope", "Scope");
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(signal).getId();
    verify(model).getMessages();
    verify(model).getNamespace("bpmn2");
    verify(model).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link BoundaryEvent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given ArrayList() add BoundaryEvent (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages_givenArrayListAddBoundaryEvent() throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Signal signal = mock(Signal.class);
    when(signal.getId()).thenReturn("42");
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(signal);

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(new BoundaryEvent());

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(processList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("http://activiti.org/bpmn", "scope", "Scope");
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(signal).getId();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespace("bpmn2");
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Process} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given ArrayList() add Process (default constructor); then calls writeAttribute(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages_givenArrayListAddProcess_thenCallsWriteAttribute()
      throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Signal signal = mock(Signal.class);
    when(signal.getId()).thenReturn("42");
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(signal);

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(new Process());

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(processList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("http://activiti.org/bpmn", "scope", "Scope");
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(signal).getId();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespace("bpmn2");
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Signal#Signal(String, String)} with id is
   *       {@code 42} and name is {@code bpmn2}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given ArrayList() add Signal(String, String) with id is '42' and name is 'bpmn2'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages_givenArrayListAddSignalWithIdIs42AndNameIsBpmn2()
      throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(new Signal("42", "bpmn2"));

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespace("bpmn2");
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages_givenEmptyString() throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Signal signal = mock(Signal.class);
    when(signal.getId()).thenReturn("42");
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(signal);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("");
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("http://activiti.org/bpmn", "scope", "Scope");
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(signal).getId();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespace("bpmn2");
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given HashMap(); then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages_givenHashMap_thenCallsWriteAttribute() throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code Namespace}.
   *   <li>Then calls {@link BpmnModel#getNamespace(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given 'Namespace'; then calls getNamespace(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages_givenNamespace_thenCallsGetNamespace() throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespace("bpmn2");
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link BpmnModel} {@link BpmnModel#getTargetNamespace()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); given 'null'; when BpmnModel getTargetNamespace() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages_givenNull_whenBpmnModelGetTargetNamespaceReturnNull()
      throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Signal signal = mock(Signal.class);
    when(signal.getId()).thenReturn("42");
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(signal);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getTargetNamespace()).thenReturn(null);
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("http://activiti.org/bpmn", "scope", "Scope");
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(signal).getId();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespace("bpmn2");
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model).getTargetNamespace();
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link Process#findFlowElementsOfType(Class)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); then calls findFlowElementsOfType(Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages_thenCallsFindFlowElementsOfType() throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Signal signal = mock(Signal.class);
    when(signal.getId()).thenReturn("42");
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(signal);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());

    ArrayList<Process> processList = new ArrayList<>();
    processList.add(process);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(processList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("http://activiti.org/bpmn", "scope", "Scope");
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(signal).getId();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespace("bpmn2");
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); then calls writeAttribute(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages_thenCallsWriteAttribute() throws Exception {
    // Arrange
    ArrayList<Message> messageList = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messageList.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Signal signal = mock(Signal.class);
    when(signal.getId()).thenReturn("42");
    when(signal.getName()).thenReturn("Name");
    when(signal.getScope()).thenReturn("Scope");

    ArrayList<Signal> signalList = new ArrayList<>();
    signalList.add(signal);

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("bpmn2", "bpmn2");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getMessages()).thenReturn(messageList);
    when(model.getSignals()).thenReturn(signalList);
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeAttribute("http://activiti.org/bpmn", "scope", "Scope");
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("signal");
    verify(writer)
        .writeStartElement("bpmn2", "message", "http://www.omg.org/spec/BPMN/20100524/MODEL");
    verify(signal).getId();
    verify(model).getMessages();
    verify(model, atLeast(1)).getNamespace("bpmn2");
    verify(model, atLeast(1)).getNamespaces();
    verify(model).getProcesses();
    verify(model).getSignals();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(signal).getName();
    verify(signal, atLeast(1)).getScope();
  }

  /**
   * Test {@link SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link IndentingXMLStreamWriter#IndentingXMLStreamWriter(XMLStreamWriter)} with
   *       writer is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SignalAndMessageDefinitionExport#writeSignalsAndMessages(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalsAndMessages(BpmnModel, XMLStreamWriter); when IndentingXMLStreamWriter(XMLStreamWriter) with writer is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SignalAndMessageDefinitionExport.writeSignalsAndMessages(BpmnModel, XMLStreamWriter)"
  })
  void testWriteSignalsAndMessages_whenIndentingXMLStreamWriterWithWriterIsNull() throws Exception {
    // Arrange
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessages()).thenReturn(new ArrayList<>());
    when(model.getSignals()).thenReturn(new ArrayList<>());
    when(model.getProcesses()).thenReturn(new ArrayList<>());

    // Act
    SignalAndMessageDefinitionExport.writeSignalsAndMessages(
        model, new IndentingXMLStreamWriter(null));

    // Assert
    verify(model).getMessages();
    verify(model).getProcesses();
    verify(model).getSignals();
  }
}
