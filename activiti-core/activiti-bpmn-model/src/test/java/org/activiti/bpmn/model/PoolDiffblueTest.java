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
import static org.junit.Assert.assertFalse;
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

public class PoolDiffblueTest {
  /**
   * Test {@link Pool#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pool#clone()}
   */
  @Test
  public void testClone_givenHashMap42IsArrayList_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("42", new ArrayList<>());
    extensionElements.put("foo", new ArrayList<>());

    Pool pool = new Pool();
    pool.setExtensionElements(extensionElements);
    pool.setAttributes(null);

    // Act
    Pool actualCloneResult = pool.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getProcessRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExecutable());
  }

  /**
   * Test {@link Pool#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pool#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));
    attributes.put("foo", new ArrayList<>());

    Pool pool = new Pool();
    pool.setExtensionElements(null);
    pool.setAttributes(attributes);

    // Act
    Pool actualCloneResult = pool.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getProcessRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExecutable());
  }

  /**
   * Test {@link Pool#clone()}.
   * <ul>
   *   <li>Given {@link Pool} (default constructor) Attributes is
   * {@link HashMap#HashMap()}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pool#clone()}
   */
  @Test
  public void testClone_givenPoolAttributesIsHashMap_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.put("foo", new ArrayList<>());

    Pool pool = new Pool();
    pool.setExtensionElements(null);
    pool.setAttributes(attributes);

    // Act
    Pool actualCloneResult = pool.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getProcessRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExecutable());
  }

  /**
   * Test {@link Pool#clone()}.
   * <ul>
   *   <li>Given {@link Pool} (default constructor) Executable is
   * {@code false}.</li>
   *   <li>Then return not Executable.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pool#clone()}
   */
  @Test
  public void testClone_givenPoolExecutableIsFalse_thenReturnNotExecutable() {
    // Arrange
    Pool pool = new Pool();
    pool.setExecutable(false);
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    pool.addAttribute(attribute);

    // Act
    Pool actualCloneResult = pool.clone();

    // Assert
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertFalse(actualCloneResult.isExecutable());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Pool#clone()}.
   * <ul>
   *   <li>Given {@link Pool} (default constructor) ExtensionElements is
   * {@link HashMap#HashMap()}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pool#clone()}
   */
  @Test
  public void testClone_givenPoolExtensionElementsIsHashMap_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("foo", new ArrayList<>());

    Pool pool = new Pool();
    pool.setExtensionElements(extensionElements);
    pool.setAttributes(null);

    // Act
    Pool actualCloneResult = pool.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getProcessRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExecutable());
  }

  /**
   * Test {@link Pool#clone()}.
   * <ul>
   *   <li>Given {@link Pool} (default constructor) ExtensionElements is
   * {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pool#clone()}
   */
  @Test
  public void testClone_givenPoolExtensionElementsIsNull_thenReturnIdIsNull() {
    // Arrange
    Pool pool = new Pool();
    pool.setExtensionElements(null);
    pool.setAttributes(null);

    // Act
    Pool actualCloneResult = pool.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getProcessRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExecutable());
  }

  /**
   * Test {@link Pool#clone()}.
   * <ul>
   *   <li>Given {@link Pool} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pool#clone()}
   */
  @Test
  public void testClone_givenPool_thenReturnIdIsNull() {
    // Arrange and Act
    Pool actualCloneResult = (new Pool()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getProcessRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExecutable());
  }

  /**
   * Test {@link Pool#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pool#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    Pool pool = new Pool();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    pool.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = pool.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Pool#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pool#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    Pool pool = new Pool();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    pool.addAttribute(attribute);
    pool.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = pool.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link Pool#setValues(Pool)} with {@code Pool}.
   * <ul>
   *   <li>Given {@link ExtensionAttribute} {@link ExtensionAttribute#getName()}
   * return {@code Name}.</li>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Pool#setValues(Pool)}
   */
  @Test
  public void testSetValuesWithPool_givenExtensionAttributeGetNameReturnName_thenCallsGetName() {
    // Arrange
    Pool pool = new Pool();
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    Pool otherElement = new Pool();
    otherElement.addAttribute(attribute);

    // Act
    pool.setValues(otherElement);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Pool}
   *   <li>{@link Pool#setExecutable(boolean)}
   *   <li>{@link Pool#setName(String)}
   *   <li>{@link Pool#setProcessRef(String)}
   *   <li>{@link Pool#getName()}
   *   <li>{@link Pool#getProcessRef()}
   *   <li>{@link Pool#isExecutable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Pool actualPool = new Pool();
    actualPool.setExecutable(true);
    actualPool.setName("Name");
    actualPool.setProcessRef("Process Ref");
    String actualName = actualPool.getName();
    String actualProcessRef = actualPool.getProcessRef();
    boolean actualIsExecutableResult = actualPool.isExecutable();

    // Assert that nothing has changed
    assertEquals("Name", actualName);
    assertEquals("Process Ref", actualProcessRef);
    assertEquals(0, actualPool.getXmlColumnNumber());
    assertEquals(0, actualPool.getXmlRowNumber());
    assertTrue(actualPool.getAttributes().isEmpty());
    assertTrue(actualPool.getExtensionElements().isEmpty());
    assertTrue(actualIsExecutableResult);
  }
}
