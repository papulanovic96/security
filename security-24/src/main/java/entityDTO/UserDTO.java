package entityDTO;

import entity.User;
import entityDTO.AuthorityDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<AuthorityDTO> userAuthorities;

    public UserDTO(Long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDTO(User u) {
        this.id = u.getId();
        this.email = u.getEmail();
        this.firstName = u.getFirstName();
        this.lastName = u.getLastName();

        this.userAuthorities = u.getUserAuthorities().stream().map(ua ->
                new AuthorityDTO(ua.getAuthority())
        ).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<AuthorityDTO> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(Set<AuthorityDTO> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

}
