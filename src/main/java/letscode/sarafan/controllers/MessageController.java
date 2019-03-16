package letscode.sarafan.controllers;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter = 5;

    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>(){{ put("id", "1"); put("text", "First message");}});
        add(new HashMap<String, String>(){{ put("id", "2"); put("text", "Second message");}});
        add(new HashMap<String, String>(){{ put("id", "3"); put("text", "Forth message");}});
        add(new HashMap<String, String>(){{ put("id", "4"); put("text", "Fifth message");}});
    }};

    @GetMapping
    public List<Map<String, String>> list(){
        return messages;
    }

    @GetMapping("{message_id}")
    public Map<String, String> getMessage(@PathVariable String message_id){
        return messages.stream()
                .filter(message -> message.get("id").equals(message_id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> createMessage(@RequestBody Map<String, String> message){
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    @PutMapping("{message_id}")
    public Map<String, String> update(@PathVariable String message_id, @RequestBody Map<String, String> params){
        Map<String, String> messageFromDb = getMessage(message_id);
        messageFromDb.putAll(params);
        messageFromDb.put("id", message_id);

        return messageFromDb;
    }

    @DeleteMapping("{message_id}")
    public Map<String, String> deleteMessage(@PathVariable String message_id) {
        Map<String, String> messageFromDb = getMessage(message_id);
        messages.remove(messageFromDb);

        return messageFromDb;

    }


}
