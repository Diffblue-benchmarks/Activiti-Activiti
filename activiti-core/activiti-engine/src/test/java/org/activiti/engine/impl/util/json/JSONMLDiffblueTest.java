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
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.junit.Test;
import org.mockito.Mockito;

public class JSONMLDiffblueTest {
  /**
   * Test {@link JSONML#toString(JSONArray)} with {@code ja}.
   * <p>
   * Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  public void testToStringWithJa() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("<[]/>", JSONML.toString(ja));
  }

  /**
   * Test {@link JSONML#toString(JSONArray)} with {@code ja}.
   * <p>
   * Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  public void testToStringWithJa2() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Map) new HashMap<>());

    // Act and Assert
    assertEquals("<{}/>", JSONML.toString(ja));
  }

  /**
   * Test {@link JSONML#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>Then return {@code <false/>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  public void testToStringWithJa_givenFalse_thenReturnFalse() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(false);

    // Act and Assert
    assertEquals("<false/>", JSONML.toString(ja));
  }

  /**
   * Test {@link JSONML#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Then return {@code <null></null>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  public void testToStringWithJa_thenReturnNullNull() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(2, true);
    ja.put(false);

    // Act and Assert
    assertEquals("<null></null>", JSONML.toString(ja));
  }

  /**
   * Test {@link JSONML#toString(JSONArray)} with {@code ja}.
   * <ul>
   *   <li>Then return {@code <true></true>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  public void testToStringWithJa_thenReturnTrueTrue() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(true);
    ja.put(false);

    // Act and Assert
    assertEquals("<true></true>", JSONML.toString(ja));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo() throws JSONException {
    // Arrange
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.get(anyInt())).thenThrow(new JSONException("An error occurred"));
    when(jsonArray.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);

    ArrayList<Object> objectList = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray).get(eq(0));
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code childNodes}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenArrayListAddChildNodes_thenThrowJSONException() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("childNodes");
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);
    when(jsonObject.keys()).thenReturn(iteratorResult);
    when(jsonObject.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
    verify(jsonObject).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link JSONObject#JSONObject()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenArrayListAddJSONObject_thenThrowJSONException() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(new JSONObject());
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);
    when(jsonObject.keys()).thenReturn(iteratorResult);
    when(jsonObject.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject).optJSONArray(eq("childNodes"));
    verify(jsonObject, atLeast(1)).optString(Mockito.<String>any());
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenArrayListAddNull_thenThrowJSONException() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);
    when(jsonObject.keys()).thenReturn(iteratorResult);
    when(jsonObject.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject).optJSONArray(eq("childNodes"));
    verify(jsonObject, atLeast(1)).optString(Mockito.<String>any());
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code tagName}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenArrayListAddTagName_thenThrowJSONException() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("tagName");
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);
    when(jsonObject.keys()).thenReturn(iteratorResult);
    when(jsonObject.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
    verify(jsonObject).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add toJSONObject
   * {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenArrayListAddToJSONObjectHttpsExampleOrgExample() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(HTTP.toJSONObject("https://example.org/example"));
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);
    when(jsonObject.keys()).thenReturn(iteratorResult);
    when(jsonObject.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject).optJSONArray(eq("childNodes"));
    verify(jsonObject, atLeast(1)).optString(Mockito.<String>any());
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONArray} {@link JSONArray#get(int)} return
   * {@link JSONArray#JSONArray()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONArrayGetReturnJSONArray_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.get(anyInt())).thenReturn(new JSONArray());
    when(jsonArray.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);

    ArrayList<Object> objectList = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray).get(eq(0));
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONArray} {@link JSONArray#get(int)} return
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code <tagName></tagName>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONArrayGetReturnNull_thenReturnTagNameTagName() throws JSONException {
    // Arrange
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.get(anyInt())).thenReturn(JSONObject.NULL);
    when(jsonArray.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);

    ArrayList<Object> objectList = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act
    String actualToStringResult = JSONML.toString(jo);

    // Assert
    verify(jsonArray, atLeast(1)).get(anyInt());
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
    assertEquals("<tagName></tagName>", actualToStringResult);
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONArray} {@link JSONArray#get(int)} return
   * {@code null}.</li>
   *   <li>Then return {@code <tagName></tagName>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONArrayGetReturnNull_thenReturnTagNameTagName2() throws JSONException {
    // Arrange
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.get(anyInt())).thenReturn(null);
    when(jsonArray.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);

    ArrayList<Object> objectList = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act
    String actualToStringResult = JSONML.toString(jo);

    // Assert
    verify(jsonArray, atLeast(1)).get(anyInt());
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
    assertEquals("<tagName></tagName>", actualToStringResult);
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray(String)} with source is {@code []}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONArrayWithSourceIsLeftSquareBracketRightSquareBracket() throws JSONException {
    // Arrange
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(new JSONArray("[]"));

    ArrayList<Object> objectList = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act
    String actualToStringResult = JSONML.toString(jo);

    // Assert
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
    assertEquals("<tagName></tagName>", actualToStringResult);
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONException#JSONException(String)} with message is
   * {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONExceptionWithMessageIsAnErrorOccurred() throws JSONException {
    // Arrange
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenThrow(new JSONException("An error occurred"));

    ArrayList<Object> objectList = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} append {@code tagName} and
   * {@link JSONObject#NULL}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONObjectAppendTagNameAndNull_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("tagName", JSONObject.NULL);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);
    when(jsonObject2.keys()).thenReturn(iteratorResult);
    when(jsonObject2.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject2);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject2).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject2).optJSONArray(eq("childNodes"));
    verify(jsonObject2, atLeast(1)).optString(Mockito.<String>any());
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code childNodes} is
   * {@code 0.5}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONObjectChildNodesIs05_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("childNodes", 0.5d);
    jsonObject.append("tagName", JSONObject.NULL);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);
    when(jsonObject2.keys()).thenReturn(iteratorResult);
    when(jsonObject2.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject2);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject2).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject2).optJSONArray(eq("childNodes"));
    verify(jsonObject2, atLeast(1)).optString(Mockito.<String>any());
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code childNodes} is
   * {@code false}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONObjectChildNodesIsFalse_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("childNodes", false);
    jsonObject.append("tagName", JSONObject.NULL);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);
    when(jsonObject2.keys()).thenReturn(iteratorResult);
    when(jsonObject2.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject2);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject2).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject2).optJSONArray(eq("childNodes"));
    verify(jsonObject2, atLeast(1)).optString(Mockito.<String>any());
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code childNodes} is
   * {@code false}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONObjectChildNodesIsFalse_thenThrowJSONException2() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("tagName", JSONObject.NULL);
    jsonObject.put("childNodes", false);
    jsonObject.append("tagName", JSONObject.NULL);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);
    when(jsonObject2.keys()).thenReturn(iteratorResult);
    when(jsonObject2.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject2);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject2).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject2).optJSONArray(eq("childNodes"));
    verify(jsonObject2, atLeast(1)).optString(Mockito.<String>any());
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code childNodes} is
   * {@link HashMap#HashMap()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONObjectChildNodesIsHashMap_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("childNodes", (Map) new HashMap<>());
    jsonObject.append("tagName", JSONObject.NULL);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);
    when(jsonObject2.keys()).thenReturn(iteratorResult);
    when(jsonObject2.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject2);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject2).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject2).optJSONArray(eq("childNodes"));
    verify(jsonObject2, atLeast(1)).optString(Mockito.<String>any());
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} increment {@code childNodes}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONObjectIncrementChildNodes_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.increment("childNodes");
    jsonObject.append("tagName", JSONObject.NULL);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);
    when(jsonObject2.keys()).thenReturn(iteratorResult);
    when(jsonObject2.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject2);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject2).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject2).optJSONArray(eq("childNodes"));
    verify(jsonObject2, atLeast(1)).optString(Mockito.<String>any());
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONObject} {@link JSONObject#optString(String)} return
   * {@code null}.</li>
   *   <li>Then calls {@link JSONArray#get(int)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONObjectOptStringReturnNull_thenCallsGet() throws JSONException {
    // Arrange
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);

    ArrayList<Object> objectList = new ArrayList<>();
    when(jsonObject.keys()).thenReturn(objectList.iterator());
    when(jsonObject.optString(Mockito.<String>any())).thenReturn(null);
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act
    JSONML.toString(jo);

    // Assert
    verify(jsonArray2, atLeast(1)).get(anyInt());
    verify(jsonArray2).length();
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
    verify(jsonObject, atLeast(1)).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code tagName} is ten.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenJSONObjectTagNameIsTen_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("tagName", 10.0d);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);
    when(jsonObject2.keys()).thenReturn(iteratorResult);
    when(jsonObject2.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject2);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject2).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject2).optJSONArray(eq("childNodes"));
    verify(jsonObject2, atLeast(1)).optString(Mockito.<String>any());
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return {@code <tagName/>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_givenNull_thenReturnTagName() throws JSONException {
    // Arrange
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(null);

    ArrayList<Object> objectList = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act
    String actualToStringResult = JSONML.toString(jo);

    // Assert
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
    assertEquals("<tagName/>", actualToStringResult);
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Then return
   * {@code <tagName><foo></foo><foo></foo><foo></foo></tagName>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_thenReturnTagNameFooFooFooFooFooFooTagName() throws JSONException {
    // Arrange
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.optJSONArray(Mockito.<String>any())).thenReturn(new JSONArray("[]"));

    ArrayList<Object> objectList = new ArrayList<>();
    when(jsonObject.keys()).thenReturn(objectList.iterator());
    when(jsonObject.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.get(anyInt())).thenReturn(jsonObject);
    when(jsonArray.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act
    String actualToStringResult = JSONML.toString(jo);

    // Assert
    verify(jsonArray, atLeast(1)).get(anyInt());
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject, atLeast(1)).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject, atLeast(1)).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
    verify(jsonObject, atLeast(1)).optString(eq("tagName"));
    assertEquals("<tagName><foo></foo><foo></foo><foo></foo></tagName>", actualToStringResult);
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Then return {@code <tagName>tagNametagNametagName</tagName>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_thenReturnTagNameTagNametagNametagNameTagName() throws JSONException {
    // Arrange
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.get(anyInt())).thenReturn("tagName");
    when(jsonArray.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);

    ArrayList<Object> objectList = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act
    String actualToStringResult = JSONML.toString(jo);

    // Assert
    verify(jsonArray, atLeast(1)).get(anyInt());
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
    assertEquals("<tagName>tagNametagNametagName</tagName>", actualToStringResult);
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   * <ul>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToStringWithJo_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONArray jsonArray = mock(JSONArray.class);
    when(jsonArray.length()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray);

    ArrayList<Object> objectList = new ArrayList<>();
    when(jsonObject.keys()).thenReturn(objectList.iterator());
    when(jsonObject.optString(Mockito.<String>any())).thenReturn("foo");
    JSONArray jsonArray2 = mock(JSONArray.class);
    when(jsonArray2.get(anyInt())).thenReturn(jsonObject);
    when(jsonArray2.length()).thenReturn(3);
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(jsonArray2);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList2.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toString(jo));
    verify(jsonArray2).get(eq(0));
    verify(jsonArray2).length();
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
    verify(jsonObject).optString(eq("tagName"));
  }
}
