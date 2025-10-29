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
   * Method under test: {@link MessageFlow#clone()}
   */
  @Test
  public void testClone() {
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
   * Method under test: {@link MessageFlow#clone()}
   */
  @Test
  public void testClone2() {
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
   * Method under test: {@link MessageFlow#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    MessageFlow messageFlow = new MessageFlow("Source Ref", "Target Ref");
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    messageFlow.addAttribute(attribute);

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
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Method under test: {@link MessageFlow#clone()}
   */
  @Test
  public void testClone4() {
    // Arrange
    MessageFlow messageFlow = new MessageFlow("Source Ref", "Target Ref");
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    messageFlow.addAttribute(attribute);
    ExtensionAttribute attribute2 = new ExtensionAttribute("Name");
    messageFlow.addAttribute(attribute2);

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
   * Method under test: {@link MessageFlow#setValues(MessageFlow)}
   */
  @Test
  public void testSetValues() {
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

  /**
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
  public void testGettersAndSetters2() {
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
}
