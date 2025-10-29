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
import org.activiti.api.task.model.TaskCandidateGroup;
import org.activiti.api.task.model.impl.TaskCandidateGroupImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {APITaskCandidateGroupConverter.class})
@ExtendWith(SpringExtension.class)
class APITaskCandidateGroupConverterDiffblueTest {
  @Autowired
  private APITaskCandidateGroupConverter aPITaskCandidateGroupConverter;

  /**
   * Method under test: {@link APITaskCandidateGroupConverter#from(IdentityLink)}
   */
  @Test
  void testFrom() {
    // Arrange and Act
    TaskCandidateGroup actualFromResult = aPITaskCandidateGroupConverter.from(new IdentityLinkEntityImpl());

    // Assert
    assertTrue(actualFromResult instanceof TaskCandidateGroupImpl);
    assertNull(actualFromResult.getTaskId());
    assertNull(actualFromResult.getGroupId());
  }

  /**
   * Method under test: {@link APITaskCandidateGroupConverter#from(IdentityLink)}
   */
  @Test
  void testFrom2() {
    // Arrange
    IdentityLinkEntityImpl identityLink = mock(IdentityLinkEntityImpl.class);
    when(identityLink.getGroupId()).thenReturn("42");
    when(identityLink.getTaskId()).thenReturn("42");

    // Act
    TaskCandidateGroup actualFromResult = aPITaskCandidateGroupConverter.from(identityLink);

    // Assert
    verify(identityLink).getGroupId();
    verify(identityLink).getTaskId();
    assertTrue(actualFromResult instanceof TaskCandidateGroupImpl);
    assertEquals("42", actualFromResult.getTaskId());
    assertEquals("42", actualFromResult.getGroupId());
  }
}
