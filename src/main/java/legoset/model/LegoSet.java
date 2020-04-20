package legoset.model;

import java.time.Year;

import javax.persistence.*;

import lombok.*;

import jpa.YearConverter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class LegoSet {

    @Id
    private String number;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    @Convert(converter=YearConverter.class)
    private Year year;

    @Column(nullable=false)
    private int pieces;

    @ManyToOne(optional=false)
    @ToString.Exclude
    private Theme theme;

}
