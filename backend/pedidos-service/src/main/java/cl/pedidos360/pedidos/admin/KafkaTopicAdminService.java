
package cl.pedidos360.pedidos.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.AlterConfigOp;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.stereotype.Service;

@Service
public class KafkaTopicAdminService {

    private final AdminClient adminClient;

    public KafkaTopicAdminService(AdminClient adminClient) {
        this.adminClient = adminClient;
    }

    public String crearTopic(String nombre, int particiones, short replicas)
            throws ExecutionException, InterruptedException {

        if (nombre == null || nombre.isBlank()
                || particiones < 1 || replicas < 1) {
            throw new IllegalArgumentException("Parámetros inválidos");
        }

        NewTopic topic = new NewTopic(nombre, particiones, replicas);
        adminClient.createTopics(List.of(topic)).all().get();

        return "Tópico creado correctamente: " + nombre;
    }

    public List<String> listarTopics()
            throws ExecutionException, InterruptedException {

        return adminClient.listTopics()
                .names().get()
                .stream().sorted().toList();
    }

    public Map<String, Object> inspeccionarTopic(String nombre)
            throws ExecutionException, InterruptedException {

        var descripcion = adminClient.describeTopics(List.of(nombre))
                .allTopicNames().get().get(nombre);

        ConfigResource recurso = new ConfigResource(
                ConfigResource.Type.TOPIC, nombre);

        Config configuracion = adminClient.describeConfigs(List.of(recurso))
                .all().get().get(recurso);

        Map<String, Object> resultado = new HashMap<>();

        resultado.put("nombre", nombre);
        resultado.put("particiones", descripcion.partitions().size());
        resultado.put("replicas",
                descripcion.partitions().get(0).replicas().size());

        configuracion.entries().forEach(entry ->
                resultado.put(entry.name(), entry.value()));

        return resultado;
    }

    public String actualizarConfiguracionTopic(
            String nombre, Long retentionMs, String cleanupPolicy)
            throws ExecutionException, InterruptedException {

        ConfigResource recurso = new ConfigResource(
                ConfigResource.Type.TOPIC, nombre);

        Collection<AlterConfigOp> operaciones = new ArrayList<>();

        if (retentionMs != null) {
            if (retentionMs < 1) {
                throw new IllegalArgumentException(
                        "La retención debe ser positiva");
            }

            operaciones.add(new AlterConfigOp(
                    new ConfigEntry("retention.ms", retentionMs.toString()),
                    AlterConfigOp.OpType.SET));
        }

        if (cleanupPolicy != null) {
            if (!cleanupPolicy.equals("delete")
                    && !cleanupPolicy.equals("compact")) {
                throw new IllegalArgumentException(
                        "Política de limpieza inválida");
            }

            operaciones.add(new AlterConfigOp(
                    new ConfigEntry("cleanup.policy", cleanupPolicy),
                    AlterConfigOp.OpType.SET));
        }

        if (operaciones.isEmpty()) {
            throw new IllegalArgumentException(
                    "No hay configuraciones para modificar");
        }

        adminClient.incrementalAlterConfigs(
                Map.of(recurso, operaciones)).all().get();

        return "Configuración actualizada: " + nombre;
    }
}
