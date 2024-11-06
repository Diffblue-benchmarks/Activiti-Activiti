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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;

public class UrlStreamSourceDiffblueTest {
  /**
   * Test {@link UrlStreamSource#UrlStreamSource(URL)}.
   * <p>
   * Method under test: {@link UrlStreamSource#UrlStreamSource(URL)}
   */
  @Test
  public void testNewUrlStreamSource() throws MalformedURLException {
    // Arrange and Act
    UrlStreamSource actualUrlStreamSource = new UrlStreamSource(
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    // Assert
    String expectedToStringResult = String.join("", "file:",
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toString());
    assertEquals(expectedToStringResult, actualUrlStreamSource.url.toString());
  }

  /**
   * Test {@link UrlStreamSource#getInputStream()}.
   * <ul>
   *   <li>Then return read is fifty-one.</li>
   * </ul>
   * <p>
   * Method under test: {@link UrlStreamSource#getInputStream()}
   */
  @Test
  public void testGetInputStream_thenReturnReadIsFiftyOne() throws IOException {
    // Arrange, Act and Assert
    byte[] byteArray = new byte[51];
    assertEquals(51,
        (new UrlStreamSource(Paths.get(System.getProperty("java.io.tmpdir"), "").toUri().toURL())).getInputStream()
            .read(byteArray));
    assertArrayEquals(".AddressBookLocks\n.java_pid14081\n.java_pid14218\n.ja".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link UrlStreamSource#getInputStream()}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UrlStreamSource#getInputStream()}
   */
  @Test
  public void testGetInputStream_thenThrowActivitiIllegalArgumentException() throws MalformedURLException {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new UrlStreamSource(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL()))
            .getInputStream());
  }
}
