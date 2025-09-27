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

public class HistoricDetailQueryPropertyDiffblueTest {
  /**
   * Test {@link HistoricDetailQueryProperty#HistoricDetailQueryProperty(String)}.
   *
   * <p>Method under test: {@link HistoricDetailQueryProperty#HistoricDetailQueryProperty(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricDetailQueryProperty.<init>(String)"})
  public void testNewHistoricDetailQueryProperty() {
    // Arrange, Act and Assert
    assertEquals("Name", new HistoricDetailQueryProperty("Name").getName());
  }

  /**
   * Test {@link HistoricDetailQueryProperty#getName()}.
   *
   * <p>Method under test: {@link HistoricDetailQueryProperty#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricDetailQueryProperty.getName()"})
  public void testGetName() {
    // Arrange, Act and Assert
    assertEquals("Name", new HistoricDetailQueryProperty("Name").getName());
  }

  /**
   * Test {@link HistoricDetailQueryProperty#findByName(String)}.
   *
   * <ul>
   *   <li>When {@code NAME_}.
   *   <li>Then return {@code NAME_}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricDetailQueryProperty#findByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoricDetailQueryProperty HistoricDetailQueryProperty.findByName(String)"})
  public void testFindByName_whenName_thenReturnName() {
    // Arrange, Act and Assert
    assertEquals("NAME_", HistoricDetailQueryProperty.findByName("NAME_").getName());
  }

  /**
   * Test {@link HistoricDetailQueryProperty#findByName(String)}.
   *
   * <ul>
   *   <li>When {@code Property Name}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricDetailQueryProperty#findByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoricDetailQueryProperty HistoricDetailQueryProperty.findByName(String)"})
  public void testFindByName_whenPropertyName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(HistoricDetailQueryProperty.findByName("Property Name"));
  }
}
