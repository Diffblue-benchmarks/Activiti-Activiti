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
   * Method under test: {@link ServiceTask#isExtended()}
   */
  @Test
  public void testIsExtended() {
    // Arrange, Act and Assert
    assertFalse((new ServiceTask()).isExtended());
  }

  /**
   * Method under test: {@link ServiceTask#isExtended()}
   */
  @Test
  public void testIsExtended2() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setExtensionId("");

    // Act and Assert
    assertFalse(serviceTask.isExtended());
  }

  /**
   * Method under test: {@link ServiceTask#isExtended()}
   */
  @Test
  public void testIsExtended3() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setExtensionId("foo");

    // Act and Assert
    assertTrue(serviceTask.isExtended());
  }

  /**
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents() {
    // Arrange, Act and Assert
    assertFalse((new ServiceTask()).hasBoundaryErrorEvents());
  }

  /**
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents2() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setBoundaryEvents(null);

    // Act and Assert
    assertFalse(serviceTask.hasBoundaryErrorEvents());
  }

  /**
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents3() {
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
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents4() {
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
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents5() {
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
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents6() {
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
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents7() {
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
   * Method under test: {@link ServiceTask#hasBoundaryErrorEvents()}
   */
  @Test
  public void testHasBoundaryErrorEvents8() {
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
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone() {
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
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(null);
    serviceTask.setCustomProperties(null);

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
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(null);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(null);
    serviceTask.setCustomProperties(customProperties);

    // Act
    ServiceTask actualCloneResult = serviceTask.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    List<CustomProperty> customProperties2 = actualCloneResult.getCustomProperties();
    assertEquals(1, customProperties2.size());
    CustomProperty getResult = customProperties2.get(0);
    assertNull(getResult.getId());
    assertNull(actualCloneResult.getId());
    assertNull(getResult.getName());
    assertNull(getResult.getSimpleValue());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getExtensionId());
    assertNull(actualCloneResult.getImplementation());
    assertNull(actualCloneResult.getImplementationType());
    assertNull(actualCloneResult.getOperationRef());
    assertNull(actualCloneResult.getResultVariableName());
    assertNull(actualCloneResult.getSkipExpression());
    assertNull(actualCloneResult.getType());
    assertNull(getResult.getComplexValue());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
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
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone4() {
    // Arrange
    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(new DataGrid());

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(null);
    serviceTask.setCustomProperties(customProperties);

    // Act
    ServiceTask actualCloneResult = serviceTask.clone();

    // Assert
    List<CustomProperty> customProperties2 = actualCloneResult.getCustomProperties();
    assertEquals(1, customProperties2.size());
    CustomProperty getResult = customProperties2.get(0);
    ComplexDataType complexValue = getResult.getComplexValue();
    assertTrue(complexValue instanceof DataGrid);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(getResult.getId());
    assertNull(actualCloneResult.getId());
    assertNull(getResult.getName());
    assertNull(getResult.getSimpleValue());
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
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
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
    assertTrue(((DataGrid) complexValue).getRows().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone5() {
    // Arrange
    DataGrid complexValue = new DataGrid();
    complexValue.setRows(null);

    CustomProperty customProperty = new CustomProperty();
    customProperty.setComplexValue(complexValue);

    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    customProperties.add(customProperty);

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(null);
    serviceTask.setCustomProperties(customProperties);

    // Act
    ServiceTask actualCloneResult = serviceTask.clone();

    // Assert
    List<CustomProperty> customProperties2 = actualCloneResult.getCustomProperties();
    assertEquals(1, customProperties2.size());
    CustomProperty getResult = customProperties2.get(0);
    ComplexDataType complexValue2 = getResult.getComplexValue();
    assertTrue(complexValue2 instanceof DataGrid);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(getResult.getId());
    assertNull(actualCloneResult.getId());
    assertNull(getResult.getName());
    assertNull(getResult.getSimpleValue());
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
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
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
    assertTrue(((DataGrid) complexValue2).getRows().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone6() {
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
    serviceTask.setFieldExtensions(null);
    serviceTask.setCustomProperties(customProperties);

    // Act
    ServiceTask actualCloneResult = serviceTask.clone();

    // Assert
    List<CustomProperty> customProperties2 = actualCloneResult.getCustomProperties();
    assertEquals(1, customProperties2.size());
    CustomProperty getResult = customProperties2.get(0);
    ComplexDataType complexValue2 = getResult.getComplexValue();
    assertTrue(complexValue2 instanceof DataGrid);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(getResult.getId());
    assertNull(actualCloneResult.getId());
    assertNull(getResult.getName());
    assertNull(getResult.getSimpleValue());
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
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    List<DataGridRow> rows2 = ((DataGrid) complexValue2).getRows();
    assertEquals(1, rows2.size());
    DataGridRow getResult2 = rows2.get(0);
    assertEquals(0, getResult2.getIndex());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(actualCloneResult.isExtended());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(getResult2.getFields().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone7() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setFieldExtensions(fieldExtensions);
    serviceTask.setCustomProperties(null);

    // Act
    ServiceTask actualCloneResult = serviceTask.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    List<FieldExtension> fieldExtensions2 = actualCloneResult.getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    FieldExtension getResult = fieldExtensions2.get(0);
    assertNull(getResult.getId());
    assertNull(actualCloneResult.getId());
    assertNull(getResult.getExpression());
    assertNull(getResult.getFieldName());
    assertNull(getResult.getStringValue());
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
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
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
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link ServiceTask#clone()}
   */
  @Test
  public void testClone8() {
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
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValues() {
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
    assertNull(otherElement.getIoSpecification());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomProperties().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValues2() {
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
    assertNull(otherElement.getCustomProperties());
    assertNull(otherElement.getIoSpecification());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValues3() {
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
    assertNull(otherElement.getIoSpecification());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
    assertSame(customProperties, otherElement.getCustomProperties());
  }

  /**
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValues4() {
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
    assertNull(otherElement.getIoSpecification());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
    assertSame(customProperties, otherElement.getCustomProperties());
  }

  /**
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValues5() {
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
    assertNull(otherElement.getIoSpecification());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
    assertSame(customProperties, otherElement.getCustomProperties());
  }

  /**
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValues6() {
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
    assertNull(otherElement.getIoSpecification());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
    assertSame(customProperties, otherElement.getCustomProperties());
  }

  /**
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValues7() {
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
    assertNull(otherElement.getCustomProperties());
    assertNull(otherElement.getIoSpecification());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValues8() {
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
    assertTrue(serviceTask.getCustomProperties().isEmpty());
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
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValues9() {
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
    List<CustomProperty> customProperties = serviceTask.getCustomProperties();
    assertEquals(1, customProperties.size());
    CustomProperty getResult = customProperties.get(0);
    assertNull(getResult.getId());
    IOSpecification ioSpecification = serviceTask.getIoSpecification();
    assertNull(ioSpecification.getId());
    MultiInstanceLoopCharacteristics loopCharacteristics = serviceTask.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(getResult.getName());
    assertNull(getResult.getSimpleValue());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertNull(getResult.getComplexValue());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(serviceTask.isExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(serviceTask.hasMultiInstanceLoopCharacteristics());
    assertTrue(serviceTask.isForCompensation());
    assertTrue(serviceTask.isAsynchronous());
    assertTrue(serviceTask.isNotExclusive());
    assertTrue(serviceTask.isExtended());
  }

  /**
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValues10() {
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
    assertNull(otherElement.getIoSpecification());
    assertNull(otherElement.getLoopCharacteristics());
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
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValues11() {
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
    assertNull(otherElement.getIoSpecification());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
    assertSame(customProperties, otherElement.getCustomProperties());
  }

  /**
   * Method under test: {@link ServiceTask#setValues(ServiceTask)}
   */
  @Test
  public void testSetValues12() {
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
    assertNull(otherElement.getCustomProperties());
    assertNull(otherElement.getIoSpecification());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.isExclusive());
  }

  /**
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
