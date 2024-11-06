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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.query.QueryProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricTaskInstanceQueryImplDiffblueTest {
  @InjectMocks
  private HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl;

  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return DatabaseType is {@code null}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl(CommandExecutor)}
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
   *   <li>
   * {@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeyLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeys()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionName()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionNameLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKeyLike()}
   *   <li>
   * {@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKeyLikeIgnoreCase()}
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
   *   <li>
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl(CommandExecutor, String)}
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
   *   <li>
   * {@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeyLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeys()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionName()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionNameLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKeyLike()}
   *   <li>
   * {@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKeyLikeIgnoreCase()}
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
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   */
  @Test
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
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId() {
    // Arrange and Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceIdResult = historicTaskInstanceQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getProcessInstanceId());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  public void testProcessInstanceIdIn_given42_whenArrayListAdd42_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processInstanceIds = new ArrayList<>();
    processInstanceIds.add("42");
    processInstanceIds.add("Process instance id list is empty");

    // Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceIdInResult = historicTaskInstanceQueryImpl
        .processInstanceIdIn(processInstanceIds);

    // Assert
    assertEquals("RES.ID_ asc", actualProcessInstanceIdInResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualProcessInstanceIdInResult.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", actualProcessInstanceIdInResult.getMssqlOrDB2OrderBy());
    assertNull(actualProcessInstanceIdInResult.orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
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
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  public void testProcessInstanceIdIn_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processInstanceIds = new ArrayList<>();
    processInstanceIds.add("Process instance id list is empty");

    // Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceIdInResult = historicTaskInstanceQueryImpl
        .processInstanceIdIn(processInstanceIds);

    // Assert
    assertEquals("RES.ID_ asc", actualProcessInstanceIdInResult.getOrderBy());
    assertEquals("RES.ID_ asc", actualProcessInstanceIdInResult.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", actualProcessInstanceIdInResult.getMssqlOrDB2OrderBy());
    assertNull(actualProcessInstanceIdInResult.orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  public void testProcessInstanceIdIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processInstanceIdIn(new ArrayList<>()));
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKey(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  public void testProcessInstanceBusinessKey() {
    // Arrange and Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceBusinessKeyResult = historicTaskInstanceQueryImpl
        .processInstanceBusinessKey("Process Instance Business Key");

    // Assert
    assertEquals("Process Instance Business Key", historicTaskInstanceQueryImpl.getProcessInstanceBusinessKey());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKeyLike(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKeyLike(String)}
   */
  @Test
  public void testProcessInstanceBusinessKeyLike() {
    // Arrange and Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceBusinessKeyLikeResult = historicTaskInstanceQueryImpl
        .processInstanceBusinessKeyLike("Process Instance Business Key Like");

    // Assert
    assertEquals("Process Instance Business Key Like",
        historicTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLike());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceBusinessKeyLikeResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKeyLikeIgnoreCase(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKeyLikeIgnoreCase(String)}
   */
  @Test
  public void testProcessInstanceBusinessKeyLikeIgnoreCase() {
    // Arrange and Act
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
  public void testExecutionId() {
    // Arrange and Act
    HistoricTaskInstanceQueryImpl actualExecutionIdResult = historicTaskInstanceQueryImpl.executionId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getExecutionId());
    assertSame(historicTaskInstanceQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId() {
    // Arrange and Act
    HistoricTaskInstanceQueryImpl actualProcessDefinitionIdResult = historicTaskInstanceQueryImpl
        .processDefinitionId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getProcessDefinitionId());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionKey(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyResult = historicTaskInstanceQueryImpl
        .processDefinitionKey("Process Definition Key");

    // Assert
    assertEquals("Process Definition Key", historicTaskInstanceQueryImpl.getProcessDefinitionKey());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyLike(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  public void testProcessDefinitionKeyLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyLikeResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyLike("Process Definition Key Like");

    // Assert
    assertEquals("Process Definition Key Like", historicTaskInstanceQueryImpl.getProcessDefinitionKeyLike());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyLikeResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyLikeIgnoreCase(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyLikeIgnoreCase(String)}
   */
  @Test
  public void testProcessDefinitionKeyLikeIgnoreCase() {
    // Arrange and Act
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
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
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
    assertTrue(actualProcessDefinitionKeyInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).orderBy);
    assertSame(processDefinitionKeys,
        ((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).getProcessDefinitionKeys());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return ProcessDefinitionKeys is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  public void testProcessDefinitionKeyIn_givenFoo_thenReturnProcessDefinitionKeysIsArrayList() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processDefinitionKeys = new ArrayList<>();
    processDefinitionKeys.add("foo");

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyInResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertTrue(actualProcessDefinitionKeyInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).orderBy);
    assertSame(processDefinitionKeys,
        ((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).getProcessDefinitionKeys());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return ProcessDefinitionKeys Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  public void testProcessDefinitionKeyIn_whenArrayList_thenReturnProcessDefinitionKeysEmpty() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyInResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyIn(new ArrayList<>());

    // Assert
    assertTrue(actualProcessDefinitionKeyInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).orderBy);
    assertTrue(
        ((HistoricTaskInstanceQueryImpl) actualProcessDefinitionKeyInResult).getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionName(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionName(String)}
   */
  @Test
  public void testProcessDefinitionName() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessDefinitionNameResult = historicTaskInstanceQueryImpl
        .processDefinitionName("Process Definition Name");

    // Assert
    assertEquals("Process Definition Name", historicTaskInstanceQueryImpl.getProcessDefinitionName());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processDefinitionNameLike(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionNameLike(String)}
   */
  @Test
  public void testProcessDefinitionNameLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessDefinitionNameLikeResult = historicTaskInstanceQueryImpl
        .processDefinitionNameLike("Process Definition Name Like");

    // Assert
    assertEquals("Process Definition Name Like", historicTaskInstanceQueryImpl.getProcessDefinitionNameLike());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionNameLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
  public void testProcessCategoryIn_given42_whenArrayListAdd42_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryInList = new ArrayList<>();
    processCategoryInList.add("42");
    processCategoryInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryInResult = historicTaskInstanceQueryImpl
        .processCategoryIn(processCategoryInList);

    // Assert
    assertTrue(actualProcessCategoryInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessCategoryInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessCategoryInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessCategoryInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualProcessCategoryInResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
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
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
  public void testProcessCategoryIn_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryInList = new ArrayList<>();
    processCategoryInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryInResult = historicTaskInstanceQueryImpl
        .processCategoryIn(processCategoryInList);

    // Assert
    assertTrue(actualProcessCategoryInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessCategoryInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessCategoryInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessCategoryInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualProcessCategoryInResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
  public void testProcessCategoryIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processCategoryIn(new ArrayList<>()));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  public void testProcessCategoryNotIn_given42_whenArrayListAdd42_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryNotInList = new ArrayList<>();
    processCategoryNotInList.add("42");
    processCategoryNotInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryNotInResult = historicTaskInstanceQueryImpl
        .processCategoryNotIn(processCategoryNotInList);

    // Assert
    assertTrue(actualProcessCategoryNotInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessCategoryNotInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessCategoryNotInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessCategoryNotInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualProcessCategoryNotInResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
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
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  public void testProcessCategoryNotIn_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryNotInList = new ArrayList<>();
    processCategoryNotInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryNotInResult = historicTaskInstanceQueryImpl
        .processCategoryNotIn(processCategoryNotInList);

    // Assert
    assertTrue(actualProcessCategoryNotInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessCategoryNotInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessCategoryNotInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessCategoryNotInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualProcessCategoryNotInResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
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
  public void testDeploymentId() {
    // Arrange and Act
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
   *   <li>Then return DeploymentIds is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  public void testDeploymentIdIn_given42_whenArrayListAdd42_thenReturnDeploymentIdsIsArrayList() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("42");
    deploymentIds.add("foo");

    // Act
    HistoricTaskInstanceQuery actualDeploymentIdInResult = historicTaskInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertTrue(actualDeploymentIdInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).orderBy);
    assertSame(deploymentIds, ((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).getDeploymentIds());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return DeploymentIds is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  public void testDeploymentIdIn_givenFoo_thenReturnDeploymentIdsIsArrayList() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("foo");

    // Act
    HistoricTaskInstanceQuery actualDeploymentIdInResult = historicTaskInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertTrue(actualDeploymentIdInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).orderBy);
    assertSame(deploymentIds, ((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).getDeploymentIds());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return DeploymentIds Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  public void testDeploymentIdIn_whenArrayList_thenReturnDeploymentIdsEmpty() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualDeploymentIdInResult = historicTaskInstanceQueryImpl
        .deploymentIdIn(new ArrayList<>());

    // Assert
    assertTrue(actualDeploymentIdInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).orderBy);
    assertTrue(((HistoricTaskInstanceQueryImpl) actualDeploymentIdInResult).getDeploymentIds().isEmpty());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskId(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskId(String)}
   */
  @Test
  public void testTaskId() {
    // Arrange and Act
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
  public void testTaskName() {
    // Arrange and Act
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
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  public void testTaskNameIn_given42_whenArrayListAdd42_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("42");
    taskNameList.add("Task name list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskNameInResult = historicTaskInstanceQueryImpl.taskNameIn(taskNameList);

    // Assert
    assertTrue(actualTaskNameInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskNameInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskNameInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskNameInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskNameInResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   * taskNameLike {@code Task Name Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
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
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   * taskName {@code Task Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
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
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  public void testTaskNameIn_givenHistoricTaskInstanceQueryImpl_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskNameInResult = historicTaskInstanceQueryImpl.taskNameIn(taskNameList);

    // Assert
    assertTrue(actualTaskNameInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskNameInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskNameInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskNameInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskNameInResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  public void testTaskNameIn_givenHistoricTaskInstanceQueryImpl_whenArrayList() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameIn(new ArrayList<>()));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
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
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
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
   *   <li>Then return TaskNameListIgnoreCase size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  public void testTaskNameInIgnoreCase_given42_thenReturnTaskNameListIgnoreCaseSizeIsTwo() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("42");
    taskNameList.add("Task name list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskNameInIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskNameInIgnoreCase(taskNameList);

    // Assert
    assertTrue(actualTaskNameInIgnoreCaseResult instanceof HistoricTaskInstanceQueryImpl);
    List<String> taskNameListIgnoreCase = ((HistoricTaskInstanceQueryImpl) actualTaskNameInIgnoreCaseResult)
        .getTaskNameListIgnoreCase();
    assertEquals(2, taskNameListIgnoreCase.size());
    assertEquals("42", taskNameListIgnoreCase.get(0));
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskNameInIgnoreCaseResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskNameInIgnoreCaseResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskNameInIgnoreCaseResult).getMssqlOrDB2OrderBy());
    assertEquals("task name list is empty", taskNameListIgnoreCase.get(1));
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskNameInIgnoreCaseResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   * taskName {@code Task Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
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
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
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
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
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
   *   <li>Then return TaskNameListIgnoreCase size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  public void testTaskNameInIgnoreCase_thenReturnTaskNameListIgnoreCaseSizeIsOne() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskNameInIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskNameInIgnoreCase(taskNameList);

    // Assert
    assertTrue(actualTaskNameInIgnoreCaseResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskNameInIgnoreCaseResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskNameInIgnoreCaseResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskNameInIgnoreCaseResult).getMssqlOrDB2OrderBy());
    List<String> taskNameListIgnoreCase = ((HistoricTaskInstanceQueryImpl) actualTaskNameInIgnoreCaseResult)
        .getTaskNameListIgnoreCase();
    assertEquals(1, taskNameListIgnoreCase.size());
    assertEquals("task name list is empty", taskNameListIgnoreCase.get(0));
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskNameInIgnoreCaseResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameLike(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameLike(String)}
   */
  @Test
  public void testTaskNameLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskNameLikeResult = historicTaskInstanceQueryImpl.taskNameLike("Task Name Like");

    // Assert
    assertEquals("Task Name Like", historicTaskInstanceQueryImpl.getTaskNameLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskNameLikeIgnoreCase(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskNameLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskNameLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskNameLikeIgnoreCase("Task Name Like Ignore Case");

    // Assert
    assertEquals("task name like ignore case", historicTaskInstanceQueryImpl.getTaskNameLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskParentTaskId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskParentTaskId(String)}
   */
  @Test
  public void testTaskParentTaskId() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskParentTaskIdResult = historicTaskInstanceQueryImpl.taskParentTaskId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getTaskParentTaskId());
    assertSame(historicTaskInstanceQueryImpl, actualTaskParentTaskIdResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDescription(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDescription(String)}
   */
  @Test
  public void testTaskDescription() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDescriptionResult = historicTaskInstanceQueryImpl
        .taskDescription("Task Description");

    // Assert
    assertEquals("Task Description", historicTaskInstanceQueryImpl.getTaskDescription());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDescriptionResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDescriptionLike(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDescriptionLike(String)}
   */
  @Test
  public void testTaskDescriptionLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDescriptionLikeResult = historicTaskInstanceQueryImpl
        .taskDescriptionLike("Task Description Like");

    // Assert
    assertEquals("Task Description Like", historicTaskInstanceQueryImpl.getTaskDescriptionLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDescriptionLikeResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#taskDescriptionLikeIgnoreCase(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDescriptionLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskDescriptionLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDescriptionLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskDescriptionLikeIgnoreCase("Task Description Like Ignore Case");

    // Assert
    assertEquals("task description like ignore case", historicTaskInstanceQueryImpl.getTaskDescriptionLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDescriptionLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDeleteReason(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDeleteReason(String)}
   */
  @Test
  public void testTaskDeleteReason() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDeleteReasonResult = historicTaskInstanceQueryImpl
        .taskDeleteReason("Just cause");

    // Assert
    assertEquals("Just cause", historicTaskInstanceQueryImpl.getTaskDeleteReason());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDeleteReasonResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDeleteReasonLike(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDeleteReasonLike(String)}
   */
  @Test
  public void testTaskDeleteReasonLike() {
    // Arrange and Act
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
  public void testTaskAssignee() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskAssigneeResult = historicTaskInstanceQueryImpl.taskAssignee("Task Assignee");

    // Assert
    assertEquals("Task Assignee", historicTaskInstanceQueryImpl.getTaskAssignee());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssigneeLike(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeLike(String)}
   */
  @Test
  public void testTaskAssigneeLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskAssigneeLikeResult = historicTaskInstanceQueryImpl
        .taskAssigneeLike("Task Assignee Like");

    // Assert
    assertEquals("Task Assignee Like", historicTaskInstanceQueryImpl.getTaskAssigneeLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeLikeResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeLikeIgnoreCase(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskAssigneeLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskAssigneeLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskAssigneeLikeIgnoreCase("Task Assignee Like Ignore Case");

    // Assert
    assertEquals("task assignee like ignore case", historicTaskInstanceQueryImpl.getTaskAssigneeLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
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
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
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
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  public void testTaskAssigneeIds_given42_whenArrayListAdd42_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("42");
    assigneeIds.add("Task assignee list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskAssigneeIdsResult = historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds);

    // Assert
    assertTrue(actualTaskAssigneeIdsResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskAssigneeIdsResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskAssigneeIdsResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskAssigneeIdsResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskAssigneeIdsResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   * taskAssignee {@code Task Assignee}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
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
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
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
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
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
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  public void testTaskAssigneeIds_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskAssigneeIdsResult = historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds);

    // Assert
    assertTrue(actualTaskAssigneeIdsResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskAssigneeIdsResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskAssigneeIdsResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskAssigneeIdsResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskAssigneeIdsResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskOwner(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskOwner(String)}
   */
  @Test
  public void testTaskOwner() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskOwnerResult = historicTaskInstanceQueryImpl.taskOwner("Task Owner");

    // Assert
    assertEquals("Task Owner", historicTaskInstanceQueryImpl.getTaskOwner());
    assertSame(historicTaskInstanceQueryImpl, actualTaskOwnerResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskOwnerLike(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskOwnerLike(String)}
   */
  @Test
  public void testTaskOwnerLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskOwnerLikeResult = historicTaskInstanceQueryImpl
        .taskOwnerLike("Task Owner Like");

    // Assert
    assertEquals("Task Owner Like", historicTaskInstanceQueryImpl.getTaskOwnerLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskOwnerLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskOwnerLikeIgnoreCase(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskOwnerLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskOwnerLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskOwnerLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskOwnerLikeIgnoreCase("Task Owner Like Ignore Case");

    // Assert
    assertEquals("task owner like ignore case", historicTaskInstanceQueryImpl.getTaskOwnerLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualTaskOwnerLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#finished()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#finished()}
   */
  @Test
  public void testFinished_givenHistoricTaskInstanceQueryImpl_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualFinishedResult = (new HistoricTaskInstanceQueryImpl()).finished();

    // Assert
    assertTrue(actualFinishedResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualFinishedResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualFinishedResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", ((HistoricTaskInstanceQueryImpl) actualFinishedResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualFinishedResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#unfinished()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#unfinished()}
   */
  @Test
  public void testUnfinished_givenHistoricTaskInstanceQueryImpl_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualUnfinishedResult = (new HistoricTaskInstanceQueryImpl()).unfinished();

    // Assert
    assertTrue(actualUnfinishedResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualUnfinishedResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualUnfinishedResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", ((HistoricTaskInstanceQueryImpl) actualUnfinishedResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualUnfinishedResult).orderBy);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)}
   * with {@code variableName}, {@code variableValue}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)}
   */
  @Test
  public void testTaskVariableValueEqualsWithVariableNameVariableValue() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueEquals("Variable Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)}
   * with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)}
   */
  @Test
  public void testTaskVariableValueEqualsWithVariableNameVariableValue_whenNull() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)}
   * with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)}
   */
  @Test
  public void testTaskVariableValueEqualsWithVariableNameVariableValue_whenNull2() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueEquals("Variable Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(Object)}
   * with {@code variableValue}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(Object)}
   */
  @Test
  public void testTaskVariableValueEqualsWithVariableValue_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsResult = (new HistoricTaskInstanceQueryImpl())
        .taskVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(actualTaskVariableValueEqualsResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskVariableValueEqualsResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskVariableValueEqualsResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskVariableValueEqualsResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskVariableValueEqualsResult).orderBy);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)}
   * with {@code variableName}, {@code variableValue}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueEqualsWithVariableNameVariableValue() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueEquals("Variable Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)}
   * with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueEqualsWithVariableNameVariableValue_whenNull() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)}
   * with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueEqualsWithVariableNameVariableValue_whenNull2() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueEquals("Variable Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(Object)}
   * with {@code variableValue}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(Object)}
   */
  @Test
  public void testProcessVariableValueEqualsWithVariableValue_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsResult = (new HistoricTaskInstanceQueryImpl())
        .processVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(actualProcessVariableValueEqualsResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessVariableValueEqualsResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessVariableValueEqualsResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessVariableValueEqualsResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualProcessVariableValueEqualsResult).orderBy);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueNotEquals() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueNotEquals("Variable Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueNotEquals_whenNull() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueNotEquals_whenNull2() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueNotEquals("Variable Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEqualsIgnoreCase(String, String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testProcessVariableValueEqualsIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processVariableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEqualsIgnoreCase(String, String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testProcessVariableValueNotEqualsIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueNotEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processVariableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThan(String, Object)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThan(String, Object)}
   */
  @Test
  public void testProcessVariableValueGreaterThan() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueGreaterThanResult = historicTaskInstanceQueryImpl
        .processVariableValueGreaterThan("Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueGreaterThanResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThan(String, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThan(String, Object)}
   */
  @Test
  public void testProcessVariableValueGreaterThan_whenNull() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueGreaterThanResult = historicTaskInstanceQueryImpl
        .processVariableValueGreaterThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueGreaterThanResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThanOrEqual(String, Object)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testProcessVariableValueGreaterThanOrEqual() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueGreaterThanOrEqualResult = historicTaskInstanceQueryImpl
        .processVariableValueGreaterThanOrEqual("Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThanOrEqual(String, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testProcessVariableValueGreaterThanOrEqual_whenNull() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueGreaterThanOrEqualResult = historicTaskInstanceQueryImpl
        .processVariableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThan(String, Object)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThan(String, Object)}
   */
  @Test
  public void testProcessVariableValueLessThan() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueLessThanResult = historicTaskInstanceQueryImpl
        .processVariableValueLessThan("Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLessThanResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThan(String, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThan(String, Object)}
   */
  @Test
  public void testProcessVariableValueLessThan_whenNull() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueLessThanResult = historicTaskInstanceQueryImpl
        .processVariableValueLessThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLessThanResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThanOrEqual(String, Object)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testProcessVariableValueLessThanOrEqual() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueLessThanOrEqualResult = historicTaskInstanceQueryImpl
        .processVariableValueLessThanOrEqual("Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLessThanOrEqualResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThanOrEqual(String, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testProcessVariableValueLessThanOrEqual_whenNull() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueLessThanOrEqualResult = historicTaskInstanceQueryImpl
        .processVariableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLessThanOrEqualResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLike(String, String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLike(String, String)}
   */
  @Test
  public void testProcessVariableValueLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueLikeResult = historicTaskInstanceQueryImpl
        .processVariableValueLike("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLikeResult);
  }

  /**
   * Test
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLikeIgnoreCase(String, String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testProcessVariableValueLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processVariableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDefinitionKey(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDefinitionKey(String)}
   */
  @Test
  public void testTaskDefinitionKey() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDefinitionKeyResult = historicTaskInstanceQueryImpl
        .taskDefinitionKey("Task Definition Key");

    // Assert
    assertEquals("Task Definition Key", historicTaskInstanceQueryImpl.getTaskDefinitionKey());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDefinitionKeyResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDefinitionKeyLike(String)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDefinitionKeyLike(String)}
   */
  @Test
  public void testTaskDefinitionKeyLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDefinitionKeyLikeResult = historicTaskInstanceQueryImpl
        .taskDefinitionKeyLike("Task Definition Key Like");

    // Assert
    assertEquals("Task Definition Key Like", historicTaskInstanceQueryImpl.getTaskDefinitionKeyLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDefinitionKeyLikeResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskPriority(Integer)}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskPriority(Integer)}
   */
  @Test
  public void testTaskPriority_givenHistoricTaskInstanceQueryImpl_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskPriorityResult = (new HistoricTaskInstanceQueryImpl()).taskPriority(1);

    // Assert
    assertTrue(actualTaskPriorityResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskPriorityResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskPriorityResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskPriorityResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskPriorityResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskMinPriority(Integer)}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskMinPriority(Integer)}
   */
  @Test
  public void testTaskMinPriority_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskMinPriorityResult = (new HistoricTaskInstanceQueryImpl()).taskMinPriority(1);

    // Assert
    assertTrue(actualTaskMinPriorityResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskMinPriorityResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskMinPriorityResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskMinPriorityResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskMinPriorityResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskMaxPriority(Integer)}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskMaxPriority(Integer)}
   */
  @Test
  public void testTaskMaxPriority_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskMaxPriorityResult = (new HistoricTaskInstanceQueryImpl()).taskMaxPriority(1);

    // Assert
    assertTrue(actualTaskMaxPriorityResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskMaxPriorityResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskMaxPriorityResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskMaxPriorityResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskMaxPriorityResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processFinished()}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processFinished()}
   */
  @Test
  public void testProcessFinished_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessFinishedResult = (new HistoricTaskInstanceQueryImpl()).processFinished();

    // Assert
    assertTrue(actualProcessFinishedResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessFinishedResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessFinishedResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessFinishedResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualProcessFinishedResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#processUnfinished()}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processUnfinished()}
   */
  @Test
  public void testProcessUnfinished_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessUnfinishedResult = (new HistoricTaskInstanceQueryImpl()).processUnfinished();

    // Assert
    assertTrue(actualProcessUnfinishedResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessUnfinishedResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualProcessUnfinishedResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualProcessUnfinishedResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualProcessUnfinishedResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDueDate(Date)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDueDate(Date)}
   */
  @Test
  public void testTaskDueDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskDueDate(dueDate));
    assertSame(dueDate, historicTaskInstanceQueryImpl.getDueDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDueDate(Date)}.
   * <ul>
   *   <li>When {@link java.sql.Date}.</li>
   *   <li>Then
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()} DueDate
   * is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDueDate(java.util.Date)}
   */
  @Test
  public void testTaskDueDate_whenDate_thenHistoricTaskInstanceQueryImplDueDateIsDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date dueDate = mock(java.sql.Date.class);

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
  public void testTaskDueAfter() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date dueAfter = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskDueAfter(dueAfter));
    assertSame(dueAfter, historicTaskInstanceQueryImpl.getDueAfter());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDueAfter(Date)}.
   * <ul>
   *   <li>When {@link java.sql.Date}.</li>
   *   <li>Then
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   * DueAfter is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDueAfter(java.util.Date)}
   */
  @Test
  public void testTaskDueAfter_whenDate_thenHistoricTaskInstanceQueryImplDueAfterIsDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date dueAfter = mock(java.sql.Date.class);

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
  public void testTaskDueBefore() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date dueBefore = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskDueBefore(dueBefore));
    assertSame(dueBefore, historicTaskInstanceQueryImpl.getDueBefore());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskDueBefore(Date)}.
   * <ul>
   *   <li>When {@link java.sql.Date}.</li>
   *   <li>Then
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   * DueBefore is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDueBefore(java.util.Date)}
   */
  @Test
  public void testTaskDueBefore_whenDate_thenHistoricTaskInstanceQueryImplDueBeforeIsDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date dueBefore = mock(java.sql.Date.class);

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
  public void testTaskCreatedOn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date creationDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedOn(creationDate));
    assertSame(creationDate, historicTaskInstanceQueryImpl.getCreationDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCreatedOn(Date)}.
   * <ul>
   *   <li>When {@link java.sql.Date}.</li>
   *   <li>Then
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   * CreationDate is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCreatedOn(java.util.Date)}
   */
  @Test
  public void testTaskCreatedOn_whenDate_thenHistoricTaskInstanceQueryImplCreationDateIsDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date creationDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedOn(creationDate));
    assertSame(creationDate, historicTaskInstanceQueryImpl.getCreationDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCreatedBefore(Date)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCreatedBefore(Date)}
   */
  @Test
  public void testTaskCreatedBefore() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date creationBeforeDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedBefore(creationBeforeDate));
    assertSame(creationBeforeDate, historicTaskInstanceQueryImpl.getCreationBeforeDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCreatedBefore(Date)}.
   * <ul>
   *   <li>Then
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   * CreationBeforeDate is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCreatedBefore(java.util.Date)}
   */
  @Test
  public void testTaskCreatedBefore_thenHistoricTaskInstanceQueryImplCreationBeforeDateIsDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date creationBeforeDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedBefore(creationBeforeDate));
    assertSame(creationBeforeDate, historicTaskInstanceQueryImpl.getCreationBeforeDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCreatedAfter(Date)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCreatedAfter(Date)}
   */
  @Test
  public void testTaskCreatedAfter() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date creationAfterDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedAfter(creationAfterDate));
    assertSame(creationAfterDate, historicTaskInstanceQueryImpl.getCreationAfterDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCreatedAfter(Date)}.
   * <ul>
   *   <li>Then
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   * CreationAfterDate is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCreatedAfter(java.util.Date)}
   */
  @Test
  public void testTaskCreatedAfter_thenHistoricTaskInstanceQueryImplCreationAfterDateIsDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date creationAfterDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedAfter(creationAfterDate));
    assertSame(creationAfterDate, historicTaskInstanceQueryImpl.getCreationAfterDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCompletedOn(Date)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCompletedOn(Date)}
   */
  @Test
  public void testTaskCompletedOn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date completedDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedOn(completedDate));
    assertSame(completedDate, historicTaskInstanceQueryImpl.getCompletedDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCompletedOn(Date)}.
   * <ul>
   *   <li>Then
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   * CompletedDate is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCompletedOn(java.util.Date)}
   */
  @Test
  public void testTaskCompletedOn_thenHistoricTaskInstanceQueryImplCompletedDateIsDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date completedDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedOn(completedDate));
    assertSame(completedDate, historicTaskInstanceQueryImpl.getCompletedDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCompletedBefore(Date)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCompletedBefore(Date)}
   */
  @Test
  public void testTaskCompletedBefore() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date completedBeforeDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedBefore(completedBeforeDate));
    assertSame(completedBeforeDate, historicTaskInstanceQueryImpl.getCompletedBeforeDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCompletedBefore(Date)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCompletedBefore(java.util.Date)}
   */
  @Test
  public void testTaskCompletedBefore2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date completedBeforeDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedBefore(completedBeforeDate));
    assertSame(completedBeforeDate, historicTaskInstanceQueryImpl.getCompletedBeforeDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCompletedAfter(Date)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCompletedAfter(Date)}
   */
  @Test
  public void testTaskCompletedAfter() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date completedAfterDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedAfter(completedAfterDate));
    assertSame(completedAfterDate, historicTaskInstanceQueryImpl.getCompletedAfterDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCompletedAfter(Date)}.
   * <ul>
   *   <li>Then
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   * CompletedAfterDate is {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCompletedAfter(java.util.Date)}
   */
  @Test
  public void testTaskCompletedAfter_thenHistoricTaskInstanceQueryImplCompletedAfterDateIsDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date completedAfterDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedAfter(completedAfterDate));
    assertSame(completedAfterDate, historicTaskInstanceQueryImpl.getCompletedAfterDate());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#withoutTaskDueDate()}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#withoutTaskDueDate()}
   */
  @Test
  public void testWithoutTaskDueDate_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualWithoutTaskDueDateResult = (new HistoricTaskInstanceQueryImpl())
        .withoutTaskDueDate();

    // Assert
    assertTrue(actualWithoutTaskDueDateResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualWithoutTaskDueDateResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualWithoutTaskDueDateResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualWithoutTaskDueDateResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualWithoutTaskDueDateResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCategory(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCategory(String)}
   */
  @Test
  public void testTaskCategory() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskCategoryResult = historicTaskInstanceQueryImpl.taskCategory("Category");

    // Assert
    assertEquals("Category", historicTaskInstanceQueryImpl.getCategory());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCategoryResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String)} with
   * {@code candidateUser}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String)}
   */
  @Test
  public void testTaskCandidateUserWithCandidateUser() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskCandidateUserResult = historicTaskInstanceQueryImpl
        .taskCandidateUser("2020-03-01");

    // Assert
    assertEquals("2020-03-01", historicTaskInstanceQueryImpl.getCandidateUser());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   * with {@code candidateUser}, {@code usersGroups}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  public void testTaskCandidateUserWithCandidateUserUsersGroups() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskCandidateUser(null, new ArrayList<>()));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   * with {@code candidateUser}, {@code usersGroups}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  public void testTaskCandidateUserWithCandidateUserUsersGroups_given42_whenArrayListAdd42() {
    // Arrange
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
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   * with {@code candidateUser}, {@code usersGroups}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  public void testTaskCandidateUserWithCandidateUserUsersGroups_givenFoo_whenArrayListAddFoo() {
    // Arrange
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
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   * with {@code candidateUser}, {@code usersGroups}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  public void testTaskCandidateUserWithCandidateUserUsersGroups_whenArrayList() {
    // Arrange
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
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String)} with
   * {@code candidateUser}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String)}
   */
  @Test
  public void testTaskCandidateUserWithCandidateUser_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskCandidateUser(null));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateGroup(String)}.
   * <ul>
   *   <li>Then {@link HistoricTaskInstanceQueryImpl} CandidateGroups size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroup(String)}
   */
  @Test
  public void testTaskCandidateGroup_thenHistoricTaskInstanceQueryImplCandidateGroupsSizeIsOne() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskCandidateGroupResult = historicTaskInstanceQueryImpl
        .taskCandidateGroup("2020-03-01");

    // Assert
    List<String> candidateGroups = historicTaskInstanceQueryImpl.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals("2020-03-01", candidateGroups.get(0));
    assertEquals("2020-03-01", historicTaskInstanceQueryImpl.getCandidateGroup());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateGroupResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateGroup(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroup(String)}
   */
  @Test
  public void testTaskCandidateGroup_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskCandidateGroup(null));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}.
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  public void testTaskCandidateGroupIn() {
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
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  public void testTaskCandidateGroupIn_given42_whenArrayListAdd42_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("Candidate group list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateGroupInResult = historicTaskInstanceQueryImpl
        .taskCandidateGroupIn(candidateGroups);

    // Assert
    assertTrue(actualTaskCandidateGroupInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskCandidateGroupInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskCandidateGroupInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskCandidateGroupInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskCandidateGroupInResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  public void testTaskCandidateGroupIn_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("Candidate group list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateGroupInResult = historicTaskInstanceQueryImpl
        .taskCandidateGroupIn(candidateGroups);

    // Assert
    assertTrue(actualTaskCandidateGroupInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskCandidateGroupInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskCandidateGroupInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskCandidateGroupInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskCandidateGroupInResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
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
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskInvolvedUser(String)}
   */
  @Test
  public void testTaskInvolvedUser() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskInvolvedUserResult = historicTaskInstanceQueryImpl
        .taskInvolvedUser("Involved User");

    // Assert
    assertEquals("Involved User", historicTaskInstanceQueryImpl.getInvolvedUser());
    assertSame(historicTaskInstanceQueryImpl, actualTaskInvolvedUserResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  public void testTaskInvolvedGroupsIn_given42_whenArrayListAdd42_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("42");
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    HistoricTaskInstanceQuery actualTaskInvolvedGroupsInResult = historicTaskInstanceQueryImpl
        .taskInvolvedGroupsIn(involvedGroups);

    // Assert
    assertTrue(actualTaskInvolvedGroupsInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskInvolvedGroupsInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskInvolvedGroupsInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskInvolvedGroupsInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskInvolvedGroupsInResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  public void testTaskInvolvedGroupsIn_thenReturnOrderByIsResIdAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    HistoricTaskInstanceQuery actualTaskInvolvedGroupsInResult = historicTaskInstanceQueryImpl
        .taskInvolvedGroupsIn(involvedGroups);

    // Assert
    assertTrue(actualTaskInvolvedGroupsInResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskInvolvedGroupsInResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskInvolvedGroupsInResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskInvolvedGroupsInResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskInvolvedGroupsInResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
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
   *   <li>Then {@link HistoricTaskInstanceQueryImpl} TenantId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskTenantId(String)}
   */
  @Test
  public void testTaskTenantId_when42_thenHistoricTaskInstanceQueryImplTenantIdIs42() {
    // Arrange and Act
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
  public void testTaskTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskTenantId(null));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskTenantIdLike(String)}.
   * <ul>
   *   <li>Then {@link HistoricTaskInstanceQueryImpl} TenantIdLike is
   * {@code Tenant Id Like}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskTenantIdLike(String)}
   */
  @Test
  public void testTaskTenantIdLike_thenHistoricTaskInstanceQueryImplTenantIdLikeIsTenantIdLike() {
    // Arrange and Act
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
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskTenantIdLike(String)}
   */
  @Test
  public void testTaskTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskTenantIdLike(null));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#taskWithoutTenantId()}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskWithoutTenantId()}
   */
  @Test
  public void testTaskWithoutTenantId_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskWithoutTenantIdResult = (new HistoricTaskInstanceQueryImpl())
        .taskWithoutTenantId();

    // Assert
    assertTrue(actualTaskWithoutTenantIdResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskWithoutTenantIdResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualTaskWithoutTenantIdResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualTaskWithoutTenantIdResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualTaskWithoutTenantIdResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#locale(String)}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#locale(String)}
   */
  @Test
  public void testLocale() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualLocaleResult = historicTaskInstanceQueryImpl.locale("en");

    // Assert
    assertEquals("en", historicTaskInstanceQueryImpl.getLocale());
    assertSame(historicTaskInstanceQueryImpl, actualLocaleResult);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#withLocalizationFallback()}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#withLocalizationFallback()}
   */
  @Test
  public void testWithLocalizationFallback_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualWithLocalizationFallbackResult = (new HistoricTaskInstanceQueryImpl())
        .withLocalizationFallback();

    // Assert
    assertTrue(actualWithLocalizationFallbackResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualWithLocalizationFallbackResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualWithLocalizationFallbackResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualWithLocalizationFallbackResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualWithLocalizationFallbackResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#includeTaskLocalVariables()}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#includeTaskLocalVariables()}
   */
  @Test
  public void testIncludeTaskLocalVariables_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualIncludeTaskLocalVariablesResult = (new HistoricTaskInstanceQueryImpl())
        .includeTaskLocalVariables();

    // Assert
    assertTrue(actualIncludeTaskLocalVariablesResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualIncludeTaskLocalVariablesResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualIncludeTaskLocalVariablesResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualIncludeTaskLocalVariablesResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualIncludeTaskLocalVariablesResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#includeProcessVariables()}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#includeProcessVariables()}
   */
  @Test
  public void testIncludeProcessVariables_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualIncludeProcessVariablesResult = (new HistoricTaskInstanceQueryImpl())
        .includeProcessVariables();

    // Assert
    assertTrue(actualIncludeProcessVariablesResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualIncludeProcessVariablesResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualIncludeProcessVariablesResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualIncludeProcessVariablesResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualIncludeProcessVariablesResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#limitTaskVariables(Integer)}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#limitTaskVariables(Integer)}
   */
  @Test
  public void testLimitTaskVariables_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualLimitTaskVariablesResult = (new HistoricTaskInstanceQueryImpl())
        .limitTaskVariables(1);

    // Assert
    assertTrue(actualLimitTaskVariablesResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualLimitTaskVariablesResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualLimitTaskVariablesResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualLimitTaskVariablesResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualLimitTaskVariablesResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#or()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#or()}
   */
  @Test
  public void testOr_givenHistoricTaskInstanceQueryImpl_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualOrResult = (new HistoricTaskInstanceQueryImpl()).or();

    // Assert
    assertTrue(actualOrResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualOrResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualOrResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", ((HistoricTaskInstanceQueryImpl) actualOrResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualOrResult).orderBy);
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#endOr()}.
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#endOr()}
   */
  @Test
  public void testEndOr() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new HistoricTaskInstanceQueryImpl()).endOr());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#localize(HistoricTaskInstance)}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>Then calls
   * {@link HistoricTaskInstanceEntityImpl#setLocalizedDescription(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#localize(HistoricTaskInstance)}
   */
  @Test
  public void testLocalize_givenHistoricTaskInstanceQueryImpl_thenCallsSetLocalizedDescription() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    HistoricTaskInstanceEntityImpl task = mock(HistoricTaskInstanceEntityImpl.class);
    doNothing().when(task).setLocalizedDescription(Mockito.<String>any());
    doNothing().when(task).setLocalizedName(Mockito.<String>any());

    // Act
    historicTaskInstanceQueryImpl.localize(task);

    // Assert that nothing has changed
    verify(task).setLocalizedDescription(isNull());
    verify(task).setLocalizedName(isNull());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#localize(HistoricTaskInstance)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#localize(HistoricTaskInstance)}
   */
  @Test
  public void testLocalize_givenNull() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.locale("en");
    HistoricTaskInstanceEntityImpl task = mock(HistoricTaskInstanceEntityImpl.class);
    when(task.getProcessDefinitionId()).thenReturn(null);
    doNothing().when(task).setLocalizedDescription(Mockito.<String>any());
    doNothing().when(task).setLocalizedName(Mockito.<String>any());

    // Act
    historicTaskInstanceQueryImpl.localize(task);

    // Assert that nothing has changed
    verify(task).getProcessDefinitionId();
    verify(task).setLocalizedDescription(isNull());
    verify(task).setLocalizedName(isNull());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#localize(HistoricTaskInstance)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#localize(HistoricTaskInstance)}
   */
  @Test
  public void testLocalize_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.locale("en");
    HistoricTaskInstanceEntityImpl task = mock(HistoricTaskInstanceEntityImpl.class);
    when(task.getTaskDefinitionKey()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(task.getProcessDefinitionId()).thenReturn("42");
    doNothing().when(task).setLocalizedDescription(Mockito.<String>any());
    doNothing().when(task).setLocalizedName(Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.localize(task));
    verify(task).getProcessDefinitionId();
    verify(task).getTaskDefinitionKey();
    verify(task).setLocalizedDescription(isNull());
    verify(task).setLocalizedName(isNull());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#getMssqlOrDB2OrderBy()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()} orderBy
   * {@link QueryProperty}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  public void testGetMssqlOrDB2OrderBy_givenHistoricTaskInstanceQueryImplOrderByQueryProperty() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));
    historicTaskInstanceQueryImpl.addOrder("RES.ID_ asc", AbstractQuery.SORTORDER_ASC,
        AbstractQuery.NullHandlingOnOrder.NULLS_FIRST);

    // Act and Assert
    assertEquals("TEMPRES_ID_ asc asc", historicTaskInstanceQueryImpl.getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#getMssqlOrDB2OrderBy()}.
   * <ul>
   *   <li>Then return {@code TEMPRES_ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
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
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  public void testGetMssqlOrDB2OrderBy_thenReturnTempresIdAscAsc() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.addOrder("RES.ID_ asc", AbstractQuery.SORTORDER_ASC,
        AbstractQuery.NullHandlingOnOrder.NULLS_FIRST);

    // Act and Assert
    assertEquals("TEMPRES_ID_ asc asc", historicTaskInstanceQueryImpl.getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#getCandidateGroups()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()} orderBy
   * {@link QueryProperty}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#getCandidateGroups()}
   */
  @Test
  public void testGetCandidateGroups_givenHistoricTaskInstanceQueryImplOrderByQueryProperty() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertNull(historicTaskInstanceQueryImpl.getCandidateGroups());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryImpl#getCandidateGroups()}.
   * <ul>
   *   <li>Given
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricTaskInstanceQueryImpl#getCandidateGroups()}
   */
  @Test
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
