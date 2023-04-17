package freela.api.FREELAAPI.application.web.dtos.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequest {

    @NotNull
    @Schema(name = "Descrição", description = "Descrição da proposta ou do pedido", example = "Arte com traços finos!")
    private String description;

    @NotNull
    @Schema(name = "Titulo", description = "Titulo do pedido", example = "Tatuagem realista de rosto")
    private String title;

    @NotNull
    @Schema(name = "Categoria", description = "Categoria do pedido", example = "Tatuagem")
    private Integer category;

    @NotNull
    @Schema(name = "Valor maximo", description = "Valor maximo a pagar", example = "150.00")
    private Double maxValue;

    @NotNull
    @NotEmpty
    @Schema(name = "SubCategorias", description = "Lista de subCategorias")
    private ArrayList<Integer> subCategoryIds;
}
