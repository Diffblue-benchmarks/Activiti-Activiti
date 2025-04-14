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
import org.activiti.api.process.model.ProcessCandidateStarterGroup;
import org.activiti.api.process.model.events.ProcessCandidateStarterGroupEvent;
import org.activiti.api.process.model.events.ProcessCandidateStarterGroupEvent.ProcessCandidateStarterGroupEvents;
import org.activiti.api.process.runtime.events.ProcessCandidateStarterGroupAddedEvent;
import org.activiti.api.runtime.event.impl.ProcessCandidateStarterGroupAddedEventImpl;
import org.activiti.api.runtime.model.impl.ProcessCandidateStarterGroupImpl;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterGroupConverter;
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

@ContextConfiguration(classes = {ToAPIProcessCandidateStarterGroupAddedEventConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ToAPIProcessCandidateStarterGroupAddedEventConverterDiffblueTest {
  @MockBean
  private APIProcessCandidateStarterGroupConverter aPIProcessCandidateStarterGroupConverter;

  @Autowired
  private ToAPIProcessCandidateStarterGroupAddedEventConverter toAPIProcessCandidateStarterGroupAddedEventConverter;

  /**
   * Test {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#ToAPIProcessCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)}.
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#ToAPIProcessCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)}
   */
  @Test
  @DisplayName("Test new ToAPIProcessCandidateStarterGroupAddedEventConverter(APIProcessCandidateStarterGroupConverter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void ToAPIProcessCandidateStarterGroupAddedEventConverter.<init>(APIProcessCandidateStarterGroupConverter)"})
  void testNewToAPIProcessCandidateStarterGroupAddedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    ToAPIProcessCandidateStarterGroupAddedEventConverter actualToAPIProcessCandidateStarterGroupAddedEventConverter = new ToAPIProcessCandidateStarterGroupAddedEventConverter(
        new APIProcessCandidateStarterGroupConverter());

    // Assert
    assertFalse(actualToAPIProcessCandidateStarterGroupAddedEventConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPIProcessCandidateStarterGroupAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent() {
    // Arrange, Act and Assert
    assertFalse(toAPIProcessCandidateStarterGroupAddedEventConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPIProcessCandidateStarterGroupAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent2() {
    // Arrange
    ProcessCandidateStarterGroupImpl processCandidateStarterGroupImpl = new ProcessCandidateStarterGroupImpl("42",
        "42");

    when(aPIProcessCandidateStarterGroupConverter.from(Mockito.<IdentityLink>any()))
        .thenReturn(processCandidateStarterGroupImpl);

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setProcessDefId("Entity");
    identityLinkEntityImpl.setGroupId("42");

    // Act
    Optional<ProcessCandidateStarterGroupAddedEvent> actualFromResult = toAPIProcessCandidateStarterGroupAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    verify(aPIProcessCandidateStarterGroupConverter).from(isA(IdentityLink.class));
    ProcessCandidateStarterGroupAddedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof ProcessCandidateStarterGroupAddedEventImpl);
    ProcessCandidateStarterGroup entity = getResult.getEntity();
    assertTrue(entity instanceof ProcessCandidateStarterGroupImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ProcessCandidateStarterGroupEvents.PROCESS_CANDIDATE_STARTER_GROUP_ADDED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
    assertSame(processCandidateStarterGroupImpl, entity);
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPIProcessCandidateStarterGroupAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent3() {
    // Arrange
    ToAPIProcessCandidateStarterGroupAddedEventConverter toAPIProcessCandidateStarterGroupAddedEventConverter = new ToAPIProcessCandidateStarterGroupAddedEventConverter(
        new APIProcessCandidateStarterGroupConverter());

    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setProcessDefId("Entity");
    identityLinkEntityImpl.setGroupId("42");

    // Act
    Optional<ProcessCandidateStarterGroupAddedEvent> actualFromResult = toAPIProcessCandidateStarterGroupAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED));

    // Assert
    ProcessCandidateStarterGroupAddedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof ProcessCandidateStarterGroupAddedEventImpl);
    assertTrue(getResult.getEntity() instanceof ProcessCandidateStarterGroupImpl);
    assertNull(getResult.getProcessDefinitionVersion());
    assertNull(getResult.getBusinessKey());
    assertNull(getResult.getParentProcessInstanceId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionKey());
    assertNull(getResult.getProcessInstanceId());
    assertEquals(ProcessCandidateStarterGroupEvents.PROCESS_CANDIDATE_STARTER_GROUP_ADDED, getResult.getEventType());
    assertTrue(actualFromResult.isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code candidate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'candidate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPIProcessCandidateStarterGroupAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenCandidate() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setProcessDefId("Entity");
    identityLinkEntityImpl.setGroupId(null);

    // Act and Assert
    assertFalse(toAPIProcessCandidateStarterGroupAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPIProcessCandidateStarterGroupAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_givenNull() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setProcessDefId("Entity");
    identityLinkEntityImpl.setGroupId(null);

    // Act and Assert
    assertFalse(toAPIProcessCandidateStarterGroupAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) ProcessDefId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ToAPIProcessCandidateStarterGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; when IdentityLinkEntityImpl (default constructor) ProcessDefId is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional ToAPIProcessCandidateStarterGroupAddedEventConverter.from(ActivitiEntityEvent)"})
  void testFromWithActivitiEntityEvent_whenIdentityLinkEntityImplProcessDefIdIsNull() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setProcessDefId(null);
    identityLinkEntityImpl.setGroupId(null);

    // Act and Assert
    assertFalse(toAPIProcessCandidateStarterGroupAddedEventConverter
        .from(new ActivitiEntityEventImpl(identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
        .isPresent());
  }
}
