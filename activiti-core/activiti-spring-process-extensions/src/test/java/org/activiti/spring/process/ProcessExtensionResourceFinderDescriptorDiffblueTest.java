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
package org.activiti.spring.process;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessExtensionResourceFinderDescriptorDiffblueTest {
  /**
   * Test
   * {@link ProcessExtensionResourceFinderDescriptor#ProcessExtensionResourceFinderDescriptor(boolean, String, String)}.
   * <p>
   * Method under test:
   * {@link ProcessExtensionResourceFinderDescriptor#ProcessExtensionResourceFinderDescriptor(boolean, String, String)}
   */
  @Test
  @DisplayName("Test new ProcessExtensionResourceFinderDescriptor(boolean, String, String)")
  void testNewProcessExtensionResourceFinderDescriptor() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    ProcessExtensionResourceFinderDescriptor actualProcessExtensionResourceFinderDescriptor = new ProcessExtensionResourceFinderDescriptor(
        true, "Location Prefix", "Location Suffix");

    // Assert
    assertEquals("Location Prefix", actualProcessExtensionResourceFinderDescriptor.getLocationPrefix());
    List<String> locationSuffixes = actualProcessExtensionResourceFinderDescriptor.getLocationSuffixes();
    assertEquals(1, locationSuffixes.size());
    assertEquals("Location Suffix", locationSuffixes.get(0));
    assertEquals("No process extensions were found for auto-deployment in the location 'Location Prefix'",
        actualProcessExtensionResourceFinderDescriptor.getMsgForEmptyResources());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessExtensionResourceFinderDescriptor#validate(List)}
   *   <li>{@link ProcessExtensionResourceFinderDescriptor#getLocationPrefix()}
   *   <li>{@link ProcessExtensionResourceFinderDescriptor#getLocationSuffixes()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor = new ProcessExtensionResourceFinderDescriptor(
        true, "Location Prefix", "Location Suffix");

    // Act
    processExtensionResourceFinderDescriptor.validate(new ArrayList<>());
    String actualLocationPrefix = processExtensionResourceFinderDescriptor.getLocationPrefix();
    List<String> actualLocationSuffixes = processExtensionResourceFinderDescriptor.getLocationSuffixes();

    // Assert that nothing has changed
    assertEquals("Location Prefix", actualLocationPrefix);
    assertEquals(1, actualLocationSuffixes.size());
    assertEquals("Location Suffix", actualLocationSuffixes.get(0));
  }

  /**
   * Test
   * {@link ProcessExtensionResourceFinderDescriptor#shouldLookUpResources()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExtensionResourceFinderDescriptor#shouldLookUpResources()}
   */
  @Test
  @DisplayName("Test shouldLookUpResources(); then return 'false'")
  void testShouldLookUpResources_thenReturnFalse() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertFalse((new ProcessExtensionResourceFinderDescriptor(false, "Location Prefix", "Location Suffix"))
        .shouldLookUpResources());
  }

  /**
   * Test
   * {@link ProcessExtensionResourceFinderDescriptor#shouldLookUpResources()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExtensionResourceFinderDescriptor#shouldLookUpResources()}
   */
  @Test
  @DisplayName("Test shouldLookUpResources(); then return 'true'")
  void testShouldLookUpResources_thenReturnTrue() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertTrue((new ProcessExtensionResourceFinderDescriptor(true, "Location Prefix", "Location Suffix"))
        .shouldLookUpResources());
  }

  /**
   * Test
   * {@link ProcessExtensionResourceFinderDescriptor#getMsgForEmptyResources()}.
   * <p>
   * Method under test:
   * {@link ProcessExtensionResourceFinderDescriptor#getMsgForEmptyResources()}
   */
  @Test
  @DisplayName("Test getMsgForEmptyResources()")
  void testGetMsgForEmptyResources() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertEquals("No process extensions were found for auto-deployment in the location 'Location Prefix'",
        (new ProcessExtensionResourceFinderDescriptor(true, "Location Prefix", "Location Suffix"))
            .getMsgForEmptyResources());
  }

  /**
   * Test
   * {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}.
   * <p>
   * Method under test:
   * {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}
   */
  @Test
  @DisplayName("Test getMsgForResourcesFound(List)")
  void testGetMsgForResourcesFound() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor = new ProcessExtensionResourceFinderDescriptor(
        true, "Location Prefix", "Location Suffix");

    // Act and Assert
    assertEquals("The following process extension files will be deployed: []",
        processExtensionResourceFinderDescriptor.getMsgForResourcesFound(new ArrayList<>()));
  }

  /**
   * Test
   * {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}.
   * <p>
   * Method under test:
   * {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}
   */
  @Test
  @DisplayName("Test getMsgForResourcesFound(List)")
  void testGetMsgForResourcesFound2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor = new ProcessExtensionResourceFinderDescriptor(
        true, "Location Prefix", "Location Suffix");

    ArrayList<String> processExtensionFiles = new ArrayList<>();
    processExtensionFiles.add("foo");

    // Act and Assert
    assertEquals("The following process extension files will be deployed: [foo]",
        processExtensionResourceFinderDescriptor.getMsgForResourcesFound(processExtensionFiles));
  }

  /**
   * Test
   * {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}.
   * <p>
   * Method under test:
   * {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}
   */
  @Test
  @DisplayName("Test getMsgForResourcesFound(List)")
  void testGetMsgForResourcesFound3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor = new ProcessExtensionResourceFinderDescriptor(
        true, "Location Prefix", "Location Suffix");

    ArrayList<String> processExtensionFiles = new ArrayList<>();
    processExtensionFiles.add("42");
    processExtensionFiles.add("foo");

    // Act and Assert
    assertEquals("The following process extension files will be deployed: [42, foo]",
        processExtensionResourceFinderDescriptor.getMsgForResourcesFound(processExtensionFiles));
  }
}
