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
import java.util.HashSet;
import java.util.Set;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.query.QueryProperty;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class HistoricVariableInstanceQueryImplDiffblueTest {
  /**
   * Test {@link HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricVariableInstanceQueryImpl.<init>()"})
  public void testNewHistoricVariableInstanceQueryImpl() {
    // Arrange and Act
    HistoricVariableInstanceQueryImpl actualHistoricVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualHistoricVariableInstanceQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualHistoricVariableInstanceQueryImpl.getOrderByColumns());
    assertNull(actualHistoricVariableInstanceQueryImpl.getDatabaseType());
    assertNull(actualHistoricVariableInstanceQueryImpl.getActivityInstanceId());
    assertNull(actualHistoricVariableInstanceQueryImpl.getProcessInstanceId());
    assertNull(actualHistoricVariableInstanceQueryImpl.getTaskId());
    assertNull(actualHistoricVariableInstanceQueryImpl.getVariableName());
    assertNull(actualHistoricVariableInstanceQueryImpl.getVariableNameLike());
    assertNull(actualHistoricVariableInstanceQueryImpl.orderBy);
    assertNull(actualHistoricVariableInstanceQueryImpl.executionId);
    assertNull(actualHistoricVariableInstanceQueryImpl.id);
    assertNull(actualHistoricVariableInstanceQueryImpl.executionIds);
    assertNull(actualHistoricVariableInstanceQueryImpl.taskIds);
    assertNull(actualHistoricVariableInstanceQueryImpl.nullHandlingOnOrder);
    assertNull(actualHistoricVariableInstanceQueryImpl.resultType);
    assertNull(actualHistoricVariableInstanceQueryImpl.getQueryVariableValue());
    assertNull(actualHistoricVariableInstanceQueryImpl.commandContext);
    assertNull(actualHistoricVariableInstanceQueryImpl.commandExecutor);
    assertNull(actualHistoricVariableInstanceQueryImpl.orderProperty);
    assertEquals(0, actualHistoricVariableInstanceQueryImpl.getFirstResult());
    assertEquals(1, actualHistoricVariableInstanceQueryImpl.getFirstRow());
    assertFalse(actualHistoricVariableInstanceQueryImpl.getExcludeTaskRelated());
    assertFalse(actualHistoricVariableInstanceQueryImpl.excludeVariableInitialization);
    assertEquals(Integer.MAX_VALUE, actualHistoricVariableInstanceQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualHistoricVariableInstanceQueryImpl.getMaxResults());
    Object actualParameter = actualHistoricVariableInstanceQueryImpl.getParameter();
    assertSame(actualHistoricVariableInstanceQueryImpl, actualParameter);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#processInstanceId(String)}.
   *
   * <ul>
   *   <li>Then {@link HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()}
   *       ProcessInstanceId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl HistoricVariableInstanceQueryImpl.processInstanceId(String)"
  })
  public void testProcessInstanceId_thenHistoricVariableInstanceQueryImplProcessInstanceIdIs42() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Act
    HistoricVariableInstanceQueryImpl actualProcessInstanceIdResult =
        historicVariableInstanceQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", historicVariableInstanceQueryImpl.getProcessInstanceId());
    assertSame(historicVariableInstanceQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#processInstanceId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl HistoricVariableInstanceQueryImpl.processInstanceId(String)"
  })
  public void testProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().processInstanceId(null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#executionId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()} {@link
   *       HistoricVariableInstanceQueryImpl#executionId} is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#executionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl HistoricVariableInstanceQueryImpl.executionId(String)"
  })
  public void testExecutionId_when42_thenHistoricVariableInstanceQueryImplExecutionIdIs42() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Act
    HistoricVariableInstanceQueryImpl actualExecutionIdResult =
        historicVariableInstanceQueryImpl.executionId("42");

    // Assert
    assertEquals("42", historicVariableInstanceQueryImpl.executionId);
    assertSame(historicVariableInstanceQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#executionId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#executionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl HistoricVariableInstanceQueryImpl.executionId(String)"
  })
  public void testExecutionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().executionId(null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}.
   *
   * <ul>
   *   <li>Then {@link HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()} {@link
   *       HistoricVariableInstanceQueryImpl#executionIds} size is one.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl HistoricVariableInstanceQueryImpl.executionIds(Set)"
  })
  public void testExecutionIds_thenHistoricVariableInstanceQueryImplExecutionIdsSizeIsOne() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    HashSet<String> executionIds = new HashSet<>();
    executionIds.add("Set of executionIds is empty");

    // Act
    HistoricVariableInstanceQueryImpl actualExecutionIdsResult =
        historicVariableInstanceQueryImpl.executionIds(executionIds);

    // Assert
    assertEquals(1, historicVariableInstanceQueryImpl.executionIds.size());
    assertSame(historicVariableInstanceQueryImpl, actualExecutionIdsResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}.
   *
   * <ul>
   *   <li>Then {@link HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()} {@link
   *       HistoricVariableInstanceQueryImpl#executionIds} size is two.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl HistoricVariableInstanceQueryImpl.executionIds(Set)"
  })
  public void testExecutionIds_thenHistoricVariableInstanceQueryImplExecutionIdsSizeIsTwo() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    HashSet<String> executionIds = new HashSet<>();
    executionIds.add("42");
    executionIds.add("Set of executionIds is empty");

    // Act
    HistoricVariableInstanceQueryImpl actualExecutionIdsResult =
        historicVariableInstanceQueryImpl.executionIds(executionIds);

    // Assert
    Set<String> stringSet = historicVariableInstanceQueryImpl.executionIds;
    assertEquals(2, stringSet.size());
    Set<String> stringSet2 = actualExecutionIdsResult.executionIds;
    assertEquals(2, stringSet2.size());
    assertTrue(stringSet.contains("42"));
    assertTrue(stringSet2.contains("42"));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}.
   *
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl HistoricVariableInstanceQueryImpl.executionIds(Set)"
  })
  public void testExecutionIds_whenHashSet_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.executionIds(new HashSet<>()));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl HistoricVariableInstanceQueryImpl.executionIds(Set)"
  })
  public void testExecutionIds_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().executionIds(null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#taskId(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then {@link HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()} TaskId
   *       is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#taskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.taskId(String)"
  })
  public void testTaskId_when42_thenHistoricVariableInstanceQueryImplTaskIdIs42() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Act
    HistoricVariableInstanceQuery actualTaskIdResult =
        historicVariableInstanceQueryImpl.taskId("42");

    // Assert
    assertEquals("42", historicVariableInstanceQueryImpl.getTaskId());
    assertSame(historicVariableInstanceQueryImpl, actualTaskIdResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#taskId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#taskId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.taskId(String)"
  })
  public void testTaskId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().taskId(null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#taskIds(Set)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then {@link HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()} {@link
   *       HistoricVariableInstanceQueryImpl#taskIds} size is two.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#taskIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl HistoricVariableInstanceQueryImpl.taskIds(Set)"
  })
  public void testTaskIds_given42_thenHistoricVariableInstanceQueryImplTaskIdsSizeIsTwo() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    HashSet<String> taskIds = new HashSet<>();
    taskIds.add("42");
    taskIds.add("Set of taskIds is empty");

    // Act
    HistoricVariableInstanceQueryImpl actualTaskIdsResult =
        historicVariableInstanceQueryImpl.taskIds(taskIds);

    // Assert
    Set<String> stringSet = historicVariableInstanceQueryImpl.taskIds;
    assertEquals(2, stringSet.size());
    Set<String> stringSet2 = actualTaskIdsResult.taskIds;
    assertEquals(2, stringSet2.size());
    assertTrue(stringSet.contains("42"));
    assertTrue(stringSet2.contains("42"));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#taskIds(Set)}.
   *
   * <ul>
   *   <li>Then {@link HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()} {@link
   *       HistoricVariableInstanceQueryImpl#taskIds} size is one.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#taskIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl HistoricVariableInstanceQueryImpl.taskIds(Set)"
  })
  public void testTaskIds_thenHistoricVariableInstanceQueryImplTaskIdsSizeIsOne() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    HashSet<String> taskIds = new HashSet<>();
    taskIds.add("Set of taskIds is empty");

    // Act
    HistoricVariableInstanceQueryImpl actualTaskIdsResult =
        historicVariableInstanceQueryImpl.taskIds(taskIds);

    // Assert
    assertEquals(1, historicVariableInstanceQueryImpl.taskIds.size());
    assertSame(historicVariableInstanceQueryImpl, actualTaskIdsResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#taskIds(Set)}.
   *
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#taskIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl HistoricVariableInstanceQueryImpl.taskIds(Set)"
  })
  public void testTaskIds_whenHashSet_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.taskIds(new HashSet<>()));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#taskIds(Set)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#taskIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQueryImpl HistoricVariableInstanceQueryImpl.taskIds(Set)"
  })
  public void testTaskIds_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().taskIds(null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#excludeTaskVariables()}.
   *
   * <ul>
   *   <li>Then {@link HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()}
   *       ExcludeTaskRelated.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#excludeTaskVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.excludeTaskVariables()"
  })
  public void testExcludeTaskVariables_thenHistoricVariableInstanceQueryImplExcludeTaskRelated() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Act
    HistoricVariableInstanceQuery actualExcludeTaskVariablesResult =
        historicVariableInstanceQueryImpl.excludeTaskVariables();

    // Assert
    assertTrue(historicVariableInstanceQueryImpl.getExcludeTaskRelated());
    assertSame(historicVariableInstanceQueryImpl, actualExcludeTaskVariablesResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#excludeTaskVariables()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#excludeTaskVariables()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.excludeTaskVariables()"
  })
  public void testExcludeTaskVariables_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();
    historicVariableInstanceQueryImpl.taskId("42");

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.excludeTaskVariables());
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableName(String)}.
   *
   * <ul>
   *   <li>Then {@link HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()}
   *       VariableName is {@code Variable Name}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableName(String)"
  })
  public void testVariableName_thenHistoricVariableInstanceQueryImplVariableNameIsVariableName() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Act
    HistoricVariableInstanceQuery actualVariableNameResult =
        historicVariableInstanceQueryImpl.variableName("Variable Name");

    // Assert
    assertEquals("Variable Name", historicVariableInstanceQueryImpl.getVariableName());
    assertSame(historicVariableInstanceQueryImpl, actualVariableNameResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableName(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableName(String)"
  })
  public void testVariableName_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().variableName(null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String, Object)}.
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableValueEquals(String, Object)"
  })
  public void testVariableValueEquals() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Act
    HistoricVariableInstanceQuery actualVariableValueEqualsResult =
        historicVariableInstanceQueryImpl.variableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertEquals("Variable Name", historicVariableInstanceQueryImpl.getVariableName());
    assertSame(historicVariableInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String, Object)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableValueEquals(String, Object)"
  })
  public void testVariableValueEquals_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().variableValueEquals("Variable Name", null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableValueEquals(String, Object)"
  })
  public void testVariableValueEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().variableValueEquals(null, null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String, Object)}.
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableValueNotEquals(String, Object)"
  })
  public void testVariableValueNotEquals() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Act
    HistoricVariableInstanceQuery actualVariableValueNotEqualsResult =
        historicVariableInstanceQueryImpl.variableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertEquals("Variable Name", historicVariableInstanceQueryImpl.getVariableName());
    assertSame(historicVariableInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String, Object)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableValueNotEquals(String, Object)"
  })
  public void testVariableValueNotEquals_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            new HistoricVariableInstanceQueryImpl().variableValueNotEquals("Variable Name", null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableValueNotEquals(String, Object)"
  })
  public void testVariableValueNotEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().variableValueNotEquals(null, null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableValueLike(String, String)}.
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableValueLike(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableValueLike(String, String)"
  })
  public void testVariableValueLike() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Act
    HistoricVariableInstanceQuery actualVariableValueLikeResult =
        historicVariableInstanceQueryImpl.variableValueLike("Variable Name", "42");

    // Assert
    assertEquals("Variable Name", historicVariableInstanceQueryImpl.getVariableName());
    assertSame(historicVariableInstanceQueryImpl, actualVariableValueLikeResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableValueLike(String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableValueLike(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableValueLike(String, String)"
  })
  public void testVariableValueLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().variableValueLike(null, null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableValueLike(String, String)}.
   *
   * <ul>
   *   <li>When {@code Variable Name}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableValueLike(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableValueLike(String, String)"
  })
  public void testVariableValueLike_whenVariableName_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().variableValueLike("Variable Name", null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}.
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableValueLikeIgnoreCase(String, String)"
  })
  public void testVariableValueLikeIgnoreCase() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Act
    HistoricVariableInstanceQuery actualVariableValueLikeIgnoreCaseResult =
        historicVariableInstanceQueryImpl.variableValueLikeIgnoreCase("Variable Name", "42");

    // Assert
    assertEquals("Variable Name", historicVariableInstanceQueryImpl.getVariableName());
    assertSame(historicVariableInstanceQueryImpl, actualVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableValueLikeIgnoreCase(String, String)"
  })
  public void testVariableValueLikeIgnoreCase_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().variableValueLikeIgnoreCase(null, null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricVariableInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableValueLikeIgnoreCase(String, String)"
  })
  public void testVariableValueLikeIgnoreCase_thenThrowActivitiIllegalArgumentException2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            new HistoricVariableInstanceQueryImpl()
                .variableValueLikeIgnoreCase("Variable Name", null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableNameLike(String)}.
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableNameLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableNameLike(String)"
  })
  public void testVariableNameLike() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();

    // Act
    HistoricVariableInstanceQuery actualVariableNameLikeResult =
        historicVariableInstanceQueryImpl.variableNameLike("Variable Name Like");

    // Assert
    assertEquals("Variable Name Like", historicVariableInstanceQueryImpl.getVariableNameLike());
    assertSame(historicVariableInstanceQueryImpl, actualVariableNameLikeResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableNameLike(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#variableNameLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceQuery HistoricVariableInstanceQueryImpl.variableNameLike(String)"
  })
  public void testVariableNameLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricVariableInstanceQueryImpl().variableNameLike(null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#executeCount(CommandContext)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#executeCount(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long HistoricVariableInstanceQueryImpl.executeCount(CommandContext)"})
  public void testExecuteCount_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();
    historicVariableInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.executeCount(null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#executeList(CommandContext, Page)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricVariableInstanceQueryImpl#executeList(CommandContext,
   * Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.List HistoricVariableInstanceQueryImpl.executeList(CommandContext, Page)"
  })
  public void testExecuteList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl =
        new HistoricVariableInstanceQueryImpl();
    historicVariableInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.executeList(null, new Page(1, 3)));
  }
}
