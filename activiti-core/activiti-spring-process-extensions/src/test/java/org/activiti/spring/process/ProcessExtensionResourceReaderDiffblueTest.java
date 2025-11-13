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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.activiti.spring.process.variable.types.VariableType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessExtensionResourceReader.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ProcessExtensionResourceReaderDiffblueTest {
  @Autowired private Map<String, VariableType> map;

  @MockBean private ObjectMapper objectMapper;

  @Autowired private ProcessExtensionResourceReader processExtensionResourceReader;

  @MockBean private VariableType variableType;

  /**
   * Test {@link ProcessExtensionResourceReader#getResourceNameSelector()}.
   *
   * <ul>
   *   <li>Then return not test {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#getResourceNameSelector()}
   */
  @Test
  @DisplayName("Test getResourceNameSelector(); then return not test 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.function.Predicate ProcessExtensionResourceReader.getResourceNameSelector()"
  })
  void testGetResourceNameSelector_thenReturnNotTestFoo() {
    // Arrange, Act and Assert
    assertFalse(processExtensionResourceReader.getResourceNameSelector().test("foo"));
  }

  /**
   * Test {@link ProcessExtensionResourceReader#getResourceNameSelector()}.
   *
   * <ul>
   *   <li>Then return test {@code -extensions.json}.
   * </ul>
   *
   * <p>Method under test: {@link ProcessExtensionResourceReader#getResourceNameSelector()}
   */
  @Test
  @DisplayName("Test getResourceNameSelector(); then return test '-extensions.json'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.function.Predicate ProcessExtensionResourceReader.getResourceNameSelector()"
  })
  void testGetResourceNameSelector_thenReturnTestExtensionsJson() {
    // Arrange, Act and Assert
    assertTrue(processExtensionResourceReader.getResourceNameSelector().test("-extensions.json"));
  }
}
