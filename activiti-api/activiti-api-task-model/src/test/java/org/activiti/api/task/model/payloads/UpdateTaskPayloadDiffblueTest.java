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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UpdateTaskPayloadDiffblueTest {
  /**
   * Test {@link UpdateTaskPayload#UpdateTaskPayload()}.
   * <p>
   * Method under test: {@link UpdateTaskPayload#UpdateTaskPayload()}
   */
  @Test
  @DisplayName("Test new UpdateTaskPayload()")
  void testNewUpdateTaskPayload() {
    // Arrange and Act
    UpdateTaskPayload actualUpdateTaskPayload = new UpdateTaskPayload();

    // Assert
    assertNull(actualUpdateTaskPayload.getPriority());
    assertNull(actualUpdateTaskPayload.getAssignee());
    assertNull(actualUpdateTaskPayload.getDescription());
    assertNull(actualUpdateTaskPayload.getFormKey());
    assertNull(actualUpdateTaskPayload.getName());
    assertNull(actualUpdateTaskPayload.getParentTaskId());
    assertNull(actualUpdateTaskPayload.getTaskId());
    assertNull(actualUpdateTaskPayload.getDueDate());
  }

  /**
   * Test
   * {@link UpdateTaskPayload#UpdateTaskPayload(String, String, String, Date, Integer, String, String, String)}.
   * <p>
   * Method under test:
   * {@link UpdateTaskPayload#UpdateTaskPayload(String, String, String, Date, Integer, String, String, String)}
   */
  @Test
  @DisplayName("Test new UpdateTaskPayload(String, String, String, Date, Integer, String, String, String)")
  void testNewUpdateTaskPayload2() {
    // Arrange and Act
    UpdateTaskPayload actualUpdateTaskPayload = new UpdateTaskPayload("42", "Name",
        "The characteristics of someone or something",
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), 1, "Assignee", "42",
        "Form Key");

    // Assert
    assertEquals("42", actualUpdateTaskPayload.getParentTaskId());
    assertEquals("42", actualUpdateTaskPayload.getTaskId());
    assertEquals("Assignee", actualUpdateTaskPayload.getAssignee());
    assertEquals("Form Key", actualUpdateTaskPayload.getFormKey());
    assertEquals("Name", actualUpdateTaskPayload.getName());
    assertEquals("The characteristics of someone or something", actualUpdateTaskPayload.getDescription());
    assertEquals(1, actualUpdateTaskPayload.getPriority().intValue());
  }

  /**
   * Test
   * {@link UpdateTaskPayload#UpdateTaskPayload(String, String, String, Date, Integer, String, String, String)}.
   * <ul>
   *   <li>When {@link java.sql.Date}.</li>
   *   <li>Then return DueDate is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UpdateTaskPayload#UpdateTaskPayload(String, String, String, java.util.Date, Integer, String, String, String)}
   */
  @Test
  @DisplayName("Test new UpdateTaskPayload(String, String, String, Date, Integer, String, String, String); when Date; then return DueDate is Date")
  void testNewUpdateTaskPayload_whenDate_thenReturnDueDateIsDate() {
    // Arrange
    java.sql.Date dueDate = mock(java.sql.Date.class);

    // Act
    UpdateTaskPayload actualUpdateTaskPayload = new UpdateTaskPayload("42", "Name",
        "The characteristics of someone or something", dueDate, 1, "Assignee", "42", "Form Key");

    // Assert
    assertEquals("42", actualUpdateTaskPayload.getParentTaskId());
    assertEquals("42", actualUpdateTaskPayload.getTaskId());
    assertEquals("Assignee", actualUpdateTaskPayload.getAssignee());
    assertEquals("Form Key", actualUpdateTaskPayload.getFormKey());
    assertEquals("Name", actualUpdateTaskPayload.getName());
    assertEquals("The characteristics of someone or something", actualUpdateTaskPayload.getDescription());
    assertEquals(1, actualUpdateTaskPayload.getPriority().intValue());
    assertSame(dueDate, actualUpdateTaskPayload.getDueDate());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateTaskPayload#setAssignee(String)}
   *   <li>{@link UpdateTaskPayload#setDescription(String)}
   *   <li>{@link UpdateTaskPayload#setDueDate(Date)}
   *   <li>{@link UpdateTaskPayload#setFormKey(String)}
   *   <li>{@link UpdateTaskPayload#setName(String)}
   *   <li>{@link UpdateTaskPayload#setParentTaskId(String)}
   *   <li>{@link UpdateTaskPayload#setPriority(Integer)}
   *   <li>{@link UpdateTaskPayload#setTaskId(String)}
   *   <li>{@link UpdateTaskPayload#getAssignee()}
   *   <li>{@link UpdateTaskPayload#getDescription()}
   *   <li>{@link UpdateTaskPayload#getDueDate()}
   *   <li>{@link UpdateTaskPayload#getFormKey()}
   *   <li>{@link UpdateTaskPayload#getId()}
   *   <li>{@link UpdateTaskPayload#getName()}
   *   <li>{@link UpdateTaskPayload#getParentTaskId()}
   *   <li>{@link UpdateTaskPayload#getPriority()}
   *   <li>{@link UpdateTaskPayload#getTaskId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    UpdateTaskPayload updateTaskPayload = new UpdateTaskPayload();

    // Act
    updateTaskPayload.setAssignee("Assignee");
    updateTaskPayload.setDescription("The characteristics of someone or something");
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    updateTaskPayload.setDueDate(dueDate);
    updateTaskPayload.setFormKey("Form Key");
    updateTaskPayload.setName("Name");
    updateTaskPayload.setParentTaskId("42");
    updateTaskPayload.setPriority(1);
    updateTaskPayload.setTaskId("42");
    String actualAssignee = updateTaskPayload.getAssignee();
    String actualDescription = updateTaskPayload.getDescription();
    Date actualDueDate = updateTaskPayload.getDueDate();
    String actualFormKey = updateTaskPayload.getFormKey();
    updateTaskPayload.getId();
    String actualName = updateTaskPayload.getName();
    String actualParentTaskId = updateTaskPayload.getParentTaskId();
    Integer actualPriority = updateTaskPayload.getPriority();

    // Assert that nothing has changed
    assertEquals("42", actualParentTaskId);
    assertEquals("42", updateTaskPayload.getTaskId());
    assertEquals("Assignee", actualAssignee);
    assertEquals("Form Key", actualFormKey);
    assertEquals("Name", actualName);
    assertEquals("The characteristics of someone or something", actualDescription);
    assertEquals(1, actualPriority.intValue());
    assertSame(dueDate, actualDueDate);
  }
}
