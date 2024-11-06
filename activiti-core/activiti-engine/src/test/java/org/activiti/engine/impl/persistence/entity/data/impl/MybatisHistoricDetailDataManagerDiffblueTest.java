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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.HistoricDetailAssignmentEntity;
import org.activiti.engine.impl.persistence.entity.HistoricDetailAssignmentEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricDetailEntity;
import org.activiti.engine.impl.persistence.entity.HistoricDetailEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricDetailTransitionInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricDetailTransitionInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntity;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.junit.Test;

public class MybatisHistoricDetailDataManagerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MybatisHistoricDetailDataManager#MybatisHistoricDetailDataManager(ProcessEngineConfigurationImpl)}
   *   <li>{@link MybatisHistoricDetailDataManager#getManagedEntityClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends HistoricDetailEntity> actualManagedEntityClass = (new MybatisHistoricDetailDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<HistoricDetailEntityImpl> expectedManagedEntityClass = HistoricDetailEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisHistoricDetailDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisHistoricDetailDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> (new MybatisHistoricDetailDataManager(new JtaProcessEngineConfiguration())).create());
  }

  /**
   * Test
   * {@link MybatisHistoricDetailDataManager#createHistoricDetailAssignment()}.
   * <p>
   * Method under test:
   * {@link MybatisHistoricDetailDataManager#createHistoricDetailAssignment()}
   */
  @Test
  public void testCreateHistoricDetailAssignment() {
    // Arrange and Act
    HistoricDetailAssignmentEntity actualCreateHistoricDetailAssignmentResult = (new MybatisHistoricDetailDataManager(
        new JtaProcessEngineConfiguration())).createHistoricDetailAssignment();

    // Assert
    assertTrue(actualCreateHistoricDetailAssignmentResult instanceof HistoricDetailAssignmentEntityImpl);
    assertNull(actualCreateHistoricDetailAssignmentResult.getActivityInstanceId());
    assertNull(actualCreateHistoricDetailAssignmentResult.getExecutionId());
    assertNull(actualCreateHistoricDetailAssignmentResult.getId());
    assertNull(actualCreateHistoricDetailAssignmentResult.getProcessInstanceId());
    assertNull(actualCreateHistoricDetailAssignmentResult.getTaskId());
    assertNull(actualCreateHistoricDetailAssignmentResult.getDetailType());
    assertNull(actualCreateHistoricDetailAssignmentResult.getTime());
    assertFalse(actualCreateHistoricDetailAssignmentResult.isDeleted());
    assertFalse(actualCreateHistoricDetailAssignmentResult.isInserted());
    assertFalse(actualCreateHistoricDetailAssignmentResult.isUpdated());
  }

  /**
   * Test
   * {@link MybatisHistoricDetailDataManager#createHistoricDetailAssignment()}.
   * <p>
   * Method under test:
   * {@link MybatisHistoricDetailDataManager#createHistoricDetailAssignment()}
   */
  @Test
  public void testCreateHistoricDetailAssignment2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoricDetailAssignmentEntity actualCreateHistoricDetailAssignmentResult = (new MybatisHistoricDetailDataManager(
        processEngineConfiguration)).createHistoricDetailAssignment();

    // Assert
    assertTrue(actualCreateHistoricDetailAssignmentResult instanceof HistoricDetailAssignmentEntityImpl);
    assertNull(actualCreateHistoricDetailAssignmentResult.getActivityInstanceId());
    assertNull(actualCreateHistoricDetailAssignmentResult.getExecutionId());
    assertNull(actualCreateHistoricDetailAssignmentResult.getId());
    assertNull(actualCreateHistoricDetailAssignmentResult.getProcessInstanceId());
    assertNull(actualCreateHistoricDetailAssignmentResult.getTaskId());
    assertNull(actualCreateHistoricDetailAssignmentResult.getDetailType());
    assertNull(actualCreateHistoricDetailAssignmentResult.getTime());
    assertFalse(actualCreateHistoricDetailAssignmentResult.isDeleted());
    assertFalse(actualCreateHistoricDetailAssignmentResult.isInserted());
    assertFalse(actualCreateHistoricDetailAssignmentResult.isUpdated());
  }

  /**
   * Test
   * {@link MybatisHistoricDetailDataManager#createHistoricDetailTransitionInstance()}.
   * <p>
   * Method under test:
   * {@link MybatisHistoricDetailDataManager#createHistoricDetailTransitionInstance()}
   */
  @Test
  public void testCreateHistoricDetailTransitionInstance() {
    // Arrange and Act
    HistoricDetailTransitionInstanceEntity actualCreateHistoricDetailTransitionInstanceResult = (new MybatisHistoricDetailDataManager(
        new JtaProcessEngineConfiguration())).createHistoricDetailTransitionInstance();

    // Assert
    assertTrue(
        actualCreateHistoricDetailTransitionInstanceResult instanceof HistoricDetailTransitionInstanceEntityImpl);
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getActivityInstanceId());
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getExecutionId());
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getId());
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getProcessInstanceId());
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getTaskId());
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getDetailType());
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getTime());
    assertFalse(actualCreateHistoricDetailTransitionInstanceResult.isDeleted());
    assertFalse(actualCreateHistoricDetailTransitionInstanceResult.isInserted());
    assertFalse(actualCreateHistoricDetailTransitionInstanceResult.isUpdated());
  }

  /**
   * Test
   * {@link MybatisHistoricDetailDataManager#createHistoricDetailTransitionInstance()}.
   * <p>
   * Method under test:
   * {@link MybatisHistoricDetailDataManager#createHistoricDetailTransitionInstance()}
   */
  @Test
  public void testCreateHistoricDetailTransitionInstance2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoricDetailTransitionInstanceEntity actualCreateHistoricDetailTransitionInstanceResult = (new MybatisHistoricDetailDataManager(
        processEngineConfiguration)).createHistoricDetailTransitionInstance();

    // Assert
    assertTrue(
        actualCreateHistoricDetailTransitionInstanceResult instanceof HistoricDetailTransitionInstanceEntityImpl);
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getActivityInstanceId());
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getExecutionId());
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getId());
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getProcessInstanceId());
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getTaskId());
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getDetailType());
    assertNull(actualCreateHistoricDetailTransitionInstanceResult.getTime());
    assertFalse(actualCreateHistoricDetailTransitionInstanceResult.isDeleted());
    assertFalse(actualCreateHistoricDetailTransitionInstanceResult.isInserted());
    assertFalse(actualCreateHistoricDetailTransitionInstanceResult.isUpdated());
  }

  /**
   * Test
   * {@link MybatisHistoricDetailDataManager#createHistoricDetailVariableInstanceUpdate()}.
   * <p>
   * Method under test:
   * {@link MybatisHistoricDetailDataManager#createHistoricDetailVariableInstanceUpdate()}
   */
  @Test
  public void testCreateHistoricDetailVariableInstanceUpdate() {
    // Arrange and Act
    HistoricDetailVariableInstanceUpdateEntity actualCreateHistoricDetailVariableInstanceUpdateResult = (new MybatisHistoricDetailDataManager(
        new JtaProcessEngineConfiguration())).createHistoricDetailVariableInstanceUpdate();

    // Assert
    assertTrue(
        actualCreateHistoricDetailVariableInstanceUpdateResult instanceof HistoricDetailVariableInstanceUpdateEntityImpl);
    assertEquals("VariableUpdate", actualCreateHistoricDetailVariableInstanceUpdateResult.getDetailType());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getBytes());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getDoubleValue());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getLongValue());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getCachedValue());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getActivityInstanceId());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getExecutionId());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getId());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getProcessInstanceId());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getTaskId());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getVariableName());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getVariableTypeName());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getName());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getTextValue());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getTextValue2());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getTime());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getByteArrayRef());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getVariableType());
    assertEquals(0, actualCreateHistoricDetailVariableInstanceUpdateResult.getRevision());
    assertEquals(1, actualCreateHistoricDetailVariableInstanceUpdateResult.getRevisionNext());
    assertFalse(actualCreateHistoricDetailVariableInstanceUpdateResult.isDeleted());
    assertFalse(actualCreateHistoricDetailVariableInstanceUpdateResult.isInserted());
    assertFalse(actualCreateHistoricDetailVariableInstanceUpdateResult.isUpdated());
  }

  /**
   * Test
   * {@link MybatisHistoricDetailDataManager#createHistoricDetailVariableInstanceUpdate()}.
   * <p>
   * Method under test:
   * {@link MybatisHistoricDetailDataManager#createHistoricDetailVariableInstanceUpdate()}
   */
  @Test
  public void testCreateHistoricDetailVariableInstanceUpdate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    HistoricDetailVariableInstanceUpdateEntity actualCreateHistoricDetailVariableInstanceUpdateResult = (new MybatisHistoricDetailDataManager(
        processEngineConfiguration)).createHistoricDetailVariableInstanceUpdate();

    // Assert
    assertTrue(
        actualCreateHistoricDetailVariableInstanceUpdateResult instanceof HistoricDetailVariableInstanceUpdateEntityImpl);
    assertEquals("VariableUpdate", actualCreateHistoricDetailVariableInstanceUpdateResult.getDetailType());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getBytes());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getDoubleValue());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getLongValue());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getCachedValue());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getActivityInstanceId());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getExecutionId());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getId());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getProcessInstanceId());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getTaskId());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getVariableName());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getVariableTypeName());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getName());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getTextValue());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getTextValue2());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getTime());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getByteArrayRef());
    assertNull(actualCreateHistoricDetailVariableInstanceUpdateResult.getVariableType());
    assertEquals(0, actualCreateHistoricDetailVariableInstanceUpdateResult.getRevision());
    assertEquals(1, actualCreateHistoricDetailVariableInstanceUpdateResult.getRevisionNext());
    assertFalse(actualCreateHistoricDetailVariableInstanceUpdateResult.isDeleted());
    assertFalse(actualCreateHistoricDetailVariableInstanceUpdateResult.isInserted());
    assertFalse(actualCreateHistoricDetailVariableInstanceUpdateResult.isUpdated());
  }
}
