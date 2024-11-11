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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ToMessageSubscriptionCancelledConverterDiffblueTest {
  /**
   * Test
   * {@link ToMessageSubscriptionCancelledConverter#from(ActivitiEntityEvent)}
   * with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test:
   * {@link ToMessageSubscriptionCancelledConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  void testFromWithActivitiEntityEvent() {
    // Arrange
    ToMessageSubscriptionCancelledConverter toMessageSubscriptionCancelledConverter = new ToMessageSubscriptionCancelledConverter(
        new MessageSubscriptionConverter());

    // Act and Assert
    assertFalse(
        toMessageSubscriptionCancelledConverter.from(new ActivitiProcessCancelledEventImpl(mock(ProcessInstance.class)))
            .isPresent());
  }

  /**
   * Test
   * {@link ToMessageSubscriptionCancelledConverter#from(ActivitiEntityEvent)}
   * with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ToMessageSubscriptionCancelledConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; then return not Present")
  void testFromWithActivitiEntityEvent_thenReturnNotPresent() {
    // Arrange
    ToMessageSubscriptionCancelledConverter toMessageSubscriptionCancelledConverter = new ToMessageSubscriptionCancelledConverter(
        new MessageSubscriptionConverter());

    // Act and Assert
    assertFalse(toMessageSubscriptionCancelledConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }
}