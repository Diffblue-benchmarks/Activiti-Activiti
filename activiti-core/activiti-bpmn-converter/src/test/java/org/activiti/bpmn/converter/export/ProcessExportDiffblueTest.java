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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.EventListener;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.Process;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessExportDiffblueTest {
  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ActivitiListener} (default
   * constructor).</li>
   *   <li>Then calls {@link EventListener#getEntityType()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); given ArrayList() add ActivitiListener (default constructor); then calls getEntityType()")
  void testWriteProcess_givenArrayListAddActivitiListener_thenCallsGetEntityType() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(activitiListenerList);
    when(process.getLanes()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("candidateStarterGroups"),
        eq("bpmn2"));
    verify(xtw).writeCharacters(eq("Documentation"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EventListener} (default
   * constructor).</li>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); given ArrayList() add EventListener (default constructor); then calls writeStartElement(String)")
  void testWriteProcess_givenArrayListAddEventListener_thenCallsWriteStartElement() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(new EventListener());
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("candidateStarterGroups"),
        eq("bpmn2"));
    verify(xtw).writeCharacters(eq("Documentation"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EventListener} (default
   * constructor).</li>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); given ArrayList() add EventListener (default constructor); then calls writeStartElement(String)")
  void testWriteProcess_givenArrayListAddEventListener_thenCallsWriteStartElement2() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(new EventListener());
    eventListenerList.add(new EventListener());
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("candidateStarterGroups"),
        eq("bpmn2"));
    verify(xtw).writeCharacters(eq("Documentation"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); given ArrayList() add FieldExtension (default constructor)")
  void testWriteProcess_givenArrayListAddFieldExtension() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    ArrayList<FieldExtension> fieldExtensionList = new ArrayList<>();
    fieldExtensionList.add(new FieldExtension());
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getCustomPropertiesResolverImplementationType())
        .thenReturn("Custom Properties Resolver Implementation Type");
    when(activitiListener.getImplementationType()).thenReturn("Implementation Type");
    when(activitiListener.getOnTransaction()).thenReturn("On Transaction");
    when(activitiListener.getFieldExtensions()).thenReturn(fieldExtensionList);
    when(activitiListener.getEvent()).thenReturn("Event");

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(activitiListenerList);
    when(process.getLanes()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("candidateStarterGroups"),
        eq("bpmn2"));
    verify(xtw).writeCharacters(eq("Documentation"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getCustomPropertiesResolverImplementationType();
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getFieldExtensions();
    verify(activitiListener, atLeast(1)).getImplementationType();
    verify(activitiListener).getOnTransaction();
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link Lane} (default
   * constructor).</li>
   *   <li>Then calls {@link EventListener#getEntityType()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); given ArrayList() add Lane (default constructor); then calls getEntityType()")
  void testWriteProcess_givenArrayListAddLane_thenCallsGetEntityType() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(new Lane());
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(laneList);
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("candidateStarterGroups"),
        eq("bpmn2"));
    verify(xtw).writeCharacters(eq("Documentation"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process, atLeast(1)).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code Documentation}.</li>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); given 'Documentation'; then calls writeCharacters(String)")
  void testWriteProcess_givenDocumentation_thenCallsWriteCharacters() throws Exception {
    // Arrange
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeCharacters(eq("Documentation"));
    verify(xtw).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link Process} {@link Process#getDocumentation()} return empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); given empty string; when Process getDocumentation() return empty string")
  void testWriteProcess_givenEmptyString_whenProcessGetDocumentationReturnEmptyString() throws Exception {
    // Arrange
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(new ArrayList<>());
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeStartElement(eq("bpmn2"), eq("process"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(process).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link EventListener} {@link EventListener#getEntityType()} return
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); given EventListener getEntityType() return 'null'")
  void testWriteProcess_givenEventListenerGetEntityTypeReturnNull() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("null");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("candidateStarterGroups"),
        eq("bpmn2"));
    verify(xtw).writeCharacters(eq("Documentation"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code bpmn2} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link EventListener#getEntityType()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); given HashMap() 'bpmn2' is ArrayList(); then calls getEntityType()")
  void testWriteProcess_givenHashMapBpmn2IsArrayList_thenCallsGetEntityType() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(stringListMap);
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("candidateStarterGroups"),
        eq("bpmn2"));
    verify(xtw).writeCharacters(eq("Documentation"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code bpmn2} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link EventListener#getEntityType()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); given HashMap() 'bpmn2' is ArrayList(); then calls getEntityType()")
  void testWriteProcess_givenHashMapBpmn2IsArrayList_thenCallsGetEntityType2() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("candidateStarterGroups"),
        eq("bpmn2"));
    verify(xtw).writeCharacters(eq("Documentation"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process, atLeast(1)).getExtensionElements();
    verify(process).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link ActivitiListener#getCustomPropertiesResolverImplementationType()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); then calls getCustomPropertiesResolverImplementationType()")
  void testWriteProcess_thenCallsGetCustomPropertiesResolverImplementationType() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getCustomPropertiesResolverImplementationType())
        .thenReturn("Custom Properties Resolver Implementation Type");
    when(activitiListener.getImplementationType()).thenReturn("Implementation Type");
    when(activitiListener.getOnTransaction()).thenReturn("On Transaction");
    when(activitiListener.getFieldExtensions()).thenReturn(new ArrayList<>());
    when(activitiListener.getEvent()).thenReturn("Event");

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(activitiListenerList);
    when(process.getLanes()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("candidateStarterGroups"),
        eq("bpmn2"));
    verify(xtw).writeCharacters(eq("Documentation"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getCustomPropertiesResolverImplementationType();
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getFieldExtensions();
    verify(activitiListener, atLeast(1)).getImplementationType();
    verify(activitiListener).getOnTransaction();
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls {@link EventListener#getEntityType()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); then calls getEntityType()")
  void testWriteProcess_thenCallsGetEntityType() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getLanes()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("candidateStarterGroups"),
        eq("bpmn2"));
    verify(xtw).writeCharacters(eq("Documentation"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls {@link EventListener#getImplementation()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeProcess(Process, XMLStreamWriter); then calls getImplementation()")
  void testWriteProcess_thenCallsGetImplementation() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getImplementation()).thenReturn("Implementation");
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("class");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getCustomPropertiesResolverImplementationType())
        .thenReturn("Custom Properties Resolver Implementation Type");
    when(activitiListener.getImplementationType()).thenReturn("Implementation Type");
    when(activitiListener.getOnTransaction()).thenReturn("On Transaction");
    when(activitiListener.getFieldExtensions()).thenReturn(new ArrayList<>());
    when(activitiListener.getEvent()).thenReturn("Event");

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getDocumentation()).thenReturn("Documentation");
    when(process.getName()).thenReturn("Name");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(new ArrayList<>());
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(activitiListenerList);
    when(process.getLanes()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("candidateStarterGroups"),
        eq("bpmn2"));
    verify(xtw).writeCharacters(eq("Documentation"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getCustomPropertiesResolverImplementationType();
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getFieldExtensions();
    verify(activitiListener, atLeast(1)).getImplementationType();
    verify(activitiListener).getOnTransaction();
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener).getImplementation();
    verify(eventListener).getImplementationType();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }
}
