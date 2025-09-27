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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CreateProcessInstancePayloadDiffblueTest {
  /**
   * Test {@link CreateProcessInstancePayload#CreateProcessInstancePayload()}.
   *
   * <p>Method under test: {@link CreateProcessInstancePayload#CreateProcessInstancePayload()}
   */
  @Test
  @DisplayName("Test new CreateProcessInstancePayload()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateProcessInstancePayload.<init>()"})
  void testNewCreateProcessInstancePayload() {
    // Arrange and Act
    CreateProcessInstancePayload actualCreateProcessInstancePayload =
        new CreateProcessInstancePayload();

    // Assert
    assertNull(actualCreateProcessInstancePayload.getBusinessKey());
    assertNull(actualCreateProcessInstancePayload.getName());
    assertNull(actualCreateProcessInstancePayload.getProcessDefinitionId());
    assertNull(actualCreateProcessInstancePayload.getProcessDefinitionKey());
  }

  /**
   * Test {@link CreateProcessInstancePayload#CreateProcessInstancePayload(String, String, String,
   * String)}.
   *
   * <p>Method under test: {@link CreateProcessInstancePayload#CreateProcessInstancePayload(String,
   * String, String, String)}
   */
  @Test
  @DisplayName("Test new CreateProcessInstancePayload(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateProcessInstancePayload.<init>(String, String, String, String)"})
  void testNewCreateProcessInstancePayload2() {
    // Arrange and Act
    CreateProcessInstancePayload actualCreateProcessInstancePayload =
        new CreateProcessInstancePayload("42", "Process Definition Key", "Name", "Business Key");

    // Assert
    assertEquals("42", actualCreateProcessInstancePayload.getProcessDefinitionId());
    assertEquals("Business Key", actualCreateProcessInstancePayload.getBusinessKey());
    assertEquals("Name", actualCreateProcessInstancePayload.getName());
    assertEquals(
        "Process Definition Key", actualCreateProcessInstancePayload.getProcessDefinitionKey());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String CreateProcessInstancePayload.getBusinessKey()",
    "String CreateProcessInstancePayload.getId()",
    "String CreateProcessInstancePayload.getName()",
    "String CreateProcessInstancePayload.getProcessDefinitionId()",
    "String CreateProcessInstancePayload.getProcessDefinitionKey()",
    "void CreateProcessInstancePayload.setName(String)"
  })
  void testGettersAndSetters() {
    // Arrange
    CreateProcessInstancePayload createProcessInstancePayload = new CreateProcessInstancePayload();

    // Act
    createProcessInstancePayload.setName("Name");
    String actualBusinessKey = createProcessInstancePayload.getBusinessKey();
    createProcessInstancePayload.getId();
    String actualName = createProcessInstancePayload.getName();
    String actualProcessDefinitionId = createProcessInstancePayload.getProcessDefinitionId();

    // Assert
    assertEquals("Name", actualName);
    assertNull(actualBusinessKey);
    assertNull(actualProcessDefinitionId);
    assertNull(createProcessInstancePayload.getProcessDefinitionKey());
  }
}
