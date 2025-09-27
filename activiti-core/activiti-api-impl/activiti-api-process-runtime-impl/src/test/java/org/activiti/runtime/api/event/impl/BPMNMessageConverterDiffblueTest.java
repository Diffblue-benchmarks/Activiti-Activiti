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
package org.activiti.runtime.api.event.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.process.model.payloads.MessageEventPayload;
import org.activiti.api.runtime.model.impl.BPMNMessageImpl;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.ActivitiMessageEvent;
import org.activiti.engine.delegate.event.impl.ActivitiMessageEventImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BPMNMessageConverter.class})
@ExtendWith(SpringExtension.class)
class BPMNMessageConverterDiffblueTest {
  @Autowired private BPMNMessageConverter bPMNMessageConverter;

  /**
   * Test {@link BPMNMessageConverter#convertToBPMNMessage(ActivitiMessageEvent)}.
   *
   * <ul>
   *   <li>Then return MessagePayload BusinessKey is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNMessageConverter#convertToBPMNMessage(ActivitiMessageEvent)}
   */
  @Test
  @DisplayName(
      "Test convertToBPMNMessage(ActivitiMessageEvent); then return MessagePayload BusinessKey is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BPMNMessageImpl BPMNMessageConverter.convertToBPMNMessage(ActivitiMessageEvent)"
  })
  void testConvertToBPMNMessage_thenReturnMessagePayloadBusinessKeyIsNull() {
    // Arrange and Act
    BPMNMessageImpl actualConvertToBPMNMessageResult =
        bPMNMessageConverter.convertToBPMNMessage(
            new ActivitiMessageEventImpl(ActivitiEventType.ENTITY_CREATED));

    // Assert
    MessageEventPayload messagePayload = actualConvertToBPMNMessageResult.getMessagePayload();
    assertNull(messagePayload.getBusinessKey());
    assertNull(messagePayload.getCorrelationKey());
    assertNull(messagePayload.getName());
    assertNull(actualConvertToBPMNMessageResult.getElementId());
    assertNull(actualConvertToBPMNMessageResult.getProcessDefinitionId());
    assertNull(actualConvertToBPMNMessageResult.getProcessInstanceId());
    assertNull(messagePayload.getVariables());
  }
}
