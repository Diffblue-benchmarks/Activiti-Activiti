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
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.query.QueryProperty;
import org.activiti.engine.runtime.ExecutionQuery;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ExecutionQueryImplDiffblueTest {
  /**
   * Test {@link ExecutionQueryImpl#ExecutionQueryImpl()}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#ExecutionQueryImpl()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionQueryImpl.<init>()"})
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
    Object actualParameter = actualExecutionQueryImpl.getParameter();
    assertSame(actualExecutionQueryImpl, actualParameter);
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()} ProcessDefinitionId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId_when42_thenExecutionQueryImplProcessDefinitionIdIs42() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQueryImpl actualProcessDefinitionIdResult =
        executionQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getProcessDefinitionId());
    assertSame(executionQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.processDefinitionId(String)"})
  public void testProcessDefinitionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().processDefinitionId(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionKey(String)}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.processDefinitionKey(String)"})
  public void testProcessDefinitionKey() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQueryImpl actualProcessDefinitionKeyResult =
        executionQueryImpl.processDefinitionKey("Process Definition Key");

    // Assert
    assertEquals("Process Definition Key", executionQueryImpl.getProcessDefinitionKey());
    assertSame(executionQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionKey(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.processDefinitionKey(String)"})
  public void testProcessDefinitionKey_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().processDefinitionKey(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionCategory(String)}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processDefinitionCategory(String)"})
  public void testProcessDefinitionCategory() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessDefinitionCategoryResult =
        executionQueryImpl.processDefinitionCategory("Process Definition Category");

    // Assert
    assertEquals("Process Definition Category", executionQueryImpl.getProcessDefinitionCategory());
    assertSame(executionQueryImpl, actualProcessDefinitionCategoryResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionCategory(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processDefinitionCategory(String)"})
  public void testProcessDefinitionCategory_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().processDefinitionCategory(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionName(String)}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processDefinitionName(String)"})
  public void testProcessDefinitionName() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessDefinitionNameResult =
        executionQueryImpl.processDefinitionName("Process Definition Name");

    // Assert
    assertEquals("Process Definition Name", executionQueryImpl.getProcessDefinitionName());
    assertSame(executionQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionName(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processDefinitionName(String)"})
  public void testProcessDefinitionName_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().processDefinitionName(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionVersion(Integer)}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processDefinitionVersion(Integer)"})
  public void testProcessDefinitionVersion() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessDefinitionVersionResult =
        executionQueryImpl.processDefinitionVersion(1);

    // Assert
    assertEquals(1, executionQueryImpl.getProcessDefinitionVersion().intValue());
    assertSame(executionQueryImpl, actualProcessDefinitionVersionResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionVersion(Integer)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processDefinitionVersion(Integer)"})
  public void testProcessDefinitionVersion_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().processDefinitionVersion(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()} ProcessInstanceId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId_when42_thenExecutionQueryImplProcessInstanceIdIs42() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQueryImpl actualProcessInstanceIdResult = executionQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getProcessInstanceId());
    assertSame(executionQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().processInstanceId(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#rootProcessInstanceId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()} RootProcessInstanceId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#rootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.rootProcessInstanceId(String)"})
  public void testRootProcessInstanceId_when42_thenExecutionQueryImplRootProcessInstanceIdIs42() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQueryImpl actualRootProcessInstanceIdResult =
        executionQueryImpl.rootProcessInstanceId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getRootProcessInstanceId());
    assertSame(executionQueryImpl, actualRootProcessInstanceIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#rootProcessInstanceId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#rootProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.rootProcessInstanceId(String)"})
  public void testRootProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().rootProcessInstanceId(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceBusinessKey(String)} with {@code businessKey}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processInstanceBusinessKey(String)"})
  public void testProcessInstanceBusinessKeyWithBusinessKey() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessInstanceBusinessKeyResult =
        executionQueryImpl.processInstanceBusinessKey("Business Key");

    // Assert
    assertEquals("Business Key", executionQueryImpl.getBusinessKey());
    assertSame(executionQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceBusinessKey(String)} with {@code businessKey}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processInstanceBusinessKey(String)"})
  public void testProcessInstanceBusinessKeyWithBusinessKey2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().processInstanceBusinessKey(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)} with {@code
   * processInstanceBusinessKey}, {@code includeChildExecutions}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionQuery ExecutionQueryImpl.processInstanceBusinessKey(String, boolean)"
  })
  public void testProcessInstanceBusinessKeyWithProcessInstanceBusinessKeyIncludeChildExecutions() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessInstanceBusinessKeyResult =
        executionQueryImpl.processInstanceBusinessKey("Process Instance Business Key", true);

    // Assert
    assertEquals("Process Instance Business Key", executionQueryImpl.getBusinessKey());
    assertTrue(executionQueryImpl.isIncludeChildExecutionsWithBusinessKeyQuery());
    assertSame(executionQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)} with {@code
   * processInstanceBusinessKey}, {@code includeChildExecutions}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionQuery ExecutionQueryImpl.processInstanceBusinessKey(String, boolean)"
  })
  public void
      testProcessInstanceBusinessKeyWithProcessInstanceBusinessKeyIncludeChildExecutions2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().processInstanceBusinessKey(null, false));
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)} with {@code
   * processInstanceBusinessKey}, {@code includeChildExecutions}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionQuery ExecutionQueryImpl.processInstanceBusinessKey(String, boolean)"
  })
  public void
      testProcessInstanceBusinessKeyWithProcessInstanceBusinessKeyIncludeChildExecutions3() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessInstanceBusinessKeyResult =
        executionQueryImpl.processInstanceBusinessKey("Process Instance Business Key", false);

    // Assert
    assertEquals("Process Instance Business Key", executionQueryImpl.getBusinessKey());
    assertFalse(executionQueryImpl.isIncludeChildExecutionsWithBusinessKeyQuery());
    assertSame(executionQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)} with {@code
   * processInstanceBusinessKey}, {@code includeChildExecutions}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processInstanceBusinessKey(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionQuery ExecutionQueryImpl.processInstanceBusinessKey(String, boolean)"
  })
  public void
      testProcessInstanceBusinessKeyWithProcessInstanceBusinessKeyIncludeChildExecutions4() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().processInstanceBusinessKey(null, true));
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionKeys(Set)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys_given42_whenHashSetAdd42() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("42");
    processDefinitionKeys.add("foo");

    // Act
    ExecutionQuery actualProcessDefinitionKeysResult =
        executionQueryImpl.processDefinitionKeys(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, executionQueryImpl.getProcessDefinitionKeys());
    assertSame(executionQueryImpl, actualProcessDefinitionKeysResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionKeys(Set)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link HashSet#HashSet()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys_givenFoo_whenHashSetAddFoo() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("foo");

    // Act
    ExecutionQuery actualProcessDefinitionKeysResult =
        executionQueryImpl.processDefinitionKeys(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, executionQueryImpl.getProcessDefinitionKeys());
    assertSame(executionQueryImpl, actualProcessDefinitionKeysResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionKeys(Set)}.
   *
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys_whenHashSet() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    HashSet<String> processDefinitionKeys = new HashSet<>();

    // Act
    ExecutionQuery actualProcessDefinitionKeysResult =
        executionQueryImpl.processDefinitionKeys(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, executionQueryImpl.getProcessDefinitionKeys());
    assertSame(executionQueryImpl, actualProcessDefinitionKeysResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processDefinitionKeys(Set)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().processDefinitionKeys(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#executionId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()} ExecutionId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#executionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.executionId(String)"})
  public void testExecutionId_when42_thenExecutionQueryImplExecutionIdIs42() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQueryImpl actualExecutionIdResult = executionQueryImpl.executionId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getExecutionId());
    assertSame(executionQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#executionId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#executionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.executionId(String)"})
  public void testExecutionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new ExecutionQueryImpl().executionId(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#activityId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()} ActivityId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#activityId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.activityId(String)"})
  public void testActivityId_when42_thenExecutionQueryImplActivityIdIs42() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQueryImpl actualActivityIdResult = executionQueryImpl.activityId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getActivityId());
    assertEquals("42", actualActivityIdResult.getActivityId());
    assertTrue(executionQueryImpl.isActive());
    assertTrue(actualActivityIdResult.isActive());
    Object actualParameter = actualActivityIdResult.getParameter();
    assertSame(actualActivityIdResult, actualParameter);
  }

  /**
   * Test {@link ExecutionQueryImpl#activityId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()} ActivityId is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#activityId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.activityId(String)"})
  public void testActivityId_whenNull_thenExecutionQueryImplActivityIdIsNull() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQueryImpl actualActivityIdResult = executionQueryImpl.activityId(null);

    // Assert
    assertNull(executionQueryImpl.getActivityId());
    assertNull(actualActivityIdResult.getActivityId());
    assertFalse(executionQueryImpl.isActive());
    assertFalse(actualActivityIdResult.isActive());
    Object actualParameter = actualActivityIdResult.getParameter();
    assertSame(actualActivityIdResult, actualParameter);
  }

  /**
   * Test {@link ExecutionQueryImpl#parentId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()} ParentId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#parentId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.parentId(String)"})
  public void testParentId_when42_thenExecutionQueryImplParentIdIs42() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQueryImpl actualParentIdResult = executionQueryImpl.parentId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getParentId());
    assertSame(executionQueryImpl, actualParentIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#parentId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#parentId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.parentId(String)"})
  public void testParentId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new ExecutionQueryImpl().parentId(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#executionTenantId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()} TenantId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#executionTenantId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.executionTenantId(String)"})
  public void testExecutionTenantId_when42_thenExecutionQueryImplTenantIdIs42() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQueryImpl actualExecutionTenantIdResult = executionQueryImpl.executionTenantId("42");

    // Assert
    assertEquals("42", executionQueryImpl.getTenantId());
    assertSame(executionQueryImpl, actualExecutionTenantIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#executionTenantId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#executionTenantId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.executionTenantId(String)"})
  public void testExecutionTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().executionTenantId(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#executionTenantIdLike(String)}.
   *
   * <ul>
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()} TenantIdLike is {@code Tenant Id
   *       Like}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#executionTenantIdLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.executionTenantIdLike(String)"})
  public void testExecutionTenantIdLike_thenExecutionQueryImplTenantIdLikeIsTenantIdLike() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQueryImpl actualExecutionTenantIdLikeResult =
        executionQueryImpl.executionTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", executionQueryImpl.getTenantIdLike());
    assertSame(executionQueryImpl, actualExecutionTenantIdLikeResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#executionTenantIdLike(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#executionTenantIdLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.executionTenantIdLike(String)"})
  public void testExecutionTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().executionTenantIdLike(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#executionWithoutTenantId()}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#executionWithoutTenantId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQueryImpl ExecutionQueryImpl.executionWithoutTenantId()"})
  public void testExecutionWithoutTenantId() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQueryImpl actualExecutionWithoutTenantIdResult =
        executionQueryImpl.executionWithoutTenantId();

    // Assert
    assertTrue(executionQueryImpl.isWithoutTenantId());
    assertSame(executionQueryImpl, actualExecutionWithoutTenantIdResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#signalEventSubscription(String)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionQueryImpl#ExecutionQueryImpl()}.
   *   <li>Then return {@link ExecutionQueryImpl#ExecutionQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#signalEventSubscription(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.signalEventSubscription(String)"})
  public void testSignalEventSubscription_givenExecutionQueryImpl_thenReturnExecutionQueryImpl() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualSignalEventSubscriptionResult =
        executionQueryImpl.signalEventSubscription("Signal Name");

    // Assert
    assertSame(executionQueryImpl, actualSignalEventSubscriptionResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#signalEventSubscription(String)}.
   *
   * <ul>
   *   <li>Then return {@link ExecutionQueryImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#signalEventSubscription(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.signalEventSubscription(String)"})
  public void testSignalEventSubscription_thenReturnExecutionQueryImpl() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    executionQueryImpl.eventSubscription("signal", "signal");

    // Act
    ExecutionQuery actualSignalEventSubscriptionResult =
        executionQueryImpl.signalEventSubscription("Signal Name");

    // Assert
    assertTrue(actualSignalEventSubscriptionResult instanceof ExecutionQueryImpl);
    List<EventSubscriptionQueryValue> eventSubscriptions =
        ((ExecutionQueryImpl) actualSignalEventSubscriptionResult).getEventSubscriptions();
    assertEquals(2, eventSubscriptions.size());
    EventSubscriptionQueryValue getResult = eventSubscriptions.get(1);
    assertEquals("Signal Name", getResult.getEventName());
    assertEquals("signal", eventSubscriptions.get(0).getEventName());
    assertEquals("signal", getResult.getEventType());
  }

  /**
   * Test {@link ExecutionQueryImpl#signalEventSubscription(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#signalEventSubscription(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.signalEventSubscription(String)"})
  public void testSignalEventSubscription_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().signalEventSubscription(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#signalEventSubscriptionName(String)}.
   *
   * <ul>
   *   <li>Then return {@link ExecutionQueryImpl#ExecutionQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#signalEventSubscriptionName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.signalEventSubscriptionName(String)"})
  public void testSignalEventSubscriptionName_thenReturnExecutionQueryImpl() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualSignalEventSubscriptionNameResult =
        executionQueryImpl.signalEventSubscriptionName("Signal Name");

    // Assert
    assertSame(executionQueryImpl, actualSignalEventSubscriptionNameResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#signalEventSubscriptionName(String)}.
   *
   * <ul>
   *   <li>Then return {@link ExecutionQueryImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#signalEventSubscriptionName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.signalEventSubscriptionName(String)"})
  public void testSignalEventSubscriptionName_thenReturnExecutionQueryImpl2() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    executionQueryImpl.eventSubscription("signal", "signal");

    // Act
    ExecutionQuery actualSignalEventSubscriptionNameResult =
        executionQueryImpl.signalEventSubscriptionName("Signal Name");

    // Assert
    assertTrue(actualSignalEventSubscriptionNameResult instanceof ExecutionQueryImpl);
    List<EventSubscriptionQueryValue> eventSubscriptions =
        ((ExecutionQueryImpl) actualSignalEventSubscriptionNameResult).getEventSubscriptions();
    assertEquals(2, eventSubscriptions.size());
    EventSubscriptionQueryValue getResult = eventSubscriptions.get(1);
    assertEquals("Signal Name", getResult.getEventName());
    assertEquals("signal", eventSubscriptions.get(0).getEventName());
    assertEquals("signal", getResult.getEventType());
  }

  /**
   * Test {@link ExecutionQueryImpl#signalEventSubscriptionName(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#signalEventSubscriptionName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.signalEventSubscriptionName(String)"})
  public void testSignalEventSubscriptionName_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().signalEventSubscriptionName(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#messageEventSubscriptionName(String)}.
   *
   * <ul>
   *   <li>Then return {@link ExecutionQueryImpl#ExecutionQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#messageEventSubscriptionName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.messageEventSubscriptionName(String)"})
  public void testMessageEventSubscriptionName_thenReturnExecutionQueryImpl() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualMessageEventSubscriptionNameResult =
        executionQueryImpl.messageEventSubscriptionName("Message Name");

    // Assert
    assertSame(executionQueryImpl, actualMessageEventSubscriptionNameResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#messageEventSubscriptionName(String)}.
   *
   * <ul>
   *   <li>Then return {@link ExecutionQueryImpl}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#messageEventSubscriptionName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.messageEventSubscriptionName(String)"})
  public void testMessageEventSubscriptionName_thenReturnExecutionQueryImpl2() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    executionQueryImpl.eventSubscription("message", "message");

    // Act
    ExecutionQuery actualMessageEventSubscriptionNameResult =
        executionQueryImpl.messageEventSubscriptionName("Message Name");

    // Assert
    assertTrue(actualMessageEventSubscriptionNameResult instanceof ExecutionQueryImpl);
    List<EventSubscriptionQueryValue> eventSubscriptions =
        ((ExecutionQueryImpl) actualMessageEventSubscriptionNameResult).getEventSubscriptions();
    assertEquals(2, eventSubscriptions.size());
    EventSubscriptionQueryValue getResult = eventSubscriptions.get(1);
    assertEquals("Message Name", getResult.getEventName());
    assertEquals("message", eventSubscriptions.get(0).getEventName());
    assertEquals("message", getResult.getEventType());
  }

  /**
   * Test {@link ExecutionQueryImpl#messageEventSubscriptionName(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#messageEventSubscriptionName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.messageEventSubscriptionName(String)"})
  public void testMessageEventSubscriptionName_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().messageEventSubscriptionName(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#eventSubscription(String, String)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionQueryImpl#ExecutionQueryImpl()} EventSubscriptions is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.eventSubscription(String, String)"})
  public void testEventSubscription_givenExecutionQueryImplEventSubscriptionsIsArrayList() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    executionQueryImpl.setEventSubscriptions(new ArrayList<>());

    // Act
    ExecutionQuery actualEventSubscriptionResult =
        executionQueryImpl.eventSubscription("Event Type", "Event Name");

    // Assert
    assertSame(executionQueryImpl, actualEventSubscriptionResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#eventSubscription(String, String)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionQueryImpl#ExecutionQueryImpl()}.
   *   <li>Then return {@link ExecutionQueryImpl#ExecutionQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.eventSubscription(String, String)"})
  public void testEventSubscription_givenExecutionQueryImpl_thenReturnExecutionQueryImpl() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualEventSubscriptionResult =
        executionQueryImpl.eventSubscription("Event Type", "Event Name");

    // Assert
    assertSame(executionQueryImpl, actualEventSubscriptionResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#eventSubscription(String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.eventSubscription(String, String)"})
  public void testEventSubscription_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().eventSubscription(null, "Event Name"));
  }

  /**
   * Test {@link ExecutionQueryImpl#eventSubscription(String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#eventSubscription(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.eventSubscription(String, String)"})
  public void testEventSubscription_whenNull_thenThrowActivitiIllegalArgumentException2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ExecutionQueryImpl().eventSubscription("Event Type", null));
  }

  /**
   * Test {@link ExecutionQueryImpl#processVariableValueEquals(String, Object)} with {@code
   * variableName}, {@code variableValue}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionQuery ExecutionQueryImpl.processVariableValueEquals(String, Object)"
  })
  public void testProcessVariableValueEqualsWithVariableNameVariableValue_whenNull() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessVariableValueEqualsResult =
        executionQueryImpl.processVariableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processVariableValueEquals(String, Object)} with {@code
   * variableName}, {@code variableValue}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionQuery ExecutionQueryImpl.processVariableValueEquals(String, Object)"
  })
  public void testProcessVariableValueEqualsWithVariableNameVariableValue_whenNull2() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessVariableValueEqualsResult =
        executionQueryImpl.processVariableValueEquals("Variable Name", null);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processVariableValueEquals(Object)} with {@code variableValue}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processVariableValueEquals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processVariableValueEquals(Object)"})
  public void testProcessVariableValueEqualsWithVariableValue() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessVariableValueEqualsResult =
        executionQueryImpl.processVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processVariableValueNotEquals(String, Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionQuery ExecutionQueryImpl.processVariableValueNotEquals(String, Object)"
  })
  public void testProcessVariableValueNotEquals_whenNull() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessVariableValueNotEqualsResult =
        executionQueryImpl.processVariableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processVariableValueNotEquals(String, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionQuery ExecutionQueryImpl.processVariableValueNotEquals(String, Object)"
  })
  public void testProcessVariableValueNotEquals_whenNull2() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessVariableValueNotEqualsResult =
        executionQueryImpl.processVariableValueNotEquals("Variable Name", null);

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processVariableValueEqualsIgnoreCase(String, String)}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processVariableValueEqualsIgnoreCase(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionQuery ExecutionQueryImpl.processVariableValueEqualsIgnoreCase(String, String)"
  })
  public void testProcessVariableValueEqualsIgnoreCase() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessVariableValueEqualsIgnoreCaseResult =
        executionQueryImpl.processVariableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processVariableValueNotEqualsIgnoreCase(String, String)}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processVariableValueNotEqualsIgnoreCase(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionQuery ExecutionQueryImpl.processVariableValueNotEqualsIgnoreCase(String, String)"
  })
  public void testProcessVariableValueNotEqualsIgnoreCase() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessVariableValueNotEqualsIgnoreCaseResult =
        executionQueryImpl.processVariableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processVariableValueLike(String, String)}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processVariableValueLike(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.processVariableValueLike(String, String)"})
  public void testProcessVariableValueLike() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessVariableValueLikeResult =
        executionQueryImpl.processVariableValueLike("Name", "42");

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueLikeResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#processVariableValueLikeIgnoreCase(String, String)}.
   *
   * <p>Method under test: {@link ExecutionQueryImpl#processVariableValueLikeIgnoreCase(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionQuery ExecutionQueryImpl.processVariableValueLikeIgnoreCase(String, String)"
  })
  public void testProcessVariableValueLikeIgnoreCase() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualProcessVariableValueLikeIgnoreCaseResult =
        executionQueryImpl.processVariableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualProcessVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#startedBefore(Date)}.
   *
   * <ul>
   *   <li>Then return {@link ExecutionQueryImpl#ExecutionQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#startedBefore(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.startedBefore(Date)"})
  public void testStartedBefore_thenReturnExecutionQueryImpl() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    Date beforeTime =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    ExecutionQuery actualStartedBeforeResult = executionQueryImpl.startedBefore(beforeTime);

    // Assert
    assertSame(executionQueryImpl, actualStartedBeforeResult);
    assertSame(beforeTime, executionQueryImpl.getStartedBefore());
  }

  /**
   * Test {@link ExecutionQueryImpl#startedBefore(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#startedBefore(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.startedBefore(Date)"})
  public void testStartedBefore_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new ExecutionQueryImpl().startedBefore(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#startedAfter(Date)}.
   *
   * <ul>
   *   <li>Then return {@link ExecutionQueryImpl#ExecutionQueryImpl()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#startedAfter(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.startedAfter(Date)"})
  public void testStartedAfter_thenReturnExecutionQueryImpl() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    Date afterTime =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    ExecutionQuery actualStartedAfterResult = executionQueryImpl.startedAfter(afterTime);

    // Assert
    assertSame(executionQueryImpl, actualStartedAfterResult);
    assertSame(afterTime, executionQueryImpl.getStartedAfter());
  }

  /**
   * Test {@link ExecutionQueryImpl#startedAfter(Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#startedAfter(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.startedAfter(Date)"})
  public void testStartedAfter_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new ExecutionQueryImpl().startedAfter(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#startedBy(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()} StartedBy is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#startedBy(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.startedBy(String)"})
  public void testStartedBy_when42_thenExecutionQueryImplStartedByIs42() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualStartedByResult = executionQueryImpl.startedBy("42");

    // Assert
    assertEquals("42", executionQueryImpl.getStartedBy());
    assertSame(executionQueryImpl, actualStartedByResult);
  }

  /**
   * Test {@link ExecutionQueryImpl#startedBy(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#startedBy(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionQuery ExecutionQueryImpl.startedBy(String)"})
  public void testStartedBy_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> new ExecutionQueryImpl().startedBy(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#executeCount(CommandContext)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#executeCount(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long ExecutionQueryImpl.executeCount(CommandContext)"})
  public void testExecuteCount_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    executionQueryImpl.orderBy(mock(QueryProperty.class));
    executionQueryImpl.addVariable("description", JSONObject.NULL, QueryOperator.NOT_EQUALS, false);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> executionQueryImpl.executeCount(null));
  }

  /**
   * Test {@link ExecutionQueryImpl#executeList(CommandContext, Page)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionQueryImpl#executeList(CommandContext, Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List ExecutionQueryImpl.executeList(CommandContext, Page)"})
  public void testExecuteList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    executionQueryImpl.orderBy(mock(QueryProperty.class));
    executionQueryImpl.addVariable("description", JSONObject.NULL, QueryOperator.NOT_EQUALS, false);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.executeList(null, new Page(1, 3)));
  }
}
