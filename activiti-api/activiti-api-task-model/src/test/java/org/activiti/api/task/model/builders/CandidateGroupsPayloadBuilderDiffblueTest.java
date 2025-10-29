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
import org.junit.jupiter.api.Test;

class CandidateGroupsPayloadBuilderDiffblueTest {
  /**
   * Method under test:
   * {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  void testWithCandidateGroups() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult = TaskPayloadBuilder.addCandidateGroups();
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act
    CandidateGroupsPayloadBuilder actualWithCandidateGroupsResult = addCandidateGroupsResult
        .withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, addCandidateGroupsResult.build().getCandidateGroups());
    assertSame(addCandidateGroupsResult, actualWithCandidateGroupsResult);
  }

  /**
   * Method under test:
   * {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  void testWithCandidateGroups2() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult = TaskPayloadBuilder.addCandidateGroups();

    // Act and Assert
    assertSame(addCandidateGroupsResult, addCandidateGroupsResult.withCandidateGroups(null));
  }

  /**
   * Method under test:
   * {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  void testWithCandidateGroups3() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult = TaskPayloadBuilder.addCandidateGroups();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("foo");

    // Act
    CandidateGroupsPayloadBuilder actualWithCandidateGroupsResult = addCandidateGroupsResult
        .withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, addCandidateGroupsResult.build().getCandidateGroups());
    assertSame(addCandidateGroupsResult, actualWithCandidateGroupsResult);
  }

  /**
   * Method under test:
   * {@link CandidateGroupsPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  void testWithCandidateGroups4() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult = TaskPayloadBuilder.addCandidateGroups();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("foo");

    // Act
    CandidateGroupsPayloadBuilder actualWithCandidateGroupsResult = addCandidateGroupsResult
        .withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, addCandidateGroupsResult.build().getCandidateGroups());
    assertSame(addCandidateGroupsResult, actualWithCandidateGroupsResult);
  }

  /**
   * Method under test:
   * {@link CandidateGroupsPayloadBuilder#withCandidateGroup(String)}
   */
  @Test
  void testWithCandidateGroup() {
    // Arrange
    CandidateGroupsPayloadBuilder addCandidateGroupsResult = TaskPayloadBuilder.addCandidateGroups();

    // Act and Assert
    assertSame(addCandidateGroupsResult, addCandidateGroupsResult.withCandidateGroup("2020-03-01"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CandidateGroupsPayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link CandidateGroupsPayloadBuilder}
   *   <li>{@link CandidateGroupsPayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
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
