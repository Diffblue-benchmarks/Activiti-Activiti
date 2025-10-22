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
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ToTimerCancelledConverterDiffblueTest {
  /**
   * Test {@link ToTimerCancelledConverter#from(ActivitiEvent)}.
   * <ul>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTimerCancelledConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEvent); then return not Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Optional ToTimerCancelledConverter.from(ActivitiEvent)"})
  void testFrom_thenReturnNotPresent() {
    // Arrange
    ToTimerCancelledConverter toTimerCancelledConverter = new ToTimerCancelledConverter(new BPMNTimerConverter());

    // Act and Assert
    assertFalse(toTimerCancelledConverter.from(new ActivitiActivityCancelledEventImpl()).isPresent());
  }

  /**
   * Test {@link ToTimerCancelledConverter#from(ActivitiEvent)}.
   * <ul>
   *   <li>When {@link ActivitiEntityEventImpl#ActivitiEntityEventImpl(Object, ActivitiEventType)} with {@code Entity} and type is {@code ENTITY_CREATED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTimerCancelledConverter#from(ActivitiEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEvent); when ActivitiEntityEventImpl(Object, ActivitiEventType) with 'Entity' and type is 'ENTITY_CREATED'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Optional ToTimerCancelledConverter.from(ActivitiEvent)"})
  void testFrom_whenActivitiEntityEventImplWithEntityAndTypeIsEntityCreated() {
    // Arrange
    ToTimerCancelledConverter toTimerCancelledConverter = new ToTimerCancelledConverter(new BPMNTimerConverter());

    // Act and Assert
    assertFalse(toTimerCancelledConverter.from(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }
}
