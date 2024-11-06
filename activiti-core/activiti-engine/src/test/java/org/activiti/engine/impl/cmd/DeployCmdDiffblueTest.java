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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisResourceDataManager;
import org.activiti.engine.impl.repository.DeploymentBuilderImpl;
import org.junit.Test;

public class DeployCmdDiffblueTest {
  /**
   * Test {@link DeployCmd#deploymentsDiffer(DeploymentEntity, DeploymentEntity)}.
   * <p>
   * Method under test:
   * {@link DeployCmd#deploymentsDiffer(DeploymentEntity, DeploymentEntity)}
   */
  @Test
  public void testDeploymentsDiffer() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    ResourceEntityManagerImpl resourceEntityManager = new ResourceEntityManagerImpl(processEngineConfiguration,
        new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeployCmd<Object> deployCmd = new DeployCmd<>(
        new DeploymentBuilderImpl(repositoryService, new DeploymentEntityImpl(), resourceEntityManager));
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();

    // Act and Assert
    assertTrue(deployCmd.deploymentsDiffer(deployment, new DeploymentEntityImpl()));
  }

  /**
   * Test {@link DeployCmd#deploymentsDiffer(DeploymentEntity, DeploymentEntity)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeployCmd#deploymentsDiffer(DeploymentEntity, DeploymentEntity)}
   */
  @Test
  public void testDeploymentsDiffer_thenReturnTrue() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    DeployCmd<Object> deployCmd = new DeployCmd<>(
        new DeploymentBuilderImpl(repositoryService, deployment, new ResourceEntityManagerImpl(
            processEngineConfiguration, new MybatisResourceDataManager(new JtaProcessEngineConfiguration()))));
    DeploymentEntityImpl deployment2 = new DeploymentEntityImpl();

    // Act and Assert
    assertTrue(deployCmd.deploymentsDiffer(deployment2, new DeploymentEntityImpl()));
  }
}
