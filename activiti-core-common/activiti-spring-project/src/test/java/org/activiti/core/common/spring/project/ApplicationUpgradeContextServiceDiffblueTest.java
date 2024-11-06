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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.activiti.core.common.project.model.ProjectManifest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

class ApplicationUpgradeContextServiceDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ApplicationUpgradeContextService#ApplicationUpgradeContextService(String, Integer, Boolean, ObjectMapper, ResourcePatternResolver)}
   *   <li>{@link ApplicationUpgradeContextService#getEnforcedAppVersion()}
   *   <li>{@link ApplicationUpgradeContextService#isRollbackDeployment()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act
    ApplicationUpgradeContextService actualApplicationUpgradeContextService = new ApplicationUpgradeContextService(
        "Path", 1, true, objectMapper, new AnnotationConfigReactiveWebApplicationContext());
    Integer actualEnforcedAppVersion = actualApplicationUpgradeContextService.getEnforcedAppVersion();
    boolean actualIsRollbackDeploymentResult = actualApplicationUpgradeContextService.isRollbackDeployment();

    // Assert
    assertEquals(1, actualEnforcedAppVersion.intValue());
    assertTrue(actualIsRollbackDeploymentResult);
  }

  /**
   * Test {@link ApplicationUpgradeContextService#loadProjectManifest()}.
   * <p>
   * Method under test:
   * {@link ApplicationUpgradeContextService#loadProjectManifest()}
   */
  @Test
  @DisplayName("Test loadProjectManifest()")
  void testLoadProjectManifest() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertThrows(FileNotFoundException.class, () -> (new ApplicationUpgradeContextService("Path", 1, true, objectMapper,
        new AnnotationConfigReactiveWebApplicationContext())).loadProjectManifest());
  }

  /**
   * Test {@link ApplicationUpgradeContextService#loadProjectManifest()}.
   * <p>
   * Method under test:
   * {@link ApplicationUpgradeContextService#loadProjectManifest()}
   */
  @Test
  @DisplayName("Test loadProjectManifest()")
  void testLoadProjectManifest2() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.readValue(Mockito.<InputStream>any(), Mockito.<Class<ProjectManifest>>any()))
        .thenThrow(new FileNotFoundException("foo"));
    PathMatchingResourcePatternResolver resourceLoader = mock(PathMatchingResourcePatternResolver.class);
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    // Act and Assert
    assertThrows(FileNotFoundException.class,
        () -> (new ApplicationUpgradeContextService("Path", 1, true, objectMapper, resourceLoader))
            .loadProjectManifest());
    verify(objectMapper).readValue(isA(InputStream.class), isA(Class.class));
    verify(resourceLoader).getResource(eq("Path"));
  }

  /**
   * Test {@link ApplicationUpgradeContextService#loadProjectManifest()}.
   * <ul>
   *   <li>Then return {@link ProjectManifest} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ApplicationUpgradeContextService#loadProjectManifest()}
   */
  @Test
  @DisplayName("Test loadProjectManifest(); then return ProjectManifest (default constructor)")
  void testLoadProjectManifest_thenReturnProjectManifest() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProjectManifest projectManifest = new ProjectManifest();
    projectManifest.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    projectManifest.setCreationDate("2020-03-01");
    projectManifest.setDescription("The characteristics of someone or something");
    projectManifest.setId("42");
    projectManifest.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    projectManifest.setLastModifiedDate("2020-03-01");
    projectManifest.setName("Name");
    projectManifest.setVersion("1.0.2");
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.readValue(Mockito.<InputStream>any(), Mockito.<Class<ProjectManifest>>any()))
        .thenReturn(projectManifest);
    PathMatchingResourcePatternResolver resourceLoader = mock(PathMatchingResourcePatternResolver.class);
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    // Act
    ProjectManifest actualLoadProjectManifestResult = (new ApplicationUpgradeContextService("Path", 1, true,
        objectMapper, resourceLoader)).loadProjectManifest();

    // Assert
    verify(objectMapper).readValue(isA(InputStream.class), isA(Class.class));
    verify(resourceLoader).getResource(eq("Path"));
    assertSame(projectManifest, actualLoadProjectManifestResult);
  }

  /**
   * Test {@link ApplicationUpgradeContextService#hasEnforcedAppVersion()}.
   * <p>
   * Method under test:
   * {@link ApplicationUpgradeContextService#hasEnforcedAppVersion()}
   */
  @Test
  @DisplayName("Test hasEnforcedAppVersion()")
  void testHasEnforcedAppVersion() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertTrue((new ApplicationUpgradeContextService("Path", 1, true, objectMapper,
        new AnnotationConfigReactiveWebApplicationContext())).hasEnforcedAppVersion());
  }

  /**
   * Test {@link ApplicationUpgradeContextService#hasEnforcedAppVersion()}.
   * <p>
   * Method under test:
   * {@link ApplicationUpgradeContextService#hasEnforcedAppVersion()}
   */
  @Test
  @DisplayName("Test hasEnforcedAppVersion()")
  void testHasEnforcedAppVersion2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ObjectMapper objectMapper = mock(ObjectMapper.class);

    // Act and Assert
    assertTrue((new ApplicationUpgradeContextService("Path", 1, true, objectMapper,
        new AnnotationConfigReactiveWebApplicationContext())).hasEnforcedAppVersion());
  }

  /**
   * Test {@link ApplicationUpgradeContextService#hasEnforcedAppVersion()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ApplicationUpgradeContextService#hasEnforcedAppVersion()}
   */
  @Test
  @DisplayName("Test hasEnforcedAppVersion(); then return 'false'")
  void testHasEnforcedAppVersion_thenReturnFalse() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertFalse((new ApplicationUpgradeContextService("Path", 0, true, objectMapper,
        new AnnotationConfigReactiveWebApplicationContext())).hasEnforcedAppVersion());
  }
}
