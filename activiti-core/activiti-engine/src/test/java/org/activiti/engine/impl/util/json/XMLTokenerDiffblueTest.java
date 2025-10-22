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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class XMLTokenerDiffblueTest {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object XMLTokener.nextContent()"})
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object XMLTokener.nextContent()"})
  public void testNextContent_givenXMLTokenerWithSIsFoo_thenReturnFoo() throws JSONException {
    // Arrange
    XMLTokener xmlTokener = new XMLTokener("foo");

    // Act and Assert
    assertEquals("foo", xmlTokener.nextContent());
    assertFalse(xmlTokener.end());
  }

  /**
   * Test {@link XMLTokener#nextMeta()}.
   * <ul>
   *   <li>Given {@link XMLTokener#XMLTokener(String)} with s is {@code foo}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XMLTokener#nextMeta()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object XMLTokener.nextMeta()"})
  public void testNextMeta_givenXMLTokenerWithSIsFoo_thenReturnTrue() throws JSONException {
    // Arrange, Act and Assert
    assertTrue((Boolean) (new XMLTokener("foo")).nextMeta());
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.Object XMLTokener.nextToken()"})
  public void testNextToken_givenXMLTokenerWithSIsFoo_thenReturnFoo() throws JSONException {
    // Arrange
    XMLTokener xmlTokener = new XMLTokener("foo");

    // Act and Assert
    assertEquals("foo", xmlTokener.nextToken());
    assertTrue(xmlTokener.end());
  }
}
