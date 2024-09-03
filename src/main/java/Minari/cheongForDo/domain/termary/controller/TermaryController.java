package Minari.cheongForDo.domain.termary.controller;

import Minari.cheongForDo.domain.term.entity.Term;
import Minari.cheongForDo.domain.termary.service.TermaryService;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/termary")
@RestController
@AllArgsConstructor
public class TermaryController {
    private final TermaryService termaryService;



    @PostMapping("/summarize")
    public String summarizeTerm(@RequestBody Term term) {
        return termaryService.summarizeTerm(term.getTermExplain());
    }
}
