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
import java.util.ArrayList;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TerminateEndEventActivityBehaviorDiffblueTest {
  @InjectMocks
  private TerminateEndEventActivityBehavior terminateEndEventActivityBehavior;

  /**
   * Test new {@link TerminateEndEventActivityBehavior} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link TerminateEndEventActivityBehavior}
   */
  @Test
  public void testNewTerminateEndEventActivityBehavior() {
    // Arrange and Act
    TerminateEndEventActivityBehavior actualTerminateEndEventActivityBehavior = new TerminateEndEventActivityBehavior();

    // Assert
    assertFalse(actualTerminateEndEventActivityBehavior.isTerminateAll());
    assertFalse(actualTerminateEndEventActivityBehavior.isTerminateMultiInstance());
  }

  /**
   * Test
   * {@link TerminateEndEventActivityBehavior#deleteExecutionEntities(ExecutionEntityManager, ExecutionEntity, String)}.
   * <p>
   * Method under test:
   * {@link TerminateEndEventActivityBehavior#deleteExecutionEntities(ExecutionEntityManager, ExecutionEntity, String)}
   */
  @Test
  public void testDeleteExecutionEntities() {
    // Arrange
    ArrayList<ExecutionEntity> executionEntityList = new ArrayList<>();
    executionEntityList.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.collectChildren(Mockito.<ExecutionEntity>any())).thenReturn(executionEntityList);
    doNothing().when(executionEntityManager)
        .cancelExecutionAndRelatedData(Mockito.<ExecutionEntity>any(), Mockito.<String>any());

    // Act
    terminateEndEventActivityBehavior.deleteExecutionEntities(executionEntityManager,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause");

    // Assert
    verify(executionEntityManager, atLeast(1)).cancelExecutionAndRelatedData(Mockito.<ExecutionEntity>any(),
        eq("Just cause"));
    verify(executionEntityManager).collectChildren(isA(ExecutionEntity.class));
  }

  /**
   * Test
   * {@link TerminateEndEventActivityBehavior#deleteExecutionEntities(ExecutionEntityManager, ExecutionEntity, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TerminateEndEventActivityBehavior#deleteExecutionEntities(ExecutionEntityManager, ExecutionEntity, String)}
   */
  @Test
  public void testDeleteExecutionEntities_givenArrayList() {
    // Arrange
    ExecutionEntityManager executionEntityManager = mock(ExecutionEntityManager.class);
    when(executionEntityManager.collectChildren(Mockito.<ExecutionEntity>any())).thenReturn(new ArrayList<>());
    doNothing().when(executionEntityManager)
        .cancelExecutionAndRelatedData(Mockito.<ExecutionEntity>any(), Mockito.<String>any());

    // Act
    terminateEndEventActivityBehavior.deleteExecutionEntities(executionEntityManager,
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Just cause");

    // Assert
    verify(executionEntityManager).cancelExecutionAndRelatedData(isA(ExecutionEntity.class), eq("Just cause"));
    verify(executionEntityManager).collectChildren(isA(ExecutionEntity.class));
  }

  /**
   * Test {@link TerminateEndEventActivityBehavior#createDeleteReason(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code Terminated by end event: 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TerminateEndEventActivityBehavior#createDeleteReason(String)}
   */
  @Test
  public void testCreateDeleteReason_when42_thenReturnTerminatedByEndEvent42() {
    // Arrange, Act and Assert
    assertEquals("Terminated by end event: 42", TerminateEndEventActivityBehavior.createDeleteReason("42"));
  }

  /**
   * Test {@link TerminateEndEventActivityBehavior#createDeleteReason(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code Terminated by end event}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TerminateEndEventActivityBehavior#createDeleteReason(String)}
   */
  @Test
  public void testCreateDeleteReason_whenNull_thenReturnTerminatedByEndEvent() {
    // Arrange, Act and Assert
    assertEquals("Terminated by end event", TerminateEndEventActivityBehavior.createDeleteReason(null));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TerminateEndEventActivityBehavior#setTerminateAll(boolean)}
   *   <li>
   * {@link TerminateEndEventActivityBehavior#setTerminateMultiInstance(boolean)}
   *   <li>{@link TerminateEndEventActivityBehavior#isTerminateAll()}
   *   <li>{@link TerminateEndEventActivityBehavior#isTerminateMultiInstance()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    TerminateEndEventActivityBehavior terminateEndEventActivityBehavior = new TerminateEndEventActivityBehavior();

    // Act
    terminateEndEventActivityBehavior.setTerminateAll(true);
    terminateEndEventActivityBehavior.setTerminateMultiInstance(true);
    boolean actualIsTerminateAllResult = terminateEndEventActivityBehavior.isTerminateAll();

    // Assert that nothing has changed
    assertTrue(actualIsTerminateAllResult);
    assertTrue(terminateEndEventActivityBehavior.isTerminateMultiInstance());
  }
}
