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
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MybatisProcessDefinitionDataManagerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MybatisProcessDefinitionDataManager#MybatisProcessDefinitionDataManager(ProcessEngineConfigurationImpl)}
   *   <li>{@link MybatisProcessDefinitionDataManager#getManagedEntityClass()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MybatisProcessDefinitionDataManager.<init>(ProcessEngineConfigurationImpl)",
      "Class MybatisProcessDefinitionDataManager.getManagedEntityClass()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends ProcessDefinitionEntity> actualManagedEntityClass = (new MybatisProcessDefinitionDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<ProcessDefinitionEntityImpl> expectedManagedEntityClass = ProcessDefinitionEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisProcessDefinitionDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisProcessDefinitionDataManager#create()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessDefinitionEntity MybatisProcessDefinitionDataManager.create()"})
  public void testCreate() {
    // Arrange and Act
    ProcessDefinitionEntity actualCreateResult = (new MybatisProcessDefinitionDataManager(
        new JtaProcessEngineConfiguration())).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof ProcessDefinitionEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getHistoryLevel());
    assertNull(actualCreateResult.getAppVersion());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getEngineVersion());
    assertNull(actualCreateResult.getCategory());
    assertNull(actualCreateResult.getDeploymentId());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getDiagramResourceName());
    assertNull(actualCreateResult.getKey());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getResourceName());
    assertNull(((ProcessDefinitionEntityImpl) actualCreateResult).getVariables());
    assertNull(((ProcessDefinitionEntityImpl) actualCreateResult).getIoSpecification());
    assertEquals(0, actualCreateResult.getVersion());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(1, actualCreateResult.getSuspensionState());
    assertEquals(2, ((Map<String, Integer>) persistentState).size());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertFalse(actualCreateResult.getHasStartFormKey());
    assertFalse(actualCreateResult.isGraphicalNotationDefined());
    assertFalse(actualCreateResult.hasGraphicalNotation());
    assertFalse(actualCreateResult.hasStartFormKey());
    assertFalse(actualCreateResult.isSuspended());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("category"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("suspensionState"));
  }
}
