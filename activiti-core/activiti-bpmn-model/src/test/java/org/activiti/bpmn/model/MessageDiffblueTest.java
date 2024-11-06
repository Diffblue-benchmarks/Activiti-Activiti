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
import org.activiti.bpmn.model.Message.Builder;
import org.junit.Test;

public class MessageDiffblueTest {
  /**
   * Test Builder {@link Builder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Message.Builder#build()}
   *   <li>{@link Message.Builder#attributes(Map)}
   *   <li>{@link Message.Builder#extensionElements(Map)}
   *   <li>{@link Message.Builder#id(String)}
   *   <li>{@link Message.Builder#itemRef(String)}
   *   <li>{@link Message.Builder#name(String)}
   *   <li>{@link Message.Builder#xmlColumnNumber(int)}
   *   <li>{@link Message.Builder#xmlRowNumber(int)}
   * </ul>
   */
  @Test
  public void testBuilderBuild() {
    // Arrange
    Message.Builder builderResult = Message.builder();
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    Message.Builder attributesResult = builderResult.attributes(attributes);
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();

    // Act
    Message actualBuildResult = attributesResult.extensionElements(extensionElements)
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getId());
    assertEquals("Item Ref", actualBuildResult.getItemRef());
    assertEquals("Name", actualBuildResult.getName());
    assertEquals(10, actualBuildResult.getXmlColumnNumber());
    assertEquals(10, actualBuildResult.getXmlRowNumber());
    Map<String, List<ExtensionAttribute>> attributes2 = actualBuildResult.getAttributes();
    assertTrue(attributes2.isEmpty());
    Map<String, List<ExtensionElement>> extensionElements2 = actualBuildResult.getExtensionElements();
    assertTrue(extensionElements2.isEmpty());
    assertSame(attributes, attributes2);
    assertSame(extensionElements, extensionElements2);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Message#Message()}
   *   <li>{@link Message#setItemRef(String)}
   *   <li>{@link Message#setName(String)}
   *   <li>{@link Message#builderFrom(Message)}
   *   <li>{@link Message#getItemRef()}
   *   <li>{@link Message#getName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Message actualMessage = new Message();
    actualMessage.setItemRef("Item Ref");
    actualMessage.setName("Name");
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    actualMessage.builderFrom(message);
    String actualItemRef = actualMessage.getItemRef();

    // Assert that nothing has changed
    assertEquals("Item Ref", actualItemRef);
    assertEquals("Name", actualMessage.getName());
    assertEquals(0, actualMessage.getXmlColumnNumber());
    assertEquals(0, actualMessage.getXmlRowNumber());
    assertTrue(actualMessage.getAttributes().isEmpty());
    assertTrue(actualMessage.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Message#Message(String, String, String)}
   *   <li>{@link Message#setItemRef(String)}
   *   <li>{@link Message#setName(String)}
   *   <li>{@link Message#builderFrom(Message)}
   *   <li>{@link Message#getItemRef()}
   *   <li>{@link Message#getName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_when42_thenReturnIdIs42() {
    // Arrange and Act
    Message actualMessage = new Message("42", "Name", "Item Ref");
    actualMessage.setItemRef("Item Ref");
    actualMessage.setName("Name");
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    actualMessage.builderFrom(message);
    String actualItemRef = actualMessage.getItemRef();
    String actualName = actualMessage.getName();

    // Assert that nothing has changed
    assertEquals("42", actualMessage.getId());
    assertEquals("Item Ref", actualItemRef);
    assertEquals("Name", actualName);
    assertEquals(0, actualMessage.getXmlColumnNumber());
    assertEquals(0, actualMessage.getXmlRowNumber());
    assertTrue(actualMessage.getAttributes().isEmpty());
    assertTrue(actualMessage.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Message#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#clone()}
   */
  @Test
  public void testClone_givenHashMap42IsArrayList_thenReturnIdIs42() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("foo", new ArrayList<>());
    Message.Builder attributesResult = Message.builder().attributes(attributes);

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("42", new ArrayList<>());
    extensionElements.put("foo", new ArrayList<>());
    Message buildResult = attributesResult.extensionElements(extensionElements)
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();

    // Act
    Message actualCloneResult = buildResult.clone();

    // Assert
    assertEquals("42", actualCloneResult.getId());
    assertEquals("Item Ref", actualCloneResult.getItemRef());
    assertEquals("Name", actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Message#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnIdIs42() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));
    attributes.put("foo", new ArrayList<>());
    Message.Builder attributesResult = Message.builder().attributes(attributes);
    Message buildResult = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();

    // Act
    Message actualCloneResult = buildResult.clone();

    // Assert
    assertEquals("42", actualCloneResult.getId());
    assertEquals("Item Ref", actualCloneResult.getItemRef());
    assertEquals("Name", actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Message#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#clone()}
   */
  @Test
  public void testClone_givenHashMapFooIsArrayList_thenReturnIdIs42() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("foo", new ArrayList<>());
    Message.Builder attributesResult = Message.builder().attributes(attributes);
    Message buildResult = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();

    // Act
    Message actualCloneResult = buildResult.clone();

    // Assert
    assertEquals("42", actualCloneResult.getId());
    assertEquals("Item Ref", actualCloneResult.getItemRef());
    assertEquals("Name", actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Message#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#clone()}
   */
  @Test
  public void testClone_givenHashMapFooIsArrayList_thenReturnIdIs422() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("foo", new ArrayList<>());
    Message.Builder attributesResult = Message.builder().attributes(attributes);

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", new ArrayList<>());
    Message buildResult = attributesResult.extensionElements(extensionElements)
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();

    // Act
    Message actualCloneResult = buildResult.clone();

    // Assert
    assertEquals("42", actualCloneResult.getId());
    assertEquals("Item Ref", actualCloneResult.getItemRef());
    assertEquals("Name", actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Message#clone()}.
   * <ul>
   *   <li>Given {@link Message#Message(String, String, String)} with id is
   * {@code 42} and {@code Name} and {@code Item Ref} ExtensionElements is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#clone()}
   */
  @Test
  public void testClone_givenMessageWithIdIs42AndNameAndItemRefExtensionElementsIsNull() {
    // Arrange
    Message message = new Message("42", "Name", "Item Ref");
    message.setExtensionElements(null);
    message.setAttributes(null);

    // Act
    Message actualCloneResult = message.clone();

    // Assert
    assertEquals("42", actualCloneResult.getId());
    assertEquals("Item Ref", actualCloneResult.getItemRef());
    assertEquals("Name", actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Message#clone()}.
   * <ul>
   *   <li>Given {@link Message#Message(String, String, String)} with id is
   * {@code 42} and {@code Name} and {@code Item Ref}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#clone()}
   */
  @Test
  public void testClone_givenMessageWithIdIs42AndNameAndItemRef_thenReturnIdIs42() {
    // Arrange and Act
    Message actualCloneResult = (new Message("42", "Name", "Item Ref")).clone();

    // Assert
    assertEquals("42", actualCloneResult.getId());
    assertEquals("Item Ref", actualCloneResult.getItemRef());
    assertEquals("Name", actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Message#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    Message message = new Message("42", "Name", "Item Ref");
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    message.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = message.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Message#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    Message message = new Message("42", "Name", "Item Ref");
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    message.addAttribute(attribute);
    message.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = message.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Message#setValues(Message)} with {@code Message}.
   * <ul>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Message#setValues(Message)}
   */
  @Test
  public void testSetValuesWithMessage_thenCallsGetName() {
    // Arrange
    Message message = new Message("42", "Name", "Item Ref");
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    Message otherElement = new Message("42", "Name", "Item Ref");
    otherElement.addAttribute(attribute);

    // Act
    message.setValues(otherElement);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }
}
