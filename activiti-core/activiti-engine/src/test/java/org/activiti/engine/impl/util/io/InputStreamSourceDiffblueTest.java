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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.ActivitiException;
import org.junit.Test;
import org.mockito.Mockito;

public class InputStreamSourceDiffblueTest {
  /**
   * Method under test: {@link InputStreamSource#getInputStream()}
   */
  @Test
  public void testGetInputStream() throws IOException {
    // Arrange, Act and Assert
    byte[] byteArray = new byte[8];
    assertEquals(8, (new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))).getInputStream()
        .read(byteArray));
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test: {@link InputStreamSource#getInputStream()}
   */
  @Test
  public void testGetInputStream2() throws IOException {
    // Arrange
    DataInputStream inputStream = mock(DataInputStream.class);
    when(inputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(1);
    when(inputStream.available()).thenReturn(1);
    doNothing().when(inputStream).close();

    // Act
    InputStream actualInputStream = (new InputStreamSource(inputStream)).getInputStream();

    // Assert
    verify(inputStream).read(isA(byte[].class), eq(0), eq(8192));
    verify(inputStream).available();
    verify(inputStream).close();
    byte[] byteArray = new byte[1];
    assertEquals(1, actualInputStream.read(byteArray));
    assertArrayEquals(new byte[]{0}, byteArray);
  }

  /**
   * Method under test: {@link InputStreamSource#getInputStream()}
   */
  @Test
  public void testGetInputStream3() throws IOException {
    // Arrange
    DataInputStream inputStream = mock(DataInputStream.class);
    when(inputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt()))
        .thenThrow(new IOException("Could not completely read inputstream "));
    when(inputStream.available()).thenReturn(1);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> (new InputStreamSource(inputStream)).getInputStream());
    verify(inputStream).read(isA(byte[].class), eq(0), eq(8192));
    verify(inputStream).available();
  }

  /**
   * Method under test: {@link InputStreamSource#getInputStream()}
   */
  @Test
  public void testGetInputStream4() throws IOException {
    // Arrange
    DataInputStream inputStream = mock(DataInputStream.class);
    when(inputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt()))
        .thenThrow(new ActivitiException("An error occurred"));
    when(inputStream.available()).thenReturn(1);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> (new InputStreamSource(inputStream)).getInputStream());
    verify(inputStream).read(isA(byte[].class), eq(0), eq(8192));
    verify(inputStream).available();
  }

  /**
   * Method under test: {@link InputStreamSource#getInputStream()}
   */
  @Test
  public void testGetInputStream5() throws IOException {
    // Arrange
    DataInputStream inputStream = mock(DataInputStream.class);
    when(inputStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(0);
    when(inputStream.available()).thenReturn(1);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> (new InputStreamSource(inputStream)).getInputStream());
    verify(inputStream).read(isA(byte[].class), eq(0), eq(8192));
    verify(inputStream).available();
  }

  /**
   * Method under test: {@link InputStreamSource#toString()}
   */
  @Test
  public void testToString() throws UnsupportedEncodingException {
    // Arrange, Act and Assert
    assertEquals("InputStream",
        (new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")))).toString());
  }

  /**
   * Method under test:
   * {@link InputStreamSource#getBytesFromInputStream(InputStream)}
   */
  @Test
  public void testGetBytesFromInputStream() throws IOException {
    // Arrange
    InputStreamSource inputStreamSource = new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
    ByteArrayInputStream inStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act
    byte[] actualBytesFromInputStream = inputStreamSource.getBytesFromInputStream(inStream);

    // Assert
    assertEquals(-1, inStream.read(new byte[]{}));
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualBytesFromInputStream);
  }

  /**
   * Method under test:
   * {@link InputStreamSource#getBytesFromInputStream(InputStream)}
   */
  @Test
  public void testGetBytesFromInputStream2() throws IOException {
    // Arrange
    InputStreamSource inputStreamSource = new InputStreamSource(mock(DataInputStream.class));
    ByteArrayInputStream inStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act
    byte[] actualBytesFromInputStream = inputStreamSource.getBytesFromInputStream(inStream);

    // Assert
    assertEquals(-1, inStream.read(new byte[]{}));
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualBytesFromInputStream);
  }

  /**
   * Method under test:
   * {@link InputStreamSource#getBytesFromInputStream(InputStream)}
   */
  @Test
  public void testGetBytesFromInputStream3() throws IOException {
    // Arrange
    InputStreamSource inputStreamSource = new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
    DataInputStream inStream = mock(DataInputStream.class);
    when(inStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenThrow(new IOException("foo"));
    when(inStream.available()).thenReturn(1);

    // Act and Assert
    assertThrows(IOException.class, () -> inputStreamSource.getBytesFromInputStream(inStream));
    verify(inStream).read(isA(byte[].class), eq(0), eq(1));
    verify(inStream).available();
  }

  /**
   * Method under test:
   * {@link InputStreamSource#getBytesFromInputStream(InputStream)}
   */
  @Test
  public void testGetBytesFromInputStream4() throws IOException {
    // Arrange
    InputStreamSource inputStreamSource = new InputStreamSource(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
    DataInputStream inStream = mock(DataInputStream.class);
    when(inStream.read(Mockito.<byte[]>any(), anyInt(), anyInt())).thenReturn(-1);
    when(inStream.available()).thenReturn(1);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> inputStreamSource.getBytesFromInputStream(inStream));
    verify(inStream).read(isA(byte[].class), eq(0), eq(1));
    verify(inStream).available();
  }
}
