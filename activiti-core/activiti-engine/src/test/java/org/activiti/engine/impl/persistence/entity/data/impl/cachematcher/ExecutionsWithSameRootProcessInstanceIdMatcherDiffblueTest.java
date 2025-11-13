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
package org.activiti.engine.impl.persistence.entity.data.impl.cachematcher;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import org.activiti.engine.impl.persistence.cache.CachedEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ExecutionsWithSameRootProcessInstanceIdMatcherDiffblueTest {
  /**
   * Test {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection,
   * Collection, String)}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionsWithSameRootProcessInstanceIdMatcher.getMatchingExecution(Collection, Collection, String)"
  })
  public void testGetMatchingExecution_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionsWithSameRootProcessInstanceIdMatcher executionsWithSameRootProcessInstanceIdMatcher =
        new ExecutionsWithSameRootProcessInstanceIdMatcher();

    LinkedHashSet<ExecutionEntity> databaseEntities = new LinkedHashSet<>();
    databaseEntities.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    LinkedHashSet<CachedEntity> cachedEntities = new LinkedHashSet<>();
    cachedEntities.add(
        new CachedEntity(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true));

    // Act and Assert
    assertNull(
        executionsWithSameRootProcessInstanceIdMatcher.getMatchingExecution(
            databaseEntities, cachedEntities, "42"));
  }

  /**
   * Test {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection,
   * Collection, String)}.
   *
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionsWithSameRootProcessInstanceIdMatcher.getMatchingExecution(Collection, Collection, String)"
  })
  public void testGetMatchingExecution_givenCreateWithEmptyRelationshipCollections2() {
    // Arrange
    ExecutionsWithSameRootProcessInstanceIdMatcher executionsWithSameRootProcessInstanceIdMatcher =
        new ExecutionsWithSameRootProcessInstanceIdMatcher();

    LinkedHashSet<ExecutionEntity> databaseEntities = new LinkedHashSet<>();
    databaseEntities.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertNull(
        executionsWithSameRootProcessInstanceIdMatcher.getMatchingExecution(
            databaseEntities, null, "42"));
  }

  /**
   * Test {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection,
   * Collection, String)}.
   *
   * <ul>
   *   <li>Given {@link ExecutionEntityImpl} {@link ExecutionEntityImpl#getId()} return {@code 42}.
   *   <li>Then calls {@link ExecutionEntityImpl#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionsWithSameRootProcessInstanceIdMatcher.getMatchingExecution(Collection, Collection, String)"
  })
  public void testGetMatchingExecution_givenExecutionEntityImplGetIdReturn42_thenCallsGetId() {
    // Arrange
    ExecutionsWithSameRootProcessInstanceIdMatcher executionsWithSameRootProcessInstanceIdMatcher =
        new ExecutionsWithSameRootProcessInstanceIdMatcher();

    ExecutionEntityImpl executionEntityImpl = mock(ExecutionEntityImpl.class);
    when(executionEntityImpl.getId()).thenReturn("42");

    LinkedHashSet<ExecutionEntity> databaseEntities = new LinkedHashSet<>();
    databaseEntities.add(executionEntityImpl);

    LinkedHashSet<CachedEntity> cachedEntities = new LinkedHashSet<>();
    cachedEntities.add(
        new CachedEntity(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true));

    // Act
    executionsWithSameRootProcessInstanceIdMatcher.getMatchingExecution(
        databaseEntities, cachedEntities, "42");

    // Assert
    verify(executionEntityImpl).getId();
  }

  /**
   * Test {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection,
   * Collection, String)}.
   *
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionsWithSameRootProcessInstanceIdMatcher.getMatchingExecution(Collection, Collection, String)"
  })
  public void testGetMatchingExecution_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionsWithSameRootProcessInstanceIdMatcher executionsWithSameRootProcessInstanceIdMatcher =
        new ExecutionsWithSameRootProcessInstanceIdMatcher();

    LinkedHashSet<ExecutionEntity> databaseEntities = new LinkedHashSet<>();
    databaseEntities.add(mock(ExecutionEntityImpl.class));

    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setId("42");

    CachedEntity cachedEntity = mock(CachedEntity.class);
    when(cachedEntity.getEntity()).thenReturn(createWithEmptyRelationshipCollectionsResult);

    LinkedHashSet<CachedEntity> cachedEntities = new LinkedHashSet<>();
    cachedEntities.add(cachedEntity);

    // Act
    ExecutionEntity actualMatchingExecution =
        executionsWithSameRootProcessInstanceIdMatcher.getMatchingExecution(
            databaseEntities, cachedEntities, "42");

    // Assert
    verify(cachedEntity).getEntity();
    assertSame(createWithEmptyRelationshipCollectionsResult, actualMatchingExecution);
  }

  /**
   * Test {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection,
   * Collection, String)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionsWithSameRootProcessInstanceIdMatcher.getMatchingExecution(Collection, Collection, String)"
  })
  public void testGetMatchingExecution_whenArrayList_thenReturnNull() {
    // Arrange
    ExecutionsWithSameRootProcessInstanceIdMatcher executionsWithSameRootProcessInstanceIdMatcher =
        new ExecutionsWithSameRootProcessInstanceIdMatcher();
    ArrayList<ExecutionEntity> databaseEntities = new ArrayList<>();

    // Act and Assert
    assertNull(
        executionsWithSameRootProcessInstanceIdMatcher.getMatchingExecution(
            databaseEntities, new ArrayList<>(), "42"));
  }

  /**
   * Test {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection,
   * Collection, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ExecutionEntity ExecutionsWithSameRootProcessInstanceIdMatcher.getMatchingExecution(Collection, Collection, String)"
  })
  public void testGetMatchingExecution_whenNull_thenReturnNull() {
    // Arrange
    ExecutionsWithSameRootProcessInstanceIdMatcher executionsWithSameRootProcessInstanceIdMatcher =
        new ExecutionsWithSameRootProcessInstanceIdMatcher();

    LinkedHashSet<CachedEntity> cachedEntities = new LinkedHashSet<>();
    cachedEntities.add(
        new CachedEntity(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true));

    // Act and Assert
    assertNull(
        executionsWithSameRootProcessInstanceIdMatcher.getMatchingExecution(
            null, cachedEntities, "42"));
  }
}
