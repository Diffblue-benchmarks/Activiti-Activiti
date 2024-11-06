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
import java.util.ArrayList;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.spring.process.model.Extension;
import org.activiti.spring.process.model.ProcessExtensionModel;
import org.activiti.spring.process.model.TemplatesDefinition;
import org.activiti.spring.resources.DeploymentResourceLoader;
import org.activiti.spring.resources.ResourceReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessExtensionService.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
class ProcessExtensionServiceDiffblueTest {
  @MockBean
  private DeploymentResourceLoader<ProcessExtensionModel> deploymentResourceLoader;

  @MockBean
  private ProcessExtensionResourceReader processExtensionResourceReader;

  @Autowired
  private ProcessExtensionService processExtensionService;

  /**
   * Test {@link ProcessExtensionService#hasExtensionsFor(ProcessDefinition)} with
   * {@code processDefinition}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then calls {@link ProcessDefinition#getDeploymentId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExtensionService#hasExtensionsFor(ProcessDefinition)}
   */
  @Test
  @DisplayName("Test hasExtensionsFor(ProcessDefinition) with 'processDefinition'; given '42'; then calls getDeploymentId()")
  void testHasExtensionsForWithProcessDefinition_given42_thenCallsGetDeploymentId() {
    // Arrange
    when(deploymentResourceLoader.loadResourcesForDeployment(Mockito.<String>any(),
        Mockito.<ResourceReader<ProcessExtensionModel>>any())).thenReturn(new ArrayList<>());
    ProcessDefinition processDefinition = mock(ProcessDefinition.class);
    when(processDefinition.getDeploymentId()).thenReturn("42");
    when(processDefinition.getKey()).thenReturn("Key");

    // Act
    boolean actualHasExtensionsForResult = processExtensionService.hasExtensionsFor(processDefinition);

    // Assert
    verify(processDefinition).getDeploymentId();
    verify(processDefinition).getKey();
    verify(deploymentResourceLoader).loadResourcesForDeployment(eq("42"), isA(ResourceReader.class));
    assertFalse(actualHasExtensionsForResult);
  }

  /**
   * Test {@link ProcessExtensionService#hasExtensionsFor(ProcessDefinition)} with
   * {@code processDefinition}.
   * <ul>
   *   <li>When {@link ProcessDefinitionEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExtensionService#hasExtensionsFor(ProcessDefinition)}
   */
  @Test
  @DisplayName("Test hasExtensionsFor(ProcessDefinition) with 'processDefinition'; when ProcessDefinitionEntityImpl (default constructor)")
  void testHasExtensionsForWithProcessDefinition_whenProcessDefinitionEntityImpl() {
    // Arrange
    when(deploymentResourceLoader.loadResourcesForDeployment(Mockito.<String>any(),
        Mockito.<ResourceReader<ProcessExtensionModel>>any())).thenReturn(new ArrayList<>());

    // Act
    boolean actualHasExtensionsForResult = processExtensionService.hasExtensionsFor(new ProcessDefinitionEntityImpl());

    // Assert
    verify(deploymentResourceLoader).loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    assertFalse(actualHasExtensionsForResult);
  }

  /**
   * Test {@link ProcessExtensionService#getExtensionsFor(ProcessDefinition)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then calls {@link ProcessDefinition#getDeploymentId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExtensionService#getExtensionsFor(ProcessDefinition)}
   */
  @Test
  @DisplayName("Test getExtensionsFor(ProcessDefinition); given '42'; then calls getDeploymentId()")
  void testGetExtensionsFor_given42_thenCallsGetDeploymentId() {
    // Arrange
    when(deploymentResourceLoader.loadResourcesForDeployment(Mockito.<String>any(),
        Mockito.<ResourceReader<ProcessExtensionModel>>any())).thenReturn(new ArrayList<>());
    ProcessDefinition processDefinition = mock(ProcessDefinition.class);
    when(processDefinition.getDeploymentId()).thenReturn("42");
    when(processDefinition.getKey()).thenReturn("Key");

    // Act
    Extension actualExtensionsFor = processExtensionService.getExtensionsFor(processDefinition);

    // Assert
    verify(processDefinition).getDeploymentId();
    verify(processDefinition).getKey();
    verify(deploymentResourceLoader).loadResourcesForDeployment(eq("42"), isA(ResourceReader.class));
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
   * <ul>
   *   <li>When {@link ProcessDefinitionEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExtensionService#getExtensionsFor(ProcessDefinition)}
   */
  @Test
  @DisplayName("Test getExtensionsFor(ProcessDefinition); when ProcessDefinitionEntityImpl (default constructor)")
  void testGetExtensionsFor_whenProcessDefinitionEntityImpl() {
    // Arrange
    when(deploymentResourceLoader.loadResourcesForDeployment(Mockito.<String>any(),
        Mockito.<ResourceReader<ProcessExtensionModel>>any())).thenReturn(new ArrayList<>());

    // Act
    Extension actualExtensionsFor = processExtensionService.getExtensionsFor(new ProcessDefinitionEntityImpl());

    // Assert
    verify(deploymentResourceLoader).loadResourcesForDeployment(isNull(), isA(ResourceReader.class));
    TemplatesDefinition templates = actualExtensionsFor.getTemplates();
    assertNull(templates.getDefaultTemplate());
    assertTrue(actualExtensionsFor.getAssignments().isEmpty());
    assertTrue(actualExtensionsFor.getConstants().isEmpty());
    assertTrue(actualExtensionsFor.getMappings().isEmpty());
    assertTrue(actualExtensionsFor.getProperties().isEmpty());
    assertTrue(templates.getTasks().isEmpty());
  }
}
