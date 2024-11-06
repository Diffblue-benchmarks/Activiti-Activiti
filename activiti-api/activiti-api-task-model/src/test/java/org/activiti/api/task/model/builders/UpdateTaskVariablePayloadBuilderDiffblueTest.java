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
import org.activiti.api.task.model.payloads.UpdateTaskVariablePayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UpdateTaskVariablePayloadBuilderDiffblueTest {
  /**
   * Test {@link UpdateTaskVariablePayloadBuilder#withVariable(String, Object)}.
   * <p>
   * Method under test:
   * {@link UpdateTaskVariablePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object)")
  void testWithVariable() {
    // Arrange
    UpdateTaskVariablePayloadBuilder updateVariableResult = TaskPayloadBuilder.updateVariable();

    // Act
    UpdateTaskVariablePayloadBuilder actualWithVariableResult = updateVariableResult.withVariable("Name", "Value");

    // Assert
    UpdateTaskVariablePayload buildResult = updateVariableResult.build();
    assertEquals("Name", buildResult.getName());
    assertEquals("Value", buildResult.getValue());
    assertSame(updateVariableResult, actualWithVariableResult);
  }

  /**
   * Test {@link UpdateTaskVariablePayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateTaskVariablePayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link UpdateTaskVariablePayloadBuilder}
   *   <li>{@link UpdateTaskVariablePayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange and Act
    UpdateTaskVariablePayload actualBuildResult = (new UpdateTaskVariablePayloadBuilder()).withTaskId("42").build();

    // Assert
    assertEquals("42", actualBuildResult.getTaskId());
    assertNull(actualBuildResult.getValue());
    assertNull(actualBuildResult.getName());
  }
}
