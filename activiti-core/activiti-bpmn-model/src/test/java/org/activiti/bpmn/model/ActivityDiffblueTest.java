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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivityDiffblueTest {
  /**
   * Test {@link Activity#getFailedJobRetryTimeCycleValue()}.
   *
   * <p>Method under test: {@link Activity#getFailedJobRetryTimeCycleValue()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Activity.getFailedJobRetryTimeCycleValue()"})
  public void testGetFailedJobRetryTimeCycleValue() {
    // Arrange, Act and Assert
    assertNull(new AdhocSubProcess().getFailedJobRetryTimeCycleValue());
  }

  /**
   * Test {@link Activity#setFailedJobRetryTimeCycleValue(String)}.
   *
   * <p>Method under test: {@link Activity#setFailedJobRetryTimeCycleValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setFailedJobRetryTimeCycleValue(String)"})
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
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) ForCompensation is {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link Activity#isForCompensation()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Activity.isForCompensation()"})
  public void testIsForCompensation_givenAdhocSubProcessForCompensationIsTrue_thenReturnTrue() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setForCompensation(true);

    // Act and Assert
    assertTrue(adhocSubProcess.isForCompensation());
  }

  /**
   * Test {@link Activity#isForCompensation()}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link Activity#isForCompensation()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Activity.isForCompensation()"})
  public void testIsForCompensation_givenAdhocSubProcess_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new AdhocSubProcess().isForCompensation());
  }

  /**
   * Test {@link Activity#setForCompensation(boolean)}.
   *
   * <p>Method under test: {@link Activity#setForCompensation(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setForCompensation(boolean)"})
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
   *
   * <p>Method under test: {@link Activity#getBoundaryEvents()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Activity.getBoundaryEvents()"})
  public void testGetBoundaryEvents() {
    // Arrange, Act and Assert
    assertTrue(new AdhocSubProcess().getBoundaryEvents().isEmpty());
  }

  /**
   * Test {@link Activity#setBoundaryEvents(List)}.
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).
   *   <li>When {@link ArrayList#ArrayList()} add {@link BoundaryEvent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Activity#setBoundaryEvents(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setBoundaryEvents(List)"})
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
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).
   *   <li>When {@link ArrayList#ArrayList()} add {@link BoundaryEvent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Activity#setBoundaryEvents(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setBoundaryEvents(List)"})
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
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Activity#setBoundaryEvents(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setBoundaryEvents(List)"})
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
   *
   * <p>Method under test: {@link Activity#getDefaultFlow()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Activity.getDefaultFlow()"})
  public void testGetDefaultFlow() {
    // Arrange, Act and Assert
    assertNull(new AdhocSubProcess().getDefaultFlow());
  }

  /**
   * Test {@link Activity#setDefaultFlow(String)}.
   *
   * <p>Method under test: {@link Activity#setDefaultFlow(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setDefaultFlow(String)"})
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
   *
   * <p>Method under test: {@link Activity#getLoopCharacteristics()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"MultiInstanceLoopCharacteristics Activity.getLoopCharacteristics()"})
  public void testGetLoopCharacteristics() {
    // Arrange, Act and Assert
    assertNull(new AdhocSubProcess().getLoopCharacteristics());
  }

  /**
   * Test {@link Activity#setLoopCharacteristics(MultiInstanceLoopCharacteristics)}.
   *
   * <p>Method under test: {@link Activity#setLoopCharacteristics(MultiInstanceLoopCharacteristics)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setLoopCharacteristics(MultiInstanceLoopCharacteristics)"})
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
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link Activity#hasMultiInstanceLoopCharacteristics()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Activity.hasMultiInstanceLoopCharacteristics()"})
  public void testHasMultiInstanceLoopCharacteristics_givenAdhocSubProcess_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new AdhocSubProcess().hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Test {@link Activity#hasMultiInstanceLoopCharacteristics()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link Activity#hasMultiInstanceLoopCharacteristics()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Activity.hasMultiInstanceLoopCharacteristics()"})
  public void testHasMultiInstanceLoopCharacteristics_thenReturnTrue() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());

    // Act and Assert
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Test {@link Activity#getIoSpecification()}.
   *
   * <p>Method under test: {@link Activity#getIoSpecification()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"IOSpecification Activity.getIoSpecification()"})
  public void testGetIoSpecification() {
    // Arrange, Act and Assert
    assertNull(new AdhocSubProcess().getIoSpecification());
  }

  /**
   * Test {@link Activity#setIoSpecification(IOSpecification)}.
   *
   * <p>Method under test: {@link Activity#setIoSpecification(IOSpecification)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setIoSpecification(IOSpecification)"})
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
   *
   * <p>Method under test: {@link Activity#getDataInputAssociations()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Activity.getDataInputAssociations()"})
  public void testGetDataInputAssociations() {
    // Arrange, Act and Assert
    assertTrue(new AdhocSubProcess().getDataInputAssociations().isEmpty());
  }

  /**
   * Test {@link Activity#setDataInputAssociations(List)}.
   *
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Activity#setDataInputAssociations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setDataInputAssociations(List)"})
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
   *
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Activity#setDataInputAssociations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setDataInputAssociations(List)"})
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
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Activity#setDataInputAssociations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setDataInputAssociations(List)"})
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
   *
   * <p>Method under test: {@link Activity#getDataOutputAssociations()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Activity.getDataOutputAssociations()"})
  public void testGetDataOutputAssociations() {
    // Arrange, Act and Assert
    assertTrue(new AdhocSubProcess().getDataOutputAssociations().isEmpty());
  }

  /**
   * Test {@link Activity#setDataOutputAssociations(List)}.
   *
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Activity#setDataOutputAssociations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setDataOutputAssociations(List)"})
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
   *
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Activity#setDataOutputAssociations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setDataOutputAssociations(List)"})
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
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Activity#setDataOutputAssociations(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setDataOutputAssociations(List)"})
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
   *
   * <p>Method under test: {@link Activity#getMapExceptions()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Activity.getMapExceptions()"})
  public void testGetMapExceptions() {
    // Arrange, Act and Assert
    assertTrue(new AdhocSubProcess().getMapExceptions().isEmpty());
  }

  /**
   * Test {@link Activity#setMapExceptions(List)}.
   *
   * <p>Method under test: {@link Activity#setMapExceptions(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setMapExceptions(List)"})
  public void testSetMapExceptions() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry =
        new MapExceptionEntry("An error occurred", "Class Name", true);
    mapExceptions.add(mapExceptionEntry);

    // Act
    adhocSubProcess.setMapExceptions(mapExceptions);

    // Assert
    assertSame(mapExceptions, adhocSubProcess.getMapExceptions());
  }

  /**
   * Test {@link Activity#setMapExceptions(List)}.
   *
   * <p>Method under test: {@link Activity#setMapExceptions(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setMapExceptions(List)"})
  public void testSetMapExceptions2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<MapExceptionEntry> mapExceptions = new ArrayList<>();
    MapExceptionEntry mapExceptionEntry =
        new MapExceptionEntry("An error occurred", "Class Name", true);
    mapExceptions.add(mapExceptionEntry);
    MapExceptionEntry mapExceptionEntry2 =
        new MapExceptionEntry("An error occurred", "Class Name", true);
    mapExceptions.add(mapExceptionEntry2);

    // Act
    adhocSubProcess.setMapExceptions(mapExceptions);

    // Assert
    assertSame(mapExceptions, adhocSubProcess.getMapExceptions());
  }

  /**
   * Test {@link Activity#setMapExceptions(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Activity#setMapExceptions(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setMapExceptions(List)"})
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
   *
   * <p>Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setValues(Activity)"})
  public void testSetValuesWithActivity() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    DataAssociation dataAssociation2 = new DataAssociation();
    when(dataAssociation.clone()).thenReturn(dataAssociation2);

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(new DataAssociation());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    BusinessRuleTask otherActivity = mock(BusinessRuleTask.class);
    when(otherActivity.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherActivity.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherActivity.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherActivity.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherActivity.getIoSpecification()).thenReturn(null);
    when(otherActivity.getLoopCharacteristics()).thenReturn(null);
    when(otherActivity.isForCompensation()).thenReturn(true);
    when(otherActivity.isAsynchronous()).thenReturn(true);
    when(otherActivity.isNotExclusive()).thenReturn(true);
    when(otherActivity.getDefaultFlow()).thenReturn("Default Flow");
    when(otherActivity.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherActivity.getId()).thenReturn("42");
    when(otherActivity.getDocumentation()).thenReturn("Documentation");
    when(otherActivity.getName()).thenReturn("Name");
    when(otherActivity.getAttributes()).thenReturn(new HashMap<>());
    when(otherActivity.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherActivity);

    // Assert
    verify(otherActivity).getBoundaryEvents();
    verify(otherActivity, atLeast(1)).getDataInputAssociations();
    verify(otherActivity, atLeast(1)).getDataOutputAssociations();
    verify(otherActivity).getDefaultFlow();
    verify(otherActivity).getFailedJobRetryTimeCycleValue();
    verify(otherActivity).getIoSpecification();
    verify(otherActivity).getLoopCharacteristics();
    verify(otherActivity).isForCompensation();
    verify(otherActivity, atLeast(1)).getAttributes();
    verify(otherActivity, atLeast(1)).getExtensionElements();
    verify(otherActivity).getId();
    verify(dataAssociation).clone();
    verify(otherActivity).getDocumentation();
    verify(otherActivity, atLeast(1)).getExecutionListeners();
    verify(otherActivity).getName();
    verify(otherActivity).isAsynchronous();
    verify(otherActivity).isNotExclusive();
    List<DataAssociation> dataInputAssociations = adhocSubProcess.getDataInputAssociations();
    assertEquals(1, dataInputAssociations.size());
    assertEquals(1, adhocSubProcess.getDataOutputAssociations().size());
    assertEquals(1, adhocSubProcess.getExecutionListeners().size());
    assertSame(dataAssociation2, dataInputAssociations.get(0));
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   *
   * <p>Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setValues(Activity)"})
  public void testSetValuesWithActivity2() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    DataAssociation dataAssociation2 = new DataAssociation();
    when(dataAssociation.clone()).thenReturn(dataAssociation2);

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    DataAssociation dataAssociation3 = mock(DataAssociation.class);
    DataAssociation dataAssociation4 = new DataAssociation();
    when(dataAssociation3.clone()).thenReturn(dataAssociation4);

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(dataAssociation3);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    BusinessRuleTask otherActivity = mock(BusinessRuleTask.class);
    when(otherActivity.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherActivity.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherActivity.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherActivity.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherActivity.getIoSpecification()).thenReturn(null);
    when(otherActivity.getLoopCharacteristics()).thenReturn(null);
    when(otherActivity.isForCompensation()).thenReturn(true);
    when(otherActivity.isAsynchronous()).thenReturn(true);
    when(otherActivity.isNotExclusive()).thenReturn(true);
    when(otherActivity.getDefaultFlow()).thenReturn("Default Flow");
    when(otherActivity.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherActivity.getId()).thenReturn("42");
    when(otherActivity.getDocumentation()).thenReturn("Documentation");
    when(otherActivity.getName()).thenReturn("Name");
    when(otherActivity.getAttributes()).thenReturn(new HashMap<>());
    when(otherActivity.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherActivity);

    // Assert
    verify(otherActivity).getBoundaryEvents();
    verify(otherActivity, atLeast(1)).getDataInputAssociations();
    verify(otherActivity, atLeast(1)).getDataOutputAssociations();
    verify(otherActivity).getDefaultFlow();
    verify(otherActivity).getFailedJobRetryTimeCycleValue();
    verify(otherActivity).getIoSpecification();
    verify(otherActivity).getLoopCharacteristics();
    verify(otherActivity).isForCompensation();
    verify(otherActivity, atLeast(1)).getAttributes();
    verify(otherActivity, atLeast(1)).getExtensionElements();
    verify(otherActivity).getId();
    verify(dataAssociation).clone();
    verify(dataAssociation3).clone();
    verify(otherActivity).getDocumentation();
    verify(otherActivity, atLeast(1)).getExecutionListeners();
    verify(otherActivity).getName();
    verify(otherActivity).isAsynchronous();
    verify(otherActivity).isNotExclusive();
    List<DataAssociation> dataInputAssociations = adhocSubProcess.getDataInputAssociations();
    assertEquals(1, dataInputAssociations.size());
    List<DataAssociation> dataOutputAssociations = adhocSubProcess.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations.size());
    assertEquals(1, adhocSubProcess.getExecutionListeners().size());
    assertSame(dataAssociation2, dataInputAssociations.get(0));
    assertSame(dataAssociation4, dataOutputAssociations.get(0));
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) BoundaryEvents size is one.
   * </ul>
   *
   * <p>Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setValues(Activity)"})
  public void testSetValuesWithActivity_thenAdhocSubProcessBoundaryEventsSizeIsOne() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEventList.add(boundaryEvent);

    BusinessRuleTask otherActivity = mock(BusinessRuleTask.class);
    when(otherActivity.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherActivity.getDataInputAssociations()).thenReturn(null);
    when(otherActivity.getDataOutputAssociations()).thenReturn(null);
    when(otherActivity.getExecutionListeners()).thenReturn(null);
    when(otherActivity.getIoSpecification()).thenReturn(null);
    when(otherActivity.getLoopCharacteristics()).thenReturn(null);
    when(otherActivity.isForCompensation()).thenReturn(true);
    when(otherActivity.isAsynchronous()).thenReturn(true);
    when(otherActivity.isNotExclusive()).thenReturn(true);
    when(otherActivity.getDefaultFlow()).thenReturn("Default Flow");
    when(otherActivity.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherActivity.getId()).thenReturn("42");
    when(otherActivity.getDocumentation()).thenReturn("Documentation");
    when(otherActivity.getName()).thenReturn("Name");
    when(otherActivity.getAttributes()).thenReturn(new HashMap<>());
    when(otherActivity.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherActivity);

    // Assert
    verify(otherActivity).getBoundaryEvents();
    verify(otherActivity).getDataInputAssociations();
    verify(otherActivity).getDataOutputAssociations();
    verify(otherActivity).getDefaultFlow();
    verify(otherActivity).getFailedJobRetryTimeCycleValue();
    verify(otherActivity).getIoSpecification();
    verify(otherActivity).getLoopCharacteristics();
    verify(otherActivity).isForCompensation();
    verify(otherActivity, atLeast(1)).getAttributes();
    verify(otherActivity, atLeast(1)).getExtensionElements();
    verify(otherActivity).getId();
    verify(otherActivity).getDocumentation();
    verify(otherActivity).getExecutionListeners();
    verify(otherActivity).getName();
    verify(otherActivity).isAsynchronous();
    verify(otherActivity).isNotExclusive();
    List<BoundaryEvent> boundaryEvents = adhocSubProcess.getBoundaryEvents();
    assertEquals(1, boundaryEvents.size());
    assertSame(boundaryEvent, boundaryEvents.get(0));
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) DataInputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setValues(Activity)"})
  public void testSetValuesWithActivity_thenAdhocSubProcessDataInputAssociationsSizeIsOne() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(new DataAssociation());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    BusinessRuleTask otherActivity = mock(BusinessRuleTask.class);
    when(otherActivity.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherActivity.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherActivity.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherActivity.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherActivity.getIoSpecification()).thenReturn(null);
    when(otherActivity.getLoopCharacteristics()).thenReturn(null);
    when(otherActivity.isForCompensation()).thenReturn(true);
    when(otherActivity.isAsynchronous()).thenReturn(true);
    when(otherActivity.isNotExclusive()).thenReturn(true);
    when(otherActivity.getDefaultFlow()).thenReturn("Default Flow");
    when(otherActivity.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherActivity.getId()).thenReturn("42");
    when(otherActivity.getDocumentation()).thenReturn("Documentation");
    when(otherActivity.getName()).thenReturn("Name");
    when(otherActivity.getAttributes()).thenReturn(new HashMap<>());
    when(otherActivity.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherActivity);

    // Assert
    verify(otherActivity).getBoundaryEvents();
    verify(otherActivity, atLeast(1)).getDataInputAssociations();
    verify(otherActivity, atLeast(1)).getDataOutputAssociations();
    verify(otherActivity).getDefaultFlow();
    verify(otherActivity).getFailedJobRetryTimeCycleValue();
    verify(otherActivity).getIoSpecification();
    verify(otherActivity).getLoopCharacteristics();
    verify(otherActivity).isForCompensation();
    verify(otherActivity, atLeast(1)).getAttributes();
    verify(otherActivity, atLeast(1)).getExtensionElements();
    verify(otherActivity).getId();
    verify(otherActivity).getDocumentation();
    verify(otherActivity, atLeast(1)).getExecutionListeners();
    verify(otherActivity).getName();
    verify(otherActivity).isAsynchronous();
    verify(otherActivity).isNotExclusive();
    assertEquals(1, adhocSubProcess.getDataInputAssociations().size());
    assertEquals(1, adhocSubProcess.getDataOutputAssociations().size());
    assertEquals(1, adhocSubProcess.getExecutionListeners().size());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) IoSpecification Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setValues(Activity)"})
  public void testSetValuesWithActivity_thenAdhocSubProcessIoSpecificationIdIsNull() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    when(dataAssociation2.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(dataAssociation2);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    IOSpecification ioSpecification = mock(IOSpecification.class);
    when(ioSpecification.clone()).thenReturn(new IOSpecification());

    BusinessRuleTask otherActivity = mock(BusinessRuleTask.class);
    when(otherActivity.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherActivity.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherActivity.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherActivity.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherActivity.getIoSpecification()).thenReturn(ioSpecification);
    when(otherActivity.getLoopCharacteristics()).thenReturn(null);
    when(otherActivity.isForCompensation()).thenReturn(true);
    when(otherActivity.isAsynchronous()).thenReturn(true);
    when(otherActivity.isNotExclusive()).thenReturn(true);
    when(otherActivity.getDefaultFlow()).thenReturn("Default Flow");
    when(otherActivity.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherActivity.getId()).thenReturn("42");
    when(otherActivity.getDocumentation()).thenReturn("Documentation");
    when(otherActivity.getName()).thenReturn("Name");
    when(otherActivity.getAttributes()).thenReturn(new HashMap<>());
    when(otherActivity.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    adhocSubProcess.setValues(otherActivity);

    // Assert
    verify(otherActivity).getBoundaryEvents();
    verify(otherActivity, atLeast(1)).getDataInputAssociations();
    verify(otherActivity, atLeast(1)).getDataOutputAssociations();
    verify(otherActivity).getDefaultFlow();
    verify(otherActivity).getFailedJobRetryTimeCycleValue();
    verify(otherActivity, atLeast(1)).getIoSpecification();
    verify(otherActivity).getLoopCharacteristics();
    verify(otherActivity).isForCompensation();
    verify(otherActivity, atLeast(1)).getAttributes();
    verify(otherActivity, atLeast(1)).getExtensionElements();
    verify(otherActivity).getId();
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherActivity).getDocumentation();
    verify(otherActivity, atLeast(1)).getExecutionListeners();
    verify(otherActivity).getName();
    verify(otherActivity).isAsynchronous();
    verify(otherActivity).isNotExclusive();
    verify(ioSpecification).clone();
    IOSpecification ioSpecification2 = adhocSubProcess.getIoSpecification();
    assertNull(ioSpecification2.getId());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) IoSpecification is {@link
   *       IOSpecification} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setValues(Activity)"})
  public void testSetValuesWithActivity_thenAdhocSubProcessIoSpecificationIsIOSpecification() {
    // Arrange
    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    when(dataAssociation2.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(dataAssociation2);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    IOSpecification ioSpecification = mock(IOSpecification.class);
    IOSpecification ioSpecification2 = new IOSpecification();
    when(ioSpecification.clone()).thenReturn(ioSpecification2);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        mock(MultiInstanceLoopCharacteristics.class);
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics2 =
        new MultiInstanceLoopCharacteristics();
    when(multiInstanceLoopCharacteristics.clone()).thenReturn(multiInstanceLoopCharacteristics2);

    BusinessRuleTask otherActivity = mock(BusinessRuleTask.class);
    when(otherActivity.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherActivity.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherActivity.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherActivity.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherActivity.getIoSpecification()).thenReturn(ioSpecification);
    when(otherActivity.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    when(otherActivity.isForCompensation()).thenReturn(true);
    when(otherActivity.isAsynchronous()).thenReturn(true);
    when(otherActivity.isNotExclusive()).thenReturn(true);
    when(otherActivity.getDefaultFlow()).thenReturn("Default Flow");
    when(otherActivity.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherActivity.getId()).thenReturn("42");
    when(otherActivity.getDocumentation()).thenReturn("Documentation");
    when(otherActivity.getName()).thenReturn("Name");
    when(otherActivity.getAttributes()).thenReturn(new HashMap<>());
    when(otherActivity.getExtensionElements()).thenReturn(new HashMap<>());

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
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherActivity).getDocumentation();
    verify(otherActivity, atLeast(1)).getExecutionListeners();
    verify(otherActivity).getName();
    verify(otherActivity).isAsynchronous();
    verify(otherActivity).isNotExclusive();
    verify(ioSpecification).clone();
    verify(multiInstanceLoopCharacteristics).clone();
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
    assertSame(ioSpecification2, adhocSubProcess.getIoSpecification());
    assertSame(multiInstanceLoopCharacteristics2, adhocSubProcess.getLoopCharacteristics());
  }

  /**
   * Test {@link Activity#setValues(Activity)} with {@code Activity}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) LoopCharacteristics Id is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link Activity#setValues(Activity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Activity.setValues(Activity)"})
  public void testSetValuesWithActivity_thenAdhocSubProcessLoopCharacteristicsIdIsNull() {
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
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(adhocSubProcess.hasMultiInstanceLoopCharacteristics());
  }
}
