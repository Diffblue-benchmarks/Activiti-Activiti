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
import static org.mockito.Mockito.mock;
import java.util.Map;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntityImpl;
import org.junit.Test;

public class MybatisProcessDefinitionInfoDataManagerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MybatisProcessDefinitionInfoDataManager#MybatisProcessDefinitionInfoDataManager(ProcessEngineConfigurationImpl)}
   *   <li>{@link MybatisProcessDefinitionInfoDataManager#getManagedEntityClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends ProcessDefinitionInfoEntity> actualManagedEntityClass = (new MybatisProcessDefinitionInfoDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<ProcessDefinitionInfoEntityImpl> expectedManagedEntityClass = ProcessDefinitionInfoEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisProcessDefinitionInfoDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisProcessDefinitionInfoDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange and Act
    ProcessDefinitionInfoEntity actualCreateResult = (new MybatisProcessDefinitionInfoDataManager(
        new JtaProcessEngineConfiguration())).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof ProcessDefinitionInfoEntityImpl);
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("infoJsonId"));
    assertNull(((Map<String, Object>) persistentState).get("processDefinitionId"));
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getInfoJsonId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
  }

  /**
   * Test {@link MybatisProcessDefinitionInfoDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisProcessDefinitionInfoDataManager#create()}
   */
  @Test
  public void testCreate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessDefinitionInfoEntity actualCreateResult = (new MybatisProcessDefinitionInfoDataManager(
        processEngineConfiguration)).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof ProcessDefinitionInfoEntityImpl);
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("infoJsonId"));
    assertNull(((Map<String, Object>) persistentState).get("processDefinitionId"));
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getInfoJsonId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
  }
}
