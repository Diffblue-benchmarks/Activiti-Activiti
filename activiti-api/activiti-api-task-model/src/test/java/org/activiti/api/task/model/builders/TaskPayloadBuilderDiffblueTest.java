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
package org.activiti.api.task.model.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.task.model.payloads.AssignTaskPayload;
import org.activiti.api.task.model.payloads.AssignTasksPayload;
import org.activiti.api.task.model.payloads.CandidateGroupsPayload;
import org.activiti.api.task.model.payloads.CandidateUsersPayload;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.model.payloads.CreateTaskPayload;
import org.activiti.api.task.model.payloads.CreateTaskVariablePayload;
import org.activiti.api.task.model.payloads.DeleteTaskPayload;
import org.activiti.api.task.model.payloads.GetTasksPayload;
import org.activiti.api.task.model.payloads.SaveTaskPayload;
import org.activiti.api.task.model.payloads.UpdateTaskPayload;
import org.activiti.api.task.model.payloads.UpdateTaskVariablePayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskPayloadBuilderDiffblueTest {
  /**
   * Test {@link TaskPayloadBuilder#tasks()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#tasks()}
   */
  @Test
  @DisplayName("Test tasks()")
  void testTasks() {
    // Arrange, Act and Assert
    GetTasksPayload buildResult = TaskPayloadBuilder.tasks().build();
    assertNull(buildResult.getAssigneeId());
    assertNull(buildResult.getParentTaskId());
    assertNull(buildResult.getProcessInstanceId());
    assertNull(buildResult.getGroups());
    assertTrue(buildResult.isStandalone());
  }

  /**
   * Test {@link TaskPayloadBuilder#tasksForProcess(ProcessInstance)}.
   * <p>
   * Method under test:
   * {@link TaskPayloadBuilder#tasksForProcess(ProcessInstance)}
   */
  @Test
  @DisplayName("Test tasksForProcess(ProcessInstance)")
  void testTasksForProcess() {
    // Arrange
    ProcessInstance processInstance = mock(ProcessInstance.class);
    when(processInstance.getId()).thenReturn("42");

    // Act
    GetTasksPayloadBuilder actualTasksForProcessResult = TaskPayloadBuilder.tasksForProcess(processInstance);

    // Assert
    verify(processInstance).getId();
    GetTasksPayload buildResult = actualTasksForProcessResult.build();
    assertEquals("42", buildResult.getProcessInstanceId());
    assertNull(buildResult.getAssigneeId());
    assertNull(buildResult.getParentTaskId());
    assertNull(buildResult.getGroups());
    assertFalse(buildResult.isStandalone());
  }

  /**
   * Test {@link TaskPayloadBuilder#complete()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#complete()}
   */
  @Test
  @DisplayName("Test complete()")
  void testComplete() {
    // Arrange, Act and Assert
    CompleteTaskPayload buildResult = TaskPayloadBuilder.complete().build();
    assertNull(buildResult.getTaskId());
    assertNull(buildResult.getVariables());
  }

  /**
   * Test {@link TaskPayloadBuilder#save()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#save()}
   */
  @Test
  @DisplayName("Test save()")
  void testSave() {
    // Arrange, Act and Assert
    SaveTaskPayload buildResult = TaskPayloadBuilder.save().build();
    assertNull(buildResult.getTaskId());
    assertNull(buildResult.getVariables());
  }

  /**
   * Test {@link TaskPayloadBuilder#claim()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#claim()}
   */
  @Test
  @DisplayName("Test claim()")
  void testClaim() {
    // Arrange, Act and Assert
    ClaimTaskPayload buildResult = TaskPayloadBuilder.claim().build();
    assertNull(buildResult.getAssignee());
    assertNull(buildResult.getTaskId());
  }

  /**
   * Test {@link TaskPayloadBuilder#release()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#release()}
   */
  @Test
  @DisplayName("Test release()")
  void testRelease() {
    // Arrange, Act and Assert
    assertNull(TaskPayloadBuilder.release().build().getTaskId());
  }

  /**
   * Test {@link TaskPayloadBuilder#createVariable()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#createVariable()}
   */
  @Test
  @DisplayName("Test createVariable()")
  void testCreateVariable() {
    // Arrange, Act and Assert
    CreateTaskVariablePayload buildResult = TaskPayloadBuilder.createVariable().build();
    assertNull(buildResult.getValue());
    assertNull(buildResult.getName());
    assertNull(buildResult.getTaskId());
  }

  /**
   * Test {@link TaskPayloadBuilder#updateVariable()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#updateVariable()}
   */
  @Test
  @DisplayName("Test updateVariable()")
  void testUpdateVariable() {
    // Arrange, Act and Assert
    UpdateTaskVariablePayload buildResult = TaskPayloadBuilder.updateVariable().build();
    assertNull(buildResult.getValue());
    assertNull(buildResult.getName());
    assertNull(buildResult.getTaskId());
  }

  /**
   * Test {@link TaskPayloadBuilder#variables()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#variables()}
   */
  @Test
  @DisplayName("Test variables()")
  void testVariables() {
    // Arrange, Act and Assert
    assertNull(TaskPayloadBuilder.variables().build().getTaskId());
  }

  /**
   * Test {@link TaskPayloadBuilder#update()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#update()}
   */
  @Test
  @DisplayName("Test update()")
  void testUpdate() {
    // Arrange, Act and Assert
    UpdateTaskPayload buildResult = TaskPayloadBuilder.update().build();
    assertNull(buildResult.getPriority());
    assertNull(buildResult.getAssignee());
    assertNull(buildResult.getDescription());
    assertNull(buildResult.getFormKey());
    assertNull(buildResult.getName());
    assertNull(buildResult.getParentTaskId());
    assertNull(buildResult.getTaskId());
    assertNull(buildResult.getDueDate());
  }

  /**
   * Test {@link TaskPayloadBuilder#delete()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#delete()}
   */
  @Test
  @DisplayName("Test delete()")
  void testDelete() {
    // Arrange, Act and Assert
    DeleteTaskPayload buildResult = TaskPayloadBuilder.delete().build();
    assertNull(buildResult.getReason());
    assertNull(buildResult.getTaskId());
    assertFalse(buildResult.hasReason());
  }

  /**
   * Test {@link TaskPayloadBuilder#create()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#create()}
   */
  @Test
  @DisplayName("Test create()")
  void testCreate() {
    // Arrange, Act and Assert
    CreateTaskPayload buildResult = TaskPayloadBuilder.create().build();
    assertNull(buildResult.getAssignee());
    assertNull(buildResult.getDescription());
    assertNull(buildResult.getFormKey());
    assertNull(buildResult.getName());
    assertNull(buildResult.getParentTaskId());
    assertNull(buildResult.getDueDate());
    assertEquals(0, buildResult.getPriority());
    assertTrue(buildResult.getCandidateGroups().isEmpty());
    assertTrue(buildResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link TaskPayloadBuilder#assign()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#assign()}
   */
  @Test
  @DisplayName("Test assign()")
  void testAssign() {
    // Arrange, Act and Assert
    AssignTaskPayload buildResult = TaskPayloadBuilder.assign().build();
    assertNull(buildResult.getAssignee());
    assertNull(buildResult.getTaskId());
  }

  /**
   * Test {@link TaskPayloadBuilder#assignMultiple()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#assignMultiple()}
   */
  @Test
  @DisplayName("Test assignMultiple()")
  void testAssignMultiple() {
    // Arrange, Act and Assert
    AssignTasksPayload buildResult = TaskPayloadBuilder.assignMultiple().build();
    assertNull(buildResult.getAssignee());
    assertTrue(buildResult.getTaskIds().isEmpty());
  }

  /**
   * Test {@link TaskPayloadBuilder#addCandidateUsers()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#addCandidateUsers()}
   */
  @Test
  @DisplayName("Test addCandidateUsers()")
  void testAddCandidateUsers() {
    // Arrange, Act and Assert
    CandidateUsersPayload buildResult = TaskPayloadBuilder.addCandidateUsers().build();
    assertNull(buildResult.getTaskId());
    assertTrue(buildResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link TaskPayloadBuilder#deleteCandidateUsers()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#deleteCandidateUsers()}
   */
  @Test
  @DisplayName("Test deleteCandidateUsers()")
  void testDeleteCandidateUsers() {
    // Arrange, Act and Assert
    CandidateUsersPayload buildResult = TaskPayloadBuilder.deleteCandidateUsers().build();
    assertNull(buildResult.getTaskId());
    assertTrue(buildResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link TaskPayloadBuilder#addCandidateGroups()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#addCandidateGroups()}
   */
  @Test
  @DisplayName("Test addCandidateGroups()")
  void testAddCandidateGroups() {
    // Arrange, Act and Assert
    CandidateGroupsPayload buildResult = TaskPayloadBuilder.addCandidateGroups().build();
    assertNull(buildResult.getTaskId());
    assertTrue(buildResult.getCandidateGroups().isEmpty());
  }

  /**
   * Test {@link TaskPayloadBuilder#deleteCandidateGroups()}.
   * <p>
   * Method under test: {@link TaskPayloadBuilder#deleteCandidateGroups()}
   */
  @Test
  @DisplayName("Test deleteCandidateGroups()")
  void testDeleteCandidateGroups() {
    // Arrange, Act and Assert
    CandidateGroupsPayload buildResult = TaskPayloadBuilder.deleteCandidateGroups().build();
    assertNull(buildResult.getTaskId());
    assertTrue(buildResult.getCandidateGroups().isEmpty());
  }
}
