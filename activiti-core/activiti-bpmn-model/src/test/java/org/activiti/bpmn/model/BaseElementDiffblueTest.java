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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class BaseElementDiffblueTest {
  /**
   * Method under test: {@link BaseElement#getId()}
   */
  @Test
  public void testGetId() {
    // Arrange, Act and Assert
    assertNull((new ActivitiListener()).getId());
  }

  /**
   * Method under test: {@link BaseElement#getId()}
   */
  @Test
  public void testGetId2() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setExtensionElements(extensionElements);

    // Act and Assert
    assertNull(activitiListener.getId());
  }

  /**
   * Method under test: {@link BaseElement#setId(String)}
   */
  @Test
  public void testSetId() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.setId("42");

    // Assert
    assertEquals("42", activitiListener.getId());
  }

  /**
   * Method under test: {@link BaseElement#getXmlRowNumber()}
   */
  @Test
  public void testGetXmlRowNumber() {
    // Arrange, Act and Assert
    assertEquals(0, (new ActivitiListener()).getXmlRowNumber());
  }

  /**
   * Method under test: {@link BaseElement#getXmlRowNumber()}
   */
  @Test
  public void testGetXmlRowNumber2() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setExtensionElements(extensionElements);

    // Act and Assert
    assertEquals(0, activitiListener.getXmlRowNumber());
  }

  /**
   * Method under test: {@link BaseElement#setXmlRowNumber(int)}
   */
  @Test
  public void testSetXmlRowNumber() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.setXmlRowNumber(10);

    // Assert
    assertEquals(10, activitiListener.getXmlRowNumber());
  }

  /**
   * Method under test: {@link BaseElement#getXmlColumnNumber()}
   */
  @Test
  public void testGetXmlColumnNumber() {
    // Arrange, Act and Assert
    assertEquals(0, (new ActivitiListener()).getXmlColumnNumber());
  }

  /**
   * Method under test: {@link BaseElement#getXmlColumnNumber()}
   */
  @Test
  public void testGetXmlColumnNumber2() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setExtensionElements(extensionElements);

    // Act and Assert
    assertEquals(0, activitiListener.getXmlColumnNumber());
  }

  /**
   * Method under test: {@link BaseElement#setXmlColumnNumber(int)}
   */
  @Test
  public void testSetXmlColumnNumber() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.setXmlColumnNumber(10);

    // Assert
    assertEquals(10, activitiListener.getXmlColumnNumber());
  }

  /**
   * Method under test: {@link BaseElement#getExtensionElements()}
   */
  @Test
  public void testGetExtensionElements() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    Map<String, List<ExtensionElement>> actualExtensionElements = activitiListener.getExtensionElements();

    // Assert
    assertTrue(actualExtensionElements.isEmpty());
    assertSame(activitiListener.extensionElements, actualExtensionElements);
  }

  /**
   * Method under test: {@link BaseElement#getExtensionElements()}
   */
  @Test
  public void testGetExtensionElements2() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setExtensionElements(extensionElements);

    // Act
    Map<String, List<ExtensionElement>> actualExtensionElements = activitiListener.getExtensionElements();

    // Assert
    assertTrue(actualExtensionElements.isEmpty());
    assertSame(extensionElements, actualExtensionElements);
  }

  /**
   * Method under test: {@link BaseElement#addExtensionElement(ExtensionElement)}
   */
  @Test
  public void testAddExtensionElement() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.addExtensionElement(new ExtensionElement());

    // Assert that nothing has changed
    assertTrue(activitiListener.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: {@link BaseElement#addExtensionElement(ExtensionElement)}
   */
  @Test
  public void testAddExtensionElement2() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.addExtensionElement(null);

    // Assert that nothing has changed
    assertTrue(activitiListener.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: {@link BaseElement#addExtensionElement(ExtensionElement)}
   */
  @Test
  public void testAddExtensionElement3() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setName("Name");

    // Act
    activitiListener.addExtensionElement(extensionElement);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = activitiListener.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("Name");
    assertEquals(1, getResult.size());
    assertSame(extensionElement, getResult.get(0));
  }

  /**
   * Method under test: {@link BaseElement#addExtensionElement(ExtensionElement)}
   */
  @Test
  public void testAddExtensionElement4() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setName("");

    // Act
    activitiListener.addExtensionElement(extensionElement);

    // Assert that nothing has changed
    assertTrue(activitiListener.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: {@link BaseElement#setExtensionElements(Map)}
   */
  @Test
  public void testSetExtensionElements() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();

    // Act
    activitiListener.setExtensionElements(extensionElements);

    // Assert
    assertSame(extensionElements, activitiListener.getExtensionElements());
  }

  /**
   * Method under test: {@link BaseElement#setExtensionElements(Map)}
   */
  @Test
  public void testSetExtensionElements2() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    activitiListener.setExtensionElements(extensionElements);

    // Assert
    assertSame(extensionElements, activitiListener.getExtensionElements());
  }

  /**
   * Method under test: {@link BaseElement#getAttributes()}
   */
  @Test
  public void testGetAttributes() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    Map<String, List<ExtensionAttribute>> actualAttributes = activitiListener.getAttributes();

    // Assert
    assertTrue(actualAttributes.isEmpty());
    assertSame(activitiListener.attributes, actualAttributes);
  }

  /**
   * Method under test: {@link BaseElement#getAttributes()}
   */
  @Test
  public void testGetAttributes2() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setExtensionElements(extensionElements);

    // Act
    Map<String, List<ExtensionAttribute>> actualAttributes = activitiListener.getAttributes();

    // Assert
    assertTrue(actualAttributes.isEmpty());
    assertSame(activitiListener.attributes, actualAttributes);
  }

  /**
   * Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  public void testGetAttributeValue() {
    // Arrange, Act and Assert
    assertNull((new ActivitiListener()).getAttributeValue("Namespace", "Name"));
  }

  /**
   * Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  public void testGetAttributeValue2() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    assertNull(activitiListener.getAttributeValue("Namespace", "Name"));
  }

  /**
   * Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  public void testGetAttributeValue3() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(new ExtensionAttribute("Name"));
    activitiListener.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    assertNull(activitiListener.getAttributeValue("Namespace", "Name"));
  }

  /**
   * Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  public void testGetAttributeValue4() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    attribute.setNamespace("Namespace");

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(attribute);

    // Act and Assert
    assertNull(activitiListener.getAttributeValue("Namespace", "Name"));
  }

  /**
   * Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  public void testGetAttributeValue5() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    assertNull(activitiListener.getAttributeValue(null, "Name"));
  }

  /**
   * Method under test: {@link BaseElement#getAttributeValue(String, String)}
   */
  @Test
  public void testGetAttributeValue6() {
    // Arrange
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    attribute.setNamespace("Namespace");

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.addAttribute(attribute);

    // Act and Assert
    assertNull(activitiListener.getAttributeValue(null, "Name"));
  }

  /**
   * Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddAttribute() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");

    // Act
    activitiListener.addAttribute(attribute);

    // Assert
    Map<String, List<ExtensionAttribute>> attributes = activitiListener.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddAttribute2() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    activitiListener.addAttribute(attribute);
    ExtensionAttribute attribute2 = new ExtensionAttribute("Name");

    // Act
    activitiListener.addAttribute(attribute2);

    // Assert
    Map<String, List<ExtensionAttribute>> attributes = activitiListener.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(2, getResult.size());
    assertSame(attribute, getResult.get(0));
    assertSame(attribute2, getResult.get(1));
  }

  /**
   * Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddAttribute3() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.addAttribute(new ExtensionAttribute(null));

    // Assert that nothing has changed
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddAttribute4() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.addAttribute(new ExtensionAttribute(""));

    // Assert that nothing has changed
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Method under test: {@link BaseElement#addAttribute(ExtensionAttribute)}
   */
  @Test
  public void testAddAttribute5() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    // Act
    activitiListener.addAttribute(null);

    // Assert that nothing has changed
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Method under test: {@link BaseElement#setAttributes(Map)}
   */
  @Test
  public void testSetAttributes() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();

    // Act
    activitiListener.setAttributes(attributes);

    // Assert
    assertSame(attributes, activitiListener.getAttributes());
  }

  /**
   * Method under test: {@link BaseElement#setAttributes(Map)}
   */
  @Test
  public void testSetAttributes2() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    activitiListener.setAttributes(attributes);

    // Assert
    assertSame(attributes, activitiListener.getAttributes());
  }

  /**
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    ActivitiListener otherElement = new ActivitiListener();

    // Act
    activitiListener.setValues((BaseElement) otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertTrue(otherElement.getAttributes().isEmpty());
  }

  /**
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues2() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    ActivitiListener otherElement = new ActivitiListener();
    otherElement.setExtensionElements(null);
    otherElement.setAttributes(null);

    // Act
    activitiListener.setValues((BaseElement) otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertNull(otherElement.getAttributes());
  }

  /**
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues3() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    AdhocSubProcess otherElement = mock(AdhocSubProcess.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    activitiListener.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    assertEquals("42", activitiListener.getId());
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues4() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("foo", new ArrayList<>());
    AdhocSubProcess otherElement = mock(AdhocSubProcess.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    activitiListener.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    assertEquals("42", activitiListener.getId());
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues5() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("foo", new ArrayList<>());
    AdhocSubProcess otherElement = mock(AdhocSubProcess.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    activitiListener.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    assertEquals("42", activitiListener.getId());
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues6() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("foo", new ArrayList<>());
    AdhocSubProcess otherElement = mock(AdhocSubProcess.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(stringListMap);

    // Act
    activitiListener.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    assertEquals("42", activitiListener.getId());
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues7() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("foo", new ArrayList<>());
    AdhocSubProcess otherElement = mock(AdhocSubProcess.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(stringListMap);

    // Act
    activitiListener.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    assertEquals("42", activitiListener.getId());
    assertTrue(activitiListener.getAttributes().isEmpty());
  }

  /**
   * Method under test: {@link BaseElement#setValues(BaseElement)}
   */
  @Test
  public void testSetValues8() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("Name"));

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("foo", extensionAttributeList);
    AdhocSubProcess otherElement = mock(AdhocSubProcess.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    activitiListener.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    assertEquals("42", activitiListener.getId());
    assertEquals(stringListMap, activitiListener.getAttributes());
  }
}
