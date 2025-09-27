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
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.cachematcher.JobsByExecutionIdMatcher;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MybatisJobDataManagerDiffblueTest {
  /**
   * Test {@link MybatisJobDataManager#MybatisJobDataManager(ProcessEngineConfigurationImpl)}.
   *
   * <p>Method under test: {@link
   * MybatisJobDataManager#MybatisJobDataManager(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MybatisJobDataManager.<init>(ProcessEngineConfigurationImpl)"})
  public void testNewMybatisJobDataManager() {
    // Arrange and Act
    MybatisJobDataManager actualMybatisJobDataManager =
        new MybatisJobDataManager(new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(
        actualMybatisJobDataManager.jobsByExecutionIdMatcher instanceof JobsByExecutionIdMatcher);
    assertNull(actualMybatisJobDataManager.getManagedEntitySubClasses());
    Class<JobEntityImpl> expectedManagedEntityClass = JobEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualMybatisJobDataManager.getManagedEntityClass());
  }

  /**
   * Test {@link MybatisJobDataManager#getManagedEntityClass()}.
   *
   * <p>Method under test: {@link MybatisJobDataManager#getManagedEntityClass()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class MybatisJobDataManager.getManagedEntityClass()"})
  public void testGetManagedEntityClass() {
    // Arrange and Act
    Class<? extends JobEntity> actualManagedEntityClass =
        new MybatisJobDataManager(new JtaProcessEngineConfiguration()).getManagedEntityClass();

    // Assert
    Class<JobEntityImpl> expectedManagedEntityClass = JobEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisJobDataManager#create()}.
   *
   * <p>Method under test: {@link MybatisJobDataManager#create()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JobEntity MybatisJobDataManager.create()"})
  public void testCreate() {
    // Arrange and Act
    JobEntity actualCreateResult =
        new MybatisJobDataManager(new JtaProcessEngineConfiguration()).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof JobEntityImpl);
    assertEquals("", actualCreateResult.getTenantId());
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
    assertEquals(0, actualCreateResult.getMaxIterations());
    assertEquals(0, actualCreateResult.getRetries());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
    assertTrue(((Map<String, Integer>) persistentState).containsKey("duedate"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("exceptionMessage"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockExpirationTime"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("lockOwner"));
    assertTrue(((Map<String, Integer>) persistentState).containsKey("retries"));
    assertTrue(actualCreateResult.isExclusive());
  }
}
