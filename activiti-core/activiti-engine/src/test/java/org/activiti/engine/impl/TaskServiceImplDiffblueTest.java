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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplDiffblueTest {
  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  @InjectMocks
  private TaskServiceImpl taskServiceImpl;

  /**
   * Test {@link TaskServiceImpl#TaskServiceImpl(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test:
   * {@link TaskServiceImpl#TaskServiceImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewTaskServiceImpl() {
    // Arrange and Act
    TaskServiceImpl actualTaskServiceImpl = new TaskServiceImpl(new JtaProcessEngineConfiguration());

    // Assert
    assertNull(actualTaskServiceImpl.getCommandExecutor());
    assertNull(
        ((TaskServiceImpl) actualTaskServiceImpl.processEngineConfiguration.getTaskService()).getCommandExecutor());
  }

  /**
   * Test {@link TaskServiceImpl#createTaskQuery()}.
   * <p>
   * Method under test: {@link TaskServiceImpl#createTaskQuery()}
   */
  @Test
  public void testCreateTaskQuery() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    TaskQuery actualCreateTaskQueryResult = (new TaskServiceImpl(processEngineConfiguration)).createTaskQuery();

    // Assert
    assertTrue(actualCreateTaskQueryResult instanceof TaskQueryImpl);
    assertEquals("RES.ID_ asc", ((TaskQueryImpl) actualCreateTaskQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((TaskQueryImpl) actualCreateTaskQueryResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", ((TaskQueryImpl) actualCreateTaskQueryResult).getMssqlOrDB2OrderBy());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getMaxPriority());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getMinPriority());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getPriority());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getTaskVariablesLimit());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getParameter());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDatabaseType());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getAssignee());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getAssigneeLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getAssigneeLikeIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCandidateGroup());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCandidateUser());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCategory());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDelegationStateString());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDeploymentId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDescription());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDescriptionLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDescriptionLikeIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getExecutionId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getInvolvedUser());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getKey());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getKeyLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getLocale());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getName());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getNameLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getNameLikeIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getOwner());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getOwnerLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getOwnerLikeIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionKey());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionKeyLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionName());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionNameLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessInstanceBusinessKey());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessInstanceBusinessKeyLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessInstanceId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getTaskId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getTaskParentTaskId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getTenantId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getTenantIdLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getUserIdForCandidateAndAssignee());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).orderBy);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCreateTime());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCreateTimeAfter());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCreateTimeBefore());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDueAfter());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDueBefore());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDueDate());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getAssigneeIds());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCandidateGroups());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDeploymentIds());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getInvolvedGroups());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getNameList());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getNameListIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessCategoryInList());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessCategoryNotInList());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionKeys());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessInstanceIds());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).candidateGroups);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).nullHandlingOnOrder);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).resultType);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).currentOrQueryObject);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).commandContext);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).commandExecutor);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getSuspensionState());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).orderProperty);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDelegationState());
    assertEquals(0, ((TaskQueryImpl) actualCreateTaskQueryResult).getFirstResult());
    assertEquals(1, ((TaskQueryImpl) actualCreateTaskQueryResult).getFirstRow());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).hasLocalQueryVariableValue());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).hasNonLocalQueryVariableValue());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).getExcludeSubtasks());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).getNoDelegationState());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).getUnassigned());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).isBothCandidateAndAssigned());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).isIncludeProcessVariables());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).isIncludeTaskLocalVariables());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).isOrActive());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).isWithoutDueDate());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).isWithoutTenantId());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).withLocalizationFallback);
    assertTrue(((TaskQueryImpl) actualCreateTaskQueryResult).getQueryVariableValues().isEmpty());
    assertTrue(((TaskQueryImpl) actualCreateTaskQueryResult).getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE, ((TaskQueryImpl) actualCreateTaskQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE, ((TaskQueryImpl) actualCreateTaskQueryResult).getMaxResults());
  }

  /**
   * Test {@link TaskServiceImpl#createTaskQuery()}.
   * <ul>
   *   <li>Then return {@link TaskQueryImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskServiceImpl#createTaskQuery()}
   */
  @Test
  public void testCreateTaskQuery_thenReturnTaskQueryImpl() {
    // Arrange and Act
    TaskQuery actualCreateTaskQueryResult = (new TaskServiceImpl(new JtaProcessEngineConfiguration()))
        .createTaskQuery();

    // Assert
    assertTrue(actualCreateTaskQueryResult instanceof TaskQueryImpl);
    assertEquals("RES.ID_ asc", ((TaskQueryImpl) actualCreateTaskQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((TaskQueryImpl) actualCreateTaskQueryResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", ((TaskQueryImpl) actualCreateTaskQueryResult).getMssqlOrDB2OrderBy());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getMaxPriority());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getMinPriority());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getPriority());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getTaskVariablesLimit());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getParameter());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDatabaseType());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getAssignee());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getAssigneeLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getAssigneeLikeIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCandidateGroup());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCandidateUser());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCategory());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDelegationStateString());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDeploymentId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDescription());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDescriptionLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDescriptionLikeIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getExecutionId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getInvolvedUser());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getKey());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getKeyLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getLocale());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getName());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getNameLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getNameLikeIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getOwner());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getOwnerLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getOwnerLikeIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionKey());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionKeyLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionName());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionNameLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessInstanceBusinessKey());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessInstanceBusinessKeyLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessInstanceId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getTaskId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getTaskParentTaskId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getTenantId());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getTenantIdLike());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getUserIdForCandidateAndAssignee());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).orderBy);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCreateTime());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCreateTimeAfter());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCreateTimeBefore());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDueAfter());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDueBefore());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDueDate());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getAssigneeIds());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getCandidateGroups());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDeploymentIds());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getInvolvedGroups());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getNameList());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getNameListIgnoreCase());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessCategoryInList());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessCategoryNotInList());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessDefinitionKeys());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getProcessInstanceIds());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).candidateGroups);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).nullHandlingOnOrder);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).resultType);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).currentOrQueryObject);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).commandContext);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).commandExecutor);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getSuspensionState());
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).orderProperty);
    assertNull(((TaskQueryImpl) actualCreateTaskQueryResult).getDelegationState());
    assertEquals(0, ((TaskQueryImpl) actualCreateTaskQueryResult).getFirstResult());
    assertEquals(1, ((TaskQueryImpl) actualCreateTaskQueryResult).getFirstRow());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).hasLocalQueryVariableValue());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).hasNonLocalQueryVariableValue());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).getExcludeSubtasks());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).getNoDelegationState());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).getUnassigned());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).isBothCandidateAndAssigned());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).isIncludeProcessVariables());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).isIncludeTaskLocalVariables());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).isOrActive());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).isWithoutDueDate());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).isWithoutTenantId());
    assertFalse(((TaskQueryImpl) actualCreateTaskQueryResult).withLocalizationFallback);
    assertTrue(((TaskQueryImpl) actualCreateTaskQueryResult).getQueryVariableValues().isEmpty());
    assertTrue(((TaskQueryImpl) actualCreateTaskQueryResult).getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE, ((TaskQueryImpl) actualCreateTaskQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE, ((TaskQueryImpl) actualCreateTaskQueryResult).getMaxResults());
  }

  /**
   * Test {@link TaskServiceImpl#createNativeTaskQuery()}.
   * <p>
   * Method under test: {@link TaskServiceImpl#createNativeTaskQuery()}
   */
  @Test
  public void testCreateNativeTaskQuery() {
    // Arrange and Act
    NativeTaskQuery actualCreateNativeTaskQueryResult = (new TaskServiceImpl(new JtaProcessEngineConfiguration()))
        .createNativeTaskQuery();

    // Assert
    assertTrue(actualCreateNativeTaskQueryResult instanceof NativeTaskQueryImpl);
    assertNull(((NativeTaskQueryImpl) actualCreateNativeTaskQueryResult).resultType);
    assertNull(((NativeTaskQueryImpl) actualCreateNativeTaskQueryResult).commandContext);
    assertNull(((NativeTaskQueryImpl) actualCreateNativeTaskQueryResult).commandExecutor);
    assertEquals(0, ((NativeTaskQueryImpl) actualCreateNativeTaskQueryResult).firstResult);
    assertTrue(((NativeTaskQueryImpl) actualCreateNativeTaskQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE, ((NativeTaskQueryImpl) actualCreateNativeTaskQueryResult).maxResults);
  }

  /**
   * Test {@link TaskServiceImpl#createNativeTaskQuery()}.
   * <p>
   * Method under test: {@link TaskServiceImpl#createNativeTaskQuery()}
   */
  @Test
  public void testCreateNativeTaskQuery2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    NativeTaskQuery actualCreateNativeTaskQueryResult = (new TaskServiceImpl(processEngineConfiguration))
        .createNativeTaskQuery();

    // Assert
    assertTrue(actualCreateNativeTaskQueryResult instanceof NativeTaskQueryImpl);
    assertNull(((NativeTaskQueryImpl) actualCreateNativeTaskQueryResult).resultType);
    assertNull(((NativeTaskQueryImpl) actualCreateNativeTaskQueryResult).commandContext);
    assertNull(((NativeTaskQueryImpl) actualCreateNativeTaskQueryResult).commandExecutor);
    assertEquals(0, ((NativeTaskQueryImpl) actualCreateNativeTaskQueryResult).firstResult);
    assertTrue(((NativeTaskQueryImpl) actualCreateNativeTaskQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE, ((NativeTaskQueryImpl) actualCreateNativeTaskQueryResult).maxResults);
  }

  /**
   * Test {@link TaskServiceImpl#setVariable(String, String, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskServiceImpl#setVariable(String, String, Object)}
   */
  @Test
  public void testSetVariable_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> taskServiceImpl.setVariable("42", null, JSONObject.NULL));
  }

  /**
   * Test {@link TaskServiceImpl#setVariableLocal(String, String, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskServiceImpl#setVariableLocal(String, String, Object)}
   */
  @Test
  public void testSetVariableLocal_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> taskServiceImpl.setVariableLocal("42", null, JSONObject.NULL));
  }
}
