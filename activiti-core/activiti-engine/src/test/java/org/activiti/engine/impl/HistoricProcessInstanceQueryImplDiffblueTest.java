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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.junit.Test;

public class HistoricProcessInstanceQueryImplDiffblueTest {
  /**
   * Test
   * {@link HistoricProcessInstanceQueryImpl#HistoricProcessInstanceQueryImpl()}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#HistoricProcessInstanceQueryImpl()}
   */
  @Test
  public void testNewHistoricProcessInstanceQueryImpl() {
    // Arrange and Act
    HistoricProcessInstanceQueryImpl actualHistoricProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualHistoricProcessInstanceQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualHistoricProcessInstanceQueryImpl.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", actualHistoricProcessInstanceQueryImpl.getMssqlOrDB2OrderBy());
    assertEquals("null:%:%", actualHistoricProcessInstanceQueryImpl.getProcessDefinitionIdLike());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessDefinitionVersion());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessInstanceVariablesLimit());
    assertNull(actualHistoricProcessInstanceQueryImpl.getDatabaseType());
    assertNull(actualHistoricProcessInstanceQueryImpl.getBusinessKey());
    assertNull(actualHistoricProcessInstanceQueryImpl.getDeploymentId());
    assertNull(actualHistoricProcessInstanceQueryImpl.getInvolvedUser());
    assertNull(actualHistoricProcessInstanceQueryImpl.getName());
    assertNull(actualHistoricProcessInstanceQueryImpl.getNameLike());
    assertNull(actualHistoricProcessInstanceQueryImpl.getNameLikeIgnoreCase());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessDefinitionCategory());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessDefinitionId());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessDefinitionKey());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessDefinitionName());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessInstanceId());
    assertNull(actualHistoricProcessInstanceQueryImpl.getStartedBy());
    assertNull(actualHistoricProcessInstanceQueryImpl.getSuperProcessInstanceId());
    assertNull(actualHistoricProcessInstanceQueryImpl.getTenantId());
    assertNull(actualHistoricProcessInstanceQueryImpl.getTenantIdLike());
    assertNull(actualHistoricProcessInstanceQueryImpl.orderBy);
    assertNull(actualHistoricProcessInstanceQueryImpl.locale);
    assertNull(actualHistoricProcessInstanceQueryImpl.getFinishedAfter());
    assertNull(actualHistoricProcessInstanceQueryImpl.getFinishedBefore());
    assertNull(actualHistoricProcessInstanceQueryImpl.getStartedAfter());
    assertNull(actualHistoricProcessInstanceQueryImpl.getStartedBefore());
    assertNull(actualHistoricProcessInstanceQueryImpl.getDeploymentIds());
    assertNull(actualHistoricProcessInstanceQueryImpl.getInvolvedGroups());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessDefinitionKeyIn());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessKeyNotIn());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessInstanceIds());
    assertNull(actualHistoricProcessInstanceQueryImpl.nullHandlingOnOrder);
    assertNull(actualHistoricProcessInstanceQueryImpl.resultType);
    assertNull(actualHistoricProcessInstanceQueryImpl.currentOrQueryObject);
    assertNull(actualHistoricProcessInstanceQueryImpl.commandContext);
    assertNull(actualHistoricProcessInstanceQueryImpl.commandExecutor);
    assertNull(actualHistoricProcessInstanceQueryImpl.orderProperty);
    assertEquals(0, actualHistoricProcessInstanceQueryImpl.getFirstResult());
    assertEquals(1, actualHistoricProcessInstanceQueryImpl.getFirstRow());
    assertFalse(actualHistoricProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertFalse(actualHistoricProcessInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isDeleted());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isExcludeSubprocesses());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isFinished());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isIncludeProcessVariables());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isNotDeleted());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isOpen());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isUnfinished());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isWithException());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isWithoutTenantId());
    assertFalse(actualHistoricProcessInstanceQueryImpl.inOrStatement);
    assertFalse(actualHistoricProcessInstanceQueryImpl.withLocalizationFallback);
    assertTrue(actualHistoricProcessInstanceQueryImpl.getQueryVariableValues().isEmpty());
    assertTrue(actualHistoricProcessInstanceQueryImpl.getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE, actualHistoricProcessInstanceQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualHistoricProcessInstanceQueryImpl.getMaxResults());
    assertSame(actualHistoricProcessInstanceQueryImpl, actualHistoricProcessInstanceQueryImpl.getParameter());
  }

  /**
   * Test
   * {@link HistoricProcessInstanceQueryImpl#HistoricProcessInstanceQueryImpl()}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#HistoricProcessInstanceQueryImpl()}
   */
  @Test
  public void testNewHistoricProcessInstanceQueryImpl2() {
    // Arrange and Act
    HistoricProcessInstanceQueryImpl actualHistoricProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualHistoricProcessInstanceQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualHistoricProcessInstanceQueryImpl.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", actualHistoricProcessInstanceQueryImpl.getMssqlOrDB2OrderBy());
    assertEquals("null:%:%", actualHistoricProcessInstanceQueryImpl.getProcessDefinitionIdLike());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessDefinitionVersion());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessInstanceVariablesLimit());
    assertNull(actualHistoricProcessInstanceQueryImpl.getDatabaseType());
    assertNull(actualHistoricProcessInstanceQueryImpl.getBusinessKey());
    assertNull(actualHistoricProcessInstanceQueryImpl.getDeploymentId());
    assertNull(actualHistoricProcessInstanceQueryImpl.getInvolvedUser());
    assertNull(actualHistoricProcessInstanceQueryImpl.getName());
    assertNull(actualHistoricProcessInstanceQueryImpl.getNameLike());
    assertNull(actualHistoricProcessInstanceQueryImpl.getNameLikeIgnoreCase());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessDefinitionCategory());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessDefinitionId());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessDefinitionKey());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessDefinitionName());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessInstanceId());
    assertNull(actualHistoricProcessInstanceQueryImpl.getStartedBy());
    assertNull(actualHistoricProcessInstanceQueryImpl.getSuperProcessInstanceId());
    assertNull(actualHistoricProcessInstanceQueryImpl.getTenantId());
    assertNull(actualHistoricProcessInstanceQueryImpl.getTenantIdLike());
    assertNull(actualHistoricProcessInstanceQueryImpl.orderBy);
    assertNull(actualHistoricProcessInstanceQueryImpl.locale);
    assertNull(actualHistoricProcessInstanceQueryImpl.getFinishedAfter());
    assertNull(actualHistoricProcessInstanceQueryImpl.getFinishedBefore());
    assertNull(actualHistoricProcessInstanceQueryImpl.getStartedAfter());
    assertNull(actualHistoricProcessInstanceQueryImpl.getStartedBefore());
    assertNull(actualHistoricProcessInstanceQueryImpl.getDeploymentIds());
    assertNull(actualHistoricProcessInstanceQueryImpl.getInvolvedGroups());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessDefinitionKeyIn());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessKeyNotIn());
    assertNull(actualHistoricProcessInstanceQueryImpl.getProcessInstanceIds());
    assertNull(actualHistoricProcessInstanceQueryImpl.nullHandlingOnOrder);
    assertNull(actualHistoricProcessInstanceQueryImpl.resultType);
    assertNull(actualHistoricProcessInstanceQueryImpl.currentOrQueryObject);
    assertNull(actualHistoricProcessInstanceQueryImpl.commandContext);
    assertNull(actualHistoricProcessInstanceQueryImpl.commandExecutor);
    assertNull(actualHistoricProcessInstanceQueryImpl.orderProperty);
    assertEquals(0, actualHistoricProcessInstanceQueryImpl.getFirstResult());
    assertEquals(1, actualHistoricProcessInstanceQueryImpl.getFirstRow());
    assertFalse(actualHistoricProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertFalse(actualHistoricProcessInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isDeleted());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isExcludeSubprocesses());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isFinished());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isIncludeProcessVariables());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isNotDeleted());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isOpen());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isUnfinished());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isWithException());
    assertFalse(actualHistoricProcessInstanceQueryImpl.isWithoutTenantId());
    assertFalse(actualHistoricProcessInstanceQueryImpl.inOrStatement);
    assertFalse(actualHistoricProcessInstanceQueryImpl.withLocalizationFallback);
    assertTrue(actualHistoricProcessInstanceQueryImpl.getQueryVariableValues().isEmpty());
    assertTrue(actualHistoricProcessInstanceQueryImpl.getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE, actualHistoricProcessInstanceQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualHistoricProcessInstanceQueryImpl.getMaxResults());
    assertSame(actualHistoricProcessInstanceQueryImpl, actualHistoricProcessInstanceQueryImpl.getParameter());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#deploymentIdIn(List)}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  public void testDeploymentIdIn() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();
    ArrayList<String> deploymentIds = new ArrayList<>();

    // Act
    HistoricProcessInstanceQuery actualDeploymentIdInResult = historicProcessInstanceQueryImpl
        .deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, historicProcessInstanceQueryImpl.getDeploymentIds());
    assertSame(historicProcessInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#deploymentIdIn(List)}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  public void testDeploymentIdIn2() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();
    ArrayList<String> deploymentIds = new ArrayList<>();

    // Act
    HistoricProcessInstanceQuery actualDeploymentIdInResult = historicProcessInstanceQueryImpl
        .deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, historicProcessInstanceQueryImpl.getDeploymentIds());
    assertSame(historicProcessInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#finished()}.
   * <p>
   * Method under test: {@link HistoricProcessInstanceQueryImpl#finished()}
   */
  @Test
  public void testFinished() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualFinishedResult = historicProcessInstanceQueryImpl.finished();

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isFinished());
    assertSame(historicProcessInstanceQueryImpl, actualFinishedResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#deleted()}.
   * <p>
   * Method under test: {@link HistoricProcessInstanceQueryImpl#deleted()}
   */
  @Test
  public void testDeleted() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualDeletedResult = historicProcessInstanceQueryImpl.deleted();

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isDeleted());
    assertSame(historicProcessInstanceQueryImpl, actualDeletedResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#deleted()}.
   * <p>
   * Method under test: {@link HistoricProcessInstanceQueryImpl#deleted()}
   */
  @Test
  public void testDeleted2() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualDeletedResult = historicProcessInstanceQueryImpl.deleted();

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isDeleted());
    assertSame(historicProcessInstanceQueryImpl, actualDeletedResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#notDeleted()}.
   * <p>
   * Method under test: {@link HistoricProcessInstanceQueryImpl#notDeleted()}
   */
  @Test
  public void testNotDeleted() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualNotDeletedResult = historicProcessInstanceQueryImpl.notDeleted();

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isNotDeleted());
    assertSame(historicProcessInstanceQueryImpl, actualNotDeletedResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#finishedAfter(Date)}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#finishedAfter(Date)}
   */
  @Test
  public void testFinishedAfter() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();
    Date finishedAfter = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    HistoricProcessInstanceQuery actualFinishedAfterResult = historicProcessInstanceQueryImpl
        .finishedAfter(finishedAfter);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isFinished());
    assertSame(historicProcessInstanceQueryImpl, actualFinishedAfterResult);
    assertSame(finishedAfter, historicProcessInstanceQueryImpl.getFinishedAfter());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#finishedBefore(Date)}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#finishedBefore(Date)}
   */
  @Test
  public void testFinishedBefore() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();
    Date finishedBefore = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    HistoricProcessInstanceQuery actualFinishedBeforeResult = historicProcessInstanceQueryImpl
        .finishedBefore(finishedBefore);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isFinished());
    assertSame(historicProcessInstanceQueryImpl, actualFinishedBeforeResult);
    assertSame(finishedBefore, historicProcessInstanceQueryImpl.getFinishedBefore());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#excludeSubprocesses(boolean)}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#excludeSubprocesses(boolean)}
   */
  @Test
  public void testExcludeSubprocesses() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualExcludeSubprocessesResult = historicProcessInstanceQueryImpl
        .excludeSubprocesses(true);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isExcludeSubprocesses());
    assertSame(historicProcessInstanceQueryImpl, actualExcludeSubprocessesResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#excludeSubprocesses(boolean)}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#excludeSubprocesses(boolean)}
   */
  @Test
  public void testExcludeSubprocesses2() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualExcludeSubprocessesResult = historicProcessInstanceQueryImpl
        .excludeSubprocesses(true);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isExcludeSubprocesses());
    assertSame(historicProcessInstanceQueryImpl, actualExcludeSubprocessesResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#or()}.
   * <p>
   * Method under test: {@link HistoricProcessInstanceQueryImpl#or()}
   */
  @Test
  public void testOr() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualOrResult = historicProcessInstanceQueryImpl.or();

    // Assert
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl2 = historicProcessInstanceQueryImpl.currentOrQueryObject;
    assertEquals("RES.ID_ asc", historicProcessInstanceQueryImpl2.getOrderBy());
    assertEquals("RES.ID_ asc", historicProcessInstanceQueryImpl2.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", historicProcessInstanceQueryImpl2.getMssqlOrDB2OrderBy());
    assertEquals("null:%:%", historicProcessInstanceQueryImpl2.getProcessDefinitionIdLike());
    assertNull(historicProcessInstanceQueryImpl2.getProcessDefinitionVersion());
    assertNull(historicProcessInstanceQueryImpl2.getProcessInstanceVariablesLimit());
    assertNull(historicProcessInstanceQueryImpl2.getDatabaseType());
    assertNull(historicProcessInstanceQueryImpl2.getBusinessKey());
    assertNull(historicProcessInstanceQueryImpl2.getDeploymentId());
    assertNull(historicProcessInstanceQueryImpl2.getInvolvedUser());
    assertNull(historicProcessInstanceQueryImpl2.getName());
    assertNull(historicProcessInstanceQueryImpl2.getNameLike());
    assertNull(historicProcessInstanceQueryImpl2.getNameLikeIgnoreCase());
    assertNull(historicProcessInstanceQueryImpl2.getProcessDefinitionCategory());
    assertNull(historicProcessInstanceQueryImpl2.getProcessDefinitionId());
    assertNull(historicProcessInstanceQueryImpl2.getProcessDefinitionKey());
    assertNull(historicProcessInstanceQueryImpl2.getProcessDefinitionName());
    assertNull(historicProcessInstanceQueryImpl2.getProcessInstanceId());
    assertNull(historicProcessInstanceQueryImpl2.getStartedBy());
    assertNull(historicProcessInstanceQueryImpl2.getSuperProcessInstanceId());
    assertNull(historicProcessInstanceQueryImpl2.getTenantId());
    assertNull(historicProcessInstanceQueryImpl2.getTenantIdLike());
    assertNull(historicProcessInstanceQueryImpl2.orderBy);
    assertNull(historicProcessInstanceQueryImpl2.locale);
    assertNull(historicProcessInstanceQueryImpl2.getFinishedAfter());
    assertNull(historicProcessInstanceQueryImpl2.getFinishedBefore());
    assertNull(historicProcessInstanceQueryImpl2.getStartedAfter());
    assertNull(historicProcessInstanceQueryImpl2.getStartedBefore());
    assertNull(historicProcessInstanceQueryImpl2.getDeploymentIds());
    assertNull(historicProcessInstanceQueryImpl2.getInvolvedGroups());
    assertNull(historicProcessInstanceQueryImpl2.getProcessDefinitionKeyIn());
    assertNull(historicProcessInstanceQueryImpl2.getProcessKeyNotIn());
    assertNull(historicProcessInstanceQueryImpl2.getProcessInstanceIds());
    assertNull(historicProcessInstanceQueryImpl2.nullHandlingOnOrder);
    assertNull(historicProcessInstanceQueryImpl2.resultType);
    assertNull(historicProcessInstanceQueryImpl2.currentOrQueryObject);
    assertNull(historicProcessInstanceQueryImpl2.commandContext);
    assertNull(historicProcessInstanceQueryImpl2.commandExecutor);
    assertNull(historicProcessInstanceQueryImpl2.orderProperty);
    assertEquals(0, historicProcessInstanceQueryImpl2.getFirstResult());
    assertEquals(1, historicProcessInstanceQueryImpl2.getFirstRow());
    assertFalse(historicProcessInstanceQueryImpl2.hasLocalQueryVariableValue());
    assertFalse(historicProcessInstanceQueryImpl2.hasNonLocalQueryVariableValue());
    assertFalse(historicProcessInstanceQueryImpl2.isDeleted());
    assertFalse(historicProcessInstanceQueryImpl2.isExcludeSubprocesses());
    assertFalse(historicProcessInstanceQueryImpl2.isFinished());
    assertFalse(historicProcessInstanceQueryImpl2.isIncludeProcessVariables());
    assertFalse(historicProcessInstanceQueryImpl2.isNotDeleted());
    assertFalse(historicProcessInstanceQueryImpl2.isOpen());
    assertFalse(historicProcessInstanceQueryImpl2.isUnfinished());
    assertFalse(historicProcessInstanceQueryImpl2.isWithException());
    assertFalse(historicProcessInstanceQueryImpl2.isWithoutTenantId());
    assertFalse(historicProcessInstanceQueryImpl2.inOrStatement);
    assertFalse(historicProcessInstanceQueryImpl2.withLocalizationFallback);
    assertTrue(historicProcessInstanceQueryImpl.inOrStatement);
    assertEquals(Integer.MAX_VALUE, historicProcessInstanceQueryImpl2.getLastRow());
    assertEquals(Integer.MAX_VALUE, historicProcessInstanceQueryImpl2.getMaxResults());
    assertSame(historicProcessInstanceQueryImpl, actualOrResult);
    HistoricProcessInstanceQueryImpl expectedParameter = ((HistoricProcessInstanceQueryImpl) actualOrResult).currentOrQueryObject;
    assertSame(expectedParameter, historicProcessInstanceQueryImpl2.getParameter());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#endOr()}.
   * <p>
   * Method under test: {@link HistoricProcessInstanceQueryImpl#endOr()}
   */
  @Test
  public void testEndOr() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new HistoricProcessInstanceQueryImpl()).endOr());
    assertThrows(ActivitiException.class, () -> (new HistoricProcessInstanceQueryImpl()).endOr());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  public void testGetMssqlOrDB2OrderBy() {
    // Arrange, Act and Assert
    assertEquals("TEMPRES_ID_ asc", (new HistoricProcessInstanceQueryImpl()).getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#getProcessDefinitionIdLike()}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#getProcessDefinitionIdLike()}
   */
  @Test
  public void testGetProcessDefinitionIdLike() {
    // Arrange, Act and Assert
    assertEquals("null:%:%", (new HistoricProcessInstanceQueryImpl()).getProcessDefinitionIdLike());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#involvedGroupsIn(List)}.
   * <p>
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#involvedGroupsIn(List)}
   */
  @Test
  public void testInvolvedGroupsIn() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicProcessInstanceQueryImpl.involvedGroupsIn(new ArrayList<>()));
  }
}
