
package cl.pedidos360.pedidos.admin;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka/consumer-groups")
public class KafkaConsumerGroupAdminController {

    private final KafkaConsumerGroupAdminService groupAdminService;

    public KafkaConsumerGroupAdminController(
            KafkaConsumerGroupAdminService groupAdminService) {
        this.groupAdminService = groupAdminService;
    }

    @GetMapping
    public ResponseEntity<?> listarGrupos() {
        try {
            List<Map<String, Object>> grupos =
                    groupAdminService.listarGrupos();

            return ResponseEntity.ok(grupos);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Operación interrumpida"));

        } catch (ExecutionException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<?> consultarGrupo(
            @PathVariable String groupId) {
        try {
            return ResponseEntity.ok(
                    groupAdminService.consultarGrupo(groupId));

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
