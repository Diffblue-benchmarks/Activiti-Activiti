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
package org.activiti.engine.impl.persistence.entity.integration;

import static org.junit.Assert.assertSame;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.integration.IntegrationContextDataManager;
import org.activiti.engine.impl.persistence.entity.data.integration.MybatisIntegrationContextDataManager;
import org.junit.Test;

public class IntegrationContextManagerImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link IntegrationContextManagerImpl#IntegrationContextManagerImpl(ProcessEngineConfigurationImpl, IntegrationContextDataManager)}
   *   <li>{@link IntegrationContextManagerImpl#getDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    MybatisIntegrationContextDataManager dataManager = new MybatisIntegrationContextDataManager(
        new JtaProcessEngineConfiguration());

    // Act and Assert
    assertSame(dataManager,
        (new IntegrationContextManagerImpl(processEngineConfiguration, dataManager)).getDataManager());
  }
}
