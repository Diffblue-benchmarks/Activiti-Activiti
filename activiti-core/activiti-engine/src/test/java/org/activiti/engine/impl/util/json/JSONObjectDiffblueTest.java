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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.StringWriter;
import java.io.Writer;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JSONObjectDiffblueTest {
  /**
   * Test {@link JSONObject#JSONObject()}.
   *
   * <p>Method under test: {@link JSONObject#JSONObject()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>()"})
  public void testNewJSONObject() {
    // Arrange, Act and Assert
    assertEquals(0, new JSONObject().length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Object, String[])}.
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Object, String[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Object, String[])"})
  public void testNewJSONObject2() {
    // Arrange
    String[] names = new String[] {"Names"};

    // Act
    JSONObject actualJsonObject = new JSONObject(JSONObject.NULL, names);

    // Assert
    assertEquals(0, actualJsonObject.length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given {@code A}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code A}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenA_whenHashMapNullIsA_thenReturnLengthIsOne() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, (byte) 'A');

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONArray#JSONArray()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenJSONArray_whenHashMapNullIsJSONArray() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new JSONArray());

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} append {@code java.} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenJSONObjectAppendJavaAndNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("java.", JSONObject.NULL);
    SimpleEntry<Object, Object> simpleEntry = new SimpleEntry<>(jsonObject, JSONObject.NULL);

    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, simpleEntry);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} increment {@code {}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenJSONObjectIncrementLeftCurlyBracket() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.increment("{");
    jsonObject.append("java.", JSONObject.NULL);
    SimpleEntry<Object, Object> simpleEntry = new SimpleEntry<>(jsonObject, JSONObject.NULL);

    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, simpleEntry);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code java.} is ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenJSONObjectJavaIsTen() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("java.", 10.0d);
    SimpleEntry<Object, Object> simpleEntry = new SimpleEntry<>(jsonObject, JSONObject.NULL);

    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, simpleEntry);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code {} is {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenJSONObjectLeftCurlyBracketIs05() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("{", 0.5d);
    jsonObject.append("java.", JSONObject.NULL);
    SimpleEntry<Object, Object> simpleEntry = new SimpleEntry<>(jsonObject, JSONObject.NULL);

    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, simpleEntry);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code {} is {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenJSONObjectLeftCurlyBracketIsFalse() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("{", false);
    jsonObject.append("java.", JSONObject.NULL);
    SimpleEntry<Object, Object> simpleEntry = new SimpleEntry<>(jsonObject, JSONObject.NULL);

    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, simpleEntry);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code {} is {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenJSONObjectLeftCurlyBracketIsFalse2() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("java.", JSONObject.NULL);
    jsonObject.put("{", false);
    jsonObject.append("java.", JSONObject.NULL);
    SimpleEntry<Object, Object> simpleEntry = new SimpleEntry<>(jsonObject, JSONObject.NULL);

    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, simpleEntry);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code {} is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenJSONObjectLeftCurlyBracketIsHashMap() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("{", (Map) new HashMap<>());
    jsonObject.append("java.", JSONObject.NULL);
    SimpleEntry<Object, Object> simpleEntry = new SimpleEntry<>(jsonObject, JSONObject.NULL);

    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, simpleEntry);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link
   *       JSONObject#JSONObject()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenJSONObject_whenHashMapNullIsJSONObject() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new JSONObject());

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenNull_whenHashMapNullIsNull_thenReturnLengthIsOne() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, JSONObject.NULL);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code null}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenNull_whenHashMapNullIsNull_thenReturnLengthIsOne2() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, null);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenOne_whenHashMapNullIsOne_thenReturnLengthIsOne() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, (short) 1);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenOne_whenHashMapNullIsOne_thenReturnLengthIsOne2() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, 1);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenOne_whenHashMapNullIsOne_thenReturnLengthIsOne3() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, 1L);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given {@link AbstractMap.SimpleEntry#SimpleEntry(Object, Object)} with {@link
   *       JSONArray#JSONArray()} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenSimpleEntryWithJSONArrayAndNull() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new SimpleEntry<>(new JSONArray(), JSONObject.NULL));

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given {@link AbstractMap.SimpleEntry#SimpleEntry(Object, Object)} with {@link
   *       JSONObject#JSONObject()} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenSimpleEntryWithJSONObjectAndNull() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new SimpleEntry<>(new JSONObject(), JSONObject.NULL));

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given {@link AbstractMap.SimpleEntry#SimpleEntry(Object, Object)} with {@link
   *       JSONObject#NULL} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenSimpleEntryWithNullAndNull() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new SimpleEntry<>(JSONObject.NULL, JSONObject.NULL));

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given {@link AbstractMap.SimpleEntry#SimpleEntry(Object, Object)} with toJSONObject
   *       {@code https://example.org/example} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenSimpleEntryWithToJSONObjectHttpsExampleOrgExampleAndNull()
      throws JSONException {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(
        JSONObject.NULL,
        new SimpleEntry<>(HTTP.toJSONObject("https://example.org/example"), JSONObject.NULL));

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given start of heading.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is start of heading.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenStartOfHeading_whenHashMapNullIsStartOfHeading() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, '\u0001');

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is ten.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenTen_whenHashMapNullIsTen_thenReturnLengthIsOne() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, 10.0f);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code true}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenTrue_whenHashMapNullIsTrue_thenReturnLengthIsOne() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, true);

    // Act and Assert
    assertEquals(1, new JSONObject((Map) map).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(JSONTokener)}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(JSONTokener)"})
  public void testNewJSONObject_thenThrowJSONException() throws JSONException {
    // Arrange
    HTTPTokener x = mock(HTTPTokener.class);
    doThrow(new JSONException("An error occurred")).when(x).back();
    when(x.nextClean()).thenReturn('{');

    // Act and Assert
    assertThrows(JSONException.class, () -> new JSONObject(x));
    verify(x).back();
    verify(x, atLeast(1)).nextClean();
  }

  /**
   * Test {@link JSONObject#JSONObject(JSONObject, String[])}.
   *
   * <ul>
   *   <li>When array of {@link String} with {@code Names} and {@code null}.
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(JSONObject, String[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(JSONObject, String[])"})
  public void testNewJSONObject_whenArrayOfStringWithNamesAndNull_thenReturnLengthIsZero()
      throws JSONException {
    // Arrange and Act
    JSONObject actualJsonObject =
        new JSONObject(Cookie.toJSONObject("=;"), new String[] {"Names", null});

    // Assert
    assertEquals(0, actualJsonObject.length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Object)}.
   *
   * <ul>
   *   <li>When {@code Bean}.
   *   <li>Then return length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Object)"})
  public void testNewJSONObject_whenBean_thenReturnLengthIsThree() {
    // Arrange, Act and Assert
    assertEquals(3, new JSONObject((Object) "Bean").length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_whenHashMap_thenReturnLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, new JSONObject((Map) new HashMap<>()).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Object)"})
  public void testNewJSONObject_whenNull_thenReturnLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, new JSONObject(JSONObject.NULL).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_whenNull_thenReturnLengthIsZero2() {
    // Arrange, Act and Assert
    assertEquals(0, new JSONObject((Map) null).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(JSONObject, String[])}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(JSONObject, String[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(JSONObject, String[])"})
  public void testNewJSONObject_whenNull_thenReturnLengthIsZero3() {
    // Arrange
    String[] names = new String[] {"Names"};

    // Act
    JSONObject actualJsonObject = new JSONObject((JSONObject) null, names);

    // Assert
    assertEquals(0, actualJsonObject.length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Object)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(Object)"})
  public void testNewJSONObject_whenOne_thenReturnLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, new JSONObject(1).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(JSONObject, String[])}.
   *
   * <ul>
   *   <li>When toJSONObject {@code =;}.
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#JSONObject(JSONObject, String[])}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.<init>(JSONObject, String[])"})
  public void testNewJSONObject_whenToJSONObjectEqualsSignSemicolon_thenReturnLengthIsZero()
      throws JSONException {
    // Arrange
    String[] names = new String[] {"Names"};

    // Act
    JSONObject actualJsonObject = new JSONObject(Cookie.toJSONObject("=;"), names);

    // Assert
    assertEquals(0, actualJsonObject.length());
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   *   <li>When {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.accumulate(String, Object)"})
  public void testAccumulate_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull_whenNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.
   *   <li>When {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.accumulate(String, Object)"})
  public void testAccumulate_givenToJSONObjectEqualsSignSemicolonIncrementKey_whenNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   *
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.accumulate(String, Object)"})
  public void testAccumulate_whenJSONArray_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", new JSONArray());

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   *
   * <ul>
   *   <li>When {@link Double#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.accumulate(String, Object)"})
  public void testAccumulate_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(
        JSONException.class, () -> Cookie.toJSONObject("=;").accumulate("Key", Double.NaN));
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   *
   * <ul>
   *   <li>When {@link Float#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.accumulate(String, Object)"})
  public void testAccumulate_whenNaN_thenThrowJSONException2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").accumulate("Key", Float.NaN));
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.accumulate(String, Object)"})
  public void testAccumulate_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(
        JSONException.class, () -> Cookie.toJSONObject("=;").accumulate(null, JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.accumulate(String, Object)"})
  public void testAccumulate_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then toJSONObject {@code =;} length is two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.accumulate(String, Object)"})
  public void testAccumulate_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsTwo()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", null);

    // Assert
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.accumulate(String, Object)"})
  public void testAccumulate_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", 10.0d);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.accumulate(String, Object)"})
  public void testAccumulate_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree2()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", 10.0f);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>When ten.
   *   <li>Then {@link JSONObject#JSONObject()} length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.append(String, Object)"})
  public void testAppend_givenJSONObject_whenTen_thenJSONObjectLengthIsOne() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();

    // Act
    JSONObject actualAppendResult = jsonObject.append("Key", 10.0d);

    // Assert
    assertEquals(1, jsonObject.length());
    assertSame(jsonObject, actualAppendResult);
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.append(String, Object)"})
  public void testAppend_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act
    JSONObject actualAppendResult = toJSONObjectResult.append("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAppendResult);
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.append(String, Object)"})
  public void testAppend_givenToJSONObjectEqualsSignSemicolonIncrementKey() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.append("Key", JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@link Double#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.append(String, Object)"})
  public void testAppend_givenToJSONObjectEqualsSignSemicolon_whenNaN_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").append("Key", Double.NaN));
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@link Float#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.append(String, Object)"})
  public void testAppend_givenToJSONObjectEqualsSignSemicolon_whenNaN_thenThrowJSONException2()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").append("Key", Float.NaN));
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.append(String, Object)"})
  public void testAppend_givenToJSONObjectEqualsSignSemicolon_whenNull_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(
        JSONException.class, () -> Cookie.toJSONObject("=;").append(null, JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.append(String, Object)"})
  public void testAppend_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAppendResult = toJSONObjectResult.append("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAppendResult);
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.append(String, Object)"})
  public void testAppend_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsThree2()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAppendResult = toJSONObjectResult.append("Key", null);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAppendResult);
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.append(String, Object)"})
  public void testAppend_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAppendResult = toJSONObjectResult.append("Key", 10.0f);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAppendResult);
  }

  /**
   * Test {@link JSONObject#doubleToString(double)}.
   *
   * <ul>
   *   <li>When {@code 0.5}.
   *   <li>Then return {@code 0.5}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#doubleToString(double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.doubleToString(double)"})
  public void testDoubleToString_when05_thenReturn05() {
    // Arrange, Act and Assert
    assertEquals("0.5", JSONObject.doubleToString(0.5d));
  }

  /**
   * Test {@link JSONObject#doubleToString(double)}.
   *
   * <ul>
   *   <li>When {@link Double#NaN}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#doubleToString(double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.doubleToString(double)"})
  public void testDoubleToString_whenNaN_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.doubleToString(Double.NaN));
  }

  /**
   * Test {@link JSONObject#doubleToString(double)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return {@code 10}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#doubleToString(double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.doubleToString(double)"})
  public void testDoubleToString_whenTen_thenReturn10() {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.doubleToString(10.0d));
  }

  /**
   * Test {@link JSONObject#get(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   *   <li>Then return {@link JSONArray}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#get(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.get(String)"})
  public void testGet_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull_thenReturnJSONArray()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act
    Object actualGetResult = toJSONObjectResult.get("Key");

    // Assert
    assertTrue(actualGetResult instanceof JSONArray);
    assertEquals(1, ((JSONArray) actualGetResult).length());
  }

  /**
   * Test {@link JSONObject#get(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code false}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#get(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.get(String)"})
  public void testGet_givenToJSONObjectEqualsSignSemicolonKeyIsFalse_whenKey_thenReturnFalse()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act and Assert
    assertFalse((Boolean) toJSONObjectResult.get("Key"));
  }

  /**
   * Test {@link JSONObject#get(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code true}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#get(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.get(String)"})
  public void testGet_givenToJSONObjectEqualsSignSemicolonKeyIsTrue_whenKey_thenReturnTrue()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act and Assert
    assertTrue((Boolean) toJSONObjectResult.get("Key"));
  }

  /**
   * Test {@link JSONObject#get(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code Key}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#get(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.get(String)"})
  public void testGet_givenToJSONObjectEqualsSignSemicolon_whenKey_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").get("Key"));
  }

  /**
   * Test {@link JSONObject#get(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#get(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.get(String)"})
  public void testGet_givenToJSONObjectEqualsSignSemicolon_whenNull_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").get(null));
  }

  /**
   * Test {@link JSONObject#get(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#get(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.get(String)"})
  public void testGet_whenEmptyString_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").get(""));
  }

  /**
   * Test {@link JSONObject#getBoolean(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.getBoolean(String)"})
  public void testGetBoolean_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#getBoolean(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.getBoolean(String)"})
  public void testGetBoolean_givenToJSONObjectEqualsSignSemicolonKeyIsFalse_thenReturnFalse()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act and Assert
    assertFalse(toJSONObjectResult.getBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#getBoolean(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.getBoolean(String)"})
  public void testGetBoolean_givenToJSONObjectEqualsSignSemicolonKeyIsTrue_thenReturnTrue()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act and Assert
    assertTrue(toJSONObjectResult.getBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#getBoolean(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.getBoolean(String)"})
  public void testGetBoolean_givenToJSONObjectEqualsSignSemicolon_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#getBoolean(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.getBoolean(String)"})
  public void testGetBoolean_givenToJSONObjectEqualsSignSemicolon_whenEmptyString()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getBoolean(""));
  }

  /**
   * Test {@link JSONObject#getBoolean(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.getBoolean(String)"})
  public void testGetBoolean_givenToJSONObjectEqualsSignSemicolon_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getBoolean(null));
  }

  /**
   * Test {@link JSONObject#getDouble(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.getDouble(String)"})
  public void testGetDouble_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getDouble("Key"));
  }

  /**
   * Test {@link JSONObject#getDouble(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.getDouble(String)"})
  public void testGetDouble_givenToJSONObjectEqualsSignSemicolonIncrementKey_thenReturnOne()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1.0d, toJSONObjectResult.getDouble("Key"), 0.0);
  }

  /**
   * Test {@link JSONObject#getDouble(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.getDouble(String)"})
  public void testGetDouble_givenToJSONObjectEqualsSignSemicolon_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getDouble("Key"));
  }

  /**
   * Test {@link JSONObject#getDouble(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.getDouble(String)"})
  public void testGetDouble_givenToJSONObjectEqualsSignSemicolon_whenEmptyString()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getDouble(""));
  }

  /**
   * Test {@link JSONObject#getDouble(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.getDouble(String)"})
  public void testGetDouble_givenToJSONObjectEqualsSignSemicolon_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getDouble(null));
  }

  /**
   * Test {@link JSONObject#getInt(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.getInt(String)"})
  public void testGetInt_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getInt("Key"));
  }

  /**
   * Test {@link JSONObject#getInt(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.getInt(String)"})
  public void testGetInt_givenToJSONObjectEqualsSignSemicolonIncrementKey_thenReturnOne()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1, toJSONObjectResult.getInt("Key"));
  }

  /**
   * Test {@link JSONObject#getInt(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.getInt(String)"})
  public void testGetInt_givenToJSONObjectEqualsSignSemicolon_whenEmptyString()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getInt(""));
  }

  /**
   * Test {@link JSONObject#getInt(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code Key}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.getInt(String)"})
  public void testGetInt_givenToJSONObjectEqualsSignSemicolon_whenKey_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getInt("Key"));
  }

  /**
   * Test {@link JSONObject#getInt(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.getInt(String)"})
  public void testGetInt_givenToJSONObjectEqualsSignSemicolon_whenNull_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getInt(null));
  }

  /**
   * Test {@link JSONObject#getJSONArray(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.getJSONArray(String)"})
  public void testGetJSONArray_givenToJSONObjectEqualsSignSemicolonIncrementKey()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getJSONArray("Key"));
  }

  /**
   * Test {@link JSONObject#getJSONArray(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.getJSONArray(String)"})
  public void testGetJSONArray_givenToJSONObjectEqualsSignSemicolon_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONArray("Key"));
  }

  /**
   * Test {@link JSONObject#getJSONArray(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.getJSONArray(String)"})
  public void testGetJSONArray_givenToJSONObjectEqualsSignSemicolon_whenEmptyString()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONArray(""));
  }

  /**
   * Test {@link JSONObject#getJSONArray(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.getJSONArray(String)"})
  public void testGetJSONArray_givenToJSONObjectEqualsSignSemicolon_whenNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONArray(null));
  }

  /**
   * Test {@link JSONObject#getJSONArray(String)}.
   *
   * <ul>
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.getJSONArray(String)"})
  public void testGetJSONArray_thenReturnLengthIsOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(1, toJSONObjectResult.getJSONArray("Key").length());
  }

  /**
   * Test {@link JSONObject#getJSONObject(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.getJSONObject(String)"})
  public void testGetJSONObject_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getJSONObject("Key"));
  }

  /**
   * Test {@link JSONObject#getJSONObject(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.getJSONObject(String)"})
  public void testGetJSONObject_givenToJSONObjectEqualsSignSemicolon_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONObject("Key"));
  }

  /**
   * Test {@link JSONObject#getJSONObject(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.getJSONObject(String)"})
  public void testGetJSONObject_givenToJSONObjectEqualsSignSemicolon_whenEmptyString()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONObject(""));
  }

  /**
   * Test {@link JSONObject#getJSONObject(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.getJSONObject(String)"})
  public void testGetJSONObject_givenToJSONObjectEqualsSignSemicolon_whenNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONObject(null));
  }

  /**
   * Test {@link JSONObject#getJSONObject(String)}.
   *
   * <ul>
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.getJSONObject(String)"})
  public void testGetJSONObject_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals(0, toJSONObjectResult.getJSONObject("Key").length());
  }

  /**
   * Test {@link JSONObject#getLong(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.getLong(String)"})
  public void testGetLong_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getLong("Key"));
  }

  /**
   * Test {@link JSONObject#getLong(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.getLong(String)"})
  public void testGetLong_givenToJSONObjectEqualsSignSemicolonIncrementKey_thenReturnOne()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1L, toJSONObjectResult.getLong("Key"));
  }

  /**
   * Test {@link JSONObject#getLong(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.getLong(String)"})
  public void testGetLong_givenToJSONObjectEqualsSignSemicolon_whenEmptyString()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getLong(""));
  }

  /**
   * Test {@link JSONObject#getLong(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code Key}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.getLong(String)"})
  public void testGetLong_givenToJSONObjectEqualsSignSemicolon_whenKey_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getLong("Key"));
  }

  /**
   * Test {@link JSONObject#getLong(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.getLong(String)"})
  public void testGetLong_givenToJSONObjectEqualsSignSemicolon_whenNull_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getLong(null));
  }

  /**
   * Test {@link JSONObject#getNames(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Then return array of {@link String} with {@code name} and {@code value}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getNames(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String[] JSONObject.getNames(JSONObject)"})
  public void testGetNamesWithJo_thenReturnArrayOfStringWithNameAndValue() throws JSONException {
    // Arrange, Act and Assert
    assertArrayEquals(
        new String[] {"name", "value"}, JSONObject.getNames(Cookie.toJSONObject("=;")));
  }

  /**
   * Test {@link JSONObject#getNames(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getNames(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String[] JSONObject.getNames(JSONObject)"})
  public void testGetNamesWithJo_whenJSONObject_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(JSONObject.getNames(new JSONObject()));
  }

  /**
   * Test {@link JSONObject#getNames(Object)} with {@code object}.
   *
   * <ul>
   *   <li>Then return array of {@link String} with {@code CASE_INSENSITIVE_ORDER}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getNames(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String[] JSONObject.getNames(Object)"})
  public void testGetNamesWithObject_thenReturnArrayOfStringWithCaseInsensitiveOrder() {
    // Arrange, Act and Assert
    assertArrayEquals(new String[] {"CASE_INSENSITIVE_ORDER"}, JSONObject.getNames("Object"));
  }

  /**
   * Test {@link JSONObject#getNames(Object)} with {@code object}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getNames(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String[] JSONObject.getNames(Object)"})
  public void testGetNamesWithObject_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(JSONObject.getNames(JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#getNames(Object)} with {@code object}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getNames(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String[] JSONObject.getNames(Object)"})
  public void testGetNamesWithObject_whenNull_thenReturnNull2() {
    // Arrange, Act and Assert
    assertNull(JSONObject.getNames((Object) null));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@code 42}.
   *   <li>Then return {@code ["42"]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.getString(String)"})
  public void testGetString_givenToJSONObjectEqualsSignSemicolonAppendKeyAnd42_thenReturn42()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "42");

    // Act and Assert
    assertEquals("[\"42\"]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.getString(String)"})
  public void testGetString_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", null);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and ten.
   *   <li>Then return {@code [10]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.getString(String)"})
  public void testGetString_givenToJSONObjectEqualsSignSemicolonAppendKeyAndTen_thenReturn10()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", 10.0d);

    // Act and Assert
    assertEquals("[10]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.
   *   <li>Then return {@code 1}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.getString(String)"})
  public void testGetString_givenToJSONObjectEqualsSignSemicolonIncrementKey_thenReturn1()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals("1", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.getString(String)"})
  public void testGetString_givenToJSONObjectEqualsSignSemicolon_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   *
   * <ul>
   *   <li>Then return {@code [42]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.getString(String)"})
  public void testGetString_thenReturn42() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", 42);

    // Act and Assert
    assertEquals("[42]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   *
   * <ul>
   *   <li>Then return {@code {}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.getString(String)"})
  public void testGetString_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   *
   * <ul>
   *   <li>Then return {@code [null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.getString(String)"})
  public void testGetString_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   *
   * <ul>
   *   <li>Then return {@code [null,null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.getString(String)"})
  public void testGetString_thenReturnNullNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null,null]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.getString(String)"})
  public void testGetString_whenEmptyString_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getString(""));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.getString(String)"})
  public void testGetString_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getString(null));
  }

  /**
   * Test {@link JSONObject#has(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#has(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.has(String)"})
  public void testHas_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull_thenReturnTrue()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertTrue(toJSONObjectResult.has("Key"));
  }

  /**
   * Test {@link JSONObject#has(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#has(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.has(String)"})
  public void testHas_givenToJSONObjectEqualsSignSemicolon_thenReturnFalse() throws JSONException {
    // Arrange, Act and Assert
    assertFalse(Cookie.toJSONObject("=;").has("Key"));
  }

  /**
   * Test {@link JSONObject#increment(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.increment(String)"})
  public void testIncrement_givenToJSONObjectEqualsSignSemicolon() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualIncrementResult = toJSONObjectResult.increment("Key");

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualIncrementResult);
  }

  /**
   * Test {@link JSONObject#increment(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.increment(String)"})
  public void testIncrement_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.increment("Key"));
  }

  /**
   * Test {@link JSONObject#increment(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.increment(String)"})
  public void testIncrement_givenToJSONObjectEqualsSignSemicolonIncrementKey()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act
    JSONObject actualIncrementResult = toJSONObjectResult.increment("Key");

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualIncrementResult);
  }

  /**
   * Test {@link JSONObject#increment(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.increment(String)"})
  public void testIncrement_givenToJSONObjectEqualsSignSemicolonKeyIsFortyTwo()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", 42L);

    // Act
    JSONObject actualIncrementResult = toJSONObjectResult.increment("Key");

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualIncrementResult);
  }

  /**
   * Test {@link JSONObject#increment(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.increment(String)"})
  public void testIncrement_givenToJSONObjectEqualsSignSemicolonKeyIsTen() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", 10.0d);

    // Act
    JSONObject actualIncrementResult = toJSONObjectResult.increment("Key");

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualIncrementResult);
  }

  /**
   * Test {@link JSONObject#increment(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.increment(String)"})
  public void testIncrement_givenToJSONObjectEqualsSignSemicolon_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").increment(null));
  }

  /**
   * Test {@link JSONObject#isNull(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#isNull(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.isNull(String)"})
  public void testIsNull_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull_thenReturnFalse()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertFalse(toJSONObjectResult.isNull("Key"));
  }

  /**
   * Test {@link JSONObject#isNull(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#isNull(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.isNull(String)"})
  public void testIsNull_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnTrue()
      throws JSONException {
    // Arrange, Act and Assert
    assertTrue(Cookie.toJSONObject("=;").isNull("Key"));
  }

  /**
   * Test {@link JSONObject#isNull(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#isNull(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.isNull(String)"})
  public void testIsNull_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnTrue()
      throws JSONException {
    // Arrange, Act and Assert
    assertTrue(Cookie.toJSONObject("=;").isNull(null));
  }

  /**
   * Test {@link JSONObject#keys()}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then return next is {@code name}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#keys()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Iterator JSONObject.keys()"})
  public void testKeys_givenToJSONObjectEqualsSignSemicolon_thenReturnNextIsName()
      throws JSONException {
    // Arrange and Act
    Iterator actualKeysResult = Cookie.toJSONObject("=;").keys();

    // Assert
    assertEquals("name", actualKeysResult.next());
    assertEquals("value", actualKeysResult.next());
    assertFalse(actualKeysResult.hasNext());
  }

  /**
   * Test {@link JSONObject#length()}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then return two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#length()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.length()"})
  public void testLength_givenToJSONObjectEqualsSignSemicolon_thenReturnTwo() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(2, Cookie.toJSONObject("=;").length());
  }

  /**
   * Test {@link JSONObject#names()}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#names()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.names()"})
  public void testNames_givenJSONObject_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new JSONObject().names());
  }

  /**
   * Test {@link JSONObject#names()}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then return length is two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#names()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.names()"})
  public void testNames_givenToJSONObjectEqualsSignSemicolon_thenReturnLengthIsTwo()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(2, Cookie.toJSONObject("=;").names().length());
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   *
   * <ul>
   *   <li>When {@code 0.5}.
   *   <li>Then return {@code 0.5}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_when05_thenReturn05() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("0.5", JSONObject.numberToString(0.5d));
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   *
   * <ul>
   *   <li>When {@link Double#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.numberToString(Double.NaN));
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   *
   * <ul>
   *   <li>When {@link Float#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_whenNaN_thenThrowJSONException2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.numberToString(Float.NaN));
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.numberToString(null));
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return {@code 10}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_whenTen_thenReturn10() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.numberToString(10.0d));
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return {@code 10}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_whenTen_thenReturn102() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.numberToString(10.0f));
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   *
   * <ul>
   *   <li>When valueOf one.
   *   <li>Then return {@code 1}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_whenValueOfOne_thenReturn1() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("1", JSONObject.numberToString(Integer.valueOf(1)));
  }

  /**
   * Test {@link JSONObject#opt(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code false}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#opt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.opt(String)"})
  public void testOpt_givenToJSONObjectEqualsSignSemicolonKeyIsFalse_whenKey_thenReturnFalse()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act and Assert
    assertFalse((Boolean) toJSONObjectResult.opt("Key"));
  }

  /**
   * Test {@link JSONObject#opt(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code true}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#opt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.opt(String)"})
  public void testOpt_givenToJSONObjectEqualsSignSemicolonKeyIsTrue_whenKey_thenReturnTrue()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act and Assert
    assertTrue((Boolean) toJSONObjectResult.opt("Key"));
  }

  /**
   * Test {@link JSONObject#opt(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#opt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.opt(String)"})
  public void testOpt_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").opt("Key"));
  }

  /**
   * Test {@link JSONObject#opt(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#opt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.opt(String)"})
  public void testOpt_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").opt(null));
  }

  /**
   * Test {@link JSONObject#optBoolean(String, boolean)} with {@code key}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.optBoolean(String, boolean)"})
  public void testOptBooleanWithKeyDefaultValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertTrue(toJSONObjectResult.optBoolean("Key", true));
  }

  /**
   * Test {@link JSONObject#optBoolean(String, boolean)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.optBoolean(String, boolean)"})
  public void testOptBooleanWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon()
      throws JSONException {
    // Arrange, Act and Assert
    assertTrue(Cookie.toJSONObject("=;").optBoolean("Key", true));
  }

  /**
   * Test {@link JSONObject#optBoolean(String, boolean)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.optBoolean(String, boolean)"})
  public void testOptBooleanWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolonKeyIsTrue()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act and Assert
    assertTrue(toJSONObjectResult.optBoolean("Key", true));
  }

  /**
   * Test {@link JSONObject#optBoolean(String, boolean)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.optBoolean(String, boolean)"})
  public void testOptBooleanWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon_whenNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertTrue(Cookie.toJSONObject("=;").optBoolean(null, true));
  }

  /**
   * Test {@link JSONObject#optBoolean(String, boolean)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.optBoolean(String, boolean)"})
  public void testOptBooleanWithKeyDefaultValue_thenReturnFalse() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act and Assert
    assertFalse(toJSONObjectResult.optBoolean("Key", true));
  }

  /**
   * Test {@link JSONObject#optBoolean(String, boolean)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.optBoolean(String, boolean)"})
  public void testOptBooleanWithKeyDefaultValue_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertTrue(Cookie.toJSONObject("=;").optBoolean("", true));
  }

  /**
   * Test {@link JSONObject#optBoolean(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.optBoolean(String)"})
  public void testOptBooleanWithKey_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertFalse(toJSONObjectResult.optBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#optBoolean(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.optBoolean(String)"})
  public void testOptBooleanWithKey_givenToJSONObjectEqualsSignSemicolonKeyIsFalse()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act and Assert
    assertFalse(toJSONObjectResult.optBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#optBoolean(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.optBoolean(String)"})
  public void testOptBooleanWithKey_givenToJSONObjectEqualsSignSemicolon_thenReturnFalse()
      throws JSONException {
    // Arrange, Act and Assert
    assertFalse(Cookie.toJSONObject("=;").optBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#optBoolean(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.optBoolean(String)"})
  public void testOptBooleanWithKey_givenToJSONObjectEqualsSignSemicolon_whenEmptyString()
      throws JSONException {
    // Arrange, Act and Assert
    assertFalse(Cookie.toJSONObject("=;").optBoolean(""));
  }

  /**
   * Test {@link JSONObject#optBoolean(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.optBoolean(String)"})
  public void testOptBooleanWithKey_givenToJSONObjectEqualsSignSemicolon_whenNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertFalse(Cookie.toJSONObject("=;").optBoolean(null));
  }

  /**
   * Test {@link JSONObject#optBoolean(String)} with {@code key}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONObject.optBoolean(String)"})
  public void testOptBooleanWithKey_thenReturnTrue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act and Assert
    assertTrue(toJSONObjectResult.optBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#optDouble(String, double)} with {@code key}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONObject#optDouble(String, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.optDouble(String, double)"})
  public void testOptDoubleWithKeyDefaultValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(10.0d, toJSONObjectResult.optDouble("Key", 10.0d), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String, double)} with {@code key}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONObject#optDouble(String, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.optDouble(String, double)"})
  public void testOptDoubleWithKeyDefaultValue2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(10.0d, toJSONObjectResult.optDouble(null, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String, double)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optDouble(String, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.optDouble(String, double)"})
  public void testOptDoubleWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(10.0d, Cookie.toJSONObject("=;").optDouble("Key", 10.0d), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String, double)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optDouble(String, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.optDouble(String, double)"})
  public void testOptDoubleWithKeyDefaultValue_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1.0d, toJSONObjectResult.optDouble("Key", 10.0d), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optDouble(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.optDouble(String)"})
  public void testOptDoubleWithKey_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(Double.NaN, toJSONObjectResult.optDouble("Key"), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optDouble(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.optDouble(String)"})
  public void testOptDoubleWithKey_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull2()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(Double.NaN, toJSONObjectResult.optDouble(null), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code Key}.
   *   <li>Then return {@link Double#NaN}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optDouble(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.optDouble(String)"})
  public void testOptDoubleWithKey_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnNaN()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(Double.NaN, Cookie.toJSONObject("=;").optDouble("Key"), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String)} with {@code key}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optDouble(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONObject.optDouble(String)"})
  public void testOptDoubleWithKey_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1.0d, toJSONObjectResult.optDouble("Key"), 0.0);
  }

  /**
   * Test {@link JSONObject#optInt(String, int)} with {@code key}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONObject#optInt(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.optInt(String, int)"})
  public void testOptIntWithKeyDefaultValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(42, toJSONObjectResult.optInt("Key", 42));
  }

  /**
   * Test {@link JSONObject#optInt(String, int)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optInt(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.optInt(String, int)"})
  public void testOptIntWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, Cookie.toJSONObject("=;").optInt("Key", 42));
  }

  /**
   * Test {@link JSONObject#optInt(String, int)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optInt(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.optInt(String, int)"})
  public void testOptIntWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon_whenNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, Cookie.toJSONObject("=;").optInt(null, 42));
  }

  /**
   * Test {@link JSONObject#optInt(String, int)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optInt(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.optInt(String, int)"})
  public void testOptIntWithKeyDefaultValue_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1, toJSONObjectResult.optInt("Key", 42));
  }

  /**
   * Test {@link JSONObject#optInt(String, int)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optInt(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.optInt(String, int)"})
  public void testOptIntWithKeyDefaultValue_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, Cookie.toJSONObject("=;").optInt("", 42));
  }

  /**
   * Test {@link JSONObject#optInt(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.optInt(String)"})
  public void testOptIntWithKey_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(0, toJSONObjectResult.optInt("Key"));
  }

  /**
   * Test {@link JSONObject#optInt(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.optInt(String)"})
  public void testOptIntWithKey_givenToJSONObjectEqualsSignSemicolonIncrementKey_thenReturnOne()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1, toJSONObjectResult.optInt("Key"));
  }

  /**
   * Test {@link JSONObject#optInt(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.optInt(String)"})
  public void testOptIntWithKey_givenToJSONObjectEqualsSignSemicolon_whenEmptyString()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, Cookie.toJSONObject("=;").optInt(""));
  }

  /**
   * Test {@link JSONObject#optInt(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code Key}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.optInt(String)"})
  public void testOptIntWithKey_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnZero()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, Cookie.toJSONObject("=;").optInt("Key"));
  }

  /**
   * Test {@link JSONObject#optInt(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONObject.optInt(String)"})
  public void testOptIntWithKey_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnZero()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, Cookie.toJSONObject("=;").optInt(null));
  }

  /**
   * Test {@link JSONObject#optJSONArray(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optJSONArray(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.optJSONArray(String)"})
  public void testOptJSONArray_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").optJSONArray("Key"));
  }

  /**
   * Test {@link JSONObject#optJSONArray(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optJSONArray(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.optJSONArray(String)"})
  public void testOptJSONArray_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").optJSONArray(null));
  }

  /**
   * Test {@link JSONObject#optJSONArray(String)}.
   *
   * <ul>
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optJSONArray(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.optJSONArray(String)"})
  public void testOptJSONArray_thenReturnLengthIsOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(1, toJSONObjectResult.optJSONArray("Key").length());
  }

  /**
   * Test {@link JSONObject#optJSONObject(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code Key}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.optJSONObject(String)"})
  public void testOptJSONObject_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").optJSONObject("Key"));
  }

  /**
   * Test {@link JSONObject#optJSONObject(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.optJSONObject(String)"})
  public void testOptJSONObject_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").optJSONObject(null));
  }

  /**
   * Test {@link JSONObject#optJSONObject(String)}.
   *
   * <ul>
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optJSONObject(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.optJSONObject(String)"})
  public void testOptJSONObject_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals(0, toJSONObjectResult.optJSONObject("Key").length());
  }

  /**
   * Test {@link JSONObject#optLong(String, long)} with {@code key}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONObject#optLong(String, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.optLong(String, long)"})
  public void testOptLongWithKeyDefaultValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(42L, toJSONObjectResult.optLong("Key", 42L));
  }

  /**
   * Test {@link JSONObject#optLong(String, long)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optLong(String, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.optLong(String, long)"})
  public void testOptLongWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, Cookie.toJSONObject("=;").optLong("Key", 42L));
  }

  /**
   * Test {@link JSONObject#optLong(String, long)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optLong(String, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.optLong(String, long)"})
  public void testOptLongWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon_whenNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, Cookie.toJSONObject("=;").optLong(null, 42L));
  }

  /**
   * Test {@link JSONObject#optLong(String, long)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optLong(String, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.optLong(String, long)"})
  public void testOptLongWithKeyDefaultValue_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1L, toJSONObjectResult.optLong("Key", 42L));
  }

  /**
   * Test {@link JSONObject#optLong(String, long)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optLong(String, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.optLong(String, long)"})
  public void testOptLongWithKeyDefaultValue_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, Cookie.toJSONObject("=;").optLong("", 42L));
  }

  /**
   * Test {@link JSONObject#optLong(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.optLong(String)"})
  public void testOptLongWithKey_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(0L, toJSONObjectResult.optLong("Key"));
  }

  /**
   * Test {@link JSONObject#optLong(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.optLong(String)"})
  public void testOptLongWithKey_givenToJSONObjectEqualsSignSemicolon_whenEmptyString()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0L, Cookie.toJSONObject("=;").optLong(""));
  }

  /**
   * Test {@link JSONObject#optLong(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code Key}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.optLong(String)"})
  public void testOptLongWithKey_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnZero()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0L, Cookie.toJSONObject("=;").optLong("Key"));
  }

  /**
   * Test {@link JSONObject#optLong(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.optLong(String)"})
  public void testOptLongWithKey_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnZero()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0L, Cookie.toJSONObject("=;").optLong(null));
  }

  /**
   * Test {@link JSONObject#optLong(String)} with {@code key}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONObject.optLong(String)"})
  public void testOptLongWithKey_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1L, toJSONObjectResult.optLong("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   *
   * <p>Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String)"})
  public void testOptStringWithKey() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "");

    // Act and Assert
    assertEquals("[\"\"]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String, String)"})
  public void testOptStringWithKeyDefaultValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", null);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String, String)"})
  public void testOptStringWithKeyDefaultValue2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "");

    // Act and Assert
    assertEquals("[\"\"]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code 1}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String, String)"})
  public void testOptStringWithKeyDefaultValue_thenReturn1() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals("1", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code [10]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String, String)"})
  public void testOptStringWithKeyDefaultValue_thenReturn10() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", 10.0d);

    // Act and Assert
    assertEquals("[10]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String, String)"})
  public void testOptStringWithKeyDefaultValue_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", Cookie.toJSONObject("=;").optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String, String)"})
  public void testOptStringWithKeyDefaultValue_thenReturn422() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", Cookie.toJSONObject("=;").optString(null, "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code [42]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String, String)"})
  public void testOptStringWithKeyDefaultValue_thenReturn423() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", 42);

    // Act and Assert
    assertEquals("[42]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code ["42"]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String, String)"})
  public void testOptStringWithKeyDefaultValue_thenReturn424() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "42");

    // Act and Assert
    assertEquals("[\"42\"]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code {}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String, String)"})
  public void testOptStringWithKeyDefaultValue_thenReturnLeftCurlyBracketRightCurlyBracket()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code [null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String, String)"})
  public void testOptStringWithKeyDefaultValue_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code [null,null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String, String)"})
  public void testOptStringWithKeyDefaultValue_thenReturnNullNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null,null]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String)"})
  public void testOptStringWithKey_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", null);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String)"})
  public void testOptStringWithKey_givenToJSONObjectEqualsSignSemicolon_thenReturnEmptyString()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", Cookie.toJSONObject("=;").optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String)"})
  public void testOptStringWithKey_givenToJSONObjectEqualsSignSemicolon_thenReturnEmptyString2()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", Cookie.toJSONObject("=;").optString(null));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   *
   * <ul>
   *   <li>Then return {@code 1}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String)"})
  public void testOptStringWithKey_thenReturn1() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals("1", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   *
   * <ul>
   *   <li>Then return {@code [10]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String)"})
  public void testOptStringWithKey_thenReturn10() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", 10.0d);

    // Act and Assert
    assertEquals("[10]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   *
   * <ul>
   *   <li>Then return {@code [42]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String)"})
  public void testOptStringWithKey_thenReturn42() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", 42);

    // Act and Assert
    assertEquals("[42]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   *
   * <ul>
   *   <li>Then return {@code ["42"]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String)"})
  public void testOptStringWithKey_thenReturn422() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "42");

    // Act and Assert
    assertEquals("[\"42\"]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   *
   * <ul>
   *   <li>Then return {@code {}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String)"})
  public void testOptStringWithKey_thenReturnLeftCurlyBracketRightCurlyBracket()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   *
   * <ul>
   *   <li>Then return {@code [null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String)"})
  public void testOptStringWithKey_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   *
   * <ul>
   *   <li>Then return {@code [null,null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.optString(String)"})
  public void testOptStringWithKey_thenReturnNullNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null,null]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#put(String, boolean)} with {@code String}, {@code boolean}.
   *
   * <ul>
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, boolean)"})
  public void testPutWithStringBoolean_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", true);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, boolean)} with {@code String}, {@code boolean}.
   *
   * <ul>
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, boolean)"})
  public void testPutWithStringBoolean_thenToJSONObjectEqualsSignSemicolonLengthIsThree2()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", false);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, boolean)} with {@code String}, {@code boolean}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, boolean)"})
  public void testPutWithStringBoolean_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, true));
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>When {@code null}.
   *   <li>Then {@link JSONObject#JSONObject()} length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Collection)"})
  public void testPutWithStringCollection_givenJSONObject_whenNull_thenJSONObjectLengthIsOne()
      throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();

    // Act
    JSONObject actualPutResult = jsonObject.put("Key", (Collection) null);

    // Assert
    assertEquals(1, jsonObject.length());
    assertSame(jsonObject, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@code null}.
   *   <li>Then {@link JSONObject#JSONObject()} length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Collection)"})
  public void testPutWithStringCollection_givenLinkedHashSetAddNull_thenJSONObjectLengthIsOne()
      throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();

    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add(null);

    LinkedHashSet<Object> value = new LinkedHashSet<>();
    value.add(objectSet);

    // Act
    JSONObject actualPutResult = jsonObject.put("Key", (Collection) value);

    // Assert
    assertEquals(1, jsonObject.length());
    assertSame(jsonObject, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Collection)"})
  public void testPutWithStringCollection_givenNull_whenArrayListAddNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Collection)"})
  public void testPutWithStringCollection_givenNull_whenArrayListAddNull2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);
    value.add(JSONObject.NULL);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String}, {@code Collection}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Collection)"})
  public void testPutWithStringCollection_whenArrayList() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) new ArrayList<>());

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, double)} with {@code String}, {@code double}.
   *
   * <ul>
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, double)"})
  public void testPutWithStringDouble_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", 10.0d);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, double)} with {@code String}, {@code double}.
   *
   * <ul>
   *   <li>When {@link Double#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, double)"})
  public void testPutWithStringDouble_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put("Key", Double.NaN));
  }

  /**
   * Test {@link JSONObject#put(String, double)} with {@code String}, {@code double}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, double)"})
  public void testPutWithStringDouble_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, 10.0d));
  }

  /**
   * Test {@link JSONObject#put(String, int)} with {@code String}, {@code int}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, int)"})
  public void testPutWithStringInt_givenToJSONObjectEqualsSignSemicolon_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, 42));
  }

  /**
   * Test {@link JSONObject#put(String, int)} with {@code String}, {@code int}.
   *
   * <ul>
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, int)"})
  public void testPutWithStringInt_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", 42);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, long)} with {@code String}, {@code long}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, long)"})
  public void testPutWithStringLong_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, 42L));
  }

  /**
   * Test {@link JSONObject#put(String, long)} with {@code String}, {@code long}.
   *
   * <ul>
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, long)"})
  public void testPutWithStringLong_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", 42L);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given {@code A}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code A}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenA_whenHashMapNullIsA() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, (byte) 'A');

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONArray#JSONArray()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenJSONArray_whenHashMapNullIsJSONArray()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new JSONArray());

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link
   *       JSONObject#JSONObject()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenJSONObject_whenHashMapNullIsJSONObject()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new JSONObject());

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>When {@code null}.
   *   <li>Then {@link JSONObject#JSONObject()} length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenJSONObject_whenNull_thenJSONObjectLengthIsOne()
      throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();

    // Act
    JSONObject actualPutResult = jsonObject.put("Key", (Map) null);

    // Assert
    assertEquals(1, jsonObject.length());
    assertSame(jsonObject, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenNull_whenHashMapNullIsNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenNull_whenHashMapNullIsNull2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, null);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenOne_whenHashMapNullIsOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, (short) 1);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenOne_whenHashMapNullIsOne2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 1);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenOne_whenHashMapNullIsOne3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 1L);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given {@link AbstractMap.SimpleEntry#SimpleEntry(Object, Object)} with {@link
   *       JSONObject#NULL} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenSimpleEntryWithNullAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new SimpleEntry<>(JSONObject.NULL, JSONObject.NULL));

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given start of heading.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is start of heading.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenStartOfHeading_whenHashMapNullIsStartOfHeading()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, '\u0001');

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenTen_whenHashMapNullIsTen() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 10.0f);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_givenTrue_whenHashMapNullIsTrue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, true);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Map)"})
  public void testPutWithStringMap_whenHashMap() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Object)} with {@code String}, {@code Object}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>When ten.
   *   <li>Then {@link JSONObject#JSONObject()} length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Object)"})
  public void testPutWithStringObject_givenJSONObject_whenTen_thenJSONObjectLengthIsOne()
      throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();

    // Act
    JSONObject actualPutResult = jsonObject.put("Key", (Object) 10.0d);

    // Assert
    assertEquals(1, jsonObject.length());
    assertSame(jsonObject, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Object)} with {@code String}, {@code Object}.
   *
   * <ul>
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Object)"})
  public void testPutWithStringObject_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Object)} with {@code String}, {@code Object}.
   *
   * <ul>
   *   <li>When {@link Float#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Object)"})
  public void testPutWithStringObject_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put("Key", Float.NaN));
  }

  /**
   * Test {@link JSONObject#put(String, Object)} with {@code String}, {@code Object}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Object)"})
  public void testPutWithStringObject_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#put(String, Object)} with {@code String}, {@code Object}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then toJSONObject {@code =;} length is two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Object)"})
  public void testPutWithStringObject_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsTwo()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Object) null);

    // Assert
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Object)} with {@code String}, {@code Object}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.put(String, Object)"})
  public void testPutWithStringObject_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", 10.0f);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#putOnce(String, Object)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>When ten.
   *   <li>Then {@link JSONObject#JSONObject()} length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOnce(String, Object)"})
  public void testPutOnce_givenJSONObject_whenTen_thenJSONObjectLengthIsOne() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();

    // Act
    JSONObject actualPutOnceResult = jsonObject.putOnce("Key", 10.0d);

    // Assert
    assertEquals(1, jsonObject.length());
    assertSame(jsonObject, actualPutOnceResult);
  }

  /**
   * Test {@link JSONObject#putOnce(String, Object)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOnce(String, Object)"})
  public void testPutOnce_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.putOnce("Key", JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#putOnce(String, Object)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@link Double#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOnce(String, Object)"})
  public void testPutOnce_givenToJSONObjectEqualsSignSemicolon_whenNaN_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").putOnce("Key", Double.NaN));
  }

  /**
   * Test {@link JSONObject#putOnce(String, Object)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@link Float#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOnce(String, Object)"})
  public void testPutOnce_givenToJSONObjectEqualsSignSemicolon_whenNaN_thenThrowJSONException2()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").putOnce("Key", Float.NaN));
  }

  /**
   * Test {@link JSONObject#putOnce(String, Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOnce(String, Object)"})
  public void testPutOnce_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOnceResult = toJSONObjectResult.putOnce("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOnceResult);
  }

  /**
   * Test {@link JSONObject#putOnce(String, Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then toJSONObject {@code =;} length is two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOnce(String, Object)"})
  public void testPutOnce_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsTwo()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOnceResult = toJSONObjectResult.putOnce(null, JSONObject.NULL);

    // Assert
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOnceResult);
  }

  /**
   * Test {@link JSONObject#putOnce(String, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then toJSONObject {@code =;} length is two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOnce(String, Object)"})
  public void testPutOnce_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsTwo2()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOnceResult = toJSONObjectResult.putOnce("Key", null);

    // Assert
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOnceResult);
  }

  /**
   * Test {@link JSONObject#putOnce(String, Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOnce(String, Object)"})
  public void testPutOnce_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOnceResult = toJSONObjectResult.putOnce("Key", 10.0f);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOnceResult);
  }

  /**
   * Test {@link JSONObject#putOpt(String, Object)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>When ten.
   *   <li>Then {@link JSONObject#JSONObject()} length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOpt(String, Object)"})
  public void testPutOpt_givenJSONObject_whenTen_thenJSONObjectLengthIsOne() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();

    // Act
    JSONObject actualPutOptResult = jsonObject.putOpt("Key", 10.0d);

    // Assert
    assertEquals(1, jsonObject.length());
    assertSame(jsonObject, actualPutOptResult);
  }

  /**
   * Test {@link JSONObject#putOpt(String, Object)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@link Double#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOpt(String, Object)"})
  public void testPutOpt_givenToJSONObjectEqualsSignSemicolon_whenNaN_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").putOpt("Key", Double.NaN));
  }

  /**
   * Test {@link JSONObject#putOpt(String, Object)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@link Float#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOpt(String, Object)"})
  public void testPutOpt_givenToJSONObjectEqualsSignSemicolon_whenNaN_thenThrowJSONException2()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").putOpt("Key", Float.NaN));
  }

  /**
   * Test {@link JSONObject#putOpt(String, Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOpt(String, Object)"})
  public void testPutOpt_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOptResult = toJSONObjectResult.putOpt("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOptResult);
  }

  /**
   * Test {@link JSONObject#putOpt(String, Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then toJSONObject {@code =;} length is two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOpt(String, Object)"})
  public void testPutOpt_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsTwo()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOptResult = toJSONObjectResult.putOpt(null, JSONObject.NULL);

    // Assert
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOptResult);
  }

  /**
   * Test {@link JSONObject#putOpt(String, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then toJSONObject {@code =;} length is two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOpt(String, Object)"})
  public void testPutOpt_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsTwo2()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOptResult = toJSONObjectResult.putOpt("Key", null);

    // Assert
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOptResult);
  }

  /**
   * Test {@link JSONObject#putOpt(String, Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then toJSONObject {@code =;} length is three.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONObject.putOpt(String, Object)"})
  public void testPutOpt_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOptResult = toJSONObjectResult.putOpt("Key", 10.0f);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOptResult);
  }

  /**
   * Test {@link JSONObject#quote(String)}.
   *
   * <p>Method under test: {@link JSONObject#quote(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.quote(String)"})
  public void testQuote() {
    // Arrange, Act and Assert
    assertEquals("\"\\\"\\\"\"", JSONObject.quote("\"\""));
  }

  /**
   * Test {@link JSONObject#quote(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code ""}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#quote(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.quote(String)"})
  public void testQuote_whenEmptyString_thenReturnQuotationMarkQuotationMark() {
    // Arrange, Act and Assert
    assertEquals("\"\"", JSONObject.quote(""));
  }

  /**
   * Test {@link JSONObject#quote(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code ""}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#quote(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.quote(String)"})
  public void testQuote_whenNull_thenReturnQuotationMarkQuotationMark() {
    // Arrange, Act and Assert
    assertEquals("\"\"", JSONObject.quote(null));
  }

  /**
   * Test {@link JSONObject#quote(String)}.
   *
   * <ul>
   *   <li>When {@code String}.
   *   <li>Then return {@code "String"}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#quote(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.quote(String)"})
  public void testQuote_whenString_thenReturnString() {
    // Arrange, Act and Assert
    assertEquals("\"String\"", JSONObject.quote("String"));
  }

  /**
   * Test {@link JSONObject#remove(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#remove(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.remove(String)"})
  public void testRemove_givenToJSONObjectEqualsSignSemicolonKeyIsFalse_thenReturnFalse()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act
    Object actualRemoveResult = toJSONObjectResult.remove("Key");

    // Assert
    assertEquals(2, toJSONObjectResult.length());
    assertFalse((Boolean) actualRemoveResult);
  }

  /**
   * Test {@link JSONObject#remove(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#remove(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.remove(String)"})
  public void testRemove_givenToJSONObjectEqualsSignSemicolonKeyIsTrue_thenReturnTrue()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act
    Object actualRemoveResult = toJSONObjectResult.remove("Key");

    // Assert
    assertEquals(2, toJSONObjectResult.length());
    assertTrue((Boolean) actualRemoveResult);
  }

  /**
   * Test {@link JSONObject#remove(String)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#remove(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.remove(String)"})
  public void testRemove_givenToJSONObjectEqualsSignSemicolon_thenReturnNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act and Assert
    assertNull(toJSONObjectResult.remove("Key"));
    assertEquals(2, toJSONObjectResult.length());
  }

  /**
   * Test {@link JSONObject#sortedKeys()}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then return next is {@code name}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#sortedKeys()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Iterator JSONObject.sortedKeys()"})
  public void testSortedKeys_givenToJSONObjectEqualsSignSemicolon_thenReturnNextIsName()
      throws JSONException {
    // Arrange and Act
    Iterator actualSortedKeysResult = Cookie.toJSONObject("=;").sortedKeys();

    // Assert
    assertEquals("name", actualSortedKeysResult.next());
    assertEquals("value", actualSortedKeysResult.next());
    assertFalse(actualSortedKeysResult.hasNext());
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   *
   * <ul>
   *   <li>When {@code 0}.
   *   <li>Then return intValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.stringToValue(String)"})
  public void testStringToValue_when0_thenReturnIntValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, ((Integer) JSONObject.stringToValue("0")).intValue());
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   *
   * <ul>
   *   <li>When {@code 0foo}.
   *   <li>Then return {@code 0foo}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.stringToValue(String)"})
  public void testStringToValue_when0foo_thenReturn0foo() {
    // Arrange, Act and Assert
    assertEquals("0foo", JSONObject.stringToValue("0foo"));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   *
   * <ul>
   *   <li>When {@code 42.}.
   *   <li>Then return doubleValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.stringToValue(String)"})
  public void testStringToValue_when42_thenReturnDoubleValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42.0d, ((Double) JSONObject.stringToValue("42.")).doubleValue(), 0.0);
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.stringToValue(String)"})
  public void testStringToValue_when42_thenReturnIntValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, ((Integer) JSONObject.stringToValue("42")).intValue());
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   *
   * <ul>
   *   <li>When {@code 42foo}.
   *   <li>Then return {@code 42foo}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.stringToValue(String)"})
  public void testStringToValue_when42foo_thenReturn42foo() {
    // Arrange, Act and Assert
    assertEquals("42foo", JSONObject.stringToValue("42foo"));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   *
   * <ul>
   *   <li>When {@code 42true}.
   *   <li>Then return {@code 42true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.stringToValue(String)"})
  public void testStringToValue_when42true_thenReturn42true() {
    // Arrange, Act and Assert
    assertEquals("42true", JSONObject.stringToValue("42true"));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   *
   * <ul>
   *   <li>When {@code .}.
   *   <li>Then return {@code .}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.stringToValue(String)"})
  public void testStringToValue_whenDot_thenReturnDot() {
    // Arrange, Act and Assert
    assertEquals(".", JSONObject.stringToValue("."));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.stringToValue(String)"})
  public void testStringToValue_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", JSONObject.stringToValue(""));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   *
   * <ul>
   *   <li>When {@link Boolean#FALSE} toString.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.stringToValue(String)"})
  public void testStringToValue_whenFalseToString_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((Boolean) JSONObject.stringToValue(Boolean.FALSE.toString()));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   *
   * <ul>
   *   <li>When {@code foo}.
   *   <li>Then return {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.stringToValue(String)"})
  public void testStringToValue_whenFoo_thenReturnFoo() {
    // Arrange, Act and Assert
    assertEquals("foo", JSONObject.stringToValue("foo"));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   *
   * <ul>
   *   <li>When space space.
   *   <li>Then return space space.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.stringToValue(String)"})
  public void testStringToValue_whenSpaceSpace_thenReturnSpaceSpace() {
    // Arrange, Act and Assert
    assertEquals("  ", JSONObject.stringToValue("  "));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   *
   * <ul>
   *   <li>When {@link Boolean#TRUE} toString.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.stringToValue(String)"})
  public void testStringToValue_whenTrueToString_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((Boolean) JSONObject.stringToValue(Boolean.TRUE.toString()));
  }

  /**
   * Test {@link JSONObject#testValidity(Object)}.
   *
   * <ul>
   *   <li>When {@link Double#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#testValidity(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.testValidity(Object)"})
  public void testTestValidity_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.testValidity(Double.NaN));
  }

  /**
   * Test {@link JSONObject#testValidity(Object)}.
   *
   * <ul>
   *   <li>When {@link Float#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#testValidity(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.testValidity(Object)"})
  public void testTestValidity_whenNaN_thenThrowJSONException2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.testValidity(Float.NaN));
  }

  /**
   * Test {@link JSONObject#testValidity(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#testValidity(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.testValidity(Object)"})
  public void testTestValidity_whenNull_thenDoesNotThrow() throws JSONException {
    // Arrange, Act and Assert
    JSONObject.testValidity(JSONObject.NULL);
  }

  /**
   * Test {@link JSONObject#testValidity(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#testValidity(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.testValidity(Object)"})
  public void testTestValidity_whenNull_thenDoesNotThrow2() throws JSONException {
    // Arrange, Act and Assert
    JSONObject.testValidity(null);
  }

  /**
   * Test {@link JSONObject#testValidity(Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#testValidity(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.testValidity(Object)"})
  public void testTestValidity_whenTen_thenDoesNotThrow() throws JSONException {
    // Arrange, Act and Assert
    JSONObject.testValidity(10.0d);
  }

  /**
   * Test {@link JSONObject#testValidity(Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#testValidity(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONObject.testValidity(Object)"})
  public void testTestValidity_whenTen_thenDoesNotThrow2() throws JSONException {
    // Arrange, Act and Assert
    JSONObject.testValidity(10.0f);
  }

  /**
   * Test {@link JSONObject#toJSONArray(JSONArray)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>When {@link JSONArray#JSONArray()} {@link ArrayList#ArrayList()}.
   *   <li>Then return length is two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.toJSONArray(JSONArray)"})
  public void testToJSONArray_givenArrayList_whenJSONArrayArrayList_thenReturnLengthIsTwo()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    JSONArray names = new JSONArray();
    names.put((Collection) new ArrayList<>());
    names.put(true);

    // Act and Assert
    assertEquals(2, toJSONObjectResult.toJSONArray(names).length());
  }

  /**
   * Test {@link JSONObject#toJSONArray(JSONArray)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>When {@link JSONArray#JSONArray()} {@link HashMap#HashMap()}.
   *   <li>Then return length is two.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.toJSONArray(JSONArray)"})
  public void testToJSONArray_givenHashMap_whenJSONArrayHashMap_thenReturnLengthIsTwo()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    JSONArray names = new JSONArray();
    names.put((Map) new HashMap<>());
    names.put(true);

    // Act and Assert
    assertEquals(2, toJSONObjectResult.toJSONArray(names).length());
  }

  /**
   * Test {@link JSONObject#toJSONArray(JSONArray)}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.toJSONArray(JSONArray)"})
  public void testToJSONArray_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").toJSONArray(null));
  }

  /**
   * Test {@link JSONObject#toJSONArray(JSONArray)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@link JSONArray#JSONArray()} {@code true}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.toJSONArray(JSONArray)"})
  public void testToJSONArray_givenTrue_whenJSONArrayTrue_thenReturnLengthIsOne()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    JSONArray names = new JSONArray();
    names.put(true);

    // Act and Assert
    assertEquals(1, toJSONObjectResult.toJSONArray(names).length());
  }

  /**
   * Test {@link JSONObject#toJSONArray(JSONArray)}.
   *
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONObject.toJSONArray(JSONArray)"})
  public void testToJSONArray_whenJSONArray_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act and Assert
    assertNull(toJSONObjectResult.toJSONArray(new JSONArray()));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   *
   * <p>Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(
        "{\n   \"HTTP-Version\": \"https://example.org/example\",\n   \"Reason-Phrase\": \"\",\n   \"Status-Code\": \"\"\n}",
        HTTP.toJSONObject("https://example.org/example").toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <p>Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(
        "{\n"
            + "    \"HTTP-Version\": \"https://example.org/example\",\n"
            + "    \"Reason-Phrase\": \"\",\n"
            + "    \"Status-Code\": \"\"\n"
            + " }",
        HTTP.toJSONObject("https://example.org/example").toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": 1, "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturn1NameValueNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment(": ");
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n    \": \": 1,\n    \"name\": \"\",\n    \"value\": \"\",\n    \"{\": [null]\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": 0.5, "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturn05NameValueNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", 0.5d);
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n    \": \": 0.5,\n    \"name\": \"\",\n    \"value\": \"\",\n    \"{\": [null]\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": false, "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnFalseNameValueNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", false);
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n    \": \": false,\n    \"name\": \"\",\n    \"value\": \"\",\n    \"{\": [null]\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": false, "name": "", "value": "", "{": [ null, null ] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnFalseNameValueNullNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("{", JSONObject.NULL);
    toJSONObjectResult.put(": ", false);
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n    \": \": false,\n    \"name\": \"\",\n    \"value\": \"\",\n    \"{\": [\n       null,\n       null\n    ]\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code {}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnLeftCurlyBracketRightCurlyBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", new JSONObject().toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ",\n": [null], ": ": 1, "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNNull1NameValueNull()
      throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append(",\n", JSONObject.NULL);
    toJSONObjectResult.increment(": ");
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n    \",\\n\": [null],\n    \": \": 1,\n    \"name\": \"\",\n    \"value\": \"\",\n    \"{\": [null]\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code { "name": "", "value": "" }}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNameValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(
        "{\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        Cookie.toJSONObject("=;").toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { "name": "", "value": "", "{": 10 }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNameValue10() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("{", 10.0d);

    // Act and Assert
    assertEquals(
        "{\n    \"name\": \"\",\n    \"value\": \"\",\n    \"{\": 10\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNameValueNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n    \"name\": \"\",\n    \"value\": \"\",\n    \"{\": [null]\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": [], "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNameValueNull2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Collection) new ArrayList<>());
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n    \": \": [],\n    \"name\": \"\",\n    \"value\": \"\",\n    \"{\": [null]\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": {}, "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNameValueNull3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Map) new HashMap<>());
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n    \": \": {},\n    \"name\": \"\",\n    \"value\": \"\",\n    \"{\": [null]\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code {"{": [null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"{\": [null]}", jsonObject.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code { "\"\"": [null], "name": "", "value": "" }}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNullNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n    \"\\\"\\\"\": [null],\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": 1, "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturn1NameValueNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment(": ");
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n   \": \": 1,\n   \"name\": \"\",\n   \"value\": \"\",\n   \"{\": [null]\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": 0.5, "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturn05NameValueNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", 0.5d);
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n   \": \": 0.5,\n   \"name\": \"\",\n   \"value\": \"\",\n   \"{\": [null]\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": false, "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnFalseNameValueNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", false);
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n   \": \": false,\n   \"name\": \"\",\n   \"value\": \"\",\n   \"{\": [null]\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": false, "name": "", "value": "", "{": [ null, null ] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnFalseNameValueNullNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("{", JSONObject.NULL);
    toJSONObjectResult.put(": ", false);
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n   \": \": false,\n   \"name\": \"\",\n   \"value\": \"\",\n   \"{\": [\n      null,\n      null\n   ]\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   *
   * <ul>
   *   <li>Then return {@code {}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnLeftCurlyBracketRightCurlyBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", new JSONObject().toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ",\n": [null], ": ": 1, "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNNull1NameValueNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append(",\n", JSONObject.NULL);
    toJSONObjectResult.increment(": ");
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n   \",\\n\": [null],\n   \": \": 1,\n   \"name\": \"\",\n   \"value\": \"\",\n   \"{\": [null]\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   *
   * <ul>
   *   <li>Then return {@code { "name": "", "value": "" }}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNameValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(
        "{\n   \"name\": \"\",\n   \"value\": \"\"\n}", Cookie.toJSONObject("=;").toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { "name": "", "value": "", "{": 10 }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNameValue10() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("{", 10.0d);

    // Act and Assert
    assertEquals(
        "{\n   \"name\": \"\",\n   \"value\": \"\",\n   \"{\": 10\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNameValueNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n   \"name\": \"\",\n   \"value\": \"\",\n   \"{\": [null]\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": [], "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNameValueNull2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Collection) new ArrayList<>());
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n   \": \": [],\n   \"name\": \"\",\n   \"value\": \"\",\n   \"{\": [null]\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": {}, "name": "", "value": "", "{": [null] }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNameValueNull3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Map) new HashMap<>());
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n   \": \": {},\n   \"name\": \"\",\n   \"value\": \"\",\n   \"{\": [null]\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code {"{": [null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"{\": [null]}", jsonObject.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   *
   * <ul>
   *   <li>Then return {@code { "\"\"": [null], "name": "", "value": "" }}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNullNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\n   \"\\\"\\\"\": [null],\n   \"name\": \"\",\n   \"value\": \"\"\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString()}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code =;}.
   *   <li>Then return {@code {"name":"","value":""}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_givenToJSONObjectEqualsSignSemicolon_thenReturnNameValue()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{\"name\":\"\",\"value\":\"\"}", Cookie.toJSONObject("=;").toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"\"\"":1,"name":"","{":[null,null],"value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturn1NameNullNullValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("{", JSONObject.NULL);
    toJSONObjectResult.increment("\"\"");
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\"\\\"\\\"\":1,\"name\":\"\",\"{\":[null,null],\"value\":\"\"}",
        toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"\"\"":1,"name":"","{":[null],"value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturn1NameNullValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("\"\"");
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\"\\\"\\\"\":1,\"name\":\"\",\"{\":[null],\"value\":\"\"}",
        toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"\"\"":0.5,"name":"","{":[null],"value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturn05NameNullValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", 0.5d);
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\"\\\"\\\"\":0.5,\"name\":\"\",\"{\":[null],\"value\":\"\"}",
        toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"\"\"":false,"name":"","{":[null],"value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturnFalseNameNullValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", false);
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\"\\\"\\\"\":false,\"name\":\"\",\"{\":[null],\"value\":\"\"}",
        toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   *
   * <ul>
   *   <li>Then return {@code
   *       {"HTTP-Version":"https://example.org/example","Status-Code":"","Reason-Phrase":""}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturnHttpVersionHttpsExampleOrgExampleStatusCodeReasonPhrase()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(
        "{\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}",
        HTTP.toJSONObject("https://example.org/example").toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"name":"","{":10,"value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturnName10Value() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("{", 10.0d);

    // Act and Assert
    assertEquals("{\"name\":\"\",\"{\":10,\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"name":"","{":[null],"value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturnNameNullValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"name\":\"\",\"{\":[null],\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"\"\"":{},"name":"","{":[null],"value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturnNameNullValue2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", (Map) new HashMap<>());
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\"\\\"\\\"\":{},\"name\":\"\",\"{\":[null],\"value\":\"\"}",
        toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"\"\"":[null],"name":"","{":[null],"value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturnNullNameNullValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);
    toJSONObjectResult.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\"\\\"\\\"\":[null],\"name\":\"\",\"{\":[null],\"value\":\"\"}",
        toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(
        "{\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}",
        JSONObject.valueToString(HTTP.toJSONObject("https://example.org/example")));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"\"", JSONObject.valueToString("", 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent2() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(
        "{\n"
            + "    \"HTTP-Version\": \"https://example.org/example\",\n"
            + "    \"Reason-Phrase\": \"\",\n"
            + "    \"Status-Code\": \"\"\n"
            + " }",
        JSONObject.valueToString(HTTP.toJSONObject("https://example.org/example"), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code {"{}": [null]}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{}", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"{}\": [null]}", JSONObject.valueToString(jsonObject, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When {@code 0.5}.
   *   <li>Then return {@code 0.5}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_when05_thenReturn05()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("0.5", JSONObject.valueToString(0.5d, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@code "42"}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_when42_thenReturn42()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"42\"", JSONObject.valueToString("42", 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenArrayList() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", JSONObject.valueToString(new ArrayList<>(), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenFortyTwo_thenReturn42()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", JSONObject.valueToString(42, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenHashMap() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", JSONObject.valueToString(new HashMap<>(), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", JSONObject.valueToString(new JSONArray(), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenJSONObject() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", JSONObject.valueToString(new JSONObject(), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When {@link Double#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenNaN_thenThrowJSONException()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Double.NaN, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When {@link Float#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenNaN_thenThrowJSONException2()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Float.NaN, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenNull_thenReturnNull()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.valueToString(JSONObject.NULL, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenNull_thenReturnNull2()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.valueToString(null, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return {@code 10}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenTen_thenReturn10()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.valueToString(10.0d, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return {@code 10}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenTen_thenReturn102()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.valueToString(10.0f, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code
   * indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@link Boolean#TRUE} toString.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenTrue_thenReturnTrueToString()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(Boolean.TRUE.toString(), JSONObject.valueToString(true, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given backspace.
   *   <li>Then return {@code {"\b":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenBackspace_thenReturnBNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put('\b', JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"\\b\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given cr.
   *   <li>When {@link HashMap#HashMap()} cr is {@link JSONObject#NULL}.
   *   <li>Then return {@code {"\r":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenCr_whenHashMapCrIsNull_thenReturnRNull()
      throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put('\r', JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"\\r\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given form feed (ff).
   *   <li>Then return {@code {"\f":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenFormFeed_thenReturnFNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put('\f', JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"\\f\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.
   *   <li>Then return {@code {"[]":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenJSONArray_thenReturnNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put(new JSONArray(), JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"[]\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given {@link JSONException#JSONException(String)} with message is {@code An error
   *       occurred}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenJSONExceptionWithMessageIsAnErrorOccurred()
      throws JSONException {
    // Arrange
    JSONString jsonString = mock(JSONString.class);
    when(jsonString.toJSONString()).thenThrow(new JSONException("An error occurred"));

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.valueToString(jsonString));
    verify(jsonString).toJSONString();
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>Then return {@code {"{}":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenJSONObject_thenReturnNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put(new JSONObject(), JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"{}\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given {@code Json String}.
   *   <li>Then return {@code Json String}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenJsonString_thenReturnJsonString()
      throws JSONException {
    // Arrange
    JSONString jsonString = mock(JSONString.class);
    when(jsonString.toJSONString()).thenReturn("Json String");

    // Act
    String actualValueToStringResult = JSONObject.valueToString(jsonString);

    // Assert
    verify(jsonString).toJSONString();
    assertEquals("Json String", actualValueToStringResult);
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>Given {@code {}.</li>
   *   <li>Then return {@code {"{":[null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenLeftCurlyBracket_thenReturnNull()
      throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"{\":[null]}", JSONObject.valueToString(jsonObject));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given lf.
   *   <li>When {@link HashMap#HashMap()} lf is {@link JSONObject#NULL}.
   *   <li>Then return {@code {"\n":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenLf_whenHashMapLfIsNull_thenReturnNNull()
      throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put('\n', JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"\\n\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   *   <li>Then return {@code [null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenNull_whenArrayListAddNull_thenReturnNull()
      throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", JSONObject.valueToString(objectList));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   *   <li>Then return {@code [null,null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenNull_whenArrayListAddNull_thenReturnNullNull()
      throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);
    objectList.add(JSONObject.NULL);

    // Act and Assert
    assertEquals("[null,null]", JSONObject.valueToString(objectList));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@code null} is {@link JSONObject#NULL}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenNull_whenHashMapNullIsNull_thenReturnNull()
      throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put(null, JSONObject.NULL);

    // Act and Assert
    assertNull(JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   *   <li>Then return {@code {"null":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenNull_whenHashMapNullIsNull_thenReturnNullNull()
      throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put(JSONObject.NULL, JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"null\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link JSONString} {@link JSONString#toJSONString()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenNull_whenJSONStringToJSONStringReturnNull()
      throws JSONException {
    // Arrange
    JSONString jsonString = mock(JSONString.class);
    when(jsonString.toJSONString()).thenReturn(null);

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.valueToString(jsonString));
    verify(jsonString).toJSONString();
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given {@code "}.
   *   <li>Then return {@code {"\"":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenQuotationMark_thenReturnNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put('"', JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"\\\"\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given start of heading.
   *   <li>Then return {@code {"\u0001":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenStartOfHeading_thenReturnU0001Null()
      throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put('\u0001', JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"\\u0001\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>Given tab.
   *   <li>When {@link HashMap#HashMap()} tab is {@link JSONObject#NULL}.
   *   <li>Then return {@code {"\t":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenTab_whenHashMapTabIsNull_thenReturnTNull()
      throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put('\t', JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"\\t\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When {@code 0.5}.
   *   <li>Then return {@code 0.5}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_when05_thenReturn05() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("0.5", JSONObject.valueToString(0.5d));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@code "42"}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_when42_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"42\"", JSONObject.valueToString("42"));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenArrayList() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", JSONObject.valueToString(new ArrayList<>()));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code ""}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenEmptyString_thenReturnQuotationMarkQuotationMark()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"\"", JSONObject.valueToString(""));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenFortyTwo_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", JSONObject.valueToString(42));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenHashMap() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", JSONObject.valueToString(new HashMap<>()));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", JSONObject.valueToString(new JSONArray()));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenJSONObject() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", JSONObject.valueToString(new JSONObject()));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When {@link Double#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Double.NaN));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When {@link Float#NaN}.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenNaN_thenThrowJSONException2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Float.NaN));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.valueToString(JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenNull_thenReturnNull2() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.valueToString(null));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return {@code 10}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenTen_thenReturn10() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.valueToString(10.0d));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return {@code 10}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenTen_thenReturn102() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.valueToString(10.0f));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@link Boolean#TRUE} toString.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenTrue_thenReturnTrueToString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(Boolean.TRUE.toString(), JSONObject.valueToString(true));
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add {@code null}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_givenNull_whenLinkedHashSetAddNull_thenReturnLengthIsOne() {
    // Arrange
    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add(null);

    // Act
    Object actualWrapResult = JSONObject.wrap(objectSet);

    // Assert
    assertTrue(actualWrapResult instanceof JSONArray);
    assertEquals(1, ((JSONArray) actualWrapResult).length());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   *
   * <ul>
   *   <li>When {@code A}.
   *   <li>Then return byteValue is {@code A}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenA_thenReturnByteValueIsA() {
    // Arrange and Act
    Object actualWrapResult = JSONObject.wrap((byte) 'A');

    // Assert
    assertEquals('A', ((Byte) actualWrapResult).byteValue());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   *
   * <ul>
   *   <li>When {@code false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenFalse_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((Boolean) JSONObject.wrap(false));
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.
   *   <li>Then return {@link JSONArray}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenJSONArray_thenReturnJSONArray() {
    // Arrange and Act
    Object actualWrapResult = JSONObject.wrap(new JSONArray());

    // Assert
    assertTrue(actualWrapResult instanceof JSONArray);
    assertEquals(0, ((JSONArray) actualWrapResult).length());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.
   *   <li>Then return {@link JSONObject}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenJSONObject_thenReturnJSONObject() {
    // Arrange and Act
    Object actualWrapResult = JSONObject.wrap(new JSONObject());

    // Assert
    assertTrue(actualWrapResult instanceof JSONObject);
    assertEquals(0, ((JSONObject) actualWrapResult).length());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return intValue is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenOne_thenReturnIntValueIsOne() {
    // Arrange and Act
    Object actualWrapResult = JSONObject.wrap(1);

    // Assert
    assertEquals(1, ((Integer) actualWrapResult).intValue());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return longValue is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenOne_thenReturnLongValueIsOne() {
    // Arrange and Act
    Object actualWrapResult = JSONObject.wrap(1L);

    // Assert
    assertEquals(1L, ((Long) actualWrapResult).longValue());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return shortValue is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenOne_thenReturnShortValueIsOne() {
    // Arrange and Act
    Object actualWrapResult = JSONObject.wrap((short) 1);

    // Assert
    assertEquals((short) 1, ((Short) actualWrapResult).shortValue());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   *
   * <ul>
   *   <li>When start of heading.
   *   <li>Then return charValue is start of heading.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenStartOfHeading_thenReturnCharValueIsStartOfHeading() {
    // Arrange and Act
    Object actualWrapResult = JSONObject.wrap('\u0001');

    // Assert
    assertEquals('\u0001', ((Character) actualWrapResult).charValue());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return doubleValue is ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenTen_thenReturnDoubleValueIsTen() {
    // Arrange and Act
    Object actualWrapResult = JSONObject.wrap(10.0d);

    // Assert
    assertEquals(10.0d, ((Double) actualWrapResult).doubleValue(), 0.0);
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return floatValue is ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenTen_thenReturnFloatValueIsTen() {
    // Arrange and Act
    Object actualWrapResult = JSONObject.wrap(10.0f);

    // Assert
    assertEquals(10.0f, ((Float) actualWrapResult).floatValue(), 0.0f);
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenTrue_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((Boolean) JSONObject.wrap(true));
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   *
   * <p>Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = HTTP.toJSONObject("https://example.org/example");
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals(
        "{\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}",
        writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code
   *       {"\"\"":1,"name":"","value":""}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIs1NameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("\"\"");
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"\\\"\\\"\":1,\"name\":\"\",\"value\":\"\"}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code
   *       {"\"\"":10,"name":"","value":""}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIs10NameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", 10.0d);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"\\\"\\\"\":10,\"name\":\"\",\"value\":\"\"}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code {"name":"","value":""}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\"}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code
   *       {"\"\"":{},"name":"","value":""}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsNameValue2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", (Map) new HashMap<>());
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"\\\"\\\"\":{},\"name\":\"\",\"value\":\"\"}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code
   *       {"\"\"":[null],"name":"","value":""}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsNullNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"\\\"\\\"\":[null],\"name\":\"\",\"value\":\"\"}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code
   *       {"\"\"":[null,null],"name":"","value":""}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsNullNullNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);
    toJSONObjectResult.append("\"\"", JSONObject.NULL);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"\\\"\\\"\":[null,null],\"name\":\"\",\"value\":\"\"}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code
   *       {"\"\"":[null,null],"name":"","value":"","Key":-0.5}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsNullNullNameValueKey05() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", -0.5d);
    toJSONObjectResult.append("\"\"", JSONObject.NULL);
    toJSONObjectResult.append("\"\"", JSONObject.NULL);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals(
        "{\"\\\"\\\"\":[null,null],\"name\":\"\",\"value\":\"\",\"Key\":-0.5}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code
   *       {"\"\"":true,"name":"","value":""}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsTrueNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", true);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"\\\"\\\"\":true,\"name\":\"\",\"value\":\"\"}", writer.toString());
    assertSame(writer, actualWriteResult);
  }
}
