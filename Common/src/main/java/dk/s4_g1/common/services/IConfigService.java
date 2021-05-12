package dk.s4_g1.common.services;

import java.util.Optional;

public interface IConfigService {
  public Optional<String> getConfig(String key);
}
