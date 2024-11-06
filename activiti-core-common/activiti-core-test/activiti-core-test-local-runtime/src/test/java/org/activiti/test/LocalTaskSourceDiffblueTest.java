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
package org.activiti.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.payloads.GetTasksPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LocalTaskSource.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LocalTaskSourceDiffblueTest {
  @Autowired
  private LocalTaskSource localTaskSource;

  @MockBean
  private TaskRuntime taskRuntime;

  /**
   * Test {@link LocalTaskSource#getTasks(String)}.
   * <p>
   * Method under test: {@link LocalTaskSource#getTasks(String)}
   */
  @Test
  @DisplayName("Test getTasks(String)")
  void testGetTasks() {
    // Arrange
    Page<Task> page = mock(Page.class);
    when(page.getContent()).thenReturn(new ArrayList<>());
    when(taskRuntime.tasks(Mockito.<Pageable>any(), Mockito.<GetTasksPayload>any())).thenReturn(page);

    // Act
    List<Task> actualTasks = localTaskSource.getTasks("42");

    // Assert
    verify(page).getContent();
    verify(taskRuntime).tasks(isA(Pageable.class), isA(GetTasksPayload.class));
    assertTrue(actualTasks.isEmpty());
  }

  /**
   * Test {@link LocalTaskSource#canHandle(TaskStatus)}.
   * <ul>
   *   <li>When {@code COMPLETED}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalTaskSource#canHandle(Task.TaskStatus)}
   */
  @Test
  @DisplayName("Test canHandle(TaskStatus); when 'COMPLETED'; then return 'false'")
  void testCanHandle_whenCompleted_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(localTaskSource.canHandle(Task.TaskStatus.COMPLETED));
  }

  /**
   * Test {@link LocalTaskSource#canHandle(TaskStatus)}.
   * <ul>
   *   <li>When {@code CREATED}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalTaskSource#canHandle(Task.TaskStatus)}
   */
  @Test
  @DisplayName("Test canHandle(TaskStatus); when 'CREATED'; then return 'true'")
  void testCanHandle_whenCreated_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(localTaskSource.canHandle(Task.TaskStatus.CREATED));
  }
}
