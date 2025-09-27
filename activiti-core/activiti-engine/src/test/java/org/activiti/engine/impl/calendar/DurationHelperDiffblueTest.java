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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DurationHelperDiffblueTest {
  /**
   * Test {@link DurationHelper#DurationHelper(String, int, ClockReader)}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DurationHelper#DurationHelper(String, int, ClockReader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DurationHelper.<init>(String, int, ClockReader)"})
  public void testNewDurationHelper_whenSlash_thenThrowActivitiIllegalArgumentException()
      throws Exception {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DurationHelper("/", 3, new DefaultClockImpl()));
  }

  /**
   * Test {@link DurationHelper#DurationHelper(String, ClockReader)}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DurationHelper#DurationHelper(String, ClockReader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DurationHelper.<init>(String, ClockReader)"})
  public void testNewDurationHelper_whenSlash_thenThrowActivitiIllegalArgumentException2()
      throws Exception {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new DurationHelper("/", new DefaultClockImpl()));
  }
}
