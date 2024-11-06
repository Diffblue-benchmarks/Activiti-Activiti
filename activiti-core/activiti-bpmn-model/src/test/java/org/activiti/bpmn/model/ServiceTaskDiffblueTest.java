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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.alfresco.AlfrescoMailTask;
import org.junit.Test;
import org.mockito.Mockito;

public class ServiceTaskDiffblueTest {
  /**
   * Test {@link ServiceTask#isExtended()}.
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor) ExtensionId is empty
   * string.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#isExtended()}
   */
  @Test
  public void testIsExtended_givenServiceTaskExtensionIdIsEmptyString_thenReturnFalse() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setExtensionId("");

    // Act and Assert
    assertFalse(serviceTask.isExtended());
  }

  /**
   * Test {@link ServiceTask#isExtended()}.
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor) ExtensionId is
   * {@code foo}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#isExtended()}
   */
  @Test
  public void testIsExtended_givenServiceTaskExtensionIdIsFoo_thenReturnTrue() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setExtensionId("foo");

    // Act and Assert
    assertTrue(serviceTask.isExtended());
  }

  /**
   * Test {@link ServiceTask#isExtended()}.
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#isExtended()}
   */
  @Test
  public void testIsExtended_givenServiceTask_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ServiceTask()).isExtended());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   * <p>
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(null);

    BoundaryEvent boundaryEvent2 = new BoundaryEvent();
    boundaryEvent2.addEventDefinition(new CancelEventDefinition());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(boundaryEvent2);
    boundaryEvents.add(boundaryEvent);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertFalse(serviceTask.hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   * <p>
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents2() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(null);

    BoundaryEvent boundaryEvent2 = new BoundaryEvent();
    boundaryEvent2.addEventDefinition(new CancelEventDefinition());
    boundaryEvent2.addEventDefinition(new CancelEventDefinition());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(boundaryEvent2);
    boundaryEvents.add(boundaryEvent);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertFalse(serviceTask.hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor) EventDefinitions is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents_givenBoundaryEventEventDefinitionsIsArrayList() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(new ArrayList<>());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(boundaryEvent);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertFalse(serviceTask.hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor) EventDefinitions is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents_givenBoundaryEventEventDefinitionsIsNull() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(null);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(boundaryEvent);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertFalse(serviceTask.hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor) EventDefinitions is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents_givenBoundaryEventEventDefinitionsIsNull2() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(null);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());
    boundaryEvents.add(boundaryEvent);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertFalse(serviceTask.hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor) BoundaryEvents is
   * {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents_givenServiceTaskBoundaryEventsIsNull_thenReturnFalse() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setBoundaryEvents(null);

    // Act and Assert
    assertFalse(serviceTask.hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents_givenServiceTask_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ServiceTask()).hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents_thenReturnTrue() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(null);

    BoundaryEvent boundaryEvent2 = new BoundaryEvent();
    boundaryEvent2.addEventDefinition(new ErrorEventDefinition());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(boundaryEvent2);
    boundaryEvents.add(boundaryEvent);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertTrue(serviceTask.hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#clone()}.
   * <ul>
   *   <li>Given {@link AlfrescoMailTask} (default constructor).</li>
   *   <li>Then return {@link AlfrescoMailTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone_givenAlfrescoMailTask_thenReturnAlfrescoMailTask() {
    // Arrange and Act
    AlfrescoMailTask actualCloneResult = (new AlfrescoMailTask()).clone();

    // Assert
    assertTrue(actualCloneResult instanceof AlfrescoMailTask);
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
   * Test {@link ServiceTask#clone()}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   *   <li>Then return FieldExtensions size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone_givenArrayListAddFieldExtension_thenReturnFieldExtensionsSizeIsOne() {
    // Arrange
    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(null);

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(rows);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setCustomProperties(customProperties);
    serviceTask.setFieldExtensions(fieldExtensions);

    // Act and Assert
    List<FieldExtension> fieldExtensions2 = serviceTask.clone().getFieldExtensions();
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
   * Test {@link ServiceTask#clone()}.
   * <ul>
   *   <li>Given {@link DataGrid} (default constructor) Rows is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone_givenDataGridRowsIsNull() {
    // Arrange
    DataGrid complexValue = new DataGrid();
    complexValue.setRows(null);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setCustomProperties(customProperties);
    serviceTask.setFieldExtensions(null);

    // Act and Assert
    List<CustomProperty> customProperties2 = serviceTask.clone().getCustomProperties();
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
   * Test {@link ServiceTask#clone()}.
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor) CustomProperties is
   * {@code null}.</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone_givenServiceTaskCustomPropertiesIsNull_thenReturnBehaviorIsNull() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setCustomProperties(null);
    serviceTask.setFieldExtensions(null);

    // Act
    ServiceTask actualCloneResult = serviceTask.clone();

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
   * Test {@link ServiceTask#clone()}.
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor).</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone_givenServiceTask_thenReturnBehaviorIsNull() {
    // Arrange and Act
    ServiceTask actualCloneResult = (new ServiceTask()).clone();

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
   * Test {@link ServiceTask#clone()}.
   * <ul>
   *   <li>Then return CustomProperties first ComplexValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone_thenReturnCustomPropertiesFirstComplexValueIsNull() {
    // Arrange
    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setCustomProperties(customProperties);
    serviceTask.setFieldExtensions(null);

    // Act and Assert
    List<CustomProperty> customProperties2 = serviceTask.clone().getCustomProperties();
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
   * Test {@link ServiceTask#clone()}.
   * <ul>
   *   <li>Then return CustomProperties first ComplexValue Rows Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone_thenReturnCustomPropertiesFirstComplexValueRowsEmpty() {
    // Arrange
    DataGrid complexValue = new DataGrid();
    complexValue.setRows(new ArrayList<>());

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setCustomProperties(customProperties);
    serviceTask.setFieldExtensions(null);

    // Act and Assert
    List<CustomProperty> customProperties2 = serviceTask.clone().getCustomProperties();
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
   * Test {@link ServiceTask#clone()}.
   * <ul>
   *   <li>Then return CustomProperties first ComplexValue Rows size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone_thenReturnCustomPropertiesFirstComplexValueRowsSizeIsOne() {
    // Arrange
    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(null);

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(rows);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setCustomProperties(customProperties);
    serviceTask.setFieldExtensions(null);

    // Act and Assert
    List<CustomProperty> customProperties2 = serviceTask.clone().getCustomProperties();
    assertEquals(1, customProperties2.size());
    ComplexDataType complexValue2 = customProperties2.get(0).getComplexValue();
    assertTrue(complexValue2 instanceof DataGrid);
    List<DataGridRow> rows2 = ((DataGrid) complexValue2).getRows();
    assertEquals(1, rows2.size());
    DataGridRow getResult = rows2.get(0);
    assertEquals(0, getResult.getIndex());
    assertTrue(getResult.getFields().isEmpty());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValuesWithServiceTask_givenArrayListAddFieldExtension() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(fieldExtensions);
    otherElement.setCustomProperties(null);

    // Act
    serviceTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getImplementation());
    assertNull(otherElement.getImplementationType());
    assertNull(otherElement.getOperationRef());
    assertNull(otherElement.getResultVariableName());
    assertNull(otherElement.getSkipExpression());
    assertNull(otherElement.getType());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   * <ul>
   *   <li>Given {@link CustomProperty} (default constructor) ComplexValue is
   * {@link DataGrid} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValuesWithServiceTask_givenCustomPropertyComplexValueIsDataGrid() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(new DataGrid());

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(null);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getImplementation());
    assertNull(otherElement.getImplementationType());
    assertNull(otherElement.getOperationRef());
    assertNull(otherElement.getResultVariableName());
    assertNull(otherElement.getSkipExpression());
    assertNull(otherElement.getType());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   * <ul>
   *   <li>Given {@link CustomProperty} (default constructor) ComplexValue is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValuesWithServiceTask_givenCustomPropertyComplexValueIsNull() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(null);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getImplementation());
    assertNull(otherElement.getImplementationType());
    assertNull(otherElement.getOperationRef());
    assertNull(otherElement.getResultVariableName());
    assertNull(otherElement.getSkipExpression());
    assertNull(otherElement.getType());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   * <ul>
   *   <li>Given {@link DataGrid} {@link DataGrid#clone()} return {@link DataGrid}
   * (default constructor).</li>
   *   <li>Then calls {@link DataGrid#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValuesWithServiceTask_givenDataGridCloneReturnDataGrid_thenCallsClone() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    DataGrid complexValue = mock(DataGrid.class);
    when(complexValue.clone()).thenReturn(new DataGrid());

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(null);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert
    verify(complexValue).clone();
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getImplementation());
    assertNull(otherElement.getImplementationType());
    assertNull(otherElement.getOperationRef());
    assertNull(otherElement.getResultVariableName());
    assertNull(otherElement.getSkipExpression());
    assertNull(otherElement.getType());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   * <ul>
   *   <li>Given {@link DataGridRow} (default constructor) Fields is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValuesWithServiceTask_givenDataGridRowFieldsIsNull() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

    DataGridRow dataGridRow = new DataGridRow();
    dataGridRow.setFields(null);

    ArrayList<DataGridRow> rows = new ArrayList<>();
    rows.add(dataGridRow);

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(rows);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(null);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getImplementation());
    assertNull(otherElement.getImplementationType());
    assertNull(otherElement.getOperationRef());
    assertNull(otherElement.getResultVariableName());
    assertNull(otherElement.getSkipExpression());
    assertNull(otherElement.getType());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   * <ul>
   *   <li>Given {@link DataGrid} (default constructor) Rows is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValuesWithServiceTask_givenDataGridRowsIsNull() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(null);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(null);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getImplementation());
    assertNull(otherElement.getImplementationType());
    assertNull(otherElement.getOperationRef());
    assertNull(otherElement.getResultVariableName());
    assertNull(otherElement.getSkipExpression());
    assertNull(otherElement.getType());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then {@link ServiceTask} (default constructor) CustomProperties size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValuesWithServiceTask_givenTrue_thenServiceTaskCustomPropertiesSizeIsOne() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

    ArrayList<CustomProperty> customPropertyList = new ArrayList<>();
    customPropertyList.add(new CustomProperty());
    AlfrescoMailTask otherElement = mock(AlfrescoMailTask.class);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getExtensionId()).thenReturn("42");
    when(otherElement.getImplementation()).thenReturn("Implementation");
    when(otherElement.getImplementationType()).thenReturn("Implementation Type");
    when(otherElement.getOperationRef()).thenReturn("Operation Ref");
    when(otherElement.getResultVariableName()).thenReturn("Result Variable Name");
    when(otherElement.getSkipExpression()).thenReturn("Skip Expression");
    when(otherElement.getType()).thenReturn("Type");
    when(otherElement.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherElement.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCustomProperties()).thenReturn(customPropertyList);
    when(otherElement.getFieldExtensions()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    serviceTask.setValues(otherElement);

    // Assert
    verify(otherElement).getBoundaryEvents();
    verify(otherElement, atLeast(1)).getDataInputAssociations();
    verify(otherElement, atLeast(1)).getDataOutputAssociations();
    verify(otherElement).getDefaultFlow();
    verify(otherElement).getFailedJobRetryTimeCycleValue();
    verify(otherElement, atLeast(1)).getIoSpecification();
    verify(otherElement, atLeast(1)).getLoopCharacteristics();
    verify(otherElement).isForCompensation();
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(otherElement, atLeast(1)).getCustomProperties();
    verify(otherElement).getExtensionId();
    verify(otherElement).getImplementation();
    verify(otherElement).getImplementationType();
    verify(otherElement).getOperationRef();
    verify(otherElement).getResultVariableName();
    verify(otherElement).getSkipExpression();
    verify(otherElement).getType();
    verify(otherElement, atLeast(1)).getFieldExtensions();
    List<CustomProperty> customProperties = serviceTask.getCustomProperties();
    assertEquals(1, customProperties.size());
    CustomProperty getResult = customProperties.get(0);
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
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   * <ul>
   *   <li>Then calls {@link CustomProperty#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValuesWithServiceTask_thenCallsClone() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    CustomProperty customProperty = mock(CustomProperty.class);
    when(customProperty.clone()).thenReturn(new CustomProperty());
    doNothing().when(customProperty).setComplexValue(Mockito.<ComplexDataType>any());
    customProperty.setComplexValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(null);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert
    verify(customProperty).clone();
    verify(customProperty).setComplexValue(isNull());
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getImplementation());
    assertNull(otherElement.getImplementationType());
    assertNull(otherElement.getOperationRef());
    assertNull(otherElement.getResultVariableName());
    assertNull(otherElement.getSkipExpression());
    assertNull(otherElement.getType());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   * <ul>
   *   <li>Then calls {@link FieldExtension#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValuesWithServiceTask_thenCallsClone2() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    FieldExtension fieldExtension = mock(FieldExtension.class);
    when(fieldExtension.clone()).thenReturn(new FieldExtension());

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(fieldExtensions);
    otherElement.setCustomProperties(null);

    // Act
    serviceTask.setValues(otherElement);

    // Assert
    verify(fieldExtension).clone();
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getImplementation());
    assertNull(otherElement.getImplementationType());
    assertNull(otherElement.getOperationRef());
    assertNull(otherElement.getResultVariableName());
    assertNull(otherElement.getSkipExpression());
    assertNull(otherElement.getType());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   * <ul>
   *   <li>Then {@link ServiceTask} (default constructor)
   * FailedJobRetryTimeCycleValue is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValuesWithServiceTask_thenServiceTaskFailedJobRetryTimeCycleValueIs42() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    AlfrescoMailTask otherElement = mock(AlfrescoMailTask.class);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getExtensionId()).thenReturn("42");
    when(otherElement.getImplementation()).thenReturn("Implementation");
    when(otherElement.getImplementationType()).thenReturn("Implementation Type");
    when(otherElement.getOperationRef()).thenReturn("Operation Ref");
    when(otherElement.getResultVariableName()).thenReturn("Result Variable Name");
    when(otherElement.getSkipExpression()).thenReturn("Skip Expression");
    when(otherElement.getType()).thenReturn("Type");
    when(otherElement.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherElement.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCustomProperties()).thenReturn(new ArrayList<>());
    when(otherElement.getFieldExtensions()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    serviceTask.setValues(otherElement);

    // Assert
    verify(otherElement).getBoundaryEvents();
    verify(otherElement, atLeast(1)).getDataInputAssociations();
    verify(otherElement, atLeast(1)).getDataOutputAssociations();
    verify(otherElement).getDefaultFlow();
    verify(otherElement).getFailedJobRetryTimeCycleValue();
    verify(otherElement, atLeast(1)).getIoSpecification();
    verify(otherElement, atLeast(1)).getLoopCharacteristics();
    verify(otherElement).isForCompensation();
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(otherElement, atLeast(1)).getCustomProperties();
    verify(otherElement).getExtensionId();
    verify(otherElement).getImplementation();
    verify(otherElement).getImplementationType();
    verify(otherElement).getOperationRef();
    verify(otherElement).getResultVariableName();
    verify(otherElement).getSkipExpression();
    verify(otherElement).getType();
    verify(otherElement, atLeast(1)).getFieldExtensions();
    assertEquals("42", serviceTask.getFailedJobRetryTimeCycleValue());
    assertEquals("42", serviceTask.getId());
    assertEquals("42", serviceTask.getExtensionId());
    assertEquals("Default Flow", serviceTask.getDefaultFlow());
    assertEquals("Documentation", serviceTask.getDocumentation());
    assertEquals("Implementation Type", serviceTask.getImplementationType());
    assertEquals("Implementation", serviceTask.getImplementation());
    assertEquals("Name", serviceTask.getName());
    assertEquals("Operation Ref", serviceTask.getOperationRef());
    assertEquals("Result Variable Name", serviceTask.getResultVariableName());
    assertEquals("Skip Expression", serviceTask.getSkipExpression());
    assertEquals("Type", serviceTask.getType());
    IOSpecification ioSpecification = serviceTask.getIoSpecification();
    assertNull(ioSpecification.getId());
    MultiInstanceLoopCharacteristics loopCharacteristics = serviceTask.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(serviceTask.isExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(serviceTask.hasMultiInstanceLoopCharacteristics());
    assertTrue(serviceTask.isForCompensation());
    assertTrue(serviceTask.isAsynchronous());
    assertTrue(serviceTask.isNotExclusive());
    assertTrue(serviceTask.isExtended());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor) CustomProperties is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValuesWithServiceTask_whenServiceTaskCustomPropertiesIsNull() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(null);
    otherElement.setCustomProperties(null);

    // Act
    serviceTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getImplementation());
    assertNull(otherElement.getImplementationType());
    assertNull(otherElement.getOperationRef());
    assertNull(otherElement.getResultVariableName());
    assertNull(otherElement.getSkipExpression());
    assertNull(otherElement.getType());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   *   <li>Then {@link ServiceTask} (default constructor) DefaultFlow is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValuesWithServiceTask_whenServiceTask_thenServiceTaskDefaultFlowIsNull() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    ServiceTask otherElement = new ServiceTask();

    // Act
    serviceTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getImplementation());
    assertNull(otherElement.getImplementationType());
    assertNull(otherElement.getOperationRef());
    assertNull(otherElement.getResultVariableName());
    assertNull(otherElement.getSkipExpression());
    assertNull(otherElement.getType());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ServiceTask}
   *   <li>{@link ServiceTask#setCustomProperties(List)}
   *   <li>{@link ServiceTask#setExtensionId(String)}
   *   <li>{@link ServiceTask#setImplementation(String)}
   *   <li>{@link ServiceTask#setImplementationType(String)}
   *   <li>{@link ServiceTask#setOperationRef(String)}
   *   <li>{@link ServiceTask#setResultVariableName(String)}
   *   <li>{@link ServiceTask#setSkipExpression(String)}
   *   <li>{@link ServiceTask#setType(String)}
   *   <li>{@link ServiceTask#getCustomProperties()}
   *   <li>{@link ServiceTask#getExtensionId()}
   *   <li>{@link ServiceTask#getImplementation()}
   *   <li>{@link ServiceTask#getImplementationType()}
   *   <li>{@link ServiceTask#getOperationRef()}
   *   <li>{@link ServiceTask#getResultVariableName()}
   *   <li>{@link ServiceTask#getSkipExpression()}
   *   <li>{@link ServiceTask#getType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ServiceTask actualServiceTask = new ServiceTask();
    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    actualServiceTask.setCustomProperties(customProperties);
    actualServiceTask.setExtensionId("42");
    actualServiceTask.setImplementation("Implementation");
    actualServiceTask.setImplementationType("Implementation Type");
    actualServiceTask.setOperationRef("Operation Ref");
    actualServiceTask.setResultVariableName("Result Variable Name");
    actualServiceTask.setSkipExpression("Skip Expression");
    actualServiceTask.setType("Type");
    List<CustomProperty> actualCustomProperties = actualServiceTask.getCustomProperties();
    String actualExtensionId = actualServiceTask.getExtensionId();
    String actualImplementation = actualServiceTask.getImplementation();
    String actualImplementationType = actualServiceTask.getImplementationType();
    String actualOperationRef = actualServiceTask.getOperationRef();
    String actualResultVariableName = actualServiceTask.getResultVariableName();
    String actualSkipExpression = actualServiceTask.getSkipExpression();

    // Assert that nothing has changed
    assertEquals("42", actualExtensionId);
    assertEquals("Implementation Type", actualImplementationType);
    assertEquals("Implementation", actualImplementation);
    assertEquals("Operation Ref", actualOperationRef);
    assertEquals("Result Variable Name", actualResultVariableName);
    assertEquals("Skip Expression", actualSkipExpression);
    assertEquals("Type", actualServiceTask.getType());
    assertEquals(0, actualServiceTask.getXmlColumnNumber());
    assertEquals(0, actualServiceTask.getXmlRowNumber());
    assertFalse(actualServiceTask.isForCompensation());
    assertFalse(actualServiceTask.isAsynchronous());
    assertFalse(actualServiceTask.isNotExclusive());
    assertTrue(actualServiceTask.getBoundaryEvents().isEmpty());
    assertTrue(actualServiceTask.getDataInputAssociations().isEmpty());
    assertTrue(actualServiceTask.getDataOutputAssociations().isEmpty());
    assertTrue(actualServiceTask.getMapExceptions().isEmpty());
    assertTrue(actualServiceTask.getExecutionListeners().isEmpty());
    assertTrue(actualServiceTask.getIncomingFlows().isEmpty());
    assertTrue(actualServiceTask.getOutgoingFlows().isEmpty());
    assertTrue(actualCustomProperties.isEmpty());
    assertTrue(actualServiceTask.getFieldExtensions().isEmpty());
    assertTrue(actualServiceTask.getAttributes().isEmpty());
    assertTrue(actualServiceTask.getExtensionElements().isEmpty());
    assertSame(customProperties, actualCustomProperties);
  }
}
