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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class CallActivityDiffblueTest {
  /**
   * Test {@link CallActivity#clone()}.
   *
   * <ul>
   *   <li>Given {@link CallActivity} (default constructor).
   *   <li>Then return IoSpecification is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_givenCallActivity_thenReturnIoSpecificationIsNull() {
    // Arrange and Act
    CallActivity actualCloneResult = new CallActivity().clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
  }

  /**
   * Test {@link CallActivity#clone()}.
   *
   * <ul>
   *   <li>Given {@link IOSpecification} (default constructor) DataOutputs is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_givenIOSpecificationDataOutputsIsNull() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(null);

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    callActivity.setIoSpecification(ioSpecification);
    callActivity.setDataInputAssociations(dataInputAssociations);
    callActivity.setDataOutputAssociations(dataOutputAssociations);
    callActivity.setBoundaryEvents(boundaryEvents);

    // Act
    CallActivity actualCloneResult = callActivity.clone();

    // Assert
    assertEquals(1, actualCloneResult.getDataInputAssociations().size());
    assertEquals(1, actualCloneResult.getDataOutputAssociations().size());
    IOSpecification ioSpecification2 = actualCloneResult.getIoSpecification();
    assertEquals(1, ioSpecification2.getDataInputs().size());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
  }

  /**
   * Test {@link CallActivity#clone()}.
   *
   * <ul>
   *   <li>Then return Attributes size is one.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    CallActivity callActivity = new CallActivity();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    callActivity.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = callActivity.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link CallActivity#clone()}.
   *
   * <ul>
   *   <li>Then return Attributes size is two.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    CallActivity callActivity = new CallActivity();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    callActivity.addAttribute(attribute);
    callActivity.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = callActivity.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link CallActivity#clone()}.
   *
   * <ul>
   *   <li>Then return DataInputAssociations Empty.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnDataInputAssociationsEmpty() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    callActivity.setIoSpecification(ioSpecification);
    callActivity.setDataInputAssociations(null);
    callActivity.setDataOutputAssociations(dataOutputAssociations);
    callActivity.setBoundaryEvents(boundaryEvents);

    // Act
    CallActivity actualCloneResult = callActivity.clone();

    // Assert
    List<DataSpec> dataOutputs2 = actualCloneResult.getIoSpecification().getDataOutputs();
    assertEquals(1, dataOutputs2.size());
    DataSpec getResult = dataOutputs2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getItemSubjectRef());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(getResult.isCollection());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CallActivity#clone()}.
   *
   * <ul>
   *   <li>Then return DataInputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(new ArrayList<>());

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    callActivity.setIoSpecification(ioSpecification);
    callActivity.setDataInputAssociations(dataInputAssociations);
    callActivity.setDataOutputAssociations(dataOutputAssociations);
    callActivity.setBoundaryEvents(boundaryEvents);

    // Act
    CallActivity actualCloneResult = callActivity.clone();

    // Assert
    assertEquals(1, actualCloneResult.getDataInputAssociations().size());
    assertEquals(1, actualCloneResult.getDataOutputAssociations().size());
    IOSpecification ioSpecification2 = actualCloneResult.getIoSpecification();
    assertEquals(1, ioSpecification2.getDataInputs().size());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
  }

  /**
   * Test {@link CallActivity#clone()}.
   *
   * <ul>
   *   <li>Then return DataOutputAssociations Empty.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnDataOutputAssociationsEmpty() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    callActivity.setIoSpecification(ioSpecification);
    callActivity.setDataInputAssociations(dataInputAssociations);
    callActivity.setDataOutputAssociations(null);
    callActivity.setBoundaryEvents(boundaryEvents);

    // Act
    CallActivity actualCloneResult = callActivity.clone();

    // Assert
    List<DataSpec> dataOutputs2 = actualCloneResult.getIoSpecification().getDataOutputs();
    assertEquals(1, dataOutputs2.size());
    DataSpec getResult = dataOutputs2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getItemSubjectRef());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(getResult.isCollection());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CallActivity#clone()}.
   *
   * <ul>
   *   <li>Then return IoSpecification DataInputs Empty.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnIoSpecificationDataInputsEmpty() {
    // Arrange
    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(new ArrayList<>());
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    callActivity.setIoSpecification(ioSpecification);
    callActivity.setDataInputAssociations(dataInputAssociations);
    callActivity.setDataOutputAssociations(dataOutputAssociations);
    callActivity.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    IOSpecification ioSpecification2 = callActivity.clone().getIoSpecification();
    List<DataSpec> dataOutputs2 = ioSpecification2.getDataOutputs();
    assertEquals(1, dataOutputs2.size());
    DataSpec getResult = dataOutputs2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getItemSubjectRef());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(getResult.isCollection());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CallActivity#clone()}.
   *
   * <ul>
   *   <li>Then return IoSpecification DataOutputs size is one.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"CallActivity CallActivity.clone()"})
  public void testClone_thenReturnIoSpecificationDataOutputsSizeIsOne() {
    // Arrange
    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity callActivity = new CallActivity();
    callActivity.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    callActivity.setIoSpecification(ioSpecification);
    callActivity.setDataInputAssociations(dataInputAssociations);
    callActivity.setDataOutputAssociations(dataOutputAssociations);
    callActivity.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataSpec> dataOutputs2 = callActivity.clone().getIoSpecification().getDataOutputs();
    assertEquals(1, dataOutputs2.size());
    DataSpec getResult = dataOutputs2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getItemSubjectRef());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(getResult.isCollection());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CallActivity#setValues(CallActivity)} with {@code CallActivity}.
   *
   * <ul>
   *   <li>Given {@link DataSpec} {@link DataSpec#clone()} return {@link DataSpec} (default
   *       constructor).
   *   <li>Then calls {@link DataSpec#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#setValues(CallActivity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CallActivity.setValues(CallActivity)"})
  public void testSetValuesWithCallActivity_givenDataSpecCloneReturnDataSpec_thenCallsClone() {
    // Arrange
    CallActivity callActivity = new CallActivity();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity otherElement = new CallActivity();
    otherElement.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    callActivity.setValues(otherElement);

    // Assert
    verify(dataSpec).clone();
  }

  /**
   * Test {@link CallActivity#setValues(CallActivity)} with {@code CallActivity}.
   *
   * <ul>
   *   <li>Given {@link DataSpec} {@link DataSpec#clone()} return {@link DataSpec} (default
   *       constructor).
   *   <li>Then calls {@link DataSpec#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#setValues(CallActivity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CallActivity.setValues(CallActivity)"})
  public void testSetValuesWithCallActivity_givenDataSpecCloneReturnDataSpec_thenCallsClone2() {
    // Arrange
    CallActivity callActivity = new CallActivity();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    DataSpec dataSpec2 = mock(DataSpec.class);
    when(dataSpec2.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity otherElement = new CallActivity();
    otherElement.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    callActivity.setValues(otherElement);

    // Assert
    verify(dataSpec).clone();
    verify(dataSpec2).clone();
  }

  /**
   * Test {@link CallActivity#setValues(CallActivity)} with {@code CallActivity}.
   *
   * <ul>
   *   <li>Then calls {@link DataAssociation#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#setValues(CallActivity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CallActivity.setValues(CallActivity)"})
  public void testSetValuesWithCallActivity_thenCallsClone() {
    // Arrange
    CallActivity callActivity = new CallActivity();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    DataSpec dataSpec2 = mock(DataSpec.class);
    when(dataSpec2.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity otherElement = new CallActivity();
    otherElement.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    callActivity.setValues(otherElement);

    // Assert
    verify(dataAssociation).clone();
    verify(dataSpec).clone();
    verify(dataSpec2).clone();
  }

  /**
   * Test {@link CallActivity#setValues(CallActivity)} with {@code CallActivity}.
   *
   * <ul>
   *   <li>Then calls {@link DataAssociation#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#setValues(CallActivity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CallActivity.setValues(CallActivity)"})
  public void testSetValuesWithCallActivity_thenCallsClone2() {
    // Arrange
    CallActivity callActivity = new CallActivity();

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    DataSpec dataSpec2 = mock(DataSpec.class);
    when(dataSpec2.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    when(dataAssociation2.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(dataAssociation2);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity otherElement = new CallActivity();
    otherElement.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    callActivity.setValues(otherElement);

    // Assert
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(dataSpec).clone();
    verify(dataSpec2).clone();
  }

  /**
   * Test {@link CallActivity#setValues(CallActivity)} with {@code CallActivity}.
   *
   * <ul>
   *   <li>Then calls {@link MultiInstanceLoopCharacteristics#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#setValues(CallActivity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CallActivity.setValues(CallActivity)"})
  public void testSetValuesWithCallActivity_thenCallsClone3() {
    // Arrange
    CallActivity callActivity = new CallActivity();

    MultiInstanceLoopCharacteristics loopCharacteristics =
        mock(MultiInstanceLoopCharacteristics.class);
    when(loopCharacteristics.clone()).thenReturn(new MultiInstanceLoopCharacteristics());

    DataSpec dataSpec = mock(DataSpec.class);
    when(dataSpec.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(dataSpec);

    DataSpec dataSpec2 = mock(DataSpec.class);
    when(dataSpec2.clone()).thenReturn(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(dataSpec2);

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    when(dataAssociation2.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(dataAssociation2);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity otherElement = new CallActivity();
    otherElement.setLoopCharacteristics(loopCharacteristics);
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    callActivity.setValues(otherElement);

    // Assert
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(dataSpec).clone();
    verify(dataSpec2).clone();
    verify(loopCharacteristics).clone();
  }

  /**
   * Test {@link CallActivity#setValues(CallActivity)} with {@code CallActivity}.
   *
   * <ul>
   *   <li>Then calls {@link IOSpecification#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivity#setValues(CallActivity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CallActivity.setValues(CallActivity)"})
  public void testSetValuesWithCallActivity_thenCallsClone4() {
    // Arrange
    CallActivity callActivity = new CallActivity();

    MultiInstanceLoopCharacteristics loopCharacteristics =
        mock(MultiInstanceLoopCharacteristics.class);
    when(loopCharacteristics.clone()).thenReturn(new MultiInstanceLoopCharacteristics());

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(mock(DataSpec.class));

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(mock(DataSpec.class));

    IOSpecification ioSpecification = mock(IOSpecification.class);
    when(ioSpecification.clone()).thenReturn(new IOSpecification());
    doNothing().when(ioSpecification).setDataInputs(Mockito.<List<DataSpec>>any());
    doNothing().when(ioSpecification).setDataOutputs(Mockito.<List<DataSpec>>any());
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    when(dataAssociation2.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(dataAssociation2);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    CallActivity otherElement = new CallActivity();
    otherElement.setLoopCharacteristics(loopCharacteristics);
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    callActivity.setValues(otherElement);

    // Assert
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(ioSpecification).clone();
    verify(ioSpecification).setDataInputs(isA(List.class));
    verify(ioSpecification).setDataOutputs(isA(List.class));
    verify(loopCharacteristics).clone();
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link CallActivity}
   *   <li>{@link CallActivity#setBusinessKey(String)}
   *   <li>{@link CallActivity#setCalledElement(String)}
   *   <li>{@link CallActivity#setInParameters(List)}
   *   <li>{@link CallActivity#setInheritBusinessKey(boolean)}
   *   <li>{@link CallActivity#setInheritVariables(boolean)}
   *   <li>{@link CallActivity#setOutParameters(List)}
   *   <li>{@link CallActivity#getBusinessKey()}
   *   <li>{@link CallActivity#getCalledElement()}
   *   <li>{@link CallActivity#getInParameters()}
   *   <li>{@link CallActivity#getOutParameters()}
   *   <li>{@link CallActivity#isInheritBusinessKey()}
   *   <li>{@link CallActivity#isInheritVariables()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CallActivity.<init>()",
    "String CallActivity.getBusinessKey()",
    "String CallActivity.getCalledElement()",
    "List CallActivity.getInParameters()",
    "List CallActivity.getOutParameters()",
    "boolean CallActivity.isInheritBusinessKey()",
    "boolean CallActivity.isInheritVariables()",
    "void CallActivity.setBusinessKey(String)",
    "void CallActivity.setCalledElement(String)",
    "void CallActivity.setInParameters(List)",
    "void CallActivity.setInheritBusinessKey(boolean)",
    "void CallActivity.setInheritVariables(boolean)",
    "void CallActivity.setOutParameters(List)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    CallActivity actualCallActivity = new CallActivity();
    actualCallActivity.setBusinessKey("Business Key");
    actualCallActivity.setCalledElement("Called Element");
    ArrayList<IOParameter> inParameters = new ArrayList<>();
    actualCallActivity.setInParameters(inParameters);
    actualCallActivity.setInheritBusinessKey(true);
    actualCallActivity.setInheritVariables(true);
    ArrayList<IOParameter> outParameters = new ArrayList<>();
    actualCallActivity.setOutParameters(outParameters);
    String actualBusinessKey = actualCallActivity.getBusinessKey();
    String actualCalledElement = actualCallActivity.getCalledElement();
    List<IOParameter> actualInParameters = actualCallActivity.getInParameters();
    List<IOParameter> actualOutParameters = actualCallActivity.getOutParameters();
    boolean actualIsInheritBusinessKeyResult = actualCallActivity.isInheritBusinessKey();
    boolean actualIsInheritVariablesResult = actualCallActivity.isInheritVariables();

    // Assert
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("Called Element", actualCalledElement);
    assertNull(actualCallActivity.getBehavior());
    assertNull(actualCallActivity.getDefaultFlow());
    assertNull(actualCallActivity.getFailedJobRetryTimeCycleValue());
    assertNull(actualCallActivity.getId());
    assertNull(actualCallActivity.getDocumentation());
    assertNull(actualCallActivity.getName());
    assertNull(actualCallActivity.getParentContainer());
    assertNull(actualCallActivity.getIoSpecification());
    assertNull(actualCallActivity.getLoopCharacteristics());
    assertEquals(0, actualCallActivity.getXmlColumnNumber());
    assertEquals(0, actualCallActivity.getXmlRowNumber());
    assertFalse(actualCallActivity.isForCompensation());
    assertFalse(actualCallActivity.isAsynchronous());
    assertFalse(actualCallActivity.isNotExclusive());
    assertTrue(actualCallActivity.getBoundaryEvents().isEmpty());
    assertTrue(actualCallActivity.getDataInputAssociations().isEmpty());
    assertTrue(actualCallActivity.getDataOutputAssociations().isEmpty());
    assertTrue(actualCallActivity.getMapExceptions().isEmpty());
    assertTrue(actualInParameters.isEmpty());
    assertTrue(actualOutParameters.isEmpty());
    assertTrue(actualCallActivity.getExecutionListeners().isEmpty());
    assertTrue(actualCallActivity.getIncomingFlows().isEmpty());
    assertTrue(actualCallActivity.getOutgoingFlows().isEmpty());
    assertTrue(actualCallActivity.getAttributes().isEmpty());
    assertTrue(actualCallActivity.getExtensionElements().isEmpty());
    assertTrue(actualIsInheritBusinessKeyResult);
    assertTrue(actualIsInheritVariablesResult);
    assertSame(inParameters, actualInParameters);
    assertSame(outParameters, actualOutParameters);
  }
}
