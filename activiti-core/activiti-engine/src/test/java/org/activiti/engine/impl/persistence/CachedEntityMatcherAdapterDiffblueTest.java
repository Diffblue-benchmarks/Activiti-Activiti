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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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

public class CachedEntityMatcherAdapterDiffblueTest {
  /**
   * Method under test:
   * {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)}
   */
  @Test
  public void testIsRetained() {
    // Arrange
    DeadLetterJobsByExecutionIdMatcher deadLetterJobsByExecutionIdMatcher = new DeadLetterJobsByExecutionIdMatcher();
    ArrayList<DeadLetterJobEntity> databaseEntities = new ArrayList<>();
    ArrayList<CachedEntity> cachedEntities = new ArrayList<>();

    // Act and Assert
    assertFalse(deadLetterJobsByExecutionIdMatcher.isRetained(databaseEntities, cachedEntities,
        new DeadLetterJobEntityImpl(), JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)}
   */
  @Test
  public void testIsRetained2() {
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
   * Method under test:
   * {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)}
   */
  @Test
  public void testIsRetained3() {
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
   * Method under test:
   * {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)}
   */
  @Test
  public void testIsRetained4() {
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
   * Method under test:
   * {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)}
   */
  @Test
  public void testIsRetained5() {
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
   * Method under test:
   * {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)}
   */
  @Test
  public void testIsRetained6() {
    // Arrange
    DeadLetterJobsByExecutionIdMatcher deadLetterJobsByExecutionIdMatcher = new DeadLetterJobsByExecutionIdMatcher();
    ArrayList<DeadLetterJobEntity> databaseEntities = new ArrayList<>();
    ArrayList<CachedEntity> cachedEntities = new ArrayList<>();
    DeadLetterJobEntity deadLetterJobEntity = mock(DeadLetterJobEntity.class);
    when(deadLetterJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = deadLetterJobsByExecutionIdMatcher.isRetained(databaseEntities, cachedEntities,
        deadLetterJobEntity, JSONObject.NULL);

    // Assert
    verify(deadLetterJobEntity, atLeast(1)).getExecutionId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Method under test:
   * {@link CachedEntityMatcherAdapter#isRetained(Collection, Collection, Entity, Object)}
   */
  @Test
  public void testIsRetained7() {
    // Arrange
    DeadLetterJobsByExecutionIdMatcher deadLetterJobsByExecutionIdMatcher = new DeadLetterJobsByExecutionIdMatcher();
    ArrayList<DeadLetterJobEntity> databaseEntities = new ArrayList<>();
    ArrayList<CachedEntity> cachedEntities = new ArrayList<>();
    DeadLetterJobEntity deadLetterJobEntity = mock(DeadLetterJobEntity.class);
    when(deadLetterJobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = deadLetterJobsByExecutionIdMatcher.isRetained(databaseEntities, cachedEntities,
        deadLetterJobEntity, "42");

    // Assert
    verify(deadLetterJobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualIsRetainedResult);
  }
}
