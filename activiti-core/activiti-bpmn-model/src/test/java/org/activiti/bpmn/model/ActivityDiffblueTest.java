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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

public class ActivityDiffblueTest {
  /**
   * Method under test: {@link Activity#getFailedJobRetryTimeCycleValue()}
   */
  @Test
  public void testGetFailedJobRetryTimeCycleValue() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getFailedJobRetryTimeCycleValue());
  }

  /**
   * Method under test: {@link Activity#setFailedJobRetryTimeCycleValue(String)}
   */
  @Test
  public void testSetFailedJobRetryTimeCycleValue() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setFailedJobRetryTimeCycleValue("42");

    // Assert
    assertEquals("42", adhocSubProcess.getFailedJobRetryTimeCycleValue());
  }

  /**
   * Method under test: {@link Activity#isForCompensation()}
   */
  @Test
  public void testIsForCompensation() {
    // Arrange, Act and Assert
    assertFalse((new AdhocSubProcess()).isForCompensation());
  }

  /**
   * Method under test: {@link Activity#isForCompensation()}
   */
  @Test
  public void testIsForCompensation2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setForCompensation(true);

    // Act and Assert
    assertTrue(adhocSubProcess.isForCompensation());
  }

  /**
   * Method under test: {@link Activity#setForCompensation(boolean)}
   */
  @Test
  public void testSetForCompensation() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setForCompensation(true);

    // Assert
    assertTrue(adhocSubProcess.isForCompensation());
  }

  /**
   * Method under test: {@link Activity#getBoundaryEvents()}
   */
  @Test
  public void testGetBoundaryEvents() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    List<BoundaryEvent> actualBoundaryEvents = adhocSubProcess.getBoundaryEvents();

    // Assert
    assertTrue(actualBoundaryEvents.isEmpty());
    assertSame(adhocSubProcess.boundaryEvents, actualBoundaryEvents);
  }

  /**
   * Method under test: {@link Activity#setBoundaryEvents(List)}
   */
  @Test
  public void testSetBoundaryEvents() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();

    // Act
    adhocSubProcess.setBoundaryEvents(boundaryEvents);

    // Assert
    assertSame(boundaryEvents, adhocSubProcess.getBoundaryEvents());
  }

  /**
   * Method under test: {@link Activity#setBoundaryEvents(List)}
   */
  @Test
  public void testSetBoundaryEvents2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    // Act
    adhocSubProcess.setBoundaryEvents(boundaryEvents);

    // Assert
    assertSame(boundaryEvents, adhocSubProcess.getBoundaryEvents());
  }

  /**
   * Method under test: {@link Activity#setBoundaryEvents(List)}
   */
  @Test
  public void testSetBoundaryEvents3() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());
    boundaryEvents.add(new BoundaryEvent());

    // Act
    adhocSubProcess.setBoundaryEvents(boundaryEvents);

    // Assert
    assertSame(boundaryEvents, adhocSubProcess.getBoundaryEvents());
  }

  /**
   * Method under test: {@link Activity#getDefaultFlow()}
   */
  @Test
  public void testGetDefaultFlow() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getDefaultFlow());
  }

  /**
   * Method under test: {@link Activity#setDefaultFlow(String)}
   */
  @Test
  public void testSetDefaultFlow() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    adhocSubProcess.setDefaultFlow("Default Flow");

    // Assert
    assertEquals("Default Flow", adhocSubProcess.getDefaultFlow());
  }

  /**
   * Method under test: {@link Activity#getLoopCharacteristics()}
   */
  @Test
  public void testGetLoopCharacteristics() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getLoopCharacteristics());
  }

  /**
   * Method under test:
   * {@link Activity#setLoopCharacteristics(MultiInstanceLoopCharacteristics)}
   */
  @Test
  public void testSetLoopCharacteristics() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();

    // Act
    adhocSubProcess.setLoopCharacteristics(loopCharacteristics);

    // Assert
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
    assertSame(loopCharacteristics, adhocSubProcess.getLoopCharacteristics());
  }

  /**
   * Method under test: {@link Activity#hasMultiInstanceLoopCharacteristics()}
   */
  @Test
  public void testHasMultiInstanceLoopCharacteristics() {
    // Arrange, Act and Assert
    assertFalse((new AdhocSubProcess()).hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Method under test: {@link Activity#hasMultiInstanceLoopCharacteristics()}
   */
  @Test
  public void testHasMultiInstanceLoopCharacteristics2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());

    // Act and Assert
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Method under test: {@link Activity#getIoSpecification()}
   */
  @Test
  public void testGetIoSpecification() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getIoSpecification());
  }

  /**
   * Method under test: {@link Activity#setIoSpecification(IOSpecification)}
   */
  @Test
  public void testSetIoSpecification() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    IOSpecification ioSpecification = new IOSpecification();

    // Act
    adhocSubProcess.setIoSpecification(ioSpecification);

    // Assert
    assertSame(ioSpecification, adhocSubProcess.getIoSpecification());
  }

  /**
   * Method under test: {@link Activity#getDataInputAssociations()}
   */
  @Test
  public void testGetDataInputAssociations() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    List<DataAssociation> actualDataInputAssociations = adhocSubProcess.getDataInputAssociations();

    // Assert
    assertTrue(actualDataInputAssociations.isEmpty());
    assertSame(adhocSubProcess.dataInputAssociations, actualDataInputAssociations);
  }

  /**
   * Method under test: {@link Activity#setDataInputAssociations(List)}
   */
  @Test
  public void testSetDataInputAssociations() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();

    // Act
    adhocSubProcess.setDataInputAssociations(dataInputAssociations);

    // Assert
    assertSame(dataInputAssociations, adhocSubProcess.getDataInputAssociations());
  }

  /**
   * Method under test: {@link Activity#setDataInputAssociations(List)}
   */
  @Test
  public void testSetDataInputAssociations2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    // Act
    adhocSubProcess.setDataInputAssociations(dataInputAssociations);

    // Assert
    assertSame(dataInputAssociations, adhocSubProcess.getDataInputAssociations());
  }

  /**
   * Method under test: {@link Activity#setDataInputAssociations(List)}
   */
  @Test
  public void testSetDataInputAssociations3() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());
    dataInputAssociations.add(new DataAssociation());

    // Act
    adhocSubProcess.setDataInputAssociations(dataInputAssociations);

    // Assert
    assertSame(dataInputAssociations, adhocSubProcess.getDataInputAssociations());
  }

  /**
   * Method under test: {@link Activity#getDataOutputAssociations()}
   */
  @Test
  public void testGetDataOutputAssociations() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    List<DataAssociation> actualDataOutputAssociations = adhocSubProcess.getDataOutputAssociations();

    // Assert
    assertTrue(actualDataOutputAssociations.isEmpty());
    assertSame(adhocSubProcess.dataOutputAssociations, actualDataOutputAssociations);
  }

  /**
   * Method under test: {@link Activity#setDataOutputAssociations(List)}
   */
  @Test
  public void testSetDataOutputAssociations() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();

    // Act
    adhocSubProcess.setDataOutputAssociations(dataOutputAssociations);

    // Assert
    assertSame(dataOutputAssociations, adhocSubProcess.getDataOutputAssociations());
  }

  /**
   * Method under test: {@link Activity#setDataOutputAssociations(List)}
   */
  @Test
  public void testSetDataOutputAssociations2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    // Act
    adhocSubProcess.setDataOutputAssociations(dataOutputAssociations);

    // Assert
    assertSame(dataOutputAssociations, adhocSubProcess.getDataOutputAssociations());
  }

  /**
   * Method under test: {@link Activity#setDataOutputAssociations(List)}
   */
  @Test
  public void testSetDataOutputAssociations3() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());
    dataOutputAssociations.add(new DataAssociation());

    // Act
    adhocSubProcess.setDataOutputAssociations(dataOutputAssociations);

    // Assert
    assertSame(dataOutputAssociations, adhocSubProcess.getDataOutputAssociations());
  }

  /**
   * Method under test: {@link Activity#getMapExceptions()}
   */
  @Test
  public void testGetMapExceptions() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    // Act
    List<MapExceptionEntry> actualMapExceptions = adhocSubProcess.getMapExceptions();

    // Assert
    assertTrue(actualMapExceptions.isEmpty());
    assertSame(adhocSubProcess.mapExceptions, actualMapExceptions);
  }

  /**
   * Method under test: {@link Activity#setMapExceptions(List)}
   */
  @Test
  public void testSetMapExceptions() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    adhocSubProcess.setMapExceptions(mapExceptions);

    // Assert
    assertSame(mapExceptions, adhocSubProcess.getMapExceptions());
  }

  /**
   * Method under test: {@link Activity#setMapExceptions(List)}
   */
  @Test
  public void testSetMapExceptions2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act
    adhocSubProcess.setMapExceptions(mapExceptions);

    // Assert
    assertSame(mapExceptions, adhocSubProcess.getMapExceptions());
  }

  /**
   * Method under test: {@link Activity#setMapExceptions(List)}
   */
  @Test
  public void testSetMapExceptions3() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));
    mapExceptions.add(new MapExceptionEntry("An error occurred", "Class Name", true));

    // Act
    adhocSubProcess.setMapExceptions(mapExceptions);

    // Assert
    assertSame(mapExceptions, adhocSubProcess.getMapExceptions());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    AdhocSubProcess otherActivity = new AdhocSubProcess();

    // Act
    adhocSubProcess.setValues((Activity) otherActivity);

    // Assert
    assertNull(otherActivity.getDefaultFlow());
    assertNull(otherActivity.getFailedJobRetryTimeCycleValue());
    assertNull(otherActivity.getId());
    assertNull(otherActivity.getDocumentation());
    assertNull(otherActivity.getName());
    assertNull(otherActivity.getIoSpecification());
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.getDataInputAssociations().isEmpty());
    assertTrue(otherActivity.getDataOutputAssociations().isEmpty());
    assertTrue(otherActivity.isExclusive());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess otherActivity = new AdhocSubProcess();
    otherActivity.setLoopCharacteristics(null);
    otherActivity.setIoSpecification(null);
    otherActivity.setDataInputAssociations(null);
    otherActivity.setDataOutputAssociations(null);
    otherActivity.setBoundaryEvents(boundaryEvents);

    // Act
    adhocSubProcess.setValues((Activity) otherActivity);

    // Assert
    assertNull(otherActivity.getDefaultFlow());
    assertNull(otherActivity.getFailedJobRetryTimeCycleValue());
    assertNull(otherActivity.getId());
    assertNull(otherActivity.getDocumentation());
    assertNull(otherActivity.getName());
    assertNull(otherActivity.getDataInputAssociations());
    assertNull(otherActivity.getDataOutputAssociations());
    assertNull(otherActivity.getIoSpecification());
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues3() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess otherActivity = new AdhocSubProcess();
    otherActivity.setLoopCharacteristics(null);
    otherActivity.setIoSpecification(null);
    otherActivity.setDataInputAssociations(null);
    otherActivity.setDataOutputAssociations(dataOutputAssociations);
    otherActivity.setBoundaryEvents(boundaryEvents);

    // Act
    adhocSubProcess.setValues((Activity) otherActivity);

    // Assert
    assertNull(otherActivity.getDefaultFlow());
    assertNull(otherActivity.getFailedJobRetryTimeCycleValue());
    assertNull(otherActivity.getId());
    assertNull(otherActivity.getDocumentation());
    assertNull(otherActivity.getName());
    assertNull(otherActivity.getDataInputAssociations());
    assertNull(otherActivity.getIoSpecification());
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
    assertSame(dataOutputAssociations, otherActivity.getDataOutputAssociations());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues4() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess otherActivity = new AdhocSubProcess();
    otherActivity.setLoopCharacteristics(null);
    otherActivity.setIoSpecification(null);
    otherActivity.setDataInputAssociations(dataInputAssociations);
    otherActivity.setDataOutputAssociations(null);
    otherActivity.setBoundaryEvents(boundaryEvents);

    // Act
    adhocSubProcess.setValues((Activity) otherActivity);

    // Assert
    assertNull(otherActivity.getDefaultFlow());
    assertNull(otherActivity.getFailedJobRetryTimeCycleValue());
    assertNull(otherActivity.getId());
    assertNull(otherActivity.getDocumentation());
    assertNull(otherActivity.getName());
    assertNull(otherActivity.getDataOutputAssociations());
    assertNull(otherActivity.getIoSpecification());
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
    assertSame(dataInputAssociations, otherActivity.getDataInputAssociations());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues5() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess otherActivity = new AdhocSubProcess();
    otherActivity.setLoopCharacteristics(null);
    IOSpecification ioSpecification = new IOSpecification();
    otherActivity.setIoSpecification(ioSpecification);
    otherActivity.setDataInputAssociations(null);
    otherActivity.setDataOutputAssociations(null);
    otherActivity.setBoundaryEvents(boundaryEvents);

    // Act
    adhocSubProcess.setValues((Activity) otherActivity);

    // Assert
    assertNull(otherActivity.getDefaultFlow());
    assertNull(otherActivity.getFailedJobRetryTimeCycleValue());
    assertNull(otherActivity.getId());
    assertNull(otherActivity.getDocumentation());
    assertNull(otherActivity.getName());
    assertNull(otherActivity.getDataInputAssociations());
    assertNull(otherActivity.getDataOutputAssociations());
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
    assertSame(ioSpecification, otherActivity.getIoSpecification());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues6() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess otherActivity = new AdhocSubProcess();
    otherActivity.setLoopCharacteristics(null);
    otherActivity.setIoSpecification(ioSpecification);
    otherActivity.setDataInputAssociations(null);
    otherActivity.setDataOutputAssociations(null);
    otherActivity.setBoundaryEvents(boundaryEvents);

    // Act
    adhocSubProcess.setValues((Activity) otherActivity);

    // Assert
    assertNull(otherActivity.getDefaultFlow());
    assertNull(otherActivity.getFailedJobRetryTimeCycleValue());
    assertNull(otherActivity.getId());
    assertNull(otherActivity.getDocumentation());
    assertNull(otherActivity.getName());
    assertNull(otherActivity.getDataInputAssociations());
    assertNull(otherActivity.getDataOutputAssociations());
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
    assertSame(ioSpecification, otherActivity.getIoSpecification());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues7() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess otherActivity = new AdhocSubProcess();
    otherActivity.setLoopCharacteristics(null);
    otherActivity.setIoSpecification(ioSpecification);
    otherActivity.setDataInputAssociations(null);
    otherActivity.setDataOutputAssociations(null);
    otherActivity.setBoundaryEvents(boundaryEvents);

    // Act
    adhocSubProcess.setValues((Activity) otherActivity);

    // Assert
    assertNull(otherActivity.getDefaultFlow());
    assertNull(otherActivity.getFailedJobRetryTimeCycleValue());
    assertNull(otherActivity.getId());
    assertNull(otherActivity.getDocumentation());
    assertNull(otherActivity.getName());
    assertNull(otherActivity.getDataInputAssociations());
    assertNull(otherActivity.getDataOutputAssociations());
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
    assertSame(ioSpecification, otherActivity.getIoSpecification());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues8() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    BusinessRuleTask otherActivity = mock(BusinessRuleTask.class);
    when(otherActivity.isForCompensation()).thenReturn(true);
    when(otherActivity.isAsynchronous()).thenReturn(true);
    when(otherActivity.isNotExclusive()).thenReturn(true);
    when(otherActivity.getDefaultFlow()).thenReturn("Default Flow");
    when(otherActivity.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherActivity.getId()).thenReturn("42");
    when(otherActivity.getDocumentation()).thenReturn("Documentation");
    when(otherActivity.getName()).thenReturn("Name");
    when(otherActivity.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherActivity.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(otherActivity.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(otherActivity.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherActivity.getAttributes()).thenReturn(new HashMap<>());
    when(otherActivity.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherActivity.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherActivity.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    adhocSubProcess.setValues(otherActivity);

    // Assert
    verify(otherActivity).getBoundaryEvents();
    verify(otherActivity, atLeast(1)).getDataInputAssociations();
    verify(otherActivity, atLeast(1)).getDataOutputAssociations();
    verify(otherActivity).getDefaultFlow();
    verify(otherActivity).getFailedJobRetryTimeCycleValue();
    verify(otherActivity, atLeast(1)).getIoSpecification();
    verify(otherActivity, atLeast(1)).getLoopCharacteristics();
    verify(otherActivity).isForCompensation();
    verify(otherActivity, atLeast(1)).getAttributes();
    verify(otherActivity, atLeast(1)).getExtensionElements();
    verify(otherActivity).getId();
    verify(otherActivity).getDocumentation();
    verify(otherActivity, atLeast(1)).getExecutionListeners();
    verify(otherActivity).getName();
    verify(otherActivity).isAsynchronous();
    verify(otherActivity).isNotExclusive();
    assertEquals("42", adhocSubProcess.getFailedJobRetryTimeCycleValue());
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Default Flow", adhocSubProcess.getDefaultFlow());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    IOSpecification ioSpecification = adhocSubProcess.getIoSpecification();
    assertNull(ioSpecification.getId());
    MultiInstanceLoopCharacteristics loopCharacteristics = adhocSubProcess.getLoopCharacteristics();
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
    assertFalse(adhocSubProcess.isExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(adhocSubProcess.getDataInputAssociations().isEmpty());
    assertTrue(adhocSubProcess.getDataOutputAssociations().isEmpty());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
    assertTrue(adhocSubProcess.isForCompensation());
    assertTrue(adhocSubProcess.isAsynchronous());
    assertTrue(adhocSubProcess.isNotExclusive());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues9() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(new DataAssociation());
    BusinessRuleTask otherActivity = mock(BusinessRuleTask.class);
    when(otherActivity.isForCompensation()).thenReturn(true);
    when(otherActivity.isAsynchronous()).thenReturn(true);
    when(otherActivity.isNotExclusive()).thenReturn(true);
    when(otherActivity.getDefaultFlow()).thenReturn("Default Flow");
    when(otherActivity.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherActivity.getId()).thenReturn("42");
    when(otherActivity.getDocumentation()).thenReturn("Documentation");
    when(otherActivity.getName()).thenReturn("Name");
    when(otherActivity.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherActivity.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherActivity.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(otherActivity.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherActivity.getAttributes()).thenReturn(new HashMap<>());
    when(otherActivity.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherActivity.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherActivity.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    adhocSubProcess.setValues(otherActivity);

    // Assert
    verify(otherActivity).getBoundaryEvents();
    verify(otherActivity, atLeast(1)).getDataInputAssociations();
    verify(otherActivity, atLeast(1)).getDataOutputAssociations();
    verify(otherActivity).getDefaultFlow();
    verify(otherActivity).getFailedJobRetryTimeCycleValue();
    verify(otherActivity, atLeast(1)).getIoSpecification();
    verify(otherActivity, atLeast(1)).getLoopCharacteristics();
    verify(otherActivity).isForCompensation();
    verify(otherActivity, atLeast(1)).getAttributes();
    verify(otherActivity, atLeast(1)).getExtensionElements();
    verify(otherActivity).getId();
    verify(otherActivity).getDocumentation();
    verify(otherActivity, atLeast(1)).getExecutionListeners();
    verify(otherActivity).getName();
    verify(otherActivity).isAsynchronous();
    verify(otherActivity).isNotExclusive();
    assertEquals("42", adhocSubProcess.getFailedJobRetryTimeCycleValue());
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Default Flow", adhocSubProcess.getDefaultFlow());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    List<DataAssociation> dataInputAssociations = adhocSubProcess.getDataInputAssociations();
    assertEquals(1, dataInputAssociations.size());
    DataAssociation getResult = dataInputAssociations.get(0);
    assertNull(getResult.getId());
    IOSpecification ioSpecification = adhocSubProcess.getIoSpecification();
    assertNull(ioSpecification.getId());
    MultiInstanceLoopCharacteristics loopCharacteristics = adhocSubProcess.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(adhocSubProcess.isExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(adhocSubProcess.getDataOutputAssociations().isEmpty());
    assertTrue(getResult.getAssignments().isEmpty());
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
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
    assertTrue(adhocSubProcess.isForCompensation());
    assertTrue(adhocSubProcess.isAsynchronous());
    assertTrue(adhocSubProcess.isNotExclusive());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues10() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(new DataAssociation());
    BusinessRuleTask otherActivity = mock(BusinessRuleTask.class);
    when(otherActivity.isForCompensation()).thenReturn(true);
    when(otherActivity.isAsynchronous()).thenReturn(true);
    when(otherActivity.isNotExclusive()).thenReturn(true);
    when(otherActivity.getDefaultFlow()).thenReturn("Default Flow");
    when(otherActivity.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherActivity.getId()).thenReturn("42");
    when(otherActivity.getDocumentation()).thenReturn("Documentation");
    when(otherActivity.getName()).thenReturn("Name");
    when(otherActivity.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherActivity.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(otherActivity.getDataOutputAssociations()).thenReturn(dataAssociationList);
    when(otherActivity.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherActivity.getAttributes()).thenReturn(new HashMap<>());
    when(otherActivity.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherActivity.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherActivity.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    adhocSubProcess.setValues(otherActivity);

    // Assert
    verify(otherActivity).getBoundaryEvents();
    verify(otherActivity, atLeast(1)).getDataInputAssociations();
    verify(otherActivity, atLeast(1)).getDataOutputAssociations();
    verify(otherActivity).getDefaultFlow();
    verify(otherActivity).getFailedJobRetryTimeCycleValue();
    verify(otherActivity, atLeast(1)).getIoSpecification();
    verify(otherActivity, atLeast(1)).getLoopCharacteristics();
    verify(otherActivity).isForCompensation();
    verify(otherActivity, atLeast(1)).getAttributes();
    verify(otherActivity, atLeast(1)).getExtensionElements();
    verify(otherActivity).getId();
    verify(otherActivity).getDocumentation();
    verify(otherActivity, atLeast(1)).getExecutionListeners();
    verify(otherActivity).getName();
    verify(otherActivity).isAsynchronous();
    verify(otherActivity).isNotExclusive();
    assertEquals("42", adhocSubProcess.getFailedJobRetryTimeCycleValue());
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Default Flow", adhocSubProcess.getDefaultFlow());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    List<DataAssociation> dataOutputAssociations = adhocSubProcess.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations.size());
    DataAssociation getResult = dataOutputAssociations.get(0);
    assertNull(getResult.getId());
    IOSpecification ioSpecification = adhocSubProcess.getIoSpecification();
    assertNull(ioSpecification.getId());
    MultiInstanceLoopCharacteristics loopCharacteristics = adhocSubProcess.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(adhocSubProcess.isExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(adhocSubProcess.getDataInputAssociations().isEmpty());
    assertTrue(getResult.getAssignments().isEmpty());
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
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
    assertTrue(adhocSubProcess.isForCompensation());
    assertTrue(adhocSubProcess.isAsynchronous());
    assertTrue(adhocSubProcess.isNotExclusive());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues11() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    IOSpecification ioSpecification = mock(IOSpecification.class);
    IOSpecification ioSpecification2 = new IOSpecification();
    when(ioSpecification.clone()).thenReturn(ioSpecification2);
    BusinessRuleTask otherActivity = mock(BusinessRuleTask.class);
    when(otherActivity.isForCompensation()).thenReturn(true);
    when(otherActivity.isAsynchronous()).thenReturn(true);
    when(otherActivity.isNotExclusive()).thenReturn(true);
    when(otherActivity.getDefaultFlow()).thenReturn("Default Flow");
    when(otherActivity.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherActivity.getId()).thenReturn("42");
    when(otherActivity.getDocumentation()).thenReturn("Documentation");
    when(otherActivity.getName()).thenReturn("Name");
    when(otherActivity.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherActivity.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(otherActivity.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(otherActivity.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherActivity.getAttributes()).thenReturn(new HashMap<>());
    when(otherActivity.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherActivity.getIoSpecification()).thenReturn(ioSpecification);
    when(otherActivity.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    adhocSubProcess.setValues(otherActivity);

    // Assert
    verify(otherActivity).getBoundaryEvents();
    verify(otherActivity, atLeast(1)).getDataInputAssociations();
    verify(otherActivity, atLeast(1)).getDataOutputAssociations();
    verify(otherActivity).getDefaultFlow();
    verify(otherActivity).getFailedJobRetryTimeCycleValue();
    verify(otherActivity, atLeast(1)).getIoSpecification();
    verify(otherActivity, atLeast(1)).getLoopCharacteristics();
    verify(otherActivity).isForCompensation();
    verify(otherActivity, atLeast(1)).getAttributes();
    verify(otherActivity, atLeast(1)).getExtensionElements();
    verify(otherActivity).getId();
    verify(otherActivity).getDocumentation();
    verify(otherActivity, atLeast(1)).getExecutionListeners();
    verify(otherActivity).getName();
    verify(otherActivity).isAsynchronous();
    verify(otherActivity).isNotExclusive();
    verify(ioSpecification).clone();
    assertEquals("42", adhocSubProcess.getFailedJobRetryTimeCycleValue());
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Default Flow", adhocSubProcess.getDefaultFlow());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    MultiInstanceLoopCharacteristics loopCharacteristics = adhocSubProcess.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(adhocSubProcess.isExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(adhocSubProcess.getDataInputAssociations().isEmpty());
    assertTrue(adhocSubProcess.getDataOutputAssociations().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
    assertTrue(adhocSubProcess.isForCompensation());
    assertTrue(adhocSubProcess.isAsynchronous());
    assertTrue(adhocSubProcess.isNotExclusive());
    assertSame(ioSpecification2, adhocSubProcess.getIoSpecification());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues12() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    IOSpecification ioSpecification = mock(IOSpecification.class);
    IOSpecification ioSpecification2 = new IOSpecification();
    when(ioSpecification.clone()).thenReturn(ioSpecification2);
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics2 = new MultiInstanceLoopCharacteristics();
    when(multiInstanceLoopCharacteristics.clone()).thenReturn(multiInstanceLoopCharacteristics2);
    BusinessRuleTask otherActivity = mock(BusinessRuleTask.class);
    when(otherActivity.isForCompensation()).thenReturn(true);
    when(otherActivity.isAsynchronous()).thenReturn(true);
    when(otherActivity.isNotExclusive()).thenReturn(true);
    when(otherActivity.getDefaultFlow()).thenReturn("Default Flow");
    when(otherActivity.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherActivity.getId()).thenReturn("42");
    when(otherActivity.getDocumentation()).thenReturn("Documentation");
    when(otherActivity.getName()).thenReturn("Name");
    when(otherActivity.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherActivity.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(otherActivity.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(otherActivity.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherActivity.getAttributes()).thenReturn(new HashMap<>());
    when(otherActivity.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherActivity.getIoSpecification()).thenReturn(ioSpecification);
    when(otherActivity.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);

    // Act
    adhocSubProcess.setValues(otherActivity);

    // Assert
    verify(otherActivity).getBoundaryEvents();
    verify(otherActivity, atLeast(1)).getDataInputAssociations();
    verify(otherActivity, atLeast(1)).getDataOutputAssociations();
    verify(otherActivity).getDefaultFlow();
    verify(otherActivity).getFailedJobRetryTimeCycleValue();
    verify(otherActivity, atLeast(1)).getIoSpecification();
    verify(otherActivity, atLeast(1)).getLoopCharacteristics();
    verify(otherActivity).isForCompensation();
    verify(otherActivity, atLeast(1)).getAttributes();
    verify(otherActivity, atLeast(1)).getExtensionElements();
    verify(otherActivity).getId();
    verify(otherActivity).getDocumentation();
    verify(otherActivity, atLeast(1)).getExecutionListeners();
    verify(otherActivity).getName();
    verify(otherActivity).isAsynchronous();
    verify(otherActivity).isNotExclusive();
    verify(ioSpecification).clone();
    verify(multiInstanceLoopCharacteristics).clone();
    assertEquals("42", adhocSubProcess.getFailedJobRetryTimeCycleValue());
    assertEquals("42", adhocSubProcess.getId());
    assertEquals("Default Flow", adhocSubProcess.getDefaultFlow());
    assertEquals("Documentation", adhocSubProcess.getDocumentation());
    assertEquals("Name", adhocSubProcess.getName());
    assertFalse(adhocSubProcess.isExclusive());
    assertTrue(adhocSubProcess.getDataInputAssociations().isEmpty());
    assertTrue(adhocSubProcess.getDataOutputAssociations().isEmpty());
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
    assertTrue(adhocSubProcess.isForCompensation());
    assertTrue(adhocSubProcess.isAsynchronous());
    assertTrue(adhocSubProcess.isNotExclusive());
    assertSame(ioSpecification2, adhocSubProcess.getIoSpecification());
    assertSame(multiInstanceLoopCharacteristics2, adhocSubProcess.getLoopCharacteristics());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues13() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess otherActivity = new AdhocSubProcess();
    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    otherActivity.setLoopCharacteristics(loopCharacteristics);
    otherActivity.setIoSpecification(null);
    otherActivity.setDataInputAssociations(null);
    otherActivity.setDataOutputAssociations(null);
    otherActivity.setBoundaryEvents(boundaryEvents);

    // Act
    adhocSubProcess.setValues((Activity) otherActivity);

    // Assert
    assertNull(otherActivity.getDefaultFlow());
    assertNull(otherActivity.getFailedJobRetryTimeCycleValue());
    assertNull(otherActivity.getId());
    assertNull(otherActivity.getDocumentation());
    assertNull(otherActivity.getName());
    assertNull(otherActivity.getDataInputAssociations());
    assertNull(otherActivity.getDataOutputAssociations());
    assertNull(otherActivity.getIoSpecification());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertTrue(otherActivity.isExclusive());
    assertSame(loopCharacteristics, otherActivity.getLoopCharacteristics());
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues14() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(dataAssociation);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess otherActivity = new AdhocSubProcess();
    otherActivity.setLoopCharacteristics(null);
    otherActivity.setIoSpecification(null);
    otherActivity.setDataInputAssociations(null);
    otherActivity.setDataOutputAssociations(dataOutputAssociations);
    otherActivity.setBoundaryEvents(boundaryEvents);

    // Act
    adhocSubProcess.setValues((Activity) otherActivity);

    // Assert
    verify(dataAssociation).clone();
    assertNull(otherActivity.getDefaultFlow());
    assertNull(otherActivity.getFailedJobRetryTimeCycleValue());
    assertNull(otherActivity.getId());
    assertNull(otherActivity.getDocumentation());
    assertNull(otherActivity.getName());
    assertNull(otherActivity.getDataInputAssociations());
    assertNull(otherActivity.getIoSpecification());
    assertNull(otherActivity.getLoopCharacteristics());
    List<DataAssociation> dataOutputAssociations2 = otherActivity.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
    assertSame(dataOutputAssociations, dataOutputAssociations2);
  }

  /**
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValues15() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AdhocSubProcess otherActivity = new AdhocSubProcess();
    otherActivity.setLoopCharacteristics(null);
    otherActivity.setIoSpecification(null);
    otherActivity.setDataInputAssociations(dataInputAssociations);
    otherActivity.setDataOutputAssociations(null);
    otherActivity.setBoundaryEvents(boundaryEvents);

    // Act
    adhocSubProcess.setValues((Activity) otherActivity);

    // Assert
    verify(dataAssociation).clone();
    assertNull(otherActivity.getDefaultFlow());
    assertNull(otherActivity.getFailedJobRetryTimeCycleValue());
    assertNull(otherActivity.getId());
    assertNull(otherActivity.getDocumentation());
    assertNull(otherActivity.getName());
    assertNull(otherActivity.getDataOutputAssociations());
    assertNull(otherActivity.getIoSpecification());
    assertNull(otherActivity.getLoopCharacteristics());
    List<DataAssociation> dataInputAssociations2 = otherActivity.getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
    assertSame(dataInputAssociations, dataInputAssociations2);
  }
}
