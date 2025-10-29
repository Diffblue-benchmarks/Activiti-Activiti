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
package org.activiti.image.impl.icon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class TaskIconTypeDiffblueTest {
  /**
   * Method under test: {@link TaskIconType#getAnchorValue()}
   */
  @Test
  public void testGetAnchorValue() {
    // Arrange, Act and Assert
    assertEquals("top left", (new BusinessRuleTaskIconType()).getAnchorValue());
  }

  /**
   * Method under test: {@link TaskIconType#getStrokeValue()}
   */
  @Test
  public void testGetStrokeValue() {
    // Arrange, Act and Assert
    assertNull((new BusinessRuleTaskIconType()).getStrokeValue());
  }

  /**
   * Method under test: {@link TaskIconType#getFillValue()}
   */
  @Test
  public void testGetFillValue() {
    // Arrange, Act and Assert
    assertNull((new BusinessRuleTaskIconType()).getFillValue());
  }

  /**
   * Method under test: {@link TaskIconType#getWidth()}
   */
  @Test
  public void testGetWidth() {
    // Arrange, Act and Assert
    assertNull((new BusinessRuleTaskIconType()).getWidth());
  }

  /**
   * Method under test: {@link TaskIconType#getHeight()}
   */
  @Test
  public void testGetHeight() {
    // Arrange, Act and Assert
    assertNull((new BusinessRuleTaskIconType()).getHeight());
  }

  /**
   * Method under test: {@link TaskIconType#getStrokeWidth()}
   */
  @Test
  public void testGetStrokeWidth() {
    // Arrange, Act and Assert
    assertNull((new BusinessRuleTaskIconType()).getStrokeWidth());
  }
}
