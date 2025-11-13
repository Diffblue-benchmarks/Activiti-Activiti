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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ResourceFinder.class})
@ExtendWith(SpringExtension.class)
class ResourceFinderDiffblueTest {
  @Autowired private ResourceFinder resourceFinder;

  /**
   * Test {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}
   */
  @Test
  @DisplayName("Test discoverResources(ResourceFinderDescriptor); given 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ResourceFinder.discoverResources(ResourceFinderDescriptor)"})
  void testDiscoverResources_givenFalse() throws IOException {
    // Arrange
    ResourceFinderDescriptor resourceFinderDescriptor = mock(ResourceFinderDescriptor.class);
    when(resourceFinderDescriptor.shouldLookUpResources()).thenReturn(false);

    // Act
    List<Resource> actualDiscoverResourcesResult =
        resourceFinder.discoverResources(resourceFinderDescriptor);

    // Assert
    verify(resourceFinderDescriptor).shouldLookUpResources();
    assertTrue(actualDiscoverResourcesResult.isEmpty());
  }

  /**
   * Test {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}.
   *
   * <ul>
   *   <li>Given {@code Msg For Empty Resources}.
   *   <li>Then calls {@link ResourceFinderDescriptor#getMsgForEmptyResources()}.
   * </ul>
   *
   * <p>Method under test: {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}
   */
  @Test
  @DisplayName(
      "Test discoverResources(ResourceFinderDescriptor); given 'Msg For Empty Resources'; then calls getMsgForEmptyResources()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ResourceFinder.discoverResources(ResourceFinderDescriptor)"})
  void testDiscoverResources_givenMsgForEmptyResources_thenCallsGetMsgForEmptyResources()
      throws IOException {
    // Arrange
    ResourceFinderDescriptor resourceFinderDescriptor = mock(ResourceFinderDescriptor.class);
    when(resourceFinderDescriptor.getMsgForEmptyResources()).thenReturn("Msg For Empty Resources");
    when(resourceFinderDescriptor.getLocationSuffixes()).thenReturn(new ArrayList<>());
    when(resourceFinderDescriptor.shouldLookUpResources()).thenReturn(true);

    // Act
    List<Resource> actualDiscoverResourcesResult =
        resourceFinder.discoverResources(resourceFinderDescriptor);

    // Assert
    verify(resourceFinderDescriptor).getLocationSuffixes();
    verify(resourceFinderDescriptor).getMsgForEmptyResources();
    verify(resourceFinderDescriptor).shouldLookUpResources();
    assertTrue(actualDiscoverResourcesResult.isEmpty());
  }

  /**
   * Test {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}.
   *
   * <ul>
   *   <li>Then calls {@link ResourceFinderDescriptor#getLocationPrefix()}.
   * </ul>
   *
   * <p>Method under test: {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}
   */
  @Test
  @DisplayName("Test discoverResources(ResourceFinderDescriptor); then calls getLocationPrefix()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ResourceFinder.discoverResources(ResourceFinderDescriptor)"})
  void testDiscoverResources_thenCallsGetLocationPrefix() throws IOException {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("Location Suffixes");

    ResourceFinderDescriptor resourceFinderDescriptor = mock(ResourceFinderDescriptor.class);
    when(resourceFinderDescriptor.getLocationPrefix()).thenReturn("Location Prefix");
    doThrow(new IOException())
        .when(resourceFinderDescriptor)
        .validate(Mockito.<List<Resource>>any());
    when(resourceFinderDescriptor.getLocationSuffixes()).thenReturn(stringList);
    when(resourceFinderDescriptor.shouldLookUpResources()).thenReturn(true);

    // Act and Assert
    assertThrows(
        IOException.class, () -> resourceFinder.discoverResources(resourceFinderDescriptor));
    verify(resourceFinderDescriptor).getLocationPrefix();
    verify(resourceFinderDescriptor).getLocationSuffixes();
    verify(resourceFinderDescriptor).shouldLookUpResources();
    verify(resourceFinderDescriptor).validate(isA(List.class));
  }

  /**
   * Test {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}.
   *
   * <ul>
   *   <li>Then calls {@link ResourcePatternResolver#getResources(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}
   */
  @Test
  @DisplayName("Test discoverResources(ResourceFinderDescriptor); then calls getResources(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ResourceFinder.discoverResources(ResourceFinderDescriptor)"})
  void testDiscoverResources_thenCallsGetResources() throws IOException {
    // Arrange
    ResourcePatternResolver resourceLoader = mock(ResourcePatternResolver.class);
    when(resourceLoader.getResources(Mockito.<String>any())).thenThrow(new IOException());
    ResourceFinder resourceFinder = new ResourceFinder(resourceLoader);

    // Act and Assert
    assertThrows(
        IOException.class,
        () ->
            resourceFinder.discoverResources(
                new DummyResourceFinderDescriptor("Location Prefix", "Suffixes")));
    verify(resourceLoader).getResources("Location PrefixSuffixes");
  }

  /**
   * Test {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}
   */
  @Test
  @DisplayName("Test discoverResources(ResourceFinderDescriptor); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List ResourceFinder.discoverResources(ResourceFinderDescriptor)"})
  void testDiscoverResources_thenReturnSizeIsOne() throws IOException {
    // Arrange and Act
    List<Resource> actualDiscoverResourcesResult =
        resourceFinder.discoverResources(
            new DummyResourceFinderDescriptor("Location Prefix", "Suffixes"));

    // Assert
    assertEquals(1, actualDiscoverResourcesResult.size());
    Resource getResult = actualDiscoverResourcesResult.get(0);
    assertFalse(getResult.isFile());
    assertFalse(getResult.isOpen());
  }
}
