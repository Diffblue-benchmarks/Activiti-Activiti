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
import java.util.ArrayList;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.ProcessInstance.ProcessInstanceStatus;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.Task.TaskStatus;
import org.activiti.test.TaskSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessInstanceMatchersDiffblueTest {
  /**
   * Test {@link ProcessInstanceMatchers#hasBeenStarted()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#hasBeenStarted()}
   */
  @Test
  @DisplayName("Test hasBeenStarted(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.test.matchers.OperationScopeMatcher ProcessInstanceMatchers.hasBeenStarted()"
  })
  void testHasBeenStarted_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> ProcessInstanceMatchers.processInstance().hasBeenStarted());
  }

  /**
   * Test {@link ProcessInstanceMatchers#hasBeenCompleted()}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#hasBeenCompleted()}
   */
  @Test
  @DisplayName("Test hasBeenCompleted(); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.test.matchers.OperationScopeMatcher ProcessInstanceMatchers.hasBeenCompleted()"
  })
  void testHasBeenCompleted_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> ProcessInstanceMatchers.processInstance().hasBeenCompleted());
  }

  /**
   * Test {@link ProcessInstanceMatchers#status(ProcessInstanceStatus)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessInstance#getStatus()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#status(ProcessInstanceStatus)}
   */
  @Test
  @DisplayName("Test status(ProcessInstanceStatus); then calls getStatus()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessResultMatcher ProcessInstanceMatchers.status(ProcessInstanceStatus)"})
  void testStatus_thenCallsGetStatus() {
    // Arrange and Act
    ProcessResultMatcher actualStatusResult =
        ProcessInstanceMatchers.processInstance().status(ProcessInstanceStatus.CREATED);
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getStatus()).thenReturn(ProcessInstanceStatus.CREATED);
    actualStatusResult.match(processInstance);

    // Assert
    verify(processInstance).getStatus();
  }

  /**
   * Test {@link ProcessInstanceMatchers#status(ProcessInstanceStatus)}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#status(ProcessInstanceStatus)}
   */
  @Test
  @DisplayName("Test status(ProcessInstanceStatus); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessResultMatcher ProcessInstanceMatchers.status(ProcessInstanceStatus)"})
  void testStatus_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(
        () -> ProcessInstanceMatchers.processInstance().status(ProcessInstanceStatus.CREATED));
  }

  /**
   * Test {@link ProcessInstanceMatchers#status(ProcessInstanceStatus)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#status(ProcessInstanceStatus)}
   */
  @Test
  @DisplayName("Test status(ProcessInstanceStatus); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessResultMatcher ProcessInstanceMatchers.status(ProcessInstanceStatus)"})
  void testStatus_thenThrowRuntimeException() {
    // Arrange and Act
    ProcessResultMatcher actualStatusResult =
        ProcessInstanceMatchers.processInstance().status(ProcessInstanceStatus.CREATED);
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getStatus()).thenThrow(new RuntimeException());

    // Assert
    assertThrows(RuntimeException.class, () -> actualStatusResult.match(processInstance));
    verify(processInstance).getStatus();
  }

  /**
   * Test {@link ProcessInstanceMatchers#name(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessInstance#getName()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#name(String)}
   */
  @Test
  @DisplayName("Test name(String); then calls getName()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessResultMatcher ProcessInstanceMatchers.name(String)"})
  void testName_thenCallsGetName() {
    // Arrange and Act
    ProcessResultMatcher actualNameResult = ProcessInstanceMatchers.processInstance().name("Name");
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getName()).thenReturn("Name");
    actualNameResult.match(processInstance);

    // Assert
    verify(processInstance).getName();
  }

  /**
   * Test {@link ProcessInstanceMatchers#name(String)}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#name(String)}
   */
  @Test
  @DisplayName("Test name(String); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessResultMatcher ProcessInstanceMatchers.name(String)"})
  void testName_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> ProcessInstanceMatchers.processInstance().name("Name"));
  }

  /**
   * Test {@link ProcessInstanceMatchers#name(String)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#name(String)}
   */
  @Test
  @DisplayName("Test name(String); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessResultMatcher ProcessInstanceMatchers.name(String)"})
  void testName_thenThrowRuntimeException() {
    // Arrange and Act
    ProcessResultMatcher actualNameResult = ProcessInstanceMatchers.processInstance().name("Name");
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getName()).thenThrow(new RuntimeException());

    // Assert
    assertThrows(RuntimeException.class, () -> actualNameResult.match(processInstance));
    verify(processInstance).getName();
  }

  /**
   * Test {@link ProcessInstanceMatchers#businessKey(String)}.
   *
   * <ul>
   *   <li>Then calls {@link ProcessInstance#getBusinessKey()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#businessKey(String)}
   */
  @Test
  @DisplayName("Test businessKey(String); then calls getBusinessKey()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessResultMatcher ProcessInstanceMatchers.businessKey(String)"})
  void testBusinessKey_thenCallsGetBusinessKey() {
    // Arrange and Act
    ProcessResultMatcher actualBusinessKeyResult =
        ProcessInstanceMatchers.processInstance().businessKey("Business Key");
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getBusinessKey()).thenReturn("Business Key");
    actualBusinessKeyResult.match(processInstance);

    // Assert
    verify(processInstance).getBusinessKey();
  }

  /**
   * Test {@link ProcessInstanceMatchers#businessKey(String)}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#businessKey(String)}
   */
  @Test
  @DisplayName("Test businessKey(String); then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessResultMatcher ProcessInstanceMatchers.businessKey(String)"})
  void testBusinessKey_thenDoesNotThrow() {
    // Arrange, Act and Assert
    assertDoesNotThrow(() -> ProcessInstanceMatchers.processInstance().businessKey("Business Key"));
  }

  /**
   * Test {@link ProcessInstanceMatchers#businessKey(String)}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#businessKey(String)}
   */
  @Test
  @DisplayName("Test businessKey(String); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessResultMatcher ProcessInstanceMatchers.businessKey(String)"})
  void testBusinessKey_thenThrowRuntimeException() {
    // Arrange and Act
    ProcessResultMatcher actualBusinessKeyResult =
        ProcessInstanceMatchers.processInstance().businessKey("Business Key");
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getBusinessKey()).thenThrow(new RuntimeException());

    // Assert
    assertThrows(RuntimeException.class, () -> actualBusinessKeyResult.match(processInstance));
    verify(processInstance).getBusinessKey();
  }

  /**
   * Test {@link ProcessInstanceMatchers#hasTask(String, TaskStatus, TaskResultMatcher[])}.
   *
   * <ul>
   *   <li>Then calls {@link TaskSource#getTasks(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#hasTask(String, Task.TaskStatus,
   * TaskResultMatcher[])}
   */
  @Test
  @DisplayName("Test hasTask(String, TaskStatus, TaskResultMatcher[]); then calls getTasks(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessTaskMatcher ProcessInstanceMatchers.hasTask(String, Task.TaskStatus, TaskResultMatcher[])"
  })
  void testHasTask_thenCallsGetTasks() {
    // Arrange and Act
    ProcessTaskMatcher actualHasTaskResult =
        ProcessInstanceMatchers.processInstance()
            .hasTask("Task Name", TaskStatus.CREATED, mock(TaskResultMatcher.class));
    TaskSource taskSource = mock(TaskSource.class);
    when(taskSource.getTasks(Mockito.<String>any())).thenThrow(new RuntimeException());
    when(taskSource.canHandle(Mockito.<TaskStatus>any())).thenReturn(true);
    ArrayList<TaskSource> taskSources = new ArrayList<>();
    taskSources.add(taskSource);

    // Assert
    assertThrows(RuntimeException.class, () -> actualHasTaskResult.match("42", taskSources));
    verify(taskSource).canHandle(TaskStatus.CREATED);
    verify(taskSource).getTasks("42");
  }

  /**
   * Test {@link ProcessInstanceMatchers#hasTask(String, TaskStatus, TaskResultMatcher[])}.
   *
   * <ul>
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessInstanceMatchers#hasTask(String, Task.TaskStatus,
   * TaskResultMatcher[])}
   */
  @Test
  @DisplayName("Test hasTask(String, TaskStatus, TaskResultMatcher[]); then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessTaskMatcher ProcessInstanceMatchers.hasTask(String, Task.TaskStatus, TaskResultMatcher[])"
  })
  void testHasTask_thenThrowRuntimeException() {
    // Arrange and Act
    ProcessTaskMatcher actualHasTaskResult =
        ProcessInstanceMatchers.processInstance()
            .hasTask("Task Name", TaskStatus.CREATED, mock(TaskResultMatcher.class));
    TaskSource taskSource = mock(TaskSource.class);
    when(taskSource.canHandle(Mockito.<TaskStatus>any())).thenThrow(new RuntimeException());
    ArrayList<TaskSource> taskSources = new ArrayList<>();
    taskSources.add(taskSource);

    // Assert
    assertThrows(RuntimeException.class, () -> actualHasTaskResult.match("42", taskSources));
    verify(taskSource).canHandle(TaskStatus.CREATED);
  }
}
