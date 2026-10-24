package pe.test.exchange.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversion")
@Getter
@Setter
@NoArgsConstructor
public class ConversionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal monto;

    @Column(nullable = false, length = 3)
    private String monedaOrigen;

    @Column(nullable = false, length = 3)
    private String monedaDestino;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal tipoCambio;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal montoConvertido;

    @Column(nullable = false)
    private LocalDateTime fechaOperacion;
}
