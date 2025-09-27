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
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MessageValidatorDiffblueTest {
  /**
   * Test {@link MessageValidator#validate(BpmnModel, List)}.
   *
   * <p>Method under test: {@link MessageValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageValidator.validate(BpmnModel, List)"})
  void testValidate() {
    // Arrange
    MessageValidator messageValidator = new MessageValidator();

    BpmnModel bpmnModel = new BpmnModel();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    bpmnModel.addMessage(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef(null)
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
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
  }

  /**
   * Test {@link MessageValidator#validate(BpmnModel, List)}.
   *
   * <p>Method under test: {@link MessageValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageValidator.validate(BpmnModel, List)"})
  void testValidate2() {
    // Arrange
    MessageValidator messageValidator = new MessageValidator();

    BpmnModel bpmnModel = new BpmnModel();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    bpmnModel.addMessage(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
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
  }

  /**
   * Test {@link MessageValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link MessageValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageValidator.validate(BpmnModel, List)"})
  void testValidate_thenArrayListSizeIsOne() {
    // Arrange
    MessageValidator messageValidator = new MessageValidator();

    BpmnModel bpmnModel = new BpmnModel();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    bpmnModel.addMessage(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
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
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link MessageValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link MessageValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageValidator.validate(BpmnModel, List)"})
  void testValidate_thenArrayListSizeIsTwo() {
    // Arrange
    MessageValidator messageValidator = new MessageValidator();

    BpmnModel bpmnModel = new BpmnModel();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    bpmnModel.addMessage(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("Id")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    bpmnModel.addMessage(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    messageValidator.validate(bpmnModel, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("MESSAGE_INVALID_ITEM_REF", getResult.getDefaultDescription());
    assertEquals("MESSAGE_INVALID_ITEM_REF", getResult.getKey());
    assertEquals("MESSAGE_INVALID_ITEM_REF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link MessageValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link MessageValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); when BpmnModel (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MessageValidator.validate(BpmnModel, List)"})
  void testValidate_whenBpmnModel_thenArrayListEmpty() {
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
  }
}
