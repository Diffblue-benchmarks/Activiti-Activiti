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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisResourceDataManager;
import org.activiti.engine.impl.repository.DeploymentBuilderImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DeployCmdDiffblueTest {
  /**
   * Test {@link DeployCmd#deploymentsDiffer(DeploymentEntity, DeploymentEntity)}.
   *
   * <p>Method under test: {@link DeployCmd#deploymentsDiffer(DeploymentEntity, DeploymentEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DeployCmd.deploymentsDiffer(DeploymentEntity, DeploymentEntity)"})
  public void testDeploymentsDiffer() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilder =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);
    deploymentBuilder.setEnforcedAppVersion(1);
    DeployCmd<Object> deployCmd = new DeployCmd<>(deploymentBuilder);
    DeploymentEntityImpl deployment2 = new DeploymentEntityImpl();

    // Act and Assert
    assertTrue(deployCmd.deploymentsDiffer(deployment2, new DeploymentEntityImpl()));
  }

  /**
   * Test {@link DeployCmd#deploymentsDiffer(DeploymentEntity, DeploymentEntity)}.
   *
   * <ul>
   *   <li>Given {@link ResourceEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DeployCmd#deploymentsDiffer(DeploymentEntity, DeploymentEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DeployCmd.deploymentsDiffer(DeploymentEntity, DeploymentEntity)"})
  public void testDeploymentsDiffer_givenResourceEntityImpl() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilder =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);
    DeployCmd<Object> deployCmd = new DeployCmd<>(deploymentBuilder);

    DeploymentEntityImpl deployment2 = new DeploymentEntityImpl();
    deployment2.addResource(new ResourceEntityImpl());

    // Act and Assert
    assertTrue(deployCmd.deploymentsDiffer(deployment2, new DeploymentEntityImpl()));
  }

  /**
   * Test {@link DeployCmd#deploymentsDiffer(DeploymentEntity, DeploymentEntity)}.
   *
   * <ul>
   *   <li>Given {@link ResourceEntityImpl} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DeployCmd#deploymentsDiffer(DeploymentEntity, DeploymentEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DeployCmd.deploymentsDiffer(DeploymentEntity, DeploymentEntity)"})
  public void testDeploymentsDiffer_givenResourceEntityImpl_thenReturnFalse() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilder =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);
    DeployCmd<Object> deployCmd = new DeployCmd<>(deploymentBuilder);

    DeploymentEntityImpl deployment2 = new DeploymentEntityImpl();
    deployment2.addResource(new ResourceEntityImpl());

    DeploymentEntityImpl saved = new DeploymentEntityImpl();
    saved.addResource(new ResourceEntityImpl());

    // Act and Assert
    assertFalse(deployCmd.deploymentsDiffer(deployment2, saved));
  }

  /**
   * Test {@link DeployCmd#deploymentsDiffer(DeploymentEntity, DeploymentEntity)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DeployCmd#deploymentsDiffer(DeploymentEntity, DeploymentEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DeployCmd.deploymentsDiffer(DeploymentEntity, DeploymentEntity)"})
  public void testDeploymentsDiffer_thenReturnTrue() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilder =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);
    DeployCmd<Object> deployCmd = new DeployCmd<>(deploymentBuilder);
    DeploymentEntityImpl deployment2 = new DeploymentEntityImpl();

    // Act and Assert
    assertTrue(deployCmd.deploymentsDiffer(deployment2, new DeploymentEntityImpl()));
  }
}
