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
package org.activiti.bpmn.model.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.junit.Test;

public class WarningDiffblueTest {
  /**
   * Test {@link Warning#Warning(String, String, int, int)}.
   * <p>
   * Method under test: {@link Warning#Warning(String, String, int, int)}
   */
  @Test
  public void testNewWarning() {
    // Arrange and Act
    Warning actualWarning = new Warning("Warning Message", "Local Name", 2, 10);

    // Assert
    assertEquals("Local Name", actualWarning.resource);
    assertEquals("Warning Message", actualWarning.warningMessage);
    assertEquals(10, actualWarning.column);
    assertEquals(2, actualWarning.line);
  }

  /**
   * Test {@link Warning#Warning(String, BaseElement)}.
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return {@code Warning Message}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Warning#Warning(String, BaseElement)}
   */
  @Test
  public void testNewWarning_whenActivitiListener_thenReturnWarningMessage() {
    // Arrange and Act
    Warning actualWarning = new Warning("Warning Message", new ActivitiListener());

    // Assert
    assertEquals("Warning Message", actualWarning.warningMessage);
    assertNull(actualWarning.resource);
    assertEquals(0, actualWarning.column);
    assertEquals(0, actualWarning.line);
  }

  /**
   * Test {@link Warning#toString()}.
   * <ul>
   *   <li>Then return {@code Warning Message | line 2 | column 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Warning#toString()}
   */
  @Test
  public void testToString_thenReturnWarningMessageLine2Column10() {
    // Arrange, Act and Assert
    assertEquals("Warning Message | line 2 | column 10", (new Warning("Warning Message", null, 2, 10)).toString());
  }

  /**
   * Test {@link Warning#toString()}.
   * <ul>
   *   <li>Then return
   * {@code Warning Message | Local Name | line 2 | column 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Warning#toString()}
   */
  @Test
  public void testToString_thenReturnWarningMessageLocalNameLine2Column10() {
    // Arrange, Act and Assert
    assertEquals("Warning Message | Local Name | line 2 | column 10",
        (new Warning("Warning Message", "Local Name", 2, 10)).toString());
  }
}
