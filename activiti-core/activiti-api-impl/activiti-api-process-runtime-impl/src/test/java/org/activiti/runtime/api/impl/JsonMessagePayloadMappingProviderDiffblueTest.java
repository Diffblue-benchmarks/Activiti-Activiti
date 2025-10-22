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
package org.activiti.runtime.api.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.behavior.NoneVariablesCalculator;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class JsonMessagePayloadMappingProviderDiffblueTest {
  /**
   * Test {@link JsonMessagePayloadMappingProvider#getMessagePayload(DelegateExecution)}.
   * <ul>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonMessagePayloadMappingProvider#getMessagePayload(DelegateExecution)}
   */
  @Test
  @DisplayName("Test getMessagePayload(DelegateExecution); then return not Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Optional JsonMessagePayloadMappingProvider.getMessagePayload(DelegateExecution)"})
  void testGetMessagePayload_thenReturnNotPresent() {
    // Arrange
    BoundaryEvent bpmnEvent = new BoundaryEvent();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    ExpressionManager expressionManager = new ExpressionManager();
    JsonMessagePayloadMappingProvider jsonMessagePayloadMappingProvider = new JsonMessagePayloadMappingProvider(
        bpmnEvent, messageEventDefinition, expressionManager, new NoneVariablesCalculator());

    // Act and Assert
    assertFalse(jsonMessagePayloadMappingProvider
        .getMessagePayload(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
        .isPresent());
  }
}
