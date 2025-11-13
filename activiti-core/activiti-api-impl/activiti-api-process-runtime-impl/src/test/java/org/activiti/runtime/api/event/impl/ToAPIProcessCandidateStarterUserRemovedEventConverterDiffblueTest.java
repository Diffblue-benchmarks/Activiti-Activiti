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

import static org.junit.jupiter.api.Assertions.assertFalse;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterUserConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ToAPIProcessCandidateStarterUserRemovedEventConverter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ToAPIProcessCandidateStarterUserRemovedEventConverterDiffblueTest {
  @MockBean private APIProcessCandidateStarterUserConverter aPIProcessCandidateStarterUserConverter;

  @Autowired
  private ToAPIProcessCandidateStarterUserRemovedEventConverter
      toAPIProcessCandidateStarterUserRemovedEventConverter;

  /**
   * Test {@link
   * ToAPIProcessCandidateStarterUserRemovedEventConverter#ToAPIProcessCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)}.
   *
   * <p>Method under test: {@link
   * ToAPIProcessCandidateStarterUserRemovedEventConverter#ToAPIProcessCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)}
   */
  @Test
  @DisplayName(
      "Test new ToAPIProcessCandidateStarterUserRemovedEventConverter(APIProcessCandidateStarterUserConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ToAPIProcessCandidateStarterUserRemovedEventConverter.<init>(APIProcessCandidateStarterUserConverter)"
  })
  void testNewToAPIProcessCandidateStarterUserRemovedEventConverter() {
    // Arrange and Act
    ToAPIProcessCandidateStarterUserRemovedEventConverter
        actualToAPIProcessCandidateStarterUserRemovedEventConverter =
            new ToAPIProcessCandidateStarterUserRemovedEventConverter(
                aPIProcessCandidateStarterUserConverter);

    // Assert
    assertFalse(
        actualToAPIProcessCandidateStarterUserRemovedEventConverter
            .from(
                new ActivitiProcessCancelledEventImpl(
                    ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   * with {@code ActivitiEntityEvent}.
   *
   * <p>Method under test: {@link
   * ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Optional ToAPIProcessCandidateStarterUserRemovedEventConverter.from(ActivitiEntityEvent)"
  })
  void testFromWithActivitiEntityEvent() {
    // Arrange, Act and Assert
    assertFalse(
        toAPIProcessCandidateStarterUserRemovedEventConverter
            .from(
                new ActivitiProcessCancelledEventImpl(
                    ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   * with {@code ActivitiEntityEvent}.
   *
   * <ul>
   *   <li>Given {@code candidate}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'candidate'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Optional ToAPIProcessCandidateStarterUserRemovedEventConverter.from(ActivitiEntityEvent)"
  })
  void testFromWithActivitiEntityEvent_givenCandidate() throws UnsupportedEncodingException {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLinkEntityImpl.setGroupId("42");
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setProcessInstanceId("42");
    identityLinkEntityImpl.setTaskId("42");
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setProcessDefId("Entity");

    // Act and Assert
    assertFalse(
        toAPIProcessCandidateStarterUserRemovedEventConverter
            .from(
                new ActivitiEntityEventImpl(
                    identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
            .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   * with {@code ActivitiEntityEvent}.
   *
   * <ul>
   *   <li>Given {@code Type}.
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) Type is {@code Type}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'Type'; when IdentityLinkEntityImpl (default constructor) Type is 'Type'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Optional ToAPIProcessCandidateStarterUserRemovedEventConverter.from(ActivitiEntityEvent)"
  })
  void testFromWithActivitiEntityEvent_givenType_whenIdentityLinkEntityImplTypeIsType()
      throws UnsupportedEncodingException {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLinkEntityImpl.setGroupId("42");
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setProcessInstanceId("42");
    identityLinkEntityImpl.setTaskId("42");
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setProcessDefId("Entity");

    // Act and Assert
    assertFalse(
        toAPIProcessCandidateStarterUserRemovedEventConverter
            .from(
                new ActivitiEntityEventImpl(
                    identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
            .isPresent());
  }

  /**
   * Test {@link ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   * with {@code ActivitiEntityEvent}.
   *
   * <ul>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) ProcessDefId is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ToAPIProcessCandidateStarterUserRemovedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; when IdentityLinkEntityImpl (default constructor) ProcessDefId is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Optional ToAPIProcessCandidateStarterUserRemovedEventConverter.from(ActivitiEntityEvent)"
  })
  void testFromWithActivitiEntityEvent_whenIdentityLinkEntityImplProcessDefIdIsNull()
      throws UnsupportedEncodingException {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLinkEntityImpl.setGroupId("42");
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setProcessInstanceId("42");
    identityLinkEntityImpl.setTaskId("42");
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId(null);
    identityLinkEntityImpl.setProcessDefId(null);

    // Act and Assert
    assertFalse(
        toAPIProcessCandidateStarterUserRemovedEventConverter
            .from(
                new ActivitiEntityEventImpl(
                    identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
            .isPresent());
  }
}
