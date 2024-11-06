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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class FindActiveActivityIdsCmdDiffblueTest {
  /**
   * Test {@link FindActiveActivityIdsCmd#FindActiveActivityIdsCmd(String)}.
   * <p>
   * Method under test:
   * {@link FindActiveActivityIdsCmd#FindActiveActivityIdsCmd(String)}
   */
  @Test
  public void testNewFindActiveActivityIdsCmd() {
    // Arrange, Act and Assert
    assertEquals("42", (new FindActiveActivityIdsCmd("42")).executionId);
  }

  /**
   * Test {@link FindActiveActivityIdsCmd#findActiveActivityIds(ExecutionEntity)}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FindActiveActivityIdsCmd#findActiveActivityIds(ExecutionEntity)}
   */
  @Test
  public void testFindActiveActivityIds_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    FindActiveActivityIdsCmd findActiveActivityIdsCmd = new FindActiveActivityIdsCmd("42");
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    executionEntity.addChildExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertTrue(findActiveActivityIdsCmd.findActiveActivityIds(executionEntity).isEmpty());
  }

  /**
   * Test {@link FindActiveActivityIdsCmd#findActiveActivityIds(ExecutionEntity)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FindActiveActivityIdsCmd#findActiveActivityIds(ExecutionEntity)}
   */
  @Test
  public void testFindActiveActivityIds_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    FindActiveActivityIdsCmd findActiveActivityIdsCmd = new FindActiveActivityIdsCmd("42");

    // Act and Assert
    assertTrue(
        findActiveActivityIdsCmd.findActiveActivityIds(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
            .isEmpty());
  }

  /**
   * Test
   * {@link FindActiveActivityIdsCmd#collectActiveActivityIds(ExecutionEntity, List)}.
   * <p>
   * Method under test:
   * {@link FindActiveActivityIdsCmd#collectActiveActivityIds(ExecutionEntity, List)}
   */
  @Test
  public void testCollectActiveActivityIds() {
    // Arrange
    FindActiveActivityIdsCmd findActiveActivityIdsCmd = new FindActiveActivityIdsCmd("42");

    ArrayList<ExecutionEntityImpl> executionEntityImplList = new ArrayList<>();
    executionEntityImplList.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getActivityId()).thenReturn("42");
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.getExecutions()).thenReturn(executionEntityImplList);
    ArrayList<String> activeActivityIds = new ArrayList<>();

    // Act
    findActiveActivityIdsCmd.collectActiveActivityIds(executionEntity, activeActivityIds);

    // Assert
    verify(executionEntity, atLeast(1)).getActivityId();
    verify(executionEntity).getExecutions();
    verify(executionEntity).isActive();
    assertEquals(1, activeActivityIds.size());
    assertEquals("42", activeActivityIds.get(0));
  }

  /**
   * Test
   * {@link FindActiveActivityIdsCmd#collectActiveActivityIds(ExecutionEntity, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FindActiveActivityIdsCmd#collectActiveActivityIds(ExecutionEntity, List)}
   */
  @Test
  public void testCollectActiveActivityIds_givenArrayList_thenArrayListSizeIsOne() {
    // Arrange
    FindActiveActivityIdsCmd findActiveActivityIdsCmd = new FindActiveActivityIdsCmd("42");
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getActivityId()).thenReturn("42");
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    ArrayList<String> activeActivityIds = new ArrayList<>();

    // Act
    findActiveActivityIdsCmd.collectActiveActivityIds(executionEntity, activeActivityIds);

    // Assert
    verify(executionEntity, atLeast(1)).getActivityId();
    verify(executionEntity).getExecutions();
    verify(executionEntity).isActive();
    assertEquals(1, activeActivityIds.size());
    assertEquals("42", activeActivityIds.get(0));
  }

  /**
   * Test
   * {@link FindActiveActivityIdsCmd#collectActiveActivityIds(ExecutionEntity, List)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FindActiveActivityIdsCmd#collectActiveActivityIds(ExecutionEntity, List)}
   */
  @Test
  public void testCollectActiveActivityIds_givenFalse() {
    // Arrange
    FindActiveActivityIdsCmd findActiveActivityIdsCmd = new FindActiveActivityIdsCmd("42");
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.isActive()).thenReturn(false);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());
    ArrayList<String> activeActivityIds = new ArrayList<>();

    // Act
    findActiveActivityIdsCmd.collectActiveActivityIds(executionEntity, activeActivityIds);

    // Assert that nothing has changed
    verify(executionEntity).getExecutions();
    verify(executionEntity).isActive();
    assertTrue(activeActivityIds.isEmpty());
  }

  /**
   * Test
   * {@link FindActiveActivityIdsCmd#collectActiveActivityIds(ExecutionEntity, List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FindActiveActivityIdsCmd#collectActiveActivityIds(ExecutionEntity, List)}
   */
  @Test
  public void testCollectActiveActivityIds_givenFoo_whenArrayListAddFoo_thenArrayListSizeIsTwo() {
    // Arrange
    FindActiveActivityIdsCmd findActiveActivityIdsCmd = new FindActiveActivityIdsCmd("42");
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getActivityId()).thenReturn("42");
    when(executionEntity.isActive()).thenReturn(true);
    when(executionEntity.getExecutions()).thenReturn(new ArrayList<>());

    ArrayList<String> activeActivityIds = new ArrayList<>();
    activeActivityIds.add("foo");

    // Act
    findActiveActivityIdsCmd.collectActiveActivityIds(executionEntity, activeActivityIds);

    // Assert
    verify(executionEntity, atLeast(1)).getActivityId();
    verify(executionEntity).getExecutions();
    verify(executionEntity).isActive();
    assertEquals(2, activeActivityIds.size());
    assertEquals("42", activeActivityIds.get(1));
    assertEquals("foo", activeActivityIds.get(0));
  }

  /**
   * Test
   * {@link FindActiveActivityIdsCmd#collectActiveActivityIds(ExecutionEntity, List)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FindActiveActivityIdsCmd#collectActiveActivityIds(ExecutionEntity, List)}
   */
  @Test
  public void testCollectActiveActivityIds_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    FindActiveActivityIdsCmd findActiveActivityIdsCmd = new FindActiveActivityIdsCmd("42");
    ExecutionEntityImpl executionEntity = mock(ExecutionEntityImpl.class);
    when(executionEntity.getActivityId()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(executionEntity.isActive()).thenReturn(true);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> findActiveActivityIdsCmd.collectActiveActivityIds(executionEntity, new ArrayList<>()));
    verify(executionEntity).getActivityId();
    verify(executionEntity).isActive();
  }

  /**
   * Test
   * {@link FindActiveActivityIdsCmd#collectActiveActivityIds(ExecutionEntity, List)}.
   * <ul>
   *   <li>When createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FindActiveActivityIdsCmd#collectActiveActivityIds(ExecutionEntity, List)}
   */
  @Test
  public void testCollectActiveActivityIds_whenCreateWithEmptyRelationshipCollections() {
    // Arrange
    FindActiveActivityIdsCmd findActiveActivityIdsCmd = new FindActiveActivityIdsCmd("42");
    ExecutionEntityImpl executionEntity = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    ArrayList<String> activeActivityIds = new ArrayList<>();

    // Act
    findActiveActivityIdsCmd.collectActiveActivityIds(executionEntity, activeActivityIds);

    // Assert that nothing has changed
    assertTrue(activeActivityIds.isEmpty());
  }
}
