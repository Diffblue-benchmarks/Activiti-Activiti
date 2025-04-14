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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
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
import org.activiti.engine.impl.AbstractQuery.NullHandlingOnOrder;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessInstanceQueryImplDiffblueTest {
  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceIds(Set)"})
  public void testProcessInstanceIds_given42_whenHashSetAdd42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    HashSet<String> processInstanceIds = new HashSet<>();
    processInstanceIds.add("42");
    processInstanceIds.add("Set of process instance ids is empty");

    // Act
    ProcessInstanceQuery actualProcessInstanceIdsResult = processInstanceQueryImpl
        .processInstanceIds(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, processInstanceQueryImpl.getProcessInstanceIds());
    assertSame(processInstanceQueryImpl, actualProcessInstanceIdsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}.
   * <ul>
   *   <li>Then {@link ProcessInstanceQueryImpl#ProcessInstanceQueryImpl()} ProcessInstanceIds is {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceIds(Set)"})
  public void testProcessInstanceIds_thenProcessInstanceQueryImplProcessInstanceIdsIsHashSet() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    HashSet<String> processInstanceIds = new HashSet<>();
    processInstanceIds.add("Set of process instance ids is empty");

    // Act
    ProcessInstanceQuery actualProcessInstanceIdsResult = processInstanceQueryImpl
        .processInstanceIds(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, processInstanceQueryImpl.getProcessInstanceIds());
    assertSame(processInstanceQueryImpl, actualProcessInstanceIdsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceIds(Set)"})
  public void testProcessInstanceIds_whenHashSet_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processInstanceIds(new HashSet<>()));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processInstanceWithoutTenantId()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processInstanceWithoutTenantId()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processInstanceWithoutTenantId()"})
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
   * Test {@link ProcessInstanceQueryImpl#processDefinitionVersion(Integer)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionVersion(Integer)"})
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
   * Test {@link ProcessInstanceQueryImpl#processDefinitionVersion(Integer)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionVersion(Integer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionVersion(Integer)"})
  public void testProcessDefinitionVersion_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ProcessInstanceQueryImpl()).processDefinitionVersion(null));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionIds(Set)"})
  public void testProcessDefinitionIds() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    HashSet<String> processDefinitionIds = new HashSet<>();
    processDefinitionIds.add("Set of process definition ids is empty");

    // Act
    ProcessInstanceQuery actualProcessDefinitionIdsResult = processInstanceQueryImpl
        .processDefinitionIds(processDefinitionIds);

    // Assert
    assertSame(processDefinitionIds, processInstanceQueryImpl.getProcessDefinitionIds());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionIdsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionIds(Set)"})
  public void testProcessDefinitionIds_given42_whenHashSetAdd42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    HashSet<String> processDefinitionIds = new HashSet<>();
    processDefinitionIds.add("42");
    processDefinitionIds.add("Set of process definition ids is empty");

    // Act
    ProcessInstanceQuery actualProcessDefinitionIdsResult = processInstanceQueryImpl
        .processDefinitionIds(processDefinitionIds);

    // Assert
    assertSame(processDefinitionIds, processInstanceQueryImpl.getProcessDefinitionIds());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionIdsResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionIds(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionIds(Set)"})
  public void testProcessDefinitionIds_whenHashSet_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processDefinitionIds(new HashSet<>()));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("Set of process definition keys is empty");

    // Act
    ProcessInstanceQuery actualProcessDefinitionKeysResult = processInstanceQueryImpl
        .processDefinitionKeys(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, processInstanceQueryImpl.getProcessDefinitionKeys());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionKeysResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys_given42_whenHashSetAdd42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    HashSet<String> processDefinitionKeys = new HashSet<>();
    processDefinitionKeys.add("42");
    processDefinitionKeys.add("Set of process definition keys is empty");

    // Act
    ProcessInstanceQuery actualProcessDefinitionKeysResult = processInstanceQueryImpl
        .processDefinitionKeys(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, processInstanceQueryImpl.getProcessDefinitionKeys());
    assertSame(processInstanceQueryImpl, actualProcessDefinitionKeysResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#processDefinitionKeys(Set)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.processDefinitionKeys(Set)"})
  public void testProcessDefinitionKeys_whenHashSet_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.processDefinitionKeys(new HashSet<>()));
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.deploymentIdIn(List)"})
  public void testDeploymentIdIn_given42_whenArrayListAdd42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("42");
    deploymentIds.add("foo");

    // Act
    ProcessInstanceQueryImpl actualDeploymentIdInResult = processInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, processInstanceQueryImpl.getDeploymentIds());
    assertSame(processInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.deploymentIdIn(List)"})
  public void testDeploymentIdIn_givenFoo_whenArrayListAddFoo() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("foo");

    // Act
    ProcessInstanceQueryImpl actualDeploymentIdInResult = processInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, processInstanceQueryImpl.getDeploymentIds());
    assertSame(processInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQueryImpl ProcessInstanceQueryImpl.deploymentIdIn(List)"})
  public void testDeploymentIdIn_whenArrayList() {
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
   * Test {@link ProcessInstanceQueryImpl#excludeSubprocesses(boolean)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#excludeSubprocesses(boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.excludeSubprocesses(boolean)"})
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
   * Test {@link ProcessInstanceQueryImpl#active()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#active()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.active()"})
  public void testActive() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.active());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#suspended()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#suspended()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.suspended()"})
  public void testSuspended() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.suspended());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#endOr()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#endOr()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.endOr()"})
  public void testEndOr() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new ProcessInstanceQueryImpl()).endOr());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#startedBefore(Date)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#startedBefore(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.startedBefore(Date)"})
  public void testStartedBefore() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();
    Date beforeTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.startedBefore(beforeTime));
    assertSame(beforeTime, processInstanceQueryImpl.getStartedBefore());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#startedAfter(Date)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#startedAfter(Date)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.startedAfter(Date)"})
  public void testStartedAfter() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();
    Date afterTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(processInstanceQueryImpl, processInstanceQueryImpl.startedAfter(afterTime));
    assertSame(afterTime, processInstanceQueryImpl.getStartedAfter());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}.
   * <ul>
   *   <li>Then return {@code TEMPRES_ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessInstanceQueryImpl.getMssqlOrDB2OrderBy()"})
  public void testGetMssqlOrDB2OrderBy_thenReturnTempresIdAsc() {
    // Arrange, Act and Assert
    assertEquals("TEMPRES_ID_ asc", (new ProcessInstanceQueryImpl()).getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}.
   * <ul>
   *   <li>Then return {@code TEMPRES_ID_ asc asc}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ProcessInstanceQueryImpl.getMssqlOrDB2OrderBy()"})
  public void testGetMssqlOrDB2OrderBy_thenReturnTempresIdAscAsc() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();
    processInstanceQueryImpl.addOrder("RES.ID_ asc", AbstractQuery.SORTORDER_ASC, NullHandlingOnOrder.NULLS_FIRST);

    // Act and Assert
    assertEquals("TEMPRES_ID_ asc asc", processInstanceQueryImpl.getMssqlOrDB2OrderBy());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#iswithException()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#iswithException()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ProcessInstanceQueryImpl.iswithException()"})
  public void testIswithException() {
    // Arrange, Act and Assert
    assertFalse((new ProcessInstanceQueryImpl()).iswithException());
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.involvedGroupsIn(List)"})
  public void testInvolvedGroupsIn_given42_whenArrayListAdd42() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("42");
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    ProcessInstanceQuery actualInvolvedGroupsInResult = processInstanceQueryImpl.involvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, processInstanceQueryImpl.getInvolvedGroups());
    assertSame(processInstanceQueryImpl, actualInvolvedGroupsInResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}.
   * <ul>
   *   <li>Then {@link ProcessInstanceQueryImpl#ProcessInstanceQueryImpl()} InvolvedGroups is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.involvedGroupsIn(List)"})
  public void testInvolvedGroupsIn_thenProcessInstanceQueryImplInvolvedGroupsIsArrayList() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    ProcessInstanceQuery actualInvolvedGroupsInResult = processInstanceQueryImpl.involvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, processInstanceQueryImpl.getInvolvedGroups());
    assertSame(processInstanceQueryImpl, actualInvolvedGroupsInResult);
  }

  /**
   * Test {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceQueryImpl#involvedGroupsIn(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessInstanceQuery ProcessInstanceQueryImpl.involvedGroupsIn(List)"})
  public void testInvolvedGroupsIn_whenArrayList_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ProcessInstanceQueryImpl processInstanceQueryImpl = new ProcessInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> processInstanceQueryImpl.involvedGroupsIn(new ArrayList<>()));
  }
}
