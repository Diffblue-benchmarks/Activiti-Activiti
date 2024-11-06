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
package org.activiti.test.assertions;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.task.model.Task;
import org.activiti.test.EventSource;
import org.activiti.test.TaskSource;
import org.activiti.test.matchers.ProcessTaskMatcher;
import org.activiti.test.matchers.TaskResultMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskAssertionsImplDiffblueTest {
  /**
   * Test {@link TaskAssertionsImpl#expectFields(TaskResultMatcher[])}.
   * <p>
   * Method under test:
   * {@link TaskAssertionsImpl#expectFields(TaskResultMatcher[])}
   */
  @Test
  @DisplayName("Test expectFields(TaskResultMatcher[])")
  void testExpectFields() {
    // Arrange
    Task task = mock(Task.class);
    TaskAssertionsImpl taskAssertionsImpl = new TaskAssertionsImpl(task, new ArrayList<>(), mock(EventSource.class));
    TaskResultMatcher taskResultMatcher = mock(TaskResultMatcher.class);
    doNothing().when(taskResultMatcher).match(Mockito.<Task>any());

    // Act
    TaskAssertions actualExpectFieldsResult = taskAssertionsImpl.expectFields(taskResultMatcher);

    // Assert
    verify(taskResultMatcher).match(isA(Task.class));
    assertTrue(actualExpectFieldsResult instanceof TaskAssertionsImpl);
    assertSame(taskAssertionsImpl, actualExpectFieldsResult);
  }

  /**
   * Test {@link TaskAssertionsImpl#expect(ProcessTaskMatcher[])}.
   * <p>
   * Method under test: {@link TaskAssertionsImpl#expect(ProcessTaskMatcher[])}
   */
  @Test
  @DisplayName("Test expect(ProcessTaskMatcher[])")
  void testExpect() {
    // Arrange
    Task task = mock(Task.class);
    when(task.getProcessInstanceId()).thenReturn("42");
    TaskAssertionsImpl taskAssertionsImpl = new TaskAssertionsImpl(task, new ArrayList<>(), mock(EventSource.class));
    ProcessTaskMatcher processTaskMatcher = mock(ProcessTaskMatcher.class);
    doNothing().when(processTaskMatcher).match(Mockito.<String>any(), Mockito.<List<TaskSource>>any());

    // Act
    TaskAssertions actualExpectResult = taskAssertionsImpl.expect(processTaskMatcher);

    // Assert
    verify(task).getProcessInstanceId();
    verify(processTaskMatcher).match(eq("42"), isA(List.class));
    assertTrue(actualExpectResult instanceof TaskAssertionsImpl);
    assertSame(taskAssertionsImpl, actualExpectResult);
  }
}
