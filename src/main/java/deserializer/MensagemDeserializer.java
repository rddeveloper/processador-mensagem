package deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Mensagem;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class MensagemDeserializer implements Deserializer<Mensagem> {
    @Override
    public Mensagem deserialize(String s, byte[] mensagem) {
        try {
            return new ObjectMapper().readValue(mensagem, Mensagem.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
