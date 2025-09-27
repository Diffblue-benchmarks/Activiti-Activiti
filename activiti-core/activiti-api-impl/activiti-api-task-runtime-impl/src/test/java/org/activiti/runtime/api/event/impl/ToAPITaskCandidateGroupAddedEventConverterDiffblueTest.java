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
import org.activiti.runtime.api.model.impl.APITaskCandidateGroupConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ToAPITaskCandidateGroupAddedEventConverter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ToAPITaskCandidateGroupAddedEventConverterDiffblueTest {
  @MockBean private APITaskCandidateGroupConverter aPITaskCandidateGroupConverter;

  @Autowired
  private ToAPITaskCandidateGroupAddedEventConverter toAPITaskCandidateGroupAddedEventConverter;

  /**
   * Test {@link
   * ToAPITaskCandidateGroupAddedEventConverter#ToAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)}.
   *
   * <p>Method under test: {@link
   * ToAPITaskCandidateGroupAddedEventConverter#ToAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)}
   */
  @Test
  @DisplayName(
      "Test new ToAPITaskCandidateGroupAddedEventConverter(APITaskCandidateGroupConverter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ToAPITaskCandidateGroupAddedEventConverter.<init>(APITaskCandidateGroupConverter)"
  })
  void testNewToAPITaskCandidateGroupAddedEventConverter() {
    // Arrange and Act
    ToAPITaskCandidateGroupAddedEventConverter actualToAPITaskCandidateGroupAddedEventConverter =
        new ToAPITaskCandidateGroupAddedEventConverter(aPITaskCandidateGroupConverter);

    // Assert
    assertFalse(
        actualToAPITaskCandidateGroupAddedEventConverter
            .from(
                new ActivitiProcessCancelledEventImpl(
                    ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <p>Method under test: {@link
   * ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Optional ToAPITaskCandidateGroupAddedEventConverter.from(ActivitiEntityEvent)"
  })
  void testFromWithActivitiEntityEvent() {
    // Arrange, Act and Assert
    assertFalse(
        toAPITaskCandidateGroupAddedEventConverter
            .from(
                new ActivitiProcessCancelledEventImpl(
                    ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
            .isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <ul>
   *   <li>Given {@code candidate}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'candidate'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Optional ToAPITaskCandidateGroupAddedEventConverter.from(ActivitiEntityEvent)"
  })
  void testFromWithActivitiEntityEvent_givenCandidate() throws UnsupportedEncodingException {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setProcessDefId("42");
    identityLinkEntityImpl.setProcessInstanceId("42");
    identityLinkEntityImpl.setType("candidate");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId("42");
    identityLinkEntityImpl.setGroupId(null);
    identityLinkEntityImpl.setTaskId("Entity");

    // Act and Assert
    assertFalse(
        toAPITaskCandidateGroupAddedEventConverter
            .from(
                new ActivitiEntityEventImpl(
                    identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
            .isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <ul>
   *   <li>Given {@code Type}.
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) TaskId is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'Type'; when IdentityLinkEntityImpl (default constructor) TaskId is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Optional ToAPITaskCandidateGroupAddedEventConverter.from(ActivitiEntityEvent)"
  })
  void testFromWithActivitiEntityEvent_givenType_whenIdentityLinkEntityImplTaskIdIsNull()
      throws UnsupportedEncodingException {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setProcessDefId("42");
    identityLinkEntityImpl.setProcessInstanceId("42");
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId("42");
    identityLinkEntityImpl.setGroupId(null);
    identityLinkEntityImpl.setTaskId(null);

    // Act and Assert
    assertFalse(
        toAPITaskCandidateGroupAddedEventConverter
            .from(
                new ActivitiEntityEventImpl(
                    identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
            .isPresent());
  }

  /**
   * Test {@link ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)} with {@code
   * ActivitiEntityEvent}.
   *
   * <ul>
   *   <li>Given {@code Type}.
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) Type is {@code Type}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ToAPITaskCandidateGroupAddedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName(
      "Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given 'Type'; when IdentityLinkEntityImpl (default constructor) Type is 'Type'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Optional ToAPITaskCandidateGroupAddedEventConverter.from(ActivitiEntityEvent)"
  })
  void testFromWithActivitiEntityEvent_givenType_whenIdentityLinkEntityImplTypeIsType()
      throws UnsupportedEncodingException {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setDeleted(true);
    identityLinkEntityImpl.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLinkEntityImpl.setId("42");
    identityLinkEntityImpl.setInserted(true);
    identityLinkEntityImpl.setProcessDefId("42");
    identityLinkEntityImpl.setProcessInstanceId("42");
    identityLinkEntityImpl.setType("Type");
    identityLinkEntityImpl.setUpdated(true);
    identityLinkEntityImpl.setUserId("42");
    identityLinkEntityImpl.setGroupId(null);
    identityLinkEntityImpl.setTaskId("Entity");

    // Act and Assert
    assertFalse(
        toAPITaskCandidateGroupAddedEventConverter
            .from(
                new ActivitiEntityEventImpl(
                    identityLinkEntityImpl, ActivitiEventType.ENTITY_CREATED))
            .isPresent());
  }
}
