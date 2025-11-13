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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.impl.bpmn.behavior.MappingExecutionContext;
import org.activiti.engine.impl.bpmn.behavior.VariablesCalculator;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EventSubscriptionVariablesMappingProvider.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class EventSubscriptionVariablesMappingProviderDiffblueTest {
  @Autowired
  private EventSubscriptionVariablesMappingProvider eventSubscriptionVariablesMappingProvider;

  @MockBean private VariablesCalculator variablesCalculator;

  /**
   * Test {@link EventSubscriptionVariablesMappingProvider#apply(Object, EventSubscriptionEntity)}.
   *
   * <ul>
   *   <li>Given {@link VariablesCalculator}.
   *   <li>When {@code Payload}.
   *   <li>Then return {@code Payload}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionVariablesMappingProvider#apply(Object,
   * EventSubscriptionEntity)}
   */
  @Test
  @DisplayName(
      "Test apply(Object, EventSubscriptionEntity); given VariablesCalculator; when 'Payload'; then return 'Payload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object EventSubscriptionVariablesMappingProvider.apply(Object, EventSubscriptionEntity)"
  })
  void testApply_givenVariablesCalculator_whenPayload_thenReturnPayload() {
    // Arrange, Act and Assert
    assertEquals("Payload", eventSubscriptionVariablesMappingProvider.apply("Payload", null));
  }

  /**
   * Test {@link EventSubscriptionVariablesMappingProvider#apply(Object, EventSubscriptionEntity)}.
   *
   * <ul>
   *   <li>Then return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link EventSubscriptionVariablesMappingProvider#apply(Object,
   * EventSubscriptionEntity)}
   */
  @Test
  @DisplayName("Test apply(Object, EventSubscriptionEntity); then return Map")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object EventSubscriptionVariablesMappingProvider.apply(Object, EventSubscriptionEntity)"
  })
  void testApply_thenReturnMap() {
    // Arrange
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(variablesCalculator.calculateOutPutVariables(
            Mockito.<MappingExecutionContext>any(), Mockito.<Map<String, Object>>any()))
        .thenReturn(stringObjectMap);
    HashMap<Object, Object> objectObjectMap = new HashMap<>();

    EventSubscriptionEntity eventSubscription = mock(EventSubscriptionEntity.class);
    when(eventSubscription.getActivityId()).thenReturn("42");
    when(eventSubscription.getProcessDefinitionId()).thenReturn("42");

    // Act
    Object actualApplyResult =
        eventSubscriptionVariablesMappingProvider.apply(objectObjectMap, eventSubscription);

    // Assert
    verify(variablesCalculator)
        .calculateOutPutVariables(isA(MappingExecutionContext.class), isA(Map.class));
    verify(eventSubscription).getActivityId();
    verify(eventSubscription).getProcessDefinitionId();
    assertTrue(actualApplyResult instanceof Map);
    assertTrue(((Map<Object, Object>) actualApplyResult).isEmpty());
    assertSame(stringObjectMap, actualApplyResult);
  }
}
