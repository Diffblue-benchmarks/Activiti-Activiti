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

public class ScriptTaskDiffblueTest {
  /**
   * Test {@link ScriptTask#clone()}.
   * <ul>
   *   <li>Given {@link ScriptTask} (default constructor) ForCompensation is
   * {@code true}.</li>
   *   <li>Then return ForCompensation.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptTask#clone()}
   */
  @Test
  public void testClone_givenScriptTaskForCompensationIsTrue_thenReturnForCompensation() {
    // Arrange
    ScriptTask scriptTask = new ScriptTask();
    scriptTask.setForCompensation(true);

    // Act
    ScriptTask actualCloneResult = scriptTask.clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.isForCompensation());
  }

  /**
   * Test {@link ScriptTask#clone()}.
   * <ul>
   *   <li>Given {@link ScriptTask} (default constructor).</li>
   *   <li>Then return not ForCompensation.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptTask#clone()}
   */
  @Test
  public void testClone_givenScriptTask_thenReturnNotForCompensation() {
    // Arrange and Act
    ScriptTask actualCloneResult = (new ScriptTask()).clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
  }

  /**
   * Test {@link ScriptTask#clone()}.
   * <ul>
   *   <li>Then return BoundaryEvents is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptTask#clone()}
   */
  @Test
  public void testClone_thenReturnBoundaryEventsIsArrayList() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ScriptTask scriptTask = new ScriptTask();
    scriptTask.setLoopCharacteristics(null);
    scriptTask.setIoSpecification(null);
    scriptTask.setDataInputAssociations(null);
    scriptTask.setDataOutputAssociations(null);
    scriptTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertEquals(boundaryEvents, scriptTask.clone().getBoundaryEvents());
  }

  /**
   * Test {@link ScriptTask#clone()}.
   * <ul>
   *   <li>Then return DataInputAssociations size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptTask#clone()}
   */
  @Test
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ScriptTask scriptTask = new ScriptTask();
    scriptTask.setLoopCharacteristics(null);
    scriptTask.setIoSpecification(null);
    scriptTask.setDataInputAssociations(dataInputAssociations);
    scriptTask.setDataOutputAssociations(null);
    scriptTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataInputAssociations2 = scriptTask.clone().getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    DataAssociation getResult = dataInputAssociations2.get(0);
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
   * Test {@link ScriptTask#clone()}.
   * <ul>
   *   <li>Then return DataOutputAssociations size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptTask#clone()}
   */
  @Test
  public void testClone_thenReturnDataOutputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ScriptTask scriptTask = new ScriptTask();
    scriptTask.setLoopCharacteristics(null);
    scriptTask.setIoSpecification(null);
    scriptTask.setDataInputAssociations(null);
    scriptTask.setDataOutputAssociations(dataOutputAssociations);
    scriptTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataOutputAssociations2 = scriptTask.clone().getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    DataAssociation getResult = dataOutputAssociations2.get(0);
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
   * Test {@link ScriptTask#clone()}.
   * <ul>
   *   <li>Then return IoSpecification Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptTask#clone()}
   */
  @Test
  public void testClone_thenReturnIoSpecificationIdIsNull() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ScriptTask scriptTask = new ScriptTask();
    scriptTask.setLoopCharacteristics(null);
    scriptTask.setIoSpecification(new IOSpecification());
    scriptTask.setDataInputAssociations(null);
    scriptTask.setDataOutputAssociations(null);
    scriptTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    IOSpecification ioSpecification = scriptTask.clone().getIoSpecification();
    assertNull(ioSpecification.getId());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link ScriptTask#clone()}.
   * <ul>
   *   <li>Then return LoopCharacteristics Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptTask#clone()}
   */
  @Test
  public void testClone_thenReturnLoopCharacteristicsIdIsNull() {
    // Arrange
    ScriptTask scriptTask = new ScriptTask();
    scriptTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());

    // Act
    ScriptTask actualCloneResult = scriptTask.clone();

    // Assert
    MultiInstanceLoopCharacteristics loopCharacteristics = actualCloneResult.getLoopCharacteristics();
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
    assertTrue(actualCloneResult.hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Test {@link ScriptTask#setValues(ScriptTask)} with {@code ScriptTask}.
   * <ul>
   *   <li>Then calls {@link IOSpecification#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ScriptTask#setValues(ScriptTask)}
   */
  @Test
  public void testSetValuesWithScriptTask_thenCallsClone() {
    // Arrange
    ScriptTask scriptTask = new ScriptTask();
    IOSpecification ioSpecification = mock(IOSpecification.class);
    when(ioSpecification.clone()).thenReturn(new IOSpecification());
    doNothing().when(ioSpecification).setDataInputs(Mockito.<List<DataSpec>>any());
    doNothing().when(ioSpecification).setDataOutputs(Mockito.<List<DataSpec>>any());
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ScriptTask otherElement = new ScriptTask();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setLoopCharacteristics(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    scriptTask.setValues(otherElement);

    // Assert
    verify(ioSpecification).clone();
    verify(ioSpecification).setDataInputs(isNull());
    verify(ioSpecification).setDataOutputs(isNull());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ScriptTask}
   *   <li>{@link ScriptTask#setResultVariable(String)}
   *   <li>{@link ScriptTask#setScript(String)}
   *   <li>{@link ScriptTask#setScriptFormat(String)}
   *   <li>{@link ScriptTask#setAutoStoreVariables(boolean)}
   *   <li>{@link ScriptTask#getResultVariable()}
   *   <li>{@link ScriptTask#getScript()}
   *   <li>{@link ScriptTask#getScriptFormat()}
   *   <li>{@link ScriptTask#isAutoStoreVariables()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ScriptTask actualScriptTask = new ScriptTask();
    actualScriptTask.setResultVariable("Result Variable");
    actualScriptTask.setScript("Script");
    actualScriptTask.setScriptFormat("Script Format");
    actualScriptTask.setAutoStoreVariables(true);
    String actualResultVariable = actualScriptTask.getResultVariable();
    String actualScript = actualScriptTask.getScript();
    String actualScriptFormat = actualScriptTask.getScriptFormat();
    boolean actualIsAutoStoreVariablesResult = actualScriptTask.isAutoStoreVariables();

    // Assert that nothing has changed
    assertEquals("Result Variable", actualResultVariable);
    assertEquals("Script Format", actualScriptFormat);
    assertEquals("Script", actualScript);
    assertEquals(0, actualScriptTask.getXmlColumnNumber());
    assertEquals(0, actualScriptTask.getXmlRowNumber());
    assertFalse(actualScriptTask.isForCompensation());
    assertFalse(actualScriptTask.isAsynchronous());
    assertFalse(actualScriptTask.isNotExclusive());
    assertTrue(actualScriptTask.getBoundaryEvents().isEmpty());
    assertTrue(actualScriptTask.getDataInputAssociations().isEmpty());
    assertTrue(actualScriptTask.getDataOutputAssociations().isEmpty());
    assertTrue(actualScriptTask.getMapExceptions().isEmpty());
    assertTrue(actualScriptTask.getExecutionListeners().isEmpty());
    assertTrue(actualScriptTask.getIncomingFlows().isEmpty());
    assertTrue(actualScriptTask.getOutgoingFlows().isEmpty());
    assertTrue(actualScriptTask.getAttributes().isEmpty());
    assertTrue(actualScriptTask.getExtensionElements().isEmpty());
    assertTrue(actualIsAutoStoreVariablesResult);
  }
}
