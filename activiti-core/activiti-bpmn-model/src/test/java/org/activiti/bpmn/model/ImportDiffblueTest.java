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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ImportDiffblueTest {
  /**
   * Test {@link Import#clone()}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Import#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Import Import.clone()"})
  public void testClone_givenHashMap42IsArrayList_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("42", new ArrayList<>());
    extensionElements.put("Key", new ArrayList<>());

    Import resultImport = new Import();
    resultImport.setExtensionElements(extensionElements);
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
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code Key} is {@link ArrayList#ArrayList()}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Import#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Import Import.clone()"})
  public void testClone_givenHashMapKeyIsArrayList_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("Key", new ArrayList<>());

    Import resultImport = new Import();
    resultImport.setExtensionElements(extensionElements);
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
   *
   * <ul>
   *   <li>Given {@link Import} (default constructor) ExtensionElements is {@link
   *       HashMap#HashMap()}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Import#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Import Import.clone()"})
  public void testClone_givenImportExtensionElementsIsHashMap_thenReturnIdIsNull() {
    // Arrange
    Import resultImport = new Import();
    resultImport.setExtensionElements(new HashMap<>());
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
   *
   * <ul>
   *   <li>Given {@link Import} (default constructor) ExtensionElements is {@code null}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Import#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Import Import.clone()"})
  public void testClone_givenImportExtensionElementsIsNull_thenReturnIdIsNull() {
    // Arrange
    Import resultImport = new Import();
    resultImport.setExtensionElements(null);
    resultImport.setAttributes(new HashMap<>());

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
   *
   * <ul>
   *   <li>Given {@link Import} (default constructor).
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Import#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Import Import.clone()"})
  public void testClone_givenImport_thenReturnIdIsNull() {
    // Arrange and Act
    Import actualCloneResult = new Import().clone();

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
   *
   * <ul>
   *   <li>Then return Attributes size is one.
   * </ul>
   *
   * <p>Method under test: {@link Import#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Import Import.clone()"})
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
   *
   * <ul>
   *   <li>Then return Attributes size is two.
   * </ul>
   *
   * <p>Method under test: {@link Import#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Import Import.clone()"})
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
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Import.<init>()",
    "String Import.getImportType()",
    "String Import.getLocation()",
    "String Import.getNamespace()",
    "void Import.setImportType(String)",
    "void Import.setLocation(String)",
    "void Import.setNamespace(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    Import actualResultImport = new Import();
    actualResultImport.setImportType("Import Type");
    actualResultImport.setLocation("Location");
    actualResultImport.setNamespace("Namespace");
    String actualImportType = actualResultImport.getImportType();
    String actualLocation = actualResultImport.getLocation();

    // Assert
    assertEquals("Import Type", actualImportType);
    assertEquals("Location", actualLocation);
    assertEquals("Namespace", actualResultImport.getNamespace());
    assertNull(actualResultImport.getId());
    assertEquals(0, actualResultImport.getXmlColumnNumber());
    assertEquals(0, actualResultImport.getXmlRowNumber());
    assertTrue(actualResultImport.getAttributes().isEmpty());
    assertTrue(actualResultImport.getExtensionElements().isEmpty());
  }
}
