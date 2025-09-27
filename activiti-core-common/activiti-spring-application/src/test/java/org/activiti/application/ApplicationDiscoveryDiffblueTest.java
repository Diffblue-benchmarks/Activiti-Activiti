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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

class ApplicationDiscoveryDiffblueTest {
  /**
   * Test {@link ApplicationDiscovery#discoverApplications()}.
   *
   * <p>Method under test: {@link ApplicationDiscovery#discoverApplications()}
   */
  @Test
  @DisplayName("Test discoverApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationDiscovery.discoverApplications()"})
  void testDiscoverApplications() throws IOException {
    // Arrange
    PathMatchingResourcePatternResolver resourceLoader =
        mock(PathMatchingResourcePatternResolver.class);
    when(resourceLoader.getResources(Mockito.<String>any())).thenThrow(new IOException());
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    // Act and Assert
    assertThrows(
        ApplicationLoadException.class,
        () ->
            new ApplicationDiscovery(resourceLoader, "Applications Location")
                .discoverApplications());
    verify(resourceLoader).getResource("Applications Location");
    verify(resourceLoader).getResources("Applications Location**.zip");
  }

  /**
   * Test {@link ApplicationDiscovery#discoverApplications()}.
   *
   * <p>Method under test: {@link ApplicationDiscovery#discoverApplications()}
   */
  @Test
  @DisplayName("Test discoverApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationDiscovery.discoverApplications()"})
  void testDiscoverApplications2() throws IOException {
    // Arrange
    PathMatchingResourcePatternResolver resourceLoader =
        mock(PathMatchingResourcePatternResolver.class);
    ApplicationLoadException applicationLoadException =
        new ApplicationLoadException("An error occurred", new Throwable());
    when(resourceLoader.getResources(Mockito.<String>any())).thenThrow(applicationLoadException);
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    // Act and Assert
    assertThrows(
        ApplicationLoadException.class,
        () ->
            new ApplicationDiscovery(resourceLoader, "Applications Location")
                .discoverApplications());
    verify(resourceLoader).getResource("Applications Location");
    verify(resourceLoader).getResources("Applications Location**.zip");
  }

  /**
   * Test {@link ApplicationDiscovery#discoverApplications()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationDiscovery#discoverApplications()}
   */
  @Test
  @DisplayName("Test discoverApplications(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationDiscovery.discoverApplications()"})
  void testDiscoverApplications_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(
        new ApplicationDiscovery(
                new AnnotationConfigReactiveWebApplicationContext(), "Applications Location")
            .discoverApplications()
            .isEmpty());
  }

  /**
   * Test {@link ApplicationDiscovery#discoverApplications()}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationDiscovery#discoverApplications()}
   */
  @Test
  @DisplayName("Test discoverApplications(); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationDiscovery.discoverApplications()"})
  void testDiscoverApplications_thenReturnSizeIsOne() throws IOException {
    // Arrange
    PathMatchingResourcePatternResolver resourceLoader =
        mock(PathMatchingResourcePatternResolver.class);
    ByteArrayResource byteArrayResource = new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"));
    when(resourceLoader.getResources(Mockito.<String>any()))
        .thenReturn(new Resource[] {byteArrayResource});
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    // Act
    List<Resource> actualDiscoverApplicationsResult =
        new ApplicationDiscovery(resourceLoader, "Applications Location").discoverApplications();

    // Assert
    verify(resourceLoader).getResource("Applications Location");
    verify(resourceLoader).getResources("Applications Location**.zip");
    assertEquals(1, actualDiscoverApplicationsResult.size());
    assertSame(byteArrayResource, actualDiscoverApplicationsResult.get(0));
  }
}
