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
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;

class ToTimerExecutedConverterDiffblueTest {
  /**
   * Method under test: {@link ToTimerExecutedConverter#from(ActivitiEvent)}
   */
  @Test
  void testFrom() {
    // Arrange
    ToTimerExecutedConverter toTimerExecutedConverter = new ToTimerExecutedConverter(new BPMNTimerConverter());

    // Act and Assert
    assertFalse(toTimerExecutedConverter.from(new ActivitiActivityCancelledEventImpl()).isPresent());
  }

  /**
   * Method under test: {@link ToTimerExecutedConverter#from(ActivitiEvent)}
   */
  @Test
  void testFrom2() {
    // Arrange
    ToTimerExecutedConverter toTimerExecutedConverter = new ToTimerExecutedConverter(new BPMNTimerConverter());

    // Act and Assert
    assertFalse(toTimerExecutedConverter.from(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Method under test: {@link ToTimerExecutedConverter#from(ActivitiEvent)}
   */
  @Test
  void testFrom3() {
    // Arrange
    ToTimerExecutedConverter toTimerExecutedConverter = new ToTimerExecutedConverter(new BPMNTimerConverter());

    // Act and Assert
    assertFalse(
        toTimerExecutedConverter.from(new ActivitiProcessCancelledEventImpl(mock(ProcessInstance.class))).isPresent());
  }
}
