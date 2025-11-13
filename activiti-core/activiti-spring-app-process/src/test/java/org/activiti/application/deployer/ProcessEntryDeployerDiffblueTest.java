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
package org.activiti.application.deployer;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.activiti.application.ApplicationContent;
import org.activiti.application.FileContent;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisResourceDataManager;
import org.activiti.engine.impl.repository.DeploymentBuilderImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProcessEntryDeployerDiffblueTest {
  /**
   * Test {@link ProcessEntryDeployer#deployEntries(ApplicationContent)}.
   *
   * <ul>
   *   <li>Then calls {@link ApplicationContent#getFileContents(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEntryDeployer#deployEntries(ApplicationContent)}
   */
  @Test
  @DisplayName("Test deployEntries(ApplicationContent); then calls getFileContents(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessEntryDeployer.deployEntries(ApplicationContent)"})
  void testDeployEntries_thenCallsGetFileContents() throws UnsupportedEncodingException {
    // Arrange
    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.deploy(Mockito.<DeploymentBuilderImpl>any()))
        .thenReturn(new DeploymentEntityImpl());
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilderImpl =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    DeploymentBuilderImpl deploymentBuilderImpl2 = mock(DeploymentBuilderImpl.class);
    when(deploymentBuilderImpl2.enableDuplicateFiltering()).thenReturn(deploymentBuilderImpl);

    RepositoryService repositoryService2 = mock(RepositoryService.class);
    when(repositoryService2.createDeployment()).thenReturn(deploymentBuilderImpl2);
    ProcessEntryDeployer processEntryDeployer = new ProcessEntryDeployer(repositoryService2);

    ArrayList<FileContent> fileContentList = new ArrayList<>();
    FileContent fileContent = new FileContent("Name", "AXAXAXAX".getBytes("UTF-8"));
    fileContentList.add(fileContent);

    ApplicationContent application = mock(ApplicationContent.class);
    when(application.getFileContents(Mockito.<String>any())).thenReturn(fileContentList);

    // Act
    processEntryDeployer.deployEntries(application);

    // Assert
    verify(application).getFileContents("processes");
    verify(repositoryService2).createDeployment();
    verify(repositoryService).deploy(isA(DeploymentBuilderImpl.class));
    verify(deploymentBuilderImpl2).enableDuplicateFiltering();
  }
}
