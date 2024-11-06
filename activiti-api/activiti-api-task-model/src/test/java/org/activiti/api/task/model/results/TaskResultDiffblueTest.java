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
package org.activiti.api.task.model.results;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import org.activiti.api.model.shared.Payload;
import org.activiti.api.task.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskResultDiffblueTest {
  /**
   * Test {@link TaskResult#TaskResult()}.
   * <ul>
   *   <li>Then return Payload is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskResult#TaskResult()}
   */
  @Test
  @DisplayName("Test new TaskResult(); then return Payload is 'null'")
  void testNewTaskResult_thenReturnPayloadIsNull() {
    // Arrange and Act
    TaskResult actualTaskResult = new TaskResult();

    // Assert
    assertNull(actualTaskResult.getPayload());
    assertNull(actualTaskResult.getEntity());
  }

  /**
   * Test {@link TaskResult#TaskResult(Payload, Task)}.
   * <ul>
   *   <li>When {@link Payload}.</li>
   *   <li>Then return {@link Payload}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskResult#TaskResult(Payload, Task)}
   */
  @Test
  @DisplayName("Test new TaskResult(Payload, Task); when Payload; then return Payload")
  void testNewTaskResult_whenPayload_thenReturnPayload() {
    // Arrange
    Payload payload = mock(Payload.class);
    Task entity = mock(Task.class);

    // Act
    TaskResult actualTaskResult = new TaskResult(payload, entity);

    // Assert
    assertSame(payload, actualTaskResult.getPayload());
    assertSame(entity, actualTaskResult.getEntity());
  }
}
