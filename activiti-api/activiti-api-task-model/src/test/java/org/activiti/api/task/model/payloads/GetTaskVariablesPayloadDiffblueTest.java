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

class GetTaskVariablesPayloadDiffblueTest {
  /**
   * Test {@link GetTaskVariablesPayload#GetTaskVariablesPayload()}.
   * <p>
   * Method under test: {@link GetTaskVariablesPayload#GetTaskVariablesPayload()}
   */
  @Test
  @DisplayName("Test new GetTaskVariablesPayload()")
  void testNewGetTaskVariablesPayload() {
    // Arrange, Act and Assert
    assertNull((new GetTaskVariablesPayload()).getTaskId());
    assertEquals("42", (new GetTaskVariablesPayload("42")).getTaskId());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetTaskVariablesPayload#setTaskId(String)}
   *   <li>{@link GetTaskVariablesPayload#getId()}
   *   <li>{@link GetTaskVariablesPayload#getTaskId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    GetTaskVariablesPayload getTaskVariablesPayload = new GetTaskVariablesPayload();

    // Act
    getTaskVariablesPayload.setTaskId("42");
    getTaskVariablesPayload.getId();

    // Assert that nothing has changed
    assertEquals("42", getTaskVariablesPayload.getTaskId());
  }
}
