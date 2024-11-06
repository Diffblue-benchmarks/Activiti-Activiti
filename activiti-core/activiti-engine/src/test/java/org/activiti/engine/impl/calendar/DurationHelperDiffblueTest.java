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
package org.activiti.engine.impl.calendar;

import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;

public class DurationHelperDiffblueTest {
  /**
   * Test {@link DurationHelper#DurationHelper(String, int, ClockReader)}.
   * <ul>
   *   <li>When {@code /}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DurationHelper#DurationHelper(String, int, ClockReader)}
   */
  @Test
  public void testNewDurationHelper_whenSlash_thenThrowActivitiIllegalArgumentException() throws Exception {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new DurationHelper("/", 3, new DefaultClockImpl()));

    assertThrows(ActivitiIllegalArgumentException.class, () -> new DurationHelper("/", new DefaultClockImpl()));
  }
}
