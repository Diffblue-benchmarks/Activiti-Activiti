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
import org.junit.jupiter.api.Test;

class SignalValidatorDiffblueTest {
  /**
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate() {
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate2() {
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate3() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal(null, "Name"));
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_MISSING_ID", getResult.getDefaultDescription());
    assertEquals("SIGNAL_MISSING_ID", getResult.getKey());
    assertEquals("SIGNAL_MISSING_ID", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate4() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("", "Name"));
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_MISSING_ID", getResult.getDefaultDescription());
    assertEquals("SIGNAL_MISSING_ID", getResult.getKey());
    assertEquals("SIGNAL_MISSING_ID", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate5() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("42", null));
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
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
    assertTrue(resources.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate6() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("Id", "Name"));
    bpmnModel.addSignal(new Signal("42", "Name"));
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_DUPLICATE_NAME", getResult.getDefaultDescription());
    ValidationError getResult2 = errors.get(1);
    assertEquals("SIGNAL_DUPLICATE_NAME", getResult2.getDefaultDescription());
    assertEquals("SIGNAL_DUPLICATE_NAME", getResult.getKey());
    assertEquals("SIGNAL_DUPLICATE_NAME", getResult2.getKey());
    assertEquals("SIGNAL_DUPLICATE_NAME", getResult.getProblem());
    assertEquals("SIGNAL_DUPLICATE_NAME", getResult2.getProblem());
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
    assertTrue(resources.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate7() {
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test: {@link SignalValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate8() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("42", "SIGNAL_MISSING_ID"));
    bpmnModel.addSignal(new Signal(null, "Name"));
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    signalValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_MISSING_ID", getResult.getDefaultDescription());
    assertEquals("SIGNAL_MISSING_ID", getResult.getKey());
    assertEquals("SIGNAL_MISSING_ID", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  void testDuplicateName() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    // Act and Assert
    assertFalse(signalValidator.duplicateName(new ArrayList<>(), "42", "Name"));
  }

  /**
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  void testDuplicateName2() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal("42", "Name"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  void testDuplicateName3() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal("42", "Name"));
    signals.add(new Signal("42", "Name"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  void testDuplicateName4() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal("Name", "Name"));

    // Act and Assert
    assertTrue(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  void testDuplicateName5() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal(null, "Name"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  void testDuplicateName6() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal("42", "42"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, "42", "Name"));
  }

  /**
   * Method under test:
   * {@link SignalValidator#duplicateName(Collection, String, String)}
   */
  @Test
  void testDuplicateName7() {
    // Arrange
    SignalValidator signalValidator = new SignalValidator();

    ArrayList<Signal> signals = new ArrayList<>();
    signals.add(new Signal("42", "Name"));

    // Act and Assert
    assertFalse(signalValidator.duplicateName(signals, null, "Name"));
  }
}
