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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ResourceFinder.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ResourceFinderDiffblueTest {
  @Autowired
  private ResourceFinder resourceFinder;

  @MockBean
  private ResourcePatternResolver resourcePatternResolver;

  /**
   * Test {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code foo}.</li>
   *   <li>Then throw {@link IOException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}
   */
  @Test
  @DisplayName("Test discoverResources(ResourceFinderDescriptor); given ArrayList() add 'foo'; then throw IOException")
  void testDiscoverResources_givenArrayListAddFoo_thenThrowIOException() throws IOException {
    // Arrange
    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("foo");
    ResourceFinderDescriptor resourceFinderDescriptor = mock(ResourceFinderDescriptor.class);
    when(resourceFinderDescriptor.getLocationPrefix()).thenReturn("Location Prefix");
    doThrow(new IOException("foo")).when(resourceFinderDescriptor).validate(Mockito.<List<Resource>>any());
    when(resourceFinderDescriptor.getLocationSuffixes()).thenReturn(stringList);
    when(resourceFinderDescriptor.shouldLookUpResources()).thenReturn(true);

    // Act and Assert
    assertThrows(IOException.class, () -> resourceFinder.discoverResources(resourceFinderDescriptor));
    verify(resourceFinderDescriptor).getLocationPrefix();
    verify(resourceFinderDescriptor).getLocationSuffixes();
    verify(resourceFinderDescriptor).shouldLookUpResources();
    verify(resourceFinderDescriptor).validate(isA(List.class));
  }

  /**
   * Test {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}
   */
  @Test
  @DisplayName("Test discoverResources(ResourceFinderDescriptor); given 'false'")
  void testDiscoverResources_givenFalse() throws IOException {
    // Arrange
    ResourceFinderDescriptor resourceFinderDescriptor = mock(ResourceFinderDescriptor.class);
    when(resourceFinderDescriptor.shouldLookUpResources()).thenReturn(false);

    // Act
    List<Resource> actualDiscoverResourcesResult = resourceFinder.discoverResources(resourceFinderDescriptor);

    // Assert
    verify(resourceFinderDescriptor).shouldLookUpResources();
    assertTrue(actualDiscoverResourcesResult.isEmpty());
  }

  /**
   * Test {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}.
   * <ul>
   *   <li>Given {@code Msg For Empty Resources}.</li>
   *   <li>Then calls
   * {@link ResourceFinderDescriptor#getMsgForEmptyResources()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}
   */
  @Test
  @DisplayName("Test discoverResources(ResourceFinderDescriptor); given 'Msg For Empty Resources'; then calls getMsgForEmptyResources()")
  void testDiscoverResources_givenMsgForEmptyResources_thenCallsGetMsgForEmptyResources() throws IOException {
    // Arrange
    ResourceFinderDescriptor resourceFinderDescriptor = mock(ResourceFinderDescriptor.class);
    when(resourceFinderDescriptor.getMsgForEmptyResources()).thenReturn("Msg For Empty Resources");
    when(resourceFinderDescriptor.getLocationSuffixes()).thenReturn(new ArrayList<>());
    when(resourceFinderDescriptor.shouldLookUpResources()).thenReturn(true);

    // Act
    List<Resource> actualDiscoverResourcesResult = resourceFinder.discoverResources(resourceFinderDescriptor);

    // Assert
    verify(resourceFinderDescriptor).getLocationSuffixes();
    verify(resourceFinderDescriptor).getMsgForEmptyResources();
    verify(resourceFinderDescriptor).shouldLookUpResources();
    assertTrue(actualDiscoverResourcesResult.isEmpty());
  }

  /**
   * Test {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}
   */
  @Test
  @DisplayName("Test discoverResources(ResourceFinderDescriptor); then return size is one")
  void testDiscoverResources_thenReturnSizeIsOne() throws IOException {
    // Arrange and Act
    List<Resource> actualDiscoverResourcesResult = resourceFinder
        .discoverResources(new DummyResourceFinderDescriptor("Location Prefix", "Suffixes"));

    // Assert
    assertEquals(1, actualDiscoverResourcesResult.size());
    Resource getResult = actualDiscoverResourcesResult.get(0);
    assertFalse(getResult.isFile());
    assertFalse(getResult.isOpen());
  }

  /**
   * Test {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}.
   * <ul>
   *   <li>When
   * {@link DummyResourceFinderDescriptor#DummyResourceFinderDescriptor(String, String[])}
   * with {@code Location Prefix}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ResourceFinder#discoverResources(ResourceFinderDescriptor)}
   */
  @Test
  @DisplayName("Test discoverResources(ResourceFinderDescriptor); when DummyResourceFinderDescriptor(String, String[]) with 'Location Prefix'")
  void testDiscoverResources_whenDummyResourceFinderDescriptorWithLocationPrefix() throws IOException {
    // Arrange, Act and Assert
    assertTrue(resourceFinder.discoverResources(new DummyResourceFinderDescriptor("Location Prefix")).isEmpty());
  }
}
