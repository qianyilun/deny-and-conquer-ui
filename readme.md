# Install JavaFX Screen Builder

1. Go to website `https://gluonhq.com/products/scene-builder/` and download
2. Install it
3. If you are a Windows user, you need to fix a known bug by following this [tutorial](https://youtu.be/T3NlWMzPyXM?t=291)



## Game State

```JSON
{
    players: [
        "palyer1": {
        	playerName: "player1",
        	playerId: "1",
        	playerIP: "1.1.1.1",
        	playerColor: "blue",
        	thickness: "1"        	        	
        },
        "player2": {
			// ...
        }
    ],
    server: {
		server_ip: "192.168.1.1",
        "player_id"        
    },
    game {		
        map<key = {row, col}, value = {player_id, timestamp, percentage}>,
		winner: "player1"
    },
	game_config: {
    	thickness: "1",
        minimum_percentage: "50%",        
	}    
}
```



### Use Case



#### Prepare step

* Client enters its name and server's IP
  * Program sends this new client's information to server
  * Program waits for server's response (with a timeout to resend)
    * response: client's color, thickness, percentage, other client's IP
* Client swith UI to canvas



### Notes

* Each program will run server and client at the same time
* try to use TCP