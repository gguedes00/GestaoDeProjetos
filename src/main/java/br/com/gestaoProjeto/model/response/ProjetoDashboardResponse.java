package br.com.gestaoProjeto.model.response;

import java.math.BigDecimal;


public interface  ProjetoDashboardResponse {

    Integer getQtdEmAnalise();
    Integer getQtdAnaliseRealizada();
    Integer getQtdAnaliseAprovada();
    Integer getQtdIniciado();
    Integer getQtdPlanejado();
    Integer getQtdEmAndamento();
    Integer getQtdEncerrado();
    Integer getQtdCancelado();
    BigDecimal getTotalOrcadoEmAnalise();
    BigDecimal getTotalOrcadoAnaliseRealizada();
    BigDecimal getTotalOrcadoAnaliseAprovada();
    BigDecimal getTotalOrcadoIniciado();
    BigDecimal getTotalOrcadoPlanejado();
    BigDecimal getTotalOrcadoEmAndamento();
    BigDecimal getTotalOrcadoEncerrado();
    BigDecimal getTotalOrcadoCancelado();
    Double getMediaDiasEncerrados();
    Integer getTotalMembrosUnicos();
}
