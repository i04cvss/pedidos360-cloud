
package cl.pedidos360.pedidos.admin;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerGroupAdminService {

    private final AdminClient adminClient;

    public KafkaConsumerGroupAdminService(AdminClient adminClient) {
        this.adminClient = adminClient;
    }

    public List<Map<String, Object>> listarGrupos()
            throws ExecutionException, InterruptedException {

        return adminClient.listConsumerGroups()
                .all()
                .get()
                .stream()
                .map(grupo -> {
                    Map<String, Object> resultado = new HashMap<>();
                    resultado.put("groupId", grupo.groupId());
                    resultado.put("estado", grupo.state().toString());
                    return resultado;
                })
                .toList();
    }

    public Map<String, Object> consultarGrupo(String groupId)
            throws ExecutionException, InterruptedException {

        var descripcion = adminClient
                .describeConsumerGroups(List.of(groupId))
                .all()
                .get()
                .get(groupId);

        Map<String, Object> resultado = new HashMap<>();

        resultado.put("groupId", descripcion.groupId());
        resultado.put("estado", descripcion.state().toString());
        resultado.put("miembros", descripcion.members().size());

        resultado.put("integrantes", descripcion.members().stream()
                .map(miembro -> {
                    Map<String, Object> datos = new HashMap<>();
                    datos.put("memberId", miembro.consumerId());
                    datos.put("clientId", miembro.clientId());
                    datos.put("host", miembro.host());
                    datos.put("particiones", miembro.assignment()
                            .topicPartitions().size());
                    return datos;
                })
                .toList());

        return resultado;
    }
}
