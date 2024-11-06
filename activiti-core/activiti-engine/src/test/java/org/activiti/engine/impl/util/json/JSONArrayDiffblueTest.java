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
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.Test;

public class JSONArrayDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link JSONArray#JSONArray()}
   *   <li>{@link JSONArray#toString()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("[]", (new JSONArray()).toString());
  }

  /**
   * Test {@link JSONArray#JSONArray(Object)}.
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Object)}
   */
  @Test
  public void testNewJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray(JSONObject.NULL));
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code A}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenA_whenArrayListAddA_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add((byte) 'A');

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add
   * {@link JSONArray#JSONArray()}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenJSONArray_whenArrayListAddJSONArray_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(new JSONArray());

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add
   * {@link JSONObject#JSONObject()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenJSONObject_whenArrayListAddJSONObject() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(new JSONObject());

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenNull_whenArrayListAddNull_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenNull_whenArrayListAddNull_thenReturnLengthIsOne2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(null);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>Then return length is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenNull_whenArrayListAddNull_thenReturnLengthIsTwo() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertEquals(2, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link ArrayList#ArrayList()} add one.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenOne_whenArrayListAddOne_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add((short) 1);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link ArrayList#ArrayList()} add one.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenOne_whenArrayListAddOne_thenReturnLengthIsOne2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(1L);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given start of heading.</li>
   *   <li>When {@link ArrayList#ArrayList()} add start of heading.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenStartOfHeading_whenArrayListAddStartOfHeading() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add('\u0001');

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link ArrayList#ArrayList()} add ten.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenTen_whenArrayListAddTen_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(10.0f);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link ArrayList#ArrayList()} add ten.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenTen_whenArrayListAddTen_thenReturnLengthIsOne2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(10.0d);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code true}.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenTrue_whenArrayListAddTrue_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(true);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>Given two.</li>
   *   <li>When {@link ArrayList#ArrayList()} add two.</li>
   *   <li>Then return length is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_givenTwo_whenArrayListAddTwo_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(2);

    // Act and Assert
    assertEquals(1, (new JSONArray((Collection) collection)).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  public void testNewJSONArray_whenArrayList_thenReturnLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONArray((Collection) new ArrayList<>())).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(String)}.
   * <ul>
   *   <li>When {@code []}.</li>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#JSONArray(String)}
   */
  @Test
  public void testNewJSONArray_whenLeftSquareBracketRightSquareBracket_thenReturnLengthIsZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONArray("[]")).length());
  }

  /**
   * Test {@link JSONArray#get(int)}.
   * <ul>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#get(int)}
   */
  @Test
  public void testGet_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).get(1));
  }

  /**
   * Test {@link JSONArray#get(int)}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#get(int)}
   */
  @Test
  public void testGet_whenMinusOne_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).get(-1));
  }

  /**
   * Test {@link JSONArray#getBoolean(int)}.
   * <p>
   * Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  public void testGetBoolean() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(40, false);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getBoolean(1));
  }

  /**
   * Test {@link JSONArray#getBoolean(int)}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  public void testGetBoolean_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getBoolean(1));
  }

  /**
   * Test {@link JSONArray#getBoolean(int)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  public void testGetBoolean_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse(jsonArray.getBoolean(1));
  }

  /**
   * Test {@link JSONArray#getBoolean(int)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  public void testGetBoolean_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertTrue(jsonArray.getBoolean(1));
  }

  /**
   * Test {@link JSONArray#getBoolean(int)}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  public void testGetBoolean_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getBoolean(-1));
  }

  /**
   * Test {@link JSONArray#getDouble(int)}.
   * <p>
   * Method under test: {@link JSONArray#getDouble(int)}
   */
  @Test
  public void testGetDouble() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getDouble(1));
  }

  /**
   * Test {@link JSONArray#getDouble(int)}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getDouble(int)}
   */
  @Test
  public void testGetDouble_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getDouble(1));
  }

  /**
   * Test {@link JSONArray#getDouble(int)}.
   * <ul>
   *   <li>Then return {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getDouble(int)}
   */
  @Test
  public void testGetDouble_thenReturn05() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0.5d, jsonArray.getDouble(1), 0.0);
  }

  /**
   * Test {@link JSONArray#getDouble(int)}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getDouble(int)}
   */
  @Test
  public void testGetDouble_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getDouble(-1));
  }

  /**
   * Test {@link JSONArray#getInt(int)}.
   * <p>
   * Method under test: {@link JSONArray#getInt(int)}
   */
  @Test
  public void testGetInt() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getInt(1));
  }

  /**
   * Test {@link JSONArray#getInt(int)}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getInt(int)}
   */
  @Test
  public void testGetInt_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getInt(1));
  }

  /**
   * Test {@link JSONArray#getInt(int)}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getInt(int)}
   */
  @Test
  public void testGetInt_thenReturnZero() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0, jsonArray.getInt(1));
  }

  /**
   * Test {@link JSONArray#getInt(int)}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getInt(int)}
   */
  @Test
  public void testGetInt_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getInt(-1));
  }

  /**
   * Test {@link JSONArray#getJSONArray(int)}.
   * <p>
   * Method under test: {@link JSONArray#getJSONArray(int)}
   */
  @Test
  public void testGetJSONArray() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getJSONArray(1));
  }

  /**
   * Test {@link JSONArray#getJSONArray(int)}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getJSONArray(int)}
   */
  @Test
  public void testGetJSONArray_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getJSONArray(1));
  }

  /**
   * Test {@link JSONArray#getJSONArray(int)}.
   * <ul>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getJSONArray(int)}
   */
  @Test
  public void testGetJSONArray_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals(0, jsonArray.getJSONArray(1).length());
  }

  /**
   * Test {@link JSONArray#getJSONArray(int)}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getJSONArray(int)}
   */
  @Test
  public void testGetJSONArray_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getJSONArray(-1));
  }

  /**
   * Test {@link JSONArray#getJSONObject(int)}.
   * <p>
   * Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  public void testGetJSONObject() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getJSONObject(1));
  }

  /**
   * Test {@link JSONArray#getJSONObject(int)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  public void testGetJSONObject_givenHashMapComputeIfPresentNullAndBiFunction() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals(0, jsonArray.getJSONObject(1).length());
  }

  /**
   * Test {@link JSONArray#getJSONObject(int)}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  public void testGetJSONObject_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getJSONObject(1));
  }

  /**
   * Test {@link JSONArray#getJSONObject(int)}.
   * <ul>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  public void testGetJSONObject_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals(0, jsonArray.getJSONObject(1).length());
  }

  /**
   * Test {@link JSONArray#getJSONObject(int)}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  public void testGetJSONObject_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getJSONObject(-1));
  }

  /**
   * Test {@link JSONArray#getLong(int)}.
   * <p>
   * Method under test: {@link JSONArray#getLong(int)}
   */
  @Test
  public void testGetLong() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getLong(1));
  }

  /**
   * Test {@link JSONArray#getLong(int)}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getLong(int)}
   */
  @Test
  public void testGetLong_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getLong(1));
  }

  /**
   * Test {@link JSONArray#getLong(int)}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getLong(int)}
   */
  @Test
  public void testGetLong_thenReturnZero() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0L, jsonArray.getLong(1));
  }

  /**
   * Test {@link JSONArray#getLong(int)}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getLong(int)}
   */
  @Test
  public void testGetLong_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getLong(-1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   * <p>
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(",");

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[\",\"]", jsonArray.getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   * <p>
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString2() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add("");

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[\"\"]", jsonArray.getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code [null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString_givenArrayListAddNull_whenOne_thenReturnNull() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[null]", jsonArray.getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code [null,null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString_givenArrayListAddNull_whenOne_thenReturnNullNull() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);
    value.add(JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[null,null]", jsonArray.getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add two.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code [2]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString_givenArrayListAddTwo_whenOne_thenReturn2() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(2);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[2]", jsonArray.getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   *   <li>Then return {@code {"null":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString_givenHashMapComputeIfPresentNullAndBiFunction_thenReturnNullNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", jsonArray.getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code {"null":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString_givenHashMapNullIsNull_whenOne_thenReturnNullNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", jsonArray.getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   * <ul>
   *   <li>Then return {@link Boolean#FALSE} toString.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString_thenReturnFalseToString() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    String actualString = jsonArray.getString(1);

    // Assert
    assertEquals(Boolean.FALSE.toString(), actualString);
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   * <ul>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", jsonArray.getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   * <ul>
   *   <li>Then return {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString_thenReturnLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[]", jsonArray.getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   * <ul>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  public void testGetString_whenMinusOne_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).getString(-1));
  }

  /**
   * Test {@link JSONArray#isNull(int)}.
   * <p>
   * Method under test: {@link JSONArray#isNull(int)}
   */
  @Test
  public void testIsNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(6, false);

    // Act and Assert
    assertTrue(jsonArray.isNull(1));
  }

  /**
   * Test {@link JSONArray#isNull(int)}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#isNull(int)}
   */
  @Test
  public void testIsNull_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertTrue((new JSONArray("[]")).isNull(1));
  }

  /**
   * Test {@link JSONArray#isNull(int)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#isNull(int)}
   */
  @Test
  public void testIsNull_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse(jsonArray.isNull(1));
  }

  /**
   * Test {@link JSONArray#isNull(int)}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#isNull(int)}
   */
  @Test
  public void testIsNull_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertTrue((new JSONArray("[]")).isNull(-1));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   * <ul>
   *   <li>Then return {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#join(String)}
   */
  @Test
  public void testJoin_thenReturn1() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1);

    // Act and Assert
    assertEquals("1", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   * <ul>
   *   <li>Then return {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#join(String)}
   */
  @Test
  public void testJoin_thenReturn05() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(0.5d);

    // Act and Assert
    assertEquals("0.5", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   * <ul>
   *   <li>Then return {@code 10Separator[]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#join(String)}
   */
  @Test
  public void testJoin_thenReturn10Separator() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(10.0d);
    jsonArray.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("10Separator[]", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   * <ul>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#join(String)}
   */
  @Test
  public void testJoin_thenReturnEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", (new JSONArray("[]")).join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   * <ul>
   *   <li>Then return {@link Boolean#FALSE} toString.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#join(String)}
   */
  @Test
  public void testJoin_thenReturnFalseToString() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(false);

    // Act
    String actualJoinResult = jsonArray.join("Separator");

    // Assert
    assertEquals(Boolean.FALSE.toString(), actualJoinResult);
  }

  /**
   * Test {@link JSONArray#join(String)}.
   * <ul>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#join(String)}
   */
  @Test
  public void testJoin_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   * <ul>
   *   <li>Then return {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#join(String)}
   */
  @Test
  public void testJoin_thenReturnLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[]", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#join(String)}
   */
  @Test
  public void testJoin_thenReturnNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(JSONObject.NULL);

    // Act and Assert
    assertEquals("null", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   * <ul>
   *   <li>Then return {@code []Separatorfalse}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#join(String)}
   */
  @Test
  public void testJoin_thenReturnSeparatorfalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());
    jsonArray.put(false);

    // Act and Assert
    assertEquals("[]Separatorfalse", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#length()}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#length()}
   */
  @Test
  public void testLength_thenReturnZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONArray("[]")).length());
  }

  /**
   * Test {@link JSONArray#opt(int)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#opt(int)}
   */
  @Test
  public void testOpt_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull((new JSONArray("[]")).opt(1));
    assertNull((new JSONArray("[]")).opt(-1));
  }

  /**
   * Test {@link JSONArray#optBoolean(int)} with {@code index}.
   * <p>
   * Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  public void testOptBooleanWithIndex() throws JSONException {
    // Arrange, Act and Assert
    assertFalse((new JSONArray("[]")).optBoolean(1));
  }

  /**
   * Test {@link JSONArray#optBoolean(int)} with {@code index}.
   * <p>
   * Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  public void testOptBooleanWithIndex2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(9, false);

    // Act and Assert
    assertFalse(jsonArray.optBoolean(1));
  }

  /**
   * Test {@link JSONArray#optBoolean(int)} with {@code index}.
   * <p>
   * Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  public void testOptBooleanWithIndex3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse(jsonArray.optBoolean(1));
  }

  /**
   * Test {@link JSONArray#optBoolean(int, boolean)} with {@code index},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  public void testOptBooleanWithIndexDefaultValue() throws JSONException {
    // Arrange, Act and Assert
    assertTrue((new JSONArray("[]")).optBoolean(1, true));
  }

  /**
   * Test {@link JSONArray#optBoolean(int, boolean)} with {@code index},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  public void testOptBooleanWithIndexDefaultValue2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(9, false);

    // Act and Assert
    assertTrue(jsonArray.optBoolean(1, true));
  }

  /**
   * Test {@link JSONArray#optBoolean(int, boolean)} with {@code index},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  public void testOptBooleanWithIndexDefaultValue3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);
    jsonArray.put(9, false);

    // Act and Assert
    assertTrue(jsonArray.optBoolean(1, true));
  }

  /**
   * Test {@link JSONArray#optBoolean(int, boolean)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  public void testOptBooleanWithIndexDefaultValue_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse(jsonArray.optBoolean(1, true));
  }

  /**
   * Test {@link JSONArray#optBoolean(int, boolean)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  public void testOptBooleanWithIndexDefaultValue_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertTrue((new JSONArray("[]")).optBoolean(-1, true));
  }

  /**
   * Test {@link JSONArray#optBoolean(int)} with {@code index}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  public void testOptBooleanWithIndex_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);
    jsonArray.put(9, false);

    // Act and Assert
    assertTrue(jsonArray.optBoolean(1));
  }

  /**
   * Test {@link JSONArray#optBoolean(int)} with {@code index}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  public void testOptBooleanWithIndex_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertFalse((new JSONArray("[]")).optBoolean(-1));
  }

  /**
   * Test {@link JSONArray#optDouble(int)} with {@code index}.
   * <p>
   * Method under test: {@link JSONArray#optDouble(int)}
   */
  @Test
  public void testOptDoubleWithIndex() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(Double.NaN, (new JSONArray("[]")).optDouble(1), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int)} with {@code index}.
   * <p>
   * Method under test: {@link JSONArray#optDouble(int)}
   */
  @Test
  public void testOptDoubleWithIndex2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertEquals(Double.NaN, jsonArray.optDouble(1), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int, double)} with {@code index},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  public void testOptDoubleWithIndexDefaultValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(10.0d, (new JSONArray("[]")).optDouble(1, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int, double)} with {@code index},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  public void testOptDoubleWithIndexDefaultValue2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(3, false);

    // Act and Assert
    assertEquals(10.0d, jsonArray.optDouble(1, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int, double)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  public void testOptDoubleWithIndexDefaultValue_thenReturn05() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0.5d, jsonArray.optDouble(1, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int, double)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  public void testOptDoubleWithIndexDefaultValue_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(10.0d, (new JSONArray("[]")).optDouble(-1, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int, double)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>When three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  public void testOptDoubleWithIndexDefaultValue_whenThree() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(10.0d, (new JSONArray("[]")).optDouble(3, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int, double)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>When zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  public void testOptDoubleWithIndexDefaultValue_whenZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(10.0d, (new JSONArray("[]")).optDouble(0, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int)} with {@code index}.
   * <ul>
   *   <li>Then return ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optDouble(int)}
   */
  @Test
  public void testOptDoubleWithIndex_thenReturnTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 10.0d);

    // Act and Assert
    assertEquals(10.0d, jsonArray.optDouble(1), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int)} with {@code index}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optDouble(int)}
   */
  @Test
  public void testOptDoubleWithIndex_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(Double.NaN, (new JSONArray("[]")).optDouble(-1), 0.0);
  }

  /**
   * Test {@link JSONArray#optInt(int)} with {@code index}.
   * <p>
   * Method under test: {@link JSONArray#optInt(int)}
   */
  @Test
  public void testOptIntWithIndex() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONArray("[]")).optInt(1));
  }

  /**
   * Test {@link JSONArray#optInt(int)} with {@code index}.
   * <p>
   * Method under test: {@link JSONArray#optInt(int)}
   */
  @Test
  public void testOptIntWithIndex2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertEquals(0, jsonArray.optInt(1));
  }

  /**
   * Test {@link JSONArray#optInt(int)} with {@code index}.
   * <p>
   * Method under test: {@link JSONArray#optInt(int)}
   */
  @Test
  public void testOptIntWithIndex3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0, jsonArray.optInt(1));
  }

  /**
   * Test {@link JSONArray#optInt(int, int)} with {@code index},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  public void testOptIntWithIndexDefaultValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, (new JSONArray("[]")).optInt(1, 42));
  }

  /**
   * Test {@link JSONArray#optInt(int, int)} with {@code index},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  public void testOptIntWithIndexDefaultValue2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(3, false);

    // Act and Assert
    assertEquals(42, jsonArray.optInt(1, 42));
  }

  /**
   * Test {@link JSONArray#optInt(int, int)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  public void testOptIntWithIndexDefaultValue_thenReturnZero() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0, jsonArray.optInt(1, 42));
  }

  /**
   * Test {@link JSONArray#optInt(int, int)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  public void testOptIntWithIndexDefaultValue_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, (new JSONArray("[]")).optInt(-1, 42));
  }

  /**
   * Test {@link JSONArray#optInt(int, int)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>When three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  public void testOptIntWithIndexDefaultValue_whenThree() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, (new JSONArray("[]")).optInt(3, 42));
  }

  /**
   * Test {@link JSONArray#optInt(int, int)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>When zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  public void testOptIntWithIndexDefaultValue_whenZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, (new JSONArray("[]")).optInt(0, 42));
  }

  /**
   * Test {@link JSONArray#optInt(int)} with {@code index}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optInt(int)}
   */
  @Test
  public void testOptIntWithIndex_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, (new JSONArray("[]")).optInt(-1));
  }

  /**
   * Test {@link JSONArray#optJSONArray(int)}.
   * <p>
   * Method under test: {@link JSONArray#optJSONArray(int)}
   */
  @Test
  public void testOptJSONArray() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertNull(jsonArray.optJSONArray(1));
  }

  /**
   * Test {@link JSONArray#optJSONArray(int)}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optJSONArray(int)}
   */
  @Test
  public void testOptJSONArray_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertNull((new JSONArray("[]")).optJSONArray(1));
  }

  /**
   * Test {@link JSONArray#optJSONArray(int)}.
   * <ul>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optJSONArray(int)}
   */
  @Test
  public void testOptJSONArray_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals(0, jsonArray.optJSONArray(1).length());
  }

  /**
   * Test {@link JSONArray#optJSONArray(int)}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optJSONArray(int)}
   */
  @Test
  public void testOptJSONArray_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertNull((new JSONArray("[]")).optJSONArray(-1));
  }

  /**
   * Test {@link JSONArray#optJSONObject(int)}.
   * <p>
   * Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  public void testOptJSONObject() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertNull(jsonArray.optJSONObject(1));
  }

  /**
   * Test {@link JSONArray#optJSONObject(int)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  public void testOptJSONObject_givenHashMapComputeIfPresentNullAndBiFunction() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals(0, jsonArray.optJSONObject(1).length());
  }

  /**
   * Test {@link JSONArray#optJSONObject(int)}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  public void testOptJSONObject_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertNull((new JSONArray("[]")).optJSONObject(1));
  }

  /**
   * Test {@link JSONArray#optJSONObject(int)}.
   * <ul>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  public void testOptJSONObject_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals(0, jsonArray.optJSONObject(1).length());
  }

  /**
   * Test {@link JSONArray#optJSONObject(int)}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  public void testOptJSONObject_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertNull((new JSONArray("[]")).optJSONObject(-1));
  }

  /**
   * Test {@link JSONArray#optLong(int)} with {@code index}.
   * <p>
   * Method under test: {@link JSONArray#optLong(int)}
   */
  @Test
  public void testOptLongWithIndex() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0L, (new JSONArray("[]")).optLong(1));
  }

  /**
   * Test {@link JSONArray#optLong(int)} with {@code index}.
   * <p>
   * Method under test: {@link JSONArray#optLong(int)}
   */
  @Test
  public void testOptLongWithIndex2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertEquals(0L, jsonArray.optLong(1));
  }

  /**
   * Test {@link JSONArray#optLong(int)} with {@code index}.
   * <p>
   * Method under test: {@link JSONArray#optLong(int)}
   */
  @Test
  public void testOptLongWithIndex3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0L, jsonArray.optLong(1));
  }

  /**
   * Test {@link JSONArray#optLong(int, long)} with {@code index},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  public void testOptLongWithIndexDefaultValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, (new JSONArray("[]")).optLong(1, 42L));
  }

  /**
   * Test {@link JSONArray#optLong(int, long)} with {@code index},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  public void testOptLongWithIndexDefaultValue2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(3, false);

    // Act and Assert
    assertEquals(42L, jsonArray.optLong(1, 42L));
  }

  /**
   * Test {@link JSONArray#optLong(int, long)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  public void testOptLongWithIndexDefaultValue_thenReturnZero() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 0.5d);

    // Act and Assert
    assertEquals(0L, jsonArray.optLong(1, 42L));
  }

  /**
   * Test {@link JSONArray#optLong(int, long)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  public void testOptLongWithIndexDefaultValue_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, (new JSONArray("[]")).optLong(-1, 42L));
  }

  /**
   * Test {@link JSONArray#optLong(int, long)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>When three.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  public void testOptLongWithIndexDefaultValue_whenThree() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, (new JSONArray("[]")).optLong(3, 42L));
  }

  /**
   * Test {@link JSONArray#optLong(int, long)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>When zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  public void testOptLongWithIndexDefaultValue_whenZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, (new JSONArray("[]")).optLong(0, 42L));
  }

  /**
   * Test {@link JSONArray#optLong(int)} with {@code index}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optLong(int)}
   */
  @Test
  public void testOptLongWithIndex_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0L, (new JSONArray("[]")).optLong(-1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   * <p>
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptStringWithIndex() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add("");

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[\"\"]", jsonArray.optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  public void testOptStringWithIndexDefaultValue() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(",");

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[\",\"]", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index},
   * {@code defaultValue}.
   * <p>
   * Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  public void testOptStringWithIndexDefaultValue2() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add("");

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[\"\"]", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code [null,null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  public void testOptStringWithIndexDefaultValue_givenArrayListAddNull_thenReturnNullNull() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);
    value.add(JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[null,null]", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code [null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  public void testOptStringWithIndexDefaultValue_givenArrayListAddNull_whenOne_thenReturnNull() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[null]", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add two.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code [2]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  public void testOptStringWithIndexDefaultValue_givenArrayListAddTwo_whenOne_thenReturn2() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(2);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[2]", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  public void testOptStringWithIndexDefaultValue_givenHashMapComputeIfPresentNullAndBiFunction() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code {"null":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  public void testOptStringWithIndexDefaultValue_givenHashMapNullIsNull_thenReturnNullNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  public void testOptStringWithIndexDefaultValue_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", (new JSONArray("[]")).optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@link Boolean#FALSE} toString.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  public void testOptStringWithIndexDefaultValue_thenReturnFalseToString() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    String actualOptStringResult = jsonArray.optString(1, "42");

    // Assert
    assertEquals(Boolean.FALSE.toString(), actualOptStringResult);
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  public void testOptStringWithIndexDefaultValue_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  public void testOptStringWithIndexDefaultValue_thenReturnLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[]", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index},
   * {@code defaultValue}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  public void testOptStringWithIndexDefaultValue_whenMinusOne_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", (new JSONArray("[]")).optString(-1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code ["42"]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptStringWithIndex_givenArrayListAdd42_whenOne_thenReturn42() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add("42");

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[\"42\"]", jsonArray.optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code [null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptStringWithIndex_givenArrayListAddNull_whenOne_thenReturnNull() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[null]", jsonArray.optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code [null,null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptStringWithIndex_givenArrayListAddNull_whenOne_thenReturnNullNull() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);
    value.add(JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[null,null]", jsonArray.optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add two.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code [2]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptStringWithIndex_givenArrayListAddTwo_whenOne_thenReturn2() throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(2);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[2]", jsonArray.optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptStringWithIndex_givenHashMapComputeIfPresentNullAndBiFunction() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", jsonArray.optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code {"null":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptStringWithIndex_givenHashMapNullIsNull_whenOne_thenReturnNullNull() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", jsonArray.optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   * <ul>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptStringWithIndex_thenReturnEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", (new JSONArray("[]")).optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   * <ul>
   *   <li>Then return {@link Boolean#FALSE} toString.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptStringWithIndex_thenReturnFalseToString() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    String actualOptStringResult = jsonArray.optString(1);

    // Assert
    assertEquals(Boolean.FALSE.toString(), actualOptStringResult);
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   * <ul>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptStringWithIndex_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", jsonArray.optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   * <ul>
   *   <li>Then return {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptStringWithIndex_thenReturnLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[]", jsonArray.optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  public void testOptStringWithIndex_whenMinusOne_thenReturnEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", (new JSONArray("[]")).optString(-1));
  }

  /**
   * Test {@link JSONArray#put(boolean)} with {@code boolean}.
   * <p>
   * Method under test: {@link JSONArray#put(boolean)}
   */
  @Test
  public void testPutWithBoolean() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(true);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(boolean)} with {@code boolean}.
   * <p>
   * Method under test: {@link JSONArray#put(boolean)}
   */
  @Test
  public void testPutWithBoolean2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(false);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code A}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenA_whenArrayListAddA() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add((byte) 'A');

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add
   * {@link JSONArray#JSONArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenJSONArray_whenArrayListAddJSONArray() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(new JSONArray());

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add
   * {@link JSONObject#JSONObject()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenJSONObject_whenArrayListAddJSONObject() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(new JSONObject());

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenNull_whenArrayListAddNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenNull_whenArrayListAddNull2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);
    value.add(JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenNull_whenArrayListAddNull3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(null);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link ArrayList#ArrayList()} add one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenOne_whenArrayListAddOne() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add((short) 1);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link ArrayList#ArrayList()} add one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenOne_whenArrayListAddOne2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(1L);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given start of heading.</li>
   *   <li>When {@link ArrayList#ArrayList()} add start of heading.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenStartOfHeading_whenArrayListAddStartOfHeading() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add('\u0001');

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link ArrayList#ArrayList()} add ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenTen_whenArrayListAddTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(10.0f);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link ArrayList#ArrayList()} add ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenTen_whenArrayListAddTen2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(10.0d);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenTrue_whenArrayListAddTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(true);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>Given two.</li>
   *   <li>When {@link ArrayList#ArrayList()} add two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_givenTwo_whenArrayListAddTwo() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(2);

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Collection)} with {@code Collection}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  public void testPutWithCollection_whenArrayList() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put((Collection) new ArrayList<>());

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(double)} with {@code double}.
   * <p>
   * Method under test: {@link JSONArray#put(double)}
   */
  @Test
  public void testPutWithDouble() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(10.0d);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int)} with {@code int}.
   * <p>
   * Method under test: {@link JSONArray#put(int)}
   */
  @Test
  public void testPutWithInt() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(42);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, boolean)} with {@code int}, {@code boolean}.
   * <p>
   * Method under test: {@link JSONArray#put(int, boolean)}
   */
  @Test
  public void testPutWithIntBoolean() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, true);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, boolean)} with {@code int}, {@code boolean}.
   * <p>
   * Method under test: {@link JSONArray#put(int, boolean)}
   */
  @Test
  public void testPutWithIntBoolean2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, true);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, boolean)} with {@code int}, {@code boolean}.
   * <p>
   * Method under test: {@link JSONArray#put(int, boolean)}
   */
  @Test
  public void testPutWithIntBoolean3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, false);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, boolean)} with {@code int}, {@code boolean}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, boolean)}
   */
  @Test
  public void testPutWithIntBoolean_whenMinusOne_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).put(-1, true));
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) new ArrayList<>());

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code A}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenA_whenArrayListAddA() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add((byte) 'A');

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add
   * {@link JSONArray#JSONArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenJSONArray_whenArrayListAddJSONArray() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(new JSONArray());

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add
   * {@link JSONObject#JSONObject()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenJSONObject_whenArrayListAddJSONObject() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(new JSONObject());

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenNull_whenArrayListAddNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenNull_whenArrayListAddNull2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);
    value.add(JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenNull_whenArrayListAddNull3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(null);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link ArrayList#ArrayList()} add one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenOne_whenArrayListAddOne() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add((short) 1);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link ArrayList#ArrayList()} add one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenOne_whenArrayListAddOne2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(1L);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given start of heading.</li>
   *   <li>When {@link ArrayList#ArrayList()} add start of heading.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenStartOfHeading_whenArrayListAddStartOfHeading() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add('\u0001');

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link ArrayList#ArrayList()} add ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenTen_whenArrayListAddTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(10.0f);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link ArrayList#ArrayList()} add ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenTen_whenArrayListAddTen2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(10.0d);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenTrue_whenArrayListAddTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(true);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>Given two.</li>
   *   <li>When {@link ArrayList#ArrayList()} add two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_givenTwo_whenArrayListAddTwo() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    ArrayList<Object> value = new ArrayList<>();
    value.add(2);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_whenArrayList() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Collection) new ArrayList<>());

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int},
   * {@code Collection}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  public void testPutWithIntCollection_whenMinusOne_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.put(-1, (Collection) new ArrayList<>()));
  }

  /**
   * Test {@link JSONArray#put(int, double)} with {@code int}, {@code double}.
   * <p>
   * Method under test: {@link JSONArray#put(int, double)}
   */
  @Test
  public void testPutWithIntDouble() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 10.0d);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, double)} with {@code int}, {@code double}.
   * <p>
   * Method under test: {@link JSONArray#put(int, double)}
   */
  @Test
  public void testPutWithIntDouble2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 10.0d);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, double)} with {@code int}, {@code double}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, double)}
   */
  @Test
  public void testPutWithIntDouble_whenMinusOne_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).put(-1, 10.0d));
  }

  /**
   * Test {@link JSONArray#put(int, int)} with {@code int}, {@code int}.
   * <p>
   * Method under test: {@link JSONArray#put(int, int)}
   */
  @Test
  public void testPutWithIntInt() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 42);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, int)} with {@code int}, {@code int}.
   * <p>
   * Method under test: {@link JSONArray#put(int, int)}
   */
  @Test
  public void testPutWithIntInt2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 42);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, int)} with {@code int}, {@code int}.
   * <ul>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, int)}
   */
  @Test
  public void testPutWithIntInt_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).put(-1, 42));
  }

  /**
   * Test {@link JSONArray#put(int, long)} with {@code int}, {@code long}.
   * <p>
   * Method under test: {@link JSONArray#put(int, long)}
   */
  @Test
  public void testPutWithIntLong() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 42L);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, long)} with {@code int}, {@code long}.
   * <p>
   * Method under test: {@link JSONArray#put(int, long)}
   */
  @Test
  public void testPutWithIntLong2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 42L);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, long)} with {@code int}, {@code long}.
   * <ul>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, long)}
   */
  @Test
  public void testPutWithIntLong_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).put(-1, 42L));
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) new HashMap<>());

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code A}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenA_whenHashMapNullIsA() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, (byte) 'A');

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given {@link BiFunction}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenBiFunction_whenHashMapComputeIfPresentNullAndBiFunction() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONArray#JSONArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenJSONArray_whenHashMapNullIsJSONArray() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new JSONArray());

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#JSONObject()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenJSONObject_whenHashMapNullIsJSONObject() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new JSONObject());

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenNull_whenHashMapNullIsNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenNull_whenHashMapNullIsNull2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, null);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenOne_whenHashMapNullIsOne() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, (short) 1);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenOne_whenHashMapNullIsOne2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 1);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenOne_whenHashMapNullIsOne3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 1L);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given {@link SimpleEntry#SimpleEntry(Object, Object)} with
   * {@link JSONObject#NULL} and {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenSimpleEntryWithNullAndNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new AbstractMap.SimpleEntry<>(JSONObject.NULL, JSONObject.NULL));

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given start of heading.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is start of
   * heading.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenStartOfHeading_whenHashMapNullIsStartOfHeading() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, '\u0001');

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenTen_whenHashMapNullIsTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 10.0f);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_givenTrue_whenHashMapNullIsTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, true);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) value);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_whenHashMap() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) new HashMap<>());

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  public void testPutWithIntMap_whenMinusOne_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.put(-1, (Map) new HashMap<>()));
  }

  /**
   * Test {@link JSONArray#put(int, Object)} with {@code int}, {@code Object}.
   * <p>
   * Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  public void testPutWithIntObject() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, JSONObject.NULL);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Object)} with {@code int}, {@code Object}.
   * <p>
   * Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  public void testPutWithIntObject2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, JSONObject.NULL);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Object)} with {@code int}, {@code Object}.
   * <p>
   * Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  public void testPutWithIntObject3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Object) null);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Object)} with {@code int}, {@code Object}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  public void testPutWithIntObject_whenMinusOne_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> (new JSONArray("[]")).put(-1, JSONObject.NULL));
  }

  /**
   * Test {@link JSONArray#put(int, Object)} with {@code int}, {@code Object}.
   * <ul>
   *   <li>When ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  public void testPutWithIntObject_whenTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Object) 10.0d);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Object)} with {@code int}, {@code Object}.
   * <ul>
   *   <li>When ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  public void testPutWithIntObject_whenTen2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 10.0f);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(long)} with {@code long}.
   * <p>
   * Method under test: {@link JSONArray#put(long)}
   */
  @Test
  public void testPutWithLong() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(42L);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code A}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenA_whenHashMapNullIsA() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, (byte) 'A');

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given {@link BiFunction}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@link JSONObject#NULL}
   * and {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenBiFunction_whenHashMapComputeIfPresentNullAndBiFunction() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.computeIfPresent(JSONObject.NULL, mock(BiFunction.class));
    value.put(JSONObject.NULL, JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONArray#JSONArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenJSONArray_whenHashMapNullIsJSONArray() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new JSONArray());

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#JSONObject()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenJSONObject_whenHashMapNullIsJSONObject() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new JSONObject());

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenNull_whenHashMapNullIsNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenNull_whenHashMapNullIsNull2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, null);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenOne_whenHashMapNullIsOne() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, (short) 1);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenOne_whenHashMapNullIsOne2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 1);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenOne_whenHashMapNullIsOne3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 1L);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given {@link SimpleEntry#SimpleEntry(Object, Object)} with
   * {@link JSONObject#NULL} and {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenSimpleEntryWithNullAndNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new AbstractMap.SimpleEntry<>(JSONObject.NULL, JSONObject.NULL));

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given start of heading.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is start of
   * heading.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenStartOfHeading_whenHashMapNullIsStartOfHeading() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, '\u0001');

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenTen_whenHashMapNullIsTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, 10.0f);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_givenTrue_whenHashMapNullIsTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, true);

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  public void testPutWithMap_whenHashMap() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) new HashMap<>());

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Object)} with {@code Object}.
   * <p>
   * Method under test: {@link JSONArray#put(Object)}
   */
  @Test
  public void testPutWithObject() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(JSONObject.NULL);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#remove(int)}.
   * <p>
   * Method under test: {@link JSONArray#remove(int)}
   */
  @Test
  public void testRemove() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    jsonArray.remove(1);

    // Assert
    assertEquals(1, jsonArray.length());
  }

  /**
   * Test {@link JSONArray#remove(int)}.
   * <p>
   * Method under test: {@link JSONArray#remove(int)}
   */
  @Test
  public void testRemove2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act
    jsonArray.remove(1);

    // Assert
    assertEquals(1, jsonArray.length());
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   * <p>
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToStringWithIndentFactor() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[[]]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   * <p>
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToStringWithIndentFactor2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Map) new HashMap<>());

    // Act and Assert
    assertEquals("[{}]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <p>
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[[]]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <p>
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Map) new HashMap<>());

    // Act and Assert
    assertEquals("[{}]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code [1]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturn1() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1);

    // Act and Assert
    assertEquals("[1]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code [0.5]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturn05() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(0.5d);

    // Act and Assert
    assertEquals("[0.5]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code [ 10, [] ]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturn10() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(10.0d);
    jsonArray.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[\n    10,\n    []\n ]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code [false]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(false);

    // Act and Assert
    assertEquals("[false]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code [ [], false ]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturnFalse2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());
    jsonArray.put(false);

    // Act and Assert
    assertEquals("[\n    [],\n    false\n ]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturnLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", (new JSONArray("[]")).toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor},
   * {@code indent}.
   * <ul>
   *   <li>Then return {@code [null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  public void testToStringWithIndentFactorIndent_thenReturnNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code [1]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToStringWithIndentFactor_thenReturn1() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1);

    // Act and Assert
    assertEquals("[1]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code [0.5]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToStringWithIndentFactor_thenReturn05() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(0.5d);

    // Act and Assert
    assertEquals("[0.5]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code [ 10, [] ]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToStringWithIndentFactor_thenReturn10() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(10.0d);
    jsonArray.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[\n   10,\n   []\n]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code [false]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToStringWithIndentFactor_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(false);

    // Act and Assert
    assertEquals("[false]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code [ [], false ]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToStringWithIndentFactor_thenReturnFalse2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());
    jsonArray.put(false);

    // Act and Assert
    assertEquals("[\n   [],\n   false\n]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToStringWithIndentFactor_thenReturnLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", (new JSONArray("[]")).toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   * <ul>
   *   <li>Then return {@code [null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  public void testToStringWithIndentFactor_thenReturnNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(JSONObject.NULL);

    // Act and Assert
    assertEquals("[null]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   * <p>
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[[]]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   * <p>
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Map) new HashMap<>());
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[{}]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code [0.5]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite_thenStringWriterToStringIs05() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(0.5d);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[0.5]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is
   * {@code [10,[]]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite_thenStringWriterToStringIs10() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(10.0d);
    jsonArray.put((Collection) new ArrayList<>());
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[10,[]]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code [91]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite_thenStringWriterToStringIs91() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(91);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[91]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite_thenStringWriterToStringIsAString() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(91, false);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null"
        + ",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null"
        + ",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null"
        + ",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null"
        + ",null,null,null,null,null,null,null,null,null,null,null,false]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is
   * {@code [false]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite_thenStringWriterToStringIsFalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(false);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[false]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite_thenStringWriterToStringIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code [null]}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  public void testWrite_thenStringWriterToStringIsNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(JSONObject.NULL);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[null]", writer.toString());
    assertSame(writer, actualWriteResult);
  }
}
