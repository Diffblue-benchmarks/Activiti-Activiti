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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TaskWithFieldExtensionsDiffblueTest {
  /**
   * Test {@link TaskWithFieldExtensions#getFieldExtensions()}.
   * <p>
   * Method under test: {@link TaskWithFieldExtensions#getFieldExtensions()}
   */
  @Test
  public void testGetFieldExtensions() {
    // Arrange, Act and Assert
    assertTrue((new SendTask()).getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link TaskWithFieldExtensions#setFieldExtensions(List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskWithFieldExtensions#setFieldExtensions(List)}
   */
  @Test
  public void testSetFieldExtensions_givenFieldExtension_whenArrayListAddFieldExtension() {
    // Arrange
    SendTask sendTask = new SendTask();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    // Act
    sendTask.setFieldExtensions(fieldExtensions);

    // Assert
    assertSame(fieldExtensions, sendTask.getFieldExtensions());
  }

  /**
   * Test {@link TaskWithFieldExtensions#setFieldExtensions(List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskWithFieldExtensions#setFieldExtensions(List)}
   */
  @Test
  public void testSetFieldExtensions_givenFieldExtension_whenArrayListAddFieldExtension2() {
    // Arrange
    SendTask sendTask = new SendTask();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());
    fieldExtensions.add(new FieldExtension());

    // Act
    sendTask.setFieldExtensions(fieldExtensions);

    // Assert
    assertSame(fieldExtensions, sendTask.getFieldExtensions());
  }

  /**
   * Test {@link TaskWithFieldExtensions#setFieldExtensions(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskWithFieldExtensions#setFieldExtensions(List)}
   */
  @Test
  public void testSetFieldExtensions_whenArrayList() {
    // Arrange
    SendTask sendTask = new SendTask();
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();

    // Act
    sendTask.setFieldExtensions(fieldExtensions);

    // Assert
    assertSame(fieldExtensions, sendTask.getFieldExtensions());
  }
}
