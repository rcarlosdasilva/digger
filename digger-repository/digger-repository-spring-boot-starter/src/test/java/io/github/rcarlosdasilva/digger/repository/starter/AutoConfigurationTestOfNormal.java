package io.github.rcarlosdasilva.digger.repository.starter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("normal")
@SpringBootTest(classes = {DiggerRepositoryAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
public class AutoConfigurationTestOfNormal {

  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
      .withConfiguration(AutoConfigurations.of(DiggerRepositoryAutoConfiguration.class));

  @Test
  void test() {
    contextRunner.run(context -> {
      System.out.println(context);
    });
  }

}
