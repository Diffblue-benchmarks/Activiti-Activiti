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
   * Test {@link Activity#getFailedJobRetryTimeCycleValue()}.
   * <p>
   * Method under test: {@link Activity#getFailedJobRetryTimeCycleValue()}
   */
  @Test
  public void testGetFailedJobRetryTimeCycleValue() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getFailedJobRetryTimeCycleValue());
  }

  /**
   * Test {@link Activity#setFailedJobRetryTimeCycleValue(String)}.
   * <p>
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
   * Test {@link Activity#isForCompensation()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) ForCompensation is
   * {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#isForCompensation()}
   */
  @Test
  public void testIsForCompensation_givenAdhocSubProcessForCompensationIsTrue_thenReturnTrue() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setForCompensation(true);

    // Act and Assert
    assertTrue(adhocSubProcess.isForCompensation());
  }

  /**
   * Test {@link Activity#isForCompensation()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#isForCompensation()}
   */
  @Test
  public void testIsForCompensation_givenAdhocSubProcess_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AdhocSubProcess()).isForCompensation());
  }

  /**
   * Test {@link Activity#setForCompensation(boolean)}.
   * <p>
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
   * Test {@link Activity#getBoundaryEvents()}.
   * <p>
   * Method under test: {@link Activity#getBoundaryEvents()}
   */
  @Test
  public void testGetBoundaryEvents() {
    // Arrange, Act and Assert
    assertTrue((new AdhocSubProcess()).getBoundaryEvents().isEmpty());
  }

  /**
   * Test {@link Activity#setBoundaryEvents(List)}.
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link BoundaryEvent} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setBoundaryEvents(List)}
   */
  @Test
  public void testSetBoundaryEvents_givenBoundaryEvent_whenArrayListAddBoundaryEvent() {
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
   * Test {@link Activity#setBoundaryEvents(List)}.
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link BoundaryEvent} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setBoundaryEvents(List)}
   */
  @Test
  public void testSetBoundaryEvents_givenBoundaryEvent_whenArrayListAddBoundaryEvent2() {
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
   * Test {@link Activity#setBoundaryEvents(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setBoundaryEvents(List)}
   */
  @Test
  public void testSetBoundaryEvents_whenArrayList() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();

    // Act
    adhocSubProcess.setBoundaryEvents(boundaryEvents);

    // Assert
    assertSame(boundaryEvents, adhocSubProcess.getBoundaryEvents());
  }

  /**
   * Test {@link Activity#getDefaultFlow()}.
   * <p>
   * Method under test: {@link Activity#getDefaultFlow()}
   */
  @Test
  public void testGetDefaultFlow() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getDefaultFlow());
  }

  /**
   * Test {@link Activity#setDefaultFlow(String)}.
   * <p>
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
   * Test {@link Activity#getLoopCharacteristics()}.
   * <p>
   * Method under test: {@link Activity#getLoopCharacteristics()}
   */
  @Test
  public void testGetLoopCharacteristics() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getLoopCharacteristics());
  }

  /**
   * Test
   * {@link Activity#setLoopCharacteristics(MultiInstanceLoopCharacteristics)}.
   * <p>
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
   * Test {@link Activity#hasMultiInstanceLoopCharacteristics()}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#hasMultiInstanceLoopCharacteristics()}
   */
  @Test
  public void testHasMultiInstanceLoopCharacteristics_givenAdhocSubProcess_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new AdhocSubProcess()).hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Test {@link Activity#hasMultiInstanceLoopCharacteristics()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#hasMultiInstanceLoopCharacteristics()}
   */
  @Test
  public void testHasMultiInstanceLoopCharacteristics_thenReturnTrue() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());

    // Act and Assert
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Test {@link Activity#getIoSpecification()}.
   * <p>
   * Method under test: {@link Activity#getIoSpecification()}
   */
  @Test
  public void testGetIoSpecification() {
    // Arrange, Act and Assert
    assertNull((new AdhocSubProcess()).getIoSpecification());
  }

  /**
   * Test {@link Activity#setIoSpecification(IOSpecification)}.
   * <p>
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
   * Test {@link Activity#getDataInputAssociations()}.
   * <p>
   * Method under test: {@link Activity#getDataInputAssociations()}
   */
  @Test
  public void testGetDataInputAssociations() {
    // Arrange, Act and Assert
    assertTrue((new AdhocSubProcess()).getDataInputAssociations().isEmpty());
  }

  /**
   * Test {@link Activity#setDataInputAssociations(List)}.
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setDataInputAssociations(List)}
   */
  @Test
  public void testSetDataInputAssociations_givenDataAssociation() {
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
   * Test {@link Activity#setDataInputAssociations(List)}.
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setDataInputAssociations(List)}
   */
  @Test
  public void testSetDataInputAssociations_givenDataAssociation2() {
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
   * Test {@link Activity#setDataInputAssociations(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setDataInputAssociations(List)}
   */
  @Test
  public void testSetDataInputAssociations_whenArrayList() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();

    // Act
    adhocSubProcess.setDataInputAssociations(dataInputAssociations);

    // Assert
    assertSame(dataInputAssociations, adhocSubProcess.getDataInputAssociations());
  }

  /**
   * Test {@link Activity#getDataOutputAssociations()}.
   * <p>
   * Method under test: {@link Activity#getDataOutputAssociations()}
   */
  @Test
  public void testGetDataOutputAssociations() {
    // Arrange, Act and Assert
    assertTrue((new AdhocSubProcess()).getDataOutputAssociations().isEmpty());
  }

  /**
   * Test {@link Activity#setDataOutputAssociations(List)}.
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setDataOutputAssociations(List)}
   */
  @Test
  public void testSetDataOutputAssociations_givenDataAssociation() {
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
   * Test {@link Activity#setDataOutputAssociations(List)}.
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setDataOutputAssociations(List)}
   */
  @Test
  public void testSetDataOutputAssociations_givenDataAssociation2() {
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
   * Test {@link Activity#setDataOutputAssociations(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setDataOutputAssociations(List)}
   */
  @Test
  public void testSetDataOutputAssociations_whenArrayList() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();

    // Act
    adhocSubProcess.setDataOutputAssociations(dataOutputAssociations);

    // Assert
    assertSame(dataOutputAssociations, adhocSubProcess.getDataOutputAssociations());
  }

  /**
   * Test {@link Activity#getMapExceptions()}.
   * <p>
   * Method under test: {@link Activity#getMapExceptions()}
   */
  @Test
  public void testGetMapExceptions() {
    // Arrange, Act and Assert
    assertTrue((new AdhocSubProcess()).getMapExceptions().isEmpty());
  }

  /**
   * Test {@link Activity#setMapExceptions(List)}.
   * <p>
   * Method under test: {@link Activity#setMapExceptions(List)}
   */
  @Test
  public void testSetMapExceptions() {
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
   * Test {@link Activity#setMapExceptions(List)}.
   * <p>
   * Method under test: {@link Activity#setMapExceptions(List)}
   */
  @Test
  public void testSetMapExceptions2() {
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
   * Test {@link Activity#setMapExceptions(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setMapExceptions(List)}
   */
  @Test
  public void testSetMapExceptions_whenArrayList() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();

    // Act
    adhocSubProcess.setMapExceptions(mapExceptions);

    // Assert
    assertSame(mapExceptions, adhocSubProcess.getMapExceptions());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity() {
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
    assertNull(otherActivity.getIoSpecification());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertTrue(otherActivity.isExclusive());
    assertSame(loopCharacteristics, otherActivity.getLoopCharacteristics());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DataSpec} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_givenArrayListAddDataSpec() {
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
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
    assertSame(ioSpecification, otherActivity.getIoSpecification());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>Given {@link IOSpecification} (default constructor) DataOutputs is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_givenIOSpecificationDataOutputsIsNull() {
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
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
    assertSame(ioSpecification, otherActivity.getIoSpecification());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) DataInputAssociations
   * size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_thenAdhocSubProcessDataInputAssociationsSizeIsOne() {
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
    List<DataAssociation> dataInputAssociations = adhocSubProcess.getDataInputAssociations();
    assertEquals(1, dataInputAssociations.size());
    DataAssociation getResult = dataInputAssociations.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) DataOutputAssociations
   * size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_thenAdhocSubProcessDataOutputAssociationsSizeIsOne() {
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
    List<DataAssociation> dataOutputAssociations = adhocSubProcess.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations.size());
    DataAssociation getResult = dataOutputAssociations.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) IoSpecification Id is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_thenAdhocSubProcessIoSpecificationIdIsNull() {
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
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) IoSpecification is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_thenAdhocSubProcessIoSpecificationIsNull() {
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
    assertNull(otherActivity.getIoSpecification());
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) LoopCharacteristics Id
   * is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_thenAdhocSubProcessLoopCharacteristicsIdIsNull() {
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
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
    assertTrue(adhocSubProcess.isForCompensation());
    assertTrue(adhocSubProcess.isAsynchronous());
    assertTrue(adhocSubProcess.isNotExclusive());
    assertSame(ioSpecification2, adhocSubProcess.getIoSpecification());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>Then calls {@link MultiInstanceLoopCharacteristics#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_thenCallsClone() {
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
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
    assertTrue(adhocSubProcess.isForCompensation());
    assertTrue(adhocSubProcess.isAsynchronous());
    assertTrue(adhocSubProcess.isNotExclusive());
    assertSame(ioSpecification2, adhocSubProcess.getIoSpecification());
    assertSame(multiInstanceLoopCharacteristics2, adhocSubProcess.getLoopCharacteristics());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>Then calls {@link DataAssociation#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_thenCallsClone2() {
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
    assertNull(otherActivity.getIoSpecification());
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>Then calls {@link DataAssociation#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_thenCallsClone3() {
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
    assertNull(otherActivity.getIoSpecification());
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_whenAdhocSubProcess() {
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
    assertTrue(otherActivity.isExclusive());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor) DataInputAssociations
   * is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_whenAdhocSubProcessDataInputAssociationsIsArrayList() {
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
    assertNull(otherActivity.getIoSpecification());
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor) DataOutputAssociations
   * is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_whenAdhocSubProcessDataOutputAssociationsIsArrayList() {
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
    assertNull(otherActivity.getIoSpecification());
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor) IoSpecification is
   * {@link IOSpecification} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  public void testSetValuesWithActivity_whenAdhocSubProcessIoSpecificationIsIOSpecification() {
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
    assertNull(otherActivity.getLoopCharacteristics());
    assertFalse(otherActivity.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherActivity.isForCompensation());
    assertFalse(otherActivity.isAsynchronous());
    assertFalse(otherActivity.isNotExclusive());
    assertTrue(otherActivity.isExclusive());
    assertSame(ioSpecification, otherActivity.getIoSpecification());
  }
}
