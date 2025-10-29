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
import static org.mockito.Mockito.mock;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.impl.ActivitiProcessCancelledEventImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.runtime.api.model.impl.APITaskCandidateGroupConverter;
import org.junit.jupiter.api.Test;

class ToTaskCandidateGroupRemovedConverterDiffblueTest {
  /**
   * Method under test:
   * {@link ToTaskCandidateGroupRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  void testFrom() {
    // Arrange
    ToTaskCandidateGroupRemovedConverter toTaskCandidateGroupRemovedConverter = new ToTaskCandidateGroupRemovedConverter(
        new APITaskCandidateGroupConverter());

    // Act and Assert
    assertFalse(toTaskCandidateGroupRemovedConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Method under test:
   * {@link ToTaskCandidateGroupRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  void testFrom2() {
    // Arrange
    ToTaskCandidateGroupRemovedConverter toTaskCandidateGroupRemovedConverter = new ToTaskCandidateGroupRemovedConverter(
        new APITaskCandidateGroupConverter());

    // Act and Assert
    assertFalse(
        toTaskCandidateGroupRemovedConverter.from(new ActivitiProcessCancelledEventImpl(mock(ProcessInstance.class)))
            .isPresent());
  }

  /**
   * Method under test:
   * {@link ToTaskCandidateGroupRemovedConverter#ToTaskCandidateGroupRemovedConverter(APITaskCandidateGroupConverter)}
   */
  @Test
  void testNewToTaskCandidateGroupRemovedConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ToTaskCandidateGroupRemovedConverter actualToTaskCandidateGroupRemovedConverter = new ToTaskCandidateGroupRemovedConverter(
        new APITaskCandidateGroupConverter());

    // Assert
    assertFalse(actualToTaskCandidateGroupRemovedConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Method under test:
   * {@link ToTaskCandidateGroupRemovedConverter#ToTaskCandidateGroupRemovedConverter(APITaskCandidateGroupConverter)}
   */
  @Test
  void testNewToTaskCandidateGroupRemovedConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ToTaskCandidateGroupRemovedConverter actualToTaskCandidateGroupRemovedConverter = new ToTaskCandidateGroupRemovedConverter(
        mock(APITaskCandidateGroupConverter.class));

    // Assert
    assertFalse(actualToTaskCandidateGroupRemovedConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }
}
