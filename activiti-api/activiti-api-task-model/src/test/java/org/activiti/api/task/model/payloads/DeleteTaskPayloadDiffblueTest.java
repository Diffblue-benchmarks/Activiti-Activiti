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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeleteTaskPayloadDiffblueTest {
  /**
   * Test {@link DeleteTaskPayload#DeleteTaskPayload()}.
   * <p>
   * Method under test: {@link DeleteTaskPayload#DeleteTaskPayload()}
   */
  @Test
  @DisplayName("Test new DeleteTaskPayload()")
  void testNewDeleteTaskPayload() {
    // Arrange and Act
    DeleteTaskPayload actualDeleteTaskPayload = new DeleteTaskPayload();

    // Assert
    assertNull(actualDeleteTaskPayload.getReason());
    assertNull(actualDeleteTaskPayload.getTaskId());
    assertFalse(actualDeleteTaskPayload.hasReason());
  }

  /**
   * Test {@link DeleteTaskPayload#DeleteTaskPayload(String, String)}.
   * <p>
   * Method under test:
   * {@link DeleteTaskPayload#DeleteTaskPayload(String, String)}
   */
  @Test
  @DisplayName("Test new DeleteTaskPayload(String, String)")
  void testNewDeleteTaskPayload2() {
    // Arrange and Act
    DeleteTaskPayload actualDeleteTaskPayload = new DeleteTaskPayload("42", "Just cause");

    // Assert
    assertEquals("42", actualDeleteTaskPayload.getTaskId());
    assertEquals("Just cause", actualDeleteTaskPayload.getReason());
    assertTrue(actualDeleteTaskPayload.hasReason());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DeleteTaskPayload#setReason(String)}
   *   <li>{@link DeleteTaskPayload#setTaskId(String)}
   *   <li>{@link DeleteTaskPayload#getId()}
   *   <li>{@link DeleteTaskPayload#getReason()}
   *   <li>{@link DeleteTaskPayload#getTaskId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    DeleteTaskPayload deleteTaskPayload = new DeleteTaskPayload();

    // Act
    deleteTaskPayload.setReason("Just cause");
    deleteTaskPayload.setTaskId("42");
    deleteTaskPayload.getId();
    String actualReason = deleteTaskPayload.getReason();

    // Assert that nothing has changed
    assertEquals("42", deleteTaskPayload.getTaskId());
    assertEquals("Just cause", actualReason);
  }

  /**
   * Test {@link DeleteTaskPayload#hasReason()}.
   * <ul>
   *   <li>Given {@link DeleteTaskPayload#DeleteTaskPayload()} Reason is
   * {@code foo}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeleteTaskPayload#hasReason()}
   */
  @Test
  @DisplayName("Test hasReason(); given DeleteTaskPayload() Reason is 'foo'; then return 'true'")
  void testHasReason_givenDeleteTaskPayloadReasonIsFoo_thenReturnTrue() {
    // Arrange
    DeleteTaskPayload deleteTaskPayload = new DeleteTaskPayload();
    deleteTaskPayload.setReason("foo");

    // Act and Assert
    assertTrue(deleteTaskPayload.hasReason());
  }

  /**
   * Test {@link DeleteTaskPayload#hasReason()}.
   * <ul>
   *   <li>Given {@link DeleteTaskPayload#DeleteTaskPayload()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DeleteTaskPayload#hasReason()}
   */
  @Test
  @DisplayName("Test hasReason(); given DeleteTaskPayload(); then return 'false'")
  void testHasReason_givenDeleteTaskPayload_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new DeleteTaskPayload()).hasReason());
  }
}
