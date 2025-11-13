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
package org.activiti.bpmn.model.alfresco;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.ComplexDataType;
import org.activiti.bpmn.model.CustomProperty;
import org.activiti.bpmn.model.DataGrid;
import org.activiti.bpmn.model.DataGridField;
import org.activiti.bpmn.model.DataGridRow;
import org.activiti.bpmn.model.FieldExtension;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class AlfrescoScriptTaskDiffblueTest {
  /**
   * Test {@link AlfrescoScriptTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link AlfrescoScriptTask} (default constructor).
   *   <li>Then return Behavior is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AlfrescoScriptTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AlfrescoScriptTask AlfrescoScriptTask.clone()"})
  public void testClone_givenAlfrescoScriptTask_thenReturnBehaviorIsNull() {
    // Arrange and Act
    AlfrescoScriptTask actualCloneResult = new AlfrescoScriptTask().clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getExtensionId());
    assertNull(actualCloneResult.getImplementation());
    assertNull(actualCloneResult.getImplementationType());
    assertNull(actualCloneResult.getOperationRef());
    assertNull(actualCloneResult.getResultVariableName());
    assertNull(actualCloneResult.getSkipExpression());
    assertNull(actualCloneResult.getType());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(actualCloneResult.isExtended());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link AlfrescoScriptTask#clone()}.
   *
   * <ul>
   *   <li>Then CustomProperties first ComplexValue return {@link DataGrid}.
   * </ul>
   *
   * <p>Method under test: {@link AlfrescoScriptTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AlfrescoScriptTask AlfrescoScriptTask.clone()"})
  public void testClone_thenCustomPropertiesFirstComplexValueReturnDataGrid() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

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

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    AlfrescoScriptTask alfrescoScriptTask = new AlfrescoScriptTask();
    alfrescoScriptTask.setFieldExtensions(fieldExtensions);
    alfrescoScriptTask.setCustomProperties(customProperties);

    // Act and Assert
    List<CustomProperty> customProperties2 = alfrescoScriptTask.clone().getCustomProperties();
    assertEquals(1, customProperties2.size());
    ComplexDataType complexValue2 = customProperties2.get(0).getComplexValue();
    assertTrue(complexValue2 instanceof DataGrid);
    List<DataGridRow> rows2 = ((DataGrid) complexValue2).getRows();
    assertEquals(1, rows2.size());
    DataGridRow getResult = rows2.get(0);
    List<DataGridField> fields2 = getResult.getFields();
    assertEquals(1, fields2.size());
    DataGridField getResult2 = fields2.get(0);
    assertNull(getResult2.getId());
    assertNull(getResult2.getName());
    assertNull(getResult2.getValue());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult.getIndex());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AlfrescoScriptTask#clone()}.
   *
   * <ul>
   *   <li>Then return CustomProperties first Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AlfrescoScriptTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AlfrescoScriptTask AlfrescoScriptTask.clone()"})
  public void testClone_thenReturnCustomPropertiesFirstIdIsNull() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    AlfrescoScriptTask alfrescoScriptTask = new AlfrescoScriptTask();
    alfrescoScriptTask.setFieldExtensions(fieldExtensions);
    alfrescoScriptTask.setCustomProperties(customProperties);

    // Act and Assert
    List<CustomProperty> customProperties2 = alfrescoScriptTask.clone().getCustomProperties();
    assertEquals(1, customProperties2.size());
    CustomProperty getResult = customProperties2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertNull(getResult.getSimpleValue());
    assertNull(getResult.getComplexValue());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AlfrescoScriptTask#clone()}.
   *
   * <ul>
   *   <li>Then return FieldExtensions Empty.
   * </ul>
   *
   * <p>Method under test: {@link AlfrescoScriptTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AlfrescoScriptTask AlfrescoScriptTask.clone()"})
  public void testClone_thenReturnFieldExtensionsEmpty() {
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

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    AlfrescoScriptTask alfrescoScriptTask = new AlfrescoScriptTask();
    alfrescoScriptTask.setFieldExtensions(null);
    alfrescoScriptTask.setCustomProperties(customProperties);

    // Act
    AlfrescoScriptTask actualCloneResult = alfrescoScriptTask.clone();

    // Assert
    List<CustomProperty> customProperties2 = actualCloneResult.getCustomProperties();
    assertEquals(1, customProperties2.size());
    ComplexDataType complexValue2 = customProperties2.get(0).getComplexValue();
    assertTrue(complexValue2 instanceof DataGrid);
    List<DataGridRow> rows2 = ((DataGrid) complexValue2).getRows();
    assertEquals(1, rows2.size());
    DataGridRow getResult = rows2.get(0);
    List<DataGridField> fields2 = getResult.getFields();
    assertEquals(1, fields2.size());
    DataGridField getResult2 = fields2.get(0);
    assertNull(getResult2.getId());
    assertNull(getResult2.getName());
    assertNull(getResult2.getValue());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult.getIndex());
    assertTrue(actualCloneResult.getFieldExtensions().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AlfrescoScriptTask#clone()}.
   *
   * <ul>
   *   <li>Then return FieldExtensions size is one.
   * </ul>
   *
   * <p>Method under test: {@link AlfrescoScriptTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AlfrescoScriptTask AlfrescoScriptTask.clone()"})
  public void testClone_thenReturnFieldExtensionsSizeIsOne() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    AlfrescoScriptTask alfrescoScriptTask = new AlfrescoScriptTask();
    alfrescoScriptTask.setFieldExtensions(fieldExtensions);
    alfrescoScriptTask.setCustomProperties(null);

    // Act
    AlfrescoScriptTask actualCloneResult = alfrescoScriptTask.clone();

    // Assert
    List<FieldExtension> fieldExtensions2 = actualCloneResult.getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    FieldExtension getResult = fieldExtensions2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertNull(getResult.getFieldName());
    assertNull(getResult.getStringValue());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getCustomProperties().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AlfrescoScriptTask#setValues(AlfrescoScriptTask)} with {@code AlfrescoScriptTask}.
   *
   * <ul>
   *   <li>Then calls {@link FieldExtension#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link AlfrescoScriptTask#setValues(AlfrescoScriptTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AlfrescoScriptTask.setValues(AlfrescoScriptTask)"})
  public void testSetValuesWithAlfrescoScriptTask_thenCallsClone() {
    // Arrange
    AlfrescoScriptTask alfrescoScriptTask = new AlfrescoScriptTask();

    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.clone()).thenReturn(new FieldExtension());

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

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

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    AlfrescoScriptTask otherElement = new AlfrescoScriptTask();
    otherElement.setFieldExtensions(fieldExtensions);
    otherElement.setCustomProperties(customProperties);

    // Act
    alfrescoScriptTask.setValues(otherElement);

    // Assert
    verify(fieldExtension).clone();
  }

  /**
   * Test {@link AlfrescoScriptTask#setValues(AlfrescoScriptTask)} with {@code AlfrescoScriptTask}.
   *
   * <ul>
   *   <li>Then calls {@link CustomProperty#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link AlfrescoScriptTask#setValues(AlfrescoScriptTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AlfrescoScriptTask.setValues(AlfrescoScriptTask)"})
  public void testSetValuesWithAlfrescoScriptTask_thenCallsClone2() {
    // Arrange
    AlfrescoScriptTask alfrescoScriptTask = new AlfrescoScriptTask();

    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.clone()).thenReturn(new FieldExtension());

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ArrayList<DataGridField> fields = new ArrayList<>();
    fields.add(new DataGridField());

    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(fields);

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(rows);

    CustomProperty customProperty = mock(CustomProperty.class);
    when(customProperty.clone()).thenReturn(new CustomProperty());
    doNothing().when(customProperty).setComplexValue(Mockito.<ComplexDataType>any());
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    AlfrescoScriptTask otherElement = new AlfrescoScriptTask();
    otherElement.setFieldExtensions(fieldExtensions);
    otherElement.setCustomProperties(customProperties);

    // Act
    alfrescoScriptTask.setValues(otherElement);

    // Assert
    verify(customProperty).clone();
    verify(customProperty).setComplexValue(isA(ComplexDataType.class));
    verify(fieldExtension).clone();
  }

  /**
   * Test new {@link AlfrescoScriptTask} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link AlfrescoScriptTask}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AlfrescoScriptTask.<init>()"})
  public void testNewAlfrescoScriptTask() {
    // Arrange and Act
    AlfrescoScriptTask actualAlfrescoScriptTask = new AlfrescoScriptTask();

    // Assert
    assertNull(actualAlfrescoScriptTask.getBehavior());
    assertNull(actualAlfrescoScriptTask.getDefaultFlow());
    assertNull(actualAlfrescoScriptTask.getFailedJobRetryTimeCycleValue());
    assertNull(actualAlfrescoScriptTask.getId());
    assertNull(actualAlfrescoScriptTask.getDocumentation());
    assertNull(actualAlfrescoScriptTask.getName());
    assertNull(actualAlfrescoScriptTask.getExtensionId());
    assertNull(actualAlfrescoScriptTask.getImplementation());
    assertNull(actualAlfrescoScriptTask.getImplementationType());
    assertNull(actualAlfrescoScriptTask.getOperationRef());
    assertNull(actualAlfrescoScriptTask.getResultVariableName());
    assertNull(actualAlfrescoScriptTask.getSkipExpression());
    assertNull(actualAlfrescoScriptTask.getType());
    assertNull(actualAlfrescoScriptTask.getParentContainer());
    assertNull(actualAlfrescoScriptTask.getIoSpecification());
    assertNull(actualAlfrescoScriptTask.getLoopCharacteristics());
    assertEquals(0, actualAlfrescoScriptTask.getXmlColumnNumber());
    assertEquals(0, actualAlfrescoScriptTask.getXmlRowNumber());
    assertFalse(actualAlfrescoScriptTask.isForCompensation());
    assertFalse(actualAlfrescoScriptTask.isAsynchronous());
    assertFalse(actualAlfrescoScriptTask.isNotExclusive());
    assertTrue(actualAlfrescoScriptTask.getBoundaryEvents().isEmpty());
    assertTrue(actualAlfrescoScriptTask.getDataInputAssociations().isEmpty());
    assertTrue(actualAlfrescoScriptTask.getDataOutputAssociations().isEmpty());
    assertTrue(actualAlfrescoScriptTask.getMapExceptions().isEmpty());
    assertTrue(actualAlfrescoScriptTask.getExecutionListeners().isEmpty());
    assertTrue(actualAlfrescoScriptTask.getIncomingFlows().isEmpty());
    assertTrue(actualAlfrescoScriptTask.getOutgoingFlows().isEmpty());
    assertTrue(actualAlfrescoScriptTask.getCustomProperties().isEmpty());
    assertTrue(actualAlfrescoScriptTask.getFieldExtensions().isEmpty());
    assertTrue(actualAlfrescoScriptTask.getAttributes().isEmpty());
    assertTrue(actualAlfrescoScriptTask.getExtensionElements().isEmpty());
  }
}
