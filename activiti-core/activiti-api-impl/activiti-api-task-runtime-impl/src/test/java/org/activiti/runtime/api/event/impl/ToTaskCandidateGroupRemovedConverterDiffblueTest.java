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
import org.activiti.api.task.model.events.TaskCandidateGroupEvent;
import org.activiti.api.task.model.events.TaskCandidateGroupEvent.TaskCandidateGroupEvents;
import org.activiti.api.task.model.impl.TaskCandidateGroupImpl;
import org.activiti.api.task.runtime.events.TaskCandidateGroupRemovedEvent;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.runtime.api.model.impl.APITaskCandidateGroupConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ToTaskCandidateGroupRemovedConverterDiffblueTest {
  /**
   * Test {@link ToTaskCandidateGroupRemovedConverter#ToTaskCandidateGroupRemovedConverter(APITaskCandidateGroupConverter)}.
   * <p>
   * Method under test: {@link ToTaskCandidateGroupRemovedConverter#ToTaskCandidateGroupRemovedConverter(APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName("Test new ToTaskCandidateGroupRemovedConverter(APITaskCandidateGroupConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ToTaskCandidateGroupRemovedConverter.<init>(APITaskCandidateGroupConverter)"})
  void testNewToTaskCandidateGroupRemovedConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ToTaskCandidateGroupRemovedConverter actualToTaskCandidateGroupRemovedConverter = new ToTaskCandidateGroupRemovedConverter(
        new APITaskCandidateGroupConverter());

    // Assert
    assertFalse(actualToTaskCandidateGroupRemovedConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateGroupRemovedConverter#ToTaskCandidateGroupRemovedConverter(APITaskCandidateGroupConverter)}.
   * <p>
   * Method under test: {@link ToTaskCandidateGroupRemovedConverter#ToTaskCandidateGroupRemovedConverter(APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName("Test new ToTaskCandidateGroupRemovedConverter(APITaskCandidateGroupConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ToTaskCandidateGroupRemovedConverter.<init>(APITaskCandidateGroupConverter)"})
  void testNewToTaskCandidateGroupRemovedConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ToTaskCandidateGroupRemovedConverter actualToTaskCandidateGroupRemovedConverter = new ToTaskCandidateGroupRemovedConverter(
        new APITaskCandidateGroupConverter());
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setTaskId(null);
    identityLinkEntityImpl.setGroupId(null);

    // Assert
    assertFalse(actualToTaskCandidateGroupRemovedConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateGroupRemovedConverter#ToTaskCandidateGroupRemovedConverter(APITaskCandidateGroupConverter)}.
   * <p>
   * Method under test: {@link ToTaskCandidateGroupRemovedConverter#ToTaskCandidateGroupRemovedConverter(APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName("Test new ToTaskCandidateGroupRemovedConverter(APITaskCandidateGroupConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ToTaskCandidateGroupRemovedConverter.<init>(APITaskCandidateGroupConverter)"})
  void testNewToTaskCandidateGroupRemovedConverter3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ToTaskCandidateGroupRemovedConverter actualToTaskCandidateGroupRemovedConverter = new ToTaskCandidateGroupRemovedConverter(
        new APITaskCandidateGroupConverter());
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setTaskId("Entity");
    identityLinkEntityImpl.setGroupId(null);

    // Assert
    assertFalse(actualToTaskCandidateGroupRemovedConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateGroupRemovedConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToTaskCandidateGroupRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTaskCandidateGroupRemovedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent() {
    // Arrange
    ToTaskCandidateGroupRemovedConverter toTaskCandidateGroupRemovedConverter = new ToTaskCandidateGroupRemovedConverter(
        new APITaskCandidateGroupConverter());

    // Act and Assert
    assertFalse(toTaskCandidateGroupRemovedConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateGroupRemovedConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code candidate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTaskCandidateGroupRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'candidate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTaskCandidateGroupRemovedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenCandidate() {
    // Arrange
    ToTaskCandidateGroupRemovedConverter toTaskCandidateGroupRemovedConverter = new ToTaskCandidateGroupRemovedConverter(
        new APITaskCandidateGroupConverter());

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setTaskId("Entity");
    identityLinkEntityImpl.setGroupId(null);

    // Act and Assert
    assertFalse(toTaskCandidateGroupRemovedConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateGroupRemovedConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTaskCandidateGroupRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTaskCandidateGroupRemovedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenNull() {
    // Arrange
    ToTaskCandidateGroupRemovedConverter toTaskCandidateGroupRemovedConverter = new ToTaskCandidateGroupRemovedConverter(
        new APITaskCandidateGroupConverter());

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setTaskId("Entity");
    identityLinkEntityImpl.setGroupId(null);

    // Act and Assert
    assertFalse(toTaskCandidateGroupRemovedConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateGroupRemovedConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) TaskId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTaskCandidateGroupRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'null'; when IdentityLinkEntityImpl (default constructor) TaskId is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTaskCandidateGroupRemovedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenNull_whenIdentityLinkEntityImplTaskIdIsNull() {
    // Arrange
    ToTaskCandidateGroupRemovedConverter toTaskCandidateGroupRemovedConverter = new ToTaskCandidateGroupRemovedConverter(
        new APITaskCandidateGroupConverter());

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setTaskId(null);
    identityLinkEntityImpl.setGroupId(null);

    // Act and Assert
    assertFalse(toTaskCandidateGroupRemovedConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToTaskCandidateGroupRemovedConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Then {@link Optional#get()} Entity return {@link TaskCandidateGroupImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToTaskCandidateGroupRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; then get() Entity return TaskCandidateGroupImpl")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToTaskCandidateGroupRemovedConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_thenGetEntityReturnTaskCandidateGroupImpl() {
    // Arrange
    ToTaskCandidateGroupRemovedConverter toTaskCandidateGroupRemovedConverter = new ToTaskCandidateGroupRemovedConverter(
        new APITaskCandidateGroupConverter());

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setTaskId("Entity");
    identityLinkEntityImpl.setGroupId("42");

    // Act
    Optional<TaskCandidateGroupRemovedEvent> actualFromResult = toTaskCandidateGroupRemovedConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    TaskCandidateGroupRemovedEvent getResult = actualFromResult.get();
    assertTrue(getResult.getEntity() instanceof TaskCandidateGroupImpl);
    assertTrue(getResult instanceof TaskCandidateGroupRemovedImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(TaskCandidateGroupEvents.TASK_CANDIDATE_GROUP_REMOVED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }
}
