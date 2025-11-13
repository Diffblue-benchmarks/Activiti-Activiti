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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Optional;
import org.activiti.api.process.model.events.ProcessRuntimeEvent;
import org.activiti.api.process.model.events.ProcessRuntimeEvent.ProcessEvents;
import org.activiti.api.process.runtime.events.ProcessCreatedEvent;
import org.activiti.api.runtime.model.impl.ProcessInstanceImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.runtime.api.model.impl.APIProcessInstanceConverter;
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

@ContextConfiguration(classes = {ToAPIProcessCreatedEventConverter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ToAPIProcessCreatedEventConverterDiffblueTest {
  @MockBean private APIProcessInstanceConverter aPIProcessInstanceConverter;

  @Autowired private ToAPIProcessCreatedEventConverter toAPIProcessCreatedEventConverter;

  /**
   * Test {@link ToAPIProcessCreatedEventConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <p>Method under test: {@link ToAPIProcessCreatedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToAPIProcessCreatedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent() {
    // Arrange, Act and Assert
    assertFalse(
        toAPIProcessCreatedEventConverter
            .from(new ActivitiEntityEventImpl("Entity", ActivitiEventType.ENTITY_CREATED))
            .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCreatedEventConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <p>Method under test: {@link ToAPIProcessCreatedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToAPIProcessCreatedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent2() {
    // Arrange
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setParentId("42");

    ActivitiEntityEvent internalEvent = mock(ActivitiEntityEvent.class);
    when(internalEvent.getEntity()).thenReturn(createWithEmptyRelationshipCollectionsResult);

    // Act
    Optional<ProcessCreatedEvent> actualFromResult =
        toAPIProcessCreatedEventConverter.from(internalEvent);

    // Assert
    verify(internalEvent).getEntity();
    assertFalse(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToAPIProcessCreatedEventConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <p>Method under test: {@link ToAPIProcessCreatedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToAPIProcessCreatedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent3() {
    // Arrange
    ToAPIProcessCreatedEventConverter toAPIProcessCreatedEventConverter =
        new ToAPIProcessCreatedEventConverter(mock(APIProcessInstanceConverter.class));

    ActivitiEntityEvent internalEvent = mock(ActivitiEntityEvent.class);
    when(internalEvent.getEntity()).thenReturn(null);

    // Act
    Optional<ProcessCreatedEvent> actualFromResult =
        toAPIProcessCreatedEventConverter.from(internalEvent);

    // Assert
    verify(internalEvent).getEntity();
    assertFalse(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToAPIProcessCreatedEventConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <ul>
   *   <li>Then {@link Optional#get()} Entity return {@link ProcessInstanceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ToAPIProcessCreatedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; then get() Entity return ProcessInstanceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Optional ToAPIProcessCreatedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_thenGetEntityReturnProcessInstanceImpl() {
    // Arrange
    ProcessInstanceImpl processInstanceImpl = new ProcessInstanceImpl();
    when(aPIProcessInstanceConverter.from(Mockito.<ProcessInstance>any()))
        .thenReturn(processInstanceImpl);

    // Act
    Optional<ProcessCreatedEvent> actualFromResult =
        toAPIProcessCreatedEventConverter.from(
            new ActivitiProcessCancelledEventImpl(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));

    // Assert
    verify(aPIProcessInstanceConverter).from((ProcessInstance) isNull());
    ProcessCreatedEvent getResult = actualFromResult.get();
    org.activiti.api.process.model.ProcessInstance entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessInstanceImpl);
    assertTrue(getResult instanceof ProcessCreatedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ProcessEvents.PROCESS_CREATED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(processInstanceImpl, entity);
  }
}
