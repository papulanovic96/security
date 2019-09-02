package entityDTO;

import entity.Authority;

public class AuthorityDTO {
    private Long id;
    private String name;

    public AuthorityDTO() {}

    public AuthorityDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorityDTO(Authority r) {
        this.id = r.getId();
        this.name = r.getName();
    }

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


}
