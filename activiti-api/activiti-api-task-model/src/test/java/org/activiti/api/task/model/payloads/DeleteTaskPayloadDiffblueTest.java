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
import org.junit.jupiter.api.Test;

class DeleteTaskPayloadDiffblueTest {
  /**
   * Method under test: {@link DeleteTaskPayload#hasReason()}
   */
  @Test
  void testHasReason() {
    // Arrange, Act and Assert
    assertFalse((new DeleteTaskPayload()).hasReason());
  }

  /**
   * Method under test: {@link DeleteTaskPayload#hasReason()}
   */
  @Test
  void testHasReason2() {
    // Arrange
    DeleteTaskPayload deleteTaskPayload = new DeleteTaskPayload();
    deleteTaskPayload.setReason("foo");

    // Act and Assert
    assertTrue(deleteTaskPayload.hasReason());
  }

  /**
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
   * Method under test: {@link DeleteTaskPayload#DeleteTaskPayload()}
   */
  @Test
  void testNewDeleteTaskPayload() {
    // Arrange and Act
    DeleteTaskPayload actualDeleteTaskPayload = new DeleteTaskPayload();

    // Assert
    assertNull(actualDeleteTaskPayload.getReason());
    assertNull(actualDeleteTaskPayload.getTaskId());
    assertFalse(actualDeleteTaskPayload.hasReason());
  }

  /**
   * Method under test:
   * {@link DeleteTaskPayload#DeleteTaskPayload(String, String)}
   */
  @Test
  void testNewDeleteTaskPayload2() {
    // Arrange and Act
    DeleteTaskPayload actualDeleteTaskPayload = new DeleteTaskPayload("42", "Just cause");

    // Assert
    assertEquals("42", actualDeleteTaskPayload.getTaskId());
    assertEquals("Just cause", actualDeleteTaskPayload.getReason());
    assertTrue(actualDeleteTaskPayload.hasReason());
  }
}
