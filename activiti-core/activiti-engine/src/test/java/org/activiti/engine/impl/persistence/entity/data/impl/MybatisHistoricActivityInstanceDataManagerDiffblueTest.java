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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.UnfinishedHistoricActivityInstanceMatcher;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MybatisHistoricActivityInstanceDataManagerDiffblueTest {
  /**
   * Test {@link
   * MybatisHistoricActivityInstanceDataManager#MybatisHistoricActivityInstanceDataManager(ProcessEngineConfigurationImpl)}.
   *
   * <p>Method under test: {@link
   * MybatisHistoricActivityInstanceDataManager#MybatisHistoricActivityInstanceDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MybatisHistoricActivityInstanceDataManager.<init>(ProcessEngineConfigurationImpl)"
  })
  public void testNewMybatisHistoricActivityInstanceDataManager() {
    // Arrange and Act
    MybatisHistoricActivityInstanceDataManager actualMybatisHistoricActivityInstanceDataManager =
        new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(
        actualMybatisHistoricActivityInstanceDataManager.unfinishedHistoricActivityInstanceMatcher
            instanceof UnfinishedHistoricActivityInstanceMatcher);
    assertNull(actualMybatisHistoricActivityInstanceDataManager.getManagedEntitySubClasses());
    Class<HistoricActivityInstanceEntityImpl> expectedManagedEntityClass =
        HistoricActivityInstanceEntityImpl.class;
    assertEquals(
        expectedManagedEntityClass,
        actualMybatisHistoricActivityInstanceDataManager.getManagedEntityClass());
  }

  /**
   * Test {@link MybatisHistoricActivityInstanceDataManager#getManagedEntityClass()}.
   *
   * <p>Method under test: {@link
   * MybatisHistoricActivityInstanceDataManager#getManagedEntityClass()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class MybatisHistoricActivityInstanceDataManager.getManagedEntityClass()"})
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends HistoricActivityInstanceEntity> actualManagedEntityClass =
        new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration())
            .getManagedEntityClass();

    // Assert
    Class<HistoricActivityInstanceEntityImpl> expectedManagedEntityClass =
        HistoricActivityInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisHistoricActivityInstanceDataManager#create()}.
   *
   * <p>Method under test: {@link MybatisHistoricActivityInstanceDataManager#create()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricActivityInstanceEntity MybatisHistoricActivityInstanceDataManager.create()"
  })
  public void testCreate() {
    // Arrange and Act
    HistoricActivityInstanceEntity actualCreateResult =
        new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration())
            .create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricActivityInstanceEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
    assertNull(actualCreateResult.getDurationInMillis());
    assertNull(actualCreateResult.getActivityId());
    assertNull(actualCreateResult.getActivityName());
    assertNull(actualCreateResult.getActivityType());
    assertNull(actualCreateResult.getAssignee());
    assertNull(actualCreateResult.getCalledProcessInstanceId());
    assertNull(actualCreateResult.getDeleteReason());
    assertNull(actualCreateResult.getExecutionId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getEndTime());
    assertNull(actualCreateResult.getStartTime());
    assertNull(actualCreateResult.getTime());
    assertEquals(5, ((Map<String, Object>) persistentState).size());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(((Map<String, Object>) persistentState).containsKey("assignee"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("deleteReason"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("durationInMillis"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("endTime"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("executionId"));
  }
}
