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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.api.process.runtime.events.ProcessUpdatedEvent;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
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

@ContextConfiguration(classes = {ToProcessUpdatedConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ToProcessUpdatedConverterDiffblueTest {
  @MockBean
  private APIProcessInstanceConverter aPIProcessInstanceConverter;

  @Autowired
  private ToProcessUpdatedConverter toProcessUpdatedConverter;

  /**
   * Test {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)} with
   * {@code ActivitiEntityEvent}.
   * <p>
   * Method under test:
   * {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  void testFromWithActivitiEntityEvent() {
    // Arrange, Act and Assert
    assertFalse(toProcessUpdatedConverter.from(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)} with
   * {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>Then calls {@link ExecutionEntityImpl#isProcessInstanceType()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'false'; then calls isProcessInstanceType()")
  void testFromWithActivitiEntityEvent_givenFalse_thenCallsIsProcessInstanceType() {
    // Arrange
    ExecutionEntityImpl processInstance = mock(ExecutionEntityImpl.class);
    when(processInstance.isProcessInstanceType()).thenReturn(false);

    // Act
    Optional<ProcessUpdatedEvent> actualFromResult = toProcessUpdatedConverter
        .from(new ActivitiProcessCancelledEventImpl(processInstance));

    // Assert
    verify(processInstance).isProcessInstanceType();
    assertFalse(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)} with
   * {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Then {@link Optional#get()} return {@link ProcessUpdatedEventImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ToProcessUpdatedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; then get() return ProcessUpdatedEventImpl")
  void testFromWithActivitiEntityEvent_thenGetReturnProcessUpdatedEventImpl() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<org.activiti.engine.runtime.ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    Optional<ProcessUpdatedEvent> actualFromResult = toProcessUpdatedConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    verify(aPIProcessInstanceConverter).from((org.activiti.engine.runtime.ProcessInstance) isNull());
    ProcessUpdatedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof ProcessUpdatedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ProcessRuntimeEvent.ProcessEvents.PROCESS_UPDATED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(processInstanceImpl, getResult.getEntity());
  }
}
