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
package org.activiti.engine.test.mock;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.function.BiFunction;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class MocksDiffblueTest {
  /**
   * Test {@link Mocks#get(Object)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Mocks#get(Object)}
   */
  @Test
  public void testGet_givenNull_whenHashMapComputeIfPresentNullAndBiFunction() {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));

    // Act and Assert
    assertNull(Mocks.get(objectObjectMap));
  }

  /**
   * Test {@link Mocks#get(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Mocks#get(Object)}
   */
  @Test
  public void testGet_whenNull() {
    // Arrange, Act and Assert
    assertNull(Mocks.get(JSONObject.NULL));
  }
}
