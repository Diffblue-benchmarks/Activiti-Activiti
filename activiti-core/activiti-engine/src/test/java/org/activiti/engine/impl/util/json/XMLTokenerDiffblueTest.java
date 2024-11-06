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
   * Test {@link XMLTokener#XMLTokener(String)}.
   * <p>
   * Method under test: {@link XMLTokener#XMLTokener(String)}
   */
  @Test
  public void testNewXMLTokener() {
    // Arrange, Act and Assert
    assertFalse((new XMLTokener("foo")).end());
  }

  /**
   * Test {@link XMLTokener#nextContent()}.
   * <ul>
   *   <li>Given {@link XMLTokener#XMLTokener(String)} with s is empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XMLTokener#nextContent()}
   */
  @Test
  public void testNextContent_givenXMLTokenerWithSIsEmptyString_thenReturnNull() throws JSONException {
    // Arrange
    XMLTokener xmlTokener = new XMLTokener("");

    // Act and Assert
    assertNull(xmlTokener.nextContent());
    assertTrue(xmlTokener.end());
  }

  /**
   * Test {@link XMLTokener#nextContent()}.
   * <ul>
   *   <li>Given {@link XMLTokener#XMLTokener(String)} with s is {@code foo}.</li>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XMLTokener#nextContent()}
   */
  @Test
  public void testNextContent_givenXMLTokenerWithSIsFoo_thenReturnFoo() throws JSONException {
    // Arrange
    XMLTokener xmlTokener = new XMLTokener("foo");

    // Act and Assert
    assertEquals("foo", xmlTokener.nextContent());
    assertFalse(xmlTokener.end());
  }

  /**
   * Test {@link XMLTokener#nextToken()}.
   * <ul>
   *   <li>Given {@link XMLTokener#XMLTokener(String)} with s is {@code foo}.</li>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XMLTokener#nextToken()}
   */
  @Test
  public void testNextToken_givenXMLTokenerWithSIsFoo_thenReturnFoo() throws JSONException {
    // Arrange
    XMLTokener xmlTokener = new XMLTokener("foo");

    // Act and Assert
    assertEquals("foo", xmlTokener.nextToken());
    assertTrue(xmlTokener.end());
  }

  /**
   * Test {@link XMLTokener#skipPast(String)}.
   * <ul>
   *   <li>Given {@link XMLTokener#XMLTokener(String)} with s is {@code foo}.</li>
   *   <li>When empty string.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XMLTokener#skipPast(String)}
   */
  @Test
  public void testSkipPast_givenXMLTokenerWithSIsFoo_whenEmptyString_thenReturnTrue() throws JSONException {
    // Arrange, Act and Assert
    assertTrue((new XMLTokener("foo")).skipPast(""));
  }

  /**
   * Test {@link XMLTokener#skipPast(String)}.
   * <ul>
   *   <li>Given {@link XMLTokener#XMLTokener(String)} with s is {@code foo}.</li>
   *   <li>When {@code To}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XMLTokener#skipPast(String)}
   */
  @Test
  public void testSkipPast_givenXMLTokenerWithSIsFoo_whenTo_thenReturnFalse() throws JSONException {
    // Arrange, Act and Assert
    assertFalse((new XMLTokener("foo")).skipPast("To"));
  }

  /**
   * Test {@link XMLTokener#skipPast(String)}.
   * <ul>
   *   <li>Given {@link XMLTokener#XMLTokener(String)} with s is {@code quot}.</li>
   *   <li>When {@code quot}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XMLTokener#skipPast(String)}
   */
  @Test
  public void testSkipPast_givenXMLTokenerWithSIsQuot_whenQuot_thenReturnTrue() throws JSONException {
    // Arrange, Act and Assert
    assertTrue((new XMLTokener("quot")).skipPast("quot"));
  }

  /**
   * Test {@link XMLTokener#skipPast(String)}.
   * <ul>
   *   <li>Given {@link XMLTokener#XMLTokener(String)} with s is {@code quot}.</li>
   *   <li>When {@code To}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XMLTokener#skipPast(String)}
   */
  @Test
  public void testSkipPast_givenXMLTokenerWithSIsQuot_whenTo_thenReturnFalse() throws JSONException {
    // Arrange, Act and Assert
    assertFalse((new XMLTokener("quot")).skipPast("To"));
  }

  /**
   * Test {@link XMLTokener#skipPast(String)}.
   * <ul>
   *   <li>When {@code alice.liddell@example.org}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XMLTokener#skipPast(String)}
   */
  @Test
  public void testSkipPast_whenAliceLiddellExampleOrg_thenReturnFalse() throws JSONException {
    // Arrange, Act and Assert
    assertFalse((new XMLTokener("foo")).skipPast("alice.liddell@example.org"));
  }
}
