package org.activiti.spring.process.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessConstantsMappingDiffblueTest {
  /**
   * Test new {@link ProcessConstantsMapping} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link ProcessConstantsMapping}
   */
  @Test
  @DisplayName("Test new ProcessConstantsMapping (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ProcessConstantsMapping.<init>()"})
  void testNewProcessConstantsMapping() {
    // Arrange, Act and Assert
    assertTrue((new ProcessConstantsMapping()).isEmpty());
  }
}
