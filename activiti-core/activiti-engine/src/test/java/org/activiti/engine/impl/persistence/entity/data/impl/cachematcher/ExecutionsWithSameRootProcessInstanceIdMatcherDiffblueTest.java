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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import org.activiti.engine.impl.persistence.cache.CachedEntity;
import org.activiti.engine.impl.persistence.entity.AbstractEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExecutionsWithSameRootProcessInstanceIdMatcherDiffblueTest {
  @InjectMocks
  private ExecutionsWithSameRootProcessInstanceIdMatcher executionsWithSameRootProcessInstanceIdMatcher;

  /**
   * Test
   * {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection, String)}.
   * <p>
   * Method under test:
   * {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection, String)}
   */
  @Test
  public void testGetMatchingExecution() {
    // Arrange
    ExecutionsWithSameRootProcessInstanceIdMatcher executionsWithSameRootProcessInstanceIdMatcher2 = new ExecutionsWithSameRootProcessInstanceIdMatcher();
    ArrayList<ExecutionEntity> databaseEntities = new ArrayList<>();

    ArrayList<CachedEntity> cachedEntities = new ArrayList<>();
    cachedEntities.add(new CachedEntity(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), true));

    // Act and Assert
    assertNull(
        executionsWithSameRootProcessInstanceIdMatcher2.getMatchingExecution(databaseEntities, cachedEntities, "42"));
  }

  /**
   * Test
   * {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection, String)}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection, String)}
   */
  @Test
  public void testGetMatchingExecution_givenCreateWithEmptyRelationshipCollections() {
    // Arrange
    ExecutionsWithSameRootProcessInstanceIdMatcher executionsWithSameRootProcessInstanceIdMatcher2 = new ExecutionsWithSameRootProcessInstanceIdMatcher();

    ArrayList<ExecutionEntity> databaseEntities = new ArrayList<>();
    databaseEntities.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertNull(executionsWithSameRootProcessInstanceIdMatcher2.getMatchingExecution(databaseEntities, new ArrayList<>(),
        "42"));
  }

  /**
   * Test
   * {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection, String)}.
   * <ul>
   *   <li>Given createWithEmptyRelationshipCollections.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection, String)}
   */
  @Test
  public void testGetMatchingExecution_givenCreateWithEmptyRelationshipCollections2() {
    // Arrange
    ExecutionsWithSameRootProcessInstanceIdMatcher executionsWithSameRootProcessInstanceIdMatcher2 = new ExecutionsWithSameRootProcessInstanceIdMatcher();

    ArrayList<ExecutionEntity> databaseEntities = new ArrayList<>();
    databaseEntities.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());
    databaseEntities.add(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act and Assert
    assertNull(executionsWithSameRootProcessInstanceIdMatcher2.getMatchingExecution(databaseEntities, new ArrayList<>(),
        "42"));
  }

  /**
   * Test
   * {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection, String)}.
   * <ul>
   *   <li>Then calls {@link AbstractEntity#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection, String)}
   */
  @Test
  public void testGetMatchingExecution_thenCallsGetId() {
    // Arrange
    ExecutionsWithSameRootProcessInstanceIdMatcher executionsWithSameRootProcessInstanceIdMatcher2 = new ExecutionsWithSameRootProcessInstanceIdMatcher();
    ArrayList<ExecutionEntity> databaseEntities = new ArrayList<>();
    ExecutionEntityImpl entity = mock(ExecutionEntityImpl.class);
    when(entity.getPersistentState()).thenReturn(JSONObject.NULL);
    when(entity.getId()).thenReturn("42");
    CachedEntity cachedEntity = new CachedEntity(entity, true);

    ArrayList<CachedEntity> cachedEntities = new ArrayList<>();
    cachedEntities.add(cachedEntity);

    // Act
    executionsWithSameRootProcessInstanceIdMatcher2.getMatchingExecution(databaseEntities, cachedEntities, "42");

    // Assert
    verify(entity).getId();
    verify(entity).getPersistentState();
  }

  /**
   * Test
   * {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection, String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExecutionsWithSameRootProcessInstanceIdMatcher#getMatchingExecution(Collection, Collection, String)}
   */
  @Test
  public void testGetMatchingExecution_thenReturnNull() {
    // Arrange
    ExecutionsWithSameRootProcessInstanceIdMatcher executionsWithSameRootProcessInstanceIdMatcher2 = new ExecutionsWithSameRootProcessInstanceIdMatcher();
    ArrayList<ExecutionEntity> databaseEntities = new ArrayList<>();

    // Act and Assert
    assertNull(executionsWithSameRootProcessInstanceIdMatcher2.getMatchingExecution(databaseEntities, new ArrayList<>(),
        "42"));
  }
}
