package org.activiti.spring.process;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessExtensionResourceFinderDescriptorDiffblueTest {
  /**
   * Test {@link ProcessExtensionResourceFinderDescriptor#ProcessExtensionResourceFinderDescriptor(boolean, String, String)}.
   * <p>
   * Method under test: {@link ProcessExtensionResourceFinderDescriptor#ProcessExtensionResourceFinderDescriptor(boolean, String, String)}
   */
  @Test
  @DisplayName("Test new ProcessExtensionResourceFinderDescriptor(boolean, String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessExtensionResourceFinderDescriptor.<init>(boolean, String, String)"})
  void testNewProcessExtensionResourceFinderDescriptor() {
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProcessExtensionResourceFinderDescriptor.getLocationPrefix()",
      "List ProcessExtensionResourceFinderDescriptor.getLocationSuffixes()",
      "void ProcessExtensionResourceFinderDescriptor.validate(List)"})
  void testGettersAndSetters() {
    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor = new ProcessExtensionResourceFinderDescriptor(
        true, "Location Prefix", "Location Suffix");

    // Act
    processExtensionResourceFinderDescriptor.validate(new ArrayList<>());
    String actualLocationPrefix = processExtensionResourceFinderDescriptor.getLocationPrefix();
    List<String> actualLocationSuffixes = processExtensionResourceFinderDescriptor.getLocationSuffixes();

    // Assert
    assertEquals("Location Prefix", actualLocationPrefix);
    assertEquals(1, actualLocationSuffixes.size());
    assertEquals("Location Suffix", actualLocationSuffixes.get(0));
  }

  /**
   * Test {@link ProcessExtensionResourceFinderDescriptor#shouldLookUpResources()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExtensionResourceFinderDescriptor#shouldLookUpResources()}
   */
  @Test
  @DisplayName("Test shouldLookUpResources(); then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ProcessExtensionResourceFinderDescriptor.shouldLookUpResources()"})
  void testShouldLookUpResources_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ProcessExtensionResourceFinderDescriptor(false, "Location Prefix", "Location Suffix"))
        .shouldLookUpResources());
  }

  /**
   * Test {@link ProcessExtensionResourceFinderDescriptor#shouldLookUpResources()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessExtensionResourceFinderDescriptor#shouldLookUpResources()}
   */
  @Test
  @DisplayName("Test shouldLookUpResources(); then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ProcessExtensionResourceFinderDescriptor.shouldLookUpResources()"})
  void testShouldLookUpResources_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new ProcessExtensionResourceFinderDescriptor(true, "Location Prefix", "Location Suffix"))
        .shouldLookUpResources());
  }

  /**
   * Test {@link ProcessExtensionResourceFinderDescriptor#getMsgForEmptyResources()}.
   * <p>
   * Method under test: {@link ProcessExtensionResourceFinderDescriptor#getMsgForEmptyResources()}
   */
  @Test
  @DisplayName("Test getMsgForEmptyResources()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProcessExtensionResourceFinderDescriptor.getMsgForEmptyResources()"})
  void testGetMsgForEmptyResources() {
    // Arrange, Act and Assert
    assertEquals("No process extensions were found for auto-deployment in the location 'Location Prefix'",
        (new ProcessExtensionResourceFinderDescriptor(true, "Location Prefix", "Location Suffix"))
            .getMsgForEmptyResources());
  }

  /**
   * Test {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}.
   * <p>
   * Method under test: {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}
   */
  @Test
  @DisplayName("Test getMsgForResourcesFound(List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProcessExtensionResourceFinderDescriptor.getMsgForResourcesFound(List)"})
  void testGetMsgForResourcesFound() {
    // Arrange
    ProcessExtensionResourceFinderDescriptor processExtensionResourceFinderDescriptor = new ProcessExtensionResourceFinderDescriptor(
        true, "Location Prefix", "Location Suffix");

    // Act and Assert
    assertEquals("The following process extension files will be deployed: []",
        processExtensionResourceFinderDescriptor.getMsgForResourcesFound(new ArrayList<>()));
  }

  /**
   * Test {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}.
   * <p>
   * Method under test: {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}
   */
  @Test
  @DisplayName("Test getMsgForResourcesFound(List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProcessExtensionResourceFinderDescriptor.getMsgForResourcesFound(List)"})
  void testGetMsgForResourcesFound2() {
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
   * Test {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}.
   * <p>
   * Method under test: {@link ProcessExtensionResourceFinderDescriptor#getMsgForResourcesFound(List)}
   */
  @Test
  @DisplayName("Test getMsgForResourcesFound(List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ProcessExtensionResourceFinderDescriptor.getMsgForResourcesFound(List)"})
  void testGetMsgForResourcesFound3() {
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
