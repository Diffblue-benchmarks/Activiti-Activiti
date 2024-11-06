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
package org.activiti.api.process.model.payloads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResumeProcessPayloadDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ResumeProcessPayload#ResumeProcessPayload(String)}
   *   <li>{@link ResumeProcessPayload#setProcessInstanceId(String)}
   *   <li>{@link ResumeProcessPayload#getId()}
   *   <li>{@link ResumeProcessPayload#getProcessInstanceId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ResumeProcessPayload actualResumeProcessPayload = new ResumeProcessPayload("42");
    actualResumeProcessPayload.setProcessInstanceId("42");
    actualResumeProcessPayload.getId();

    // Assert that nothing has changed
    assertEquals("42", actualResumeProcessPayload.getProcessInstanceId());
  }

  /**
   * Test {@link ResumeProcessPayload#ResumeProcessPayload()}.
   * <p>
   * Method under test: {@link ResumeProcessPayload#ResumeProcessPayload()}
   */
  @Test
  @DisplayName("Test new ResumeProcessPayload()")
  void testNewResumeProcessPayload() {
    // Arrange, Act and Assert
    assertNull((new ResumeProcessPayload()).getProcessInstanceId());
  }
}
