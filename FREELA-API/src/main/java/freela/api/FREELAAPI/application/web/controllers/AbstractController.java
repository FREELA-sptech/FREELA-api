package freela.api.FREELAAPI.application.web.controllers;

import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
public class AbstractController {

    @Schema(name = "ID", description = "ID atual", example = "1")
    private Integer idAtual;

    public void auth(){

    }

}
