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
package org.activiti.test.operations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.test.EventSource;
import org.activiti.test.TaskSource;
import org.activiti.test.assertions.TaskAssertions;
import org.activiti.test.assertions.TaskAssertionsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaskRuntimeOperations.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TaskRuntimeOperationsDiffblueTest {
  @MockBean
  private EventSource eventSource;

  @Autowired
  private List<TaskSource> list;

  @MockBean
  private TaskRuntime taskRuntime;

  @Autowired
  private TaskRuntimeOperations taskRuntimeOperations;

  @MockBean
  private TaskSource taskSource;

  /**
   * Test {@link TaskRuntimeOperations#claim(ClaimTaskPayload)}.
   * <p>
   * Method under test: {@link TaskRuntimeOperations#claim(ClaimTaskPayload)}
   */
  @Test
  @DisplayName("Test claim(ClaimTaskPayload)")
  void testClaim() {
    // Arrange
    when(taskRuntime.claim(Mockito.<ClaimTaskPayload>any())).thenReturn(mock(Task.class));

    // Act
    TaskAssertions actualClaimResult = taskRuntimeOperations.claim(new ClaimTaskPayload());

    // Assert
    verify(taskRuntime).claim(isA(ClaimTaskPayload.class));
    assertTrue(actualClaimResult instanceof TaskAssertionsImpl);
  }

  /**
   * Test {@link TaskRuntimeOperations#complete(CompleteTaskPayload)}.
   * <ul>
   *   <li>When {@link CompleteTaskPayload#CompleteTaskPayload()}.</li>
   *   <li>Then return {@link TaskAssertionsImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskRuntimeOperations#complete(CompleteTaskPayload)}
   */
  @Test
  @DisplayName("Test complete(CompleteTaskPayload); when CompleteTaskPayload(); then return TaskAssertionsImpl")
  void testComplete_whenCompleteTaskPayload_thenReturnTaskAssertionsImpl() {
    // Arrange
    when(taskRuntime.complete(Mockito.<CompleteTaskPayload>any())).thenReturn(mock(Task.class));
    when(taskRuntime.task(Mockito.<String>any())).thenReturn(mock(Task.class));

    // Act
    TaskAssertions actualCompleteResult = taskRuntimeOperations.complete(new CompleteTaskPayload());

    // Assert
    verify(taskRuntime).complete(isA(CompleteTaskPayload.class));
    verify(taskRuntime).task(isNull());
    assertTrue(actualCompleteResult instanceof TaskAssertionsImpl);
  }
}
