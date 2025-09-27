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
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.HistoricVariableInstanceByProcInstMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.HistoricVariableInstanceByTaskIdMatcher;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MybatisHistoricVariableInstanceDataManagerDiffblueTest {
  /**
   * Test {@link
   * MybatisHistoricVariableInstanceDataManager#MybatisHistoricVariableInstanceDataManager(ProcessEngineConfigurationImpl)}.
   *
   * <p>Method under test: {@link
   * MybatisHistoricVariableInstanceDataManager#MybatisHistoricVariableInstanceDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MybatisHistoricVariableInstanceDataManager.<init>(ProcessEngineConfigurationImpl)"
  })
  public void testNewMybatisHistoricVariableInstanceDataManager() {
    // Arrange and Act
    MybatisHistoricVariableInstanceDataManager actualMybatisHistoricVariableInstanceDataManager =
        new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(
        actualMybatisHistoricVariableInstanceDataManager.historicVariableInstanceByProcInstMatcher
            instanceof HistoricVariableInstanceByProcInstMatcher);
    assertTrue(
        actualMybatisHistoricVariableInstanceDataManager.historicVariableInstanceByTaskIdMatcher
            instanceof HistoricVariableInstanceByTaskIdMatcher);
    assertNull(actualMybatisHistoricVariableInstanceDataManager.getManagedEntitySubClasses());
    Class<HistoricVariableInstanceEntityImpl> expectedManagedEntityClass =
        HistoricVariableInstanceEntityImpl.class;
    assertEquals(
        expectedManagedEntityClass,
        actualMybatisHistoricVariableInstanceDataManager.getManagedEntityClass());
  }

  /**
   * Test {@link MybatisHistoricVariableInstanceDataManager#getManagedEntityClass()}.
   *
   * <p>Method under test: {@link
   * MybatisHistoricVariableInstanceDataManager#getManagedEntityClass()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class MybatisHistoricVariableInstanceDataManager.getManagedEntityClass()"})
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends HistoricVariableInstanceEntity> actualManagedEntityClass =
        new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration())
            .getManagedEntityClass();

    // Assert
    Class<HistoricVariableInstanceEntityImpl> expectedManagedEntityClass =
        HistoricVariableInstanceEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisHistoricVariableInstanceDataManager#create()}.
   *
   * <p>Method under test: {@link MybatisHistoricVariableInstanceDataManager#create()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricVariableInstanceEntity MybatisHistoricVariableInstanceDataManager.create()"
  })
  public void testCreate() {
    // Arrange and Act
    HistoricVariableInstanceEntity actualCreateResult =
        new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration())
            .create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof HistoricVariableInstanceEntityImpl);
    assertNull(actualCreateResult.getBytes());
    assertNull(actualCreateResult.getDoubleValue());
    assertNull(actualCreateResult.getLongValue());
    assertNull(actualCreateResult.getCachedValue());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getVariableName());
    assertNull(actualCreateResult.getVariableTypeName());
    assertNull(actualCreateResult.getExecutionId());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getTextValue());
    assertNull(actualCreateResult.getTextValue2());
    assertNull(actualCreateResult.getTime());
    assertNull(actualCreateResult.getCreateTime());
    assertNull(actualCreateResult.getLastUpdatedTime());
    assertNull(actualCreateResult.getByteArrayRef());
    assertNull(actualCreateResult.getVariableType());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertEquals(6, ((Map<String, Object>) persistentState).size());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(((Map<String, Object>) persistentState).containsKey("createTime"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("doubleValue"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("lastUpdatedTime"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("longValue"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("textValue"));
    assertTrue(((Map<String, Object>) persistentState).containsKey("textValue2"));
  }
}
