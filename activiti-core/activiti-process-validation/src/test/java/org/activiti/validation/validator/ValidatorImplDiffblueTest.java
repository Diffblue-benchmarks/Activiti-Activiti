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
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.activiti.validation.validator.impl.ActivitiEventListenerValidator;
import org.junit.jupiter.api.Test;

class ValidatorImplDiffblueTest {
  /**
   * Method under test: {@link ValidatorImpl#addError(List, String)}
   */
  @Test
  void testAddError() {
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
   * Method under test: {@link ValidatorImpl#addError(List, String)}
   */
  @Test
  void testAddError2() {
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
    ValidationError getResult = validationErrors.get(1);
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
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test: {@link ValidatorImpl#addError(List, String)}
   */
  @Test
  void testAddError3() {
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
    ValidationError getResult = validationErrors.get(1);
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
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test: {@link ValidatorImpl#addError(List, String, Map)}
   */
  @Test
  void testAddError4() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    HashMap<String, String> params = new HashMap<>();

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
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertSame(params, params2);
  }

  /**
   * Method under test: {@link ValidatorImpl#addError(List, String, Map)}
   */
  @Test
  void testAddError5() {
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
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", params);

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(1);
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
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertSame(params, params2);
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test: {@link ValidatorImpl#addError(List, String, Map)}
   */
  @Test
  void testAddError6() {
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
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertSame(params, params2);
  }

  /**
   * Method under test: {@link ValidatorImpl#addError(List, String, BaseElement)}
   */
  @Test
  void testAddError7() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", baseElement);

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
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test: {@link ValidatorImpl#addError(List, String, BaseElement)}
   */
  @Test
  void testAddError8() {
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
   * Method under test: {@link ValidatorImpl#addError(List, String, BaseElement)}
   */
  @Test
  void testAddError9() {
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
    activitiEventListenerValidator.addError(validationErrors, "Problem", baseElement);

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(1);
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
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test: {@link ValidatorImpl#addError(List, String, BaseElement)}
   */
  @Test
  void testAddError10() {
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
    assertTrue(baseElement.getBoundaryEvents().isEmpty());
    assertTrue(baseElement.getDataInputAssociations().isEmpty());
    assertTrue(baseElement.getDataOutputAssociations().isEmpty());
    assertTrue(baseElement.getMapExceptions().isEmpty());
    assertTrue(baseElement.getExecutionListeners().isEmpty());
    assertTrue(baseElement.getIncomingFlows().isEmpty());
    assertTrue(baseElement.getOutgoingFlows().isEmpty());
    assertTrue(baseElement.getDataObjects().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(baseElement.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test: {@link ValidatorImpl#addError(List, String, BaseElement)}
   */
  @Test
  void testAddError11() {
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
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", baseElement);

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(1);
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
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, BaseElement, Map)}
   */
  @Test
  void testAddError12() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    ActivitiListener baseElement = new ActivitiListener();
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", baseElement, params);

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
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertSame(params, params2);
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, BaseElement, Map)}
   */
  @Test
  void testAddError13() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", null, params);

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
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertSame(params, params2);
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, BaseElement, Map)}
   */
  @Test
  void testAddError14() {
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
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", baseElement, params);

    // Assert
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(1);
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
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertSame(params, params2);
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, BaseElement, Map)}
   */
  @Test
  void testAddError15() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    AdhocSubProcess baseElement = new AdhocSubProcess();
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", baseElement, params);

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
    assertTrue(baseElement.getBoundaryEvents().isEmpty());
    assertTrue(baseElement.getDataInputAssociations().isEmpty());
    assertTrue(baseElement.getDataOutputAssociations().isEmpty());
    assertTrue(baseElement.getMapExceptions().isEmpty());
    assertTrue(baseElement.getExecutionListeners().isEmpty());
    assertTrue(baseElement.getIncomingFlows().isEmpty());
    assertTrue(baseElement.getOutgoingFlows().isEmpty());
    assertTrue(baseElement.getDataObjects().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(baseElement.getFlowElementMap().isEmpty());
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertSame(params, params2);
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, String)}
   */
  @Test
  void testAddError16() {
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
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, String)}
   */
  @Test
  void testAddError17() {
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
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, String)}
   */
  @Test
  void testAddError18() {
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
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, "42");

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(1);
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
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, String)}
   */
  @Test
  void testAddError19() {
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
    Process process = new Process();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, "42");

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(1);
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
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement)}
   */
  @Test
  void testAddError20() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement);

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
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement)}
   */
  @Test
  void testAddError21() {
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
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement)}
   */
  @Test
  void testAddError22() {
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
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(1);
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
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement)}
   */
  @Test
  void testAddError23() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    AdhocSubProcess baseElement = new AdhocSubProcess();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = baseElement.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = baseElement.getFlowElements();
    assertTrue(flowElements2 instanceof List);
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
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
    assertTrue(baseElement.getBoundaryEvents().isEmpty());
    assertTrue(baseElement.getDataInputAssociations().isEmpty());
    assertTrue(baseElement.getDataOutputAssociations().isEmpty());
    assertTrue(baseElement.getMapExceptions().isEmpty());
    assertTrue(baseElement.getExecutionListeners().isEmpty());
    assertTrue(baseElement.getIncomingFlows().isEmpty());
    assertTrue(baseElement.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getDataObjects().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(baseElement.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, Map)}
   */
  @Test
  void testAddError24() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, params);

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
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertSame(params, params2);
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, Map)}
   */
  @Test
  void testAddError25() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", null, null, params);

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
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertSame(params, params2);
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, Map)}
   */
  @Test
  void testAddError26() {
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
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, params);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(1);
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
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertSame(params, params2);
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, Map)}
   */
  @Test
  void testAddError27() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    AdhocSubProcess baseElement = new AdhocSubProcess();
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, params);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = baseElement.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = baseElement.getFlowElements();
    assertTrue(flowElements2 instanceof List);
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
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
    assertTrue(baseElement.getBoundaryEvents().isEmpty());
    assertTrue(baseElement.getDataInputAssociations().isEmpty());
    assertTrue(baseElement.getDataOutputAssociations().isEmpty());
    assertTrue(baseElement.getMapExceptions().isEmpty());
    assertTrue(baseElement.getExecutionListeners().isEmpty());
    assertTrue(baseElement.getIncomingFlows().isEmpty());
    assertTrue(baseElement.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getDataObjects().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(baseElement.getFlowElementMap().isEmpty());
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertSame(params, params2);
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean)}
   */
  @Test
  void testAddError28() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, true);

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
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean)}
   */
  @Test
  void testAddError29() {
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
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean)}
   */
  @Test
  void testAddError30() {
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
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, true);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(1);
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
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean)}
   */
  @Test
  void testAddError31() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    AdhocSubProcess baseElement = new AdhocSubProcess();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, true);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = baseElement.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = baseElement.getFlowElements();
    assertTrue(flowElements2 instanceof List);
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
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
    assertTrue(baseElement.getBoundaryEvents().isEmpty());
    assertTrue(baseElement.getDataInputAssociations().isEmpty());
    assertTrue(baseElement.getDataOutputAssociations().isEmpty());
    assertTrue(baseElement.getMapExceptions().isEmpty());
    assertTrue(baseElement.getExecutionListeners().isEmpty());
    assertTrue(baseElement.getIncomingFlows().isEmpty());
    assertTrue(baseElement.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getDataObjects().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(baseElement.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean, Map)}
   */
  @Test
  void testAddError32() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, true, params);

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
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(params, params2);
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean, Map)}
   */
  @Test
  void testAddError33() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", null, null, true, params);

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
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(params, params2);
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean, Map)}
   */
  @Test
  void testAddError34() {
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
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, true, params);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(1);
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
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(params, params2);
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addError(List, String, Process, BaseElement, boolean, Map)}
   */
  @Test
  void testAddError35() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    AdhocSubProcess baseElement = new AdhocSubProcess();
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addError(validationErrors, "Problem", process, baseElement, true, params);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = baseElement.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = baseElement.getFlowElements();
    assertTrue(flowElements2 instanceof List);
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
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
    assertTrue(baseElement.getBoundaryEvents().isEmpty());
    assertTrue(baseElement.getDataInputAssociations().isEmpty());
    assertTrue(baseElement.getDataOutputAssociations().isEmpty());
    assertTrue(baseElement.getMapExceptions().isEmpty());
    assertTrue(baseElement.getExecutionListeners().isEmpty());
    assertTrue(baseElement.getIncomingFlows().isEmpty());
    assertTrue(baseElement.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getDataObjects().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(baseElement.getFlowElementMap().isEmpty());
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(params, params2);
  }

  /**
   * Method under test: {@link ValidatorImpl#addError(List, ValidationError)}
   */
  @Test
  void testAddError36() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();

    ValidationError error = new ValidationError();
    error.setActivityId("42");
    error.setActivityName("Activity Name");
    error.setDefaultDescription("Default Description");
    error.setKey("Key");
    HashMap<String, String> params = new HashMap<>();
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
    assertSame(params, error.getParams());
    assertSame(error, validationErrors.get(0));
  }

  /**
   * Method under test: {@link ValidatorImpl#addError(List, ValidationError)}
   */
  @Test
  void testAddError37() {
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
    HashMap<String, String> params = new HashMap<>();
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
    assertEquals(2, validationErrors.size());
    assertSame(params, error.getParams());
    assertSame(validationError, validationErrors.get(0));
    assertSame(error, validationErrors.get(1));
  }

  /**
   * Method under test: {@link ValidatorImpl#addError(List, ValidationError)}
   */
  @Test
  void testAddError38() {
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
    assertSame(params, error.getParams());
    assertSame(error, validationErrors.get(0));
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement)}
   */
  @Test
  void testAddWarning() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", process, baseElement);

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
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement)}
   */
  @Test
  void testAddWarning2() {
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

  /**
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement)}
   */
  @Test
  void testAddWarning3() {
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
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", process, baseElement);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(1);
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
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement)}
   */
  @Test
  void testAddWarning4() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    AdhocSubProcess baseElement = new AdhocSubProcess();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", process, baseElement);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = baseElement.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = baseElement.getFlowElements();
    assertTrue(flowElements2 instanceof List);
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
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
    assertTrue(baseElement.getBoundaryEvents().isEmpty());
    assertTrue(baseElement.getDataInputAssociations().isEmpty());
    assertTrue(baseElement.getDataOutputAssociations().isEmpty());
    assertTrue(baseElement.getMapExceptions().isEmpty());
    assertTrue(baseElement.getExecutionListeners().isEmpty());
    assertTrue(baseElement.getIncomingFlows().isEmpty());
    assertTrue(baseElement.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getDataObjects().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(baseElement.getFlowElementMap().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement, Map)}
   */
  @Test
  void testAddWarning5() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", process, baseElement, params);

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
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(params, params2);
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement, Map)}
   */
  @Test
  void testAddWarning6() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", null, null, params);

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
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(params, params2);
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement, Map)}
   */
  @Test
  void testAddWarning7() {
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
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", process, baseElement, params);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, validationErrors.size());
    ValidationError getResult = validationErrors.get(1);
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
    assertTrue(baseElement.getFieldExtensions().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(params, params2);
    assertSame(validationError, validationErrors.get(0));
  }

  /**
   * Method under test:
   * {@link ValidatorImpl#addWarning(List, String, Process, BaseElement, Map)}
   */
  @Test
  void testAddWarning8() {
    // Arrange
    ActivitiEventListenerValidator activitiEventListenerValidator = new ActivitiEventListenerValidator();
    ArrayList<ValidationError> validationErrors = new ArrayList<>();
    Process process = new Process();
    AdhocSubProcess baseElement = new AdhocSubProcess();
    HashMap<String, String> params = new HashMap<>();

    // Act
    activitiEventListenerValidator.addWarning(validationErrors, "Problem", process, baseElement, params);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = baseElement.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = baseElement.getFlowElements();
    assertTrue(flowElements2 instanceof List);
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
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
    assertTrue(baseElement.getBoundaryEvents().isEmpty());
    assertTrue(baseElement.getDataInputAssociations().isEmpty());
    assertTrue(baseElement.getDataOutputAssociations().isEmpty());
    assertTrue(baseElement.getMapExceptions().isEmpty());
    assertTrue(baseElement.getExecutionListeners().isEmpty());
    assertTrue(baseElement.getIncomingFlows().isEmpty());
    assertTrue(baseElement.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(baseElement.getDataObjects().isEmpty());
    assertTrue(baseElement.getAttributes().isEmpty());
    assertTrue(process.getAttributes().isEmpty());
    assertTrue(baseElement.getExtensionElements().isEmpty());
    assertTrue(process.getExtensionElements().isEmpty());
    assertTrue(process.getFlowElementMap().isEmpty());
    assertTrue(baseElement.getFlowElementMap().isEmpty());
    Map<String, String> params2 = getResult.getParams();
    assertTrue(params2.isEmpty());
    assertTrue(getResult.isWarning());
    assertSame(params, params2);
  }
}
