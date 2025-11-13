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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessExportDiffblueTest {
  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ActivitiListener} (default constructor).
   *   <li>Then calls {@link Lane#getExtensionElements()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given ArrayList() add ActivitiListener (default constructor); then calls getExtensionElements()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenArrayListAddActivitiListener_thenCallsGetExtensionElements()
      throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(new HashMap<>());
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(activitiListenerList);
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(lane).getExtensionElements();
    verify(process).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(lane).getFlowReferences();
    verify(lane).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("not empty");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code bpmn2}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given ArrayList() add 'bpmn2'; then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenArrayListAddBpmn2_thenCallsWriteAttribute() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(new EventListener());

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    Lane lane = new Lane();
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process, atLeast(1)).getId();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EventListener} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given ArrayList() add EventListener (default constructor); then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenArrayListAddEventListener_thenCallsWriteAttribute() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(new EventListener());

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    Lane lane = new Lane();
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process, atLeast(1)).getId();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EventListener} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given ArrayList() add EventListener (default constructor); then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenArrayListAddEventListener_thenCallsWriteAttribute2() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(new EventListener());
    eventListenerList.add(new EventListener());

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    Lane lane = new Lane();
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process, atLeast(1)).getId();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default constructor).
   *   <li>Then calls {@link Lane#getExtensionElements()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor); then calls getExtensionElements()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenArrayListAddExtensionElement_thenCallsGetExtensionElements()
      throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", extensionElementList);

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(lane).getFlowReferences();
    verify(lane).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("not empty");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given ArrayList() add FieldExtension (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenArrayListAddFieldExtension() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

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
    when(activitiListener.getEvent()).thenReturn("not empty");

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(new HashMap<>());
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(activitiListenerList);
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getCustomPropertiesResolverImplementationType();
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getFieldExtensions();
    verify(activitiListener, atLeast(1)).getImplementationType();
    verify(activitiListener).getOnTransaction();
    verify(process).getAttributes();
    verify(lane).getExtensionElements();
    verify(process).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(lane).getFlowReferences();
    verify(lane).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("not empty");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code Flow References}.
   *   <li>Then calls {@link Lane#getExtensionElements()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given ArrayList() add 'Flow References'; then calls getExtensionElements()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenArrayListAddFlowReferences_thenCallsGetExtensionElements()
      throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(new HashMap<>());
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(lane).getExtensionElements();
    verify(process).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(lane).getFlowReferences();
    verify(lane).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("not empty");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code bpmn2} is {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link Lane#getExtensionElements()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given HashMap() 'bpmn2' is ArrayList(); then calls getExtensionElements()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenHashMapBpmn2IsArrayList_thenCallsGetExtensionElements()
      throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(lane).getFlowReferences();
    verify(lane).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("not empty");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code bpmn2} is {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link Lane#getExtensionElements()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given HashMap() 'bpmn2' is ArrayList(); then calls getExtensionElements()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenHashMapBpmn2IsArrayList_thenCallsGetExtensionElements2()
      throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(new HashMap<>());
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(stringListMap);
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(lane).getExtensionElements();
    verify(process).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(lane).getFlowReferences();
    verify(lane).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("not empty");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code bpmn2} is {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link Lane#getExtensionElements()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given HashMap() 'bpmn2' is ArrayList(); then calls getExtensionElements()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenHashMapBpmn2IsArrayList_thenCallsGetExtensionElements3()
      throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(new HashMap<>());
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(lane).getExtensionElements();
    verify(process, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(lane).getFlowReferences();
    verify(lane).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("not empty");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link Lane} (default constructor) Name is {@code not empty}.
   *   <li>Then calls {@link EventListener#getEntityType()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given Lane (default constructor) Name is 'not empty'; then calls getEntityType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenLaneNameIsNotEmpty_thenCallsGetEntityType() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    Lane lane = new Lane();
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process, atLeast(1)).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link Lane} (default constructor) Name is {@code not empty}.
   *   <li>Then calls {@link EventListener#getEntityType()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given Lane (default constructor) Name is 'not empty'; then calls getEntityType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenLaneNameIsNotEmpty_thenCallsGetEntityType2() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEntityType()).thenReturn("Entity Type");
    when(eventListener.getEvents()).thenReturn("Events");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(eventListener);

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    Lane lane = new Lane();
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(new Lane());
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process, atLeast(1)).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link ActivitiListener#getCustomPropertiesResolverImplementationType()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); then calls getCustomPropertiesResolverImplementationType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_thenCallsGetCustomPropertiesResolverImplementationType() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

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
    when(activitiListener.getEvent()).thenReturn("not empty");

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(new HashMap<>());
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("not empty");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("not empty");
    when(process.getName()).thenReturn("not empty");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getExecutionListeners()).thenReturn(activitiListenerList);
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement("extensionElements");
    verify(xtw, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(activitiListener, atLeast(1)).getCustomPropertiesResolverImplementationType();
    verify(activitiListener, atLeast(1)).getEvent();
    verify(activitiListener).getFieldExtensions();
    verify(activitiListener, atLeast(1)).getImplementationType();
    verify(activitiListener).getOnTransaction();
    verify(process).getAttributes();
    verify(lane).getExtensionElements();
    verify(process).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(eventListener).getEntityType();
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(lane).getFlowReferences();
    verify(lane).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("not empty");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process, atLeast(1)).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); when Process (default constructor); then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_whenProcess_thenCallsWriteAttribute() throws Exception {
    // Arrange
    Process process = new Process();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw)
        .writeStartElement("bpmn2", "process", "http://www.omg.org/spec/BPMN/20100524/MODEL");
  }
}
