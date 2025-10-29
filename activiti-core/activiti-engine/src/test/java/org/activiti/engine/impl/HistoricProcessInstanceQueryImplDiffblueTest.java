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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricProcessInstanceQueryImplDiffblueTest {
  @InjectMocks
  private HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl;

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId() {
    // Arrange and Act
    HistoricProcessInstanceQueryImpl actualProcessInstanceIdResult = historicProcessInstanceQueryImpl
        .processInstanceId("42");

    // Assert
    assertEquals("42", historicProcessInstanceQueryImpl.getProcessInstanceId());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processInstanceIds(Set)}
   */
  @Test
  public void testProcessInstanceIds() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicProcessInstanceQueryImpl.processInstanceIds(new HashSet<>()));
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId() {
    // Arrange and Act
    HistoricProcessInstanceQueryImpl actualProcessDefinitionIdResult = historicProcessInstanceQueryImpl
        .processDefinitionId("42");

    // Assert
    assertEquals("42", historicProcessInstanceQueryImpl.getProcessDefinitionId());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualProcessDefinitionKeyResult = historicProcessInstanceQueryImpl
        .processDefinitionKey("Process Definition Key");

    // Assert
    assertEquals("Process Definition Key", historicProcessInstanceQueryImpl.getProcessDefinitionKey());
    assertEquals("Process Definition Key:%:%", historicProcessInstanceQueryImpl.getProcessDefinitionIdLike());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  public void testProcessDefinitionKeyIn() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();
    ArrayList<String> processDefinitionKeys = new ArrayList<>();

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionKeyInResult = historicProcessInstanceQueryImpl
        .processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicProcessInstanceQueryImpl.getProcessDefinitionKeyIn());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  public void testProcessDefinitionCategory() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualProcessDefinitionCategoryResult = historicProcessInstanceQueryImpl
        .processDefinitionCategory("Process Definition Category");

    // Assert
    assertEquals("Process Definition Category", historicProcessInstanceQueryImpl.getProcessDefinitionCategory());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionCategoryResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processDefinitionName(String)}
   */
  @Test
  public void testProcessDefinitionName() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualProcessDefinitionNameResult = historicProcessInstanceQueryImpl
        .processDefinitionName("Process Definition Name");

    // Assert
    assertEquals("Process Definition Name", historicProcessInstanceQueryImpl.getProcessDefinitionName());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  public void testProcessDefinitionVersion() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionVersionResult = historicProcessInstanceQueryImpl
        .processDefinitionVersion(1);

    // Assert
    assertEquals(1, historicProcessInstanceQueryImpl.getProcessDefinitionVersion().intValue());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionVersionResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  public void testProcessInstanceBusinessKey() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualProcessInstanceBusinessKeyResult = historicProcessInstanceQueryImpl
        .processInstanceBusinessKey("Business Key");

    // Assert
    assertEquals("Business Key", historicProcessInstanceQueryImpl.getBusinessKey());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#deploymentId(String)}
   */
  @Test
  public void testDeploymentId() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualDeploymentIdResult = historicProcessInstanceQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", historicProcessInstanceQueryImpl.getDeploymentId());
    assertSame(historicProcessInstanceQueryImpl, actualDeploymentIdResult);
  }

  /**
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
   * Method under test: {@link HistoricProcessInstanceQueryImpl#unfinished()}
   */
  @Test
  public void testUnfinished() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualUnfinishedResult = historicProcessInstanceQueryImpl.unfinished();

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isOpen());
    assertTrue(historicProcessInstanceQueryImpl.isUnfinished());
    assertSame(historicProcessInstanceQueryImpl, actualUnfinishedResult);
  }

  /**
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
   * Method under test: {@link HistoricProcessInstanceQueryImpl#startedBy(String)}
   */
  @Test
  public void testStartedBy() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualStartedByResult = historicProcessInstanceQueryImpl.startedBy("Started By");

    // Assert
    assertEquals("Started By", historicProcessInstanceQueryImpl.getStartedBy());
    assertSame(historicProcessInstanceQueryImpl, actualStartedByResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyNotIn(List)}
   */
  @Test
  public void testProcessDefinitionKeyNotIn() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();
    ArrayList<String> processDefinitionKeys = new ArrayList<>();

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionKeyNotInResult = historicProcessInstanceQueryImpl
        .processDefinitionKeyNotIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicProcessInstanceQueryImpl.getProcessKeyNotIn());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionKeyNotInResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#startedAfter(Date)}
   */
  @Test
  public void testStartedAfter() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();
    Date startedAfter = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicProcessInstanceQueryImpl, historicProcessInstanceQueryImpl.startedAfter(startedAfter));
    assertSame(startedAfter, historicProcessInstanceQueryImpl.getStartedAfter());
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#startedBefore(Date)}
   */
  @Test
  public void testStartedBefore() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();
    Date startedBefore = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicProcessInstanceQueryImpl, historicProcessInstanceQueryImpl.startedBefore(startedBefore));
    assertSame(startedBefore, historicProcessInstanceQueryImpl.getStartedBefore());
  }

  /**
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
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#superProcessInstanceId(String)}
   */
  @Test
  public void testSuperProcessInstanceId() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualSuperProcessInstanceIdResult = historicProcessInstanceQueryImpl
        .superProcessInstanceId("42");

    // Assert
    assertEquals("42", historicProcessInstanceQueryImpl.getSuperProcessInstanceId());
    assertSame(historicProcessInstanceQueryImpl, actualSuperProcessInstanceIdResult);
  }

  /**
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
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#involvedUser(String)}
   */
  @Test
  public void testInvolvedUser() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualInvolvedUserResult = historicProcessInstanceQueryImpl
        .involvedUser("Involved User");

    // Assert
    assertEquals("Involved User", historicProcessInstanceQueryImpl.getInvolvedUser());
    assertSame(historicProcessInstanceQueryImpl, actualInvolvedUserResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processInstanceTenantId(String)}
   */
  @Test
  public void testProcessInstanceTenantId() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualProcessInstanceTenantIdResult = historicProcessInstanceQueryImpl
        .processInstanceTenantId("42");

    // Assert
    assertEquals("42", historicProcessInstanceQueryImpl.getTenantId());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceTenantIdResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processInstanceTenantId(String)}
   */
  @Test
  public void testProcessInstanceTenantId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicProcessInstanceQueryImpl.processInstanceTenantId(null));
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processInstanceTenantIdLike(String)}
   */
  @Test
  public void testProcessInstanceTenantIdLike() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualProcessInstanceTenantIdLikeResult = historicProcessInstanceQueryImpl
        .processInstanceTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", historicProcessInstanceQueryImpl.getTenantIdLike());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceTenantIdLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processInstanceTenantIdLike(String)}
   */
  @Test
  public void testProcessInstanceTenantIdLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicProcessInstanceQueryImpl.processInstanceTenantIdLike(null));
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processInstanceWithoutTenantId()}
   */
  @Test
  public void testProcessInstanceWithoutTenantId() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessInstanceWithoutTenantIdResult = historicProcessInstanceQueryImpl
        .processInstanceWithoutTenantId();

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isWithoutTenantId());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceWithoutTenantIdResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processInstanceName(String)}
   */
  @Test
  public void testProcessInstanceName() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualProcessInstanceNameResult = historicProcessInstanceQueryImpl
        .processInstanceName("Name");

    // Assert
    assertEquals("Name", historicProcessInstanceQueryImpl.getName());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceNameResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processInstanceNameLike(String)}
   */
  @Test
  public void testProcessInstanceNameLike() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualProcessInstanceNameLikeResult = historicProcessInstanceQueryImpl
        .processInstanceNameLike("Name Like");

    // Assert
    assertEquals("Name Like", historicProcessInstanceQueryImpl.getNameLike());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceNameLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#processInstanceNameLikeIgnoreCase(String)}
   */
  @Test
  public void testProcessInstanceNameLikeIgnoreCase() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualProcessInstanceNameLikeIgnoreCaseResult = historicProcessInstanceQueryImpl
        .processInstanceNameLikeIgnoreCase("Name Like Ignore Case");

    // Assert
    assertEquals("name like ignore case", historicProcessInstanceQueryImpl.getNameLikeIgnoreCase());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceNameLikeIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueEquals(Object)}
   */
  @Test
  public void testVariableValueEquals() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl = new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueEqualsResult = historicProcessInstanceQueryImpl
        .variableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEquals2() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualVariableValueEqualsResult = historicProcessInstanceQueryImpl
        .variableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEquals3() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualVariableValueEqualsResult = historicProcessInstanceQueryImpl
        .variableValueEquals("Variable Name", null);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEquals() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualVariableValueNotEqualsResult = historicProcessInstanceQueryImpl
        .variableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEquals2() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualVariableValueNotEqualsResult = historicProcessInstanceQueryImpl
        .variableValueNotEquals("Variable Name", null);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCase() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualVariableValueEqualsIgnoreCaseResult = historicProcessInstanceQueryImpl
        .variableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCase() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualVariableValueNotEqualsIgnoreCaseResult = historicProcessInstanceQueryImpl
        .variableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueGreaterThan(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThan() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualVariableValueGreaterThanResult = historicProcessInstanceQueryImpl
        .variableValueGreaterThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueGreaterThanResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqual() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualVariableValueGreaterThanOrEqualResult = historicProcessInstanceQueryImpl
        .variableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueLessThan(String, Object)}
   */
  @Test
  public void testVariableValueLessThan() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualVariableValueLessThanResult = historicProcessInstanceQueryImpl
        .variableValueLessThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueLessThanResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueLessThanOrEqual() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualVariableValueLessThanOrEqualResult = historicProcessInstanceQueryImpl
        .variableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueLessThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueLike(String, String)}
   */
  @Test
  public void testVariableValueLike() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualVariableValueLikeResult = historicProcessInstanceQueryImpl
        .variableValueLike("Name", "42");

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueLikeIgnoreCase() {
    // Arrange and Act
    HistoricProcessInstanceQuery actualVariableValueLikeIgnoreCaseResult = historicProcessInstanceQueryImpl
        .variableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueLikeIgnoreCaseResult);
  }

  /**
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
   * Method under test: {@link HistoricProcessInstanceQueryImpl#endOr()}
   */
  @Test
  public void testEndOr() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new HistoricProcessInstanceQueryImpl()).endOr());
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  public void testGetMssqlOrDB2OrderBy() {
    // Arrange, Act and Assert
    assertEquals("TEMPRES_ID_ asc", (new HistoricProcessInstanceQueryImpl()).getMssqlOrDB2OrderBy());
  }

  /**
   * Method under test:
   * {@link HistoricProcessInstanceQueryImpl#getProcessDefinitionIdLike()}
   */
  @Test
  public void testGetProcessDefinitionIdLike() {
    // Arrange, Act and Assert
    assertEquals("null:%:%", (new HistoricProcessInstanceQueryImpl()).getProcessDefinitionIdLike());
  }

  /**
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

  /**
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
}
