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
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SignalValidatorDiffblueTest {
  /**
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   *
   * <p>Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SignalValidator.validate(BpmnModel, List)"})
  void testValidate() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    Signal signal = new Signal("", "");
    signal.setScope(Signal.SCOPE_GLOBAL);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(signal);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("SIGNAL_MISSING_NAME", getResult.getDefaultDescription());
    assertEquals("SIGNAL_MISSING_NAME", getResult.getKey());
    assertEquals("SIGNAL_MISSING_NAME", getResult.getProblem());
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
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   *
   * <p>Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SignalValidator.validate(BpmnModel, List)"})
  void testValidate2() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    Signal signal = new Signal("", "");
    signal.setScope(Signal.SCOPE_PROCESS_INSTANCE);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(signal);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("SIGNAL_MISSING_NAME", getResult.getDefaultDescription());
    assertEquals("SIGNAL_MISSING_NAME", getResult.getKey());
    assertEquals("SIGNAL_MISSING_NAME", getResult.getProblem());
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
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is empty string and name is empty
   *       string Scope is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Signal(String, String) with id is empty string and name is empty string Scope is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SignalValidator.validate(BpmnModel, List)"})
  void testValidate_givenSignalWithIdIsEmptyStringAndNameIsEmptyStringScopeIsNull() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    Signal signal = new Signal("", "");
    signal.setScope(null);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(signal);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("SIGNAL_MISSING_NAME", getResult.getDefaultDescription());
    assertEquals("SIGNAL_MISSING_NAME", getResult.getKey());
    assertEquals("SIGNAL_MISSING_NAME", getResult.getProblem());
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
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is empty string and name is {@code
   *       null} Scope is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Signal(String, String) with id is empty string and name is 'null' Scope is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SignalValidator.validate(BpmnModel, List)"})
  void testValidate_givenSignalWithIdIsEmptyStringAndNameIsNullScopeIsNull() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    Signal signal = new Signal("", null);
    signal.setScope(null);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(signal);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("SIGNAL_MISSING_NAME", getResult.getDefaultDescription());
    assertEquals("SIGNAL_MISSING_NAME", getResult.getKey());
    assertEquals("SIGNAL_MISSING_NAME", getResult.getProblem());
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
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is empty string and {@code Name}
   *       Scope is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Signal(String, String) with id is empty string and 'Name' Scope is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SignalValidator.validate(BpmnModel, List)"})
  void testValidate_givenSignalWithIdIsEmptyStringAndNameScopeIsNull() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    Signal signal = new Signal("", "Name");
    signal.setScope(null);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(signal);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_MISSING_ID", getResult.getDefaultDescription());
    assertEquals("SIGNAL_MISSING_ID", getResult.getKey());
    assertEquals("SIGNAL_MISSING_ID", getResult.getProblem());
  }

  /**
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code null} and {@code Name}
   *       Scope is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Signal(String, String) with id is 'null' and 'Name' Scope is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SignalValidator.validate(BpmnModel, List)"})
  void testValidate_givenSignalWithIdIsNullAndNameScopeIsNull() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    Signal signal = new Signal(null, "Name");
    signal.setScope(null);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(signal);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_MISSING_ID", getResult.getDefaultDescription());
    assertEquals("SIGNAL_MISSING_ID", getResult.getKey());
    assertEquals("SIGNAL_MISSING_ID", getResult.getProblem());
  }

  /**
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first DefaultDescription is {@code
   *       SIGNAL_MISSING_NAME}.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); then ArrayList() first DefaultDescription is 'SIGNAL_MISSING_NAME'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SignalValidator.validate(BpmnModel, List)"})
  void testValidate_thenArrayListFirstDefaultDescriptionIsSignalMissingName() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    Signal signal = new Signal("42", "");
    signal.setScope(null);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(signal);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_MISSING_NAME", getResult.getDefaultDescription());
    assertEquals("SIGNAL_MISSING_NAME", getResult.getKey());
    assertEquals("SIGNAL_MISSING_NAME", getResult.getProblem());
  }

  /**
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is three.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); then ArrayList() size is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SignalValidator.validate(BpmnModel, List)"})
  void testValidate_thenArrayListSizeIsThree() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    Signal signal = new Signal("", "");
    signal.setScope("Bpmn Model");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(signal);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(3, errors.size());
    ValidationError getResult = errors.get(2);
    assertEquals("SIGNAL_INVALID_SCOPE", getResult.getDefaultDescription());
    assertEquals("SIGNAL_INVALID_SCOPE", getResult.getKey());
    assertEquals("SIGNAL_INVALID_SCOPE", getResult.getProblem());
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
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); when BpmnModel (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SignalValidator.validate(BpmnModel, List)"})
  void testValidate_whenBpmnModel_thenArrayListEmpty() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   *
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with {@code Id} and {@code Name}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName(
      "Test duplicateName(Collection, String, String); given Signal(String, String) with 'Id' and 'Name'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SignalValidator.duplicateName(Collection, String, String)"})
  void testDuplicateName_givenSignalWithIdAndName_thenReturnTrue() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal("Id", "Name"));

    // Act and Assert
    assertTrue(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   *
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code 42} and name is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName(
      "Test duplicateName(Collection, String, String); given Signal(String, String) with id is '42' and name is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SignalValidator.duplicateName(Collection, String, String)"})
  void testDuplicateName_givenSignalWithIdIs42AndNameIs42() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal("42", "42"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   *
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code null} and {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName(
      "Test duplicateName(Collection, String, String); given Signal(String, String) with id is 'null' and 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SignalValidator.duplicateName(Collection, String, String)"})
  void testDuplicateName_givenSignalWithIdIsNullAndName() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    LinkedHashSet<Signal> signals = new LinkedHashSet<>();
    signals.add(new Signal(null, "Name"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, null, "Name"));
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   *
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code null} and {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName(
      "Test duplicateName(Collection, String, String); given Signal(String, String) with id is 'null' and 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SignalValidator.duplicateName(Collection, String, String)"})
  void testDuplicateName_givenSignalWithIdIsNullAndName2() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    LinkedHashSet<Signal> signals = new LinkedHashSet<>();
    signals.add(new Signal(null, "Name"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()} add {@link Signal#Signal(String, String)} with id is
   *       {@code 42} and {@code Name}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName(
      "Test duplicateName(Collection, String, String); when ArrayList() add Signal(String, String) with id is '42' and 'Name'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SignalValidator.duplicateName(Collection, String, String)"})
  void testDuplicateName_whenArrayListAddSignalWithIdIs42AndName_thenReturnTrue() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal("42", "Name"));
    signals.add(new Signal("Id", "Name"));

    // Act and Assert
    assertTrue(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName(
      "Test duplicateName(Collection, String, String); when ArrayList(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SignalValidator.duplicateName(Collection, String, String)"})
  void testDuplicateName_whenArrayList_thenReturnFalse() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    // Act and Assert
    assertFalse(signalValidator.duplicateName(new ArrayList<>(), "42", "Name"));
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   *
   * <ul>
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add {@link Signal#Signal(String, String)} with
   *       id is {@code 42} and {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName(
      "Test duplicateName(Collection, String, String); when LinkedHashSet() add Signal(String, String) with id is '42' and 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SignalValidator.duplicateName(Collection, String, String)"})
  void testDuplicateName_whenLinkedHashSetAddSignalWithIdIs42AndName() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    LinkedHashSet<Signal> signals = new LinkedHashSet<>();
    signals.add(new Signal("42", "Name"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, "42", "Name"));
  }
}
