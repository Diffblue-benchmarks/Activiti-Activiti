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
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.task.model.payloads.CandidateGroupsPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CandidateGroupsPayloadBuilderDiffblueTest {
  /**
   * Test {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); given '42'; when ArrayList() add '42'")
  void testWithCandidateGroups_given42_whenArrayListAdd42() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult = TaskPayloadBuilder.addCandidateGroups();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("foo");

    // Act and Assert
    assertSame(candidateGroups,
        addCandidateGroupsResult.withCandidateGroups(candidateGroups).build().getCandidateGroups());
    assertSame(candidateGroups, addCandidateGroupsResult.build().getCandidateGroups());
  }

  /**
   * Test {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return build CandidateGroups is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); given 'foo'; then return build CandidateGroups is ArrayList()")
  void testWithCandidateGroups_givenFoo_thenReturnBuildCandidateGroupsIsArrayList() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult = TaskPayloadBuilder.addCandidateGroups();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("foo");

    // Act and Assert
    assertSame(candidateGroups,
        addCandidateGroupsResult.withCandidateGroups(candidateGroups).build().getCandidateGroups());
    assertSame(candidateGroups, addCandidateGroupsResult.build().getCandidateGroups());
  }

  /**
   * Test {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return build CandidateGroups Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); when ArrayList(); then return build CandidateGroups Empty")
  void testWithCandidateGroups_whenArrayList_thenReturnBuildCandidateGroupsEmpty() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult = TaskPayloadBuilder.addCandidateGroups();
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act and Assert
    assertTrue(addCandidateGroupsResult.withCandidateGroups(candidateGroups).build().getCandidateGroups().isEmpty());
    assertSame(candidateGroups, addCandidateGroupsResult.build().getCandidateGroups());
  }

  /**
   * Test {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return addCandidateGroups.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); when 'null'; then return addCandidateGroups")
  void testWithCandidateGroups_whenNull_thenReturnAddCandidateGroups() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult = TaskPayloadBuilder.addCandidateGroups();

    // Act and Assert
    assertSame(addCandidateGroupsResult, addCandidateGroupsResult.withCandidateGroups(null));
  }

  /**
   * Test {@link CandidateGroupsPayloadBuilder#withCandidateGroup(String)}.
   * <p>
   * Method under test:
   * {@link CandidateGroupsPayloadBuilder#withCandidateGroup(String)}
   */
  @Test
  @DisplayName("Test withCandidateGroup(String)")
  void testWithCandidateGroup() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult = TaskPayloadBuilder.addCandidateGroups();

    // Act and Assert
    assertSame(addCandidateGroupsResult, addCandidateGroupsResult.withCandidateGroup("2020-03-01"));
  }

  /**
   * Test {@link CandidateGroupsPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CandidateGroupsPayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link CandidateGroupsPayloadBuilder}
   *   <li>{@link CandidateGroupsPayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange
    CandidateGroupsPayloadBuilder candidateGroupsPayloadBuilder = new CandidateGroupsPayloadBuilder();
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act
    CandidateGroupsPayload actualBuildResult = candidateGroupsPayloadBuilder.withCandidateGroups(candidateGroups)
        .withTaskId("42")
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getTaskId());
    List<String> candidateGroups2 = actualBuildResult.getCandidateGroups();
    assertTrue(candidateGroups2.isEmpty());
    assertSame(candidateGroups, candidateGroups2);
  }
}
