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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.alfresco.AlfrescoMailTask;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ServiceTaskDiffblueTest {
  /**
   * Test {@link ServiceTask#isExtended()}.
   *
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor) ExtensionId is {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#isExtended()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServiceTask.isExtended()"})
  public void testIsExtended_givenServiceTaskExtensionIdIs42_thenReturnTrue() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setExtensionId("42");

    // Act and Assert
    assertTrue(serviceTask.isExtended());
  }

  /**
   * Test {@link ServiceTask#isExtended()}.
   *
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor) ExtensionId is empty string.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#isExtended()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServiceTask.isExtended()"})
  public void testIsExtended_givenServiceTaskExtensionIdIsEmptyString_thenReturnFalse() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setExtensionId("");

    // Act and Assert
    assertFalse(serviceTask.isExtended());
  }

  /**
   * Test {@link ServiceTask#isExtended()}.
   *
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#isExtended()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServiceTask.isExtended()"})
  public void testIsExtended_givenServiceTask_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new ServiceTask().isExtended());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServiceTask.hasBoundaryErrorEvents()"})
  public void testHasBoundaryErrorEvents_givenArrayListAddCancelEventDefinition() {
    // Arrange
    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(boundaryEvent);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertFalse(serviceTask.hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServiceTask.hasBoundaryErrorEvents()"})
  public void testHasBoundaryErrorEvents_givenArrayListAddCancelEventDefinition2() {
    // Arrange
    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    eventDefinitions.add(new CancelEventDefinition());

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(boundaryEvent);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertFalse(serviceTask.hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ErrorEventDefinition} (default
   *       constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServiceTask.hasBoundaryErrorEvents()"})
  public void testHasBoundaryErrorEvents_givenArrayListAddErrorEventDefinition_thenReturnTrue() {
    // Arrange
    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new ErrorEventDefinition());

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(boundaryEvent);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertTrue(serviceTask.hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor) EventDefinitions is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServiceTask.hasBoundaryErrorEvents()"})
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
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor) EventDefinitions is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServiceTask.hasBoundaryErrorEvents()"})
  public void testHasBoundaryErrorEvents_givenBoundaryEventEventDefinitionsIsArrayList2() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(new ArrayList<>());

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
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor) EventDefinitions is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServiceTask.hasBoundaryErrorEvents()"})
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
   *
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor) BoundaryEvents is {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServiceTask.hasBoundaryErrorEvents()"})
  public void testHasBoundaryErrorEvents_givenServiceTaskBoundaryEventsIsNull_thenReturnFalse() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setBoundaryEvents(null);

    // Act and Assert
    assertFalse(serviceTask.hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#hasBoundaryErrorEvents()}.
   *
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ServiceTask.hasBoundaryErrorEvents()"})
  public void testHasBoundaryErrorEvents_givenServiceTask_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new ServiceTask().hasBoundaryErrorEvents());
  }

  /**
   * Test {@link ServiceTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link AlfrescoMailTask} (default constructor).
   *   <li>Then return {@link AlfrescoMailTask}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ServiceTask ServiceTask.clone()"})
  public void testClone_givenAlfrescoMailTask_thenReturnAlfrescoMailTask() {
    // Arrange and Act
    AlfrescoMailTask actualCloneResult = new AlfrescoMailTask().clone();

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
    assertTrue(actualCloneResult.getFieldExtensions().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link ServiceTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link DataGrid} (default constructor) Rows is {@link ArrayList#ArrayList()}.
   *   <li>Then return CustomProperties first Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ServiceTask ServiceTask.clone()"})
  public void testClone_givenDataGridRowsIsArrayList_thenReturnCustomPropertiesFirstIdIsNull() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(new ArrayList<>());

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(fieldExtensions);
    serviceTask.setCustomProperties(customProperties);

    // Act and Assert
    List<CustomProperty> customProperties2 = serviceTask.clone().getCustomProperties();
    assertEquals(1, customProperties2.size());
    CustomProperty getResult = customProperties2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertNull(getResult.getSimpleValue());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link ServiceTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link DataGrid} (default constructor) Rows is {@code null}.
   *   <li>Then return CustomProperties first Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ServiceTask ServiceTask.clone()"})
  public void testClone_givenDataGridRowsIsNull_thenReturnCustomPropertiesFirstIdIsNull() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(null);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(fieldExtensions);
    serviceTask.setCustomProperties(customProperties);

    // Act and Assert
    List<CustomProperty> customProperties2 = serviceTask.clone().getCustomProperties();
    assertEquals(1, customProperties2.size());
    CustomProperty getResult = customProperties2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertNull(getResult.getSimpleValue());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link ServiceTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor) FieldExtensions is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ServiceTask ServiceTask.clone()"})
  public void testClone_givenServiceTaskFieldExtensionsIsNull() {
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

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(null);
    serviceTask.setCustomProperties(customProperties);

    // Act and Assert
    List<CustomProperty> customProperties2 = serviceTask.clone().getCustomProperties();
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
   * Test {@link ServiceTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link ServiceTask} (default constructor).
   *   <li>Then return Behavior is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ServiceTask ServiceTask.clone()"})
  public void testClone_givenServiceTask_thenReturnBehaviorIsNull() {
    // Arrange and Act
    ServiceTask actualCloneResult = new ServiceTask().clone();

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
    assertTrue(actualCloneResult.getFieldExtensions().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link ServiceTask#clone()}.
   *
   * <ul>
   *   <li>Then CustomProperties first ComplexValue return {@link DataGrid}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ServiceTask ServiceTask.clone()"})
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

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(fieldExtensions);
    serviceTask.setCustomProperties(customProperties);

    // Act and Assert
    List<CustomProperty> customProperties2 = serviceTask.clone().getCustomProperties();
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
   * Test {@link ServiceTask#clone()}.
   *
   * <ul>
   *   <li>Then return CustomProperties first ComplexValue is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ServiceTask ServiceTask.clone()"})
  public void testClone_thenReturnCustomPropertiesFirstComplexValueIsNull() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(fieldExtensions);
    serviceTask.setCustomProperties(customProperties);

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
   *
   * <ul>
   *   <li>Then return FieldExtensions size is one.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ServiceTask ServiceTask.clone()"})
  public void testClone_thenReturnFieldExtensionsSizeIsOne() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(fieldExtensions);
    serviceTask.setCustomProperties(null);

    // Act
    ServiceTask actualCloneResult = serviceTask.clone();

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
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   *
   * <p>Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTask.setValues(ServiceTask)"})
  public void testSetValuesWithServiceTask() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

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

    ArrayList<CustomProperty> customPropertyList = new ArrayList<>();
    customPropertyList.add(customProperty);

    AlfrescoMailTask otherElement = mock(AlfrescoMailTask.class);
    when(otherElement.getCustomProperties()).thenReturn(customPropertyList);
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
    ComplexDataType complexValue2 = customProperties.get(0).getComplexValue();
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
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTask.setValues(ServiceTask)"})
  public void testSetValuesWithServiceTask_givenArrayListAddFieldExtension() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

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

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(fieldExtensions);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert that nothing has changed
    List<CustomProperty> customProperties2 = otherElement.getCustomProperties();
    assertEquals(1, customProperties2.size());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
    assertSame(customProperties, customProperties2);
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTask.setValues(ServiceTask)"})
  public void testSetValuesWithServiceTask_givenArrayListAddFieldExtension2() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(new ArrayList<>());

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(fieldExtensions);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert that nothing has changed
    List<CustomProperty> customProperties2 = otherElement.getCustomProperties();
    assertEquals(1, customProperties2.size());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
    assertSame(customProperties, customProperties2);
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   *
   * <ul>
   *   <li>Given {@link CustomProperty} (default constructor) ComplexValue is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTask.setValues(ServiceTask)"})
  public void testSetValuesWithServiceTask_givenCustomPropertyComplexValueIsNull() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(fieldExtensions);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert that nothing has changed
    List<CustomProperty> customProperties2 = otherElement.getCustomProperties();
    assertEquals(1, customProperties2.size());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
    assertSame(customProperties, customProperties2);
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   *
   * <ul>
   *   <li>Given {@link DataGrid} {@link DataGrid#clone()} return {@link DataGrid} (default
   *       constructor).
   *   <li>Then calls {@link DataGrid#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTask.setValues(ServiceTask)"})
  public void testSetValuesWithServiceTask_givenDataGridCloneReturnDataGrid_thenCallsClone() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

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

    DataGrid complexValue = mock(DataGrid.class);
    when(complexValue.clone()).thenReturn(new DataGrid());
    doNothing().when(complexValue).setRows(Mockito.<List<DataGridRow>>any());
    complexValue.setRows(rows);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(fieldExtensions);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert that nothing has changed
    verify(complexValue).clone();
    verify(complexValue).setRows(isA(List.class));
    verify(fieldExtension).clone();
    List<CustomProperty> customProperties2 = otherElement.getCustomProperties();
    assertEquals(1, customProperties2.size());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
    assertSame(customProperties, customProperties2);
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   *
   * <ul>
   *   <li>Given {@link DataGrid} (default constructor) Rows is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTask.setValues(ServiceTask)"})
  public void testSetValuesWithServiceTask_givenDataGridRowsIsNull() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    DataGrid complexValue = new DataGrid();
    complexValue.setRows(null);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(fieldExtensions);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert that nothing has changed
    List<CustomProperty> customProperties2 = otherElement.getCustomProperties();
    assertEquals(1, customProperties2.size());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
    assertSame(customProperties, customProperties2);
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ServiceTask} (default constructor) CustomProperties is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTask.setValues(ServiceTask)"})
  public void testSetValuesWithServiceTask_givenNull_whenServiceTaskCustomPropertiesIsNull() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(fieldExtensions);
    otherElement.setCustomProperties(null);

    // Act
    serviceTask.setValues(otherElement);

    // Assert that nothing has changed
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ServiceTask} (default constructor) FieldExtensions is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTask.setValues(ServiceTask)"})
  public void testSetValuesWithServiceTask_givenNull_whenServiceTaskFieldExtensionsIsNull() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

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

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(null);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert that nothing has changed
    List<CustomProperty> customProperties2 = otherElement.getCustomProperties();
    assertEquals(1, customProperties2.size());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
    assertSame(customProperties, customProperties2);
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   *
   * <ul>
   *   <li>Then calls {@link FieldExtension#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTask.setValues(ServiceTask)"})
  public void testSetValuesWithServiceTask_thenCallsClone() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

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

    ServiceTask otherElement = new ServiceTask();
    otherElement.setFieldExtensions(fieldExtensions);
    otherElement.setCustomProperties(customProperties);

    // Act
    serviceTask.setValues(otherElement);

    // Assert that nothing has changed
    verify(fieldExtension).clone();
    List<CustomProperty> customProperties2 = otherElement.getCustomProperties();
    assertEquals(1, customProperties2.size());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
    assertSame(customProperties, customProperties2);
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   *
   * <ul>
   *   <li>Then {@link ServiceTask} (default constructor) CustomProperties Empty.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTask.setValues(ServiceTask)"})
  public void testSetValuesWithServiceTask_thenServiceTaskCustomPropertiesEmpty() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    ServiceTask otherElement = new ServiceTask();

    // Act
    serviceTask.setValues(otherElement);

    // Assert that nothing has changed
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomProperties().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link ServiceTask#setValues(ServiceTask)} with {@code ServiceTask}.
   *
   * <ul>
   *   <li>Then {@link ServiceTask} (default constructor) FailedJobRetryTimeCycleValue is {@code
   *       42}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTask.setValues(ServiceTask)"})
  public void testSetValuesWithServiceTask_thenServiceTaskFailedJobRetryTimeCycleValueIs42() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();

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

    ArrayList<CustomProperty> customPropertyList = new ArrayList<>();
    customPropertyList.add(customProperty);

    AlfrescoMailTask otherElement = mock(AlfrescoMailTask.class);
    when(otherElement.getCustomProperties()).thenReturn(customPropertyList);
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
    verify(customProperty).clone();
    verify(customProperty).setComplexValue(isA(ComplexDataType.class));
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
    assertFalse(serviceTask.isExclusive());
    assertTrue(serviceTask.hasMultiInstanceLoopCharacteristics());
    assertTrue(serviceTask.isForCompensation());
    assertTrue(serviceTask.isAsynchronous());
    assertTrue(serviceTask.isNotExclusive());
    assertTrue(serviceTask.isExtended());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTask.<init>()",
    "List ServiceTask.getCustomProperties()",
    "String ServiceTask.getExtensionId()",
    "String ServiceTask.getImplementation()",
    "String ServiceTask.getImplementationType()",
    "String ServiceTask.getOperationRef()",
    "String ServiceTask.getResultVariableName()",
    "String ServiceTask.getSkipExpression()",
    "String ServiceTask.getType()",
    "void ServiceTask.setCustomProperties(List)",
    "void ServiceTask.setExtensionId(String)",
    "void ServiceTask.setImplementation(String)",
    "void ServiceTask.setImplementationType(String)",
    "void ServiceTask.setOperationRef(String)",
    "void ServiceTask.setResultVariableName(String)",
    "void ServiceTask.setSkipExpression(String)",
    "void ServiceTask.setType(String)"
  })
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

    // Assert
    assertEquals("42", actualExtensionId);
    assertEquals("Implementation Type", actualImplementationType);
    assertEquals("Implementation", actualImplementation);
    assertEquals("Operation Ref", actualOperationRef);
    assertEquals("Result Variable Name", actualResultVariableName);
    assertEquals("Skip Expression", actualSkipExpression);
    assertEquals("Type", actualServiceTask.getType());
    assertNull(actualServiceTask.getBehavior());
    assertNull(actualServiceTask.getDefaultFlow());
    assertNull(actualServiceTask.getFailedJobRetryTimeCycleValue());
    assertNull(actualServiceTask.getId());
    assertNull(actualServiceTask.getDocumentation());
    assertNull(actualServiceTask.getName());
    assertNull(actualServiceTask.getParentContainer());
    assertNull(actualServiceTask.getIoSpecification());
    assertNull(actualServiceTask.getLoopCharacteristics());
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
