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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.model.payloads.CreateTaskVariablePayload;
import org.activiti.api.task.model.payloads.SaveTaskPayload;
import org.activiti.api.task.model.payloads.UpdateTaskPayload;
import org.activiti.api.task.model.payloads.UpdateTaskVariablePayload;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.runtime.api.model.impl.APITaskConverter;
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

@ContextConfiguration(classes = {TaskRuntimeHelper.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class TaskRuntimeHelperDiffblueTest {
  @MockBean private APITaskConverter aPITaskConverter;

  @MockBean private SecurityManager securityManager;

  @Autowired private TaskRuntimeHelper taskRuntimeHelper;

  @MockBean private TaskService taskService;

  @MockBean private TaskVariablesPayloadValidator taskVariablesPayloadValidator;

  /**
   * Test {@link TaskRuntimeHelper#applyUpdateTaskPayload(boolean, UpdateTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeHelper#applyUpdateTaskPayload(boolean,
   * UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test applyUpdateTaskPayload(boolean, UpdateTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeHelper.applyUpdateTaskPayload(boolean, UpdateTaskPayload)"
  })
  void testApplyUpdateTaskPayload() {
    // Arrange
    when(taskService.createTaskQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeHelper.applyUpdateTaskPayload(true, new UpdateTaskPayload()));
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeHelper#applyUpdateTaskPayload(boolean, UpdateTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeHelper#applyUpdateTaskPayload(boolean,
   * UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test applyUpdateTaskPayload(boolean, UpdateTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeHelper.applyUpdateTaskPayload(boolean, UpdateTaskPayload)"
  })
  void testApplyUpdateTaskPayload2() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    UpdateTaskPayload updateTaskPayload = new UpdateTaskPayload();
    updateTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeHelper.applyUpdateTaskPayload(false, updateTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeHelper#applyUpdateTaskPayload(boolean, UpdateTaskPayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeHelper#applyUpdateTaskPayload(boolean,
   * UpdateTaskPayload)}
   */
  @Test
  @DisplayName("Test applyUpdateTaskPayload(boolean, UpdateTaskPayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeHelper.applyUpdateTaskPayload(boolean, UpdateTaskPayload)"
  })
  void testApplyUpdateTaskPayload3() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    UpdateTaskPayload updateTaskPayload = new UpdateTaskPayload();
    updateTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeHelper.applyUpdateTaskPayload(false, updateTaskPayload));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeHelper#applyUpdateTaskPayload(boolean, UpdateTaskPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#applyUpdateTaskPayload(boolean,
   * UpdateTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test applyUpdateTaskPayload(boolean, UpdateTaskPayload); then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.task.model.Task TaskRuntimeHelper.applyUpdateTaskPayload(boolean, UpdateTaskPayload)"
  })
  void testApplyUpdateTaskPayload_thenThrowActivitiException() throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserRoles()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    UpdateTaskPayload updateTaskPayload = new UpdateTaskPayload();
    updateTaskPayload.setTaskId("Task Id");

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> taskRuntimeHelper.applyUpdateTaskPayload(false, updateTaskPayload));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl).or();
  }

  /**
   * Test {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}.
   *
   * <p>Method under test: {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}
   */
  @Test
  @DisplayName("Test getInternalTaskWithChecks(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.task.Task TaskRuntimeHelper.getInternalTaskWithChecks(String)"
  })
  void testGetInternalTaskWithChecks() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeHelper.getInternalTaskWithChecks("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}.
   *
   * <p>Method under test: {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}
   */
  @Test
  @DisplayName("Test getInternalTaskWithChecks(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.task.Task TaskRuntimeHelper.getInternalTaskWithChecks(String)"
  })
  void testGetInternalTaskWithChecks2() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeHelper.getInternalTaskWithChecks("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}.
   *
   * <p>Method under test: {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}
   */
  @Test
  @DisplayName("Test getInternalTaskWithChecks(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.task.Task TaskRuntimeHelper.getInternalTaskWithChecks(String)"
  })
  void testGetInternalTaskWithChecks3() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeHelper.getInternalTaskWithChecks("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}
   */
  @Test
  @DisplayName("Test getInternalTaskWithChecks(String); then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.task.Task TaskRuntimeHelper.getInternalTaskWithChecks(String)"
  })
  void testGetInternalTaskWithChecks_thenThrowActivitiException() throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserRoles()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeHelper.getInternalTaskWithChecks("42"));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl).or();
  }

  /**
   * Test {@link TaskRuntimeHelper#assertHasAccessToTask(String)}.
   *
   * <p>Method under test: {@link TaskRuntimeHelper#assertHasAccessToTask(String)}
   */
  @Test
  @DisplayName("Test assertHasAccessToTask(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.assertHasAccessToTask(String)"})
  void testAssertHasAccessToTask() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeHelper.assertHasAccessToTask("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeHelper#assertHasAccessToTask(String)}.
   *
   * <p>Method under test: {@link TaskRuntimeHelper#assertHasAccessToTask(String)}
   */
  @Test
  @DisplayName("Test assertHasAccessToTask(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.assertHasAccessToTask(String)"})
  void testAssertHasAccessToTask2() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new IllegalStateException());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeHelper.assertHasAccessToTask("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeHelper#assertHasAccessToTask(String)}.
   *
   * <p>Method under test: {@link TaskRuntimeHelper#assertHasAccessToTask(String)}
   */
  @Test
  @DisplayName("Test assertHasAccessToTask(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.assertHasAccessToTask(String)"})
  void testAssertHasAccessToTask3() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeHelper.assertHasAccessToTask("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeHelper#assertHasAccessToTask(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#assertHasAccessToTask(String)}
   */
  @Test
  @DisplayName("Test assertHasAccessToTask(String); then throw ActivitiException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.assertHasAccessToTask(String)"})
  void testAssertHasAccessToTask_thenThrowActivitiException() throws SecurityException {
    // Arrange
    TaskQueryImpl taskQueryImpl = mock(TaskQueryImpl.class);
    when(taskQueryImpl.or()).thenReturn(new TaskQueryImpl());
    when(taskService.createTaskQuery()).thenReturn(taskQueryImpl);
    when(securityManager.getAuthenticatedUserGroups()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserRoles()).thenReturn(new ArrayList<>());
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskRuntimeHelper.assertHasAccessToTask("42"));
    verify(securityManager).getAuthenticatedUserGroups();
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
    verify(taskService).createTaskQuery();
    verify(taskQueryImpl).or();
  }

  /**
   * Test {@link TaskRuntimeHelper#getInternalTask(String)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#getInternalTask(String)}
   */
  @Test
  @DisplayName("Test getInternalTask(String); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.engine.task.Task TaskRuntimeHelper.getInternalTask(String)"})
  void testGetInternalTask_thenThrowIllegalStateException() {
    // Arrange
    when(taskService.createTaskQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeHelper.getInternalTask("42"));
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeHelper#getInternalTaskVariables(String)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#getInternalTaskVariables(String)}
   */
  @Test
  @DisplayName("Test getInternalTaskVariables(String); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskRuntimeHelper.getInternalTaskVariables(String)"})
  void testGetInternalTaskVariables_thenReturnEmpty() {
    // Arrange
    when(taskService.getVariableInstancesLocal(Mockito.<String>any())).thenReturn(new HashMap<>());

    // Act
    Map<String, VariableInstance> actualInternalTaskVariables =
        taskRuntimeHelper.getInternalTaskVariables("42");

    // Assert
    verify(taskService).getVariableInstancesLocal("42");
    assertTrue(actualInternalTaskVariables.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeHelper#getInternalTaskVariables(String)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#getInternalTaskVariables(String)}
   */
  @Test
  @DisplayName("Test getInternalTaskVariables(String); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map TaskRuntimeHelper.getInternalTaskVariables(String)"})
  void testGetInternalTaskVariables_thenThrowIllegalStateException() {
    // Arrange
    when(taskService.getVariableInstancesLocal(Mockito.<String>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class, () -> taskRuntimeHelper.getInternalTaskVariables("42"));
    verify(taskService).getVariableInstancesLocal("42");
  }

  /**
   * Test {@link TaskRuntimeHelper#createVariable(boolean, CreateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeHelper#createVariable(boolean,
   * CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test createVariable(boolean, CreateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.createVariable(boolean, CreateTaskVariablePayload)"})
  void testCreateVariable() {
    // Arrange
    when(taskService.getVariableInstancesLocal(Mockito.<String>any()))
        .thenThrow(new IllegalStateException());
    when(taskVariablesPayloadValidator.handleCreateTaskVariablePayload(
            Mockito.<CreateTaskVariablePayload>any()))
        .thenReturn(new CreateTaskVariablePayload());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeHelper.createVariable(true, new CreateTaskVariablePayload()));
    verify(taskService).getVariableInstancesLocal(null);
    verify(taskVariablesPayloadValidator)
        .handleCreateTaskVariablePayload(isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeHelper#createVariable(boolean, CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService}.
   *   <li>When {@code true}.
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#createVariable(boolean,
   * CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test createVariable(boolean, CreateTaskVariablePayload); given TaskService; when 'true'; then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.createVariable(boolean, CreateTaskVariablePayload)"})
  void testCreateVariable_givenTaskService_whenTrue_thenThrowIllegalStateException() {
    // Arrange
    when(taskVariablesPayloadValidator.handleCreateTaskVariablePayload(
            Mockito.<CreateTaskVariablePayload>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeHelper.createVariable(true, new CreateTaskVariablePayload()));
    verify(taskVariablesPayloadValidator)
        .handleCreateTaskVariablePayload(isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeHelper#createVariable(boolean, CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then calls {@link TaskService#createTaskQuery()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#createVariable(boolean,
   * CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test createVariable(boolean, CreateTaskVariablePayload); then calls createTaskQuery()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.createVariable(boolean, CreateTaskVariablePayload)"})
  void testCreateVariable_thenCallsCreateTaskQuery() {
    // Arrange
    when(taskService.createTaskQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeHelper.createVariable(false, new CreateTaskVariablePayload()));
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeHelper#createVariable(boolean, CreateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then calls {@link TaskService#setVariableLocal(String, String, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#createVariable(boolean,
   * CreateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test createVariable(boolean, CreateTaskVariablePayload); then calls setVariableLocal(String, String, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.createVariable(boolean, CreateTaskVariablePayload)"})
  void testCreateVariable_thenCallsSetVariableLocal() {
    // Arrange
    when(taskService.getVariableInstancesLocal(Mockito.<String>any())).thenReturn(new HashMap<>());
    doNothing()
        .when(taskService)
        .setVariableLocal(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Object>any());
    when(taskVariablesPayloadValidator.handleCreateTaskVariablePayload(
            Mockito.<CreateTaskVariablePayload>any()))
        .thenReturn(new CreateTaskVariablePayload());

    // Act
    taskRuntimeHelper.createVariable(true, new CreateTaskVariablePayload());

    // Assert
    verify(taskService).getVariableInstancesLocal(null);
    verify(taskService).setVariableLocal(isNull(), isNull(), isNull());
    verify(taskVariablesPayloadValidator)
        .handleCreateTaskVariablePayload(isA(CreateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeHelper#updateVariable(boolean, UpdateTaskVariablePayload)}.
   *
   * <p>Method under test: {@link TaskRuntimeHelper#updateVariable(boolean,
   * UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName("Test updateVariable(boolean, UpdateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.updateVariable(boolean, UpdateTaskVariablePayload)"})
  void testUpdateVariable() {
    // Arrange
    when(taskService.getVariableInstancesLocal(Mockito.<String>any()))
        .thenThrow(new IllegalStateException());
    when(taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(
            Mockito.<UpdateTaskVariablePayload>any()))
        .thenReturn(new UpdateTaskVariablePayload());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeHelper.updateVariable(true, new UpdateTaskVariablePayload()));
    verify(taskService).getVariableInstancesLocal(null);
    verify(taskVariablesPayloadValidator)
        .handleUpdateTaskVariablePayload(isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeHelper#updateVariable(boolean, UpdateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Given {@link TaskService}.
   *   <li>Then calls {@link
   *       TaskVariablesPayloadValidator#handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#updateVariable(boolean,
   * UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test updateVariable(boolean, UpdateTaskVariablePayload); given TaskService; then calls handleUpdateTaskVariablePayload(UpdateTaskVariablePayload)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.updateVariable(boolean, UpdateTaskVariablePayload)"})
  void testUpdateVariable_givenTaskService_thenCallsHandleUpdateTaskVariablePayload() {
    // Arrange
    when(taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(
            Mockito.<UpdateTaskVariablePayload>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeHelper.updateVariable(true, new UpdateTaskVariablePayload()));
    verify(taskVariablesPayloadValidator)
        .handleUpdateTaskVariablePayload(isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeHelper#updateVariable(boolean, UpdateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then calls {@link TaskService#createTaskQuery()}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#updateVariable(boolean,
   * UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test updateVariable(boolean, UpdateTaskVariablePayload); then calls createTaskQuery()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.updateVariable(boolean, UpdateTaskVariablePayload)"})
  void testUpdateVariable_thenCallsCreateTaskQuery() {
    // Arrange
    when(taskService.createTaskQuery()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeHelper.updateVariable(false, new UpdateTaskVariablePayload()));
    verify(taskService).createTaskQuery();
  }

  /**
   * Test {@link TaskRuntimeHelper#updateVariable(boolean, UpdateTaskVariablePayload)}.
   *
   * <ul>
   *   <li>Then calls {@link TaskService#getVariableInstancesLocal(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#updateVariable(boolean,
   * UpdateTaskVariablePayload)}
   */
  @Test
  @DisplayName(
      "Test updateVariable(boolean, UpdateTaskVariablePayload); then calls getVariableInstancesLocal(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.updateVariable(boolean, UpdateTaskVariablePayload)"})
  void testUpdateVariable_thenCallsGetVariableInstancesLocal() {
    // Arrange
    when(taskService.getVariableInstancesLocal(Mockito.<String>any())).thenReturn(new HashMap<>());
    when(taskVariablesPayloadValidator.handleUpdateTaskVariablePayload(
            Mockito.<UpdateTaskVariablePayload>any()))
        .thenReturn(new UpdateTaskVariablePayload());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeHelper.updateVariable(true, new UpdateTaskVariablePayload()));
    verify(taskService).getVariableInstancesLocal(null);
    verify(taskVariablesPayloadValidator)
        .handleUpdateTaskVariablePayload(isA(UpdateTaskVariablePayload.class));
  }

  /**
   * Test {@link TaskRuntimeHelper#handleCompleteTaskPayload(CompleteTaskPayload)}.
   *
   * <ul>
   *   <li>Then {@link CompleteTaskPayload#CompleteTaskPayload()} Variables Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#handleCompleteTaskPayload(CompleteTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test handleCompleteTaskPayload(CompleteTaskPayload); then CompleteTaskPayload() Variables Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.handleCompleteTaskPayload(CompleteTaskPayload)"})
  void testHandleCompleteTaskPayload_thenCompleteTaskPayloadVariablesEmpty() {
    // Arrange
    when(taskVariablesPayloadValidator.handlePayloadVariables(Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    CompleteTaskPayload completeTaskPayload = new CompleteTaskPayload();

    // Act
    taskRuntimeHelper.handleCompleteTaskPayload(completeTaskPayload);

    // Assert
    verify(taskVariablesPayloadValidator).handlePayloadVariables(isNull());
    assertTrue(completeTaskPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link TaskRuntimeHelper#handleCompleteTaskPayload(CompleteTaskPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#handleCompleteTaskPayload(CompleteTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test handleCompleteTaskPayload(CompleteTaskPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.handleCompleteTaskPayload(CompleteTaskPayload)"})
  void testHandleCompleteTaskPayload_thenThrowIllegalStateException() {
    // Arrange
    when(taskVariablesPayloadValidator.handlePayloadVariables(Mockito.<Map<String, Object>>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeHelper.handleCompleteTaskPayload(new CompleteTaskPayload()));
    verify(taskVariablesPayloadValidator).handlePayloadVariables(isNull());
  }

  /**
   * Test {@link TaskRuntimeHelper#handleSaveTaskPayload(SaveTaskPayload)}.
   *
   * <ul>
   *   <li>Then {@link SaveTaskPayload#SaveTaskPayload()} Variables Empty.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#handleSaveTaskPayload(SaveTaskPayload)}
   */
  @Test
  @DisplayName(
      "Test handleSaveTaskPayload(SaveTaskPayload); then SaveTaskPayload() Variables Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.handleSaveTaskPayload(SaveTaskPayload)"})
  void testHandleSaveTaskPayload_thenSaveTaskPayloadVariablesEmpty() {
    // Arrange
    when(taskVariablesPayloadValidator.handlePayloadVariables(Mockito.<Map<String, Object>>any()))
        .thenReturn(new HashMap<>());
    SaveTaskPayload saveTaskPayload = new SaveTaskPayload();

    // Act
    taskRuntimeHelper.handleSaveTaskPayload(saveTaskPayload);

    // Assert
    verify(taskVariablesPayloadValidator).handlePayloadVariables(isNull());
    assertTrue(saveTaskPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link TaskRuntimeHelper#handleSaveTaskPayload(SaveTaskPayload)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.
   * </ul>
   *
   * <p>Method under test: {@link TaskRuntimeHelper#handleSaveTaskPayload(SaveTaskPayload)}
   */
  @Test
  @DisplayName("Test handleSaveTaskPayload(SaveTaskPayload); then throw IllegalStateException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TaskRuntimeHelper.handleSaveTaskPayload(SaveTaskPayload)"})
  void testHandleSaveTaskPayload_thenThrowIllegalStateException() {
    // Arrange
    when(taskVariablesPayloadValidator.handlePayloadVariables(Mockito.<Map<String, Object>>any()))
        .thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> taskRuntimeHelper.handleSaveTaskPayload(new SaveTaskPayload()));
    verify(taskVariablesPayloadValidator).handlePayloadVariables(isNull());
  }
}
