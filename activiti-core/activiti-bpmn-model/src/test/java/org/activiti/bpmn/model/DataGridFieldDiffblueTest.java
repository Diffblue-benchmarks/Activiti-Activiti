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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class DataGridFieldDiffblueTest {
  /**
   * Test {@link DataGridField#clone()}.
   * <ul>
   *   <li>Given {@link DataGridField} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGridField#clone()}
   */
  @Test
  public void testClone_givenDataGridField() {
    // Arrange and Act
    DataGridField actualCloneResult = (new DataGridField()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getValue());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataGridField#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGridField#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    DataGridField dataGridField = new DataGridField();
    dataGridField.setExtensionElements(extensionElements);

    // Act
    DataGridField actualCloneResult = dataGridField.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getValue());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataGridField#setValues(DataGridField)} with {@code otherField}.
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGridField#setValues(DataGridField)}
   */
  @Test
  public void testSetValuesWithOtherField_thenCallsGetName() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    DataGridField dataGridField = new DataGridField();
    dataGridField.addExtensionElement(extensionElement);

    // Act
    dataGridField.setValues(new DataGridField());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DataGridField}
   *   <li>{@link DataGridField#setName(String)}
   *   <li>{@link DataGridField#setValue(String)}
   *   <li>{@link DataGridField#getName()}
   *   <li>{@link DataGridField#getValue()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DataGridField actualDataGridField = new DataGridField();
    actualDataGridField.setName("Name");
    actualDataGridField.setValue("42");
    String actualName = actualDataGridField.getName();

    // Assert that nothing has changed
    assertEquals("42", actualDataGridField.getValue());
    assertEquals("Name", actualName);
    assertEquals(0, actualDataGridField.getXmlColumnNumber());
    assertEquals(0, actualDataGridField.getXmlRowNumber());
    assertTrue(actualDataGridField.getAttributes().isEmpty());
    assertTrue(actualDataGridField.getExtensionElements().isEmpty());
  }
}
