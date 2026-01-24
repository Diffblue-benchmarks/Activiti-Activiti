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
package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ContentDiffblueTest {
  /**
   * Test {@link Content#Content(String, boolean, List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then return Tags is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Content#Content(String, boolean, List)}
   */
  @Test
  @DisplayName(
      "Test new Content(String, boolean, List); given '42'; when ArrayList() add '42'; then return Tags is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Content.<init>(String, boolean, List)"})
  void testNewContent_given42_whenArrayListAdd42_thenReturnTagsIsArrayList() {
    // Arrange
    ArrayList<String> tags = new ArrayList<>();
    tags.add("42");
    tags.add("foo");

    // Act
    Content actualContent = new Content("Not all who wander are lost", true, tags);

    // Assert
    assertEquals("Not all who wander are lost", actualContent.getBody());
    assertTrue(actualContent.isApproved());
    assertSame(tags, actualContent.getTags());
  }

  /**
   * Test {@link Content#Content(String, boolean, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   *   <li>Then return Tags is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Content#Content(String, boolean, List)}
   */
  @Test
  @DisplayName(
      "Test new Content(String, boolean, List); given 'foo'; when ArrayList() add 'foo'; then return Tags is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Content.<init>(String, boolean, List)"})
  void testNewContent_givenFoo_whenArrayListAddFoo_thenReturnTagsIsArrayList() {
    // Arrange
    ArrayList<String> tags = new ArrayList<>();
    tags.add("foo");

    // Act
    Content actualContent = new Content("Not all who wander are lost", true, tags);

    // Assert
    assertEquals("Not all who wander are lost", actualContent.getBody());
    assertTrue(actualContent.isApproved());
    assertSame(tags, actualContent.getTags());
  }

  /**
   * Test {@link Content#Content(String, boolean, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Tags Empty.
   * </ul>
   *
   * <p>Method under test: {@link Content#Content(String, boolean, List)}
   */
  @Test
  @DisplayName("Test new Content(String, boolean, List); when ArrayList(); then return Tags Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Content.<init>(String, boolean, List)"})
  void testNewContent_whenArrayList_thenReturnTagsEmpty() {
    // Arrange and Act
    Content actualContent = new Content("Not all who wander are lost", true, new ArrayList<>());

    // Assert
    assertEquals("Not all who wander are lost", actualContent.getBody());
    assertTrue(actualContent.getTags().isEmpty());
    assertTrue(actualContent.isApproved());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Content#setApproved(boolean)}
   *   <li>{@link Content#setBody(String)}
   *   <li>{@link Content#setTags(List)}
   *   <li>{@link Content#toString()}
   *   <li>{@link Content#getBody()}
   *   <li>{@link Content#getTags()}
   *   <li>{@link Content#isApproved()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String Content.getBody()",
    "List Content.getTags()",
    "boolean Content.isApproved()",
    "void Content.setApproved(boolean)",
    "void Content.setBody(String)",
    "void Content.setTags(List)",
    "String Content.toString()"
  })
  void testGettersAndSetters() {
    // Arrange
    Content content = new Content("Not all who wander are lost", true, new ArrayList<>());

    // Act
    content.setApproved(true);
    content.setBody("Not all who wander are lost");
    ArrayList<String> tags = new ArrayList<>();
    content.setTags(tags);
    String actualToStringResult = content.toString();
    String actualBody = content.getBody();
    List<String> actualTags = content.getTags();
    boolean actualIsApprovedResult = content.isApproved();

    // Assert
    assertEquals(
        "Content{body='Not all who wander are lost', approved=true, tags=[]}",
        actualToStringResult);
    assertEquals("Not all who wander are lost", actualBody);
    assertTrue(actualTags.isEmpty());
    assertTrue(actualIsApprovedResult);
    assertSame(tags, actualTags);
  }
}
