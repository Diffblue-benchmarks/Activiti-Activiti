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
package org.activiti.validation.validator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AssociationValidatorDiffblueTest {
  /**
   * Test {@link AssociationValidator#validate(BpmnModel, List)} with {@code bpmnModel}, {@code
   * errors}.
   *
   * <p>Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List) with 'bpmnModel', 'errors'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationValidator.validate(BpmnModel, List)"})
  void testValidateWithBpmnModelErrors() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();

    Association artifact = new Association();
    artifact.setSourceRef("");
    artifact.setTargetRef("Bpmn Model");

    Process process = new Process();
    process.addArtifact(artifact);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.setGlobalArtifacts(null);
    bpmnModel.addProcess(process);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getProblem());
  }

  /**
   * Test {@link AssociationValidator#validate(BpmnModel, List)} with {@code bpmnModel}, {@code
   * errors}.
   *
   * <p>Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List) with 'bpmnModel', 'errors'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationValidator.validate(BpmnModel, List)"})
  void testValidateWithBpmnModelErrors2() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();

    Association artifact = new Association();
    artifact.setSourceRef("Bpmn Model");
    artifact.setTargetRef("");

    Process process = new Process();
    process.addArtifact(artifact);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.setGlobalArtifacts(null);
    bpmnModel.addProcess(process);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getProblem());
  }

  /**
   * Test {@link AssociationValidator#validate(BpmnModel, List)} with {@code bpmnModel}, {@code
   * errors}.
   *
   * <ul>
   *   <li>Given {@link Association} (default constructor) TargetRef is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List) with 'bpmnModel', 'errors'; given Association (default constructor) TargetRef is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationValidator.validate(BpmnModel, List)"})
  void testValidateWithBpmnModelErrors_givenAssociationTargetRefIsNull() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();

    Association artifact = new Association();
    artifact.setSourceRef("");
    artifact.setTargetRef(null);

    Process process = new Process();
    process.addArtifact(artifact);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.setGlobalArtifacts(null);
    bpmnModel.addProcess(process);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link AssociationValidator#validate(BpmnModel, List)} with {@code bpmnModel}, {@code
   * errors}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is four.
   * </ul>
   *
   * <p>Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List) with 'bpmnModel', 'errors'; then ArrayList() size is four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationValidator.validate(BpmnModel, List)"})
  void testValidateWithBpmnModelErrors_thenArrayListSizeIsFour() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();

    Association association = new Association();
    association.setSourceRef("");
    association.setTargetRef("");

    ArrayList<Artifact> globalArtifacts = new ArrayList<>();
    globalArtifacts.add(association);

    Association artifact = new Association();
    artifact.setSourceRef("");
    artifact.setTargetRef("");

    Process process = new Process();
    process.addArtifact(artifact);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.setGlobalArtifacts(globalArtifacts);
    bpmnModel.addProcess(process);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(4, errors.size());
    ValidationError getResult = errors.get(2);
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getProblem());
    ValidationError getResult2 = errors.get(3);
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult2.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult2.getKey());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult2.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult2.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult2.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Test {@link AssociationValidator#validate(BpmnModel, List)} with {@code bpmnModel}, {@code
   * errors}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List) with 'bpmnModel', 'errors'; then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationValidator.validate(BpmnModel, List)"})
  void testValidateWithBpmnModelErrors_thenArrayListSizeIsTwo() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();

    Association artifact = new Association();
    artifact.setSourceRef("");
    artifact.setTargetRef("");

    Process process = new Process();
    process.addArtifact(artifact);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.setGlobalArtifacts(null);
    bpmnModel.addProcess(process);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link AssociationValidator#validate(BpmnModel, List)} with {@code bpmnModel}, {@code
   * errors}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List) with 'bpmnModel', 'errors'; when BpmnModel (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationValidator.validate(BpmnModel, List)"})
  void testValidateWithBpmnModelErrors_whenBpmnModel_thenArrayListEmpty() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link AssociationValidator#validate(Process, Association, List)} with {@code process},
   * {@code association}, {@code errors}.
   *
   * <p>Method under test: {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  @DisplayName("Test validate(Process, Association, List) with 'process', 'association', 'errors'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationValidator.validate(Process, Association, List)"})
  void testValidateWithProcessAssociationErrors() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    Process process = new Process();

    Association association = new Association();
    association.setSourceRef("");
    association.setTargetRef("Association");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(process, association, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getProblem());
  }

  /**
   * Test {@link AssociationValidator#validate(Process, Association, List)} with {@code process},
   * {@code association}, {@code errors}.
   *
   * <p>Method under test: {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  @DisplayName("Test validate(Process, Association, List) with 'process', 'association', 'errors'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationValidator.validate(Process, Association, List)"})
  void testValidateWithProcessAssociationErrors2() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    Process process = new Process();

    Association association = new Association();
    association.setSourceRef("Association");
    association.setTargetRef("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(process, association, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getProblem());
  }

  /**
   * Test {@link AssociationValidator#validate(Process, Association, List)} with {@code process},
   * {@code association}, {@code errors}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  @DisplayName(
      "Test validate(Process, Association, List) with 'process', 'association', 'errors'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationValidator.validate(Process, Association, List)"})
  void testValidateWithProcessAssociationErrors_thenArrayListEmpty() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    Process process = new Process();

    Association association = new Association();
    association.setSourceRef("Association");
    association.setTargetRef("Association");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(process, association, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link AssociationValidator#validate(Process, Association, List)} with {@code process},
   * {@code association}, {@code errors}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  @DisplayName(
      "Test validate(Process, Association, List) with 'process', 'association', 'errors'; then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationValidator.validate(Process, Association, List)"})
  void testValidateWithProcessAssociationErrors_thenArrayListSizeIsTwo() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    Process process = new Process();

    Association association = new Association();
    association.setSourceRef("");
    association.setTargetRef("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(process, association, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link AssociationValidator#validate(Process, Association, List)} with {@code process},
   * {@code association}, {@code errors}.
   *
   * <ul>
   *   <li>When {@link Association} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  @DisplayName(
      "Test validate(Process, Association, List) with 'process', 'association', 'errors'; when Association (default constructor); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationValidator.validate(Process, Association, List)"})
  void testValidateWithProcessAssociationErrors_whenAssociation_thenArrayListSizeIsTwo() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    Process process = new Process();
    Association association = new Association();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(process, association, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }
}
