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
package org.activiti.api.task.model.payloads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ClaimTaskPayloadDiffblueTest {
  /**
   * Test {@link ClaimTaskPayload#ClaimTaskPayload()}.
   * <p>
   * Method under test: {@link ClaimTaskPayload#ClaimTaskPayload()}
   */
  @Test
  @DisplayName("Test new ClaimTaskPayload()")
  void testNewClaimTaskPayload() {
    // Arrange and Act
    ClaimTaskPayload actualClaimTaskPayload = new ClaimTaskPayload();

    // Assert
    assertNull(actualClaimTaskPayload.getAssignee());
    assertNull(actualClaimTaskPayload.getTaskId());
  }

  /**
   * Test {@link ClaimTaskPayload#ClaimTaskPayload(String, String)}.
   * <p>
   * Method under test: {@link ClaimTaskPayload#ClaimTaskPayload(String, String)}
   */
  @Test
  @DisplayName("Test new ClaimTaskPayload(String, String)")
  void testNewClaimTaskPayload2() {
    // Arrange and Act
    ClaimTaskPayload actualClaimTaskPayload = new ClaimTaskPayload("42", "Assignee");

    // Assert
    assertEquals("42", actualClaimTaskPayload.getTaskId());
    assertEquals("Assignee", actualClaimTaskPayload.getAssignee());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClaimTaskPayload#setAssignee(String)}
   *   <li>{@link ClaimTaskPayload#setTaskId(String)}
   *   <li>{@link ClaimTaskPayload#getAssignee()}
   *   <li>{@link ClaimTaskPayload#getId()}
   *   <li>{@link ClaimTaskPayload#getTaskId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ClaimTaskPayload claimTaskPayload = new ClaimTaskPayload();

    // Act
    claimTaskPayload.setAssignee("Assignee");
    claimTaskPayload.setTaskId("42");
    String actualAssignee = claimTaskPayload.getAssignee();
    claimTaskPayload.getId();

    // Assert that nothing has changed
    assertEquals("42", claimTaskPayload.getTaskId());
    assertEquals("Assignee", actualAssignee);
  }
}
