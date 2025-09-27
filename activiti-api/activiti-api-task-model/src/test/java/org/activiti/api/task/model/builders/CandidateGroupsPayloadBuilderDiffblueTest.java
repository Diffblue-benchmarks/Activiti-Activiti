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
package org.activiti.api.task.model.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.task.model.payloads.CandidateGroupsPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CandidateGroupsPayloadBuilderDiffblueTest {
  /**
   * Test {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); given '42'; when ArrayList() add '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CandidateGroupsPayloadBuilder CandidateGroupsPayloadBuilder.withCandidateGroups(List)"
  })
  void testWithCandidateGroups_given42_whenArrayListAdd42() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult =
        TaskPayloadBuilder.addCandidateGroups();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("foo");

    // Act
    CandidateGroupsPayloadBuilder actualWithCandidateGroupsResult =
        addCandidateGroupsResult.withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, addCandidateGroupsResult.build().getCandidateGroups());
    assertSame(addCandidateGroupsResult, actualWithCandidateGroupsResult);
  }

  /**
   * Test {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); given 'foo'; when ArrayList() add 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CandidateGroupsPayloadBuilder CandidateGroupsPayloadBuilder.withCandidateGroups(List)"
  })
  void testWithCandidateGroups_givenFoo_whenArrayListAddFoo() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult =
        TaskPayloadBuilder.addCandidateGroups();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("foo");

    // Act
    CandidateGroupsPayloadBuilder actualWithCandidateGroupsResult =
        addCandidateGroupsResult.withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, addCandidateGroupsResult.build().getCandidateGroups());
    assertSame(addCandidateGroupsResult, actualWithCandidateGroupsResult);
  }

  /**
   * Test {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CandidateGroupsPayloadBuilder CandidateGroupsPayloadBuilder.withCandidateGroups(List)"
  })
  void testWithCandidateGroups_whenArrayList() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult =
        TaskPayloadBuilder.addCandidateGroups();
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act
    CandidateGroupsPayloadBuilder actualWithCandidateGroupsResult =
        addCandidateGroupsResult.withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, addCandidateGroupsResult.build().getCandidateGroups());
    assertSame(addCandidateGroupsResult, actualWithCandidateGroupsResult);
  }

  /**
   * Test {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CandidateGroupsPayloadBuilder CandidateGroupsPayloadBuilder.withCandidateGroups(List)"
  })
  void testWithCandidateGroups_whenNull() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult =
        TaskPayloadBuilder.addCandidateGroups();

    // Act
    CandidateGroupsPayloadBuilder actualWithCandidateGroupsResult =
        addCandidateGroupsResult.withCandidateGroups(null);

    // Assert
    assertSame(addCandidateGroupsResult, actualWithCandidateGroupsResult);
  }

  /**
   * Test {@link CandidateGroupsPayloadBuilder#withCandidateGroup(String)}.
   *
   * <p>Method under test: {@link CandidateGroupsPayloadBuilder#withCandidateGroup(String)}
   */
  @Test
  @DisplayName("Test withCandidateGroup(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CandidateGroupsPayloadBuilder CandidateGroupsPayloadBuilder.withCandidateGroup(String)"
  })
  void testWithCandidateGroup() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult =
        TaskPayloadBuilder.addCandidateGroups();

    // Act
    CandidateGroupsPayloadBuilder actualWithCandidateGroupResult =
        addCandidateGroupsResult.withCandidateGroup("2020-03-01");

    // Assert
    assertSame(addCandidateGroupsResult, actualWithCandidateGroupResult);
  }

  /**
   * Test {@link CandidateGroupsPayloadBuilder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CandidateGroupsPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link CandidateGroupsPayloadBuilder}
   *   <li>{@link CandidateGroupsPayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CandidateGroupsPayloadBuilder.<init>()",
    "CandidateGroupsPayload CandidateGroupsPayloadBuilder.build()",
    "CandidateGroupsPayloadBuilder CandidateGroupsPayloadBuilder.withTaskId(String)"
  })
  void testBuild() {
    // Arrange and Act
    CandidateGroupsPayloadBuilder actualCandidateGroupsPayloadBuilder =
        new CandidateGroupsPayloadBuilder();
    ArrayList<String> candidateGroups = new ArrayList<>();
    CandidateGroupsPayload actualCandidateGroupsPayload =
        actualCandidateGroupsPayloadBuilder
            .withCandidateGroups(candidateGroups)
            .withTaskId("42")
            .build();

    // Assert
    assertEquals("42", actualCandidateGroupsPayload.getTaskId());
    List<String> candidateGroups2 = actualCandidateGroupsPayload.getCandidateGroups();
    assertTrue(candidateGroups2.isEmpty());
    assertSame(candidateGroups, candidateGroups2);
  }
}
