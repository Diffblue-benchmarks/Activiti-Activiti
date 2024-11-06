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
package org.activiti.application.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessEntryDiscoveryDiffblueTest {
  /**
   * Test {@link ProcessEntryDiscovery#filter(ZipEntry)}.
   * <ul>
   *   <li>Then return not test {@link ZipEntry#ZipEntry(String)} with
   * {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEntryDiscovery#filter(ZipEntry)}
   */
  @Test
  @DisplayName("Test filter(ZipEntry); then return not test ZipEntry(String) with 'foo'")
  void testFilter_thenReturnNotTestZipEntryWithFoo() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEntryDiscovery processEntryDiscovery = new ProcessEntryDiscovery();

    // Act
    Predicate<ZipEntry> actualFilterResult = processEntryDiscovery.filter(new ZipEntry("foo"));

    // Assert
    assertFalse(actualFilterResult.test(new ZipEntry("foo")));
  }

  /**
   * Test {@link ProcessEntryDiscovery#filter(ZipEntry)}.
   * <ul>
   *   <li>Then return test {@link ZipEntry#ZipEntry(String)} with
   * {@link ProcessEntryDiscovery#PROCESSES}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEntryDiscovery#filter(ZipEntry)}
   */
  @Test
  @DisplayName("Test filter(ZipEntry); then return test ZipEntry(String) with PROCESSES")
  void testFilter_thenReturnTestZipEntryWithProcesses() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEntryDiscovery processEntryDiscovery = new ProcessEntryDiscovery();

    // Act
    Predicate<ZipEntry> actualFilterResult = processEntryDiscovery.filter(new ZipEntry("foo"));

    // Assert
    assertTrue(actualFilterResult.test(new ZipEntry(ProcessEntryDiscovery.PROCESSES)));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ProcessEntryDiscovery}
   *   <li>{@link ProcessEntryDiscovery#getEntryType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(ProcessEntryDiscovery.PROCESSES, (new ProcessEntryDiscovery()).getEntryType());
  }
}
