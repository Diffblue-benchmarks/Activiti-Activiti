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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricDetailQuery;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.history.NativeHistoricActivityInstanceQuery;
import org.activiti.engine.history.NativeHistoricDetailQuery;
import org.activiti.engine.history.NativeHistoricProcessInstanceQuery;
import org.activiti.engine.history.NativeHistoricTaskInstanceQuery;
import org.activiti.engine.history.NativeHistoricVariableInstanceQuery;
import org.activiti.engine.history.ProcessInstanceHistoryLogQuery;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoryServiceImplDiffblueTest {
  @InjectMocks
  private HistoryServiceImpl historyServiceImpl;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test
   * {@link HistoryServiceImpl#HistoryServiceImpl(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#HistoryServiceImpl(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewHistoryServiceImpl() {
    // Arrange and Act
    HistoryServiceImpl actualHistoryServiceImpl = new HistoryServiceImpl(new JtaProcessEngineConfiguration());

    // Assert
    assertNull(actualHistoryServiceImpl.getCommandExecutor());
    assertNull(((HistoryServiceImpl) actualHistoryServiceImpl.processEngineConfiguration.getHistoryService())
        .getCommandExecutor());
  }

  /**
   * Test {@link HistoryServiceImpl#createHistoricProcessInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createHistoricProcessInstanceQuery()}
   */
  @Test
  public void testCreateHistoricProcessInstanceQuery() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualCreateHistoricProcessInstanceQueryResult = (new HistoryServiceImpl(
        new JtaProcessEngineConfiguration())).createHistoricProcessInstanceQuery();

    // Assert
    assertTrue(actualCreateHistoricProcessInstanceQueryResult instanceof HistoricProcessInstanceQueryImpl);
    assertEquals("RES.ID_ asc",
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getMssqlOrDB2OrderBy());
    assertEquals("null:%:%", ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .getProcessDefinitionIdLike());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .getProcessDefinitionVersion());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .getProcessInstanceVariablesLimit());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getParameter());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getDatabaseType());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getBusinessKey());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getDeploymentId());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getInvolvedUser());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getName());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getNameLike());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getNameLikeIgnoreCase());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .getProcessDefinitionCategory());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getProcessDefinitionId());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getProcessDefinitionKey());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getProcessDefinitionName());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getProcessInstanceId());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getStartedBy());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .getSuperProcessInstanceId());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getTenantId());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getTenantIdLike());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).orderBy);
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).locale);
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getFinishedAfter());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getFinishedBefore());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getStartedAfter());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getStartedBefore());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getDeploymentIds());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getInvolvedGroups());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .getProcessDefinitionKeyIn());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getProcessKeyNotIn());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getProcessInstanceIds());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).nullHandlingOnOrder);
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).resultType);
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).currentOrQueryObject);
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).commandContext);
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).commandExecutor);
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).orderProperty);
    assertEquals(0,
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getFirstResult());
    assertEquals(1, ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getFirstRow());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .hasLocalQueryVariableValue());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .hasNonLocalQueryVariableValue());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isDeleted());
    assertFalse(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isExcludeSubprocesses());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isFinished());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .isIncludeProcessVariables());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isNotDeleted());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isOpen());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isUnfinished());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isWithException());
    assertFalse(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isWithoutTenantId());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).inOrStatement);
    assertFalse(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).withLocalizationFallback);
    assertTrue(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getQueryVariableValues()
            .isEmpty());
    assertTrue(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getOrQueryObjects()
        .isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getMaxResults());
  }

  /**
   * Test {@link HistoryServiceImpl#createHistoricProcessInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createHistoricProcessInstanceQuery()}
   */
  @Test
  public void testCreateHistoricProcessInstanceQuery2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoricProcessInstanceQuery actualCreateHistoricProcessInstanceQueryResult = (new HistoryServiceImpl(
        processEngineConfiguration)).createHistoricProcessInstanceQuery();

    // Assert
    assertTrue(actualCreateHistoricProcessInstanceQueryResult instanceof HistoricProcessInstanceQueryImpl);
    assertEquals("RES.ID_ asc",
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getMssqlOrDB2OrderBy());
    assertEquals("null:%:%", ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .getProcessDefinitionIdLike());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .getProcessDefinitionVersion());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .getProcessInstanceVariablesLimit());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getParameter());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getDatabaseType());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getBusinessKey());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getDeploymentId());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getInvolvedUser());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getName());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getNameLike());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getNameLikeIgnoreCase());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .getProcessDefinitionCategory());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getProcessDefinitionId());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getProcessDefinitionKey());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getProcessDefinitionName());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getProcessInstanceId());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getStartedBy());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .getSuperProcessInstanceId());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getTenantId());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getTenantIdLike());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).orderBy);
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).locale);
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getFinishedAfter());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getFinishedBefore());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getStartedAfter());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getStartedBefore());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getDeploymentIds());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getInvolvedGroups());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .getProcessDefinitionKeyIn());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getProcessKeyNotIn());
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getProcessInstanceIds());
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).nullHandlingOnOrder);
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).resultType);
    assertNull(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).currentOrQueryObject);
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).commandContext);
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).commandExecutor);
    assertNull(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).orderProperty);
    assertEquals(0,
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getFirstResult());
    assertEquals(1, ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getFirstRow());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .hasLocalQueryVariableValue());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .hasNonLocalQueryVariableValue());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isDeleted());
    assertFalse(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isExcludeSubprocesses());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isFinished());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult)
        .isIncludeProcessVariables());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isNotDeleted());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isOpen());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isUnfinished());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isWithException());
    assertFalse(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).isWithoutTenantId());
    assertFalse(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).inOrStatement);
    assertFalse(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).withLocalizationFallback);
    assertTrue(
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getQueryVariableValues()
            .isEmpty());
    assertTrue(((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getOrQueryObjects()
        .isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricProcessInstanceQueryImpl) actualCreateHistoricProcessInstanceQueryResult).getMaxResults());
  }

  /**
   * Test {@link HistoryServiceImpl#createHistoricActivityInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createHistoricActivityInstanceQuery()}
   */
  @Test
  public void testCreateHistoricActivityInstanceQuery() {
    // Arrange and Act
    HistoricActivityInstanceQuery actualCreateHistoricActivityInstanceQueryResult = (new HistoryServiceImpl(
        new JtaProcessEngineConfiguration())).createHistoricActivityInstanceQuery();

    // Assert
    assertTrue(actualCreateHistoricActivityInstanceQueryResult instanceof HistoricActivityInstanceQueryImpl);
    assertEquals("RES.ID_ asc",
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getOrderByColumns());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getParameter());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getDatabaseType());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getActivityId());
    assertNull(
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getActivityInstanceId());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getActivityName());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getActivityType());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getAssignee());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getDeleteReason());
    assertNull(
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getDeleteReasonLike());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getExecutionId());
    assertNull(
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getProcessDefinitionId());
    assertNull(
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getProcessInstanceId());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getTenantId());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getTenantIdLike());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).orderBy);
    assertNull(
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).nullHandlingOnOrder);
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).resultType);
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).commandContext);
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).commandExecutor);
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).orderProperty);
    assertEquals(0,
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getFirstResult());
    assertEquals(1,
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getFirstRow());
    assertFalse(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).isFinished());
    assertFalse(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).isUnfinished());
    assertFalse(
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getMaxResults());
  }

  /**
   * Test {@link HistoryServiceImpl#createHistoricActivityInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createHistoricActivityInstanceQuery()}
   */
  @Test
  public void testCreateHistoricActivityInstanceQuery2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoricActivityInstanceQuery actualCreateHistoricActivityInstanceQueryResult = (new HistoryServiceImpl(
        processEngineConfiguration)).createHistoricActivityInstanceQuery();

    // Assert
    assertTrue(actualCreateHistoricActivityInstanceQueryResult instanceof HistoricActivityInstanceQueryImpl);
    assertEquals("RES.ID_ asc",
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getOrderByColumns());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getParameter());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getDatabaseType());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getActivityId());
    assertNull(
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getActivityInstanceId());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getActivityName());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getActivityType());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getAssignee());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getDeleteReason());
    assertNull(
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getDeleteReasonLike());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getExecutionId());
    assertNull(
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getProcessDefinitionId());
    assertNull(
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getProcessInstanceId());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getTenantId());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getTenantIdLike());
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).orderBy);
    assertNull(
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).nullHandlingOnOrder);
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).resultType);
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).commandContext);
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).commandExecutor);
    assertNull(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).orderProperty);
    assertEquals(0,
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getFirstResult());
    assertEquals(1,
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getFirstRow());
    assertFalse(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).isFinished());
    assertFalse(((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).isUnfinished());
    assertFalse(
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricActivityInstanceQueryImpl) actualCreateHistoricActivityInstanceQueryResult).getMaxResults());
  }

  /**
   * Test {@link HistoryServiceImpl#createHistoricTaskInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createHistoricTaskInstanceQuery()}
   */
  @Test
  public void testCreateHistoricTaskInstanceQuery() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoricTaskInstanceQuery actualCreateHistoricTaskInstanceQueryResult = (new HistoryServiceImpl(
        processEngineConfiguration)).createHistoricTaskInstanceQuery();

    // Assert
    assertTrue(actualCreateHistoricTaskInstanceQueryResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskMaxPriority());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskMinPriority());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskPriority());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskVariablesLimit());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getParameter());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getDatabaseType());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCandidateGroup());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCandidateUser());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCategory());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getDeploymentId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getExecutionId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getInvolvedUser());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getLocale());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessDefinitionId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessDefinitionKey());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessDefinitionKeyLike());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult)
        .getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessDefinitionName());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessDefinitionNameLike());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessInstanceBusinessKey());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult)
        .getProcessInstanceBusinessKeyLike());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult)
        .getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessInstanceId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskAssignee());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskAssigneeLike());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskAssigneeLikeIgnoreCase());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskDefinitionKey());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskDefinitionKeyLike());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskDeleteReason());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskDeleteReasonLike());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskDescription());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskDescriptionLike());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult)
        .getTaskDescriptionLikeIgnoreCase());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskName());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskNameLike());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskNameLikeIgnoreCase());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskOwner());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskOwnerLike());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskOwnerLikeIgnoreCase());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskParentTaskId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTenantId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTenantIdLike());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).orderBy);
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCompletedAfterDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCompletedBeforeDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCompletedDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCreationAfterDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCreationBeforeDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCreationDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getDueAfter());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getDueBefore());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getDueDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCandidateGroups());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getDeploymentIds());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getInvolvedGroups());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessCategoryInList());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessCategoryNotInList());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessDefinitionKeys());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessInstanceIds());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskAssigneeIds());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskNameList());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskNameListIgnoreCase());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).nullHandlingOnOrder);
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).resultType);
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).currentOrQueryObject);
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).commandContext);
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).commandExecutor);
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).orderProperty);
    assertEquals(0, ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getFirstResult());
    assertEquals(1, ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getFirstRow());
    assertFalse(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).hasLocalQueryVariableValue());
    assertFalse(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).hasNonLocalQueryVariableValue());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isFinished());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isInOrStatement());
    assertFalse(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isIncludeProcessVariables());
    assertFalse(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isIncludeTaskLocalVariables());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isProcessFinished());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isProcessUnfinished());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isUnfinished());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isWithoutDueDate());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isWithoutTenantId());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).withLocalizationFallback);
    assertTrue(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getQueryVariableValues()
        .isEmpty());
    assertTrue(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getMaxResults());
  }

  /**
   * Test {@link HistoryServiceImpl#createHistoricTaskInstanceQuery()}.
   * <ul>
   *   <li>Then return {@link HistoricTaskInstanceQueryImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createHistoricTaskInstanceQuery()}
   */
  @Test
  public void testCreateHistoricTaskInstanceQuery_thenReturnHistoricTaskInstanceQueryImpl() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualCreateHistoricTaskInstanceQueryResult = (new HistoryServiceImpl(
        new JtaProcessEngineConfiguration())).createHistoricTaskInstanceQuery();

    // Assert
    assertTrue(actualCreateHistoricTaskInstanceQueryResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskMaxPriority());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskMinPriority());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskPriority());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskVariablesLimit());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getParameter());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getDatabaseType());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCandidateGroup());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCandidateUser());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCategory());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getDeploymentId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getExecutionId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getInvolvedUser());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getLocale());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessDefinitionId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessDefinitionKey());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessDefinitionKeyLike());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult)
        .getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessDefinitionName());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessDefinitionNameLike());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessInstanceBusinessKey());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult)
        .getProcessInstanceBusinessKeyLike());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult)
        .getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessInstanceId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskAssignee());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskAssigneeLike());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskAssigneeLikeIgnoreCase());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskDefinitionKey());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskDefinitionKeyLike());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskDeleteReason());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskDeleteReasonLike());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskDescription());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskDescriptionLike());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult)
        .getTaskDescriptionLikeIgnoreCase());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskName());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskNameLike());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskNameLikeIgnoreCase());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskOwner());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskOwnerLike());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskOwnerLikeIgnoreCase());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskParentTaskId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTenantId());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTenantIdLike());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).orderBy);
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCompletedAfterDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCompletedBeforeDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCompletedDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCreationAfterDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCreationBeforeDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCreationDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getDueAfter());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getDueBefore());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getDueDate());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getCandidateGroups());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getDeploymentIds());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getInvolvedGroups());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessCategoryInList());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessCategoryNotInList());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessDefinitionKeys());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getProcessInstanceIds());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskAssigneeIds());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskNameList());
    assertNull(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getTaskNameListIgnoreCase());
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).nullHandlingOnOrder);
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).resultType);
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).currentOrQueryObject);
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).commandContext);
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).commandExecutor);
    assertNull(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).orderProperty);
    assertEquals(0, ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getFirstResult());
    assertEquals(1, ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getFirstRow());
    assertFalse(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).hasLocalQueryVariableValue());
    assertFalse(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).hasNonLocalQueryVariableValue());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isFinished());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isInOrStatement());
    assertFalse(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isIncludeProcessVariables());
    assertFalse(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isIncludeTaskLocalVariables());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isProcessFinished());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isProcessUnfinished());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isUnfinished());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isWithoutDueDate());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).isWithoutTenantId());
    assertFalse(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).withLocalizationFallback);
    assertTrue(((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getQueryVariableValues()
        .isEmpty());
    assertTrue(
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricTaskInstanceQueryImpl) actualCreateHistoricTaskInstanceQueryResult).getMaxResults());
  }

  /**
   * Test {@link HistoryServiceImpl#createHistoricDetailQuery()}.
   * <p>
   * Method under test: {@link HistoryServiceImpl#createHistoricDetailQuery()}
   */
  @Test
  public void testCreateHistoricDetailQuery() {
    // Arrange and Act
    HistoricDetailQuery actualCreateHistoricDetailQueryResult = (new HistoryServiceImpl(
        new JtaProcessEngineConfiguration())).createHistoricDetailQuery();

    // Assert
    assertTrue(actualCreateHistoricDetailQueryResult instanceof HistoricDetailQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getOrderByColumns());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getParameter());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getDatabaseType());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getActivityId());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getActivityInstanceId());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getExecutionId());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getId());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getProcessInstanceId());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getTaskId());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getType());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).orderBy);
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).nullHandlingOnOrder);
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).resultType);
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).commandContext);
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).commandExecutor);
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).orderProperty);
    assertEquals(0, ((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getFirstResult());
    assertEquals(1, ((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getFirstRow());
    assertFalse(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getExcludeTaskRelated());
    assertEquals(Integer.MAX_VALUE, ((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE, ((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getMaxResults());
  }

  /**
   * Test {@link HistoryServiceImpl#createHistoricDetailQuery()}.
   * <p>
   * Method under test: {@link HistoryServiceImpl#createHistoricDetailQuery()}
   */
  @Test
  public void testCreateHistoricDetailQuery2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoricDetailQuery actualCreateHistoricDetailQueryResult = (new HistoryServiceImpl(processEngineConfiguration))
        .createHistoricDetailQuery();

    // Assert
    assertTrue(actualCreateHistoricDetailQueryResult instanceof HistoricDetailQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getOrderByColumns());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getParameter());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getDatabaseType());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getActivityId());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getActivityInstanceId());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getExecutionId());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getId());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getProcessInstanceId());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getTaskId());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getType());
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).orderBy);
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).nullHandlingOnOrder);
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).resultType);
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).commandContext);
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).commandExecutor);
    assertNull(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).orderProperty);
    assertEquals(0, ((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getFirstResult());
    assertEquals(1, ((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getFirstRow());
    assertFalse(((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getExcludeTaskRelated());
    assertEquals(Integer.MAX_VALUE, ((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE, ((HistoricDetailQueryImpl) actualCreateHistoricDetailQueryResult).getMaxResults());
  }

  /**
   * Test {@link HistoryServiceImpl#createNativeHistoricDetailQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createNativeHistoricDetailQuery()}
   */
  @Test
  public void testCreateNativeHistoricDetailQuery() {
    // Arrange and Act
    NativeHistoricDetailQuery actualCreateNativeHistoricDetailQueryResult = (new HistoryServiceImpl(
        new JtaProcessEngineConfiguration())).createNativeHistoricDetailQuery();

    // Assert
    assertTrue(actualCreateNativeHistoricDetailQueryResult instanceof NativeHistoricDetailQueryImpl);
    assertNull(((NativeHistoricDetailQueryImpl) actualCreateNativeHistoricDetailQueryResult).resultType);
    assertNull(((NativeHistoricDetailQueryImpl) actualCreateNativeHistoricDetailQueryResult).commandContext);
    assertNull(((NativeHistoricDetailQueryImpl) actualCreateNativeHistoricDetailQueryResult).commandExecutor);
    assertEquals(0, ((NativeHistoricDetailQueryImpl) actualCreateNativeHistoricDetailQueryResult).firstResult);
    assertTrue(((NativeHistoricDetailQueryImpl) actualCreateNativeHistoricDetailQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeHistoricDetailQueryImpl) actualCreateNativeHistoricDetailQueryResult).maxResults);
  }

  /**
   * Test {@link HistoryServiceImpl#createNativeHistoricDetailQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createNativeHistoricDetailQuery()}
   */
  @Test
  public void testCreateNativeHistoricDetailQuery2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    NativeHistoricDetailQuery actualCreateNativeHistoricDetailQueryResult = (new HistoryServiceImpl(
        processEngineConfiguration)).createNativeHistoricDetailQuery();

    // Assert
    assertTrue(actualCreateNativeHistoricDetailQueryResult instanceof NativeHistoricDetailQueryImpl);
    assertNull(((NativeHistoricDetailQueryImpl) actualCreateNativeHistoricDetailQueryResult).resultType);
    assertNull(((NativeHistoricDetailQueryImpl) actualCreateNativeHistoricDetailQueryResult).commandContext);
    assertNull(((NativeHistoricDetailQueryImpl) actualCreateNativeHistoricDetailQueryResult).commandExecutor);
    assertEquals(0, ((NativeHistoricDetailQueryImpl) actualCreateNativeHistoricDetailQueryResult).firstResult);
    assertTrue(((NativeHistoricDetailQueryImpl) actualCreateNativeHistoricDetailQueryResult).getParameters().isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeHistoricDetailQueryImpl) actualCreateNativeHistoricDetailQueryResult).maxResults);
  }

  /**
   * Test {@link HistoryServiceImpl#createHistoricVariableInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createHistoricVariableInstanceQuery()}
   */
  @Test
  public void testCreateHistoricVariableInstanceQuery() {
    // Arrange and Act
    HistoricVariableInstanceQuery actualCreateHistoricVariableInstanceQueryResult = (new HistoryServiceImpl(
        new JtaProcessEngineConfiguration())).createHistoricVariableInstanceQuery();

    // Assert
    assertTrue(actualCreateHistoricVariableInstanceQueryResult instanceof HistoricVariableInstanceQueryImpl);
    assertEquals("RES.ID_ asc",
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getOrderByColumns());
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getParameter());
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getDatabaseType());
    assertNull(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getActivityInstanceId());
    assertNull(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getProcessInstanceId());
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getTaskId());
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getVariableName());
    assertNull(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getVariableNameLike());
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).orderBy);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).executionId);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).id);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).executionIds);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).taskIds);
    assertNull(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).nullHandlingOnOrder);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).resultType);
    assertNull(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getQueryVariableValue());
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).commandContext);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).commandExecutor);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).orderProperty);
    assertEquals(0,
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getFirstResult());
    assertEquals(1,
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getFirstRow());
    assertFalse(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getExcludeTaskRelated());
    assertFalse(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).excludeVariableInitialization);
    assertEquals(Integer.MAX_VALUE,
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getMaxResults());
  }

  /**
   * Test {@link HistoryServiceImpl#createHistoricVariableInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createHistoricVariableInstanceQuery()}
   */
  @Test
  public void testCreateHistoricVariableInstanceQuery2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoricVariableInstanceQuery actualCreateHistoricVariableInstanceQueryResult = (new HistoryServiceImpl(
        processEngineConfiguration)).createHistoricVariableInstanceQuery();

    // Assert
    assertTrue(actualCreateHistoricVariableInstanceQueryResult instanceof HistoricVariableInstanceQueryImpl);
    assertEquals("RES.ID_ asc",
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getOrderBy());
    assertEquals("RES.ID_ asc",
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getOrderByColumns());
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getParameter());
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getDatabaseType());
    assertNull(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getActivityInstanceId());
    assertNull(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getProcessInstanceId());
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getTaskId());
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getVariableName());
    assertNull(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getVariableNameLike());
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).orderBy);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).executionId);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).id);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).executionIds);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).taskIds);
    assertNull(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).nullHandlingOnOrder);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).resultType);
    assertNull(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getQueryVariableValue());
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).commandContext);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).commandExecutor);
    assertNull(((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).orderProperty);
    assertEquals(0,
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getFirstResult());
    assertEquals(1,
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getFirstRow());
    assertFalse(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getExcludeTaskRelated());
    assertFalse(
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).excludeVariableInitialization);
    assertEquals(Integer.MAX_VALUE,
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getLastRow());
    assertEquals(Integer.MAX_VALUE,
        ((HistoricVariableInstanceQueryImpl) actualCreateHistoricVariableInstanceQueryResult).getMaxResults());
  }

  /**
   * Test {@link HistoryServiceImpl#createNativeHistoricVariableInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createNativeHistoricVariableInstanceQuery()}
   */
  @Test
  public void testCreateNativeHistoricVariableInstanceQuery() {
    // Arrange and Act
    NativeHistoricVariableInstanceQuery actualCreateNativeHistoricVariableInstanceQueryResult = (new HistoryServiceImpl(
        new JtaProcessEngineConfiguration())).createNativeHistoricVariableInstanceQuery();

    // Assert
    assertTrue(
        actualCreateNativeHistoricVariableInstanceQueryResult instanceof NativeHistoricVariableInstanceQueryImpl);
    assertNull(
        ((NativeHistoricVariableInstanceQueryImpl) actualCreateNativeHistoricVariableInstanceQueryResult).resultType);
    assertNull(
        ((NativeHistoricVariableInstanceQueryImpl) actualCreateNativeHistoricVariableInstanceQueryResult).commandContext);
    assertNull(
        ((NativeHistoricVariableInstanceQueryImpl) actualCreateNativeHistoricVariableInstanceQueryResult).commandExecutor);
    assertEquals(0,
        ((NativeHistoricVariableInstanceQueryImpl) actualCreateNativeHistoricVariableInstanceQueryResult).firstResult);
    assertTrue(((NativeHistoricVariableInstanceQueryImpl) actualCreateNativeHistoricVariableInstanceQueryResult)
        .getParameters()
        .isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeHistoricVariableInstanceQueryImpl) actualCreateNativeHistoricVariableInstanceQueryResult).maxResults);
  }

  /**
   * Test {@link HistoryServiceImpl#createNativeHistoricVariableInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createNativeHistoricVariableInstanceQuery()}
   */
  @Test
  public void testCreateNativeHistoricVariableInstanceQuery2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    NativeHistoricVariableInstanceQuery actualCreateNativeHistoricVariableInstanceQueryResult = (new HistoryServiceImpl(
        processEngineConfiguration)).createNativeHistoricVariableInstanceQuery();

    // Assert
    assertTrue(
        actualCreateNativeHistoricVariableInstanceQueryResult instanceof NativeHistoricVariableInstanceQueryImpl);
    assertNull(
        ((NativeHistoricVariableInstanceQueryImpl) actualCreateNativeHistoricVariableInstanceQueryResult).resultType);
    assertNull(
        ((NativeHistoricVariableInstanceQueryImpl) actualCreateNativeHistoricVariableInstanceQueryResult).commandContext);
    assertNull(
        ((NativeHistoricVariableInstanceQueryImpl) actualCreateNativeHistoricVariableInstanceQueryResult).commandExecutor);
    assertEquals(0,
        ((NativeHistoricVariableInstanceQueryImpl) actualCreateNativeHistoricVariableInstanceQueryResult).firstResult);
    assertTrue(((NativeHistoricVariableInstanceQueryImpl) actualCreateNativeHistoricVariableInstanceQueryResult)
        .getParameters()
        .isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeHistoricVariableInstanceQueryImpl) actualCreateNativeHistoricVariableInstanceQueryResult).maxResults);
  }

  /**
   * Test {@link HistoryServiceImpl#createNativeHistoricProcessInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createNativeHistoricProcessInstanceQuery()}
   */
  @Test
  public void testCreateNativeHistoricProcessInstanceQuery() {
    // Arrange and Act
    NativeHistoricProcessInstanceQuery actualCreateNativeHistoricProcessInstanceQueryResult = (new HistoryServiceImpl(
        new JtaProcessEngineConfiguration())).createNativeHistoricProcessInstanceQuery();

    // Assert
    assertTrue(actualCreateNativeHistoricProcessInstanceQueryResult instanceof NativeHistoricProcessInstanceQueryImpl);
    assertNull(
        ((NativeHistoricProcessInstanceQueryImpl) actualCreateNativeHistoricProcessInstanceQueryResult).resultType);
    assertNull(
        ((NativeHistoricProcessInstanceQueryImpl) actualCreateNativeHistoricProcessInstanceQueryResult).commandContext);
    assertNull(
        ((NativeHistoricProcessInstanceQueryImpl) actualCreateNativeHistoricProcessInstanceQueryResult).commandExecutor);
    assertEquals(0,
        ((NativeHistoricProcessInstanceQueryImpl) actualCreateNativeHistoricProcessInstanceQueryResult).firstResult);
    assertTrue(
        ((NativeHistoricProcessInstanceQueryImpl) actualCreateNativeHistoricProcessInstanceQueryResult).getParameters()
            .isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeHistoricProcessInstanceQueryImpl) actualCreateNativeHistoricProcessInstanceQueryResult).maxResults);
  }

  /**
   * Test {@link HistoryServiceImpl#createNativeHistoricProcessInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createNativeHistoricProcessInstanceQuery()}
   */
  @Test
  public void testCreateNativeHistoricProcessInstanceQuery2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    NativeHistoricProcessInstanceQuery actualCreateNativeHistoricProcessInstanceQueryResult = (new HistoryServiceImpl(
        processEngineConfiguration)).createNativeHistoricProcessInstanceQuery();

    // Assert
    assertTrue(actualCreateNativeHistoricProcessInstanceQueryResult instanceof NativeHistoricProcessInstanceQueryImpl);
    assertNull(
        ((NativeHistoricProcessInstanceQueryImpl) actualCreateNativeHistoricProcessInstanceQueryResult).resultType);
    assertNull(
        ((NativeHistoricProcessInstanceQueryImpl) actualCreateNativeHistoricProcessInstanceQueryResult).commandContext);
    assertNull(
        ((NativeHistoricProcessInstanceQueryImpl) actualCreateNativeHistoricProcessInstanceQueryResult).commandExecutor);
    assertEquals(0,
        ((NativeHistoricProcessInstanceQueryImpl) actualCreateNativeHistoricProcessInstanceQueryResult).firstResult);
    assertTrue(
        ((NativeHistoricProcessInstanceQueryImpl) actualCreateNativeHistoricProcessInstanceQueryResult).getParameters()
            .isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeHistoricProcessInstanceQueryImpl) actualCreateNativeHistoricProcessInstanceQueryResult).maxResults);
  }

  /**
   * Test {@link HistoryServiceImpl#createNativeHistoricTaskInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createNativeHistoricTaskInstanceQuery()}
   */
  @Test
  public void testCreateNativeHistoricTaskInstanceQuery() {
    // Arrange and Act
    NativeHistoricTaskInstanceQuery actualCreateNativeHistoricTaskInstanceQueryResult = (new HistoryServiceImpl(
        new JtaProcessEngineConfiguration())).createNativeHistoricTaskInstanceQuery();

    // Assert
    assertTrue(actualCreateNativeHistoricTaskInstanceQueryResult instanceof NativeHistoricTaskInstanceQueryImpl);
    assertNull(((NativeHistoricTaskInstanceQueryImpl) actualCreateNativeHistoricTaskInstanceQueryResult).resultType);
    assertNull(
        ((NativeHistoricTaskInstanceQueryImpl) actualCreateNativeHistoricTaskInstanceQueryResult).commandContext);
    assertNull(
        ((NativeHistoricTaskInstanceQueryImpl) actualCreateNativeHistoricTaskInstanceQueryResult).commandExecutor);
    assertEquals(0,
        ((NativeHistoricTaskInstanceQueryImpl) actualCreateNativeHistoricTaskInstanceQueryResult).firstResult);
    assertTrue(((NativeHistoricTaskInstanceQueryImpl) actualCreateNativeHistoricTaskInstanceQueryResult).getParameters()
        .isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeHistoricTaskInstanceQueryImpl) actualCreateNativeHistoricTaskInstanceQueryResult).maxResults);
  }

  /**
   * Test {@link HistoryServiceImpl#createNativeHistoricTaskInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createNativeHistoricTaskInstanceQuery()}
   */
  @Test
  public void testCreateNativeHistoricTaskInstanceQuery2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    NativeHistoricTaskInstanceQuery actualCreateNativeHistoricTaskInstanceQueryResult = (new HistoryServiceImpl(
        processEngineConfiguration)).createNativeHistoricTaskInstanceQuery();

    // Assert
    assertTrue(actualCreateNativeHistoricTaskInstanceQueryResult instanceof NativeHistoricTaskInstanceQueryImpl);
    assertNull(((NativeHistoricTaskInstanceQueryImpl) actualCreateNativeHistoricTaskInstanceQueryResult).resultType);
    assertNull(
        ((NativeHistoricTaskInstanceQueryImpl) actualCreateNativeHistoricTaskInstanceQueryResult).commandContext);
    assertNull(
        ((NativeHistoricTaskInstanceQueryImpl) actualCreateNativeHistoricTaskInstanceQueryResult).commandExecutor);
    assertEquals(0,
        ((NativeHistoricTaskInstanceQueryImpl) actualCreateNativeHistoricTaskInstanceQueryResult).firstResult);
    assertTrue(((NativeHistoricTaskInstanceQueryImpl) actualCreateNativeHistoricTaskInstanceQueryResult).getParameters()
        .isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeHistoricTaskInstanceQueryImpl) actualCreateNativeHistoricTaskInstanceQueryResult).maxResults);
  }

  /**
   * Test {@link HistoryServiceImpl#createNativeHistoricActivityInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createNativeHistoricActivityInstanceQuery()}
   */
  @Test
  public void testCreateNativeHistoricActivityInstanceQuery() {
    // Arrange and Act
    NativeHistoricActivityInstanceQuery actualCreateNativeHistoricActivityInstanceQueryResult = (new HistoryServiceImpl(
        new JtaProcessEngineConfiguration())).createNativeHistoricActivityInstanceQuery();

    // Assert
    assertTrue(
        actualCreateNativeHistoricActivityInstanceQueryResult instanceof NativeHistoricActivityInstanceQueryImpl);
    assertNull(
        ((NativeHistoricActivityInstanceQueryImpl) actualCreateNativeHistoricActivityInstanceQueryResult).resultType);
    assertNull(
        ((NativeHistoricActivityInstanceQueryImpl) actualCreateNativeHistoricActivityInstanceQueryResult).commandContext);
    assertNull(
        ((NativeHistoricActivityInstanceQueryImpl) actualCreateNativeHistoricActivityInstanceQueryResult).commandExecutor);
    assertEquals(0,
        ((NativeHistoricActivityInstanceQueryImpl) actualCreateNativeHistoricActivityInstanceQueryResult).firstResult);
    assertTrue(((NativeHistoricActivityInstanceQueryImpl) actualCreateNativeHistoricActivityInstanceQueryResult)
        .getParameters()
        .isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeHistoricActivityInstanceQueryImpl) actualCreateNativeHistoricActivityInstanceQueryResult).maxResults);
  }

  /**
   * Test {@link HistoryServiceImpl#createNativeHistoricActivityInstanceQuery()}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createNativeHistoricActivityInstanceQuery()}
   */
  @Test
  public void testCreateNativeHistoricActivityInstanceQuery2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    NativeHistoricActivityInstanceQuery actualCreateNativeHistoricActivityInstanceQueryResult = (new HistoryServiceImpl(
        processEngineConfiguration)).createNativeHistoricActivityInstanceQuery();

    // Assert
    assertTrue(
        actualCreateNativeHistoricActivityInstanceQueryResult instanceof NativeHistoricActivityInstanceQueryImpl);
    assertNull(
        ((NativeHistoricActivityInstanceQueryImpl) actualCreateNativeHistoricActivityInstanceQueryResult).resultType);
    assertNull(
        ((NativeHistoricActivityInstanceQueryImpl) actualCreateNativeHistoricActivityInstanceQueryResult).commandContext);
    assertNull(
        ((NativeHistoricActivityInstanceQueryImpl) actualCreateNativeHistoricActivityInstanceQueryResult).commandExecutor);
    assertEquals(0,
        ((NativeHistoricActivityInstanceQueryImpl) actualCreateNativeHistoricActivityInstanceQueryResult).firstResult);
    assertTrue(((NativeHistoricActivityInstanceQueryImpl) actualCreateNativeHistoricActivityInstanceQueryResult)
        .getParameters()
        .isEmpty());
    assertEquals(Integer.MAX_VALUE,
        ((NativeHistoricActivityInstanceQueryImpl) actualCreateNativeHistoricActivityInstanceQueryResult).maxResults);
  }

  /**
   * Test {@link HistoryServiceImpl#createProcessInstanceHistoryLogQuery(String)}.
   * <p>
   * Method under test:
   * {@link HistoryServiceImpl#createProcessInstanceHistoryLogQuery(String)}
   */
  @Test
  public void testCreateProcessInstanceHistoryLogQuery() {
    // Arrange and Act
    ProcessInstanceHistoryLogQuery actualCreateProcessInstanceHistoryLogQueryResult = historyServiceImpl
        .createProcessInstanceHistoryLogQuery("42");

    // Assert
    assertTrue(actualCreateProcessInstanceHistoryLogQueryResult instanceof ProcessInstanceHistoryLogQueryImpl);
    assertEquals("42",
        ((ProcessInstanceHistoryLogQueryImpl) actualCreateProcessInstanceHistoryLogQueryResult).processInstanceId);
    assertNull(((ProcessInstanceHistoryLogQueryImpl) actualCreateProcessInstanceHistoryLogQueryResult).commandExecutor);
    assertFalse(
        ((ProcessInstanceHistoryLogQueryImpl) actualCreateProcessInstanceHistoryLogQueryResult).includeActivities);
    assertFalse(
        ((ProcessInstanceHistoryLogQueryImpl) actualCreateProcessInstanceHistoryLogQueryResult).includeComments);
    assertFalse(
        ((ProcessInstanceHistoryLogQueryImpl) actualCreateProcessInstanceHistoryLogQueryResult).includeFormProperties);
    assertFalse(((ProcessInstanceHistoryLogQueryImpl) actualCreateProcessInstanceHistoryLogQueryResult).includeTasks);
    assertFalse(
        ((ProcessInstanceHistoryLogQueryImpl) actualCreateProcessInstanceHistoryLogQueryResult).includeVariableUpdates);
    assertFalse(
        ((ProcessInstanceHistoryLogQueryImpl) actualCreateProcessInstanceHistoryLogQueryResult).includeVariables);
  }
}
