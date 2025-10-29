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
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class OperationDiffblueTest {
  /**
   * Method under test: {@link Operation#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    Operation actualCloneResult = (new Operation()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImplementationRef());
    assertNull(actualCloneResult.getInMessageRef());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getOutMessageRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getErrorMessageRef().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: {@link Operation#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    Operation operation = new Operation();
    operation.setErrorMessageRef(null);

    // Act
    Operation actualCloneResult = operation.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImplementationRef());
    assertNull(actualCloneResult.getInMessageRef());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getOutMessageRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getErrorMessageRef().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: {@link Operation#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    Operation operation = new Operation();
    operation.setExtensionElements(null);
    operation.setAttributes(null);

    // Act
    Operation actualCloneResult = operation.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImplementationRef());
    assertNull(actualCloneResult.getInMessageRef());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getOutMessageRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getErrorMessageRef().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: {@link Operation#clone()}
   */
  @Test
  public void testClone4() {
    // Arrange
    Operation operation = new Operation();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    operation.addAttribute(attribute);

    // Act
    Operation actualCloneResult = operation.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImplementationRef());
    assertNull(actualCloneResult.getInMessageRef());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getOutMessageRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertTrue(actualCloneResult.getErrorMessageRef().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Method under test: {@link Operation#clone()}
   */
  @Test
  public void testClone5() {
    // Arrange
    Operation operation = new Operation();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    operation.addAttribute(attribute);
    ExtensionAttribute attribute2 = new ExtensionAttribute("Name");
    operation.addAttribute(attribute2);

    // Act
    Operation actualCloneResult = operation.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImplementationRef());
    assertNull(actualCloneResult.getInMessageRef());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getOutMessageRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    List<ExtensionAttribute> getResult2 = attributes.get("Name");
    assertEquals(1, getResult2.size());
    assertTrue(actualCloneResult.getErrorMessageRef().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertSame(attribute, getResult.get(0));
    assertSame(attribute2, getResult2.get(0));
  }

  /**
   * Method under test: {@link Operation#setValues(Operation)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    Operation operation = new Operation();
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    Operation otherElement = new Operation();
    otherElement.addAttribute(attribute);

    // Act
    operation.setValues(otherElement);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Operation}
   *   <li>{@link Operation#setErrorMessageRef(List)}
   *   <li>{@link Operation#setImplementationRef(String)}
   *   <li>{@link Operation#setInMessageRef(String)}
   *   <li>{@link Operation#setName(String)}
   *   <li>{@link Operation#setOutMessageRef(String)}
   *   <li>{@link Operation#getErrorMessageRef()}
   *   <li>{@link Operation#getImplementationRef()}
   *   <li>{@link Operation#getInMessageRef()}
   *   <li>{@link Operation#getName()}
   *   <li>{@link Operation#getOutMessageRef()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Operation actualOperation = new Operation();
    ArrayList<String> errorMessageRef = new ArrayList<>();
    actualOperation.setErrorMessageRef(errorMessageRef);
    actualOperation.setImplementationRef("Implementation Ref");
    actualOperation.setInMessageRef("In Message Ref");
    actualOperation.setName("Name");
    actualOperation.setOutMessageRef("Out Message Ref");
    List<String> actualErrorMessageRef = actualOperation.getErrorMessageRef();
    String actualImplementationRef = actualOperation.getImplementationRef();
    String actualInMessageRef = actualOperation.getInMessageRef();
    String actualName = actualOperation.getName();

    // Assert that nothing has changed
    assertEquals("Implementation Ref", actualImplementationRef);
    assertEquals("In Message Ref", actualInMessageRef);
    assertEquals("Name", actualName);
    assertEquals("Out Message Ref", actualOperation.getOutMessageRef());
    assertEquals(0, actualOperation.getXmlColumnNumber());
    assertEquals(0, actualOperation.getXmlRowNumber());
    assertTrue(actualErrorMessageRef.isEmpty());
    assertTrue(actualOperation.getAttributes().isEmpty());
    assertTrue(actualOperation.getExtensionElements().isEmpty());
    assertSame(errorMessageRef, actualErrorMessageRef);
  }
}
