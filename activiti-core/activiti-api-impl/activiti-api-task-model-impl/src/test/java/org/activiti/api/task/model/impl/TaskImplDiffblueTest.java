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
package org.activiti.api.task.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.api.runtime.model.impl.ApplicationElementImpl;
import org.activiti.api.task.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskImpl#TaskImpl()}
   *   <li>{@link TaskImpl#setAssignee(String)}
   *   <li>{@link TaskImpl#setBusinessKey(String)}
   *   <li>{@link TaskImpl#setCandidateGroups(List)}
   *   <li>{@link TaskImpl#setCandidateUsers(List)}
   *   <li>{@link TaskImpl#setClaimedDate(Date)}
   *   <li>{@link TaskImpl#setCompletedBy(String)}
   *   <li>{@link TaskImpl#setCompletedDate(Date)}
   *   <li>{@link TaskImpl#setCreatedDate(Date)}
   *   <li>{@link TaskImpl#setDescription(String)}
   *   <li>{@link TaskImpl#setDueDate(Date)}
   *   <li>{@link TaskImpl#setDuration(Long)}
   *   <li>{@link TaskImpl#setFormKey(String)}
   *   <li>{@link TaskImpl#setId(String)}
   *   <li>{@link TaskImpl#setName(String)}
   *   <li>{@link TaskImpl#setOwner(String)}
   *   <li>{@link TaskImpl#setParentTaskId(String)}
   *   <li>{@link TaskImpl#setPriority(int)}
   *   <li>{@link TaskImpl#setProcessDefinitionId(String)}
   *   <li>{@link TaskImpl#setProcessDefinitionVersion(Integer)}
   *   <li>{@link TaskImpl#setProcessInstanceId(String)}
   *   <li>{@link TaskImpl#setStatus(Task.TaskStatus)}
   *   <li>{@link TaskImpl#setTaskDefinitionKey(String)}
   *   <li>{@link TaskImpl#toString()}
   *   <li>{@link TaskImpl#getAssignee()}
   *   <li>{@link TaskImpl#getBusinessKey()}
   *   <li>{@link TaskImpl#getCandidateGroups()}
   *   <li>{@link TaskImpl#getCandidateUsers()}
   *   <li>{@link TaskImpl#getClaimedDate()}
   *   <li>{@link TaskImpl#getCompletedBy()}
   *   <li>{@link TaskImpl#getCompletedDate()}
   *   <li>{@link TaskImpl#getCreatedDate()}
   *   <li>{@link TaskImpl#getDescription()}
   *   <li>{@link TaskImpl#getDueDate()}
   *   <li>{@link TaskImpl#getDuration()}
   *   <li>{@link TaskImpl#getFormKey()}
   *   <li>{@link TaskImpl#getId()}
   *   <li>{@link TaskImpl#getName()}
   *   <li>{@link TaskImpl#getOwner()}
   *   <li>{@link TaskImpl#getParentTaskId()}
   *   <li>{@link TaskImpl#getPriority()}
   *   <li>{@link TaskImpl#getProcessDefinitionId()}
   *   <li>{@link TaskImpl#getProcessDefinitionVersion()}
   *   <li>{@link TaskImpl#getProcessInstanceId()}
   *   <li>{@link TaskImpl#getStatus()}
   *   <li>{@link TaskImpl#getTaskDefinitionKey()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    TaskImpl actualTaskImpl = new TaskImpl();
    actualTaskImpl.setAssignee("Assignee");
    actualTaskImpl.setBusinessKey("Business Key");
    ArrayList<String> candidateGroups = new ArrayList<>();
    actualTaskImpl.setCandidateGroups(candidateGroups);
    ArrayList<String> candidateUsers = new ArrayList<>();
    actualTaskImpl.setCandidateUsers(candidateUsers);
    Date claimedDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualTaskImpl.setClaimedDate(claimedDate);
    actualTaskImpl.setCompletedBy("Completed By");
    Date completedDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualTaskImpl.setCompletedDate(completedDate);
    Date createdDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualTaskImpl.setCreatedDate(createdDate);
    actualTaskImpl.setDescription("The characteristics of someone or something");
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualTaskImpl.setDueDate(dueDate);
    actualTaskImpl.setDuration(1L);
    actualTaskImpl.setFormKey("Form Key");
    actualTaskImpl.setId("42");
    actualTaskImpl.setName("Name");
    actualTaskImpl.setOwner("Owner");
    actualTaskImpl.setParentTaskId("42");
    actualTaskImpl.setPriority(1);
    actualTaskImpl.setProcessDefinitionId("42");
    actualTaskImpl.setProcessDefinitionVersion(1);
    actualTaskImpl.setProcessInstanceId("42");
    actualTaskImpl.setStatus(Task.TaskStatus.CREATED);
    actualTaskImpl.setTaskDefinitionKey("Task Definition Key");
    actualTaskImpl.toString();
    String actualAssignee = actualTaskImpl.getAssignee();
    String actualBusinessKey = actualTaskImpl.getBusinessKey();
    List<String> actualCandidateGroups = actualTaskImpl.getCandidateGroups();
    List<String> actualCandidateUsers = actualTaskImpl.getCandidateUsers();
    Date actualClaimedDate = actualTaskImpl.getClaimedDate();
    String actualCompletedBy = actualTaskImpl.getCompletedBy();
    Date actualCompletedDate = actualTaskImpl.getCompletedDate();
    Date actualCreatedDate = actualTaskImpl.getCreatedDate();
    String actualDescription = actualTaskImpl.getDescription();
    Date actualDueDate = actualTaskImpl.getDueDate();
    Long actualDuration = actualTaskImpl.getDuration();
    String actualFormKey = actualTaskImpl.getFormKey();
    String actualId = actualTaskImpl.getId();
    String actualName = actualTaskImpl.getName();
    String actualOwner = actualTaskImpl.getOwner();
    String actualParentTaskId = actualTaskImpl.getParentTaskId();
    int actualPriority = actualTaskImpl.getPriority();
    String actualProcessDefinitionId = actualTaskImpl.getProcessDefinitionId();
    Integer actualProcessDefinitionVersion = actualTaskImpl.getProcessDefinitionVersion();
    String actualProcessInstanceId = actualTaskImpl.getProcessInstanceId();
    Task.TaskStatus actualStatus = actualTaskImpl.getStatus();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("42", actualParentTaskId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("Assignee", actualAssignee);
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("Completed By", actualCompletedBy);
    assertEquals("Form Key", actualFormKey);
    assertEquals("Name", actualName);
    assertEquals("Owner", actualOwner);
    assertEquals("Task Definition Key", actualTaskImpl.getTaskDefinitionKey());
    assertEquals("The characteristics of someone or something", actualDescription);
    assertEquals(1, actualProcessDefinitionVersion.intValue());
    assertEquals(1, actualPriority);
    assertEquals(1L, actualDuration.longValue());
    assertEquals(Task.TaskStatus.CREATED, actualStatus);
    assertTrue(actualCandidateGroups.isEmpty());
    assertTrue(actualCandidateUsers.isEmpty());
    assertSame(candidateGroups, actualCandidateGroups);
    assertSame(candidateUsers, actualCandidateUsers);
    assertSame(claimedDate, actualClaimedDate);
    assertSame(completedDate, actualCompletedDate);
    assertSame(createdDate, actualCreatedDate);
    assertSame(dueDate, actualDueDate);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskImpl#TaskImpl(String, String, Task.TaskStatus)}
   *   <li>{@link TaskImpl#setAssignee(String)}
   *   <li>{@link TaskImpl#setBusinessKey(String)}
   *   <li>{@link TaskImpl#setCandidateGroups(List)}
   *   <li>{@link TaskImpl#setCandidateUsers(List)}
   *   <li>{@link TaskImpl#setClaimedDate(Date)}
   *   <li>{@link TaskImpl#setCompletedBy(String)}
   *   <li>{@link TaskImpl#setCompletedDate(Date)}
   *   <li>{@link TaskImpl#setCreatedDate(Date)}
   *   <li>{@link TaskImpl#setDescription(String)}
   *   <li>{@link TaskImpl#setDueDate(Date)}
   *   <li>{@link TaskImpl#setDuration(Long)}
   *   <li>{@link TaskImpl#setFormKey(String)}
   *   <li>{@link TaskImpl#setId(String)}
   *   <li>{@link TaskImpl#setName(String)}
   *   <li>{@link TaskImpl#setOwner(String)}
   *   <li>{@link TaskImpl#setParentTaskId(String)}
   *   <li>{@link TaskImpl#setPriority(int)}
   *   <li>{@link TaskImpl#setProcessDefinitionId(String)}
   *   <li>{@link TaskImpl#setProcessDefinitionVersion(Integer)}
   *   <li>{@link TaskImpl#setProcessInstanceId(String)}
   *   <li>{@link TaskImpl#setStatus(Task.TaskStatus)}
   *   <li>{@link TaskImpl#setTaskDefinitionKey(String)}
   *   <li>{@link TaskImpl#toString()}
   *   <li>{@link TaskImpl#getAssignee()}
   *   <li>{@link TaskImpl#getBusinessKey()}
   *   <li>{@link TaskImpl#getCandidateGroups()}
   *   <li>{@link TaskImpl#getCandidateUsers()}
   *   <li>{@link TaskImpl#getClaimedDate()}
   *   <li>{@link TaskImpl#getCompletedBy()}
   *   <li>{@link TaskImpl#getCompletedDate()}
   *   <li>{@link TaskImpl#getCreatedDate()}
   *   <li>{@link TaskImpl#getDescription()}
   *   <li>{@link TaskImpl#getDueDate()}
   *   <li>{@link TaskImpl#getDuration()}
   *   <li>{@link TaskImpl#getFormKey()}
   *   <li>{@link TaskImpl#getId()}
   *   <li>{@link TaskImpl#getName()}
   *   <li>{@link TaskImpl#getOwner()}
   *   <li>{@link TaskImpl#getParentTaskId()}
   *   <li>{@link TaskImpl#getPriority()}
   *   <li>{@link TaskImpl#getProcessDefinitionId()}
   *   <li>{@link TaskImpl#getProcessDefinitionVersion()}
   *   <li>{@link TaskImpl#getProcessInstanceId()}
   *   <li>{@link TaskImpl#getStatus()}
   *   <li>{@link TaskImpl#getTaskDefinitionKey()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when '42'")
  void testGettersAndSetters_when42() {
    // Arrange and Act
    TaskImpl actualTaskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    actualTaskImpl.setAssignee("Assignee");
    actualTaskImpl.setBusinessKey("Business Key");
    ArrayList<String> candidateGroups = new ArrayList<>();
    actualTaskImpl.setCandidateGroups(candidateGroups);
    ArrayList<String> candidateUsers = new ArrayList<>();
    actualTaskImpl.setCandidateUsers(candidateUsers);
    Date claimedDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualTaskImpl.setClaimedDate(claimedDate);
    actualTaskImpl.setCompletedBy("Completed By");
    Date completedDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualTaskImpl.setCompletedDate(completedDate);
    Date createdDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualTaskImpl.setCreatedDate(createdDate);
    actualTaskImpl.setDescription("The characteristics of someone or something");
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualTaskImpl.setDueDate(dueDate);
    actualTaskImpl.setDuration(1L);
    actualTaskImpl.setFormKey("Form Key");
    actualTaskImpl.setId("42");
    actualTaskImpl.setName("Name");
    actualTaskImpl.setOwner("Owner");
    actualTaskImpl.setParentTaskId("42");
    actualTaskImpl.setPriority(1);
    actualTaskImpl.setProcessDefinitionId("42");
    actualTaskImpl.setProcessDefinitionVersion(1);
    actualTaskImpl.setProcessInstanceId("42");
    actualTaskImpl.setStatus(Task.TaskStatus.CREATED);
    actualTaskImpl.setTaskDefinitionKey("Task Definition Key");
    actualTaskImpl.toString();
    String actualAssignee = actualTaskImpl.getAssignee();
    String actualBusinessKey = actualTaskImpl.getBusinessKey();
    List<String> actualCandidateGroups = actualTaskImpl.getCandidateGroups();
    List<String> actualCandidateUsers = actualTaskImpl.getCandidateUsers();
    Date actualClaimedDate = actualTaskImpl.getClaimedDate();
    String actualCompletedBy = actualTaskImpl.getCompletedBy();
    Date actualCompletedDate = actualTaskImpl.getCompletedDate();
    Date actualCreatedDate = actualTaskImpl.getCreatedDate();
    String actualDescription = actualTaskImpl.getDescription();
    Date actualDueDate = actualTaskImpl.getDueDate();
    Long actualDuration = actualTaskImpl.getDuration();
    String actualFormKey = actualTaskImpl.getFormKey();
    String actualId = actualTaskImpl.getId();
    String actualName = actualTaskImpl.getName();
    String actualOwner = actualTaskImpl.getOwner();
    String actualParentTaskId = actualTaskImpl.getParentTaskId();
    int actualPriority = actualTaskImpl.getPriority();
    String actualProcessDefinitionId = actualTaskImpl.getProcessDefinitionId();
    Integer actualProcessDefinitionVersion = actualTaskImpl.getProcessDefinitionVersion();
    String actualProcessInstanceId = actualTaskImpl.getProcessInstanceId();
    Task.TaskStatus actualStatus = actualTaskImpl.getStatus();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("42", actualParentTaskId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("Assignee", actualAssignee);
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("Completed By", actualCompletedBy);
    assertEquals("Form Key", actualFormKey);
    assertEquals("Name", actualName);
    assertEquals("Owner", actualOwner);
    assertEquals("Task Definition Key", actualTaskImpl.getTaskDefinitionKey());
    assertEquals("The characteristics of someone or something", actualDescription);
    assertEquals(1, actualProcessDefinitionVersion.intValue());
    assertEquals(1, actualPriority);
    assertEquals(1L, actualDuration.longValue());
    assertEquals(Task.TaskStatus.CREATED, actualStatus);
    assertTrue(actualCandidateGroups.isEmpty());
    assertTrue(actualCandidateUsers.isEmpty());
    assertSame(candidateGroups, actualCandidateGroups);
    assertSame(candidateUsers, actualCandidateUsers);
    assertSame(claimedDate, actualClaimedDate);
    assertSame(completedDate, actualCompletedDate);
    assertSame(createdDate, actualCreatedDate);
    assertSame(dueDate, actualDueDate);
  }

  /**
   * Test {@link TaskImpl#isStandalone()}.
   * <ul>
   *   <li>Given {@link TaskImpl#TaskImpl(String, String, TaskStatus)} with id is
   * {@code 42} and {@code Name} and status is {@code CREATED}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#isStandalone()}
   */
  @Test
  @DisplayName("Test isStandalone(); given TaskImpl(String, String, TaskStatus) with id is '42' and 'Name' and status is 'CREATED'; then return 'true'")
  void testIsStandalone_givenTaskImplWithIdIs42AndNameAndStatusIsCreated_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new TaskImpl("42", "Name", Task.TaskStatus.CREATED)).isStandalone());
  }

  /**
   * Test {@link TaskImpl#isStandalone()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#isStandalone()}
   */
  @Test
  @DisplayName("Test isStandalone(); then return 'false'")
  void testIsStandalone_thenReturnFalse() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setProcessInstanceId("foo");

    // Act and Assert
    assertFalse(taskImpl.isStandalone());
  }

  /**
   * Test {@link TaskImpl#equals(Object)}, and {@link TaskImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskImpl#equals(Object)}
   *   <li>{@link TaskImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    TaskImpl taskImpl2 = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);

    // Act and Assert
    assertEquals(taskImpl, taskImpl2);
    int expectedHashCodeResult = taskImpl.hashCode();
    assertEquals(expectedHashCodeResult, taskImpl2.hashCode());
  }

  /**
   * Test {@link TaskImpl#equals(Object)}, and {@link TaskImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskImpl#equals(Object)}
   *   <li>{@link TaskImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);

    // Act and Assert
    assertEquals(taskImpl, taskImpl);
    int expectedHashCodeResult = taskImpl.hashCode();
    assertEquals(expectedHashCodeResult, taskImpl.hashCode());
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("Id", "Name", Task.TaskStatus.CREATED);

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", null, Task.TaskStatus.CREATED);

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", null);

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange, Act and Assert
    assertNotEquals(new TaskImpl("42", "Name", Task.TaskStatus.CREATED), mock(ApplicationElementImpl.class));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setOwner("Owner");

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setAssignee("Assignee");

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setDescription("The characteristics of someone or something");

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setClaimedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setDueDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setPriority(1);

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setProcessDefinitionId("42");

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setProcessInstanceId("42");

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setParentTaskId("42");

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setFormKey("Form Key");

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setCompletedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual17() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setDuration(1L);

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual18() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setProcessDefinitionVersion(1);

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual19() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setBusinessKey("Business Key");

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual20() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setTaskDefinitionKey("Task Definition Key");

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual21() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setCompletedBy("Completed By");

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual22() {
    // Arrange
    TaskImpl taskImpl = new TaskImpl("42", "Name", Task.TaskStatus.CREATED);
    taskImpl.setAppVersion("1.0.2");

    // Act and Assert
    assertNotEquals(taskImpl, new TaskImpl("42", "Name", Task.TaskStatus.CREATED));
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TaskImpl("42", "Name", Task.TaskStatus.CREATED), null);
  }

  /**
   * Test {@link TaskImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TaskImpl("42", "Name", Task.TaskStatus.CREATED), "Different type to TaskImpl");
  }
}
