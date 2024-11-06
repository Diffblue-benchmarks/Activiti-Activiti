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
import java.io.UnsupportedEncodingException;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskCandidateEventConverterHelperDiffblueTest {
  /**
   * Test
   * {@link TaskCandidateEventConverterHelper#isTaskCandidateUserLink(IdentityLink)}.
   * <ul>
   *   <li>Given {@code candidate}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskCandidateEventConverterHelper#isTaskCandidateUserLink(IdentityLink)}
   */
  @Test
  @DisplayName("Test isTaskCandidateUserLink(IdentityLink); given 'candidate'")
  void testIsTaskCandidateUserLink_givenCandidate() throws UnsupportedEncodingException {
    // Arrange
    TaskCandidateEventConverterHelper taskCandidateEventConverterHelper = new TaskCandidateEventConverterHelper();

    IdentityLinkEntityImpl identityLink = new IdentityLinkEntityImpl();
    identityLink.setDeleted(true);
    identityLink.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLink.setGroupId("42");
    identityLink.setId("42");
    identityLink.setInserted(true);
    identityLink.setProcessDefId("42");
    identityLink.setProcessInstanceId("42");
    identityLink.setType("candidate");
    identityLink.setUpdated(true);
    identityLink.setUserId(null);
    identityLink.setTaskId("Identity Link");

    // Act and Assert
    assertFalse(taskCandidateEventConverterHelper.isTaskCandidateUserLink(identityLink));
  }

  /**
   * Test
   * {@link TaskCandidateEventConverterHelper#isTaskCandidateUserLink(IdentityLink)}.
   * <ul>
   *   <li>Given {@code Type}.</li>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) Type is
   * {@code Type}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskCandidateEventConverterHelper#isTaskCandidateUserLink(IdentityLink)}
   */
  @Test
  @DisplayName("Test isTaskCandidateUserLink(IdentityLink); given 'Type'; when IdentityLinkEntityImpl (default constructor) Type is 'Type'")
  void testIsTaskCandidateUserLink_givenType_whenIdentityLinkEntityImplTypeIsType()
      throws UnsupportedEncodingException {
    // Arrange
    TaskCandidateEventConverterHelper taskCandidateEventConverterHelper = new TaskCandidateEventConverterHelper();

    IdentityLinkEntityImpl identityLink = new IdentityLinkEntityImpl();
    identityLink.setDeleted(true);
    identityLink.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLink.setGroupId("42");
    identityLink.setId("42");
    identityLink.setInserted(true);
    identityLink.setProcessDefId("42");
    identityLink.setProcessInstanceId("42");
    identityLink.setType("Type");
    identityLink.setUpdated(true);
    identityLink.setUserId(null);
    identityLink.setTaskId("Identity Link");

    // Act and Assert
    assertFalse(taskCandidateEventConverterHelper.isTaskCandidateUserLink(identityLink));
  }

  /**
   * Test
   * {@link TaskCandidateEventConverterHelper#isTaskCandidateUserLink(IdentityLink)}.
   * <ul>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskCandidateEventConverterHelper#isTaskCandidateUserLink(IdentityLink)}
   */
  @Test
  @DisplayName("Test isTaskCandidateUserLink(IdentityLink); when IdentityLinkEntityImpl (default constructor); then return 'false'")
  void testIsTaskCandidateUserLink_whenIdentityLinkEntityImpl_thenReturnFalse() {
    // Arrange
    TaskCandidateEventConverterHelper taskCandidateEventConverterHelper = new TaskCandidateEventConverterHelper();

    // Act and Assert
    assertFalse(taskCandidateEventConverterHelper.isTaskCandidateUserLink(new IdentityLinkEntityImpl()));
  }

  /**
   * Test
   * {@link TaskCandidateEventConverterHelper#isTaskCandidateGroupLink(IdentityLink)}.
   * <ul>
   *   <li>Given {@code candidate}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskCandidateEventConverterHelper#isTaskCandidateGroupLink(IdentityLink)}
   */
  @Test
  @DisplayName("Test isTaskCandidateGroupLink(IdentityLink); given 'candidate'")
  void testIsTaskCandidateGroupLink_givenCandidate() throws UnsupportedEncodingException {
    // Arrange
    TaskCandidateEventConverterHelper taskCandidateEventConverterHelper = new TaskCandidateEventConverterHelper();

    IdentityLinkEntityImpl identityLink = new IdentityLinkEntityImpl();
    identityLink.setDeleted(true);
    identityLink.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLink.setId("42");
    identityLink.setInserted(true);
    identityLink.setProcessDefId("42");
    identityLink.setProcessInstanceId("42");
    identityLink.setType("candidate");
    identityLink.setUpdated(true);
    identityLink.setUserId("42");
    identityLink.setGroupId(null);
    identityLink.setTaskId("Identity Link");

    // Act and Assert
    assertFalse(taskCandidateEventConverterHelper.isTaskCandidateGroupLink(identityLink));
  }

  /**
   * Test
   * {@link TaskCandidateEventConverterHelper#isTaskCandidateGroupLink(IdentityLink)}.
   * <ul>
   *   <li>Given {@code Type}.</li>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor) Type is
   * {@code Type}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskCandidateEventConverterHelper#isTaskCandidateGroupLink(IdentityLink)}
   */
  @Test
  @DisplayName("Test isTaskCandidateGroupLink(IdentityLink); given 'Type'; when IdentityLinkEntityImpl (default constructor) Type is 'Type'")
  void testIsTaskCandidateGroupLink_givenType_whenIdentityLinkEntityImplTypeIsType()
      throws UnsupportedEncodingException {
    // Arrange
    TaskCandidateEventConverterHelper taskCandidateEventConverterHelper = new TaskCandidateEventConverterHelper();

    IdentityLinkEntityImpl identityLink = new IdentityLinkEntityImpl();
    identityLink.setDeleted(true);
    identityLink.setDetails("AXAXAXAX".getBytes("UTF-8"));
    identityLink.setId("42");
    identityLink.setInserted(true);
    identityLink.setProcessDefId("42");
    identityLink.setProcessInstanceId("42");
    identityLink.setType("Type");
    identityLink.setUpdated(true);
    identityLink.setUserId("42");
    identityLink.setGroupId(null);
    identityLink.setTaskId("Identity Link");

    // Act and Assert
    assertFalse(taskCandidateEventConverterHelper.isTaskCandidateGroupLink(identityLink));
  }

  /**
   * Test
   * {@link TaskCandidateEventConverterHelper#isTaskCandidateGroupLink(IdentityLink)}.
   * <ul>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskCandidateEventConverterHelper#isTaskCandidateGroupLink(IdentityLink)}
   */
  @Test
  @DisplayName("Test isTaskCandidateGroupLink(IdentityLink); when IdentityLinkEntityImpl (default constructor); then return 'false'")
  void testIsTaskCandidateGroupLink_whenIdentityLinkEntityImpl_thenReturnFalse() {
    // Arrange
    TaskCandidateEventConverterHelper taskCandidateEventConverterHelper = new TaskCandidateEventConverterHelper();

    // Act and Assert
    assertFalse(taskCandidateEventConverterHelper.isTaskCandidateGroupLink(new IdentityLinkEntityImpl()));
  }
}
