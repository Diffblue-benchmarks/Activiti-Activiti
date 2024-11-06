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
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.ComplexDataType;
import org.activiti.bpmn.model.CustomProperty;
import org.activiti.bpmn.model.DataGrid;
import org.activiti.bpmn.model.DataGridRow;
import org.activiti.bpmn.model.FieldExtension;
import org.junit.Test;
import org.mockito.Mockito;

public class AlfrescoMailTaskDiffblueTest {
  /**
   * Test {@link AlfrescoMailTask#clone()}.
   * <ul>
   *   <li>Given {@link AlfrescoMailTask} (default constructor) CustomProperties is
   * {@code null}.</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoMailTask#clone()}
   */
  @Test
  public void testClone_givenAlfrescoMailTaskCustomPropertiesIsNull_thenReturnBehaviorIsNull() {
    // Arrange
    AlfrescoMailTask alfrescoMailTask = new AlfrescoMailTask();
    alfrescoMailTask.setCustomProperties(null);
    alfrescoMailTask.setFieldExtensions(null);

    // Act
    AlfrescoMailTask actualCloneResult = alfrescoMailTask.clone();

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
    assertTrue(actualCloneResult.getCustomProperties().isEmpty());
    assertTrue(actualCloneResult.getFieldExtensions().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link AlfrescoMailTask#clone()}.
   * <ul>
   *   <li>Given {@link AlfrescoMailTask} (default constructor).</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoMailTask#clone()}
   */
  @Test
  public void testClone_givenAlfrescoMailTask_thenReturnBehaviorIsNull() {
    // Arrange and Act
    AlfrescoMailTask actualCloneResult = (new AlfrescoMailTask()).clone();

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
    assertTrue(actualCloneResult.getCustomProperties().isEmpty());
    assertTrue(actualCloneResult.getFieldExtensions().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link AlfrescoMailTask#clone()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   *   <li>Then return FieldExtensions size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoMailTask#clone()}
   */
  @Test
  public void testClone_givenArrayListAddFieldExtension_thenReturnFieldExtensionsSizeIsOne() {
    // Arrange
    DataGrid complexValue = new DataGrid();
    complexValue.setRows(null);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    AlfrescoMailTask alfrescoMailTask = new AlfrescoMailTask();
    alfrescoMailTask.setCustomProperties(customProperties);
    alfrescoMailTask.setFieldExtensions(fieldExtensions);

    // Act and Assert
    List<FieldExtension> fieldExtensions2 = alfrescoMailTask.clone().getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    FieldExtension getResult = fieldExtensions2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertNull(getResult.getFieldName());
    assertNull(getResult.getStringValue());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AlfrescoMailTask#clone()}.
   * <ul>
   *   <li>Then CustomProperties first ComplexValue return {@link DataGrid}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoMailTask#clone()}
   */
  @Test
  public void testClone_thenCustomPropertiesFirstComplexValueReturnDataGrid() {
    // Arrange
    DataGrid complexValue = new DataGrid();
    complexValue.setRows(null);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    AlfrescoMailTask alfrescoMailTask = new AlfrescoMailTask();
    alfrescoMailTask.setCustomProperties(customProperties);
    alfrescoMailTask.setFieldExtensions(null);

    // Act and Assert
    List<CustomProperty> customProperties2 = alfrescoMailTask.clone().getCustomProperties();
    assertEquals(1, customProperties2.size());
    CustomProperty getResult = customProperties2.get(0);
    ComplexDataType complexValue2 = getResult.getComplexValue();
    assertTrue(complexValue2 instanceof DataGrid);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertNull(getResult.getSimpleValue());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(((DataGrid) complexValue2).getRows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AlfrescoMailTask#clone()}.
   * <ul>
   *   <li>Then return CustomProperties first ComplexValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoMailTask#clone()}
   */
  @Test
  public void testClone_thenReturnCustomPropertiesFirstComplexValueIsNull() {
    // Arrange
    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    AlfrescoMailTask alfrescoMailTask = new AlfrescoMailTask();
    alfrescoMailTask.setCustomProperties(customProperties);
    alfrescoMailTask.setFieldExtensions(null);

    // Act and Assert
    List<CustomProperty> customProperties2 = alfrescoMailTask.clone().getCustomProperties();
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
   * Test {@link AlfrescoMailTask#setValues(AlfrescoMailTask)} with
   * {@code AlfrescoMailTask}.
   * <ul>
   *   <li>Then calls {@link CustomProperty#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoMailTask#setValues(AlfrescoMailTask)}
   */
  @Test
  public void testSetValuesWithAlfrescoMailTask_thenCallsClone() {
    // Arrange
    AlfrescoMailTask alfrescoMailTask = new AlfrescoMailTask();

    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(null);

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

    AlfrescoMailTask otherElement = new AlfrescoMailTask();
    otherElement.setFieldExtensions(null);
    otherElement.setCustomProperties(customProperties);

    // Act
    alfrescoMailTask.setValues(otherElement);

    // Assert
    verify(customProperty).clone();
    verify(customProperty).setComplexValue(isA(ComplexDataType.class));
  }

  /**
   * Test {@link AlfrescoMailTask#setValues(AlfrescoMailTask)} with
   * {@code AlfrescoMailTask}.
   * <ul>
   *   <li>Then calls {@link FieldExtension#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoMailTask#setValues(AlfrescoMailTask)}
   */
  @Test
  public void testSetValuesWithAlfrescoMailTask_thenCallsClone2() {
    // Arrange
    AlfrescoMailTask alfrescoMailTask = new AlfrescoMailTask();
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.clone()).thenReturn(new FieldExtension());

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    AlfrescoMailTask otherElement = new AlfrescoMailTask();
    otherElement.setFieldExtensions(fieldExtensions);
    otherElement.setCustomProperties(null);

    // Act
    alfrescoMailTask.setValues(otherElement);

    // Assert
    verify(fieldExtension).clone();
  }

  /**
   * Test new {@link AlfrescoMailTask} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link AlfrescoMailTask}
   */
  @Test
  public void testNewAlfrescoMailTask() {
    // Arrange and Act
    AlfrescoMailTask actualAlfrescoMailTask = new AlfrescoMailTask();

    // Assert
    assertNull(actualAlfrescoMailTask.getBehavior());
    assertNull(actualAlfrescoMailTask.getDefaultFlow());
    assertNull(actualAlfrescoMailTask.getFailedJobRetryTimeCycleValue());
    assertNull(actualAlfrescoMailTask.getId());
    assertNull(actualAlfrescoMailTask.getDocumentation());
    assertNull(actualAlfrescoMailTask.getName());
    assertNull(actualAlfrescoMailTask.getExtensionId());
    assertNull(actualAlfrescoMailTask.getImplementation());
    assertNull(actualAlfrescoMailTask.getImplementationType());
    assertNull(actualAlfrescoMailTask.getOperationRef());
    assertNull(actualAlfrescoMailTask.getResultVariableName());
    assertNull(actualAlfrescoMailTask.getSkipExpression());
    assertNull(actualAlfrescoMailTask.getType());
    assertNull(actualAlfrescoMailTask.getParentContainer());
    assertNull(actualAlfrescoMailTask.getIoSpecification());
    assertNull(actualAlfrescoMailTask.getLoopCharacteristics());
    assertEquals(0, actualAlfrescoMailTask.getXmlColumnNumber());
    assertEquals(0, actualAlfrescoMailTask.getXmlRowNumber());
    assertFalse(actualAlfrescoMailTask.isForCompensation());
    assertFalse(actualAlfrescoMailTask.isAsynchronous());
    assertFalse(actualAlfrescoMailTask.isNotExclusive());
    assertTrue(actualAlfrescoMailTask.getBoundaryEvents().isEmpty());
    assertTrue(actualAlfrescoMailTask.getDataInputAssociations().isEmpty());
    assertTrue(actualAlfrescoMailTask.getDataOutputAssociations().isEmpty());
    assertTrue(actualAlfrescoMailTask.getMapExceptions().isEmpty());
    assertTrue(actualAlfrescoMailTask.getExecutionListeners().isEmpty());
    assertTrue(actualAlfrescoMailTask.getIncomingFlows().isEmpty());
    assertTrue(actualAlfrescoMailTask.getOutgoingFlows().isEmpty());
    assertTrue(actualAlfrescoMailTask.getCustomProperties().isEmpty());
    assertTrue(actualAlfrescoMailTask.getFieldExtensions().isEmpty());
    assertTrue(actualAlfrescoMailTask.getAttributes().isEmpty());
    assertTrue(actualAlfrescoMailTask.getExtensionElements().isEmpty());
  }
}
