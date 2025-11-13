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
import java.util.LinkedHashSet;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JSONArrayDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link JSONArray#JSONArray()}
   *   <li>{@link JSONArray#toString()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>()", "String JSONArray.toString()"})
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("[]", new JSONArray().toString());
  }

  /**
   * Test {@link JSONArray#JSONArray(Object)}.
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Object)"})
  public void testNewJSONArray() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray(JSONObject.NULL));
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given {@code A}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code A}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenA_whenArrayListAddA_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add((byte) 'A');

    // Act and Assert
    assertEquals(1, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONArray#JSONArray()}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenJSONArray_whenArrayListAddJSONArray_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(new JSONArray());

    // Act and Assert
    assertEquals(1, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#JSONObject()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenJSONObject_whenArrayListAddJSONObject() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(new JSONObject());

    // Act and Assert
    assertEquals(1, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@code null}.
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add {@link LinkedHashSet#LinkedHashSet()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenLinkedHashSetAddNull_whenLinkedHashSetAddLinkedHashSet() {
    // Arrange
    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add(null);

    LinkedHashSet<Object> collection = new LinkedHashSet<>();
    collection.add(objectSet);

    // Act and Assert
    assertEquals(1, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenNull_whenArrayListAddNull_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertEquals(1, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   *   <li>Then return length is two.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenNull_whenArrayListAddNull_thenReturnLengthIsTwo() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertEquals(2, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link ArrayList#ArrayList()} add one.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenOne_whenArrayListAddOne_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add((short) 1);

    // Act and Assert
    assertEquals(1, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link ArrayList#ArrayList()} add one.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenOne_whenArrayListAddOne_thenReturnLengthIsOne2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(1L);

    // Act and Assert
    assertEquals(1, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given start of heading.
   *   <li>When {@link ArrayList#ArrayList()} add start of heading.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenStartOfHeading_whenArrayListAddStartOfHeading() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add('\u0001');

    // Act and Assert
    assertEquals(1, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@link ArrayList#ArrayList()} add ten.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenTen_whenArrayListAddTen_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(10.0f);

    // Act and Assert
    assertEquals(1, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@link ArrayList#ArrayList()} add ten.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenTen_whenArrayListAddTen_thenReturnLengthIsOne2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(10.0d);

    // Act and Assert
    assertEquals(1, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code true}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenTrue_whenArrayListAddTrue_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(true);

    // Act and Assert
    assertEquals(1, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>Given two.
   *   <li>When {@link ArrayList#ArrayList()} add two.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_givenTwo_whenArrayListAddTwo_thenReturnLengthIsOne() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(2);

    // Act and Assert
    assertEquals(1, new JSONArray((Collection) collection).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(JSONTokener)}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(JSONTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(JSONTokener)"})
  public void testNewJSONArray_thenThrowJSONException() throws JSONException {
    // Arrange
    HTTPTokener x = mock(HTTPTokener.class);
    doThrow(new JSONException("An error occurred")).when(x).back();
    when(x.nextClean()).thenReturn('[');

    // Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray(x));
    verify(x).back();
    verify(x, atLeast(1)).nextClean();
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_whenArrayList_thenReturnLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, new JSONArray((Collection) new ArrayList<>()).length());
  }

  /**
   * Test {@link JSONArray#JSONArray(String)}.
   *
   * <ul>
   *   <li>When {@code []}.
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(String)"})
  public void testNewJSONArray_whenLeftSquareBracketRightSquareBracket_thenReturnLengthIsZero()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, new JSONArray("[]").length());
  }

  /**
   * Test {@link JSONArray#JSONArray(Collection)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#JSONArray(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JSONArray.<init>(Collection)"})
  public void testNewJSONArray_whenNull_thenReturnLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, new JSONArray((Collection) null).length());
  }

  /**
   * Test {@link JSONArray#get(int)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#get(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONArray.get(int)"})
  public void testGet_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse((Boolean) jsonArray.get(1));
  }

  /**
   * Test {@link JSONArray#get(int)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#get(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONArray.get(int)"})
  public void testGet_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertTrue((Boolean) jsonArray.get(1));
  }

  /**
   * Test {@link JSONArray#get(int)}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#get(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONArray.get(int)"})
  public void testGet_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").get(1));
  }

  /**
   * Test {@link JSONArray#get(int)}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#get(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONArray.get(int)"})
  public void testGet_thenThrowJSONException2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").get(-1));
  }

  /**
   * Test {@link JSONArray#getBoolean(int)}.
   *
   * <p>Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.getBoolean(int)"})
  public void testGetBoolean() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getBoolean(1));
  }

  /**
   * Test {@link JSONArray#getBoolean(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.getBoolean(int)"})
  public void testGetBoolean_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getBoolean(1));
  }

  /**
   * Test {@link JSONArray#getBoolean(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.getBoolean(int)"})
  public void testGetBoolean_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket2()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getBoolean(-1));
  }

  /**
   * Test {@link JSONArray#getBoolean(int)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.getBoolean(int)"})
  public void testGetBoolean_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse(jsonArray.getBoolean(1));
  }

  /**
   * Test {@link JSONArray#getBoolean(int)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getBoolean(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.getBoolean(int)"})
  public void testGetBoolean_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertTrue(jsonArray.getBoolean(1));
  }

  /**
   * Test {@link JSONArray#getDouble(int)}.
   *
   * <p>Method under test: {@link JSONArray#getDouble(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.getDouble(int)"})
  public void testGetDouble() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getDouble(1));
  }

  /**
   * Test {@link JSONArray#getDouble(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getDouble(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.getDouble(int)"})
  public void testGetDouble_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getDouble(1));
  }

  /**
   * Test {@link JSONArray#getDouble(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getDouble(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.getDouble(int)"})
  public void testGetDouble_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket2()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getDouble(-1));
  }

  /**
   * Test {@link JSONArray#getDouble(int)}.
   *
   * <ul>
   *   <li>Then return ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getDouble(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.getDouble(int)"})
  public void testGetDouble_thenReturnTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 10.0d);

    // Act and Assert
    assertEquals(10.0d, jsonArray.getDouble(1), 0.0);
  }

  /**
   * Test {@link JSONArray#getInt(int)}.
   *
   * <p>Method under test: {@link JSONArray#getInt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.getInt(int)"})
  public void testGetInt() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getInt(1));
  }

  /**
   * Test {@link JSONArray#getInt(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getInt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.getInt(int)"})
  public void testGetInt_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getInt(1));
  }

  /**
   * Test {@link JSONArray#getInt(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getInt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.getInt(int)"})
  public void testGetInt_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket2()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getInt(-1));
  }

  /**
   * Test {@link JSONArray#getInt(int)}.
   *
   * <ul>
   *   <li>Then return ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getInt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.getInt(int)"})
  public void testGetInt_thenReturnTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 10.0d);

    // Act and Assert
    assertEquals(10, jsonArray.getInt(1));
  }

  /**
   * Test {@link JSONArray#getJSONArray(int)}.
   *
   * <p>Method under test: {@link JSONArray#getJSONArray(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.getJSONArray(int)"})
  public void testGetJSONArray() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getJSONArray(1));
  }

  /**
   * Test {@link JSONArray#getJSONArray(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getJSONArray(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.getJSONArray(int)"})
  public void testGetJSONArray_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getJSONArray(1));
  }

  /**
   * Test {@link JSONArray#getJSONArray(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getJSONArray(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.getJSONArray(int)"})
  public void testGetJSONArray_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket2()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getJSONArray(-1));
  }

  /**
   * Test {@link JSONArray#getJSONArray(int)}.
   *
   * <ul>
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getJSONArray(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.getJSONArray(int)"})
  public void testGetJSONArray_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals(0, jsonArray.getJSONArray(1).length());
  }

  /**
   * Test {@link JSONArray#getJSONObject(int)}.
   *
   * <p>Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.getJSONObject(int)"})
  public void testGetJSONObject() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getJSONObject(1));
  }

  /**
   * Test {@link JSONArray#getJSONObject(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.getJSONObject(int)"})
  public void testGetJSONObject_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getJSONObject(1));
  }

  /**
   * Test {@link JSONArray#getJSONObject(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.getJSONObject(int)"})
  public void testGetJSONObject_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket2()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getJSONObject(-1));
  }

  /**
   * Test {@link JSONArray#getJSONObject(int)}.
   *
   * <ul>
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getJSONObject(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.getJSONObject(int)"})
  public void testGetJSONObject_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals(0, jsonArray.getJSONObject(1).length());
  }

  /**
   * Test {@link JSONArray#getLong(int)}.
   *
   * <p>Method under test: {@link JSONArray#getLong(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.getLong(int)"})
  public void testGetLong() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertThrows(JSONException.class, () -> jsonArray.getLong(1));
  }

  /**
   * Test {@link JSONArray#getLong(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getLong(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.getLong(int)"})
  public void testGetLong_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getLong(1));
  }

  /**
   * Test {@link JSONArray#getLong(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getLong(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.getLong(int)"})
  public void testGetLong_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket2()
      throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getLong(-1));
  }

  /**
   * Test {@link JSONArray#getLong(int)}.
   *
   * <ul>
   *   <li>Then return ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getLong(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.getLong(int)"})
  public void testGetLong_thenReturnTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 10.0d);

    // Act and Assert
    assertEquals(10L, jsonArray.getLong(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   *   <li>When one.
   *   <li>Then return {@code [null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.getString(int)"})
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
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   *   <li>When one.
   *   <li>Then return {@code [null,null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.getString(int)"})
  public void testGetString_givenArrayListAddNull_whenOne_thenReturnNullNull()
      throws JSONException {
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
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   *   <li>When one.
   *   <li>Then return {@code {"null":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.getString(int)"})
  public void testGetString_givenHashMapNullIsNull_whenOne_thenReturnNullNull()
      throws JSONException {
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
   *
   * <ul>
   *   <li>Then return {@code {}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.getString(int)"})
  public void testGetString_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", jsonArray.getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   *
   * <ul>
   *   <li>Then return {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.getString(int)"})
  public void testGetString_thenReturnLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[]", jsonArray.getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   *
   * <ul>
   *   <li>Then return {@link Boolean#TRUE} toString.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.getString(int)"})
  public void testGetString_thenReturnTrueToString() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertEquals(Boolean.TRUE.toString(), jsonArray.getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.getString(int)"})
  public void testGetString_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getString(1));
  }

  /**
   * Test {@link JSONArray#getString(int)}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#getString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.getString(int)"})
  public void testGetString_thenThrowJSONException2() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").getString(-1));
  }

  /**
   * Test {@link JSONArray#isNull(int)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#isNull(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.isNull(int)"})
  public void testIsNull_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertFalse(jsonArray.isNull(1));
  }

  /**
   * Test {@link JSONArray#isNull(int)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#isNull(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.isNull(int)"})
  public void testIsNull_thenReturnTrue() throws JSONException {
    // Arrange, Act and Assert
    assertTrue(new JSONArray("[]").isNull(1));
  }

  /**
   * Test {@link JSONArray#isNull(int)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#isNull(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.isNull(int)"})
  public void testIsNull_thenReturnTrue2() throws JSONException {
    // Arrange, Act and Assert
    assertTrue(new JSONArray("[]").isNull(-1));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   *   <li>Then return {@code {"null":null}Separatortrue}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#join(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.join(String)"})
  public void testJoin_givenHashMapNullIsNull_thenReturnNullNullSeparatortrue()
      throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Map) value);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("{\"null\":null}Separatortrue", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   *
   * <ul>
   *   <li>Then return {@code 1Separatortrue}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#join(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.join(String)"})
  public void testJoin_thenReturn1Separatortrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("1Separatortrue", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   *
   * <ul>
   *   <li>Then return {@code 0.5Separatortrue}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#join(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.join(String)"})
  public void testJoin_thenReturn05Separatortrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(0.5d);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("0.5Separatortrue", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   *
   * <ul>
   *   <li>Then return {@code 10}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#join(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.join(String)"})
  public void testJoin_thenReturn10() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(10.0d);

    // Act and Assert
    assertEquals("10", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#join(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.join(String)"})
  public void testJoin_thenReturnEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", new JSONArray("[]").join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   *
   * <ul>
   *   <li>Then return {@code falseSeparatortrue}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#join(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.join(String)"})
  public void testJoin_thenReturnFalseSeparatortrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(false);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("falseSeparatortrue", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   *
   * <ul>
   *   <li>Then return {@code nullSeparatortrue}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#join(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.join(String)"})
  public void testJoin_thenReturnNullSeparatortrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(JSONObject.NULL);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("nullSeparatortrue", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   *
   * <ul>
   *   <li>Then return {@code []Separatortrue}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#join(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.join(String)"})
  public void testJoin_thenReturnSeparatortrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[]Separatortrue", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   *
   * <ul>
   *   <li>Then return {@code {}Separatortrue}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#join(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.join(String)"})
  public void testJoin_thenReturnSeparatortrue2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Map) new HashMap<>());
    jsonArray.put(true);

    // Act and Assert
    assertEquals("{}Separatortrue", jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#join(String)}.
   *
   * <ul>
   *   <li>Then return {@link Boolean#TRUE} toString.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#join(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.join(String)"})
  public void testJoin_thenReturnTrueToString() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(true);

    // Act and Assert
    assertEquals(Boolean.TRUE.toString(), jsonArray.join("Separator"));
  }

  /**
   * Test {@link JSONArray#length()}.
   *
   * <ul>
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#length()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.length()"})
  public void testLength_thenReturnZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, new JSONArray("[]").length());
  }

  /**
   * Test {@link JSONArray#opt(int)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#opt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONArray.opt(int)"})
  public void testOpt_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse((Boolean) jsonArray.opt(1));
  }

  /**
   * Test {@link JSONArray#opt(int)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#opt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONArray.opt(int)"})
  public void testOpt_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(new JSONArray("[]").opt(1));
  }

  /**
   * Test {@link JSONArray#opt(int)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#opt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONArray.opt(int)"})
  public void testOpt_thenReturnNull2() throws JSONException {
    // Arrange, Act and Assert
    assertNull(new JSONArray("[]").opt(-1));
  }

  /**
   * Test {@link JSONArray#opt(int)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#opt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONArray.opt(int)"})
  public void testOpt_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertTrue((Boolean) jsonArray.opt(1));
  }

  /**
   * Test {@link JSONArray#optBoolean(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.optBoolean(int)"})
  public void testOptBooleanWithIndex() throws JSONException {
    // Arrange, Act and Assert
    assertFalse(new JSONArray("[]").optBoolean(1));
  }

  /**
   * Test {@link JSONArray#optBoolean(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.optBoolean(int)"})
  public void testOptBooleanWithIndex2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertFalse(jsonArray.optBoolean(1));
  }

  /**
   * Test {@link JSONArray#optBoolean(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.optBoolean(int)"})
  public void testOptBooleanWithIndex3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse(jsonArray.optBoolean(1));
  }

  /**
   * Test {@link JSONArray#optBoolean(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.optBoolean(int)"})
  public void testOptBooleanWithIndex4() throws JSONException {
    // Arrange, Act and Assert
    assertFalse(new JSONArray("[]").optBoolean(-1));
  }

  /**
   * Test {@link JSONArray#optBoolean(int, boolean)} with {@code index}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.optBoolean(int, boolean)"})
  public void testOptBooleanWithIndexDefaultValue() throws JSONException {
    // Arrange, Act and Assert
    assertTrue(new JSONArray("[]").optBoolean(1, true));
  }

  /**
   * Test {@link JSONArray#optBoolean(int, boolean)} with {@code index}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.optBoolean(int, boolean)"})
  public void testOptBooleanWithIndexDefaultValue2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertTrue(jsonArray.optBoolean(1, true));
  }

  /**
   * Test {@link JSONArray#optBoolean(int, boolean)} with {@code index}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.optBoolean(int, boolean)"})
  public void testOptBooleanWithIndexDefaultValue3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertTrue(jsonArray.optBoolean(1, true));
  }

  /**
   * Test {@link JSONArray#optBoolean(int, boolean)} with {@code index}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.optBoolean(int, boolean)"})
  public void testOptBooleanWithIndexDefaultValue4() throws JSONException {
    // Arrange, Act and Assert
    assertTrue(new JSONArray("[]").optBoolean(-1, true));
  }

  /**
   * Test {@link JSONArray#optBoolean(int, boolean)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optBoolean(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.optBoolean(int, boolean)"})
  public void testOptBooleanWithIndexDefaultValue_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act and Assert
    assertFalse(jsonArray.optBoolean(1, true));
  }

  /**
   * Test {@link JSONArray#optBoolean(int)} with {@code index}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optBoolean(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JSONArray.optBoolean(int)"})
  public void testOptBooleanWithIndex_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertTrue(jsonArray.optBoolean(1));
  }

  /**
   * Test {@link JSONArray#optDouble(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optDouble(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.optDouble(int)"})
  public void testOptDoubleWithIndex() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(Double.NaN, new JSONArray("[]").optDouble(1), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optDouble(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.optDouble(int)"})
  public void testOptDoubleWithIndex2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertEquals(Double.NaN, jsonArray.optDouble(1), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optDouble(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.optDouble(int)"})
  public void testOptDoubleWithIndex3() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(Double.NaN, new JSONArray("[]").optDouble(-1), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int, double)} with {@code index}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.optDouble(int, double)"})
  public void testOptDoubleWithIndexDefaultValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(10.0d, new JSONArray("[]").optDouble(1, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int, double)} with {@code index}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.optDouble(int, double)"})
  public void testOptDoubleWithIndexDefaultValue2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertEquals(10.0d, jsonArray.optDouble(1, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int, double)} with {@code index}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.optDouble(int, double)"})
  public void testOptDoubleWithIndexDefaultValue3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 10.0d);

    // Act and Assert
    assertEquals(10.0d, jsonArray.optDouble(1, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int, double)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>When minus one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.optDouble(int, double)"})
  public void testOptDoubleWithIndexDefaultValue_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(10.0d, new JSONArray("[]").optDouble(-1, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int, double)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>When three.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.optDouble(int, double)"})
  public void testOptDoubleWithIndexDefaultValue_whenThree() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(10.0d, new JSONArray("[]").optDouble(3, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int, double)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>When zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optDouble(int, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.optDouble(int, double)"})
  public void testOptDoubleWithIndexDefaultValue_whenZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(10.0d, new JSONArray("[]").optDouble(0, 10.0d), 0.0);
  }

  /**
   * Test {@link JSONArray#optDouble(int)} with {@code index}.
   *
   * <ul>
   *   <li>Then return forty-two.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optDouble(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"double JSONArray.optDouble(int)"})
  public void testOptDoubleWithIndex_thenReturnFortyTwo() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 42);

    // Act and Assert
    assertEquals(42.0d, jsonArray.optDouble(1), 0.0);
  }

  /**
   * Test {@link JSONArray#optInt(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optInt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.optInt(int)"})
  public void testOptIntWithIndex() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, new JSONArray("[]").optInt(1));
  }

  /**
   * Test {@link JSONArray#optInt(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optInt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.optInt(int)"})
  public void testOptIntWithIndex2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertEquals(0, jsonArray.optInt(1));
  }

  /**
   * Test {@link JSONArray#optInt(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optInt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.optInt(int)"})
  public void testOptIntWithIndex3() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, new JSONArray("[]").optInt(-1));
  }

  /**
   * Test {@link JSONArray#optInt(int, int)} with {@code index}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.optInt(int, int)"})
  public void testOptIntWithIndexDefaultValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, new JSONArray("[]").optInt(1, 42));
  }

  /**
   * Test {@link JSONArray#optInt(int, int)} with {@code index}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.optInt(int, int)"})
  public void testOptIntWithIndexDefaultValue2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertEquals(42, jsonArray.optInt(1, 42));
  }

  /**
   * Test {@link JSONArray#optInt(int, int)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.optInt(int, int)"})
  public void testOptIntWithIndexDefaultValue_thenReturnTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 10.0d);

    // Act and Assert
    assertEquals(10, jsonArray.optInt(1, 42));
  }

  /**
   * Test {@link JSONArray#optInt(int, int)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>When minus one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.optInt(int, int)"})
  public void testOptIntWithIndexDefaultValue_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, new JSONArray("[]").optInt(-1, 42));
  }

  /**
   * Test {@link JSONArray#optInt(int, int)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>When three.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.optInt(int, int)"})
  public void testOptIntWithIndexDefaultValue_whenThree() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, new JSONArray("[]").optInt(3, 42));
  }

  /**
   * Test {@link JSONArray#optInt(int, int)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>When zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optInt(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.optInt(int, int)"})
  public void testOptIntWithIndexDefaultValue_whenZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42, new JSONArray("[]").optInt(0, 42));
  }

  /**
   * Test {@link JSONArray#optInt(int)} with {@code index}.
   *
   * <ul>
   *   <li>Then return ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optInt(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int JSONArray.optInt(int)"})
  public void testOptIntWithIndex_thenReturnTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 10.0d);

    // Act and Assert
    assertEquals(10, jsonArray.optInt(1));
  }

  /**
   * Test {@link JSONArray#optJSONArray(int)}.
   *
   * <p>Method under test: {@link JSONArray#optJSONArray(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.optJSONArray(int)"})
  public void testOptJSONArray() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertNull(jsonArray.optJSONArray(1));
  }

  /**
   * Test {@link JSONArray#optJSONArray(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optJSONArray(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.optJSONArray(int)"})
  public void testOptJSONArray_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertNull(new JSONArray("[]").optJSONArray(1));
  }

  /**
   * Test {@link JSONArray#optJSONArray(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optJSONArray(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.optJSONArray(int)"})
  public void testOptJSONArray_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket2()
      throws JSONException {
    // Arrange, Act and Assert
    assertNull(new JSONArray("[]").optJSONArray(-1));
  }

  /**
   * Test {@link JSONArray#optJSONArray(int)}.
   *
   * <ul>
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optJSONArray(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.optJSONArray(int)"})
  public void testOptJSONArray_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals(0, jsonArray.optJSONArray(1).length());
  }

  /**
   * Test {@link JSONArray#optJSONObject(int)}.
   *
   * <p>Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.optJSONObject(int)"})
  public void testOptJSONObject() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertNull(jsonArray.optJSONObject(1));
  }

  /**
   * Test {@link JSONArray#optJSONObject(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.optJSONObject(int)"})
  public void testOptJSONObject_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertNull(new JSONArray("[]").optJSONObject(1));
  }

  /**
   * Test {@link JSONArray#optJSONObject(int)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.optJSONObject(int)"})
  public void testOptJSONObject_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket2()
      throws JSONException {
    // Arrange, Act and Assert
    assertNull(new JSONArray("[]").optJSONObject(-1));
  }

  /**
   * Test {@link JSONArray#optJSONObject(int)}.
   *
   * <ul>
   *   <li>Then return length is zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optJSONObject(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.optJSONObject(int)"})
  public void testOptJSONObject_thenReturnLengthIsZero() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals(0, jsonArray.optJSONObject(1).length());
  }

  /**
   * Test {@link JSONArray#optLong(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optLong(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.optLong(int)"})
  public void testOptLongWithIndex() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0L, new JSONArray("[]").optLong(1));
  }

  /**
   * Test {@link JSONArray#optLong(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optLong(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.optLong(int)"})
  public void testOptLongWithIndex2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertEquals(0L, jsonArray.optLong(1));
  }

  /**
   * Test {@link JSONArray#optLong(int)} with {@code index}.
   *
   * <p>Method under test: {@link JSONArray#optLong(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.optLong(int)"})
  public void testOptLongWithIndex3() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0L, new JSONArray("[]").optLong(-1));
  }

  /**
   * Test {@link JSONArray#optLong(int, long)} with {@code index}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.optLong(int, long)"})
  public void testOptLongWithIndexDefaultValue() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, new JSONArray("[]").optLong(1, 42L));
  }

  /**
   * Test {@link JSONArray#optLong(int, long)} with {@code index}, {@code defaultValue}.
   *
   * <p>Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.optLong(int, long)"})
  public void testOptLongWithIndexDefaultValue2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertEquals(42L, jsonArray.optLong(1, 42L));
  }

  /**
   * Test {@link JSONArray#optLong(int, long)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.optLong(int, long)"})
  public void testOptLongWithIndexDefaultValue_thenReturnTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 10.0d);

    // Act and Assert
    assertEquals(10L, jsonArray.optLong(1, 42L));
  }

  /**
   * Test {@link JSONArray#optLong(int, long)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>When minus one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.optLong(int, long)"})
  public void testOptLongWithIndexDefaultValue_whenMinusOne() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, new JSONArray("[]").optLong(-1, 42L));
  }

  /**
   * Test {@link JSONArray#optLong(int, long)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>When three.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.optLong(int, long)"})
  public void testOptLongWithIndexDefaultValue_whenThree() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, new JSONArray("[]").optLong(3, 42L));
  }

  /**
   * Test {@link JSONArray#optLong(int, long)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>When zero.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optLong(int, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.optLong(int, long)"})
  public void testOptLongWithIndexDefaultValue_whenZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(42L, new JSONArray("[]").optLong(0, 42L));
  }

  /**
   * Test {@link JSONArray#optLong(int)} with {@code index}.
   *
   * <ul>
   *   <li>Then return ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optLong(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long JSONArray.optLong(int)"})
  public void testOptLongWithIndex_thenReturnTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, 10.0d);

    // Act and Assert
    assertEquals(10L, jsonArray.optLong(1));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   *   <li>Then return {@code [null,null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int, String)"})
  public void testOptStringWithIndexDefaultValue_givenArrayListAddNull_thenReturnNullNull()
      throws JSONException {
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
   * Test {@link JSONArray#optString(int, String)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   *   <li>When one.
   *   <li>Then return {@code [null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int, String)"})
  public void testOptStringWithIndexDefaultValue_givenArrayListAddNull_whenOne_thenReturnNull()
      throws JSONException {
    // Arrange
    ArrayList<Object> value = new ArrayList<>();
    value.add(JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) value);

    // Act and Assert
    assertEquals("[null]", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   *   <li>Then return {@code {"null":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int, String)"})
  public void testOptStringWithIndexDefaultValue_givenHashMapNullIsNull_thenReturnNullNull()
      throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) value);

    // Act and Assert
    assertEquals("{\"null\":null}", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int, String)"})
  public void testOptStringWithIndexDefaultValue_thenReturn42() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", new JSONArray("[]").optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int, String)"})
  public void testOptStringWithIndexDefaultValue_thenReturn422() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("42", new JSONArray("[]").optString(-1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code {}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int, String)"})
  public void testOptStringWithIndexDefaultValue_thenReturnLeftCurlyBracketRightCurlyBracket()
      throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int, String)"})
  public void testOptStringWithIndexDefaultValue_thenReturnLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[]", jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int, String)} with {@code index}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@link Boolean#TRUE} toString.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int, String)"})
  public void testOptStringWithIndexDefaultValue_thenReturnTrueToString() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertEquals(Boolean.TRUE.toString(), jsonArray.optString(1, "42"));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   *   <li>When one.
   *   <li>Then return {@code [null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int)"})
  public void testOptStringWithIndex_givenArrayListAddNull_whenOne_thenReturnNull()
      throws JSONException {
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
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   *   <li>When one.
   *   <li>Then return {@code [null,null]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int)"})
  public void testOptStringWithIndex_givenArrayListAddNull_whenOne_thenReturnNullNull()
      throws JSONException {
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
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   *   <li>When one.
   *   <li>Then return {@code {"null":null}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int)"})
  public void testOptStringWithIndex_givenHashMapNullIsNull_whenOne_thenReturnNullNull()
      throws JSONException {
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
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int)"})
  public void testOptStringWithIndex_thenReturnEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", new JSONArray("[]").optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int)"})
  public void testOptStringWithIndex_thenReturnEmptyString2() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", new JSONArray("[]").optString(-1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   *
   * <ul>
   *   <li>Then return {@code {}}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int)"})
  public void testOptStringWithIndex_thenReturnLeftCurlyBracketRightCurlyBracket()
      throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", jsonArray.optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   *
   * <ul>
   *   <li>Then return {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int)"})
  public void testOptStringWithIndex_thenReturnLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, (Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("[]", jsonArray.optString(1));
  }

  /**
   * Test {@link JSONArray#optString(int)} with {@code index}.
   *
   * <ul>
   *   <li>Then return {@link Boolean#TRUE} toString.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#optString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.optString(int)"})
  public void testOptStringWithIndex_thenReturnTrueToString() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act and Assert
    assertEquals(Boolean.TRUE.toString(), jsonArray.optString(1));
  }

  /**
   * Test {@link JSONArray#put(boolean)} with {@code boolean}.
   *
   * <p>Method under test: {@link JSONArray#put(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(boolean)"})
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
   *
   * <p>Method under test: {@link JSONArray#put(boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(boolean)"})
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
   *
   * <ul>
   *   <li>Given {@code A}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code A}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
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
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONArray#JSONArray()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
  public void testPutWithCollection_givenJSONArray_whenArrayListAddJSONArray()
      throws JSONException {
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
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#JSONObject()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
  public void testPutWithCollection_givenJSONObject_whenArrayListAddJSONObject()
      throws JSONException {
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
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
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
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
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
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
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
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link ArrayList#ArrayList()} add one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
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
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link ArrayList#ArrayList()} add one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
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
   *
   * <ul>
   *   <li>Given start of heading.
   *   <li>When {@link ArrayList#ArrayList()} add start of heading.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
  public void testPutWithCollection_givenStartOfHeading_whenArrayListAddStartOfHeading()
      throws JSONException {
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
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@link ArrayList#ArrayList()} add ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
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
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@link ArrayList#ArrayList()} add ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
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
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
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
   *
   * <ul>
   *   <li>Given two.
   *   <li>When {@link ArrayList#ArrayList()} add two.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
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
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Collection)"})
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
   *
   * <p>Method under test: {@link JSONArray#put(double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(double)"})
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
   *
   * <p>Method under test: {@link JSONArray#put(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int)"})
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
   *
   * <p>Method under test: {@link JSONArray#put(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, boolean)"})
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
   *
   * <p>Method under test: {@link JSONArray#put(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, boolean)"})
  public void testPutWithIntBoolean2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, true);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, boolean)} with {@code int}, {@code boolean}.
   *
   * <p>Method under test: {@link JSONArray#put(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, boolean)"})
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
   *
   * <ul>
   *   <li>When minus one.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, boolean)"})
  public void testPutWithIntBoolean_whenMinusOne_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").put(-1, true));
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
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
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@code A}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
  public void testPutWithIntCollection_givenLinkedHashSetAddA() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add((byte) 'A');

    LinkedHashSet<Object> value = new LinkedHashSet<>();
    value.add(objectSet);

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@link JSONArray#JSONArray()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
  public void testPutWithIntCollection_givenLinkedHashSetAddJSONArray() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add(new JSONArray());

    LinkedHashSet<Object> value = new LinkedHashSet<>();
    value.add(objectSet);

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@link JSONObject#JSONObject()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
  public void testPutWithIntCollection_givenLinkedHashSetAddJSONObject() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add(new JSONObject());

    LinkedHashSet<Object> value = new LinkedHashSet<>();
    value.add(objectSet);

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
  public void testPutWithIntCollection_givenLinkedHashSetAddNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add(null);

    LinkedHashSet<Object> value = new LinkedHashSet<>();
    value.add(objectSet);

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
  public void testPutWithIntCollection_givenLinkedHashSetAddOne() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add((short) 1);

    LinkedHashSet<Object> value = new LinkedHashSet<>();
    value.add(objectSet);

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
  public void testPutWithIntCollection_givenLinkedHashSetAddOne2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add(1L);

    LinkedHashSet<Object> value = new LinkedHashSet<>();
    value.add(objectSet);

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add start of heading.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
  public void testPutWithIntCollection_givenLinkedHashSetAddStartOfHeading() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add('\u0001');

    LinkedHashSet<Object> value = new LinkedHashSet<>();
    value.add(objectSet);

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
  public void testPutWithIntCollection_givenLinkedHashSetAddTen() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add(10.0f);

    LinkedHashSet<Object> value = new LinkedHashSet<>();
    value.add(objectSet);

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
  public void testPutWithIntCollection_givenLinkedHashSetAddTen2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add(10.0d);

    LinkedHashSet<Object> value = new LinkedHashSet<>();
    value.add(objectSet);

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
  public void testPutWithIntCollection_givenLinkedHashSetAddTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add(true);

    LinkedHashSet<Object> value = new LinkedHashSet<>();
    value.add(objectSet);

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link LinkedHashSet#LinkedHashSet()} add two.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
  public void testPutWithIntCollection_givenLinkedHashSetAddTwo() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    LinkedHashSet<Object> objectSet = new LinkedHashSet<>();
    objectSet.add(2);

    LinkedHashSet<Object> value = new LinkedHashSet<>();
    value.add(objectSet);

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Collection) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
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
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
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
   * Test {@link JSONArray#put(int, Collection)} with {@code int}, {@code Collection}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Collection)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Collection)"})
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
   * Test {@link JSONArray#put(int, double)} with {@code int}, {@code double}.
   *
   * <p>Method under test: {@link JSONArray#put(int, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, double)"})
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
   *
   * <p>Method under test: {@link JSONArray#put(int, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, double)"})
  public void testPutWithIntDouble2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 10.0d);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, double)} with {@code int}, {@code double}.
   *
   * <ul>
   *   <li>When minus one.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, double)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, double)"})
  public void testPutWithIntDouble_whenMinusOne_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").put(-1, 10.0d));
  }

  /**
   * Test {@link JSONArray#put(int, int)} with {@code int}, {@code int}.
   *
   * <p>Method under test: {@link JSONArray#put(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, int)"})
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
   *
   * <p>Method under test: {@link JSONArray#put(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, int)"})
  public void testPutWithIntInt2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 42);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, int)} with {@code int}, {@code int}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, int)"})
  public void testPutWithIntInt_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").put(-1, 42));
  }

  /**
   * Test {@link JSONArray#put(int, long)} with {@code int}, {@code long}.
   *
   * <p>Method under test: {@link JSONArray#put(int, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, long)"})
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
   *
   * <p>Method under test: {@link JSONArray#put(int, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, long)"})
  public void testPutWithIntLong2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 42L);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, long)} with {@code int}, {@code long}.
   *
   * <ul>
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, long)"})
  public void testPutWithIntLong_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").put(-1, 42L));
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   *
   * <p>Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Map)"})
  public void testPutWithIntMap() throws JSONException {
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
   *
   * <p>Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Map)"})
  public void testPutWithIntMap2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, (Map) new HashMap<>());

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   *
   * <p>Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Map)"})
  public void testPutWithIntMap3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(0, (Map) null);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Map)} with {@code int}, {@code Map}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Map)"})
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
   * Test {@link JSONArray#put(int, Object)} with {@code int}, {@code Object}.
   *
   * <p>Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Object)"})
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
   *
   * <p>Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Object)"})
  public void testPutWithIntObject2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act
    JSONArray actualPutResult = jsonArray.put(1, JSONObject.NULL);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Object)} with {@code int}, {@code Object}.
   *
   * <p>Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Object)"})
  public void testPutWithIntObject3() throws JSONException {
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
   *
   * <p>Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Object)"})
  public void testPutWithIntObject4() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act
    JSONArray actualPutResult = jsonArray.put(1, 10.0f);

    // Assert
    assertEquals(2, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(int, Object)} with {@code int}, {@code Object}.
   *
   * <p>Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Object)"})
  public void testPutWithIntObject5() throws JSONException {
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
   *
   * <ul>
   *   <li>When minus one.
   *   <li>Then throw {@link JSONException}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(int, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(int, Object)"})
  public void testPutWithIntObject_whenMinusOne_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> new JSONArray("[]").put(-1, JSONObject.NULL));
  }

  /**
   * Test {@link JSONArray#put(long)} with {@code long}.
   *
   * <p>Method under test: {@link JSONArray#put(long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(long)"})
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
   *
   * <ul>
   *   <li>Given {@code A}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code A}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
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
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONArray#JSONArray()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
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
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link
   *       JSONObject#JSONObject()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
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
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
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
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
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
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
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
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
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
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
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
   *
   * <ul>
   *   <li>Given {@link SimpleEntry#SimpleEntry(Object, Object)} with {@link JSONObject#NULL} and
   *       {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
  public void testPutWithMap_givenSimpleEntryWithNullAndNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, new SimpleEntry<>(JSONObject.NULL, JSONObject.NULL));

    // Act
    JSONArray actualPutResult = jsonArray.put((Map) value);

    // Assert
    assertEquals(1, jsonArray.length());
    assertSame(jsonArray, actualPutResult);
  }

  /**
   * Test {@link JSONArray#put(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Given start of heading.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is start of heading.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
  public void testPutWithMap_givenStartOfHeading_whenHashMapNullIsStartOfHeading()
      throws JSONException {
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
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is ten.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
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
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
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
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#put(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Map)"})
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
   *
   * <p>Method under test: {@link JSONArray#put(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONArray.put(Object)"})
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
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#remove(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONArray.remove(int)"})
  public void testRemove_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, false);

    // Act
    Object actualRemoveResult = jsonArray.remove(1);

    // Assert
    assertEquals(1, jsonArray.length());
    assertFalse((Boolean) actualRemoveResult);
  }

  /**
   * Test {@link JSONArray#remove(int)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#remove(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JSONArray.remove(int)"})
  public void testRemove_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1, true);

    // Act
    Object actualRemoveResult = jsonArray.remove(1);

    // Assert
    assertEquals(1, jsonArray.length());
    assertTrue((Boolean) actualRemoveResult);
  }

  /**
   * Test {@link JSONArray#toJSONObject(JSONArray)}.
   *
   * <p>Method under test: {@link JSONArray#toJSONObject(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.toJSONObject(JSONArray)"})
  public void testToJSONObject() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());

    JSONArray names = new JSONArray();
    names.put(true);

    // Act and Assert
    assertEquals(1, jsonArray.toJSONObject(names).length());
  }

  /**
   * Test {@link JSONArray#toJSONObject(JSONArray)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>When {@link JSONArray#JSONArray()} {@link ArrayList#ArrayList()}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toJSONObject(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.toJSONObject(JSONArray)"})
  public void testToJSONObject_givenArrayList_whenJSONArrayArrayList_thenReturnLengthIsOne()
      throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());

    JSONArray names = new JSONArray();
    names.put((Collection) new ArrayList<>());
    names.put(true);

    // Act and Assert
    assertEquals(1, jsonArray.toJSONObject(names).length());
  }

  /**
   * Test {@link JSONArray#toJSONObject(JSONArray)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>When {@link JSONArray#JSONArray()} {@link HashMap#HashMap()}.
   *   <li>Then return length is one.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toJSONObject(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.toJSONObject(JSONArray)"})
  public void testToJSONObject_givenHashMap_whenJSONArrayHashMap_thenReturnLengthIsOne()
      throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());

    JSONArray names = new JSONArray();
    names.put((Map) new HashMap<>());
    names.put(true);

    // Act and Assert
    assertEquals(1, jsonArray.toJSONObject(names).length());
  }

  /**
   * Test {@link JSONArray#toJSONObject(JSONArray)}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []} {@code -0.5}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toJSONObject(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.toJSONObject(JSONArray)"})
  public void testToJSONObject_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket05()
      throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(-0.5d);

    JSONArray names = new JSONArray();
    names.put(true);

    // Act and Assert
    assertEquals(1, jsonArray.toJSONObject(names).length());
  }

  /**
   * Test {@link JSONArray#toJSONObject(JSONArray)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toJSONObject(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.toJSONObject(JSONArray)"})
  public void testToJSONObject_thenReturnNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    JSONArray names = new JSONArray();
    names.put(true);

    // Act and Assert
    assertNull(jsonArray.toJSONObject(names));
  }

  /**
   * Test {@link JSONArray#toJSONObject(JSONArray)}.
   *
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toJSONObject(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.toJSONObject(JSONArray)"})
  public void testToJSONObject_whenJSONArray_thenReturnNull() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");

    // Act and Assert
    assertNull(jsonArray.toJSONObject(new JSONArray()));
  }

  /**
   * Test {@link JSONArray#toJSONObject(JSONArray)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toJSONObject(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONArray.toJSONObject(JSONArray)"})
  public void testToJSONObject_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertNull(new JSONArray("[]").toJSONObject(null));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code [ 1, true ]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturn1True() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[\n    1,\n    true\n ]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code [ 0.5, true ]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturn05True() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(0.5d);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[\n    0.5,\n    true\n ]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code [10]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturn10() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(10.0d);

    // Act and Assert
    assertEquals("[10]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code [ false, true ]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnFalseTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(false);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[\n    false,\n    true\n ]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", new JSONArray("[]").toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code [ null, true ]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnNullTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(JSONObject.NULL);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[\n    null,\n    true\n ]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code [true]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[true]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code [ [], true ]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnTrue2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[\n    [],\n    true\n ]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int, int)} with {@code indentFactor}, {@code indent}.
   *
   * <ul>
   *   <li>Then return {@code [ {}, true ]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int, int)"})
  public void testToStringWithIndentFactorIndent_thenReturnTrue3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Map) new HashMap<>());
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[\n    {},\n    true\n ]", jsonArray.toString(3, 1));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   *
   * <ul>
   *   <li>Then return {@code [ 1, true ]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int)"})
  public void testToStringWithIndentFactor_thenReturn1True() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(1);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[\n   1,\n   true\n]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   *
   * <ul>
   *   <li>Then return {@code [ 0.5, true ]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int)"})
  public void testToStringWithIndentFactor_thenReturn05True() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(0.5d);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[\n   0.5,\n   true\n]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   *
   * <ul>
   *   <li>Then return {@code [10]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int)"})
  public void testToStringWithIndentFactor_thenReturn10() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(10.0d);

    // Act and Assert
    assertEquals("[10]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   *
   * <ul>
   *   <li>Then return {@code [ false, true ]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnFalseTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(false);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[\n   false,\n   true\n]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   *
   * <ul>
   *   <li>Then return {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnLeftSquareBracketRightSquareBracket()
      throws JSONException {
    // Arrange, Act and Assert
    assertEquals("[]", new JSONArray("[]").toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   *
   * <ul>
   *   <li>Then return {@code [ null, true ]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnNullTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(JSONObject.NULL);
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[\n   null,\n   true\n]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   *
   * <ul>
   *   <li>Then return {@code [true]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[true]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   *
   * <ul>
   *   <li>Then return {@code [ [], true ]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnTrue2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[\n   [],\n   true\n]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#toString(int)} with {@code indentFactor}.
   *
   * <ul>
   *   <li>Then return {@code [ {}, true ]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#toString(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONArray.toString(int)"})
  public void testToStringWithIndentFactor_thenReturnTrue3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Map) new HashMap<>());
    jsonArray.put(true);

    // Act and Assert
    assertEquals("[\n   {},\n   true\n]", jsonArray.toString(3));
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code [0.5,true]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONArray.write(Writer)"})
  public void testWrite_thenStringWriterToStringIs05True() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(0.5d);
    jsonArray.put(true);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[0.5,true]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code [10]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONArray.write(Writer)"})
  public void testWrite_thenStringWriterToStringIs10() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(10.0d);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[10]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code [91,true]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONArray.write(Writer)"})
  public void testWrite_thenStringWriterToStringIs91True() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(91);
    jsonArray.put(true);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[91,true]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code [false,true]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONArray.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsFalseTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(false);
    jsonArray.put(true);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[false,true]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONArray.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsLeftSquareBracketRightSquareBracket()
      throws JSONException {
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
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code [null,true]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONArray.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsNullTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(JSONObject.NULL);
    jsonArray.put(true);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[null,true]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code [true]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONArray.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsTrue() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put(true);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[true]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code [[],true]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONArray.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsTrue2() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Collection) new ArrayList<>());
    jsonArray.put(true);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[[],true]", writer.toString());
    assertSame(writer, actualWriteResult);
  }

  /**
   * Test {@link JSONArray#write(Writer)}.
   *
   * <ul>
   *   <li>Then {@link StringWriter#StringWriter()} toString is {@code [{},true]}.
   * </ul>
   *
   * <p>Method under test: {@link JSONArray#write(Writer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Writer JSONArray.write(Writer)"})
  public void testWrite_thenStringWriterToStringIsTrue3() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray("[]");
    jsonArray.put((Map) new HashMap<>());
    jsonArray.put(true);
    StringWriter writer = new StringWriter();

    // Act
    Writer actualWriteResult = jsonArray.write(writer);

    // Assert
    assertEquals("[{},true]", writer.toString());
    assertSame(writer, actualWriteResult);
  }
}
