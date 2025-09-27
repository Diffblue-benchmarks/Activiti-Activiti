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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SaveTaskPayloadDiffblueTest {
  /**
   * Test {@link SaveTaskPayload#SaveTaskPayload()}.
   *
   * <p>Method under test: {@link SaveTaskPayload#SaveTaskPayload()}
   */
  @Test
  @DisplayName("Test new SaveTaskPayload()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SaveTaskPayload.<init>()"})
  void testNewSaveTaskPayload() {
    // Arrange and Act
    SaveTaskPayload actualSaveTaskPayload = new SaveTaskPayload();

    // Assert
    assertNull(actualSaveTaskPayload.getTaskId());
    assertNull(actualSaveTaskPayload.getVariables());
  }

  /**
   * Test {@link SaveTaskPayload#SaveTaskPayload(String, Map)}.
   *
   * <p>Method under test: {@link SaveTaskPayload#SaveTaskPayload(String, Map)}
   */
  @Test
  @DisplayName("Test new SaveTaskPayload(String, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SaveTaskPayload.<init>(String, Map)"})
  void testNewSaveTaskPayload2() {
    // Arrange and Act
    SaveTaskPayload actualSaveTaskPayload = new SaveTaskPayload("42", new HashMap<>());

    // Assert
    assertEquals("42", actualSaveTaskPayload.getTaskId());
    assertTrue(actualSaveTaskPayload.getVariables().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link SaveTaskPayload#setTaskId(String)}
   *   <li>{@link SaveTaskPayload#setVariables(Map)}
   *   <li>{@link SaveTaskPayload#getId()}
   *   <li>{@link SaveTaskPayload#getTaskId()}
   *   <li>{@link SaveTaskPayload#getVariables()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String SaveTaskPayload.getId()",
    "String SaveTaskPayload.getTaskId()",
    "Map SaveTaskPayload.getVariables()",
    "void SaveTaskPayload.setTaskId(String)",
    "void SaveTaskPayload.setVariables(Map)"
  })
  void testGettersAndSetters() {
    // Arrange
    SaveTaskPayload saveTaskPayload = new SaveTaskPayload();

    // Act
    saveTaskPayload.setTaskId("42");
    HashMap<String, Object> variables = new HashMap<>();
    saveTaskPayload.setVariables(variables);
    saveTaskPayload.getId();
    String actualTaskId = saveTaskPayload.getTaskId();
    Map<String, Object> actualVariables = saveTaskPayload.getVariables();

    // Assert
    assertEquals("42", actualTaskId);
    assertTrue(actualVariables.isEmpty());
    assertSame(variables, actualVariables);
  }
}
