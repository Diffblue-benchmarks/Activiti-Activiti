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
package org.activiti.engine.impl.persistence.entity.data.impl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ExecutionTreeStringBuilderDiffblueTest {
  /**
   * Test {@link ExecutionTreeStringBuilder#ExecutionTreeStringBuilder(ExecutionEntity)}.
   * <p>
   * Method under test: {@link ExecutionTreeStringBuilder#ExecutionTreeStringBuilder(ExecutionEntity)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutionTreeStringBuilder.<init>(ExecutionEntity)"})
  public void testNewExecutionTreeStringBuilder() {
    // Arrange, Act and Assert
    ExecutionEntity executionEntity = (new ExecutionTreeStringBuilder(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections())).executionEntity;
    Object persistentState = executionEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(executionEntity instanceof ExecutionEntityImpl);
    assertEquals("", executionEntity.getTenantId());
    assertNull(((ExecutionEntityImpl) executionEntity).getCachedElContext());
    assertNull(executionEntity.getAppVersion());
    assertNull(executionEntity.getProcessDefinitionVersion());
    assertEquals(23, ((Map<Object, Object>) persistentState).size());
    assertNull(((Map<Object, Object>) persistentState).get("processDefinitionId"));
    assertNull(executionEntity.getCurrentActivityId());
    assertNull(executionEntity.getEventName());
    assertNull(executionEntity.getId());
    assertNull(executionEntity.getParentId());
    assertNull(executionEntity.getProcessDefinitionId());
    assertNull(executionEntity.getProcessInstanceId());
    assertNull(executionEntity.getRootProcessInstanceId());
    assertNull(executionEntity.getSuperExecutionId());
    assertNull(executionEntity.getDeleteReason());
    assertNull(executionEntity.getStartUserId());
    assertNull(((ExecutionEntityImpl) executionEntity).getActivityName());
    assertNull(executionEntity.getActivityId());
    assertNull(executionEntity.getDescription());
    assertNull(executionEntity.getName());
    assertNull(executionEntity.getParentProcessInstanceId());
    assertNull(executionEntity.getBusinessKey());
    assertNull(executionEntity.getDeploymentId());
    assertNull(executionEntity.getLocalizedDescription());
    assertNull(executionEntity.getLocalizedName());
    assertNull(executionEntity.getProcessDefinitionKey());
    assertNull(executionEntity.getProcessDefinitionName());
    assertNull(executionEntity.getLockTime());
    assertNull(executionEntity.getStartTime());
    assertNull(((ExecutionEntityImpl) executionEntity).getQueryVariables());
    assertNull(executionEntity.getCurrentActivitiListener());
    assertNull(executionEntity.getCurrentFlowElement());
    assertNull(executionEntity.getEngineServices());
    assertNull(executionEntity.getParent());
    assertNull(executionEntity.getProcessInstance());
    assertNull(executionEntity.getSuperExecution());
    assertEquals(0, ((Integer) ((Map<Object, Object>) persistentState).get("suspendedJobCount")).intValue());
    assertEquals(0, ((ExecutionEntityImpl) executionEntity).getDeadLetterJobCount());
    assertEquals(0, ((ExecutionEntityImpl) executionEntity).getEventSubscriptionCount());
    assertEquals(0, ((ExecutionEntityImpl) executionEntity).getIdentityLinkCount());
    assertEquals(0, ((ExecutionEntityImpl) executionEntity).getJobCount());
    assertEquals(0, ((ExecutionEntityImpl) executionEntity).getSuspendedJobCount());
    assertEquals(0, ((ExecutionEntityImpl) executionEntity).getTaskCount());
    assertEquals(0, ((ExecutionEntityImpl) executionEntity).getTimerJobCount());
    assertEquals(0, ((ExecutionEntityImpl) executionEntity).getVariableCount());
    assertEquals(1, ((Integer) ((Map<Object, Object>) persistentState).get("suspensionState")).intValue());
    assertEquals(1, executionEntity.getRevision());
    assertEquals(1, executionEntity.getSuspensionState());
    assertEquals(2, executionEntity.getRevisionNext());
    assertFalse(executionEntity.isConcurrent());
    assertFalse(executionEntity.isEnded());
    assertFalse(executionEntity.isRootExecution());
    assertFalse(executionEntity.isInserted());
    assertFalse(executionEntity.isUpdated());
    assertFalse(executionEntity.isDeleted());
    assertFalse(executionEntity.isEventScope());
    assertFalse(executionEntity.isMultiInstanceRoot());
    assertFalse(((ExecutionEntityImpl) executionEntity).isCountEnabled());
    assertFalse(executionEntity.isSuspended());
    assertTrue(executionEntity.getEventSubscriptions().isEmpty());
    assertTrue(executionEntity.getExecutions().isEmpty());
    assertTrue(executionEntity.getIdentityLinks().isEmpty());
    assertTrue(executionEntity.getJobs().isEmpty());
    assertTrue(executionEntity.getTasks().isEmpty());
    assertTrue(executionEntity.getTimerJobs().isEmpty());
    assertTrue(executionEntity.getTransientVariables().isEmpty());
    assertTrue(executionEntity.getTransientVariablesLocal().isEmpty());
    assertTrue(executionEntity.getVariableInstances().isEmpty());
    assertTrue(executionEntity.getVariableInstancesLocal().isEmpty());
    assertTrue(executionEntity.getVariables().isEmpty());
    assertTrue(executionEntity.getVariablesLocal().isEmpty());
    assertTrue(((ExecutionEntityImpl) executionEntity).getUsedVariablesCache().isEmpty());
    assertTrue(((ExecutionEntityImpl) executionEntity).getVariableInstanceEntities().isEmpty());
    assertTrue(executionEntity.getProcessVariables().isEmpty());
    assertTrue(executionEntity.getVariableNames().isEmpty());
    assertTrue(executionEntity.getVariableNamesLocal().isEmpty());
    assertTrue(executionEntity.isActive());
    assertTrue(executionEntity.isProcessInstanceType());
    assertTrue(executionEntity.isScope());
    assertTrue((Boolean) ((Map<Object, Object>) persistentState).get("isScope"));
  }
}
