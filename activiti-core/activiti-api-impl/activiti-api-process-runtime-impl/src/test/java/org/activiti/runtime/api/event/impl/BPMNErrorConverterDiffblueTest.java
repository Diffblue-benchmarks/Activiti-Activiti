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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.runtime.model.impl.BPMNErrorImpl;
import org.activiti.engine.delegate.event.ActivitiErrorEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiErrorEventImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BPMNErrorConverter.class})
@ExtendWith(SpringExtension.class)
class BPMNErrorConverterDiffblueTest {
  @Autowired
  private BPMNErrorConverter bPMNErrorConverter;

  /**
   * Method under test:
   * {@link BPMNErrorConverter#convertToBPMNError(ActivitiErrorEvent)}
   */
  @Test
  void testConvertToBPMNError() {
    // Arrange and Act
    BPMNErrorImpl actualConvertToBPMNErrorResult = bPMNErrorConverter
        .convertToBPMNError(new ActivitiErrorEventImpl(ActivitiEventType.ENTITY_CREATED));

    // Assert
    assertNull(actualConvertToBPMNErrorResult.getActivityName());
    assertNull(actualConvertToBPMNErrorResult.getActivityType());
    assertNull(actualConvertToBPMNErrorResult.getExecutionId());
    assertNull(actualConvertToBPMNErrorResult.getElementId());
    assertNull(actualConvertToBPMNErrorResult.getProcessDefinitionId());
    assertNull(actualConvertToBPMNErrorResult.getProcessInstanceId());
    assertNull(actualConvertToBPMNErrorResult.getErrorCode());
    assertNull(actualConvertToBPMNErrorResult.getErrorId());
  }

  /**
   * Method under test:
   * {@link BPMNErrorConverter#convertToBPMNError(ActivitiErrorEvent)}
   */
  @Test
  void testConvertToBPMNError2() {
    // Arrange
    ActivitiErrorEventImpl internalEvent = mock(ActivitiErrorEventImpl.class);
    when(internalEvent.getActivityId()).thenReturn("42");
    when(internalEvent.getActivityName()).thenReturn("Activity Name");
    when(internalEvent.getActivityType()).thenReturn("Activity Type");
    when(internalEvent.getErrorCode()).thenReturn("An error occurred");
    when(internalEvent.getErrorId()).thenReturn("An error occurred");
    when(internalEvent.getExecutionId()).thenReturn("42");
    when(internalEvent.getProcessDefinitionId()).thenReturn("42");
    when(internalEvent.getProcessInstanceId()).thenReturn("42");

    // Act
    BPMNErrorImpl actualConvertToBPMNErrorResult = bPMNErrorConverter.convertToBPMNError(internalEvent);

    // Assert
    verify(internalEvent).getActivityId();
    verify(internalEvent).getActivityName();
    verify(internalEvent).getActivityType();
    verify(internalEvent).getErrorCode();
    verify(internalEvent).getErrorId();
    verify(internalEvent).getExecutionId();
    verify(internalEvent).getProcessDefinitionId();
    verify(internalEvent).getProcessInstanceId();
    assertEquals("42", actualConvertToBPMNErrorResult.getExecutionId());
    assertEquals("42", actualConvertToBPMNErrorResult.getElementId());
    assertEquals("42", actualConvertToBPMNErrorResult.getProcessDefinitionId());
    assertEquals("42", actualConvertToBPMNErrorResult.getProcessInstanceId());
    assertEquals("Activity Name", actualConvertToBPMNErrorResult.getActivityName());
    assertEquals("Activity Type", actualConvertToBPMNErrorResult.getActivityType());
    assertEquals("An error occurred", actualConvertToBPMNErrorResult.getErrorCode());
    assertEquals("An error occurred", actualConvertToBPMNErrorResult.getErrorId());
  }
}
