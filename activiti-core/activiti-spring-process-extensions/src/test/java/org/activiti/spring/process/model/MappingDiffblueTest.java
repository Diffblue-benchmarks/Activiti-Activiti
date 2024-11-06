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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MappingDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Mapping}
   *   <li>{@link Mapping#setType(Mapping.SourceMappingType)}
   *   <li>{@link Mapping#setValue(Object)}
   *   <li>{@link Mapping#getType()}
   *   <li>{@link Mapping#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    Mapping actualMapping = new Mapping();
    actualMapping.setType(Mapping.SourceMappingType.VARIABLE);
    actualMapping.setValue("Value");
    Mapping.SourceMappingType actualType = actualMapping.getType();

    // Assert that nothing has changed
    assertEquals("Value", actualMapping.getValue());
    assertEquals(Mapping.SourceMappingType.VARIABLE, actualType);
  }
}
