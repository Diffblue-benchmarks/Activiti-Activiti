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
package org.activiti.core.common.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProjectManifestDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ProjectManifest}
   *   <li>{@link ProjectManifest#setCreatedBy(String)}
   *   <li>{@link ProjectManifest#setCreationDate(String)}
   *   <li>{@link ProjectManifest#setDescription(String)}
   *   <li>{@link ProjectManifest#setId(String)}
   *   <li>{@link ProjectManifest#setLastModifiedBy(String)}
   *   <li>{@link ProjectManifest#setLastModifiedDate(String)}
   *   <li>{@link ProjectManifest#setName(String)}
   *   <li>{@link ProjectManifest#setVersion(String)}
   *   <li>{@link ProjectManifest#getCreatedBy()}
   *   <li>{@link ProjectManifest#getCreationDate()}
   *   <li>{@link ProjectManifest#getDescription()}
   *   <li>{@link ProjectManifest#getId()}
   *   <li>{@link ProjectManifest#getLastModifiedBy()}
   *   <li>{@link ProjectManifest#getLastModifiedDate()}
   *   <li>{@link ProjectManifest#getName()}
   *   <li>{@link ProjectManifest#getVersion()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ProjectManifest actualProjectManifest = new ProjectManifest();
    actualProjectManifest.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
    actualProjectManifest.setCreationDate("2020-03-01");
    actualProjectManifest.setDescription("The characteristics of someone or something");
    actualProjectManifest.setId("42");
    actualProjectManifest.setLastModifiedBy("Jan 1, 2020 9:00am GMT+0100");
    actualProjectManifest.setLastModifiedDate("2020-03-01");
    actualProjectManifest.setName("Name");
    actualProjectManifest.setVersion("1.0.2");
    String actualCreatedBy = actualProjectManifest.getCreatedBy();
    String actualCreationDate = actualProjectManifest.getCreationDate();
    String actualDescription = actualProjectManifest.getDescription();
    String actualId = actualProjectManifest.getId();
    String actualLastModifiedBy = actualProjectManifest.getLastModifiedBy();
    String actualLastModifiedDate = actualProjectManifest.getLastModifiedDate();
    String actualName = actualProjectManifest.getName();

    // Assert that nothing has changed
    assertEquals("1.0.2", actualProjectManifest.getVersion());
    assertEquals("2020-03-01", actualCreationDate);
    assertEquals("2020-03-01", actualLastModifiedDate);
    assertEquals("42", actualId);
    assertEquals("Jan 1, 2020 8:00am GMT+0100", actualCreatedBy);
    assertEquals("Jan 1, 2020 9:00am GMT+0100", actualLastModifiedBy);
    assertEquals("Name", actualName);
    assertEquals("The characteristics of someone or something", actualDescription);
  }
}
