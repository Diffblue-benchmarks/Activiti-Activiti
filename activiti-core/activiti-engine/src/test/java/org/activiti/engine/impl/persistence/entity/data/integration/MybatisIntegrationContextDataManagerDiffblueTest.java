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
package org.activiti.engine.impl.persistence.entity.data.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.Map;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextEntity;
import org.activiti.engine.impl.persistence.entity.integration.IntegrationContextEntityImpl;
import org.junit.Test;

public class MybatisIntegrationContextDataManagerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MybatisIntegrationContextDataManager#MybatisIntegrationContextDataManager(ProcessEngineConfigurationImpl)}
   *   <li>{@link MybatisIntegrationContextDataManager#getManagedEntityClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends IntegrationContextEntity> actualManagedEntityClass = (new MybatisIntegrationContextDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<IntegrationContextEntityImpl> expectedManagedEntityClass = IntegrationContextEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisIntegrationContextDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisIntegrationContextDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange and Act
    IntegrationContextEntity actualCreateResult = (new MybatisIntegrationContextDataManager(
        new JtaProcessEngineConfiguration())).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof IntegrationContextEntityImpl);
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getExecutionId());
    assertNull(actualCreateResult.getFlowNodeId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getCreatedDate());
    assertEquals(1, ((IntegrationContextEntityImpl) actualCreateResult).getRevision());
    assertEquals(2, ((IntegrationContextEntityImpl) actualCreateResult).getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(((Map<Object, Object>) persistentState).isEmpty());
  }

  /**
   * Test {@link MybatisIntegrationContextDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisIntegrationContextDataManager#create()}
   */
  @Test
  public void testCreate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    IntegrationContextEntity actualCreateResult = (new MybatisIntegrationContextDataManager(processEngineConfiguration))
        .create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof IntegrationContextEntityImpl);
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getExecutionId());
    assertNull(actualCreateResult.getFlowNodeId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getCreatedDate());
    assertEquals(1, ((IntegrationContextEntityImpl) actualCreateResult).getRevision());
    assertEquals(2, ((IntegrationContextEntityImpl) actualCreateResult).getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(((Map<Object, Object>) persistentState).isEmpty());
  }
}
