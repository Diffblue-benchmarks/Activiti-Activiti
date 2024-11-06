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
package org.activiti.test.matchers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.task.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskMatchersDiffblueTest {
  /**
   * Test {@link TaskMatchers#assignee(String)}.
   * <p>
   * Method under test: {@link TaskMatchers#assignee(String)}
   */
  @Test
  @DisplayName("Test assignee(String)")
  void testAssignee() {
    // Arrange and Act
    TaskResultMatcher actualAssigneeResult = TaskMatchers.task().assignee("Assignee");
    Task task = mock(Task.class);
    when(task.getAssignee()).thenReturn("Assignee");
    actualAssigneeResult.match(task);

    // Assert
    verify(task).getAssignee();
  }

  /**
   * Test {@link TaskMatchers#assignee(String)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskMatchers#assignee(String)}
   */
  @Test
  @DisplayName("Test assignee(String); then throw RuntimeException")
  void testAssignee_thenThrowRuntimeException() {
    // Arrange and Act
    TaskResultMatcher actualAssigneeResult = TaskMatchers.task().assignee("Assignee");
    Task task = mock(Task.class);
    when(task.getAssignee()).thenThrow(new RuntimeException("foo"));

    // Assert
    assertThrows(RuntimeException.class, () -> actualAssigneeResult.match(task));
    verify(task).getAssignee();
  }

  /**
   * Test {@link TaskMatchers#withAssignee(String)}.
   * <p>
   * Method under test: {@link TaskMatchers#withAssignee(String)}
   */
  @Test
  @DisplayName("Test withAssignee(String)")
  void testWithAssignee() {
    // Arrange and Act
    TaskResultMatcher actualWithAssigneeResult = TaskMatchers.withAssignee("Assignee");
    Task task = mock(Task.class);
    when(task.getAssignee()).thenReturn("Assignee");
    actualWithAssigneeResult.match(task);

    // Assert
    verify(task).getAssignee();
  }

  /**
   * Test {@link TaskMatchers#withAssignee(String)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskMatchers#withAssignee(String)}
   */
  @Test
  @DisplayName("Test withAssignee(String); then throw RuntimeException")
  void testWithAssignee_thenThrowRuntimeException() {
    // Arrange and Act
    TaskResultMatcher actualWithAssigneeResult = TaskMatchers.withAssignee("Assignee");
    Task task = mock(Task.class);
    when(task.getAssignee()).thenThrow(new RuntimeException("foo"));

    // Assert
    assertThrows(RuntimeException.class, () -> actualWithAssigneeResult.match(task));
    verify(task).getAssignee();
  }
}
