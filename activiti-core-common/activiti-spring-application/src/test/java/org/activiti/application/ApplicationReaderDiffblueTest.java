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
package org.activiti.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationReaderDiffblueTest {
  /**
   * Test {@link ApplicationReader#read(InputStream)}.
   * <p>
   * Method under test: {@link ApplicationReader#read(InputStream)}
   */
  @Test
  @DisplayName("Test read(InputStream)")
  void testRead() throws IOException {
    // Arrange
    ApplicationReader applicationReader = new ApplicationReader(new ArrayList<>());
    ByteArrayInputStream inputStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act
    ApplicationContent actualReadResult = applicationReader.read(inputStream);

    // Assert
    assertEquals(-1, inputStream.read(new byte[]{}));
    assertTrue(actualReadResult.getFileContents("Entry Type").isEmpty());
  }

  /**
   * Test {@link ApplicationReader#read(InputStream)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ApplicationEntryDiscovery}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ApplicationReader#read(InputStream)}
   */
  @Test
  @DisplayName("Test read(InputStream); given ArrayList() add ApplicationEntryDiscovery")
  void testRead_givenArrayListAddApplicationEntryDiscovery() throws IOException {
    // Arrange
    ArrayList<ApplicationEntryDiscovery> applicationEntryDiscoveries = new ArrayList<>();
    applicationEntryDiscoveries.add(mock(ApplicationEntryDiscovery.class));
    ApplicationReader applicationReader = new ApplicationReader(applicationEntryDiscoveries);
    ByteArrayInputStream inputStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));

    // Act
    ApplicationContent actualReadResult = applicationReader.read(inputStream);

    // Assert
    assertEquals(-1, inputStream.read(new byte[]{}));
    assertTrue(actualReadResult.getFileContents("Entry Type").isEmpty());
  }
}
