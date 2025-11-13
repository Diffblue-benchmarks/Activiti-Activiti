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
package org.activiti.spring.process.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProcessExtensionModelDiffblueTest {
  /**
   * Test {@link ProcessExtensionModel#getExtensions(String)}.
   *
   * <p>Method under test: {@link ProcessExtensionModel#getExtensions(String)}
   */
  @Test
  @DisplayName("Test getExtensions(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Extension ProcessExtensionModel.getExtensions(String)"})
  void testGetExtensions() {
    // Arrange, Act and Assert
    assertNull(new ProcessExtensionModel().getExtensions("Process Definition Key"));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ProcessExtensionModel.<init>()",
    "Map ProcessExtensionModel.getAllExtensions()",
    "String ProcessExtensionModel.getId()",
    "void ProcessExtensionModel.setExtensions(Map)",
    "void ProcessExtensionModel.setId(String)"
  })
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
