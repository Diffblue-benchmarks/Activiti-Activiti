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
   * Test {@link ItemDefinition#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone_givenHashMap42IsArrayList_thenReturnAttributesSizeIsOne() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("42", new ArrayList<>());
    extensionElements.put("foo", new ArrayList<>());

    ItemDefinition itemDefinition = new ItemDefinition();
    itemDefinition.setExtensionElements(extensionElements);
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    itemDefinition.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = itemDefinition.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link ItemDefinition#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));
    extensionElements.put("foo", new ArrayList<>());

    ItemDefinition itemDefinition = new ItemDefinition();
    itemDefinition.setExtensionElements(extensionElements);
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    itemDefinition.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = itemDefinition.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link ItemDefinition#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone_givenHashMapFooIsArrayList_thenReturnAttributesSizeIsOne() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", new ArrayList<>());

    ItemDefinition itemDefinition = new ItemDefinition();
    itemDefinition.setExtensionElements(extensionElements);
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    itemDefinition.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = itemDefinition.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link ItemDefinition#clone()}.
   * <ul>
   *   <li>Given {@link ItemDefinition} (default constructor) ExtensionElements is
   * {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone_givenItemDefinitionExtensionElementsIsNull_thenReturnIdIsNull() {
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
   * Test {@link ItemDefinition#clone()}.
   * <ul>
   *   <li>Given {@link ItemDefinition} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone_givenItemDefinition_thenReturnIdIsNull() {
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
   * Test {@link ItemDefinition#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    ItemDefinition itemDefinition = new ItemDefinition();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    itemDefinition.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = itemDefinition.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link ItemDefinition#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ItemDefinition#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    ItemDefinition itemDefinition = new ItemDefinition();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    itemDefinition.addAttribute(attribute);
    itemDefinition.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = itemDefinition.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link ItemDefinition#setValues(ItemDefinition)} with
   * {@code ItemDefinition}.
   * <ul>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ItemDefinition#setValues(ItemDefinition)}
   */
  @Test
  public void testSetValuesWithItemDefinition_thenCallsGetName() {
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
   * Test getters and setters.
   * <p>
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
