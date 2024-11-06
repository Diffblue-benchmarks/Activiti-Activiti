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
package org.activiti.core.el;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Method;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ActivitiFunctionMapperDiffblueTest {
  /**
   * Test {@link ActivitiFunctionMapper#resolveFunction(String, String)}.
   * <p>
   * Method under test:
   * {@link ActivitiFunctionMapper#resolveFunction(String, String)}
   */
  @Test
  @DisplayName("Test resolveFunction(String, String)")
  void testResolveFunction() {
    // Arrange, Act and Assert
    assertNull((new ActivitiFunctionMapper()).resolveFunction("Prefix", "Local Name"));
  }

  /**
   * Test {@link ActivitiFunctionMapper#setFunction(String, String, Method)}.
   * <p>
   * Method under test:
   * {@link ActivitiFunctionMapper#setFunction(String, String, Method)}
   */
  @Test
  @DisplayName("Test setFunction(String, String, Method)")
  void testSetFunction() {
    // Arrange
    ActivitiFunctionMapper activitiFunctionMapper = new ActivitiFunctionMapper();

    // Act
    activitiFunctionMapper.setFunction("Prefix", "Local Name", null);

    // Assert
    Map<String, Method> stringMethodMap = activitiFunctionMapper.map;
    assertEquals(1, stringMethodMap.size());
    assertNull(stringMethodMap.get("Prefix:Local Name"));
  }

  /**
   * Test new {@link ActivitiFunctionMapper} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ActivitiFunctionMapper}
   */
  @Test
  @DisplayName("Test new ActivitiFunctionMapper (default constructor)")
  void testNewActivitiFunctionMapper() {
    // Arrange, Act and Assert
    assertTrue((new ActivitiFunctionMapper()).map.isEmpty());
  }
}
