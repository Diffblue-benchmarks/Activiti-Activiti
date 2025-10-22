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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ExtensionElementDiffblueTest {
  /**
   * Test {@link ExtensionElement#clone()}.
   * <ul>
   *   <li>Given {@link ExtensionElement} (default constructor) ChildElements is {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtensionElement#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExtensionElement ExtensionElement.clone()"})
  public void testClone_givenExtensionElementChildElementsIsNull_thenReturnIdIsNull() {
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
   * Test {@link ExtensionElement#clone()}.
   * <ul>
   *   <li>Given {@link ExtensionElement} (default constructor) Name is {@code Name}.</li>
   *   <li>Then return ChildElements size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtensionElement#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExtensionElement ExtensionElement.clone()"})
  public void testClone_givenExtensionElementNameIsName_thenReturnChildElementsSizeIsOne() {
    // Arrange
    ExtensionElement childElement = new ExtensionElement();
    childElement.setName("Name");

    ExtensionElement extensionElement = new ExtensionElement();
    extensionElement.addChildElement(childElement);

    // Act and Assert
    Map<String, List<ExtensionElement>> childElements = extensionElement.clone().getChildElements();
    assertEquals(1, childElements.size());
    List<ExtensionElement> getResult = childElements.get("Name");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("Name", getResult2.getName());
    assertNull(getResult2.getId());
    assertNull(getResult2.getElementText());
    assertNull(getResult2.getNamespace());
    assertNull(getResult2.getNamespacePrefix());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
  }

  /**
   * Test {@link ExtensionElement#clone()}.
   * <ul>
   *   <li>Given {@link ExtensionElement} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtensionElement#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExtensionElement ExtensionElement.clone()"})
  public void testClone_givenExtensionElement_thenReturnIdIsNull() {
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
   * Test {@link ExtensionElement#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExtensionElement#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ExtensionElement ExtensionElement.clone()"})
  public void testClone_givenHashMapFooIsArrayList_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionElement>> childElements = new HashMap<>();
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
   * Test getters and setters.
   * <p>
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExtensionElement.<init>()", "Map ExtensionElement.getChildElements()",
      "String ExtensionElement.getElementText()", "String ExtensionElement.getName()",
      "String ExtensionElement.getNamespace()", "String ExtensionElement.getNamespacePrefix()",
      "void ExtensionElement.setChildElements(Map)", "void ExtensionElement.setElementText(String)",
      "void ExtensionElement.setName(String)", "void ExtensionElement.setNamespace(String)",
      "void ExtensionElement.setNamespacePrefix(String)"})
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

    // Assert
    assertEquals("Element Text", actualElementText);
    assertEquals("Name", actualName);
    assertEquals("Namespace Prefix", actualExtensionElement.getNamespacePrefix());
    assertEquals("Namespace", actualNamespace);
    assertNull(actualExtensionElement.getId());
    assertEquals(0, actualExtensionElement.getXmlColumnNumber());
    assertEquals(0, actualExtensionElement.getXmlRowNumber());
    assertTrue(actualExtensionElement.getAttributes().isEmpty());
    assertTrue(actualExtensionElement.getExtensionElements().isEmpty());
    assertTrue(actualChildElements.isEmpty());
    assertSame(childElements, actualChildElements);
  }
}
