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
import org.activiti.api.task.model.TaskCandidateUser;
import org.activiti.api.task.model.events.TaskCandidateUserEvent;
import org.activiti.api.task.model.events.TaskCandidateUserEvent.TaskCandidateUserEvents;
import org.activiti.api.task.model.impl.TaskCandidateUserImpl;
import org.activiti.api.task.runtime.events.TaskCandidateUserAddedEvent;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.activiti.runtime.api.model.impl.APITaskCandidateUserConverter;
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

@ContextConfiguration(classes = {ToAPITaskCandidateUserAddedEventConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ToAPITaskCandidateUserAddedEventConverterDiffblueTest {
  @MockBean
  private APITaskCandidateUserConverter aPITaskCandidateUserConverter;

  @Autowired
  private ToAPITaskCandidateUserAddedEventConverter toAPITaskCandidateUserAddedEventConverter;

  /**
   * Test {@link ToAPITaskCandidateUserAddedEventConverter#ToAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)}.
   * <p>
   * Method under test: {@link ToAPITaskCandidateUserAddedEventConverter#ToAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName("Test new ToAPITaskCandidateUserAddedEventConverter(APITaskCandidateUserConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ToAPITaskCandidateUserAddedEventConverter.<init>(APITaskCandidateUserConverter)"})
  void testNewToAPITaskCandidateUserAddedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ToAPITaskCandidateUserAddedEventConverter actualToAPITaskCandidateUserAddedEventConverter = new ToAPITaskCandidateUserAddedEventConverter(
        new APITaskCandidateUserConverter());

    // Assert
    assertFalse(actualToAPITaskCandidateUserAddedEventConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateUserAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToAPITaskCandidateUserAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPITaskCandidateUserAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent() {
    // Arrange, Act and Assert
    assertFalse(toAPITaskCandidateUserAddedEventConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateUserAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToAPITaskCandidateUserAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPITaskCandidateUserAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent2() {
    // Arrange
    TaskCandidateUserImpl taskCandidateUserImpl = new TaskCandidateUserImpl("42", "42");

    when(aPITaskCandidateUserConverter.from(Mockito.<IdentityLink>any())).thenReturn(taskCandidateUserImpl);

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setUserId("42");
    identityLinkEntityImpl.setTaskId("Entity");

    // Act
    Optional<TaskCandidateUserAddedEvent> actualFromResult = toAPITaskCandidateUserAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(aPITaskCandidateUserConverter).from(isA(IdentityLink.class));
    TaskCandidateUserAddedEvent getResult = actualFromResult.get();
    TaskCandidateUser entity = getResult.getEntity();
    assertTrue(entity instanceof TaskCandidateUserImpl);
    assertTrue(getResult instanceof TaskCandidateUserAddedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(TaskCandidateUserEvents.TASK_CANDIDATE_USER_ADDED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(taskCandidateUserImpl, entity);
  }

  /**
   * Test {@link ToAPITaskCandidateUserAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToAPITaskCandidateUserAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPITaskCandidateUserAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent3() {
    // Arrange
    ToAPITaskCandidateUserAddedEventConverter toAPITaskCandidateUserAddedEventConverter = new ToAPITaskCandidateUserAddedEventConverter(
        new APITaskCandidateUserConverter());

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setUserId("42");
    identityLinkEntityImpl.setTaskId("Entity");

    // Act
    Optional<TaskCandidateUserAddedEvent> actualFromResult = toAPITaskCandidateUserAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    TaskCandidateUserAddedEvent getResult = actualFromResult.get();
    assertTrue(getResult.getEntity() instanceof TaskCandidateUserImpl);
    assertTrue(getResult instanceof TaskCandidateUserAddedEventImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(TaskCandidateUserEvents.TASK_CANDIDATE_USER_ADDED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateUserAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code candidate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToAPITaskCandidateUserAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'candidate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPITaskCandidateUserAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenCandidate() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setTaskId("Entity");

    // Act and Assert
    assertFalse(toAPITaskCandidateUserAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateUserAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) TaskId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToAPITaskCandidateUserAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'null'; when IdentityLinkEntityImpl (default constructor) TaskId is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPITaskCandidateUserAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenNull_whenIdentityLinkEntityImplTaskIdIsNull() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setTaskId(null);

    // Act and Assert
    assertFalse(toAPITaskCandidateUserAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateUserAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) UserId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToAPITaskCandidateUserAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'null'; when IdentityLinkEntityImpl (default constructor) UserId is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPITaskCandidateUserAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenNull_whenIdentityLinkEntityImplUserIdIsNull() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setTaskId("Entity");

    // Act and Assert
    assertFalse(toAPITaskCandidateUserAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }
}
