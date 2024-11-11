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
import org.activiti.runtime.api.model.impl.APIProcessCandidateStarterGroupConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ToAPIProcessCandidateStarterGroupRemovedEventConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ToAPIProcessCandidateStarterGroupRemovedEventConverterDiffblueTest {
  @MockBean
  private APIProcessCandidateStarterGroupConverter aPIProcessCandidateStarterGroupConverter;

  @Autowired
  private ToAPIProcessCandidateStarterGroupRemovedEventConverter toAPIProcessCandidateStarterGroupRemovedEventConverter;

  /**
   * Test
   * {@link ToAPIProcessCandidateStarterGroupRemovedEventConverter#ToAPIProcessCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)}.
   * <p>
   * Method under test:
   * {@link ToAPIProcessCandidateStarterGroupRemovedEventConverter#ToAPIProcessCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)}
   */
  @Test
  @DisplayName("Test new ToAPIProcessCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)")
  void testNewToAPIProcessCandidateStarterGroupRemovedEventConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ToAPIProcessCandidateStarterGroupRemovedEventConverter actualToAPIProcessCandidateStarterGroupRemovedEventConverter = new ToAPIProcessCandidateStarterGroupRemovedEventConverter(
        new APIProcessCandidateStarterGroupConverter());

    // Assert
    assertFalse(actualToAPIProcessCandidateStarterGroupRemovedEventConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test
   * {@link ToAPIProcessCandidateStarterGroupRemovedEventConverter#ToAPIProcessCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)}.
   * <p>
   * Method under test:
   * {@link ToAPIProcessCandidateStarterGroupRemovedEventConverter#ToAPIProcessCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)}
   */
  @Test
  @DisplayName("Test new ToAPIProcessCandidateStarterGroupRemovedEventConverter(APIProcessCandidateStarterGroupConverter)")
  void testNewToAPIProcessCandidateStarterGroupRemovedEventConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ToAPIProcessCandidateStarterGroupRemovedEventConverter actualToAPIProcessCandidateStarterGroupRemovedEventConverter = new ToAPIProcessCandidateStarterGroupRemovedEventConverter(
        mock(APIProcessCandidateStarterGroupConverter.class));

    // Assert
    assertFalse(actualToAPIProcessCandidateStarterGroupRemovedEventConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
  }

  /**
   * Test
   * {@link ToAPIProcessCandidateStarterGroupRemovedEventConverter#from(ActivitiEntityEvent)}
   * with {@code ActivitiEntityEvent}.
   * <p>
   * Method under test:
   * {@link ToAPIProcessCandidateStarterGroupRemovedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  void testFromWithActivitiEntityEvent() {
    // Arrange, Act and Assert
    assertFalse(toAPIProcessCandidateStarterGroupRemovedEventConverter
        .from(new ActivitiProcessCancelledEventImpl(ExecutionEntityImpl.createWithEmptyRelationshipCollections()))
        .isPresent());
    assertFalse(toAPIProcessCandidateStarterGroupRemovedEventConverter
        .from(new ActivitiProcessCancelledEventImpl(mock(ProcessInstance.class)))
        .isPresent());
  }
}