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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.activiti.core.common.spring.project.ApplicationUpgradeContextService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisResourceDataManager;
import org.activiti.engine.impl.repository.DeploymentBuilderImpl;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ContextResource;
import org.springframework.core.io.Resource;

public class AbstractAutoDeploymentStrategyDiffblueTest {
  /**
   * Test {@link AbstractAutoDeploymentStrategy#handlesMode(String)}.
   * <p>
   * Method under test: {@link AbstractAutoDeploymentStrategy#handlesMode(String)}
   */
  @Test
  public void testHandlesMode() {
    // Arrange
    AnnotationConfigApplicationContext resourceLoader = new AnnotationConfigApplicationContext();
    resourceLoader.addApplicationListener(mock(ApplicationListener.class));

    // Act and Assert
    assertFalse((new DefaultAutoDeploymentStrategy(
        new ApplicationUpgradeContextService("Path", 1, true, new ObjectMapper(), resourceLoader)))
        .handlesMode("Mode"));
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#handlesMode(String)}.
   * <ul>
   *   <li>When {@link DefaultAutoDeploymentStrategy#DEPLOYMENT_MODE}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractAutoDeploymentStrategy#handlesMode(String)}
   */
  @Test
  public void testHandlesMode_whenDeployment_mode_thenReturnTrue() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertTrue((new DefaultAutoDeploymentStrategy(
        new ApplicationUpgradeContextService("Path", 1, true, objectMapper, new AnnotationConfigApplicationContext())))
        .handlesMode(DefaultAutoDeploymentStrategy.DEPLOYMENT_MODE));
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#handlesMode(String)}.
   * <ul>
   *   <li>When {@code Mode}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractAutoDeploymentStrategy#handlesMode(String)}
   */
  @Test
  public void testHandlesMode_whenMode_thenReturnFalse() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertFalse((new DefaultAutoDeploymentStrategy(
        new ApplicationUpgradeContextService("Path", 1, true, objectMapper, new AnnotationConfigApplicationContext())))
        .handlesMode("Mode"));
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#handlesMode(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractAutoDeploymentStrategy#handlesMode(String)}
   */
  @Test
  public void testHandlesMode_whenNull_thenReturnFalse() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertFalse((new DefaultAutoDeploymentStrategy(
        new ApplicationUpgradeContextService("Path", 1, true, objectMapper, new AnnotationConfigApplicationContext())))
        .handlesMode(null));
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#determineResourceName(Resource)}.
   * <ul>
   *   <li>Given {@code Path Within Context}.</li>
   *   <li>Then return {@code Path Within Context}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractAutoDeploymentStrategy#determineResourceName(Resource)}
   */
  @Test
  public void testDetermineResourceName_givenPathWithinContext_thenReturnPathWithinContext() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    DefaultAutoDeploymentStrategy defaultAutoDeploymentStrategy = new DefaultAutoDeploymentStrategy(
        new ApplicationUpgradeContextService("Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));
    ContextResource resource = mock(ContextResource.class);
    when(resource.getPathWithinContext()).thenReturn("Path Within Context");

    // Act
    String actualDetermineResourceNameResult = defaultAutoDeploymentStrategy.determineResourceName(resource);

    // Assert
    verify(resource).getPathWithinContext();
    assertEquals("Path Within Context", actualDetermineResourceNameResult);
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#determineResourceName(Resource)}.
   * <ul>
   *   <li>Then return
   * {@code Byte array resource [resource loaded from byte array]}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractAutoDeploymentStrategy#determineResourceName(Resource)}
   */
  @Test
  public void testDetermineResourceName_thenReturnByteArrayResourceResourceLoadedFromByteArray()
      throws UnsupportedEncodingException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    DefaultAutoDeploymentStrategy defaultAutoDeploymentStrategy = new DefaultAutoDeploymentStrategy(
        new ApplicationUpgradeContextService("Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));

    // Act and Assert
    assertEquals("Byte array resource [resource loaded from byte array]",
        defaultAutoDeploymentStrategy.determineResourceName(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"))));
  }

  /**
   * Test
   * {@link AbstractAutoDeploymentStrategy#validateModel(Resource, RepositoryService)}.
   * <p>
   * Method under test:
   * {@link AbstractAutoDeploymentStrategy#validateModel(Resource, RepositoryService)}
   */
  @Test
  public void testValidateModel() throws UnsupportedEncodingException {
    // Arrange
    AnnotationConfigApplicationContext resourceLoader = new AnnotationConfigApplicationContext();
    resourceLoader.addApplicationListener(mock(ApplicationListener.class));
    DefaultAutoDeploymentStrategy defaultAutoDeploymentStrategy = new DefaultAutoDeploymentStrategy(
        new ApplicationUpgradeContextService("Path", 1, true, new ObjectMapper(), resourceLoader));
    ByteArrayResource resource = new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertTrue(defaultAutoDeploymentStrategy.validateModel(resource, new RepositoryServiceImpl()));
  }

  /**
   * Test
   * {@link AbstractAutoDeploymentStrategy#validateModel(Resource, RepositoryService)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractAutoDeploymentStrategy#validateModel(Resource, RepositoryService)}
   */
  @Test
  public void testValidateModel_thenReturnTrue() throws UnsupportedEncodingException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    DefaultAutoDeploymentStrategy defaultAutoDeploymentStrategy = new DefaultAutoDeploymentStrategy(
        new ApplicationUpgradeContextService("Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));
    ByteArrayResource resource = new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertTrue(defaultAutoDeploymentStrategy.validateModel(resource, new RepositoryServiceImpl()));
  }

  /**
   * Test
   * {@link AbstractAutoDeploymentStrategy#validateModel(Resource, RepositoryService)}.
   * <ul>
   *   <li>When {@link ClassPathResource#ClassPathResource(String)} with path is
   * {@code .bpmn20.xml}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractAutoDeploymentStrategy#validateModel(Resource, RepositoryService)}
   */
  @Test
  public void testValidateModel_whenClassPathResourceWithPathIsBpmn20Xml_thenReturnFalse() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    DefaultAutoDeploymentStrategy defaultAutoDeploymentStrategy = new DefaultAutoDeploymentStrategy(
        new ApplicationUpgradeContextService("Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));
    ClassPathResource resource = new ClassPathResource(".bpmn20.xml");

    // Act and Assert
    assertFalse(defaultAutoDeploymentStrategy.validateModel(resource, new RepositoryServiceImpl()));
  }

  /**
   * Test
   * {@link AbstractAutoDeploymentStrategy#loadApplicationUpgradeContext(DeploymentBuilder)}.
   * <p>
   * Method under test:
   * {@link AbstractAutoDeploymentStrategy#loadApplicationUpgradeContext(DeploymentBuilder)}
   */
  @Test
  public void testLoadApplicationUpgradeContext() {
    // Arrange, Act and Assert
    assertNull((new DefaultAutoDeploymentStrategy(null)).loadApplicationUpgradeContext(null));
  }

  /**
   * Test
   * {@link AbstractAutoDeploymentStrategy#loadApplicationUpgradeContext(DeploymentBuilder)}.
   * <p>
   * Method under test:
   * {@link AbstractAutoDeploymentStrategy#loadApplicationUpgradeContext(DeploymentBuilder)}
   */
  @Test
  public void testLoadApplicationUpgradeContext2() {
    // Arrange
    ApplicationUpgradeContextService applicationUpgradeContextService = mock(ApplicationUpgradeContextService.class);
    when(applicationUpgradeContextService.hasEnforcedAppVersion()).thenReturn(false);
    when(applicationUpgradeContextService.hasProjectManifest()).thenReturn(false);

    // Act
    DeploymentBuilder actualLoadApplicationUpgradeContextResult = (new DefaultAutoDeploymentStrategy(
        applicationUpgradeContextService)).loadApplicationUpgradeContext(null);

    // Assert
    verify(applicationUpgradeContextService).hasEnforcedAppVersion();
    verify(applicationUpgradeContextService).hasProjectManifest();
    assertNull(actualLoadApplicationUpgradeContextResult);
  }

  /**
   * Test
   * {@link AbstractAutoDeploymentStrategy#loadApplicationUpgradeContext(DeploymentBuilder)}.
   * <p>
   * Method under test:
   * {@link AbstractAutoDeploymentStrategy#loadApplicationUpgradeContext(DeploymentBuilder)}
   */
  @Test
  public void testLoadApplicationUpgradeContext3() throws IOException {
    // Arrange
    ApplicationUpgradeContextService applicationUpgradeContextService = mock(ApplicationUpgradeContextService.class);
    when(applicationUpgradeContextService.getEnforcedAppVersion()).thenReturn(1);
    when(applicationUpgradeContextService.loadProjectManifest()).thenThrow(new IOException("foo"));
    when(applicationUpgradeContextService.hasEnforcedAppVersion()).thenReturn(true);
    when(applicationUpgradeContextService.hasProjectManifest()).thenReturn(true);
    DefaultAutoDeploymentStrategy defaultAutoDeploymentStrategy = new DefaultAutoDeploymentStrategy(
        applicationUpgradeContextService);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
    DeploymentBuilderImpl deploymentBuilder = new DeploymentBuilderImpl(repositoryService, deployment,
        new ResourceEntityManagerImpl(processEngineConfiguration,
            new MybatisResourceDataManager(new SpringProcessEngineConfiguration())));

    // Act
    DeploymentBuilder actualLoadApplicationUpgradeContextResult = defaultAutoDeploymentStrategy
        .loadApplicationUpgradeContext(deploymentBuilder);

    // Assert
    verify(applicationUpgradeContextService, atLeast(1)).getEnforcedAppVersion();
    verify(applicationUpgradeContextService).hasEnforcedAppVersion();
    verify(applicationUpgradeContextService).hasProjectManifest();
    verify(applicationUpgradeContextService).loadProjectManifest();
    assertEquals(1, deploymentBuilder.getEnforcedAppVersion().intValue());
    assertTrue(deploymentBuilder.hasEnforcedAppVersion());
    assertSame(deploymentBuilder, actualLoadApplicationUpgradeContextResult);
  }

  /**
   * Test
   * {@link AbstractAutoDeploymentStrategy#loadApplicationUpgradeContext(DeploymentBuilder)}.
   * <ul>
   *   <li>Then calls
   * {@link ApplicationUpgradeContextService#loadProjectManifest()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractAutoDeploymentStrategy#loadApplicationUpgradeContext(DeploymentBuilder)}
   */
  @Test
  public void testLoadApplicationUpgradeContext_thenCallsLoadProjectManifest() throws IOException {
    // Arrange
    ApplicationUpgradeContextService applicationUpgradeContextService = mock(ApplicationUpgradeContextService.class);
    when(applicationUpgradeContextService.loadProjectManifest()).thenThrow(new IOException("foo"));
    when(applicationUpgradeContextService.hasEnforcedAppVersion()).thenReturn(false);
    when(applicationUpgradeContextService.hasProjectManifest()).thenReturn(true);

    // Act
    DeploymentBuilder actualLoadApplicationUpgradeContextResult = (new DefaultAutoDeploymentStrategy(
        applicationUpgradeContextService)).loadApplicationUpgradeContext(null);

    // Assert
    verify(applicationUpgradeContextService).hasEnforcedAppVersion();
    verify(applicationUpgradeContextService).hasProjectManifest();
    verify(applicationUpgradeContextService).loadProjectManifest();
    assertNull(actualLoadApplicationUpgradeContextResult);
  }
}
