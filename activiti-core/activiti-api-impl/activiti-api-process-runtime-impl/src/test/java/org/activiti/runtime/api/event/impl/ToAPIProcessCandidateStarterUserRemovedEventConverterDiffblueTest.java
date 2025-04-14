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
import org.activiti.api.process.model.ProcessCandidateStarterUser;
import org.activiti.api.process.model.events.ProcessCandidateStarterUserEvent;
import org.activiti.api.process.model.events.ProcessCandidateStarterUserEvent.ProcessCandidateStarterUserEvents;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterUserRemovedEvent;
import org.activiti.api.runtime.event.impl.ProcessCandidateStarterUserRemovedEventImpl;
import org.activiti.api.runtime.model.impl.ProcessCandidateStarterUserImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterUserConverter;
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

@ContextConfiguration(classes = {ToAPIProcessCandidateStarterUserRemovedEventConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ToAPIProcessCandidateStarterUserRemovedEventConverterDiffblueTest {
  @MockBean
  private APIProcessCandidateStarterUserConverter aPIProcessCandidateStarterUserConverter;

  @Autowired
  private ToAPIProcessCandidateStarterUserRemovedEventConverter toAPIProcessCandidateStarterUserRemovedEventConverter;

  /**
   * Test {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#ToAPIProcessCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)}.
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#ToAPIProcessCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)}
   */
  @Test
  @DisplayName("Test new ToAPIProcessCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ToAPIProcessCandidateStarterUserRemovedEventConverter.<init>(APIProcessCandidateStarterUserConverter)"})
  void testNewToAPIProcessCandidateStarterUserRemovedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ToAPIProcessCandidateStarterUserRemovedEventConverter actualToAPIProcessCandidateStarterUserRemovedEventConverter = new ToAPIProcessCandidateStarterUserRemovedEventConverter(
        new APIProcessCandidateStarterUserConverter());

    // Assert
    assertFalse(actualToAPIProcessCandidateStarterUserRemovedEventConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPIProcessCandidateStarterUserRemovedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent() {
    // Arrange, Act and Assert
    assertFalse(toAPIProcessCandidateStarterUserRemovedEventConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPIProcessCandidateStarterUserRemovedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent2() {
    // Arrange
    ProcessCandidateStarterUserImpl processCandidateStarterUserImpl = new ProcessCandidateStarterUserImpl("42", "42");

    when(aPIProcessCandidateStarterUserConverter.from(Mockito.<IdentityLink>any()))
        .thenReturn(processCandidateStarterUserImpl);

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setProcessDefId("Entity");
    identityLinkEntityImpl.setUserId("42");

    // Act
    Optional<ProcessCandidateStarterUserRemovedEvent> actualFromResult = toAPIProcessCandidateStarterUserRemovedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(aPIProcessCandidateStarterUserConverter).from(isA(IdentityLink.class));
    ProcessCandidateStarterUserRemovedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof ProcessCandidateStarterUserRemovedEventImpl);
    ProcessCandidateStarterUser entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessCandidateStarterUserImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ProcessCandidateStarterUserEvents.PROCESS_CANDIDATE_STARTER_USER_REMOVED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(processCandidateStarterUserImpl, entity);
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPIProcessCandidateStarterUserRemovedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent3() {
    // Arrange
    ToAPIProcessCandidateStarterUserRemovedEventConverter toAPIProcessCandidateStarterUserRemovedEventConverter = new ToAPIProcessCandidateStarterUserRemovedEventConverter(
        new APIProcessCandidateStarterUserConverter());

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setProcessDefId("Entity");
    identityLinkEntityImpl.setUserId("42");

    // Act
    Optional<ProcessCandidateStarterUserRemovedEvent> actualFromResult = toAPIProcessCandidateStarterUserRemovedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    ProcessCandidateStarterUserRemovedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof ProcessCandidateStarterUserRemovedEventImpl);
    assertTrue(getResult.getEntity() instanceof ProcessCandidateStarterUserImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ProcessCandidateStarterUserEvents.PROCESS_CANDIDATE_STARTER_USER_REMOVED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code candidate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'candidate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPIProcessCandidateStarterUserRemovedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenCandidate() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setProcessDefId("Entity");
    identityLinkEntityImpl.setUserId(null);

    // Act and Assert
    assertFalse(toAPIProcessCandidateStarterUserRemovedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) UserId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'null'; when IdentityLinkEntityImpl (default constructor) UserId is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPIProcessCandidateStarterUserRemovedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenNull_whenIdentityLinkEntityImplUserIdIsNull() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setProcessDefId("Entity");
    identityLinkEntityImpl.setUserId(null);

    // Act and Assert
    assertFalse(toAPIProcessCandidateStarterUserRemovedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) ProcessDefId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; when IdentityLinkEntityImpl (default constructor) ProcessDefId is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPIProcessCandidateStarterUserRemovedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_whenIdentityLinkEntityImplProcessDefIdIsNull() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setProcessDefId(null);
    identityLinkEntityImpl.setUserId(null);

    // Act and Assert
    assertFalse(toAPIProcessCandidateStarterUserRemovedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }
}
