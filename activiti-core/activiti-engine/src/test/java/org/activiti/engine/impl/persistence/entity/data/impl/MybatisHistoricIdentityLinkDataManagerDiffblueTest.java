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
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.HistoricIdentityLinksByProcInstMatcher;
import org.junit.Test;

public class MybatisHistoricIdentityLinkDataManagerDiffblueTest {
  /**
   * Test
   * {@link MybatisHistoricIdentityLinkDataManager#MybatisHistoricIdentityLinkDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisHistoricIdentityLinkDataManager#MybatisHistoricIdentityLinkDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisHistoricIdentityLinkDataManager_givenCustomFunctionProvider() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    MybatisHistoricIdentityLinkDataManager actualMybatisHistoricIdentityLinkDataManager = new MybatisHistoricIdentityLinkDataManager(
        processEngineConfiguration);

    // Assert
    assertTrue(
        actualMybatisHistoricIdentityLinkDataManager.historicIdentityLinksByProcInstMatcher instanceof HistoricIdentityLinksByProcInstMatcher);
    assertNull(actualMybatisHistoricIdentityLinkDataManager.getManagedEntitySubClasses());
    Class<HistoricIdentityLinkEntityImpl> expectedManagedEntityClass = HistoricIdentityLinkEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisHistoricIdentityLinkDataManager.getManagedEntityClass());
  }

  /**
   * Test
   * {@link MybatisHistoricIdentityLinkDataManager#MybatisHistoricIdentityLinkDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisHistoricIdentityLinkDataManager#MybatisHistoricIdentityLinkDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisHistoricIdentityLinkDataManager_whenJtaProcessEngineConfiguration() {
    // Arrange and Act
    MybatisHistoricIdentityLinkDataManager actualMybatisHistoricIdentityLinkDataManager = new MybatisHistoricIdentityLinkDataManager(
        new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(
        actualMybatisHistoricIdentityLinkDataManager.historicIdentityLinksByProcInstMatcher instanceof HistoricIdentityLinksByProcInstMatcher);
    assertNull(actualMybatisHistoricIdentityLinkDataManager.getManagedEntitySubClasses());
    Class<HistoricIdentityLinkEntityImpl> expectedManagedEntityClass = HistoricIdentityLinkEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisHistoricIdentityLinkDataManager.getManagedEntityClass());
  }

  /**
   * Test {@link MybatisHistoricIdentityLinkDataManager#getManagedEntityClass()}.
   * <p>
   * Method under test:
   * {@link MybatisHistoricIdentityLinkDataManager#getManagedEntityClass()}
   */
  @Test
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends HistoricIdentityLinkEntity> actualManagedEntityClass = (new MybatisHistoricIdentityLinkDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<HistoricIdentityLinkEntityImpl> expectedManagedEntityClass = HistoricIdentityLinkEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisHistoricIdentityLinkDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisHistoricIdentityLinkDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange and Act
    HistoricIdentityLinkEntity actualCreateResult = (new MybatisHistoricIdentityLinkDataManager(
        new JtaProcessEngineConfiguration())).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricIdentityLinkEntityImpl);
    assertNull(actualCreateResult.getDetails());
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("id"));
    assertNull(((Map<String, Object>) persistentState).get("type"));
    assertNull(actualCreateResult.getGroupId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getType());
    assertNull(actualCreateResult.getUserId());
    assertNull(actualCreateResult.getId());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertFalse(actualCreateResult.isGroup());
    assertFalse(actualCreateResult.isUser());
  }

  /**
   * Test {@link MybatisHistoricIdentityLinkDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisHistoricIdentityLinkDataManager#create()}
   */
  @Test
  public void testCreate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoricIdentityLinkEntity actualCreateResult = (new MybatisHistoricIdentityLinkDataManager(
        processEngineConfiguration)).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricIdentityLinkEntityImpl);
    assertNull(actualCreateResult.getDetails());
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("id"));
    assertNull(((Map<String, Object>) persistentState).get("type"));
    assertNull(actualCreateResult.getGroupId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getType());
    assertNull(actualCreateResult.getUserId());
    assertNull(actualCreateResult.getId());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertFalse(actualCreateResult.isGroup());
    assertFalse(actualCreateResult.isUser());
  }
}
