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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EntityCacheImplDiffblueTest {
  @InjectMocks
  private EntityCacheImpl entityCacheImpl;

  /**
   * Method under test: {@link EntityCacheImpl#findInCache(Class)}
   */
  @Test
  public void testFindInCache() {
    // Arrange
    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    Class<Object> entityClass = Object.class;

    // Act and Assert
    assertTrue(entityCacheImpl.findInCache(entityClass).isEmpty());
  }

  /**
   * Method under test: {@link EntityCacheImpl#findInCache(Class)}
   */
  @Test
  public void testFindInCache2() {
    // Arrange
    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    AttachmentEntityImpl entity = new AttachmentEntityImpl();
    entityCacheImpl.put(entity, true);
    Class<Object> entityClass = Object.class;

    // Act
    List<Object> actualFindInCacheResult = entityCacheImpl.findInCache(entityClass);

    // Assert
    assertEquals(1, actualFindInCacheResult.size());
    assertSame(entity, actualFindInCacheResult.get(0));
  }

  /**
   * Method under test: {@link EntityCacheImpl#findInCache(Class)}
   */
  @Test
  public void testFindInCache3() {
    // Arrange
    Entity entity = mock(Entity.class);
    when(entity.getPersistentState()).thenReturn(JSONObject.NULL);
    when(entity.getId()).thenReturn("42");

    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    entityCacheImpl.put(entity, true);
    Class<Object> entityClass = Object.class;

    // Act
    List<Object> actualFindInCacheResult = entityCacheImpl.findInCache(entityClass);

    // Assert
    verify(entity).getId();
    verify(entity).getPersistentState();
    assertEquals(1, actualFindInCacheResult.size());
  }

  /**
   * Method under test: {@link EntityCacheImpl#findInCache(Class, String)}
   */
  @Test
  public void testFindInCache4() {
    // Arrange
    Class<Object> entityClass = Object.class;

    // Act and Assert
    assertNull(entityCacheImpl.findInCache(entityClass, "42"));
  }

  /**
   * Method under test:
   * {@link EntityCacheImpl#findClassCacheByCheckingSubclasses(Class)}
   */
  @Test
  public void testFindClassCacheByCheckingSubclasses() {
    // Arrange
    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    Class<Object> entityClass = Object.class;

    // Act and Assert
    assertNull(entityCacheImpl.findClassCacheByCheckingSubclasses(entityClass));
  }

  /**
   * Method under test:
   * {@link EntityCacheImpl#findClassCacheByCheckingSubclasses(Class)}
   */
  @Test
  public void testFindClassCacheByCheckingSubclasses2() {
    // Arrange
    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    AttachmentEntityImpl entity = new AttachmentEntityImpl();
    entityCacheImpl.put(entity, true);
    Class<Object> entityClass = Object.class;

    // Act
    Map<String, CachedEntity> actualFindClassCacheByCheckingSubclassesResult = entityCacheImpl
        .findClassCacheByCheckingSubclasses(entityClass);

    // Assert
    assertEquals(1, actualFindClassCacheByCheckingSubclassesResult.size());
    CachedEntity getResult = actualFindClassCacheByCheckingSubclassesResult.get(null);
    Object originalPersistentState = getResult.getOriginalPersistentState();
    assertTrue(originalPersistentState instanceof Map);
    assertEquals(2, ((Map<String, Object>) originalPersistentState).size());
    assertNull(((Map<String, Object>) originalPersistentState).get("description"));
    assertNull(((Map<String, Object>) originalPersistentState).get("name"));
    assertFalse(getResult.hasChanged());
    assertSame(entity, getResult.getEntity());
  }

  /**
   * Method under test:
   * {@link EntityCacheImpl#findClassCacheByCheckingSubclasses(Class)}
   */
  @Test
  public void testFindClassCacheByCheckingSubclasses3() {
    // Arrange
    Entity entity = mock(Entity.class);
    when(entity.getPersistentState()).thenReturn(JSONObject.NULL);
    when(entity.getId()).thenReturn("42");

    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    entityCacheImpl.put(entity, true);
    Class<Object> entityClass = Object.class;

    // Act
    Map<String, CachedEntity> actualFindClassCacheByCheckingSubclassesResult = entityCacheImpl
        .findClassCacheByCheckingSubclasses(entityClass);

    // Assert
    verify(entity).getId();
    verify(entity).getPersistentState();
    assertEquals(1, actualFindClassCacheByCheckingSubclassesResult.size());
    assertFalse(actualFindClassCacheByCheckingSubclassesResult.get("42").hasChanged());
  }

  /**
   * Method under test:
   * {@link EntityCacheImpl#findClassCacheByCheckingSubclasses(Class)}
   */
  @Test
  public void testFindClassCacheByCheckingSubclasses4() {
    // Arrange
    Entity entity = mock(Entity.class);
    when(entity.getPersistentState()).thenReturn(JSONObject.NULL);
    when(entity.getId()).thenReturn("42");

    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    entityCacheImpl.put(entity, true);
    Class<Class> entityClass = Class.class;

    // Act
    Map<String, CachedEntity> actualFindClassCacheByCheckingSubclassesResult = entityCacheImpl
        .findClassCacheByCheckingSubclasses(entityClass);

    // Assert
    verify(entity).getId();
    verify(entity).getPersistentState();
    assertNull(actualFindClassCacheByCheckingSubclassesResult);
  }

  /**
   * Method under test: {@link EntityCacheImpl#cacheRemove(Class, String)}
   */
  @Test
  public void testCacheRemove() {
    // Arrange
    AttachmentEntityImpl entity = mock(AttachmentEntityImpl.class);
    when(entity.getPersistentState()).thenReturn(JSONObject.NULL);
    when(entity.getId()).thenReturn("42");

    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    entityCacheImpl.put(entity, true);
    Class<Object> entityClass = Object.class;

    // Act
    entityCacheImpl.cacheRemove(entityClass, "42");

    // Assert that nothing has changed
    verify(entity).getId();
    verify(entity).getPersistentState();
  }

  /**
   * Method under test: {@link EntityCacheImpl#findInCacheAsCachedObjects(Class)}
   */
  @Test
  public void testFindInCacheAsCachedObjects() {
    // Arrange
    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    Class<Object> entityClass = Object.class;

    // Act and Assert
    assertNull(entityCacheImpl.findInCacheAsCachedObjects(entityClass));
  }

  /**
   * Method under test: {@link EntityCacheImpl#findInCacheAsCachedObjects(Class)}
   */
  @Test
  public void testFindInCacheAsCachedObjects2() {
    // Arrange
    Entity entity = mock(Entity.class);
    when(entity.getPersistentState()).thenReturn(JSONObject.NULL);
    when(entity.getId()).thenReturn("42");

    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    entityCacheImpl.put(entity, true);
    Class<Object> entityClass = Object.class;

    // Act
    Collection<CachedEntity> actualFindInCacheAsCachedObjectsResult = entityCacheImpl
        .findInCacheAsCachedObjects(entityClass);

    // Assert
    verify(entity).getId();
    verify(entity).getPersistentState();
    assertNull(actualFindInCacheAsCachedObjectsResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link EntityCacheImpl}
   *   <li>{@link EntityCacheImpl#close()}
   *   <li>{@link EntityCacheImpl#flush()}
   *   <li>{@link EntityCacheImpl#getAllCachedEntities()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    EntityCacheImpl actualEntityCacheImpl = new EntityCacheImpl();
    actualEntityCacheImpl.close();
    actualEntityCacheImpl.flush();

    // Assert that nothing has changed
    assertTrue(actualEntityCacheImpl.getAllCachedEntities().isEmpty());
  }
}
