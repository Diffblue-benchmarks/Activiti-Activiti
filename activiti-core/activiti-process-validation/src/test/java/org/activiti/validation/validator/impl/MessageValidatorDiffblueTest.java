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
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.Test;

class MessageValidatorDiffblueTest {
  /**
   * Method under test: {@link MessageValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate() {
    // Arrange
    MessageValidator messageValidator = new MessageValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    messageValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test: {@link MessageValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate2() {
    // Arrange
    MessageValidator messageValidator = new MessageValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addMessage(new Message("42", "Name", "Item Ref"));
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    messageValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MESSAGE_INVALID_ITEM_REF", getResult.getDefaultDescription());
    assertEquals("MESSAGE_INVALID_ITEM_REF", getResult.getKey());
    assertEquals("MESSAGE_INVALID_ITEM_REF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test: {@link MessageValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate3() {
    // Arrange
    MessageValidator messageValidator = new MessageValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addMessage(new Message("42", "Name", null));
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    messageValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test: {@link MessageValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate4() {
    // Arrange
    MessageValidator messageValidator = new MessageValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addMessage(new Message("42", "Name", ""));
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    messageValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test: {@link MessageValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate5() {
    // Arrange
    MessageValidator messageValidator = new MessageValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addMessage(new Message("Id", "MESSAGE_INVALID_ITEM_REF", "MESSAGE_INVALID_ITEM_REF"));
    bpmnModel.addMessage(new Message("42", "Name", "Item Ref"));
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    messageValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MESSAGE_INVALID_ITEM_REF", getResult.getDefaultDescription());
    ValidationError getResult2 = errors.get(1);
    assertEquals("MESSAGE_INVALID_ITEM_REF", getResult2.getDefaultDescription());
    assertEquals("MESSAGE_INVALID_ITEM_REF", getResult.getKey());
    assertEquals("MESSAGE_INVALID_ITEM_REF", getResult2.getKey());
    assertEquals("MESSAGE_INVALID_ITEM_REF", getResult.getProblem());
    assertEquals("MESSAGE_INVALID_ITEM_REF", getResult2.getProblem());
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
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }
}
