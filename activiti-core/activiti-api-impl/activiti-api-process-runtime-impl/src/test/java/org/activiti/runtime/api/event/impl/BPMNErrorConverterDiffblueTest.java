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
import org.activiti.api.runtime.model.impl.BPMNErrorImpl;
import org.activiti.engine.delegate.event.ActivitiErrorEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiErrorEventImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BPMNErrorConverter.class})
@ExtendWith(SpringExtension.class)
class BPMNErrorConverterDiffblueTest {
  @Autowired private BPMNErrorConverter bPMNErrorConverter;

  /**
   * Test {@link BPMNErrorConverter#convertToBPMNError(ActivitiErrorEvent)}.
   *
   * <ul>
   *   <li>Then return ActivityName is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNErrorConverter#convertToBPMNError(ActivitiErrorEvent)}
   */
  @Test
  @DisplayName("Test convertToBPMNError(ActivitiErrorEvent); then return ActivityName is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BPMNErrorImpl BPMNErrorConverter.convertToBPMNError(ActivitiErrorEvent)"})
  void testConvertToBPMNError_thenReturnActivityNameIsNull() {
    // Arrange and Act
    BPMNErrorImpl actualConvertToBPMNErrorResult =
        bPMNErrorConverter.convertToBPMNError(
            new ActivitiErrorEventImpl(ActivitiEventType.ENTITY_CREATED));

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
}
