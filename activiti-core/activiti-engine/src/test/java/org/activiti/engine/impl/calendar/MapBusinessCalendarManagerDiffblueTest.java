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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class MapBusinessCalendarManagerDiffblueTest {
  /**
   * Test {@link MapBusinessCalendarManager#MapBusinessCalendarManager(Map)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link MapBusinessCalendarManager#MapBusinessCalendarManager(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void MapBusinessCalendarManager.<init>(Map)"})
  public void testNewMapBusinessCalendarManager_whenNull_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> new MapBusinessCalendarManager(null));
  }

  /**
   * Test {@link MapBusinessCalendarManager#getBusinessCalendar(String)}.
   *
   * <ul>
   *   <li>Then return {@link DefaultBusinessCalendar}.
   * </ul>
   *
   * <p>Method under test: {@link MapBusinessCalendarManager#getBusinessCalendar(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BusinessCalendar MapBusinessCalendarManager.getBusinessCalendar(String)"})
  public void testGetBusinessCalendar_thenReturnDefaultBusinessCalendar() {
    // Arrange
    MapBusinessCalendarManager mapBusinessCalendarManager = new MapBusinessCalendarManager();
    DefaultBusinessCalendar businessCalendar = new DefaultBusinessCalendar();
    mapBusinessCalendarManager.addBusinessCalendar("Business Calendar Ref", businessCalendar);

    // Act
    BusinessCalendar actualBusinessCalendar =
        mapBusinessCalendarManager.getBusinessCalendar("Business Calendar Ref");

    // Assert
    assertTrue(actualBusinessCalendar instanceof DefaultBusinessCalendar);
    assertSame(businessCalendar, actualBusinessCalendar);
  }

  /**
   * Test {@link MapBusinessCalendarManager#getBusinessCalendar(String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MapBusinessCalendarManager#getBusinessCalendar(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"BusinessCalendar MapBusinessCalendarManager.getBusinessCalendar(String)"})
  public void testGetBusinessCalendar_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new MapBusinessCalendarManager().getBusinessCalendar("Business Calendar Ref"));
  }

  /**
   * Test {@link MapBusinessCalendarManager#addBusinessCalendar(String, BusinessCalendar)}.
   *
   * <ul>
   *   <li>Then return {@link MapBusinessCalendarManager}.
   * </ul>
   *
   * <p>Method under test: {@link MapBusinessCalendarManager#addBusinessCalendar(String,
   * BusinessCalendar)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BusinessCalendarManager MapBusinessCalendarManager.addBusinessCalendar(String, BusinessCalendar)"
  })
  public void testAddBusinessCalendar_thenReturnMapBusinessCalendarManager() {
    // Arrange
    MapBusinessCalendarManager mapBusinessCalendarManager = new MapBusinessCalendarManager();
    DefaultBusinessCalendar businessCalendar = new DefaultBusinessCalendar();

    // Act
    BusinessCalendarManager actualAddBusinessCalendarResult =
        mapBusinessCalendarManager.addBusinessCalendar("Business Calendar Ref", businessCalendar);
    BusinessCalendar actualBusinessCalendar =
        actualAddBusinessCalendarResult.getBusinessCalendar("Business Calendar Ref");

    // Assert
    assertTrue(actualAddBusinessCalendarResult instanceof MapBusinessCalendarManager);
    assertSame(businessCalendar, actualBusinessCalendar);
  }

  /**
   * Test {@link MapBusinessCalendarManager#addBusinessCalendar(String, BusinessCalendar)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link MapBusinessCalendarManager#addBusinessCalendar(String,
   * BusinessCalendar)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BusinessCalendarManager MapBusinessCalendarManager.addBusinessCalendar(String, BusinessCalendar)"
  })
  public void testAddBusinessCalendar_whenNull_thenThrowActivitiException() {
    // Arrange
    MapBusinessCalendarManager mapBusinessCalendarManager = new MapBusinessCalendarManager();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            mapBusinessCalendarManager
                .addBusinessCalendar(null, new DefaultBusinessCalendar())
                .getBusinessCalendar("Business Calendar Ref"));
  }
}
