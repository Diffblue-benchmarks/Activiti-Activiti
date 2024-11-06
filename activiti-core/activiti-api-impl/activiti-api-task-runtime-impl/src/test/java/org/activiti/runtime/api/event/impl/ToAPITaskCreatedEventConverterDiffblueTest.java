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
package org.activiti.runtime.api.event.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.impl.TaskImpl;
import org.activiti.api.task.runtime.events.TaskCreatedEvent;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.activiti.runtime.api.model.impl.APITaskConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ToAPITaskCreatedEventConverterDiffblueTest {
  /**
   * Test {@link ToAPITaskCreatedEventConverter#from(ActivitiEntityEvent)} with
   * {@code ActivitiEntityEvent}.
   * <p>
   * Method under test:
   * {@link ToAPITaskCreatedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'")
  void testFromWithActivitiEntityEvent() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    APITaskConverter taskConverter = mock(APITaskConverter.class);
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);

    when(taskConverter.fromWithCandidates(Mockito.<org.activiti.engine.task.Task>any())).thenReturn(taskImpl);
    ToAPITaskCreatedEventConverter toAPITaskCreatedEventConverter = new ToAPITaskCreatedEventConverter(taskConverter);
    ActivitiEntityEvent internalEvent = mock(ActivitiEntityEvent.class);
    when(internalEvent.getEntity()).thenReturn(mock(TaskEntityImpl.class));

    // Act
    Optional<TaskCreatedEvent> actualFromResult = toAPITaskCreatedEventConverter.from(internalEvent);

    // Assert
    verify(internalEvent).getEntity();
    verify(taskConverter).fromWithCandidates(isA(org.activiti.engine.task.Task.class));
    TaskCreatedEvent getResult = actualFromResult.get();
    assertTrue(getResult instanceof TaskCreatedEventImpl);
    assertSame(taskImpl, getResult.getEntity());
  }

  /**
   * Test {@link ToAPITaskCreatedEventConverter#from(ActivitiEntityEvent)} with
   * {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ToAPITaskCreatedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  void testFromWithActivitiEntityEvent_givenArrayListAddIdentityLinkEntityImpl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());
    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    ToAPITaskCreatedEventConverter toAPITaskCreatedEventConverter = new ToAPITaskCreatedEventConverter(
        new APITaskConverter(taskService));
    TaskEntityImpl taskEntityImpl = mock(TaskEntityImpl.class);
    when(taskEntityImpl.isDeleted()).thenReturn(true);
    when(taskEntityImpl.getPriority()).thenReturn(1);
    when(taskEntityImpl.getAppVersion()).thenReturn(1);
    when(taskEntityImpl.getId()).thenReturn("42");
    when(taskEntityImpl.getAssignee()).thenReturn("Assignee");
    when(taskEntityImpl.getBusinessKey()).thenReturn("Business Key");
    when(taskEntityImpl.getDescription()).thenReturn("The characteristics of someone or something");
    when(taskEntityImpl.getFormKey()).thenReturn("Form Key");
    when(taskEntityImpl.getName()).thenReturn("Name");
    when(taskEntityImpl.getOwner()).thenReturn("Owner");
    when(taskEntityImpl.getParentTaskId()).thenReturn("42");
    when(taskEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(taskEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(taskEntityImpl.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(taskEntityImpl.getClaimTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(taskEntityImpl.getCreateTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(taskEntityImpl.getDueDate())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    ActivitiEntityEvent internalEvent = mock(ActivitiEntityEvent.class);
    when(internalEvent.getEntity()).thenReturn(taskEntityImpl);

    // Act
    Optional<TaskCreatedEvent> actualFromResult = toAPITaskCreatedEventConverter.from(internalEvent);

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    verify(internalEvent).getEntity();
    verify(taskEntityImpl, atLeast(1)).getId();
    verify(taskEntityImpl).getAppVersion();
    verify(taskEntityImpl).getAssignee();
    verify(taskEntityImpl).getBusinessKey();
    verify(taskEntityImpl).getClaimTime();
    verify(taskEntityImpl).getCreateTime();
    verify(taskEntityImpl).getDescription();
    verify(taskEntityImpl).getDueDate();
    verify(taskEntityImpl).getFormKey();
    verify(taskEntityImpl).getName();
    verify(taskEntityImpl).getOwner();
    verify(taskEntityImpl).getParentTaskId();
    verify(taskEntityImpl).getPriority();
    verify(taskEntityImpl).getProcessDefinitionId();
    verify(taskEntityImpl).getProcessInstanceId();
    verify(taskEntityImpl).getTaskDefinitionKey();
    verify(taskEntityImpl).isDeleted();
    TaskCreatedEvent getResult = actualFromResult.get();
    Task entity = getResult.getEntity();
    assertTrue(entity instanceof TaskImpl);
    assertTrue(getResult instanceof TaskCreatedEventImpl);
    assertEquals("1", entity.getAppVersion());
    assertEquals("42", entity.getParentTaskId());
    assertEquals("42", entity.getProcessDefinitionId());
    assertEquals("42", entity.getProcessInstanceId());
    assertEquals("Assignee", entity.getAssignee());
    assertEquals("Business Key", entity.getBusinessKey());
    assertEquals("Form Key", entity.getFormKey());
    assertEquals("Owner", entity.getOwner());
    assertEquals("Task Definition Key", entity.getTaskDefinitionKey());
    assertEquals("The characteristics of someone or something", entity.getDescription());
    assertEquals(1, entity.getPriority());
    assertEquals(Task.TaskStatus.CANCELLED, entity.getStatus());
    assertFalse(entity.isStandalone());
    assertTrue(entity.getCandidateGroups().isEmpty());
    assertTrue(entity.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link ToAPITaskCreatedEventConverter#from(ActivitiEntityEvent)} with
   * {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link IdentityLinkEntityImpl}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ToAPITaskCreatedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; given ArrayList() add IdentityLinkEntityImpl (default constructor)")
  void testFromWithActivitiEntityEvent_givenArrayListAddIdentityLinkEntityImpl2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ArrayList<IdentityLink> identityLinkList = new ArrayList<>();
    identityLinkList.add(new IdentityLinkEntityImpl());
    identityLinkList.add(new IdentityLinkEntityImpl());
    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(identityLinkList);
    ToAPITaskCreatedEventConverter toAPITaskCreatedEventConverter = new ToAPITaskCreatedEventConverter(
        new APITaskConverter(taskService));
    TaskEntityImpl taskEntityImpl = mock(TaskEntityImpl.class);
    when(taskEntityImpl.isDeleted()).thenReturn(true);
    when(taskEntityImpl.getPriority()).thenReturn(1);
    when(taskEntityImpl.getAppVersion()).thenReturn(1);
    when(taskEntityImpl.getId()).thenReturn("42");
    when(taskEntityImpl.getAssignee()).thenReturn("Assignee");
    when(taskEntityImpl.getBusinessKey()).thenReturn("Business Key");
    when(taskEntityImpl.getDescription()).thenReturn("The characteristics of someone or something");
    when(taskEntityImpl.getFormKey()).thenReturn("Form Key");
    when(taskEntityImpl.getName()).thenReturn("Name");
    when(taskEntityImpl.getOwner()).thenReturn("Owner");
    when(taskEntityImpl.getParentTaskId()).thenReturn("42");
    when(taskEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(taskEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(taskEntityImpl.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(taskEntityImpl.getClaimTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(taskEntityImpl.getCreateTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(taskEntityImpl.getDueDate())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    ActivitiEntityEvent internalEvent = mock(ActivitiEntityEvent.class);
    when(internalEvent.getEntity()).thenReturn(taskEntityImpl);

    // Act
    Optional<TaskCreatedEvent> actualFromResult = toAPITaskCreatedEventConverter.from(internalEvent);

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    verify(internalEvent).getEntity();
    verify(taskEntityImpl, atLeast(1)).getId();
    verify(taskEntityImpl).getAppVersion();
    verify(taskEntityImpl).getAssignee();
    verify(taskEntityImpl).getBusinessKey();
    verify(taskEntityImpl).getClaimTime();
    verify(taskEntityImpl).getCreateTime();
    verify(taskEntityImpl).getDescription();
    verify(taskEntityImpl).getDueDate();
    verify(taskEntityImpl).getFormKey();
    verify(taskEntityImpl).getName();
    verify(taskEntityImpl).getOwner();
    verify(taskEntityImpl).getParentTaskId();
    verify(taskEntityImpl).getPriority();
    verify(taskEntityImpl).getProcessDefinitionId();
    verify(taskEntityImpl).getProcessInstanceId();
    verify(taskEntityImpl).getTaskDefinitionKey();
    verify(taskEntityImpl).isDeleted();
    TaskCreatedEvent getResult = actualFromResult.get();
    Task entity = getResult.getEntity();
    assertTrue(entity instanceof TaskImpl);
    assertTrue(getResult instanceof TaskCreatedEventImpl);
    assertEquals("1", entity.getAppVersion());
    assertEquals("42", entity.getParentTaskId());
    assertEquals("42", entity.getProcessDefinitionId());
    assertEquals("42", entity.getProcessInstanceId());
    assertEquals("Assignee", entity.getAssignee());
    assertEquals("Business Key", entity.getBusinessKey());
    assertEquals("Form Key", entity.getFormKey());
    assertEquals("Owner", entity.getOwner());
    assertEquals("Task Definition Key", entity.getTaskDefinitionKey());
    assertEquals("The characteristics of someone or something", entity.getDescription());
    assertEquals(1, entity.getPriority());
    assertEquals(Task.TaskStatus.CANCELLED, entity.getStatus());
    assertFalse(entity.isStandalone());
    assertTrue(entity.getCandidateGroups().isEmpty());
    assertTrue(entity.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link ToAPITaskCreatedEventConverter#from(ActivitiEntityEvent)} with
   * {@code ActivitiEntityEvent}.
   * <ul>
   *   <li>Then {@link Optional#get()} Entity return {@link TaskImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ToAPITaskCreatedEventConverter#from(ActivitiEntityEvent)}
   */
  @Test
  @DisplayName("Test from(ActivitiEntityEvent) with 'ActivitiEntityEvent'; then get() Entity return TaskImpl")
  void testFromWithActivitiEntityEvent_thenGetEntityReturnTaskImpl() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskService taskService = mock(TaskService.class);
    when(taskService.getIdentityLinksForTask(Mockito.<String>any())).thenReturn(new ArrayList<>());
    ToAPITaskCreatedEventConverter toAPITaskCreatedEventConverter = new ToAPITaskCreatedEventConverter(
        new APITaskConverter(taskService));
    TaskEntityImpl taskEntityImpl = mock(TaskEntityImpl.class);
    when(taskEntityImpl.isDeleted()).thenReturn(true);
    when(taskEntityImpl.getPriority()).thenReturn(1);
    when(taskEntityImpl.getAppVersion()).thenReturn(1);
    when(taskEntityImpl.getId()).thenReturn("42");
    when(taskEntityImpl.getAssignee()).thenReturn("Assignee");
    when(taskEntityImpl.getBusinessKey()).thenReturn("Business Key");
    when(taskEntityImpl.getDescription()).thenReturn("The characteristics of someone or something");
    when(taskEntityImpl.getFormKey()).thenReturn("Form Key");
    when(taskEntityImpl.getName()).thenReturn("Name");
    when(taskEntityImpl.getOwner()).thenReturn("Owner");
    when(taskEntityImpl.getParentTaskId()).thenReturn("42");
    when(taskEntityImpl.getProcessDefinitionId()).thenReturn("42");
    when(taskEntityImpl.getProcessInstanceId()).thenReturn("42");
    when(taskEntityImpl.getTaskDefinitionKey()).thenReturn("Task Definition Key");
    when(taskEntityImpl.getClaimTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(taskEntityImpl.getCreateTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(taskEntityImpl.getDueDate())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    ActivitiEntityEvent internalEvent = mock(ActivitiEntityEvent.class);
    when(internalEvent.getEntity()).thenReturn(taskEntityImpl);

    // Act
    Optional<TaskCreatedEvent> actualFromResult = toAPITaskCreatedEventConverter.from(internalEvent);

    // Assert
    verify(taskService).getIdentityLinksForTask(eq("42"));
    verify(internalEvent).getEntity();
    verify(taskEntityImpl, atLeast(1)).getId();
    verify(taskEntityImpl).getAppVersion();
    verify(taskEntityImpl).getAssignee();
    verify(taskEntityImpl).getBusinessKey();
    verify(taskEntityImpl).getClaimTime();
    verify(taskEntityImpl).getCreateTime();
    verify(taskEntityImpl).getDescription();
    verify(taskEntityImpl).getDueDate();
    verify(taskEntityImpl).getFormKey();
    verify(taskEntityImpl).getName();
    verify(taskEntityImpl).getOwner();
    verify(taskEntityImpl).getParentTaskId();
    verify(taskEntityImpl).getPriority();
    verify(taskEntityImpl).getProcessDefinitionId();
    verify(taskEntityImpl).getProcessInstanceId();
    verify(taskEntityImpl).getTaskDefinitionKey();
    verify(taskEntityImpl).isDeleted();
    TaskCreatedEvent getResult = actualFromResult.get();
    Task entity = getResult.getEntity();
    assertTrue(entity instanceof TaskImpl);
    assertTrue(getResult instanceof TaskCreatedEventImpl);
    assertEquals("1", entity.getAppVersion());
    assertEquals("42", entity.getParentTaskId());
    assertEquals("42", entity.getProcessDefinitionId());
    assertEquals("42", entity.getProcessInstanceId());
    assertEquals("Assignee", entity.getAssignee());
    assertEquals("Business Key", entity.getBusinessKey());
    assertEquals("Form Key", entity.getFormKey());
    assertEquals("Owner", entity.getOwner());
    assertEquals("Task Definition Key", entity.getTaskDefinitionKey());
    assertEquals("The characteristics of someone or something", entity.getDescription());
    assertEquals(1, entity.getPriority());
    assertEquals(Task.TaskStatus.CANCELLED, entity.getStatus());
    assertFalse(entity.isStandalone());
    assertTrue(entity.getCandidateGroups().isEmpty());
    assertTrue(entity.getCandidateUsers().isEmpty());
  }
}
