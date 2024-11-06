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

public class CustomPropertyDiffblueTest {
  /**
   * Test {@link CustomProperty#clone()}.
   * <ul>
   *   <li>Given {@link CustomProperty} (default constructor).</li>
   *   <li>Then return ComplexValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomProperty#clone()}
   */
  @Test
  public void testClone_givenCustomProperty_thenReturnComplexValueIsNull() {
    // Arrange and Act
    CustomProperty actualCloneResult = (new CustomProperty()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getSimpleValue());
    assertNull(actualCloneResult.getComplexValue());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CustomProperty#clone()}.
   * <ul>
   *   <li>Given {@link DataGridRow} (default constructor) Fields is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomProperty#clone()}
   */
  @Test
  public void testClone_givenDataGridRowFieldsIsNull() {
    // Arrange
    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(null);

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(rows);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    // Act and Assert
    ComplexDataType complexValue2 = customProperty.clone().getComplexValue();
    assertTrue(complexValue2 instanceof DataGrid);
    List<DataGridRow> rows2 = ((DataGrid) complexValue2).getRows();
    assertEquals(1, rows2.size());
    DataGridRow getResult = rows2.get(0);
    assertEquals(0, getResult.getIndex());
    assertTrue(getResult.getFields().isEmpty());
  }

  /**
   * Test {@link CustomProperty#clone()}.
   * <ul>
   *   <li>Given {@link DataGrid} (default constructor) Rows is
   * {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return ComplexValue Rows Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomProperty#clone()}
   */
  @Test
  public void testClone_givenDataGridRowsIsArrayList_thenReturnComplexValueRowsEmpty() {
    // Arrange
    DataGrid complexValue = new DataGrid();
    complexValue.setRows(new ArrayList<>());

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    // Act
    CustomProperty actualCloneResult = customProperty.clone();

    // Assert
    ComplexDataType complexValue2 = actualCloneResult.getComplexValue();
    assertTrue(complexValue2 instanceof DataGrid);
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getSimpleValue());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(((DataGrid) complexValue2).getRows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CustomProperty#clone()}.
   * <ul>
   *   <li>Given {@link DataGrid} (default constructor) Rows is {@code null}.</li>
   *   <li>Then return ComplexValue Rows Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomProperty#clone()}
   */
  @Test
  public void testClone_givenDataGridRowsIsNull_thenReturnComplexValueRowsEmpty() {
    // Arrange
    DataGrid complexValue = new DataGrid();
    complexValue.setRows(null);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    // Act
    CustomProperty actualCloneResult = customProperty.clone();

    // Assert
    ComplexDataType complexValue2 = actualCloneResult.getComplexValue();
    assertTrue(complexValue2 instanceof DataGrid);
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getSimpleValue());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(((DataGrid) complexValue2).getRows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CustomProperty#clone()}.
   * <ul>
   *   <li>Then return ComplexValue Rows first Fields size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomProperty#clone()}
   */
  @Test
  public void testClone_thenReturnComplexValueRowsFirstFieldsSizeIsOne() {
    // Arrange
    ArrayList<DataGridField> fields = new ArrayList<>();
    fields.add(new DataGridField());

    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(fields);

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(rows);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    // Act and Assert
    ComplexDataType complexValue2 = customProperty.clone().getComplexValue();
    assertTrue(complexValue2 instanceof DataGrid);
    List<DataGridRow> rows2 = ((DataGrid) complexValue2).getRows();
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
   * Test {@link CustomProperty#clone()}.
   * <ul>
   *   <li>Then return ComplexValue Rows first Index is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomProperty#clone()}
   */
  @Test
  public void testClone_thenReturnComplexValueRowsFirstIndexIsZero() {
    // Arrange
    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(new ArrayList<>());

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(rows);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    // Act and Assert
    ComplexDataType complexValue2 = customProperty.clone().getComplexValue();
    assertTrue(complexValue2 instanceof DataGrid);
    List<DataGridRow> rows2 = ((DataGrid) complexValue2).getRows();
    assertEquals(1, rows2.size());
    DataGridRow getResult = rows2.get(0);
    assertEquals(0, getResult.getIndex());
    assertTrue(getResult.getFields().isEmpty());
  }

  /**
   * Test {@link CustomProperty#setValues(CustomProperty)} with
   * {@code otherProperty}.
   * <ul>
   *   <li>Given {@link DataGrid} {@link DataGrid#clone()} return {@link DataGrid}
   * (default constructor).</li>
   *   <li>Then calls {@link DataGrid#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomProperty#setValues(CustomProperty)}
   */
  @Test
  public void testSetValuesWithOtherProperty_givenDataGridCloneReturnDataGrid_thenCallsClone() {
    // Arrange
    CustomProperty customProperty = new CustomProperty();
    DataGrid complexValue = mock(DataGrid.class);
    when(complexValue.clone()).thenReturn(new DataGrid());

    CustomProperty otherProperty = new CustomProperty();
    otherProperty.setComplexValue(complexValue);

    // Act
    customProperty.setValues(otherProperty);

    // Assert
    verify(complexValue).clone();
  }

  /**
   * Test {@link CustomProperty#setValues(CustomProperty)} with
   * {@code otherProperty}.
   * <ul>
   *   <li>Then calls {@link DataGridRow#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CustomProperty#setValues(CustomProperty)}
   */
  @Test
  public void testSetValuesWithOtherProperty_thenCallsClone() {
    // Arrange
    CustomProperty customProperty = new CustomProperty();
    DataGridRow dataGridRow = mock(DataGridRow.class);
    when(dataGridRow.clone()).thenReturn(new DataGridRow());
    doNothing().when(dataGridRow).setFields(Mockito.<List<DataGridField>>any());
    dataGridRow.setFields(null);

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(rows);

    CustomProperty otherProperty = new CustomProperty();
    otherProperty.setComplexValue(complexValue);

    // Act
    customProperty.setValues(otherProperty);

    // Assert
    verify(dataGridRow).clone();
    verify(dataGridRow).setFields(isNull());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CustomProperty}
   *   <li>{@link CustomProperty#setComplexValue(ComplexDataType)}
   *   <li>{@link CustomProperty#setName(String)}
   *   <li>{@link CustomProperty#setSimpleValue(String)}
   *   <li>{@link CustomProperty#getComplexValue()}
   *   <li>{@link CustomProperty#getName()}
   *   <li>{@link CustomProperty#getSimpleValue()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    CustomProperty actualCustomProperty = new CustomProperty();
    DataGrid complexValue = new DataGrid();
    actualCustomProperty.setComplexValue(complexValue);
    actualCustomProperty.setName("Name");
    actualCustomProperty.setSimpleValue("42");
    ComplexDataType actualComplexValue = actualCustomProperty.getComplexValue();
    String actualName = actualCustomProperty.getName();

    // Assert that nothing has changed
    assertTrue(actualComplexValue instanceof DataGrid);
    assertEquals("42", actualCustomProperty.getSimpleValue());
    assertEquals("Name", actualName);
    assertEquals(0, actualCustomProperty.getXmlColumnNumber());
    assertEquals(0, actualCustomProperty.getXmlRowNumber());
    assertTrue(actualCustomProperty.getAttributes().isEmpty());
    assertTrue(actualCustomProperty.getExtensionElements().isEmpty());
    assertSame(complexValue, actualComplexValue);
  }
}
