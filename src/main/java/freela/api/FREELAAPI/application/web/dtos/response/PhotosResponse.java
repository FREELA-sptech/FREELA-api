package freela.api.FREELAAPI.application.web.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotosResponse {
    private Integer id;
    private byte[] bytes;
}
