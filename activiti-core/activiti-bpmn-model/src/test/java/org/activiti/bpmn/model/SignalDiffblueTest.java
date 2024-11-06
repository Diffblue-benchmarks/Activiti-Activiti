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

public class SignalDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Signal#Signal()}
   *   <li>{@link Signal#setName(String)}
   *   <li>{@link Signal#setScope(String)}
   *   <li>{@link Signal#getName()}
   *   <li>{@link Signal#getScope()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Signal actualSignal = new Signal();
    actualSignal.setName("Name");
    actualSignal.setScope("Scope");
    String actualName = actualSignal.getName();

    // Assert that nothing has changed
    assertEquals("Name", actualName);
    assertEquals("Scope", actualSignal.getScope());
    assertEquals(0, actualSignal.getXmlColumnNumber());
    assertEquals(0, actualSignal.getXmlRowNumber());
    assertTrue(actualSignal.getAttributes().isEmpty());
    assertTrue(actualSignal.getExtensionElements().isEmpty());
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
   *   <li>{@link Signal#Signal(String, String)}
   *   <li>{@link Signal#setName(String)}
   *   <li>{@link Signal#setScope(String)}
   *   <li>{@link Signal#getName()}
   *   <li>{@link Signal#getScope()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_when42_thenReturnIdIs42() {
    // Arrange and Act
    Signal actualSignal = new Signal("42", "Name");
    actualSignal.setName("Name");
    actualSignal.setScope("Scope");
    String actualName = actualSignal.getName();
    String actualScope = actualSignal.getScope();

    // Assert that nothing has changed
    assertEquals("42", actualSignal.getId());
    assertEquals("Name", actualName);
    assertEquals("Scope", actualScope);
    assertEquals(0, actualSignal.getXmlColumnNumber());
    assertEquals(0, actualSignal.getXmlRowNumber());
    assertTrue(actualSignal.getAttributes().isEmpty());
    assertTrue(actualSignal.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Signal#clone()}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code 42} and
   * {@code Name} ExtensionElements is {@code null}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Signal#clone()}
   */
  @Test
  public void testClone_givenSignalWithIdIs42AndNameExtensionElementsIsNull_thenReturnIdIs42() {
    // Arrange
    Signal signal = new Signal("42", "Name");
    signal.setExtensionElements(null);
    signal.setAttributes(null);

    // Act
    Signal actualCloneResult = signal.clone();

    // Assert
    assertEquals("42", actualCloneResult.getId());
    assertEquals("Name", actualCloneResult.getName());
    assertNull(actualCloneResult.getScope());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Signal#clone()}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code 42} and
   * {@code Name}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Signal#clone()}
   */
  @Test
  public void testClone_givenSignalWithIdIs42AndName_thenReturnIdIs42() {
    // Arrange and Act
    Signal actualCloneResult = (new Signal("42", "Name")).clone();

    // Assert
    assertEquals("42", actualCloneResult.getId());
    assertEquals("Name", actualCloneResult.getName());
    assertNull(actualCloneResult.getScope());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Signal#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Signal#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    Signal signal = new Signal("42", "Name");
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    signal.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = signal.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Signal#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link Signal#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    Signal signal = new Signal("42", "Name");
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    signal.addAttribute(attribute);
    signal.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = signal.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Signal#setValues(Signal)} with {@code Signal}.
   * <ul>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Signal#setValues(Signal)}
   */
  @Test
  public void testSetValuesWithSignal_thenCallsGetName() {
    // Arrange
    Signal signal = new Signal("42", "Name");
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    Signal otherElement = new Signal("42", "Name");
    otherElement.addAttribute(attribute);

    // Act
    signal.setValues(otherElement);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }
}
