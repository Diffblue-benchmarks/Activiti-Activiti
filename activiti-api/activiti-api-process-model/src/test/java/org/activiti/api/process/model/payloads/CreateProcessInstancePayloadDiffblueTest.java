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

class CreateProcessInstancePayloadDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateProcessInstancePayload#setName(String)}
   *   <li>{@link CreateProcessInstancePayload#getBusinessKey()}
   *   <li>{@link CreateProcessInstancePayload#getId()}
   *   <li>{@link CreateProcessInstancePayload#getName()}
   *   <li>{@link CreateProcessInstancePayload#getProcessDefinitionId()}
   *   <li>{@link CreateProcessInstancePayload#getProcessDefinitionKey()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    CreateProcessInstancePayload createProcessInstancePayload = new CreateProcessInstancePayload();

    // Act
    createProcessInstancePayload.setName("Name");
    createProcessInstancePayload.getBusinessKey();
    createProcessInstancePayload.getId();
    String actualName = createProcessInstancePayload.getName();
    createProcessInstancePayload.getProcessDefinitionId();
    createProcessInstancePayload.getProcessDefinitionKey();

    // Assert that nothing has changed
    assertEquals("Name", actualName);
  }

  /**
   * Method under test:
   * {@link CreateProcessInstancePayload#CreateProcessInstancePayload()}
   */
  @Test
  void testNewCreateProcessInstancePayload() {
    // Arrange and Act
    CreateProcessInstancePayload actualCreateProcessInstancePayload = new CreateProcessInstancePayload();

    // Assert
    assertNull(actualCreateProcessInstancePayload.getBusinessKey());
    assertNull(actualCreateProcessInstancePayload.getName());
    assertNull(actualCreateProcessInstancePayload.getProcessDefinitionId());
    assertNull(actualCreateProcessInstancePayload.getProcessDefinitionKey());
  }

  /**
   * Method under test:
   * {@link CreateProcessInstancePayload#CreateProcessInstancePayload(String, String, String, String)}
   */
  @Test
  void testNewCreateProcessInstancePayload2() {
    // Arrange and Act
    CreateProcessInstancePayload actualCreateProcessInstancePayload = new CreateProcessInstancePayload("42",
        "Process Definition Key", "Name", "Business Key");

    // Assert
    assertEquals("42", actualCreateProcessInstancePayload.getProcessDefinitionId());
    assertEquals("Business Key", actualCreateProcessInstancePayload.getBusinessKey());
    assertEquals("Name", actualCreateProcessInstancePayload.getName());
    assertEquals("Process Definition Key", actualCreateProcessInstancePayload.getProcessDefinitionKey());
  }
}
