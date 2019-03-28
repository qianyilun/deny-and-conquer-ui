package model;

import java.util.ArrayList;
import java.util.List;

public class GlobalStatus {
    private static GlobalStatus ourInstance = new GlobalStatus();

    private List<PlayerDTO> playerDTOS;
    private String serverIP;
    private ConfigurationDTO configurationDTO;

    public static GlobalStatus getInstance() {
        return ourInstance;
    }

    public List<PlayerDTO> getPlayerDTOS() {
        return playerDTOS;
    }

    public void setPlayerDTOS(List<PlayerDTO> playerDTOS) {
        this.playerDTOS = new ArrayList<>(playerDTOS);
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public ConfigurationDTO getConfigurationDTO() {
        return configurationDTO;
    }

    public void setConfigurationDTO(ConfigurationDTO configurationDTO) {
        this.configurationDTO = configurationDTO;
    }
}
