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

public class ImportDiffblueTest {
  /**
   * Test {@link Import#clone()}.
   * <ul>
   *   <li>Given {@link Import} (default constructor) ExtensionElements is
   * {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Import#clone()}
   */
  @Test
  public void testClone_givenImportExtensionElementsIsNull_thenReturnIdIsNull() {
    // Arrange
    Import resultImport = new Import();
    resultImport.setExtensionElements(null);
    resultImport.setAttributes(null);

    // Act
    Import actualCloneResult = resultImport.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImportType());
    assertNull(actualCloneResult.getLocation());
    assertNull(actualCloneResult.getNamespace());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Import#clone()}.
   * <ul>
   *   <li>Given {@link Import} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Import#clone()}
   */
  @Test
  public void testClone_givenImport_thenReturnIdIsNull() {
    // Arrange and Act
    Import actualCloneResult = (new Import()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImportType());
    assertNull(actualCloneResult.getLocation());
    assertNull(actualCloneResult.getNamespace());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Import#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Import#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    Import resultImport = new Import();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    resultImport.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = resultImport.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Import#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link Import#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    Import resultImport = new Import();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    resultImport.addAttribute(attribute);
    resultImport.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = resultImport.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Import#setValues(Import)} with {@code Import}.
   * <ul>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Import#setValues(Import)}
   */
  @Test
  public void testSetValuesWithImport_thenCallsGetName() {
    // Arrange
    Import resultImport = new Import();
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    Import otherElement = new Import();
    otherElement.addAttribute(attribute);

    // Act
    resultImport.setValues(otherElement);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Import}
   *   <li>{@link Import#setImportType(String)}
   *   <li>{@link Import#setLocation(String)}
   *   <li>{@link Import#setNamespace(String)}
   *   <li>{@link Import#getImportType()}
   *   <li>{@link Import#getLocation()}
   *   <li>{@link Import#getNamespace()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Import actualResultImport = new Import();
    actualResultImport.setImportType("Import Type");
    actualResultImport.setLocation("Location");
    actualResultImport.setNamespace("Namespace");
    String actualImportType = actualResultImport.getImportType();
    String actualLocation = actualResultImport.getLocation();

    // Assert that nothing has changed
    assertEquals("Import Type", actualImportType);
    assertEquals("Location", actualLocation);
    assertEquals("Namespace", actualResultImport.getNamespace());
    assertEquals(0, actualResultImport.getXmlColumnNumber());
    assertEquals(0, actualResultImport.getXmlRowNumber());
    assertTrue(actualResultImport.getAttributes().isEmpty());
    assertTrue(actualResultImport.getExtensionElements().isEmpty());
  }
}
