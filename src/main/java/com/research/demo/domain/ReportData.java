package com.research.demo.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import org.hibernate.annotations.Type;

/**
 * A ReportData.
 */
@Entity
@Table(name = "report_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReportData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "es_index")
    private String esIndex;

    @Lob
    @Type(type = "text")
    @Column(name = "cron_info")
    private String cronInfo;

    @Column(name = "display_name")
    private String displayName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ReportData name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEsIndex() {
        return esIndex;
    }

    public ReportData esIndex(String esIndex) {
        this.esIndex = esIndex;
        return this;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }

    public String getCronInfo() {
        return cronInfo;
    }

    public ReportData cronInfo(String cronInfo) {
        this.cronInfo = cronInfo;
        return this;
    }

    public void setCronInfo(String cronInfo) {
        this.cronInfo = cronInfo;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ReportData displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportData reportData = (ReportData) o;
        if (reportData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reportData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReportData{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", esIndex='" + getEsIndex() + "'" +
            ", cronInfo='" + getCronInfo() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            "}";
    }
}
