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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Optional;
import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.api.process.runtime.events.ProcessStartedEvent;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiProcessStartedEvent;
import org.activiti.engine.delegate.event.impl.ActivitiProcessStartedEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ToAPIProcessStartedEventConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ToAPIProcessStartedEventConverterDiffblueTest {
  @MockBean
  private APIProcessInstanceConverter aPIProcessInstanceConverter;

  @Autowired
  private ToAPIProcessStartedEventConverter toAPIProcessStartedEventConverter;

  /**
   * Test
   * {@link ToAPIProcessStartedEventConverter#from(ActivitiProcessStartedEvent)}
   * with {@code ActivitiProcessStartedEvent}.
   * <ul>
   *   <li>Then {@link Optional#get()} return {@link ProcessStartedEventImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ToAPIProcessStartedEventConverter#from(ActivitiProcessStartedEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiProcessStartedEvent) with 'ActivitiProcessStartedEvent'; then get() return ProcessStartedEventImpl")
  void testFromWithActivitiProcessStartedEvent_thenGetReturnProcessStartedEventImpl() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult = ExecutionEntityImpl
        .createWithEmptyRelationshipCollections();

    // Act
    Optional<ProcessStartedEvent> actualFromResult = toAPIProcessStartedEventConverter
        .from(new ActivitiProcessStartedEventImpl(createWithEmptyRelationshipCollectionsResult, new HashMap<>(), true));

    // Assert
    verify(aPIProcessInstanceConverter).from((org.activiti.engine.runtime.ProcessInstance) isNull());
    ProcessStartedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof ProcessStartedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertNull(getResult.getNestedProcessDefinitionId());
    assertNull(getResult.getNestedProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_STARTED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(processInstanceImpl, getResult.getEntity());
  }
}
