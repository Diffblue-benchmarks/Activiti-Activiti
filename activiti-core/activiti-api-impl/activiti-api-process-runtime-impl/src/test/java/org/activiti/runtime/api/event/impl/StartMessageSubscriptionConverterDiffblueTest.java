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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.runtime.model.impl.StartMessageSubscriptionImpl;
import org.activiti.engine.impl.persistence.entity.MessageEventSubscriptionEntity;
import org.activiti.engine.impl.persistence.entity.MessageEventSubscriptionEntityImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StartMessageSubscriptionConverter.class})
@ExtendWith(SpringExtension.class)
class StartMessageSubscriptionConverterDiffblueTest {
  @Autowired
  private StartMessageSubscriptionConverter startMessageSubscriptionConverter;

  /**
   * Test
   * {@link StartMessageSubscriptionConverter#convertToStartMessageSubscription(MessageEventSubscriptionEntity)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageSubscriptionConverter#convertToStartMessageSubscription(MessageEventSubscriptionEntity)}
   */
  @Test
  @DisplayName("Test convertToStartMessageSubscription(MessageEventSubscriptionEntity); given '42'; then return ActivityId is '42'")
  void testConvertToStartMessageSubscription_given42_thenReturnActivityIdIs42() {
    // Arrange
    MessageEventSubscriptionEntityImpl messageEventSubscriptionEntity = mock(MessageEventSubscriptionEntityImpl.class);
    when(messageEventSubscriptionEntity.getId()).thenReturn("42");
    when(messageEventSubscriptionEntity.getActivityId()).thenReturn("42");
    when(messageEventSubscriptionEntity.getConfiguration()).thenReturn("Configuration");
    when(messageEventSubscriptionEntity.getEventName()).thenReturn("Event Name");
    when(messageEventSubscriptionEntity.getProcessDefinitionId()).thenReturn("42");
    when(messageEventSubscriptionEntity.getCreated())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    StartMessageSubscriptionImpl actualConvertToStartMessageSubscriptionResult = startMessageSubscriptionConverter
        .convertToStartMessageSubscription(messageEventSubscriptionEntity);

    // Assert
    verify(messageEventSubscriptionEntity).getId();
    verify(messageEventSubscriptionEntity).getActivityId();
    verify(messageEventSubscriptionEntity).getConfiguration();
    verify(messageEventSubscriptionEntity).getCreated();
    verify(messageEventSubscriptionEntity).getEventName();
    verify(messageEventSubscriptionEntity).getProcessDefinitionId();
    assertEquals("42", actualConvertToStartMessageSubscriptionResult.getActivityId());
    assertEquals("42", actualConvertToStartMessageSubscriptionResult.getId());
    assertEquals("42", actualConvertToStartMessageSubscriptionResult.getProcessDefinitionId());
    assertEquals("Configuration", actualConvertToStartMessageSubscriptionResult.getConfiguration());
    assertEquals("Event Name", actualConvertToStartMessageSubscriptionResult.getEventName());
  }
}
