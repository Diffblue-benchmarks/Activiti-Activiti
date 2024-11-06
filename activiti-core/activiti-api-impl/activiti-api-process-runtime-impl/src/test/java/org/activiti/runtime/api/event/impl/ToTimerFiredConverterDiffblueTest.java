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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ToTimerFiredConverterDiffblueTest {
  /**
   * Test {@link ToTimerFiredConverter#from(ActivitiEvent)}.
   * <ul>
   *   <li>When {@link ActivitiActivityCancelledEventImpl} (default
   * constructor).</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTimerFiredConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEvent); when ActivitiActivityCancelledEventImpl (default constructor); then return not Present")
  void testFrom_whenActivitiActivityCancelledEventImpl_thenReturnNotPresent() {
    // Arrange
    ToTimerFiredConverter toTimerFiredConverter = new ToTimerFiredConverter(new BPMNTimerConverter());

    // Act and Assert
    assertFalse(toTimerFiredConverter.from(new ActivitiActivityCancelledEventImpl()).isPresent());
  }

  /**
   * Test {@link ToTimerFiredConverter#from(ActivitiEvent)}.
   * <ul>
   *   <li>When
   * {@link ActivitiEntityEventImpl#ActivitiEntityEventImpl(Object, ActivitiEventType)}
   * with {@code Entity} and type is {@code ENTITY_CREATED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTimerFiredConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEvent); when ActivitiEntityEventImpl(Object, ActivitiEventType) with 'Entity' and type is 'ENTITY_CREATED'")
  void testFrom_whenActivitiEntityEventImplWithEntityAndTypeIsEntityCreated() {
    // Arrange
    ToTimerFiredConverter toTimerFiredConverter = new ToTimerFiredConverter(new BPMNTimerConverter());

    // Act and Assert
    assertFalse(toTimerFiredConverter.from(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToTimerFiredConverter#from(ActivitiEvent)}.
   * <ul>
   *   <li>When
   * {@link ActivitiProcessCancelledEventImpl#ActivitiProcessCancelledEventImpl(ProcessInstance)}
   * with {@link ProcessInstance}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTimerFiredConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEvent); when ActivitiProcessCancelledEventImpl(ProcessInstance) with ProcessInstance")
  void testFrom_whenActivitiProcessCancelledEventImplWithProcessInstance() {
    // Arrange
    ToTimerFiredConverter toTimerFiredConverter = new ToTimerFiredConverter(new BPMNTimerConverter());

    // Act and Assert
    assertFalse(
        toTimerFiredConverter.from(new ActivitiProcessCancelledEventImpl(mock(ProcessInstance.class))).isPresent());
  }
}
