package org.activiti.application.discovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessEntryDiscoveryDiffblueTest {
  /**
   * Test {@link ProcessEntryDiscovery#filter(ZipEntry)}.
   * <ul>
   *   <li>Then return not test {@link ZipEntry#ZipEntry(String)} with {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEntryDiscovery#filter(ZipEntry)}
   */
  @Test
  @DisplayName("Test filter(ZipEntry); then return not test ZipEntry(String) with 'foo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Predicate ProcessEntryDiscovery.filter(ZipEntry)"})
  void testFilter_thenReturnNotTestZipEntryWithFoo() {
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
   *   <li>Then return test {@link ZipEntry#ZipEntry(String)} with {@link ProcessEntryDiscovery#PROCESSES}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEntryDiscovery#filter(ZipEntry)}
   */
  @Test
  @DisplayName("Test filter(ZipEntry); then return test ZipEntry(String) with PROCESSES")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Predicate ProcessEntryDiscovery.filter(ZipEntry)"})
  void testFilter_thenReturnTestZipEntryWithProcesses() {
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessEntryDiscovery.<init>()", "java.lang.String ProcessEntryDiscovery.getEntryType()"})
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(ProcessEntryDiscovery.PROCESSES, (new ProcessEntryDiscovery()).getEntryType());
  }
}
