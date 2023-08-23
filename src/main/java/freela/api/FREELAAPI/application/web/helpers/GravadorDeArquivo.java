package freela.api.FREELAAPI.application.web.helpers;

import freela.api.FREELAAPI.application.web.dtos.response.OrderResponse;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

public class GravadorDeArquivo {

    public static byte[] gravaArquivoTxt(List<OrderResponse> lista) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             BufferedWriter saida = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream))) {

            int contaRegDadosGravados = 0;

            // Monta o registro de header
            String header = "00ID   Descrição do Pedido                       Título do Pedido                          Valor Max   Data de Expiração             "
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "01";

            // Grava o registro de header
            gravaRegistro(saida, header);

            // Monta e grava os registros de dados ou de corpo
            for (OrderResponse order : lista) {
                String corpo = "02";
                corpo += String.format("%-5s", order.getId());
                corpo += String.format("%-40.40s", order.getDescription());
                corpo += String.format("%-40.40s", order.getTitle());
                corpo += String.format("%-12.2f", order.getMaxValue());
                corpo += String.format("%-30s", order.getExpirationTime());
                corpo += "null";
                gravaRegistro(saida, corpo);
                contaRegDadosGravados++;
            }

            // Monta e grava o registro de trailer
            String trailer = "01" + String.format("%010d", contaRegDadosGravados);
            gravaRegistro(saida, trailer);

            // Retorna o conteúdo do arquivo como array de bytes
            saida.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }

    private static void gravaRegistro(BufferedWriter saida, String registro) throws IOException {
        saida.append(registro).append("\n");
    }
}
