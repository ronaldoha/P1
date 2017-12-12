package code;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class User implements Serializable
{
    private static final long serialVersionUID = 5864896800675704551L;
    private List<String> list;
    private Map<String, String> map;

    public List<String> getList()
    {
        return list;
    }
    public void setList(List<String> list)
    {
        this.list = list;
    }
    public Map<String, String> getMap()
    {
        return map;
    }

    public void setMap(Map<String, String> map)
    {
        this.map = map;
    }
}
