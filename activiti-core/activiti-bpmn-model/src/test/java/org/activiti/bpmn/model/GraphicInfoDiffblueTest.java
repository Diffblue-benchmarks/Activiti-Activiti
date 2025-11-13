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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.activiti.bpmn.model.Message.Builder;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class GraphicInfoDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link GraphicInfo}
   *   <li>{@link GraphicInfo#setElement(BaseElement)}
   *   <li>{@link GraphicInfo#setExpanded(Boolean)}
   *   <li>{@link GraphicInfo#setHeight(double)}
   *   <li>{@link GraphicInfo#setWidth(double)}
   *   <li>{@link GraphicInfo#setX(double)}
   *   <li>{@link GraphicInfo#setXmlColumnNumber(int)}
   *   <li>{@link GraphicInfo#setXmlRowNumber(int)}
   *   <li>{@link GraphicInfo#setY(double)}
   *   <li>{@link GraphicInfo#getElement()}
   *   <li>{@link GraphicInfo#getExpanded()}
   *   <li>{@link GraphicInfo#getHeight()}
   *   <li>{@link GraphicInfo#getWidth()}
   *   <li>{@link GraphicInfo#getX()}
   *   <li>{@link GraphicInfo#getXmlColumnNumber()}
   *   <li>{@link GraphicInfo#getXmlRowNumber()}
   *   <li>{@link GraphicInfo#getY()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void GraphicInfo.<init>()",
    "BaseElement GraphicInfo.getElement()",
    "Boolean GraphicInfo.getExpanded()",
    "double GraphicInfo.getHeight()",
    "double GraphicInfo.getWidth()",
    "double GraphicInfo.getX()",
    "int GraphicInfo.getXmlColumnNumber()",
    "int GraphicInfo.getXmlRowNumber()",
    "double GraphicInfo.getY()",
    "void GraphicInfo.setElement(BaseElement)",
    "void GraphicInfo.setExpanded(Boolean)",
    "void GraphicInfo.setHeight(double)",
    "void GraphicInfo.setWidth(double)",
    "void GraphicInfo.setX(double)",
    "void GraphicInfo.setXmlColumnNumber(int)",
    "void GraphicInfo.setXmlRowNumber(int)",
    "void GraphicInfo.setY(double)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    GraphicInfo actualGraphicInfo = new GraphicInfo();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message element =
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build();
    actualGraphicInfo.setElement(element);
    actualGraphicInfo.setExpanded(true);
    actualGraphicInfo.setHeight(10.0d);
    actualGraphicInfo.setWidth(10.0d);
    actualGraphicInfo.setX(2.0d);
    actualGraphicInfo.setXmlColumnNumber(10);
    actualGraphicInfo.setXmlRowNumber(10);
    actualGraphicInfo.setY(3.0d);
    BaseElement actualElement = actualGraphicInfo.getElement();
    Boolean actualExpanded = actualGraphicInfo.getExpanded();
    double actualHeight = actualGraphicInfo.getHeight();
    double actualWidth = actualGraphicInfo.getWidth();
    double actualX = actualGraphicInfo.getX();
    int actualXmlColumnNumber = actualGraphicInfo.getXmlColumnNumber();
    int actualXmlRowNumber = actualGraphicInfo.getXmlRowNumber();

    // Assert
    assertEquals(10, actualXmlColumnNumber);
    assertEquals(10, actualXmlRowNumber);
    assertEquals(10.0d, actualHeight, 0.0);
    assertEquals(10.0d, actualWidth, 0.0);
    assertEquals(2.0d, actualX, 0.0);
    assertEquals(3.0d, actualGraphicInfo.getY(), 0.0);
    assertTrue(actualExpanded);
    assertSame(element, actualElement);
  }
}
