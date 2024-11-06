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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;
import org.mockito.Mockito;

public class InterfaceDiffblueTest {
  /**
   * Test {@link Interface#clone()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code foo}.</li>
   *   <li>Then return Operations first ErrorMessageRef size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#clone()}
   */
  @Test
  public void testClone_givenArrayListAddFoo_thenReturnOperationsFirstErrorMessageRefSizeIsOne() {
    // Arrange
    ArrayList<String> errorMessageRef = new ArrayList<>();
    errorMessageRef.add("foo");

    Operation operation = new Operation();
    operation.setErrorMessageRef(errorMessageRef);

    ArrayList<Operation> operations = new ArrayList<>();
    operations.add(operation);

    Interface resultInterface = new Interface();
    resultInterface.setOperations(operations);

    // Act and Assert
    List<Operation> operations2 = resultInterface.clone().getOperations();
    assertEquals(1, operations2.size());
    Operation getResult = operations2.get(0);
    List<String> errorMessageRef2 = getResult.getErrorMessageRef();
    assertEquals(1, errorMessageRef2.size());
    assertEquals("foo", errorMessageRef2.get(0));
    assertNull(getResult.getId());
    assertNull(getResult.getImplementationRef());
    assertNull(getResult.getInMessageRef());
    assertNull(getResult.getName());
    assertNull(getResult.getOutMessageRef());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Interface#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#clone()}
   */
  @Test
  public void testClone_givenHashMap42IsArrayList_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("42", new ArrayList<>());
    extensionElements.put("foo", new ArrayList<>());

    Interface resultInterface = new Interface();
    resultInterface.setExtensionElements(extensionElements);
    resultInterface.setAttributes(null);

    // Act
    Interface actualCloneResult = resultInterface.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImplementationRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getOperations().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Interface#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));
    attributes.put("foo", new ArrayList<>());

    Interface resultInterface = new Interface();
    resultInterface.setExtensionElements(null);
    resultInterface.setAttributes(attributes);

    // Act
    Interface actualCloneResult = resultInterface.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImplementationRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getOperations().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Interface#clone()}.
   * <ul>
   *   <li>Given {@link Interface} (default constructor) Attributes is
   * {@link HashMap#HashMap()}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#clone()}
   */
  @Test
  public void testClone_givenInterfaceAttributesIsHashMap_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("foo", new ArrayList<>());

    Interface resultInterface = new Interface();
    resultInterface.setExtensionElements(null);
    resultInterface.setAttributes(attributes);

    // Act
    Interface actualCloneResult = resultInterface.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImplementationRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getOperations().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Interface#clone()}.
   * <ul>
   *   <li>Given {@link Interface} (default constructor) ExtensionElements is
   * {@link HashMap#HashMap()}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#clone()}
   */
  @Test
  public void testClone_givenInterfaceExtensionElementsIsHashMap_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", new ArrayList<>());

    Interface resultInterface = new Interface();
    resultInterface.setExtensionElements(extensionElements);
    resultInterface.setAttributes(null);

    // Act
    Interface actualCloneResult = resultInterface.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImplementationRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getOperations().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Interface#clone()}.
   * <ul>
   *   <li>Given {@link Interface} (default constructor) ExtensionElements is
   * {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#clone()}
   */
  @Test
  public void testClone_givenInterfaceExtensionElementsIsNull_thenReturnIdIsNull() {
    // Arrange
    Interface resultInterface = new Interface();
    resultInterface.setExtensionElements(null);
    resultInterface.setAttributes(null);

    // Act
    Interface actualCloneResult = resultInterface.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImplementationRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getOperations().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Interface#clone()}.
   * <ul>
   *   <li>Given {@link Interface} (default constructor) Operations is
   * {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#clone()}
   */
  @Test
  public void testClone_givenInterfaceOperationsIsNull_thenReturnIdIsNull() {
    // Arrange
    Interface resultInterface = new Interface();
    resultInterface.setOperations(null);

    // Act
    Interface actualCloneResult = resultInterface.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImplementationRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getOperations().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Interface#clone()}.
   * <ul>
   *   <li>Given {@link Interface} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#clone()}
   */
  @Test
  public void testClone_givenInterface_thenReturnIdIsNull() {
    // Arrange and Act
    Interface actualCloneResult = (new Interface()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getImplementationRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getOperations().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Interface#clone()}.
   * <ul>
   *   <li>Given {@link Operation} (default constructor) ErrorMessageRef is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#clone()}
   */
  @Test
  public void testClone_givenOperationErrorMessageRefIsNull() {
    // Arrange
    Operation operation = new Operation();
    operation.setErrorMessageRef(null);

    ArrayList<Operation> operations = new ArrayList<>();
    operations.add(operation);

    Interface resultInterface = new Interface();
    resultInterface.setOperations(operations);

    // Act and Assert
    List<Operation> operations2 = resultInterface.clone().getOperations();
    assertEquals(1, operations2.size());
    Operation getResult = operations2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getImplementationRef());
    assertNull(getResult.getInMessageRef());
    assertNull(getResult.getName());
    assertNull(getResult.getOutMessageRef());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getErrorMessageRef().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Interface#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    Interface resultInterface = new Interface();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    resultInterface.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = resultInterface.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Interface#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    Interface resultInterface = new Interface();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    resultInterface.addAttribute(attribute);
    resultInterface.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = resultInterface.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Interface#clone()}.
   * <ul>
   *   <li>Then return Operations first ErrorMessageRef Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#clone()}
   */
  @Test
  public void testClone_thenReturnOperationsFirstErrorMessageRefEmpty() {
    // Arrange
    Operation operation = new Operation();
    operation.setErrorMessageRef(new ArrayList<>());

    ArrayList<Operation> operations = new ArrayList<>();
    operations.add(operation);

    Interface resultInterface = new Interface();
    resultInterface.setOperations(operations);

    // Act and Assert
    List<Operation> operations2 = resultInterface.clone().getOperations();
    assertEquals(1, operations2.size());
    Operation getResult = operations2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getImplementationRef());
    assertNull(getResult.getInMessageRef());
    assertNull(getResult.getName());
    assertNull(getResult.getOutMessageRef());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getErrorMessageRef().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Interface#setValues(Interface)} with {@code Interface}.
   * <ul>
   *   <li>Given {@link Operation} {@link Operation#clone()} return
   * {@link Operation} (default constructor).</li>
   *   <li>Then calls {@link Operation#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#setValues(Interface)}
   */
  @Test
  public void testSetValuesWithInterface_givenOperationCloneReturnOperation_thenCallsClone() {
    // Arrange
    Interface resultInterface = new Interface();
    Operation operation = mock(Operation.class);
    when(operation.clone()).thenReturn(new Operation());
    doNothing().when(operation).setErrorMessageRef(Mockito.<List<String>>any());
    operation.setErrorMessageRef(null);

    ArrayList<Operation> operations = new ArrayList<>();
    operations.add(operation);

    Interface otherElement = new Interface();
    otherElement.setOperations(operations);

    // Act
    resultInterface.setValues(otherElement);

    // Assert
    verify(operation).clone();
    verify(operation).setErrorMessageRef(isNull());
  }

  /**
   * Test {@link Interface#setValues(Interface)} with {@code Interface}.
   * <ul>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Interface#setValues(Interface)}
   */
  @Test
  public void testSetValuesWithInterface_thenCallsGetName() {
    // Arrange
    Interface resultInterface = new Interface();
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    Interface otherElement = new Interface();
    otherElement.addAttribute(attribute);

    // Act
    resultInterface.setValues(otherElement);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Interface}
   *   <li>{@link Interface#setImplementationRef(String)}
   *   <li>{@link Interface#setName(String)}
   *   <li>{@link Interface#setOperations(List)}
   *   <li>{@link Interface#getImplementationRef()}
   *   <li>{@link Interface#getName()}
   *   <li>{@link Interface#getOperations()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Interface actualResultInterface = new Interface();
    actualResultInterface.setImplementationRef("Implementation Ref");
    actualResultInterface.setName("Name");
    ArrayList<Operation> operations = new ArrayList<>();
    actualResultInterface.setOperations(operations);
    String actualImplementationRef = actualResultInterface.getImplementationRef();
    String actualName = actualResultInterface.getName();
    List<Operation> actualOperations = actualResultInterface.getOperations();

    // Assert that nothing has changed
    assertEquals("Implementation Ref", actualImplementationRef);
    assertEquals("Name", actualName);
    assertEquals(0, actualResultInterface.getXmlColumnNumber());
    assertEquals(0, actualResultInterface.getXmlRowNumber());
    assertTrue(actualOperations.isEmpty());
    assertTrue(actualResultInterface.getAttributes().isEmpty());
    assertTrue(actualResultInterface.getExtensionElements().isEmpty());
    assertSame(operations, actualOperations);
  }
}
