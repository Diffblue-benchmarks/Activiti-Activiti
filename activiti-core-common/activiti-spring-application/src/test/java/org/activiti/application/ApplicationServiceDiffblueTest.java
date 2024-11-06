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
package org.activiti.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

class ApplicationServiceDiffblueTest {
  /**
   * Test {@link ApplicationService#loadApplications()}.
   * <p>
   * Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName("Test loadApplications()")
  void testLoadApplications() throws IOException {
    // Arrange
    PathMatchingResourcePatternResolver resourceLoader = mock(PathMatchingResourcePatternResolver.class);
    when(resourceLoader.getResources(Mockito.<String>any()))
        .thenThrow(new ApplicationLoadException("An error occurred", new Throwable()));
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));
    ApplicationDiscovery applicationDiscovery = new ApplicationDiscovery(resourceLoader, "Applications Location");

    // Act and Assert
    assertThrows(ApplicationLoadException.class,
        () -> (new ApplicationService(applicationDiscovery, new ApplicationReader(new ArrayList<>())))
            .loadApplications());
    verify(resourceLoader).getResource(eq("Applications Location"));
    verify(resourceLoader).getResources(eq("Applications Location**.zip"));
  }

  /**
   * Test {@link ApplicationService#loadApplications()}.
   * <p>
   * Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName("Test loadApplications()")
  void testLoadApplications2() throws IOException {
    // Arrange
    PathMatchingResourcePatternResolver resourceLoader = mock(PathMatchingResourcePatternResolver.class);
    when(resourceLoader.getResources(Mockito.<String>any())).thenReturn(new Resource[]{new ClassPathResource("Path")});
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));
    ApplicationDiscovery applicationDiscovery = new ApplicationDiscovery(resourceLoader, "Applications Location");

    // Act and Assert
    assertThrows(ApplicationLoadException.class,
        () -> (new ApplicationService(applicationDiscovery, new ApplicationReader(new ArrayList<>())))
            .loadApplications());
    verify(resourceLoader).getResource(eq("Applications Location"));
    verify(resourceLoader).getResources(eq("Applications Location**.zip"));
  }

  /**
   * Test {@link ApplicationService#loadApplications()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName("Test loadApplications(); then return Empty")
  void testLoadApplications_thenReturnEmpty() {
    // Arrange
    ApplicationDiscovery applicationDiscovery = new ApplicationDiscovery(
        new AnnotationConfigReactiveWebApplicationContext(), "Applications Location");

    // Act and Assert
    assertTrue(
        (new ApplicationService(applicationDiscovery, new ApplicationReader(new ArrayList<>()))).loadApplications()
            .isEmpty());
  }

  /**
   * Test {@link ApplicationService#loadApplications()}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName("Test loadApplications(); then return size is one")
  void testLoadApplications_thenReturnSizeIsOne() throws IOException {
    // Arrange
    PathMatchingResourcePatternResolver resourceLoader = mock(PathMatchingResourcePatternResolver.class);
    when(resourceLoader.getResources(Mockito.<String>any()))
        .thenReturn(new Resource[]{new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"))});
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));
    ApplicationDiscovery applicationDiscovery = new ApplicationDiscovery(resourceLoader, "Applications Location");

    // Act
    List<ApplicationContent> actualLoadApplicationsResult = (new ApplicationService(applicationDiscovery,
        new ApplicationReader(new ArrayList<>()))).loadApplications();

    // Assert
    verify(resourceLoader).getResource(eq("Applications Location"));
    verify(resourceLoader).getResources(eq("Applications Location**.zip"));
    assertEquals(1, actualLoadApplicationsResult.size());
  }
}
