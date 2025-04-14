package org.activiti.examples;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DemoApplicationConfiguration.class})
@ExtendWith(SpringExtension.class)
class DemoApplicationConfigurationDiffblueTest {
  @Autowired
  private DemoApplicationConfiguration demoApplicationConfiguration;

  /**
   * Test {@link DemoApplicationConfiguration#userDetailsService()}.
   * <ul>
   *   <li>Given {@link DemoApplicationConfiguration}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplicationConfiguration#userDetailsService()}
   */
  @Test
  @DisplayName("Test userDetailsService(); given DemoApplicationConfiguration")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.security.core.userdetails.UserDetailsService DemoApplicationConfiguration.userDetailsService()"})
  void testUserDetailsService_givenDemoApplicationConfiguration() {
    // Arrange, Act and Assert
    assertTrue(demoApplicationConfiguration.userDetailsService() instanceof InMemoryUserDetailsManager);
  }

  /**
   * Test {@link DemoApplicationConfiguration#userDetailsService()}.
   * <ul>
   *   <li>Given {@link DemoApplicationConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoApplicationConfiguration#userDetailsService()}
   */
  @Test
  @DisplayName("Test userDetailsService(); given DemoApplicationConfiguration (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.security.core.userdetails.UserDetailsService DemoApplicationConfiguration.userDetailsService()"})
  void testUserDetailsService_givenDemoApplicationConfiguration2() {
    // Arrange, Act and Assert
    assertTrue((new DemoApplicationConfiguration()).userDetailsService() instanceof InMemoryUserDetailsManager);
  }
}
