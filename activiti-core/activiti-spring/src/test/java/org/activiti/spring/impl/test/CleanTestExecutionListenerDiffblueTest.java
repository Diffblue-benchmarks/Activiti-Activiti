package org.activiti.spring.impl.test;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CleanTestExecutionListenerDiffblueTest {
  /**
   * Test new {@link CleanTestExecutionListener} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link CleanTestExecutionListener}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void CleanTestExecutionListener.<init>()"})
  public void testNewCleanTestExecutionListener() {
    // Arrange, Act and Assert
    assertEquals(Integer.MAX_VALUE, (new CleanTestExecutionListener()).getOrder());
  }
}
