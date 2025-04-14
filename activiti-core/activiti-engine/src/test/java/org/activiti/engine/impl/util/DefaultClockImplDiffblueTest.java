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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.TimeZone;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DefaultClockImplDiffblueTest {
  /**
   * Test {@link DefaultClockImpl#getCurrentTimeZone()}.
   * <p>
   * Method under test: {@link DefaultClockImpl#getCurrentTimeZone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"TimeZone DefaultClockImpl.getCurrentTimeZone()"})
  public void testGetCurrentTimeZone() {
    // Arrange and Act
    TimeZone actualCurrentTimeZone = (new DefaultClockImpl()).getCurrentTimeZone();

    // Assert
    assertEquals("Greenwich Mean Time", actualCurrentTimeZone.getDisplayName());
    assertEquals(3600000, actualCurrentTimeZone.getDSTSavings());
    String expectedID = System.getProperty("user.timezone");
    assertEquals(expectedID, actualCurrentTimeZone.getID());
  }
}
