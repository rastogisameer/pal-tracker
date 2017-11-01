package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private String port, memory, index, address;

    @GetMapping("/env")
    public Map<String, String> getEnv(){

        Map<String, String> env = new HashMap<String, String>();
        env.put("PORT", port);
        env.put("MEMORY_LIMIT", memory);
        env.put("CF_INSTANCE_INDEX", index);
        env.put("CF_INSTANCE_ADDR", address);
        return env;
    }
    public EnvController(@Value("${PORT:NULL}") String port, @Value("${MEMORY_LIMIT:NULL}") String memory, @Value("${CF_INSTANCE_INDEX:NULL}") String index, @Value("${CF_INSTANCE_ADDR:NULL}") String address) {
        this.port = port;
        this.memory = memory;
        this.index = index;
        this.address = address;
    }
}
