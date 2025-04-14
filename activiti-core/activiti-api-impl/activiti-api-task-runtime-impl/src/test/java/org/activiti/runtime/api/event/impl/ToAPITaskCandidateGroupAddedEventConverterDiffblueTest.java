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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Optional;
import org.activiti.api.task.model.TaskCandidateGroup;
import org.activiti.api.task.model.events.TaskCandidateGroupEvent;
import org.activiti.api.task.model.events.TaskCandidateGroupEvent.TaskCandidateGroupEvents;
import org.activiti.api.task.model.impl.TaskCandidateGroupImpl;
import org.activiti.api.task.runtime.events.TaskCandidateGroupAddedEvent;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.activiti.runtime.api.model.impl.APITaskCandidateGroupConverter;
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

@ContextConfiguration(classes = {ToAPITaskCandidateGroupAddedEventConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ToAPITaskCandidateGroupAddedEventConverterDiffblueTest {
  @MockBean
  private APITaskCandidateGroupConverter aPITaskCandidateGroupConverter;

  @Autowired
  private ToAPITaskCandidateGroupAddedEventConverter toAPITaskCandidateGroupAddedEventConverter;

  /**
   * Test {@link ToAPITaskCandidateGroupAddedEventConverter#ToAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)}.
   * <p>
   * Method under test: {@link ToAPITaskCandidateGroupAddedEventConverter#ToAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName("Test new ToAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ToAPITaskCandidateGroupAddedEventConverter.<init>(APITaskCandidateGroupConverter)"})
  void testNewToAPITaskCandidateGroupAddedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ToAPITaskCandidateGroupAddedEventConverter actualToAPITaskCandidateGroupAddedEventConverter = new ToAPITaskCandidateGroupAddedEventConverter(
        new APITaskCandidateGroupConverter());

    // Assert
    assertFalse(actualToAPITaskCandidateGroupAddedEventConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPITaskCandidateGroupAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent() {
    // Arrange, Act and Assert
    assertFalse(toAPITaskCandidateGroupAddedEventConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPITaskCandidateGroupAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent2() {
    // Arrange
    TaskCandidateGroupImpl taskCandidateGroupImpl = new TaskCandidateGroupImpl("42", "42");

    when(aPITaskCandidateGroupConverter.from(Mockito.<IdentityLink>any())).thenReturn(taskCandidateGroupImpl);

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setTaskId("Entity");
    identityLinkEntityImpl.setGroupId("42");

    // Act
    Optional<TaskCandidateGroupAddedEvent> actualFromResult = toAPITaskCandidateGroupAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(aPITaskCandidateGroupConverter).from(isA(IdentityLink.class));
    TaskCandidateGroupAddedEvent getResult = actualFromResult.get();
    TaskCandidateGroup entity = getResult.getEntity();
    assertTrue(entity instanceof TaskCandidateGroupImpl);
    assertTrue(getResult instanceof TaskCandidateGroupAddedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(TaskCandidateGroupEvents.TASK_CANDIDATE_GROUP_ADDED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(taskCandidateGroupImpl, entity);
  }

  /**
   * Test {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPITaskCandidateGroupAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent3() {
    // Arrange
    ToAPITaskCandidateGroupAddedEventConverter toAPITaskCandidateGroupAddedEventConverter = new ToAPITaskCandidateGroupAddedEventConverter(
        new APITaskCandidateGroupConverter());

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setTaskId("Entity");
    identityLinkEntityImpl.setGroupId("42");

    // Act
    Optional<TaskCandidateGroupAddedEvent> actualFromResult = toAPITaskCandidateGroupAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    TaskCandidateGroupAddedEvent getResult = actualFromResult.get();
    assertTrue(getResult.getEntity() instanceof TaskCandidateGroupImpl);
    assertTrue(getResult instanceof TaskCandidateGroupAddedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(TaskCandidateGroupEvents.TASK_CANDIDATE_GROUP_ADDED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code candidate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'candidate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPITaskCandidateGroupAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenCandidate() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setTaskId("Entity");
    identityLinkEntityImpl.setGroupId(null);

    // Act and Assert
    assertFalse(toAPITaskCandidateGroupAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPITaskCandidateGroupAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenNull() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setTaskId("Entity");
    identityLinkEntityImpl.setGroupId(null);

    // Act and Assert
    assertFalse(toAPITaskCandidateGroupAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) TaskId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'null'; when IdentityLinkEntityImpl (default constructor) TaskId is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPITaskCandidateGroupAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenNull_whenIdentityLinkEntityImplTaskIdIsNull() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setTaskId(null);
    identityLinkEntityImpl.setGroupId(null);

    // Act and Assert
    assertFalse(toAPITaskCandidateGroupAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }
}
