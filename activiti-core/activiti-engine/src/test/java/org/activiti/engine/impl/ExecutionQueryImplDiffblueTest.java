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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExecutionQueryImplDiffblueTest {
  @InjectMocks
  private ExecutionQueryImpl executionQueryImpl;

  /**
   * Method under test: {@link ExecutionQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId() {
    // Arrange and Act
    ExecutionQueryImpl actualProcessDefinitionIdResult = executionQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getProcessDefinitionId());
    assertSame(executionQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.processDefinitionId(null));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey() {
    // Arrange and Act
    ExecutionQueryImpl actualProcessDefinitionKeyResult = executionQueryImpl
        .processDefinitionKey("Process Definition Key");

    // Assert
    assertEquals("Process Definition Key", executionQueryImpl.getProcessDefinitionKey());
    assertSame(executionQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.processDefinitionKey(null));
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  public void testProcessDefinitionCategory() {
    // Arrange and Act
    ExecutionQuery actualProcessDefinitionCategoryResult = executionQueryImpl
        .processDefinitionCategory("Process Definition Category");

    // Assert
    assertEquals("Process Definition Category", executionQueryImpl.getProcessDefinitionCategory());
    assertSame(executionQueryImpl, actualProcessDefinitionCategoryResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  public void testProcessDefinitionCategory2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.processDefinitionCategory(null));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#processDefinitionName(String)}
   */
  @Test
  public void testProcessDefinitionName() {
    // Arrange and Act
    ExecutionQuery actualProcessDefinitionNameResult = executionQueryImpl
        .processDefinitionName("Process Definition Name");

    // Assert
    assertEquals("Process Definition Name", executionQueryImpl.getProcessDefinitionName());
    assertSame(executionQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#processDefinitionName(String)}
   */
  @Test
  public void testProcessDefinitionName2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.processDefinitionName(null));
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  public void testProcessDefinitionVersion() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessDefinitionVersionResult = executionQueryImpl.processDefinitionVersion(1);

    // Assert
    assertEquals(1, executionQueryImpl.getProcessDefinitionVersion().intValue());
    assertSame(executionQueryImpl, actualProcessDefinitionVersionResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  public void testProcessDefinitionVersion2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).processDefinitionVersion(null));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId() {
    // Arrange and Act
    ExecutionQueryImpl actualProcessInstanceIdResult = executionQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getProcessInstanceId());
    assertSame(executionQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.processInstanceId(null));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#rootProcessInstanceId(String)}
   */
  @Test
  public void testRootProcessInstanceId() {
    // Arrange and Act
    ExecutionQueryImpl actualRootProcessInstanceIdResult = executionQueryImpl.rootProcessInstanceId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getRootProcessInstanceId());
    assertSame(executionQueryImpl, actualRootProcessInstanceIdResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#rootProcessInstanceId(String)}
   */
  @Test
  public void testRootProcessInstanceId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.rootProcessInstanceId(null));
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  public void testProcessInstanceBusinessKey() {
    // Arrange and Act
    ExecutionQuery actualProcessInstanceBusinessKeyResult = executionQueryImpl
        .processInstanceBusinessKey("Business Key");

    // Assert
    assertEquals("Business Key", executionQueryImpl.getBusinessKey());
    assertSame(executionQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  public void testProcessInstanceBusinessKey2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.processInstanceBusinessKey(null));
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   */
  @Test
  public void testProcessInstanceBusinessKey3() {
    // Arrange and Act
    ExecutionQuery actualProcessInstanceBusinessKeyResult = executionQueryImpl
        .processInstanceBusinessKey("Process Instance Business Key", true);

    // Assert
    assertEquals("Process Instance Business Key", executionQueryImpl.getBusinessKey());
    assertTrue(executionQueryImpl.isIncludeChildExecutionsWithBusinessKeyQuery());
    assertSame(executionQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   */
  @Test
  public void testProcessInstanceBusinessKey4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.processInstanceBusinessKey(null, true));
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   */
  @Test
  public void testProcessInstanceBusinessKey5() {
    // Arrange and Act
    ExecutionQuery actualProcessInstanceBusinessKeyResult = executionQueryImpl
        .processInstanceBusinessKey("Process Instance Business Key", false);

    // Assert
    assertEquals("Process Instance Business Key", executionQueryImpl.getBusinessKey());
    assertFalse(executionQueryImpl.isIncludeChildExecutionsWithBusinessKeyQuery());
    assertSame(executionQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   */
  @Test
  public void testProcessInstanceBusinessKey6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.processInstanceBusinessKey(null, false));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  public void testProcessDefinitionKeys() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    HashSet<String> processDefinitionKeys = new HashSet<>();

    // Act
    ExecutionQuery actualProcessDefinitionKeysResult = executionQueryImpl.processDefinitionKeys(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, executionQueryImpl.getProcessDefinitionKeys());
    assertSame(executionQueryImpl, actualProcessDefinitionKeysResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  public void testProcessDefinitionKeys2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ExecutionQueryImpl()).processDefinitionKeys(null));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId() {
    // Arrange and Act
    ExecutionQueryImpl actualExecutionIdResult = executionQueryImpl.executionId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getExecutionId());
    assertSame(executionQueryImpl, actualExecutionIdResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.executionId(null));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#activityId(String)}
   */
  @Test
  public void testActivityId() {
    // Arrange and Act
    ExecutionQueryImpl actualActivityIdResult = executionQueryImpl.activityId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getActivityId());
    assertTrue(executionQueryImpl.isActive());
    assertSame(executionQueryImpl, actualActivityIdResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#activityId(String)}
   */
  @Test
  public void testActivityId2() {
    // Arrange and Act
    ExecutionQueryImpl actualActivityIdResult = executionQueryImpl.activityId(null);

    // Assert
    assertNull(executionQueryImpl.getActivityId());
    assertFalse(executionQueryImpl.isActive());
    assertSame(executionQueryImpl, actualActivityIdResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#parentId(String)}
   */
  @Test
  public void testParentId() {
    // Arrange and Act
    ExecutionQueryImpl actualParentIdResult = executionQueryImpl.parentId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getParentId());
    assertSame(executionQueryImpl, actualParentIdResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#parentId(String)}
   */
  @Test
  public void testParentId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.parentId(null));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#executionTenantId(String)}
   */
  @Test
  public void testExecutionTenantId() {
    // Arrange and Act
    ExecutionQueryImpl actualExecutionTenantIdResult = executionQueryImpl.executionTenantId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getTenantId());
    assertSame(executionQueryImpl, actualExecutionTenantIdResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#executionTenantId(String)}
   */
  @Test
  public void testExecutionTenantId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.executionTenantId(null));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#executionTenantIdLike(String)}
   */
  @Test
  public void testExecutionTenantIdLike() {
    // Arrange and Act
    ExecutionQueryImpl actualExecutionTenantIdLikeResult = executionQueryImpl.executionTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", executionQueryImpl.getTenantIdLike());
    assertSame(executionQueryImpl, actualExecutionTenantIdLikeResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#executionTenantIdLike(String)}
   */
  @Test
  public void testExecutionTenantIdLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.executionTenantIdLike(null));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#executionWithoutTenantId()}
   */
  @Test
  public void testExecutionWithoutTenantId() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQueryImpl actualExecutionWithoutTenantIdResult = executionQueryImpl.executionWithoutTenantId();

    // Assert
    assertTrue(executionQueryImpl.isWithoutTenantId());
    assertSame(executionQueryImpl, actualExecutionWithoutTenantIdResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#signalEventSubscription(String)}
   */
  @Test
  public void testSignalEventSubscription() {
    // Arrange, Act and Assert
    assertSame(executionQueryImpl, executionQueryImpl.signalEventSubscription("Signal Name"));
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.signalEventSubscription(null));
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#signalEventSubscriptionName(String)}
   */
  @Test
  public void testSignalEventSubscriptionName() {
    // Arrange, Act and Assert
    assertSame(executionQueryImpl, executionQueryImpl.signalEventSubscriptionName("Signal Name"));
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.signalEventSubscriptionName(null));
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#messageEventSubscriptionName(String)}
   */
  @Test
  public void testMessageEventSubscriptionName() {
    // Arrange, Act and Assert
    assertSame(executionQueryImpl, executionQueryImpl.messageEventSubscriptionName("Message Name"));
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.messageEventSubscriptionName(null));
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  public void testEventSubscription() {
    // Arrange, Act and Assert
    assertSame(executionQueryImpl, executionQueryImpl.eventSubscription("Event Type", "Event Name"));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.eventSubscription(null, "Event Name"));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.eventSubscription("Event Type", null));
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueEquals(Object)}
   */
  @Test
  public void testProcessVariableValueEquals() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessVariableValueEqualsResult = executionQueryImpl
        .processVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueEquals2() {
    // Arrange and Act
    ExecutionQuery actualProcessVariableValueEqualsResult = executionQueryImpl
        .processVariableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueEquals3() {
    // Arrange and Act
    ExecutionQuery actualProcessVariableValueEqualsResult = executionQueryImpl
        .processVariableValueEquals("Variable Name", null);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueNotEquals() {
    // Arrange and Act
    ExecutionQuery actualProcessVariableValueNotEqualsResult = executionQueryImpl
        .processVariableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueNotEquals2() {
    // Arrange and Act
    ExecutionQuery actualProcessVariableValueNotEqualsResult = executionQueryImpl
        .processVariableValueNotEquals("Variable Name", null);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testProcessVariableValueEqualsIgnoreCase() {
    // Arrange and Act
    ExecutionQuery actualProcessVariableValueEqualsIgnoreCaseResult = executionQueryImpl
        .processVariableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testProcessVariableValueNotEqualsIgnoreCase() {
    // Arrange and Act
    ExecutionQuery actualProcessVariableValueNotEqualsIgnoreCaseResult = executionQueryImpl
        .processVariableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueLike(String, String)}
   */
  @Test
  public void testProcessVariableValueLike() {
    // Arrange and Act
    ExecutionQuery actualProcessVariableValueLikeResult = executionQueryImpl.processVariableValueLike("Name", "42");

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueLikeResult);
  }

  /**
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testProcessVariableValueLikeIgnoreCase() {
    // Arrange and Act
    ExecutionQuery actualProcessVariableValueLikeIgnoreCaseResult = executionQueryImpl
        .processVariableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#startedBefore(Date)}
   */
  @Test
  public void testStartedBefore() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    Date beforeTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(executionQueryImpl, executionQueryImpl.startedBefore(beforeTime));
    assertSame(beforeTime, executionQueryImpl.getStartedBefore());
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#startedBefore(Date)}
   */
  @Test
  public void testStartedBefore2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ExecutionQueryImpl()).startedBefore(null));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#startedAfter(Date)}
   */
  @Test
  public void testStartedAfter() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    Date afterTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(executionQueryImpl, executionQueryImpl.startedAfter(afterTime));
    assertSame(afterTime, executionQueryImpl.getStartedAfter());
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#startedAfter(Date)}
   */
  @Test
  public void testStartedAfter2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ExecutionQueryImpl()).startedAfter(null));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#startedBy(String)}
   */
  @Test
  public void testStartedBy() {
    // Arrange and Act
    ExecutionQuery actualStartedByResult = executionQueryImpl.startedBy("42");

    // Assert
    assertEquals("42", executionQueryImpl.getStartedBy());
    assertSame(executionQueryImpl, actualStartedByResult);
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#startedBy(String)}
   */
  @Test
  public void testStartedBy2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.startedBy(null));
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#localize(Execution, String)}
   */
  @Test
  public void testLocalize() {
    // Arrange
    ExecutionEntityImpl execution = mock(ExecutionEntityImpl.class);
    when(execution.getProcessDefinitionId()).thenReturn("42");
    doNothing().when(execution).setLocalizedDescription(Mockito.<String>any());
    doNothing().when(execution).setLocalizedName(Mockito.<String>any());

    // Act
    executionQueryImpl.localize(execution, "42");

    // Assert that nothing has changed
    verify(execution).getProcessDefinitionId();
    verify(execution).setLocalizedDescription(isNull());
    verify(execution).setLocalizedName(isNull());
  }

  /**
   * Method under test: {@link ExecutionQueryImpl#ExecutionQueryImpl()}
   */
  @Test
  public void testNewExecutionQueryImpl() {
    // Arrange and Act
    ExecutionQueryImpl actualExecutionQueryImpl = new ExecutionQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualExecutionQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualExecutionQueryImpl.getOrderByColumns());
    assertNull(actualExecutionQueryImpl.getProcessDefinitionVersion());
    assertNull(actualExecutionQueryImpl.getDatabaseType());
    assertNull(actualExecutionQueryImpl.getActivityId());
    assertNull(actualExecutionQueryImpl.getBusinessKey());
    assertNull(actualExecutionQueryImpl.getExecutionId());
    assertNull(actualExecutionQueryImpl.getInvolvedUser());
    assertNull(actualExecutionQueryImpl.getName());
    assertNull(actualExecutionQueryImpl.getNameLike());
    assertNull(actualExecutionQueryImpl.getNameLikeIgnoreCase());
    assertNull(actualExecutionQueryImpl.getParentId());
    assertNull(actualExecutionQueryImpl.getProcessDefinitionCategory());
    assertNull(actualExecutionQueryImpl.getProcessDefinitionId());
    assertNull(actualExecutionQueryImpl.getProcessDefinitionKey());
    assertNull(actualExecutionQueryImpl.getProcessDefinitionName());
    assertNull(actualExecutionQueryImpl.getProcessInstanceId());
    assertNull(actualExecutionQueryImpl.getProcessInstanceIds());
    assertNull(actualExecutionQueryImpl.getRootProcessInstanceId());
    assertNull(actualExecutionQueryImpl.getStartedBy());
    assertNull(actualExecutionQueryImpl.getSubProcessInstanceId());
    assertNull(actualExecutionQueryImpl.getSuperProcessInstanceId());
    assertNull(actualExecutionQueryImpl.getTenantId());
    assertNull(actualExecutionQueryImpl.getTenantIdLike());
    assertNull(actualExecutionQueryImpl.orderBy);
    assertNull(actualExecutionQueryImpl.deploymentId);
    assertNull(actualExecutionQueryImpl.locale);
    assertNull(actualExecutionQueryImpl.getStartedAfter());
    assertNull(actualExecutionQueryImpl.getStartedBefore());
    assertNull(actualExecutionQueryImpl.getInvolvedGroups());
    assertNull(actualExecutionQueryImpl.deploymentIds);
    assertNull(actualExecutionQueryImpl.getEventSubscriptions());
    assertNull(actualExecutionQueryImpl.getProcessDefinitionIds());
    assertNull(actualExecutionQueryImpl.getProcessDefinitionKeys());
    assertNull(actualExecutionQueryImpl.nullHandlingOnOrder);
    assertNull(actualExecutionQueryImpl.resultType);
    assertNull(actualExecutionQueryImpl.commandContext);
    assertNull(actualExecutionQueryImpl.commandExecutor);
    assertNull(actualExecutionQueryImpl.getSuspensionState());
    assertNull(actualExecutionQueryImpl.orderProperty);
    assertEquals(0, actualExecutionQueryImpl.getFirstResult());
    assertEquals(1, actualExecutionQueryImpl.getFirstRow());
    assertFalse(actualExecutionQueryImpl.hasLocalQueryVariableValue());
    assertFalse(actualExecutionQueryImpl.hasNonLocalQueryVariableValue());
    assertFalse(actualExecutionQueryImpl.getOnlyProcessInstances());
    assertFalse(actualExecutionQueryImpl.isActive());
    assertFalse(actualExecutionQueryImpl.isExcludeSubprocesses());
    assertFalse(actualExecutionQueryImpl.isIncludeChildExecutionsWithBusinessKeyQuery());
    assertFalse(actualExecutionQueryImpl.isOnlyChildExecutions());
    assertFalse(actualExecutionQueryImpl.isOnlyProcessInstanceExecutions());
    assertFalse(actualExecutionQueryImpl.isOnlySubProcessExecutions());
    assertFalse(actualExecutionQueryImpl.isProcessInstancesOnly());
    assertFalse(actualExecutionQueryImpl.isWithoutTenantId());
    assertFalse(actualExecutionQueryImpl.withLocalizationFallback);
    assertTrue(actualExecutionQueryImpl.getQueryVariableValues().isEmpty());
    assertTrue(actualExecutionQueryImpl.orQueryObjects.isEmpty());
    assertEquals(Integer.MAX_VALUE, actualExecutionQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualExecutionQueryImpl.getMaxResults());
    assertSame(actualExecutionQueryImpl, actualExecutionQueryImpl.getParameter());
  }
}
