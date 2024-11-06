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
   * Test {@link ExecutionQueryImpl#ExecutionQueryImpl()}.
   * <p>
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

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ExecutionQueryImpl} ProcessDefinitionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId_when42_thenExecutionQueryImplProcessDefinitionIdIs42() {
    // Arrange and Act
    ExecutionQueryImpl actualProcessDefinitionIdResult = executionQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getProcessDefinitionId());
    assertSame(executionQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.processDefinitionId(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionKey(String)}.
   * <p>
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
   * Test {@link ExecutionQueryImpl#processDefinitionKey(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.processDefinitionKey(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionCategory(String)}.
   * <p>
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
   * Test {@link ExecutionQueryImpl#processDefinitionCategory(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  public void testProcessDefinitionCategory_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.processDefinitionCategory(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionName(String)}.
   * <p>
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
   * Test {@link ExecutionQueryImpl#processDefinitionName(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#processDefinitionName(String)}
   */
  @Test
  public void testProcessDefinitionName_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.processDefinitionName(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionVersion(Integer)}.
   * <p>
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
   * Test {@link ExecutionQueryImpl#processDefinitionVersion(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  public void testProcessDefinitionVersion_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).processDefinitionVersion(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ExecutionQueryImpl} ProcessInstanceId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId_when42_thenExecutionQueryImplProcessInstanceIdIs42() {
    // Arrange and Act
    ExecutionQueryImpl actualProcessInstanceIdResult = executionQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getProcessInstanceId());
    assertSame(executionQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.processInstanceId(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#rootProcessInstanceId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ExecutionQueryImpl} RootProcessInstanceId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#rootProcessInstanceId(String)}
   */
  @Test
  public void testRootProcessInstanceId_when42_thenExecutionQueryImplRootProcessInstanceIdIs42() {
    // Arrange and Act
    ExecutionQueryImpl actualRootProcessInstanceIdResult = executionQueryImpl.rootProcessInstanceId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getRootProcessInstanceId());
    assertSame(executionQueryImpl, actualRootProcessInstanceIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#rootProcessInstanceId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#rootProcessInstanceId(String)}
   */
  @Test
  public void testRootProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.rootProcessInstanceId(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceBusinessKey(String)} with
   * {@code businessKey}.
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  public void testProcessInstanceBusinessKeyWithBusinessKey() {
    // Arrange and Act
    ExecutionQuery actualProcessInstanceBusinessKeyResult = executionQueryImpl
        .processInstanceBusinessKey("Business Key");

    // Assert
    assertEquals("Business Key", executionQueryImpl.getBusinessKey());
    assertSame(executionQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceBusinessKey(String)} with
   * {@code businessKey}.
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  public void testProcessInstanceBusinessKeyWithBusinessKey2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.processInstanceBusinessKey(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   * with {@code processInstanceBusinessKey}, {@code includeChildExecutions}.
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   */
  @Test
  public void testProcessInstanceBusinessKeyWithProcessInstanceBusinessKeyIncludeChildExecutions() {
    // Arrange and Act
    ExecutionQuery actualProcessInstanceBusinessKeyResult = executionQueryImpl
        .processInstanceBusinessKey("Process Instance Business Key", true);

    // Assert
    assertEquals("Process Instance Business Key", executionQueryImpl.getBusinessKey());
    assertTrue(executionQueryImpl.isIncludeChildExecutionsWithBusinessKeyQuery());
    assertSame(executionQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   * with {@code processInstanceBusinessKey}, {@code includeChildExecutions}.
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   */
  @Test
  public void testProcessInstanceBusinessKeyWithProcessInstanceBusinessKeyIncludeChildExecutions2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.processInstanceBusinessKey(null, true));
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   * with {@code processInstanceBusinessKey}, {@code includeChildExecutions}.
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   */
  @Test
  public void testProcessInstanceBusinessKeyWithProcessInstanceBusinessKeyIncludeChildExecutions3() {
    // Arrange and Act
    ExecutionQuery actualProcessInstanceBusinessKeyResult = executionQueryImpl
        .processInstanceBusinessKey("Process Instance Business Key", false);

    // Assert
    assertEquals("Process Instance Business Key", executionQueryImpl.getBusinessKey());
    assertFalse(executionQueryImpl.isIncludeChildExecutionsWithBusinessKeyQuery());
    assertSame(executionQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   * with {@code processInstanceBusinessKey}, {@code includeChildExecutions}.
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   */
  @Test
  public void testProcessInstanceBusinessKeyWithProcessInstanceBusinessKeyIncludeChildExecutions4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.processInstanceBusinessKey(null, false));
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionKeys(Set)}.
   * <ul>
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()}
   * ProcessDefinitionKeys is {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  public void testProcessDefinitionKeys_thenExecutionQueryImplProcessDefinitionKeysIsHashSet() {
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
   * Test {@link ExecutionQueryImpl#processDefinitionKeys(Set)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  public void testProcessDefinitionKeys_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ExecutionQueryImpl()).processDefinitionKeys(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#executionId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ExecutionQueryImpl} ExecutionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId_when42_thenExecutionQueryImplExecutionIdIs42() {
    // Arrange and Act
    ExecutionQueryImpl actualExecutionIdResult = executionQueryImpl.executionId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getExecutionId());
    assertSame(executionQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#executionId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.executionId(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#activityId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#activityId(String)}
   */
  @Test
  public void testActivityId_when42_thenReturnActivityIdIs42() {
    // Arrange and Act
    ExecutionQueryImpl actualActivityIdResult = executionQueryImpl.activityId("42");

    // Assert
    assertEquals("42", actualActivityIdResult.getActivityId());
    assertEquals("42", executionQueryImpl.getActivityId());
    assertTrue(actualActivityIdResult.isActive());
    assertTrue(executionQueryImpl.isActive());
  }

  /**
   * Test {@link ExecutionQueryImpl#activityId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return ActivityId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#activityId(String)}
   */
  @Test
  public void testActivityId_whenNull_thenReturnActivityIdIsNull() {
    // Arrange and Act
    ExecutionQueryImpl actualActivityIdResult = executionQueryImpl.activityId(null);

    // Assert
    assertNull(actualActivityIdResult.getActivityId());
    assertNull(executionQueryImpl.getActivityId());
    assertFalse(actualActivityIdResult.isActive());
    assertFalse(executionQueryImpl.isActive());
  }

  /**
   * Test {@link ExecutionQueryImpl#parentId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ExecutionQueryImpl} ParentId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#parentId(String)}
   */
  @Test
  public void testParentId_when42_thenExecutionQueryImplParentIdIs42() {
    // Arrange and Act
    ExecutionQueryImpl actualParentIdResult = executionQueryImpl.parentId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getParentId());
    assertSame(executionQueryImpl, actualParentIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#parentId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#parentId(String)}
   */
  @Test
  public void testParentId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.parentId(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#executionTenantId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ExecutionQueryImpl} TenantId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#executionTenantId(String)}
   */
  @Test
  public void testExecutionTenantId_when42_thenExecutionQueryImplTenantIdIs42() {
    // Arrange and Act
    ExecutionQueryImpl actualExecutionTenantIdResult = executionQueryImpl.executionTenantId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getTenantId());
    assertSame(executionQueryImpl, actualExecutionTenantIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#executionTenantId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#executionTenantId(String)}
   */
  @Test
  public void testExecutionTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.executionTenantId(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#executionTenantIdLike(String)}.
   * <ul>
   *   <li>Then {@link ExecutionQueryImpl} TenantIdLike is
   * {@code Tenant Id Like}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#executionTenantIdLike(String)}
   */
  @Test
  public void testExecutionTenantIdLike_thenExecutionQueryImplTenantIdLikeIsTenantIdLike() {
    // Arrange and Act
    ExecutionQueryImpl actualExecutionTenantIdLikeResult = executionQueryImpl.executionTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", executionQueryImpl.getTenantIdLike());
    assertSame(executionQueryImpl, actualExecutionTenantIdLikeResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#executionTenantIdLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#executionTenantIdLike(String)}
   */
  @Test
  public void testExecutionTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.executionTenantIdLike(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#executionWithoutTenantId()}.
   * <p>
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
   * Test {@link ExecutionQueryImpl#signalEventSubscription(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#signalEventSubscription(String)}
   */
  @Test
  public void testSignalEventSubscription_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.signalEventSubscription(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#signalEventSubscription(String)}.
   * <ul>
   *   <li>When {@code Signal Name}.</li>
   *   <li>Then return {@link ExecutionQueryImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#signalEventSubscription(String)}
   */
  @Test
  public void testSignalEventSubscription_whenSignalName_thenReturnExecutionQueryImpl() {
    // Arrange, Act and Assert
    assertSame(executionQueryImpl, executionQueryImpl.signalEventSubscription("Signal Name"));
  }

  /**
   * Test {@link ExecutionQueryImpl#signalEventSubscriptionName(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#signalEventSubscriptionName(String)}
   */
  @Test
  public void testSignalEventSubscriptionName_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.signalEventSubscriptionName(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#signalEventSubscriptionName(String)}.
   * <ul>
   *   <li>When {@code Signal Name}.</li>
   *   <li>Then return {@link ExecutionQueryImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#signalEventSubscriptionName(String)}
   */
  @Test
  public void testSignalEventSubscriptionName_whenSignalName_thenReturnExecutionQueryImpl() {
    // Arrange, Act and Assert
    assertSame(executionQueryImpl, executionQueryImpl.signalEventSubscriptionName("Signal Name"));
  }

  /**
   * Test {@link ExecutionQueryImpl#messageEventSubscriptionName(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#messageEventSubscriptionName(String)}
   */
  @Test
  public void testMessageEventSubscriptionName_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.messageEventSubscriptionName(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#messageEventSubscriptionName(String)}.
   * <ul>
   *   <li>When {@code Message Name}.</li>
   *   <li>Then return {@link ExecutionQueryImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#messageEventSubscriptionName(String)}
   */
  @Test
  public void testMessageEventSubscriptionName_whenMessageName_thenReturnExecutionQueryImpl() {
    // Arrange, Act and Assert
    assertSame(executionQueryImpl, executionQueryImpl.messageEventSubscriptionName("Message Name"));
  }

  /**
   * Test {@link ExecutionQueryImpl#eventSubscription(String, String)}.
   * <ul>
   *   <li>When {@code Event Type}.</li>
   *   <li>Then return {@link ExecutionQueryImpl}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  public void testEventSubscription_whenEventType_thenReturnExecutionQueryImpl() {
    // Arrange, Act and Assert
    assertSame(executionQueryImpl, executionQueryImpl.eventSubscription("Event Type", "Event Name"));
  }

  /**
   * Test {@link ExecutionQueryImpl#eventSubscription(String, String)}.
   * <ul>
   *   <li>When {@code Event Type}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  public void testEventSubscription_whenEventType_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.eventSubscription("Event Type", null));
  }

  /**
   * Test {@link ExecutionQueryImpl#eventSubscription(String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  public void testEventSubscription_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.eventSubscription(null, "Event Name"));
  }

  /**
   * Test {@link ExecutionQueryImpl#processVariableValueEquals(String, Object)}
   * with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueEqualsWithVariableNameVariableValue_whenNull() {
    // Arrange and Act
    ExecutionQuery actualProcessVariableValueEqualsResult = executionQueryImpl
        .processVariableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processVariableValueEquals(String, Object)}
   * with {@code variableName}, {@code variableValue}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueEqualsWithVariableNameVariableValue_whenNull2() {
    // Arrange and Act
    ExecutionQuery actualProcessVariableValueEqualsResult = executionQueryImpl
        .processVariableValueEquals("Variable Name", null);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processVariableValueEquals(Object)} with
   * {@code variableValue}.
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueEquals(Object)}
   */
  @Test
  public void testProcessVariableValueEqualsWithVariableValue() {
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
   * Test
   * {@link ExecutionQueryImpl#processVariableValueNotEquals(String, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueNotEquals_whenNull() {
    // Arrange and Act
    ExecutionQuery actualProcessVariableValueNotEqualsResult = executionQueryImpl
        .processVariableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Test
   * {@link ExecutionQueryImpl#processVariableValueNotEquals(String, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueNotEquals_whenNull2() {
    // Arrange and Act
    ExecutionQuery actualProcessVariableValueNotEqualsResult = executionQueryImpl
        .processVariableValueNotEquals("Variable Name", null);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Test
   * {@link ExecutionQueryImpl#processVariableValueEqualsIgnoreCase(String, String)}.
   * <p>
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
   * Test
   * {@link ExecutionQueryImpl#processVariableValueNotEqualsIgnoreCase(String, String)}.
   * <p>
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
   * Test {@link ExecutionQueryImpl#processVariableValueLike(String, String)}.
   * <p>
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
   * Test
   * {@link ExecutionQueryImpl#processVariableValueLikeIgnoreCase(String, String)}.
   * <p>
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
   * Test {@link ExecutionQueryImpl#startedBefore(Date)}.
   * <ul>
   *   <li>Then return {@link ExecutionQueryImpl#ExecutionQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#startedBefore(Date)}
   */
  @Test
  public void testStartedBefore_thenReturnExecutionQueryImpl() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    Date beforeTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(executionQueryImpl, executionQueryImpl.startedBefore(beforeTime));
    assertSame(beforeTime, executionQueryImpl.getStartedBefore());
  }

  /**
   * Test {@link ExecutionQueryImpl#startedBefore(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#startedBefore(Date)}
   */
  @Test
  public void testStartedBefore_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ExecutionQueryImpl()).startedBefore(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#startedAfter(Date)}.
   * <ul>
   *   <li>Then return {@link ExecutionQueryImpl#ExecutionQueryImpl()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#startedAfter(Date)}
   */
  @Test
  public void testStartedAfter_thenReturnExecutionQueryImpl() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    Date afterTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(executionQueryImpl, executionQueryImpl.startedAfter(afterTime));
    assertSame(afterTime, executionQueryImpl.getStartedAfter());
  }

  /**
   * Test {@link ExecutionQueryImpl#startedAfter(Date)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#startedAfter(Date)}
   */
  @Test
  public void testStartedAfter_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ExecutionQueryImpl()).startedAfter(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#startedBy(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link ExecutionQueryImpl} StartedBy is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#startedBy(String)}
   */
  @Test
  public void testStartedBy_when42_thenExecutionQueryImplStartedByIs42() {
    // Arrange and Act
    ExecutionQuery actualStartedByResult = executionQueryImpl.startedBy("42");

    // Assert
    assertEquals("42", executionQueryImpl.getStartedBy());
    assertSame(executionQueryImpl, actualStartedByResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#startedBy(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#startedBy(String)}
   */
  @Test
  public void testStartedBy_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.startedBy(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#localize(Execution, String)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then calls {@link ExecutionEntityImpl#getProcessDefinitionId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutionQueryImpl#localize(Execution, String)}
   */
  @Test
  public void testLocalize_given42_thenCallsGetProcessDefinitionId() {
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
}
