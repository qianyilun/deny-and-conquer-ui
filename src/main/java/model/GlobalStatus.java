package model;

import java.util.ArrayList;
import java.util.List;

public class GlobalStatus {
    private static GlobalStatus ourInstance = new GlobalStatus();

    private ConfigurationDTO configurationDTO;

    public static GlobalStatus getInstance() {
        return ourInstance;
    }

    public ConfigurationDTO getConfigurationDTO() {
        return configurationDTO;
    }

    public void setConfigurationDTO(ConfigurationDTO configurationDTO) {
        this.configurationDTO = configurationDTO;
    }
}
