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
package org.activiti.core.common.spring.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

class ApplicationUpgradeContextServiceDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ApplicationUpgradeContextService#ApplicationUpgradeContextService(String, Integer,
   *       Boolean, ObjectMapper, ResourcePatternResolver)}
   *   <li>{@link ApplicationUpgradeContextService#getEnforcedAppVersion()}
   *   <li>{@link ApplicationUpgradeContextService#isRollbackDeployment()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ApplicationUpgradeContextService.<init>(String, Integer, Boolean, ObjectMapper, ResourcePatternResolver)",
    "Integer ApplicationUpgradeContextService.getEnforcedAppVersion()",
    "boolean ApplicationUpgradeContextService.isRollbackDeployment()"
  })
  void testGettersAndSetters() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act
    ApplicationUpgradeContextService actualApplicationUpgradeContextService =
        new ApplicationUpgradeContextService(
            "Path", 1, true, objectMapper, new AnnotationConfigReactiveWebApplicationContext());
    Integer actualEnforcedAppVersion =
        actualApplicationUpgradeContextService.getEnforcedAppVersion();
    boolean actualIsRollbackDeploymentResult =
        actualApplicationUpgradeContextService.isRollbackDeployment();

    // Assert
    assertEquals(1, actualEnforcedAppVersion.intValue());
    assertTrue(actualIsRollbackDeploymentResult);
  }

  /**
   * Test {@link ApplicationUpgradeContextService#loadProjectManifest()}.
   *
   * <p>Method under test: {@link ApplicationUpgradeContextService#loadProjectManifest()}
   */
  @Test
  @DisplayName("Test loadProjectManifest()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.core.common.project.model.ProjectManifest ApplicationUpgradeContextService.loadProjectManifest()"
  })
  void testLoadProjectManifest() throws IOException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        FileNotFoundException.class,
        () ->
            new ApplicationUpgradeContextService(
                    "Path",
                    1,
                    true,
                    objectMapper,
                    new AnnotationConfigReactiveWebApplicationContext())
                .loadProjectManifest());
  }

  /**
   * Test {@link ApplicationUpgradeContextService#loadProjectManifest()}.
   *
   * <ul>
   *   <li>Then calls {@link ByteArrayResource#exists()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationUpgradeContextService#loadProjectManifest()}
   */
  @Test
  @DisplayName("Test loadProjectManifest(); then calls exists()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.core.common.project.model.ProjectManifest ApplicationUpgradeContextService.loadProjectManifest()"
  })
  void testLoadProjectManifest_thenCallsExists() throws IOException {
    // Arrange
    ByteArrayResource byteArrayResource = mock(ByteArrayResource.class);
    when(byteArrayResource.getInputStream()).thenThrow(new FileNotFoundException());
    when(byteArrayResource.exists()).thenReturn(true);

    PathMatchingResourcePatternResolver resourceLoader =
        mock(PathMatchingResourcePatternResolver.class);
    when(resourceLoader.getResource(Mockito.<String>any())).thenReturn(byteArrayResource);
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        FileNotFoundException.class,
        () ->
            new ApplicationUpgradeContextService("Path", 1, true, objectMapper, resourceLoader)
                .loadProjectManifest());
    verify(byteArrayResource).exists();
    verify(byteArrayResource).getInputStream();
    verify(resourceLoader).getResource("Path");
  }

  /**
   * Test {@link ApplicationUpgradeContextService#hasProjectManifest()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationUpgradeContextService#hasProjectManifest()}
   */
  @Test
  @DisplayName("Test hasProjectManifest(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ApplicationUpgradeContextService.hasProjectManifest()"})
  void testHasProjectManifest_thenReturnFalse() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertFalse(
        new ApplicationUpgradeContextService(
                "Path", 1, true, objectMapper, new AnnotationConfigReactiveWebApplicationContext())
            .hasProjectManifest());
  }

  /**
   * Test {@link ApplicationUpgradeContextService#hasProjectManifest()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationUpgradeContextService#hasProjectManifest()}
   */
  @Test
  @DisplayName("Test hasProjectManifest(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ApplicationUpgradeContextService.hasProjectManifest()"})
  void testHasProjectManifest_thenReturnTrue() throws UnsupportedEncodingException {
    // Arrange
    PathMatchingResourcePatternResolver resourceLoader =
        mock(PathMatchingResourcePatternResolver.class);
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act
    boolean actualHasProjectManifestResult =
        new ApplicationUpgradeContextService("Path", 1, true, objectMapper, resourceLoader)
            .hasProjectManifest();

    // Assert
    verify(resourceLoader).getResource("Path");
    assertTrue(actualHasProjectManifestResult);
  }

  /**
   * Test {@link ApplicationUpgradeContextService#hasEnforcedAppVersion()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationUpgradeContextService#hasEnforcedAppVersion()}
   */
  @Test
  @DisplayName("Test hasEnforcedAppVersion(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ApplicationUpgradeContextService.hasEnforcedAppVersion()"})
  void testHasEnforcedAppVersion_thenReturnFalse() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertFalse(
        new ApplicationUpgradeContextService(
                "Path", 0, true, objectMapper, new AnnotationConfigReactiveWebApplicationContext())
            .hasEnforcedAppVersion());
  }

  /**
   * Test {@link ApplicationUpgradeContextService#hasEnforcedAppVersion()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationUpgradeContextService#hasEnforcedAppVersion()}
   */
  @Test
  @DisplayName("Test hasEnforcedAppVersion(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ApplicationUpgradeContextService.hasEnforcedAppVersion()"})
  void testHasEnforcedAppVersion_thenReturnTrue() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertTrue(
        new ApplicationUpgradeContextService(
                "Path", 1, true, objectMapper, new AnnotationConfigReactiveWebApplicationContext())
            .hasEnforcedAppVersion());
  }
}
