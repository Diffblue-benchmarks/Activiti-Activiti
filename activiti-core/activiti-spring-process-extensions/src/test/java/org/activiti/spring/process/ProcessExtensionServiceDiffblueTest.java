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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
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
import org.activiti.engine.repository.ProcessDefinition;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessExtensionService.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ProcessExtensionServiceDiffblueTest {
  @MockBean private DeploymentResourceLoader<ProcessExtensionModel> deploymentResourceLoader;

  @MockBean private ProcessExtensionResourceReader processExtensionResourceReader;

  @Autowired private ProcessExtensionService processExtensionService;

  /**
   * Test {@link ProcessExtensionService#hasExtensionsFor(ProcessDefinition)} with {@code
   * processDefinition}.
   *
   * <p>Method under test: {@link ProcessExtensionService#hasExtensionsFor(ProcessDefinition)}
   */
  @Test
  @DisplayName("Test hasExtensionsFor(ProcessDefinition) with 'processDefinition'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessExtensionService.hasExtensionsFor(ProcessDefinition)"})
  void testHasExtensionsForWithProcessDefinition() {
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
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    ProcessExtensionService processExtensionService =
        new ProcessExtensionService(processExtensionLoader, processExtensionReader);

    // Act
    boolean actualHasExtensionsForResult =
        processExtensionService.hasExtensionsFor(new ProcessDefinitionEntityImpl());

    // Assert
    verify(processExtensionLoader).loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    assertFalse(actualHasExtensionsForResult);
  }

  /**
   * Test {@link ProcessExtensionService#hasExtensionsFor(String)} with {@code processDefinitionId}.
   *
   * <p>Method under test: {@link ProcessExtensionService#hasExtensionsFor(String)}
   */
  @Test
  @DisplayName("Test hasExtensionsFor(String) with 'processDefinitionId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessExtensionService.hasExtensionsFor(String)"})
  void testHasExtensionsForWithProcessDefinitionId() {
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
    boolean actualHasExtensionsForResult = processExtensionService.hasExtensionsFor("42");

    // Assert
    verify(repositoryService).getProcessDefinition("42");
    verify(processExtensionLoader).loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    assertFalse(actualHasExtensionsForResult);
  }

  /**
   * Test {@link ProcessExtensionService#hasExtensionsFor(String)} with {@code processDefinitionId}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionService#hasExtensionsFor(String)}
   */
  @Test
  @DisplayName("Test hasExtensionsFor(String) with 'processDefinitionId'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessExtensionService.hasExtensionsFor(String)"})
  void testHasExtensionsForWithProcessDefinitionId_thenReturnFalse() {
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
    boolean actualHasExtensionsForResult = processExtensionService.hasExtensionsFor("42");

    // Assert
    verify(repositoryService).getProcessDefinition("42");
    verify(processExtensionLoader).loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    assertFalse(actualHasExtensionsForResult);
  }

  /**
   * Test {@link ProcessExtensionService#hasExtensionsFor(ProcessDefinition)} with {@code
   * processDefinition}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionService#hasExtensionsFor(ProcessDefinition)}
   */
  @Test
  @DisplayName("Test hasExtensionsFor(ProcessDefinition) with 'processDefinition'; given '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessExtensionService.hasExtensionsFor(ProcessDefinition)"})
  void testHasExtensionsForWithProcessDefinition_given42() {
    // Arrange
    when(deploymentResourceLoader.loadResourcesForDeployment(
            Mockito.<String>any(), Mockito.<ResourceReader<ProcessExtensionModel>>any()))
        .thenReturn(new ArrayList<>());

    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    processDefinition.setDeploymentId("42");

    // Act
    boolean actualHasExtensionsForResult =
        processExtensionService.hasExtensionsFor(processDefinition);

    // Assert
    verify(deploymentResourceLoader)
        .loadResourcesForDeployment(eq("42"), isA(ResourceReader.class));
    assertFalse(actualHasExtensionsForResult);
  }

  /**
   * Test {@link ProcessExtensionService#hasExtensionsFor(ProcessDefinition)} with {@code
   * processDefinition}.
   *
   * <ul>
   *   <li>When {@link ProcessDefinitionEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionService#hasExtensionsFor(ProcessDefinition)}
   */
  @Test
  @DisplayName(
      "Test hasExtensionsFor(ProcessDefinition) with 'processDefinition'; when ProcessDefinitionEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessExtensionService.hasExtensionsFor(ProcessDefinition)"})
  void testHasExtensionsForWithProcessDefinition_whenProcessDefinitionEntityImpl() {
    // Arrange
    when(deploymentResourceLoader.loadResourcesForDeployment(
            Mockito.<String>any(), Mockito.<ResourceReader<ProcessExtensionModel>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    boolean actualHasExtensionsForResult =
        processExtensionService.hasExtensionsFor(new ProcessDefinitionEntityImpl());

    // Assert
    verify(deploymentResourceLoader)
        .loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    assertFalse(actualHasExtensionsForResult);
  }

  /**
   * Test {@link ProcessExtensionService#getExtensionsFor(ProcessDefinition)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ProcessDefinitionEntityImpl} (default constructor) DeploymentId is {@code
   *       42}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionService#getExtensionsFor(ProcessDefinition)}
   */
  @Test
  @DisplayName(
      "Test getExtensionsFor(ProcessDefinition); given '42'; when ProcessDefinitionEntityImpl (default constructor) DeploymentId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Extension ProcessExtensionService.getExtensionsFor(ProcessDefinition)"})
  void testGetExtensionsFor_given42_whenProcessDefinitionEntityImplDeploymentIdIs42() {
    // Arrange
    when(deploymentResourceLoader.loadResourcesForDeployment(
            Mockito.<String>any(), Mockito.<ResourceReader<ProcessExtensionModel>>any()))
        .thenReturn(new ArrayList<>());

    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    processDefinition.setDeploymentId("42");

    // Act
    Extension actualExtensionsFor = processExtensionService.getExtensionsFor(processDefinition);

    // Assert
    verify(deploymentResourceLoader)
        .loadResourcesForDeployment(eq("42"), isA(ResourceReader.class));
    TemplatesDefinition templates = actualExtensionsFor.getTemplates();
    assertNull(templates.getDefaultTemplate());
    assertTrue(actualExtensionsFor.getAssignments().isEmpty());
    assertTrue(actualExtensionsFor.getConstants().isEmpty());
    assertTrue(actualExtensionsFor.getMappings().isEmpty());
    assertTrue(actualExtensionsFor.getProperties().isEmpty());
    assertTrue(templates.getTasks().isEmpty());
  }

  /**
   * Test {@link ProcessExtensionService#getExtensionsFor(ProcessDefinition)}.
   *
   * <ul>
   *   <li>Given {@link ProcessExtensionModel} (default constructor) Extensions is {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionService#getExtensionsFor(ProcessDefinition)}
   */
  @Test
  @DisplayName(
      "Test getExtensionsFor(ProcessDefinition); given ProcessExtensionModel (default constructor) Extensions is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Extension ProcessExtensionService.getExtensionsFor(ProcessDefinition)"})
  void testGetExtensionsFor_givenProcessExtensionModelExtensionsIsHashMap() {
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
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ProcessExtensionResourceReader processExtensionReader =
        new ProcessExtensionResourceReader(objectMapper, new HashMap<>());

    ProcessExtensionService processExtensionService =
        new ProcessExtensionService(processExtensionLoader, processExtensionReader);

    // Act
    Extension actualExtensionsFor =
        processExtensionService.getExtensionsFor(new ProcessDefinitionEntityImpl());

    // Assert
    verify(processExtensionLoader).loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    TemplatesDefinition templates = actualExtensionsFor.getTemplates();
    assertNull(templates.getDefaultTemplate());
    assertTrue(actualExtensionsFor.getAssignments().isEmpty());
    assertTrue(actualExtensionsFor.getConstants().isEmpty());
    assertTrue(actualExtensionsFor.getMappings().isEmpty());
    assertTrue(actualExtensionsFor.getProperties().isEmpty());
    assertTrue(templates.getTasks().isEmpty());
  }

  /**
   * Test {@link ProcessExtensionService#getExtensionsFor(ProcessDefinition)}.
   *
   * <ul>
   *   <li>When {@link ProcessDefinitionEntityImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionService#getExtensionsFor(ProcessDefinition)}
   */
  @Test
  @DisplayName(
      "Test getExtensionsFor(ProcessDefinition); when ProcessDefinitionEntityImpl (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Extension ProcessExtensionService.getExtensionsFor(ProcessDefinition)"})
  void testGetExtensionsFor_whenProcessDefinitionEntityImpl() {
    // Arrange
    when(deploymentResourceLoader.loadResourcesForDeployment(
            Mockito.<String>any(), Mockito.<ResourceReader<ProcessExtensionModel>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    Extension actualExtensionsFor =
        processExtensionService.getExtensionsFor(new ProcessDefinitionEntityImpl());

    // Assert
    verify(deploymentResourceLoader)
        .loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    TemplatesDefinition templates = actualExtensionsFor.getTemplates();
    assertNull(templates.getDefaultTemplate());
    assertTrue(actualExtensionsFor.getAssignments().isEmpty());
    assertTrue(actualExtensionsFor.getConstants().isEmpty());
    assertTrue(actualExtensionsFor.getMappings().isEmpty());
    assertTrue(actualExtensionsFor.getProperties().isEmpty());
    assertTrue(templates.getTasks().isEmpty());
  }

  /**
   * Test {@link ProcessExtensionService#getExtensionsForId(String)}.
   *
   * <ul>
   *   <li>Given {@link ProcessExtensionModel} (default constructor) Extensions is {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionService#getExtensionsForId(String)}
   */
  @Test
  @DisplayName(
      "Test getExtensionsForId(String); given ProcessExtensionModel (default constructor) Extensions is HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Extension ProcessExtensionService.getExtensionsForId(String)"})
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
    Extension actualExtensionsForId = processExtensionService.getExtensionsForId("42");

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
   * Test {@link ProcessExtensionService#getExtensionsForId(String)}.
   *
   * <ul>
   *   <li>Then return Templates DefaultTemplate is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionService#getExtensionsForId(String)}
   */
  @Test
  @DisplayName("Test getExtensionsForId(String); then return Templates DefaultTemplate is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Extension ProcessExtensionService.getExtensionsForId(String)"})
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
    Extension actualExtensionsForId = processExtensionService.getExtensionsForId("42");

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
