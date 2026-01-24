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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionByProcessInstanceMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionsByParentExecutionIdAndActivityIdEntityMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionsByParentExecutionIdEntityMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionsByProcessInstanceIdEntityMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionsByRootProcessInstanceMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionsWithSameRootProcessInstanceIdMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.InactiveExecutionsByProcInstMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.InactiveExecutionsInActivityAndProcInstMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.InactiveExecutionsInActivityMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.ProcessInstancesByProcessDefinitionMatcher;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.SubProcessInstanceExecutionBySuperExecutionIdMatcher;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MybatisExecutionDataManagerDiffblueTest {
  /**
   * Test {@link
   * MybatisExecutionDataManager#MybatisExecutionDataManager(ProcessEngineConfigurationImpl)}.
   *
   * <p>Method under test: {@link
   * MybatisExecutionDataManager#MybatisExecutionDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MybatisExecutionDataManager.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewMybatisExecutionDataManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    MybatisExecutionDataManager actualMybatisExecutionDataManager =
        new MybatisExecutionDataManager(processEngineConfiguration);

    // Assert
    assertTrue(
        actualMybatisExecutionDataManager.executionByProcessInstanceMatcher
            instanceof ExecutionByProcessInstanceMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.executionsByParentExecutionIdAndActivityIdEntityMatcher
            instanceof ExecutionsByParentExecutionIdAndActivityIdEntityMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.executionsByParentIdMatcher
            instanceof ExecutionsByParentExecutionIdEntityMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.executionsByProcessInstanceIdMatcher
            instanceof ExecutionsByProcessInstanceIdEntityMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.executionsByRootProcessInstanceMatcher
            instanceof ExecutionsByRootProcessInstanceMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.executionsWithSameRootProcessInstanceIdMatcher
            instanceof ExecutionsWithSameRootProcessInstanceIdMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.inactiveExecutionsByProcInstMatcher
            instanceof InactiveExecutionsByProcInstMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.inactiveExecutionsInActivityAndProcInstMatcher
            instanceof InactiveExecutionsInActivityAndProcInstMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.inactiveExecutionsInActivityMatcher
            instanceof InactiveExecutionsInActivityMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.processInstancesByProcessDefinitionMatcher
            instanceof ProcessInstancesByProcessDefinitionMatcher);
    assertTrue(
        actualMybatisExecutionDataManager.subProcessInstanceBySuperExecutionIdMatcher
            instanceof SubProcessInstanceExecutionBySuperExecutionIdMatcher);
    assertNull(actualMybatisExecutionDataManager.getManagedEntitySubClasses());
    Class<ExecutionEntityImpl> expectedManagedEntityClass = ExecutionEntityImpl.class;
    assertEquals(
        expectedManagedEntityClass, actualMybatisExecutionDataManager.getManagedEntityClass());
    assertSame(
        actualMybatisExecutionDataManager.performanceSettings,
        processEngineConfiguration.getPerformanceSettings());
  }

  /**
   * Test {@link MybatisExecutionDataManager#getManagedEntityClass()}.
   *
   * <p>Method under test: {@link MybatisExecutionDataManager#getManagedEntityClass()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class MybatisExecutionDataManager.getManagedEntityClass()"})
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends ExecutionEntity> actualManagedEntityClass =
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration())
            .getManagedEntityClass();

    // Assert
    Class<ExecutionEntityImpl> expectedManagedEntityClass = ExecutionEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisExecutionDataManager#findById(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MybatisExecutionDataManager#findById(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntity MybatisExecutionDataManager.findById(String)"})
  public void testFindById_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()).findById(null));
  }
}
