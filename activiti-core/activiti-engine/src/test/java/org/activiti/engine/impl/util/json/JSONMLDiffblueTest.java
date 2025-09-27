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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class JSONMLDiffblueTest {
  /**
   * Test {@link JSONML#toJSONArray(XMLTokener)} with {@code x}.
   *
   * <p>Method under test: {@link JSONML#toJSONArray(XMLTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONML.toJSONArray(XMLTokener)"})
  public void testToJSONArrayWithX() throws JSONException {
    // Arrange
    XMLTokener x = mock(XMLTokener.class);
    when(x.nextToken()).thenThrow(new JSONException("An error occurred"));
    when(x.nextContent()).thenReturn(XML.LT);

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toJSONArray(x));
    verify(x).nextContent();
    verify(x).nextToken();
  }

  /**
   * Test {@link JSONML#toJSONArray(XMLTokener)} with {@code x}.
   *
   * <p>Method under test: {@link JSONML#toJSONArray(XMLTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONML.toJSONArray(XMLTokener)"})
  public void testToJSONArrayWithX2() throws JSONException {
    // Arrange
    XMLTokener x = mock(XMLTokener.class);
    when(x.next()).thenThrow(new JSONException("An error occurred"));
    when(x.nextToken()).thenReturn(XML.BANG);
    when(x.nextContent()).thenReturn(XML.LT);

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toJSONArray(x));
    verify(x).next();
    verify(x).nextContent();
    verify(x).nextToken();
  }

  /**
   * Test {@link JSONML#toJSONArray(XMLTokener)} with {@code x}.
   *
   * <ul>
   *   <li>Given {@link XML#BANG}.
   *   <li>When {@link XMLTokener} {@link XMLTokener#nextToken()} return {@link XML#BANG}.
   *   <li>Then calls {@link XMLTokener#next()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toJSONArray(XMLTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONML.toJSONArray(XMLTokener)"})
  public void testToJSONArrayWithX_givenBang_whenXMLTokenerNextTokenReturnBang_thenCallsNext()
      throws JSONException {
    // Arrange
    XMLTokener x = mock(XMLTokener.class);
    when(x.skipPast(Mockito.<String>any())).thenThrow(new JSONException("An error occurred"));
    when(x.next()).thenReturn('-');
    when(x.nextToken()).thenReturn(XML.BANG);
    when(x.nextContent()).thenReturn(XML.LT);

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toJSONArray(x));
    verify(x, atLeast(1)).next();
    verify(x).nextContent();
    verify(x).nextToken();
    verify(x).skipPast("-->");
  }

  /**
   * Test {@link JSONML#toJSONArray(XMLTokener)} with {@code x}.
   *
   * <ul>
   *   <li>Given {@link XML#QUEST}.
   *   <li>Then calls {@link XMLTokener#skipPast(String)}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toJSONArray(XMLTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONML.toJSONArray(XMLTokener)"})
  public void testToJSONArrayWithX_givenQuest_thenCallsSkipPast() throws JSONException {
    // Arrange
    XMLTokener x = mock(XMLTokener.class);
    when(x.skipPast(Mockito.<String>any())).thenThrow(new JSONException("An error occurred"));
    when(x.nextToken()).thenReturn(XML.QUEST);
    when(x.nextContent()).thenReturn(XML.LT);

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toJSONArray(x));
    verify(x).nextContent();
    verify(x).nextToken();
    verify(x).skipPast("?>");
  }

  /**
   * Test {@link JSONML#toJSONArray(XMLTokener)} with {@code x}.
   *
   * <ul>
   *   <li>Given {@link XML#SLASH}.
   *   <li>When {@link XMLTokener} {@link XMLTokener#nextToken()} return {@link XML#SLASH}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toJSONArray(XMLTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONArray JSONML.toJSONArray(XMLTokener)"})
  public void testToJSONArrayWithX_givenSlash_whenXMLTokenerNextTokenReturnSlash()
      throws JSONException {
    // Arrange
    XMLTokener x = mock(XMLTokener.class);
    when(x.nextToken()).thenReturn(XML.SLASH);
    when(x.nextContent()).thenReturn(XML.LT);

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toJSONArray(x));
    verify(x).nextContent();
    verify(x, atLeast(1)).nextToken();
  }

  /**
   * Test {@link JSONML#toJSONObject(XMLTokener)} with {@code x}.
   *
   * <p>Method under test: {@link JSONML#toJSONObject(XMLTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONML.toJSONObject(XMLTokener)"})
  public void testToJSONObjectWithX() throws JSONException {
    // Arrange
    XMLTokener x = mock(XMLTokener.class);
    when(x.nextToken()).thenThrow(new JSONException("An error occurred"));
    when(x.nextContent()).thenReturn(XML.LT);

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toJSONObject(x));
    verify(x).nextContent();
    verify(x).nextToken();
  }

  /**
   * Test {@link JSONML#toJSONObject(XMLTokener)} with {@code x}.
   *
   * <p>Method under test: {@link JSONML#toJSONObject(XMLTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONML.toJSONObject(XMLTokener)"})
  public void testToJSONObjectWithX2() throws JSONException {
    // Arrange
    XMLTokener x = mock(XMLTokener.class);
    when(x.next()).thenThrow(new JSONException("An error occurred"));
    when(x.nextToken()).thenReturn(XML.BANG);
    when(x.nextContent()).thenReturn(XML.LT);

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toJSONObject(x));
    verify(x).next();
    verify(x).nextContent();
    verify(x).nextToken();
  }

  /**
   * Test {@link JSONML#toJSONObject(XMLTokener)} with {@code x}.
   *
   * <ul>
   *   <li>Given {@link XML#BANG}.
   *   <li>When {@link XMLTokener} {@link XMLTokener#nextToken()} return {@link XML#BANG}.
   *   <li>Then calls {@link XMLTokener#next()}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toJSONObject(XMLTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONML.toJSONObject(XMLTokener)"})
  public void testToJSONObjectWithX_givenBang_whenXMLTokenerNextTokenReturnBang_thenCallsNext()
      throws JSONException {
    // Arrange
    XMLTokener x = mock(XMLTokener.class);
    when(x.skipPast(Mockito.<String>any())).thenThrow(new JSONException("An error occurred"));
    when(x.next()).thenReturn('-');
    when(x.nextToken()).thenReturn(XML.BANG);
    when(x.nextContent()).thenReturn(XML.LT);

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toJSONObject(x));
    verify(x, atLeast(1)).next();
    verify(x).nextContent();
    verify(x).nextToken();
    verify(x).skipPast("-->");
  }

  /**
   * Test {@link JSONML#toJSONObject(XMLTokener)} with {@code x}.
   *
   * <ul>
   *   <li>Given {@link XML#QUEST}.
   *   <li>Then calls {@link XMLTokener#skipPast(String)}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toJSONObject(XMLTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONML.toJSONObject(XMLTokener)"})
  public void testToJSONObjectWithX_givenQuest_thenCallsSkipPast() throws JSONException {
    // Arrange
    XMLTokener x = mock(XMLTokener.class);
    when(x.skipPast(Mockito.<String>any())).thenThrow(new JSONException("An error occurred"));
    when(x.nextToken()).thenReturn(XML.QUEST);
    when(x.nextContent()).thenReturn(XML.LT);

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toJSONObject(x));
    verify(x).nextContent();
    verify(x).nextToken();
    verify(x).skipPast("?>");
  }

  /**
   * Test {@link JSONML#toJSONObject(XMLTokener)} with {@code x}.
   *
   * <ul>
   *   <li>Given {@link XML#SLASH}.
   *   <li>When {@link XMLTokener} {@link XMLTokener#nextToken()} return {@link XML#SLASH}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toJSONObject(XMLTokener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"JSONObject JSONML.toJSONObject(XMLTokener)"})
  public void testToJSONObjectWithX_givenSlash_whenXMLTokenerNextTokenReturnSlash()
      throws JSONException {
    // Arrange
    XMLTokener x = mock(XMLTokener.class);
    when(x.nextToken()).thenReturn(XML.SLASH);
    when(x.nextContent()).thenReturn(XML.LT);

    // Act and Assert
    assertThrows(JSONException.class, () -> JSONML.toJSONObject(x));
    verify(x).nextContent();
    verify(x, atLeast(1)).nextToken();
  }

  /**
   * Test {@link JSONML#toString(JSONArray)} with {@code ja}.
   *
   * <p>Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONArray)"})
  public void testToStringWithJa() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Collection) new ArrayList<>());
    ja.put(true);

    // Act and Assert
    assertEquals("<[]></[]>", JSONML.toString(ja));
  }

  /**
   * Test {@link JSONML#toString(JSONArray)} with {@code ja}.
   *
   * <p>Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONArray)"})
  public void testToStringWithJa2() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put((Map) new HashMap<>());
    ja.put(true);

    // Act and Assert
    assertEquals("<{}></{}>", JSONML.toString(ja));
  }

  /**
   * Test {@link JSONML#toString(JSONArray)} with {@code ja}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>Then return {@code <null/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONArray)"})
  public void testToStringWithJa_givenOne_thenReturnNull() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(1, (Map) new HashMap<>());

    // Act and Assert
    assertEquals("<null/>", JSONML.toString(ja));
  }

  /**
   * Test {@link JSONML#toString(JSONArray)} with {@code ja}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return {@code <true/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONArray)"})
  public void testToStringWithJa_givenTrue_thenReturnTrue() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(true);

    // Act and Assert
    assertEquals("<true/>", JSONML.toString(ja));
  }

  /**
   * Test {@link JSONML#toString(JSONArray)} with {@code ja}.
   *
   * <ul>
   *   <li>Then return {@code <false></false>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONArray)"})
  public void testToStringWithJa_thenReturnFalseFalse() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(false);
    ja.put(true);

    // Act and Assert
    assertEquals("<false></false>", JSONML.toString(ja));
  }

  /**
   * Test {@link JSONML#toString(JSONArray)} with {@code ja}.
   *
   * <ul>
   *   <li>Then return {@code <null></null>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONArray)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONArray)"})
  public void testToStringWithJa_thenReturnNullNull() throws JSONException {
    // Arrange
    JSONArray ja = new JSONArray("[]");
    ja.put(4, false);
    ja.put(true);

    // Act and Assert
    assertEquals("<null></null>", JSONML.toString(ja));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given {@code 0.5}.
   *   <li>Then return {@code <[0.5] name="" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_given05_thenReturn05NameValue() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", 0.5d);

    // Act and Assert
    assertEquals("<[0.5] name=\"\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then return {@code <[&quot;42&quot;] name="" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_given42_thenReturnQuot42QuotNameValue() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", "42");

    // Act and Assert
    assertEquals("<[&quot;42&quot;] name=\"\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given {@code childNodes}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_givenChildNodes() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.put("childNodes", (Map) new HashMap<>());
    jo.append("tagName", JSONObject.NULL);

    // Act and Assert
    assertEquals("<[null] name=\"\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given {@code ,}.
   *   <li>Then return {@code <[null] name="" ,="{}" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_givenComma_thenReturnNullNameValue() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.put(",", (Map) new HashMap<>());
    jo.append("tagName", JSONObject.NULL);

    // Act and Assert
    assertEquals("<[null] name=\"\" ,=\"{}\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given forty-two.
   *   <li>Then return {@code <[42] name="" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_givenFortyTwo_thenReturn42NameValue() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", 42);

    // Act and Assert
    assertEquals("<[42] name=\"\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given {@link JSONArray#JSONArray()}.
   *   <li>Then return {@code <[[]] name="" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_givenJSONArray_thenReturnNameValue() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", new JSONArray());

    // Act and Assert
    assertEquals("<[[]] name=\"\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#JSONObject()}.
   *   <li>Then return {@code <[{}] name="" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_givenJSONObject_thenReturnNameValue() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", new JSONObject());

    // Act and Assert
    assertEquals("<[{}] name=\"\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_givenNull() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", null);

    // Act and Assert
    assertEquals("<[null] name=\"\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given {@code &quot;}.
   *   <li>Then return {@code <[null,&quot;42&quot;] name="" &amp;quot;="[null]" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_givenQuot_thenReturnNullQuot42QuotNameAmpQuotNullValue()
      throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", JSONObject.NULL);
    jo.append("&quot;", JSONObject.NULL);
    jo.append("tagName", "42");

    // Act and Assert
    assertEquals(
        "<[null,&quot;42&quot;] name=\"\" &amp;quot;=\"[null]\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given {@code &quot;}.
   *   <li>Then return {@code <[&quot;42&quot;] name="" &amp;quot;="[null]" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_givenQuot_thenReturnQuot42QuotNameAmpQuotNullValue()
      throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("&quot;", JSONObject.NULL);
    jo.append("tagName", "42");

    // Act and Assert
    assertEquals(
        "<[&quot;42&quot;] name=\"\" &amp;quot;=\"[null]\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>Then return {@code <[10] name="" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_givenTen_thenReturn10NameValue() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", 10.0d);

    // Act and Assert
    assertEquals("<[10] name=\"\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>Then return {@code <[10] name="" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_givenTen_thenReturn10NameValue2() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", 10.0f);

    // Act and Assert
    assertEquals("<[10] name=\"\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given toJSONObject {@code https://example.org/example}.
   *   <li>Then return a string.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_givenToJSONObjectHttpsExampleOrgExample_thenReturnAString()
      throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", HTTP.toJSONObject("https://example.org/example"));

    // Act and Assert
    assertEquals(
        "<[{&quot;HTTP-Version&quot;:&quot;https://example.org/example&quot;,&quot;Status-Code&quot;:&quot;"
            + "&quot;,&quot;Reason-Phrase&quot;:&quot;&quot;}] name=\"\" value=\"\"/>",
        JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return {@code <[true] name="" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_givenTrue_thenReturnTrueNameValue() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", true);

    // Act and Assert
    assertEquals("<[true] name=\"\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Then return {@code <[null] name="" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_thenReturnNullNameValue() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", JSONObject.NULL);

    // Act and Assert
    assertEquals("<[null] name=\"\" value=\"\"/>", JSONML.toString(jo));
  }

  /**
   * Test {@link JSONML#toString(JSONObject)} with {@code jo}.
   *
   * <ul>
   *   <li>Then return {@code <[&quot;&quot;] name="" value=""/>}.
   * </ul>
   *
   * <p>Method under test: {@link JSONML#toString(JSONObject)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String JSONML.toString(JSONObject)"})
  public void testToStringWithJo_thenReturnQuotQuotNameValue() throws JSONException {
    // Arrange
    JSONObject jo = Cookie.toJSONObject("=;");
    jo.append("tagName", "");

    // Act and Assert
    assertEquals("<[&quot;&quot;] name=\"\" value=\"\"/>", JSONML.toString(jo));
  }
}
