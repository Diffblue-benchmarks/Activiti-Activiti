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
package org.activiti.engine.impl.transformer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Date;
import org.junit.Test;

public class DateToStringDiffblueTest {
  /**
   * Test {@link DateToString#primTransform(Object)}.
   * <ul>
   *   <li>Given {@link Long#MAX_VALUE}.</li>
   *   <li>When {@link Date} {@link java.util.Date#getTime()} return
   * {@link Long#MAX_VALUE}.</li>
   *   <li>Then calls {@link java.util.Date#getTime()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateToString#primTransform(Object)}
   */
  @Test
  public void testPrimTransform_givenMax_value_whenDateGetTimeReturnMax_value_thenCallsGetTime() throws Exception {
    // Arrange
    DateToString dateToString = new DateToString();
    java.sql.Date date = mock(java.sql.Date.class);
    when(date.getTime()).thenReturn(Long.MAX_VALUE);

    // Act
    dateToString.primTransform(date);

    // Assert
    verify(date).getTime();
  }

  /**
   * Test {@link DateToString#primTransform(Object)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link Date} {@link java.util.Date#getTime()} return ten.</li>
   *   <li>Then calls {@link java.util.Date#getTime()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateToString#primTransform(Object)}
   */
  @Test
  public void testPrimTransform_givenTen_whenDateGetTimeReturnTen_thenCallsGetTime() throws Exception {
    // Arrange
    DateToString dateToString = new DateToString();
    java.sql.Date date = mock(java.sql.Date.class);
    when(date.getTime()).thenReturn(10L);

    // Act
    dateToString.primTransform(date);

    // Assert
    verify(date).getTime();
  }
}
