package freela.api.FREELAAPI.application.web.dtos.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequest {

    @NotNull
    @Schema(name = "description", description = "Descrição da proposta ou do pedido", example = "Arte com traços finos!")
    private String description;

    @NotNull
    @Schema(name = "title", description = "Titulo do pedido", example = "Tatuagem realista de rosto")
    private String title;

    @NotNull
    @Schema(name = "expirationTime", description = "Expiração do Pedido", example = "8 Dias")
    private String expirationTime;

    @NotNull
    @Schema(name = "category", description = "Categoria do pedido", example = "Tatuagem")
    private List<Integer> categoryIds;

    @NotNull
    @Schema(name = "maxValue", description = "Valor maximo a pagar", example = "150.00")
    private Double maxValue;

    @NotNull
    @NotEmpty
    @Schema(name = "subCategoryIds", description = "Lista de subCategorias")
    private List<Integer> subCategoryIds;
}
