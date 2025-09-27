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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessEntryDiscovery.class})
@ExtendWith(SpringExtension.class)
class ProcessEntryDiscoveryDiffblueTest {
  @Autowired private ProcessEntryDiscovery processEntryDiscovery;

  /**
   * Test {@link ProcessEntryDiscovery#filter(ZipEntry)}.
   *
   * <ul>
   *   <li>Then return not test {@link ZipEntry#ZipEntry(String)} with {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEntryDiscovery#filter(ZipEntry)}
   */
  @Test
  @DisplayName("Test filter(ZipEntry); then return not test ZipEntry(String) with 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Predicate ProcessEntryDiscovery.filter(ZipEntry)"})
  void testFilter_thenReturnNotTestZipEntryWithFoo() {
    // Arrange and Act
    Predicate<ZipEntry> actualFilterResult = processEntryDiscovery.filter(new ZipEntry("foo"));
    boolean actualTestResult = actualFilterResult.test(new ZipEntry("foo"));

    // Assert
    assertFalse(actualTestResult);
  }

  /**
   * Test {@link ProcessEntryDiscovery#filter(ZipEntry)}.
   *
   * <ul>
   *   <li>Then return test {@link ZipEntry#ZipEntry(String)} with {@link
   *       ProcessEntryDiscovery#PROCESSES}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessEntryDiscovery#filter(ZipEntry)}
   */
  @Test
  @DisplayName("Test filter(ZipEntry); then return test ZipEntry(String) with PROCESSES")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Predicate ProcessEntryDiscovery.filter(ZipEntry)"})
  void testFilter_thenReturnTestZipEntryWithProcesses() {
    // Arrange and Act
    Predicate<ZipEntry> actualFilterResult = processEntryDiscovery.filter(new ZipEntry("foo"));
    boolean actualTestResult =
        actualFilterResult.test(new ZipEntry(ProcessEntryDiscovery.PROCESSES));

    // Assert
    assertTrue(actualTestResult);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ProcessEntryDiscovery}
   *   <li>{@link ProcessEntryDiscovery#getEntryType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessEntryDiscovery.<init>()",
    "java.lang.String ProcessEntryDiscovery.getEntryType()"
  })
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(ProcessEntryDiscovery.PROCESSES, new ProcessEntryDiscovery().getEntryType());
  }
}
