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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnXMLUtilDiffblueTest {
  /**
   * Test
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   * with {@code baseElement}, {@code didWriteExtensionStartElement},
   * {@code namespaceMap}, {@code xtw}.
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'namespaceMap', 'xtw'")
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementNamespaceMapXtw() throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act and Assert
    assertTrue(BpmnXMLUtil.writeExtensionElements(baseElement, true, namespaceMap, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   * with {@code baseElement}, {@code didWriteExtensionStartElement},
   * {@code namespaceMap}, {@code xtw}.
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'namespaceMap', 'xtw'")
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementNamespaceMapXtw2() throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act and Assert
    assertFalse(
        BpmnXMLUtil.writeExtensionElements(baseElement, false, namespaceMap, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   * with {@code baseElement}, {@code didWriteExtensionStartElement},
   * {@code namespaceMap}, {@code xtw}.
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'namespaceMap', 'xtw'")
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementNamespaceMapXtw3() throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();

    HashMap<String, String> namespaceMap = new HashMap<>();
    namespaceMap.computeIfPresent("foo", mock(BiFunction.class));

    // Act and Assert
    assertTrue(BpmnXMLUtil.writeExtensionElements(baseElement, true, namespaceMap, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, XMLStreamWriter)}
   * with {@code baseElement}, {@code didWriteExtensionStartElement}, {@code xtw}.
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElements(BaseElement, boolean, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'xtw'")
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementXtw() throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();

    // Act and Assert
    assertTrue(BpmnXMLUtil.writeExtensionElements(baseElement, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, XMLStreamWriter)}
   * with {@code baseElement}, {@code didWriteExtensionStartElement}, {@code xtw}.
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElements(BaseElement, boolean, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'xtw'")
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementXtw2() throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();

    // Act and Assert
    assertFalse(BpmnXMLUtil.writeExtensionElements(baseElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)")
  void testWriteExtensionElement() throws Exception {
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
   * Test
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor)")
  void testWriteExtensionElement_givenArrayListAddExtensionElement() throws Exception {
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
   * Test
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code Element Text}.</li>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCData(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); given 'Element Text'; then calls writeCData(String)")
  void testWriteExtensionElement_givenElementText_thenCallsWriteCData() throws Exception {
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
   * Test
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); given empty string")
  void testWriteExtensionElement_givenEmptyString() throws Exception {
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
   * Test
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getChildElements()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); then calls getChildElements()")
  void testWriteExtensionElement_thenCallsGetChildElements() throws Exception {
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
   * Test
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); then calls writeStartElement(String, String)")
  void testWriteExtensionElement_thenCallsWriteStartElement() throws Exception {
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
   * Test
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   * <ul>
   *   <li>Then throw {@link XMLStreamException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); then throw XMLStreamException")
  void testWriteExtensionElement_thenThrowXMLStreamException() throws Exception {
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
   * Test {@link BpmnXMLUtil#parseDelimitedList(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLUtil#parseDelimitedList(String)}
   */
  @Test
  @DisplayName("Test parseDelimitedList(String); when empty string; then return Empty")
  void testParseDelimitedList_whenEmptyString_thenReturnEmpty() {
    // Arrange and Act
    List<String> actualParseDelimitedListResult = BpmnXMLUtil.parseDelimitedList("");

    // Assert
    assertTrue(actualParseDelimitedListResult.isEmpty());
  }

  /**
   * Test {@link BpmnXMLUtil#parseDelimitedList(String)}.
   * <ul>
   *   <li>When {@code foo}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLUtil#parseDelimitedList(String)}
   */
  @Test
  @DisplayName("Test parseDelimitedList(String); when 'foo'; then return size is one")
  void testParseDelimitedList_whenFoo_thenReturnSizeIsOne() {
    // Arrange and Act
    List<String> actualParseDelimitedListResult = BpmnXMLUtil.parseDelimitedList("foo");

    // Assert
    assertEquals(1, actualParseDelimitedListResult.size());
    assertEquals("foo", actualParseDelimitedListResult.get(0));
  }

  /**
   * Test {@link BpmnXMLUtil#parseDelimitedList(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLUtil#parseDelimitedList(String)}
   */
  @Test
  @DisplayName("Test parseDelimitedList(String); when 'null'; then return Empty")
  void testParseDelimitedList_whenNull_thenReturnEmpty() {
    // Arrange and Act
    List<String> actualParseDelimitedListResult = BpmnXMLUtil.parseDelimitedList(null);

    // Assert
    assertTrue(actualParseDelimitedListResult.isEmpty());
  }

  /**
   * Test {@link BpmnXMLUtil#convertToDelimitedString(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code 42,foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); given '42'; when ArrayList() add '42'; then return '42,foo'")
  void testConvertToDelimitedString_given42_whenArrayListAdd42_thenReturn42Foo() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("42");
    stringList.add("foo");

    // Act and Assert
    assertEquals("42,foo", BpmnXMLUtil.convertToDelimitedString(stringList));
  }

  /**
   * Test {@link BpmnXMLUtil#convertToDelimitedString(List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code 42,foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); given ArrayList() add '42'; then return '42,foo'")
  void testConvertToDelimitedString_givenArrayListAdd42_thenReturn42Foo() {
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
   * Test {@link BpmnXMLUtil#convertToDelimitedString(List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} iterator.</li>
   *   <li>Then calls {@link COWArrayList#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); given ArrayList() iterator; then calls iterator()")
  void testConvertToDelimitedString_givenArrayListIterator_thenCallsIterator() {
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
   * Test {@link BpmnXMLUtil#convertToDelimitedString(List)}.
   * <ul>
   *   <li>Given {@code String List}.</li>
   *   <li>Then return {@code String List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); given 'String List'; then return 'String List'")
  void testConvertToDelimitedString_givenStringList_thenReturnStringList() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("String List");

    // Act and Assert
    assertEquals("String List", BpmnXMLUtil.convertToDelimitedString(stringList));
  }

  /**
   * Test {@link BpmnXMLUtil#convertToDelimitedString(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); when ArrayList(); then return empty string")
  void testConvertToDelimitedString_whenArrayList_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", BpmnXMLUtil.convertToDelimitedString(new ArrayList<>()));
  }

  /**
   * Test {@link BpmnXMLUtil#convertToDelimitedString(List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); when 'null'; then return empty string")
  void testConvertToDelimitedString_whenNull_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", BpmnXMLUtil.convertToDelimitedString(null));
  }

  /**
   * Test
   * {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])}
   * with {@code attributes}, {@code xtw}, {@code blackLists}.
   * <ul>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])}
   */
  @Test
  @DisplayName("Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'; then calls getName()")
  void testWriteCustomAttributesWithAttributesXtwBlackLists_thenCallsGetName() throws XMLStreamException {
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
   * Test
   * {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])}
   * with {@code attributes}, {@code xtw}, {@code namespaceMap},
   * {@code blackLists}.
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])}
   */
  @Test
  @DisplayName("Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists() throws XMLStreamException {
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
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with name is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName("Test isBlacklisted(ExtensionAttribute, List[]); given ExtensionAttribute(String) with name is '42'")
  void testIsBlacklisted_givenExtensionAttributeWithNameIs42() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("42"));

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with
   * {@code Name}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName("Test isBlacklisted(ExtensionAttribute, List[]); given ExtensionAttribute(String) with 'Name'; then return 'true'")
  void testIsBlacklisted_givenExtensionAttributeWithName_thenReturnTrue() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));

    // Act and Assert
    assertTrue(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String, String)} with
   * namespace is {@code Name} and {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName("Test isBlacklisted(ExtensionAttribute, List[]); given ExtensionAttribute(String, String) with namespace is 'Name' and 'Name'")
  void testIsBlacklisted_givenExtensionAttributeWithNamespaceIsNameAndName() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name", "Name"));

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   * <ul>
   *   <li>When {@link ExtensionAttribute#ExtensionAttribute(String)} with
   * {@code Name}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName("Test isBlacklisted(ExtensionAttribute, List[]); when ExtensionAttribute(String) with 'Name'; then return 'false'")
  void testIsBlacklisted_whenExtensionAttributeWithName_thenReturnFalse() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, new ArrayList<>()));
  }

  /**
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   * <ul>
   *   <li>When {@link ExtensionAttribute#ExtensionAttribute(String, String)} with
   * {@code Namespace} and {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName("Test isBlacklisted(ExtensionAttribute, List[]); when ExtensionAttribute(String, String) with 'Namespace' and 'Name'")
  void testIsBlacklisted_whenExtensionAttributeWithNamespaceAndName() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Namespace", "Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   * <ul>
   *   <li>When {@link ExtensionAttribute#ExtensionAttribute(String, String)} with
   * {@code Namespace} and {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName("Test isBlacklisted(ExtensionAttribute, List[]); when ExtensionAttribute(String, String) with 'Namespace' and 'Name'")
  void testIsBlacklisted_whenExtensionAttributeWithNamespaceAndName2() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Namespace", "Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name", "Name"));

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   * <ul>
   *   <li>When {@link ExtensionAttribute#ExtensionAttribute(String, String)} with
   * namespace is {@code Name} and {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName("Test isBlacklisted(ExtensionAttribute, List[]); when ExtensionAttribute(String, String) with namespace is 'Name' and 'Name'")
  void testIsBlacklisted_whenExtensionAttributeWithNamespaceIsNameAndName() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name", "Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name", "Name"));

    // Act and Assert
    assertTrue(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName("Test isBlacklisted(ExtensionAttribute, List[]); when 'null'; then return 'false'")
  void testIsBlacklisted_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(new ExtensionAttribute("Name"), null));
  }

  /**
   * Test
   * {@link BpmnXMLUtil#writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter)")
  void testWriteIncomingAndOutgoingFlowElement() throws Exception {
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
   * Test
   * {@link BpmnXMLUtil#writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link FlowNode#getIncomingFlows()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter); given ArrayList(); then calls getIncomingFlows()")
  void testWriteIncomingAndOutgoingFlowElement_givenArrayList_thenCallsGetIncomingFlows() throws Exception {
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
   * Test
   * {@link BpmnXMLUtil#writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter); then calls writeCharacters(String)")
  void testWriteIncomingAndOutgoingFlowElement_thenCallsWriteCharacters() throws Exception {
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
   * Test
   * {@link BpmnXMLUtil#writeIncomingElementChild(XMLStreamWriter, SequenceFlow)}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeIncomingElementChild(XMLStreamWriter, SequenceFlow)}
   */
  @Test
  @DisplayName("Test writeIncomingElementChild(XMLStreamWriter, SequenceFlow); then calls writeCharacters(String)")
  void testWriteIncomingElementChild_thenCallsWriteCharacters() throws Exception {
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
   * Test
   * {@link BpmnXMLUtil#writeOutgoingElementChild(XMLStreamWriter, SequenceFlow)}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnXMLUtil#writeOutgoingElementChild(XMLStreamWriter, SequenceFlow)}
   */
  @Test
  @DisplayName("Test writeOutgoingElementChild(XMLStreamWriter, SequenceFlow); then calls writeCharacters(String)")
  void testWriteOutgoingElementChild_thenCallsWriteCharacters() throws Exception {
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
