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
package org.activiti.runtime.api.model.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.process.model.BPMNActivity;
import org.activiti.api.runtime.model.impl.BPMNActivityImpl;
import org.activiti.engine.delegate.event.ActivitiActivityEvent;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ToActivityConverter.class})
@ExtendWith(SpringExtension.class)
class ToActivityConverterDiffblueTest {
  @Autowired private ToActivityConverter toActivityConverter;

  /**
   * Test {@link ToActivityConverter#from(ActivitiActivityEvent)}.
   *
   * <ul>
   *   <li>When {@link ActivitiActivityCancelledEventImpl} (default constructor).
   *   <li>Then return {@link BPMNActivityImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ToActivityConverter#from(ActivitiActivityEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiActivityEvent); when ActivitiActivityCancelledEventImpl (default constructor); then return BPMNActivityImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BPMNActivity ToActivityConverter.from(ActivitiActivityEvent)"})
  void testFrom_whenActivitiActivityCancelledEventImpl_thenReturnBPMNActivityImpl() {
    // Arrange and Act
    BPMNActivity actualFromResult =
        toActivityConverter.from(new ActivitiActivityCancelledEventImpl());

    // Assert
    assertTrue(actualFromResult instanceof BPMNActivityImpl);
    assertNull(actualFromResult.getActivityName());
    assertNull(actualFromResult.getActivityType());
    assertNull(actualFromResult.getExecutionId());
    assertNull(actualFromResult.getElementId());
    assertNull(actualFromResult.getProcessDefinitionId());
    assertNull(actualFromResult.getProcessInstanceId());
  }
}
