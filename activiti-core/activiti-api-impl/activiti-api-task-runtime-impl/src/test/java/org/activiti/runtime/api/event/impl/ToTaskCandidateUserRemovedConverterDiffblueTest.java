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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Optional;
import org.activiti.api.task.model.events.TaskCandidateUserEvent;
import org.activiti.api.task.model.events.TaskCandidateUserEvent.TaskCandidateUserEvents;
import org.activiti.api.task.model.impl.TaskCandidateUserImpl;
import org.activiti.api.task.runtime.events.TaskCandidateUserRemovedEvent;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.runtime.api.model.impl.APITaskCandidateUserConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ToTaskCandidateUserRemovedConverterDiffblueTest {
  /**
   * Test {@link ToTaskCandidateUserRemovedConverter#ToTaskCandidateUserRemovedConverter(APITaskCandidateUserConverter)}.
   * <p>
   * Method under test: {@link ToTaskCandidateUserRemovedConverter#ToTaskCandidateUserRemovedConverter(APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName("Test new ToTaskCandidateUserRemovedConverter(APITaskCandidateUserConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ToTaskCandidateUserRemovedConverter.<init>(APITaskCandidateUserConverter)"})
  void testNewToTaskCandidateUserRemovedConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ToTaskCandidateUserRemovedConverter actualToTaskCandidateUserRemovedConverter = new ToTaskCandidateUserRemovedConverter(
        new APITaskCandidateUserConverter());

    // Assert
    assertFalse(actualToTaskCandidateUserRemovedConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateUserRemovedConverter#ToTaskCandidateUserRemovedConverter(APITaskCandidateUserConverter)}.
   * <p>
   * Method under test: {@link ToTaskCandidateUserRemovedConverter#ToTaskCandidateUserRemovedConverter(APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName("Test new ToTaskCandidateUserRemovedConverter(APITaskCandidateUserConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ToTaskCandidateUserRemovedConverter.<init>(APITaskCandidateUserConverter)"})
  void testNewToTaskCandidateUserRemovedConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ToTaskCandidateUserRemovedConverter actualToTaskCandidateUserRemovedConverter = new ToTaskCandidateUserRemovedConverter(
        new APITaskCandidateUserConverter());
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setTaskId(null);

    // Assert
    assertFalse(actualToTaskCandidateUserRemovedConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateUserRemovedConverter#ToTaskCandidateUserRemovedConverter(APITaskCandidateUserConverter)}.
   * <p>
   * Method under test: {@link ToTaskCandidateUserRemovedConverter#ToTaskCandidateUserRemovedConverter(APITaskCandidateUserConverter)}
   */
  @Test
  @DisplayName("Test new ToTaskCandidateUserRemovedConverter(APITaskCandidateUserConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ToTaskCandidateUserRemovedConverter.<init>(APITaskCandidateUserConverter)"})
  void testNewToTaskCandidateUserRemovedConverter3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ToTaskCandidateUserRemovedConverter actualToTaskCandidateUserRemovedConverter = new ToTaskCandidateUserRemovedConverter(
        new APITaskCandidateUserConverter());
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setTaskId("Entity");

    // Assert
    assertFalse(actualToTaskCandidateUserRemovedConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateUserRemovedConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToTaskCandidateUserRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTaskCandidateUserRemovedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent() {
    // Arrange
    ToTaskCandidateUserRemovedConverter toTaskCandidateUserRemovedConverter = new ToTaskCandidateUserRemovedConverter(
        new APITaskCandidateUserConverter());

    // Act and Assert
    assertFalse(toTaskCandidateUserRemovedConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateUserRemovedConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then {@link Optional#get()} Entity return {@link TaskCandidateUserImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTaskCandidateUserRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given '42'; then get() Entity return TaskCandidateUserImpl")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTaskCandidateUserRemovedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_given42_thenGetEntityReturnTaskCandidateUserImpl() {
    // Arrange
    ToTaskCandidateUserRemovedConverter toTaskCandidateUserRemovedConverter = new ToTaskCandidateUserRemovedConverter(
        new APITaskCandidateUserConverter());

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setUserId("42");
    identityLinkEntityImpl.setTaskId("Entity");

    // Act
    Optional<TaskCandidateUserRemovedEvent> actualFromResult = toTaskCandidateUserRemovedConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    TaskCandidateUserRemovedEvent getResult = actualFromResult.get();
    assertTrue(getResult.getEntity() instanceof TaskCandidateUserImpl);
    assertTrue(getResult instanceof TaskCandidateUserRemovedImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(TaskCandidateUserEvents.TASK_CANDIDATE_USER_REMOVED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToTaskCandidateUserRemovedConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code candidate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTaskCandidateUserRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'candidate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTaskCandidateUserRemovedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenCandidate() {
    // Arrange
    ToTaskCandidateUserRemovedConverter toTaskCandidateUserRemovedConverter = new ToTaskCandidateUserRemovedConverter(
        new APITaskCandidateUserConverter());

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setTaskId("Entity");

    // Act and Assert
    assertFalse(toTaskCandidateUserRemovedConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateUserRemovedConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) TaskId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTaskCandidateUserRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'null'; when IdentityLinkEntityImpl (default constructor) TaskId is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTaskCandidateUserRemovedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenNull_whenIdentityLinkEntityImplTaskIdIsNull() {
    // Arrange
    ToTaskCandidateUserRemovedConverter toTaskCandidateUserRemovedConverter = new ToTaskCandidateUserRemovedConverter(
        new APITaskCandidateUserConverter());

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setTaskId(null);

    // Act and Assert
    assertFalse(toTaskCandidateUserRemovedConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateUserRemovedConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) UserId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTaskCandidateUserRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'null'; when IdentityLinkEntityImpl (default constructor) UserId is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTaskCandidateUserRemovedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenNull_whenIdentityLinkEntityImplUserIdIsNull() {
    // Arrange
    ToTaskCandidateUserRemovedConverter toTaskCandidateUserRemovedConverter = new ToTaskCandidateUserRemovedConverter(
        new APITaskCandidateUserConverter());

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setTaskId("Entity");

    // Act and Assert
    assertFalse(toTaskCandidateUserRemovedConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }
}
