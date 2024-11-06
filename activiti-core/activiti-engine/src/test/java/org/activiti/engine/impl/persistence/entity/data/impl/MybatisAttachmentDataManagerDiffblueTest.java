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
import org.activiti.engine.impl.persistence.entity.AttachmentEntity;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.junit.Test;

public class MybatisAttachmentDataManagerDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link MybatisAttachmentDataManager#MybatisAttachmentDataManager(ProcessEngineConfigurationImpl)}
   *   <li>{@link MybatisAttachmentDataManager#getManagedEntityClass()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends AttachmentEntity> actualManagedEntityClass = (new MybatisAttachmentDataManager(
        new JtaProcessEngineConfiguration())).getManagedEntityClass();

    // Assert
    Class<AttachmentEntityImpl> expectedManagedEntityClass = AttachmentEntityImpl.class;
    assertEquals(expectedManagedEntityClass, actualManagedEntityClass);
  }

  /**
   * Test {@link MybatisAttachmentDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisAttachmentDataManager#create()}
   */
  @Test
  public void testCreate() {
    // Arrange and Act
    AttachmentEntity actualCreateResult = (new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()))
        .create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof AttachmentEntityImpl);
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("description"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(actualCreateResult.getUserId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getContentId());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getType());
    assertNull(actualCreateResult.getUrl());
    assertNull(actualCreateResult.getTime());
    assertNull(actualCreateResult.getContent());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
  }

  /**
   * Test {@link MybatisAttachmentDataManager#create()}.
   * <p>
   * Method under test: {@link MybatisAttachmentDataManager#create()}
   */
  @Test
  public void testCreate2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    AttachmentEntity actualCreateResult = (new MybatisAttachmentDataManager(processEngineConfiguration)).create();

    // Assert
    Object persistentState = actualCreateResult.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateResult instanceof AttachmentEntityImpl);
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("description"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(actualCreateResult.getUserId());
    assertNull(actualCreateResult.getId());
    assertNull(actualCreateResult.getContentId());
    assertNull(actualCreateResult.getDescription());
    assertNull(actualCreateResult.getName());
    assertNull(actualCreateResult.getProcessInstanceId());
    assertNull(actualCreateResult.getTaskId());
    assertNull(actualCreateResult.getType());
    assertNull(actualCreateResult.getUrl());
    assertNull(actualCreateResult.getTime());
    assertNull(actualCreateResult.getContent());
    assertEquals(1, actualCreateResult.getRevision());
    assertEquals(2, actualCreateResult.getRevisionNext());
    assertFalse(actualCreateResult.isDeleted());
    assertFalse(actualCreateResult.isInserted());
    assertFalse(actualCreateResult.isUpdated());
  }
}
