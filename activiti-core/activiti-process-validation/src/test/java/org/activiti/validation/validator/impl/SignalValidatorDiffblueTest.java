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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SignalValidatorDiffblueTest {
  /**
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with {@code Id} and
   * {@code Name}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given Signal(String, String) with 'Id' and 'Name'; then ArrayList() size is two")
  void testValidate_givenSignalWithIdAndName_thenArrayListSizeIsTwo() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("Id", "Name"));
    bpmnModel.addSignal(new Signal("42", "Name"));
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_DUPLICATE_NAME", getResult.getDefaultDescription());
    ValidationError getResult2 = errors.get(1);
    assertEquals("SIGNAL_DUPLICATE_NAME", getResult2.getDefaultDescription());
    assertEquals("SIGNAL_DUPLICATE_NAME", getResult.getKey());
    assertEquals("SIGNAL_DUPLICATE_NAME", getResult2.getKey());
    assertEquals("SIGNAL_DUPLICATE_NAME", getResult.getProblem());
    assertEquals("SIGNAL_DUPLICATE_NAME", getResult2.getProblem());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code 42} and
   * name is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given Signal(String, String) with id is '42' and name is '42'")
  void testValidate_givenSignalWithIdIs42AndNameIs42() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("42", "42"));
    bpmnModel.addSignal(new Signal("42", "Name"));
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
  }

  /**
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code 42} and
   * name is {@code SIGNAL_MISSING_ID}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given Signal(String, String) with id is '42' and name is 'SIGNAL_MISSING_ID'")
  void testValidate_givenSignalWithIdIs42AndNameIsSignalMissingId() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("42", "SIGNAL_MISSING_ID"));
    bpmnModel.addSignal(new Signal(null, "Name"));
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
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code 42} and
   * {@code Name}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given Signal(String, String) with id is '42' and 'Name'; then BpmnModel (default constructor) Resources List")
  void testValidate_givenSignalWithIdIs42AndName_thenBpmnModelResourcesList() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("42", "Name"));
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
  }

  /**
   * Test {@link SignalValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is empty string and
   * {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given Signal(String, String) with id is empty string and 'Name'")
  void testValidate_givenSignalWithIdIsEmptyStringAndName() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("", "Name"));
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
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code null} and
   * {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given Signal(String, String) with id is 'null' and 'Name'")
  void testValidate_givenSignalWithIdIsNullAndName() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal(null, "Name"));
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
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first DefaultDescription is
   * {@code SIGNAL_MISSING_NAME}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); then ArrayList() first DefaultDescription is 'SIGNAL_MISSING_NAME'")
  void testValidate_thenArrayListFirstDefaultDescriptionIsSignalMissingName() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("42", null));
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
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); when BpmnModel (default constructor); then BpmnModel (default constructor) Resources List")
  void testValidate_whenBpmnModel_thenBpmnModelResourcesList() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code 42} and
   * {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName("Test duplicateName(Collection, String, String); given Signal(String, String) with id is '42' and 'Name'")
  void testDuplicateName_givenSignalWithIdIs42AndName() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal("42", "Name"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code 42} and
   * {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName("Test duplicateName(Collection, String, String); given Signal(String, String) with id is '42' and 'Name'")
  void testDuplicateName_givenSignalWithIdIs42AndName2() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal("42", "Name"));
    signals.add(new Signal("42", "Name"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code 42} and
   * name is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName("Test duplicateName(Collection, String, String); given Signal(String, String) with id is '42' and name is '42'")
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
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code 42} and
   * {@code Name}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName("Test duplicateName(Collection, String, String); given Signal(String, String) with id is '42' and 'Name'; when 'null'; then return 'false'")
  void testDuplicateName_givenSignalWithIdIs42AndName_whenNull_thenReturnFalse() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal("42", "Name"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, null, "Name"));
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code Name} and
   * {@code Name}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName("Test duplicateName(Collection, String, String); given Signal(String, String) with id is 'Name' and 'Name'; then return 'true'")
  void testDuplicateName_givenSignalWithIdIsNameAndName_thenReturnTrue() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal("Name", "Name"));

    // Act and Assert
    assertTrue(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code null} and
   * {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName("Test duplicateName(Collection, String, String); given Signal(String, String) with id is 'null' and 'Name'")
  void testDuplicateName_givenSignalWithIdIsNullAndName() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal(null, "Name"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Test {@link SignalValidator#duplicateName(Collection, String, String)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  @DisplayName("Test duplicateName(Collection, String, String); when ArrayList(); then return 'false'")
  void testDuplicateName_whenArrayList_thenReturnFalse() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    // Act and Assert
    assertFalse(signalValidator.duplicateName(new ArrayList<>(), "42", "Name"));
  }
}
