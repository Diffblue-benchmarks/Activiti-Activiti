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

public class HistoricActivityInstanceQueryPropertyDiffblueTest {
  /**
   * Test {@link
   * HistoricActivityInstanceQueryProperty#HistoricActivityInstanceQueryProperty(String)}.
   *
   * <p>Method under test: {@link
   * HistoricActivityInstanceQueryProperty#HistoricActivityInstanceQueryProperty(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricActivityInstanceQueryProperty.<init>(String)"})
  public void testNewHistoricActivityInstanceQueryProperty() {
    // Arrange, Act and Assert
    assertEquals("Name", new HistoricActivityInstanceQueryProperty("Name").getName());
  }

  /**
   * Test {@link HistoricActivityInstanceQueryProperty#getName()}.
   *
   * <p>Method under test: {@link HistoricActivityInstanceQueryProperty#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricActivityInstanceQueryProperty.getName()"})
  public void testGetName() {
    // Arrange, Act and Assert
    assertEquals("Name", new HistoricActivityInstanceQueryProperty("Name").getName());
  }

  /**
   * Test {@link HistoricActivityInstanceQueryProperty#findByName(String)}.
   *
   * <ul>
   *   <li>When {@code ACT_ID_}.
   *   <li>Then return Name is {@code ACT_ID_}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricActivityInstanceQueryProperty#findByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricActivityInstanceQueryProperty HistoricActivityInstanceQueryProperty.findByName(String)"
  })
  public void testFindByName_whenActId_thenReturnNameIsActId() {
    // Arrange, Act and Assert
    assertEquals("ACT_ID_", HistoricActivityInstanceQueryProperty.findByName("ACT_ID_").getName());
  }

  /**
   * Test {@link HistoricActivityInstanceQueryProperty#findByName(String)}.
   *
   * <ul>
   *   <li>When {@code Property Name}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricActivityInstanceQueryProperty#findByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricActivityInstanceQueryProperty HistoricActivityInstanceQueryProperty.findByName(String)"
  })
  public void testFindByName_whenPropertyName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(HistoricActivityInstanceQueryProperty.findByName("Property Name"));
  }
}
