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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
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
import org.junit.experimental.categories.Category;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class AbstractAutoDeploymentStrategyDiffblueTest {
  /**
   * Test {@link AbstractAutoDeploymentStrategy#handlesMode(String)}.
   *
   * <ul>
   *   <li>When {@link DefaultAutoDeploymentStrategy#DEPLOYMENT_MODE}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractAutoDeploymentStrategy#handlesMode(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractAutoDeploymentStrategy.handlesMode(String)"})
  public void testHandlesMode_whenDeployment_mode_thenReturnTrue() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertTrue(
        new DefaultAutoDeploymentStrategy(
                new ApplicationUpgradeContextService(
                    "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()))
            .handlesMode(DefaultAutoDeploymentStrategy.DEPLOYMENT_MODE));
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#handlesMode(String)}.
   *
   * <ul>
   *   <li>When {@code Mode}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractAutoDeploymentStrategy#handlesMode(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractAutoDeploymentStrategy.handlesMode(String)"})
  public void testHandlesMode_whenMode_thenReturnFalse() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertFalse(
        new DefaultAutoDeploymentStrategy(
                new ApplicationUpgradeContextService(
                    "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()))
            .handlesMode("Mode"));
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#handlesMode(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractAutoDeploymentStrategy#handlesMode(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractAutoDeploymentStrategy.handlesMode(String)"})
  public void testHandlesMode_whenNull_thenReturnFalse() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertFalse(
        new DefaultAutoDeploymentStrategy(
                new ApplicationUpgradeContextService(
                    "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()))
            .handlesMode(null));
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#determineResourceName(Resource)}.
   *
   * <ul>
   *   <li>Then return {@code Byte array resource [resource loaded from byte array]}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractAutoDeploymentStrategy#determineResourceName(Resource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String AbstractAutoDeploymentStrategy.determineResourceName(Resource)"})
  public void testDetermineResourceName_thenReturnByteArrayResourceResourceLoadedFromByteArray()
      throws UnsupportedEncodingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    DefaultAutoDeploymentStrategy defaultAutoDeploymentStrategy =
        new DefaultAutoDeploymentStrategy(
            new ApplicationUpgradeContextService(
                "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));

    // Act
    String actualDetermineResourceNameResult =
        defaultAutoDeploymentStrategy.determineResourceName(
            new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    assertEquals(
        "Byte array resource [resource loaded from byte array]", actualDetermineResourceNameResult);
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#determineResourceName(Resource)}.
   *
   * <ul>
   *   <li>When {@link ClassPathResource#ClassPathResource(String)} with {@code Path}.
   *   <li>Then return {@code Path}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractAutoDeploymentStrategy#determineResourceName(Resource)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String AbstractAutoDeploymentStrategy.determineResourceName(Resource)"})
  public void testDetermineResourceName_whenClassPathResourceWithPath_thenReturnPath() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    DefaultAutoDeploymentStrategy defaultAutoDeploymentStrategy =
        new DefaultAutoDeploymentStrategy(
            new ApplicationUpgradeContextService(
                "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));

    // Act
    String actualDetermineResourceNameResult =
        defaultAutoDeploymentStrategy.determineResourceName(new ClassPathResource("Path"));

    // Assert
    assertEquals("Path", actualDetermineResourceNameResult);
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#validateModel(Resource, RepositoryService)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractAutoDeploymentStrategy#validateModel(Resource,
   * RepositoryService)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean AbstractAutoDeploymentStrategy.validateModel(Resource, RepositoryService)"
  })
  public void testValidateModel_thenReturnTrue() throws UnsupportedEncodingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    DefaultAutoDeploymentStrategy defaultAutoDeploymentStrategy =
        new DefaultAutoDeploymentStrategy(
            new ApplicationUpgradeContextService(
                "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));
    ByteArrayResource resource = new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"));

    // Act and Assert
    assertTrue(defaultAutoDeploymentStrategy.validateModel(resource, new RepositoryServiceImpl()));
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#validateModel(Resource, RepositoryService)}.
   *
   * <ul>
   *   <li>When {@link ClassPathResource#ClassPathResource(String)} with path is {@code
   *       .bpmn20.xml}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractAutoDeploymentStrategy#validateModel(Resource,
   * RepositoryService)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean AbstractAutoDeploymentStrategy.validateModel(Resource, RepositoryService)"
  })
  public void testValidateModel_whenClassPathResourceWithPathIsBpmn20Xml_thenReturnFalse() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    DefaultAutoDeploymentStrategy defaultAutoDeploymentStrategy =
        new DefaultAutoDeploymentStrategy(
            new ApplicationUpgradeContextService(
                "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));
    ClassPathResource resource = new ClassPathResource(".bpmn20.xml");

    // Act and Assert
    assertFalse(defaultAutoDeploymentStrategy.validateModel(resource, new RepositoryServiceImpl()));
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#loadApplicationUpgradeContext(DeploymentBuilder)}.
   *
   * <p>Method under test: {@link
   * AbstractAutoDeploymentStrategy#loadApplicationUpgradeContext(DeploymentBuilder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DeploymentBuilder AbstractAutoDeploymentStrategy.loadApplicationUpgradeContext(DeploymentBuilder)"
  })
  public void testLoadApplicationUpgradeContext() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    DefaultAutoDeploymentStrategy defaultAutoDeploymentStrategy =
        new DefaultAutoDeploymentStrategy(
            new ApplicationUpgradeContextService(
                "Path", 1, true, objectMapper, new AnnotationConfigApplicationContext()));
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    DeploymentEntityImpl deployment = new DeploymentEntityImpl();
    SpringProcessEngineConfiguration processEngineConfiguration =
        new SpringProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration,
            new MybatisResourceDataManager(new SpringProcessEngineConfiguration()));

    DeploymentBuilderImpl deploymentBuilder =
        new DeploymentBuilderImpl(repositoryService, deployment, resourceEntityManager);

    // Act
    DeploymentBuilder actualLoadApplicationUpgradeContextResult =
        defaultAutoDeploymentStrategy.loadApplicationUpgradeContext(deploymentBuilder);

    // Assert
    assertEquals(1, deploymentBuilder.getEnforcedAppVersion().intValue());
    assertTrue(deploymentBuilder.hasEnforcedAppVersion());
    assertSame(deploymentBuilder, actualLoadApplicationUpgradeContextResult);
  }

  /**
   * Test {@link AbstractAutoDeploymentStrategy#loadApplicationUpgradeContext(DeploymentBuilder)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AbstractAutoDeploymentStrategy#loadApplicationUpgradeContext(DeploymentBuilder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DeploymentBuilder AbstractAutoDeploymentStrategy.loadApplicationUpgradeContext(DeploymentBuilder)"
  })
  public void testLoadApplicationUpgradeContext_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new DefaultAutoDeploymentStrategy(null).loadApplicationUpgradeContext(null));
  }
}
