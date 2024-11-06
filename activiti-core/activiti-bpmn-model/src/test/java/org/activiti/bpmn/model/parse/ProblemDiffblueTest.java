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
package org.activiti.bpmn.model.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.junit.Test;

public class ProblemDiffblueTest {
  /**
   * Test {@link Problem#Problem(String, String, int, int)}.
   * <p>
   * Method under test: {@link Problem#Problem(String, String, int, int)}
   */
  @Test
  public void testNewProblem() {
    // Arrange and Act
    Problem actualProblem = new Problem("An error occurred", "Local Name", 2, 10);

    // Assert
    assertEquals("An error occurred", actualProblem.errorMessage);
    assertEquals("Local Name", actualProblem.resource);
    assertEquals(10, actualProblem.column);
    assertEquals(2, actualProblem.line);
  }

  /**
   * Test {@link Problem#Problem(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link Problem#Problem(String, GraphicInfo)}
   */
  @Test
  public void testNewProblem2() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    Problem actualProblem = new Problem("An error occurred", graphicInfo);

    // Assert
    assertEquals("An error occurred", actualProblem.errorMessage);
    assertNull(actualProblem.resource);
    assertEquals(10, actualProblem.column);
    assertEquals(10, actualProblem.line);
  }

  /**
   * Test {@link Problem#Problem(String, BaseElement)}.
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return {@link Problem#errorMessage} is
   * {@code An error occurred}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Problem#Problem(String, BaseElement)}
   */
  @Test
  public void testNewProblem_whenActivitiListener_thenReturnErrorMessageIsAnErrorOccurred() {
    // Arrange and Act
    Problem actualProblem = new Problem("An error occurred", new ActivitiListener());

    // Assert
    assertEquals("An error occurred", actualProblem.errorMessage);
    assertNull(actualProblem.resource);
    assertEquals(0, actualProblem.column);
    assertEquals(0, actualProblem.line);
  }

  /**
   * Test {@link Problem#toString()}.
   * <ul>
   *   <li>Then return {@code An error occurred | line 2 | column 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Problem#toString()}
   */
  @Test
  public void testToString_thenReturnAnErrorOccurredLine2Column10() {
    // Arrange, Act and Assert
    assertEquals("An error occurred | line 2 | column 10", (new Problem("An error occurred", null, 2, 10)).toString());
  }

  /**
   * Test {@link Problem#toString()}.
   * <ul>
   *   <li>Then return
   * {@code An error occurred | Local Name | line 2 | column 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Problem#toString()}
   */
  @Test
  public void testToString_thenReturnAnErrorOccurredLocalNameLine2Column10() {
    // Arrange, Act and Assert
    assertEquals("An error occurred | Local Name | line 2 | column 10",
        (new Problem("An error occurred", "Local Name", 2, 10)).toString());
  }
}
