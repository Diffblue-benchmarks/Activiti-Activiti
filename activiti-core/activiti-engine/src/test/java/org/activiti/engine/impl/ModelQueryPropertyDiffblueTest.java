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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ModelQueryPropertyDiffblueTest {
  /**
   * Test {@link ModelQueryProperty#ModelQueryProperty(String)}.
   * <p>
   * Method under test: {@link ModelQueryProperty#ModelQueryProperty(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ModelQueryProperty.<init>(String)"})
  public void testNewModelQueryProperty() {
    // Arrange, Act and Assert
    assertEquals("Name", (new ModelQueryProperty("Name")).getName());
  }

  /**
   * Test {@link ModelQueryProperty#getName()}.
   * <p>
   * Method under test: {@link ModelQueryProperty#getName()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String ModelQueryProperty.getName()"})
  public void testGetName() {
    // Arrange, Act and Assert
    assertEquals("Name", (new ModelQueryProperty("Name")).getName());
  }

  /**
   * Test {@link ModelQueryProperty#findByName(String)}.
   * <p>
   * Method under test: {@link ModelQueryProperty#findByName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ModelQueryProperty ModelQueryProperty.findByName(String)"})
  public void testFindByName() {
    // Arrange, Act and Assert
    assertNull(ModelQueryProperty.findByName("Property Name"));
  }
}
