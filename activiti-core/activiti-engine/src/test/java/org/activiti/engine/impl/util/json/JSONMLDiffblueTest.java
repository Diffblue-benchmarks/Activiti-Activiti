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
   * Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  public void testToString() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(false);

    // Act and Assert
    assertEquals("<false/>", JSONML.toString(ja));
  }

  /**
   * Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  public void testToString2() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Collection) new ArrayList<>());

    // Act and Assert
    assertEquals("<[]/>", JSONML.toString(ja));
  }

  /**
   * Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  public void testToString3() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Map) new HashMap<>());

    // Act and Assert
    assertEquals("<{}/>", JSONML.toString(ja));
  }

  /**
   * Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  public void testToString4() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(true);
    ja.put(false);

    // Act and Assert
    assertEquals("<true></true>", JSONML.toString(ja));
  }

  /**
   * Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  public void testToString5() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(2, true);
    ja.put(false);

    // Act and Assert
    assertEquals("<null></null>", JSONML.toString(ja));
  }

  /**
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString6() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString7() throws JSONException {
    // Arrange
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(new JSONArray("[]"));

    ArrayList<Object> objectList = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act
    JSONML.toString(jo);

    // Assert
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString8() throws JSONException {
    // Arrange
    JSONObject jo = mock(JSONObject.class);
    when(jo.optJSONArray(Mockito.<String>any())).thenReturn(null);

    ArrayList<Object> objectList = new ArrayList<>();
    when(jo.keys()).thenReturn(objectList.iterator());
    when(jo.optString(Mockito.<String>any())).thenReturn("tagName");

    // Act
    JSONML.toString(jo);

    // Assert
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString9() throws JSONException {
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
    JSONML.toString(jo);

    // Assert
    verify(jsonArray, atLeast(1)).get(anyInt());
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString10() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString11() throws JSONException {
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
    JSONML.toString(jo);

    // Assert
    verify(jsonArray, atLeast(1)).get(anyInt());
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString12() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString13() throws JSONException {
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
    JSONML.toString(jo);

    // Assert
    verify(jsonArray, atLeast(1)).get(anyInt());
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
  }

  /**
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString14() throws JSONException {
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
    JSONML.toString(jo);

    // Assert
    verify(jsonArray, atLeast(1)).get(anyInt());
    verify(jsonArray).length();
    verify(jo).keys();
    verify(jsonObject, atLeast(1)).keys();
    verify(jo).optJSONArray(eq("childNodes"));
    verify(jsonObject, atLeast(1)).optJSONArray(eq("childNodes"));
    verify(jo).optString(eq("tagName"));
    verify(jsonObject, atLeast(1)).optString(eq("tagName"));
  }

  /**
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString15() throws JSONException {
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

  /**
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString16() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString17() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString18() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString19() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString20() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString21() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString22() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString23() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString24() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString25() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString26() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString27() throws JSONException {
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
   * Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  public void testToString28() throws JSONException {
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
}
