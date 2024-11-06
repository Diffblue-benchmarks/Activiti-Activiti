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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import org.junit.Test;

public class ModelEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ModelEntityImpl}
   *   <li>{@link ModelEntityImpl#setCategory(String)}
   *   <li>{@link ModelEntityImpl#setCreateTime(Date)}
   *   <li>{@link ModelEntityImpl#setDeploymentId(String)}
   *   <li>{@link ModelEntityImpl#setEditorSourceExtraValueId(String)}
   *   <li>{@link ModelEntityImpl#setEditorSourceValueId(String)}
   *   <li>{@link ModelEntityImpl#setKey(String)}
   *   <li>{@link ModelEntityImpl#setLastUpdateTime(Date)}
   *   <li>{@link ModelEntityImpl#setMetaInfo(String)}
   *   <li>{@link ModelEntityImpl#setName(String)}
   *   <li>{@link ModelEntityImpl#setTenantId(String)}
   *   <li>{@link ModelEntityImpl#setVersion(Integer)}
   *   <li>{@link ModelEntityImpl#getCategory()}
   *   <li>{@link ModelEntityImpl#getCreateTime()}
   *   <li>{@link ModelEntityImpl#getDeploymentId()}
   *   <li>{@link ModelEntityImpl#getEditorSourceExtraValueId()}
   *   <li>{@link ModelEntityImpl#getEditorSourceValueId()}
   *   <li>{@link ModelEntityImpl#getKey()}
   *   <li>{@link ModelEntityImpl#getLastUpdateTime()}
   *   <li>{@link ModelEntityImpl#getMetaInfo()}
   *   <li>{@link ModelEntityImpl#getName()}
   *   <li>{@link ModelEntityImpl#getTenantId()}
   *   <li>{@link ModelEntityImpl#getVersion()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ModelEntityImpl actualModelEntityImpl = new ModelEntityImpl();
    actualModelEntityImpl.setCategory("Category");
    Date createTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualModelEntityImpl.setCreateTime(createTime);
    actualModelEntityImpl.setDeploymentId("42");
    actualModelEntityImpl.setEditorSourceExtraValueId("42");
    actualModelEntityImpl.setEditorSourceValueId("42");
    actualModelEntityImpl.setKey("Key");
    Date lastUpdateTime = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualModelEntityImpl.setLastUpdateTime(lastUpdateTime);
    actualModelEntityImpl.setMetaInfo("Meta Info");
    actualModelEntityImpl.setName("Name");
    actualModelEntityImpl.setTenantId("42");
    actualModelEntityImpl.setVersion(1);
    String actualCategory = actualModelEntityImpl.getCategory();
    Date actualCreateTime = actualModelEntityImpl.getCreateTime();
    String actualDeploymentId = actualModelEntityImpl.getDeploymentId();
    String actualEditorSourceExtraValueId = actualModelEntityImpl.getEditorSourceExtraValueId();
    String actualEditorSourceValueId = actualModelEntityImpl.getEditorSourceValueId();
    String actualKey = actualModelEntityImpl.getKey();
    Date actualLastUpdateTime = actualModelEntityImpl.getLastUpdateTime();
    String actualMetaInfo = actualModelEntityImpl.getMetaInfo();
    String actualName = actualModelEntityImpl.getName();
    String actualTenantId = actualModelEntityImpl.getTenantId();

    // Assert that nothing has changed
    assertEquals("42", actualDeploymentId);
    assertEquals("42", actualEditorSourceExtraValueId);
    assertEquals("42", actualEditorSourceValueId);
    assertEquals("42", actualTenantId);
    assertEquals("Category", actualCategory);
    assertEquals("Key", actualKey);
    assertEquals("Meta Info", actualMetaInfo);
    assertEquals("Name", actualName);
    assertEquals(1, actualModelEntityImpl.getVersion().intValue());
    assertEquals(1, actualModelEntityImpl.getRevision());
    assertFalse(actualModelEntityImpl.isDeleted());
    assertFalse(actualModelEntityImpl.isInserted());
    assertFalse(actualModelEntityImpl.isUpdated());
    assertSame(createTime, actualCreateTime);
    assertSame(lastUpdateTime, actualLastUpdateTime);
  }

  /**
   * Test {@link ModelEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given {@link ModelEntityImpl} (default constructor).</li>
   *   <li>Then return {@code createTime} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_givenModelEntityImpl_thenReturnCreateTimeIsNull() {
    // Arrange and Act
    Object actualPersistentState = (new ModelEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(10, ((Map<String, Integer>) actualPersistentState).size());
    assertNull(((Map<String, Integer>) actualPersistentState).get("category"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("createTime"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("deploymentId"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("editorSourceExtraValueId"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("editorSourceValueId"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("key"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("lastUpdateTime"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("metaInfo"));
    assertNull(((Map<String, Integer>) actualPersistentState).get("name"));
    assertEquals(1, ((Map<String, Integer>) actualPersistentState).get("version").intValue());
  }

  /**
   * Test {@link ModelEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Then return containsKey {@code createTime}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_thenReturnContainsKeyCreateTime() {
    // Arrange
    ModelEntityImpl modelEntityImpl = new ModelEntityImpl();
    modelEntityImpl.setCreateTime(mock(java.sql.Date.class));

    // Act
    Object actualPersistentState = modelEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertEquals(10, ((Map<String, Object>) actualPersistentState).size());
    assertNull(((Map<String, Object>) actualPersistentState).get("category"));
    assertNull(((Map<String, Object>) actualPersistentState).get("deploymentId"));
    assertNull(((Map<String, Object>) actualPersistentState).get("editorSourceExtraValueId"));
    assertNull(((Map<String, Object>) actualPersistentState).get("editorSourceValueId"));
    assertNull(((Map<String, Object>) actualPersistentState).get("key"));
    assertNull(((Map<String, Object>) actualPersistentState).get("lastUpdateTime"));
    assertNull(((Map<String, Object>) actualPersistentState).get("metaInfo"));
    assertNull(((Map<String, Object>) actualPersistentState).get("name"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("createTime"));
    assertTrue(((Map<String, Object>) actualPersistentState).containsKey("version"));
  }

  /**
   * Test {@link ModelEntityImpl#hasEditorSource()}.
   * <ul>
   *   <li>Given {@link ModelEntityImpl} (default constructor) CreateTime is
   * {@link Date}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityImpl#hasEditorSource()}
   */
  @Test
  public void testHasEditorSource_givenModelEntityImplCreateTimeIsDate_thenReturnFalse() {
    // Arrange
    ModelEntityImpl modelEntityImpl = new ModelEntityImpl();
    modelEntityImpl.setCreateTime(mock(java.sql.Date.class));

    // Act and Assert
    assertFalse(modelEntityImpl.hasEditorSource());
  }

  /**
   * Test {@link ModelEntityImpl#hasEditorSource()}.
   * <ul>
   *   <li>Given {@link ModelEntityImpl} (default constructor) EditorSourceValueId
   * is {@code foo}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityImpl#hasEditorSource()}
   */
  @Test
  public void testHasEditorSource_givenModelEntityImplEditorSourceValueIdIsFoo_thenReturnTrue() {
    // Arrange
    ModelEntityImpl modelEntityImpl = new ModelEntityImpl();
    modelEntityImpl.setEditorSourceValueId("foo");

    // Act and Assert
    assertTrue(modelEntityImpl.hasEditorSource());
  }

  /**
   * Test {@link ModelEntityImpl#hasEditorSource()}.
   * <ul>
   *   <li>Given {@link ModelEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityImpl#hasEditorSource()}
   */
  @Test
  public void testHasEditorSource_givenModelEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ModelEntityImpl()).hasEditorSource());
  }

  /**
   * Test {@link ModelEntityImpl#hasEditorSourceExtra()}.
   * <ul>
   *   <li>Given {@link ModelEntityImpl} (default constructor) CreateTime is
   * {@link Date}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityImpl#hasEditorSourceExtra()}
   */
  @Test
  public void testHasEditorSourceExtra_givenModelEntityImplCreateTimeIsDate_thenReturnFalse() {
    // Arrange
    ModelEntityImpl modelEntityImpl = new ModelEntityImpl();
    modelEntityImpl.setCreateTime(mock(java.sql.Date.class));

    // Act and Assert
    assertFalse(modelEntityImpl.hasEditorSourceExtra());
  }

  /**
   * Test {@link ModelEntityImpl#hasEditorSourceExtra()}.
   * <ul>
   *   <li>Given {@link ModelEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityImpl#hasEditorSourceExtra()}
   */
  @Test
  public void testHasEditorSourceExtra_givenModelEntityImpl_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ModelEntityImpl()).hasEditorSourceExtra());
  }

  /**
   * Test {@link ModelEntityImpl#hasEditorSourceExtra()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ModelEntityImpl#hasEditorSourceExtra()}
   */
  @Test
  public void testHasEditorSourceExtra_thenReturnTrue() {
    // Arrange
    ModelEntityImpl modelEntityImpl = new ModelEntityImpl();
    modelEntityImpl.setEditorSourceExtraValueId("foo");

    // Act and Assert
    assertTrue(modelEntityImpl.hasEditorSourceExtra());
  }
}
