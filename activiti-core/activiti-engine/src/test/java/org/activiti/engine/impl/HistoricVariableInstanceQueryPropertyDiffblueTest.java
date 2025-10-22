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

public class HistoricVariableInstanceQueryPropertyDiffblueTest {
  /**
   * Test {@link HistoricVariableInstanceQueryProperty#HistoricVariableInstanceQueryProperty(String)}.
   * <p>
   * Method under test: {@link HistoricVariableInstanceQueryProperty#HistoricVariableInstanceQueryProperty(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void HistoricVariableInstanceQueryProperty.<init>(String)"})
  public void testNewHistoricVariableInstanceQueryProperty() {
    // Arrange, Act and Assert
    assertEquals("Name", (new HistoricVariableInstanceQueryProperty("Name")).getName());
  }

  /**
   * Test {@link HistoricVariableInstanceQueryProperty#getName()}.
   * <p>
   * Method under test: {@link HistoricVariableInstanceQueryProperty#getName()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String HistoricVariableInstanceQueryProperty.getName()"})
  public void testGetName() {
    // Arrange, Act and Assert
    assertEquals("Name", (new HistoricVariableInstanceQueryProperty("Name")).getName());
  }

  /**
   * Test {@link HistoricVariableInstanceQueryProperty#findByName(String)}.
   * <p>
   * Method under test: {@link HistoricVariableInstanceQueryProperty#findByName(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricVariableInstanceQueryProperty HistoricVariableInstanceQueryProperty.findByName(String)"})
  public void testFindByName() {
    // Arrange, Act and Assert
    assertNull(HistoricVariableInstanceQueryProperty.findByName("Property Name"));
  }
}
