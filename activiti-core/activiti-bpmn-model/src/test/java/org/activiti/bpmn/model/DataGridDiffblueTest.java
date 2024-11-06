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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;

public class DataGridDiffblueTest {
  /**
   * Test {@link DataGrid#clone()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DataGridField} (default
   * constructor).</li>
   *   <li>Then return Rows first Fields size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGrid#clone()}
   */
  @Test
  public void testClone_givenArrayListAddDataGridField_thenReturnRowsFirstFieldsSizeIsOne() {
    // Arrange
    ArrayList<DataGridField> fields = new ArrayList<>();
    fields.add(new DataGridField());

    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(fields);

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid dataGrid = new DataGrid();
    dataGrid.setRows(rows);

    // Act and Assert
    List<DataGridRow> rows2 = dataGrid.clone().getRows();
    assertEquals(1, rows2.size());
    List<DataGridField> fields2 = rows2.get(0).getFields();
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
   * Test {@link DataGrid#clone()}.
   * <ul>
   *   <li>Given {@link DataGridRow} (default constructor) Fields is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Rows first Index is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGrid#clone()}
   */
  @Test
  public void testClone_givenDataGridRowFieldsIsArrayList_thenReturnRowsFirstIndexIsZero() {
    // Arrange
    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(new ArrayList<>());

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid dataGrid = new DataGrid();
    dataGrid.setRows(rows);

    // Act and Assert
    List<DataGridRow> rows2 = dataGrid.clone().getRows();
    assertEquals(1, rows2.size());
    DataGridRow getResult = rows2.get(0);
    assertEquals(0, getResult.getIndex());
    assertTrue(getResult.getFields().isEmpty());
  }

  /**
   * Test {@link DataGrid#clone()}.
   * <ul>
   *   <li>Given {@link DataGridRow} (default constructor) Fields is
   * {@code null}.</li>
   *   <li>Then return Rows first Index is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGrid#clone()}
   */
  @Test
  public void testClone_givenDataGridRowFieldsIsNull_thenReturnRowsFirstIndexIsZero() {
    // Arrange
    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(null);

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid dataGrid = new DataGrid();
    dataGrid.setRows(rows);

    // Act and Assert
    List<DataGridRow> rows2 = dataGrid.clone().getRows();
    assertEquals(1, rows2.size());
    DataGridRow getResult = rows2.get(0);
    assertEquals(0, getResult.getIndex());
    assertTrue(getResult.getFields().isEmpty());
  }

  /**
   * Test {@link DataGrid#clone()}.
   * <ul>
   *   <li>Given {@link DataGrid} (default constructor) Rows is {@code null}.</li>
   *   <li>Then return Rows Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGrid#clone()}
   */
  @Test
  public void testClone_givenDataGridRowsIsNull_thenReturnRowsEmpty() {
    // Arrange
    DataGrid dataGrid = new DataGrid();
    dataGrid.setRows(null);

    // Act and Assert
    assertTrue(dataGrid.clone().getRows().isEmpty());
  }

  /**
   * Test {@link DataGrid#clone()}.
   * <ul>
   *   <li>Given {@link DataGrid} (default constructor).</li>
   *   <li>Then return Rows Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGrid#clone()}
   */
  @Test
  public void testClone_givenDataGrid_thenReturnRowsEmpty() {
    // Arrange, Act and Assert
    assertTrue((new DataGrid()).clone().getRows().isEmpty());
  }

  /**
   * Test {@link DataGrid#setValues(DataGrid)}.
   * <ul>
   *   <li>Given {@link DataGridField} {@link DataGridField#clone()} return
   * {@link DataGridField} (default constructor).</li>
   *   <li>Then calls {@link DataGridField#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGrid#setValues(DataGrid)}
   */
  @Test
  public void testSetValues_givenDataGridFieldCloneReturnDataGridField_thenCallsClone() {
    // Arrange
    DataGrid dataGrid = new DataGrid();
    DataGridField dataGridField = mock(DataGridField.class);
    when(dataGridField.clone()).thenReturn(new DataGridField());

    ArrayList<DataGridField> fields = new ArrayList<>();
    fields.add(dataGridField);

    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(fields);

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid otherGrid = new DataGrid();
    otherGrid.setRows(rows);

    // Act
    dataGrid.setValues(otherGrid);

    // Assert
    verify(dataGridField).clone();
  }

  /**
   * Test {@link DataGrid#setValues(DataGrid)}.
   * <ul>
   *   <li>Given {@link DataGridRow} {@link DataGridRow#clone()} return
   * {@link DataGridRow} (default constructor).</li>
   *   <li>Then calls {@link DataGridRow#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataGrid#setValues(DataGrid)}
   */
  @Test
  public void testSetValues_givenDataGridRowCloneReturnDataGridRow_thenCallsClone() {
    // Arrange
    DataGrid dataGrid = new DataGrid();
    DataGridRow dataGridRow = mock(DataGridRow.class);
    when(dataGridRow.clone()).thenReturn(new DataGridRow());
    doNothing().when(dataGridRow).setFields(Mockito.<List<DataGridField>>any());
    dataGridRow.setFields(null);

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid otherGrid = new DataGrid();
    otherGrid.setRows(rows);

    // Act
    dataGrid.setValues(otherGrid);

    // Assert
    verify(dataGridRow).clone();
    verify(dataGridRow).setFields(isNull());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DataGrid}
   *   <li>{@link DataGrid#setRows(List)}
   *   <li>{@link DataGrid#getRows()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DataGrid actualDataGrid = new DataGrid();
    ArrayList<DataGridRow> rows = new ArrayList<>();
    actualDataGrid.setRows(rows);
    List<DataGridRow> actualRows = actualDataGrid.getRows();

    // Assert that nothing has changed
    assertTrue(actualRows.isEmpty());
    assertSame(rows, actualRows);
  }
}
