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
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given ArrayList() add ActivitiListener (default constructor); then calls writeStartElement(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenArrayListAddActivitiListener_thenCallsWriteStartElement()
      throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(activitiListenerList);
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
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

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    Lane lane = new Lane();
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process, atLeast(1)).getId();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EventListener} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given ArrayList() add EventListener (default constructor); then calls writeStartElement(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenArrayListAddEventListener_thenCallsWriteStartElement()
      throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<EventListener> eventListenerList = new ArrayList<>();
    eventListenerList.add(new EventListener());

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EventListener} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given ArrayList() add EventListener (default constructor); then calls writeStartElement(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenArrayListAddEventListener_thenCallsWriteStartElement2()
      throws Exception {
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

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(eventListenerList);
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor); then calls writeStartElement(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenArrayListAddExtensionElement_thenCallsWriteStartElement()
      throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", extensionElementList);

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
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

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(new HashMap<>());
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(process).getAttributes();
    verify(lane).getExtensionElements();
    verify(process).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code bpmn2}.
   *   <li>When {@link Process} {@link Process#getDocumentation()} return {@code bpmn2}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given 'bpmn2'; when Process getDocumentation() return 'bpmn2'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenBpmn2_whenProcessGetDocumentationReturnBpmn2() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn("bpmn2");
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process, atLeast(1)).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code bpmn2}.
   *   <li>When {@link Process} {@link Process#getName()} return {@code bpmn2}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given 'bpmn2'; when Process getName() return 'bpmn2'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenBpmn2_whenProcessGetNameReturnBpmn2() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn("bpmn2");
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
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
   *   <li>Given {@link ExtensionElement} {@link ExtensionElement#getNamespace()} return empty
   *       string.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given ExtensionElement getNamespace() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenExtensionElementGetNamespaceReturnEmptyString() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", extensionElementList);

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer).writeCData("Element Text");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(extensionElement).getAttributes();
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code bpmn2} is {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given HashMap() 'bpmn2' is ArrayList(); then calls writeStartElement(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenHashMapBpmn2IsArrayList_thenCallsWriteStartElement() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code bpmn2} is {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given HashMap() 'bpmn2' is ArrayList(); then calls writeStartElement(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenHashMapBpmn2IsArrayList_thenCallsWriteStartElement2()
      throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    HashMap<String, List<ExtensionAttribute>> stringListMap2 = new HashMap<>();
    stringListMap2.put("bpmn2", new ArrayList<>());

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(stringListMap2);
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link Lane} (default constructor) Name is empty string.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); given Lane (default constructor) Name is empty string; then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_givenLaneNameIsEmptyString_thenCallsWriteAttribute() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    Lane lane = new Lane();
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(process, atLeast(1)).getId();
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeNamespace(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); then calls writeNamespace(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_thenCallsWriteNamespace() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", extensionElementList);

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(writer).writeCData("Element Text");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(extensionElement).getAttributes();
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeNamespace(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); then calls writeNamespace(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_thenCallsWriteNamespace2() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap2 = new HashMap<>();
    stringListMap2.put("bpmn2", extensionElementList);

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap2);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(writer).writeCData("Element Text");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(extensionElement).getAttributes();
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExport#writeProcess(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeProcess(Process, XMLStreamWriter); then calls writeStartElement(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessExport.writeProcess(Process, XMLStreamWriter)"})
  void testWriteProcess_thenCallsWriteStartElement() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("2020-03-01");

    ArrayList<String> stringList2 = new ArrayList<>();
    stringList2.add("2020-03-01");

    ArrayList<String> flowReferences = new ArrayList<>();
    flowReferences.add("Lanes");

    ArrayList<String> stringList3 = new ArrayList<>();
    stringList3.add("Flow References");

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", extensionElementList);

    Lane lane = mock(Lane.class);
    when(lane.getName()).thenReturn("not empty");
    when(lane.getFlowReferences()).thenReturn(stringList3);
    when(lane.getId()).thenReturn("42");
    when(lane.getExtensionElements()).thenReturn(stringListMap);
    doNothing().when(lane).setFlowReferences(Mockito.<List<String>>any());
    doNothing().when(lane).setName(Mockito.<String>any());
    lane.setName("");
    lane.setFlowReferences(flowReferences);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);

    Process process = mock(Process.class);
    when(process.getDocumentation()).thenReturn(null);
    when(process.getName()).thenReturn(null);
    when(process.getCandidateStarterGroups()).thenReturn(stringList);
    when(process.getCandidateStarterUsers()).thenReturn(stringList2);
    when(process.getLanes()).thenReturn(laneList);
    when(process.isExecutable()).thenReturn(true);
    when(process.getId()).thenReturn("42");
    when(process.getEventListeners()).thenReturn(new ArrayList<>());
    when(process.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(process.getAttributes()).thenReturn(new HashMap<>());
    when(process.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            eq("2020-03-01"));
    verify(writer).writeCData("Element Text");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer).writeStartElement("extensionElements");
    verify(writer).writeStartElement("Namespace", "Name");
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(extensionElement).getAttributes();
    verify(process).getAttributes();
    verify(process).getExtensionElements();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process, atLeast(1)).getId();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(lane).setFlowReferences(isA(List.class));
    verify(lane).setName("");
    verify(process, atLeast(1)).getCandidateStarterGroups();
    verify(process, atLeast(1)).getCandidateStarterUsers();
    verify(process).getDocumentation();
    verify(process).getEventListeners();
    verify(process).getExecutionListeners();
    verify(process, atLeast(1)).getLanes();
    verify(process).getName();
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

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    ProcessExport.writeProcess(process, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer)
        .writeStartElement("bpmn2", "process", "http://www.omg.org/spec/BPMN/20100524/MODEL");
  }
}
