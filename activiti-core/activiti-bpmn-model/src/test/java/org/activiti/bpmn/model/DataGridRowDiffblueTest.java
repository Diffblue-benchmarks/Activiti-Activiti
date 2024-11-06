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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class DataGridRowDiffblueTest {
  /**
   * Test {@link DataGridRow#clone()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DataGridField} (default
   * constructor).</li>
   *   <li>Then return Fields size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGridRow#clone()}
   */
  @Test
  public void testClone_givenArrayListAddDataGridField_thenReturnFieldsSizeIsOne() {
    // Arrange
    ArrayList<DataGridField> fields = new ArrayList<>();
    fields.add(new DataGridField());

    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(fields);

    // Act and Assert
    List<DataGridField> fields2 = dataGridRow.clone().getFields();
    assertEquals(1, fields2.size());
    DataGridField getResult = fields2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertNull(getResult.getValue());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataGridRow#clone()}.
   * <ul>
   *   <li>Given {@link DataGridRow} (default constructor) Fields is
   * {@code null}.</li>
   *   <li>Then return Index is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGridRow#clone()}
   */
  @Test
  public void testClone_givenDataGridRowFieldsIsNull_thenReturnIndexIsZero() {
    // Arrange
    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(null);

    // Act
    DataGridRow actualCloneResult = dataGridRow.clone();

    // Assert
    assertEquals(0, actualCloneResult.getIndex());
    assertTrue(actualCloneResult.getFields().isEmpty());
  }

  /**
   * Test {@link DataGridRow#clone()}.
   * <ul>
   *   <li>Given {@link DataGridRow} (default constructor).</li>
   *   <li>Then return Index is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGridRow#clone()}
   */
  @Test
  public void testClone_givenDataGridRow_thenReturnIndexIsZero() {
    // Arrange and Act
    DataGridRow actualCloneResult = (new DataGridRow()).clone();

    // Assert
    assertEquals(0, actualCloneResult.getIndex());
    assertTrue(actualCloneResult.getFields().isEmpty());
  }

  /**
   * Test {@link DataGridRow#setValues(DataGridRow)}.
   * <ul>
   *   <li>Given {@link DataGridField} {@link DataGridField#clone()} return
   * {@link DataGridField} (default constructor).</li>
   *   <li>Then calls {@link DataGridField#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGridRow#setValues(DataGridRow)}
   */
  @Test
  public void testSetValues_givenDataGridFieldCloneReturnDataGridField_thenCallsClone() {
    // Arrange
    DataGridRow dataGridRow = new DataGridRow();
    DataGridField dataGridField = mock(DataGridField.class);
    when(dataGridField.clone()).thenReturn(new DataGridField());

    ArrayList<DataGridField> fields = new ArrayList<>();
    fields.add(dataGridField);

    DataGridRow otherRow = new DataGridRow();
    otherRow.setFields(fields);

    // Act
    dataGridRow.setValues(otherRow);

    // Assert
    verify(dataGridField).clone();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DataGridRow}
   *   <li>{@link DataGridRow#setFields(List)}
   *   <li>{@link DataGridRow#setIndex(int)}
   *   <li>{@link DataGridRow#getFields()}
   *   <li>{@link DataGridRow#getIndex()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DataGridRow actualDataGridRow = new DataGridRow();
    ArrayList<DataGridField> fields = new ArrayList<>();
    actualDataGridRow.setFields(fields);
    actualDataGridRow.setIndex(1);
    List<DataGridField> actualFields = actualDataGridRow.getFields();

    // Assert that nothing has changed
    assertEquals(1, actualDataGridRow.getIndex());
    assertTrue(actualFields.isEmpty());
    assertSame(fields, actualFields);
  }
}
