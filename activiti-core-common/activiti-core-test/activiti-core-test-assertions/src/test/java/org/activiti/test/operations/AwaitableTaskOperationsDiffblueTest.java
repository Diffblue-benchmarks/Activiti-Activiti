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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.test.EventSource;
import org.activiti.test.assertions.AwaitTaskAssertions;
import org.activiti.test.assertions.TaskAssertions;
import org.activiti.test.assertions.TaskAssertionsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AwaitableTaskOperationsDiffblueTest {
  /**
   * Test {@link AwaitableTaskOperations#claim(ClaimTaskPayload)}.
   * <ul>
   *   <li>Given
   * {@link AwaitableTaskOperations#AwaitableTaskOperations(TaskOperations, boolean)}
   * with {@link TaskOperations} and awaitEnabled is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AwaitableTaskOperations#claim(ClaimTaskPayload)}
   */
  @Test
  @DisplayName("Test claim(ClaimTaskPayload); given AwaitableTaskOperations(TaskOperations, boolean) with TaskOperations and awaitEnabled is 'true'")
  void testClaim_givenAwaitableTaskOperationsWithTaskOperationsAndAwaitEnabledIsTrue() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskOperations taskOperations = mock(TaskOperations.class);
    Task task = mock(Task.class);
    when(taskOperations.claim(Mockito.<ClaimTaskPayload>any()))
        .thenReturn(new AwaitTaskAssertions(new TaskAssertionsImpl(task, new ArrayList<>(), mock(EventSource.class))));
    AwaitableTaskOperations awaitableTaskOperations = new AwaitableTaskOperations(taskOperations, true);

    // Act
    TaskAssertions actualClaimResult = awaitableTaskOperations.claim(new ClaimTaskPayload());

    // Assert
    verify(taskOperations).claim(isA(ClaimTaskPayload.class));
    assertTrue(actualClaimResult instanceof AwaitTaskAssertions);
  }

  /**
   * Test {@link AwaitableTaskOperations#claim(ClaimTaskPayload)}.
   * <ul>
   *   <li>Then return
   * {@link AwaitTaskAssertions#AwaitTaskAssertions(TaskAssertions)} with
   * taskAssertions is
   * {@link TaskAssertionsImpl#TaskAssertionsImpl(Task, List, EventSource)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AwaitableTaskOperations#claim(ClaimTaskPayload)}
   */
  @Test
  @DisplayName("Test claim(ClaimTaskPayload); then return AwaitTaskAssertions(TaskAssertions) with taskAssertions is TaskAssertionsImpl(Task, List, EventSource)")
  void testClaim_thenReturnAwaitTaskAssertionsWithTaskAssertionsIsTaskAssertionsImpl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskOperations taskOperations = mock(TaskOperations.class);
    Task task = mock(Task.class);
    AwaitTaskAssertions awaitTaskAssertions = new AwaitTaskAssertions(
        new TaskAssertionsImpl(task, new ArrayList<>(), mock(EventSource.class)));
    when(taskOperations.claim(Mockito.<ClaimTaskPayload>any())).thenReturn(awaitTaskAssertions);
    AwaitableTaskOperations awaitableTaskOperations = new AwaitableTaskOperations(taskOperations, false);

    // Act
    TaskAssertions actualClaimResult = awaitableTaskOperations.claim(new ClaimTaskPayload());

    // Assert
    verify(taskOperations).claim(isA(ClaimTaskPayload.class));
    assertTrue(actualClaimResult instanceof AwaitTaskAssertions);
    assertSame(awaitTaskAssertions, actualClaimResult);
  }

  /**
   * Test {@link AwaitableTaskOperations#complete(CompleteTaskPayload)}.
   * <ul>
   *   <li>Given
   * {@link AwaitableTaskOperations#AwaitableTaskOperations(TaskOperations, boolean)}
   * with {@link TaskOperations} and awaitEnabled is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AwaitableTaskOperations#complete(CompleteTaskPayload)}
   */
  @Test
  @DisplayName("Test complete(CompleteTaskPayload); given AwaitableTaskOperations(TaskOperations, boolean) with TaskOperations and awaitEnabled is 'true'")
  void testComplete_givenAwaitableTaskOperationsWithTaskOperationsAndAwaitEnabledIsTrue() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskOperations taskOperations = mock(TaskOperations.class);
    Task task = mock(Task.class);
    when(taskOperations.complete(Mockito.<CompleteTaskPayload>any()))
        .thenReturn(new AwaitTaskAssertions(new TaskAssertionsImpl(task, new ArrayList<>(), mock(EventSource.class))));
    AwaitableTaskOperations awaitableTaskOperations = new AwaitableTaskOperations(taskOperations, true);

    // Act
    TaskAssertions actualCompleteResult = awaitableTaskOperations.complete(new CompleteTaskPayload());

    // Assert
    verify(taskOperations).complete(isA(CompleteTaskPayload.class));
    assertTrue(actualCompleteResult instanceof AwaitTaskAssertions);
  }

  /**
   * Test {@link AwaitableTaskOperations#complete(CompleteTaskPayload)}.
   * <ul>
   *   <li>Then return
   * {@link AwaitTaskAssertions#AwaitTaskAssertions(TaskAssertions)} with
   * taskAssertions is
   * {@link TaskAssertionsImpl#TaskAssertionsImpl(Task, List, EventSource)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AwaitableTaskOperations#complete(CompleteTaskPayload)}
   */
  @Test
  @DisplayName("Test complete(CompleteTaskPayload); then return AwaitTaskAssertions(TaskAssertions) with taskAssertions is TaskAssertionsImpl(Task, List, EventSource)")
  void testComplete_thenReturnAwaitTaskAssertionsWithTaskAssertionsIsTaskAssertionsImpl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskOperations taskOperations = mock(TaskOperations.class);
    Task task = mock(Task.class);
    AwaitTaskAssertions awaitTaskAssertions = new AwaitTaskAssertions(
        new TaskAssertionsImpl(task, new ArrayList<>(), mock(EventSource.class)));
    when(taskOperations.complete(Mockito.<CompleteTaskPayload>any())).thenReturn(awaitTaskAssertions);
    AwaitableTaskOperations awaitableTaskOperations = new AwaitableTaskOperations(taskOperations, false);

    // Act
    TaskAssertions actualCompleteResult = awaitableTaskOperations.complete(new CompleteTaskPayload());

    // Assert
    verify(taskOperations).complete(isA(CompleteTaskPayload.class));
    assertTrue(actualCompleteResult instanceof AwaitTaskAssertions);
    assertSame(awaitTaskAssertions, actualCompleteResult);
  }
}
