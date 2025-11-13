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
package org.activiti.spring.autodeployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.activiti.core.common.spring.project.ApplicationUpgradeContextService;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.repository.DeploymentBuilderImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

public class FailOnNoProcessAutoDeploymentStrategyDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link
   *       FailOnNoProcessAutoDeploymentStrategy#FailOnNoProcessAutoDeploymentStrategy(ApplicationUpgradeContextService)}
   *   <li>{@link FailOnNoProcessAutoDeploymentStrategy#getDeploymentMode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void FailOnNoProcessAutoDeploymentStrategy.<init>(ApplicationUpgradeContextService)",
    "String FailOnNoProcessAutoDeploymentStrategy.getDeploymentMode()"
  })
  public void testGettersAndSetters() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertEquals(
        FailOnNoProcessAutoDeploymentStrategy.DEPLOYMENT_MODE,
        new FailOnNoProcessAutoDeploymentStrategy(
                new ApplicationUpgradeContextService(
                    "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()))
            .getDeploymentMode());
  }

  /**
   * Test {@link FailOnNoProcessAutoDeploymentStrategy#deployResources(String, Resource[],
   * RepositoryService)}.
   *
   * <ul>
   *   <li>When empty array of {@link Resource}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link FailOnNoProcessAutoDeploymentStrategy#deployResources(String,
   * Resource[], RepositoryService)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void FailOnNoProcessAutoDeploymentStrategy.deployResources(String, Resource[], RepositoryService)"
  })
  public void testDeployResources_whenEmptyArrayOfResource_thenThrowActivitiException() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    FailOnNoProcessAutoDeploymentStrategy failOnNoProcessAutoDeploymentStrategy =
        new FailOnNoProcessAutoDeploymentStrategy(
            new ApplicationUpgradeContextService(
                "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));

    DeploymentBuilderImpl deploymentBuilderImpl = mock(DeploymentBuilderImpl.class);
    when(deploymentBuilderImpl.setEnforcedAppVersion(Mockito.<Integer>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    DeploymentBuilderImpl deploymentBuilderImpl2 = mock(DeploymentBuilderImpl.class);
    when(deploymentBuilderImpl2.name(Mockito.<String>any())).thenReturn(deploymentBuilderImpl);

    DeploymentBuilderImpl deploymentBuilderImpl3 = mock(DeploymentBuilderImpl.class);
    when(deploymentBuilderImpl3.enableDuplicateFiltering()).thenReturn(deploymentBuilderImpl2);

    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.createDeployment()).thenReturn(deploymentBuilderImpl3);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            failOnNoProcessAutoDeploymentStrategy.deployResources(
                "Deployment Name Hint", new Resource[] {}, repositoryService));
    verify(repositoryService).createDeployment();
    verify(deploymentBuilderImpl3).enableDuplicateFiltering();
    verify(deploymentBuilderImpl2).name("Deployment Name Hint");
    verify(deploymentBuilderImpl).setEnforcedAppVersion(1);
  }
}
