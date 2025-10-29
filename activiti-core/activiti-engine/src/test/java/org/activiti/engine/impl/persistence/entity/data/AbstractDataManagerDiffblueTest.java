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
package org.activiti.engine.impl.persistence.entity.data;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisAttachmentDataManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractDataManagerDiffblueTest {
  @InjectMocks
  private MybatisAttachmentDataManager mybatisAttachmentDataManager;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Method under test: {@link AbstractDataManager#getManagedEntitySubClasses()}
   */
  @Test
  public void testGetManagedEntitySubClasses() {
    // Arrange, Act and Assert
    assertNull((new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration())).getManagedEntitySubClasses());
  }

  /**
   * Method under test: {@link AbstractDataManager#getManagedEntitySubClasses()}
   */
  @Test
  public void testGetManagedEntitySubClasses2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull((new MybatisAttachmentDataManager(processEngineConfiguration)).getManagedEntitySubClasses());
  }

  /**
   * Method under test: {@link AbstractDataManager#findById(String)}
   */
  @Test
  public void testFindById() {
    // Arrange, Act and Assert
    assertNull(mybatisAttachmentDataManager.findById(null));
  }
}
