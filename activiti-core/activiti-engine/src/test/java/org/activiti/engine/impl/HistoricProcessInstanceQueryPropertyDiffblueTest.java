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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HistoricProcessInstanceQueryPropertyDiffblueTest {
  /**
   * Test {@link HistoricProcessInstanceQueryProperty#HistoricProcessInstanceQueryProperty(String)}.
   *
   * <p>Method under test: {@link
   * HistoricProcessInstanceQueryProperty#HistoricProcessInstanceQueryProperty(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricProcessInstanceQueryProperty.<init>(String)"})
  public void testNewHistoricProcessInstanceQueryProperty() {
    // Arrange, Act and Assert
    assertEquals("Name", new HistoricProcessInstanceQueryProperty("Name").getName());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryProperty#getName()}.
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryProperty#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricProcessInstanceQueryProperty.getName()"})
  public void testGetName() {
    // Arrange, Act and Assert
    assertEquals("Name", new HistoricProcessInstanceQueryProperty("Name").getName());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryProperty#findByName(String)}.
   *
   * <ul>
   *   <li>When {@code DEF.KEY_}.
   *   <li>Then return Name is {@code DEF.KEY_}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryProperty#findByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQueryProperty HistoricProcessInstanceQueryProperty.findByName(String)"
  })
  public void testFindByName_whenDefKey_thenReturnNameIsDefKey() {
    // Arrange, Act and Assert
    assertEquals("DEF.KEY_", HistoricProcessInstanceQueryProperty.findByName("DEF.KEY_").getName());
  }

  /**
   * Test {@link HistoricProcessInstanceQueryProperty#findByName(String)}.
   *
   * <ul>
   *   <li>When {@code Property Name}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricProcessInstanceQueryProperty#findByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricProcessInstanceQueryProperty HistoricProcessInstanceQueryProperty.findByName(String)"
  })
  public void testFindByName_whenPropertyName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(HistoricProcessInstanceQueryProperty.findByName("Property Name"));
  }
}
