package dev.beenary.repository;

import generated.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Repository
public class UserRepository {
    private static final Map<String, User> users = new HashMap<>();

    @PostConstruct
    public void initData() {
        User edgar = new User();
        edgar.setId("34385351M");
        edgar.setNombre("EDGAR SANCHEZ PINEDA");
        edgar.setPermitido("YES");
        users.put(edgar.getId(), edgar);

        User paco = new User();
        paco.setId("18744558M");
        paco.setNombre("FRANCISCO UÑACH");
        paco.setPermitido("NO");

        users.put(paco.getId(), paco);

        User franco = new User();
        franco.setId("65855223P");
        franco.setNombre("FRANCO CAUDILLO UNIVERSAL");
        franco.setPermitido("SI");

        users.put(franco.getId(), franco);

        User pablo = new User();
        pablo.setId("999999999H");
        pablo.setNombre("PABLO COLETAS RATA");
        pablo.setPermitido("YES");

        users.put(pablo.getId(), pablo);

    }

    public User findUser(String id) {
        Assert.notNull(id, "Debes introducir un documento de identidad a buscar");
        return users.get(id);
    }


}
