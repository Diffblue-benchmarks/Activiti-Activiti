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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class XMLDiffblueTest {
  /**
   * Test {@link XML#escape(String)}.
   * <ul>
   *   <li>When {@code -->}.</li>
   *   <li>Then return {@code --&gt;}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#escape(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.escape(String)"})
  public void testEscape_whenDashDashGreaterThanSign_thenReturnGt() {
    // Arrange, Act and Assert
    assertEquals("--&gt;", XML.escape("-->"));
  }

  /**
   * Test {@link XML#escape(String)}.
   * <ul>
   *   <li>When {@code <}.</li>
   *   <li>Then return {@code &lt;}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#escape(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.escape(String)"})
  public void testEscape_whenLessThanSign_thenReturnLt() {
    // Arrange, Act and Assert
    assertEquals("&lt;", XML.escape("<"));
  }

  /**
   * Test {@link XML#escape(String)}.
   * <ul>
   *   <li>When {@code String}.</li>
   *   <li>Then return {@code String}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#escape(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.escape(String)"})
  public void testEscape_whenString_thenReturnString() {
    // Arrange, Act and Assert
    assertEquals("String", XML.escape("String"));
  }

  /**
   * Test {@link XML#noSpace(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#noSpace(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void XML.noSpace(String)"})
  public void testNoSpace_whenEmptyString_thenThrowJSONException() throws JSONException {
    // Arrange, Act and Assert
    assertThrows(JSONException.class, () -> XML.noSpace(""));
  }

  /**
   * Test {@link XML#toJSONObject(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toJSONObject(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONObject XML.toJSONObject(String)"})
  public void testToJSONObject_whenEmptyString_thenReturnLengthIsZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, XML.toJSONObject("").length());
  }

  /**
   * Test {@link XML#toJSONObject(String)}.
   * <ul>
   *   <li>When {@code <?>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toJSONObject(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONObject XML.toJSONObject(String)"})
  public void testToJSONObject_whenLessThanSignQuestionMarkGreaterThanSign() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, XML.toJSONObject("<?>").length());
  }

  /**
   * Test {@link XML#toJSONObject(String)}.
   * <ul>
   *   <li>When {@code String}.</li>
   *   <li>Then return length is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toJSONObject(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"JSONObject XML.toJSONObject(String)"})
  public void testToJSONObject_whenString_thenReturnLengthIsZero() throws JSONException {
    // Arrange, Act and Assert
    assertEquals(0, XML.toJSONObject("String").length());
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("<HTTP-Version>https://example.org/example</HTTP-Version><Status-Code/><Reason-Phrase/>",
        XML.toString(HTTP.toJSONObject("https://example.org/example")));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(HTTP.toJSONObject(
        "{\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}"));
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.opt(Mockito.<String>any())).thenReturn(jsonObject);
    when(jsonObject2.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject2, "Tag Name"));
    verify(jsonObject2).keys();
    verify(jsonObject).keys();
    verify(jsonObject2).opt(eq(
        "{\"Request-URI\":\"\",\"Method\":\"{\\\"HTTP-Version\\\":\\\"https://example.org/example\\\",\\\"Status-Code\\\":\\\"\\\",\\\"Reason-Phrase\\\":\\\"\\\"}\",\"HTTP-Version\":\"\"}"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code content}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenArrayListAddContent() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("content");
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.opt(Mockito.<String>any())).thenReturn(mock(JSONObject.class));
    when(jsonObject.keys()).thenReturn(iteratorResult);

    // Act
    XML.toString(jsonObject, "Tag Name");

    // Assert
    verify(jsonObject).keys();
    verify(jsonObject).opt(eq("content"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code content}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then calls {@link JSONObject#opt(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenArrayListAddContent_whenNull_thenCallsOpt() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add("content");
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.opt(Mockito.<String>any())).thenReturn(mock(JSONObject.class));
    when(jsonObject.keys()).thenReturn(iteratorResult);

    // Act
    XML.toString(jsonObject, null);

    // Assert
    verify(jsonObject).keys();
    verify(jsonObject).opt(eq("content"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONArray#JSONArray()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenArrayListAddJSONArray_thenThrowJSONException() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(new JSONArray());
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.opt(Mockito.<String>any())).thenReturn(jsonObject);
    when(jsonObject2.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject2, "Tag Name"));
    verify(jsonObject2).keys();
    verify(jsonObject).keys();
    verify(jsonObject2).opt(eq("[]"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#JSONObject()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenArrayListAddJSONObject_thenThrowJSONException() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(new JSONObject());
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.opt(Mockito.<String>any())).thenReturn(jsonObject);
    when(jsonObject2.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject2, "Tag Name"));
    verify(jsonObject2).keys();
    verify(jsonObject).keys();
    verify(jsonObject2).opt(eq("{}"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenArrayListAddNull_thenThrowJSONException() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.opt(Mockito.<String>any())).thenReturn(jsonObject);
    when(jsonObject2.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject2, "Tag Name"));
    verify(jsonObject2).keys();
    verify(jsonObject).keys();
    verify(jsonObject2).opt(eq("null"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenArrayListAddNull_whenNull_thenThrowJSONException() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.opt(Mockito.<String>any())).thenReturn(jsonObject);
    when(jsonObject2.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject2, null));
    verify(jsonObject2).keys();
    verify(jsonObject).keys();
    verify(jsonObject2).opt(eq("null"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add toJSONObject {@code https://example.org/example}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenArrayListAddToJSONObjectHttpsExampleOrgExample() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(HTTP.toJSONObject("https://example.org/example"));
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.opt(Mockito.<String>any())).thenReturn(jsonObject);
    when(jsonObject2.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject2, "Tag Name"));
    verify(jsonObject2).keys();
    verify(jsonObject).keys();
    verify(jsonObject2)
        .opt(eq("{\"HTTP-Version\":\"https://example.org/example\",\"Status-Code\":\"\",\"Reason-Phrase\":\"\"}"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} iterator.</li>
   *   <li>Then return {@code <Tag Name></Tag Name>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenArrayListIterator_thenReturnTagNameTagName() throws JSONException {
    // Arrange
    JSONObject jsonObject = mock(JSONObject.class);

    ArrayList<Object> objectList = new ArrayList<>();
    when(jsonObject.keys()).thenReturn(objectList.iterator());

    // Act
    String actualToStringResult = XML.toString(jsonObject, "Tag Name");

    // Assert
    verify(jsonObject).keys();
    assertEquals("<Tag Name></Tag Name>", actualToStringResult);
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.</li>
   *   <li>When {@link JSONObject} {@link JSONObject#opt(String)} return {@link JSONArray#JSONArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenJSONArray_whenJSONObjectOptReturnJSONArray() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.opt(Mockito.<String>any())).thenReturn(new JSONArray());
    when(jsonObject.keys()).thenReturn(iteratorResult);

    // Act
    String actualToStringResult = XML.toString(jsonObject, "Tag Name");

    // Assert
    verify(jsonObject).keys();
    verify(jsonObject).opt(eq("null"));
    assertEquals("<Tag Name></Tag Name>", actualToStringResult);
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} append {@code {} and {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenJSONObjectAppendLeftCurlyBracketAndNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject3 = mock(JSONObject.class);
    when(jsonObject3.opt(Mockito.<String>any())).thenReturn(jsonObject2);
    when(jsonObject3.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject3, "Tag Name"));
    verify(jsonObject3).keys();
    verify(jsonObject2).keys();
    verify(jsonObject3).opt(eq("{\"{\":[null]}"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} append {@code {} and {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenJSONObjectAppendLeftCurlyBracketAndNull2() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", null);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject3 = mock(JSONObject.class);
    when(jsonObject3.opt(Mockito.<String>any())).thenReturn(jsonObject2);
    when(jsonObject3.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject3, "Tag Name"));
    verify(jsonObject3).keys();
    verify(jsonObject2).keys();
    verify(jsonObject3).opt(eq("{\"{\":[null]}"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} append {@code {} and ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenJSONObjectAppendLeftCurlyBracketAndTen() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", 10.0f);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject3 = mock(JSONObject.class);
    when(jsonObject3.opt(Mockito.<String>any())).thenReturn(jsonObject2);
    when(jsonObject3.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject3, "Tag Name"));
    verify(jsonObject3).keys();
    verify(jsonObject2).keys();
    verify(jsonObject3).opt(eq("{\"{\":[10]}"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code ,} is {@code 0.5}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenJSONObjectCommaIs05_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(",", 0.5d);
    jsonObject.append("{", JSONObject.NULL);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject3 = mock(JSONObject.class);
    when(jsonObject3.opt(Mockito.<String>any())).thenReturn(jsonObject2);
    when(jsonObject3.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject3, "Tag Name"));
    verify(jsonObject3).keys();
    verify(jsonObject2).keys();
    verify(jsonObject3).opt(eq("{\"{\":[null],\",\":0.5}"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code ,} is {@code false}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenJSONObjectCommaIsFalse_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(",", false);
    jsonObject.append("{", JSONObject.NULL);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject3 = mock(JSONObject.class);
    when(jsonObject3.opt(Mockito.<String>any())).thenReturn(jsonObject2);
    when(jsonObject3.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject3, "Tag Name"));
    verify(jsonObject3).keys();
    verify(jsonObject2).keys();
    verify(jsonObject3).opt(eq("{\"{\":[null],\",\":false}"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code ,} is {@code false}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenJSONObjectCommaIsFalse_thenThrowJSONException2() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("{", JSONObject.NULL);
    jsonObject.put(",", false);
    jsonObject.append("{", JSONObject.NULL);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject3 = mock(JSONObject.class);
    when(jsonObject3.opt(Mockito.<String>any())).thenReturn(jsonObject2);
    when(jsonObject3.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject3, "Tag Name"));
    verify(jsonObject3).keys();
    verify(jsonObject2).keys();
    verify(jsonObject3).opt(eq("{\"{\":[null,null],\",\":false}"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code ,} is {@link HashMap#HashMap()}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenJSONObjectCommaIsHashMap_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(",", (Map) new HashMap<>());
    jsonObject.append("{", JSONObject.NULL);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject3 = mock(JSONObject.class);
    when(jsonObject3.opt(Mockito.<String>any())).thenReturn(jsonObject2);
    when(jsonObject3.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject3, "Tag Name"));
    verify(jsonObject3).keys();
    verify(jsonObject2).keys();
    verify(jsonObject3).opt(eq("{\"{\":[null],\",\":{}}"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} increment {@code ,}.</li>
   *   <li>Then throw {@link JSONException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenJSONObjectIncrementComma_thenThrowJSONException() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.increment(",");
    jsonObject.append("{", JSONObject.NULL);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject3 = mock(JSONObject.class);
    when(jsonObject3.opt(Mockito.<String>any())).thenReturn(jsonObject2);
    when(jsonObject3.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject3, "Tag Name"));
    verify(jsonObject3).keys();
    verify(jsonObject2).keys();
    verify(jsonObject3).opt(eq("{\"{\":[null],\",\":1}"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()} {@code {} is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenJSONObjectLeftCurlyBracketIsTen() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("{", 10.0d);

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(jsonObject);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject2 = mock(JSONObject.class);
    when(jsonObject2.keys()).thenThrow(new JSONException("An error occurred"));
    JSONObject jsonObject3 = mock(JSONObject.class);
    when(jsonObject3.opt(Mockito.<String>any())).thenReturn(jsonObject2);
    when(jsonObject3.keys()).thenReturn(iteratorResult);

    // Act and Assert
    assertThrows(JSONException.class, () -> XML.toString(jsonObject3, "Tag Name"));
    verify(jsonObject3).keys();
    verify(jsonObject2).keys();
    verify(jsonObject3).opt(eq("{\"{\":10}"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code <Tag Name><null>null</null></Tag Name>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenNull_thenReturnTagNameNullNullNullTagName() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.opt(Mockito.<String>any())).thenReturn(JSONObject.NULL);
    when(jsonObject.keys()).thenReturn(iteratorResult);

    // Act
    String actualToStringResult = XML.toString(jsonObject, "Tag Name");

    // Assert
    verify(jsonObject).keys();
    verify(jsonObject).opt(eq("null"));
    assertEquals("<Tag Name><null>null</null></Tag Name>", actualToStringResult);
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then return {@code <Tag Name><null/></Tag Name>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_givenNull_thenReturnTagNameNullTagName() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.opt(Mockito.<String>any())).thenReturn(null);
    when(jsonObject.keys()).thenReturn(iteratorResult);

    // Act
    String actualToStringResult = XML.toString(jsonObject, "Tag Name");

    // Assert
    verify(jsonObject).keys();
    verify(jsonObject).opt(eq("null"));
    assertEquals("<Tag Name><null/></Tag Name>", actualToStringResult);
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Then return a string.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_thenReturnAString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("<Tag Name><HTTP-Version>https://example.org/example</HTTP-Version><Status-Code/><Reason-Phrase/></Tag"
        + " Name>", XML.toString(HTTP.toJSONObject("https://example.org/example"), "Tag Name"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>Then return {@code <Tag Name><null>&lt;/</null></Tag Name>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_thenReturnTagNameNullLtNullTagName() throws JSONException {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);
    Iterator<Object> iteratorResult = objectList.iterator();
    JSONObject jsonObject = mock(JSONObject.class);
    when(jsonObject.opt(Mockito.<String>any())).thenReturn("</");
    when(jsonObject.keys()).thenReturn(iteratorResult);

    // Act
    String actualToStringResult = XML.toString(jsonObject, "Tag Name");

    // Assert
    verify(jsonObject).keys();
    verify(jsonObject).opt(eq("null"));
    assertEquals("<Tag Name><null>&lt;/</null></Tag Name>", actualToStringResult);
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_whenJSONArray_thenReturnEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", XML.toString(new JSONArray(), "Tag Name"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.</li>
   *   <li>Then return {@code <Tag Name></Tag Name>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_whenJSONObject_thenReturnTagNameTagName() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("<Tag Name></Tag Name>", XML.toString(new JSONObject(), "Tag Name"));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code "null"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"null\"", XML.toString(null, null));
  }

  /**
   * Test {@link XML#toString(Object, String)} with {@code o}, {@code tagName}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code <Tag Name>null</Tag Name>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object, String)"})
  public void testToStringWithOTagName_whenNull_thenReturnTagNameNullTagName() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("<Tag Name>null</Tag Name>", XML.toString(JSONObject.NULL, "Tag Name"));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>Given {@code content}.</li>
   *   <li>When {@link JSONObject#JSONObject()} append {@code content} and {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_givenContent_whenJSONObjectAppendContentAndNull_thenReturnNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("content", JSONObject.NULL);

    // Act and Assert
    assertEquals("null", XML.toString(jsonObject));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>Then return {@code <Key/>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_givenEmptyString_thenReturnKey() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("Key", "");

    // Act and Assert
    assertEquals("<Key/>", XML.toString(jsonObject));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>When {@link JSONArray#JSONArray()} {@code false}.</li>
   *   <li>Then return {@code <array>false</array>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_givenFalse_whenJSONArrayFalse_thenReturnArrayFalseArray() throws JSONException {
    // Arrange
    JSONArray jsonArray = new JSONArray();
    jsonArray.put(false);

    // Act and Assert
    assertEquals("<array>false</array>", XML.toString(jsonArray));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@link JSONObject#NULL} is {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code <Key><null/></Key>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_givenHashMapNullIsNull_thenReturnKeyNullKey() throws JSONException {
    // Arrange
    HashMap<Object, Object> value = new HashMap<>();
    value.put(JSONObject.NULL, JSONObject.NULL);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Key", (Map) value);

    // Act and Assert
    assertEquals("<Key><null/></Key>", XML.toString(jsonObject));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>When {@link JSONObject#JSONObject()} {@code Key} is {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code <Key></Key>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_givenHashMap_whenJSONObjectKeyIsHashMap_thenReturnKeyKey() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Key", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("<Key></Key>", XML.toString(jsonObject));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.</li>
   *   <li>When {@link JSONObject#JSONObject()} append {@code Key} and {@link JSONArray#JSONArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_givenJSONArray_whenJSONObjectAppendKeyAndJSONArray() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("Key", new JSONArray());

    // Act and Assert
    assertEquals("<Key></Key>", XML.toString(jsonObject));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link JSONObject#JSONObject()} append {@code Key} and {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code <Key>null</Key>}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_givenNull_whenJSONObjectAppendKeyAndNull_thenReturnKeyNullKey() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("Key", JSONObject.NULL);

    // Act and Assert
    assertEquals("<Key>null</Key>", XML.toString(jsonObject));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>Then return {@code {}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_thenReturnLeftCurlyBracketRightCurlyBracket() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("content", (Map) new HashMap<>());

    // Act and Assert
    assertEquals("{}", XML.toString(jsonObject));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>When {@code -->}.</li>
   *   <li>Then return {@code "--&gt;"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_whenDashDashGreaterThanSign_thenReturnGt() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"--&gt;\"", XML.toString("-->"));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>When {@link JSONArray#JSONArray()}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_whenJSONArray_thenReturnEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", XML.toString(new JSONArray()));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()} append {@code content} and {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_whenJSONObjectAppendContentAndNull_thenReturnNullNull() throws JSONException {
    // Arrange
    JSONObject jsonObject = new JSONObject();
    jsonObject.append("content", JSONObject.NULL);
    jsonObject.append("content", JSONObject.NULL);

    // Act and Assert
    assertEquals("null\nnull", XML.toString(jsonObject));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>When {@link JSONObject#JSONObject()}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_whenJSONObject_thenReturnEmptyString() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("", XML.toString(new JSONObject()));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>When {@code <}.</li>
   *   <li>Then return {@code "&lt;"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_whenLessThanSign_thenReturnLt() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"&lt;\"", XML.toString("<"));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code "null"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_whenNull_thenReturnNull() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"null\"", XML.toString(JSONObject.NULL));
  }

  /**
   * Test {@link XML#toString(Object)} with {@code o}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code "null"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link XML#toString(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String XML.toString(Object)"})
  public void testToStringWithO_whenNull_thenReturnNull2() throws JSONException {
    // Arrange, Act and Assert
    assertEquals("\"null\"", XML.toString(null));
  }
}
