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
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.AbstractQuery.NullHandlingOnOrder;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.query.QueryProperty;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class HistoricProcessInstanceQueryImplDiffblueTest {
  /**
   * Test {@link HistoricProcessInstanceQueryImpl#HistoricProcessInstanceQueryImpl()}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#HistoricProcessInstanceQueryImpl()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricProcessInstanceQueryImpl.<init>()"})
  public void testNewHistoricProcessInstanceQueryImpl() {
    // Arrange and Act
    HistoricProcessInstanceQueryImpl actualHistoricProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

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
    Object actualParameter = actualHistoricProcessInstanceQueryImpl.getParameter();
    assertSame(actualHistoricProcessInstanceQueryImpl, actualParameter);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceId(String)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQueryImpl HistoricProcessInstanceQueryImpl.processInstanceId(String)"
  })
  public void testProcessInstanceId() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQueryImpl actualProcessInstanceIdResult =
        historicProcessInstanceQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", historicProcessInstanceQueryImpl.getProcessInstanceId());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceIds(Set)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processInstanceIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceIds(Set)"
  })
  public void testProcessInstanceIds() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    HashSet<String> processInstanceIds = new HashSet<>();
    processInstanceIds.add("Set of process instance ids is empty");

    // Act
    HistoricProcessInstanceQuery actualProcessInstanceIdsResult =
        historicProcessInstanceQueryImpl.processInstanceIds(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, historicProcessInstanceQueryImpl.getProcessInstanceIds());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceIdsResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceIds(Set)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processInstanceIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceIds(Set)"
  })
  public void testProcessInstanceIds_given42_whenHashSetAdd42() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    HashSet<String> processInstanceIds = new HashSet<>();
    processInstanceIds.add("42");
    processInstanceIds.add("Set of process instance ids is empty");

    // Act
    HistoricProcessInstanceQuery actualProcessInstanceIdsResult =
        historicProcessInstanceQueryImpl.processInstanceIds(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, historicProcessInstanceQueryImpl.getProcessInstanceIds());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceIdsResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceIds(Set)}.
   *
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processInstanceIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceIds(Set)"
  })
  public void testProcessInstanceIds_whenHashSet_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> historicProcessInstanceQueryImpl.processInstanceIds(new HashSet<>()));
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceIds(Set)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processInstanceIds(Set)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceIds(Set)"
  })
  public void testProcessInstanceIds_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricProcessInstanceQueryImpl().processInstanceIds(null));
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processDefinitionId(String)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processDefinitionId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQueryImpl HistoricProcessInstanceQueryImpl.processDefinitionId(String)"
  })
  public void testProcessDefinitionId() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQueryImpl actualProcessDefinitionIdResult =
        historicProcessInstanceQueryImpl.processDefinitionId("42");

    // Assert
    assertEquals("42", historicProcessInstanceQueryImpl.getProcessDefinitionId());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processDefinitionKey(String)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processDefinitionKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processDefinitionKey(String)"
  })
  public void testProcessDefinitionKey() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionKeyResult =
        historicProcessInstanceQueryImpl.processDefinitionKey("Process Definition Key");

    // Assert
    assertEquals(
        "Process Definition Key", historicProcessInstanceQueryImpl.getProcessDefinitionKey());
    assertEquals(
        "Process Definition Key:%:%",
        historicProcessInstanceQueryImpl.getProcessDefinitionIdLike());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyIn(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processDefinitionKeyIn(List)"
  })
  public void testProcessDefinitionKeyIn_given42_whenArrayListAdd42() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    ArrayList<String> processDefinitionKeys = new ArrayList<>();
    processDefinitionKeys.add("42");
    processDefinitionKeys.add("foo");

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionKeyInResult =
        historicProcessInstanceQueryImpl.processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicProcessInstanceQueryImpl.getProcessDefinitionKeyIn());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyIn(List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processDefinitionKeyIn(List)"
  })
  public void testProcessDefinitionKeyIn_givenFoo_whenArrayListAddFoo() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    ArrayList<String> processDefinitionKeys = new ArrayList<>();
    processDefinitionKeys.add("foo");

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionKeyInResult =
        historicProcessInstanceQueryImpl.processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicProcessInstanceQueryImpl.getProcessDefinitionKeyIn());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyIn(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processDefinitionKeyIn(List)"
  })
  public void testProcessDefinitionKeyIn_whenArrayList() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();
    ArrayList<String> processDefinitionKeys = new ArrayList<>();

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionKeyInResult =
        historicProcessInstanceQueryImpl.processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicProcessInstanceQueryImpl.getProcessDefinitionKeyIn());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processDefinitionCategory(String)}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#processDefinitionCategory(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processDefinitionCategory(String)"
  })
  public void testProcessDefinitionCategory() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionCategoryResult =
        historicProcessInstanceQueryImpl.processDefinitionCategory("Process Definition Category");

    // Assert
    assertEquals(
        "Process Definition Category",
        historicProcessInstanceQueryImpl.getProcessDefinitionCategory());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionCategoryResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processDefinitionName(String)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processDefinitionName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processDefinitionName(String)"
  })
  public void testProcessDefinitionName() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionNameResult =
        historicProcessInstanceQueryImpl.processDefinitionName("Process Definition Name");

    // Assert
    assertEquals(
        "Process Definition Name", historicProcessInstanceQueryImpl.getProcessDefinitionName());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processDefinitionVersion(Integer)}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processDefinitionVersion(Integer)"
  })
  public void testProcessDefinitionVersion() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionVersionResult =
        historicProcessInstanceQueryImpl.processDefinitionVersion(1);

    // Assert
    assertEquals(1, historicProcessInstanceQueryImpl.getProcessDefinitionVersion().intValue());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionVersionResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceBusinessKey(String)}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceBusinessKey(String)"
  })
  public void testProcessInstanceBusinessKey() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessInstanceBusinessKeyResult =
        historicProcessInstanceQueryImpl.processInstanceBusinessKey("Business Key");

    // Assert
    assertEquals("Business Key", historicProcessInstanceQueryImpl.getBusinessKey());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#deploymentId(String)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#deploymentId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.deploymentId(String)"
  })
  public void testDeploymentId() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualDeploymentIdResult =
        historicProcessInstanceQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", historicProcessInstanceQueryImpl.getDeploymentId());
    assertSame(historicProcessInstanceQueryImpl, actualDeploymentIdResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#deploymentIdIn(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.deploymentIdIn(List)"
  })
  public void testDeploymentIdIn_given42_whenArrayListAdd42() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("42");
    deploymentIds.add("foo");

    // Act
    HistoricProcessInstanceQuery actualDeploymentIdInResult =
        historicProcessInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, historicProcessInstanceQueryImpl.getDeploymentIds());
    assertSame(historicProcessInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#deploymentIdIn(List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.deploymentIdIn(List)"
  })
  public void testDeploymentIdIn_givenFoo_whenArrayListAddFoo() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("foo");

    // Act
    HistoricProcessInstanceQuery actualDeploymentIdInResult =
        historicProcessInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, historicProcessInstanceQueryImpl.getDeploymentIds());
    assertSame(historicProcessInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#deploymentIdIn(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.deploymentIdIn(List)"
  })
  public void testDeploymentIdIn_whenArrayList() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();
    ArrayList<String> deploymentIds = new ArrayList<>();

    // Act
    HistoricProcessInstanceQuery actualDeploymentIdInResult =
        historicProcessInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, historicProcessInstanceQueryImpl.getDeploymentIds());
    assertSame(historicProcessInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#finished()}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#finished()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.finished()"})
  public void testFinished() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualFinishedResult = historicProcessInstanceQueryImpl.finished();

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isFinished());
    assertSame(historicProcessInstanceQueryImpl, actualFinishedResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#unfinished()}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#unfinished()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.unfinished()"})
  public void testUnfinished() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualUnfinishedResult =
        historicProcessInstanceQueryImpl.unfinished();

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isOpen());
    assertTrue(historicProcessInstanceQueryImpl.isUnfinished());
    assertSame(historicProcessInstanceQueryImpl, actualUnfinishedResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#deleted()}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#deleted()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.deleted()"})
  public void testDeleted() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualDeletedResult = historicProcessInstanceQueryImpl.deleted();

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isDeleted());
    assertSame(historicProcessInstanceQueryImpl, actualDeletedResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#notDeleted()}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#notDeleted()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.notDeleted()"})
  public void testNotDeleted() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualNotDeletedResult =
        historicProcessInstanceQueryImpl.notDeleted();

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isNotDeleted());
    assertSame(historicProcessInstanceQueryImpl, actualNotDeletedResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#startedBy(String)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#startedBy(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.startedBy(String)"
  })
  public void testStartedBy() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualStartedByResult =
        historicProcessInstanceQueryImpl.startedBy("Started By");

    // Assert
    assertEquals("Started By", historicProcessInstanceQueryImpl.getStartedBy());
    assertSame(historicProcessInstanceQueryImpl, actualStartedByResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyNotIn(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyNotIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processDefinitionKeyNotIn(List)"
  })
  public void testProcessDefinitionKeyNotIn_given42_whenArrayListAdd42() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    ArrayList<String> processDefinitionKeys = new ArrayList<>();
    processDefinitionKeys.add("42");
    processDefinitionKeys.add("foo");

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionKeyNotInResult =
        historicProcessInstanceQueryImpl.processDefinitionKeyNotIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicProcessInstanceQueryImpl.getProcessKeyNotIn());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionKeyNotInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyNotIn(List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyNotIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processDefinitionKeyNotIn(List)"
  })
  public void testProcessDefinitionKeyNotIn_givenFoo_whenArrayListAddFoo() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    ArrayList<String> processDefinitionKeys = new ArrayList<>();
    processDefinitionKeys.add("foo");

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionKeyNotInResult =
        historicProcessInstanceQueryImpl.processDefinitionKeyNotIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicProcessInstanceQueryImpl.getProcessKeyNotIn());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionKeyNotInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyNotIn(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processDefinitionKeyNotIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processDefinitionKeyNotIn(List)"
  })
  public void testProcessDefinitionKeyNotIn_whenArrayList() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();
    ArrayList<String> processDefinitionKeys = new ArrayList<>();

    // Act
    HistoricProcessInstanceQuery actualProcessDefinitionKeyNotInResult =
        historicProcessInstanceQueryImpl.processDefinitionKeyNotIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicProcessInstanceQueryImpl.getProcessKeyNotIn());
    assertSame(historicProcessInstanceQueryImpl, actualProcessDefinitionKeyNotInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#startedAfter(Date)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#startedAfter(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.startedAfter(Date)"
  })
  public void testStartedAfter() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();
    Date startedAfter =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    HistoricProcessInstanceQuery actualStartedAfterResult =
        historicProcessInstanceQueryImpl.startedAfter(startedAfter);

    // Assert
    assertSame(historicProcessInstanceQueryImpl, actualStartedAfterResult);
    assertSame(startedAfter, historicProcessInstanceQueryImpl.getStartedAfter());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#startedBefore(Date)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#startedBefore(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.startedBefore(Date)"
  })
  public void testStartedBefore() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();
    Date startedBefore =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    HistoricProcessInstanceQuery actualStartedBeforeResult =
        historicProcessInstanceQueryImpl.startedBefore(startedBefore);

    // Assert
    assertSame(historicProcessInstanceQueryImpl, actualStartedBeforeResult);
    assertSame(startedBefore, historicProcessInstanceQueryImpl.getStartedBefore());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#finishedAfter(Date)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#finishedAfter(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.finishedAfter(Date)"
  })
  public void testFinishedAfter() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();
    Date finishedAfter =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    HistoricProcessInstanceQuery actualFinishedAfterResult =
        historicProcessInstanceQueryImpl.finishedAfter(finishedAfter);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isFinished());
    assertSame(historicProcessInstanceQueryImpl, actualFinishedAfterResult);
    assertSame(finishedAfter, historicProcessInstanceQueryImpl.getFinishedAfter());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#finishedBefore(Date)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#finishedBefore(Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.finishedBefore(Date)"
  })
  public void testFinishedBefore() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();
    Date finishedBefore =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    HistoricProcessInstanceQuery actualFinishedBeforeResult =
        historicProcessInstanceQueryImpl.finishedBefore(finishedBefore);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isFinished());
    assertSame(historicProcessInstanceQueryImpl, actualFinishedBeforeResult);
    assertSame(finishedBefore, historicProcessInstanceQueryImpl.getFinishedBefore());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#superProcessInstanceId(String)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#superProcessInstanceId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.superProcessInstanceId(String)"
  })
  public void testSuperProcessInstanceId() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualSuperProcessInstanceIdResult =
        historicProcessInstanceQueryImpl.superProcessInstanceId("42");

    // Assert
    assertEquals("42", historicProcessInstanceQueryImpl.getSuperProcessInstanceId());
    assertSame(historicProcessInstanceQueryImpl, actualSuperProcessInstanceIdResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#excludeSubprocesses(boolean)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#excludeSubprocesses(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.excludeSubprocesses(boolean)"
  })
  public void testExcludeSubprocesses() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualExcludeSubprocessesResult =
        historicProcessInstanceQueryImpl.excludeSubprocesses(true);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isExcludeSubprocesses());
    assertSame(historicProcessInstanceQueryImpl, actualExcludeSubprocessesResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#involvedUser(String)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#involvedUser(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.involvedUser(String)"
  })
  public void testInvolvedUser() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualInvolvedUserResult =
        historicProcessInstanceQueryImpl.involvedUser("Involved User");

    // Assert
    assertEquals("Involved User", historicProcessInstanceQueryImpl.getInvolvedUser());
    assertSame(historicProcessInstanceQueryImpl, actualInvolvedUserResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceTenantId(String)}.
   *
   * <ul>
   *   <li>Then {@link HistoricProcessInstanceQueryImpl#HistoricProcessInstanceQueryImpl()} TenantId
   *       is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processInstanceTenantId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceTenantId(String)"
  })
  public void testProcessInstanceTenantId_thenHistoricProcessInstanceQueryImplTenantIdIs42() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessInstanceTenantIdResult =
        historicProcessInstanceQueryImpl.processInstanceTenantId("42");

    // Assert
    assertEquals("42", historicProcessInstanceQueryImpl.getTenantId());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceTenantIdResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceTenantId(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processInstanceTenantId(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceTenantId(String)"
  })
  public void testProcessInstanceTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricProcessInstanceQueryImpl().processInstanceTenantId(null));
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceTenantIdLike(String)}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#processInstanceTenantIdLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceTenantIdLike(String)"
  })
  public void testProcessInstanceTenantIdLike() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessInstanceTenantIdLikeResult =
        historicProcessInstanceQueryImpl.processInstanceTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", historicProcessInstanceQueryImpl.getTenantIdLike());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceTenantIdLikeResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceTenantIdLike(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#processInstanceTenantIdLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceTenantIdLike(String)"
  })
  public void testProcessInstanceTenantIdLike_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricProcessInstanceQueryImpl().processInstanceTenantIdLike(null));
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceWithoutTenantId()}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processInstanceWithoutTenantId()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceWithoutTenantId()"
  })
  public void testProcessInstanceWithoutTenantId() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessInstanceWithoutTenantIdResult =
        historicProcessInstanceQueryImpl.processInstanceWithoutTenantId();

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.isWithoutTenantId());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceWithoutTenantIdResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceName(String)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processInstanceName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceName(String)"
  })
  public void testProcessInstanceName() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessInstanceNameResult =
        historicProcessInstanceQueryImpl.processInstanceName("Name");

    // Assert
    assertEquals("Name", historicProcessInstanceQueryImpl.getName());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceNameResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceNameLike(String)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#processInstanceNameLike(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceNameLike(String)"
  })
  public void testProcessInstanceNameLike() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessInstanceNameLikeResult =
        historicProcessInstanceQueryImpl.processInstanceNameLike("Name Like");

    // Assert
    assertEquals("Name Like", historicProcessInstanceQueryImpl.getNameLike());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceNameLikeResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#processInstanceNameLikeIgnoreCase(String)}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#processInstanceNameLikeIgnoreCase(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.processInstanceNameLikeIgnoreCase(String)"
  })
  public void testProcessInstanceNameLikeIgnoreCase() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualProcessInstanceNameLikeIgnoreCaseResult =
        historicProcessInstanceQueryImpl.processInstanceNameLikeIgnoreCase("Name Like Ignore Case");

    // Assert
    assertEquals("name like ignore case", historicProcessInstanceQueryImpl.getNameLikeIgnoreCase());
    assertSame(historicProcessInstanceQueryImpl, actualProcessInstanceNameLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueEquals(String, Object)} with {@code
   * variableName}, {@code variableValue}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#variableValueEquals(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueEquals(String, Object)"
  })
  public void testVariableValueEqualsWithVariableNameVariableValue_whenNull() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueEqualsResult =
        historicProcessInstanceQueryImpl.variableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueEquals(String, Object)} with {@code
   * variableName}, {@code variableValue}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#variableValueEquals(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueEquals(String, Object)"
  })
  public void testVariableValueEqualsWithVariableNameVariableValue_whenNull2() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueEqualsResult =
        historicProcessInstanceQueryImpl.variableValueEquals("Variable Name", null);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueEquals(Object)} with {@code
   * variableValue}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#variableValueEquals(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueEquals(Object)"
  })
  public void testVariableValueEqualsWithVariableValue() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueEqualsResult =
        historicProcessInstanceQueryImpl.variableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueNotEquals(String, Object)} with
   * {@code variableName}, {@code variableValue}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#variableValueNotEquals(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueNotEquals(String, Object)"
  })
  public void testVariableValueNotEqualsWithVariableNameVariableValue_whenNull() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueNotEqualsResult =
        historicProcessInstanceQueryImpl.variableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueNotEquals(String, Object)} with
   * {@code variableName}, {@code variableValue}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#variableValueNotEquals(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueNotEquals(String, Object)"
  })
  public void testVariableValueNotEqualsWithVariableNameVariableValue_whenNull2() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueNotEqualsResult =
        historicProcessInstanceQueryImpl.variableValueNotEquals("Variable Name", null);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   * with {@code name}, {@code value}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueEqualsIgnoreCase(String, String)"
  })
  public void testVariableValueEqualsIgnoreCaseWithNameValue() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueEqualsIgnoreCaseResult =
        historicProcessInstanceQueryImpl.variableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   * with {@code name}, {@code value}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueNotEqualsIgnoreCase(String, String)"
  })
  public void testVariableValueNotEqualsIgnoreCaseWithNameValue() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueNotEqualsIgnoreCaseResult =
        historicProcessInstanceQueryImpl.variableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueGreaterThan(String, Object)} with
   * {@code name}, {@code value}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#variableValueGreaterThan(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueGreaterThan(String, Object)"
  })
  public void testVariableValueGreaterThanWithNameValue() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueGreaterThanResult =
        historicProcessInstanceQueryImpl.variableValueGreaterThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueGreaterThanResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueGreaterThan(String, Object)} with
   * {@code name}, {@code value}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#variableValueGreaterThan(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueGreaterThan(String, Object)"
  })
  public void testVariableValueGreaterThanWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricProcessInstanceQueryImpl().variableValueGreaterThan("Name", null));
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   * with {@code name}, {@code value}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueGreaterThanOrEqual(String, Object)"
  })
  public void testVariableValueGreaterThanOrEqualWithNameValue() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueGreaterThanOrEqualResult =
        historicProcessInstanceQueryImpl.variableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   * with {@code name}, {@code value}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueGreaterThanOrEqual(String, Object)"
  })
  public void testVariableValueGreaterThanOrEqualWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricProcessInstanceQueryImpl().variableValueGreaterThanOrEqual("Name", null));
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueLessThan(String, Object)} with {@code
   * name}, {@code value}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#variableValueLessThan(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueLessThan(String, Object)"
  })
  public void testVariableValueLessThanWithNameValue() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueLessThanResult =
        historicProcessInstanceQueryImpl.variableValueLessThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueLessThanResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueLessThan(String, Object)} with {@code
   * name}, {@code value}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#variableValueLessThan(String,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueLessThan(String, Object)"
  })
  public void testVariableValueLessThanWithNameValue_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricProcessInstanceQueryImpl().variableValueLessThan("Name", null));
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueLessThanOrEqual(String, Object)} with
   * {@code name}, {@code value}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueLessThanOrEqual(String, Object)"
  })
  public void testVariableValueLessThanOrEqualWithNameValue() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueLessThanOrEqualResult =
        historicProcessInstanceQueryImpl.variableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueLessThanOrEqualResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueLessThanOrEqual(String, Object)} with
   * {@code name}, {@code value}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueLessThanOrEqual(String, Object)"
  })
  public void testVariableValueLessThanOrEqualWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricProcessInstanceQueryImpl().variableValueLessThanOrEqual("Name", null));
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueLike(String, String)} with {@code
   * name}, {@code value}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#variableValueLike(String,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueLike(String, String)"
  })
  public void testVariableValueLikeWithNameValue() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueLikeResult =
        historicProcessInstanceQueryImpl.variableValueLike("Name", "42");

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueLikeResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)} with
   * {@code name}, {@code value}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.variableValueLikeIgnoreCase(String, String)"
  })
  public void testVariableValueLikeIgnoreCaseWithNameValue() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualVariableValueLikeIgnoreCaseResult =
        historicProcessInstanceQueryImpl.variableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicProcessInstanceQueryImpl, actualVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#or()}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#or()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.or()"})
  public void testOr() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act
    HistoricProcessInstanceQuery actualOrResult = historicProcessInstanceQueryImpl.or();

    // Assert
    assertTrue(historicProcessInstanceQueryImpl.inOrStatement);
    assertSame(historicProcessInstanceQueryImpl, actualOrResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#endOr()}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#endOr()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.endOr()"})
  public void testEndOr() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> new HistoricProcessInstanceQueryImpl().endOr());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}.
   *
   * <ul>
   *   <li>Then return {@code TEMPRES_ID_ asc}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricProcessInstanceQueryImpl.getMssqlOrDB2OrderBy()"})
  public void testGetMssqlOrDB2OrderBy_thenReturnTempresIdAsc() {
    // Arrange, Act and Assert
    assertEquals("TEMPRES_ID_ asc", new HistoricProcessInstanceQueryImpl().getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}.
   *
   * <ul>
   *   <li>Then return {@code TEMPRES_ID_ asc asc}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricProcessInstanceQueryImpl.getMssqlOrDB2OrderBy()"})
  public void testGetMssqlOrDB2OrderBy_thenReturnTempresIdAscAsc() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();
    historicProcessInstanceQueryImpl.addOrder(
        "RES.ID_ asc", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act and Assert
    assertEquals("TEMPRES_ID_ asc asc", historicProcessInstanceQueryImpl.getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#executeCount(CommandContext)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#executeCount(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long HistoricProcessInstanceQueryImpl.executeCount(CommandContext)"})
  public void testExecuteCount_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();
    historicProcessInstanceQueryImpl.orderBy(mock(QueryProperty.class));
    historicProcessInstanceQueryImpl.addVariable(
        "description", JSONObject.NULL, QueryOperator.NOT_EQUALS, false);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> historicProcessInstanceQueryImpl.executeCount(null));
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#executeList(CommandContext, Page)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#executeList(CommandContext,
   * Page)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List HistoricProcessInstanceQueryImpl.executeList(CommandContext, Page)"})
  public void testExecuteList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();
    historicProcessInstanceQueryImpl.orderBy(mock(QueryProperty.class));
    historicProcessInstanceQueryImpl.addVariable(
        "description", JSONObject.NULL, QueryOperator.NOT_EQUALS, false);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> historicProcessInstanceQueryImpl.executeList(null, new Page(1, 3)));
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#checkQueryOk()}.
   *
   * <ul>
   *   <li>Given {@link HistoricProcessInstanceQueryImpl#HistoricProcessInstanceQueryImpl()}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#checkQueryOk()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricProcessInstanceQueryImpl.checkQueryOk()"})
  public void testCheckQueryOk_givenHistoricProcessInstanceQueryImpl_thenDoesNotThrow() {
    // Arrange, Act and Assert
    new HistoricProcessInstanceQueryImpl().checkQueryOk();
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#checkQueryOk()}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#checkQueryOk()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricProcessInstanceQueryImpl.checkQueryOk()"})
  public void testCheckQueryOk_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();
    historicProcessInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> historicProcessInstanceQueryImpl.checkQueryOk());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#getProcessDefinitionIdLike()}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#getProcessDefinitionIdLike()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricProcessInstanceQueryImpl.getProcessDefinitionIdLike()"})
  public void testGetProcessDefinitionIdLike() {
    // Arrange, Act and Assert
    assertEquals("null:%:%", new HistoricProcessInstanceQueryImpl().getProcessDefinitionIdLike());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#involvedGroupsIn(List)}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#involvedGroupsIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.involvedGroupsIn(List)"
  })
  public void testInvolvedGroupsIn() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    HistoricProcessInstanceQuery actualInvolvedGroupsInResult =
        historicProcessInstanceQueryImpl.involvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, historicProcessInstanceQueryImpl.getInvolvedGroups());
    assertSame(historicProcessInstanceQueryImpl, actualInvolvedGroupsInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#involvedGroupsIn(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#involvedGroupsIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.involvedGroupsIn(List)"
  })
  public void testInvolvedGroupsIn_given42_whenArrayListAdd42() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("42");
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    HistoricProcessInstanceQuery actualInvolvedGroupsInResult =
        historicProcessInstanceQueryImpl.involvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, historicProcessInstanceQueryImpl.getInvolvedGroups());
    assertSame(historicProcessInstanceQueryImpl, actualInvolvedGroupsInResult);
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#involvedGroupsIn(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#involvedGroupsIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.involvedGroupsIn(List)"
  })
  public void testInvolvedGroupsIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    HistoricProcessInstanceQueryImpl historicProcessInstanceQueryImpl =
        new HistoricProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> historicProcessInstanceQueryImpl.involvedGroupsIn(new ArrayList<>()));
  }

  /**
   * Test {@link HistoricProcessInstanceQueryImpl#involvedGroupsIn(List)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryImpl#involvedGroupsIn(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQuery HistoricProcessInstanceQueryImpl.involvedGroupsIn(List)"
  })
  public void testInvolvedGroupsIn_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new HistoricProcessInstanceQueryImpl().involvedGroupsIn(null));
  }
}
