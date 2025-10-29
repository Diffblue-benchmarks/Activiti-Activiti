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
import org.junit.jupiter.api.Test;

class ProcessEntryDiscoveryDiffblueTest {
  /**
   * Method under test: {@link ProcessEntryDiscovery#filter(ZipEntry)}
   */
  @Test
  void testFilter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEntryDiscovery processEntryDiscovery = new ProcessEntryDiscovery();

    // Act
    Predicate<ZipEntry> actualFilterResult = processEntryDiscovery.filter(new ZipEntry("foo"));

    // Assert
    assertFalse(actualFilterResult.test(new ZipEntry("foo")));
  }

  /**
   * Method under test: {@link ProcessEntryDiscovery#filter(ZipEntry)}
   */
  @Test
  void testFilter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEntryDiscovery processEntryDiscovery = new ProcessEntryDiscovery();

    // Act
    Predicate<ZipEntry> actualFilterResult = processEntryDiscovery.filter(new ZipEntry("foo"));

    // Assert
    assertTrue(actualFilterResult.test(new ZipEntry(ProcessEntryDiscovery.PROCESSES)));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ProcessEntryDiscovery}
   *   <li>{@link ProcessEntryDiscovery#getEntryType()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(ProcessEntryDiscovery.PROCESSES, (new ProcessEntryDiscovery()).getEntryType());
  }
}
