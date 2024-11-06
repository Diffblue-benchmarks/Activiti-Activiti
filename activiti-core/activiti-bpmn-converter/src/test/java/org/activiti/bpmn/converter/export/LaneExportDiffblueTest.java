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
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.Process;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LaneExportDiffblueTest {
  /**
   * Test {@link LaneExport#writeLanes(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code bpmn2}.</li>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeLanes(Process, XMLStreamWriter); given ArrayList() add 'bpmn2'; then calls writeCharacters(String)")
  void testWriteLanes_givenArrayListAddBpmn2_thenCallsWriteCharacters() throws Exception {
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
   * Test {@link LaneExport#writeLanes(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ExtensionAttribute#ExtensionAttribute(String)} with name is
   * {@code bpmn2}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeLanes(Process, XMLStreamWriter); given ArrayList() add ExtensionAttribute(String) with name is 'bpmn2'")
  void testWriteLanes_givenArrayListAddExtensionAttributeWithNameIsBpmn2() throws Exception {
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

  /**
   * Test {@link LaneExport#writeLanes(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default
   * constructor).</li>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeLanes(Process, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor); then calls writeStartElement(String)")
  void testWriteLanes_givenArrayListAddExtensionElement_thenCallsWriteStartElement() throws Exception {
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
   * Test {@link LaneExport#writeLanes(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>When
   * {@link IndentingXMLStreamWriter#IndentingXMLStreamWriter(XMLStreamWriter)}
   * with writer is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeLanes(Process, XMLStreamWriter); given ArrayList(); when IndentingXMLStreamWriter(XMLStreamWriter) with writer is 'null'")
  void testWriteLanes_givenArrayList_whenIndentingXMLStreamWriterWithWriterIsNull() throws Exception {
    // Arrange
    Process process = mock(Process.class);
    when(process.getLanes()).thenReturn(new ArrayList<>());

    // Act
    LaneExport.writeLanes(process, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(process).getLanes();
  }

  /**
   * Test {@link LaneExport#writeLanes(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ExtensionElement} {@link ExtensionElement#getNamespace()}
   * return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeLanes(Process, XMLStreamWriter); given ExtensionElement getNamespace() return empty string")
  void testWriteLanes_givenExtensionElementGetNamespaceReturnEmptyString() throws Exception {
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
   * Test {@link LaneExport#writeLanes(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code bpmn2} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeLanes(Process, XMLStreamWriter); given HashMap() 'bpmn2' is ArrayList(); then calls writeStartElement(String)")
  void testWriteLanes_givenHashMapBpmn2IsArrayList_thenCallsWriteStartElement() throws Exception {
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
   * Test {@link LaneExport#writeLanes(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link Lane} {@link Lane#getName()} return empty string.</li>
   *   <li>Then calls {@link BaseElement#getExtensionElements()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeLanes(Process, XMLStreamWriter); given Lane getName() return empty string; then calls getExtensionElements()")
  void testWriteLanes_givenLaneGetNameReturnEmptyString_thenCallsGetExtensionElements() throws Exception {
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
   * Test {@link LaneExport#writeLanes(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link Lane} {@link Lane#getName()} return {@code Name}.</li>
   *   <li>Then calls {@link BaseElement#getExtensionElements()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeLanes(Process, XMLStreamWriter); given Lane getName() return 'Name'; then calls getExtensionElements()")
  void testWriteLanes_givenLaneGetNameReturnName_thenCallsGetExtensionElements() throws Exception {
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
   * Test {@link LaneExport#writeLanes(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeLanes(Process, XMLStreamWriter); then calls writeStartElement(String, String)")
  void testWriteLanes_thenCallsWriteStartElement() throws Exception {
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
   * Test {@link LaneExport#writeLanes(Process, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LaneExport#writeLanes(Process, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeLanes(Process, XMLStreamWriter); then calls writeStartElement(String, String)")
  void testWriteLanes_thenCallsWriteStartElement2() throws Exception {
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
}
