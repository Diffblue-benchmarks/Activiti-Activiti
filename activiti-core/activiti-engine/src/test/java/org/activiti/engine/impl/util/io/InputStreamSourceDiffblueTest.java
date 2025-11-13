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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.ActivitiException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class InputStreamSourceDiffblueTest {
  /**
   * Test {@link InputStreamSource#toString()}.
   *
   * <p>Method under test: {@link InputStreamSource#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String InputStreamSource.toString()"})
  public void testToString() throws UnsupportedEncodingException {
    // Arrange, Act and Assert
    assertEquals(
        "InputStream",
        new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))).toString());
  }

  /**
   * Test {@link InputStreamSource#getBytesFromInputStream(InputStream)}.
   *
   * <p>Method under test: {@link InputStreamSource#getBytesFromInputStream(InputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] InputStreamSource.getBytesFromInputStream(InputStream)"})
  public void testGetBytesFromInputStream() throws IOException {
    // Arrange
    InputStreamSource inputStreamSource =
        new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
    ByteArrayInputStream inStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act
    byte[] actualBytesFromInputStream = inputStreamSource.getBytesFromInputStream(inStream);

    // Assert
    int actualReadResult = inStream.read(new byte[] {});
    assertEquals(-1, actualReadResult);
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualBytesFromInputStream);
  }

  /**
   * Test {@link InputStreamSource#getBytesFromInputStream(InputStream)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link InputStreamSource#getBytesFromInputStream(InputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] InputStreamSource.getBytesFromInputStream(InputStream)"})
  public void testGetBytesFromInputStream_thenThrowActivitiException() throws IOException {
    // Arrange
    InputStreamSource inputStreamSource =
        new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    DataInputStream inStream = mock(DataInputStream.class);
    when(inStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(-1);
    when(inStream.available()).thenReturn(1);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> inputStreamSource.getBytesFromInputStream(inStream));
    verify(inStream).read(isA(byte[].class), eq(0), eq(1));
    verify(inStream).available();
  }

  /**
   * Test {@link InputStreamSource#getBytesFromInputStream(InputStream)}.
   *
   * <ul>
   *   <li>When {@link DataInputStream} {@link DataInputStream#available()} throw {@link
   *       IOException#IOException()}.
   * </ul>
   *
   * <p>Method under test: {@link InputStreamSource#getBytesFromInputStream(InputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] InputStreamSource.getBytesFromInputStream(InputStream)"})
  public void testGetBytesFromInputStream_whenDataInputStreamAvailableThrowIOException()
      throws IOException {
    // Arrange
    InputStreamSource inputStreamSource =
        new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    DataInputStream inStream = mock(DataInputStream.class);
    when(inStream.available()).thenThrow(new IOException());

    // Act and Assert
    assertThrows(IOException.class, () -> inputStreamSource.getBytesFromInputStream(inStream));
    verify(inStream).available();
  }

  /**
   * Test {@link InputStreamSource#getBytesFromInputStream(InputStream)}.
   *
   * <ul>
   *   <li>When {@link DataInputStream} {@link DataInputStream#read(byte[], int, int)} throw {@link
   *       IOException#IOException()}.
   * </ul>
   *
   * <p>Method under test: {@link InputStreamSource#getBytesFromInputStream(InputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] InputStreamSource.getBytesFromInputStream(InputStream)"})
  public void testGetBytesFromInputStream_whenDataInputStreamReadThrowIOException()
      throws IOException {
    // Arrange
    InputStreamSource inputStreamSource =
        new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));

    DataInputStream inStream = mock(DataInputStream.class);
    when(inStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenThrow(new IOException());
    when(inStream.available()).thenReturn(1);

    // Act and Assert
    assertThrows(IOException.class, () -> inputStreamSource.getBytesFromInputStream(inStream));
    verify(inStream).read(isA(byte[].class), eq(0), eq(1));
    verify(inStream).available();
  }
}
