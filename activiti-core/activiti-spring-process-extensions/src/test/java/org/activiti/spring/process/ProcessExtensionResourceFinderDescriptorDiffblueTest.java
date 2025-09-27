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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessExtensionResourceFinderDescriptorDiffblueTest {
  /**
   * Test {@link
   * ProcessExtensionResourceFinderDescriptor#ProcessExtensionResourceFinderDescriptor(boolean,
   * String, String)}.
   *
   * <p>Method under test: {@link
   * ProcessExtensionResourceFinderDescriptor#ProcessExtensionResourceFinderDescriptor(boolean,
   * String, String)}
   */
  @Test
  @DisplayName("Test new ProcessExtensionResourceFinderDescriptor(boolean, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessExtensionResourceFinderDescriptor.<init>(boolean, String, String)"
  })
  void testNewProcessExtensionResourceFinderDescriptor() {
    // Arrange and Act
    ProcessExtensionResourceFinderDescriptor actualProcessExtensionResourceFinderDescriptor =
        new ProcessExtensionResourceFinderDescriptor(true, "Location Prefix", "Location Suffix");

    // Assert
    assertEquals(
        "Location Prefix", actualProcessExtensionResourceFinderDescriptor.getLocationPrefix());
    List<String> locationSuffixes =
        actualProcessExtensionResourceFinderDescriptor.getLocationSuffixes();
    assertEquals(1, locationSuffixes.size());
    assertEquals("Location Suffix", locationSuffixes.get(0));
    assertEquals(
        "No process extensions were found for auto-deployment in the location 'Location Prefix'",
        actualProcessExtensionResourceFinderDescriptor.getMsgForEmptyResources());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ProcessExtensionResourceFinderDescriptor#validate(List)}
   *   <li>{@link ProcessExtensionResourceFinderDescriptor#getLocationPrefix()}
   *   <li>{@link ProcessExtensionResourceFinderDescriptor#getLocationSuffixes()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ProcessExtensionResourceFinderDescriptor.getLocationPrefix()",
    "List ProcessExtensionResourceFinderDescriptor.getLocationSuffixes()",
    "void ProcessExtensionResourceFinderDescriptor.validate(List)"
  })
  void testGettersAndSetters() {
    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor =
        new ProcessExtensionResourceFinderDescriptor(true, "Location Prefix", "Location Suffix");

    // Act
    processExtensionResourceFinderDescriptor.validate(new ArrayList<>());
    String actualLocationPrefix = processExtensionResourceFinderDescriptor.getLocationPrefix();
    List<String> actualLocationSuffixes =
        processExtensionResourceFinderDescriptor.getLocationSuffixes();

    // Assert
    assertEquals("Location Prefix", actualLocationPrefix);
    assertEquals(1, actualLocationSuffixes.size());
    assertEquals("Location Suffix", actualLocationSuffixes.get(0));
  }

  /**
   * Test {@link ProcessExtensionResourceFinderDescriptor#shouldLookUpResources()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceFinderDescriptor#shouldLookUpResources()}
   */
  @Test
  @DisplayName("Test shouldLookUpResources(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessExtensionResourceFinderDescriptor.shouldLookUpResources()"})
  void testShouldLookUpResources_thenReturnFalse() {
    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor =
        new ProcessExtensionResourceFinderDescriptor(false, "Location Prefix", "Location Suffix");

    // Act and Assert
    assertFalse(processExtensionResourceFinderDescriptor.shouldLookUpResources());
  }

  /**
   * Test {@link ProcessExtensionResourceFinderDescriptor#shouldLookUpResources()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceFinderDescriptor#shouldLookUpResources()}
   */
  @Test
  @DisplayName("Test shouldLookUpResources(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ProcessExtensionResourceFinderDescriptor.shouldLookUpResources()"})
  void testShouldLookUpResources_thenReturnTrue() {
    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor =
        new ProcessExtensionResourceFinderDescriptor(true, "Location Prefix", "Location Suffix");

    // Act and Assert
    assertTrue(processExtensionResourceFinderDescriptor.shouldLookUpResources());
  }

  /**
   * Test {@link ProcessExtensionResourceFinderDescriptor#getMsgForEmptyResources()}.
   *
   * <p>Method under test: {@link
   * ProcessExtensionResourceFinderDescriptor#getMsgForEmptyResources()}
   */
  @Test
  @DisplayName("Test getMsgForEmptyResources()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ProcessExtensionResourceFinderDescriptor.getMsgForEmptyResources()"})
  void testGetMsgForEmptyResources() {
    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor =
        new ProcessExtensionResourceFinderDescriptor(true, "Location Prefix", "Location Suffix");

    // Act and Assert
    assertEquals(
        "No process extensions were found for auto-deployment in the location 'Location Prefix'",
        processExtensionResourceFinderDescriptor.getMsgForEmptyResources());
  }

  /**
   * Test {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}.
   *
   * <p>Method under test: {@link
   * ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}
   */
  @Test
  @DisplayName("Test getMsgForResourcesFound(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ProcessExtensionResourceFinderDescriptor.getMsgForResourcesFound(List)"
  })
  void testGetMsgForResourcesFound() {
    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor =
        new ProcessExtensionResourceFinderDescriptor(true, "Location Prefix", "Location Suffix");

    // Act and Assert
    assertEquals(
        "The following process extension files will be deployed: []",
        processExtensionResourceFinderDescriptor.getMsgForResourcesFound(new ArrayList<>()));
  }

  /**
   * Test {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}.
   *
   * <p>Method under test: {@link
   * ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}
   */
  @Test
  @DisplayName("Test getMsgForResourcesFound(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ProcessExtensionResourceFinderDescriptor.getMsgForResourcesFound(List)"
  })
  void testGetMsgForResourcesFound2() {
    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor =
        new ProcessExtensionResourceFinderDescriptor(true, "Location Prefix", "Location Suffix");

    ArrayList<String> processExtensionFiles = new ArrayList<>();
    processExtensionFiles.add("foo");

    // Act and Assert
    assertEquals(
        "The following process extension files will be deployed: [foo]",
        processExtensionResourceFinderDescriptor.getMsgForResourcesFound(processExtensionFiles));
  }

  /**
   * Test {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}.
   *
   * <p>Method under test: {@link
   * ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}
   */
  @Test
  @DisplayName("Test getMsgForResourcesFound(List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String ProcessExtensionResourceFinderDescriptor.getMsgForResourcesFound(List)"
  })
  void testGetMsgForResourcesFound3() {
    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor =
        new ProcessExtensionResourceFinderDescriptor(true, "Location Prefix", "Location Suffix");

    ArrayList<String> processExtensionFiles = new ArrayList<>();
    processExtensionFiles.add("42");
    processExtensionFiles.add("foo");

    // Act and Assert
    assertEquals(
        "The following process extension files will be deployed: [42, foo]",
        processExtensionResourceFinderDescriptor.getMsgForResourcesFound(processExtensionFiles));
  }
}
