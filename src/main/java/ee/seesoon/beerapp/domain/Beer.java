package ee.seesoon.beerapp.domain;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Beer.
 */
@Entity
@Table(name = "beer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Beer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Beer name, max symbols 100
     */
    @NotNull
    @Size(min = 1, max = 100)
    @ApiModelProperty(value = "Beer name, max symbols 100", required = true)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /**
     * Beer alcohol percentage
     */
    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @ApiModelProperty(value = "Beer alcohol percentage", required = true)
    @Column(name = "alcohol_percentage", nullable = false)
    private Double alcoholPercentage;

    /**
     * Beer description
     */
    @NotNull
    @Size(min = 1, max = 2000)
    @ApiModelProperty(value = "Beer description", required = true)
    @Column(name = "description", length = 2000, nullable = false)
    private String description;

    /**
     * Date when beer was added to database
     */
    @NotNull
    @ApiModelProperty(value = "Date when beer was added to database", required = true)
    @Column(name = "added_date", nullable = false)
    private LocalDate addedDate;

    @Column(name = "adress_of_origin")
    private String adressOfOrigin;

    @Column(name = "style")
    private String style;

    @Column(name = "comment")
    private String comment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Beer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public Beer alcoholPercentage(Double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
        return this;
    }

    public void setAlcoholPercentage(Double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public String getDescription() {
        return description;
    }

    public Beer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public Beer addedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
        return this;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public String getAdressOfOrigin() {
        return adressOfOrigin;
    }

    public Beer adressOfOrigin(String adressOfOrigin) {
        this.adressOfOrigin = adressOfOrigin;
        return this;
    }

    public void setAdressOfOrigin(String adressOfOrigin) {
        this.adressOfOrigin = adressOfOrigin;
    }

    public String getStyle() {
        return style;
    }

    public Beer style(String style) {
        this.style = style;
        return this;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getComment() {
        return comment;
    }

    public Beer comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beer)) {
            return false;
        }
        return id != null && id.equals(((Beer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", alcoholPercentage=" + getAlcoholPercentage() +
            ", description='" + getDescription() + "'" +
            ", addedDate='" + getAddedDate() + "'" +
            ", adressOfOrigin='" + getAdressOfOrigin() + "'" +
            ", style='" + getStyle() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
