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
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.IdentityLinksByProcInstMatcher;
import org.junit.Test;

public class MybatisIdentityLinkDataManagerDiffblueTest {
  /**
   * Test
   * {@link MybatisIdentityLinkDataManager#MybatisIdentityLinkDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisIdentityLinkDataManager#MybatisIdentityLinkDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisIdentityLinkDataManager_givenCustomFunctionProvider() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    MybatisIdentityLinkDataManager actualMybatisIdentityLinkDataManager = new MybatisIdentityLinkDataManager(
        processEngineConfiguration);

    // Assert
    assertTrue(
        actualMybatisIdentityLinkDataManager.identityLinkByProcessInstanceMatcher instanceof IdentityLinksByProcInstMatcher);
    assertNull(actualMybatisIdentityLinkDataManager.getManagedEntitySubClasses());
    Class<IdentityLinkEntityImpl> expectedManagedEntityClass = IdentityLinkEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisIdentityLinkDataManager.getManagedEntityClass());
  }

  /**
   * Test
   * {@link MybatisIdentityLinkDataManager#MybatisIdentityLinkDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisIdentityLinkDataManager#MybatisIdentityLinkDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisIdentityLinkDataManager_whenJtaProcessEngineConfiguration() {
    // Arrange and Act
    MybatisIdentityLinkDataManager actualMybatisIdentityLinkDataManager = new MybatisIdentityLinkDataManager(
        new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(
        actualMybatisIdentityLinkDataManager.identityLinkByProcessInstanceMatcher instanceof IdentityLinksByProcInstMatcher);
    assertNull(actualMybatisIdentityLinkDataManager.getManagedEntitySubClasses());
    Class<IdentityLinkEntityImpl> expectedManagedEntityClass = IdentityLinkEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisIdentityLinkDataManager.getManagedEntityClass());
  }

  /**
   * Test {@link MybatisIdentityLinkDataManager#getManagedEntityClass()}.
   * <p>
   * Method under test:
   * {@link MybatisIdentityLinkDataManager#getManagedEntityClass()}
   */
  @Test
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends IdentityLinkEntity> actualManagedEntityClass = (new MybatisIdentityLinkDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<IdentityLinkEntityImpl> expectedManagedEntityClass = IdentityLinkEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisIdentityLinkDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisIdentityLinkDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange and Act
    IdentityLinkEntity actualCreateResult = (new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()))
        .create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof IdentityLinkEntityImpl);
    assertNull(actualCreateResult.getDetails());
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("id"));
    assertNull(((Map<String, Object>) persistentState).get("type"));
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getProcessDefId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getGroupId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getType());
    assertNull(actualCreateResult.getUserId());
    assertNull(actualCreateResult.getProcessInstance());
    assertNull(actualCreateResult.getProcessDef());
    assertNull(actualCreateResult.getTask());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertFalse(actualCreateResult.isGroup());
    assertFalse(actualCreateResult.isUser());
  }

  /**
   * Test {@link MybatisIdentityLinkDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisIdentityLinkDataManager#create()}
   */
  @Test
  public void testCreate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    IdentityLinkEntity actualCreateResult = (new MybatisIdentityLinkDataManager(processEngineConfiguration)).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof IdentityLinkEntityImpl);
    assertNull(actualCreateResult.getDetails());
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("id"));
    assertNull(((Map<String, Object>) persistentState).get("type"));
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getProcessDefId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getGroupId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getType());
    assertNull(actualCreateResult.getUserId());
    assertNull(actualCreateResult.getProcessInstance());
    assertNull(actualCreateResult.getProcessDef());
    assertNull(actualCreateResult.getTask());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertFalse(actualCreateResult.isGroup());
    assertFalse(actualCreateResult.isUser());
  }
}
