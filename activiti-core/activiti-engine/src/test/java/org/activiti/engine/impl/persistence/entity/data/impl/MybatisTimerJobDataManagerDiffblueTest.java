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
import org.activiti.engine.impl.persistence.entity.TimerJobEntity;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.TimerJobsByExecutionIdMatcher;
import org.junit.Test;

public class MybatisTimerJobDataManagerDiffblueTest {
  /**
   * Test
   * {@link MybatisTimerJobDataManager#MybatisTimerJobDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link CustomFunctionProvider}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisTimerJobDataManager#MybatisTimerJobDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisTimerJobDataManager_givenCustomFunctionProvider() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    MybatisTimerJobDataManager actualMybatisTimerJobDataManager = new MybatisTimerJobDataManager(
        processEngineConfiguration);

    // Assert
    assertTrue(actualMybatisTimerJobDataManager.timerJobsByExecutionIdMatcher instanceof TimerJobsByExecutionIdMatcher);
    assertNull(actualMybatisTimerJobDataManager.getManagedEntitySubClasses());
    Class<TimerJobEntityImpl> expectedManagedEntityClass = TimerJobEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisTimerJobDataManager.getManagedEntityClass());
  }

  /**
   * Test
   * {@link MybatisTimerJobDataManager#MybatisTimerJobDataManager(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MybatisTimerJobDataManager#MybatisTimerJobDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testNewMybatisTimerJobDataManager_whenJtaProcessEngineConfiguration() {
    // Arrange and Act
    MybatisTimerJobDataManager actualMybatisTimerJobDataManager = new MybatisTimerJobDataManager(
        new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(actualMybatisTimerJobDataManager.timerJobsByExecutionIdMatcher instanceof TimerJobsByExecutionIdMatcher);
    assertNull(actualMybatisTimerJobDataManager.getManagedEntitySubClasses());
    Class<TimerJobEntityImpl> expectedManagedEntityClass = TimerJobEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisTimerJobDataManager.getManagedEntityClass());
  }

  /**
   * Test {@link MybatisTimerJobDataManager#getManagedEntityClass()}.
   * <p>
   * Method under test: {@link MybatisTimerJobDataManager#getManagedEntityClass()}
   */
  @Test
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends TimerJobEntity> actualManagedEntityClass = (new MybatisTimerJobDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<TimerJobEntityImpl> expectedManagedEntityClass = TimerJobEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisTimerJobDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisTimerJobDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange and Act
    TimerJobEntity actualCreateResult = (new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration())).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof TimerJobEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(((Map<String, Integer>) persistentState).get("lockExpirationTime"));
    assertNull(((Map<String, Integer>) persistentState).get("lockOwner"));
    assertNull(actualCreateResult.getExceptionStacktrace());
    assertNull(actualCreateResult.getJobHandlerConfiguration());
    assertNull(actualCreateResult.getJobHandlerType());
    assertNull(actualCreateResult.getJobType());
    assertNull(actualCreateResult.getRepeat());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getLockOwner());
    assertNull(actualCreateResult.getExceptionMessage());
    assertNull(actualCreateResult.getExecutionId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getEndDate());
    assertNull(actualCreateResult.getLockExpirationTime());
    assertNull(actualCreateResult.getDuedate());
    assertNull(actualCreateResult.getExceptionByteArrayRef());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualCreateResult.getMaxIterations());
    assertEquals(0, actualCreateResult.getRetries());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(actualCreateResult.isExclusive());
  }

  /**
   * Test {@link MybatisTimerJobDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisTimerJobDataManager#create()}
   */
  @Test
  public void testCreate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    TimerJobEntity actualCreateResult = (new MybatisTimerJobDataManager(processEngineConfiguration)).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof TimerJobEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(((Map<String, Integer>) persistentState).get("lockExpirationTime"));
    assertNull(((Map<String, Integer>) persistentState).get("lockOwner"));
    assertNull(actualCreateResult.getExceptionStacktrace());
    assertNull(actualCreateResult.getJobHandlerConfiguration());
    assertNull(actualCreateResult.getJobHandlerType());
    assertNull(actualCreateResult.getJobType());
    assertNull(actualCreateResult.getRepeat());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getLockOwner());
    assertNull(actualCreateResult.getExceptionMessage());
    assertNull(actualCreateResult.getExecutionId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getEndDate());
    assertNull(actualCreateResult.getLockExpirationTime());
    assertNull(actualCreateResult.getDuedate());
    assertNull(actualCreateResult.getExceptionByteArrayRef());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, actualCreateResult.getMaxIterations());
    assertEquals(0, actualCreateResult.getRetries());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(actualCreateResult.isExclusive());
  }
}
