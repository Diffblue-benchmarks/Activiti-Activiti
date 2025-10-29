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
import org.activiti.runtime.api.model.impl.APITaskCandidateUserConverter;
import org.junit.jupiter.api.Test;

class ToTaskCandidateUserRemovedConverterDiffblueTest {
  /**
   * Method under test:
   * {@link ToTaskCandidateUserRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  void testFrom() {
    // Arrange
    ToTaskCandidateUserRemovedConverter toTaskCandidateUserRemovedConverter = new ToTaskCandidateUserRemovedConverter(
        new APITaskCandidateUserConverter());

    // Act and Assert
    assertFalse(toTaskCandidateUserRemovedConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Method under test:
   * {@link ToTaskCandidateUserRemovedConverter#from(ActivitiEntityEvent)}
   */
  @Test
  void testFrom2() {
    // Arrange
    ToTaskCandidateUserRemovedConverter toTaskCandidateUserRemovedConverter = new ToTaskCandidateUserRemovedConverter(
        new APITaskCandidateUserConverter());

    // Act and Assert
    assertFalse(
        toTaskCandidateUserRemovedConverter.from(new ActivitiProcessCancelledEventImpl(mock(ProcessInstance.class)))
            .isPresent());
  }

  /**
   * Method under test:
   * {@link ToTaskCandidateUserRemovedConverter#ToTaskCandidateUserRemovedConverter(APITaskCandidateUserConverter)}
   */
  @Test
  void testNewToTaskCandidateUserRemovedConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ToTaskCandidateUserRemovedConverter actualToTaskCandidateUserRemovedConverter = new ToTaskCandidateUserRemovedConverter(
        new APITaskCandidateUserConverter());

    // Assert
    assertFalse(actualToTaskCandidateUserRemovedConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Method under test:
   * {@link ToTaskCandidateUserRemovedConverter#ToTaskCandidateUserRemovedConverter(APITaskCandidateUserConverter)}
   */
  @Test
  void testNewToTaskCandidateUserRemovedConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ToTaskCandidateUserRemovedConverter actualToTaskCandidateUserRemovedConverter = new ToTaskCandidateUserRemovedConverter(
        mock(APITaskCandidateUserConverter.class));

    // Assert
    assertFalse(actualToTaskCandidateUserRemovedConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }
}
