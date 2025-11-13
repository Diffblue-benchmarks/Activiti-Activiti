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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManagerImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class TerminateEndEventActivityBehaviorDiffblueTest {
  /**
   * Test new {@link TerminateEndEventActivityBehavior} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * TerminateEndEventActivityBehavior}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void TerminateEndEventActivityBehavior.<init>()"})
  public void testNewTerminateEndEventActivityBehavior() {
    // Arrange and Act
    TerminateEndEventActivityBehavior actualTerminateEndEventActivityBehavior =
        new TerminateEndEventActivityBehavior();

    // Assert
    assertFalse(actualTerminateEndEventActivityBehavior.isTerminateAll());
    assertFalse(actualTerminateEndEventActivityBehavior.isTerminateMultiInstance());
  }

  /**
   * Test {@link TerminateEndEventActivityBehavior#deleteExecutionEntities(ExecutionEntityManager,
   * ExecutionEntity, String)}.
   *
   * <p>Method under test: {@link
   * TerminateEndEventActivityBehavior#deleteExecutionEntities(ExecutionEntityManager,
   * ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TerminateEndEventActivityBehavior.deleteExecutionEntities(ExecutionEntityManager, ExecutionEntity, String)"
  })
  public void testDeleteExecutionEntities() {
    // Arrange
    TerminateEndEventActivityBehavior terminateEndEventActivityBehavior =
        new TerminateEndEventActivityBehavior();

    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    ExecutionEntityManagerImpl executionEntityManager = mock(ExecutionEntityManagerImpl.class);
    when(executionEntityManager.collectChildren(Mockito.<ExecutionEntity>any()))
        .thenReturn(executionEntityList);
    doNothing()
        .when(executionEntityManager)
        .cancelExecutionAndRelatedData(Mockito.<ExecutionEntity>any(), Mockito.<String>any());

    // Act
    terminateEndEventActivityBehavior.deleteExecutionEntities(
        executionEntityManager, mock(ExecutionEntity.class), "Just cause");

    // Assert
    verify(executionEntityManager, atLeast(1))
        .cancelExecutionAndRelatedData(Mockito.<ExecutionEntity>any(), eq("Just cause"));
    verify(executionEntityManager).collectChildren(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link TerminateEndEventActivityBehavior#deleteExecutionEntities(ExecutionEntityManager,
   * ExecutionEntity, String)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       ExecutionEntityManagerImpl#cancelExecutionAndRelatedData(ExecutionEntity, String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TerminateEndEventActivityBehavior#deleteExecutionEntities(ExecutionEntityManager,
   * ExecutionEntity, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TerminateEndEventActivityBehavior.deleteExecutionEntities(ExecutionEntityManager, ExecutionEntity, String)"
  })
  public void testDeleteExecutionEntities_thenCallsCancelExecutionAndRelatedData() {
    // Arrange
    TerminateEndEventActivityBehavior terminateEndEventActivityBehavior =
        new TerminateEndEventActivityBehavior();

    ExecutionEntityManagerImpl executionEntityManager = mock(ExecutionEntityManagerImpl.class);
    when(executionEntityManager.collectChildren(Mockito.<ExecutionEntity>any()))
        .thenReturn(new ArrayList<>());
    doNothing()
        .when(executionEntityManager)
        .cancelExecutionAndRelatedData(Mockito.<ExecutionEntity>any(), Mockito.<String>any());

    // Act
    terminateEndEventActivityBehavior.deleteExecutionEntities(
        executionEntityManager, mock(ExecutionEntity.class), "Just cause");

    // Assert
    verify(executionEntityManager)
        .cancelExecutionAndRelatedData(isA(ExecutionEntity.class), eq("Just cause"));
    verify(executionEntityManager).collectChildren(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link TerminateEndEventActivityBehavior#createDeleteReason(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@code Terminated by end event: 42}.
   * </ul>
   *
   * <p>Method under test: {@link TerminateEndEventActivityBehavior#createDeleteReason(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TerminateEndEventActivityBehavior.createDeleteReason(String)"})
  public void testCreateDeleteReason_when42_thenReturnTerminatedByEndEvent42() {
    // Arrange, Act and Assert
    assertEquals(
        "Terminated by end event: 42", TerminateEndEventActivityBehavior.createDeleteReason("42"));
  }

  /**
   * Test {@link TerminateEndEventActivityBehavior#createDeleteReason(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code Terminated by end event}.
   * </ul>
   *
   * <p>Method under test: {@link TerminateEndEventActivityBehavior#createDeleteReason(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TerminateEndEventActivityBehavior.createDeleteReason(String)"})
  public void testCreateDeleteReason_whenNull_thenReturnTerminatedByEndEvent() {
    // Arrange, Act and Assert
    assertEquals(
        "Terminated by end event", TerminateEndEventActivityBehavior.createDeleteReason(null));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TerminateEndEventActivityBehavior#setTerminateAll(boolean)}
   *   <li>{@link TerminateEndEventActivityBehavior#setTerminateMultiInstance(boolean)}
   *   <li>{@link TerminateEndEventActivityBehavior#isTerminateAll()}
   *   <li>{@link TerminateEndEventActivityBehavior#isTerminateMultiInstance()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TerminateEndEventActivityBehavior.isTerminateAll()",
    "boolean TerminateEndEventActivityBehavior.isTerminateMultiInstance()",
    "void TerminateEndEventActivityBehavior.setTerminateAll(boolean)",
    "void TerminateEndEventActivityBehavior.setTerminateMultiInstance(boolean)"
  })
  public void testGettersAndSetters() {
    // Arrange
    TerminateEndEventActivityBehavior terminateEndEventActivityBehavior =
        new TerminateEndEventActivityBehavior();

    // Act
    terminateEndEventActivityBehavior.setTerminateAll(true);
    terminateEndEventActivityBehavior.setTerminateMultiInstance(true);
    boolean actualIsTerminateAllResult = terminateEndEventActivityBehavior.isTerminateAll();

    // Assert
    assertTrue(actualIsTerminateAllResult);
    assertTrue(terminateEndEventActivityBehavior.isTerminateMultiInstance());
  }
}
