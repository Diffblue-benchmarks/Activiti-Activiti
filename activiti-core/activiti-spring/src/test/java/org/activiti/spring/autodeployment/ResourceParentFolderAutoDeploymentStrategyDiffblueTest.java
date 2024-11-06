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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import org.activiti.core.common.spring.project.ApplicationUpgradeContextService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisResourceDataManager;
import org.activiti.engine.impl.repository.DeploymentBuilderImpl;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public class ResourceParentFolderAutoDeploymentStrategyDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ResourceParentFolderAutoDeploymentStrategy#ResourceParentFolderAutoDeploymentStrategy(ApplicationUpgradeContextService)}
   *   <li>{@link ResourceParentFolderAutoDeploymentStrategy#getDeploymentMode()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertEquals(ResourceParentFolderAutoDeploymentStrategy.DEPLOYMENT_MODE,
        (new ResourceParentFolderAutoDeploymentStrategy(new ApplicationUpgradeContextService("Path", 1, true,
            objectMapper, new AnnotationConfigApplicationContext()))).getDeploymentMode());
  }

  /**
   * Test
   * {@link ResourceParentFolderAutoDeploymentStrategy#deployResources(String, Resource[], RepositoryService)}.
   * <ul>
   *   <li>Then calls {@link RepositoryService#createDeployment()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ResourceParentFolderAutoDeploymentStrategy#deployResources(String, Resource[], RepositoryService)}
   */
  @Test
  public void testDeployResources_thenCallsCreateDeployment() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    ResourceParentFolderAutoDeploymentStrategy resourceParentFolderAutoDeploymentStrategy = new ResourceParentFolderAutoDeploymentStrategy(
        new ApplicationUpgradeContextService("Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));
    ByteArrayResource byteArrayResource = mock(ByteArrayResource.class);
    when(byteArrayResource.getInputStream()).thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
    when(byteArrayResource.getFile()).thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());
    when(byteArrayResource.getDescription()).thenReturn("The characteristics of someone or something");
    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.deploy(Mockito.<DeploymentBuilderImpl>any())).thenReturn(new DeploymentEntityImpl());
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
    RepositoryService repositoryService2 = mock(RepositoryService.class);
    when(repositoryService2.createDeployment())
        .thenReturn(new DeploymentBuilderImpl(repositoryService, deployment, new ResourceEntityManagerImpl(
            processEngineConfiguration, new MybatisResourceDataManager(new SpringProcessEngineConfiguration()))));

    // Act
    resourceParentFolderAutoDeploymentStrategy.deployResources("Deployment Name Hint",
        new Resource[]{byteArrayResource}, repositoryService2);

    // Assert
    verify(repositoryService2).createDeployment();
    verify(repositoryService).deploy(isA(DeploymentBuilderImpl.class));
    verify(byteArrayResource, atLeast(1)).getFile();
    verify(byteArrayResource, atLeast(1)).getDescription();
    verify(byteArrayResource).getInputStream();
  }
}
