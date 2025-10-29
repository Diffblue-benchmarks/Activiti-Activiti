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
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;

public class HistoryLevelDiffblueTest {
  /**
   * Method under test: {@link HistoryLevel#getHistoryLevelForKey(String)}
   */
  @Test
  public void testGetHistoryLevelForKey() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> HistoryLevel.getHistoryLevelForKey("Key"));
    assertEquals(HistoryLevel.NONE, HistoryLevel.getHistoryLevelForKey("none"));
  }

  /**
   * Method under test: {@link HistoryLevel#getKey()}
   */
  @Test
  public void testGetKey() {
    // Arrange, Act and Assert
    assertEquals("none", HistoryLevel.valueOf("NONE").getKey());
  }

  /**
   * Method under test: {@link HistoryLevel#isAtLeast(HistoryLevel)}
   */
  @Test
  public void testIsAtLeast() {
    // Arrange, Act and Assert
    assertTrue(HistoryLevel.NONE.isAtLeast(HistoryLevel.NONE));
    assertFalse(HistoryLevel.NONE.isAtLeast(HistoryLevel.ACTIVITY));
  }
}
