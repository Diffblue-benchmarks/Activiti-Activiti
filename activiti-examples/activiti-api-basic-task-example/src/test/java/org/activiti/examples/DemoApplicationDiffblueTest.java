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
package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.events.TaskRuntimeEvent;
import org.activiti.api.task.model.events.TaskRuntimeEvent.TaskEvents;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.model.payloads.CreateTaskPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.api.task.runtime.events.TaskAssignedEvent;
import org.activiti.api.task.runtime.events.TaskCompletedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.runtime.api.event.impl.TaskAssignedEventImpl;
import org.activiti.runtime.api.event.impl.TaskCompletedImpl;
import org.activiti.runtime.api.query.impl.PageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

@ExtendWith(MockitoExtension.class)
class DemoApplicationDiffblueTest {
  @InjectMocks private DemoApplication demoApplication;

  @Mock private Logger logger;

  @Mock private SecurityUtil securityUtil;

  @Mock private TaskRuntime taskRuntime;

  /**
   * Test {@link DemoApplication#run(String[])}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link TaskImpl#TaskImpl()}.
   *   <li>Then calls {@link TaskRuntime#claim(ClaimTaskPayload)}.
   * </ul>
   *
   * <p>Method under test: {@link DemoApplication#run(String[])}
   */
  @Test
  @DisplayName(
      "Test run(String[]); given ArrayList() add TaskImpl(); then calls claim(ClaimTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DemoApplication.run(String[])"})
  void testRun_givenArrayListAddTaskImpl_thenCallsClaim() {
    // Arrange
    doNothing().when(securityUtil).logInAs(Mockito.<String>any());

    ArrayList<Task> content = new ArrayList<>();
    content.add(new TaskImpl());
    PageImpl<Task> pageImpl = new PageImpl<>(content, 1000);
    when(taskRuntime.claim(Mockito.<ClaimTaskPayload>any())).thenReturn(new TaskImpl());
    when(taskRuntime.complete(Mockito.<CompleteTaskPayload>any())).thenReturn(new TaskImpl());
    when(taskRuntime.tasks(Mockito.<Pageable>any())).thenReturn(pageImpl);
    when(taskRuntime.create(Mockito.<CreateTaskPayload>any())).thenReturn(new TaskImpl());
    doNothing().when(logger).info(Mockito.<String>any());

    // Act
    demoApplication.run("Args");

    // Assert
    verify(taskRuntime).claim(isA(ClaimTaskPayload.class));
    verify(taskRuntime).complete(isA(CompleteTaskPayload.class));
    verify(taskRuntime).create(isA(CreateTaskPayload.class));
    verify(taskRuntime, atLeast(1)).tasks(Mockito.<Pageable>any());
    verify(securityUtil, atLeast(1)).logInAs(Mockito.<String>any());
    verify(logger, atLeast(1)).info(Mockito.<String>any());
  }

  /**
   * Test {@link DemoApplication#taskAssignedListener()}.
   *
   * <p>Method under test: {@link DemoApplication#taskAssignedListener()}
   */
  @Test
  @DisplayName("Test taskAssignedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskRuntimeEventListener DemoApplication.taskAssignedListener()"})
  void testTaskAssignedListener() {
    // Arrange
    doNothing().when(logger).info(Mockito.<String>any());

    // Act
    TaskRuntimeEventListener<TaskAssignedEvent> actualTaskAssignedListenerResult =
        demoApplication.taskAssignedListener();
    TaskImpl entity = new TaskImpl();
    TaskAssignedEventImpl taskAssignedEventImpl = new TaskAssignedEventImpl(entity);
    actualTaskAssignedListenerResult.onEvent(taskAssignedEventImpl);

    // Assert that nothing has changed
    verify(logger)
        .info(">>> Task Assigned: 'null' We can send a notification to the assginee: null");
    Task entity2 = taskAssignedEventImpl.getEntity();
    assertTrue(entity2 instanceof TaskImpl);
    assertEquals(TaskEvents.TASK_ASSIGNED, taskAssignedEventImpl.getEventType());
    assertSame(entity, entity2);
  }

  /**
   * Test {@link DemoApplication#taskCompletedListener()}.
   *
   * <p>Method under test: {@link DemoApplication#taskCompletedListener()}
   */
  @Test
  @DisplayName("Test taskCompletedListener()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"TaskRuntimeEventListener DemoApplication.taskCompletedListener()"})
  void testTaskCompletedListener() {
    // Arrange
    doNothing().when(logger).info(Mockito.<String>any());

    // Act
    TaskRuntimeEventListener<TaskCompletedEvent> actualTaskCompletedListenerResult =
        demoApplication.taskCompletedListener();
    TaskImpl entity = new TaskImpl();
    TaskCompletedImpl taskCompletedImpl = new TaskCompletedImpl(entity);
    actualTaskCompletedListenerResult.onEvent(taskCompletedImpl);

    // Assert that nothing has changed
    verify(logger).info(">>> Task Completed: 'null' We can send a notification to the owner: null");
    Task entity2 = taskCompletedImpl.getEntity();
    assertTrue(entity2 instanceof TaskImpl);
    assertEquals(TaskEvents.TASK_COMPLETED, taskCompletedImpl.getEventType());
    assertSame(entity, entity2);
  }
}
