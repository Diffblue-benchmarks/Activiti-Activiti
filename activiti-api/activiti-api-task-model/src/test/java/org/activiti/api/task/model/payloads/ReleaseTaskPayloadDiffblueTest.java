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

class ReleaseTaskPayloadDiffblueTest {
  /**
   * Test {@link ReleaseTaskPayload#ReleaseTaskPayload()}.
   * <p>
   * Method under test: {@link ReleaseTaskPayload#ReleaseTaskPayload()}
   */
  @Test
  @DisplayName("Test new ReleaseTaskPayload()")
  void testNewReleaseTaskPayload() {
    // Arrange, Act and Assert
    assertNull((new ReleaseTaskPayload()).getTaskId());
    assertEquals("42", (new ReleaseTaskPayload("42")).getTaskId());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ReleaseTaskPayload#setTaskId(String)}
   *   <li>{@link ReleaseTaskPayload#getId()}
   *   <li>{@link ReleaseTaskPayload#getTaskId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ReleaseTaskPayload releaseTaskPayload = new ReleaseTaskPayload();

    // Act
    releaseTaskPayload.setTaskId("42");
    releaseTaskPayload.getId();

    // Assert that nothing has changed
    assertEquals("42", releaseTaskPayload.getTaskId());
  }
}
