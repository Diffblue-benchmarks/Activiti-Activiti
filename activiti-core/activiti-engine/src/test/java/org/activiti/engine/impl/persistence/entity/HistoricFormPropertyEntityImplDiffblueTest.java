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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class HistoricFormPropertyEntityImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link HistoricFormPropertyEntityImpl}
   *   <li>{@link HistoricFormPropertyEntityImpl#setPropertyId(String)}
   *   <li>{@link HistoricFormPropertyEntityImpl#setPropertyValue(String)}
   *   <li>{@link HistoricFormPropertyEntityImpl#getPropertyId()}
   *   <li>{@link HistoricFormPropertyEntityImpl#getPropertyValue()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    HistoricFormPropertyEntityImpl actualHistoricFormPropertyEntityImpl = new HistoricFormPropertyEntityImpl();
    actualHistoricFormPropertyEntityImpl.setPropertyId("42");
    actualHistoricFormPropertyEntityImpl.setPropertyValue("42");
    String actualPropertyId = actualHistoricFormPropertyEntityImpl.getPropertyId();

    // Assert that nothing has changed
    assertEquals("42", actualPropertyId);
    assertEquals("42", actualHistoricFormPropertyEntityImpl.getPropertyValue());
    assertEquals("FormProperty", actualHistoricFormPropertyEntityImpl.getDetailType());
    assertFalse(actualHistoricFormPropertyEntityImpl.isDeleted());
    assertFalse(actualHistoricFormPropertyEntityImpl.isInserted());
    assertFalse(actualHistoricFormPropertyEntityImpl.isUpdated());
  }
}
