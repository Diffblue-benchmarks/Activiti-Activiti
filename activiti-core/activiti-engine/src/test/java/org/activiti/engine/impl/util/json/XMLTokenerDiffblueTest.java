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
package org.activiti.engine.impl.util.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class XMLTokenerDiffblueTest {
  /**
   * Method under test: {@link XMLTokener#XMLTokener(String)}
   */
  @Test
  public void testNewXMLTokener() {
    // Arrange, Act and Assert
    assertFalse((new XMLTokener("foo")).end());
  }

  /**
   * Method under test: {@link XMLTokener#nextContent()}
   */
  @Test
  public void testNextContent() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("foo", (new XMLTokener("foo")).nextContent());
  }

  /**
   * Method under test: {@link XMLTokener#nextContent()}
   */
  @Test
  public void testNextContent2() throws JSONException {
    // Arrange
    XMLTokener xmlTokener = new XMLTokener("");

    // Act and Assert
    assertNull(xmlTokener.nextContent());
    assertTrue(xmlTokener.end());
  }

  /**
   * Method under test: {@link XMLTokener#nextToken()}
   */
  @Test
  public void testNextToken() throws JSONException {
    // Arrange
    XMLTokener xmlTokener = new XMLTokener("foo");

    // Act and Assert
    assertEquals("foo", xmlTokener.nextToken());
    assertTrue(xmlTokener.end());
  }

  /**
   * Method under test: {@link XMLTokener#skipPast(String)}
   */
  @Test
  public void testSkipPast() throws JSONException {
    // Arrange, Act and Assert
    assertFalse((new XMLTokener("foo")).skipPast("alice.liddell@example.org"));
    assertFalse((new XMLTokener("foo")).skipPast("To"));
    assertTrue((new XMLTokener("foo")).skipPast(""));
    assertFalse((new XMLTokener("quot")).skipPast("To"));
    assertTrue((new XMLTokener("quot")).skipPast("quot"));
  }
}
