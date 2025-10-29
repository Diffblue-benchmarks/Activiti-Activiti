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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.junit.Test;

public class MapBusinessCalendarManagerDiffblueTest {
  /**
   * Method under test:
   * {@link MapBusinessCalendarManager#getBusinessCalendar(String)}
   */
  @Test
  public void testGetBusinessCalendar() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new MapBusinessCalendarManager()).getBusinessCalendar("Business Calendar Ref"));
  }

  /**
   * Method under test:
   * {@link MapBusinessCalendarManager#getBusinessCalendar(String)}
   */
  @Test
  public void testGetBusinessCalendar2() {
    // Arrange
    MapBusinessCalendarManager mapBusinessCalendarManager = new MapBusinessCalendarManager();
    DefaultBusinessCalendar businessCalendar = new DefaultBusinessCalendar();
    mapBusinessCalendarManager.addBusinessCalendar("Business Calendar Ref", businessCalendar);

    // Act
    BusinessCalendar actualBusinessCalendar = mapBusinessCalendarManager.getBusinessCalendar("Business Calendar Ref");

    // Assert
    assertTrue(actualBusinessCalendar instanceof DefaultBusinessCalendar);
    assertSame(businessCalendar, actualBusinessCalendar);
  }

  /**
   * Method under test:
   * {@link MapBusinessCalendarManager#addBusinessCalendar(String, BusinessCalendar)}
   */
  @Test
  public void testAddBusinessCalendar() {
    // Arrange
    MapBusinessCalendarManager mapBusinessCalendarManager = new MapBusinessCalendarManager();

    // Act
    BusinessCalendarManager actualAddBusinessCalendarResult = mapBusinessCalendarManager
        .addBusinessCalendar("Business Calendar Ref", new DefaultBusinessCalendar());

    // Assert
    assertTrue(actualAddBusinessCalendarResult instanceof MapBusinessCalendarManager);
    assertSame(mapBusinessCalendarManager, actualAddBusinessCalendarResult);
  }

  /**
   * Method under test:
   * {@link MapBusinessCalendarManager#addBusinessCalendar(String, BusinessCalendar)}
   */
  @Test
  public void testAddBusinessCalendar2() {
    // Arrange
    MapBusinessCalendarManager mapBusinessCalendarManager = new MapBusinessCalendarManager();

    // Act
    BusinessCalendarManager actualAddBusinessCalendarResult = mapBusinessCalendarManager
        .addBusinessCalendar("Business Calendar Ref", mock(DefaultBusinessCalendar.class));

    // Assert
    assertTrue(actualAddBusinessCalendarResult instanceof MapBusinessCalendarManager);
    assertSame(mapBusinessCalendarManager, actualAddBusinessCalendarResult);
  }

  /**
   * Method under test:
   * {@link MapBusinessCalendarManager#MapBusinessCalendarManager(Map)}
   */
  @Test
  public void testNewMapBusinessCalendarManager() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> new MapBusinessCalendarManager(null));
  }
}
