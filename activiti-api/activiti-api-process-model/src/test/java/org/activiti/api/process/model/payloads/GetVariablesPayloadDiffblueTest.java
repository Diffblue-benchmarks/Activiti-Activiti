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
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetVariablesPayloadDiffblueTest {
  /**
   * Test {@link GetVariablesPayload#GetVariablesPayload()}.
   * <p>
   * Method under test: {@link GetVariablesPayload#GetVariablesPayload()}
   */
  @Test
  @DisplayName("Test new GetVariablesPayload()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetVariablesPayload.<init>()"})
  void testNewGetVariablesPayload() {
    // Arrange, Act and Assert
    assertNull((new GetVariablesPayload()).getProcessInstanceId());
  }

  /**
   * Test {@link GetVariablesPayload#GetVariablesPayload(String)}.
   * <p>
   * Method under test: {@link GetVariablesPayload#GetVariablesPayload(String)}
   */
  @Test
  @DisplayName("Test new GetVariablesPayload(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetVariablesPayload.<init>(String)"})
  void testNewGetVariablesPayload2() {
    // Arrange, Act and Assert
    assertEquals("42", (new GetVariablesPayload("42")).getProcessInstanceId());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetVariablesPayload#setProcessInstanceId(String)}
   *   <li>{@link GetVariablesPayload#getId()}
   *   <li>{@link GetVariablesPayload#getProcessInstanceId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String GetVariablesPayload.getId()", "String GetVariablesPayload.getProcessInstanceId()",
      "void GetVariablesPayload.setProcessInstanceId(String)"})
  void testGettersAndSetters() {
    // Arrange
    GetVariablesPayload getVariablesPayload = new GetVariablesPayload();

    // Act
    getVariablesPayload.setProcessInstanceId("42");
    getVariablesPayload.getId();

    // Assert
    assertEquals("42", getVariablesPayload.getProcessInstanceId());
  }
}
