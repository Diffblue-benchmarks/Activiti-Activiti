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

class UpdateProcessPayloadDiffblueTest {
  /**
   * Test {@link UpdateProcessPayload#UpdateProcessPayload()}.
   * <p>
   * Method under test: {@link UpdateProcessPayload#UpdateProcessPayload()}
   */
  @Test
  @DisplayName("Test new UpdateProcessPayload()")
  void testNewUpdateProcessPayload() {
    // Arrange and Act
    UpdateProcessPayload actualUpdateProcessPayload = new UpdateProcessPayload();

    // Assert
    assertNull(actualUpdateProcessPayload.getBusinessKey());
    assertNull(actualUpdateProcessPayload.getDescription());
    assertNull(actualUpdateProcessPayload.getName());
    assertNull(actualUpdateProcessPayload.getProcessInstanceId());
  }

  /**
   * Test
   * {@link UpdateProcessPayload#UpdateProcessPayload(String, String, String, String)}.
   * <p>
   * Method under test:
   * {@link UpdateProcessPayload#UpdateProcessPayload(String, String, String, String)}
   */
  @Test
  @DisplayName("Test new UpdateProcessPayload(String, String, String, String)")
  void testNewUpdateProcessPayload2() {
    // Arrange and Act
    UpdateProcessPayload actualUpdateProcessPayload = new UpdateProcessPayload("42", "Name",
        "The characteristics of someone or something", "Business Key");

    // Assert
    assertEquals("42", actualUpdateProcessPayload.getProcessInstanceId());
    assertEquals("Business Key", actualUpdateProcessPayload.getBusinessKey());
    assertEquals("Name", actualUpdateProcessPayload.getName());
    assertEquals("The characteristics of someone or something", actualUpdateProcessPayload.getDescription());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateProcessPayload#setBusinessKey(String)}
   *   <li>{@link UpdateProcessPayload#setDescription(String)}
   *   <li>{@link UpdateProcessPayload#setName(String)}
   *   <li>{@link UpdateProcessPayload#setProcessInstanceId(String)}
   *   <li>{@link UpdateProcessPayload#getBusinessKey()}
   *   <li>{@link UpdateProcessPayload#getDescription()}
   *   <li>{@link UpdateProcessPayload#getId()}
   *   <li>{@link UpdateProcessPayload#getName()}
   *   <li>{@link UpdateProcessPayload#getProcessInstanceId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    UpdateProcessPayload updateProcessPayload = new UpdateProcessPayload();

    // Act
    updateProcessPayload.setBusinessKey("Business Key");
    updateProcessPayload.setDescription("The characteristics of someone or something");
    updateProcessPayload.setName("Name");
    updateProcessPayload.setProcessInstanceId("42");
    String actualBusinessKey = updateProcessPayload.getBusinessKey();
    String actualDescription = updateProcessPayload.getDescription();
    updateProcessPayload.getId();
    String actualName = updateProcessPayload.getName();

    // Assert that nothing has changed
    assertEquals("42", updateProcessPayload.getProcessInstanceId());
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("Name", actualName);
    assertEquals("The characteristics of someone or something", actualDescription);
  }
}
