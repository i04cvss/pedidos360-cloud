
package cl.pedidos360.pedidos.admin;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka/topics")
public class KafkaTopicAdminController {

    private final KafkaTopicAdminService topicAdminService;

    public KafkaTopicAdminController(
            KafkaTopicAdminService topicAdminService) {
        this.topicAdminService = topicAdminService;
    }

    @PostMapping
    public ResponseEntity<?> crearTopic(
            @RequestBody Map<String, Object> datos) {
        try {
            String nombre = (String) datos.get("nombre");
            int particiones = ((Number) datos.get("particiones")).intValue();
            short replicas = ((Number) datos.get("replicas")).shortValue();

            String resultado = topicAdminService.crearTopic(
                    nombre, particiones, replicas);

            return ResponseEntity.ok(Map.of("mensaje", resultado));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Operación interrumpida"));
        } catch (ExecutionException | RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTopics() {
        try {
            List<String> topics = topicAdminService.listarTopics();
            return ResponseEntity.ok(topics);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Operación interrumpida"));
        } catch (ExecutionException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<?> inspeccionarTopic(
            @PathVariable String nombre) {
        try {
            return ResponseEntity.ok(
                    topicAdminService.inspeccionarTopic(nombre));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Operación interrumpida"));
        } catch (ExecutionException | RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<?> actualizarTopic(
            @PathVariable String nombre,
            @RequestBody Map<String, Object> datos) {
        try {
            Long retentionMs = datos.get("retentionMs") == null
                    ? null
                    : ((Number) datos.get("retentionMs")).longValue();

            String cleanupPolicy =
                    (String) datos.get("cleanupPolicy");

            String resultado =
                    topicAdminService.actualizarConfiguracionTopic(
                            nombre, retentionMs, cleanupPolicy);

            return ResponseEntity.ok(Map.of("mensaje", resultado));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Operación interrumpida"));
        } catch (ExecutionException | RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
