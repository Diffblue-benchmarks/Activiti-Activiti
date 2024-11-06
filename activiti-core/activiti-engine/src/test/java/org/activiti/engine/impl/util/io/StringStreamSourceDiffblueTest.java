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
package org.activiti.engine.impl.util.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import java.io.IOException;
import org.activiti.engine.ActivitiException;
import org.junit.Test;

public class StringStreamSourceDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code String}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StringStreamSource#StringStreamSource(String)}
   *   <li>{@link StringStreamSource#toString()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenString() {
    // Arrange, Act and Assert
    assertEquals("String", (new StringStreamSource("String")).toString());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StringStreamSource#StringStreamSource(String, String)}
   *   <li>{@link StringStreamSource#toString()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenUtf8() {
    // Arrange, Act and Assert
    assertEquals("String", (new StringStreamSource("String", "UTF-8")).toString());
  }

  /**
   * Test {@link StringStreamSource#getInputStream()}.
   * <ul>
   *   <li>Given {@link StringStreamSource#StringStreamSource(String)} with
   * {@code String}.</li>
   *   <li>Then return read is six.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringStreamSource#getInputStream()}
   */
  @Test
  public void testGetInputStream_givenStringStreamSourceWithString_thenReturnReadIsSix() throws IOException {
    // Arrange, Act and Assert
    byte[] byteArray = new byte[6];
    assertEquals(6, (new StringStreamSource("String")).getInputStream().read(byteArray));
    assertArrayEquals("String".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link StringStreamSource#getInputStream()}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringStreamSource#getInputStream()}
   */
  @Test
  public void testGetInputStream_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class,
        () -> (new StringStreamSource("String", "Byte Array Encoding")).getInputStream());
  }
}
