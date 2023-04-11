package freela.api.FREELAAPI.application.web.controllers;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbstractController {
    @Schema(name = "ID", description = "ID atual", example = "1")
    private Integer idAtual;

    public void auth(){

    }

}
