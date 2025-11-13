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
package org.activiti.engine.impl.history;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HistoryLevelDiffblueTest {
  /**
   * Test {@link HistoryLevel#getHistoryLevelForKey(String)}.
   *
   * <ul>
   *   <li>When {@code Key}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link HistoryLevel#getHistoryLevelForKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoryLevel HistoryLevel.getHistoryLevelForKey(String)"})
  public void testGetHistoryLevelForKey_whenKey_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> HistoryLevel.getHistoryLevelForKey("Key"));
  }

  /**
   * Test {@link HistoryLevel#getHistoryLevelForKey(String)}.
   *
   * <ul>
   *   <li>When {@code none}.
   *   <li>Then return {@code NONE}.
   * </ul>
   *
   * <p>Method under test: {@link HistoryLevel#getHistoryLevelForKey(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"HistoryLevel HistoryLevel.getHistoryLevelForKey(String)"})
  public void testGetHistoryLevelForKey_whenNone_thenReturnNone() {
    // Arrange, Act and Assert
    assertEquals(HistoryLevel.NONE, HistoryLevel.getHistoryLevelForKey("none"));
  }

  /**
   * Test {@link HistoryLevel#getKey()}.
   *
   * <p>Method under test: {@link HistoryLevel#getKey()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String HistoryLevel.getKey()"})
  public void testGetKey() {
    // Arrange, Act and Assert
    assertEquals("none", HistoryLevel.valueOf("NONE").getKey());
  }

  /**
   * Test {@link HistoryLevel#isAtLeast(HistoryLevel)}.
   *
   * <ul>
   *   <li>When {@code ACTIVITY}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link HistoryLevel#isAtLeast(HistoryLevel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean HistoryLevel.isAtLeast(HistoryLevel)"})
  public void testIsAtLeast_whenActivity_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(HistoryLevel.NONE.isAtLeast(HistoryLevel.ACTIVITY));
  }

  /**
   * Test {@link HistoryLevel#isAtLeast(HistoryLevel)}.
   *
   * <ul>
   *   <li>When {@code NONE}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link HistoryLevel#isAtLeast(HistoryLevel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean HistoryLevel.isAtLeast(HistoryLevel)"})
  public void testIsAtLeast_whenNone_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(HistoryLevel.NONE.isAtLeast(HistoryLevel.NONE));
  }
}
