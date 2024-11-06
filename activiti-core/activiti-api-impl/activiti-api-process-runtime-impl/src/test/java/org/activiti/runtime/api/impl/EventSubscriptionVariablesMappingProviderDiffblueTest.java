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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import org.activiti.engine.impl.bpmn.behavior.VariablesCalculator;
import org.activiti.engine.impl.persistence.entity.CompensateEventSubscriptionEntityImpl;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EventSubscriptionVariablesMappingProvider.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class EventSubscriptionVariablesMappingProviderDiffblueTest {
  @Autowired
  private EventSubscriptionVariablesMappingProvider eventSubscriptionVariablesMappingProvider;

  @MockBean
  private VariablesCalculator variablesCalculator;

  /**
   * Test
   * {@link EventSubscriptionVariablesMappingProvider#apply(Object, EventSubscriptionEntity)}.
   * <ul>
   *   <li>When {@link CompensateEventSubscriptionEntityImpl}.</li>
   *   <li>Then return {@code Payload}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubscriptionVariablesMappingProvider#apply(Object, EventSubscriptionEntity)}
   */
  @Test
  @DisplayName("Test apply(Object, EventSubscriptionEntity); when CompensateEventSubscriptionEntityImpl; then return 'Payload'")
  void testApply_whenCompensateEventSubscriptionEntityImpl_thenReturnPayload() {
    // Arrange, Act and Assert
    assertEquals("Payload",
        eventSubscriptionVariablesMappingProvider.apply("Payload", mock(CompensateEventSubscriptionEntityImpl.class)));
  }

  /**
   * Test
   * {@link EventSubscriptionVariablesMappingProvider#apply(Object, EventSubscriptionEntity)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code Payload}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EventSubscriptionVariablesMappingProvider#apply(Object, EventSubscriptionEntity)}
   */
  @Test
  @DisplayName("Test apply(Object, EventSubscriptionEntity); when 'null'; then return 'Payload'")
  void testApply_whenNull_thenReturnPayload() {
    // Arrange, Act and Assert
    assertEquals("Payload", eventSubscriptionVariablesMappingProvider.apply("Payload", null));
  }
}
