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
import org.junit.jupiter.api.DisplayName;
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
   * Test {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}
   */
  @Test
  @DisplayName("Test getInternalTaskWithChecks(String); then throw ActivitiException")
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
   * Test {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}
   */
  @Test
  @DisplayName("Test getInternalTaskWithChecks(String); then throw IllegalStateException")
  void testGetInternalTaskWithChecks_thenThrowIllegalStateException() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeHelper.getInternalTaskWithChecks("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskRuntimeHelper#getInternalTaskWithChecks(String)}
   */
  @Test
  @DisplayName("Test getInternalTaskWithChecks(String); then throw NotFoundException")
  void testGetInternalTaskWithChecks_thenThrowNotFoundException() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new NotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeHelper.getInternalTaskWithChecks("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeHelper#assertHasAccessToTask(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeHelper#assertHasAccessToTask(String)}
   */
  @Test
  @DisplayName("Test assertHasAccessToTask(String); then throw ActivitiException")
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
   * Test {@link TaskRuntimeHelper#assertHasAccessToTask(String)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeHelper#assertHasAccessToTask(String)}
   */
  @Test
  @DisplayName("Test assertHasAccessToTask(String); then throw IllegalStateException")
  void testAssertHasAccessToTask_thenThrowIllegalStateException() {
    // Arrange
    when(securityManager.getAuthenticatedUserId()).thenReturn("");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeHelper.assertHasAccessToTask("42"));
    verify(securityManager).getAuthenticatedUserId();
  }

  /**
   * Test {@link TaskRuntimeHelper#assertHasAccessToTask(String)}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeHelper#assertHasAccessToTask(String)}
   */
  @Test
  @DisplayName("Test assertHasAccessToTask(String); then throw NotFoundException")
  void testAssertHasAccessToTask_thenThrowNotFoundException() throws SecurityException {
    // Arrange
    when(securityManager.getAuthenticatedUserRoles()).thenThrow(new NotFoundException("An error occurred"));
    when(securityManager.getAuthenticatedUserId()).thenReturn("42");

    // Act and Assert
    assertThrows(NotFoundException.class, () -> taskRuntimeHelper.assertHasAccessToTask("42"));
    verify(securityManager).getAuthenticatedUserId();
    verify(securityManager).getAuthenticatedUserRoles();
  }

  /**
   * Test {@link TaskRuntimeHelper#getInternalTaskVariables(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeHelper#getInternalTaskVariables(String)}
   */
  @Test
  @DisplayName("Test getInternalTaskVariables(String); then return Empty")
  void testGetInternalTaskVariables_thenReturnEmpty() {
    // Arrange
    when(taskService.getVariableInstancesLocal(Mockito.<String>any())).thenReturn(new HashMap<>());

    // Act
    Map<String, VariableInstance> actualInternalTaskVariables = taskRuntimeHelper.getInternalTaskVariables("42");

    // Assert
    verify(taskService).getVariableInstancesLocal(eq("42"));
    assertTrue(actualInternalTaskVariables.isEmpty());
  }

  /**
   * Test {@link TaskRuntimeHelper#getInternalTaskVariables(String)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskRuntimeHelper#getInternalTaskVariables(String)}
   */
  @Test
  @DisplayName("Test getInternalTaskVariables(String); then throw IllegalStateException")
  void testGetInternalTaskVariables_thenThrowIllegalStateException() {
    // Arrange
    when(taskService.getVariableInstancesLocal(Mockito.<String>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeHelper.getInternalTaskVariables("42"));
    verify(taskService).getVariableInstancesLocal(eq("42"));
  }

  /**
   * Test
   * {@link TaskRuntimeHelper#handleCompleteTaskPayload(CompleteTaskPayload)}.
   * <ul>
   *   <li>Then {@link CompleteTaskPayload#CompleteTaskPayload()} Variables
   * Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskRuntimeHelper#handleCompleteTaskPayload(CompleteTaskPayload)}
   */
  @Test
  @DisplayName("Test handleCompleteTaskPayload(CompleteTaskPayload); then CompleteTaskPayload() Variables Empty")
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
   * Test
   * {@link TaskRuntimeHelper#handleCompleteTaskPayload(CompleteTaskPayload)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskRuntimeHelper#handleCompleteTaskPayload(CompleteTaskPayload)}
   */
  @Test
  @DisplayName("Test handleCompleteTaskPayload(CompleteTaskPayload); then throw IllegalStateException")
  void testHandleCompleteTaskPayload_thenThrowIllegalStateException() {
    // Arrange
    when(taskVariablesPayloadValidator.handlePayloadVariables(Mockito.<Map<String, Object>>any()))
        .thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class,
        () -> taskRuntimeHelper.handleCompleteTaskPayload(new CompleteTaskPayload()));
    verify(taskVariablesPayloadValidator).handlePayloadVariables(isNull());
  }

  /**
   * Test {@link TaskRuntimeHelper#handleSaveTaskPayload(SaveTaskPayload)}.
   * <ul>
   *   <li>Then {@link SaveTaskPayload#SaveTaskPayload()} Variables Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskRuntimeHelper#handleSaveTaskPayload(SaveTaskPayload)}
   */
  @Test
  @DisplayName("Test handleSaveTaskPayload(SaveTaskPayload); then SaveTaskPayload() Variables Empty")
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
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskRuntimeHelper#handleSaveTaskPayload(SaveTaskPayload)}
   */
  @Test
  @DisplayName("Test handleSaveTaskPayload(SaveTaskPayload); then throw IllegalStateException")
  void testHandleSaveTaskPayload_thenThrowIllegalStateException() {
    // Arrange
    when(taskVariablesPayloadValidator.handlePayloadVariables(Mockito.<Map<String, Object>>any()))
        .thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> taskRuntimeHelper.handleSaveTaskPayload(new SaveTaskPayload()));
    verify(taskVariablesPayloadValidator).handlePayloadVariables(isNull());
  }
}
