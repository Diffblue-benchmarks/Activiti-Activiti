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
package org.activiti.engine.impl.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimerUtilDiffblueTest {
  @InjectMocks
  private TimerUtil timerUtil;

  /**
   * Test {@link TimerUtil#prepareRepeat(String)}.
   * <ul>
   *   <li>When {@code 2020-03-01}.</li>
   *   <li>Then return {@code 2020-03-01}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerUtil#prepareRepeat(String)}
   */
  @Test
  public void testPrepareRepeat_when20200301_thenReturn20200301() {
    // Arrange, Act and Assert
    assertEquals("2020-03-01", TimerUtil.prepareRepeat("2020-03-01"));
  }

  /**
   * Test {@link TimerUtil#prepareRepeat(String)}.
   * <ul>
   *   <li>When {@code R}.</li>
   *   <li>Then return {@code R}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerUtil#prepareRepeat(String)}
   */
  @Test
  public void testPrepareRepeat_whenR_thenReturnR() {
    // Arrange, Act and Assert
    assertEquals("R", TimerUtil.prepareRepeat("R"));
  }
}
