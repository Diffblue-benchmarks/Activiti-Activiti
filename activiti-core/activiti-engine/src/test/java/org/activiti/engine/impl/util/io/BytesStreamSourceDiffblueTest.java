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
import java.io.IOException;
import org.junit.Test;

public class BytesStreamSourceDiffblueTest {
  /**
   * Test {@link BytesStreamSource#BytesStreamSource(byte[])}.
   * <p>
   * Method under test: {@link BytesStreamSource#BytesStreamSource(byte[])}
   */
  @Test
  public void testNewBytesStreamSource() throws IOException {
    // Arrange and Act
    BytesStreamSource actualBytesStreamSource = new BytesStreamSource("AXAXAXAX".getBytes("UTF-8"));

    // Assert
    assertEquals(8, actualBytesStreamSource.getInputStream().read(new byte[8]));
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualBytesStreamSource.bytes);
  }

  /**
   * Test {@link BytesStreamSource#getInputStream()}.
   * <p>
   * Method under test: {@link BytesStreamSource#getInputStream()}
   */
  @Test
  public void testGetInputStream() throws IOException {
    // Arrange, Act and Assert
    byte[] byteArray = new byte[8];
    assertEquals(8, (new BytesStreamSource("AXAXAXAX".getBytes("UTF-8"))).getInputStream().read(byteArray));
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), byteArray);
  }
}
