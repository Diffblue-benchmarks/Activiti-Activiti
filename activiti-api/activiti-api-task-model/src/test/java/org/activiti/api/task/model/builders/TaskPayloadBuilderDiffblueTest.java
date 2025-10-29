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
import org.junit.jupiter.api.Test;

class TaskPayloadBuilderDiffblueTest {
  /**
   * Method under test: {@link TaskPayloadBuilder#tasks()}
   */
  @Test
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
   * Method under test:
   * {@link TaskPayloadBuilder#tasksForProcess(ProcessInstance)}
   */
  @Test
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
   * Method under test: {@link TaskPayloadBuilder#complete()}
   */
  @Test
  void testComplete() {
    // Arrange, Act and Assert
    CompleteTaskPayload buildResult = TaskPayloadBuilder.complete().build();
    assertNull(buildResult.getTaskId());
    assertNull(buildResult.getVariables());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#save()}
   */
  @Test
  void testSave() {
    // Arrange, Act and Assert
    SaveTaskPayload buildResult = TaskPayloadBuilder.save().build();
    assertNull(buildResult.getTaskId());
    assertNull(buildResult.getVariables());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#claim()}
   */
  @Test
  void testClaim() {
    // Arrange, Act and Assert
    ClaimTaskPayload buildResult = TaskPayloadBuilder.claim().build();
    assertNull(buildResult.getAssignee());
    assertNull(buildResult.getTaskId());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#release()}
   */
  @Test
  void testRelease() {
    // Arrange, Act and Assert
    assertNull(TaskPayloadBuilder.release().build().getTaskId());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#createVariable()}
   */
  @Test
  void testCreateVariable() {
    // Arrange, Act and Assert
    CreateTaskVariablePayload buildResult = TaskPayloadBuilder.createVariable().build();
    assertNull(buildResult.getValue());
    assertNull(buildResult.getName());
    assertNull(buildResult.getTaskId());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#updateVariable()}
   */
  @Test
  void testUpdateVariable() {
    // Arrange, Act and Assert
    UpdateTaskVariablePayload buildResult = TaskPayloadBuilder.updateVariable().build();
    assertNull(buildResult.getValue());
    assertNull(buildResult.getName());
    assertNull(buildResult.getTaskId());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#variables()}
   */
  @Test
  void testVariables() {
    // Arrange, Act and Assert
    assertNull(TaskPayloadBuilder.variables().build().getTaskId());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#update()}
   */
  @Test
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
   * Method under test: {@link TaskPayloadBuilder#delete()}
   */
  @Test
  void testDelete() {
    // Arrange, Act and Assert
    DeleteTaskPayload buildResult = TaskPayloadBuilder.delete().build();
    assertNull(buildResult.getReason());
    assertNull(buildResult.getTaskId());
    assertFalse(buildResult.hasReason());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#create()}
   */
  @Test
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
   * Method under test: {@link TaskPayloadBuilder#assign()}
   */
  @Test
  void testAssign() {
    // Arrange, Act and Assert
    AssignTaskPayload buildResult = TaskPayloadBuilder.assign().build();
    assertNull(buildResult.getAssignee());
    assertNull(buildResult.getTaskId());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#assignMultiple()}
   */
  @Test
  void testAssignMultiple() {
    // Arrange, Act and Assert
    AssignTasksPayload buildResult = TaskPayloadBuilder.assignMultiple().build();
    assertNull(buildResult.getAssignee());
    assertTrue(buildResult.getTaskIds().isEmpty());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#addCandidateUsers()}
   */
  @Test
  void testAddCandidateUsers() {
    // Arrange, Act and Assert
    CandidateUsersPayload buildResult = TaskPayloadBuilder.addCandidateUsers().build();
    assertNull(buildResult.getTaskId());
    assertTrue(buildResult.getCandidateUsers().isEmpty());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#deleteCandidateUsers()}
   */
  @Test
  void testDeleteCandidateUsers() {
    // Arrange, Act and Assert
    CandidateUsersPayload buildResult = TaskPayloadBuilder.deleteCandidateUsers().build();
    assertNull(buildResult.getTaskId());
    assertTrue(buildResult.getCandidateUsers().isEmpty());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#addCandidateGroups()}
   */
  @Test
  void testAddCandidateGroups() {
    // Arrange, Act and Assert
    CandidateGroupsPayload buildResult = TaskPayloadBuilder.addCandidateGroups().build();
    assertNull(buildResult.getTaskId());
    assertTrue(buildResult.getCandidateGroups().isEmpty());
  }

  /**
   * Method under test: {@link TaskPayloadBuilder#deleteCandidateGroups()}
   */
  @Test
  void testDeleteCandidateGroups() {
    // Arrange, Act and Assert
    CandidateGroupsPayload buildResult = TaskPayloadBuilder.deleteCandidateGroups().build();
    assertNull(buildResult.getTaskId());
    assertTrue(buildResult.getCandidateGroups().isEmpty());
  }
}
