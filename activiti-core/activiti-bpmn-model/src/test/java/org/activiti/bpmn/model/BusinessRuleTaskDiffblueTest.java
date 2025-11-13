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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BusinessRuleTaskDiffblueTest {
  /**
   * Test {@link BusinessRuleTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor) DataInputAssociations is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link BusinessRuleTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BusinessRuleTask BusinessRuleTask.clone()"})
  public void testClone_givenBusinessRuleTaskDataInputAssociationsIsNull() {
    // Arrange
    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    BusinessRuleTask businessRuleTask = new BusinessRuleTask();
    businessRuleTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    businessRuleTask.setIoSpecification(new IOSpecification());
    businessRuleTask.setDataInputAssociations(null);
    businessRuleTask.setDataOutputAssociations(dataOutputAssociations);
    businessRuleTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataOutputAssociations2 =
        businessRuleTask.clone().getDataOutputAssociations();
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
   * Test {@link BusinessRuleTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor) DataOutputAssociations is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link BusinessRuleTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BusinessRuleTask BusinessRuleTask.clone()"})
  public void testClone_givenBusinessRuleTaskDataOutputAssociationsIsNull() {
    // Arrange
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    BusinessRuleTask businessRuleTask = new BusinessRuleTask();
    businessRuleTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    businessRuleTask.setIoSpecification(new IOSpecification());
    businessRuleTask.setDataInputAssociations(dataInputAssociations);
    businessRuleTask.setDataOutputAssociations(null);
    businessRuleTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataInputAssociations2 =
        businessRuleTask.clone().getDataInputAssociations();
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
   * Test {@link BusinessRuleTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor) Exclude is {@code true}.
   *   <li>Then return Exclude.
   * </ul>
   *
   * <p>Method under test: {@link BusinessRuleTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BusinessRuleTask BusinessRuleTask.clone()"})
  public void testClone_givenBusinessRuleTaskExcludeIsTrue_thenReturnExclude() {
    // Arrange
    BusinessRuleTask businessRuleTask = new BusinessRuleTask();
    businessRuleTask.setExclude(true);

    // Act
    BusinessRuleTask actualCloneResult = businessRuleTask.clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.isExclude());
  }

  /**
   * Test {@link BusinessRuleTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor).
   *   <li>Then return IoSpecification is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BusinessRuleTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BusinessRuleTask BusinessRuleTask.clone()"})
  public void testClone_givenBusinessRuleTask_thenReturnIoSpecificationIsNull() {
    // Arrange and Act
    BusinessRuleTask actualCloneResult = new BusinessRuleTask().clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
  }

  /**
   * Test {@link BusinessRuleTask#clone()}.
   *
   * <ul>
   *   <li>Then return DataInputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link BusinessRuleTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BusinessRuleTask BusinessRuleTask.clone()"})
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    BusinessRuleTask businessRuleTask = new BusinessRuleTask();
    businessRuleTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    businessRuleTask.setIoSpecification(new IOSpecification());
    businessRuleTask.setDataInputAssociations(dataInputAssociations);
    businessRuleTask.setDataOutputAssociations(dataOutputAssociations);
    businessRuleTask.setBoundaryEvents(boundaryEvents);

    // Act
    BusinessRuleTask actualCloneResult = businessRuleTask.clone();

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
   *   <li>default or parameterless constructor of {@link BusinessRuleTask}
   *   <li>{@link BusinessRuleTask#setClassName(String)}
   *   <li>{@link BusinessRuleTask#setExclude(boolean)}
   *   <li>{@link BusinessRuleTask#setInputVariables(List)}
   *   <li>{@link BusinessRuleTask#setResultVariableName(String)}
   *   <li>{@link BusinessRuleTask#setRuleNames(List)}
   *   <li>{@link BusinessRuleTask#getClassName()}
   *   <li>{@link BusinessRuleTask#getInputVariables()}
   *   <li>{@link BusinessRuleTask#getResultVariableName()}
   *   <li>{@link BusinessRuleTask#getRuleNames()}
   *   <li>{@link BusinessRuleTask#isExclude()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BusinessRuleTask.<init>()",
    "String BusinessRuleTask.getClassName()",
    "List BusinessRuleTask.getInputVariables()",
    "String BusinessRuleTask.getResultVariableName()",
    "List BusinessRuleTask.getRuleNames()",
    "boolean BusinessRuleTask.isExclude()",
    "void BusinessRuleTask.setClassName(String)",
    "void BusinessRuleTask.setExclude(boolean)",
    "void BusinessRuleTask.setInputVariables(List)",
    "void BusinessRuleTask.setResultVariableName(String)",
    "void BusinessRuleTask.setRuleNames(List)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    BusinessRuleTask actualBusinessRuleTask = new BusinessRuleTask();
    actualBusinessRuleTask.setClassName("Class Name");
    actualBusinessRuleTask.setExclude(true);
    ArrayList<String> inputVariables = new ArrayList<>();
    actualBusinessRuleTask.setInputVariables(inputVariables);
    actualBusinessRuleTask.setResultVariableName("Result Variable Name");
    ArrayList<String> ruleNames = new ArrayList<>();
    actualBusinessRuleTask.setRuleNames(ruleNames);
    String actualClassName = actualBusinessRuleTask.getClassName();
    List<String> actualInputVariables = actualBusinessRuleTask.getInputVariables();
    String actualResultVariableName = actualBusinessRuleTask.getResultVariableName();
    List<String> actualRuleNames = actualBusinessRuleTask.getRuleNames();
    boolean actualIsExcludeResult = actualBusinessRuleTask.isExclude();

    // Assert
    assertEquals("Class Name", actualClassName);
    assertEquals("Result Variable Name", actualResultVariableName);
    assertNull(actualBusinessRuleTask.getBehavior());
    assertNull(actualBusinessRuleTask.getDefaultFlow());
    assertNull(actualBusinessRuleTask.getFailedJobRetryTimeCycleValue());
    assertNull(actualBusinessRuleTask.getId());
    assertNull(actualBusinessRuleTask.getDocumentation());
    assertNull(actualBusinessRuleTask.getName());
    assertNull(actualBusinessRuleTask.getParentContainer());
    assertNull(actualBusinessRuleTask.getIoSpecification());
    assertNull(actualBusinessRuleTask.getLoopCharacteristics());
    assertEquals(0, actualBusinessRuleTask.getXmlColumnNumber());
    assertEquals(0, actualBusinessRuleTask.getXmlRowNumber());
    assertFalse(actualBusinessRuleTask.isForCompensation());
    assertFalse(actualBusinessRuleTask.isAsynchronous());
    assertFalse(actualBusinessRuleTask.isNotExclusive());
    assertTrue(actualBusinessRuleTask.getBoundaryEvents().isEmpty());
    assertTrue(actualBusinessRuleTask.getDataInputAssociations().isEmpty());
    assertTrue(actualBusinessRuleTask.getDataOutputAssociations().isEmpty());
    assertTrue(actualBusinessRuleTask.getMapExceptions().isEmpty());
    assertTrue(actualInputVariables.isEmpty());
    assertTrue(actualRuleNames.isEmpty());
    assertTrue(actualBusinessRuleTask.getExecutionListeners().isEmpty());
    assertTrue(actualBusinessRuleTask.getIncomingFlows().isEmpty());
    assertTrue(actualBusinessRuleTask.getOutgoingFlows().isEmpty());
    assertTrue(actualBusinessRuleTask.getAttributes().isEmpty());
    assertTrue(actualBusinessRuleTask.getExtensionElements().isEmpty());
    assertTrue(actualIsExcludeResult);
    assertSame(inputVariables, actualInputVariables);
    assertSame(ruleNames, actualRuleNames);
  }
}
