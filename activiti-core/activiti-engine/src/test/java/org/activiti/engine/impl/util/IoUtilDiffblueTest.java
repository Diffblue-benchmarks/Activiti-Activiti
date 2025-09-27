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
package org.activiti.engine.impl.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.activiti.engine.ActivitiException;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class IoUtilDiffblueTest {
  /**
   * Test {@link IoUtil#readInputStream(InputStream, String)}.
   *
   * <p>Method under test: {@link IoUtil#readInputStream(InputStream, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] IoUtil.readInputStream(InputStream, String)"})
  public void testReadInputStream() throws IOException {
    // Arrange
    ByteArrayInputStream inputStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act
    byte[] actualReadInputStreamResult = IoUtil.readInputStream(inputStream, "Input Stream Name");

    // Assert
    int actualReadResult = inputStream.read(new byte[] {});
    assertEquals(-1, actualReadResult);
    assertArrayEquals("AXAXAXAX".getBytes("UTF-8"), actualReadInputStreamResult);
  }

  /**
   * Test {@link IoUtil#readInputStream(InputStream, String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link IoUtil#readInputStream(InputStream, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] IoUtil.readInputStream(InputStream, String)"})
  public void testReadInputStream_thenThrowActivitiException() throws IOException {
    // Arrange
    DataInputStream inputStream = mock(DataInputStream.class);
    when(inputStream.read(Mockito.<byte[]>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> IoUtil.readInputStream(inputStream, "Input Stream Name"));
    verify(inputStream).read(isA(byte[].class));
  }

  /**
   * Test {@link IoUtil#readFileAsString(String)}.
   *
   * <ul>
   *   <li>When {@code /directory/foo.txt}.
   * </ul>
   *
   * <p>Method under test: {@link IoUtil#readFileAsString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String IoUtil.readFileAsString(String)"})
  public void testReadFileAsString_whenDirectoryFooTxt() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> IoUtil.readFileAsString("/directory/foo.txt"));
  }

  /**
   * Test {@link IoUtil#readFileAsString(String)}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link IoUtil#readFileAsString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String IoUtil.readFileAsString(String)"})
  public void testReadFileAsString_whenEmptyString() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> IoUtil.readFileAsString(""));
  }

  /**
   * Test {@link IoUtil#getFile(String)}.
   *
   * <ul>
   *   <li>When {@code /directory/foo.txt}.
   * </ul>
   *
   * <p>Method under test: {@link IoUtil#getFile(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.io.File IoUtil.getFile(String)"})
  public void testGetFile_whenDirectoryFooTxt() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> IoUtil.getFile("/directory/foo.txt"));
  }

  /**
   * Test {@link IoUtil#writeStringToFile(String, String)}.
   *
   * <ul>
   *   <li>When {@code /directory/foo.txt}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link IoUtil#writeStringToFile(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IoUtil.writeStringToFile(String, String)"})
  public void testWriteStringToFile_whenDirectoryFooTxt_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> IoUtil.writeStringToFile("Not all who wander are lost", "/directory/foo.txt"));
  }

  /**
   * Test {@link IoUtil#writeStringToFile(String, String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link IoUtil#writeStringToFile(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IoUtil.writeStringToFile(String, String)"})
  public void testWriteStringToFile_whenEmptyString_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> IoUtil.writeStringToFile("Not all who wander are lost", ""));
  }

  /**
   * Test {@link IoUtil#closeSilently(InputStream)} with {@code inputStream}.
   *
   * <ul>
   *   <li>Given {@link IOException#IOException()}.
   * </ul>
   *
   * <p>Method under test: {@link IoUtil#closeSilently(InputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IoUtil.closeSilently(InputStream)"})
  public void testCloseSilentlyWithInputStream_givenIOException() throws IOException {
    // Arrange
    DataInputStream inputStream = mock(DataInputStream.class);
    doThrow(new IOException()).when(inputStream).close();

    // Act
    IoUtil.closeSilently(inputStream);

    // Assert
    verify(inputStream).close();
  }

  /**
   * Test {@link IoUtil#closeSilently(InputStream)} with {@code inputStream}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link IoUtil#closeSilently(InputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IoUtil.closeSilently(InputStream)"})
  public void testCloseSilentlyWithInputStream_thenThrowActivitiException() throws IOException {
    // Arrange
    DataInputStream inputStream = mock(DataInputStream.class);
    doThrow(new ActivitiException("An error occurred")).when(inputStream).close();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> IoUtil.closeSilently(inputStream));
    verify(inputStream).close();
  }

  /**
   * Test {@link IoUtil#closeSilently(OutputStream)} with {@code outputStream}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link IoUtil#closeSilently(OutputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IoUtil.closeSilently(OutputStream)"})
  public void testCloseSilentlyWithOutputStream_thenThrowActivitiException() {
    // Arrange
    SyslogOutputStream outputStream = mock(SyslogOutputStream.class);
    doThrow(new ActivitiException("An error occurred")).when(outputStream).close();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> IoUtil.closeSilently(outputStream));
    verify(outputStream).close();
  }
}
