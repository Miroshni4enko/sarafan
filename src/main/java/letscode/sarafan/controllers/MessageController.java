package letscode.sarafan.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import letscode.sarafan.domain.Message;
import letscode.sarafan.domain.Views;
import letscode.sarafan.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list(){
        return messageRepo.findAll();
    }

    @GetMapping("{message_id}")
    public Message getMessage(@PathVariable("message_id") Message message){
        return message;
    }

    @PostMapping
    public Message createMessage(@RequestBody Message message){
        message.setCreated_at(LocalDateTime.now());
        return messageRepo.save(message);
    }

    @PutMapping("{message_id}")
    public Message update(@PathVariable("message_id") Message messageFromDb, @RequestBody Message messageToUp){
        BeanUtils.copyProperties(messageToUp, messageFromDb, "id");

        return messageRepo.save(messageFromDb);
    }

    @DeleteMapping("{message_id}")
    public Message deleteMessage(@PathVariable("message_id") Message message) {
        messageRepo.delete(message);
        return message;
    }


}
