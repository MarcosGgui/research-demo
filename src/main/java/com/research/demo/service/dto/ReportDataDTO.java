package com.research.demo.service.dto;
import com.research.demo.domain.AbstractAuditingEntity;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ReportData entity.
 */
public class ReportDataDTO extends AbstractAuditingEntity implements Serializable {

    private Long id;

    private String name;

    private String esIndex;

    @Lob
    private String cronInfo;

    private String displayName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }

    public String getCronInfo() {
        return cronInfo;
    }

    public void setCronInfo(String cronInfo) {
        this.cronInfo = cronInfo;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReportDataDTO reportDataDTO = (ReportDataDTO) o;
        if (reportDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reportDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReportDataDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", esIndex='" + getEsIndex() + "'" +
            ", cronInfo='" + getCronInfo() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            "}";
    }
}
