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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CompensateIconTypeDiffblueTest {
  /**
   * Test {@link CompensateIconType#getWidth()}.
   * <p>
   * Method under test: {@link CompensateIconType#getWidth()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Integer CompensateIconType.getWidth()"})
  public void testGetWidth() {
    // Arrange, Act and Assert
    assertEquals(15, (new CompensateIconType()).getWidth().intValue());
  }

  /**
   * Test {@link CompensateIconType#getHeight()}.
   * <p>
   * Method under test: {@link CompensateIconType#getHeight()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Integer CompensateIconType.getHeight()"})
  public void testGetHeight() {
    // Arrange, Act and Assert
    assertEquals(Short.SIZE, (new CompensateIconType()).getHeight().intValue());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CompensateIconType}
   *   <li>{@link CompensateIconType#getAnchorValue()}
   *   <li>{@link CompensateIconType#getDValue()}
   *   <li>{@link CompensateIconType#getFillValue()}
   *   <li>{@link CompensateIconType#getStrokeValue()}
   *   <li>{@link CompensateIconType#getStrokeWidth()}
   *   <li>{@link CompensateIconType#getStyleValue()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CompensateIconType.<init>()", "String CompensateIconType.getAnchorValue()",
      "String CompensateIconType.getDValue()", "String CompensateIconType.getFillValue()",
      "String CompensateIconType.getStrokeValue()", "String CompensateIconType.getStrokeWidth()",
      "String CompensateIconType.getStyleValue()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    CompensateIconType actualCompensateIconType = new CompensateIconType();
    String actualAnchorValue = actualCompensateIconType.getAnchorValue();
    String actualDValue = actualCompensateIconType.getDValue();
    String actualFillValue = actualCompensateIconType.getFillValue();
    String actualStrokeValue = actualCompensateIconType.getStrokeValue();
    String actualStrokeWidth = actualCompensateIconType.getStrokeWidth();

    // Assert
    assertEquals("#585858", actualStrokeValue);
    assertEquals("1.4", actualStrokeWidth);
    assertEquals("none", actualFillValue);
    assertNull(actualAnchorValue);
    assertNull(actualDValue);
    assertNull(actualCompensateIconType.getStyleValue());
  }
}
