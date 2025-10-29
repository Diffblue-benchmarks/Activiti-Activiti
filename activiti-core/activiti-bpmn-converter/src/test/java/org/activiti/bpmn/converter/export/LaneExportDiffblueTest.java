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
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.Process;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LaneExportDiffblueTest {
  /**
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  void testWriteLanes() throws Exception {
    // Arrange
    Process process = mock(Process.class);
    when(process.getLanes()).thenReturn(new ArrayList<>());

    // Act
    LaneExport.writeLanes(process, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(process).getLanes();
  }

  /**
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  void testWriteLanes2() throws Exception {
    // Arrange
    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(new Lane());
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getLanes()).thenReturn(laneList);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    LaneExport.writeLanes(process, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("id"), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(process).getId();
    verify(process, atLeast(1)).getLanes();
  }

  /**
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  void testWriteLanes3() throws Exception {
    // Arrange
    Lane lane = mock(Lane.class);
    when(lane.getId()).thenReturn("42");
    when(lane.getName()).thenReturn("Name");
    when(lane.getFlowReferences()).thenReturn(new ArrayList<>());
    when(lane.getExtensionElements()).thenReturn(new HashMap<>());

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getLanes()).thenReturn(laneList);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    LaneExport.writeLanes(process, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(lane).getExtensionElements();
    verify(lane).getId();
    verify(process).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(process, atLeast(1)).getLanes();
  }

  /**
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  void testWriteLanes4() throws Exception {
    // Arrange
    Lane lane = mock(Lane.class);
    when(lane.getId()).thenReturn("42");
    when(lane.getName()).thenReturn("");
    when(lane.getFlowReferences()).thenReturn(new ArrayList<>());
    when(lane.getExtensionElements()).thenReturn(new HashMap<>());

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getLanes()).thenReturn(laneList);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    LaneExport.writeLanes(process, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("id"), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(lane).getExtensionElements();
    verify(lane).getId();
    verify(process).getId();
    verify(lane).getFlowReferences();
    verify(lane).getName();
    verify(process, atLeast(1)).getLanes();
  }

  /**
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  void testWriteLanes5() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
    Lane lane = mock(Lane.class);
    when(lane.getId()).thenReturn("42");
    when(lane.getName()).thenReturn("Name");
    when(lane.getFlowReferences()).thenReturn(stringList);
    when(lane.getExtensionElements()).thenReturn(new HashMap<>());

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getLanes()).thenReturn(laneList);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    LaneExport.writeLanes(process, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeCharacters(eq("bpmn2"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(lane).getExtensionElements();
    verify(lane).getId();
    verify(process).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(process, atLeast(1)).getLanes();
  }

  /**
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  void testWriteLanes6() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());
    Lane lane = mock(Lane.class);
    when(lane.getId()).thenReturn("42");
    when(lane.getName()).thenReturn("Name");
    when(lane.getFlowReferences()).thenReturn(stringList);
    when(lane.getExtensionElements()).thenReturn(stringListMap);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getLanes()).thenReturn(laneList);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    LaneExport.writeLanes(process, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeCharacters(eq("bpmn2"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(process, atLeast(1)).getLanes();
  }

  /**
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  void testWriteLanes7() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", extensionElementList);
    Lane lane = mock(Lane.class);
    when(lane.getId()).thenReturn("42");
    when(lane.getName()).thenReturn("Name");
    when(lane.getFlowReferences()).thenReturn(stringList);
    when(lane.getExtensionElements()).thenReturn(stringListMap);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getLanes()).thenReturn(laneList);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    LaneExport.writeLanes(process, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeCharacters(eq("bpmn2"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process).getId();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(process, atLeast(1)).getLanes();
  }

  /**
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  void testWriteLanes8() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
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
    when(lane.getId()).thenReturn("42");
    when(lane.getName()).thenReturn("Name");
    when(lane.getFlowReferences()).thenReturn(stringList);
    when(lane.getExtensionElements()).thenReturn(stringListMap);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getLanes()).thenReturn(laneList);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    LaneExport.writeLanes(process, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeNamespace(eq("Namespace Prefix"), eq("Namespace"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeCharacters(eq("bpmn2"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(extensionElement).getAttributes();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process).getId();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(process, atLeast(1)).getLanes();
  }

  /**
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  void testWriteLanes9() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
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
    when(lane.getId()).thenReturn("42");
    when(lane.getName()).thenReturn("Name");
    when(lane.getFlowReferences()).thenReturn(stringList);
    when(lane.getExtensionElements()).thenReturn(stringListMap);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getLanes()).thenReturn(laneList);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    LaneExport.writeLanes(process, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeCharacters(eq("bpmn2"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(extensionElement).getAttributes();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process).getId();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(process, atLeast(1)).getLanes();
  }

  /**
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  void testWriteLanes10() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");
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
    when(lane.getId()).thenReturn("42");
    when(lane.getName()).thenReturn("Name");
    when(lane.getFlowReferences()).thenReturn(stringList);
    when(lane.getExtensionElements()).thenReturn(stringListMap);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getLanes()).thenReturn(laneList);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    LaneExport.writeLanes(process, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeCharacters(eq("bpmn2"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(extensionElement).getAttributes();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process).getId();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(process, atLeast(1)).getLanes();
  }

  /**
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  void testWriteLanes11() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", new ArrayList<>());
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap2 = new HashMap<>();
    stringListMap2.put("bpmn2", extensionElementList);
    Lane lane = mock(Lane.class);
    when(lane.getId()).thenReturn("42");
    when(lane.getName()).thenReturn("Name");
    when(lane.getFlowReferences()).thenReturn(stringList);
    when(lane.getExtensionElements()).thenReturn(stringListMap2);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getLanes()).thenReturn(laneList);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    LaneExport.writeLanes(process, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeCharacters(eq("bpmn2"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(extensionElement).getAttributes();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process).getId();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(process, atLeast(1)).getLanes();
  }

  /**
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  void testWriteLanes12() throws Exception {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("bpmn2");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("bpmn2"));

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("bpmn2", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap2 = new HashMap<>();
    stringListMap2.put("bpmn2", extensionElementList);
    Lane lane = mock(Lane.class);
    when(lane.getId()).thenReturn("42");
    when(lane.getName()).thenReturn("Name");
    when(lane.getFlowReferences()).thenReturn(stringList);
    when(lane.getExtensionElements()).thenReturn(stringListMap2);

    ArrayList<Lane> laneList = new ArrayList<>();
    laneList.add(lane);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getLanes()).thenReturn(laneList);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    LaneExport.writeLanes(process, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeCharacters(eq("bpmn2"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw).writeStartElement(eq("extensionElements"));
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(extensionElement).getAttributes();
    verify(lane, atLeast(1)).getExtensionElements();
    verify(lane).getId();
    verify(process).getId();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
    verify(lane).getFlowReferences();
    verify(lane, atLeast(1)).getName();
    verify(process, atLeast(1)).getLanes();
  }
}
