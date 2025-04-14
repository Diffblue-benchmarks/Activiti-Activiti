package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.process.runtime.connector.Connector;
import org.activiti.api.runtime.model.impl.IntegrationContextImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DemoApplicationDiffblueTest {
  /**
   * Test {@link DemoApplication#processTextConnector()}.
   * <p>
   * Method under test: {@link DemoApplication#processTextConnector()}
   */
  @Test
  @DisplayName("Test processTextConnector()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Connector DemoApplication.processTextConnector()"})
  void testProcessTextConnector() {
    // Arrange and Act
    Connector actualProcessTextConnectorResult = (new DemoApplication()).processTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();
    integrationContextImpl.addInBoundVariable("content", "Value");

    // Assert
    assertSame(integrationContextImpl, actualProcessTextConnectorResult.apply(integrationContextImpl));
  }

  /**
   * Test {@link DemoApplication#tagTextConnector()}.
   * <p>
   * Method under test: {@link DemoApplication#tagTextConnector()}
   */
  @Test
  @DisplayName("Test tagTextConnector()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Connector DemoApplication.tagTextConnector()"})
  void testTagTextConnector() {
    // Arrange and Act
    Connector actualTagTextConnectorResult = (new DemoApplication()).tagTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Assert
    assertSame(integrationContextImpl, actualTagTextConnectorResult.apply(integrationContextImpl));
  }

  /**
   * Test {@link DemoApplication#discardTextConnector()}.
   * <p>
   * Method under test: {@link DemoApplication#discardTextConnector()}
   */
  @Test
  @DisplayName("Test discardTextConnector()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Connector DemoApplication.discardTextConnector()"})
  void testDiscardTextConnector() {
    // Arrange and Act
    Connector actualDiscardTextConnectorResult = (new DemoApplication()).discardTextConnector();
    IntegrationContextImpl integrationContextImpl = new IntegrationContextImpl();

    // Assert
    assertSame(integrationContextImpl, actualDiscardTextConnectorResult.apply(integrationContextImpl));
  }
}
