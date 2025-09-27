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
package org.activiti.spring.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import java.util.function.Predicate;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeploymentResourceLoaderDiffblueTest {
  /**
   * Test {@link DeploymentResourceLoader#loadResourcesForDeployment(String, ResourceReader)}.
   *
   * <p>Method under test: {@link DeploymentResourceLoader#loadResourcesForDeployment(String,
   * ResourceReader)}
   */
  @Test
  @DisplayName("Test loadResourcesForDeployment(String, ResourceReader)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeploymentResourceLoader.loadResourcesForDeployment(String, ResourceReader)"
  })
  void testLoadResourcesForDeployment() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");

    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.getDeploymentResourceNames(Mockito.<String>any()))
        .thenReturn(stringList);

    DeploymentResourceLoader<Object> deploymentResourceLoader = new DeploymentResourceLoader<>();
    deploymentResourceLoader.setRepositoryService(repositoryService);

    ResourceReader<Object> resourceLoaderDescriptor = mock(ResourceReader.class);
    when(resourceLoaderDescriptor.getResourceNameSelector()).thenThrow(new IllegalStateException());

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> deploymentResourceLoader.loadResourcesForDeployment("42", resourceLoaderDescriptor));
    verify(repositoryService).getDeploymentResourceNames("42");
    verify(resourceLoaderDescriptor).getResourceNameSelector();
  }

  /**
   * Test {@link DeploymentResourceLoader#loadResourcesForDeployment(String, ResourceReader)}.
   *
   * <p>Method under test: {@link DeploymentResourceLoader#loadResourcesForDeployment(String,
   * ResourceReader)}
   */
  @Test
  @DisplayName("Test loadResourcesForDeployment(String, ResourceReader)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeploymentResourceLoader.loadResourcesForDeployment(String, ResourceReader)"
  })
  void testLoadResourcesForDeployment2() {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");

    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.getResourceAsStream(Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new IllegalStateException());
    when(repositoryService.getDeploymentResourceNames(Mockito.<String>any()))
        .thenReturn(stringList);

    DeploymentResourceLoader<Object> deploymentResourceLoader = new DeploymentResourceLoader<>();
    deploymentResourceLoader.setRepositoryService(repositoryService);

    Predicate<String> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<String>any())).thenReturn(true);

    ResourceReader<Object> resourceLoaderDescriptor = mock(ResourceReader.class);
    when(resourceLoaderDescriptor.getResourceNameSelector()).thenReturn(predicate);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> deploymentResourceLoader.loadResourcesForDeployment("42", resourceLoaderDescriptor));
    verify(predicate).test("foo");
    verify(repositoryService).getDeploymentResourceNames("42");
    verify(repositoryService).getResourceAsStream("42", "foo");
    verify(resourceLoaderDescriptor).getResourceNameSelector();
  }

  /**
   * Test {@link DeploymentResourceLoader#loadResourcesForDeployment(String, ResourceReader)}.
   *
   * <p>Method under test: {@link DeploymentResourceLoader#loadResourcesForDeployment(String,
   * ResourceReader)}
   */
  @Test
  @DisplayName("Test loadResourcesForDeployment(String, ResourceReader)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeploymentResourceLoader.loadResourcesForDeployment(String, ResourceReader)"
  })
  void testLoadResourcesForDeployment3() throws IOException {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");

    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.getResourceAsStream(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(null);
    when(repositoryService.getDeploymentResourceNames(Mockito.<String>any()))
        .thenReturn(stringList);

    DeploymentResourceLoader<Object> deploymentResourceLoader = new DeploymentResourceLoader<>();
    deploymentResourceLoader.setRepositoryService(repositoryService);

    Predicate<String> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<String>any())).thenReturn(true);

    ResourceReader<Object> resourceLoaderDescriptor = mock(ResourceReader.class);
    when(resourceLoaderDescriptor.read(Mockito.<InputStream>any())).thenReturn(null);
    when(resourceLoaderDescriptor.getResourceNameSelector()).thenReturn(predicate);

    // Act
    List<Object> actualLoadResourcesForDeploymentResult =
        deploymentResourceLoader.loadResourcesForDeployment("42", resourceLoaderDescriptor);

    // Assert
    verify(predicate).test("foo");
    verify(repositoryService).getDeploymentResourceNames("42");
    verify(repositoryService).getResourceAsStream("42", "foo");
    verify(resourceLoaderDescriptor).getResourceNameSelector();
    verify(resourceLoaderDescriptor).read(isNull());
    assertTrue(actualLoadResourcesForDeploymentResult.isEmpty());
  }

  /**
   * Test {@link DeploymentResourceLoader#loadResourcesForDeployment(String, ResourceReader)}.
   *
   * <p>Method under test: {@link DeploymentResourceLoader#loadResourcesForDeployment(String,
   * ResourceReader)}
   */
  @Test
  @DisplayName("Test loadResourcesForDeployment(String, ResourceReader)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeploymentResourceLoader.loadResourcesForDeployment(String, ResourceReader)"
  })
  void testLoadResourcesForDeployment4() throws IOException {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");

    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.getResourceAsStream(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(null);
    when(repositoryService.getDeploymentResourceNames(Mockito.<String>any()))
        .thenReturn(stringList);

    DeploymentResourceLoader<Object> deploymentResourceLoader = new DeploymentResourceLoader<>();
    deploymentResourceLoader.setRepositoryService(repositoryService);

    Predicate<String> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<String>any())).thenReturn(true);

    ResourceReader<Object> resourceLoaderDescriptor = mock(ResourceReader.class);
    when(resourceLoaderDescriptor.read(Mockito.<InputStream>any())).thenThrow(new IOException());
    when(resourceLoaderDescriptor.getResourceNameSelector()).thenReturn(predicate);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> deploymentResourceLoader.loadResourcesForDeployment("42", resourceLoaderDescriptor));
    verify(predicate).test("foo");
    verify(repositoryService).getDeploymentResourceNames("42");
    verify(repositoryService).getResourceAsStream("42", "foo");
    verify(resourceLoaderDescriptor).getResourceNameSelector();
    verify(resourceLoaderDescriptor).read(isNull());
  }

  /**
   * Test {@link DeploymentResourceLoader#loadResourcesForDeployment(String, ResourceReader)}.
   *
   * <ul>
   *   <li>Given {@link IOException#IOException()}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentResourceLoader#loadResourcesForDeployment(String,
   * ResourceReader)}
   */
  @Test
  @DisplayName("Test loadResourcesForDeployment(String, ResourceReader); given IOException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeploymentResourceLoader.loadResourcesForDeployment(String, ResourceReader)"
  })
  void testLoadResourcesForDeployment_givenIOException() throws IOException {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");

    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.getResourceAsStream(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
    when(repositoryService.getDeploymentResourceNames(Mockito.<String>any()))
        .thenReturn(stringList);

    DeploymentResourceLoader<Object> deploymentResourceLoader = new DeploymentResourceLoader<>();
    deploymentResourceLoader.setRepositoryService(repositoryService);

    Predicate<String> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<String>any())).thenReturn(true);

    ResourceReader<Object> resourceLoaderDescriptor = mock(ResourceReader.class);
    when(resourceLoaderDescriptor.read(Mockito.<InputStream>any())).thenThrow(new IOException());
    when(resourceLoaderDescriptor.getResourceNameSelector()).thenReturn(predicate);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> deploymentResourceLoader.loadResourcesForDeployment("42", resourceLoaderDescriptor));
    verify(predicate).test("foo");
    verify(repositoryService).getDeploymentResourceNames("42");
    verify(repositoryService).getResourceAsStream("42", "foo");
    verify(resourceLoaderDescriptor).getResourceNameSelector();
    verify(resourceLoaderDescriptor).read(isA(InputStream.class));
  }

  /**
   * Test {@link DeploymentResourceLoader#loadResourcesForDeployment(String, ResourceReader)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ResourceReader} {@link ResourceReader#read(InputStream)} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentResourceLoader#loadResourcesForDeployment(String,
   * ResourceReader)}
   */
  @Test
  @DisplayName(
      "Test loadResourcesForDeployment(String, ResourceReader); given 'null'; when ResourceReader read(InputStream) return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeploymentResourceLoader.loadResourcesForDeployment(String, ResourceReader)"
  })
  void testLoadResourcesForDeployment_givenNull_whenResourceReaderReadReturnNull()
      throws IOException {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");

    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.getResourceAsStream(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
    when(repositoryService.getDeploymentResourceNames(Mockito.<String>any()))
        .thenReturn(stringList);

    DeploymentResourceLoader<Object> deploymentResourceLoader = new DeploymentResourceLoader<>();
    deploymentResourceLoader.setRepositoryService(repositoryService);

    Predicate<String> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<String>any())).thenReturn(true);

    ResourceReader<Object> resourceLoaderDescriptor = mock(ResourceReader.class);
    when(resourceLoaderDescriptor.read(Mockito.<InputStream>any())).thenReturn(null);
    when(resourceLoaderDescriptor.getResourceNameSelector()).thenReturn(predicate);

    // Act
    List<Object> actualLoadResourcesForDeploymentResult =
        deploymentResourceLoader.loadResourcesForDeployment("42", resourceLoaderDescriptor);

    // Assert
    verify(predicate).test("foo");
    verify(repositoryService).getDeploymentResourceNames("42");
    verify(repositoryService).getResourceAsStream("42", "foo");
    verify(resourceLoaderDescriptor).getResourceNameSelector();
    verify(resourceLoaderDescriptor).read(isA(InputStream.class));
    assertTrue(actualLoadResourcesForDeploymentResult.isEmpty());
  }

  /**
   * Test {@link DeploymentResourceLoader#loadResourcesForDeployment(String, ResourceReader)}.
   *
   * <ul>
   *   <li>Given {@code Read}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentResourceLoader#loadResourcesForDeployment(String,
   * ResourceReader)}
   */
  @Test
  @DisplayName(
      "Test loadResourcesForDeployment(String, ResourceReader); given 'Read'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeploymentResourceLoader.loadResourcesForDeployment(String, ResourceReader)"
  })
  void testLoadResourcesForDeployment_givenRead_thenReturnSizeIsOne() throws IOException {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");

    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.getResourceAsStream(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
    when(repositoryService.getDeploymentResourceNames(Mockito.<String>any()))
        .thenReturn(stringList);

    DeploymentResourceLoader<Object> deploymentResourceLoader = new DeploymentResourceLoader<>();
    deploymentResourceLoader.setRepositoryService(repositoryService);

    Predicate<String> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<String>any())).thenReturn(true);

    ResourceReader<Object> resourceLoaderDescriptor = mock(ResourceReader.class);
    when(resourceLoaderDescriptor.read(Mockito.<InputStream>any())).thenReturn("Read");
    when(resourceLoaderDescriptor.getResourceNameSelector()).thenReturn(predicate);

    // Act
    List<Object> actualLoadResourcesForDeploymentResult =
        deploymentResourceLoader.loadResourcesForDeployment("42", resourceLoaderDescriptor);

    // Assert
    verify(predicate).test("foo");
    verify(repositoryService).getDeploymentResourceNames("42");
    verify(repositoryService).getResourceAsStream("42", "foo");
    verify(resourceLoaderDescriptor).getResourceNameSelector();
    verify(resourceLoaderDescriptor).read(isA(InputStream.class));
    assertEquals(1, actualLoadResourcesForDeploymentResult.size());
    assertEquals("Read", actualLoadResourcesForDeploymentResult.get(0));
  }

  /**
   * Test {@link DeploymentResourceLoader#loadResourcesForDeployment(String, ResourceReader)}.
   *
   * <ul>
   *   <li>When {@link ResourceReader} {@link ResourceReader#read(InputStream)} throw {@link
   *       IllegalStateException#IllegalStateException()}.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentResourceLoader#loadResourcesForDeployment(String,
   * ResourceReader)}
   */
  @Test
  @DisplayName(
      "Test loadResourcesForDeployment(String, ResourceReader); when ResourceReader read(InputStream) throw IllegalStateException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeploymentResourceLoader.loadResourcesForDeployment(String, ResourceReader)"
  })
  void testLoadResourcesForDeployment_whenResourceReaderReadThrowIllegalStateException()
      throws IOException {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");

    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.getResourceAsStream(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
    when(repositoryService.getDeploymentResourceNames(Mockito.<String>any()))
        .thenReturn(stringList);

    DeploymentResourceLoader<Object> deploymentResourceLoader = new DeploymentResourceLoader<>();
    deploymentResourceLoader.setRepositoryService(repositoryService);

    Predicate<String> predicate = mock(Predicate.class);
    when(predicate.test(Mockito.<String>any())).thenReturn(true);

    ResourceReader<Object> resourceLoaderDescriptor = mock(ResourceReader.class);
    when(resourceLoaderDescriptor.read(Mockito.<InputStream>any()))
        .thenThrow(new IllegalStateException());
    when(resourceLoaderDescriptor.getResourceNameSelector()).thenReturn(predicate);

    // Act and Assert
    assertThrows(
        IllegalStateException.class,
        () -> deploymentResourceLoader.loadResourcesForDeployment("42", resourceLoaderDescriptor));
    verify(predicate).test("foo");
    verify(repositoryService).getDeploymentResourceNames("42");
    verify(repositoryService).getResourceAsStream("42", "foo");
    verify(resourceLoaderDescriptor).getResourceNameSelector();
    verify(resourceLoaderDescriptor).read(isA(InputStream.class));
  }

  /**
   * Test {@link DeploymentResourceLoader#loadResourcesForDeployment(String, ResourceReader)}.
   *
   * <ul>
   *   <li>When {@link ResourceReader}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentResourceLoader#loadResourcesForDeployment(String,
   * ResourceReader)}
   */
  @Test
  @DisplayName(
      "Test loadResourcesForDeployment(String, ResourceReader); when ResourceReader; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeploymentResourceLoader.loadResourcesForDeployment(String, ResourceReader)"
  })
  void testLoadResourcesForDeployment_whenResourceReader_thenReturnEmpty() {
    // Arrange
    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    when(repositoryService.getDeploymentResourceNames(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());

    DeploymentResourceLoader<Object> deploymentResourceLoader = new DeploymentResourceLoader<>();
    deploymentResourceLoader.setRepositoryService(repositoryService);

    // Act
    List<Object> actualLoadResourcesForDeploymentResult =
        deploymentResourceLoader.loadResourcesForDeployment("42", mock(ResourceReader.class));

    // Assert
    verify(repositoryService).getDeploymentResourceNames("42");
    assertTrue(actualLoadResourcesForDeploymentResult.isEmpty());
  }
}
