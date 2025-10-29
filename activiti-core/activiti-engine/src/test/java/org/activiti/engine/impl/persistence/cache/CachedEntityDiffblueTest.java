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
package org.activiti.engine.impl.persistence.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class CachedEntityDiffblueTest {
  /**
   * Method under test: {@link CachedEntity#hasChanged()}
   */
  @Test
  public void testHasChanged() {
    // Arrange, Act and Assert
    assertFalse((new CachedEntity(new AttachmentEntityImpl(), true)).hasChanged());
  }

  /**
   * Method under test: {@link CachedEntity#hasChanged()}
   */
  @Test
  public void testHasChanged2() {
    // Arrange
    EventLogEntryEntityImpl entity = new EventLogEntryEntityImpl();
    entity.setData(new byte[]{'A', 1, 'A', 1, 'A', 1, 'A', 1});
    entity.setDeleted(true);
    entity.setExecutionId("42");
    entity.setId("42");
    entity.setInserted(true);
    entity.setLockOwner("name");
    entity.setLockTime("name");
    entity.setLogNumber(1L);
    entity.setProcessDefinitionId("42");
    entity.setProcessInstanceId("42");
    entity.setProcessed(1);
    entity.setTaskId("42");
    entity.setTimeStamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    entity.setType("name");
    entity.setUpdated(true);
    entity.setUserId("42");

    // Act and Assert
    assertFalse((new CachedEntity(entity, true)).hasChanged());
  }

  /**
   * Method under test: {@link CachedEntity#hasChanged()}
   */
  @Test
  public void testHasChanged3() {
    // Arrange
    Entity entity = mock(Entity.class);
    when(entity.getPersistentState()).thenReturn(JSONObject.NULL);

    // Act
    boolean actualHasChangedResult = (new CachedEntity(entity, true)).hasChanged();

    // Assert
    verify(entity, atLeast(1)).getPersistentState();
    assertFalse(actualHasChangedResult);
  }

  /**
   * Method under test: {@link CachedEntity#hasChanged()}
   */
  @Test
  public void testHasChanged4() {
    // Arrange
    Entity entity = mock(Entity.class);
    when(entity.getPersistentState()).thenReturn(JSONObject.NULL);

    CachedEntity cachedEntity = new CachedEntity(entity, true);
    cachedEntity.setEntity(new AttachmentEntityImpl());

    // Act
    boolean actualHasChangedResult = cachedEntity.hasChanged();

    // Assert
    verify(entity).getPersistentState();
    assertTrue(actualHasChangedResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CachedEntity#setEntity(Entity)}
   *   <li>{@link CachedEntity#setOriginalPersistentState(Object)}
   *   <li>{@link CachedEntity#getEntity()}
   *   <li>{@link CachedEntity#getOriginalPersistentState()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    CachedEntity cachedEntity = new CachedEntity(new AttachmentEntityImpl(), true);
    AttachmentEntityImpl entity = new AttachmentEntityImpl();

    // Act
    cachedEntity.setEntity(entity);
    Object object = JSONObject.NULL;
    cachedEntity.setOriginalPersistentState(object);
    Entity actualEntity = cachedEntity.getEntity();

    // Assert that nothing has changed
    assertSame(entity, actualEntity);
    assertSame(object, cachedEntity.getOriginalPersistentState());
  }

  /**
   * Method under test: {@link CachedEntity#CachedEntity(Entity, boolean)}
   */
  @Test
  public void testNewCachedEntity() {
    // Arrange
    AttachmentEntityImpl entity = new AttachmentEntityImpl();

    // Act
    CachedEntity actualCachedEntity = new CachedEntity(entity, true);

    // Assert
    Object originalPersistentState = actualCachedEntity.getOriginalPersistentState();
    assertTrue(originalPersistentState instanceof Map);
    assertEquals(2, ((Map<String, Object>) originalPersistentState).size());
    assertNull(((Map<String, Object>) originalPersistentState).get("description"));
    assertNull(((Map<String, Object>) originalPersistentState).get("name"));
    assertFalse(actualCachedEntity.hasChanged());
    assertSame(entity, actualCachedEntity.getEntity());
  }

  /**
   * Method under test: {@link CachedEntity#CachedEntity(Entity, boolean)}
   */
  @Test
  public void testNewCachedEntity2() {
    // Arrange
    AttachmentEntityImpl entity = new AttachmentEntityImpl();

    // Act
    CachedEntity actualCachedEntity = new CachedEntity(entity, false);

    // Assert
    assertNull(actualCachedEntity.getOriginalPersistentState());
    assertTrue(actualCachedEntity.hasChanged());
    assertSame(entity, actualCachedEntity.getEntity());
  }

  /**
   * Method under test: {@link CachedEntity#CachedEntity(Entity, boolean)}
   */
  @Test
  public void testNewCachedEntity3() {
    // Arrange
    Entity entity = mock(Entity.class);
    when(entity.getPersistentState()).thenReturn(JSONObject.NULL);

    // Act
    CachedEntity actualCachedEntity = new CachedEntity(entity, true);

    // Assert
    verify(entity).getPersistentState();
    assertFalse(actualCachedEntity.hasChanged());
    assertSame(entity, actualCachedEntity.getEntity());
  }
}
