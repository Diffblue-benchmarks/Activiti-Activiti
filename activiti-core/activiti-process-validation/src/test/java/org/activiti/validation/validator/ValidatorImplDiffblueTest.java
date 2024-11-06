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
package org.activiti.validation.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.activiti.validation.validator.impl.ActivitiEventListenerValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidatorImplDiffblueTest {
  /**
   * Test {@link ValidatorImpl#addError(List, ValidationError)} with
   * {@code validationErrors}, {@code error}.
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, ValidationError)}
   */
  @Test
  @DisplayName("Test addError(List, ValidationError) with 'validationErrors', 'error'")
  void testAddErrorWithValidationErrorsError() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    HashMap<String, String> params = new HashMap<>();
    params.computeIfPresent("foo", mock(BiFunction.class));

    ValidationError error = new ValidationError();
    error.setActivityId("42");
    error.setActivityName("Activity Name");
    error.setDefaultDescription("Default Description");
    error.setKey("Key");
    error.setParams(params);
    error.setProblem("Problem");
    error.setProcessDefinitionId("42");
    error.setProcessDefinitionName("Process Definition Name");
    error.setValidatorSetName("Validator Set Name");
    error.setWarning(true);
    error.setXmlColumnNumber(10);
    error.setXmlLineNumber(2);

    // Act
    activitiEventListenerValidator.addError(validationErrors, error);

    // Assert
    assertEquals(1, validationErrors.size());
    assertSame(error, validationErrors.get(0));
  }

  /**
   * Test {@link ValidatorImpl#addError(List, ValidationError)} with
   * {@code validationErrors}, {@code error}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, ValidationError)}
   */
  @Test
  @DisplayName("Test addError(List, ValidationError) with 'validationErrors', 'error'; given HashMap(); then ArrayList() size is one")
  void testAddErrorWithValidationErrorsError_givenHashMap_thenArrayListSizeIsOne() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    ValidationError error = new ValidationError();
    error.setActivityId("42");
    error.setActivityName("Activity Name");
    error.setDefaultDescription("Default Description");
    error.setKey("Key");
    error.setParams(new HashMap<>());
    error.setProblem("Problem");
    error.setProcessDefinitionId("42");
    error.setProcessDefinitionName("Process Definition Name");
    error.setValidatorSetName("Validator Set Name");
    error.setWarning(true);
    error.setXmlColumnNumber(10);
    error.setXmlLineNumber(2);

    // Act
    activitiEventListenerValidator.addError(validationErrors, error);

    // Assert
    assertEquals(1, validationErrors.size());
    assertSame(error, validationErrors.get(0));
  }

  /**
   * Test {@link ValidatorImpl#addError(List, ValidationError)} with
   * {@code validationErrors}, {@code error}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, ValidationError)}
   */
  @Test
  @DisplayName("Test addError(List, ValidationError) with 'validationErrors', 'error'; then ArrayList() size is two")
  void testAddErrorWithValidationErrorsError_thenArrayListSizeIsTwo() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);

    ValidationError error = new ValidationError();
    error.setActivityId("42");
    error.setActivityName("Activity Name");
    error.setDefaultDescription("Default Description");
    error.setKey("Key");
    error.setParams(new HashMap<>());
    error.setProblem("Problem");
    error.setProcessDefinitionId("42");
    error.setProcessDefinitionName("Process Definition Name");
    error.setValidatorSetName("Validator Set Name");
    error.setWarning(true);
    error.setXmlColumnNumber(10);
    error.setXmlLineNumber(2);

    // Act
    activitiEventListenerValidator.addError(validationErrors, error);

    // Assert
    assertEquals(2, validationErrors.size());
    assertSame(validationError, validationErrors.get(0));
    assertSame(error, validationErrors.get(1));
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String)} with
   * {@code validationErrors}, {@code problem}.
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, String)}
   */
  @Test
  @DisplayName("Test addError(List, String) with 'validationErrors', 'problem'")
  void testAddErrorWithValidationErrorsProblem() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    HashMap<String, String> params = new HashMap<>();
    params.computeIfPresent("foo", mock(BiFunction.class));

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(params);
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem");

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, BaseElement)} with
   * {@code validationErrors}, {@code problem}, {@code baseElement}.
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, String, BaseElement)}
   */
  @Test
  @DisplayName("Test addError(List, String, BaseElement) with 'validationErrors', 'problem', 'baseElement'")
  void testAddErrorWithValidationErrorsProblemBaseElement() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    AdhocSubProcess baseElement = new AdhocSubProcess();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", baseElement);

    // Assert
    Collection<FlowElement> flowElements = baseElement.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, BaseElement)} with
   * {@code validationErrors}, {@code problem}, {@code baseElement}.
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, String, BaseElement)}
   */
  @Test
  @DisplayName("Test addError(List, String, BaseElement) with 'validationErrors', 'problem', 'baseElement'")
  void testAddErrorWithValidationErrorsProblemBaseElement2() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    HashMap<String, String> params = new HashMap<>();
    params.computeIfPresent("foo", mock(BiFunction.class));

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(params);
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", new ActivitiListener());

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, BaseElement, Map)} with
   * {@code validationErrors}, {@code problem}, {@code baseElement},
   * {@code params}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, BaseElement, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, BaseElement, Map) with 'validationErrors', 'problem', 'baseElement', 'params'")
  void testAddErrorWithValidationErrorsProblemBaseElementParams() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    AdhocSubProcess baseElement = new AdhocSubProcess();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", baseElement, new HashMap<>());

    // Assert
    Collection<FlowElement> flowElements = baseElement.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, BaseElement, Map)} with
   * {@code validationErrors}, {@code problem}, {@code baseElement},
   * {@code params}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, BaseElement, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, BaseElement, Map) with 'validationErrors', 'problem', 'baseElement', 'params'; then ArrayList() size is one")
  void testAddErrorWithValidationErrorsProblemBaseElementParams_thenArrayListSizeIsOne() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", baseElement, new HashMap<>());

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
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
   * Test {@link ValidatorImpl#addError(List, String, BaseElement, Map)} with
   * {@code validationErrors}, {@code problem}, {@code baseElement},
   * {@code params}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, BaseElement, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, BaseElement, Map) with 'validationErrors', 'problem', 'baseElement', 'params'; then ArrayList() size is two")
  void testAddErrorWithValidationErrorsProblemBaseElementParams_thenArrayListSizeIsTwo() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", baseElement, new HashMap<>());

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, BaseElement, Map)} with
   * {@code validationErrors}, {@code problem}, {@code baseElement},
   * {@code params}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, BaseElement, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, BaseElement, Map) with 'validationErrors', 'problem', 'baseElement', 'params'; when 'null'")
  void testAddErrorWithValidationErrorsProblemBaseElementParams_whenNull() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", null, new HashMap<>());

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
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
   * Test {@link ValidatorImpl#addError(List, String, BaseElement)} with
   * {@code validationErrors}, {@code problem}, {@code baseElement}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, String, BaseElement)}
   */
  @Test
  @DisplayName("Test addError(List, String, BaseElement) with 'validationErrors', 'problem', 'baseElement'; then ArrayList() size is one")
  void testAddErrorWithValidationErrorsProblemBaseElement_thenArrayListSizeIsOne() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", new ActivitiListener());

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
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
   * Test {@link ValidatorImpl#addError(List, String, BaseElement)} with
   * {@code validationErrors}, {@code problem}, {@code baseElement}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, String, BaseElement)}
   */
  @Test
  @DisplayName("Test addError(List, String, BaseElement) with 'validationErrors', 'problem', 'baseElement'; then ArrayList() size is two")
  void testAddErrorWithValidationErrorsProblemBaseElement_thenArrayListSizeIsTwo() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", new ActivitiListener());

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, BaseElement)} with
   * {@code validationErrors}, {@code problem}, {@code baseElement}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, String, BaseElement)}
   */
  @Test
  @DisplayName("Test addError(List, String, BaseElement) with 'validationErrors', 'problem', 'baseElement'; when 'null'")
  void testAddErrorWithValidationErrorsProblemBaseElement_whenNull() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", (BaseElement) null);

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
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
   * Test {@link ValidatorImpl#addError(List, String, Map)} with
   * {@code validationErrors}, {@code problem}, {@code params}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, String, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, Map) with 'validationErrors', 'problem', 'params'; given 'foo'")
  void testAddErrorWithValidationErrorsProblemParams_givenFoo() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    HashMap<String, String> params = new HashMap<>();
    params.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", params);

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
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
   * Test {@link ValidatorImpl#addError(List, String, Map)} with
   * {@code validationErrors}, {@code problem}, {@code params}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, String, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, Map) with 'validationErrors', 'problem', 'params'; then ArrayList() size is one")
  void testAddErrorWithValidationErrorsProblemParams_thenArrayListSizeIsOne() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", new HashMap<>());

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
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
   * Test {@link ValidatorImpl#addError(List, String, Map)} with
   * {@code validationErrors}, {@code problem}, {@code params}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, String, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, Map) with 'validationErrors', 'problem', 'params'; then ArrayList() size is two")
  void testAddErrorWithValidationErrorsProblemParams_thenArrayListSizeIsTwo() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", new HashMap<>());

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, Process, BaseElement)} with
   * {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement) with 'validationErrors', 'problem', 'process', 'baseElement'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElement() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, new ActivitiListener());

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, Process, BaseElement)} with
   * {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement) with 'validationErrors', 'problem', 'process', 'baseElement'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElement2() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    AdhocSubProcess baseElement = new AdhocSubProcess();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement);

    // Assert
    Collection<FlowElement> flowElements = baseElement.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code isWarning}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement, boolean) with 'validationErrors', 'problem', 'process', 'baseElement', 'isWarning'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElementIsWarning() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, new ActivitiListener(), true);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code isWarning}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement, boolean) with 'validationErrors', 'problem', 'process', 'baseElement', 'isWarning'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElementIsWarning2() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);
    Process process = new Process();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, new ActivitiListener(), true);

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
    assertTrue(getResult2.isWarning());
  }

  /**
   * Test
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code isWarning}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement, boolean) with 'validationErrors', 'problem', 'process', 'baseElement', 'isWarning'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElementIsWarning3() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    AdhocSubProcess baseElement = new AdhocSubProcess();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, true);

    // Assert
    Collection<FlowElement> flowElements = baseElement.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean, Map)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code isWarning}, {@code params}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement, boolean, Map) with 'validationErrors', 'problem', 'process', 'baseElement', 'isWarning', 'params'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElementIsWarningParams() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, true, new HashMap<>());

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean, Map)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code isWarning}, {@code params}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement, boolean, Map) with 'validationErrors', 'problem', 'process', 'baseElement', 'isWarning', 'params'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElementIsWarningParams2() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", null, null, true, new HashMap<>());

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean, Map)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code isWarning}, {@code params}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement, boolean, Map) with 'validationErrors', 'problem', 'process', 'baseElement', 'isWarning', 'params'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElementIsWarningParams3() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, true, new HashMap<>());

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
    assertTrue(getResult2.isWarning());
  }

  /**
   * Test
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean, Map)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code isWarning}, {@code params}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement, boolean, Map) with 'validationErrors', 'problem', 'process', 'baseElement', 'isWarning', 'params'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElementIsWarningParams4() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    AdhocSubProcess baseElement = new AdhocSubProcess();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, true, new HashMap<>());

    // Assert
    Collection<FlowElement> flowElements = baseElement.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code isWarning}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement, boolean) with 'validationErrors', 'problem', 'process', 'baseElement', 'isWarning'; when 'null'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElementIsWarning_whenNull() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", null, null, true);

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, Process, BaseElement, Map)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code params}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement, Map) with 'validationErrors', 'problem', 'process', 'baseElement', 'params'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElementParams() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, new HashMap<>());

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, Process, BaseElement, Map)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code params}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement, Map) with 'validationErrors', 'problem', 'process', 'baseElement', 'params'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElementParams2() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, new HashMap<>());

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, Process, BaseElement, Map)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code params}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement, Map) with 'validationErrors', 'problem', 'process', 'baseElement', 'params'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElementParams3() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    AdhocSubProcess baseElement = new AdhocSubProcess();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, new HashMap<>());

    // Assert
    Collection<FlowElement> flowElements = baseElement.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, Process, BaseElement, Map)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code params}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, Map)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement, Map) with 'validationErrors', 'problem', 'process', 'baseElement', 'params'; when 'null'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElementParams_whenNull() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", null, null, new HashMap<>());

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
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
   * Test {@link ValidatorImpl#addError(List, String, Process, BaseElement)} with
   * {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement) with 'validationErrors', 'problem', 'process', 'baseElement'; then ArrayList() size is two")
  void testAddErrorWithValidationErrorsProblemProcessBaseElement_thenArrayListSizeIsTwo() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);
    Process process = new Process();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, new ActivitiListener());

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, Process, BaseElement)} with
   * {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, BaseElement) with 'validationErrors', 'problem', 'process', 'baseElement'; when 'null'")
  void testAddErrorWithValidationErrorsProblemProcessBaseElement_whenNull() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", (Process) null, (BaseElement) null);

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
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
   * Test {@link ValidatorImpl#addError(List, String, Process, String)} with
   * {@code validationErrors}, {@code problem}, {@code process}, {@code id}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, String)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, String) with 'validationErrors', 'problem', 'process', 'id'")
  void testAddErrorWithValidationErrorsProblemProcessId() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    HashMap<String, String> params = new HashMap<>();
    params.computeIfPresent("foo", mock(BiFunction.class));

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(params);
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", new Process(), "42");

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("42", getResult2.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, Process, String)} with
   * {@code validationErrors}, {@code problem}, {@code process}, {@code id}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, String)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, String) with 'validationErrors', 'problem', 'process', 'id'; then ArrayList() size is two")
  void testAddErrorWithValidationErrorsProblemProcessId_thenArrayListSizeIsTwo() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", new Process(), "42");

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("42", getResult2.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, Process, String)} with
   * {@code validationErrors}, {@code problem}, {@code process}, {@code id}.
   * <ul>
   *   <li>Then {@link Process} (default constructor) FlowElements
   * {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, String)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, String) with 'validationErrors', 'problem', 'process', 'id'; then Process (default constructor) FlowElements List")
  void testAddErrorWithValidationErrorsProblemProcessId_thenProcessFlowElementsList() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, "42");

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String, Process, String)} with
   * {@code validationErrors}, {@code problem}, {@code process}, {@code id}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, String)}
   */
  @Test
  @DisplayName("Test addError(List, String, Process, String) with 'validationErrors', 'problem', 'process', 'id'; when 'null'; then ArrayList() size is one")
  void testAddErrorWithValidationErrorsProblemProcessId_whenNull_thenArrayListSizeIsOne() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", null, "42");

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
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
   * Test {@link ValidatorImpl#addError(List, String)} with
   * {@code validationErrors}, {@code problem}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, String)}
   */
  @Test
  @DisplayName("Test addError(List, String) with 'validationErrors', 'problem'; then ArrayList() size is two")
  void testAddErrorWithValidationErrorsProblem_thenArrayListSizeIsTwo() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem");

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addError(List, String)} with
   * {@code validationErrors}, {@code problem}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ValidatorImpl#addError(List, String)}
   */
  @Test
  @DisplayName("Test addError(List, String) with 'validationErrors', 'problem'; when ArrayList(); then ArrayList() size is one")
  void testAddErrorWithValidationErrorsProblem_whenArrayList_thenArrayListSizeIsOne() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem");

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
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
   * Test {@link ValidatorImpl#addWarning(List, String, Process, BaseElement)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement)}
   */
  @Test
  @DisplayName("Test addWarning(List, String, Process, BaseElement) with 'validationErrors', 'problem', 'process', 'baseElement'")
  void testAddWarningWithValidationErrorsProblemProcessBaseElement() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", process, new ActivitiListener());

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addWarning(List, String, Process, BaseElement)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement)}
   */
  @Test
  @DisplayName("Test addWarning(List, String, Process, BaseElement) with 'validationErrors', 'problem', 'process', 'baseElement'")
  void testAddWarningWithValidationErrorsProblemProcessBaseElement2() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);
    Process process = new Process();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", process, new ActivitiListener());

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
    assertTrue(getResult2.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addWarning(List, String, Process, BaseElement)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement)}
   */
  @Test
  @DisplayName("Test addWarning(List, String, Process, BaseElement) with 'validationErrors', 'problem', 'process', 'baseElement'")
  void testAddWarningWithValidationErrorsProblemProcessBaseElement3() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    AdhocSubProcess baseElement = new AdhocSubProcess();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", process, baseElement);

    // Assert
    Collection<FlowElement> flowElements = baseElement.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement, Map)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code params}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement, Map)}
   */
  @Test
  @DisplayName("Test addWarning(List, String, Process, BaseElement, Map) with 'validationErrors', 'problem', 'process', 'baseElement', 'params'")
  void testAddWarningWithValidationErrorsProblemProcessBaseElementParams() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", process, baseElement, new HashMap<>());

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement, Map)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code params}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement, Map)}
   */
  @Test
  @DisplayName("Test addWarning(List, String, Process, BaseElement, Map) with 'validationErrors', 'problem', 'process', 'baseElement', 'params'")
  void testAddWarningWithValidationErrorsProblemProcessBaseElementParams2() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    validationErrors.add(validationError);
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", process, baseElement, new HashMap<>());

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    ValidationError getResult2 = validationErrors.get(1);
    assertEquals("Problem", getResult2.getDefaultDescription());
    assertEquals("Problem", getResult2.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Problem", getResult2.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
    assertTrue(getResult2.isWarning());
  }

  /**
   * Test
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement, Map)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code params}.
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement, Map)}
   */
  @Test
  @DisplayName("Test addWarning(List, String, Process, BaseElement, Map) with 'validationErrors', 'problem', 'process', 'baseElement', 'params'")
  void testAddWarningWithValidationErrorsProblemProcessBaseElementParams3() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    AdhocSubProcess baseElement = new AdhocSubProcess();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", process, baseElement, new HashMap<>());

    // Assert
    Collection<FlowElement> flowElements = baseElement.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement, Map)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}, {@code params}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement, Map)}
   */
  @Test
  @DisplayName("Test addWarning(List, String, Process, BaseElement, Map) with 'validationErrors', 'problem', 'process', 'baseElement', 'params'; when 'null'")
  void testAddWarningWithValidationErrorsProblemProcessBaseElementParams_whenNull() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", null, null, new HashMap<>());

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link ValidatorImpl#addWarning(List, String, Process, BaseElement)}
   * with {@code validationErrors}, {@code problem}, {@code process},
   * {@code baseElement}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement)}
   */
  @Test
  @DisplayName("Test addWarning(List, String, Process, BaseElement) with 'validationErrors', 'problem', 'process', 'baseElement'; when 'null'")
  void testAddWarningWithValidationErrorsProblemProcessBaseElement_whenNull() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", null, null);

    // Assert
    assertEquals(1, validationErrors.size());
    ValidationError getResult = validationErrors.get(0);
    assertEquals("Problem", getResult.getDefaultDescription());
    assertEquals("Problem", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }
}
