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
import org.junit.jupiter.api.Test;

class DeleteProcessPayloadDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DeleteProcessPayload#setProcessInstanceId(String)}
   *   <li>{@link DeleteProcessPayload#setReason(String)}
   *   <li>{@link DeleteProcessPayload#getId()}
   *   <li>{@link DeleteProcessPayload#getProcessInstanceId()}
   *   <li>{@link DeleteProcessPayload#getReason()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    DeleteProcessPayload deleteProcessPayload = new DeleteProcessPayload();

    // Act
    deleteProcessPayload.setProcessInstanceId("42");
    deleteProcessPayload.setReason("Just cause");
    deleteProcessPayload.getId();
    String actualProcessInstanceId = deleteProcessPayload.getProcessInstanceId();

    // Assert that nothing has changed
    assertEquals("42", actualProcessInstanceId);
    assertEquals("Just cause", deleteProcessPayload.getReason());
  }

  /**
   * Method under test: {@link DeleteProcessPayload#DeleteProcessPayload()}
   */
  @Test
  void testNewDeleteProcessPayload() {
    // Arrange and Act
    DeleteProcessPayload actualDeleteProcessPayload = new DeleteProcessPayload();

    // Assert
    assertNull(actualDeleteProcessPayload.getProcessInstanceId());
    assertNull(actualDeleteProcessPayload.getReason());
  }

  /**
   * Method under test:
   * {@link DeleteProcessPayload#DeleteProcessPayload(String, String)}
   */
  @Test
  void testNewDeleteProcessPayload2() {
    // Arrange and Act
    DeleteProcessPayload actualDeleteProcessPayload = new DeleteProcessPayload("42", "Just cause");

    // Assert
    assertEquals("42", actualDeleteProcessPayload.getProcessInstanceId());
    assertEquals("Just cause", actualDeleteProcessPayload.getReason());
  }
}
