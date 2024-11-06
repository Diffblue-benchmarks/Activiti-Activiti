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
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class MessageFlowDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageFlow#MessageFlow()}
   *   <li>{@link MessageFlow#setMessageRef(String)}
   *   <li>{@link MessageFlow#setName(String)}
   *   <li>{@link MessageFlow#setSourceRef(String)}
   *   <li>{@link MessageFlow#setTargetRef(String)}
   *   <li>{@link MessageFlow#toString()}
   *   <li>{@link MessageFlow#getMessageRef()}
   *   <li>{@link MessageFlow#getName()}
   *   <li>{@link MessageFlow#getSourceRef()}
   *   <li>{@link MessageFlow#getTargetRef()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    MessageFlow actualMessageFlow = new MessageFlow();
    actualMessageFlow.setMessageRef("Message Ref");
    actualMessageFlow.setName("Name");
    actualMessageFlow.setSourceRef("Source Ref");
    actualMessageFlow.setTargetRef("Target Ref");
    String actualToStringResult = actualMessageFlow.toString();
    String actualMessageRef = actualMessageFlow.getMessageRef();
    String actualName = actualMessageFlow.getName();
    String actualSourceRef = actualMessageFlow.getSourceRef();

    // Assert that nothing has changed
    assertEquals("Message Ref", actualMessageRef);
    assertEquals("Name", actualName);
    assertEquals("Source Ref --> Target Ref", actualToStringResult);
    assertEquals("Source Ref", actualSourceRef);
    assertEquals("Target Ref", actualMessageFlow.getTargetRef());
    assertEquals(0, actualMessageFlow.getXmlColumnNumber());
    assertEquals(0, actualMessageFlow.getXmlRowNumber());
    assertTrue(actualMessageFlow.getAttributes().isEmpty());
    assertTrue(actualMessageFlow.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Source Ref}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageFlow#MessageFlow(String, String)}
   *   <li>{@link MessageFlow#setMessageRef(String)}
   *   <li>{@link MessageFlow#setName(String)}
   *   <li>{@link MessageFlow#setSourceRef(String)}
   *   <li>{@link MessageFlow#setTargetRef(String)}
   *   <li>{@link MessageFlow#toString()}
   *   <li>{@link MessageFlow#getMessageRef()}
   *   <li>{@link MessageFlow#getName()}
   *   <li>{@link MessageFlow#getSourceRef()}
   *   <li>{@link MessageFlow#getTargetRef()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenSourceRef() {
    // Arrange and Act
    MessageFlow actualMessageFlow = new MessageFlow("Source Ref", "Target Ref");
    actualMessageFlow.setMessageRef("Message Ref");
    actualMessageFlow.setName("Name");
    actualMessageFlow.setSourceRef("Source Ref");
    actualMessageFlow.setTargetRef("Target Ref");
    String actualToStringResult = actualMessageFlow.toString();
    String actualMessageRef = actualMessageFlow.getMessageRef();
    String actualName = actualMessageFlow.getName();
    String actualSourceRef = actualMessageFlow.getSourceRef();

    // Assert that nothing has changed
    assertEquals("Message Ref", actualMessageRef);
    assertEquals("Name", actualName);
    assertEquals("Source Ref --> Target Ref", actualToStringResult);
    assertEquals("Source Ref", actualSourceRef);
    assertEquals("Target Ref", actualMessageFlow.getTargetRef());
    assertEquals(0, actualMessageFlow.getXmlColumnNumber());
    assertEquals(0, actualMessageFlow.getXmlRowNumber());
    assertTrue(actualMessageFlow.getAttributes().isEmpty());
    assertTrue(actualMessageFlow.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link MessageFlow#clone()}.
   * <ul>
   *   <li>Given {@link MessageFlow#MessageFlow(String, String)} with
   * {@code Source Ref} and {@code Target Ref} ExtensionElements is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlow#clone()}
   */
  @Test
  public void testClone_givenMessageFlowWithSourceRefAndTargetRefExtensionElementsIsNull() {
    // Arrange
    MessageFlow messageFlow = new MessageFlow("Source Ref", "Target Ref");
    messageFlow.setExtensionElements(null);
    messageFlow.setAttributes(null);

    // Act
    MessageFlow actualCloneResult = messageFlow.clone();

    // Assert
    assertEquals("Source Ref", actualCloneResult.getSourceRef());
    assertEquals("Target Ref", actualCloneResult.getTargetRef());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getMessageRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link MessageFlow#clone()}.
   * <ul>
   *   <li>Given {@link MessageFlow#MessageFlow(String, String)} with
   * {@code Source Ref} and {@code Target Ref}.</li>
   *   <li>Then return {@code Source Ref}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlow#clone()}
   */
  @Test
  public void testClone_givenMessageFlowWithSourceRefAndTargetRef_thenReturnSourceRef() {
    // Arrange and Act
    MessageFlow actualCloneResult = (new MessageFlow("Source Ref", "Target Ref")).clone();

    // Assert
    assertEquals("Source Ref", actualCloneResult.getSourceRef());
    assertEquals("Target Ref", actualCloneResult.getTargetRef());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getMessageRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link MessageFlow#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlow#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    MessageFlow messageFlow = new MessageFlow("Source Ref", "Target Ref");
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    messageFlow.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = messageFlow.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link MessageFlow#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlow#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    MessageFlow messageFlow = new MessageFlow("Source Ref", "Target Ref");
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    messageFlow.addAttribute(attribute);
    messageFlow.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = messageFlow.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link MessageFlow#setValues(MessageFlow)} with {@code otherFlow}.
   * <ul>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlow#setValues(MessageFlow)}
   */
  @Test
  public void testSetValuesWithOtherFlow_thenCallsGetName() {
    // Arrange
    MessageFlow messageFlow = new MessageFlow("Source Ref", "Target Ref");
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    MessageFlow otherFlow = new MessageFlow("Source Ref", "Target Ref");
    otherFlow.addAttribute(attribute);

    // Act
    messageFlow.setValues(otherFlow);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }
}
