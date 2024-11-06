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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import java.math.BigDecimal;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class AbstractTransformerDiffblueTest {
  /**
   * Test {@link AbstractTransformer#transform(Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then return {@code 2.3}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractTransformer#transform(Object)}
   */
  @Test
  public void testTransform_whenBigDecimalWith23_thenReturn23() {
    // Arrange
    BigDecimalToString bigDecimalToString = new BigDecimalToString();

    // Act and Assert
    assertEquals("2.3", bigDecimalToString.transform(new BigDecimal("2.3")));
  }

  /**
   * Test {@link AbstractTransformer#transform(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractTransformer#transform(Object)}
   */
  @Test
  public void testTransform_whenNull_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new BigDecimalToString()).transform(JSONObject.NULL));
  }
}
