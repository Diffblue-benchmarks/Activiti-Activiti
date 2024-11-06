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
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class DataObjectDiffblueTest {
  /**
   * Test {@link DataObject#clone()}.
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).</li>
   *   <li>Then return {@link BooleanDataObject}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataObject#clone()}
   */
  @Test
  public void testClone_givenBooleanDataObject_thenReturnBooleanDataObject() {
    // Arrange and Act
    BooleanDataObject actualCloneResult = (new BooleanDataObject()).clone();

    // Assert
    assertTrue(actualCloneResult instanceof BooleanDataObject);
    assertNull(((BooleanDataObject) actualCloneResult).getValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataObject#clone()}.
   * <ul>
   *   <li>Given {@link DataObject} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataObject#clone()}
   */
  @Test
  public void testClone_givenDataObject_thenReturnIdIsNull() {
    // Arrange and Act
    DataObject actualCloneResult = (new DataObject()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataObject#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataObject#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));

    DataObject dataObject = new DataObject();
    dataObject.setExtensionElements(null);
    dataObject.setAttributes(attributes);

    // Act
    DataObject actualCloneResult = dataObject.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataObject#setValues(DataObject)} with {@code DataObject}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then {@link DataObject} (default constructor) Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataObject#setValues(DataObject)}
   */
  @Test
  public void testSetValuesWithDataObject_given42_thenDataObjectIdIs42() {
    // Arrange
    DataObject dataObject = new DataObject();
    BooleanDataObject otherElement = mock(BooleanDataObject.class);
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    ItemDefinition itemDefinition = new ItemDefinition();
    when(otherElement.getItemSubjectRef()).thenReturn(itemDefinition);

    // Act
    dataObject.setValues(otherElement);

    // Assert
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement, atLeast(1)).getId();
    verify(otherElement).getItemSubjectRef();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement, atLeast(1)).getName();
    assertEquals("42", dataObject.getId());
    assertEquals("Documentation", dataObject.getDocumentation());
    assertEquals("Name", dataObject.getName());
    assertSame(itemDefinition, dataObject.getItemSubjectRef());
  }

  /**
   * Test {@link DataObject#setValues(DataObject)} with {@code DataObject}.
   * <ul>
   *   <li>When {@link DataObject} (default constructor).</li>
   *   <li>Then {@link DataObject} (default constructor) Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataObject#setValues(DataObject)}
   */
  @Test
  public void testSetValuesWithDataObject_whenDataObject_thenDataObjectIdIsNull() {
    // Arrange
    DataObject dataObject = new DataObject();
    DataObject otherElement = new DataObject();

    // Act
    dataObject.setValues(otherElement);

    // Assert
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getItemSubjectRef());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DataObject}
   *   <li>{@link DataObject#setItemSubjectRef(ItemDefinition)}
   *   <li>{@link DataObject#getItemSubjectRef()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DataObject actualDataObject = new DataObject();
    ItemDefinition itemSubjectRef = new ItemDefinition();
    actualDataObject.setItemSubjectRef(itemSubjectRef);
    ItemDefinition actualItemSubjectRef = actualDataObject.getItemSubjectRef();

    // Assert that nothing has changed
    assertEquals(0, actualDataObject.getXmlColumnNumber());
    assertEquals(0, actualDataObject.getXmlRowNumber());
    assertTrue(actualDataObject.getExecutionListeners().isEmpty());
    assertTrue(actualDataObject.getAttributes().isEmpty());
    assertTrue(actualDataObject.getExtensionElements().isEmpty());
    assertSame(itemSubjectRef, actualItemSubjectRef);
  }
}
