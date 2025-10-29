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
import java.util.TreeMap;
import java.util.function.BiFunction;
import org.junit.Test;

public class ExtensionElementDiffblueTest {
  /**
   * Method under test: {@link ExtensionElement#addChildElement(ExtensionElement)}
   */
  @Test
  public void testAddChildElement() {
    // Arrange
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.addAttribute(attribute);

    // Act
    extensionElement.addChildElement(new ExtensionElement());

    // Assert that nothing has changed
    verify(attribute, atLeast(1)).getName();
  }

  /**
   * Method under test: {@link ExtensionElement#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    ExtensionElement actualCloneResult = (new ExtensionElement()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getElementText());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getNamespace());
    assertNull(actualCloneResult.getNamespacePrefix());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getChildElements().isEmpty());
  }

  /**
   * Method under test: {@link ExtensionElement#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setChildElements(null);

    // Act
    ExtensionElement actualCloneResult = extensionElement.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getElementText());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getNamespace());
    assertNull(actualCloneResult.getNamespacePrefix());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getChildElements().isEmpty());
  }

  /**
   * Method under test: {@link ExtensionElement#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    ExtensionElement childElement = new ExtensionElement();
    childElement.setName("Name");

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.addChildElement(childElement);

    // Act
    ExtensionElement actualCloneResult = extensionElement.clone();

    // Assert
    Map<String, List<ExtensionElement>> childElements = actualCloneResult.getChildElements();
    assertEquals(1, childElements.size());
    List<ExtensionElement> getResult = childElements.get("Name");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("Name", getResult2.getName());
    assertNull(getResult2.getId());
    assertNull(actualCloneResult.getId());
    assertNull(getResult2.getElementText());
    assertNull(actualCloneResult.getElementText());
    assertNull(actualCloneResult.getName());
    assertNull(getResult2.getNamespace());
    assertNull(actualCloneResult.getNamespace());
    assertNull(getResult2.getNamespacePrefix());
    assertNull(actualCloneResult.getNamespacePrefix());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
  }

  /**
   * Method under test: {@link ExtensionElement#clone()}
   */
  @Test
  public void testClone4() {
    // Arrange
    TreeMap<String, List<ExtensionElement>> childElements = new TreeMap<>();
    childElements.put("foo", new ArrayList<>());

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setChildElements(childElements);

    // Act
    ExtensionElement actualCloneResult = extensionElement.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getElementText());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getNamespace());
    assertNull(actualCloneResult.getNamespacePrefix());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getChildElements().isEmpty());
  }

  /**
   * Method under test: {@link ExtensionElement#clone()}
   */
  @Test
  public void testClone5() {
    // Arrange
    TreeMap<String, List<ExtensionElement>> childElements = new TreeMap<>();
    childElements.replaceAll(mock(BiFunction.class));
    childElements.put("foo", new ArrayList<>());

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.setChildElements(childElements);

    // Act
    ExtensionElement actualCloneResult = extensionElement.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getElementText());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getNamespace());
    assertNull(actualCloneResult.getNamespacePrefix());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getChildElements().isEmpty());
  }

  /**
   * Method under test: {@link ExtensionElement#setValues(ExtensionElement)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.addAttribute(attribute);

    // Act
    extensionElement.setValues(new ExtensionElement());

    // Assert
    verify(attribute, atLeast(1)).getName();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ExtensionElement}
   *   <li>{@link ExtensionElement#setChildElements(Map)}
   *   <li>{@link ExtensionElement#setElementText(String)}
   *   <li>{@link ExtensionElement#setName(String)}
   *   <li>{@link ExtensionElement#setNamespace(String)}
   *   <li>{@link ExtensionElement#setNamespacePrefix(String)}
   *   <li>{@link ExtensionElement#getChildElements()}
   *   <li>{@link ExtensionElement#getElementText()}
   *   <li>{@link ExtensionElement#getName()}
   *   <li>{@link ExtensionElement#getNamespace()}
   *   <li>{@link ExtensionElement#getNamespacePrefix()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ExtensionElement actualExtensionElement = new ExtensionElement();
    HashMap<String, List<ExtensionElement>> childElements = new HashMap<>();
    actualExtensionElement.setChildElements(childElements);
    actualExtensionElement.setElementText("Element Text");
    actualExtensionElement.setName("Name");
    actualExtensionElement.setNamespace("Namespace");
    actualExtensionElement.setNamespacePrefix("Namespace Prefix");
    Map<String, List<ExtensionElement>> actualChildElements = actualExtensionElement.getChildElements();
    String actualElementText = actualExtensionElement.getElementText();
    String actualName = actualExtensionElement.getName();
    String actualNamespace = actualExtensionElement.getNamespace();

    // Assert that nothing has changed
    assertEquals("Element Text", actualElementText);
    assertEquals("Name", actualName);
    assertEquals("Namespace Prefix", actualExtensionElement.getNamespacePrefix());
    assertEquals("Namespace", actualNamespace);
    assertEquals(0, actualExtensionElement.getXmlColumnNumber());
    assertEquals(0, actualExtensionElement.getXmlRowNumber());
    assertTrue(actualExtensionElement.getAttributes().isEmpty());
    assertTrue(actualExtensionElement.getExtensionElements().isEmpty());
    assertTrue(actualChildElements.isEmpty());
    assertSame(childElements, actualChildElements);
  }
}
