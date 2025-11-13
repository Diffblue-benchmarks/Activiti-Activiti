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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.task.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TaskMatchersDiffblueTest {
  /**
   * Test {@link TaskMatchers#hasBeenAssigned()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link TaskMatchers#hasBeenAssigned()}
   */
  @Test
  @DisplayName("Test hasBeenAssigned(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.test.matchers.OperationScopeMatcher TaskMatchers.hasBeenAssigned()"
  })
  void testHasBeenAssigned_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> TaskMatchers.task().hasBeenAssigned());
  }

  /**
   * Test {@link TaskMatchers#hasBeenCompleted()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link TaskMatchers#hasBeenCompleted()}
   */
  @Test
  @DisplayName("Test hasBeenCompleted(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.test.matchers.OperationScopeMatcher TaskMatchers.hasBeenCompleted()"
  })
  void testHasBeenCompleted_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> TaskMatchers.task().hasBeenCompleted());
  }

  /**
   * Test {@link TaskMatchers#assignee(String)}.
   *
   * <ul>
   *   <li>Then calls {@link Task#getAssignee()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskMatchers#assignee(String)}
   */
  @Test
  @DisplayName("Test assignee(String); then calls getAssignee()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskResultMatcher TaskMatchers.assignee(String)"})
  void testAssignee_thenCallsGetAssignee() {
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
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link TaskMatchers#assignee(String)}
   */
  @Test
  @DisplayName("Test assignee(String); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskResultMatcher TaskMatchers.assignee(String)"})
  void testAssignee_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> TaskMatchers.task().assignee("Assignee"));
  }

  /**
   * Test {@link TaskMatchers#assignee(String)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskMatchers#assignee(String)}
   */
  @Test
  @DisplayName("Test assignee(String); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskResultMatcher TaskMatchers.assignee(String)"})
  void testAssignee_thenThrowRuntimeException() {
    // Arrange and Act
    TaskResultMatcher actualAssigneeResult = TaskMatchers.task().assignee("Assignee");
    Task task = mock(Task.class);
    when(task.getAssignee()).thenThrow(new RuntimeException());

    // Assert
    assertThrows(RuntimeException.class, () -> actualAssigneeResult.match(task));
    verify(task).getAssignee();
  }

  /**
   * Test {@link TaskMatchers#withAssignee(String)}.
   *
   * <ul>
   *   <li>Then calls {@link Task#getAssignee()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskMatchers#withAssignee(String)}
   */
  @Test
  @DisplayName("Test withAssignee(String); then calls getAssignee()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskResultMatcher TaskMatchers.withAssignee(String)"})
  void testWithAssignee_thenCallsGetAssignee() {
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
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link TaskMatchers#withAssignee(String)}
   */
  @Test
  @DisplayName("Test withAssignee(String); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskResultMatcher TaskMatchers.withAssignee(String)"})
  void testWithAssignee_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> TaskMatchers.withAssignee("Assignee"));
  }

  /**
   * Test {@link TaskMatchers#withAssignee(String)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskMatchers#withAssignee(String)}
   */
  @Test
  @DisplayName("Test withAssignee(String); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskResultMatcher TaskMatchers.withAssignee(String)"})
  void testWithAssignee_thenThrowRuntimeException() {
    // Arrange and Act
    TaskResultMatcher actualWithAssigneeResult = TaskMatchers.withAssignee("Assignee");
    Task task = mock(Task.class);
    when(task.getAssignee()).thenThrow(new RuntimeException());

    // Assert
    assertThrows(RuntimeException.class, () -> actualWithAssigneeResult.match(task));
    verify(task).getAssignee();
  }
}
