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

class ErrorValidatorDiffblueTest {
  /**
   * Test {@link ErrorValidator#validate(BpmnModel, List)}.
   * <p>
   * Method under test: {@link ErrorValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List)")
  void testValidate() {
    // Arrange
    ErrorValidator errorValidator = new ErrorValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addError("An error occurred", "An error occurred", "An error occurred");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    errorValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link ErrorValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given empty string; then ArrayList() size is one")
  void testValidate_givenEmptyString_thenArrayListSizeIsOne() {
    // Arrange
    ErrorValidator errorValidator = new ErrorValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addError("An error occurred", "An error occurred", "");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    errorValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("An error occurred", getResult.getActivityId());
    assertEquals("ERROR_MISSING_ERROR_CODE", getResult.getDefaultDescription());
    assertEquals("ERROR_MISSING_ERROR_CODE", getResult.getKey());
    assertEquals("ERROR_MISSING_ERROR_CODE", getResult.getProblem());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ErrorValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@code Error Ref}.</li>
   *   <li>When {@link BpmnModel#addError(String, String, String)} with
   * {@code Error Ref} and {@code Error Name} and {@code Error Code}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given 'Error Ref'; when addError(String, String, String) with 'Error Ref' and 'Error Name' and 'Error Code'")
  void testValidate_givenErrorRef_whenAddErrorWithErrorRefAndErrorNameAndErrorCode() {
    // Arrange
    ErrorValidator errorValidator = new ErrorValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addError("Error Ref", "Error Name", "Error Code");
    bpmnModel.addError("An error occurred", "An error occurred", "An error occurred");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    errorValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link ErrorValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); when BpmnModel (default constructor); then ArrayList() Empty")
  void testValidate_whenBpmnModel_thenArrayListEmpty() {
    // Arrange
    ErrorValidator errorValidator = new ErrorValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    errorValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }
}
