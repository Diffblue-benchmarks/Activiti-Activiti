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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.StringWriter;
import java.io.Writer;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JSONObjectDiffblueTest {
  /**
   * Test {@link JSONObject#JSONObject()}.
   * <p>
   * Method under test: {@link JSONObject#JSONObject()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>()"})
  public void testNewJSONObject() {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONObject()).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Object, String[])}.
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Object, String[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Object, String[])"})
  public void testNewJSONObject2() {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONObject(JSONObject.NULL, new String[]{"Names"})).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code A}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenA_whenHashMapNullIsA_thenReturnLengthIsOne() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, (byte) 'A');

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONArray#JSONArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenJSONArray_whenHashMapNullIsJSONArray() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new JSONArray());

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#JSONObject()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenJSONObject_whenHashMapNullIsJSONObject() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new JSONObject());

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenNull_whenHashMapNullIsNull_thenReturnLengthIsOne() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, JSONObject.NULL);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code null}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenNull_whenHashMapNullIsNull_thenReturnLengthIsOne2() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, null);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenOne_whenHashMapNullIsOne_thenReturnLengthIsOne() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, (short) 1);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenOne_whenHashMapNullIsOne_thenReturnLengthIsOne2() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, 1);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenOne_whenHashMapNullIsOne_thenReturnLengthIsOne3() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, 1L);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link AbstractMap.SimpleEntry#SimpleEntry(Object, Object)} with {@link JSONArray#JSONArray()} and {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenSimpleEntryWithJSONArrayAndNull() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new SimpleEntry<>(new JSONArray(), JSONObject.NULL));

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link AbstractMap.SimpleEntry#SimpleEntry(Object, Object)} with {@link JSONObject#JSONObject()} and {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenSimpleEntryWithJSONObjectAndNull() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new SimpleEntry<>(new JSONObject(), JSONObject.NULL));

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link AbstractMap.SimpleEntry#SimpleEntry(Object, Object)} with {@link JSONObject#NULL} and {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenSimpleEntryWithNullAndNull() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new SimpleEntry<>(JSONObject.NULL, JSONObject.NULL));

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link AbstractMap.SimpleEntry#SimpleEntry(Object, Object)} with toJSONObject {@code https://example.org/example} and {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenSimpleEntryWithToJSONObjectHttpsExampleOrgExampleAndNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new SimpleEntry<>(HTTP.toJSONObject("https://example.org/example"), JSONObject.NULL));

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given start of heading.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is start of heading.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenStartOfHeading_whenHashMapNullIsStartOfHeading() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, '\u0001');

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is ten.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenTen_whenHashMapNullIsTen_thenReturnLengthIsOne() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, 10.0f);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code true}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_givenTrue_whenHashMapNullIsTrue_thenReturnLengthIsOne() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, true);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(JSONObject, String[])}.
   * <ul>
   *   <li>When array of {@link String} with {@code null}.</li>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(JSONObject, String[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(JSONObject, String[])"})
  public void testNewJSONObject_whenArrayOfStringWithNull_thenReturnLengthIsZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONObject(Cookie.toJSONObject("=;"), new String[]{null})).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Object)}.
   * <ul>
   *   <li>When {@code Bean}.</li>
   *   <li>Then return length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Object)"})
  public void testNewJSONObject_whenBean_thenReturnLengthIsThree() {
    // Arrange, Act and Assert
    assertEquals(3, (new JSONObject((Object) "Bean")).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Map)"})
  public void testNewJSONObject_whenHashMap_thenReturnLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONObject((Map) new HashMap<>())).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Object)"})
  public void testNewJSONObject_whenNull_thenReturnLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONObject(JSONObject.NULL)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(JSONObject, String[])}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(JSONObject, String[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(JSONObject, String[])"})
  public void testNewJSONObject_whenNull_thenReturnLengthIsZero2() {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONObject((JSONObject) null, new String[]{"Names"})).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(Object)"})
  public void testNewJSONObject_whenOne_thenReturnLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONObject(1)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(JSONObject, String[])}.
   * <ul>
   *   <li>When toJSONObject {@code =;}.</li>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(JSONObject, String[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.<init>(JSONObject, String[])"})
  public void testNewJSONObject_whenToJSONObjectEqualsSignSemicolon_thenReturnLengthIsZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONObject(Cookie.toJSONObject("=;"), new String[]{"Names"})).length());
  }

  /**
   * Test {@link JSONObject#doubleToString(double)}.
   * <ul>
   *   <li>When {@code 0.5}.</li>
   *   <li>Then return {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#doubleToString(double)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.doubleToString(double)"})
  public void testDoubleToString_when05_thenReturn05() {
    // Arrange, Act and Assert
    assertEquals("0.5", JSONObject.doubleToString(0.5d));
  }

  /**
   * Test {@link JSONObject#doubleToString(double)}.
   * <ul>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#doubleToString(double)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.doubleToString(double)"})
  public void testDoubleToString_whenNaN_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.doubleToString(Double.NaN));
  }

  /**
   * Test {@link JSONObject#doubleToString(double)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return {@code 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#doubleToString(double)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.doubleToString(double)"})
  public void testDoubleToString_whenTen_thenReturn10() {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.doubleToString(10.0d));
  }

  /**
   * Test {@link JSONObject#getNames(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Then return array of {@link String} with {@code name} and {@code value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getNames(JSONObject)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String[] JSONObject.getNames(JSONObject)"})
  public void testGetNamesWithJo_thenReturnArrayOfStringWithNameAndValue() throws JSONException {
    // Arrange, Act and Assert
    assertArrayEquals(new String[]{"name", "value"}, JSONObject.getNames(Cookie.toJSONObject("=;")));
  }

  /**
   * Test {@link JSONObject#getNames(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getNames(JSONObject)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String[] JSONObject.getNames(JSONObject)"})
  public void testGetNamesWithJo_whenJSONObject_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(JSONObject.getNames(new JSONObject()));
  }

  /**
   * Test {@link JSONObject#getNames(Object)} with {@code object}.
   * <ul>
   *   <li>Then return array of {@link String} with {@code CASE_INSENSITIVE_ORDER}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getNames(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String[] JSONObject.getNames(Object)"})
  public void testGetNamesWithObject_thenReturnArrayOfStringWithCaseInsensitiveOrder() {
    // Arrange, Act and Assert
    assertArrayEquals(new String[]{"CASE_INSENSITIVE_ORDER"}, JSONObject.getNames("Object"));
  }

  /**
   * Test {@link JSONObject#getNames(Object)} with {@code object}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getNames(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String[] JSONObject.getNames(Object)"})
  public void testGetNamesWithObject_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(JSONObject.getNames(JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#getNames(Object)} with {@code object}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getNames(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String[] JSONObject.getNames(Object)"})
  public void testGetNamesWithObject_whenNull_thenReturnNull2() {
    // Arrange, Act and Assert
    assertNull(JSONObject.getNames((Object) null));
  }

  /**
   * Test {@link JSONObject#keys()}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then return next is {@code name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#keys()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Iterator JSONObject.keys()"})
  public void testKeys_givenToJSONObjectEqualsSignSemicolon_thenReturnNextIsName() throws JSONException {
    // Arrange and Act
    Iterator actualKeysResult = Cookie.toJSONObject("=;").keys();

    // Assert
    assertEquals("name", actualKeysResult.next());
    assertEquals("value", actualKeysResult.next());
    assertFalse(actualKeysResult.hasNext());
  }

  /**
   * Test {@link JSONObject#length()}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then return two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#length()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int JSONObject.length()"})
  public void testLength_givenToJSONObjectEqualsSignSemicolon_thenReturnTwo() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(2, Cookie.toJSONObject("=;").length());
  }

  /**
   * Test {@link JSONObject#names()}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#names()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray JSONObject.names()"})
  public void testNames_givenJSONObject_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new JSONObject()).names());
  }

  /**
   * Test {@link JSONObject#names()}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then return length is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#names()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray JSONObject.names()"})
  public void testNames_givenToJSONObjectEqualsSignSemicolon_thenReturnLengthIsTwo() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(2, Cookie.toJSONObject("=;").names().length());
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   * <ul>
   *   <li>When {@code 0.5}.</li>
   *   <li>Then return {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_when05_thenReturn05() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("0.5", JSONObject.numberToString(0.5d));
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   * <ul>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.numberToString(Double.NaN));
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   * <ul>
   *   <li>When {@link Float#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_whenNaN_thenThrowJSONException2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.numberToString(Float.NaN));
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.numberToString(null));
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return {@code 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_whenTen_thenReturn10() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.numberToString(10.0d));
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return {@code 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_whenTen_thenReturn102() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.numberToString(10.0f));
  }

  /**
   * Test {@link JSONObject#numberToString(Number)}.
   * <ul>
   *   <li>When valueOf one.</li>
   *   <li>Then return {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#numberToString(Number)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.numberToString(Number)"})
  public void testNumberToString_whenValueOfOne_thenReturn1() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("1", JSONObject.numberToString(Integer.valueOf(1)));
  }

  /**
   * Test {@link JSONObject#sortedKeys()}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then return next is {@code name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#sortedKeys()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Iterator JSONObject.sortedKeys()"})
  public void testSortedKeys_givenToJSONObjectEqualsSignSemicolon_thenReturnNextIsName() throws JSONException {
    // Arrange and Act
    Iterator actualSortedKeysResult = Cookie.toJSONObject("=;").sortedKeys();

    // Assert
    assertEquals("name", actualSortedKeysResult.next());
    assertEquals("value", actualSortedKeysResult.next());
    assertFalse(actualSortedKeysResult.hasNext());
  }

  /**
   * Test {@link JSONObject#testValidity(Object)}.
   * <ul>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#testValidity(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.testValidity(Object)"})
  public void testTestValidity_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.testValidity(Double.NaN));
  }

  /**
   * Test {@link JSONObject#testValidity(Object)}.
   * <ul>
   *   <li>When {@link Float#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#testValidity(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void JSONObject.testValidity(Object)"})
  public void testTestValidity_whenNaN_thenThrowJSONException2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.testValidity(Float.NaN));
  }

  /**
   * Test {@link JSONObject#toJSONArray(JSONArray)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>When {@link JSONArray#JSONArray()} {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return length is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray JSONObject.toJSONArray(JSONArray)"})
  public void testToJSONArray_givenArrayList_whenJSONArrayArrayList_thenReturnLengthIsTwo() throws JSONException {
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
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>When {@link JSONArray#JSONArray()} {@link HashMap#HashMap()}.</li>
   *   <li>Then return length is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray JSONObject.toJSONArray(JSONArray)"})
  public void testToJSONArray_givenHashMap_whenJSONArrayHashMap_thenReturnLengthIsTwo() throws JSONException {
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
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray JSONObject.toJSONArray(JSONArray)"})
  public void testToJSONArray_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").toJSONArray(null));
  }

  /**
   * Test {@link JSONObject#toJSONArray(JSONArray)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link JSONArray#JSONArray()} {@code true}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray JSONObject.toJSONArray(JSONArray)"})
  public void testToJSONArray_givenTrue_whenJSONArrayTrue_thenReturnLengthIsOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    JSONArray names = new JSONArray();
    names.put(true);

    // Act and Assert
    assertEquals(1, toJSONObjectResult.toJSONArray(names).length());
  }

  /**
   * Test {@link JSONObject#toJSONArray(JSONArray)}.
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toJSONArray(JSONArray)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONArray JSONObject.toJSONArray(JSONArray)"})
  public void testToJSONArray_whenJSONArray_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act and Assert
    assertNull(toJSONObjectResult.toJSONArray(new JSONArray()));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(
        "{\n   \"HTTP-Version\": \"https://example.org/example\",\n   \"Reason-Phrase\": \"\",\n   \"Status-Code\": \"\"\n}",
        HTTP.toJSONObject("https://example.org/example").toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{\n" + "    \"HTTP-Version\": \"https://example.org/example\",\n" + "    \"Reason-Phrase\": \"\",\n"
        + "    \"Status-Code\": \"\"\n" + " }", HTTP.toJSONObject("https://example.org/example").toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": 1, "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturn1NameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment(": ");

    // Act and Assert
    assertEquals("{\n    \": \": 1,\n    \"name\": \"\",\n    \"value\": \"\"\n }", toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": 1, "name": "", "value": "", "{": 10 }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturn1NameValue10() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("{", 10.0d);
    toJSONObjectResult.increment(": ");

    // Act and Assert
    assertEquals("{\n    \": \": 1,\n    \"name\": \"\",\n    \"value\": \"\",\n    \"{\": 10\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": 0.5, "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturn05NameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", 0.5d);

    // Act and Assert
    assertEquals("{\n    \": \": 0.5,\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": false, "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnFalseNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", false);

    // Act and Assert
    assertEquals("{\n    \": \": false,\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", (new JSONObject()).toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ",\n": [null], "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNNullNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append(",\n", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n    \",\\n\": [null],\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNameValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{\n    \"name\": \"\",\n    \"value\": \"\"\n }", Cookie.toJSONObject("=;").toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": [], "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNameValue2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("{\n    \": \": [],\n    \"name\": \"\",\n    \"value\": \"\"\n }", toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": {}, "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNameValue3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{\n    \": \": {},\n    \"name\": \"\",\n    \"value\": \"\"\n }", toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code {": ": [null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append(": ", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\": \": [null]}", jsonObject.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": [null], "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNullNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append(": ", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n    \": \": [null],\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code { "\"\"": [null], "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNullNameValue2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n    \"\\\"\\\"\": [null],\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} append {@code :} and {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code {": ": [null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_givenJSONObjectAppendColonAndNull_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append(": ", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\": \": [null]}", jsonObject.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": 1, "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturn1NameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment(": ");

    // Act and Assert
    assertEquals("{\n   \": \": 1,\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": 1, "name": "", "value": "", "{": 10 }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturn1NameValue10() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("{", 10.0d);
    toJSONObjectResult.increment(": ");

    // Act and Assert
    assertEquals("{\n   \": \": 1,\n   \"name\": \"\",\n   \"value\": \"\",\n   \"{\": 10\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": 0.5, "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturn05NameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", 0.5d);

    // Act and Assert
    assertEquals("{\n   \": \": 0.5,\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": false, "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnFalseNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", false);

    // Act and Assert
    assertEquals("{\n   \": \": false,\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", (new JSONObject()).toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ",\n": [null], "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNNullNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append(",\n", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n   \",\\n\": [null],\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNameValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{\n   \"name\": \"\",\n   \"value\": \"\"\n}", Cookie.toJSONObject("=;").toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": [], "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNameValue2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("{\n   \": \": [],\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": {}, "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNameValue3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{\n   \": \": {},\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { ": ": [null], "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNullNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append(": ", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n   \": \": [null],\n   \"name\": \"\",\n   \"value\": \"\"\n}", toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code { "\"\"": [null], "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNullNameValue2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n   \"\\\"\\\"\": [null],\n   \"name\": \"\",\n   \"value\": \"\"\n}",
        toJSONObjectResult.toString(3));
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then return {@code {"name":"","value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_givenToJSONObjectEqualsSignSemicolon_thenReturnNameValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{\"name\":\"\",\"value\":\"\"}", Cookie.toJSONObject("=;").toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"\"\"":1,"name":"","{":10,"value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturn1Name10Value() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("{", 10.0d);
    toJSONObjectResult.increment("\"\"");

    // Act and Assert
    assertEquals("{\"\\\"\\\"\":1,\"name\":\"\",\"{\":10,\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"\"\"":1,"name":"","value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturn1NameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("\"\"");

    // Act and Assert
    assertEquals("{\"\\\"\\\"\":1,\"name\":\"\",\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"\"\"":0.5,"name":"","value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturn05NameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", 0.5d);

    // Act and Assert
    assertEquals("{\"\\\"\\\"\":0.5,\"name\":\"\",\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"\"\"":false,"name":"","value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturnFalseNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", false);

    // Act and Assert
    assertEquals("{\"\\\"\\\"\":false,\"name\":\"\",\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"HTTP-Version":"https://example.org/example","Status-Code":"","Reason-Phrase":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturnHttpVersionHttpsExampleOrgExampleStatusCodeReasonPhrase() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}",
        HTTP.toJSONObject("https://example.org/example").toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"\"\"":{},"name":"","value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturnNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{\"\\\"\\\"\":{},\"name\":\"\",\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#toString()}.
   * <ul>
   *   <li>Then return {@code {"\"\"":[null],"name":"","value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.toString()"})
  public void testToString_thenReturnNullNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"\\\"\\\"\":[null],\"name\":\"\",\"value\":\"\"}", toJSONObjectResult.toString());
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}",
        JSONObject.valueToString(HTTP.toJSONObject("https://example.org/example")));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = HTTP.toJSONObject("https://example.org/example");
    toJSONObjectResult.append("\"\"", JSONObject.NULL);

    // Act and Assert
    assertEquals(
        "{\"\\\"\\\"\":[null],\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}",
        JSONObject.valueToString(toJSONObjectResult));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"\"", JSONObject.valueToString("", 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent2() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(
        "{\n" + "    \"HTTP-Version\": \"https://example.org/example\",\n" + "    \"Reason-Phrase\": \"\",\n"
            + "    \"Status-Code\": \"\"\n" + " }",
        JSONObject.valueToString(HTTP.toJSONObject("https://example.org/example"), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code {"{}": [null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{}", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"{}\": [null]}", JSONObject.valueToString(jsonObject, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@code 0.5}.</li>
   *   <li>Then return {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_when05_thenReturn05() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("0.5", JSONObject.valueToString(0.5d, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code "42"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_when42_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"42\"", JSONObject.valueToString("42", 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenArrayList() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", JSONObject.valueToString(new ArrayList<>(), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenFortyTwo_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", JSONObject.valueToString(42, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenHashMap() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", JSONObject.valueToString(new HashMap<>(), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", JSONObject.valueToString(new JSONArray(), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenJSONObject() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", JSONObject.valueToString(new JSONObject(), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Double.NaN, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link Float#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenNaN_thenThrowJSONException2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Float.NaN, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.valueToString(JSONObject.NULL, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenNull_thenReturnNull2() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.valueToString(null, 3, 0));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return {@code 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenTen_thenReturn10() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.valueToString(10.0d, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return {@code 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenTen_thenReturn102() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.valueToString(10.0f, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value}, {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then return {@link Boolean#TRUE} toString.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object, int, int)"})
  public void testValueToStringWithValueIndentFactorIndent_whenTrue_thenReturnTrueToString() throws JSONException {
    // Arrange and Act
    String actualValueToStringResult = JSONObject.valueToString(true, 3, 1);

    // Assert
    assertEquals(Boolean.TRUE.toString(), actualValueToStringResult);
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenLeftCurlyBracket_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"{\":[null]}", JSONObject.valueToString(jsonObject));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code [null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenNull_whenArrayListAddNull_thenReturnNull() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", JSONObject.valueToString(objectList));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code {"null":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenNull_whenHashMapNullIsNull_thenReturnNullNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put(JSONObject.NULL, JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"null\":null}", JSONObject.valueToString(objectObjectMap));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link JSONArray#JSONArray()} one is {@code true}.</li>
   *   <li>Then return {@code [null,true]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_givenOne_whenJSONArrayOneIsTrue_thenReturnNullTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray();
    jsonArray.put(1, true);

    // Act and Assert
    assertEquals("[null,true]", JSONObject.valueToString(jsonArray));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When {@code 0.5}.</li>
   *   <li>Then return {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_when05_thenReturn05() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("0.5", JSONObject.valueToString(0.5d));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code "42"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_when42_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"42\"", JSONObject.valueToString("42"));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenArrayList() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", JSONObject.valueToString(new ArrayList<>()));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code ""}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenEmptyString_thenReturnQuotationMarkQuotationMark() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"\"", JSONObject.valueToString(""));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenFortyTwo_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", JSONObject.valueToString(42));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenHashMap() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", JSONObject.valueToString(new HashMap<>()));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", JSONObject.valueToString(new JSONArray()));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenJSONObject() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", JSONObject.valueToString(new JSONObject()));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Double.NaN));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When {@link Float#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenNaN_thenThrowJSONException2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Float.NaN));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.valueToString(JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenNull_thenReturnNull2() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.valueToString(null));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return {@code 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenTen_thenReturn10() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.valueToString(10.0d));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return {@code 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenTen_thenReturn102() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.valueToString(10.0f));
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then return {@link Boolean#TRUE} toString.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String JSONObject.valueToString(Object)"})
  public void testValueToStringWithValue_whenTrue_thenReturnTrueToString() throws JSONException {
    // Arrange and Act
    String actualValueToStringResult = JSONObject.valueToString(true);

    // Assert
    assertEquals(Boolean.TRUE.toString(), actualValueToStringResult);
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   * <ul>
   *   <li>When {@code A}.</li>
   *   <li>Then return byteValue is {@code A}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenA_thenReturnByteValueIsA() {
    // Arrange, Act and Assert
    assertEquals('A', ((Byte) JSONObject.wrap((byte) 'A')).byteValue());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenFalse_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((Boolean) JSONObject.wrap(false));
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.</li>
   *   <li>Then return {@link JSONArray}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.</li>
   *   <li>Then return {@link JSONObject}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return intValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenOne_thenReturnIntValueIsOne() {
    // Arrange, Act and Assert
    assertEquals(1, ((Integer) JSONObject.wrap(1)).intValue());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return longValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenOne_thenReturnLongValueIsOne() {
    // Arrange, Act and Assert
    assertEquals(1L, ((Long) JSONObject.wrap(1L)).longValue());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return shortValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenOne_thenReturnShortValueIsOne() {
    // Arrange, Act and Assert
    assertEquals((short) 1, ((Short) JSONObject.wrap((short) 1)).shortValue());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   * <ul>
   *   <li>When start of heading.</li>
   *   <li>Then return charValue is start of heading.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenStartOfHeading_thenReturnCharValueIsStartOfHeading() {
    // Arrange, Act and Assert
    assertEquals('\u0001', ((Character) JSONObject.wrap('\u0001')).charValue());
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return doubleValue is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenTen_thenReturnDoubleValueIsTen() {
    // Arrange, Act and Assert
    assertEquals(10.0d, ((Double) JSONObject.wrap(10.0d)).doubleValue(), 0.0);
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return floatValue is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenTen_thenReturnFloatValueIsTen() {
    // Arrange, Act and Assert
    assertEquals(10.0f, ((Float) JSONObject.wrap(10.0f)).floatValue(), 0.0f);
  }

  /**
   * Test {@link JSONObject#wrap(Object)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object JSONObject.wrap(Object)"})
  public void testWrap_whenTrue_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((Boolean) JSONObject.wrap(true));
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = HTTP.toJSONObject("https://example.org/example");
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}",
        writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code {"\"\"":10,"name":"","value":"","Key":1}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIs10NameValueKey1() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("\"\"", 10.0d);
    toJSONObjectResult.increment("Key");
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"\\\"\\\"\":10,\"name\":\"\",\"value\":\"\",\"Key\":1}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code {"name":"","value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code {"name":"","value":"","Key":{}}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsNameValueKey() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":{}}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code {"name":"","value":"","Key":1}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsNameValueKey1() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":1}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code {"name":"","value":"","Key":0.5}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsNameValueKey05() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", 0.5d);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":0.5}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code {"name":"","value":"","Key":false}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsNameValueKeyFalse() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":false}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code {"name":"","value":"","Key":[null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsNameValueKeyNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":[null]}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code {"name":"","value":"","Key":[null,null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Writer JSONObject.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsNameValueKeyNullNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);
    toJSONObjectResult.append("Key", JSONObject.NULL);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = toJSONObjectResult.write(writer);

    // Assert
    assertEquals("{\"name\":\"\",\"value\":\"\",\"Key\":[null,null]}", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code {"\"\"":[null],"name":"","value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
}
