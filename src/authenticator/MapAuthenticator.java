package authenticator;

import java.util.HashMap;
import java.util.Map;

public class MapAuthenticator extends Authenticator
{
    final private Map<String, String>
            mapData = new HashMap<>();
    public MapAuthenticator() {
        mapData.put("admin", "admin");
        mapData.put("samatar", "rata");
    }
    @Override
    protected boolean isLoginExists(String username) {
        return mapData.containsKey(username);
    }
    @Override
    protected String getPassword(String username) {
        return mapData.get(username);
    }
}