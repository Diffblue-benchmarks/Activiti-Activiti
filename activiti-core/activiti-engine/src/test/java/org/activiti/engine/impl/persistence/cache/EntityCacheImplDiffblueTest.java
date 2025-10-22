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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.List;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.activiti.engine.impl.persistence.entity.Entity;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EntityCacheImplDiffblueTest {
  /**
   * Test {@link EntityCacheImpl#findInCache(Class, String)} with {@code entityClass}, {@code id}.
   * <ul>
   *   <li>Given {@link EntityCacheImpl} (default constructor) {@link AttachmentEntityImpl} (default constructor) is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityCacheImpl#findInCache(Class, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object EntityCacheImpl.findInCache(Class, String)"})
  public void testFindInCacheWithEntityClassId_givenEntityCacheImplAttachmentEntityImplIsTrue() {
    // Arrange
    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    entityCacheImpl.put(new AttachmentEntityImpl(), true);
    Class<Object> entityClass = Object.class;

    // Act and Assert
    assertNull(entityCacheImpl.findInCache(entityClass, "42"));
  }

  /**
   * Test {@link EntityCacheImpl#findInCache(Class, String)} with {@code entityClass}, {@code id}.
   * <ul>
   *   <li>Given {@link EntityCacheImpl} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityCacheImpl#findInCache(Class, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object EntityCacheImpl.findInCache(Class, String)"})
  public void testFindInCacheWithEntityClassId_givenEntityCacheImpl_thenReturnNull() {
    // Arrange
    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    Class<Object> entityClass = Object.class;

    // Act and Assert
    assertNull(entityCacheImpl.findInCache(entityClass, "42"));
  }

  /**
   * Test {@link EntityCacheImpl#findInCache(Class, String)} with {@code entityClass}, {@code id}.
   * <ul>
   *   <li>Then calls {@link Entity#getId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityCacheImpl#findInCache(Class, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object EntityCacheImpl.findInCache(Class, String)"})
  public void testFindInCacheWithEntityClassId_thenCallsGetId() {
    // Arrange
    Entity entity = mock(Entity.class);
    when(entity.getPersistentState()).thenReturn(JSONObject.NULL);
    when(entity.getId()).thenReturn("42");

    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    entityCacheImpl.put(entity, true);
    Class<Object> entityClass = Object.class;

    // Act
    entityCacheImpl.findInCache(entityClass, "42");

    // Assert
    verify(entity).getId();
    verify(entity).getPersistentState();
  }

  /**
   * Test {@link EntityCacheImpl#findInCache(Class, String)} with {@code entityClass}, {@code id}.
   * <ul>
   *   <li>When {@code Class}.</li>
   *   <li>Then calls {@link Entity#getId()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityCacheImpl#findInCache(Class, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object EntityCacheImpl.findInCache(Class, String)"})
  public void testFindInCacheWithEntityClassId_whenJavaLangClass_thenCallsGetId() {
    // Arrange
    Entity entity = mock(Entity.class);
    when(entity.getPersistentState()).thenReturn(JSONObject.NULL);
    when(entity.getId()).thenReturn("42");

    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    entityCacheImpl.put(entity, true);
    Class<Class> entityClass = Class.class;

    // Act
    Object actualFindInCacheResult = entityCacheImpl.findInCache(entityClass, "42");

    // Assert
    verify(entity).getId();
    verify(entity).getPersistentState();
    assertNull(actualFindInCacheResult);
  }

  /**
   * Test {@link EntityCacheImpl#findInCache(Class)} with {@code entityClass}.
   * <ul>
   *   <li>Given {@link EntityCacheImpl} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityCacheImpl#findInCache(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EntityCacheImpl.findInCache(Class)"})
  public void testFindInCacheWithEntityClass_givenEntityCacheImpl_thenReturnEmpty() {
    // Arrange
    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    Class<Object> entityClass = Object.class;

    // Act and Assert
    assertTrue(entityCacheImpl.findInCache(entityClass).isEmpty());
  }

  /**
   * Test {@link EntityCacheImpl#findInCache(Class)} with {@code entityClass}.
   * <ul>
   *   <li>When {@code Class}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityCacheImpl#findInCache(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EntityCacheImpl.findInCache(Class)"})
  public void testFindInCacheWithEntityClass_whenJavaLangClass_thenReturnEmpty() {
    // Arrange
    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    entityCacheImpl.put(new AttachmentEntityImpl(), true);
    Class<Class> entityClass = Class.class;

    // Act and Assert
    assertTrue(entityCacheImpl.findInCache(entityClass).isEmpty());
  }

  /**
   * Test {@link EntityCacheImpl#findInCache(Class)} with {@code entityClass}.
   * <ul>
   *   <li>When {@code Object}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityCacheImpl#findInCache(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List EntityCacheImpl.findInCache(Class)"})
  public void testFindInCacheWithEntityClass_whenJavaLangObject_thenReturnSizeIsOne() {
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
   * Test {@link EntityCacheImpl#findClassCacheByCheckingSubclasses(Class)}.
   * <ul>
   *   <li>Given {@link EntityCacheImpl} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityCacheImpl#findClassCacheByCheckingSubclasses(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map EntityCacheImpl.findClassCacheByCheckingSubclasses(Class)"})
  public void testFindClassCacheByCheckingSubclasses_givenEntityCacheImpl_thenReturnNull() {
    // Arrange
    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    Class<Object> entityClass = Object.class;

    // Act and Assert
    assertNull(entityCacheImpl.findClassCacheByCheckingSubclasses(entityClass));
  }

  /**
   * Test {@link EntityCacheImpl#findClassCacheByCheckingSubclasses(Class)}.
   * <ul>
   *   <li>When {@code Class}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityCacheImpl#findClassCacheByCheckingSubclasses(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map EntityCacheImpl.findClassCacheByCheckingSubclasses(Class)"})
  public void testFindClassCacheByCheckingSubclasses_whenJavaLangClass_thenReturnNull() {
    // Arrange
    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    entityCacheImpl.put(new AttachmentEntityImpl(), true);
    Class<Class> entityClass = Class.class;

    // Act and Assert
    assertNull(entityCacheImpl.findClassCacheByCheckingSubclasses(entityClass));
  }

  /**
   * Test {@link EntityCacheImpl#findClassCacheByCheckingSubclasses(Class)}.
   * <ul>
   *   <li>When {@code Object}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link EntityCacheImpl#findClassCacheByCheckingSubclasses(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Map EntityCacheImpl.findClassCacheByCheckingSubclasses(Class)"})
  public void testFindClassCacheByCheckingSubclasses_whenJavaLangObject_thenReturnSizeIsOne() {
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
    Entity entity2 = getResult.getEntity();
    assertTrue(entity2 instanceof AttachmentEntityImpl);
    assertEquals(2, ((Map<String, Object>) originalPersistentState).size());
    assertFalse(getResult.hasChanged());
    assertTrue(((Map<String, Object>) originalPersistentState).containsKey("description"));
    assertTrue(((Map<String, Object>) originalPersistentState).containsKey("name"));
    assertSame(entity, entity2);
  }

  /**
   * Test {@link EntityCacheImpl#findInCacheAsCachedObjects(Class)}.
   * <p>
   * Method under test: {@link EntityCacheImpl#findInCacheAsCachedObjects(Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Collection EntityCacheImpl.findInCacheAsCachedObjects(Class)"})
  public void testFindInCacheAsCachedObjects() {
    // Arrange
    EntityCacheImpl entityCacheImpl = new EntityCacheImpl();
    Class<Object> entityClass = Object.class;

    // Act and Assert
    assertNull(entityCacheImpl.findInCacheAsCachedObjects(entityClass));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link EntityCacheImpl}
   *   <li>{@link EntityCacheImpl#close()}
   *   <li>{@link EntityCacheImpl#flush()}
   *   <li>{@link EntityCacheImpl#getAllCachedEntities()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EntityCacheImpl.<init>()", "void EntityCacheImpl.close()", "void EntityCacheImpl.flush()",
      "Map EntityCacheImpl.getAllCachedEntities()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    EntityCacheImpl actualEntityCacheImpl = new EntityCacheImpl();
    actualEntityCacheImpl.close();
    actualEntityCacheImpl.flush();

    // Assert
    assertTrue(actualEntityCacheImpl.getAllCachedEntities().isEmpty());
  }
}
