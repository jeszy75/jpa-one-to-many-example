package legoset;

import java.time.Year;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import legoset.model.LegoSet;

import legoset.model.Theme;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LegoSetService {

    private EntityManager em;

    public LegoSetService(EntityManager em) {
        this.em = em;
    }

    public Optional<Theme> findThemeByName(String name) {
        try {
            Theme theme = em.createQuery("SELECT t FROM Theme t WHERE t.name = :name", Theme.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(theme);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public LegoSet createLegoSet(String number, String name, Year year, int pieces, String themeName) {
        Optional<Theme> theme = findThemeByName(themeName);
        if (theme.isPresent()) {
            LegoSet legoSet = new LegoSet(number, name, year, pieces, theme.get());
            theme.get().getLegoSets().add(legoSet);
            em.persist(legoSet);
            return legoSet;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void createThemes() {
        em.getTransaction().begin();
        em.persist(new Theme("Architecture", Year.of(2008)));
        em.persist(new Theme("City", Year.of(1978)));
        em.persist(new Theme("Star Wars", Year.of(1999)));
        em.getTransaction().commit();
    }

    private void createLegoSets() {
        em.getTransaction().begin();
        createLegoSet("60073", "Service Truck", Year.of(2015), 233, "City");
        createLegoSet("75211", "Imperial TIE Fighter", Year.of(2018), 519, "Star Wars");
        createLegoSet("21034", "London", Year.of(2017), 468, "Architecture");
        createLegoSet("21044", "Paris", Year.of(2017), 649, "Architecture");
        em.getTransaction().commit();
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();
        LegoSetService service = new LegoSetService(em);

        service.createThemes();
        service.createLegoSets();

        service.findThemeByName("Architecture").ifPresent(theme -> {
            log.info(theme);
            log.info("Lego set(s) in the {} series:", theme.getName());
            theme.getLegoSets().forEach(log::info);
        });

        em.close();
        emf.close();
    }

}
