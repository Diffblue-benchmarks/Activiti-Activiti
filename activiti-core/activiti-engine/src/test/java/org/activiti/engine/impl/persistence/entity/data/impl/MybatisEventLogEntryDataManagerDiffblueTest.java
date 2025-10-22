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
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntity;
import org.activiti.engine.impl.persistence.entity.EventLogEntryEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MybatisEventLogEntryDataManagerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MybatisEventLogEntryDataManager#MybatisEventLogEntryDataManager(ProcessEngineConfigurationImpl)}
   *   <li>{@link MybatisEventLogEntryDataManager#getManagedEntityClass()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void MybatisEventLogEntryDataManager.<init>(ProcessEngineConfigurationImpl)",
      "Class MybatisEventLogEntryDataManager.getManagedEntityClass()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends EventLogEntryEntity> actualManagedEntityClass = (new MybatisEventLogEntryDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<EventLogEntryEntityImpl> expectedManagedEntityClass = EventLogEntryEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisEventLogEntryDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisEventLogEntryDataManager#create()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"EventLogEntryEntity MybatisEventLogEntryDataManager.create()"})
  public void testCreate() {
    // Arrange and Act
    EventLogEntryEntity actualCreateResult = (new MybatisEventLogEntryDataManager(new JtaProcessEngineConfiguration()))
        .create();

    // Assert
    assertTrue(actualCreateResult instanceof EventLogEntryEntityImpl);
    assertNull(actualCreateResult.getData());
    assertNull(actualCreateResult.getPersistentState());
    assertNull(actualCreateResult.getExecutionId());
    assertNull(actualCreateResult.getProcessDefinitionId());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getType());
    assertNull(actualCreateResult.getUserId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getLockOwner());
    assertNull(actualCreateResult.getLockTime());
    assertNull(actualCreateResult.getTimeStamp());
    assertEquals(0, actualCreateResult.getProcessed());
    assertEquals(0L, actualCreateResult.getLogNumber());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
  }
}
