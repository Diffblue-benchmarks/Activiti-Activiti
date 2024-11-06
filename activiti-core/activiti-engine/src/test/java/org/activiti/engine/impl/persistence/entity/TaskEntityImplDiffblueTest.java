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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.engine.task.DelegationState;
import org.junit.Test;

public class TaskEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskEntityImpl#forceUpdate()}
   *   <li>{@link TaskEntityImpl#setAppVersion(Integer)}
   *   <li>{@link TaskEntityImpl#setBusinessKey(String)}
   *   <li>{@link TaskEntityImpl#setCanceled(boolean)}
   *   <li>{@link TaskEntityImpl#setCategory(String)}
   *   <li>{@link TaskEntityImpl#setClaimTime(Date)}
   *   <li>{@link TaskEntityImpl#setCreateTime(Date)}
   *   <li>{@link TaskEntityImpl#setCurrentActivitiListener(ActivitiListener)}
   *   <li>{@link TaskEntityImpl#setDelegationState(DelegationState)}
   *   <li>{@link TaskEntityImpl#setDeleted(boolean)}
   *   <li>{@link TaskEntityImpl#setDescription(String)}
   *   <li>{@link TaskEntityImpl#setDueDate(Date)}
   *   <li>{@link TaskEntityImpl#setEventName(String)}
   *   <li>{@link TaskEntityImpl#setExecution(ExecutionEntity)}
   *   <li>{@link TaskEntityImpl#setExecutionId(String)}
   *   <li>{@link TaskEntityImpl#setFormKey(String)}
   *   <li>{@link TaskEntityImpl#setLocalizedDescription(String)}
   *   <li>{@link TaskEntityImpl#setLocalizedName(String)}
   *   <li>{@link TaskEntityImpl#setName(String)}
   *   <li>{@link TaskEntityImpl#setOwner(String)}
   *   <li>{@link TaskEntityImpl#setParentTaskId(String)}
   *   <li>{@link TaskEntityImpl#setPriority(int)}
   *   <li>{@link TaskEntityImpl#setProcessDefinitionId(String)}
   *   <li>{@link TaskEntityImpl#setProcessInstance(ExecutionEntity)}
   *   <li>{@link TaskEntityImpl#setProcessInstanceId(String)}
   *   <li>{@link TaskEntityImpl#setQueryVariables(List)}
   *   <li>{@link TaskEntityImpl#setRevision(int)}
   *   <li>{@link TaskEntityImpl#setSuspensionState(int)}
   *   <li>{@link TaskEntityImpl#setTaskDefinitionKey(String)}
   *   <li>{@link TaskEntityImpl#setTenantId(String)}
   *   <li>{@link TaskEntityImpl#toString()}
   *   <li>{@link TaskEntityImpl#getAppVersion()}
   *   <li>{@link TaskEntityImpl#getAssignee()}
   *   <li>{@link TaskEntityImpl#getBusinessKey()}
   *   <li>{@link TaskEntityImpl#getCategory()}
   *   <li>{@link TaskEntityImpl#getClaimTime()}
   *   <li>{@link TaskEntityImpl#getCreateTime()}
   *   <li>{@link TaskEntityImpl#getCurrentActivitiListener()}
   *   <li>{@link TaskEntityImpl#getDelegationState()}
   *   <li>{@link TaskEntityImpl#getDueDate()}
   *   <li>{@link TaskEntityImpl#getEventName()}
   *   <li>{@link TaskEntityImpl#getExecutionId()}
   *   <li>{@link TaskEntityImpl#getFormKey()}
   *   <li>{@link TaskEntityImpl#getLocalizedDescription()}
   *   <li>{@link TaskEntityImpl#getLocalizedName()}
   *   <li>{@link TaskEntityImpl#getOwner()}
   *   <li>{@link TaskEntityImpl#getParentTaskId()}
   *   <li>{@link TaskEntityImpl#getPriority()}
   *   <li>{@link TaskEntityImpl#getProcessDefinitionId()}
   *   <li>{@link TaskEntityImpl#getProcessInstanceId()}
   *   <li>{@link TaskEntityImpl#getRevision()}
   *   <li>{@link TaskEntityImpl#getSuspensionState()}
   *   <li>{@link TaskEntityImpl#getTaskDefinitionKey()}
   *   <li>{@link TaskEntityImpl#getTenantId()}
   *   <li>{@link TaskEntityImpl#isActivityIdUsedForDetails()}
   *   <li>{@link TaskEntityImpl#isCanceled()}
   *   <li>{@link TaskEntityImpl#isDeleted()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    TaskEntityImpl taskEntityImpl = new TaskEntityImpl();

    // Act
    taskEntityImpl.forceUpdate();
    taskEntityImpl.setAppVersion(1);
    taskEntityImpl.setBusinessKey("Business Key");
    taskEntityImpl.setCanceled(true);
    taskEntityImpl.setCategory("Category");
    Date claimTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    taskEntityImpl.setClaimTime(claimTime);
    Date createTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    taskEntityImpl.setCreateTime(createTime);
    ActivitiListener currentActivitiListener = new ActivitiListener();
    taskEntityImpl.setCurrentActivitiListener(currentActivitiListener);
    taskEntityImpl.setDelegationState(DelegationState.PENDING);
    taskEntityImpl.setDeleted(true);
    taskEntityImpl.setDescription("The characteristics of someone or something");
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    taskEntityImpl.setDueDate(dueDate);
    taskEntityImpl.setEventName("Event Name");
    taskEntityImpl.setExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    taskEntityImpl.setExecutionId("42");
    taskEntityImpl.setFormKey("Form Key");
    taskEntityImpl.setLocalizedDescription("Localized Description");
    taskEntityImpl.setLocalizedName("Localized Name");
    taskEntityImpl.setName("Task Name");
    taskEntityImpl.setOwner("Owner");
    taskEntityImpl.setParentTaskId("42");
    taskEntityImpl.setPriority(1);
    taskEntityImpl.setProcessDefinitionId("42");
    taskEntityImpl.setProcessInstance(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    taskEntityImpl.setProcessInstanceId("42");
    taskEntityImpl.setQueryVariables(new ArrayList<>());
    taskEntityImpl.setRevision(1);
    taskEntityImpl.setSuspensionState(1);
    taskEntityImpl.setTaskDefinitionKey("Task Definition Key");
    taskEntityImpl.setTenantId("42");
    String actualToStringResult = taskEntityImpl.toString();
    Integer actualAppVersion = taskEntityImpl.getAppVersion();
    taskEntityImpl.getAssignee();
    String actualBusinessKey = taskEntityImpl.getBusinessKey();
    String actualCategory = taskEntityImpl.getCategory();
    Date actualClaimTime = taskEntityImpl.getClaimTime();
    Date actualCreateTime = taskEntityImpl.getCreateTime();
    ActivitiListener actualCurrentActivitiListener = taskEntityImpl.getCurrentActivitiListener();
    DelegationState actualDelegationState = taskEntityImpl.getDelegationState();
    Date actualDueDate = taskEntityImpl.getDueDate();
    String actualEventName = taskEntityImpl.getEventName();
    String actualExecutionId = taskEntityImpl.getExecutionId();
    String actualFormKey = taskEntityImpl.getFormKey();
    String actualLocalizedDescription = taskEntityImpl.getLocalizedDescription();
    String actualLocalizedName = taskEntityImpl.getLocalizedName();
    String actualOwner = taskEntityImpl.getOwner();
    String actualParentTaskId = taskEntityImpl.getParentTaskId();
    int actualPriority = taskEntityImpl.getPriority();
    String actualProcessDefinitionId = taskEntityImpl.getProcessDefinitionId();
    String actualProcessInstanceId = taskEntityImpl.getProcessInstanceId();
    int actualRevision = taskEntityImpl.getRevision();
    int actualSuspensionState = taskEntityImpl.getSuspensionState();
    String actualTaskDefinitionKey = taskEntityImpl.getTaskDefinitionKey();
    String actualTenantId = taskEntityImpl.getTenantId();
    boolean actualIsActivityIdUsedForDetailsResult = taskEntityImpl.isActivityIdUsedForDetails();
    boolean actualIsCanceledResult = taskEntityImpl.isCanceled();
    boolean actualIsDeletedResult = taskEntityImpl.isDeleted();

    // Assert that nothing has changed
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualParentTaskId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("42", actualProcessInstanceId);
    assertEquals("42", actualTenantId);
    assertEquals("Business Key", actualBusinessKey);
    assertEquals("Category", actualCategory);
    assertEquals("Event Name", actualEventName);
    assertEquals("Form Key", actualFormKey);
    assertEquals("Localized Description", actualLocalizedDescription);
    assertEquals("Localized Name", actualLocalizedName);
    assertEquals("Owner", actualOwner);
    assertEquals("Task Definition Key", actualTaskDefinitionKey);
    assertEquals("Task[id=null, name=Task Name]", actualToStringResult);
    assertEquals(1, actualAppVersion.intValue());
    assertEquals(1, actualPriority);
    assertEquals(1, actualRevision);
    assertEquals(1, actualSuspensionState);
    assertEquals(DelegationState.PENDING, actualDelegationState);
    assertFalse(actualIsActivityIdUsedForDetailsResult);
    assertTrue(actualIsCanceledResult);
    assertTrue(actualIsDeletedResult);
    assertSame(currentActivitiListener, actualCurrentActivitiListener);
    assertSame(claimTime, actualClaimTime);
    assertSame(createTime, actualCreateTime);
    assertSame(dueDate, actualDueDate);
  }
}
