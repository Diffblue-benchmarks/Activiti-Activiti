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
package org.activiti.runtime.api.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import org.activiti.api.task.model.Task.TaskStatus;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class APITaskConverterDiffblueTest {
  /**
   * Test {@link APITaskConverter#fromWithCandidates(Task)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link APITaskConverter#fromWithCandidates(Task)}
   */
  @Test
  @DisplayName(
      "Test fromWithCandidates(Task); given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task APITaskConverter.fromWithCandidates(Task)"})
  void testFromWithCandidates_givenArrayListAddIdentityLinkEntityImpl() {
    // Arrange
    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter apiTaskConverter = new APITaskConverter(taskService);

    Task internalTask = mock(Task.class);
    when(internalTask.isSuspended()).thenReturn(false);
    when(internalTask.getPriority()).thenReturn(1);
    when(internalTask.getAppVersion()).thenReturn(1);
    when(internalTask.getAssignee()).thenReturn("Assignee");
    when(internalTask.getBusinessKey()).thenReturn("Business Key");
    when(internalTask.getDescription()).thenReturn("The characteristics of someone or something");
    when(internalTask.getFormKey()).thenReturn("Form Key");
    when(internalTask.getId()).thenReturn("42");
    when(internalTask.getName()).thenReturn("Name");
    when(internalTask.getOwner()).thenReturn("Owner");
    when(internalTask.getParentTaskId()).thenReturn("42");
    when(internalTask.getProcessDefinitionId()).thenReturn("42");
    when(internalTask.getProcessInstanceId()).thenReturn("42");
    when(internalTask.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(internalTask.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    org.activiti.api.task.model.Task actualFromWithCandidatesResult =
        apiTaskConverter.fromWithCandidates(internalTask);

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(internalTask).getAppVersion();
    verify(internalTask).isSuspended();
    verify(internalTask, atLeast(1)).getAssignee();
    verify(internalTask).getBusinessKey();
    verify(internalTask).getClaimTime();
    verify(internalTask).getCreateTime();
    verify(internalTask).getDescription();
    verify(internalTask).getDueDate();
    verify(internalTask).getFormKey();
    verify(internalTask, atLeast(1)).getId();
    verify(internalTask).getName();
    verify(internalTask).getOwner();
    verify(internalTask).getParentTaskId();
    verify(internalTask).getPriority();
    verify(internalTask).getProcessDefinitionId();
    verify(internalTask).getProcessInstanceId();
    verify(internalTask).getTaskDefinitionKey();
    assertTrue(actualFromWithCandidatesResult instanceof TaskImpl);
    assertEquals("1", actualFromWithCandidatesResult.getAppVersion());
    assertEquals("42", actualFromWithCandidatesResult.getId());
    assertEquals("42", actualFromWithCandidatesResult.getParentTaskId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessDefinitionId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessInstanceId());
    assertEquals("Assignee", actualFromWithCandidatesResult.getAssignee());
    assertEquals("Business Key", actualFromWithCandidatesResult.getBusinessKey());
    assertEquals("Form Key", actualFromWithCandidatesResult.getFormKey());
    assertEquals("Name", actualFromWithCandidatesResult.getName());
    assertEquals("Owner", actualFromWithCandidatesResult.getOwner());
    assertEquals("Task Definition Key", actualFromWithCandidatesResult.getTaskDefinitionKey());
    assertEquals(
        "The characteristics of someone or something",
        actualFromWithCandidatesResult.getDescription());
    assertNull(actualFromWithCandidatesResult.getProcessDefinitionVersion());
    assertNull(actualFromWithCandidatesResult.getDuration());
    assertNull(actualFromWithCandidatesResult.getCompletedBy());
    assertNull(actualFromWithCandidatesResult.getCompletedDate());
    assertEquals(1, actualFromWithCandidatesResult.getPriority());
    assertEquals(TaskStatus.ASSIGNED, actualFromWithCandidatesResult.getStatus());
    assertFalse(actualFromWithCandidatesResult.isStandalone());
    assertTrue(actualFromWithCandidatesResult.getCandidateGroups().isEmpty());
    assertTrue(actualFromWithCandidatesResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link APITaskConverter#fromWithCandidates(Task)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link APITaskConverter#fromWithCandidates(Task)}
   */
  @Test
  @DisplayName(
      "Test fromWithCandidates(Task); given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task APITaskConverter.fromWithCandidates(Task)"})
  void testFromWithCandidates_givenArrayListAddIdentityLinkEntityImpl2() {
    // Arrange
    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());
    identityLinkList.add(new IdentityLinkEntityImpl());

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter apiTaskConverter = new APITaskConverter(taskService);

    Task internalTask = mock(Task.class);
    when(internalTask.isSuspended()).thenReturn(false);
    when(internalTask.getPriority()).thenReturn(1);
    when(internalTask.getAppVersion()).thenReturn(1);
    when(internalTask.getAssignee()).thenReturn("Assignee");
    when(internalTask.getBusinessKey()).thenReturn("Business Key");
    when(internalTask.getDescription()).thenReturn("The characteristics of someone or something");
    when(internalTask.getFormKey()).thenReturn("Form Key");
    when(internalTask.getId()).thenReturn("42");
    when(internalTask.getName()).thenReturn("Name");
    when(internalTask.getOwner()).thenReturn("Owner");
    when(internalTask.getParentTaskId()).thenReturn("42");
    when(internalTask.getProcessDefinitionId()).thenReturn("42");
    when(internalTask.getProcessInstanceId()).thenReturn("42");
    when(internalTask.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(internalTask.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    org.activiti.api.task.model.Task actualFromWithCandidatesResult =
        apiTaskConverter.fromWithCandidates(internalTask);

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(internalTask).getAppVersion();
    verify(internalTask).isSuspended();
    verify(internalTask, atLeast(1)).getAssignee();
    verify(internalTask).getBusinessKey();
    verify(internalTask).getClaimTime();
    verify(internalTask).getCreateTime();
    verify(internalTask).getDescription();
    verify(internalTask).getDueDate();
    verify(internalTask).getFormKey();
    verify(internalTask, atLeast(1)).getId();
    verify(internalTask).getName();
    verify(internalTask).getOwner();
    verify(internalTask).getParentTaskId();
    verify(internalTask).getPriority();
    verify(internalTask).getProcessDefinitionId();
    verify(internalTask).getProcessInstanceId();
    verify(internalTask).getTaskDefinitionKey();
    assertTrue(actualFromWithCandidatesResult instanceof TaskImpl);
    assertEquals("1", actualFromWithCandidatesResult.getAppVersion());
    assertEquals("42", actualFromWithCandidatesResult.getId());
    assertEquals("42", actualFromWithCandidatesResult.getParentTaskId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessDefinitionId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessInstanceId());
    assertEquals("Assignee", actualFromWithCandidatesResult.getAssignee());
    assertEquals("Business Key", actualFromWithCandidatesResult.getBusinessKey());
    assertEquals("Form Key", actualFromWithCandidatesResult.getFormKey());
    assertEquals("Name", actualFromWithCandidatesResult.getName());
    assertEquals("Owner", actualFromWithCandidatesResult.getOwner());
    assertEquals("Task Definition Key", actualFromWithCandidatesResult.getTaskDefinitionKey());
    assertEquals(
        "The characteristics of someone or something",
        actualFromWithCandidatesResult.getDescription());
    assertNull(actualFromWithCandidatesResult.getProcessDefinitionVersion());
    assertNull(actualFromWithCandidatesResult.getDuration());
    assertNull(actualFromWithCandidatesResult.getCompletedBy());
    assertNull(actualFromWithCandidatesResult.getCompletedDate());
    assertEquals(1, actualFromWithCandidatesResult.getPriority());
    assertEquals(TaskStatus.ASSIGNED, actualFromWithCandidatesResult.getStatus());
    assertFalse(actualFromWithCandidatesResult.isStandalone());
    assertTrue(actualFromWithCandidatesResult.getCandidateGroups().isEmpty());
    assertTrue(actualFromWithCandidatesResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link APITaskConverter#fromWithCandidates(Task)}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>Then return Assignee is empty string.
   * </ul>
   *
   * <p>Method under test: {@link APITaskConverter#fromWithCandidates(Task)}
   */
  @Test
  @DisplayName(
      "Test fromWithCandidates(Task); given empty string; then return Assignee is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task APITaskConverter.fromWithCandidates(Task)"})
  void testFromWithCandidates_givenEmptyString_thenReturnAssigneeIsEmptyString() {
    // Arrange
    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter apiTaskConverter = new APITaskConverter(taskService);

    Task internalTask = mock(Task.class);
    when(internalTask.isSuspended()).thenReturn(false);
    when(internalTask.getPriority()).thenReturn(1);
    when(internalTask.getAppVersion()).thenReturn(1);
    when(internalTask.getAssignee()).thenReturn("");
    when(internalTask.getBusinessKey()).thenReturn("Business Key");
    when(internalTask.getDescription()).thenReturn("The characteristics of someone or something");
    when(internalTask.getFormKey()).thenReturn("Form Key");
    when(internalTask.getId()).thenReturn("42");
    when(internalTask.getName()).thenReturn("Name");
    when(internalTask.getOwner()).thenReturn("Owner");
    when(internalTask.getParentTaskId()).thenReturn("42");
    when(internalTask.getProcessDefinitionId()).thenReturn("42");
    when(internalTask.getProcessInstanceId()).thenReturn("42");
    when(internalTask.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(internalTask.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    org.activiti.api.task.model.Task actualFromWithCandidatesResult =
        apiTaskConverter.fromWithCandidates(internalTask);

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(internalTask).getAppVersion();
    verify(internalTask).isSuspended();
    verify(internalTask, atLeast(1)).getAssignee();
    verify(internalTask).getBusinessKey();
    verify(internalTask).getClaimTime();
    verify(internalTask).getCreateTime();
    verify(internalTask).getDescription();
    verify(internalTask).getDueDate();
    verify(internalTask).getFormKey();
    verify(internalTask, atLeast(1)).getId();
    verify(internalTask).getName();
    verify(internalTask).getOwner();
    verify(internalTask).getParentTaskId();
    verify(internalTask).getPriority();
    verify(internalTask).getProcessDefinitionId();
    verify(internalTask).getProcessInstanceId();
    verify(internalTask).getTaskDefinitionKey();
    assertTrue(actualFromWithCandidatesResult instanceof TaskImpl);
    assertEquals("", actualFromWithCandidatesResult.getAssignee());
    assertEquals("1", actualFromWithCandidatesResult.getAppVersion());
    assertEquals("42", actualFromWithCandidatesResult.getId());
    assertEquals("42", actualFromWithCandidatesResult.getParentTaskId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessDefinitionId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessInstanceId());
    assertEquals("Business Key", actualFromWithCandidatesResult.getBusinessKey());
    assertEquals("Form Key", actualFromWithCandidatesResult.getFormKey());
    assertEquals("Name", actualFromWithCandidatesResult.getName());
    assertEquals("Owner", actualFromWithCandidatesResult.getOwner());
    assertEquals("Task Definition Key", actualFromWithCandidatesResult.getTaskDefinitionKey());
    assertEquals(
        "The characteristics of someone or something",
        actualFromWithCandidatesResult.getDescription());
    assertNull(actualFromWithCandidatesResult.getProcessDefinitionVersion());
    assertNull(actualFromWithCandidatesResult.getDuration());
    assertNull(actualFromWithCandidatesResult.getCompletedBy());
    assertNull(actualFromWithCandidatesResult.getCompletedDate());
    assertEquals(1, actualFromWithCandidatesResult.getPriority());
    assertEquals(TaskStatus.CREATED, actualFromWithCandidatesResult.getStatus());
    assertFalse(actualFromWithCandidatesResult.isStandalone());
    assertTrue(actualFromWithCandidatesResult.getCandidateGroups().isEmpty());
    assertTrue(actualFromWithCandidatesResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link APITaskConverter#fromWithCandidates(Task)}.
   *
   * <ul>
   *   <li>Given {@link IdentityLinkEntityImpl} (default constructor) Type is {@code candidate}.
   * </ul>
   *
   * <p>Method under test: {@link APITaskConverter#fromWithCandidates(Task)}
   */
  @Test
  @DisplayName(
      "Test fromWithCandidates(Task); given IdentityLinkEntityImpl (default constructor) Type is 'candidate'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task APITaskConverter.fromWithCandidates(Task)"})
  void testFromWithCandidates_givenIdentityLinkEntityImplTypeIsCandidate() {
    // Arrange
    IdentityLinkEntityImpl identityLinkEntityImpl = new IdentityLinkEntityImpl();
    identityLinkEntityImpl.setType("candidate");

    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(identityLinkEntityImpl);

    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    APITaskConverter apiTaskConverter = new APITaskConverter(taskService);

    Task internalTask = mock(Task.class);
    when(internalTask.isSuspended()).thenReturn(false);
    when(internalTask.getPriority()).thenReturn(1);
    when(internalTask.getAppVersion()).thenReturn(1);
    when(internalTask.getAssignee()).thenReturn("Assignee");
    when(internalTask.getBusinessKey()).thenReturn("Business Key");
    when(internalTask.getDescription()).thenReturn("The characteristics of someone or something");
    when(internalTask.getFormKey()).thenReturn("Form Key");
    when(internalTask.getId()).thenReturn("42");
    when(internalTask.getName()).thenReturn("Name");
    when(internalTask.getOwner()).thenReturn("Owner");
    when(internalTask.getParentTaskId()).thenReturn("42");
    when(internalTask.getProcessDefinitionId()).thenReturn("42");
    when(internalTask.getProcessInstanceId()).thenReturn("42");
    when(internalTask.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(internalTask.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    org.activiti.api.task.model.Task actualFromWithCandidatesResult =
        apiTaskConverter.fromWithCandidates(internalTask);

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(internalTask).getAppVersion();
    verify(internalTask).isSuspended();
    verify(internalTask, atLeast(1)).getAssignee();
    verify(internalTask).getBusinessKey();
    verify(internalTask).getClaimTime();
    verify(internalTask).getCreateTime();
    verify(internalTask).getDescription();
    verify(internalTask).getDueDate();
    verify(internalTask).getFormKey();
    verify(internalTask, atLeast(1)).getId();
    verify(internalTask).getName();
    verify(internalTask).getOwner();
    verify(internalTask).getParentTaskId();
    verify(internalTask).getPriority();
    verify(internalTask).getProcessDefinitionId();
    verify(internalTask).getProcessInstanceId();
    verify(internalTask).getTaskDefinitionKey();
    assertTrue(actualFromWithCandidatesResult instanceof TaskImpl);
    assertEquals("1", actualFromWithCandidatesResult.getAppVersion());
    assertEquals("42", actualFromWithCandidatesResult.getId());
    assertEquals("42", actualFromWithCandidatesResult.getParentTaskId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessDefinitionId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessInstanceId());
    assertEquals("Assignee", actualFromWithCandidatesResult.getAssignee());
    assertEquals("Business Key", actualFromWithCandidatesResult.getBusinessKey());
    assertEquals("Form Key", actualFromWithCandidatesResult.getFormKey());
    assertEquals("Name", actualFromWithCandidatesResult.getName());
    assertEquals("Owner", actualFromWithCandidatesResult.getOwner());
    assertEquals("Task Definition Key", actualFromWithCandidatesResult.getTaskDefinitionKey());
    assertEquals(
        "The characteristics of someone or something",
        actualFromWithCandidatesResult.getDescription());
    assertNull(actualFromWithCandidatesResult.getProcessDefinitionVersion());
    assertNull(actualFromWithCandidatesResult.getDuration());
    assertNull(actualFromWithCandidatesResult.getCompletedBy());
    assertNull(actualFromWithCandidatesResult.getCompletedDate());
    assertEquals(1, actualFromWithCandidatesResult.getPriority());
    assertEquals(TaskStatus.ASSIGNED, actualFromWithCandidatesResult.getStatus());
    assertFalse(actualFromWithCandidatesResult.isStandalone());
    assertTrue(actualFromWithCandidatesResult.getCandidateGroups().isEmpty());
    assertTrue(actualFromWithCandidatesResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link APITaskConverter#fromWithCandidates(Task)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then return Assignee is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link APITaskConverter#fromWithCandidates(Task)}
   */
  @Test
  @DisplayName("Test fromWithCandidates(Task); given 'null'; then return Assignee is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task APITaskConverter.fromWithCandidates(Task)"})
  void testFromWithCandidates_givenNull_thenReturnAssigneeIsNull() {
    // Arrange
    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter apiTaskConverter = new APITaskConverter(taskService);

    Task internalTask = mock(Task.class);
    when(internalTask.isSuspended()).thenReturn(false);
    when(internalTask.getPriority()).thenReturn(1);
    when(internalTask.getAppVersion()).thenReturn(1);
    when(internalTask.getAssignee()).thenReturn(null);
    when(internalTask.getBusinessKey()).thenReturn("Business Key");
    when(internalTask.getDescription()).thenReturn("The characteristics of someone or something");
    when(internalTask.getFormKey()).thenReturn("Form Key");
    when(internalTask.getId()).thenReturn("42");
    when(internalTask.getName()).thenReturn("Name");
    when(internalTask.getOwner()).thenReturn("Owner");
    when(internalTask.getParentTaskId()).thenReturn("42");
    when(internalTask.getProcessDefinitionId()).thenReturn("42");
    when(internalTask.getProcessInstanceId()).thenReturn("42");
    when(internalTask.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(internalTask.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    org.activiti.api.task.model.Task actualFromWithCandidatesResult =
        apiTaskConverter.fromWithCandidates(internalTask);

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(internalTask).getAppVersion();
    verify(internalTask).isSuspended();
    verify(internalTask, atLeast(1)).getAssignee();
    verify(internalTask).getBusinessKey();
    verify(internalTask).getClaimTime();
    verify(internalTask).getCreateTime();
    verify(internalTask).getDescription();
    verify(internalTask).getDueDate();
    verify(internalTask).getFormKey();
    verify(internalTask, atLeast(1)).getId();
    verify(internalTask).getName();
    verify(internalTask).getOwner();
    verify(internalTask).getParentTaskId();
    verify(internalTask).getPriority();
    verify(internalTask).getProcessDefinitionId();
    verify(internalTask).getProcessInstanceId();
    verify(internalTask).getTaskDefinitionKey();
    assertTrue(actualFromWithCandidatesResult instanceof TaskImpl);
    assertEquals("1", actualFromWithCandidatesResult.getAppVersion());
    assertEquals("42", actualFromWithCandidatesResult.getId());
    assertEquals("42", actualFromWithCandidatesResult.getParentTaskId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessDefinitionId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessInstanceId());
    assertEquals("Business Key", actualFromWithCandidatesResult.getBusinessKey());
    assertEquals("Form Key", actualFromWithCandidatesResult.getFormKey());
    assertEquals("Name", actualFromWithCandidatesResult.getName());
    assertEquals("Owner", actualFromWithCandidatesResult.getOwner());
    assertEquals("Task Definition Key", actualFromWithCandidatesResult.getTaskDefinitionKey());
    assertEquals(
        "The characteristics of someone or something",
        actualFromWithCandidatesResult.getDescription());
    assertNull(actualFromWithCandidatesResult.getProcessDefinitionVersion());
    assertNull(actualFromWithCandidatesResult.getDuration());
    assertNull(actualFromWithCandidatesResult.getAssignee());
    assertNull(actualFromWithCandidatesResult.getCompletedBy());
    assertNull(actualFromWithCandidatesResult.getCompletedDate());
    assertEquals(1, actualFromWithCandidatesResult.getPriority());
    assertEquals(TaskStatus.CREATED, actualFromWithCandidatesResult.getStatus());
    assertFalse(actualFromWithCandidatesResult.isStandalone());
    assertTrue(actualFromWithCandidatesResult.getCandidateGroups().isEmpty());
    assertTrue(actualFromWithCandidatesResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link APITaskConverter#fromWithCandidates(Task)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return Status is {@code SUSPENDED}.
   * </ul>
   *
   * <p>Method under test: {@link APITaskConverter#fromWithCandidates(Task)}
   */
  @Test
  @DisplayName("Test fromWithCandidates(Task); given 'true'; then return Status is 'SUSPENDED'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task APITaskConverter.fromWithCandidates(Task)"})
  void testFromWithCandidates_givenTrue_thenReturnStatusIsSuspended() {
    // Arrange
    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter apiTaskConverter = new APITaskConverter(taskService);

    Task internalTask = mock(Task.class);
    when(internalTask.isSuspended()).thenReturn(true);
    when(internalTask.getPriority()).thenReturn(1);
    when(internalTask.getAppVersion()).thenReturn(1);
    when(internalTask.getAssignee()).thenReturn("Assignee");
    when(internalTask.getBusinessKey()).thenReturn("Business Key");
    when(internalTask.getDescription()).thenReturn("The characteristics of someone or something");
    when(internalTask.getFormKey()).thenReturn("Form Key");
    when(internalTask.getId()).thenReturn("42");
    when(internalTask.getName()).thenReturn("Name");
    when(internalTask.getOwner()).thenReturn("Owner");
    when(internalTask.getParentTaskId()).thenReturn("42");
    when(internalTask.getProcessDefinitionId()).thenReturn("42");
    when(internalTask.getProcessInstanceId()).thenReturn("42");
    when(internalTask.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(internalTask.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    org.activiti.api.task.model.Task actualFromWithCandidatesResult =
        apiTaskConverter.fromWithCandidates(internalTask);

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(internalTask).getAppVersion();
    verify(internalTask).isSuspended();
    verify(internalTask).getAssignee();
    verify(internalTask).getBusinessKey();
    verify(internalTask).getClaimTime();
    verify(internalTask).getCreateTime();
    verify(internalTask).getDescription();
    verify(internalTask).getDueDate();
    verify(internalTask).getFormKey();
    verify(internalTask, atLeast(1)).getId();
    verify(internalTask).getName();
    verify(internalTask).getOwner();
    verify(internalTask).getParentTaskId();
    verify(internalTask).getPriority();
    verify(internalTask).getProcessDefinitionId();
    verify(internalTask).getProcessInstanceId();
    verify(internalTask).getTaskDefinitionKey();
    assertTrue(actualFromWithCandidatesResult instanceof TaskImpl);
    assertEquals("1", actualFromWithCandidatesResult.getAppVersion());
    assertEquals("42", actualFromWithCandidatesResult.getId());
    assertEquals("42", actualFromWithCandidatesResult.getParentTaskId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessDefinitionId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessInstanceId());
    assertEquals("Assignee", actualFromWithCandidatesResult.getAssignee());
    assertEquals("Business Key", actualFromWithCandidatesResult.getBusinessKey());
    assertEquals("Form Key", actualFromWithCandidatesResult.getFormKey());
    assertEquals("Name", actualFromWithCandidatesResult.getName());
    assertEquals("Owner", actualFromWithCandidatesResult.getOwner());
    assertEquals("Task Definition Key", actualFromWithCandidatesResult.getTaskDefinitionKey());
    assertEquals(
        "The characteristics of someone or something",
        actualFromWithCandidatesResult.getDescription());
    assertNull(actualFromWithCandidatesResult.getProcessDefinitionVersion());
    assertNull(actualFromWithCandidatesResult.getDuration());
    assertNull(actualFromWithCandidatesResult.getCompletedBy());
    assertNull(actualFromWithCandidatesResult.getCompletedDate());
    assertEquals(1, actualFromWithCandidatesResult.getPriority());
    assertEquals(TaskStatus.SUSPENDED, actualFromWithCandidatesResult.getStatus());
    assertFalse(actualFromWithCandidatesResult.isStandalone());
    assertTrue(actualFromWithCandidatesResult.getCandidateGroups().isEmpty());
    assertTrue(actualFromWithCandidatesResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link APITaskConverter#fromWithCandidates(Task)}.
   *
   * <ul>
   *   <li>Then return Status is {@code ASSIGNED}.
   * </ul>
   *
   * <p>Method under test: {@link APITaskConverter#fromWithCandidates(Task)}
   */
  @Test
  @DisplayName("Test fromWithCandidates(Task); then return Status is 'ASSIGNED'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.task.model.Task APITaskConverter.fromWithCandidates(Task)"})
  void testFromWithCandidates_thenReturnStatusIsAssigned() {
    // Arrange
    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    APITaskConverter apiTaskConverter = new APITaskConverter(taskService);

    Task internalTask = mock(Task.class);
    when(internalTask.isSuspended()).thenReturn(false);
    when(internalTask.getPriority()).thenReturn(1);
    when(internalTask.getAppVersion()).thenReturn(1);
    when(internalTask.getAssignee()).thenReturn("Assignee");
    when(internalTask.getBusinessKey()).thenReturn("Business Key");
    when(internalTask.getDescription()).thenReturn("The characteristics of someone or something");
    when(internalTask.getFormKey()).thenReturn("Form Key");
    when(internalTask.getId()).thenReturn("42");
    when(internalTask.getName()).thenReturn("Name");
    when(internalTask.getOwner()).thenReturn("Owner");
    when(internalTask.getParentTaskId()).thenReturn("42");
    when(internalTask.getProcessDefinitionId()).thenReturn("42");
    when(internalTask.getProcessInstanceId()).thenReturn("42");
    when(internalTask.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(internalTask.getClaimTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getCreateTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(internalTask.getDueDate())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    org.activiti.api.task.model.Task actualFromWithCandidatesResult =
        apiTaskConverter.fromWithCandidates(internalTask);

    // Assert
    verify(taskService).getIdentityLinksForTask("42");
    verify(internalTask).getAppVersion();
    verify(internalTask).isSuspended();
    verify(internalTask, atLeast(1)).getAssignee();
    verify(internalTask).getBusinessKey();
    verify(internalTask).getClaimTime();
    verify(internalTask).getCreateTime();
    verify(internalTask).getDescription();
    verify(internalTask).getDueDate();
    verify(internalTask).getFormKey();
    verify(internalTask, atLeast(1)).getId();
    verify(internalTask).getName();
    verify(internalTask).getOwner();
    verify(internalTask).getParentTaskId();
    verify(internalTask).getPriority();
    verify(internalTask).getProcessDefinitionId();
    verify(internalTask).getProcessInstanceId();
    verify(internalTask).getTaskDefinitionKey();
    assertTrue(actualFromWithCandidatesResult instanceof TaskImpl);
    assertEquals("1", actualFromWithCandidatesResult.getAppVersion());
    assertEquals("42", actualFromWithCandidatesResult.getId());
    assertEquals("42", actualFromWithCandidatesResult.getParentTaskId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessDefinitionId());
    assertEquals("42", actualFromWithCandidatesResult.getProcessInstanceId());
    assertEquals("Assignee", actualFromWithCandidatesResult.getAssignee());
    assertEquals("Business Key", actualFromWithCandidatesResult.getBusinessKey());
    assertEquals("Form Key", actualFromWithCandidatesResult.getFormKey());
    assertEquals("Name", actualFromWithCandidatesResult.getName());
    assertEquals("Owner", actualFromWithCandidatesResult.getOwner());
    assertEquals("Task Definition Key", actualFromWithCandidatesResult.getTaskDefinitionKey());
    assertEquals(
        "The characteristics of someone or something",
        actualFromWithCandidatesResult.getDescription());
    assertNull(actualFromWithCandidatesResult.getProcessDefinitionVersion());
    assertNull(actualFromWithCandidatesResult.getDuration());
    assertNull(actualFromWithCandidatesResult.getCompletedBy());
    assertNull(actualFromWithCandidatesResult.getCompletedDate());
    assertEquals(1, actualFromWithCandidatesResult.getPriority());
    assertEquals(TaskStatus.ASSIGNED, actualFromWithCandidatesResult.getStatus());
    assertFalse(actualFromWithCandidatesResult.isStandalone());
    assertTrue(actualFromWithCandidatesResult.getCandidateGroups().isEmpty());
    assertTrue(actualFromWithCandidatesResult.getCandidateUsers().isEmpty());
  }
}
