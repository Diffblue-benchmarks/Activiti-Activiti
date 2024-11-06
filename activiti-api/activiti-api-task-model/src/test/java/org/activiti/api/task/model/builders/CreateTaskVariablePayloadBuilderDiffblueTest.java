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
package org.activiti.api.task.model.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.activiti.api.task.model.payloads.CreateTaskVariablePayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateTaskVariablePayloadBuilderDiffblueTest {
  /**
   * Test {@link CreateTaskVariablePayloadBuilder#withVariable(String, Object)}.
   * <p>
   * Method under test:
   * {@link CreateTaskVariablePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object)")
  void testWithVariable() {
    // Arrange
    CreateTaskVariablePayloadBuilder createVariableResult = TaskPayloadBuilder.createVariable();

    // Act
    CreateTaskVariablePayloadBuilder actualWithVariableResult = createVariableResult.withVariable("Name", "Value");

    // Assert
    CreateTaskVariablePayload buildResult = createVariableResult.build();
    assertEquals("Name", buildResult.getName());
    assertEquals("Value", buildResult.getValue());
    assertSame(createVariableResult, actualWithVariableResult);
  }

  /**
   * Test {@link CreateTaskVariablePayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateTaskVariablePayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link CreateTaskVariablePayloadBuilder}
   *   <li>{@link CreateTaskVariablePayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange and Act
    CreateTaskVariablePayload actualBuildResult = (new CreateTaskVariablePayloadBuilder()).withTaskId("42").build();

    // Assert
    assertEquals("42", actualBuildResult.getTaskId());
    assertNull(actualBuildResult.getValue());
    assertNull(actualBuildResult.getName());
  }
}
