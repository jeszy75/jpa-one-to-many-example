package legoset.model;

import jpa.YearConverter;
import lombok.*;

import javax.persistence.*;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
public class Theme {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(nullable=false, unique=true)
    private String name;

    @Column(nullable=false)
    @Convert(converter=YearConverter.class)
    private Year startYear;

    @Convert(converter=YearConverter.class)
    private Year endYear;

    @OneToMany(mappedBy="theme")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<LegoSet> legoSets = new HashSet<>();

    public Theme(String name, Year startYear, Year endYear) {
        this.name = name;
        this.startYear = startYear;
        this.endYear = endYear;
        System.out.println(this);
    }

    public Theme(String name, Year startYear) {
        this(name, startYear, null);
    }

}
