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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CachedEntityDiffblueTest {
  /**
   * Test {@link CachedEntity#CachedEntity(Entity, boolean)}.
   *
   * <ul>
   *   <li>Then OriginalPersistentState return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link CachedEntity#CachedEntity(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CachedEntity.<init>(Entity, boolean)"})
  public void testNewCachedEntity_thenOriginalPersistentStateReturnMap() {
    // Arrange and Act
    CachedEntity actualCachedEntity = new CachedEntity(new AttachmentEntityImpl(), true);

    // Assert
    Object originalPersistentState = actualCachedEntity.getOriginalPersistentState();
    assertTrue(originalPersistentState instanceof Map);
    Entity entity = actualCachedEntity.getEntity();
    assertTrue(entity instanceof AttachmentEntityImpl);
    assertEquals(2, ((Map<String, Object>) originalPersistentState).size());
    assertNull(((Map<String, Object>) originalPersistentState).get("description"));
    assertNull(((Map<String, Object>) originalPersistentState).get("name"));
    assertFalse(actualCachedEntity.hasChanged());
    assertEquals(originalPersistentState, entity.getPersistentState());
  }

  /**
   * Test {@link CachedEntity#CachedEntity(Entity, boolean)}.
   *
   * <ul>
   *   <li>When {@code false}.
   *   <li>Then Entity PersistentState return {@link Map}.
   * </ul>
   *
   * <p>Method under test: {@link CachedEntity#CachedEntity(Entity, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CachedEntity.<init>(Entity, boolean)"})
  public void testNewCachedEntity_whenFalse_thenEntityPersistentStateReturnMap() {
    // Arrange and Act
    CachedEntity actualCachedEntity = new CachedEntity(new AttachmentEntityImpl(), false);

    // Assert
    Entity entity = actualCachedEntity.getEntity();
    Object persistentState = entity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(entity instanceof AttachmentEntityImpl);
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("description"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(actualCachedEntity.getOriginalPersistentState());
    assertTrue(actualCachedEntity.hasChanged());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CachedEntity#setEntity(Entity)}
   *   <li>{@link CachedEntity#setOriginalPersistentState(Object)}
   *   <li>{@link CachedEntity#getEntity()}
   *   <li>{@link CachedEntity#getOriginalPersistentState()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Entity CachedEntity.getEntity()",
    "Object CachedEntity.getOriginalPersistentState()",
    "void CachedEntity.setEntity(Entity)",
    "void CachedEntity.setOriginalPersistentState(Object)"
  })
  public void testGettersAndSetters() {
    // Arrange
    CachedEntity cachedEntity = new CachedEntity(new AttachmentEntityImpl(), true);
    AttachmentEntityImpl entity = new AttachmentEntityImpl();

    // Act
    cachedEntity.setEntity(entity);
    Object object = JSONObject.NULL;
    cachedEntity.setOriginalPersistentState(object);
    Entity actualEntity = cachedEntity.getEntity();

    // Assert
    assertSame(entity, actualEntity);
    assertSame(object, cachedEntity.getOriginalPersistentState());
  }

  /**
   * Test {@link CachedEntity#hasChanged()}.
   *
   * <p>Method under test: {@link CachedEntity#hasChanged()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean CachedEntity.hasChanged()"})
  public void testHasChanged() {
    // Arrange, Act and Assert
    assertFalse(new CachedEntity(new AttachmentEntityImpl(), true).hasChanged());
  }

  /**
   * Test {@link CachedEntity#hasChanged()}.
   *
   * <ul>
   *   <li>Given {@code A}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link CachedEntity#hasChanged()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean CachedEntity.hasChanged()"})
  public void testHasChanged_givenA_thenReturnFalse() {
    // Arrange
    EventLogEntryEntityImpl entity = new EventLogEntryEntityImpl();
    entity.setData(new byte[] {'A', 1, 'A', 1, 'A', 1, 'A', 1});
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
    entity.setTimeStamp(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    entity.setType("name");
    entity.setUpdated(true);
    entity.setUserId("42");

    // Act and Assert
    assertFalse(new CachedEntity(entity, true).hasChanged());
  }

  /**
   * Test {@link CachedEntity#hasChanged()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link CachedEntity#hasChanged()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean CachedEntity.hasChanged()"})
  public void testHasChanged_thenReturnTrue() {
    // Arrange
    CachedEntity cachedEntity = new CachedEntity(new AttachmentEntityImpl(), true);
    cachedEntity.setOriginalPersistentState(JSONObject.NULL);

    // Act and Assert
    assertTrue(cachedEntity.hasChanged());
  }
}
