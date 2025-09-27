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

public class HistoricTaskInstanceQueryPropertyDiffblueTest {
  /**
   * Test {@link HistoricTaskInstanceQueryProperty#HistoricTaskInstanceQueryProperty(String)}.
   *
   * <p>Method under test: {@link
   * HistoricTaskInstanceQueryProperty#HistoricTaskInstanceQueryProperty(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void HistoricTaskInstanceQueryProperty.<init>(String)"})
  public void testNewHistoricTaskInstanceQueryProperty() {
    // Arrange, Act and Assert
    assertEquals("Name", new HistoricTaskInstanceQueryProperty("Name").getName());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryProperty#getName()}.
   *
   * <p>Method under test: {@link HistoricTaskInstanceQueryProperty#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoricTaskInstanceQueryProperty.getName()"})
  public void testGetName() {
    // Arrange, Act and Assert
    assertEquals("Name", new HistoricTaskInstanceQueryProperty("Name").getName());
  }

  /**
   * Test {@link HistoricTaskInstanceQueryProperty#findByName(String)}.
   *
   * <ul>
   *   <li>When {@code Property Name}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceQueryProperty#findByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricTaskInstanceQueryProperty HistoricTaskInstanceQueryProperty.findByName(String)"
  })
  public void testFindByName_whenPropertyName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(HistoricTaskInstanceQueryProperty.findByName("Property Name"));
  }

  /**
   * Test {@link HistoricTaskInstanceQueryProperty#findByName(String)}.
   *
   * <ul>
   *   <li>When {@code RES.ASSIGNEE_}.
   *   <li>Then return Name is {@code RES.ASSIGNEE_}.
   * </ul>
   *
   * <p>Method under test: {@link HistoricTaskInstanceQueryProperty#findByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HistoricTaskInstanceQueryProperty HistoricTaskInstanceQueryProperty.findByName(String)"
  })
  public void testFindByName_whenResAssignee_thenReturnNameIsResAssignee() {
    // Arrange, Act and Assert
    assertEquals(
        "RES.ASSIGNEE_", HistoricTaskInstanceQueryProperty.findByName("RES.ASSIGNEE_").getName());
  }
}
