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
package org.activiti.engine.impl.persistence.entity.data.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.impl.persistence.entity.ModelEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MybatisModelDataManagerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MybatisModelDataManager#MybatisModelDataManager(ProcessEngineConfigurationImpl)}
   *   <li>{@link MybatisModelDataManager#getManagedEntityClass()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MybatisModelDataManager.<init>(ProcessEngineConfigurationImpl)",
      "Class MybatisModelDataManager.getManagedEntityClass()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends ModelEntity> actualManagedEntityClass = (new MybatisModelDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<ModelEntityImpl> expectedManagedEntityClass = ModelEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisModelDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisModelDataManager#create()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ModelEntity MybatisModelDataManager.create()"})
  public void testCreate() {
    // Arrange and Act
    ModelEntity actualCreateResult = (new MybatisModelDataManager(new JtaProcessEngineConfiguration())).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof ModelEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getEditorSourceExtraValueId());
    assertNull(actualCreateResult.getEditorSourceValueId());
    assertNull(actualCreateResult.getCategory());
    assertNull(actualCreateResult.getDeploymentId());
    assertNull(actualCreateResult.getKey());
    assertNull(actualCreateResult.getMetaInfo());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getCreateTime());
    assertNull(actualCreateResult.getLastUpdateTime());
    assertEquals(1, actualCreateResult.getVersion().intValue());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(10, ((Map<String, Integer>) persistentState).size());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertFalse(actualCreateResult.hasEditorSource());
    assertFalse(actualCreateResult.hasEditorSourceExtra());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("category"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("createTime"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("deploymentId"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("editorSourceValueId"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("metaInfo"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("name"));
  }
}
