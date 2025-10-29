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
import org.junit.Test;

public class CompensateThrowIconTypeDiffblueTest {
  /**
   * Method under test: {@link CompensateThrowIconType#getWidth()}
   */
  @Test
  public void testGetWidth() {
    // Arrange, Act and Assert
    assertEquals(15, (new CompensateThrowIconType()).getWidth().intValue());
  }

  /**
   * Method under test: {@link CompensateThrowIconType#getHeight()}
   */
  @Test
  public void testGetHeight() {
    // Arrange, Act and Assert
    assertEquals(Short.SIZE, (new CompensateThrowIconType()).getHeight().intValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CompensateThrowIconType}
   *   <li>{@link CompensateThrowIconType#getFillValue()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("#585858", (new CompensateThrowIconType()).getFillValue());
  }
}
