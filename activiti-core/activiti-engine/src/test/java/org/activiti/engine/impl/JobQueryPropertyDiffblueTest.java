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

public class JobQueryPropertyDiffblueTest {
  /**
   * Test {@link JobQueryProperty#JobQueryProperty(String)}.
   * <p>
   * Method under test: {@link JobQueryProperty#JobQueryProperty(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JobQueryProperty.<init>(String)"})
  public void testNewJobQueryProperty() {
    // Arrange, Act and Assert
    assertEquals("Name", (new JobQueryProperty("Name")).getName());
  }

  /**
   * Test {@link JobQueryProperty#getName()}.
   * <p>
   * Method under test: {@link JobQueryProperty#getName()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JobQueryProperty.getName()"})
  public void testGetName() {
    // Arrange, Act and Assert
    assertEquals("Name", (new JobQueryProperty("Name")).getName());
  }

  /**
   * Test {@link JobQueryProperty#findByName(String)}.
   * <p>
   * Method under test: {@link JobQueryProperty#findByName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JobQueryProperty JobQueryProperty.findByName(String)"})
  public void testFindByName() {
    // Arrange, Act and Assert
    assertNull(JobQueryProperty.findByName("Property Name"));
  }
}
