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
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntity;
import org.activiti.engine.impl.persistence.entity.ResourceEntityImpl;
import org.junit.Test;

public class MybatisResourceDataManagerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MybatisResourceDataManager#MybatisResourceDataManager(ProcessEngineConfigurationImpl)}
   *   <li>{@link MybatisResourceDataManager#getManagedEntityClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends ResourceEntity> actualManagedEntityClass = (new MybatisResourceDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<ResourceEntityImpl> expectedManagedEntityClass = ResourceEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisResourceDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisResourceDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange and Act
    ResourceEntity actualCreateResult = (new MybatisResourceDataManager(new JtaProcessEngineConfiguration())).create();

    // Assert
    assertTrue(actualCreateResult instanceof ResourceEntityImpl);
    assertNull(actualCreateResult.getBytes());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getDeploymentId());
    assertNull(actualCreateResult.getName());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertFalse(actualCreateResult.isGenerated());
  }

  /**
   * Test {@link MybatisResourceDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisResourceDataManager#create()}
   */
  @Test
  public void testCreate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ResourceEntity actualCreateResult = (new MybatisResourceDataManager(processEngineConfiguration)).create();

    // Assert
    assertTrue(actualCreateResult instanceof ResourceEntityImpl);
    assertNull(actualCreateResult.getBytes());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getDeploymentId());
    assertNull(actualCreateResult.getName());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertFalse(actualCreateResult.isGenerated());
  }
}