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
import static org.mockito.Mockito.mock;
import java.io.StringWriter;
import java.io.Writer;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class JSONObjectDiffblueTest {
  /**
   * Test {@link JSONObject#JSONObject()}.
   * <p>
   * Method under test: {@link JSONObject#JSONObject()}
   */
  @Test
  public void testNewJSONObject() {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONObject()).length());
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
   *   <li>Given {@link BiFunction}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject_givenBiFunction_whenHashMapComputeIfPresentNullAndBiFunction() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    map.put(JSONObject.NULL, JSONObject.NULL);

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONArray#JSONArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
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
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#JSONObject()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
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
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
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
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@code null}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
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
   *   <li>Given {@link SimpleEntry#SimpleEntry(Object, Object)} with
   * {@link JSONObject#NULL} and {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
  public void testNewJSONObject_givenSimpleEntryWithNullAndNull() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.put(JSONObject.NULL, new AbstractMap.SimpleEntry<>(JSONObject.NULL, JSONObject.NULL));

    // Act and Assert
    assertEquals(1, (new JSONObject((Map) map)).length());
  }

  /**
   * Test {@link JSONObject#JSONObject(Map)}.
   * <ul>
   *   <li>Given start of heading.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is start of
   * heading.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
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
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@code true}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#JSONObject(Map)}
   */
  @Test
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
  public void testNewJSONObject_whenNull_thenReturnLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONObject(JSONObject.NULL)).length());
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
  public void testNewJSONObject_whenToJSONObjectEqualsSignSemicolon_thenReturnLengthIsZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONObject(Cookie.toJSONObject("=;"), new String[]{"Names"})).length());
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull_whenNull() throws JSONException {
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
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.</li>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate_givenToJSONObjectEqualsSignSemicolonIncrementKey_whenNull() throws JSONException {
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
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate_whenJSONArray_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
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
   * <ul>
   *   <li>When {@link JSONArray}.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate_whenJSONArray_thenToJSONObjectEqualsSignSemicolonLengthIsThree2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAccumulateResult = toJSONObjectResult.accumulate("Key", mock(JSONArray.class));

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAccumulateResult);
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   * <ul>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").accumulate("Key", Double.NaN));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").accumulate("Key", Float.NaN));
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").accumulate(null, JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#accumulate(String, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
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
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then toJSONObject {@code =;} length is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsTwo() throws JSONException {
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
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
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
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#accumulate(String, Object)}
   */
  @Test
  public void testAccumulate_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree2() throws JSONException {
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
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONArray}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend_givenToJSONObjectEqualsSignSemicolonAppendKeyAndJSONArray() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", mock(JSONArray.class));

    // Act
    JSONObject actualAppendResult = toJSONObjectResult.append("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAppendResult);
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
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
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend_givenToJSONObjectEqualsSignSemicolonIncrementKey() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.append("Key", JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend_givenToJSONObjectEqualsSignSemicolon_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").append("Key", Double.NaN));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").append("Key", Float.NaN));
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend_givenToJSONObjectEqualsSignSemicolon_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").append(null, JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
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
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsThree2() throws JSONException {
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
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualAppendResult = toJSONObjectResult.append("Key", 10.0d);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualAppendResult);
  }

  /**
   * Test {@link JSONObject#append(String, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#append(String, Object)}
   */
  @Test
  public void testAppend_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree2() throws JSONException {
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
   * <ul>
   *   <li>When {@code 0.5}.</li>
   *   <li>Then return {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#doubleToString(double)}
   */
  @Test
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
  public void testDoubleToString_whenTen_thenReturn10() {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.doubleToString(10.0d));
  }

  /**
   * Test {@link JSONObject#get(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@link JSONArray}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#get(String)}
   */
  @Test
  public void testGet_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull_thenReturnJSONArray() throws JSONException {
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
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#get(String)}
   */
  @Test
  public void testGet_givenToJSONObjectEqualsSignSemicolon_whenKey_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").get("Key"));
  }

  /**
   * Test {@link JSONObject#get(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#get(String)}
   */
  @Test
  public void testGet_givenToJSONObjectEqualsSignSemicolon_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").get(null));
  }

  /**
   * Test {@link JSONObject#get(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#get(String)}
   */
  @Test
  public void testGet_whenEmptyString_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").get(""));
  }

  /**
   * Test {@link JSONObject#getBoolean(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  public void testGetBoolean_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#getBoolean(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code false}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  public void testGetBoolean_givenToJSONObjectEqualsSignSemicolonKeyIsFalse_thenReturnFalse() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act and Assert
    assertFalse(toJSONObjectResult.getBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#getBoolean(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  public void testGetBoolean_givenToJSONObjectEqualsSignSemicolonKeyIsTrue_thenReturnTrue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act and Assert
    assertTrue(toJSONObjectResult.getBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#getBoolean(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  public void testGetBoolean_givenToJSONObjectEqualsSignSemicolon_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#getBoolean(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  public void testGetBoolean_givenToJSONObjectEqualsSignSemicolon_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getBoolean(""));
  }

  /**
   * Test {@link JSONObject#getBoolean(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getBoolean(String)}
   */
  @Test
  public void testGetBoolean_givenToJSONObjectEqualsSignSemicolon_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getBoolean(null));
  }

  /**
   * Test {@link JSONObject#getDouble(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  public void testGetDouble_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getDouble("Key"));
  }

  /**
   * Test {@link JSONObject#getDouble(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  public void testGetDouble_givenToJSONObjectEqualsSignSemicolonIncrementKey_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1.0d, toJSONObjectResult.getDouble("Key"), 0.0);
  }

  /**
   * Test {@link JSONObject#getDouble(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  public void testGetDouble_givenToJSONObjectEqualsSignSemicolon_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getDouble("Key"));
  }

  /**
   * Test {@link JSONObject#getDouble(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  public void testGetDouble_givenToJSONObjectEqualsSignSemicolon_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getDouble(""));
  }

  /**
   * Test {@link JSONObject#getDouble(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getDouble(String)}
   */
  @Test
  public void testGetDouble_givenToJSONObjectEqualsSignSemicolon_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getDouble(null));
  }

  /**
   * Test {@link JSONObject#getInt(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  public void testGetInt_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getInt("Key"));
  }

  /**
   * Test {@link JSONObject#getInt(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  public void testGetInt_givenToJSONObjectEqualsSignSemicolonIncrementKey_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1, toJSONObjectResult.getInt("Key"));
  }

  /**
   * Test {@link JSONObject#getInt(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  public void testGetInt_givenToJSONObjectEqualsSignSemicolon_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getInt(""));
  }

  /**
   * Test {@link JSONObject#getInt(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  public void testGetInt_givenToJSONObjectEqualsSignSemicolon_whenKey_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getInt("Key"));
  }

  /**
   * Test {@link JSONObject#getInt(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getInt(String)}
   */
  @Test
  public void testGetInt_givenToJSONObjectEqualsSignSemicolon_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getInt(null));
  }

  /**
   * Test {@link JSONObject#getJSONArray(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONArray}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  public void testGetJSONArray_givenToJSONObjectEqualsSignSemicolonAppendKeyAndJSONArray() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", mock(JSONArray.class));

    // Act and Assert
    assertEquals(1, toJSONObjectResult.getJSONArray("Key").length());
  }

  /**
   * Test {@link JSONObject#getJSONArray(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  public void testGetJSONArray_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(1, toJSONObjectResult.getJSONArray("Key").length());
  }

  /**
   * Test {@link JSONObject#getJSONArray(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  public void testGetJSONArray_givenToJSONObjectEqualsSignSemicolonIncrementKey() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getJSONArray("Key"));
  }

  /**
   * Test {@link JSONObject#getJSONArray(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  public void testGetJSONArray_givenToJSONObjectEqualsSignSemicolon_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONArray("Key"));
  }

  /**
   * Test {@link JSONObject#getJSONArray(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  public void testGetJSONArray_givenToJSONObjectEqualsSignSemicolon_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONArray(""));
  }

  /**
   * Test {@link JSONObject#getJSONArray(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getJSONArray(String)}
   */
  @Test
  public void testGetJSONArray_givenToJSONObjectEqualsSignSemicolon_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONArray(null));
  }

  /**
   * Test {@link JSONObject#getJSONObject(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  public void testGetJSONObject_givenHashMapComputeIfPresentNullAndBiFunction() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals(0, toJSONObjectResult.getJSONObject("Key").length());
  }

  /**
   * Test {@link JSONObject#getJSONObject(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  public void testGetJSONObject_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getJSONObject("Key"));
  }

  /**
   * Test {@link JSONObject#getJSONObject(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  public void testGetJSONObject_givenToJSONObjectEqualsSignSemicolon_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONObject("Key"));
  }

  /**
   * Test {@link JSONObject#getJSONObject(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  public void testGetJSONObject_givenToJSONObjectEqualsSignSemicolon_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONObject(""));
  }

  /**
   * Test {@link JSONObject#getJSONObject(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  public void testGetJSONObject_givenToJSONObjectEqualsSignSemicolon_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getJSONObject(null));
  }

  /**
   * Test {@link JSONObject#getJSONObject(String)}.
   * <ul>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getJSONObject(String)}
   */
  @Test
  public void testGetJSONObject_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals(0, toJSONObjectResult.getJSONObject("Key").length());
  }

  /**
   * Test {@link JSONObject#getLong(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  public void testGetLong_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.getLong("Key"));
  }

  /**
   * Test {@link JSONObject#getLong(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  public void testGetLong_givenToJSONObjectEqualsSignSemicolonIncrementKey_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1L, toJSONObjectResult.getLong("Key"));
  }

  /**
   * Test {@link JSONObject#getLong(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  public void testGetLong_givenToJSONObjectEqualsSignSemicolon_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getLong(""));
  }

  /**
   * Test {@link JSONObject#getLong(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  public void testGetLong_givenToJSONObjectEqualsSignSemicolon_whenKey_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getLong("Key"));
  }

  /**
   * Test {@link JSONObject#getLong(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getLong(String)}
   */
  @Test
  public void testGetLong_givenToJSONObjectEqualsSignSemicolon_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getLong(null));
  }

  /**
   * Test {@link JSONObject#getNames(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Then return array of {@link String} with {@code name} and
   * {@code value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getNames(JSONObject)}
   */
  @Test
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
  public void testGetNamesWithJo_whenJSONObject_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(JSONObject.getNames(new JSONObject()));
  }

  /**
   * Test {@link JSONObject#getNames(Object)} with {@code object}.
   * <ul>
   *   <li>Then return array of {@link String} with
   * {@code CASE_INSENSITIVE_ORDER}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getNames(Object)}
   */
  @Test
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
  public void testGetNamesWithObject_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(JSONObject.getNames(JSONObject.NULL));
    assertNull(JSONObject.getNames((Object) null));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   *   <li>Then return {@code {"null":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString_givenHashMapComputeIfPresentNullAndBiFunction_thenReturnNullNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@code {"null":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString_givenHashMapNullIsNull_whenKey_thenReturnNullNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@code 42}.</li>
   *   <li>Then return {@code ["42"]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString_givenToJSONObjectEqualsSignSemicolonAppendKeyAnd42_thenReturn42() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "42");

    // Act and Assert
    assertEquals("[\"42\"]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", null);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.</li>
   *   <li>Then return {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString_givenToJSONObjectEqualsSignSemicolonIncrementKey_thenReturn1() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals("1", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString_givenToJSONObjectEqualsSignSemicolon_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   * <ul>
   *   <li>Then return {@code [42]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString_thenReturn42() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", 42);

    // Act and Assert
    assertEquals("[42]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   * <ul>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   * <ul>
   *   <li>Then return {@code [null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.getString("Key"));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   * <ul>
   *   <li>Then return {@code [null,null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
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
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString_whenEmptyString_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getString(""));
  }

  /**
   * Test {@link JSONObject#getString(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#getString(String)}
   */
  @Test
  public void testGetString_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").getString(null));
  }

  /**
   * Test {@link JSONObject#has(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#has(String)}
   */
  @Test
  public void testHas_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull_thenReturnTrue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertTrue(toJSONObjectResult.has("Key"));
  }

  /**
   * Test {@link JSONObject#has(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#has(String)}
   */
  @Test
  public void testHas_givenToJSONObjectEqualsSignSemicolon_thenReturnFalse() throws JSONException {
    // Arrange, Act and Assert
    assertFalse(Cookie.toJSONObject("=;").has("Key"));
  }

  /**
   * Test {@link JSONObject#increment(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#increment(String)}
   */
  @Test
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
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  public void testIncrement_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.increment("Key"));
  }

  /**
   * Test {@link JSONObject#increment(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  public void testIncrement_givenToJSONObjectEqualsSignSemicolonIncrementKey() throws JSONException {
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
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  public void testIncrement_givenToJSONObjectEqualsSignSemicolonKeyIs05() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", 0.5d);

    // Act
    JSONObject actualIncrementResult = toJSONObjectResult.increment("Key");

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualIncrementResult);
  }

  /**
   * Test {@link JSONObject#increment(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  public void testIncrement_givenToJSONObjectEqualsSignSemicolonKeyIsOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", 1L);

    // Act
    JSONObject actualIncrementResult = toJSONObjectResult.increment("Key");

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualIncrementResult);
  }

  /**
   * Test {@link JSONObject#increment(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#increment(String)}
   */
  @Test
  public void testIncrement_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").increment(null));
  }

  /**
   * Test {@link JSONObject#isNull(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#isNull(String)}
   */
  @Test
  public void testIsNull_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull_thenReturnFalse() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertFalse(toJSONObjectResult.isNull("Key"));
  }

  /**
   * Test {@link JSONObject#isNull(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#isNull(String)}
   */
  @Test
  public void testIsNull_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnTrue() throws JSONException {
    // Arrange, Act and Assert
    assertTrue(Cookie.toJSONObject("=;").isNull("Key"));
  }

  /**
   * Test {@link JSONObject#isNull(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#isNull(String)}
   */
  @Test
  public void testIsNull_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnTrue() throws JSONException {
    // Arrange, Act and Assert
    assertTrue(Cookie.toJSONObject("=;").isNull(null));
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
  public void testNumberToString_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.numberToString(Double.NaN));
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
  public void testNumberToString_whenTen_thenReturn10() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.numberToString(10.0d));
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
  public void testNumberToString_whenValueOfOne_thenReturn1() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("1", JSONObject.numberToString(Integer.valueOf(1)));
  }

  /**
   * Test {@link JSONObject#opt(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#opt(String)}
   */
  @Test
  public void testOpt_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").opt("Key"));
  }

  /**
   * Test {@link JSONObject#opt(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#opt(String)}
   */
  @Test
  public void testOpt_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").opt(null));
  }

  /**
   * Test {@link JSONObject#optBoolean(String, boolean)} with {@code key},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  public void testOptBooleanWithKeyDefaultValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertTrue(toJSONObjectResult.optBoolean("Key", true));
  }

  /**
   * Test {@link JSONObject#optBoolean(String, boolean)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  public void testOptBooleanWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon() throws JSONException {
    // Arrange, Act and Assert
    assertTrue(Cookie.toJSONObject("=;").optBoolean("Key", true));
  }

  /**
   * Test {@link JSONObject#optBoolean(String, boolean)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  public void testOptBooleanWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolonKeyIsTrue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act and Assert
    assertTrue(toJSONObjectResult.optBoolean("Key", true));
  }

  /**
   * Test {@link JSONObject#optBoolean(String, boolean)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  public void testOptBooleanWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertTrue(Cookie.toJSONObject("=;").optBoolean(null, true));
  }

  /**
   * Test {@link JSONObject#optBoolean(String, boolean)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  public void testOptBooleanWithKeyDefaultValue_thenReturnFalse() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act and Assert
    assertFalse(toJSONObjectResult.optBoolean("Key", true));
  }

  /**
   * Test {@link JSONObject#optBoolean(String, boolean)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optBoolean(String, boolean)}
   */
  @Test
  public void testOptBooleanWithKeyDefaultValue_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertTrue(Cookie.toJSONObject("=;").optBoolean("", true));
  }

  /**
   * Test {@link JSONObject#optBoolean(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  public void testOptBooleanWithKey_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertFalse(toJSONObjectResult.optBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#optBoolean(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  public void testOptBooleanWithKey_givenToJSONObjectEqualsSignSemicolonKeyIsFalse() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act and Assert
    assertFalse(toJSONObjectResult.optBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#optBoolean(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  public void testOptBooleanWithKey_givenToJSONObjectEqualsSignSemicolon_thenReturnFalse() throws JSONException {
    // Arrange, Act and Assert
    assertFalse(Cookie.toJSONObject("=;").optBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#optBoolean(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  public void testOptBooleanWithKey_givenToJSONObjectEqualsSignSemicolon_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertFalse(Cookie.toJSONObject("=;").optBoolean(""));
  }

  /**
   * Test {@link JSONObject#optBoolean(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  public void testOptBooleanWithKey_givenToJSONObjectEqualsSignSemicolon_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertFalse(Cookie.toJSONObject("=;").optBoolean(null));
  }

  /**
   * Test {@link JSONObject#optBoolean(String)} with {@code key}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optBoolean(String)}
   */
  @Test
  public void testOptBooleanWithKey_thenReturnTrue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act and Assert
    assertTrue(toJSONObjectResult.optBoolean("Key"));
  }

  /**
   * Test {@link JSONObject#optDouble(String, double)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optDouble(String, double)}
   */
  @Test
  public void testOptDoubleWithKeyDefaultValue_givenJSONObjectAppendKeyAndNull_thenReturnTen() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(10.0d, jsonObject.optDouble("Key", 10.0d), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String, double)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optDouble(String, double)}
   */
  @Test
  public void testOptDoubleWithKeyDefaultValue_givenJSONObjectAppendKeyAndNull_whenNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(10.0d, jsonObject.optDouble(null, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String, double)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} increment {@code Key}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optDouble(String, double)}
   */
  @Test
  public void testOptDoubleWithKeyDefaultValue_givenJSONObjectIncrementKey_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.increment("Key");

    // Act and Assert
    assertEquals(1.0d, jsonObject.optDouble("Key", 10.0d), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String, double)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optDouble(String, double)}
   */
  @Test
  public void testOptDoubleWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(10.0d, Cookie.toJSONObject("=;").optDouble("Key", 10.0d), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String)} with {@code key}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@link Double#NaN}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optDouble(String)}
   */
  @Test
  public void testOptDoubleWithKey_givenJSONObjectAppendKeyAndNull_whenKey_thenReturnNaN() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(Double.NaN, jsonObject.optDouble("Key"), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String)} with {@code key}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link Double#NaN}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optDouble(String)}
   */
  @Test
  public void testOptDoubleWithKey_givenJSONObjectAppendKeyAndNull_whenNull_thenReturnNaN() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(Double.NaN, jsonObject.optDouble(null), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String)} with {@code key}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} increment {@code Key}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optDouble(String)}
   */
  @Test
  public void testOptDoubleWithKey_givenJSONObjectIncrementKey_whenKey_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.increment("Key");

    // Act and Assert
    assertEquals(1.0d, jsonObject.optDouble("Key"), 0.0);
  }

  /**
   * Test {@link JSONObject#optDouble(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@link Double#NaN}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optDouble(String)}
   */
  @Test
  public void testOptDoubleWithKey_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnNaN() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(Double.NaN, Cookie.toJSONObject("=;").optDouble("Key"), 0.0);
  }

  /**
   * Test {@link JSONObject#optInt(String, int)} with {@code key},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONObject#optInt(String, int)}
   */
  @Test
  public void testOptIntWithKeyDefaultValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(42, toJSONObjectResult.optInt("Key", 42));
  }

  /**
   * Test {@link JSONObject#optInt(String, int)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optInt(String, int)}
   */
  @Test
  public void testOptIntWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, Cookie.toJSONObject("=;").optInt("Key", 42));
  }

  /**
   * Test {@link JSONObject#optInt(String, int)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optInt(String, int)}
   */
  @Test
  public void testOptIntWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, Cookie.toJSONObject("=;").optInt(null, 42));
  }

  /**
   * Test {@link JSONObject#optInt(String, int)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optInt(String, int)}
   */
  @Test
  public void testOptIntWithKeyDefaultValue_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1, toJSONObjectResult.optInt("Key", 42));
  }

  /**
   * Test {@link JSONObject#optInt(String, int)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optInt(String, int)}
   */
  @Test
  public void testOptIntWithKeyDefaultValue_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, Cookie.toJSONObject("=;").optInt("", 42));
  }

  /**
   * Test {@link JSONObject#optInt(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  public void testOptIntWithKey_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(0, toJSONObjectResult.optInt("Key"));
  }

  /**
   * Test {@link JSONObject#optInt(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} increment {@code Key}.</li>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  public void testOptIntWithKey_givenToJSONObjectEqualsSignSemicolonIncrementKey_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1, toJSONObjectResult.optInt("Key"));
  }

  /**
   * Test {@link JSONObject#optInt(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  public void testOptIntWithKey_givenToJSONObjectEqualsSignSemicolon_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, Cookie.toJSONObject("=;").optInt(""));
  }

  /**
   * Test {@link JSONObject#optInt(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  public void testOptIntWithKey_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, Cookie.toJSONObject("=;").optInt("Key"));
  }

  /**
   * Test {@link JSONObject#optInt(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optInt(String)}
   */
  @Test
  public void testOptIntWithKey_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, Cookie.toJSONObject("=;").optInt(null));
  }

  /**
   * Test {@link JSONObject#optJSONArray(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONArray}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optJSONArray(String)}
   */
  @Test
  public void testOptJSONArray_givenToJSONObjectEqualsSignSemicolonAppendKeyAndJSONArray() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", mock(JSONArray.class));

    // Act and Assert
    assertEquals(1, toJSONObjectResult.optJSONArray("Key").length());
  }

  /**
   * Test {@link JSONObject#optJSONArray(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optJSONArray(String)}
   */
  @Test
  public void testOptJSONArray_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(1, toJSONObjectResult.optJSONArray("Key").length());
  }

  /**
   * Test {@link JSONObject#optJSONArray(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optJSONArray(String)}
   */
  @Test
  public void testOptJSONArray_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").optJSONArray("Key"));
  }

  /**
   * Test {@link JSONObject#optJSONArray(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optJSONArray(String)}
   */
  @Test
  public void testOptJSONArray_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").optJSONArray(null));
  }

  /**
   * Test {@link JSONObject#optJSONObject(String)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optJSONObject(String)}
   */
  @Test
  public void testOptJSONObject_givenHashMapComputeIfPresentNullAndBiFunction() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals(0, toJSONObjectResult.optJSONObject("Key").length());
  }

  /**
   * Test {@link JSONObject#optJSONObject(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optJSONObject(String)}
   */
  @Test
  public void testOptJSONObject_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").optJSONObject("Key"));
  }

  /**
   * Test {@link JSONObject#optJSONObject(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optJSONObject(String)}
   */
  @Test
  public void testOptJSONObject_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(Cookie.toJSONObject("=;").optJSONObject(null));
  }

  /**
   * Test {@link JSONObject#optJSONObject(String)}.
   * <ul>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optJSONObject(String)}
   */
  @Test
  public void testOptJSONObject_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals(0, toJSONObjectResult.optJSONObject("Key").length());
  }

  /**
   * Test {@link JSONObject#optLong(String, long)} with {@code key},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONObject#optLong(String, long)}
   */
  @Test
  public void testOptLongWithKeyDefaultValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(42L, toJSONObjectResult.optLong("Key", 42L));
  }

  /**
   * Test {@link JSONObject#optLong(String, long)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optLong(String, long)}
   */
  @Test
  public void testOptLongWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, Cookie.toJSONObject("=;").optLong("Key", 42L));
  }

  /**
   * Test {@link JSONObject#optLong(String, long)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optLong(String, long)}
   */
  @Test
  public void testOptLongWithKeyDefaultValue_givenToJSONObjectEqualsSignSemicolon_whenNull() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, Cookie.toJSONObject("=;").optLong(null, 42L));
  }

  /**
   * Test {@link JSONObject#optLong(String, long)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optLong(String, long)}
   */
  @Test
  public void testOptLongWithKeyDefaultValue_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1L, toJSONObjectResult.optLong("Key", 42L));
  }

  /**
   * Test {@link JSONObject#optLong(String, long)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optLong(String, long)}
   */
  @Test
  public void testOptLongWithKeyDefaultValue_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, Cookie.toJSONObject("=;").optLong("", 42L));
  }

  /**
   * Test {@link JSONObject#optLong(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  public void testOptLongWithKey_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals(0L, toJSONObjectResult.optLong("Key"));
  }

  /**
   * Test {@link JSONObject#optLong(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  public void testOptLongWithKey_givenToJSONObjectEqualsSignSemicolon_whenEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0L, Cookie.toJSONObject("=;").optLong(""));
  }

  /**
   * Test {@link JSONObject#optLong(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  public void testOptLongWithKey_givenToJSONObjectEqualsSignSemicolon_whenKey_thenReturnZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0L, Cookie.toJSONObject("=;").optLong("Key"));
  }

  /**
   * Test {@link JSONObject#optLong(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  public void testOptLongWithKey_givenToJSONObjectEqualsSignSemicolon_whenNull_thenReturnZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0L, Cookie.toJSONObject("=;").optLong(null));
  }

  /**
   * Test {@link JSONObject#optLong(String)} with {@code key}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optLong(String)}
   */
  @Test
  public void testOptLongWithKey_thenReturnOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals(1L, toJSONObjectResult.optLong("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   * <p>
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptStringWithKey() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "");

    // Act and Assert
    assertEquals("[\"\"]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptStringWithKeyDefaultValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", null);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptStringWithKeyDefaultValue2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "");

    // Act and Assert
    assertEquals("[\"\"]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptStringWithKeyDefaultValue_givenHashMapComputeIfPresentNullAndBiFunction() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code {"null":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptStringWithKeyDefaultValue_givenHashMapNullIsNull_thenReturnNullNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptStringWithKeyDefaultValue_thenReturn1() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals("1", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptStringWithKeyDefaultValue_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", Cookie.toJSONObject("=;").optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code [42]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptStringWithKeyDefaultValue_thenReturn422() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", 42);

    // Act and Assert
    assertEquals("[42]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code ["42"]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptStringWithKeyDefaultValue_thenReturn423() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "42");

    // Act and Assert
    assertEquals("[\"42\"]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptStringWithKeyDefaultValue_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code [null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptStringWithKeyDefaultValue_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code [null,null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptStringWithKeyDefaultValue_thenReturnNullNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null,null]", toJSONObjectResult.optString("Key", "42"));
  }

  /**
   * Test {@link JSONObject#optString(String, String)} with {@code key},
   * {@code defaultValue}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String, String)}
   */
  @Test
  public void testOptStringWithKeyDefaultValue_whenNull_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", Cookie.toJSONObject("=;").optString(null, "42"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptStringWithKey_givenHashMapComputeIfPresentNullAndBiFunction() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@code {"null":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptStringWithKey_givenHashMapNullIsNull_whenKey_thenReturnNullNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptStringWithKey_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", null);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptStringWithKey_givenToJSONObjectEqualsSignSemicolon_thenReturnEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", Cookie.toJSONObject("=;").optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   * <ul>
   *   <li>Then return {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptStringWithKey_thenReturn1() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment("Key");

    // Act and Assert
    assertEquals("1", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   * <ul>
   *   <li>Then return {@code [42]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptStringWithKey_thenReturn42() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", 42);

    // Act and Assert
    assertEquals("[42]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   * <ul>
   *   <li>Then return {@code ["42"]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptStringWithKey_thenReturn422() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", "42");

    // Act and Assert
    assertEquals("[\"42\"]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   * <ul>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptStringWithKey_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   * <ul>
   *   <li>Then return {@code [null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptStringWithKey_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   * <ul>
   *   <li>Then return {@code [null,null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptStringWithKey_thenReturnNullNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("[null,null]", toJSONObjectResult.optString("Key"));
  }

  /**
   * Test {@link JSONObject#optString(String)} with {@code key}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#optString(String)}
   */
  @Test
  public void testOptStringWithKey_whenNull_thenReturnEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", Cookie.toJSONObject("=;").optString(null));
  }

  /**
   * Test {@link JSONObject#put(String, boolean)} with {@code String},
   * {@code boolean}.
   * <ul>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, boolean)}
   */
  @Test
  public void testPutWithStringBoolean_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", true);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, boolean)} with {@code String},
   * {@code boolean}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, boolean)}
   */
  @Test
  public void testPutWithStringBoolean_whenFalse() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", false);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, boolean)} with {@code String},
   * {@code boolean}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, boolean)}
   */
  @Test
  public void testPutWithStringBoolean_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, true));
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code A}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenA_whenArrayListAddA() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add((byte) 'A');

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} first {@link JSONArray}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenJSONArray_thenArrayListFirstJSONArray() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    JSONArray jsonArray = new JSONArray();
    value.add(jsonArray);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    Object getResult = value.get(0);
    assertTrue(getResult instanceof JSONArray);
    assertEquals(3, toJSONObjectResult.length());
    assertSame(jsonArray, getResult);
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONArray}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONArray}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenJSONArray_whenArrayListAddJSONArray() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(mock(JSONArray.class));

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} first {@link JSONObject}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenJSONObject_thenArrayListFirstJSONObject() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    JSONObject jsonObject = new JSONObject();
    value.add(jsonObject);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    Object getResult = value.get(0);
    assertTrue(getResult instanceof JSONObject);
    assertEquals(3, toJSONObjectResult.length());
    assertSame(jsonObject, getResult);
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} first is {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenNull_thenArrayListFirstIsNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
    Object expectedGetResult = actualPutResult.NULL;
    assertSame(expectedGetResult, value.get(0));
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} first is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenNull_thenArrayListFirstIsNull2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(null);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertNull(value.get(0));
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenNull_thenArrayListSizeIsTwo() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);
    value.add(JSONObject.NULL);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(2, value.size());
    Object object = actualPutResult.NULL;
    assertSame(object, value.get(0));
    assertSame(object, value.get(1));
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link ArrayList#ArrayList()} add one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenOne_whenArrayListAddOne() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add((short) 1);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link ArrayList#ArrayList()} add one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenOne_whenArrayListAddOne2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(1L);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given start of heading.</li>
   *   <li>When {@link ArrayList#ArrayList()} add start of heading.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenStartOfHeading_whenArrayListAddStartOfHeading() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add('\u0001');

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>Then {@link ArrayList#ArrayList()} first doubleValue is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenTen_thenArrayListFirstDoubleValueIsTen() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(10.0d);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(10.0d, ((Double) value.get(0)).doubleValue(), 0.0);
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>Then {@link ArrayList#ArrayList()} first floatValue is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenTen_thenArrayListFirstFloatValueIsTen() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(10.0f);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(1, value.size());
    assertEquals(10.0f, ((Float) value.get(0)).floatValue(), 0.0f);
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenTrue_whenArrayListAddTrue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(true);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>Given two.</li>
   *   <li>When {@link ArrayList#ArrayList()} add two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_givenTwo_whenArrayListAddTwo() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    ArrayList<Object> value = new ArrayList<>();
    value.add(2);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Collection) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
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
   * Test {@link JSONObject#put(String, Collection)} with {@code String},
   * {@code Collection}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Collection)}
   */
  @Test
  public void testPutWithStringCollection_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.put(null, (Collection) new ArrayList<>()));
  }

  /**
   * Test {@link JSONObject#put(String, double)} with {@code String},
   * {@code double}.
   * <ul>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, double)}
   */
  @Test
  public void testPutWithStringDouble_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", 10.0d);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, double)} with {@code String},
   * {@code double}.
   * <ul>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, double)}
   */
  @Test
  public void testPutWithStringDouble_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put("Key", Double.NaN));
  }

  /**
   * Test {@link JSONObject#put(String, double)} with {@code String},
   * {@code double}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, double)}
   */
  @Test
  public void testPutWithStringDouble_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, 10.0d));
  }

  /**
   * Test {@link JSONObject#put(String, int)} with {@code String}, {@code int}.
   * <ul>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, int)}
   */
  @Test
  public void testPutWithStringInt_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", 42);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, int)} with {@code String}, {@code int}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, int)}
   */
  @Test
  public void testPutWithStringInt_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, 42));
  }

  /**
   * Test {@link JSONObject#put(String, long)} with {@code String}, {@code long}.
   * <ul>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, long)}
   */
  @Test
  public void testPutWithStringLong_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", 42L);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, long)} with {@code String}, {@code long}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, long)}
   */
  @Test
  public void testPutWithStringLong_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, 42L));
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code A}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPutWithStringMap_givenBiFunction() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONArray#JSONArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPutWithStringMap_givenJSONArray_whenHashMapNullIsJSONArray() throws JSONException {
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
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#JSONObject()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPutWithStringMap_givenJSONObject_whenHashMapNullIsJSONObject() throws JSONException {
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
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
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
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
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
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
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
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@link SimpleEntry#SimpleEntry(Object, Object)} with
   * {@link JSONObject#NULL} and {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPutWithStringMap_givenSimpleEntryWithNullAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new AbstractMap.SimpleEntry<>(JSONObject.NULL, JSONObject.NULL));

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Map) value);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   * <ul>
   *   <li>Given start of heading.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is start of
   * heading.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPutWithStringMap_givenStartOfHeading_whenHashMapNullIsStartOfHeading() throws JSONException {
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
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
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
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
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
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
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
   * Test {@link JSONObject#put(String, Map)} with {@code String}, {@code Map}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Map)}
   */
  @Test
  public void testPutWithStringMap_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.put(null, (Map) new HashMap<>()));
  }

  /**
   * Test {@link JSONObject#put(String, Object)} with {@code String},
   * {@code Object}.
   * <ul>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPutWithStringObject_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", JSONObject.NULL);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Object)} with {@code String},
   * {@code Object}.
   * <ul>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPutWithStringObject_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put("Key", (Object) Double.NaN));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put("Key", Float.NaN));
  }

  /**
   * Test {@link JSONObject#put(String, Object)} with {@code String},
   * {@code Object}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPutWithStringObject_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").put(null, JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#put(String, Object)} with {@code String},
   * {@code Object}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then toJSONObject {@code =;} length is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPutWithStringObject_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsTwo() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Object) null);

    // Assert
    assertEquals(2, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Object)} with {@code String},
   * {@code Object}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPutWithStringObject_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutResult = toJSONObjectResult.put("Key", (Object) 10.0d);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutResult);
  }

  /**
   * Test {@link JSONObject#put(String, Object)} with {@code String},
   * {@code Object}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#put(String, Object)}
   */
  @Test
  public void testPutWithStringObject_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree2() throws JSONException {
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
   * <ul>
   *   <li>Given toJSONObject {@code =;} append {@code Key} and
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce_givenToJSONObjectEqualsSignSemicolonAppendKeyAndNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append("Key", JSONObject.NULL);

    // Act and Assert
    assertThrows(JSONException.class, () -> toJSONObjectResult.putOnce("Key", JSONObject.NULL));
  }

  /**
   * Test {@link JSONObject#putOnce(String, Object)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce_givenToJSONObjectEqualsSignSemicolon_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").putOnce("Key", Double.NaN));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").putOnce("Key", Float.NaN));
  }

  /**
   * Test {@link JSONObject#putOnce(String, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
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
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then toJSONObject {@code =;} length is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsTwo() throws JSONException {
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
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then toJSONObject {@code =;} length is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsTwo2() throws JSONException {
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
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOnceResult = toJSONObjectResult.putOnce("Key", 10.0d);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOnceResult);
  }

  /**
   * Test {@link JSONObject#putOnce(String, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOnce(String, Object)}
   */
  @Test
  public void testPutOnce_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree2() throws JSONException {
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
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt_givenToJSONObjectEqualsSignSemicolon_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").putOpt("Key", Double.NaN));
    assertThrows(JSONException.class, () -> Cookie.toJSONObject("=;").putOpt("Key", Float.NaN));
  }

  /**
   * Test {@link JSONObject#putOpt(String, Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
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
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then toJSONObject {@code =;} length is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsTwo() throws JSONException {
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
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then toJSONObject {@code =;} length is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt_whenNull_thenToJSONObjectEqualsSignSemicolonLengthIsTwo2() throws JSONException {
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
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act
    JSONObject actualPutOptResult = toJSONObjectResult.putOpt("Key", 10.0d);

    // Assert
    assertEquals(3, toJSONObjectResult.length());
    assertSame(toJSONObjectResult, actualPutOptResult);
  }

  /**
   * Test {@link JSONObject#putOpt(String, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then toJSONObject {@code =;} length is three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#putOpt(String, Object)}
   */
  @Test
  public void testPutOpt_whenTen_thenToJSONObjectEqualsSignSemicolonLengthIsThree2() throws JSONException {
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
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code ""}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#quote(String)}
   */
  @Test
  public void testQuote_whenEmptyString_thenReturnQuotationMarkQuotationMark() {
    // Arrange, Act and Assert
    assertEquals("\"\"", JSONObject.quote(""));
  }

  /**
   * Test {@link JSONObject#quote(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code ""}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#quote(String)}
   */
  @Test
  public void testQuote_whenNull_thenReturnQuotationMarkQuotationMark() {
    // Arrange, Act and Assert
    assertEquals("\"\"", JSONObject.quote(null));
  }

  /**
   * Test {@link JSONObject#quote(String)}.
   * <ul>
   *   <li>When {@code String}.</li>
   *   <li>Then return {@code "String"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#quote(String)}
   */
  @Test
  public void testQuote_whenString_thenReturnString() {
    // Arrange, Act and Assert
    assertEquals("\"String\"", JSONObject.quote("String"));
  }

  /**
   * Test {@link JSONObject#remove(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#remove(String)}
   */
  @Test
  public void testRemove_givenToJSONObjectEqualsSignSemicolonKeyIsFalse() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", false);

    // Act
    toJSONObjectResult.remove("Key");

    // Assert
    assertEquals(2, toJSONObjectResult.length());
  }

  /**
   * Test {@link JSONObject#remove(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;} {@code Key} is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#remove(String)}
   */
  @Test
  public void testRemove_givenToJSONObjectEqualsSignSemicolonKeyIsTrue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put("Key", true);

    // Act
    toJSONObjectResult.remove("Key");

    // Assert
    assertEquals(2, toJSONObjectResult.length());
  }

  /**
   * Test {@link JSONObject#remove(String)}.
   * <ul>
   *   <li>Given toJSONObject {@code =;}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#remove(String)}
   */
  @Test
  public void testRemove_givenToJSONObjectEqualsSignSemicolon_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");

    // Act and Assert
    assertNull(toJSONObjectResult.remove("Key"));
    assertEquals(2, toJSONObjectResult.length());
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
  public void testSortedKeys_givenToJSONObjectEqualsSignSemicolon_thenReturnNextIsName() throws JSONException {
    // Arrange and Act
    Iterator actualSortedKeysResult = Cookie.toJSONObject("=;").sortedKeys();

    // Assert
    assertEquals("name", actualSortedKeysResult.next());
    assertEquals("value", actualSortedKeysResult.next());
    assertFalse(actualSortedKeysResult.hasNext());
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   * <ul>
   *   <li>When {@code 0foo}.</li>
   *   <li>Then return {@code 0foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  public void testStringToValue_when0foo_thenReturn0foo() {
    // Arrange, Act and Assert
    assertEquals("0foo", JSONObject.stringToValue("0foo"));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   * <ul>
   *   <li>When {@code 42.}.</li>
   *   <li>Then return doubleValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  public void testStringToValue_when42_thenReturnDoubleValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42.0d, ((Double) JSONObject.stringToValue("42.")).doubleValue(), 0.0);
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   * <ul>
   *   <li>When {@code 42foo}.</li>
   *   <li>Then return {@code 42foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  public void testStringToValue_when42foo_thenReturn42foo() {
    // Arrange, Act and Assert
    assertEquals("42foo", JSONObject.stringToValue("42foo"));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   * <ul>
   *   <li>When {@code 42true}.</li>
   *   <li>Then return {@code 42true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  public void testStringToValue_when42true_thenReturn42true() {
    // Arrange, Act and Assert
    assertEquals("42true", JSONObject.stringToValue("42true"));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   * <ul>
   *   <li>When {@code .}.</li>
   *   <li>Then return {@code .}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  public void testStringToValue_whenDot_thenReturnDot() {
    // Arrange, Act and Assert
    assertEquals(".", JSONObject.stringToValue("."));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  public void testStringToValue_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", JSONObject.stringToValue(""));
  }

  /**
   * Test {@link JSONObject#stringToValue(String)}.
   * <ul>
   *   <li>When {@code foo}.</li>
   *   <li>Then return {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#stringToValue(String)}
   */
  @Test
  public void testStringToValue_whenFoo_thenReturnFoo() {
    // Arrange, Act and Assert
    assertEquals("foo", JSONObject.stringToValue("foo"));
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
  public void testTestValidity_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.testValidity(Double.NaN));
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
  public void testToStringWithIndentFactor() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(
        "{\n   \"HTTP-Version\": \"https://example.org/example\",\n   \"Reason-Phrase\": \"\",\n   \"Status-Code\": \"\"\n}",
        HTTP.toJSONObject("https://example.org/example").toString(3));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{\n" + "    \"HTTP-Version\": \"https://example.org/example\",\n" + "    \"Reason-Phrase\": \"\",\n"
        + "    \"Status-Code\": \"\"\n" + " }", HTTP.toJSONObject("https://example.org/example").toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": 1, "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturn1NameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.increment(": ");

    // Act and Assert
    assertEquals("{\n    \": \": 1,\n    \"name\": \"\",\n    \"value\": \"\"\n }", toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": 1, "name": "", "value": "", "{": 10 }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
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
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": 0.5, "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturn05NameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", 0.5d);

    // Act and Assert
    assertEquals("{\n    \": \": 0.5,\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": false, "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturnFalseNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", false);

    // Act and Assert
    assertEquals("{\n    \": \": false,\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", (new JSONObject()).toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code { ",\n": [null], "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturnNNullNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append(",\n", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n    \",\\n\": [null],\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code { "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturnNameValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{\n    \"name\": \"\",\n    \"value\": \"\"\n }", Cookie.toJSONObject("=;").toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": [], "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturnNameValue2() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("{\n    \": \": [],\n    \"name\": \"\",\n    \"value\": \"\"\n }", toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": {}, "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturnNameValue3() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.put(": ", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{\n    \": \": {},\n    \"name\": \"\",\n    \"value\": \"\"\n }", toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code {": ": [null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append(": ", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\": \": [null]}", jsonObject.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code { ": ": [null], "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturnNullNameValue() throws JSONException {
    // Arrange
    JSONObject toJSONObjectResult = Cookie.toJSONObject("=;");
    toJSONObjectResult.append(": ", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\n    \": \": [null],\n    \"name\": \"\",\n    \"value\": \"\"\n }",
        toJSONObjectResult.toString(3, 1));
  }

  /**
   * Test {@link JSONObject#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code { "\"\"": [null], "name": "", "value": "" }}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int, int)}
   */
  @Test
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
   *   <li>Given {@link JSONObject#JSONObject()} append {@code :} and
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code {": ": [null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString(int)}
   */
  @Test
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
   *   <li>Then return
   * {@code {"HTTP-Version":"https://example.org/example","Status-Code":"","Reason-Phrase":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#toString()}
   */
  @Test
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
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"\"", JSONObject.valueToString("", 3, 1));
    assertEquals(
        "{\n" + "    \"HTTP-Version\": \"https://example.org/example\",\n" + "    \"Reason-Phrase\": \"\",\n"
            + "    \"Status-Code\": \"\"\n" + " }",
        JSONObject.valueToString(HTTP.toJSONObject("https://example.org/example"), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>Then return {@code {"{}": [null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{}", JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"{}\": [null]}", JSONObject.valueToString(jsonObject, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@code 0.5}.</li>
   *   <li>Then return {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent_when05_thenReturn05() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("0.5", JSONObject.valueToString(0.5d, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code "42"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent_when42_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"42\"", JSONObject.valueToString("42", 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent_whenArrayList() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", JSONObject.valueToString(new ArrayList<>(), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent_whenFortyTwo_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", JSONObject.valueToString(42, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent_whenHashMap() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", JSONObject.valueToString(new HashMap<>(), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent_whenJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", JSONObject.valueToString(new JSONArray(), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent_whenJSONObject() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("{}", JSONObject.valueToString(new JSONObject(), 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link Double#NaN}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Double.NaN, 3, 1));
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Float.NaN, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.valueToString(JSONObject.NULL, 3, 1));
    assertEquals("null", JSONObject.valueToString(null, 3, 0));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return {@code 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent_whenTen_thenReturn10() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.valueToString(10.0d, 3, 1));
    assertEquals("10", JSONObject.valueToString(10.0f, 3, 1));
  }

  /**
   * Test {@link JSONObject#valueToString(Object, int, int)} with {@code value},
   * {@code indentFactor}, {@code indent}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then return {@link Boolean#TRUE} toString.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object, int, int)}
   */
  @Test
  public void testValueToStringWithValueIndentFactorIndent_whenTrue_thenReturnTrueToString() throws JSONException {
    // Arrange and Act
    String actualValueToStringResult = JSONObject.valueToString(true, 3, 1);

    // Assert
    assertEquals(Boolean.TRUE.toString(), actualValueToStringResult);
  }

  /**
   * Test {@link JSONObject#valueToString(Object)} with {@code value}.
   * <ul>
   *   <li>Given {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
  public void testValueToStringWithValue_givenBiFunction() throws JSONException {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    objectObjectMap.put(JSONObject.NULL, JSONObject.NULL);

    // Act and Assert
    assertEquals("{\"null\":null}", JSONObject.valueToString(objectObjectMap));
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
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code {"null":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#valueToString(Object)}
   */
  @Test
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
  public void testValueToStringWithValue_whenNaN_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> JSONObject.valueToString(Double.NaN));
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
  public void testValueToStringWithValue_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("null", JSONObject.valueToString(JSONObject.NULL));
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
  public void testValueToStringWithValue_whenTen_thenReturn10() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("10", JSONObject.valueToString(10.0d));
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
  public void testValueToStringWithValue_whenTrue_thenReturnTrueToString() throws JSONException {
    // Arrange and Act
    String actualValueToStringResult = JSONObject.valueToString(true);

    // Assert
    assertEquals(Boolean.TRUE.toString(), actualValueToStringResult);
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
   *   <li>When ten.</li>
   *   <li>Then return doubleValue is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#wrap(Object)}
   */
  @Test
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
  public void testWrap_whenTen_thenReturnFloatValueIsTen() {
    // Arrange, Act and Assert
    assertEquals(10.0f, ((Float) JSONObject.wrap(10.0f)).floatValue(), 0.0f);
  }

  /**
   * Test {@link JSONObject#write(Writer)}.
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
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
   *   <li>Then {@link StringWriter#StringWriter()} toString is
   * {@code {"\"\"":10,"name":"","value":"","Key":1}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
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
   *   <li>Then {@link StringWriter#StringWriter()} toString is
   * {@code {"name":"","value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
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
   *   <li>Then {@link StringWriter#StringWriter()} toString is
   * {@code {"name":"","value":"","Key":{}}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
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
   *   <li>Then {@link StringWriter#StringWriter()} toString is
   * {@code {"name":"","value":"","Key":1}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
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
   *   <li>Then {@link StringWriter#StringWriter()} toString is
   * {@code {"name":"","value":"","Key":0.5}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
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
   *   <li>Then {@link StringWriter#StringWriter()} toString is
   * {@code {"name":"","value":"","Key":false}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
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
   *   <li>Then {@link StringWriter#StringWriter()} toString is
   * {@code {"name":"","value":"","Key":[null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
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
   *   <li>Then {@link StringWriter#StringWriter()} toString is
   * {@code {"name":"","value":"","Key":[null,null]}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
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
   *   <li>Then {@link StringWriter#StringWriter()} toString is
   * {@code {"\"\"":[null],"name":"","value":""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONObject#write(Writer)}
   */
  @Test
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
