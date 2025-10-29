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
import org.junit.jupiter.api.Test;

class TaskResultDiffblueTest {
  /**
   * Method under test: {@link TaskResult#TaskResult()}
   */
  @Test
  void testNewTaskResult() {
    // Arrange and Act
    TaskResult actualTaskResult = new TaskResult();

    // Assert
    assertNull(actualTaskResult.getPayload());
    assertNull(actualTaskResult.getEntity());
  }

  /**
   * Method under test: {@link TaskResult#TaskResult(Payload, Task)}
   */
  @Test
  void testNewTaskResult2() {
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
