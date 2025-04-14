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
package org.activiti.engine.impl.persistence;

import static org.junit.Assert.assertFalse;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import org.activiti.engine.impl.persistence.cache.CachedEntity;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntity;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.DeadLetterJobsByExecutionIdMatcher;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CachedEntityMatcherAdapterDiffblueTest {
  /**
   * Test {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)} with {@code databaseEntities}, {@code cachedEntities}, {@code entity}, {@code param}.
   * <p>
   * Method under test: {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean CachedEntityMatcherAdapter.isRetained(Collection, Collection, Entity, Object)"})
  public void testIsRetainedWithDatabaseEntitiesCachedEntitiesEntityParam() {
    // Arrange
    DeadLetterJobsByExecutionIdMatcher deadLetterJobsByExecutionIdMatcher = new DeadLetterJobsByExecutionIdMatcher();

    ArrayList<DeadLetterJobEntity> databaseEntities = new ArrayList<>();
    databaseEntities.add(new DeadLetterJobEntityImpl());
    ArrayList<CachedEntity> cachedEntities = new ArrayList<>();

    // Act and Assert
    assertFalse(deadLetterJobsByExecutionIdMatcher.isRetained(databaseEntities, cachedEntities,
        new DeadLetterJobEntityImpl(), JSONObject.NULL));
  }

  /**
   * Test {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)} with {@code databaseEntities}, {@code cachedEntities}, {@code entity}, {@code param}.
   * <p>
   * Method under test: {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean CachedEntityMatcherAdapter.isRetained(Collection, Collection, Entity, Object)"})
  public void testIsRetainedWithDatabaseEntitiesCachedEntitiesEntityParam2() {
    // Arrange
    DeadLetterJobsByExecutionIdMatcher deadLetterJobsByExecutionIdMatcher = new DeadLetterJobsByExecutionIdMatcher();

    ArrayList<DeadLetterJobEntity> databaseEntities = new ArrayList<>();
    databaseEntities.add(new DeadLetterJobEntityImpl());
    databaseEntities.add(new DeadLetterJobEntityImpl());
    ArrayList<CachedEntity> cachedEntities = new ArrayList<>();

    // Act and Assert
    assertFalse(deadLetterJobsByExecutionIdMatcher.isRetained(databaseEntities, cachedEntities,
        new DeadLetterJobEntityImpl(), JSONObject.NULL));
  }

  /**
   * Test {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)} with {@code databaseEntities}, {@code cachedEntities}, {@code entity}, {@code param}.
   * <p>
   * Method under test: {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean CachedEntityMatcherAdapter.isRetained(Collection, Collection, Entity, Object)"})
  public void testIsRetainedWithDatabaseEntitiesCachedEntitiesEntityParam3() {
    // Arrange
    DeadLetterJobsByExecutionIdMatcher deadLetterJobsByExecutionIdMatcher = new DeadLetterJobsByExecutionIdMatcher();
    ArrayList<DeadLetterJobEntity> databaseEntities = new ArrayList<>();

    ArrayList<CachedEntity> cachedEntities = new ArrayList<>();
    cachedEntities.add(new CachedEntity(new AttachmentEntityImpl(), true));

    // Act and Assert
    assertFalse(deadLetterJobsByExecutionIdMatcher.isRetained(databaseEntities, cachedEntities,
        new DeadLetterJobEntityImpl(), JSONObject.NULL));
  }

  /**
   * Test {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)} with {@code databaseEntities}, {@code cachedEntities}, {@code entity}, {@code param}.
   * <p>
   * Method under test: {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean CachedEntityMatcherAdapter.isRetained(Collection, Collection, Entity, Object)"})
  public void testIsRetainedWithDatabaseEntitiesCachedEntitiesEntityParam4() {
    // Arrange
    DeadLetterJobsByExecutionIdMatcher deadLetterJobsByExecutionIdMatcher = new DeadLetterJobsByExecutionIdMatcher();
    ArrayList<DeadLetterJobEntity> databaseEntities = new ArrayList<>();

    ArrayList<CachedEntity> cachedEntities = new ArrayList<>();
    cachedEntities.add(new CachedEntity(new AttachmentEntityImpl(), true));
    cachedEntities.add(new CachedEntity(new AttachmentEntityImpl(), true));

    // Act and Assert
    assertFalse(deadLetterJobsByExecutionIdMatcher.isRetained(databaseEntities, cachedEntities,
        new DeadLetterJobEntityImpl(), JSONObject.NULL));
  }

  /**
   * Test {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)} with {@code databaseEntities}, {@code cachedEntities}, {@code entity}, {@code param}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean CachedEntityMatcherAdapter.isRetained(Collection, Collection, Entity, Object)"})
  public void testIsRetainedWithDatabaseEntitiesCachedEntitiesEntityParam_thenReturnFalse() {
    // Arrange
    DeadLetterJobsByExecutionIdMatcher deadLetterJobsByExecutionIdMatcher = new DeadLetterJobsByExecutionIdMatcher();
    ArrayList<DeadLetterJobEntity> databaseEntities = new ArrayList<>();
    ArrayList<CachedEntity> cachedEntities = new ArrayList<>();

    // Act and Assert
    assertFalse(deadLetterJobsByExecutionIdMatcher.isRetained(databaseEntities, cachedEntities,
        new DeadLetterJobEntityImpl(), JSONObject.NULL));
  }
}
