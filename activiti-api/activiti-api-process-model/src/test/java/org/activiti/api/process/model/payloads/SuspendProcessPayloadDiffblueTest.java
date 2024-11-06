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

class SuspendProcessPayloadDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SuspendProcessPayload#SuspendProcessPayload(String)}
   *   <li>{@link SuspendProcessPayload#setProcessInstanceId(String)}
   *   <li>{@link SuspendProcessPayload#getId()}
   *   <li>{@link SuspendProcessPayload#getProcessInstanceId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    SuspendProcessPayload actualSuspendProcessPayload = new SuspendProcessPayload("42");
    actualSuspendProcessPayload.setProcessInstanceId("42");
    actualSuspendProcessPayload.getId();

    // Assert that nothing has changed
    assertEquals("42", actualSuspendProcessPayload.getProcessInstanceId());
  }

  /**
   * Test {@link SuspendProcessPayload#SuspendProcessPayload()}.
   * <p>
   * Method under test: {@link SuspendProcessPayload#SuspendProcessPayload()}
   */
  @Test
  @DisplayName("Test new SuspendProcessPayload()")
  void testNewSuspendProcessPayload() {
    // Arrange, Act and Assert
    assertNull((new SuspendProcessPayload()).getProcessInstanceId());
  }
}
