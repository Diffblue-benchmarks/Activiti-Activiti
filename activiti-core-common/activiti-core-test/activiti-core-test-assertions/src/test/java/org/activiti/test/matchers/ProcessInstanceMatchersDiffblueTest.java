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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.task.model.Task;
import org.activiti.test.TaskSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessInstanceMatchersDiffblueTest {
  /**
   * Method under test:
   * {@link ProcessInstanceMatchers#status(ProcessInstance.ProcessInstanceStatus)}
   */
  @Test
  void testStatus() {
    // Arrange and Act
    ProcessResultMatcher actualStatusResult = ProcessInstanceMatchers.processInstance()
        .status(ProcessInstance.ProcessInstanceStatus.CREATED);
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getStatus()).thenReturn(ProcessInstance.ProcessInstanceStatus.CREATED);
    actualStatusResult.match(processInstance);

    // Assert
    verify(processInstance).getStatus();
  }

  /**
   * Method under test:
   * {@link ProcessInstanceMatchers#status(ProcessInstance.ProcessInstanceStatus)}
   */
  @Test
  void testStatus2() {
    // Arrange and Act
    ProcessResultMatcher actualStatusResult = ProcessInstanceMatchers.processInstance()
        .status(ProcessInstance.ProcessInstanceStatus.CREATED);
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getStatus()).thenThrow(new RuntimeException("foo"));

    // Assert
    assertThrows(RuntimeException.class, () -> actualStatusResult.match(processInstance));
    verify(processInstance).getStatus();
  }

  /**
   * Method under test: {@link ProcessInstanceMatchers#name(String)}
   */
  @Test
  void testName() {
    // Arrange and Act
    ProcessResultMatcher actualNameResult = ProcessInstanceMatchers.processInstance().name("Name");
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getName()).thenReturn("Name");
    actualNameResult.match(processInstance);

    // Assert
    verify(processInstance).getName();
  }

  /**
   * Method under test: {@link ProcessInstanceMatchers#name(String)}
   */
  @Test
  void testName2() {
    // Arrange and Act
    ProcessResultMatcher actualNameResult = ProcessInstanceMatchers.processInstance().name("Name");
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getName()).thenThrow(new RuntimeException("foo"));

    // Assert
    assertThrows(RuntimeException.class, () -> actualNameResult.match(processInstance));
    verify(processInstance).getName();
  }

  /**
   * Method under test: {@link ProcessInstanceMatchers#businessKey(String)}
   */
  @Test
  void testBusinessKey() {
    // Arrange and Act
    ProcessResultMatcher actualBusinessKeyResult = ProcessInstanceMatchers.processInstance()
        .businessKey("Business Key");
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getBusinessKey()).thenReturn("Business Key");
    actualBusinessKeyResult.match(processInstance);

    // Assert
    verify(processInstance).getBusinessKey();
  }

  /**
   * Method under test: {@link ProcessInstanceMatchers#businessKey(String)}
   */
  @Test
  void testBusinessKey2() {
    // Arrange and Act
    ProcessResultMatcher actualBusinessKeyResult = ProcessInstanceMatchers.processInstance()
        .businessKey("Business Key");
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getBusinessKey()).thenThrow(new RuntimeException("foo"));

    // Assert
    assertThrows(RuntimeException.class, () -> actualBusinessKeyResult.match(processInstance));
    verify(processInstance).getBusinessKey();
  }

  /**
   * Method under test:
   * {@link ProcessInstanceMatchers#hasTask(String, Task.TaskStatus, TaskResultMatcher[])}
   */
  @Test
  void testHasTask() {
    // Arrange and Act
    ProcessTaskMatcher actualHasTaskResult = ProcessInstanceMatchers.processInstance()
        .hasTask("Task Name", Task.TaskStatus.CREATED, mock(TaskResultMatcher.class));
    TaskSource taskSource = mock(TaskSource.class);
    when(taskSource.getTasks(Mockito.<String>any())).thenThrow(new RuntimeException("foo"));
    when(taskSource.canHandle(Mockito.<Task.TaskStatus>any())).thenReturn(true);
    ArrayList<TaskSource> taskSources = new ArrayList<>();
    taskSources.add(taskSource);

    // Assert
    assertThrows(RuntimeException.class, () -> actualHasTaskResult.match("42", taskSources));
    verify(taskSource).canHandle(eq(Task.TaskStatus.CREATED));
    verify(taskSource).getTasks(eq("42"));
  }
}
