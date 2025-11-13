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
package org.activiti.runtime.api.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.api.task.model.Task.TaskStatus;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.api.task.model.payloads.AssignTaskPayload;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.model.payloads.CreateTaskVariablePayload;
import org.activiti.api.task.model.payloads.DeleteTaskPayload;
import org.activiti.api.task.model.payloads.GetTaskVariablesPayload;
import org.activiti.api.task.model.payloads.GetTasksPayload;
import org.activiti.api.task.model.payloads.ReleaseTaskPayload;
import org.activiti.api.task.model.payloads.SaveTaskPayload;
import org.activiti.api.task.model.payloads.UpdateTaskPayload;
import org.activiti.api.task.model.payloads.UpdateTaskVariablePayload;
import org.activiti.api.task.runtime.conf.TaskRuntimeConfiguration;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.runtime.api.conf.impl.TaskRuntimeConfigurationImpl;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.activiti.runtime.api.model.impl.APIVariableInstanceConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaskRuntimeImpl.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class TaskRuntimeImplDiffblueTest {
  @MockBean private APITaskConverter aPITaskConverter;

  @MockBean private APIVariableInstanceConverter aPIVariableInstanceConverter;

  @MockBean private SecurityManager securityManager;

  @MockBean private TaskRuntimeConfiguration taskRuntimeConfiguration;

  @MockBean private TaskRuntimeHelper taskRuntimeHelper;

  @Autowired private TaskRuntimeImpl taskRuntimeImpl;

  @MockBean private TaskService taskService;

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TaskRuntimeImpl#TaskRuntimeImpl(TaskService, SecurityManager, APITaskConverter,
   *       APIVariableInstanceConverter, TaskRuntimeConfiguration, TaskRuntimeHelper)}
   *   <li>{@link TaskRuntimeImpl#configuration()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TaskRuntimeImpl.<init>(TaskService, SecurityManager, APITaskConverter, APIVariableInstanceConverter, TaskRuntimeConfiguration, TaskRuntimeHelper)",
    "TaskRuntimeConfiguration TaskRuntimeImpl.configuration()"
  })
  void testGettersAndSetters() {
    // Arrange
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager = mock(SecurityManager.class);
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    SecurityManager securityManager2 = mock(SecurityManager.class);
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(
            taskService2, taskConverter2, securityManager2, taskVariablesValidator);

    // Act
    TaskRuntimeImpl actualTaskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);
    TaskRuntimeConfiguration actualConfigurationResult = actualTaskRuntimeImpl.configuration();

    // Assert
    assertTrue(actualConfigurationResult instanceof TaskRuntimeConfigurationImpl);
    assertSame(configuration, actualConfigurationResult);
  }

  /**
   * Test {@link TaskRuntimeImpl#task(String)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   *   <li>Then return {@link TaskImpl}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#task(String)}
   */
  @Test
  @DisplayName(
      "Test task(String); given ArrayList() add IdentityLinkEntityImpl (default constructor); then return TaskImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.task(String)"})
  void testTask_givenArrayListAddIdentityLinkEntityImpl_thenReturnTaskImpl() {
    // Arrange
    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager = mock(SecurityManager.class);
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    // Act
    org.activiti.api.task.model.Task actualTaskResult = taskRuntimeImpl.task("42");

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("42");
    assertTrue(actualTaskResult instanceof TaskImpl);
    assertEquals("1", actualTaskResult.getAppVersion());
    assertEquals("42", actualTaskResult.getId());
    assertEquals("42", actualTaskResult.getParentTaskId());
    assertEquals("42", actualTaskResult.getProcessDefinitionId());
    assertEquals("42", actualTaskResult.getProcessInstanceId());
    assertEquals("Assignee", actualTaskResult.getAssignee());
    assertEquals("Business Key", actualTaskResult.getBusinessKey());
    assertEquals("Form Key", actualTaskResult.getFormKey());
    assertEquals("Name", actualTaskResult.getName());
    assertEquals("Owner", actualTaskResult.getOwner());
    assertEquals("Task Definition Key", actualTaskResult.getTaskDefinitionKey());
    assertEquals("The characteristics of someone or something", actualTaskResult.getDescription());
    assertEquals(1, actualTaskResult.getPriority());
    assertEquals(TaskStatus.SUSPENDED, actualTaskResult.getStatus());
    assertFalse(actualTaskResult.isStandalone());
    assertTrue(actualTaskResult.getCandidateGroups().isEmpty());
    assertTrue(actualTaskResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link TaskRuntimeImpl#task(String)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   *   <li>Then return {@link TaskImpl}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#task(String)}
   */
  @Test
  @DisplayName(
      "Test task(String); given ArrayList() add IdentityLinkEntityImpl (default constructor); then return TaskImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.task(String)"})
  void testTask_givenArrayListAddIdentityLinkEntityImpl_thenReturnTaskImpl2() {
    // Arrange
    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager = mock(SecurityManager.class);
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    // Act
    org.activiti.api.task.model.Task actualTaskResult = taskRuntimeImpl.task("42");

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("42");
    assertTrue(actualTaskResult instanceof TaskImpl);
    assertEquals("1", actualTaskResult.getAppVersion());
    assertEquals("42", actualTaskResult.getId());
    assertEquals("42", actualTaskResult.getParentTaskId());
    assertEquals("42", actualTaskResult.getProcessDefinitionId());
    assertEquals("42", actualTaskResult.getProcessInstanceId());
    assertEquals("Assignee", actualTaskResult.getAssignee());
    assertEquals("Business Key", actualTaskResult.getBusinessKey());
    assertEquals("Form Key", actualTaskResult.getFormKey());
    assertEquals("Name", actualTaskResult.getName());
    assertEquals("Owner", actualTaskResult.getOwner());
    assertEquals("Task Definition Key", actualTaskResult.getTaskDefinitionKey());
    assertEquals("The characteristics of someone or something", actualTaskResult.getDescription());
    assertEquals(1, actualTaskResult.getPriority());
    assertEquals(TaskStatus.SUSPENDED, actualTaskResult.getStatus());
    assertFalse(actualTaskResult.isStandalone());
    assertTrue(actualTaskResult.getCandidateGroups().isEmpty());
    assertTrue(actualTaskResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link TaskRuntimeImpl#task(String)}.
   *
   * <ul>
   *   <li>Then return {@link TaskImpl}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#task(String)}
   */
  @Test
  @DisplayName("Test task(String); then return TaskImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.task(String)"})
  void testTask_thenReturnTaskImpl() {
    // Arrange
    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager = mock(SecurityManager.class);
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    // Act
    org.activiti.api.task.model.Task actualTaskResult = taskRuntimeImpl.task("42");

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("42");
    assertTrue(actualTaskResult instanceof TaskImpl);
    assertEquals("1", actualTaskResult.getAppVersion());
    assertEquals("42", actualTaskResult.getId());
    assertEquals("42", actualTaskResult.getParentTaskId());
    assertEquals("42", actualTaskResult.getProcessDefinitionId());
    assertEquals("42", actualTaskResult.getProcessInstanceId());
    assertEquals("Assignee", actualTaskResult.getAssignee());
    assertEquals("Business Key", actualTaskResult.getBusinessKey());
    assertEquals("Form Key", actualTaskResult.getFormKey());
    assertEquals("Name", actualTaskResult.getName());
    assertEquals("Owner", actualTaskResult.getOwner());
    assertEquals("Task Definition Key", actualTaskResult.getTaskDefinitionKey());
    assertEquals("The characteristics of someone or something", actualTaskResult.getDescription());
    assertEquals(1, actualTaskResult.getPriority());
    assertEquals(TaskStatus.SUSPENDED, actualTaskResult.getStatus());
    assertFalse(actualTaskResult.isStandalone());
    assertTrue(actualTaskResult.getCandidateGroups().isEmpty());
    assertTrue(actualTaskResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link TaskRuntimeImpl#task(String)}.
   *
   * <ul>
   *   <li>Then return {@link TaskImpl#TaskImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#task(String)}
   */
  @Test
  @DisplayName("Test task(String); then return TaskImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.task(String)"})
  void testTask_thenReturnTaskImpl2() {
    // Arrange
    APITaskConverter taskConverter = mock(APITaskConverter.class);
    TaskImpl taskImpl = new TaskImpl();
    when(taskConverter.fromWithCandidates(Mockito.<Task>any())).thenReturn(taskImpl);

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any()))
        .thenReturn(mock(Task.class));
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager = mock(SecurityManager.class);
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    // Act
    org.activiti.api.task.model.Task actualTaskResult = taskRuntimeImpl.task("42");

    // Assert
    verify(taskRuntimeHelper).getInternalTaskWithChecks("42");
    verify(taskConverter).fromWithCandidates(isA(Task.class));
    assertSame(taskImpl, actualTaskResult);
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName("Test tasks(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable)"})
  void testTasksWithPageable() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName("Test tasks(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable)"})
  void testTasksWithPageable2() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserGroups()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName("Test tasks(Pageable) with 'pageable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable)"})
  void testTasksWithPageable3() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload() {
    // Arrange
    when(taskService.createTaskQuery()).thenThrow(new IllegalStateException());
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload2() {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(new TaskQueryImpl());
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload3() throws SecurityException {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(new TaskQueryImpl());
    when(securityManager.getAuthenticatedUserGroups()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload4() {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(mock(TaskQueryImpl.class));
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.tasks(Pageable.of(1, 3), mock(GetTasksPayload.class)));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName("Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload5() {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(mock(TaskQueryImpl.class));
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.tasks(Pageable.of(1, 3), mock(GetTasksPayload.class)));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then calls {@link GetTasksPayload#getAssigneeId()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; given '42'; then calls getAssigneeId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload_given42_thenCallsGetAssigneeId()
      throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.taskCandidateOrAssigned(Mockito.<String>any(), Mockito.<List<String>>any()))
        .thenReturn(new TaskQueryImpl());

    TaskQueryImpl taskQueryImpl2 = mock(TaskQueryImpl.class);
    when(taskQueryImpl2.or()).thenReturn(taskQueryImpl);
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl2);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetTasksPayload getTasksPayload = mock(GetTasksPayload.class);
    when(getTasksPayload.getAssigneeId()).thenReturn("42");
    when(getTasksPayload.getGroups()).thenReturn(new ArrayList<>());
    doNothing().when(getTasksPayload).setAssigneeId(Mockito.<String>any());
    doNothing().when(getTasksPayload).setGroups(Mockito.<List<String>>any());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.tasks(pageable, getTasksPayload));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(getTasksPayload).getAssigneeId();
    verify(getTasksPayload).getGroups();
    verify(getTasksPayload).setAssigneeId("42");
    verify(getTasksPayload).setGroups(isA(List.class));
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl2).or();
    verify(taskQueryImpl).taskCandidateOrAssigned(eq("42"), isA(List.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <ul>
   *   <li>Given {@link IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; given IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload_givenIllegalStateException() throws SecurityException {
    // Arrange
    when(taskService.createTaskQuery()).thenReturn(mock(TaskQueryImpl.class));
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    GetTasksPayload getTasksPayload = mock(GetTasksPayload.class);
    doThrow(new IllegalStateException()).when(getTasksPayload).setAssigneeId(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.tasks(pageable, getTasksPayload));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(getTasksPayload).setAssigneeId("42");
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <ul>
   *   <li>Given {@link TaskQueryImpl} {@link TaskQueryImpl#or()} return {@link
   *       TaskQueryImpl#TaskQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; given TaskQueryImpl or() return TaskQueryImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload_givenTaskQueryImplOrReturnTaskQueryImpl()
      throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    Pageable pageable = Pageable.of(1, 3);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> taskRuntimeImpl.tasks(pageable, new GetTasksPayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl).or();
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)} with {@code pageable}, {@code
   * getTasksPayload}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then calls {@link TaskQueryImpl#taskCandidateOrAssigned(String, List)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable, GetTasksPayload)}
   */
  @Test
  @DisplayName(
      "Test tasks(Pageable, GetTasksPayload) with 'pageable', 'getTasksPayload'; when 'null'; then calls taskCandidateOrAssigned(String, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable, GetTasksPayload)"
  })
  void testTasksWithPageableGetTasksPayload_whenNull_thenCallsTaskCandidateOrAssigned()
      throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.taskCandidateOrAssigned(Mockito.<String>any(), Mockito.<List<String>>any()))
        .thenReturn(new TaskQueryImpl());

    TaskQueryImpl taskQueryImpl2 = mock(TaskQueryImpl.class);
    when(taskQueryImpl2.or()).thenReturn(taskQueryImpl);
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl2);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3), null));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl2).or();
    verify(taskQueryImpl).taskCandidateOrAssigned(eq("42"), isA(List.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#tasks(Pageable)} with {@code pageable}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#tasks(Pageable)}
   */
  @Test
  @DisplayName("Test tasks(Pageable) with 'pageable'; then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.runtime.shared.query.Page TaskRuntimeImpl.tasks(Pageable)"})
  void testTasksWithPageable_thenThrowActivitiException() throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.tasks(Pageable.of(1, 3)));
    verify(securityManager, atLeast(1)).getAuthenticatedUserGroups();
    verify(securityManager, atLeast(1)).getAuthenticatedUserId();
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl).or();
  }

  /**
   * Test {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}
   */
  @Test
  @DisplayName("Test complete(CompleteTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.complete(CompleteTaskPayload)"
  })
  void testComplete() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    SecurityManager securityManager2 = mock(SecurityManager.class);
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(
            taskService2, taskConverter2, securityManager2, taskVariablesValidator);

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.complete(new CompleteTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}
   */
  @Test
  @DisplayName("Test complete(CompleteTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.complete(CompleteTaskPayload)"
  })
  void testComplete2() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    SecurityManager securityManager2 = mock(SecurityManager.class);
    when(securityManager2.getAuthenticatedUserId()).thenThrow(new IllegalStateException());
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService, taskConverter, securityManager2, taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter2,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.complete(new CompleteTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager2).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}
   */
  @Test
  @DisplayName("Test complete(CompleteTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.complete(CompleteTaskPayload)"
  })
  void testComplete3() throws SecurityException {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    SecurityManager securityManager2 = mock(SecurityManager.class);
    when(securityManager2.getAuthenticatedUserRoles()).thenThrow(new IllegalStateException());
    when(securityManager2.getAuthenticatedUserId()).thenReturn("42");
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService, taskConverter, securityManager2, taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter2,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.complete(new CompleteTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager2).getAuthenticatedUserId();
    verify(securityManager2).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}
   */
  @Test
  @DisplayName("Test complete(CompleteTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.complete(CompleteTaskPayload)"
  })
  void testComplete4() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    TaskImpl taskImpl =
        new TaskImpl(
            "42", "The task needs to be claimed before trying to complete it", TaskStatus.CREATED);
    taskImpl.setAssignee("");

    APITaskConverter taskConverter = mock(APITaskConverter.class);
    when(taskConverter.fromWithCandidates(Mockito.<Task>any())).thenReturn(taskImpl);

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any()))
        .thenReturn(mock(Task.class));
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    CompleteTaskPayload completeTaskPayload = new CompleteTaskPayload();
    completeTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.complete(completeTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
    verify(taskConverter).fromWithCandidates(isA(Task.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link APITaskConverter} {@link APITaskConverter#fromWithCandidates(Task)} return
   *       {@link TaskImpl#TaskImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test complete(CompleteTaskPayload); given APITaskConverter fromWithCandidates(Task) return TaskImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.complete(CompleteTaskPayload)"
  })
  void testComplete_givenAPITaskConverterFromWithCandidatesReturnTaskImpl() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    APITaskConverter taskConverter = mock(APITaskConverter.class);
    when(taskConverter.fromWithCandidates(Mockito.<Task>any())).thenReturn(new TaskImpl());

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any()))
        .thenReturn(mock(Task.class));
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    CompleteTaskPayload completeTaskPayload = new CompleteTaskPayload();
    completeTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.complete(completeTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
    verify(taskConverter).fromWithCandidates(isA(Task.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test complete(CompleteTaskPayload); given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.complete(CompleteTaskPayload)"
  })
  void testComplete_givenArrayListAddIdentityLinkEntityImpl() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    CompleteTaskPayload completeTaskPayload = new CompleteTaskPayload();
    completeTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.complete(completeTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test complete(CompleteTaskPayload); given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.complete(CompleteTaskPayload)"
  })
  void testComplete_givenArrayListAddIdentityLinkEntityImpl2() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    CompleteTaskPayload completeTaskPayload = new CompleteTaskPayload();
    completeTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.complete(completeTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskQueryImpl} {@link TaskQueryImpl#or()} return {@link
   *       TaskQueryImpl#TaskQueryImpl()}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test complete(CompleteTaskPayload); given TaskQueryImpl or() return TaskQueryImpl(); then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.complete(CompleteTaskPayload)"
  })
  void testComplete_givenTaskQueryImplOrReturnTaskQueryImpl_thenThrowActivitiException()
      throws SecurityException {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());

    TaskServiceImpl taskService = mock(TaskServiceImpl.class);
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);

    SecurityManager securityManager2 = mock(SecurityManager.class);
    when(securityManager2.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager2.getAuthenticatedUserRoles()).thenReturn(new ArrayList<>());
    when(securityManager2.getAuthenticatedUserId()).thenReturn("42");
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService, taskConverter, securityManager2, taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter2,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    CompleteTaskPayload completeTaskPayload = new CompleteTaskPayload();
    completeTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.complete(completeTaskPayload));
    verify(securityManager2).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager2).getAuthenticatedUserId();
    verify(securityManager2).getAuthenticatedUserRoles();
    verify(taskQueryImpl).or();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link TaskService#getIdentityLinksForTask(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#complete(CompleteTaskPayload)}
   */
  @Test
  @DisplayName("Test complete(CompleteTaskPayload); then calls getIdentityLinksForTask(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.complete(CompleteTaskPayload)"
  })
  void testComplete_thenCallsGetIdentityLinksForTask() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    CompleteTaskPayload completeTaskPayload = new CompleteTaskPayload();
    completeTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.complete(completeTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}
   */
  @Test
  @DisplayName("Test claim(ClaimTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.claim(ClaimTaskPayload)"})
  void testClaim() {
    // Arrange
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager = mock(SecurityManager.class);
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService2, taskConverter2, null, taskVariablesValidator);

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.claim(new ClaimTaskPayload()));
  }

  /**
   * Test {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test claim(ClaimTaskPayload); given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.claim(ClaimTaskPayload)"})
  void testClaim_givenArrayListAddIdentityLinkEntityImpl() {
    // Arrange
    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager = mock(SecurityManager.class);
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    ClaimTaskPayload claimTaskPayload = new ClaimTaskPayload();
    claimTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.claim(claimTaskPayload));
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test claim(ClaimTaskPayload); given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.claim(ClaimTaskPayload)"})
  void testClaim_givenArrayListAddIdentityLinkEntityImpl2() {
    // Arrange
    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager = mock(SecurityManager.class);
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    ClaimTaskPayload claimTaskPayload = new ClaimTaskPayload();
    claimTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.claim(claimTaskPayload));
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} throw
   *       {@link IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test claim(ClaimTaskPayload); given SecurityManager getAuthenticatedUserId() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.claim(ClaimTaskPayload)"})
  void testClaim_givenSecurityManagerGetAuthenticatedUserIdThrowIllegalStateException() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService, taskConverter, securityManager, taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager2 = mock(SecurityManager.class);
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager2,
            taskConverter2,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.claim(new ClaimTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskQueryImpl} {@link TaskQueryImpl#or()} return {@link
   *       TaskQueryImpl#TaskQueryImpl()}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test claim(ClaimTaskPayload); given TaskQueryImpl or() return TaskQueryImpl(); then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.claim(ClaimTaskPayload)"})
  void testClaim_givenTaskQueryImplOrReturnTaskQueryImpl_thenThrowActivitiException()
      throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());

    TaskServiceImpl taskService = mock(TaskServiceImpl.class);
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);

    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserRoles()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService, taskConverter, securityManager, taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager2 = mock(SecurityManager.class);
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager2,
            taskConverter2,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    ClaimTaskPayload claimTaskPayload = new ClaimTaskPayload();
    claimTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.claim(claimTaskPayload));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
    verify(taskQueryImpl).or();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link TaskService#getIdentityLinksForTask(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}
   */
  @Test
  @DisplayName("Test claim(ClaimTaskPayload); then calls getIdentityLinksForTask(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.claim(ClaimTaskPayload)"})
  void testClaim_thenCallsGetIdentityLinksForTask() {
    // Arrange
    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager = mock(SecurityManager.class);
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    ClaimTaskPayload claimTaskPayload = new ClaimTaskPayload();
    claimTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.claim(claimTaskPayload));
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}.
   *
   * <ul>
   *   <li>Then return {@link TaskImpl#TaskImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}
   */
  @Test
  @DisplayName("Test claim(ClaimTaskPayload); then return TaskImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.claim(ClaimTaskPayload)"})
  void testClaim_thenReturnTaskImpl() {
    // Arrange
    TaskServiceImpl taskService = mock(TaskServiceImpl.class);
    doNothing().when(taskService).claim(Mockito.<String>any(), Mockito.<String>any());

    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    APITaskConverter taskConverter = mock(APITaskConverter.class);
    TaskImpl taskImpl = new TaskImpl();
    when(taskConverter.fromWithCandidates(Mockito.<Task>any())).thenReturn(taskImpl);

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any()))
        .thenReturn(mock(Task.class));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    ClaimTaskPayload claimTaskPayload = new ClaimTaskPayload();
    claimTaskPayload.setTaskId("Task Id");

    // Act
    org.activiti.api.task.model.Task actualClaimResult = taskRuntimeImpl.claim(claimTaskPayload);

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).claim("Task Id", "42");
    verify(taskRuntimeHelper, atLeast(1)).getInternalTaskWithChecks("Task Id");
    verify(taskConverter, atLeast(1)).fromWithCandidates(isA(Task.class));
    assertSame(taskImpl, actualClaimResult);
  }

  /**
   * Test {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}.
   *
   * <ul>
   *   <li>Then return {@link TaskImpl#TaskImpl(String, String, TaskStatus)} with id is {@code 42}
   *       and {@code Name} and status is {@code CREATED}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#claim(ClaimTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test claim(ClaimTaskPayload); then return TaskImpl(String, String, TaskStatus) with id is '42' and 'Name' and status is 'CREATED'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.claim(ClaimTaskPayload)"})
  void testClaim_thenReturnTaskImplWithIdIs42AndNameAndStatusIsCreated() {
    // Arrange
    TaskServiceImpl taskService = mock(TaskServiceImpl.class);
    doNothing().when(taskService).claim(Mockito.<String>any(), Mockito.<String>any());

    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    TaskImpl taskImpl = new TaskImpl("42", "Name", TaskStatus.CREATED);
    taskImpl.setAssignee("");

    APITaskConverter taskConverter = mock(APITaskConverter.class);
    when(taskConverter.fromWithCandidates(Mockito.<Task>any())).thenReturn(taskImpl);

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any()))
        .thenReturn(mock(Task.class));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    ClaimTaskPayload claimTaskPayload = new ClaimTaskPayload();
    claimTaskPayload.setTaskId("Task Id");

    // Act
    org.activiti.api.task.model.Task actualClaimResult = taskRuntimeImpl.claim(claimTaskPayload);

    // Assert
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).claim("Task Id", "42");
    verify(taskRuntimeHelper, atLeast(1)).getInternalTaskWithChecks("Task Id");
    verify(taskConverter, atLeast(1)).fromWithCandidates(isA(Task.class));
    assertSame(taskImpl, actualClaimResult);
  }

  /**
   * Test {@link TaskRuntimeImpl#release(ReleaseTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link APITaskConverter} {@link APITaskConverter#fromWithCandidates(Task)} return
   *       {@link TaskImpl#TaskImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#release(ReleaseTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test release(ReleaseTaskPayload); given APITaskConverter fromWithCandidates(Task) return TaskImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.release(ReleaseTaskPayload)"
  })
  void testRelease_givenAPITaskConverterFromWithCandidatesReturnTaskImpl() {
    // Arrange
    APITaskConverter taskConverter = mock(APITaskConverter.class);
    when(taskConverter.fromWithCandidates(Mockito.<Task>any())).thenReturn(new TaskImpl());

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any()))
        .thenReturn(mock(Task.class));
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager = mock(SecurityManager.class);
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    ReleaseTaskPayload releaseTaskPayload = new ReleaseTaskPayload();
    releaseTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.release(releaseTaskPayload));
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
    verify(taskConverter).fromWithCandidates(isA(Task.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#release(ReleaseTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#release(ReleaseTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test release(ReleaseTaskPayload); given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.release(ReleaseTaskPayload)"
  })
  void testRelease_givenArrayListAddIdentityLinkEntityImpl() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    ReleaseTaskPayload releaseTaskPayload = new ReleaseTaskPayload();
    releaseTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.release(releaseTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#release(ReleaseTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#release(ReleaseTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test release(ReleaseTaskPayload); given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.release(ReleaseTaskPayload)"
  })
  void testRelease_givenArrayListAddIdentityLinkEntityImpl2() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    ReleaseTaskPayload releaseTaskPayload = new ReleaseTaskPayload();
    releaseTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.release(releaseTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#release(ReleaseTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} throw
   *       {@link IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#release(ReleaseTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test release(ReleaseTaskPayload); given SecurityManager getAuthenticatedUserId() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.release(ReleaseTaskPayload)"
  })
  void testRelease_givenSecurityManagerGetAuthenticatedUserIdThrowIllegalStateException() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    ReleaseTaskPayload releaseTaskPayload = new ReleaseTaskPayload();
    releaseTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.release(releaseTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#release(ReleaseTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskQueryImpl} {@link TaskQueryImpl#or()} return {@link
   *       TaskQueryImpl#TaskQueryImpl()}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#release(ReleaseTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test release(ReleaseTaskPayload); given TaskQueryImpl or() return TaskQueryImpl(); then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.release(ReleaseTaskPayload)"
  })
  void testRelease_givenTaskQueryImplOrReturnTaskQueryImpl_thenThrowActivitiException()
      throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());

    TaskServiceImpl taskService = mock(TaskServiceImpl.class);
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);

    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserRoles()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService, taskConverter, securityManager, taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager2 = mock(SecurityManager.class);
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager2,
            taskConverter2,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    ReleaseTaskPayload releaseTaskPayload = new ReleaseTaskPayload();
    releaseTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.release(releaseTaskPayload));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
    verify(taskQueryImpl).or();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#release(ReleaseTaskPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link TaskService#getIdentityLinksForTask(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#release(ReleaseTaskPayload)}
   */
  @Test
  @DisplayName("Test release(ReleaseTaskPayload); then calls getIdentityLinksForTask(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeImpl.release(ReleaseTaskPayload)"
  })
  void testRelease_thenCallsGetIdentityLinksForTask() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    ReleaseTaskPayload releaseTaskPayload = new ReleaseTaskPayload();
    releaseTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.release(releaseTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#update(UpdateTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskQueryImpl} {@link TaskQueryImpl#or()} return {@link
   *       TaskQueryImpl#TaskQueryImpl()}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test update(UpdateTaskPayload); given TaskQueryImpl or() return TaskQueryImpl(); then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.update(UpdateTaskPayload)"})
  void testUpdate_givenTaskQueryImplOrReturnTaskQueryImpl_thenThrowActivitiException()
      throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());

    TaskServiceImpl taskService = mock(TaskServiceImpl.class);
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);

    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserRoles()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService, taskConverter, securityManager, taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager2 = mock(SecurityManager.class);
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager2,
            taskConverter2,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    UpdateTaskPayload updateTaskPayload = new UpdateTaskPayload();
    updateTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.update(updateTaskPayload));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
    verify(taskQueryImpl).or();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#update(UpdateTaskPayload)}.
   *
   * <ul>
   *   <li>Then return {@link TaskImpl#TaskImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateTaskPayload); then return TaskImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.update(UpdateTaskPayload)"})
  void testUpdate_thenReturnTaskImpl() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl();
    when(taskRuntimeHelper.applyUpdateTaskPayload(anyBoolean(), Mockito.<UpdateTaskPayload>any()))
        .thenReturn(taskImpl);

    // Act
    org.activiti.api.task.model.Task actualUpdateResult =
        taskRuntimeImpl.update(new UpdateTaskPayload());

    // Assert
    verify(taskRuntimeHelper).applyUpdateTaskPayload(eq(false), isA(UpdateTaskPayload.class));
    assertSame(taskImpl, actualUpdateResult);
  }

  /**
   * Test {@link TaskRuntimeImpl#update(UpdateTaskPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#update(UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test update(UpdateTaskPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.update(UpdateTaskPayload)"})
  void testUpdate_thenThrowIllegalStateException() {
    // Arrange
    when(taskRuntimeHelper.applyUpdateTaskPayload(anyBoolean(), Mockito.<UpdateTaskPayload>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.update(new UpdateTaskPayload()));
    verify(taskRuntimeHelper).applyUpdateTaskPayload(eq(false), isA(UpdateTaskPayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.delete(DeleteTaskPayload)"})
  void testDelete() {
    // Arrange
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager = mock(SecurityManager.class);
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService2, taskConverter2, null, taskVariablesValidator);

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.delete(new DeleteTaskPayload("42", "Just cause")));
  }

  /**
   * Test {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.delete(DeleteTaskPayload)"})
  void testDelete2() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    TaskImpl taskImpl =
        new TaskImpl(
            "42",
            "You cannot delete a task where you are not the assignee/owner",
            TaskStatus.CREATED);
    taskImpl.setAssignee("");

    APITaskConverter taskConverter = mock(APITaskConverter.class);
    when(taskConverter.fromWithCandidates(Mockito.<Task>any())).thenReturn(taskImpl);

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any()))
        .thenReturn(mock(Task.class));
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    DeleteTaskPayload deleteTaskPayload = new DeleteTaskPayload();
    deleteTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.delete(deleteTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
    verify(taskConverter).fromWithCandidates(isA(Task.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.delete(DeleteTaskPayload)"})
  void testDelete3() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    TaskImpl taskImpl =
        new TaskImpl(
            "42",
            "You cannot delete a task where you are not the assignee/owner",
            TaskStatus.CREATED);
    taskImpl.setOwner("");

    APITaskConverter taskConverter = mock(APITaskConverter.class);
    when(taskConverter.fromWithCandidates(Mockito.<Task>any())).thenReturn(taskImpl);

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any()))
        .thenReturn(mock(Task.class));
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    DeleteTaskPayload deleteTaskPayload = new DeleteTaskPayload();
    deleteTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.delete(deleteTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
    verify(taskConverter).fromWithCandidates(isA(Task.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link APITaskConverter} {@link APITaskConverter#fromWithCandidates(Task)} return
   *       {@link TaskImpl#TaskImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test delete(DeleteTaskPayload); given APITaskConverter fromWithCandidates(Task) return TaskImpl()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.delete(DeleteTaskPayload)"})
  void testDelete_givenAPITaskConverterFromWithCandidatesReturnTaskImpl() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    APITaskConverter taskConverter = mock(APITaskConverter.class);
    when(taskConverter.fromWithCandidates(Mockito.<Task>any())).thenReturn(new TaskImpl());

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any()))
        .thenReturn(mock(Task.class));
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    DeleteTaskPayload deleteTaskPayload = new DeleteTaskPayload();
    deleteTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.delete(deleteTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
    verify(taskConverter).fromWithCandidates(isA(Task.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test delete(DeleteTaskPayload); given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.delete(DeleteTaskPayload)"})
  void testDelete_givenArrayListAddIdentityLinkEntityImpl() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    DeleteTaskPayload deleteTaskPayload = new DeleteTaskPayload();
    deleteTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.delete(deleteTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test delete(DeleteTaskPayload); given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.delete(DeleteTaskPayload)"})
  void testDelete_givenArrayListAddIdentityLinkEntityImpl2() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    DeleteTaskPayload deleteTaskPayload = new DeleteTaskPayload();
    deleteTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.delete(deleteTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} throw
   *       {@link IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test delete(DeleteTaskPayload); given SecurityManager getAuthenticatedUserId() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.delete(DeleteTaskPayload)"})
  void testDelete_givenSecurityManagerGetAuthenticatedUserIdThrowIllegalStateException() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());
    TaskServiceImpl taskService = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService, taskConverter, securityManager, taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager2 = mock(SecurityManager.class);
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager2,
            taskConverter2,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.delete(new DeleteTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} throw
   *       {@link IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test delete(DeleteTaskPayload); given SecurityManager getAuthenticatedUserId() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.delete(DeleteTaskPayload)"})
  void testDelete_givenSecurityManagerGetAuthenticatedUserIdThrowIllegalStateException2() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    DeleteTaskPayload deleteTaskPayload = new DeleteTaskPayload();
    deleteTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.delete(deleteTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskQueryImpl} {@link TaskQueryImpl#or()} return {@link
   *       TaskQueryImpl#TaskQueryImpl()}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test delete(DeleteTaskPayload); given TaskQueryImpl or() return TaskQueryImpl(); then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.delete(DeleteTaskPayload)"})
  void testDelete_givenTaskQueryImplOrReturnTaskQueryImpl_thenThrowActivitiException()
      throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());

    TaskServiceImpl taskService = mock(TaskServiceImpl.class);
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);

    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserRoles()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService, taskConverter, securityManager, taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager2 = mock(SecurityManager.class);
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager2,
            taskConverter2,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    DeleteTaskPayload deleteTaskPayload = new DeleteTaskPayload();
    deleteTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.delete(deleteTaskPayload));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
    verify(taskQueryImpl).or();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link TaskService#getIdentityLinksForTask(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#delete(DeleteTaskPayload)}
   */
  @Test
  @DisplayName("Test delete(DeleteTaskPayload); then calls getIdentityLinksForTask(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.delete(DeleteTaskPayload)"})
  void testDelete_thenCallsGetIdentityLinksForTask() {
    // Arrange
    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter taskConverter = new APITaskConverter(taskService);

    Task task = mock(Task.class);
    when(task.isSuspended()).thenReturn(true);
    when(task.getPriority()).thenReturn(1);
    when(task.getAppVersion()).thenReturn(1);
    when(task.getAssignee()).thenReturn("Assignee");
    when(task.getBusinessKey()).thenReturn("Business Key");
    when(task.getDescription()).thenReturn("The characteristics of someone or something");
    when(task.getFormKey()).thenReturn("Form Key");
    when(task.getId()).thenReturn("42");
    when(task.getName()).thenReturn("Name");
    when(task.getOwner()).thenReturn("Owner");
    when(task.getParentTaskId()).thenReturn("42");
    when(task.getProcessDefinitionId()).thenReturn("42");
    when(task.getProcessInstanceId()).thenReturn("42");
    when(task.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(task.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(task.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    TaskRuntimeHelper taskRuntimeHelper = mock(TaskRuntimeHelper.class);
    when(taskRuntimeHelper.getInternalTaskWithChecks(Mockito.<String>any())).thenReturn(task);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager,
            taskConverter,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    DeleteTaskPayload deleteTaskPayload = new DeleteTaskPayload();
    deleteTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.delete(deleteTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(taskService).getIdentityLinksForTask("42");
    verify(task).getAppVersion();
    verify(task).isSuspended();
    verify(task).getAssignee();
    verify(task).getBusinessKey();
    verify(task).getClaimTime();
    verify(task).getCreateTime();
    verify(task).getDescription();
    verify(task).getDueDate();
    verify(task).getFormKey();
    verify(task, atLeast(1)).getId();
    verify(task).getName();
    verify(task).getOwner();
    verify(task).getParentTaskId();
    verify(task).getPriority();
    verify(task).getProcessDefinitionId();
    verify(task).getProcessInstanceId();
    verify(task).getTaskDefinitionKey();
    verify(taskRuntimeHelper).getInternalTaskWithChecks("Task Id");
  }

  /**
   * Test {@link TaskRuntimeImpl#userCandidates(String)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.userCandidates(String)"})
  void testUserCandidates() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#userCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       empty string.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test userCandidates(String); given SecurityManager getAuthenticatedUserId() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_givenSecurityManagerGetAuthenticatedUserIdReturnEmptyString() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#userCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test userCandidates(String); given SecurityManager getAuthenticatedUserId() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_givenSecurityManagerGetAuthenticatedUserIdReturnNull() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#userCandidates(String)}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserRoles()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#userCandidates(String)}
   */
  @Test
  @DisplayName("Test userCandidates(String); then calls getAuthenticatedUserRoles()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.userCandidates(String)"})
  void testUserCandidates_thenCallsGetAuthenticatedUserRoles() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.userCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeImpl#groupCandidates(String)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#groupCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       empty string.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test groupCandidates(String); given SecurityManager getAuthenticatedUserId() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_givenSecurityManagerGetAuthenticatedUserIdReturnEmptyString() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#groupCandidates(String)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName(
      "Test groupCandidates(String); given SecurityManager getAuthenticatedUserId() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_givenSecurityManagerGetAuthenticatedUserIdReturnNull() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#groupCandidates(String)}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserRoles()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#groupCandidates(String)}
   */
  @Test
  @DisplayName("Test groupCandidates(String); then calls getAuthenticatedUserRoles()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.groupCandidates(String)"})
  void testGroupCandidates_thenCallsGetAuthenticatedUserRoles() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.groupCandidates("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}.
   *
   * <ul>
   *   <li>Given {@link APIVariableInstanceConverter} {@link
   *       APIVariableInstanceConverter#from(Collection)} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName(
      "Test variables(GetTaskVariablesPayload); given APIVariableInstanceConverter from(Collection) throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_givenAPIVariableInstanceConverterFromThrowIllegalStateException() {
    // Arrange
    when(aPIVariableInstanceConverter.from(Mockito.<Collection<VariableInstance>>any()))
        .thenThrow(new IllegalStateException());
    when(taskRuntimeHelper.getInternalTaskVariables(Mockito.<String>any()))
        .thenReturn(new HashMap<>());
    doNothing().when(taskRuntimeHelper).assertHasAccessToTask(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.variables(new GetTaskVariablesPayload()));
    verify(taskRuntimeHelper).assertHasAccessToTask(null);
    verify(taskRuntimeHelper).getInternalTaskVariables(null);
    verify(aPIVariableInstanceConverter).from(isA(Collection.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}.
   *
   * <ul>
   *   <li>Given {@link APIVariableInstanceConverter}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName(
      "Test variables(GetTaskVariablesPayload); given APIVariableInstanceConverter; then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_givenAPIVariableInstanceConverter_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskRuntimeHelper)
        .assertHasAccessToTask(Mockito.<String>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.variables(new GetTaskVariablesPayload()));
    verify(taskRuntimeHelper).assertHasAccessToTask(null);
  }

  /**
   * Test {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskQueryImpl} {@link TaskQueryImpl#or()} return {@link
   *       TaskQueryImpl#TaskQueryImpl()}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName(
      "Test variables(GetTaskVariablesPayload); given TaskQueryImpl or() return TaskQueryImpl(); then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_givenTaskQueryImplOrReturnTaskQueryImpl_thenThrowActivitiException()
      throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());

    TaskServiceImpl taskService = mock(TaskServiceImpl.class);
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);

    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserRoles()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService, taskConverter, securityManager, taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager2 = mock(SecurityManager.class);
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager2,
            taskConverter2,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    GetTaskVariablesPayload getTaskVariablesPayload = new GetTaskVariablesPayload();
    getTaskVariablesPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.variables(getTaskVariablesPayload));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
    verify(taskQueryImpl).or();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#variables(GetTaskVariablesPayload)}
   */
  @Test
  @DisplayName("Test variables(GetTaskVariablesPayload); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List TaskRuntimeImpl.variables(GetTaskVariablesPayload)"})
  void testVariables_thenReturnEmpty() {
    // Arrange
    when(aPIVariableInstanceConverter.from(Mockito.<Collection<VariableInstance>>any()))
        .thenReturn(new ArrayList<>());
    when(taskRuntimeHelper.getInternalTaskVariables(Mockito.<String>any()))
        .thenReturn(new HashMap<>());
    doNothing().when(taskRuntimeHelper).assertHasAccessToTask(Mockito.<String>any());

    // Act
    List<org.activiti.api.model.shared.model.VariableInstance> actualVariablesResult =
        taskRuntimeImpl.variables(new GetTaskVariablesPayload());

    // Assert
    verify(taskRuntimeHelper).assertHasAccessToTask(null);
    verify(taskRuntimeHelper).getInternalTaskVariables(null);
    verify(aPIVariableInstanceConverter).from(isA(Collection.class));
    assertTrue(actualVariablesResult.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskRuntimeHelper} {@link TaskRuntimeHelper#createVariable(boolean,
   *       CreateTaskVariablePayload)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test createVariable(CreateTaskVariablePayload); given TaskRuntimeHelper createVariable(boolean, CreateTaskVariablePayload) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.createVariable(CreateTaskVariablePayload)"})
  void testCreateVariable_givenTaskRuntimeHelperCreateVariableDoesNothing() {
    // Arrange
    doNothing()
        .when(taskRuntimeHelper)
        .createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act
    taskRuntimeImpl.createVariable(new CreateTaskVariablePayload());

    // Assert
    verify(taskRuntimeHelper).createVariable(eq(false), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#createVariable(CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test createVariable(CreateTaskVariablePayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.createVariable(CreateTaskVariablePayload)"})
  void testCreateVariable_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskRuntimeHelper)
        .createVariable(anyBoolean(), Mockito.<CreateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.createVariable(new CreateTaskVariablePayload()));
    verify(taskRuntimeHelper).createVariable(eq(false), isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskRuntimeHelper} {@link TaskRuntimeHelper#updateVariable(boolean,
   *       UpdateTaskVariablePayload)} does nothing.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test updateVariable(UpdateTaskVariablePayload); given TaskRuntimeHelper updateVariable(boolean, UpdateTaskVariablePayload) does nothing")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.updateVariable(UpdateTaskVariablePayload)"})
  void testUpdateVariable_givenTaskRuntimeHelperUpdateVariableDoesNothing() {
    // Arrange
    doNothing()
        .when(taskRuntimeHelper)
        .updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act
    taskRuntimeImpl.updateVariable(new UpdateTaskVariablePayload());

    // Assert
    verify(taskRuntimeHelper).updateVariable(eq(false), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#updateVariable(UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test updateVariable(UpdateTaskVariablePayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.updateVariable(UpdateTaskVariablePayload)"})
  void testUpdateVariable_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskRuntimeHelper)
        .updateVariable(anyBoolean(), Mockito.<UpdateTaskVariablePayload>any());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeImpl.updateVariable(new UpdateTaskVariablePayload()));
    verify(taskRuntimeHelper).updateVariable(eq(false), isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#save(SaveTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskQueryImpl} {@link TaskQueryImpl#or()} return {@link
   *       TaskQueryImpl#TaskQueryImpl()}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#save(SaveTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test save(SaveTaskPayload); given TaskQueryImpl or() return TaskQueryImpl(); then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.save(SaveTaskPayload)"})
  void testSave_givenTaskQueryImplOrReturnTaskQueryImpl_thenThrowActivitiException()
      throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());

    TaskServiceImpl taskService = mock(TaskServiceImpl.class);
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);

    SecurityManager securityManager = mock(SecurityManager.class);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserRoles()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");
    APITaskConverter taskConverter =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");
    TaskVariablesPayloadValidator taskVariablesValidator =
        new TaskVariablesPayloadValidator(dateFormatterProvider, new VariableNameValidator());

    TaskRuntimeHelper taskRuntimeHelper =
        new TaskRuntimeHelper(taskService, taskConverter, securityManager, taskVariablesValidator);
    TaskServiceImpl taskService2 = new TaskServiceImpl(new JtaProcessEngineConfiguration());
    SecurityManager securityManager2 = mock(SecurityManager.class);
    APITaskConverter taskConverter2 =
        new APITaskConverter(new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    APIVariableInstanceConverter variableInstanceConverter = new APIVariableInstanceConverter();
    ArrayList<TaskRuntimeEventListener<?>> taskRuntimeEventListeners = new ArrayList<>();
    TaskRuntimeConfigurationImpl configuration =
        new TaskRuntimeConfigurationImpl(taskRuntimeEventListeners, new ArrayList<>());

    TaskRuntimeImpl taskRuntimeImpl =
        new TaskRuntimeImpl(
            taskService2,
            securityManager2,
            taskConverter2,
            variableInstanceConverter,
            configuration,
            taskRuntimeHelper);

    SaveTaskPayload saveTaskPayload = new SaveTaskPayload();
    saveTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeImpl.save(saveTaskPayload));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
    verify(taskQueryImpl).or();
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeImpl#save(SaveTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#setVariablesLocal(String, Map)} does
   *       nothing.
   *   <li>Then calls {@link TaskService#setVariablesLocal(String, Map)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#save(SaveTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test save(SaveTaskPayload); given TaskService setVariablesLocal(String, Map) does nothing; then calls setVariablesLocal(String, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.save(SaveTaskPayload)"})
  void testSave_givenTaskServiceSetVariablesLocalDoesNothing_thenCallsSetVariablesLocal() {
    // Arrange
    doNothing()
        .when(taskService)
        .setVariablesLocal(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    doNothing().when(taskRuntimeHelper).assertHasAccessToTask(Mockito.<String>any());
    doNothing().when(taskRuntimeHelper).handleSaveTaskPayload(Mockito.<SaveTaskPayload>any());

    // Act
    taskRuntimeImpl.save(new SaveTaskPayload());

    // Assert
    verify(taskService).setVariablesLocal(isNull(), isNull());
    verify(taskRuntimeHelper).assertHasAccessToTask(null);
    verify(taskRuntimeHelper).handleSaveTaskPayload(isA(SaveTaskPayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#save(SaveTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService} {@link TaskService#setVariablesLocal(String, Map)} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#save(SaveTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test save(SaveTaskPayload); given TaskService setVariablesLocal(String, Map) throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.save(SaveTaskPayload)"})
  void testSave_givenTaskServiceSetVariablesLocalThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskService)
        .setVariablesLocal(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
    doNothing().when(taskRuntimeHelper).assertHasAccessToTask(Mockito.<String>any());
    doNothing().when(taskRuntimeHelper).handleSaveTaskPayload(Mockito.<SaveTaskPayload>any());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.save(new SaveTaskPayload()));
    verify(taskService).setVariablesLocal(isNull(), isNull());
    verify(taskRuntimeHelper).assertHasAccessToTask(null);
    verify(taskRuntimeHelper).handleSaveTaskPayload(isA(SaveTaskPayload.class));
  }

  /**
   * Test {@link TaskRuntimeImpl#save(SaveTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService}.
   *   <li>When {@link SaveTaskPayload#SaveTaskPayload()}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#save(SaveTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test save(SaveTaskPayload); given TaskService; when SaveTaskPayload(); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeImpl.save(SaveTaskPayload)"})
  void testSave_givenTaskService_whenSaveTaskPayload_thenThrowIllegalStateException() {
    // Arrange
    doThrow(new IllegalStateException())
        .when(taskRuntimeHelper)
        .assertHasAccessToTask(Mockito.<String>any());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeImpl.save(new SaveTaskPayload()));
    verify(taskRuntimeHelper).assertHasAccessToTask(null);
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName("Test assign(AssignTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.assign(AssignTaskPayload)"})
  void testAssign() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       empty string.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test assign(AssignTaskPayload); given SecurityManager getAuthenticatedUserId() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.assign(AssignTaskPayload)"})
  void testAssign_givenSecurityManagerGetAuthenticatedUserIdReturnEmptyString() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test assign(AssignTaskPayload); given SecurityManager getAuthenticatedUserId() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.assign(AssignTaskPayload)"})
  void testAssign_givenSecurityManagerGetAuthenticatedUserIdReturnNull() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn(null);

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   *
   * <ul>
   *   <li>Given {@link SecurityManager} {@link SecurityManager#getAuthenticatedUserId()} throw
   *       {@link IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test assign(AssignTaskPayload); given SecurityManager getAuthenticatedUserId() throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.assign(AssignTaskPayload)"})
  void testAssign_givenSecurityManagerGetAuthenticatedUserIdThrowIllegalStateException() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeImpl#assign(AssignTaskPayload)}.
   *
   * <ul>
   *   <li>Then calls {@link SecurityManager#getAuthenticatedUserGroups()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeImpl#assign(AssignTaskPayload)}
   */
  @Test
  @DisplayName("Test assign(AssignTaskPayload); then calls getAuthenticatedUserGroups()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task TaskRuntimeImpl.assign(AssignTaskPayload)"})
  void testAssign_thenCallsGetAuthenticatedUserGroups() throws SecurityException {
    // Arrange
    when(taskService.createTaskQuery()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserRoles()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeImpl.assign(new AssignTaskPayload()));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
    verify(taskService).createTaskQuery();
  }
}
