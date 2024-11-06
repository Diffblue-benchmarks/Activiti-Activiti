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
package org.activiti.runtime.api.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.task.model.TaskCandidateUser;
import org.activiti.api.task.model.impl.TaskCandidateUserImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {APITaskCandidateUserConverter.class})
@ExtendWith(SpringExtension.class)
class APITaskCandidateUserConverterDiffblueTest {
  @Autowired
  private APITaskCandidateUserConverter aPITaskCandidateUserConverter;

  /**
   * Test {@link APITaskCandidateUserConverter#from(IdentityLink)} with
   * {@code IdentityLink}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return TaskId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link APITaskCandidateUserConverter#from(IdentityLink)}
   */
  @Test
  @DisplayName("Test from(IdentityLink) with 'IdentityLink'; given '42'; then return TaskId is '42'")
  void testFromWithIdentityLink_given42_thenReturnTaskIdIs42() {
    // Arrange
    IdentityLinkEntityImpl identityLink = mock(IdentityLinkEntityImpl.class);
    when(identityLink.getTaskId()).thenReturn("42");
    when(identityLink.getUserId()).thenReturn("42");

    // Act
    TaskCandidateUser actualFromResult = aPITaskCandidateUserConverter.from(identityLink);

    // Assert
    verify(identityLink).getTaskId();
    verify(identityLink).getUserId();
    assertTrue(actualFromResult instanceof TaskCandidateUserImpl);
    assertEquals("42", actualFromResult.getTaskId());
    assertEquals("42", actualFromResult.getUserId());
  }

  /**
   * Test {@link APITaskCandidateUserConverter#from(IdentityLink)} with
   * {@code IdentityLink}.
   * <ul>
   *   <li>When {@link IdentityLinkEntityImpl} (default constructor).</li>
   *   <li>Then return TaskId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link APITaskCandidateUserConverter#from(IdentityLink)}
   */
  @Test
  @DisplayName("Test from(IdentityLink) with 'IdentityLink'; when IdentityLinkEntityImpl (default constructor); then return TaskId is 'null'")
  void testFromWithIdentityLink_whenIdentityLinkEntityImpl_thenReturnTaskIdIsNull() {
    // Arrange and Act
    TaskCandidateUser actualFromResult = aPITaskCandidateUserConverter.from(new IdentityLinkEntityImpl());

    // Assert
    assertTrue(actualFromResult instanceof TaskCandidateUserImpl);
    assertNull(actualFromResult.getTaskId());
    assertNull(actualFromResult.getUserId());
  }
}
