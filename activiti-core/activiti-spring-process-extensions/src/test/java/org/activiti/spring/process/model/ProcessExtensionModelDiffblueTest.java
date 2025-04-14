package org.activiti.spring.process.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessExtensionModelDiffblueTest {
  /**
   * Test {@link ProcessExtensionModel#getExtensions(String)}.
   * <p>
   * Method under test: {@link ProcessExtensionModel#getExtensions(String)}
   */
  @Test
  @DisplayName("Test getExtensions(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Extension ProcessExtensionModel.getExtensions(String)"})
  void testGetExtensions() {
    // Arrange, Act and Assert
    assertNull((new ProcessExtensionModel()).getExtensions("Process Definition Key"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ProcessExtensionModel}
   *   <li>{@link ProcessExtensionModel#setExtensions(Map)}
   *   <li>{@link ProcessExtensionModel#setId(String)}
   *   <li>{@link ProcessExtensionModel#getAllExtensions()}
   *   <li>{@link ProcessExtensionModel#getId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessExtensionModel.<init>()", "Map ProcessExtensionModel.getAllExtensions()",
      "String ProcessExtensionModel.getId()", "void ProcessExtensionModel.setExtensions(Map)",
      "void ProcessExtensionModel.setId(String)"})
  void testGettersAndSetters() {
    // Arrange and Act
    ProcessExtensionModel actualProcessExtensionModel = new ProcessExtensionModel();
    HashMap<String, Extension> extensions = new HashMap<>();
    actualProcessExtensionModel.setExtensions(extensions);
    actualProcessExtensionModel.setId("42");
    Map<String, Extension> actualAllExtensions = actualProcessExtensionModel.getAllExtensions();

    // Assert
    assertEquals("42", actualProcessExtensionModel.getId());
    assertTrue(actualAllExtensions.isEmpty());
    assertSame(extensions, actualAllExtensions);
  }
}
