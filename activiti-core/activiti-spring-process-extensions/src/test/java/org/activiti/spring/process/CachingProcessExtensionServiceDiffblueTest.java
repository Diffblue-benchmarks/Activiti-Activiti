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
package org.activiti.spring.process;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.HashMap;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.spring.process.model.Extension;
import org.activiti.spring.process.model.ProcessExtensionModel;
import org.activiti.spring.process.model.TemplatesDefinition;
import org.activiti.spring.resources.DeploymentResourceLoader;
import org.activiti.spring.resources.ResourceReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CachingProcessExtensionService.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class CachingProcessExtensionServiceDiffblueTest {
  @Autowired private CachingProcessExtensionService cachingProcessExtensionService;

  @MockBean private ProcessExtensionService processExtensionService;

  /**
   * Test {@link CachingProcessExtensionService#getExtensionsForId(String)}.
   *
   * <ul>
   *   <li>Given {@link ProcessExtensionModel} (default constructor) Extensions is {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link CachingProcessExtensionService#getExtensionsForId(String)}
   */
  @Test
  @DisplayName(
      "Test getExtensionsForId(String); given ProcessExtensionModel (default constructor) Extensions is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Extension CachingProcessExtensionService.getExtensionsForId(String)"})
  void testGetExtensionsForId_givenProcessExtensionModelExtensionsIsHashMap() {
    // Arrange
    ProcessExtensionModel processExtensionModel = new ProcessExtensionModel();
    processExtensionModel.setExtensions(new HashMap<>());
    processExtensionModel.setId("42");

    ArrayList<ProcessExtensionModel> processExtensionModelList = new ArrayList<>();
    processExtensionModelList.add(processExtensionModel);

    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader =
        mock(DeploymentResourceLoader.class);
    when(processExtensionLoader.loadResourcesForDeployment(
            Mockito.<String>any(), Mockito.<ResourceReader<ProcessExtensionModel>>any()))
        .thenReturn(processExtensionModelList);

    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.getProcessDefinition(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    ProcessExtensionService processExtensionService =
        new ProcessExtensionService(processExtensionLoader, processExtensionReader);
    processExtensionService.setRepositoryService(repositoryService);

    // Act
    Extension actualExtensionsForId =
        new CachingProcessExtensionService(processExtensionService).getExtensionsForId("42");

    // Assert
    verify(repositoryService).getProcessDefinition("42");
    verify(processExtensionLoader).loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    TemplatesDefinition templates = actualExtensionsForId.getTemplates();
    assertNull(templates.getDefaultTemplate());
    assertTrue(actualExtensionsForId.getAssignments().isEmpty());
    assertTrue(actualExtensionsForId.getConstants().isEmpty());
    assertTrue(actualExtensionsForId.getMappings().isEmpty());
    assertTrue(actualExtensionsForId.getProperties().isEmpty());
    assertTrue(templates.getTasks().isEmpty());
  }

  /**
   * Test {@link CachingProcessExtensionService#getExtensionsForId(String)}.
   *
   * <ul>
   *   <li>Then return {@link Extension} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link CachingProcessExtensionService#getExtensionsForId(String)}
   */
  @Test
  @DisplayName("Test getExtensionsForId(String); then return Extension (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Extension CachingProcessExtensionService.getExtensionsForId(String)"})
  void testGetExtensionsForId_thenReturnExtension() {
    // Arrange
    Extension extension = new Extension();
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act
    Extension actualExtensionsForId = cachingProcessExtensionService.getExtensionsForId("42");

    // Assert
    verify(processExtensionService).getExtensionsForId("42");
    assertSame(extension, actualExtensionsForId);
  }

  /**
   * Test {@link CachingProcessExtensionService#getExtensionsForId(String)}.
   *
   * <ul>
   *   <li>Then return Templates DefaultTemplate is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CachingProcessExtensionService#getExtensionsForId(String)}
   */
  @Test
  @DisplayName("Test getExtensionsForId(String); then return Templates DefaultTemplate is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Extension CachingProcessExtensionService.getExtensionsForId(String)"})
  void testGetExtensionsForId_thenReturnTemplatesDefaultTemplateIsNull() {
    // Arrange
    DeploymentResourceLoader<ProcessExtensionModel> processExtensionLoader =
        mock(DeploymentResourceLoader.class);
    when(processExtensionLoader.loadResourcesForDeployment(
            Mockito.<String>any(), Mockito.<ResourceReader<ProcessExtensionModel>>any()))
        .thenReturn(new ArrayList<>());

    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.getProcessDefinition(Mockito.<String>any()))
        .thenReturn(new ProcessDefinitionEntityImpl());
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    ProcessExtensionService processExtensionService =
        new ProcessExtensionService(processExtensionLoader, processExtensionReader);
    processExtensionService.setRepositoryService(repositoryService);

    // Act
    Extension actualExtensionsForId =
        new CachingProcessExtensionService(processExtensionService).getExtensionsForId("42");

    // Assert
    verify(repositoryService).getProcessDefinition("42");
    verify(processExtensionLoader).loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    TemplatesDefinition templates = actualExtensionsForId.getTemplates();
    assertNull(templates.getDefaultTemplate());
    assertTrue(actualExtensionsForId.getAssignments().isEmpty());
    assertTrue(actualExtensionsForId.getConstants().isEmpty());
    assertTrue(actualExtensionsForId.getMappings().isEmpty());
    assertTrue(actualExtensionsForId.getProperties().isEmpty());
    assertTrue(templates.getTasks().isEmpty());
  }
}
