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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ScriptTaskDiffblueTest {
  /**
   * Test {@link ScriptTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link ScriptTask} (default constructor) DataInputAssociations is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ScriptTask ScriptTask.clone()"})
  public void testClone_givenScriptTaskDataInputAssociationsIsNull() {
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

    ScriptTask scriptTask = new ScriptTask();
    scriptTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    scriptTask.setIoSpecification(ioSpecification);
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
   *
   * <ul>
   *   <li>Given {@link ScriptTask} (default constructor) DataOutputAssociations is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ScriptTask ScriptTask.clone()"})
  public void testClone_givenScriptTaskDataOutputAssociationsIsNull() {
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

    ScriptTask scriptTask = new ScriptTask();
    scriptTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    scriptTask.setIoSpecification(ioSpecification);
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
   *
   * <ul>
   *   <li>Given {@link ScriptTask} (default constructor) ForCompensation is {@code true}.
   *   <li>Then return ForCompensation.
   * </ul>
   *
   * <p>Method under test: {@link ScriptTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ScriptTask ScriptTask.clone()"})
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
    assertTrue(actualCloneResult.isForCompensation());
  }

  /**
   * Test {@link ScriptTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link ScriptTask} (default constructor).
   *   <li>Then return IoSpecification is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ScriptTask ScriptTask.clone()"})
  public void testClone_givenScriptTask_thenReturnIoSpecificationIsNull() {
    // Arrange and Act
    ScriptTask actualCloneResult = new ScriptTask().clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
  }

  /**
   * Test {@link ScriptTask#clone()}.
   *
   * <ul>
   *   <li>Then return DataInputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link ScriptTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ScriptTask ScriptTask.clone()"})
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
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

    ScriptTask scriptTask = new ScriptTask();
    scriptTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    scriptTask.setIoSpecification(ioSpecification);
    scriptTask.setDataInputAssociations(dataInputAssociations);
    scriptTask.setDataOutputAssociations(dataOutputAssociations);
    scriptTask.setBoundaryEvents(boundaryEvents);

    // Act
    ScriptTask actualCloneResult = scriptTask.clone();

    // Assert
    List<DataAssociation> dataInputAssociations2 = actualCloneResult.getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    DataAssociation getResult = dataInputAssociations2.get(0);
    assertNull(getResult.getId());
    List<DataAssociation> dataOutputAssociations2 = actualCloneResult.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    DataAssociation getResult2 = dataOutputAssociations2.get(0);
    assertNull(getResult2.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult2.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult2.getTargetRef());
    assertNull(getResult.getTransformation());
    assertNull(getResult2.getTransformation());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(getResult2.getAssignments().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ScriptTask.<init>()",
    "String ScriptTask.getResultVariable()",
    "String ScriptTask.getScript()",
    "String ScriptTask.getScriptFormat()",
    "boolean ScriptTask.isAutoStoreVariables()",
    "void ScriptTask.setAutoStoreVariables(boolean)",
    "void ScriptTask.setResultVariable(String)",
    "void ScriptTask.setScript(String)",
    "void ScriptTask.setScriptFormat(String)"
  })
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

    // Assert
    assertEquals("Result Variable", actualResultVariable);
    assertEquals("Script Format", actualScriptFormat);
    assertEquals("Script", actualScript);
    assertNull(actualScriptTask.getBehavior());
    assertNull(actualScriptTask.getDefaultFlow());
    assertNull(actualScriptTask.getFailedJobRetryTimeCycleValue());
    assertNull(actualScriptTask.getId());
    assertNull(actualScriptTask.getDocumentation());
    assertNull(actualScriptTask.getName());
    assertNull(actualScriptTask.getParentContainer());
    assertNull(actualScriptTask.getIoSpecification());
    assertNull(actualScriptTask.getLoopCharacteristics());
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
