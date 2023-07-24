package br.com.incode.base.infrastructure.util;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {

    @Column(name = "DATA_INCLUSAO")
    protected LocalDateTime dataInclusao;

    @Column(name = "DATA_ALTERACAO")
    protected LocalDateTime dataAlteracao;

}
