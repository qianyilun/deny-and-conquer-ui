package model.status.server_client;

import model.dto.ConfigurationDTO;
import model.dto.PlayerDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for save all information about client side for server-client communication usage
 */

public class ClientStatus {
    private static ClientStatus ourInstance = new ClientStatus();

    public static ClientStatus getInstance() {
        return ourInstance;
    }

    private ClientStatus() {
    }

    private List<PlayerDTO> playerDTOS;
    private String serverIP;
    private ConfigurationDTO configurationDTO;


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
