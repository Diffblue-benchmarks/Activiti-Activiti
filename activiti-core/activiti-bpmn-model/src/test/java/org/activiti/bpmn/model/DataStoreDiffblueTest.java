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

public class DataStoreDiffblueTest {
  /**
   * Test {@link DataStore#clone()}.
   * <ul>
   *   <li>Given {@link DataStore} (default constructor) ExtensionElements is
   * {@code null}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataStore#clone()}
   */
  @Test
  public void testClone_givenDataStoreExtensionElementsIsNull_thenReturnIdIsNull() {
    // Arrange
    DataStore dataStore = new DataStore();
    dataStore.setExtensionElements(null);
    dataStore.setAttributes(null);

    // Act
    DataStore actualCloneResult = dataStore.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDataState());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataStore#clone()}.
   * <ul>
   *   <li>Given {@link DataStore} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataStore#clone()}
   */
  @Test
  public void testClone_givenDataStore_thenReturnIdIsNull() {
    // Arrange and Act
    DataStore actualCloneResult = (new DataStore()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDataState());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataStore#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataStore#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    DataStore dataStore = new DataStore();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    dataStore.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = dataStore.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link DataStore#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataStore#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    DataStore dataStore = new DataStore();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    dataStore.addAttribute(attribute);
    dataStore.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = dataStore.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link DataStore#setValues(DataStore)} with {@code DataStore}.
   * <ul>
   *   <li>Then calls {@link ExtensionAttribute#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataStore#setValues(DataStore)}
   */
  @Test
  public void testSetValuesWithDataStore_thenCallsGetName() {
    // Arrange
    DataStore dataStore = new DataStore();
    ExtensionAttribute attribute = mock(ExtensionAttribute.class);
    when(attribute.getName()).thenReturn("Name");

    DataStore otherElement = new DataStore();
    otherElement.addAttribute(attribute);

    // Act
    dataStore.setValues(otherElement);

    // Assert
    verify(attribute, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DataStore}
   *   <li>{@link DataStore#setDataState(String)}
   *   <li>{@link DataStore#setItemSubjectRef(String)}
   *   <li>{@link DataStore#setName(String)}
   *   <li>{@link DataStore#getDataState()}
   *   <li>{@link DataStore#getItemSubjectRef()}
   *   <li>{@link DataStore#getName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DataStore actualDataStore = new DataStore();
    actualDataStore.setDataState("Data State");
    actualDataStore.setItemSubjectRef("Hello from the Dreaming Spires");
    actualDataStore.setName("Name");
    String actualDataState = actualDataStore.getDataState();
    String actualItemSubjectRef = actualDataStore.getItemSubjectRef();

    // Assert that nothing has changed
    assertEquals("Data State", actualDataState);
    assertEquals("Hello from the Dreaming Spires", actualItemSubjectRef);
    assertEquals("Name", actualDataStore.getName());
    assertEquals(0, actualDataStore.getXmlColumnNumber());
    assertEquals(0, actualDataStore.getXmlRowNumber());
    assertTrue(actualDataStore.getAttributes().isEmpty());
    assertTrue(actualDataStore.getExtensionElements().isEmpty());
  }
}
