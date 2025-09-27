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

public class OperationDiffblueTest {
  /**
   * Test {@link Operation#clone()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code foo}.
   *   <li>Then return ErrorMessageRef size is one.
   * </ul>
   *
   * <p>Method under test: {@link Operation#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Operation Operation.clone()"})
  public void testClone_givenArrayListAddFoo_thenReturnErrorMessageRefSizeIsOne() {
    // Arrange
    ArrayList<String> errorMessageRef = new ArrayList<>();
    errorMessageRef.add("foo");

    Operation operation = new Operation();
    operation.setErrorMessageRef(errorMessageRef);
    operation.addAttribute(new ExtensionAttribute("Name"));

    // Act
    Operation actualCloneResult = operation.clone();

    // Assert
    List<String> errorMessageRef2 = actualCloneResult.getErrorMessageRef();
    assertEquals(1, errorMessageRef2.size());
    assertEquals("foo", errorMessageRef2.get(0));
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    assertTrue(attributes.containsKey("Name"));
  }

  /**
   * Test {@link Operation#clone()}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Operation#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Operation Operation.clone()"})
  public void testClone_givenHashMap42IsArrayList_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("42", new ArrayList<>());
    extensionElements.put("foo", new ArrayList<>());

    Operation operation = new Operation();
    operation.setExtensionElements(extensionElements);
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
   * Test {@link Operation#clone()}.
   *
   * <ul>
   *   <li>Given {@link Operation} (default constructor) Attributes is {@link HashMap#HashMap()}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Operation#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Operation Operation.clone()"})
  public void testClone_givenOperationAttributesIsHashMap_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("foo", new ArrayList<>());

    Operation operation = new Operation();
    operation.setExtensionElements(null);
    operation.setAttributes(attributes);

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
   * Test {@link Operation#clone()}.
   *
   * <ul>
   *   <li>Given {@link Operation} (default constructor) ErrorMessageRef is {@code null}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Operation#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Operation Operation.clone()"})
  public void testClone_givenOperationErrorMessageRefIsNull_thenReturnIdIsNull() {
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
   * Test {@link Operation#clone()}.
   *
   * <ul>
   *   <li>Given {@link Operation} (default constructor) ExtensionElements is {@link
   *       HashMap#HashMap()}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Operation#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Operation Operation.clone()"})
  public void testClone_givenOperationExtensionElementsIsHashMap_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", new ArrayList<>());

    Operation operation = new Operation();
    operation.setExtensionElements(extensionElements);
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
   * Test {@link Operation#clone()}.
   *
   * <ul>
   *   <li>Given {@link Operation} (default constructor) ExtensionElements is {@code null}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Operation#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Operation Operation.clone()"})
  public void testClone_givenOperationExtensionElementsIsNull_thenReturnIdIsNull() {
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
   * Test {@link Operation#clone()}.
   *
   * <ul>
   *   <li>Given {@link Operation} (default constructor).
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Operation#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Operation Operation.clone()"})
  public void testClone_givenOperation_thenReturnIdIsNull() {
    // Arrange and Act
    Operation actualCloneResult = new Operation().clone();

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
   * Test {@link Operation#clone()}.
   *
   * <ul>
   *   <li>Then return Attributes {@code Name} size is one.
   * </ul>
   *
   * <p>Method under test: {@link Operation#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Operation Operation.clone()"})
  public void testClone_thenReturnAttributesNameSizeIsOne() {
    // Arrange
    Operation operation = new Operation();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    operation.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = operation.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Operation#clone()}.
   *
   * <ul>
   *   <li>Then return Attributes size is two.
   * </ul>
   *
   * <p>Method under test: {@link Operation#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Operation Operation.clone()"})
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    Operation operation = new Operation();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    operation.addAttribute(attribute);
    operation.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = operation.clone().getAttributes();
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void Operation.<init>()",
    "List Operation.getErrorMessageRef()",
    "String Operation.getImplementationRef()",
    "String Operation.getInMessageRef()",
    "String Operation.getName()",
    "String Operation.getOutMessageRef()",
    "void Operation.setErrorMessageRef(List)",
    "void Operation.setImplementationRef(String)",
    "void Operation.setInMessageRef(String)",
    "void Operation.setName(String)",
    "void Operation.setOutMessageRef(String)"
  })
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

    // Assert
    assertEquals("Implementation Ref", actualImplementationRef);
    assertEquals("In Message Ref", actualInMessageRef);
    assertEquals("Name", actualName);
    assertEquals("Out Message Ref", actualOperation.getOutMessageRef());
    assertNull(actualOperation.getId());
    assertEquals(0, actualOperation.getXmlColumnNumber());
    assertEquals(0, actualOperation.getXmlRowNumber());
    assertTrue(actualErrorMessageRef.isEmpty());
    assertTrue(actualOperation.getAttributes().isEmpty());
    assertTrue(actualOperation.getExtensionElements().isEmpty());
    assertSame(errorMessageRef, actualErrorMessageRef);
  }
}
