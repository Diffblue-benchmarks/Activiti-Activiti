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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
   *
   * <p>Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName("Test loadApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationService.loadApplications()"})
  void testLoadApplications() {
    // Arrange
    ApplicationDiscovery applicationDiscovery =
        new ApplicationDiscovery(
            new AnnotationConfigReactiveWebApplicationContext(), "Applications Location");
    ApplicationService applicationService =
        new ApplicationService(applicationDiscovery, new ApplicationReader(new ArrayList<>()));

    // Act and Assert
    assertTrue(applicationService.loadApplications().isEmpty());
  }

  /**
   * Test {@link ApplicationService#loadApplications()}.
   *
   * <p>Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName("Test loadApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationService.loadApplications()"})
  void testLoadApplications2() throws IOException {
    // Arrange
    PathMatchingResourcePatternResolver resourceLoader =
        mock(PathMatchingResourcePatternResolver.class);
    when(resourceLoader.getResources(Mockito.<String>any()))
        .thenReturn(new Resource[] {new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"))});
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));
    ApplicationDiscovery applicationDiscovery =
        new ApplicationDiscovery(resourceLoader, "Applications Location");
    ApplicationService applicationService =
        new ApplicationService(applicationDiscovery, new ApplicationReader(new ArrayList<>()));

    // Act
    List<ApplicationContent> actualLoadApplicationsResult = applicationService.loadApplications();

    // Assert
    verify(resourceLoader).getResource("Applications Location");
    verify(resourceLoader).getResources("Applications Location**.zip");
    assertEquals(1, actualLoadApplicationsResult.size());
  }

  /**
   * Test {@link ApplicationService#loadApplications()}.
   *
   * <p>Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName("Test loadApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationService.loadApplications()"})
  void testLoadApplications3() throws IOException {
    // Arrange
    PathMatchingResourcePatternResolver resourceLoader =
        mock(PathMatchingResourcePatternResolver.class);
    ApplicationLoadException applicationLoadException =
        new ApplicationLoadException("An error occurred", new Throwable());
    when(resourceLoader.getResources(Mockito.<String>any())).thenThrow(applicationLoadException);
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));
    ApplicationDiscovery applicationDiscovery =
        new ApplicationDiscovery(resourceLoader, "Applications Location");
    ApplicationService applicationService =
        new ApplicationService(applicationDiscovery, new ApplicationReader(new ArrayList<>()));

    // Act and Assert
    assertThrows(ApplicationLoadException.class, () -> applicationService.loadApplications());
    verify(resourceLoader).getResource("Applications Location");
    verify(resourceLoader).getResources("Applications Location**.zip");
  }

  /**
   * Test {@link ApplicationService#loadApplications()}.
   *
   * <p>Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName("Test loadApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationService.loadApplications()"})
  void testLoadApplications4() throws IOException {
    // Arrange
    PathMatchingResourcePatternResolver resourceLoader =
        mock(PathMatchingResourcePatternResolver.class);
    when(resourceLoader.getResources(Mockito.<String>any()))
        .thenReturn(new Resource[] {new ClassPathResource("Path")});
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));
    ApplicationDiscovery applicationDiscovery =
        new ApplicationDiscovery(resourceLoader, "Applications Location");
    ApplicationService applicationService =
        new ApplicationService(applicationDiscovery, new ApplicationReader(new ArrayList<>()));

    // Act and Assert
    assertThrows(ApplicationLoadException.class, () -> applicationService.loadApplications());
    verify(resourceLoader).getResource("Applications Location");
    verify(resourceLoader).getResources("Applications Location**.zip");
  }

  /**
   * Test {@link ApplicationService#loadApplications()}.
   *
   * <p>Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName("Test loadApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationService.loadApplications()"})
  void testLoadApplications5() {
    // Arrange
    ApplicationDiscovery applicationDiscovery = mock(ApplicationDiscovery.class);
    when(applicationDiscovery.discoverApplications()).thenReturn(new ArrayList<>());
    ApplicationService applicationService =
        new ApplicationService(applicationDiscovery, new ApplicationReader(new ArrayList<>()));

    // Act
    List<ApplicationContent> actualLoadApplicationsResult = applicationService.loadApplications();

    // Assert
    verify(applicationDiscovery).discoverApplications();
    assertTrue(actualLoadApplicationsResult.isEmpty());
  }

  /**
   * Test {@link ApplicationService#loadApplications()}.
   *
   * <p>Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName("Test loadApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationService.loadApplications()"})
  void testLoadApplications6() throws IOException {
    // Arrange
    ByteArrayResource byteArrayResource = mock(ByteArrayResource.class);
    when(byteArrayResource.getInputStream()).thenReturn(null);

    ArrayList<Resource> resourceList = new ArrayList<>();
    resourceList.add(byteArrayResource);

    ApplicationDiscovery applicationDiscovery = mock(ApplicationDiscovery.class);
    when(applicationDiscovery.discoverApplications()).thenReturn(resourceList);

    ApplicationReader applicationReader = mock(ApplicationReader.class);
    ApplicationLoadException applicationLoadException =
        new ApplicationLoadException("An error occurred", new Throwable());
    when(applicationReader.read(Mockito.<InputStream>any())).thenThrow(applicationLoadException);

    ApplicationService applicationService =
        new ApplicationService(applicationDiscovery, applicationReader);

    // Act and Assert
    assertThrows(ApplicationLoadException.class, () -> applicationService.loadApplications());
    verify(applicationDiscovery).discoverApplications();
    verify(applicationReader).read(isNull());
    verify(byteArrayResource).getInputStream();
  }

  /**
   * Test {@link ApplicationService#loadApplications()}.
   *
   * <ul>
   *   <li>Then return first is {@link ApplicationContent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName(
      "Test loadApplications(); then return first is ApplicationContent (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationService.loadApplications()"})
  void testLoadApplications_thenReturnFirstIsApplicationContent() throws IOException {
    // Arrange
    ByteArrayResource byteArrayResource = mock(ByteArrayResource.class);
    when(byteArrayResource.getInputStream())
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<Resource> resourceList = new ArrayList<>();
    resourceList.add(byteArrayResource);

    ApplicationDiscovery applicationDiscovery = mock(ApplicationDiscovery.class);
    when(applicationDiscovery.discoverApplications()).thenReturn(resourceList);

    ApplicationReader applicationReader = mock(ApplicationReader.class);
    ApplicationContent applicationContent = new ApplicationContent();
    when(applicationReader.read(Mockito.<InputStream>any())).thenReturn(applicationContent);

    ApplicationService applicationService =
        new ApplicationService(applicationDiscovery, applicationReader);

    // Act
    List<ApplicationContent> actualLoadApplicationsResult = applicationService.loadApplications();

    // Assert
    verify(applicationDiscovery).discoverApplications();
    verify(applicationReader).read(isA(InputStream.class));
    verify(byteArrayResource).getInputStream();
    assertEquals(1, actualLoadApplicationsResult.size());
    assertSame(applicationContent, actualLoadApplicationsResult.get(0));
  }

  /**
   * Test {@link ApplicationService#loadApplications()}.
   *
   * <ul>
   *   <li>Then return first is {@link ApplicationContent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName(
      "Test loadApplications(); then return first is ApplicationContent (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ApplicationService.loadApplications()"})
  void testLoadApplications_thenReturnFirstIsApplicationContent2() throws IOException {
    // Arrange
    ByteArrayResource byteArrayResource = mock(ByteArrayResource.class);
    when(byteArrayResource.getInputStream()).thenReturn(null);

    ArrayList<Resource> resourceList = new ArrayList<>();
    resourceList.add(byteArrayResource);

    ApplicationDiscovery applicationDiscovery = mock(ApplicationDiscovery.class);
    when(applicationDiscovery.discoverApplications()).thenReturn(resourceList);

    ApplicationReader applicationReader = mock(ApplicationReader.class);
    ApplicationContent applicationContent = new ApplicationContent();
    when(applicationReader.read(Mockito.<InputStream>any())).thenReturn(applicationContent);

    ApplicationService applicationService =
        new ApplicationService(applicationDiscovery, applicationReader);

    // Act
    List<ApplicationContent> actualLoadApplicationsResult = applicationService.loadApplications();

    // Assert
    verify(applicationDiscovery).discoverApplications();
    verify(applicationReader).read(isNull());
    verify(byteArrayResource).getInputStream();
    assertEquals(1, actualLoadApplicationsResult.size());
    assertSame(applicationContent, actualLoadApplicationsResult.get(0));
  }
}
