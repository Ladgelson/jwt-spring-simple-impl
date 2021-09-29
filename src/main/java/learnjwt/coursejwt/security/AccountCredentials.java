package learnjwt.coursejwt.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCredentials implements Serializable {
    private static final long serialVersionUID = 1l;

    private String username;
    private String password;
}
