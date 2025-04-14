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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.AbstractQuery.NullHandlingOnOrder;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HistoricTaskInstanceQueryImplDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return DatabaseType is {@code null}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl(CommandExecutor)}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCandidateGroup()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCandidateUser()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCategory()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCompletedAfterDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCompletedBeforeDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCompletedDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCreationAfterDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCreationBeforeDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCreationDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDeploymentId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDeploymentIds()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDueAfter()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDueBefore()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDueDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getExecutionId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getInvolvedGroups()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getInvolvedUser()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getLocale()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getOrQueryObjects()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessCategoryInList()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessCategoryNotInList()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeyLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeyLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeys()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionName()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionNameLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKeyLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKeyLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceIds()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssignee()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssigneeIds()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssigneeLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssigneeLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDefinitionKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDefinitionKeyLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDeleteReason()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDeleteReasonLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDescription()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDescriptionLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDescriptionLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskMaxPriority()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskMinPriority()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskName()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameList()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameListIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskOwner()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskOwnerLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskOwnerLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskParentTaskId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskPriority()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskVariablesLimit()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTenantId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTenantIdLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isFinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isInOrStatement()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isIncludeProcessVariables()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isIncludeTaskLocalVariables()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isProcessFinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isProcessUnfinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isUnfinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isWithoutDueDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isWithoutTenantId()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void HistoricTaskInstanceQueryImpl.<init>(CommandExecutor)",
      "void HistoricTaskInstanceQueryImpl.<init>(CommandExecutor, String)",
      "String HistoricTaskInstanceQueryImpl.getCandidateGroup()",
      "String HistoricTaskInstanceQueryImpl.getCandidateUser()", "String HistoricTaskInstanceQueryImpl.getCategory()",
      "Date HistoricTaskInstanceQueryImpl.getCompletedAfterDate()",
      "Date HistoricTaskInstanceQueryImpl.getCompletedBeforeDate()",
      "Date HistoricTaskInstanceQueryImpl.getCompletedDate()",
      "Date HistoricTaskInstanceQueryImpl.getCreationAfterDate()",
      "Date HistoricTaskInstanceQueryImpl.getCreationBeforeDate()",
      "Date HistoricTaskInstanceQueryImpl.getCreationDate()", "String HistoricTaskInstanceQueryImpl.getDeploymentId()",
      "List HistoricTaskInstanceQueryImpl.getDeploymentIds()", "Date HistoricTaskInstanceQueryImpl.getDueAfter()",
      "Date HistoricTaskInstanceQueryImpl.getDueBefore()", "Date HistoricTaskInstanceQueryImpl.getDueDate()",
      "String HistoricTaskInstanceQueryImpl.getExecutionId()", "List HistoricTaskInstanceQueryImpl.getInvolvedGroups()",
      "String HistoricTaskInstanceQueryImpl.getInvolvedUser()", "String HistoricTaskInstanceQueryImpl.getLocale()",
      "List HistoricTaskInstanceQueryImpl.getOrQueryObjects()",
      "List HistoricTaskInstanceQueryImpl.getProcessCategoryInList()",
      "List HistoricTaskInstanceQueryImpl.getProcessCategoryNotInList()",
      "String HistoricTaskInstanceQueryImpl.getProcessDefinitionId()",
      "String HistoricTaskInstanceQueryImpl.getProcessDefinitionKey()",
      "String HistoricTaskInstanceQueryImpl.getProcessDefinitionKeyLike()",
      "String HistoricTaskInstanceQueryImpl.getProcessDefinitionKeyLikeIgnoreCase()",
      "List HistoricTaskInstanceQueryImpl.getProcessDefinitionKeys()",
      "String HistoricTaskInstanceQueryImpl.getProcessDefinitionName()",
      "String HistoricTaskInstanceQueryImpl.getProcessDefinitionNameLike()",
      "String HistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKey()",
      "String HistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLike()",
      "String HistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLikeIgnoreCase()",
      "String HistoricTaskInstanceQueryImpl.getProcessInstanceId()",
      "List HistoricTaskInstanceQueryImpl.getProcessInstanceIds()",
      "String HistoricTaskInstanceQueryImpl.getTaskAssignee()",
      "List HistoricTaskInstanceQueryImpl.getTaskAssigneeIds()",
      "String HistoricTaskInstanceQueryImpl.getTaskAssigneeLike()",
      "String HistoricTaskInstanceQueryImpl.getTaskAssigneeLikeIgnoreCase()",
      "String HistoricTaskInstanceQueryImpl.getTaskDefinitionKey()",
      "String HistoricTaskInstanceQueryImpl.getTaskDefinitionKeyLike()",
      "String HistoricTaskInstanceQueryImpl.getTaskDeleteReason()",
      "String HistoricTaskInstanceQueryImpl.getTaskDeleteReasonLike()",
      "String HistoricTaskInstanceQueryImpl.getTaskDescription()",
      "String HistoricTaskInstanceQueryImpl.getTaskDescriptionLike()",
      "String HistoricTaskInstanceQueryImpl.getTaskDescriptionLikeIgnoreCase()",
      "String HistoricTaskInstanceQueryImpl.getTaskId()", "Integer HistoricTaskInstanceQueryImpl.getTaskMaxPriority()",
      "Integer HistoricTaskInstanceQueryImpl.getTaskMinPriority()",
      "String HistoricTaskInstanceQueryImpl.getTaskName()", "String HistoricTaskInstanceQueryImpl.getTaskNameLike()",
      "String HistoricTaskInstanceQueryImpl.getTaskNameLikeIgnoreCase()",
      "List HistoricTaskInstanceQueryImpl.getTaskNameList()",
      "List HistoricTaskInstanceQueryImpl.getTaskNameListIgnoreCase()",
      "String HistoricTaskInstanceQueryImpl.getTaskOwner()", "String HistoricTaskInstanceQueryImpl.getTaskOwnerLike()",
      "String HistoricTaskInstanceQueryImpl.getTaskOwnerLikeIgnoreCase()",
      "String HistoricTaskInstanceQueryImpl.getTaskParentTaskId()",
      "Integer HistoricTaskInstanceQueryImpl.getTaskPriority()",
      "Integer HistoricTaskInstanceQueryImpl.getTaskVariablesLimit()",
      "String HistoricTaskInstanceQueryImpl.getTenantId()", "String HistoricTaskInstanceQueryImpl.getTenantIdLike()",
      "boolean HistoricTaskInstanceQueryImpl.isFinished()", "boolean HistoricTaskInstanceQueryImpl.isInOrStatement()",
      "boolean HistoricTaskInstanceQueryImpl.isIncludeProcessVariables()",
      "boolean HistoricTaskInstanceQueryImpl.isIncludeTaskLocalVariables()",
      "boolean HistoricTaskInstanceQueryImpl.isProcessFinished()",
      "boolean HistoricTaskInstanceQueryImpl.isProcessUnfinished()",
      "boolean HistoricTaskInstanceQueryImpl.isUnfinished()",
      "boolean HistoricTaskInstanceQueryImpl.isWithoutDueDate()",
      "boolean HistoricTaskInstanceQueryImpl.isWithoutTenantId()"})
  public void testGettersAndSetters_thenReturnDatabaseTypeIsNull() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();

    // Act
    HistoricTaskInstanceQueryImpl actualHistoricTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl(
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    String actualCandidateGroup = actualHistoricTaskInstanceQueryImpl.getCandidateGroup();
    String actualCandidateUser = actualHistoricTaskInstanceQueryImpl.getCandidateUser();
    String actualCategory = actualHistoricTaskInstanceQueryImpl.getCategory();
    Date actualCompletedAfterDate = actualHistoricTaskInstanceQueryImpl.getCompletedAfterDate();
    Date actualCompletedBeforeDate = actualHistoricTaskInstanceQueryImpl.getCompletedBeforeDate();
    Date actualCompletedDate = actualHistoricTaskInstanceQueryImpl.getCompletedDate();
    Date actualCreationAfterDate = actualHistoricTaskInstanceQueryImpl.getCreationAfterDate();
    Date actualCreationBeforeDate = actualHistoricTaskInstanceQueryImpl.getCreationBeforeDate();
    Date actualCreationDate = actualHistoricTaskInstanceQueryImpl.getCreationDate();
    String actualDeploymentId = actualHistoricTaskInstanceQueryImpl.getDeploymentId();
    List<String> actualDeploymentIds = actualHistoricTaskInstanceQueryImpl.getDeploymentIds();
    Date actualDueAfter = actualHistoricTaskInstanceQueryImpl.getDueAfter();
    Date actualDueBefore = actualHistoricTaskInstanceQueryImpl.getDueBefore();
    Date actualDueDate = actualHistoricTaskInstanceQueryImpl.getDueDate();
    String actualExecutionId = actualHistoricTaskInstanceQueryImpl.getExecutionId();
    List<String> actualInvolvedGroups = actualHistoricTaskInstanceQueryImpl.getInvolvedGroups();
    String actualInvolvedUser = actualHistoricTaskInstanceQueryImpl.getInvolvedUser();
    String actualLocale = actualHistoricTaskInstanceQueryImpl.getLocale();
    List<HistoricTaskInstanceQueryImpl> actualOrQueryObjects = actualHistoricTaskInstanceQueryImpl.getOrQueryObjects();
    List<String> actualProcessCategoryInList = actualHistoricTaskInstanceQueryImpl.getProcessCategoryInList();
    List<String> actualProcessCategoryNotInList = actualHistoricTaskInstanceQueryImpl.getProcessCategoryNotInList();
    String actualProcessDefinitionId = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionId();
    String actualProcessDefinitionKey = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKey();
    String actualProcessDefinitionKeyLike = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeyLike();
    String actualProcessDefinitionKeyLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl
        .getProcessDefinitionKeyLikeIgnoreCase();
    List<String> actualProcessDefinitionKeys = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeys();
    String actualProcessDefinitionName = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionName();
    String actualProcessDefinitionNameLike = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionNameLike();
    String actualProcessInstanceBusinessKey = actualHistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKey();
    String actualProcessInstanceBusinessKeyLike = actualHistoricTaskInstanceQueryImpl
        .getProcessInstanceBusinessKeyLike();
    String actualProcessInstanceBusinessKeyLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl
        .getProcessInstanceBusinessKeyLikeIgnoreCase();
    String actualProcessInstanceId = actualHistoricTaskInstanceQueryImpl.getProcessInstanceId();
    List<String> actualProcessInstanceIds = actualHistoricTaskInstanceQueryImpl.getProcessInstanceIds();
    String actualTaskAssignee = actualHistoricTaskInstanceQueryImpl.getTaskAssignee();
    List<String> actualTaskAssigneeIds = actualHistoricTaskInstanceQueryImpl.getTaskAssigneeIds();
    String actualTaskAssigneeLike = actualHistoricTaskInstanceQueryImpl.getTaskAssigneeLike();
    String actualTaskAssigneeLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskAssigneeLikeIgnoreCase();
    String actualTaskDefinitionKey = actualHistoricTaskInstanceQueryImpl.getTaskDefinitionKey();
    String actualTaskDefinitionKeyLike = actualHistoricTaskInstanceQueryImpl.getTaskDefinitionKeyLike();
    String actualTaskDeleteReason = actualHistoricTaskInstanceQueryImpl.getTaskDeleteReason();
    String actualTaskDeleteReasonLike = actualHistoricTaskInstanceQueryImpl.getTaskDeleteReasonLike();
    String actualTaskDescription = actualHistoricTaskInstanceQueryImpl.getTaskDescription();
    String actualTaskDescriptionLike = actualHistoricTaskInstanceQueryImpl.getTaskDescriptionLike();
    String actualTaskDescriptionLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskDescriptionLikeIgnoreCase();
    String actualTaskId = actualHistoricTaskInstanceQueryImpl.getTaskId();
    Integer actualTaskMaxPriority = actualHistoricTaskInstanceQueryImpl.getTaskMaxPriority();
    Integer actualTaskMinPriority = actualHistoricTaskInstanceQueryImpl.getTaskMinPriority();
    String actualTaskName = actualHistoricTaskInstanceQueryImpl.getTaskName();
    String actualTaskNameLike = actualHistoricTaskInstanceQueryImpl.getTaskNameLike();
    String actualTaskNameLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskNameLikeIgnoreCase();
    List<String> actualTaskNameList = actualHistoricTaskInstanceQueryImpl.getTaskNameList();
    List<String> actualTaskNameListIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskNameListIgnoreCase();
    String actualTaskOwner = actualHistoricTaskInstanceQueryImpl.getTaskOwner();
    String actualTaskOwnerLike = actualHistoricTaskInstanceQueryImpl.getTaskOwnerLike();
    String actualTaskOwnerLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskOwnerLikeIgnoreCase();
    String actualTaskParentTaskId = actualHistoricTaskInstanceQueryImpl.getTaskParentTaskId();
    Integer actualTaskPriority = actualHistoricTaskInstanceQueryImpl.getTaskPriority();
    Integer actualTaskVariablesLimit = actualHistoricTaskInstanceQueryImpl.getTaskVariablesLimit();
    String actualTenantId = actualHistoricTaskInstanceQueryImpl.getTenantId();
    String actualTenantIdLike = actualHistoricTaskInstanceQueryImpl.getTenantIdLike();
    boolean actualIsFinishedResult = actualHistoricTaskInstanceQueryImpl.isFinished();
    boolean actualIsInOrStatementResult = actualHistoricTaskInstanceQueryImpl.isInOrStatement();
    boolean actualIsIncludeProcessVariablesResult = actualHistoricTaskInstanceQueryImpl.isIncludeProcessVariables();
    boolean actualIsIncludeTaskLocalVariablesResult = actualHistoricTaskInstanceQueryImpl.isIncludeTaskLocalVariables();
    boolean actualIsProcessFinishedResult = actualHistoricTaskInstanceQueryImpl.isProcessFinished();
    boolean actualIsProcessUnfinishedResult = actualHistoricTaskInstanceQueryImpl.isProcessUnfinished();
    boolean actualIsUnfinishedResult = actualHistoricTaskInstanceQueryImpl.isUnfinished();
    boolean actualIsWithoutDueDateResult = actualHistoricTaskInstanceQueryImpl.isWithoutDueDate();
    boolean actualIsWithoutTenantIdResult = actualHistoricTaskInstanceQueryImpl.isWithoutTenantId();

    // Assert
    assertNull(actualTaskMaxPriority);
    assertNull(actualTaskMinPriority);
    assertNull(actualTaskPriority);
    assertNull(actualTaskVariablesLimit);
    assertNull(actualHistoricTaskInstanceQueryImpl.getParameter());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDatabaseType());
    assertNull(actualCandidateGroup);
    assertNull(actualCandidateUser);
    assertNull(actualCategory);
    assertNull(actualDeploymentId);
    assertNull(actualExecutionId);
    assertNull(actualInvolvedUser);
    assertNull(actualLocale);
    assertNull(actualProcessDefinitionId);
    assertNull(actualProcessDefinitionKey);
    assertNull(actualProcessDefinitionKeyLike);
    assertNull(actualProcessDefinitionKeyLikeIgnoreCase);
    assertNull(actualProcessDefinitionName);
    assertNull(actualProcessDefinitionNameLike);
    assertNull(actualProcessInstanceBusinessKey);
    assertNull(actualProcessInstanceBusinessKeyLike);
    assertNull(actualProcessInstanceBusinessKeyLikeIgnoreCase);
    assertNull(actualProcessInstanceId);
    assertNull(actualTaskAssignee);
    assertNull(actualTaskAssigneeLike);
    assertNull(actualTaskAssigneeLikeIgnoreCase);
    assertNull(actualTaskDefinitionKey);
    assertNull(actualTaskDefinitionKeyLike);
    assertNull(actualTaskDeleteReason);
    assertNull(actualTaskDeleteReasonLike);
    assertNull(actualTaskDescription);
    assertNull(actualTaskDescriptionLike);
    assertNull(actualTaskDescriptionLikeIgnoreCase);
    assertNull(actualTaskId);
    assertNull(actualTaskName);
    assertNull(actualTaskNameLike);
    assertNull(actualTaskNameLikeIgnoreCase);
    assertNull(actualTaskOwner);
    assertNull(actualTaskOwnerLike);
    assertNull(actualTaskOwnerLikeIgnoreCase);
    assertNull(actualTaskParentTaskId);
    assertNull(actualTenantId);
    assertNull(actualTenantIdLike);
    assertNull(actualCompletedAfterDate);
    assertNull(actualCompletedBeforeDate);
    assertNull(actualCompletedDate);
    assertNull(actualCreationAfterDate);
    assertNull(actualCreationBeforeDate);
    assertNull(actualCreationDate);
    assertNull(actualDueAfter);
    assertNull(actualDueBefore);
    assertNull(actualDueDate);
    assertNull(actualDeploymentIds);
    assertNull(actualInvolvedGroups);
    assertNull(actualProcessCategoryInList);
    assertNull(actualProcessCategoryNotInList);
    assertNull(actualProcessDefinitionKeys);
    assertNull(actualProcessInstanceIds);
    assertNull(actualTaskAssigneeIds);
    assertNull(actualTaskNameList);
    assertNull(actualTaskNameListIgnoreCase);
    assertEquals(0, actualHistoricTaskInstanceQueryImpl.getFirstResult());
    assertFalse(actualIsFinishedResult);
    assertFalse(actualIsInOrStatementResult);
    assertFalse(actualIsIncludeProcessVariablesResult);
    assertFalse(actualIsIncludeTaskLocalVariablesResult);
    assertFalse(actualIsProcessFinishedResult);
    assertFalse(actualIsProcessUnfinishedResult);
    assertFalse(actualIsUnfinishedResult);
    assertFalse(actualIsWithoutDueDateResult);
    assertFalse(actualIsWithoutTenantIdResult);
    assertTrue(actualHistoricTaskInstanceQueryImpl.getQueryVariableValues().isEmpty());
    assertTrue(actualOrQueryObjects.isEmpty());
    assertEquals(Integer.MAX_VALUE, actualHistoricTaskInstanceQueryImpl.getMaxResults());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Database Type}.</li>
   *   <li>Then return {@code Database Type}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl(CommandExecutor, String)}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCandidateGroup()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCandidateUser()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCategory()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCompletedAfterDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCompletedBeforeDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCompletedDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCreationAfterDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCreationBeforeDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCreationDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDeploymentId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDeploymentIds()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDueAfter()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDueBefore()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDueDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getExecutionId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getInvolvedGroups()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getInvolvedUser()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getLocale()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getOrQueryObjects()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessCategoryInList()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessCategoryNotInList()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeyLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeyLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeys()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionName()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionNameLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKeyLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKeyLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceIds()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssignee()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssigneeIds()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssigneeLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssigneeLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDefinitionKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDefinitionKeyLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDeleteReason()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDeleteReasonLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDescription()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDescriptionLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDescriptionLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskMaxPriority()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskMinPriority()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskName()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameList()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameListIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskOwner()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskOwnerLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskOwnerLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskParentTaskId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskPriority()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskVariablesLimit()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTenantId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTenantIdLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isFinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isInOrStatement()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isIncludeProcessVariables()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isIncludeTaskLocalVariables()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isProcessFinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isProcessUnfinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isUnfinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isWithoutDueDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isWithoutTenantId()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void HistoricTaskInstanceQueryImpl.<init>(CommandExecutor)",
      "void HistoricTaskInstanceQueryImpl.<init>(CommandExecutor, String)",
      "String HistoricTaskInstanceQueryImpl.getCandidateGroup()",
      "String HistoricTaskInstanceQueryImpl.getCandidateUser()", "String HistoricTaskInstanceQueryImpl.getCategory()",
      "Date HistoricTaskInstanceQueryImpl.getCompletedAfterDate()",
      "Date HistoricTaskInstanceQueryImpl.getCompletedBeforeDate()",
      "Date HistoricTaskInstanceQueryImpl.getCompletedDate()",
      "Date HistoricTaskInstanceQueryImpl.getCreationAfterDate()",
      "Date HistoricTaskInstanceQueryImpl.getCreationBeforeDate()",
      "Date HistoricTaskInstanceQueryImpl.getCreationDate()", "String HistoricTaskInstanceQueryImpl.getDeploymentId()",
      "List HistoricTaskInstanceQueryImpl.getDeploymentIds()", "Date HistoricTaskInstanceQueryImpl.getDueAfter()",
      "Date HistoricTaskInstanceQueryImpl.getDueBefore()", "Date HistoricTaskInstanceQueryImpl.getDueDate()",
      "String HistoricTaskInstanceQueryImpl.getExecutionId()", "List HistoricTaskInstanceQueryImpl.getInvolvedGroups()",
      "String HistoricTaskInstanceQueryImpl.getInvolvedUser()", "String HistoricTaskInstanceQueryImpl.getLocale()",
      "List HistoricTaskInstanceQueryImpl.getOrQueryObjects()",
      "List HistoricTaskInstanceQueryImpl.getProcessCategoryInList()",
      "List HistoricTaskInstanceQueryImpl.getProcessCategoryNotInList()",
      "String HistoricTaskInstanceQueryImpl.getProcessDefinitionId()",
      "String HistoricTaskInstanceQueryImpl.getProcessDefinitionKey()",
      "String HistoricTaskInstanceQueryImpl.getProcessDefinitionKeyLike()",
      "String HistoricTaskInstanceQueryImpl.getProcessDefinitionKeyLikeIgnoreCase()",
      "List HistoricTaskInstanceQueryImpl.getProcessDefinitionKeys()",
      "String HistoricTaskInstanceQueryImpl.getProcessDefinitionName()",
      "String HistoricTaskInstanceQueryImpl.getProcessDefinitionNameLike()",
      "String HistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKey()",
      "String HistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLike()",
      "String HistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLikeIgnoreCase()",
      "String HistoricTaskInstanceQueryImpl.getProcessInstanceId()",
      "List HistoricTaskInstanceQueryImpl.getProcessInstanceIds()",
      "String HistoricTaskInstanceQueryImpl.getTaskAssignee()",
      "List HistoricTaskInstanceQueryImpl.getTaskAssigneeIds()",
      "String HistoricTaskInstanceQueryImpl.getTaskAssigneeLike()",
      "String HistoricTaskInstanceQueryImpl.getTaskAssigneeLikeIgnoreCase()",
      "String HistoricTaskInstanceQueryImpl.getTaskDefinitionKey()",
      "String HistoricTaskInstanceQueryImpl.getTaskDefinitionKeyLike()",
      "String HistoricTaskInstanceQueryImpl.getTaskDeleteReason()",
      "String HistoricTaskInstanceQueryImpl.getTaskDeleteReasonLike()",
      "String HistoricTaskInstanceQueryImpl.getTaskDescription()",
      "String HistoricTaskInstanceQueryImpl.getTaskDescriptionLike()",
      "String HistoricTaskInstanceQueryImpl.getTaskDescriptionLikeIgnoreCase()",
      "String HistoricTaskInstanceQueryImpl.getTaskId()", "Integer HistoricTaskInstanceQueryImpl.getTaskMaxPriority()",
      "Integer HistoricTaskInstanceQueryImpl.getTaskMinPriority()",
      "String HistoricTaskInstanceQueryImpl.getTaskName()", "String HistoricTaskInstanceQueryImpl.getTaskNameLike()",
      "String HistoricTaskInstanceQueryImpl.getTaskNameLikeIgnoreCase()",
      "List HistoricTaskInstanceQueryImpl.getTaskNameList()",
      "List HistoricTaskInstanceQueryImpl.getTaskNameListIgnoreCase()",
      "String HistoricTaskInstanceQueryImpl.getTaskOwner()", "String HistoricTaskInstanceQueryImpl.getTaskOwnerLike()",
      "String HistoricTaskInstanceQueryImpl.getTaskOwnerLikeIgnoreCase()",
      "String HistoricTaskInstanceQueryImpl.getTaskParentTaskId()",
      "Integer HistoricTaskInstanceQueryImpl.getTaskPriority()",
      "Integer HistoricTaskInstanceQueryImpl.getTaskVariablesLimit()",
      "String HistoricTaskInstanceQueryImpl.getTenantId()", "String HistoricTaskInstanceQueryImpl.getTenantIdLike()",
      "boolean HistoricTaskInstanceQueryImpl.isFinished()", "boolean HistoricTaskInstanceQueryImpl.isInOrStatement()",
      "boolean HistoricTaskInstanceQueryImpl.isIncludeProcessVariables()",
      "boolean HistoricTaskInstanceQueryImpl.isIncludeTaskLocalVariables()",
      "boolean HistoricTaskInstanceQueryImpl.isProcessFinished()",
      "boolean HistoricTaskInstanceQueryImpl.isProcessUnfinished()",
      "boolean HistoricTaskInstanceQueryImpl.isUnfinished()",
      "boolean HistoricTaskInstanceQueryImpl.isWithoutDueDate()",
      "boolean HistoricTaskInstanceQueryImpl.isWithoutTenantId()"})
  public void testGettersAndSetters_whenDatabaseType_thenReturnDatabaseType() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();

    // Act
    HistoricTaskInstanceQueryImpl actualHistoricTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl(
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()), "Database Type");
    String actualCandidateGroup = actualHistoricTaskInstanceQueryImpl.getCandidateGroup();
    String actualCandidateUser = actualHistoricTaskInstanceQueryImpl.getCandidateUser();
    String actualCategory = actualHistoricTaskInstanceQueryImpl.getCategory();
    Date actualCompletedAfterDate = actualHistoricTaskInstanceQueryImpl.getCompletedAfterDate();
    Date actualCompletedBeforeDate = actualHistoricTaskInstanceQueryImpl.getCompletedBeforeDate();
    Date actualCompletedDate = actualHistoricTaskInstanceQueryImpl.getCompletedDate();
    Date actualCreationAfterDate = actualHistoricTaskInstanceQueryImpl.getCreationAfterDate();
    Date actualCreationBeforeDate = actualHistoricTaskInstanceQueryImpl.getCreationBeforeDate();
    Date actualCreationDate = actualHistoricTaskInstanceQueryImpl.getCreationDate();
    String actualDeploymentId = actualHistoricTaskInstanceQueryImpl.getDeploymentId();
    List<String> actualDeploymentIds = actualHistoricTaskInstanceQueryImpl.getDeploymentIds();
    Date actualDueAfter = actualHistoricTaskInstanceQueryImpl.getDueAfter();
    Date actualDueBefore = actualHistoricTaskInstanceQueryImpl.getDueBefore();
    Date actualDueDate = actualHistoricTaskInstanceQueryImpl.getDueDate();
    String actualExecutionId = actualHistoricTaskInstanceQueryImpl.getExecutionId();
    List<String> actualInvolvedGroups = actualHistoricTaskInstanceQueryImpl.getInvolvedGroups();
    String actualInvolvedUser = actualHistoricTaskInstanceQueryImpl.getInvolvedUser();
    String actualLocale = actualHistoricTaskInstanceQueryImpl.getLocale();
    List<HistoricTaskInstanceQueryImpl> actualOrQueryObjects = actualHistoricTaskInstanceQueryImpl.getOrQueryObjects();
    List<String> actualProcessCategoryInList = actualHistoricTaskInstanceQueryImpl.getProcessCategoryInList();
    List<String> actualProcessCategoryNotInList = actualHistoricTaskInstanceQueryImpl.getProcessCategoryNotInList();
    String actualProcessDefinitionId = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionId();
    String actualProcessDefinitionKey = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKey();
    String actualProcessDefinitionKeyLike = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeyLike();
    String actualProcessDefinitionKeyLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl
        .getProcessDefinitionKeyLikeIgnoreCase();
    List<String> actualProcessDefinitionKeys = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeys();
    String actualProcessDefinitionName = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionName();
    String actualProcessDefinitionNameLike = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionNameLike();
    String actualProcessInstanceBusinessKey = actualHistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKey();
    String actualProcessInstanceBusinessKeyLike = actualHistoricTaskInstanceQueryImpl
        .getProcessInstanceBusinessKeyLike();
    String actualProcessInstanceBusinessKeyLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl
        .getProcessInstanceBusinessKeyLikeIgnoreCase();
    String actualProcessInstanceId = actualHistoricTaskInstanceQueryImpl.getProcessInstanceId();
    List<String> actualProcessInstanceIds = actualHistoricTaskInstanceQueryImpl.getProcessInstanceIds();
    String actualTaskAssignee = actualHistoricTaskInstanceQueryImpl.getTaskAssignee();
    List<String> actualTaskAssigneeIds = actualHistoricTaskInstanceQueryImpl.getTaskAssigneeIds();
    String actualTaskAssigneeLike = actualHistoricTaskInstanceQueryImpl.getTaskAssigneeLike();
    String actualTaskAssigneeLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskAssigneeLikeIgnoreCase();
    String actualTaskDefinitionKey = actualHistoricTaskInstanceQueryImpl.getTaskDefinitionKey();
    String actualTaskDefinitionKeyLike = actualHistoricTaskInstanceQueryImpl.getTaskDefinitionKeyLike();
    String actualTaskDeleteReason = actualHistoricTaskInstanceQueryImpl.getTaskDeleteReason();
    String actualTaskDeleteReasonLike = actualHistoricTaskInstanceQueryImpl.getTaskDeleteReasonLike();
    String actualTaskDescription = actualHistoricTaskInstanceQueryImpl.getTaskDescription();
    String actualTaskDescriptionLike = actualHistoricTaskInstanceQueryImpl.getTaskDescriptionLike();
    String actualTaskDescriptionLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskDescriptionLikeIgnoreCase();
    String actualTaskId = actualHistoricTaskInstanceQueryImpl.getTaskId();
    Integer actualTaskMaxPriority = actualHistoricTaskInstanceQueryImpl.getTaskMaxPriority();
    Integer actualTaskMinPriority = actualHistoricTaskInstanceQueryImpl.getTaskMinPriority();
    String actualTaskName = actualHistoricTaskInstanceQueryImpl.getTaskName();
    String actualTaskNameLike = actualHistoricTaskInstanceQueryImpl.getTaskNameLike();
    String actualTaskNameLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskNameLikeIgnoreCase();
    List<String> actualTaskNameList = actualHistoricTaskInstanceQueryImpl.getTaskNameList();
    List<String> actualTaskNameListIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskNameListIgnoreCase();
    String actualTaskOwner = actualHistoricTaskInstanceQueryImpl.getTaskOwner();
    String actualTaskOwnerLike = actualHistoricTaskInstanceQueryImpl.getTaskOwnerLike();
    String actualTaskOwnerLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskOwnerLikeIgnoreCase();
    String actualTaskParentTaskId = actualHistoricTaskInstanceQueryImpl.getTaskParentTaskId();
    Integer actualTaskPriority = actualHistoricTaskInstanceQueryImpl.getTaskPriority();
    Integer actualTaskVariablesLimit = actualHistoricTaskInstanceQueryImpl.getTaskVariablesLimit();
    String actualTenantId = actualHistoricTaskInstanceQueryImpl.getTenantId();
    String actualTenantIdLike = actualHistoricTaskInstanceQueryImpl.getTenantIdLike();
    boolean actualIsFinishedResult = actualHistoricTaskInstanceQueryImpl.isFinished();
    boolean actualIsInOrStatementResult = actualHistoricTaskInstanceQueryImpl.isInOrStatement();
    boolean actualIsIncludeProcessVariablesResult = actualHistoricTaskInstanceQueryImpl.isIncludeProcessVariables();
    boolean actualIsIncludeTaskLocalVariablesResult = actualHistoricTaskInstanceQueryImpl.isIncludeTaskLocalVariables();
    boolean actualIsProcessFinishedResult = actualHistoricTaskInstanceQueryImpl.isProcessFinished();
    boolean actualIsProcessUnfinishedResult = actualHistoricTaskInstanceQueryImpl.isProcessUnfinished();
    boolean actualIsUnfinishedResult = actualHistoricTaskInstanceQueryImpl.isUnfinished();
    boolean actualIsWithoutDueDateResult = actualHistoricTaskInstanceQueryImpl.isWithoutDueDate();
    boolean actualIsWithoutTenantIdResult = actualHistoricTaskInstanceQueryImpl.isWithoutTenantId();

    // Assert
    assertEquals("Database Type", actualHistoricTaskInstanceQueryImpl.getDatabaseType());
    assertNull(actualTaskMaxPriority);
    assertNull(actualTaskMinPriority);
    assertNull(actualTaskPriority);
    assertNull(actualTaskVariablesLimit);
    assertNull(actualHistoricTaskInstanceQueryImpl.getParameter());
    assertNull(actualCandidateGroup);
    assertNull(actualCandidateUser);
    assertNull(actualCategory);
    assertNull(actualDeploymentId);
    assertNull(actualExecutionId);
    assertNull(actualInvolvedUser);
    assertNull(actualLocale);
    assertNull(actualProcessDefinitionId);
    assertNull(actualProcessDefinitionKey);
    assertNull(actualProcessDefinitionKeyLike);
    assertNull(actualProcessDefinitionKeyLikeIgnoreCase);
    assertNull(actualProcessDefinitionName);
    assertNull(actualProcessDefinitionNameLike);
    assertNull(actualProcessInstanceBusinessKey);
    assertNull(actualProcessInstanceBusinessKeyLike);
    assertNull(actualProcessInstanceBusinessKeyLikeIgnoreCase);
    assertNull(actualProcessInstanceId);
    assertNull(actualTaskAssignee);
    assertNull(actualTaskAssigneeLike);
    assertNull(actualTaskAssigneeLikeIgnoreCase);
    assertNull(actualTaskDefinitionKey);
    assertNull(actualTaskDefinitionKeyLike);
    assertNull(actualTaskDeleteReason);
    assertNull(actualTaskDeleteReasonLike);
    assertNull(actualTaskDescription);
    assertNull(actualTaskDescriptionLike);
    assertNull(actualTaskDescriptionLikeIgnoreCase);
    assertNull(actualTaskId);
    assertNull(actualTaskName);
    assertNull(actualTaskNameLike);
    assertNull(actualTaskNameLikeIgnoreCase);
    assertNull(actualTaskOwner);
    assertNull(actualTaskOwnerLike);
    assertNull(actualTaskOwnerLikeIgnoreCase);
    assertNull(actualTaskParentTaskId);
    assertNull(actualTenantId);
    assertNull(actualTenantIdLike);
    assertNull(actualCompletedAfterDate);
    assertNull(actualCompletedBeforeDate);
    assertNull(actualCompletedDate);
    assertNull(actualCreationAfterDate);
    assertNull(actualCreationBeforeDate);
    assertNull(actualCreationDate);
    assertNull(actualDueAfter);
    assertNull(actualDueBefore);
    assertNull(actualDueDate);
    assertNull(actualDeploymentIds);
    assertNull(actualInvolvedGroups);
    assertNull(actualProcessCategoryInList);
    assertNull(actualProcessCategoryNotInList);
    assertNull(actualProcessDefinitionKeys);
    assertNull(actualProcessInstanceIds);
    assertNull(actualTaskAssigneeIds);
    assertNull(actualTaskNameList);
    assertNull(actualTaskNameListIgnoreCase);
    assertEquals(0, actualHistoricTaskInstanceQueryImpl.getFirstResult());
    assertFalse(actualIsFinishedResult);
    assertFalse(actualIsInOrStatementResult);
    assertFalse(actualIsIncludeProcessVariablesResult);
    assertFalse(actualIsIncludeTaskLocalVariablesResult);
    assertFalse(actualIsProcessFinishedResult);
    assertFalse(actualIsProcessUnfinishedResult);
    assertFalse(actualIsUnfinishedResult);
    assertFalse(actualIsWithoutDueDateResult);
    assertFalse(actualIsWithoutTenantIdResult);
    assertTrue(actualHistoricTaskInstanceQueryImpl.getQueryVariableValues().isEmpty());
    assertTrue(actualOrQueryObjects.isEmpty());
    assertEquals(Integer.MAX_VALUE, actualHistoricTaskInstanceQueryImpl.getMaxResults());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void HistoricTaskInstanceQueryImpl.<init>()"})
  public void testNewHistoricTaskInstanceQueryImpl() {
    // Arrange and Act
    HistoricTaskInstanceQueryImpl actualHistoricTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualHistoricTaskInstanceQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualHistoricTaskInstanceQueryImpl.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", actualHistoricTaskInstanceQueryImpl.getMssqlOrDB2OrderBy());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskMaxPriority());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskMinPriority());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskPriority());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskVariablesLimit());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDatabaseType());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCandidateGroup());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCandidateUser());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCategory());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDeploymentId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getExecutionId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getInvolvedUser());
    assertNull(actualHistoricTaskInstanceQueryImpl.getLocale());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKey());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeyLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionName());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionNameLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKey());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessInstanceId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskAssignee());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskAssigneeLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskAssigneeLikeIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDefinitionKey());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDefinitionKeyLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDeleteReason());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDeleteReasonLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDescription());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDescriptionLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDescriptionLikeIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskName());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskNameLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskNameLikeIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskOwner());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskOwnerLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskOwnerLikeIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskParentTaskId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTenantId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTenantIdLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.orderBy);
    assertNull(actualHistoricTaskInstanceQueryImpl.getCompletedAfterDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCompletedBeforeDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCompletedDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCreationAfterDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCreationBeforeDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCreationDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDueAfter());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDueBefore());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDueDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCandidateGroups());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDeploymentIds());
    assertNull(actualHistoricTaskInstanceQueryImpl.getInvolvedGroups());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessCategoryInList());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessCategoryNotInList());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeys());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessInstanceIds());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskAssigneeIds());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskNameList());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskNameListIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.nullHandlingOnOrder);
    assertNull(actualHistoricTaskInstanceQueryImpl.resultType);
    assertNull(actualHistoricTaskInstanceQueryImpl.currentOrQueryObject);
    assertNull(actualHistoricTaskInstanceQueryImpl.commandContext);
    assertNull(actualHistoricTaskInstanceQueryImpl.commandExecutor);
    assertNull(actualHistoricTaskInstanceQueryImpl.orderProperty);
    assertEquals(0, actualHistoricTaskInstanceQueryImpl.getFirstResult());
    assertEquals(1, actualHistoricTaskInstanceQueryImpl.getFirstRow());
    assertFalse(actualHistoricTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertFalse(actualHistoricTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isFinished());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isInOrStatement());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isIncludeProcessVariables());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isIncludeTaskLocalVariables());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isProcessFinished());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isProcessUnfinished());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isUnfinished());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isWithoutDueDate());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isWithoutTenantId());
    assertFalse(actualHistoricTaskInstanceQueryImpl.withLocalizationFallback);
    assertTrue(actualHistoricTaskInstanceQueryImpl.getQueryVariableValues().isEmpty());
    assertTrue(actualHistoricTaskInstanceQueryImpl.getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE, actualHistoricTaskInstanceQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualHistoricTaskInstanceQueryImpl.getMaxResults());
    assertSame(actualHistoricTaskInstanceQueryImpl, actualHistoricTaskInstanceQueryImpl.getParameter());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processInstanceId(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQueryImpl HistoricTaskInstanceQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceIdResult = historicTaskInstanceQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getProcessInstanceId());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQueryImpl HistoricTaskInstanceQueryImpl.processInstanceIdIn(List)"})
  public void testProcessInstanceIdIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processInstanceIds = new ArrayList<>();
    processInstanceIds.add("Process instance id list is empty");

    // Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceIdInResult = historicTaskInstanceQueryImpl
        .processInstanceIdIn(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, historicTaskInstanceQueryImpl.getProcessInstanceIds());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceIdInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQueryImpl HistoricTaskInstanceQueryImpl.processInstanceIdIn(List)"})
  public void testProcessInstanceIdIn_given42_whenArrayListAdd42() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processInstanceIds = new ArrayList<>();
    processInstanceIds.add("42");
    processInstanceIds.add("Process instance id list is empty");

    // Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceIdInResult = historicTaskInstanceQueryImpl
        .processInstanceIdIn(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, historicTaskInstanceQueryImpl.getProcessInstanceIds());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceIdInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQueryImpl HistoricTaskInstanceQueryImpl.processInstanceIdIn(List)"})
  public void testProcessInstanceIdIn_givenNull_whenArrayListAddNull() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processInstanceIds = new ArrayList<>();
    processInstanceIds.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processInstanceIdIn(processInstanceIds));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQueryImpl HistoricTaskInstanceQueryImpl.processInstanceIdIn(List)"})
  public void testProcessInstanceIdIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processInstanceIdIn(new ArrayList<>()));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKey(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQueryImpl HistoricTaskInstanceQueryImpl.processInstanceBusinessKey(String)"})
  public void testProcessInstanceBusinessKey() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceBusinessKeyResult = historicTaskInstanceQueryImpl
        .processInstanceBusinessKey("Process Instance Business Key");

    // Assert
    assertEquals("Process Instance Business Key", historicTaskInstanceQueryImpl.getProcessInstanceBusinessKey());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKeyLike(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKeyLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQueryImpl HistoricTaskInstanceQueryImpl.processInstanceBusinessKeyLike(String)"})
  public void testProcessInstanceBusinessKeyLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceBusinessKeyLikeResult = historicTaskInstanceQueryImpl
        .processInstanceBusinessKeyLike("Process Instance Business Key Like");

    // Assert
    assertEquals("Process Instance Business Key Like",
        historicTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLike());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceBusinessKeyLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKeyLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKeyLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processInstanceBusinessKeyLikeIgnoreCase(String)"})
  public void testProcessInstanceBusinessKeyLikeIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessInstanceBusinessKeyLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processInstanceBusinessKeyLikeIgnoreCase("Process Instance Business Key Like Ignore Case");

    // Assert
    assertEquals("process instance business key like ignore case",
        historicTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceBusinessKeyLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#executionId(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#executionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQueryImpl HistoricTaskInstanceQueryImpl.executionId(String)"})
  public void testExecutionId() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQueryImpl actualExecutionIdResult = historicTaskInstanceQueryImpl.executionId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getExecutionId());
    assertSame(historicTaskInstanceQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionId(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQueryImpl HistoricTaskInstanceQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQueryImpl actualProcessDefinitionIdResult = historicTaskInstanceQueryImpl
        .processDefinitionId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getProcessDefinitionId());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionKey(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processDefinitionKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processDefinitionKey(String)"})
  public void testProcessDefinitionKey() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyResult = historicTaskInstanceQueryImpl
        .processDefinitionKey("Process Definition Key");

    // Assert
    assertEquals("Process Definition Key", historicTaskInstanceQueryImpl.getProcessDefinitionKey());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyLike(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processDefinitionKeyLike(String)"})
  public void testProcessDefinitionKeyLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyLikeResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyLike("Process Definition Key Like");

    // Assert
    assertEquals("Process Definition Key Like", historicTaskInstanceQueryImpl.getProcessDefinitionKeyLike());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processDefinitionKeyLikeIgnoreCase(String)"})
  public void testProcessDefinitionKeyLikeIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyLikeIgnoreCase("Process Definition Key Like Ignore Case");

    // Assert
    assertEquals("process definition key like ignore case",
        historicTaskInstanceQueryImpl.getProcessDefinitionKeyLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processDefinitionKeyIn(List)"})
  public void testProcessDefinitionKeyIn_given42_whenArrayListAdd42() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processDefinitionKeys = new ArrayList<>();
    processDefinitionKeys.add("42");
    processDefinitionKeys.add("foo");

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyInResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicTaskInstanceQueryImpl.getProcessDefinitionKeys());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processDefinitionKeyIn(List)"})
  public void testProcessDefinitionKeyIn_givenFoo_whenArrayListAddFoo() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processDefinitionKeys = new ArrayList<>();
    processDefinitionKeys.add("foo");

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyInResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicTaskInstanceQueryImpl.getProcessDefinitionKeys());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processDefinitionKeyIn(List)"})
  public void testProcessDefinitionKeyIn_whenArrayList() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    ArrayList<String> processDefinitionKeys = new ArrayList<>();

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyInResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicTaskInstanceQueryImpl.getProcessDefinitionKeys());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionName(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processDefinitionName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processDefinitionName(String)"})
  public void testProcessDefinitionName() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionNameResult = historicTaskInstanceQueryImpl
        .processDefinitionName("Process Definition Name");

    // Assert
    assertEquals("Process Definition Name", historicTaskInstanceQueryImpl.getProcessDefinitionName());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionNameLike(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processDefinitionNameLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processDefinitionNameLike(String)"})
  public void testProcessDefinitionNameLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionNameLikeResult = historicTaskInstanceQueryImpl
        .processDefinitionNameLike("Process Definition Name Like");

    // Assert
    assertEquals("Process Definition Name Like", historicTaskInstanceQueryImpl.getProcessDefinitionNameLike());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionNameLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processCategoryIn(List)"})
  public void testProcessCategoryIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryInList = new ArrayList<>();
    processCategoryInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryInResult = historicTaskInstanceQueryImpl
        .processCategoryIn(processCategoryInList);

    // Assert
    assertSame(processCategoryInList, historicTaskInstanceQueryImpl.getProcessCategoryInList());
    assertSame(historicTaskInstanceQueryImpl, actualProcessCategoryInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processCategoryIn(List)"})
  public void testProcessCategoryIn_given42_whenArrayListAdd42() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryInList = new ArrayList<>();
    processCategoryInList.add("42");
    processCategoryInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryInResult = historicTaskInstanceQueryImpl
        .processCategoryIn(processCategoryInList);

    // Assert
    assertSame(processCategoryInList, historicTaskInstanceQueryImpl.getProcessCategoryInList());
    assertSame(historicTaskInstanceQueryImpl, actualProcessCategoryInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processCategoryIn(List)"})
  public void testProcessCategoryIn_givenNull_whenArrayListAddNull() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryInList = new ArrayList<>();
    processCategoryInList.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processCategoryIn(processCategoryInList));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processCategoryIn(List)"})
  public void testProcessCategoryIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processCategoryIn(new ArrayList<>()));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processCategoryNotIn(List)"})
  public void testProcessCategoryNotIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryNotInList = new ArrayList<>();
    processCategoryNotInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryNotInResult = historicTaskInstanceQueryImpl
        .processCategoryNotIn(processCategoryNotInList);

    // Assert
    assertSame(processCategoryNotInList, historicTaskInstanceQueryImpl.getProcessCategoryNotInList());
    assertSame(historicTaskInstanceQueryImpl, actualProcessCategoryNotInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processCategoryNotIn(List)"})
  public void testProcessCategoryNotIn_given42_whenArrayListAdd42() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryNotInList = new ArrayList<>();
    processCategoryNotInList.add("42");
    processCategoryNotInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryNotInResult = historicTaskInstanceQueryImpl
        .processCategoryNotIn(processCategoryNotInList);

    // Assert
    assertSame(processCategoryNotInList, historicTaskInstanceQueryImpl.getProcessCategoryNotInList());
    assertSame(historicTaskInstanceQueryImpl, actualProcessCategoryNotInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processCategoryNotIn(List)"})
  public void testProcessCategoryNotIn_givenNull_whenArrayListAddNull() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryNotInList = new ArrayList<>();
    processCategoryNotInList.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processCategoryNotIn(processCategoryNotInList));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processCategoryNotIn(List)"})
  public void testProcessCategoryNotIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processCategoryNotIn(new ArrayList<>()));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#deploymentId(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#deploymentId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.deploymentId(String)"})
  public void testDeploymentId() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualDeploymentIdResult = historicTaskInstanceQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getDeploymentId());
    assertSame(historicTaskInstanceQueryImpl, actualDeploymentIdResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.deploymentIdIn(List)"})
  public void testDeploymentIdIn_given42_whenArrayListAdd42() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("42");
    deploymentIds.add("foo");

    // Act
    HistoricTaskInstanceQuery actualDeploymentIdInResult = historicTaskInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, historicTaskInstanceQueryImpl.getDeploymentIds());
    assertSame(historicTaskInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.deploymentIdIn(List)"})
  public void testDeploymentIdIn_givenFoo_whenArrayListAddFoo() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("foo");

    // Act
    HistoricTaskInstanceQuery actualDeploymentIdInResult = historicTaskInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, historicTaskInstanceQueryImpl.getDeploymentIds());
    assertSame(historicTaskInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.deploymentIdIn(List)"})
  public void testDeploymentIdIn_whenArrayList() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    ArrayList<String> deploymentIds = new ArrayList<>();

    // Act
    HistoricTaskInstanceQuery actualDeploymentIdInResult = historicTaskInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, historicTaskInstanceQueryImpl.getDeploymentIds());
    assertSame(historicTaskInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskId(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskId(String)"})
  public void testTaskId() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskIdResult = historicTaskInstanceQueryImpl.taskId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getTaskId());
    assertSame(historicTaskInstanceQueryImpl, actualTaskIdResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskName(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskName(String)"})
  public void testTaskName() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskNameResult = historicTaskInstanceQueryImpl.taskName("Task Name");

    // Assert
    assertEquals("Task Name", historicTaskInstanceQueryImpl.getTaskName());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskNameLikeIgnoreCase("Task Name Like Ignore Case");

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskNameIn(taskNameList));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn_given42_whenArrayListAdd42() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("42");
    taskNameList.add("Task name list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskNameInResult = historicTaskInstanceQueryImpl.taskNameIn(taskNameList);

    // Assert
    assertSame(taskNameList, historicTaskInstanceQueryImpl.getTaskNameList());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()} taskNameLike {@code Task Name Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn_givenHistoricTaskInstanceQueryImplTaskNameLikeTaskNameLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskNameLike("Task Name Like");

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskNameIn(taskNameList));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()} taskName {@code Task Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn_givenHistoricTaskInstanceQueryImplTaskNameTaskName() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskName("Task Name");

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskNameIn(taskNameList));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn_givenHistoricTaskInstanceQueryImpl_whenArrayList() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameIn(new ArrayList<>()));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Then {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()} TaskNameList is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameIn(List)"})
  public void testTaskNameIn_thenHistoricTaskInstanceQueryImplTaskNameListIsArrayList() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskNameInResult = historicTaskInstanceQueryImpl.taskNameIn(taskNameList);

    // Assert
    assertSame(taskNameList, historicTaskInstanceQueryImpl.getTaskNameList());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskNameLike("Task Name Like");

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskNameLikeIgnoreCase("Task Name Like Ignore Case");

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase_given42_whenArrayListAdd42() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("42");
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}.
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()} taskName {@code Task Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase_givenHistoricTaskInstanceQueryImplTaskNameTaskName() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskName("Task Name");

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}.
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase_givenHistoricTaskInstanceQueryImpl_whenArrayList() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameInIgnoreCase(new ArrayList<>()));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase_givenNull_whenArrayListAddNull() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}.
   * <ul>
   *   <li>Then return {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameInIgnoreCase(List)"})
  public void testTaskNameInIgnoreCase_thenReturnHistoricTaskInstanceQueryImpl() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameLike(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameLike(String)"})
  public void testTaskNameLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskNameLikeResult = historicTaskInstanceQueryImpl.taskNameLike("Task Name Like");

    // Assert
    assertEquals("Task Name Like", historicTaskInstanceQueryImpl.getTaskNameLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskNameLikeIgnoreCase(String)"})
  public void testTaskNameLikeIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskNameLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskNameLikeIgnoreCase("Task Name Like Ignore Case");

    // Assert
    assertEquals("task name like ignore case", historicTaskInstanceQueryImpl.getTaskNameLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskParentTaskId(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskParentTaskId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskParentTaskId(String)"})
  public void testTaskParentTaskId() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskParentTaskIdResult = historicTaskInstanceQueryImpl.taskParentTaskId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getTaskParentTaskId());
    assertSame(historicTaskInstanceQueryImpl, actualTaskParentTaskIdResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDescription(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDescription(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskDescription(String)"})
  public void testTaskDescription() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskDescriptionResult = historicTaskInstanceQueryImpl
        .taskDescription("Task Description");

    // Assert
    assertEquals("Task Description", historicTaskInstanceQueryImpl.getTaskDescription());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDescriptionResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDescriptionLike(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDescriptionLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskDescriptionLike(String)"})
  public void testTaskDescriptionLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskDescriptionLikeResult = historicTaskInstanceQueryImpl
        .taskDescriptionLike("Task Description Like");

    // Assert
    assertEquals("Task Description Like", historicTaskInstanceQueryImpl.getTaskDescriptionLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDescriptionLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDescriptionLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDescriptionLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskDescriptionLikeIgnoreCase(String)"})
  public void testTaskDescriptionLikeIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskDescriptionLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskDescriptionLikeIgnoreCase("Task Description Like Ignore Case");

    // Assert
    assertEquals("task description like ignore case", historicTaskInstanceQueryImpl.getTaskDescriptionLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDescriptionLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDeleteReason(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDeleteReason(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskDeleteReason(String)"})
  public void testTaskDeleteReason() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskDeleteReasonResult = historicTaskInstanceQueryImpl
        .taskDeleteReason("Just cause");

    // Assert
    assertEquals("Just cause", historicTaskInstanceQueryImpl.getTaskDeleteReason());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDeleteReasonResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDeleteReasonLike(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDeleteReasonLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskDeleteReasonLike(String)"})
  public void testTaskDeleteReasonLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskDeleteReasonLikeResult = historicTaskInstanceQueryImpl
        .taskDeleteReasonLike("Just cause");

    // Assert
    assertEquals("Just cause", historicTaskInstanceQueryImpl.getTaskDeleteReasonLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDeleteReasonLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssignee(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskAssignee(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskAssignee(String)"})
  public void testTaskAssignee() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskAssigneeResult = historicTaskInstanceQueryImpl.taskAssignee("Task Assignee");

    // Assert
    assertEquals("Task Assignee", historicTaskInstanceQueryImpl.getTaskAssignee());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssigneeLike(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskAssigneeLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskAssigneeLike(String)"})
  public void testTaskAssigneeLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskAssigneeLikeResult = historicTaskInstanceQueryImpl
        .taskAssigneeLike("Task Assignee Like");

    // Assert
    assertEquals("Task Assignee Like", historicTaskInstanceQueryImpl.getTaskAssigneeLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssigneeLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskAssigneeLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskAssigneeLikeIgnoreCase(String)"})
  public void testTaskAssigneeLikeIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskAssigneeLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskAssigneeLikeIgnoreCase("Task Assignee Like Ignore Case");

    // Assert
    assertEquals("task assignee like ignore case", historicTaskInstanceQueryImpl.getTaskAssigneeLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskAssigneeLike("Task Assignee Like");

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskAssigneeLikeIgnoreCase("Task Assignee Like Ignore Case");

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds_given42_whenArrayListAdd42() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("42");
    assigneeIds.add("Task assignee list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskAssigneeIdsResult = historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds);

    // Assert
    assertSame(assigneeIds, historicTaskInstanceQueryImpl.getTaskAssigneeIds());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeIdsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}.
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()} taskAssignee {@code Task Assignee}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds_givenHistoricTaskInstanceQueryImplTaskAssigneeTaskAssignee() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskAssignee("Task Assignee");

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}.
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds_givenHistoricTaskInstanceQueryImpl_whenArrayList() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskAssigneeIds(new ArrayList<>()));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds_givenNull_whenArrayListAddNull() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}.
   * <ul>
   *   <li>Then {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()} TaskAssigneeIds is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskAssigneeIds(List)"})
  public void testTaskAssigneeIds_thenHistoricTaskInstanceQueryImplTaskAssigneeIdsIsArrayList() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskAssigneeIdsResult = historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds);

    // Assert
    assertSame(assigneeIds, historicTaskInstanceQueryImpl.getTaskAssigneeIds());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeIdsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskOwner(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskOwner(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskOwner(String)"})
  public void testTaskOwner() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskOwnerResult = historicTaskInstanceQueryImpl.taskOwner("Task Owner");

    // Assert
    assertEquals("Task Owner", historicTaskInstanceQueryImpl.getTaskOwner());
    assertSame(historicTaskInstanceQueryImpl, actualTaskOwnerResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskOwnerLike(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskOwnerLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskOwnerLike(String)"})
  public void testTaskOwnerLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskOwnerLikeResult = historicTaskInstanceQueryImpl
        .taskOwnerLike("Task Owner Like");

    // Assert
    assertEquals("Task Owner Like", historicTaskInstanceQueryImpl.getTaskOwnerLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskOwnerLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskOwnerLikeIgnoreCase(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskOwnerLikeIgnoreCase(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskOwnerLikeIgnoreCase(String)"})
  public void testTaskOwnerLikeIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskOwnerLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskOwnerLikeIgnoreCase("Task Owner Like Ignore Case");

    // Assert
    assertEquals("task owner like ignore case", historicTaskInstanceQueryImpl.getTaskOwnerLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualTaskOwnerLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#finished()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#finished()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.finished()"})
  public void testFinished() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualFinishedResult = historicTaskInstanceQueryImpl.finished();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isFinished());
    assertSame(historicTaskInstanceQueryImpl, actualFinishedResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#unfinished()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#unfinished()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.unfinished()"})
  public void testUnfinished() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualUnfinishedResult = historicTaskInstanceQueryImpl.unfinished();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isUnfinished());
    assertSame(historicTaskInstanceQueryImpl, actualUnfinishedResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)} with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueEquals(String, Object)"})
  public void testTaskVariableValueEqualsWithVariableNameVariableValue_whenNull() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)} with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueEquals(String, Object)"})
  public void testTaskVariableValueEqualsWithVariableNameVariableValue_whenNull2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueEquals("Variable Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(Object)} with {@code variableValue}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueEquals(Object)"})
  public void testTaskVariableValueEqualsWithVariableValue() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueEqualsIgnoreCase(String, String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueEqualsIgnoreCase(String, String)"})
  public void testTaskVariableValueEqualsIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskVariableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueNotEqualsIgnoreCase(String, String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueNotEqualsIgnoreCase(String, String)"})
  public void testTaskVariableValueNotEqualsIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueNotEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskVariableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueNotEquals(String, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueNotEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueNotEquals(String, Object)"})
  public void testTaskVariableValueNotEquals_whenNull() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueNotEqualsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueNotEquals(String, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueNotEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueNotEquals(String, Object)"})
  public void testTaskVariableValueNotEquals_whenNull2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueNotEquals("Variable Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueNotEqualsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueGreaterThan(String, Object)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueGreaterThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueGreaterThan(String, Object)"})
  public void testTaskVariableValueGreaterThan() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueGreaterThanResult = historicTaskInstanceQueryImpl
        .taskVariableValueGreaterThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueGreaterThanResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueGreaterThanOrEqual(String, Object)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueGreaterThanOrEqual(String, Object)"})
  public void testTaskVariableValueGreaterThanOrEqual() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueGreaterThanOrEqualResult = historicTaskInstanceQueryImpl
        .taskVariableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueLessThan(String, Object)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueLessThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueLessThan(String, Object)"})
  public void testTaskVariableValueLessThan() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueLessThanResult = historicTaskInstanceQueryImpl
        .taskVariableValueLessThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueLessThanResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueLessThanOrEqual(String, Object)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueLessThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueLessThanOrEqual(String, Object)"})
  public void testTaskVariableValueLessThanOrEqual() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueLessThanOrEqualResult = historicTaskInstanceQueryImpl
        .taskVariableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueLessThanOrEqualResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueLike(String, String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueLike(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueLike(String, String)"})
  public void testTaskVariableValueLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueLikeResult = historicTaskInstanceQueryImpl
        .taskVariableValueLike("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueLikeIgnoreCase(String, String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskVariableValueLikeIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskVariableValueLikeIgnoreCase(String, String)"})
  public void testTaskVariableValueLikeIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskVariableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)} with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueEquals(String, Object)"})
  public void testProcessVariableValueEqualsWithVariableNameVariableValue_whenNull() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)} with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueEquals(String, Object)"})
  public void testProcessVariableValueEqualsWithVariableNameVariableValue_whenNull2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueEquals("Variable Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(Object)} with {@code variableValue}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueEquals(Object)"})
  public void testProcessVariableValueEqualsWithVariableValue() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueNotEquals(String, Object)"})
  public void testProcessVariableValueNotEquals_whenNull() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueNotEquals(String, Object)"})
  public void testProcessVariableValueNotEquals_whenNull2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueNotEquals("Variable Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueEqualsIgnoreCase(String, String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueEqualsIgnoreCase(String, String)"})
  public void testProcessVariableValueEqualsIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processVariableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEqualsIgnoreCase(String, String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueNotEqualsIgnoreCase(String, String)"})
  public void testProcessVariableValueNotEqualsIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueNotEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processVariableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThan(String, Object)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueGreaterThan(String, Object)"})
  public void testProcessVariableValueGreaterThan() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueGreaterThanResult = historicTaskInstanceQueryImpl
        .processVariableValueGreaterThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueGreaterThanResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThanOrEqual(String, Object)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueGreaterThanOrEqual(String, Object)"})
  public void testProcessVariableValueGreaterThanOrEqual() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueGreaterThanOrEqualResult = historicTaskInstanceQueryImpl
        .processVariableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThan(String, Object)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueLessThan(String, Object)"})
  public void testProcessVariableValueLessThan() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueLessThanResult = historicTaskInstanceQueryImpl
        .processVariableValueLessThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLessThanResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThanOrEqual(String, Object)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueLessThanOrEqual(String, Object)"})
  public void testProcessVariableValueLessThanOrEqual() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueLessThanOrEqualResult = historicTaskInstanceQueryImpl
        .processVariableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLessThanOrEqualResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueLike(String, String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueLike(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueLike(String, String)"})
  public void testProcessVariableValueLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueLikeResult = historicTaskInstanceQueryImpl
        .processVariableValueLike("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueLikeIgnoreCase(String, String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processVariableValueLikeIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processVariableValueLikeIgnoreCase(String, String)"})
  public void testProcessVariableValueLikeIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processVariableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDefinitionKey(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDefinitionKey(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskDefinitionKey(String)"})
  public void testTaskDefinitionKey() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskDefinitionKeyResult = historicTaskInstanceQueryImpl
        .taskDefinitionKey("Task Definition Key");

    // Assert
    assertEquals("Task Definition Key", historicTaskInstanceQueryImpl.getTaskDefinitionKey());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDefinitionKeyResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDefinitionKeyLike(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDefinitionKeyLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskDefinitionKeyLike(String)"})
  public void testTaskDefinitionKeyLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskDefinitionKeyLikeResult = historicTaskInstanceQueryImpl
        .taskDefinitionKeyLike("Task Definition Key Like");

    // Assert
    assertEquals("Task Definition Key Like", historicTaskInstanceQueryImpl.getTaskDefinitionKeyLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDefinitionKeyLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskPriority(Integer)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskPriority(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskPriority(Integer)"})
  public void testTaskPriority() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskPriorityResult = historicTaskInstanceQueryImpl.taskPriority(1);

    // Assert
    assertEquals(1, historicTaskInstanceQueryImpl.getTaskPriority().intValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskPriorityResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskMinPriority(Integer)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskMinPriority(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskMinPriority(Integer)"})
  public void testTaskMinPriority() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskMinPriorityResult = historicTaskInstanceQueryImpl.taskMinPriority(1);

    // Assert
    assertEquals(1, historicTaskInstanceQueryImpl.getTaskMinPriority().intValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskMinPriorityResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskMaxPriority(Integer)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskMaxPriority(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskMaxPriority(Integer)"})
  public void testTaskMaxPriority() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskMaxPriorityResult = historicTaskInstanceQueryImpl.taskMaxPriority(1);

    // Assert
    assertEquals(1, historicTaskInstanceQueryImpl.getTaskMaxPriority().intValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskMaxPriorityResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processFinished()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processFinished()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processFinished()"})
  public void testProcessFinished() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessFinishedResult = historicTaskInstanceQueryImpl.processFinished();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isProcessFinished());
    assertSame(historicTaskInstanceQueryImpl, actualProcessFinishedResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processUnfinished()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processUnfinished()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.processUnfinished()"})
  public void testProcessUnfinished() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessUnfinishedResult = historicTaskInstanceQueryImpl.processUnfinished();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isProcessUnfinished());
    assertSame(historicTaskInstanceQueryImpl, actualProcessUnfinishedResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDueDate(Date)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDueDate(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskDueDate(Date)"})
  public void testTaskDueDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskDueDate(dueDate));
    assertSame(dueDate, historicTaskInstanceQueryImpl.getDueDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDueAfter(Date)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDueAfter(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskDueAfter(Date)"})
  public void testTaskDueAfter() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date dueAfter = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskDueAfter(dueAfter));
    assertSame(dueAfter, historicTaskInstanceQueryImpl.getDueAfter());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDueBefore(Date)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDueBefore(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskDueBefore(Date)"})
  public void testTaskDueBefore() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date dueBefore = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskDueBefore(dueBefore));
    assertSame(dueBefore, historicTaskInstanceQueryImpl.getDueBefore());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCreatedOn(Date)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCreatedOn(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCreatedOn(Date)"})
  public void testTaskCreatedOn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date creationDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedOn(creationDate));
    assertSame(creationDate, historicTaskInstanceQueryImpl.getCreationDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCreatedBefore(Date)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCreatedBefore(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCreatedBefore(Date)"})
  public void testTaskCreatedBefore() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date creationBeforeDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedBefore(creationBeforeDate));
    assertSame(creationBeforeDate, historicTaskInstanceQueryImpl.getCreationBeforeDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCreatedAfter(Date)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCreatedAfter(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCreatedAfter(Date)"})
  public void testTaskCreatedAfter() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date creationAfterDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedAfter(creationAfterDate));
    assertSame(creationAfterDate, historicTaskInstanceQueryImpl.getCreationAfterDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCompletedOn(Date)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCompletedOn(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCompletedOn(Date)"})
  public void testTaskCompletedOn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date completedDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedOn(completedDate));
    assertSame(completedDate, historicTaskInstanceQueryImpl.getCompletedDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCompletedBefore(Date)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCompletedBefore(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCompletedBefore(Date)"})
  public void testTaskCompletedBefore() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date completedBeforeDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedBefore(completedBeforeDate));
    assertSame(completedBeforeDate, historicTaskInstanceQueryImpl.getCompletedBeforeDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCompletedAfter(Date)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCompletedAfter(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCompletedAfter(Date)"})
  public void testTaskCompletedAfter() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date completedAfterDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedAfter(completedAfterDate));
    assertSame(completedAfterDate, historicTaskInstanceQueryImpl.getCompletedAfterDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#withoutTaskDueDate()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#withoutTaskDueDate()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.withoutTaskDueDate()"})
  public void testWithoutTaskDueDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualWithoutTaskDueDateResult = historicTaskInstanceQueryImpl.withoutTaskDueDate();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isWithoutDueDate());
    assertSame(historicTaskInstanceQueryImpl, actualWithoutTaskDueDateResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCategory(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCategory(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCategory(String)"})
  public void testTaskCategory() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskCategoryResult = historicTaskInstanceQueryImpl.taskCategory("Category");

    // Assert
    assertEquals("Category", historicTaskInstanceQueryImpl.getCategory());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCategoryResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String)} with {@code candidateUser}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCandidateUser(String)"})
  public void testTaskCandidateUserWithCandidateUser() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateUserResult = historicTaskInstanceQueryImpl
        .taskCandidateUser("2020-03-01");

    // Assert
    assertEquals("2020-03-01", historicTaskInstanceQueryImpl.getCandidateUser());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)} with {@code candidateUser}, {@code usersGroups}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCandidateUser(String, List)"})
  public void testTaskCandidateUserWithCandidateUserUsersGroups() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskCandidateUser(null, new ArrayList<>()));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)} with {@code candidateUser}, {@code usersGroups}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCandidateUser(String, List)"})
  public void testTaskCandidateUserWithCandidateUserUsersGroups_given42_whenArrayListAdd42() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> usersGroups = new ArrayList<>();
    usersGroups.add("42");
    usersGroups.add("foo");

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateUserResult = historicTaskInstanceQueryImpl
        .taskCandidateUser("2020-03-01", usersGroups);

    // Assert
    assertEquals("2020-03-01", historicTaskInstanceQueryImpl.getCandidateUser());
    assertSame(usersGroups, historicTaskInstanceQueryImpl.getCandidateGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)} with {@code candidateUser}, {@code usersGroups}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCandidateUser(String, List)"})
  public void testTaskCandidateUserWithCandidateUserUsersGroups_givenFoo_whenArrayListAddFoo() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> usersGroups = new ArrayList<>();
    usersGroups.add("foo");

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateUserResult = historicTaskInstanceQueryImpl
        .taskCandidateUser("2020-03-01", usersGroups);

    // Assert
    assertEquals("2020-03-01", historicTaskInstanceQueryImpl.getCandidateUser());
    assertSame(usersGroups, historicTaskInstanceQueryImpl.getCandidateGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)} with {@code candidateUser}, {@code usersGroups}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCandidateUser(String, List)"})
  public void testTaskCandidateUserWithCandidateUserUsersGroups_whenArrayList() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    ArrayList<String> usersGroups = new ArrayList<>();

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateUserResult = historicTaskInstanceQueryImpl
        .taskCandidateUser("2020-03-01", usersGroups);

    // Assert
    assertEquals("2020-03-01", historicTaskInstanceQueryImpl.getCandidateUser());
    assertSame(usersGroups, historicTaskInstanceQueryImpl.getCandidateGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String)} with {@code candidateUser}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCandidateUser(String)"})
  public void testTaskCandidateUserWithCandidateUser_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).taskCandidateUser(null));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateGroup(String)}.
   * <ul>
   *   <li>Then {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()} CandidateGroup is {@code 2020-03-01}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCandidateGroup(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCandidateGroup(String)"})
  public void testTaskCandidateGroup_thenHistoricTaskInstanceQueryImplCandidateGroupIs20200301() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateGroupResult = historicTaskInstanceQueryImpl
        .taskCandidateGroup("2020-03-01");

    // Assert
    assertEquals("2020-03-01", historicTaskInstanceQueryImpl.getCandidateGroup());
    assertEquals(1, historicTaskInstanceQueryImpl.getCandidateGroups().size());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateGroupResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateGroup(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCandidateGroup(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCandidateGroup(String)"})
  public void testTaskCandidateGroup_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).taskCandidateGroup(null));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCandidateGroupIn(List)"})
  public void testTaskCandidateGroupIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("Candidate group list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateGroupInResult = historicTaskInstanceQueryImpl
        .taskCandidateGroupIn(candidateGroups);

    // Assert
    assertSame(candidateGroups, historicTaskInstanceQueryImpl.getCandidateGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateGroupInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCandidateGroupIn(List)"})
  public void testTaskCandidateGroupIn2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskCandidateGroup("2020-03-01");

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("Candidate group list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskCandidateGroupIn(candidateGroups));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCandidateGroupIn(List)"})
  public void testTaskCandidateGroupIn_given42_whenArrayListAdd42() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("Candidate group list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateGroupInResult = historicTaskInstanceQueryImpl
        .taskCandidateGroupIn(candidateGroups);

    // Assert
    assertSame(candidateGroups, historicTaskInstanceQueryImpl.getCandidateGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateGroupInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskCandidateGroupIn(List)"})
  public void testTaskCandidateGroupIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskCandidateGroupIn(new ArrayList<>()));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskInvolvedUser(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskInvolvedUser(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskInvolvedUser(String)"})
  public void testTaskInvolvedUser() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskInvolvedUserResult = historicTaskInstanceQueryImpl
        .taskInvolvedUser("Involved User");

    // Assert
    assertEquals("Involved User", historicTaskInstanceQueryImpl.getInvolvedUser());
    assertSame(historicTaskInstanceQueryImpl, actualTaskInvolvedUserResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskInvolvedGroupsIn(List)"})
  public void testTaskInvolvedGroupsIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    HistoricTaskInstanceQuery actualTaskInvolvedGroupsInResult = historicTaskInstanceQueryImpl
        .taskInvolvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, historicTaskInstanceQueryImpl.getInvolvedGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskInvolvedGroupsInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskInvolvedGroupsIn(List)"})
  public void testTaskInvolvedGroupsIn_given42_whenArrayListAdd42() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("42");
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    HistoricTaskInstanceQuery actualTaskInvolvedGroupsInResult = historicTaskInstanceQueryImpl
        .taskInvolvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, historicTaskInstanceQueryImpl.getInvolvedGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskInvolvedGroupsInResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskInvolvedGroupsIn(List)"})
  public void testTaskInvolvedGroupsIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskInvolvedGroupsIn(new ArrayList<>()));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskTenantId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()} TenantId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskTenantId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskTenantId(String)"})
  public void testTaskTenantId_when42_thenHistoricTaskInstanceQueryImplTenantIdIs42() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskTenantIdResult = historicTaskInstanceQueryImpl.taskTenantId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getTenantId());
    assertSame(historicTaskInstanceQueryImpl, actualTaskTenantIdResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskTenantId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskTenantId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskTenantId(String)"})
  public void testTaskTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).taskTenantId(null));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskTenantIdLike(String)}.
   * <ul>
   *   <li>Then {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()} TenantIdLike is {@code Tenant Id Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskTenantIdLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskTenantIdLike(String)"})
  public void testTaskTenantIdLike_thenHistoricTaskInstanceQueryImplTenantIdLikeIsTenantIdLike() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskTenantIdLikeResult = historicTaskInstanceQueryImpl
        .taskTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", historicTaskInstanceQueryImpl.getTenantIdLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskTenantIdLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskTenantIdLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskTenantIdLike(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskTenantIdLike(String)"})
  public void testTaskTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).taskTenantIdLike(null));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskWithoutTenantId()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskWithoutTenantId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.taskWithoutTenantId()"})
  public void testTaskWithoutTenantId() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskWithoutTenantIdResult = historicTaskInstanceQueryImpl.taskWithoutTenantId();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isWithoutTenantId());
    assertSame(historicTaskInstanceQueryImpl, actualTaskWithoutTenantIdResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#locale(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#locale(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.locale(String)"})
  public void testLocale() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualLocaleResult = historicTaskInstanceQueryImpl.locale("en");

    // Assert
    assertEquals("en", historicTaskInstanceQueryImpl.getLocale());
    assertSame(historicTaskInstanceQueryImpl, actualLocaleResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#includeTaskLocalVariables()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#includeTaskLocalVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.includeTaskLocalVariables()"})
  public void testIncludeTaskLocalVariables() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualIncludeTaskLocalVariablesResult = historicTaskInstanceQueryImpl
        .includeTaskLocalVariables();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isIncludeTaskLocalVariables());
    assertSame(historicTaskInstanceQueryImpl, actualIncludeTaskLocalVariablesResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#includeProcessVariables()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#includeProcessVariables()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.includeProcessVariables()"})
  public void testIncludeProcessVariables() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualIncludeProcessVariablesResult = historicTaskInstanceQueryImpl
        .includeProcessVariables();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isIncludeProcessVariables());
    assertSame(historicTaskInstanceQueryImpl, actualIncludeProcessVariablesResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#limitTaskVariables(Integer)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#limitTaskVariables(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.limitTaskVariables(Integer)"})
  public void testLimitTaskVariables() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualLimitTaskVariablesResult = historicTaskInstanceQueryImpl.limitTaskVariables(1);

    // Assert
    assertEquals(1, historicTaskInstanceQueryImpl.getTaskVariablesLimit().intValue());
    assertSame(historicTaskInstanceQueryImpl, actualLimitTaskVariablesResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#or()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#or()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.or()"})
  public void testOr() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualOrResult = historicTaskInstanceQueryImpl.or();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isInOrStatement());
    assertSame(historicTaskInstanceQueryImpl, actualOrResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#endOr()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#endOr()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricTaskInstanceQuery HistoricTaskInstanceQueryImpl.endOr()"})
  public void testEndOr() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new HistoricTaskInstanceQueryImpl()).endOr());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#getMssqlOrDB2OrderBy()}.
   * <ul>
   *   <li>Then return {@code TEMPRES_ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String HistoricTaskInstanceQueryImpl.getMssqlOrDB2OrderBy()"})
  public void testGetMssqlOrDB2OrderBy_thenReturnTempresIdAsc() {
    // Arrange, Act and Assert
    assertEquals("TEMPRES_ID_ asc", (new HistoricTaskInstanceQueryImpl()).getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#getMssqlOrDB2OrderBy()}.
   * <ul>
   *   <li>Then return {@code TEMPRES_ID_ asc asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String HistoricTaskInstanceQueryImpl.getMssqlOrDB2OrderBy()"})
  public void testGetMssqlOrDB2OrderBy_thenReturnTempresIdAscAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.addOrder("RES.ID_ asc", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act and Assert
    assertEquals("TEMPRES_ID_ asc asc", historicTaskInstanceQueryImpl.getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#getCandidateGroups()}.
   * <ul>
   *   <li>Given {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#getCandidateGroups()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List HistoricTaskInstanceQueryImpl.getCandidateGroups()"})
  public void testGetCandidateGroups_givenHistoricTaskInstanceQueryImpl_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceQueryImpl()).getCandidateGroups());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#getCandidateGroups()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#getCandidateGroups()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List HistoricTaskInstanceQueryImpl.getCandidateGroups()"})
  public void testGetCandidateGroups_thenReturnSizeIsOne() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskCandidateGroup("2020-03-01");

    // Act
    List<String> actualCandidateGroups = historicTaskInstanceQueryImpl.getCandidateGroups();

    // Assert
    assertEquals(1, actualCandidateGroups.size());
    assertEquals("2020-03-01", actualCandidateGroups.get(0));
  }
}
