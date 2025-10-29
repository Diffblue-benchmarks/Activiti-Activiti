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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.activiti.api.runtime.shared.NotFoundException;
import org.activiti.api.runtime.shared.security.SecurityManager;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.model.payloads.SaveTaskPayload;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TaskRuntimeHelper.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TaskRuntimeHelperDiffblueTest {
  @MockBean
  private APITaskConverter aPITaskConverter;

  @MockBean
  private SecurityManager securityManager;

  @Autowired
  private TaskRuntimeHelper taskRuntimeHelper;

  @MockBean
  private TaskService taskService;

  @MockBean
  private TaskVariablesPayloadValidator taskVariablesPayloadValidator;

  /**
   * Method under test:
   * {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}
   */
  @Test
  void testGetInternalTaskWithChecks() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new NotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeHelper.getInternalTaskWithChecks("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Method under test:
   * {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}
   */
  @Test
  void testGetInternalTaskWithChecks2() throws SecurityException {
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
   * Method under test:
   * {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}
   */
  @Test
  void testGetInternalTaskWithChecks3() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeHelper.getInternalTaskWithChecks("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Method under test: {@link TaskRuntimeHelper#assertHasAccessToTask(String)}
   */
  @Test
  void testAssertHasAccessToTask() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new NotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeHelper.assertHasAccessToTask("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Method under test: {@link TaskRuntimeHelper#assertHasAccessToTask(String)}
   */
  @Test
  void testAssertHasAccessToTask2() throws SecurityException {
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
   * Method under test: {@link TaskRuntimeHelper#assertHasAccessToTask(String)}
   */
  @Test
  void testAssertHasAccessToTask3() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeHelper.assertHasAccessToTask("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Method under test: {@link TaskRuntimeHelper#getInternalTaskVariables(String)}
   */
  @Test
  void testGetInternalTaskVariables() {
    // Arrange
    HashMap<String, VariableInstance> stringVariableInstanceMap = new HashMap<>();
    when(taskService.getVariableInstancesLocal(Mockito.<String>any())).thenReturn(stringVariableInstanceMap);

    // Act
    Map<String, VariableInstance> actualInternalTaskVariables = taskRuntimeHelper.getInternalTaskVariables("42");

    // Assert
    verify(taskService).getVariableInstancesLocal(eq("42"));
    assertTrue(actualInternalTaskVariables.isEmpty());
    assertSame(stringVariableInstanceMap, actualInternalTaskVariables);
  }

  /**
   * Method under test: {@link TaskRuntimeHelper#getInternalTaskVariables(String)}
   */
  @Test
  void testGetInternalTaskVariables2() {
    // Arrange
    when(taskService.getVariableInstancesLocal(Mockito.<String>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeHelper.getInternalTaskVariables("42"));
    verify(taskService).getVariableInstancesLocal(eq("42"));
  }

  /**
   * Method under test:
   * {@link TaskRuntimeHelper#handleCompleteTaskPayload(CompleteTaskPayload)}
   */
  @Test
  void testHandleCompleteTaskPayload() {
    // Arrange
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(taskVariablesPayloadValidator.handlePayloadVariables(Mockito.<Map<String, Object>>any()))
        .thenReturn(stringObjectMap);
    CompleteTaskPayload completeTaskPayload = new CompleteTaskPayload();

    // Act
    taskRuntimeHelper.handleCompleteTaskPayload(completeTaskPayload);

    // Assert
    verify(taskVariablesPayloadValidator).handlePayloadVariables(isNull());
    Map<String, Object> variables = completeTaskPayload.getVariables();
    assertTrue(variables.isEmpty());
    assertSame(stringObjectMap, variables);
  }

  /**
   * Method under test:
   * {@link TaskRuntimeHelper#handleCompleteTaskPayload(CompleteTaskPayload)}
   */
  @Test
  void testHandleCompleteTaskPayload2() {
    // Arrange
    when(taskVariablesPayloadValidator.handlePayloadVariables(Mockito.<Map<String, Object>>any()))
        .thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> taskRuntimeHelper.handleCompleteTaskPayload(new CompleteTaskPayload()));
    verify(taskVariablesPayloadValidator).handlePayloadVariables(isNull());
  }

  /**
   * Method under test:
   * {@link TaskRuntimeHelper#handleSaveTaskPayload(SaveTaskPayload)}
   */
  @Test
  void testHandleSaveTaskPayload() {
    // Arrange
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(taskVariablesPayloadValidator.handlePayloadVariables(Mockito.<Map<String, Object>>any()))
        .thenReturn(stringObjectMap);
    SaveTaskPayload saveTaskPayload = new SaveTaskPayload();

    // Act
    taskRuntimeHelper.handleSaveTaskPayload(saveTaskPayload);

    // Assert
    verify(taskVariablesPayloadValidator).handlePayloadVariables(isNull());
    Map<String, Object> variables = saveTaskPayload.getVariables();
    assertTrue(variables.isEmpty());
    assertSame(stringObjectMap, variables);
  }

  /**
   * Method under test:
   * {@link TaskRuntimeHelper#handleSaveTaskPayload(SaveTaskPayload)}
   */
  @Test
  void testHandleSaveTaskPayload2() {
    // Arrange
    when(taskVariablesPayloadValidator.handlePayloadVariables(Mockito.<Map<String, Object>>any()))
        .thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeHelper.handleSaveTaskPayload(new SaveTaskPayload()));
    verify(taskVariablesPayloadValidator).handlePayloadVariables(isNull());
  }
}
