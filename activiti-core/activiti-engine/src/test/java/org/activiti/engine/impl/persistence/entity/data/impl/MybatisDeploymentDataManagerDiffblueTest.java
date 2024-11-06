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
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.junit.Test;

public class MybatisDeploymentDataManagerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MybatisDeploymentDataManager#MybatisDeploymentDataManager(ProcessEngineConfigurationImpl)}
   *   <li>{@link MybatisDeploymentDataManager#getManagedEntityClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends DeploymentEntity> actualManagedEntityClass = (new MybatisDeploymentDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<DeploymentEntityImpl> expectedManagedEntityClass = DeploymentEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisDeploymentDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisDeploymentDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange and Act
    DeploymentEntity actualCreateResult = (new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()))
        .create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof DeploymentEntityImpl);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertEquals("", ((Map<String, String>) persistentState).get("tenantId"));
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getVersion());
    assertNull(((Map<String, String>) persistentState).get("category"));
    assertNull(((Map<String, String>) persistentState).get("key"));
    assertNull(actualCreateResult.getEngineVersion());
    assertNull(actualCreateResult.getProjectReleaseVersion());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getCategory());
    assertNull(actualCreateResult.getKey());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getDeploymentTime());
    assertNull(actualCreateResult.getResources());
    assertFalse(actualCreateResult.isNew());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
  }

  /**
   * Test {@link MybatisDeploymentDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisDeploymentDataManager#create()}
   */
  @Test
  public void testCreate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    DeploymentEntity actualCreateResult = (new MybatisDeploymentDataManager(processEngineConfiguration)).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof DeploymentEntityImpl);
    assertEquals(3, ((Map<String, String>) persistentState).size());
    assertEquals("", ((Map<String, String>) persistentState).get("tenantId"));
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getVersion());
    assertNull(((Map<String, String>) persistentState).get("category"));
    assertNull(((Map<String, String>) persistentState).get("key"));
    assertNull(actualCreateResult.getEngineVersion());
    assertNull(actualCreateResult.getProjectReleaseVersion());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getCategory());
    assertNull(actualCreateResult.getKey());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getDeploymentTime());
    assertNull(actualCreateResult.getResources());
    assertFalse(actualCreateResult.isNew());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
  }
}
