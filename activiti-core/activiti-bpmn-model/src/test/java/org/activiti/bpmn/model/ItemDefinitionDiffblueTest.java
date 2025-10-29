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

public class ItemDefinitionDiffblueTest {
  /**
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    ItemDefinition actualCloneResult = (new ItemDefinition()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getItemKind());
    assertNull(actualCloneResult.getStructureRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    ItemDefinition itemDefinition = new ItemDefinition();
    itemDefinition.setExtensionElements(null);
    itemDefinition.setAttributes(null);

    // Act
    ItemDefinition actualCloneResult = itemDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getItemKind());
    assertNull(actualCloneResult.getStructureRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    ItemDefinition itemDefinition = new ItemDefinition();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    itemDefinition.addAttribute(attribute);

    // Act
    ItemDefinition actualCloneResult = itemDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getItemKind());
    assertNull(actualCloneResult.getStructureRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone4() {
    // Arrange
    ItemDefinition itemDefinition = new ItemDefinition();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    itemDefinition.addAttribute(attribute);
    ExtensionAttribute attribute2 = new ExtensionAttribute("Name");
    itemDefinition.addAttribute(attribute2);

    // Act
    ItemDefinition actualCloneResult = itemDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getItemKind());
    assertNull(actualCloneResult.getStructureRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    List<ExtensionAttribute> getResult2 = attributes.get("Name");
    assertEquals(1, getResult2.size());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertSame(attribute, getResult.get(0));
    assertSame(attribute2, getResult2.get(0));
  }

  /**
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone5() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", new ArrayList<>());

    ItemDefinition itemDefinition = new ItemDefinition();
    itemDefinition.setExtensionElements(extensionElements);
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    itemDefinition.addAttribute(attribute);

    // Act
    ItemDefinition actualCloneResult = itemDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getItemKind());
    assertNull(actualCloneResult.getStructureRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone6() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("42", new ArrayList<>());
    extensionElements.put("foo", new ArrayList<>());

    ItemDefinition itemDefinition = new ItemDefinition();
    itemDefinition.setExtensionElements(extensionElements);
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    itemDefinition.addAttribute(attribute);

    // Act
    ItemDefinition actualCloneResult = itemDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getItemKind());
    assertNull(actualCloneResult.getStructureRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone7() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));
    extensionElements.put("foo", new ArrayList<>());

    ItemDefinition itemDefinition = new ItemDefinition();
    itemDefinition.setExtensionElements(extensionElements);
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    itemDefinition.addAttribute(attribute);

    // Act
    ItemDefinition actualCloneResult = itemDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getItemKind());
    assertNull(actualCloneResult.getStructureRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Method under test: {@link ItemDefinition#setValues(ItemDefinition)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    ItemDefinition itemDefinition = new ItemDefinition();
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    ItemDefinition otherElement = new ItemDefinition();
    otherElement.addAttribute(attribute);

    // Act
    itemDefinition.setValues(otherElement);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ItemDefinition}
   *   <li>{@link ItemDefinition#setItemKind(String)}
   *   <li>{@link ItemDefinition#setStructureRef(String)}
   *   <li>{@link ItemDefinition#getItemKind()}
   *   <li>{@link ItemDefinition#getStructureRef()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ItemDefinition actualItemDefinition = new ItemDefinition();
    actualItemDefinition.setItemKind("Item Kind");
    actualItemDefinition.setStructureRef("Structure Ref");
    String actualItemKind = actualItemDefinition.getItemKind();

    // Assert that nothing has changed
    assertEquals("Item Kind", actualItemKind);
    assertEquals("Structure Ref", actualItemDefinition.getStructureRef());
    assertEquals(0, actualItemDefinition.getXmlColumnNumber());
    assertEquals(0, actualItemDefinition.getXmlRowNumber());
    assertTrue(actualItemDefinition.getAttributes().isEmpty());
    assertTrue(actualItemDefinition.getExtensionElements().isEmpty());
  }
}
