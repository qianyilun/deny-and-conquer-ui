# Install JavaFX Screen Builder

1. Go to website `https://gluonhq.com/products/scene-builder/` and download
2. Install it
3. If you are a Windows user, you need to fix a known bug by following this [tutorial](https://youtu.be/T3NlWMzPyXM?t=291)



## Game State

```JSON
{
    players: [
        "palyer1": {
        	name: "player1",
        	id: "1",
        	ip: "1.1.1.1",
        	color: "blue",
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

