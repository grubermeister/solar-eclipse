package dev.atomixsoft.solar_eclipse.client.util.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Controller {
    private final Input m_In;
    private final Map<String, List<Integer>> m_InputMap;


    public Controller() {
        m_In = Input.Instance();
        m_InputMap = new HashMap<>();
    }

    public boolean isPressed(String controlName) {
        if(!m_InputMap.containsKey(controlName)) return false;

        List<Integer> bindings = m_InputMap.get(controlName);
        for(Integer key : bindings)
            if(m_In.isKeyDown(key)) return m_In.isKeyDown(key);

        return false;
    }

    public boolean justPressed(String controlName) {
        if(!m_InputMap.containsKey(controlName)) return false;

        List<Integer> bindings = m_InputMap.get(controlName);
        for(Integer key : bindings)
            if(m_In.keyJustPressed(key)) return m_In.keyJustPressed(key);

        return false;
    }

    public void addBinding(String controlName, int key) {
        m_InputMap.putIfAbsent(controlName, new ArrayList<>());
        List<Integer> bindings = m_InputMap.get(controlName);

        for(int keyBind : bindings) {
            if (keyBind == key) {
                System.out.println(controlName + " is already bound with " + key + "!");
                return;
            }
        }

        bindings.add(key);
    }
}
