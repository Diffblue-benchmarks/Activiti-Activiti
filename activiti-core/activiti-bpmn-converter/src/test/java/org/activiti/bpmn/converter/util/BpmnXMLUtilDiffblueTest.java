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
package org.activiti.bpmn.converter.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.core.util.COWArrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnXMLUtilDiffblueTest {
  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeDefaultAttribute(String, String, XMLStreamWriter)}
   */
  @Test
  void testWriteDefaultAttribute() throws Exception {
    // Arrange
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeDefaultAttribute("Attribute Name", "42", xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("Attribute Name"), eq("42"));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeQualifiedAttribute(String, String, XMLStreamWriter)}
   */
  @Test
  void testWriteQualifiedAttribute() throws Exception {
    // Arrange
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeQualifiedAttribute("Attribute Name", "42", xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("Attribute Name"), eq("42"));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElements() throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act and Assert
    assertTrue(BpmnXMLUtil.writeExtensionElements(baseElement, true, namespaceMap, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElements2() throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act and Assert
    assertFalse(
        BpmnXMLUtil.writeExtensionElements(baseElement, false, namespaceMap, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElements3() throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();

    HashMap<String, String> namespaceMap = new HashMap<>();
    namespaceMap.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    assertTrue(BpmnXMLUtil.writeExtensionElements(baseElement, true, namespaceMap, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElements4() throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();

    // Act and Assert
    assertTrue(BpmnXMLUtil.writeExtensionElements(baseElement, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElements5() throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();

    // Act and Assert
    assertFalse(BpmnXMLUtil.writeExtensionElements(baseElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElement() throws Exception {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getName()).thenReturn("Name");
    HashMap<String, String> namespaceMap = new HashMap<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeExtensionElement(extensionElement, namespaceMap, xtw);

    // Assert
    verify(xtw).writeNamespace(eq("Namespace Prefix"), eq("Namespace"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("Namespace Prefix"), eq("Name"), eq("Namespace"));
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElement2() throws Exception {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn(null);
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getChildElements()).thenReturn(new HashMap<>());
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getName()).thenReturn("Name");
    HashMap<String, String> namespaceMap = new HashMap<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeExtensionElement(extensionElement, namespaceMap, xtw);

    // Assert
    verify(xtw).writeNamespace(eq("Namespace Prefix"), eq("Namespace"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("Namespace Prefix"), eq("Name"), eq("Namespace"));
    verify(extensionElement).getAttributes();
    verify(extensionElement).getChildElements();
    verify(extensionElement).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElement3() throws Exception {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("foo", new ArrayList<>());
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getName()).thenReturn("Name");
    HashMap<String, String> namespaceMap = new HashMap<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeExtensionElement(extensionElement, namespaceMap, xtw);

    // Assert
    verify(xtw).writeNamespace(eq("Namespace Prefix"), eq("Namespace"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("Namespace Prefix"), eq("Name"), eq("Namespace"));
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElement4() throws Exception {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getNamespacePrefix()).thenReturn(null);
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getName()).thenReturn("Name");
    HashMap<String, String> namespaceMap = new HashMap<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();

    // Act
    BpmnXMLUtil.writeExtensionElement(extensionElement, namespaceMap, xtw);

    // Assert that nothing has changed
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElement5() throws Exception {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getNamespacePrefix()).thenReturn("");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getName()).thenReturn("Name");
    HashMap<String, String> namespaceMap = new HashMap<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();

    // Act
    BpmnXMLUtil.writeExtensionElement(extensionElement, namespaceMap, xtw);

    // Assert that nothing has changed
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElement6() throws Exception {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("Name");
    HashMap<String, String> namespaceMap = new HashMap<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();

    // Act
    BpmnXMLUtil.writeExtensionElement(extensionElement, namespaceMap, xtw);

    // Assert that nothing has changed
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("Name"));
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElement7() throws Exception {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("Name");
    HashMap<String, String> namespaceMap = new HashMap<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLStreamException("foo")).when(xtw).writeStartElement(Mockito.<String>any());

    // Act and Assert
    assertThrows(XMLStreamException.class,
        () -> BpmnXMLUtil.writeExtensionElement(extensionElement, namespaceMap, xtw));
    verify(xtw).writeStartElement(eq("Name"));
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElement8() throws Exception {
    // Arrange
    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("", new ArrayList<>());
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn(null);
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getChildElements()).thenReturn(stringListMap);
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("Name");
    HashMap<String, String> namespaceMap = new HashMap<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();

    // Act
    BpmnXMLUtil.writeExtensionElement(extensionElement, namespaceMap, xtw);

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("Name"));
    verify(extensionElement).getAttributes();
    verify(extensionElement).getChildElements();
    verify(extensionElement).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElement9() throws Exception {
    // Arrange
    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("", extensionElementList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn(null);
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getChildElements()).thenReturn(stringListMap);
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("Name");
    HashMap<String, String> namespaceMap = new HashMap<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();

    // Act
    BpmnXMLUtil.writeExtensionElement(extensionElement, namespaceMap, xtw);

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("Name"));
    verify(extensionElement).getAttributes();
    verify(extensionElement).getChildElements();
    verify(extensionElement).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElement10() throws Exception {
    // Arrange
    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setName("Name");
    extensionElement.addAttribute(new ExtensionAttribute("Name"));

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("", extensionElementList);
    ExtensionElement extensionElement2 = mock(ExtensionElement.class);
    when(extensionElement2.getElementText()).thenReturn(null);
    when(extensionElement2.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement2.getChildElements()).thenReturn(stringListMap);
    when(extensionElement2.getNamespace()).thenReturn(null);
    when(extensionElement2.getName()).thenReturn("Name");
    HashMap<String, String> namespaceMap = new HashMap<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();

    // Act
    BpmnXMLUtil.writeExtensionElement(extensionElement2, namespaceMap, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(eq("Name"));
    verify(extensionElement2).getAttributes();
    verify(extensionElement2).getChildElements();
    verify(extensionElement2).getElementText();
    verify(extensionElement2, atLeast(1)).getName();
    verify(extensionElement2).getNamespace();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElement11() throws Exception {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    attribute.setValue("42");

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setName("Name");
    extensionElement.addAttribute(attribute);

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("", extensionElementList);
    ExtensionElement extensionElement2 = mock(ExtensionElement.class);
    when(extensionElement2.getElementText()).thenReturn(null);
    when(extensionElement2.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement2.getChildElements()).thenReturn(stringListMap);
    when(extensionElement2.getNamespace()).thenReturn(null);
    when(extensionElement2.getName()).thenReturn("Name");
    HashMap<String, String> namespaceMap = new HashMap<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();

    // Act
    BpmnXMLUtil.writeExtensionElement(extensionElement2, namespaceMap, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("Name"), eq("42"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(eq("Name"));
    verify(extensionElement2).getAttributes();
    verify(extensionElement2).getChildElements();
    verify(extensionElement2).getElementText();
    verify(extensionElement2, atLeast(1)).getName();
    verify(extensionElement2).getNamespace();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionElement12() throws Exception {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Namespace", "Name");
    attribute.setValue("42");

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setName("Name");
    extensionElement.addAttribute(attribute);

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("", extensionElementList);
    ExtensionElement extensionElement2 = mock(ExtensionElement.class);
    when(extensionElement2.getElementText()).thenReturn(null);
    when(extensionElement2.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement2.getChildElements()).thenReturn(stringListMap);
    when(extensionElement2.getNamespace()).thenReturn(null);
    when(extensionElement2.getName()).thenReturn("Name");
    HashMap<String, String> namespaceMap = new HashMap<>();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();

    // Act
    BpmnXMLUtil.writeExtensionElement(extensionElement2, namespaceMap, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("Namespace"), eq("Name"), eq("42"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(eq("Name"));
    verify(extensionElement2).getAttributes();
    verify(extensionElement2).getChildElements();
    verify(extensionElement2).getElementText();
    verify(extensionElement2, atLeast(1)).getName();
    verify(extensionElement2).getNamespace();
  }

  /**
   * Method under test: {@link BpmnXMLUtil#parseDelimitedList(String)}
   */
  @Test
  void testParseDelimitedList() {
    // Arrange and Act
    List<String> actualParseDelimitedListResult = BpmnXMLUtil.parseDelimitedList("foo");

    // Assert
    assertEquals(1, actualParseDelimitedListResult.size());
    assertEquals("foo", actualParseDelimitedListResult.get(0));
  }

  /**
   * Method under test: {@link BpmnXMLUtil#parseDelimitedList(String)}
   */
  @Test
  void testParseDelimitedList2() {
    // Arrange and Act
    List<String> actualParseDelimitedListResult = BpmnXMLUtil.parseDelimitedList(null);

    // Assert
    assertTrue(actualParseDelimitedListResult.isEmpty());
  }

  /**
   * Method under test: {@link BpmnXMLUtil#parseDelimitedList(String)}
   */
  @Test
  void testParseDelimitedList3() {
    // Arrange and Act
    List<String> actualParseDelimitedListResult = BpmnXMLUtil.parseDelimitedList("");

    // Assert
    assertTrue(actualParseDelimitedListResult.isEmpty());
  }

  /**
   * Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  void testConvertToDelimitedString() {
    // Arrange, Act and Assert
    assertEquals("", BpmnXMLUtil.convertToDelimitedString(new ArrayList<>()));
    assertEquals("", BpmnXMLUtil.convertToDelimitedString(null));
  }

  /**
   * Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  void testConvertToDelimitedString2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("String List");

    // Act and Assert
    assertEquals("String List", BpmnXMLUtil.convertToDelimitedString(stringList));
  }

  /**
   * Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  void testConvertToDelimitedString3() {
    // Arrange
    COWArrayList<String> stringList = mock(COWArrayList.class);

    ArrayList<String> stringList2 = new ArrayList<>();
    when(stringList.iterator()).thenReturn(stringList2.iterator());

    // Act
    String actualConvertToDelimitedStringResult = BpmnXMLUtil.convertToDelimitedString(stringList);

    // Assert
    verify(stringList).iterator();
    assertEquals("", actualConvertToDelimitedStringResult);
  }

  /**
   * Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  void testConvertToDelimitedString4() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("42");
    stringList.add("foo");
    COWArrayList<String> stringList2 = mock(COWArrayList.class);
    when(stringList2.iterator()).thenReturn(stringList.iterator());

    // Act
    String actualConvertToDelimitedStringResult = BpmnXMLUtil.convertToDelimitedString(stringList2);

    // Assert
    verify(stringList2).iterator();
    assertEquals("42,foo", actualConvertToDelimitedStringResult);
  }

  /**
   * Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  void testConvertToDelimitedString5() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("42");
    stringList.add("foo");

    // Act and Assert
    assertEquals("42,foo", BpmnXMLUtil.convertToDelimitedString(stringList));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])}
   */
  @Test
  void testWriteCustomAttributes() throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(null);
    HashMap<String, String> namespaceMap = new HashMap<>();

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(new ExtensionAttribute("Namespace", "Name"));

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, extensionAttributeList2);

    // Assert that nothing has changed
    verify(extensionAttribute).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])}
   */
  @Test
  void testWriteCustomAttributes2() throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(null);

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(new ExtensionAttribute("Namespace", "Name"));

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, extensionAttributeList2);

    // Assert that nothing has changed
    verify(extensionAttribute).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  void testIsBlacklisted() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  void testIsBlacklisted2() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));

    // Act and Assert
    assertTrue(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  void testIsBlacklisted3() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("42"));

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  void testIsBlacklisted4() {
    // Arrange, Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(new ExtensionAttribute("Name"), null));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  void testIsBlacklisted5() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Namespace", "Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  void testIsBlacklisted6() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name", "Name"));

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  void testIsBlacklisted7() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Namespace", "Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name", "Name"));

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  void testIsBlacklisted8() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name", "Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name", "Name"));

    // Act and Assert
    assertTrue(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter)}
   */
  @Test
  void testWriteIncomingAndOutgoingFlowElement() throws Exception {
    // Arrange
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getIncomingFlows()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(new ArrayList<>());

    // Act
    BpmnXMLUtil.writeIncomingAndOutgoingFlowElement(flowNode, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(flowNode).getIncomingFlows();
    verify(flowNode).getOutgoingFlows();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter)}
   */
  @Test
  void testWriteIncomingAndOutgoingFlowElement2() throws Exception {
    // Arrange
    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getIncomingFlows()).thenReturn(sequenceFlowList);
    when(flowNode.getOutgoingFlows()).thenReturn(new ArrayList<>());
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeIncomingAndOutgoingFlowElement(flowNode, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeCharacters((String) isNull());
    verify(writer).writeEndElement();
    verify(writer).writeStartElement(eq("bpmn2"), eq("incoming"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowNode, atLeast(1)).getIncomingFlows();
    verify(flowNode).getOutgoingFlows();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter)}
   */
  @Test
  void testWriteIncomingAndOutgoingFlowElement3() throws Exception {
    // Arrange
    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));

    ArrayList<SequenceFlow> sequenceFlowList2 = new ArrayList<>();
    sequenceFlowList2.add(new SequenceFlow("bpmn2", "bpmn2"));
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getIncomingFlows()).thenReturn(sequenceFlowList);
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList2);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeIncomingAndOutgoingFlowElement(flowNode, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeCharacters((String) isNull());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(flowNode, atLeast(1)).getIncomingFlows();
    verify(flowNode, atLeast(1)).getOutgoingFlows();
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeIncomingElementChild(XMLStreamWriter, SequenceFlow)}
   */
  @Test
  void testWriteIncomingElementChild() throws Exception {
    // Arrange
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeIncomingElementChild(xtw, new SequenceFlow("Source Ref", "Target Ref"));

    // Assert that nothing has changed
    verify(xtw).writeCharacters((String) isNull());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("bpmn2"), eq("incoming"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
  }

  /**
   * Method under test:
   * {@link BpmnXMLUtil#writeOutgoingElementChild(XMLStreamWriter, SequenceFlow)}
   */
  @Test
  void testWriteOutgoingElementChild() throws Exception {
    // Arrange
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeOutgoingElementChild(xtw, new SequenceFlow("Source Ref", "Target Ref"));

    // Assert that nothing has changed
    verify(xtw).writeCharacters((String) isNull());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("bpmn2"), eq("outgoing"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
  }
}
