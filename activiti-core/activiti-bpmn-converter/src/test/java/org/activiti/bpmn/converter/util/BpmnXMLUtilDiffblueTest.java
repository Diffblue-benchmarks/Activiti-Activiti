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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.SequenceFlow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnXMLUtilDiffblueTest {
  /**
   * Test {@link BpmnXMLUtil#writeDefaultAttribute(String, String, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeDefaultAttribute(String, String,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeDefaultAttribute(String, String, XMLStreamWriter); when '42'; then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeDefaultAttribute(String, String, XMLStreamWriter)"})
  void testWriteDefaultAttribute_when42_thenCallsWriteAttribute() throws Exception {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeDefaultAttribute("Attribute Name", "42", new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("Attribute Name", "42");
  }

  /**
   * Test {@link BpmnXMLUtil#writeQualifiedAttribute(String, String, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeQualifiedAttribute(String, String,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeQualifiedAttribute(String, String, XMLStreamWriter); then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeQualifiedAttribute(String, String, XMLStreamWriter)"})
  void testWriteQualifiedAttribute_thenCallsWriteAttribute() throws Exception {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeQualifiedAttribute(
        "Attribute Name", "42", new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("activiti", "http://activiti.org/bpmn", "Attribute Name", "42");
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   * with {@code baseElement}, {@code didWriteExtensionStartElement}, {@code namespaceMap}, {@code
   * xtw}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'namespaceMap', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnXMLUtil.writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementNamespaceMapXtw()
      throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act and Assert
    assertTrue(
        BpmnXMLUtil.writeExtensionElements(
            baseElement, true, namespaceMap, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   * with {@code baseElement}, {@code didWriteExtensionStartElement}, {@code namespaceMap}, {@code
   * xtw}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'namespaceMap', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnXMLUtil.writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementNamespaceMapXtw2()
      throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();

    // Act and Assert
    assertFalse(
        BpmnXMLUtil.writeExtensionElements(
            baseElement, false, null, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   * with {@code baseElement}, {@code didWriteExtensionStartElement}, {@code namespaceMap}, {@code
   * xtw}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'namespaceMap', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnXMLUtil.writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementNamespaceMapXtw3()
      throws Exception {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", new ArrayList<>());

    Builder builderResult = Message.builder();
    Message baseElement =
        builderResult
            .attributes(new HashMap<>())
            .extensionElements(extensionElements)
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build();
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act and Assert
    assertTrue(
        BpmnXMLUtil.writeExtensionElements(
            baseElement, true, namespaceMap, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)}
   * with {@code baseElement}, {@code didWriteExtensionStartElement}, {@code namespaceMap}, {@code
   * xtw}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'namespaceMap', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnXMLUtil.writeExtensionElements(BaseElement, boolean, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementNamespaceMapXtw4()
      throws Exception {
    // Arrange
    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", extensionElementList);

    Builder builderResult = Message.builder();
    Message baseElement =
        builderResult
            .attributes(new HashMap<>())
            .extensionElements(extensionElements)
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build();
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act and Assert
    assertTrue(
        BpmnXMLUtil.writeExtensionElements(
            baseElement, true, namespaceMap, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, XMLStreamWriter)} with
   * {@code baseElement}, {@code didWriteExtensionStartElement}, {@code xtw}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElements(BaseElement, boolean, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnXMLUtil.writeExtensionElements(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementXtw()
      throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();

    // Act and Assert
    assertTrue(
        BpmnXMLUtil.writeExtensionElements(baseElement, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, XMLStreamWriter)} with
   * {@code baseElement}, {@code didWriteExtensionStartElement}, {@code xtw}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElements(BaseElement, boolean, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnXMLUtil.writeExtensionElements(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementXtw2()
      throws Exception {
    // Arrange
    ActivitiListener baseElement = new ActivitiListener();

    // Act and Assert
    assertFalse(
        BpmnXMLUtil.writeExtensionElements(baseElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, XMLStreamWriter)} with
   * {@code baseElement}, {@code didWriteExtensionStartElement}, {@code xtw}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElements(BaseElement, boolean, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnXMLUtil.writeExtensionElements(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementXtw3()
      throws Exception {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", new ArrayList<>());

    ActivitiListener baseElement = new ActivitiListener();
    baseElement.setExtensionElements(extensionElements);

    // Act and Assert
    assertTrue(
        BpmnXMLUtil.writeExtensionElements(baseElement, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean, XMLStreamWriter)} with
   * {@code baseElement}, {@code didWriteExtensionStartElement}, {@code xtw}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElements(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElements(BaseElement, boolean, XMLStreamWriter) with 'baseElement', 'didWriteExtensionStartElement', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnXMLUtil.writeExtensionElements(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteExtensionElementsWithBaseElementDidWriteExtensionStartElementXtw4()
      throws Exception {
    // Arrange
    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", extensionElementList);

    ActivitiListener baseElement = new ActivitiListener();
    baseElement.setExtensionElements(extensionElements);

    // Act and Assert
    assertTrue(
        BpmnXMLUtil.writeExtensionElements(baseElement, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement() throws Exception {
    // Arrange
    ExtensionAttribute extensionAttribute = new ExtensionAttribute("Name");
    extensionAttribute.setValue("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", extensionAttributeList);

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLStreamException())
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            BpmnXMLUtil.writeExtensionElement(
                extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeAttribute("Name", "42");
    verify(writer).writeStartElement("not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement2() throws Exception {
    // Arrange
    ExtensionAttribute extensionAttribute = new ExtensionAttribute("Namespace", "Name");
    extensionAttribute.setValue("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", extensionAttributeList);

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLStreamException())
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            BpmnXMLUtil.writeExtensionElement(
                extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeAttribute("Namespace", "Name", "42");
    verify(writer).writeStartElement("not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement3() throws Exception {
    // Arrange
    ExtensionAttribute extensionAttribute = new ExtensionAttribute("Namespace", "Name");
    extensionAttribute.setNamespacePrefix("Namespace Prefix");
    extensionAttribute.setValue("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", extensionAttributeList);

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLStreamException())
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            BpmnXMLUtil.writeExtensionElement(
                extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(writer).writeStartElement("not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionAttribute#ExtensionAttribute()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); given ArrayList() add ExtensionAttribute()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_givenArrayListAddExtensionAttribute() throws Exception {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute());

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", extensionAttributeList);

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("foo");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doThrow(new XMLStreamException()).when(writer).writeCData(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            BpmnXMLUtil.writeExtensionElement(
                extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeCData("foo");
    verify(writer).writeStartElement("not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link
   *       ExtensionAttribute#ExtensionAttribute(String)} with {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); given ArrayList() add ExtensionAttribute(String) with 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_givenArrayListAddExtensionAttributeWithName() throws Exception {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", extensionAttributeList);

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("foo");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doThrow(new XMLStreamException()).when(writer).writeCData(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            BpmnXMLUtil.writeExtensionElement(
                extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeCData("foo");
    verify(writer).writeStartElement("not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code Element Text}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); given 'Element Text'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_givenElementText() throws Exception {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getNamespacePrefix()).thenReturn("not empty");
    when(extensionElement.getNamespace()).thenReturn("not empty");
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeExtensionElement(
        extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeNamespace("not empty", "not empty");
    verify(writer).writeCData("Element Text");
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("not empty", "not empty", "not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link ExtensionElement#getChildElements()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); given HashMap() 'foo' is ArrayList(); then calls getChildElements()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_givenHashMapFooIsArrayList_thenCallsGetChildElements()
      throws Exception {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("foo", new ArrayList<>());

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn(null);
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getChildElements()).thenReturn(new HashMap<>());
    when(extensionElement.getNamespacePrefix()).thenReturn("not empty");
    when(extensionElement.getNamespace()).thenReturn("not empty");
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeExtensionElement(
        extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeNamespace("not empty", "not empty");
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("not empty", "not empty", "not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement).getChildElements();
    verify(extensionElement).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link ExtensionElement#getChildElements()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); given HashMap() 'foo' is ArrayList(); then calls getChildElements()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_givenHashMapFooIsArrayList_thenCallsGetChildElements2()
      throws Exception {
    // Arrange
    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("foo", new ArrayList<>());

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn(null);
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getChildElements()).thenReturn(stringListMap);
    when(extensionElement.getNamespacePrefix()).thenReturn("not empty");
    when(extensionElement.getNamespace()).thenReturn("not empty");
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeExtensionElement(
        extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeNamespace("not empty", "not empty");
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("not empty", "not empty", "not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement).getChildElements();
    verify(extensionElement).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCData(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); given HashMap(); then calls writeCData(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_givenHashMap_thenCallsWriteCData() throws Exception {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("foo");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doThrow(new XMLStreamException()).when(writer).writeCData(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            BpmnXMLUtil.writeExtensionElement(
                extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeCData("foo");
    verify(writer).writeStartElement("not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getChildElements()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); then calls getChildElements()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_thenCallsGetChildElements() throws Exception {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn(null);
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getChildElements()).thenReturn(new HashMap<>());
    when(extensionElement.getNamespacePrefix()).thenReturn("not empty");
    when(extensionElement.getNamespace()).thenReturn("not empty");
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeExtensionElement(
        extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeNamespace("not empty", "not empty");
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("not empty", "not empty", "not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement).getChildElements();
    verify(extensionElement).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); then calls writeStartElement(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_thenCallsWriteStartElement() throws Exception {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("foo");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getNamespacePrefix()).thenReturn("not empty");
    when(extensionElement.getNamespace()).thenReturn("not empty");
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLStreamException()).when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            BpmnXMLUtil.writeExtensionElement(
                extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeNamespace("not empty", "not empty");
    verify(writer).writeCData("foo");
    verify(writer).writeStartElement("not empty", "not empty", "not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); then calls writeStartElement(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_thenCallsWriteStartElement2() throws Exception {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("foo");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getNamespacePrefix()).thenReturn(null);
    when(extensionElement.getNamespace()).thenReturn("not empty");
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doThrow(new XMLStreamException()).when(writer).writeCData(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            BpmnXMLUtil.writeExtensionElement(
                extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeCData("foo");
    verify(writer).writeStartElement("not empty", "not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link ExtensionElement} {@link ExtensionElement#getName()} return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); when ExtensionElement getName() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_whenExtensionElementGetNameReturnEmptyString() throws Exception {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("");
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act
    BpmnXMLUtil.writeExtensionElement(
        extensionElement, namespaceMap, new IndentingXMLStreamWriter(null));

    // Assert
    verify(extensionElement).getName();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link IndentingXMLStreamWriter} {@link
   *       IndentingXMLStreamWriter#writeAttribute(String, String)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); when IndentingXMLStreamWriter writeAttribute(String, String) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_whenIndentingXMLStreamWriterWriteAttributeDoesNothing()
      throws Exception {
    // Arrange
    ExtensionAttribute extensionAttribute = new ExtensionAttribute("Name");
    extensionAttribute.setValue("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", extensionAttributeList);

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("foo");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doThrow(new XMLStreamException()).when(writer).writeCData(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            BpmnXMLUtil.writeExtensionElement(
                extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeAttribute("Name", "42");
    verify(writer).writeCData("foo");
    verify(writer).writeStartElement("not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link IndentingXMLStreamWriter} {@link
   *       IndentingXMLStreamWriter#writeAttribute(String, String, String)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); when IndentingXMLStreamWriter writeAttribute(String, String, String) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_whenIndentingXMLStreamWriterWriteAttributeDoesNothing2()
      throws Exception {
    // Arrange
    ExtensionAttribute extensionAttribute = new ExtensionAttribute("Namespace", "Name");
    extensionAttribute.setValue("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", extensionAttributeList);

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("foo");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doThrow(new XMLStreamException()).when(writer).writeCData(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            BpmnXMLUtil.writeExtensionElement(
                extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeAttribute("Namespace", "Name", "42");
    verify(writer).writeCData("foo");
    verify(writer).writeStartElement("not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link IndentingXMLStreamWriter} {@link
   *       IndentingXMLStreamWriter#writeAttribute(String, String, String, String)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeExtensionElement(ExtensionElement, Map,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionElement(ExtensionElement, Map, XMLStreamWriter); when IndentingXMLStreamWriter writeAttribute(String, String, String, String) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeExtensionElement(ExtensionElement, Map, XMLStreamWriter)"
  })
  void testWriteExtensionElement_whenIndentingXMLStreamWriterWriteAttributeDoesNothing3()
      throws Exception {
    // Arrange
    ExtensionAttribute extensionAttribute = new ExtensionAttribute("Namespace", "Name");
    extensionAttribute.setNamespacePrefix("Namespace Prefix");
    extensionAttribute.setValue("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", extensionAttributeList);

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("foo");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getNamespace()).thenReturn(null);
    when(extensionElement.getName()).thenReturn("not empty");
    HashMap<String, String> namespaceMap = new HashMap<>();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    doThrow(new XMLStreamException()).when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () ->
            BpmnXMLUtil.writeExtensionElement(
                extensionElement, namespaceMap, new IndentingXMLStreamWriter(writer)));
    verify(writer).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(writer).writeCData("foo");
    verify(writer).writeStartElement("not empty");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test {@link BpmnXMLUtil#parseDelimitedList(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#parseDelimitedList(String)}
   */
  @Test
  @DisplayName("Test parseDelimitedList(String); when empty string; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnXMLUtil.parseDelimitedList(String)"})
  void testParseDelimitedList_whenEmptyString_thenReturnEmpty() {
    // Arrange and Act
    List<String> actualParseDelimitedListResult = BpmnXMLUtil.parseDelimitedList("");

    // Assert
    assertTrue(actualParseDelimitedListResult.isEmpty());
  }

  /**
   * Test {@link BpmnXMLUtil#parseDelimitedList(String)}.
   *
   * <ul>
   *   <li>When {@code foo}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#parseDelimitedList(String)}
   */
  @Test
  @DisplayName("Test parseDelimitedList(String); when 'foo'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnXMLUtil.parseDelimitedList(String)"})
  void testParseDelimitedList_whenFoo_thenReturnSizeIsOne() {
    // Arrange and Act
    List<String> actualParseDelimitedListResult = BpmnXMLUtil.parseDelimitedList("foo");

    // Assert
    assertEquals(1, actualParseDelimitedListResult.size());
    assertEquals("foo", actualParseDelimitedListResult.get(0));
  }

  /**
   * Test {@link BpmnXMLUtil#parseDelimitedList(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#parseDelimitedList(String)}
   */
  @Test
  @DisplayName("Test parseDelimitedList(String); when 'null'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnXMLUtil.parseDelimitedList(String)"})
  void testParseDelimitedList_whenNull_thenReturnEmpty() {
    // Arrange and Act
    List<String> actualParseDelimitedListResult = BpmnXMLUtil.parseDelimitedList(null);

    // Assert
    assertTrue(actualParseDelimitedListResult.isEmpty());
  }

  /**
   * Test {@link BpmnXMLUtil#convertToDelimitedString(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then return {@code 42,foo}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName(
      "Test convertToDelimitedString(List); given '42'; when ArrayList() add '42'; then return '42,foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnXMLUtil.convertToDelimitedString(List)"})
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
   *
   * <ul>
   *   <li>Given {@code String List}.
   *   <li>Then return {@code String List}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName(
      "Test convertToDelimitedString(List); given 'String List'; then return 'String List'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnXMLUtil.convertToDelimitedString(List)"})
  void testConvertToDelimitedString_givenStringList_thenReturnStringList() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("String List");

    // Act and Assert
    assertEquals("String List", BpmnXMLUtil.convertToDelimitedString(stringList));
  }

  /**
   * Test {@link BpmnXMLUtil#convertToDelimitedString(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); when ArrayList(); then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnXMLUtil.convertToDelimitedString(List)"})
  void testConvertToDelimitedString_whenArrayList_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", BpmnXMLUtil.convertToDelimitedString(new ArrayList<>()));
  }

  /**
   * Test {@link BpmnXMLUtil#convertToDelimitedString(List)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); when 'null'; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnXMLUtil.convertToDelimitedString(List)"})
  void testConvertToDelimitedString_whenNull_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", BpmnXMLUtil.convertToDelimitedString(null));
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists() throws XMLStreamException {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Namespace", "Name"));

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLStreamException())
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () -> BpmnXMLUtil.writeCustomAttributes(attributes, xtw, new ArrayList<>()));
    verify(writer).writeAttribute("Namespace", "Name", null);
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists2() throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLStreamException())
        .when(writer)
        .writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () -> BpmnXMLUtil.writeCustomAttributes(attributes, xtw, new ArrayList<>()));
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists3() throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(new ExtensionAttribute("Name"));

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, extensionAttributeList2);

    // Assert
    verify(writer).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute).getValue();
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists4() throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(new ExtensionAttribute("Name"));

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, extensionAttributeList2);

    // Assert
    verify(writer).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute).getValue();
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists5() throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);
    IndentingXMLStreamWriter writer =
        new IndentingXMLStreamWriter(mock(IndentingXMLStreamWriter.class));
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer);

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(new ExtensionAttribute("Namespace", "Name"));

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, extensionAttributeList2);

    // Assert
    verify(extensionAttribute).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists6() throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute());
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(new ExtensionAttribute("Name"));

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, extensionAttributeList2);

    // Assert
    verify(writer).writeAttribute(null, null);
    verify(writer).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute).getValue();
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists7() throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ExtensionAttribute extensionAttribute2 = mock(ExtensionAttribute.class);
    when(extensionAttribute2.getName()).thenReturn("Name");
    when(extensionAttribute2.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute2.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute2.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute2);
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(new ExtensionAttribute("Name"));

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, extensionAttributeList2);

    // Assert
    verify(writer, atLeast(1)).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute2, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute2, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute2, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute2).getValue();
    verify(extensionAttribute).getValue();
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists8() throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ExtensionAttribute extensionAttribute2 = mock(ExtensionAttribute.class);
    when(extensionAttribute2.getName()).thenReturn("Name");
    when(extensionAttribute2.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute2.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute2.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute2);
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);

    ExtensionAttribute extensionAttribute3 = new ExtensionAttribute("Name");
    extensionAttribute3.setNamespace("Name");

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(extensionAttribute3);

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, extensionAttributeList2);

    // Assert
    verify(writer, atLeast(1)).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute2, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute2, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute2, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute2).getValue();
    verify(extensionAttribute).getValue();
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists9() throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ExtensionAttribute extensionAttribute2 = mock(ExtensionAttribute.class);
    when(extensionAttribute2.getName()).thenReturn("Name");
    when(extensionAttribute2.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute2.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute2.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));
    extensionAttributeList.add(extensionAttribute2);
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);

    ExtensionAttribute extensionAttribute3 = new ExtensionAttribute("Name");
    extensionAttribute3.setNamespace("Name");

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(extensionAttribute3);

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, extensionAttributeList2);

    // Assert
    verify(writer).writeAttribute("Name", null);
    verify(writer, atLeast(1)).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute2, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute2, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute2, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute2).getValue();
    verify(extensionAttribute).getValue();
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'; then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists_thenCallsWriteAttribute()
      throws XMLStreamException {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, new ArrayList<>());

    // Assert
    verify(writer).writeAttribute("Name", null);
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'; then calls writeAttribute(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists_thenCallsWriteAttribute2()
      throws XMLStreamException {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Namespace", "Name"));

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, new ArrayList<>());

    // Assert
    verify(writer).writeAttribute("Namespace", "Name", null);
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'; then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists_thenCallsWriteAttribute3()
      throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, new ArrayList<>());

    // Assert
    verify(writer).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute).getValue();
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, List[])} with {@code
   * attributes}, {@code xtw}, {@code blackLists}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, List[]) with 'attributes', 'xtw', 'blackLists'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, List[])"})
  void testWriteCustomAttributesWithAttributesXtwBlackLists_whenNull() throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, new IndentingXMLStreamWriter(writer2), null);

    // Assert
    verify(writer).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute).getValue();
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists()
      throws XMLStreamException {
    // Arrange
    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(null);
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, new ArrayList<>());

    // Assert that nothing has changed
    assertTrue(namespaceMap.isEmpty());
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists2()
      throws XMLStreamException {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, new ArrayList<>());

    // Assert that nothing has changed
    verify(writer).writeAttribute("Name", null);
    assertTrue(namespaceMap.isEmpty());
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists3()
      throws XMLStreamException {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Namespace", "Name"));

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, new ArrayList<>());

    // Assert that nothing has changed
    verify(writer).writeAttribute("Namespace", "Name", null);
    assertTrue(namespaceMap.isEmpty());
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists4()
      throws XMLStreamException {
    // Arrange
    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Namespace", "Name"));

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLStreamException())
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () -> BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, new ArrayList<>()));
    verify(writer).writeAttribute("Namespace", "Name", null);
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists5()
      throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, new ArrayList<>());

    // Assert
    verify(writer).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute).getValue();
    assertEquals(1, namespaceMap.size());
    assertEquals("Namespace", namespaceMap.get("Namespace Prefix"));
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists6()
      throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doThrow(new XMLStreamException())
        .when(writer)
        .writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act and Assert
    assertThrows(
        XMLStreamException.class,
        () -> BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, new ArrayList<>()));
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists7()
      throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);
    HashMap<String, String> namespaceMap = new HashMap<>();

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(new ExtensionAttribute("Name"));

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, extensionAttributeList2);

    // Assert
    verify(writer).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute).getValue();
    assertEquals(1, namespaceMap.size());
    assertEquals("Namespace", namespaceMap.get("Namespace Prefix"));
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists8()
      throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);
    HashMap<String, String> namespaceMap = new HashMap<>();

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(new ExtensionAttribute("Name"));

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, extensionAttributeList2);

    // Assert
    verify(writer).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute).getValue();
    assertEquals(1, namespaceMap.size());
    assertEquals("Namespace", namespaceMap.get("Namespace Prefix"));
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists9()
      throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);
    IndentingXMLStreamWriter writer =
        new IndentingXMLStreamWriter(mock(IndentingXMLStreamWriter.class));
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer);
    HashMap<String, String> namespaceMap = new HashMap<>();

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(new ExtensionAttribute("Namespace", "Name"));

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, extensionAttributeList2);

    // Assert that nothing has changed
    verify(extensionAttribute).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    assertTrue(namespaceMap.isEmpty());
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists10()
      throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute());
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);
    HashMap<String, String> namespaceMap = new HashMap<>();

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(new ExtensionAttribute("Name"));

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, extensionAttributeList2);

    // Assert
    verify(writer).writeAttribute(null, null);
    verify(writer).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute).getValue();
    assertEquals(1, namespaceMap.size());
    assertEquals("Namespace", namespaceMap.get("Namespace Prefix"));
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists11()
      throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ExtensionAttribute extensionAttribute2 = mock(ExtensionAttribute.class);
    when(extensionAttribute2.getName()).thenReturn("Name");
    when(extensionAttribute2.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute2.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute2.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute2);
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);
    HashMap<String, String> namespaceMap = new HashMap<>();

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(new ExtensionAttribute("Name"));

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, extensionAttributeList2);

    // Assert
    verify(writer, atLeast(1)).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute2, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute2, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute2, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute2).getValue();
    verify(extensionAttribute).getValue();
    assertEquals(1, namespaceMap.size());
    assertEquals("Namespace", namespaceMap.get("Namespace Prefix"));
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists12()
      throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ExtensionAttribute extensionAttribute2 = mock(ExtensionAttribute.class);
    when(extensionAttribute2.getName()).thenReturn("Name");
    when(extensionAttribute2.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute2.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute2.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute2);
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);
    HashMap<String, String> namespaceMap = new HashMap<>();

    ExtensionAttribute extensionAttribute3 = new ExtensionAttribute("Name");
    extensionAttribute3.setNamespace("Name");

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(extensionAttribute3);

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, extensionAttributeList2);

    // Assert
    verify(writer, atLeast(1)).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute2, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute2, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute2, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute2).getValue();
    verify(extensionAttribute).getValue();
    assertEquals(1, namespaceMap.size());
    assertEquals("Namespace", namespaceMap.get("Namespace Prefix"));
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists13()
      throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ExtensionAttribute extensionAttribute2 = mock(ExtensionAttribute.class);
    when(extensionAttribute2.getName()).thenReturn("Name");
    when(extensionAttribute2.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute2.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute2.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));
    extensionAttributeList.add(extensionAttribute2);
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);
    HashMap<String, String> namespaceMap = new HashMap<>();

    ExtensionAttribute extensionAttribute3 = new ExtensionAttribute("Name");
    extensionAttribute3.setNamespace("Name");

    ArrayList<ExtensionAttribute> extensionAttributeList2 = new ArrayList<>();
    extensionAttributeList2.add(extensionAttribute3);

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, extensionAttributeList2);

    // Assert
    verify(writer).writeAttribute("Name", null);
    verify(writer, atLeast(1)).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute2, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute2, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute2, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute2).getValue();
    verify(extensionAttribute).getValue();
    assertEquals(1, namespaceMap.size());
    assertEquals("Namespace", namespaceMap.get("Namespace Prefix"));
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'; given ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists_givenArrayList()
      throws XMLStreamException {
    // Arrange
    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(new ArrayList<>());
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(null);
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, new ArrayList<>());

    // Assert that nothing has changed
    assertTrue(namespaceMap.isEmpty());
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'; given ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists_givenArrayList2()
      throws XMLStreamException {
    // Arrange
    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(new ArrayList<>());
    attributes.add(new ArrayList<>());
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(null);
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, new ArrayList<>());

    // Assert that nothing has changed
    assertTrue(namespaceMap.isEmpty());
  }

  /**
   * Test {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])} with
   * {@code attributes}, {@code xtw}, {@code namespaceMap}, {@code blackLists}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeCustomAttributes(Collection, XMLStreamWriter,
   * Map, List[])}
   */
  @Test
  @DisplayName(
      "Test writeCustomAttributes(Collection, XMLStreamWriter, Map, List[]) with 'attributes', 'xtw', 'namespaceMap', 'blackLists'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeCustomAttributes(Collection, XMLStreamWriter, Map, List[])"
  })
  void testWriteCustomAttributesWithAttributesXtwNamespaceMapBlackLists_whenNull()
      throws XMLStreamException {
    // Arrange
    ExtensionAttribute extensionAttribute = mock(ExtensionAttribute.class);
    when(extensionAttribute.getName()).thenReturn("Name");
    when(extensionAttribute.getNamespace()).thenReturn("Namespace");
    when(extensionAttribute.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionAttribute.getValue()).thenReturn("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    ArrayList<List<ExtensionAttribute>> attributes = new ArrayList<>();
    attributes.add(extensionAttributeList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer2);
    HashMap<String, String> namespaceMap = new HashMap<>();

    // Act
    BpmnXMLUtil.writeCustomAttributes(attributes, xtw, namespaceMap, null);

    // Assert
    verify(writer).writeAttribute("Namespace Prefix", "Namespace", "Name", "42");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(extensionAttribute).getName();
    verify(extensionAttribute, atLeast(1)).getNamespace();
    verify(extensionAttribute, atLeast(1)).getNamespacePrefix();
    verify(extensionAttribute).getValue();
    assertEquals(1, namespaceMap.size());
    assertEquals("Namespace", namespaceMap.get("Namespace Prefix"));
  }

  /**
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   *
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName(
      "Test isBlacklisted(ExtensionAttribute, List[]); given ExtensionAttribute(String) with 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnXMLUtil.isBlacklisted(ExtensionAttribute, List[])"})
  void testIsBlacklisted_givenExtensionAttributeWithName() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name", "Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   *
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with name is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName(
      "Test isBlacklisted(ExtensionAttribute, List[]); given ExtensionAttribute(String) with name is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnXMLUtil.isBlacklisted(ExtensionAttribute, List[])"})
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
   *
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with {@code Name} Namespace
   *       is {@code Namespace}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName(
      "Test isBlacklisted(ExtensionAttribute, List[]); given ExtensionAttribute(String) with 'Name' Namespace is 'Namespace'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnXMLUtil.isBlacklisted(ExtensionAttribute, List[])"})
  void testIsBlacklisted_givenExtensionAttributeWithNameNamespaceIsNamespace() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name", "Name");

    ExtensionAttribute extensionAttribute = new ExtensionAttribute("Name");
    extensionAttribute.setNamespace("Namespace");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   *
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String)} with {@code Name}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName(
      "Test isBlacklisted(ExtensionAttribute, List[]); given ExtensionAttribute(String) with 'Name'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnXMLUtil.isBlacklisted(ExtensionAttribute, List[])"})
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
   *
   * <ul>
   *   <li>Given {@link ExtensionAttribute#ExtensionAttribute(String, String)} with namespace is
   *       {@code Name} and {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName(
      "Test isBlacklisted(ExtensionAttribute, List[]); given ExtensionAttribute(String, String) with namespace is 'Name' and 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnXMLUtil.isBlacklisted(ExtensionAttribute, List[])"})
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
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName("Test isBlacklisted(ExtensionAttribute, List[]); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnXMLUtil.isBlacklisted(ExtensionAttribute, List[])"})
  void testIsBlacklisted_thenReturnTrue() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name", "Name");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name", "Name"));

    // Act and Assert
    assertTrue(BpmnXMLUtil.isBlacklisted(attribute, extensionAttributeList));
  }

  /**
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   *
   * <ul>
   *   <li>When {@link ExtensionAttribute#ExtensionAttribute(String)} with {@code Name}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName(
      "Test isBlacklisted(ExtensionAttribute, List[]); when ExtensionAttribute(String) with 'Name'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnXMLUtil.isBlacklisted(ExtensionAttribute, List[])"})
  void testIsBlacklisted_whenExtensionAttributeWithName_thenReturnFalse() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    // Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(attribute, new ArrayList<>()));
  }

  /**
   * Test {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#isBlacklisted(ExtensionAttribute, List[])}
   */
  @Test
  @DisplayName("Test isBlacklisted(ExtensionAttribute, List[]); when 'null'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnXMLUtil.isBlacklisted(ExtensionAttribute, List[])"})
  void testIsBlacklisted_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(BpmnXMLUtil.isBlacklisted(new ExtensionAttribute("Name"), null));
  }

  /**
   * Test {@link BpmnXMLUtil#writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeIncomingAndOutgoingFlowElement(FlowNode,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter); then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnXMLUtil.writeIncomingAndOutgoingFlowElement(FlowNode, XMLStreamWriter)"
  })
  void testWriteIncomingAndOutgoingFlowElement_thenCallsWriteCharacters() throws Exception {
    // Arrange
    ArrayList<SequenceFlow> incomingFlows = new ArrayList<>();
    incomingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();
    outgoingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    AdhocSubProcess flowNode = new AdhocSubProcess();
    flowNode.setIncomingFlows(incomingFlows);
    flowNode.setOutgoingFlows(outgoingFlows);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    BpmnXMLUtil.writeIncomingAndOutgoingFlowElement(
        flowNode, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeCharacters(null);
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
  }

  /**
   * Test {@link BpmnXMLUtil#writeIncomingElementChild(XMLStreamWriter, SequenceFlow)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeIncomingElementChild(XMLStreamWriter,
   * SequenceFlow)}
   */
  @Test
  @DisplayName(
      "Test writeIncomingElementChild(XMLStreamWriter, SequenceFlow); then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeIncomingElementChild(XMLStreamWriter, SequenceFlow)"})
  void testWriteIncomingElementChild_thenCallsWriteCharacters() throws Exception {
    // Arrange
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer);

    // Act
    BpmnXMLUtil.writeIncomingElementChild(xtw, new SequenceFlow("Source Ref", "Target Ref"));

    // Assert
    verify(writer).writeCharacters(null);
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement("bpmn2", "incoming", "http://www.omg.org/spec/BPMN/20100524/MODEL");
  }

  /**
   * Test {@link BpmnXMLUtil#writeOutgoingElementChild(XMLStreamWriter, SequenceFlow)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnXMLUtil#writeOutgoingElementChild(XMLStreamWriter,
   * SequenceFlow)}
   */
  @Test
  @DisplayName(
      "Test writeOutgoingElementChild(XMLStreamWriter, SequenceFlow); then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnXMLUtil.writeOutgoingElementChild(XMLStreamWriter, SequenceFlow)"})
  void testWriteOutgoingElementChild_thenCallsWriteCharacters() throws Exception {
    // Arrange
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BpmnXMLUtil.writeOutgoingElementChild(xtw, new SequenceFlow("Source Ref", "Target Ref"));

    // Assert
    verify(xtw).writeCharacters(null);
    verify(xtw).writeEndElement();
    verify(xtw)
        .writeStartElement("bpmn2", "outgoing", "http://www.omg.org/spec/BPMN/20100524/MODEL");
  }
}
