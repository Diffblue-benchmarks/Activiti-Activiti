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
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessInstanceQueryImplDiffblueTest {
  @InjectMocks
  private ProcessInstanceQueryImpl processInstanceQueryImpl;

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId() {
    // Arrange and Act
    ProcessInstanceQueryImpl actualProcessInstanceIdResult = processInstanceQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getExecutionId());
    assertEquals("42", processInstanceQueryImpl.getProcessInstanceId());
    assertSame(processInstanceQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> processInstanceQueryImpl.processInstanceId(null));
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}
   */
  @Test
  public void testProcessInstanceIds() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processInstanceIds(new HashSet<>()));
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  public void testProcessInstanceBusinessKey() {
    // Arrange and Act
    ProcessInstanceQuery actualProcessInstanceBusinessKeyResult = processInstanceQueryImpl
        .processInstanceBusinessKey("Business Key");

    // Assert
    assertEquals("Business Key", processInstanceQueryImpl.getBusinessKey());
    assertSame(processInstanceQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  public void testProcessInstanceBusinessKey2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processInstanceBusinessKey(null));
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processInstanceBusinessKey(String, String)}
   */
  @Test
  public void testProcessInstanceBusinessKey3() {
    // Arrange and Act
    ProcessInstanceQuery actualProcessInstanceBusinessKeyResult = processInstanceQueryImpl
        .processInstanceBusinessKey("Business Key", "Process Definition Key");

    // Assert
    assertEquals("Business Key", processInstanceQueryImpl.getBusinessKey());
    assertEquals("Process Definition Key", processInstanceQueryImpl.getProcessDefinitionKey());
    assertSame(processInstanceQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processInstanceBusinessKey(String, String)}
   */
  @Test
  public void testProcessInstanceBusinessKey4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processInstanceBusinessKey(null, "Process Definition Key"));
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processInstanceTenantId(String)}
   */
  @Test
  public void testProcessInstanceTenantId() {
    // Arrange and Act
    ProcessInstanceQuery actualProcessInstanceTenantIdResult = processInstanceQueryImpl.processInstanceTenantId("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getTenantId());
    assertSame(processInstanceQueryImpl, actualProcessInstanceTenantIdResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processInstanceTenantId(String)}
   */
  @Test
  public void testProcessInstanceTenantId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> processInstanceQueryImpl.processInstanceTenantId(null));
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processInstanceTenantIdLike(String)}
   */
  @Test
  public void testProcessInstanceTenantIdLike() {
    // Arrange and Act
    ProcessInstanceQuery actualProcessInstanceTenantIdLikeResult = processInstanceQueryImpl
        .processInstanceTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", processInstanceQueryImpl.getTenantIdLike());
    assertSame(processInstanceQueryImpl, actualProcessInstanceTenantIdLikeResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processInstanceTenantIdLike(String)}
   */
  @Test
  public void testProcessInstanceTenantIdLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processInstanceTenantIdLike(null));
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processInstanceWithoutTenantId()}
   */
  @Test
  public void testProcessInstanceWithoutTenantId() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessInstanceWithoutTenantIdResult = processInstanceQueryImpl
        .processInstanceWithoutTenantId();

    // Assert
    assertTrue(processInstanceQueryImpl.isWithoutTenantId());
    assertSame(processInstanceQueryImpl, actualProcessInstanceWithoutTenantIdResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  public void testProcessDefinitionCategory() {
    // Arrange and Act
    ProcessInstanceQuery actualProcessDefinitionCategoryResult = processInstanceQueryImpl
        .processDefinitionCategory("Process Definition Category");

    // Assert
    assertEquals("Process Definition Category", processInstanceQueryImpl.getProcessDefinitionCategory());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionCategoryResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  public void testProcessDefinitionCategory2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processDefinitionCategory(null));
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processDefinitionName(String)}
   */
  @Test
  public void testProcessDefinitionName() {
    // Arrange and Act
    ProcessInstanceQuery actualProcessDefinitionNameResult = processInstanceQueryImpl
        .processDefinitionName("Process Definition Name");

    // Assert
    assertEquals("Process Definition Name", processInstanceQueryImpl.getProcessDefinitionName());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processDefinitionName(String)}
   */
  @Test
  public void testProcessDefinitionName2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> processInstanceQueryImpl.processDefinitionName(null));
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  public void testProcessDefinitionVersion() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualProcessDefinitionVersionResult = processInstanceQueryImpl.processDefinitionVersion(1);

    // Assert
    assertEquals(1, processInstanceQueryImpl.getProcessDefinitionVersion().intValue());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionVersionResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId() {
    // Arrange and Act
    ProcessInstanceQueryImpl actualProcessDefinitionIdResult = processInstanceQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getProcessDefinitionId());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> processInstanceQueryImpl.processDefinitionId(null));
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}
   */
  @Test
  public void testProcessDefinitionIds() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processDefinitionIds(new HashSet<>()));
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey() {
    // Arrange and Act
    ProcessInstanceQueryImpl actualProcessDefinitionKeyResult = processInstanceQueryImpl
        .processDefinitionKey("Process Definition Key");

    // Assert
    assertEquals("Process Definition Key", processInstanceQueryImpl.getProcessDefinitionKey());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> processInstanceQueryImpl.processDefinitionKey(null));
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  public void testProcessDefinitionKeys() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processDefinitionKeys(new HashSet<>()));
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#deploymentId(String)}
   */
  @Test
  public void testDeploymentId() {
    // Arrange and Act
    ProcessInstanceQueryImpl actualDeploymentIdResult = processInstanceQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getDeploymentId());
    assertSame(processInstanceQueryImpl, actualDeploymentIdResult);
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  public void testDeploymentIdIn() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();
    ArrayList<String> deploymentIds = new ArrayList<>();

    // Act
    ProcessInstanceQueryImpl actualDeploymentIdInResult = processInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, processInstanceQueryImpl.getDeploymentIds());
    assertSame(processInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#superProcessInstanceId(String)}
   */
  @Test
  public void testSuperProcessInstanceId() {
    // Arrange and Act
    ProcessInstanceQuery actualSuperProcessInstanceIdResult = processInstanceQueryImpl.superProcessInstanceId("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getSuperProcessInstanceId());
    assertSame(processInstanceQueryImpl, actualSuperProcessInstanceIdResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#subProcessInstanceId(String)}
   */
  @Test
  public void testSubProcessInstanceId() {
    // Arrange and Act
    ProcessInstanceQuery actualSubProcessInstanceIdResult = processInstanceQueryImpl.subProcessInstanceId("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getSubProcessInstanceId());
    assertSame(processInstanceQueryImpl, actualSubProcessInstanceIdResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#excludeSubprocesses(boolean)}
   */
  @Test
  public void testExcludeSubprocesses() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualExcludeSubprocessesResult = processInstanceQueryImpl.excludeSubprocesses(true);

    // Assert
    assertTrue(processInstanceQueryImpl.isExcludeSubprocesses());
    assertSame(processInstanceQueryImpl, actualExcludeSubprocessesResult);
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#involvedUser(String)}
   */
  @Test
  public void testInvolvedUser() {
    // Arrange and Act
    ProcessInstanceQuery actualInvolvedUserResult = processInstanceQueryImpl.involvedUser("Involved User");

    // Assert
    assertEquals("Involved User", processInstanceQueryImpl.getInvolvedUser());
    assertSame(processInstanceQueryImpl, actualInvolvedUserResult);
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#involvedUser(String)}
   */
  @Test
  public void testInvolvedUser2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> processInstanceQueryImpl.involvedUser(null));
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#active()}
   */
  @Test
  public void testActive() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.active());
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#suspended()}
   */
  @Test
  public void testSuspended() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.suspended());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processInstanceName(String)}
   */
  @Test
  public void testProcessInstanceName() {
    // Arrange and Act
    ProcessInstanceQuery actualProcessInstanceNameResult = processInstanceQueryImpl.processInstanceName("Name");

    // Assert
    assertEquals("Name", processInstanceQueryImpl.getName());
    assertSame(processInstanceQueryImpl, actualProcessInstanceNameResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processInstanceNameLike(String)}
   */
  @Test
  public void testProcessInstanceNameLike() {
    // Arrange and Act
    ProcessInstanceQuery actualProcessInstanceNameLikeResult = processInstanceQueryImpl
        .processInstanceNameLike("Name Like");

    // Assert
    assertEquals("Name Like", processInstanceQueryImpl.getNameLike());
    assertSame(processInstanceQueryImpl, actualProcessInstanceNameLikeResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#processInstanceNameLikeIgnoreCase(String)}
   */
  @Test
  public void testProcessInstanceNameLikeIgnoreCase() {
    // Arrange and Act
    ProcessInstanceQuery actualProcessInstanceNameLikeIgnoreCaseResult = processInstanceQueryImpl
        .processInstanceNameLikeIgnoreCase("Name Like Ignore Case");

    // Assert
    assertEquals("name like ignore case", processInstanceQueryImpl.getNameLikeIgnoreCase());
    assertSame(processInstanceQueryImpl, actualProcessInstanceNameLikeIgnoreCaseResult);
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#or()}
   */
  @Test
  public void testOr() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualOrResult = processInstanceQueryImpl.or();

    // Assert
    ProcessInstanceQueryImpl processInstanceQueryImpl2 = processInstanceQueryImpl.currentOrQueryObject;
    assertEquals("RES.ID_ asc", processInstanceQueryImpl2.getOrderBy());
    assertEquals("RES.ID_ asc", processInstanceQueryImpl2.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", processInstanceQueryImpl2.getMssqlOrDB2OrderBy());
    assertNull(processInstanceQueryImpl2.getProcessDefinitionVersion());
    assertNull(processInstanceQueryImpl2.getProcessInstanceVariablesLimit());
    assertNull(processInstanceQueryImpl2.getDatabaseType());
    assertNull(processInstanceQueryImpl2.getActivityId());
    assertNull(processInstanceQueryImpl2.getBusinessKey());
    assertNull(processInstanceQueryImpl2.getDeploymentId());
    assertNull(processInstanceQueryImpl2.getExecutionId());
    assertNull(processInstanceQueryImpl2.getInvolvedUser());
    assertNull(processInstanceQueryImpl2.getName());
    assertNull(processInstanceQueryImpl2.getNameLike());
    assertNull(processInstanceQueryImpl2.getNameLikeIgnoreCase());
    assertNull(processInstanceQueryImpl2.getParentId());
    assertNull(processInstanceQueryImpl2.getProcessDefinitionCategory());
    assertNull(processInstanceQueryImpl2.getProcessDefinitionId());
    assertNull(processInstanceQueryImpl2.getProcessDefinitionKey());
    assertNull(processInstanceQueryImpl2.getProcessDefinitionName());
    assertNull(processInstanceQueryImpl2.getProcessInstanceId());
    assertNull(processInstanceQueryImpl2.getRootProcessInstanceId());
    assertNull(processInstanceQueryImpl2.getStartedBy());
    assertNull(processInstanceQueryImpl2.getSubProcessInstanceId());
    assertNull(processInstanceQueryImpl2.getSuperProcessInstanceId());
    assertNull(processInstanceQueryImpl2.getTenantId());
    assertNull(processInstanceQueryImpl2.getTenantIdLike());
    assertNull(processInstanceQueryImpl2.orderBy);
    assertNull(processInstanceQueryImpl2.activityId);
    assertNull(processInstanceQueryImpl2.locale);
    assertNull(processInstanceQueryImpl2.getStartedAfter());
    assertNull(processInstanceQueryImpl2.getStartedBefore());
    assertNull(processInstanceQueryImpl2.getDeploymentIds());
    assertNull(processInstanceQueryImpl2.getInvolvedGroups());
    assertNull(processInstanceQueryImpl2.getEventSubscriptions());
    assertNull(processInstanceQueryImpl2.getProcessDefinitionIds());
    assertNull(processInstanceQueryImpl2.getProcessDefinitionKeys());
    assertNull(processInstanceQueryImpl2.getProcessInstanceIds());
    assertNull(processInstanceQueryImpl2.nullHandlingOnOrder);
    assertNull(processInstanceQueryImpl2.resultType);
    assertNull(processInstanceQueryImpl2.currentOrQueryObject);
    assertNull(processInstanceQueryImpl2.commandContext);
    assertNull(processInstanceQueryImpl2.commandExecutor);
    assertNull(processInstanceQueryImpl2.getSuspensionState());
    assertNull(processInstanceQueryImpl2.orderProperty);
    assertEquals(0, processInstanceQueryImpl2.getFirstResult());
    assertEquals(1, processInstanceQueryImpl2.getFirstRow());
    assertFalse(processInstanceQueryImpl2.hasLocalQueryVariableValue());
    assertFalse(processInstanceQueryImpl2.hasNonLocalQueryVariableValue());
    assertFalse(processInstanceQueryImpl2.isExcludeSubprocesses());
    assertFalse(processInstanceQueryImpl2.isIncludeChildExecutionsWithBusinessKeyQuery());
    assertFalse(processInstanceQueryImpl2.isIncludeProcessVariables());
    assertFalse(processInstanceQueryImpl2.isOnlyChildExecutions());
    assertFalse(processInstanceQueryImpl2.isOnlyProcessInstanceExecutions());
    assertFalse(processInstanceQueryImpl2.isOnlySubProcessExecutions());
    assertFalse(processInstanceQueryImpl2.isWithoutTenantId());
    assertFalse(processInstanceQueryImpl2.inOrStatement);
    assertFalse(processInstanceQueryImpl2.withJobException);
    assertFalse(processInstanceQueryImpl2.withLocalizationFallback);
    assertTrue(processInstanceQueryImpl2.getOnlyProcessInstances());
    assertTrue(processInstanceQueryImpl.inOrStatement);
    assertEquals(Integer.MAX_VALUE, processInstanceQueryImpl2.getLastRow());
    assertEquals(Integer.MAX_VALUE, processInstanceQueryImpl2.getMaxResults());
    assertSame(processInstanceQueryImpl, actualOrResult);
    ProcessInstanceQueryImpl expectedParameter = ((ProcessInstanceQueryImpl) actualOrResult).currentOrQueryObject;
    assertSame(expectedParameter, processInstanceQueryImpl2.getParameter());
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#endOr()}
   */
  @Test
  public void testEndOr() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ProcessInstanceQueryImpl()).endOr());
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueEquals(Object)}
   */
  @Test
  public void testVariableValueEquals() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act
    ProcessInstanceQuery actualVariableValueEqualsResult = processInstanceQueryImpl
        .variableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEquals2() {
    // Arrange and Act
    ProcessInstanceQuery actualVariableValueEqualsResult = processInstanceQueryImpl.variableValueEquals("Variable Name",
        JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEquals3() {
    // Arrange and Act
    ProcessInstanceQuery actualVariableValueEqualsResult = processInstanceQueryImpl.variableValueEquals("Variable Name",
        null);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEquals() {
    // Arrange and Act
    ProcessInstanceQuery actualVariableValueNotEqualsResult = processInstanceQueryImpl
        .variableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEquals2() {
    // Arrange and Act
    ProcessInstanceQuery actualVariableValueNotEqualsResult = processInstanceQueryImpl
        .variableValueNotEquals("Variable Name", null);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCase() {
    // Arrange and Act
    ProcessInstanceQuery actualVariableValueEqualsIgnoreCaseResult = processInstanceQueryImpl
        .variableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCase() {
    // Arrange and Act
    ProcessInstanceQuery actualVariableValueNotEqualsIgnoreCaseResult = processInstanceQueryImpl
        .variableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueGreaterThan(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThan() {
    // Arrange and Act
    ProcessInstanceQuery actualVariableValueGreaterThanResult = processInstanceQueryImpl
        .variableValueGreaterThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueGreaterThanResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqual() {
    // Arrange and Act
    ProcessInstanceQuery actualVariableValueGreaterThanOrEqualResult = processInstanceQueryImpl
        .variableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueLessThan(String, Object)}
   */
  @Test
  public void testVariableValueLessThan() {
    // Arrange and Act
    ProcessInstanceQuery actualVariableValueLessThanResult = processInstanceQueryImpl.variableValueLessThan("Name",
        JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueLessThanResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueLessThanOrEqual() {
    // Arrange and Act
    ProcessInstanceQuery actualVariableValueLessThanOrEqualResult = processInstanceQueryImpl
        .variableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueLessThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueLike(String, String)}
   */
  @Test
  public void testVariableValueLike() {
    // Arrange and Act
    ProcessInstanceQuery actualVariableValueLikeResult = processInstanceQueryImpl.variableValueLike("Name", "42");

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueLikeResult);
  }

  /**
   * Method under test:
   * {@link ProcessInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueLikeIgnoreCase() {
    // Arrange and Act
    ProcessInstanceQuery actualVariableValueLikeIgnoreCaseResult = processInstanceQueryImpl
        .variableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(processInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(processInstanceQueryImpl, actualVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#startedBefore(Date)}
   */
  @Test
  public void testStartedBefore() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();
    Date beforeTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.startedBefore(beforeTime));
    assertSame(beforeTime, processInstanceQueryImpl.getStartedBefore());
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#startedAfter(Date)}
   */
  @Test
  public void testStartedAfter() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();
    Date afterTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.startedAfter(afterTime));
    assertSame(afterTime, processInstanceQueryImpl.getStartedAfter());
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#startedBy(String)}
   */
  @Test
  public void testStartedBy() {
    // Arrange and Act
    ProcessInstanceQuery actualStartedByResult = processInstanceQueryImpl.startedBy("42");

    // Assert
    assertEquals("42", processInstanceQueryImpl.getStartedBy());
    assertSame(processInstanceQueryImpl, actualStartedByResult);
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  public void testGetMssqlOrDB2OrderBy() {
    // Arrange, Act and Assert
    assertEquals("TEMPRES_ID_ asc", (new ProcessInstanceQueryImpl()).getMssqlOrDB2OrderBy());
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#iswithException()}
   */
  @Test
  public void testIswithException() {
    // Arrange, Act and Assert
    assertFalse((new ProcessInstanceQueryImpl()).iswithException());
  }

  /**
   * Method under test: {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}
   */
  @Test
  public void testInvolvedGroupsIn() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.involvedGroupsIn(new ArrayList<>()));
  }
}
